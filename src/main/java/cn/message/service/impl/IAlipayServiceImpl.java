package cn.message.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.FileItem;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.internal.util.WebUtils;
import com.alipay.api.request.AlipayCommerceDataSendRequest;
import com.alipay.api.request.AlipayOpenPublicMessageCustomSendRequest;
import com.alipay.api.request.AlipayOpenPublicMessageSingleSendRequest;
import com.alipay.api.request.AlipayUserCertdocSyncRequest;
import com.alipay.api.request.AlipayZdatafrontDatatransferedFileuploadRequest;
import com.alipay.api.response.AlipayCommerceDataSendResponse;
import com.alipay.api.response.AlipayOpenPublicMessageCustomSendResponse;
import com.alipay.api.response.AlipayOpenPublicMessageSingleSendResponse;
import com.alipay.api.response.AlipayUserCertdocSyncResponse;
import com.alipay.api.response.AlipayZdatafrontDatatransferedFileuploadResponse;

import cn.message.bean.CardReceive;
import cn.message.dao.IMessageDao;
import cn.message.model.alipay.AlipayPostMessageModel;
import cn.message.model.alipay.AlipayServiceEnvConstants;
import cn.message.model.alipay.AlipayUserInfo;
import cn.message.model.alipay.CardReceiveConstants;
import cn.message.model.alipay.TemplateDataAlipayModel;
import cn.message.model.alipay.TemplateDataAlipayModel.Property;
import cn.message.model.alipay.TemplateDataAlipayModel.Template;
import cn.message.model.alipay.message.IMessage;
import cn.message.service.IAlipayService;
import cn.message.utils.GsonUtil;
import cn.message.utils.alipay.AlipayAPIClientFactory;
import cn.message.utils.alipay.dispatch.MessageDispatch;
import cn.message.utils.alipay.dispatch.executor.AbstractGeneralExecutor;
import cn.sdk.bean.BaseBean;
import cn.sdk.util.DateUtil;
import cn.sdk.util.MsgCode;

@Service("alipayService")
@SuppressWarnings(value = "all")
public class IAlipayServiceImpl implements IAlipayService {

	Logger logger = Logger.getLogger(IAlipayServiceImpl.class);

	@Autowired
	private IMessageDao messageDao;
	
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

	@Override
	public int insertCardReceive(CardReceive cardReceive) {
		int count = 0;
		try {
			count = messageDao.insertCardReceive(cardReceive);
		} catch (Exception e) {
			logger.error("【支付宝卡包】写入领卡记录异常：cardReceive="+cardReceive, e);
			e.printStackTrace();
		}
		return count;
	}

	@Override
	public int queryReceiveCardCount(String certNo, String type) {
		int count = 0;
		try {
			count = messageDao.queryReceiveCardCount(certNo, type);
		} catch (Exception e) {
			logger.error("【支付宝卡包】查询领卡数量异常：certNo="+certNo+",type="+type, e);
			e.printStackTrace();
		}
		return count;
	}

	/*{ "file_type", "trans_picture" },
	{ "type_id", "cif_driving_shenzhen_police_pic" },
	{ "file_description", "深圳交警电子驾照正面" },
	{ "file_name", "certificate.png" },
	{ "file", Convert.FromBase64String(CertificateResult["result"]["dzz"].ToString()) },*/
	/**
	 * 上传驾驶证正面照片
	 * @param base64Img
	 * @return
	 */
	public BaseBean uploadJsCardImg(String base64Img) {
		BaseBean baseBean = new BaseBean();
		try {
			AlipayZdatafrontDatatransferedFileuploadRequest fileuploadRequest = new AlipayZdatafrontDatatransferedFileuploadRequest();
			fileuploadRequest.setFileType("trans_picture");
			fileuploadRequest.setTypeId("cif_driving_shenzhen_police_pic");
			fileuploadRequest.setFileDescription("深圳交警电子驾照正面");
			byte[] content = base64Img.getBytes();
			FileItem file = new FileItem("certificate.png", content);
			fileuploadRequest.setFile(file);
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapipre.alipay.com/gateway.do", AlipayServiceEnvConstants.APP_ID, 
	                AlipayServiceEnvConstants.PRIVATE_KEY, "json", AlipayServiceEnvConstants.CHARSET,AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY, AlipayServiceEnvConstants.SIGN_TYPE);
			//AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			AlipayZdatafrontDatatransferedFileuploadResponse response = alipayClient.execute(fileuploadRequest);
			logger.info("【支付宝卡包】uploadJsCardImg调支付宝接口返回结果：" + JSON.toJSONString(response));
			if(response.isSuccess()){
				baseBean.setCode(MsgCode.success);
				String resultData = response.getResultData();//文件访问地址
				baseBean.setData(resultData);
			}else{
				baseBean.setCode(response.getCode());
				baseBean.setMsg(response.getMsg());
				baseBean.setData(response.getBody());
			}
		} catch (Exception e) {
			logger.error("【支付宝卡包】uploadJsCardImg调支付宝接口异常", e);
			e.printStackTrace();
		}
		return baseBean;
	}

	/*{ "file_type", "trans_picture" },
    { "type_id", "cif_electronic_driving_shenzhen_police_pic" },
    { "file_description", "深圳交警电子行驶证正面" },
    { "file_name", "driving_license.png" },
    { "file", Convert.FromBase64String(carImageData["result"]["dzz"].ToString()) },*/
	/**
	 * 上传行驶证正面照片
	 * @param base64Img
	 * @return
	 */
	public BaseBean uploadXsCardImg(String base64Img) {
		BaseBean baseBean = new BaseBean();
		try {
			AlipayZdatafrontDatatransferedFileuploadRequest fileuploadRequest = new AlipayZdatafrontDatatransferedFileuploadRequest();
			fileuploadRequest.setFileType("trans_picture");
			fileuploadRequest.setTypeId("cif_electronic_driving_shenzhen_police_pic");
			fileuploadRequest.setFileDescription("深圳交警电子行驶证正面");
			byte[] content = base64Img.getBytes();
			FileItem file = new FileItem("driving_license.png", content);
			fileuploadRequest.setFile(file);
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapipre.alipay.com/gateway.do", AlipayServiceEnvConstants.APP_ID, 
					AlipayServiceEnvConstants.PRIVATE_KEY, "json", AlipayServiceEnvConstants.CHARSET,AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY, AlipayServiceEnvConstants.SIGN_TYPE);
			//AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			AlipayZdatafrontDatatransferedFileuploadResponse response = alipayClient.execute(fileuploadRequest);
			logger.info("【支付宝卡包】uploadXsCardImg调支付宝接口返回结果：" + JSON.toJSONString(response));
			if(response.isSuccess()){
				baseBean.setCode(MsgCode.success);
				String resultData = response.getResultData();//文件访问地址
				baseBean.setData(resultData);
			}else{
				baseBean.setCode(response.getCode());
				baseBean.setMsg(response.getMsg());
				baseBean.setData(response.getBody());
			}
		} catch (Exception e) {
			logger.error("【支付宝卡包】uploadXsCardImg调支付宝接口异常", e);
			e.printStackTrace();
		}
		return baseBean;
	}
	
	/**
	 * 发送证件信息到支付宝
	 * @param bizContent
	 * @return
	 */
	public BaseBean sendCardInfo(String bizContent) {
		BaseBean baseBean = new BaseBean();
		logger.info("【支付宝卡包】sendCardInfo请求参数：" + bizContent);
		try {
			/*AlipayCommerceDataSendRequest dataSendRequest = new AlipayCommerceDataSendRequest();
			dataSendRequest.setBizContent(bizContent);//AlipayServiceEnvConstants.ALIPAY_GATEWAY
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapipre.alipay.com/gateway.do", AlipayServiceEnvConstants.APP_ID, 
					AlipayServiceEnvConstants.PRIVATE_KEY, "json", AlipayServiceEnvConstants.CHARSET,AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY, AlipayServiceEnvConstants.SIGN_TYPE);
			//AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			AlipayCommerceDataSendResponse response = alipayClient.execute(dataSendRequest);
			logger.info("【支付宝卡包】sendCardInfo调支付宝接口返回结果："+ JSON.toJSONString(response));
			if(response.isSuccess()){
				baseBean.setCode(MsgCode.success);
				baseBean.setData(response.getBody());
			}else{
				baseBean.setCode(response.getCode());
				baseBean.setMsg(response.getMsg());
				baseBean.setData(response.getBody());
			}*/
			AlipayUserCertdocSyncRequest certdocSyncRequest = new AlipayUserCertdocSyncRequest();
			certdocSyncRequest.setBizContent(bizContent);
			AlipayClient alipayClient = new DefaultAlipayClient("https://openapipre.alipay.com/gateway.do", AlipayServiceEnvConstants.APP_ID, 
					AlipayServiceEnvConstants.PRIVATE_KEY, "json", AlipayServiceEnvConstants.CHARSET,AlipayServiceEnvConstants.ALIPAY_PUBLIC_KEY, AlipayServiceEnvConstants.SIGN_TYPE);
			//AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
			AlipayUserCertdocSyncResponse response = alipayClient.execute(certdocSyncRequest);
			logger.info("【支付宝卡包】sendCardInfo调支付宝接口返回结果："+ JSON.toJSONString(response));
			if(response.isSuccess()){
				baseBean.setCode(MsgCode.success);
				baseBean.setData(response.getBody());
			}else{
				baseBean.setCode(response.getCode());
				baseBean.setMsg(response.getMsg());
				baseBean.setData(response.getBody());
			}
		} catch (Exception e) {
			logger.error("【支付宝卡包】sendCardInfo调用支付宝接口异常，请求参数：" + bizContent, e);
			e.printStackTrace();
		}
		return baseBean;
	}

	/**
	 * 修改卡包状态
	 * @param cardno 身份证
	 * @param cardtype 卡类型
	 * @param uid 支付宝唯一标识
	 * @return
	 */
	public boolean updateCardReceiveType(String cardno, String cardtype, String uid) {
		boolean isSuccess = false;
		
		try {
			int count = 0;
			if("SZ_E_DRIVING_LICENSE".equals(cardtype)){//驾驶证
				count = messageDao.updateCardReceiveType(uid, cardno, CardReceiveConstants.CARD_RECEIVE_TYPE_DRIVER, CardReceiveConstants.CARD_RECEIVE_TYPE_DELETED_DRIVER);
			}else if("SZ_E_VEHICLE_LICENSE".equals(cardtype)){//行驶证
				count = messageDao.updateCardReceiveType(uid, cardno, CardReceiveConstants.CARD_RECEIVE_TYPE_CAR, CardReceiveConstants.CARD_RECEIVE_TYPE_DELETED_CAR);
			}
			
			if(count > 0){
				logger.info("【支付宝卡包】updateCardReceiveType修改卡包状态成功，cardno="+cardno+"，cardtype="+cardtype+"，uid="+uid);
				isSuccess = true;
			}else{
				logger.info("【支付宝卡包】updateCardReceiveType修改卡包状态失败，cardno="+cardno+"，cardtype="+cardtype+"，uid="+uid);
			}
		} catch (Exception e) {
			logger.error("【支付宝卡包】updateCardReceiveType修改卡包状态异常，cardno="+cardno+"，cardtype="+cardtype+"，uid="+uid, e);
			e.printStackTrace();
		}
		return isSuccess;
	}
}
