package cn.message.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.WebUtils;
import com.alipay.api.request.AlipayOpenAuthTokenAppRequest;
import com.alipay.api.request.AlipayOpenPublicMessageCustomSendRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.request.AlipayUserUserinfoShareRequest;
import com.alipay.api.response.AlipayOpenAuthTokenAppResponse;
import com.alipay.api.response.AlipayOpenPublicMessageCustomSendResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;
import com.alipay.api.response.AlipayUserUserinfoShareResponse;

import cn.message.model.alipay.AlipayPostMessageModel;
import cn.message.model.alipay.AlipayServiceEnvConstants;
import cn.message.model.alipay.AlipayUserInfo;
import cn.message.model.alipay.TemplateDataAlipayModel;
import cn.message.model.alipay.TemplateDataAlipayModel.Template;
import cn.message.model.alipay.TemplateDataAlipayModel.Property;
import cn.message.model.alipay.message.IMessage;
import cn.message.service.IAlipayService;
import cn.message.utils.GsonUtil;
import cn.message.utils.alipay.AlipayAPIClientFactory;
import cn.message.utils.alipay.dispatch.MessageDispatch;
import cn.message.utils.alipay.dispatch.executor.AbstractGeneralExecutor;
import cn.message.utils.wechat.HttpRequest;
import cn.sdk.util.DateUtil;
import cn.sdk.util.GsonBuilderUtil;
import cn.sdk.util.HttpUtils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayOpenPublicMessageCustomSendRequest;
import com.alipay.api.request.AlipayOpenPublicMessageSingleSendRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayOpenPublicMessageCustomSendResponse;
import com.alipay.api.response.AlipayOpenPublicMessageSingleSendResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

@Service("alipayService")
@SuppressWarnings(value = "all")
public class IAlipayServiceImpl implements IAlipayService {

	Logger logger = Logger.getLogger(IAlipayServiceImpl.class);

	@Value("${alipayPublicKey}")
	private String alipayPublicKey;

	@Value("${signCharset}")
	private String signCharset;

	@Value("${charset}")
	private String charset;

	@Value("${signType}")
	private String signType;

	@Value("${appId}")
	private String appId;

	@Value("${privateKey}")
	private String privateKey;

	@Value("${publicKey}")
	private String publicKey;

	@Value("${alipayGateWay}")
	private String alipayGateWay;

	@Value("${grantType}")
	private String grantType;

	@Override
	public void processPostMessage(AlipayPostMessageModel model) {
		try {
			AbstractGeneralExecutor executor = MessageDispatch.dispatch(model.getMsgType(), model.getEventType());
			// 执行消息处理
			IMessage message = executor.invoke(model);

			// 最终转换出来的json格式消息
			String jsonMessage = GsonUtil.toJson(message);

			// 初始化支付宝sdk客户端
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			// 获取发送消息接口实例
			AlipayOpenPublicMessageCustomSendRequest request = new AlipayOpenPublicMessageCustomSendRequest();
			// 将报文放入content中
			request.setBizContent(jsonMessage);
			// 发送消息
			AlipayOpenPublicMessageCustomSendResponse response = alipayClient.execute(request);
			if (null == response || !response.isSuccess()) {
				logger.info("异步发送失败 code=" + response.getCode() + "msg：" + response.getMsg());
			}
		} catch (Exception e) {
			logger.info("支付宝异步发送消息异常", e);
		}
	}
	
	@Override
	public AlipayUserInfo callback4UserId(String code) {
		String content1 = "";
		String content2 = "";
		String content3 = "";
		try {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Map<String, String> parameters = new LinkedHashMap<String, String>();
			parameters.put("app_id", AlipayServiceEnvConstants.APP_ID);
			parameters.put("method", "alipay.system.oauth.token");
			parameters.put("charset", "GBK");
			parameters.put("sign_type", "RSA");
			parameters.put("timestamp", dateFormat.format(new Date()));
			parameters.put("version", "1.0");
			parameters.put("grant_type", "authorization_code");
			parameters.put("code", code);
			parameters.put("refresh_token", "");
			parameters.put("sign", AlipaySignature.rsaSign(parameters, AlipayServiceEnvConstants.PRIVATE_KEY, "GBK"));
			content1 = WebUtils.doPost(AlipayServiceEnvConstants.ALIPAY_GATEWAY, parameters, 100000, 100000);
			logger.info("alipay.system.oauth.token响应的信息是：" + content1);
			
			
			JSONObject jsonObject = JSON.parseObject(content1);
			String xddd = jsonObject.getString("alipay_system_oauth_token_response");
			jsonObject = JSON.parseObject(xddd);
			String accessToken = jsonObject.getString("access_token");

			Map<String, String> param = new LinkedHashMap<String, String>();
			param.put("method", "alipay.user.userinfo.share");
			param.put("timestamp", dateFormat.format(new Date()));
			param.put("app_id", AlipayServiceEnvConstants.APP_ID);
			param.put("auth_token", accessToken);
			param.put("charset", "GBK");
			param.put("version", "1.0");
			param.put("sign_type", "RSA");
			param.put("sign", AlipaySignature.rsaSign(param, AlipayServiceEnvConstants.PRIVATE_KEY, "UTF-8"));
			content2 = WebUtils.doPost(AlipayServiceEnvConstants.ALIPAY_GATEWAY, param, 100000, 100000);
			logger.info("alipay.user.userinfo.share：" + content2);
			
			JSONObject jsonObject2 = JSON.parseObject(content2);
			String userInfoStr = jsonObject2.getString("alipay_user_userinfo_share_response");
			jsonObject2 = JSON.parseObject(userInfoStr);
			//用户手机号
			String mobile = jsonObject2.getString("mobile");
			String alipayUserId = jsonObject2.getString("alipay_user_id");
			String nickName = jsonObject2.getString("real_name");
			String avatar = jsonObject2.getString("avatar");
			return new AlipayUserInfo(alipayUserId, nickName, avatar, mobile);
			
			/*AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			// 3. 利用authCode获得authToken
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(code);
			oauthTokenRequest.setGrantType(AlipayServiceEnvConstants.GRANT_TYPE);
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient.execute(oauthTokenRequest);
			
			logger.info("oauthTokenResponse：" + JSON.toJSONString(oauthTokenResponse));
			// 成功获得authToken
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
				String access_Token = oauthTokenResponse.getAccessToken();
				logger.info("accessToken：" + access_Token);
				
				AlipayUserInfoShareRequest userinfoShareRequest = new AlipayUserInfoShareRequest();
				AlipayUserInfoShareResponse userinfoShareResponse = alipayClient.execute(userinfoShareRequest,access_Token);
				logger.info("userinfoShareResponse：" + JSON.toJSONString(userinfoShareResponse));
				
				// 4. 利用authToken获取用户信息
				AlipayUserUserinfoShareRequest userUserinfoShareRequest = new AlipayUserUserinfoShareRequest();
				AlipayUserUserinfoShareResponse userUserinfoShareResponse = alipayClient.execute(userUserinfoShareRequest, access_Token);
				logger.info("userUserinfoShareResponse：" + JSON.toJSONString(userUserinfoShareResponse));

				// 成功获得用户信息
				if (null != userinfoShareResponse && userinfoShareResponse.isSuccess()) {
					// 获取成功 返回给web
					System.out.println("用户手机号码：" + userinfoShareResponse.getMobile());
					logger.info("用户对象为：" + userinfoShareResponse);
					logger.info("用户手机号；= " + userinfoShareResponse.getMobile());
					return new AlipayUserInfo(userinfoShareResponse.getUserId(), userinfoShareResponse.getNickName(),
							userinfoShareResponse.getAvatar());
				} else {
					logger.error("alipay  获取用户信息失败");
				}
			} else {
				logger.error("alipay authCode换取authToken失败");
			}*/
		} catch (Exception e) {
			logger.info("alipay 获取用户信息失败", e);
		}
		return null;
	}

	public String getAlipayPublicKey() {
		return alipayPublicKey;
	}

	public void setAlipayPublicKey(String alipayPublicKey) {
		this.alipayPublicKey = alipayPublicKey;
	}

	public String getSignCharset() {
		return signCharset;
	}

	public void setSignCharset(String signCharset) {
		this.signCharset = signCharset;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getSignType() {
		return signType;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}

	public String getAlipayGateWay() {
		return alipayGateWay;
	}

	public void setAlipayGateWay(String alipayGateWay) {
		this.alipayGateWay = alipayGateWay;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}
	
	
	public static void main(String[] args) throws AlipayApiException {
		AlipayClient alipayClient = AlipayAPIClientFactory
				.getAlipayClient();
		AlipayOpenPublicMessageSingleSendRequest request = new AlipayOpenPublicMessageSingleSendRequest();
		String json ="{\"to_user_id\":\"2088312278245396\",\"template\":{\"template_id\":\"62a773bd975b42e2a304ed4578e62b03\",\"context\":{\"remark\":{\"value\":\"在线办理,请点击详情\",\"color\":\"\"},\"action_name\":\"查看详情\",\"keyword1\":{\"value\":\"交通违法信息通知\",\"color\":\"\"},\"head_color\":\"\",\"first\":{\"value\":\"您绑定的粤B8A17R于2017-08-19 10:53:28在新洲九街-新洲九街因有在允许停放时段以外停放的的违法行为被监控设备记录，请您自违法发生之日起45日内处理完毕。逾期未处理完毕的，自第46日起停驶该机动车。未停驶上路将予以罚款300元，并扣留车辆。\",\"color\":\"\"},\"keyword2\":{\"value\":\"2017年09月18日\",\"color\":\"\"},\"url\":\"http://zhifubao.chudaokeji.com/#/trafficPushNews?digits=7684&numberPlate=%E7%B2%A4B8A17R&plateType=02\"}}}";
		Map<String, Property> map = new HashMap<String, Property>();
		map.put("first", new Property("您的驾驶证状态为“注销可恢复”，为不影响您驾驶证的正常使用，请您尽快办理恢复驾驶证资格业务。", ""));
		map.put("keyword1", new Property("驾驶证注销", ""));
		map.put("keyword2", new Property(DateUtil.format(new Date(), new SimpleDateFormat("yyyy年MM月dd日")), ""));
		map.put("remark", new Property("点击进入“预约类业务”，选择“驾驶证业务”办理恢复驾驶资格业务。", ""));
		
		//高锡刚 正式号
		Template template = new Template("62a773bd975b42e2a304ed4578e62b03", "", "", "", map);
		TemplateDataAlipayModel alipayModel = new TemplateDataAlipayModel("2088312278245396",template);
		//request.setBizContent(GsonBuilderUtil.toJson(alipayModel));
		request.setBizContent(json);
		AlipayOpenPublicMessageSingleSendResponse response = alipayClient.execute(request);
		
		System.out.println(response.getBody());
		System.out.println(response.getCode());
		System.out.println(response.getErrorCode());
		if(response.isSuccess()){
			System.out.println("调用成功");
		} else {
			System.out.println("调用失败");
		}
		
		//Template template = new Template("f90dbfff7a3e4a8287b02a500d89049d", "", "", "", map);
		//TemplateDataAlipayModel alipayModel = new TemplateDataAlipayModel("2088312278245396",template);
		
		//request.setBizContent(json);
		
	}
}
