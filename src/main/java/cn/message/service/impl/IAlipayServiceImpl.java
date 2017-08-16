package cn.message.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import cn.message.model.alipay.message.IMessage;
import cn.message.service.IAlipayService;
import cn.message.utils.GsonUtil;
import cn.message.utils.alipay.AlipayAPIClientFactory;
import cn.message.utils.alipay.dispatch.MessageDispatch;
import cn.message.utils.alipay.dispatch.executor.AbstractGeneralExecutor;
import cn.message.utils.wechat.HttpRequest;
import cn.sdk.util.DateUtil;
import cn.sdk.util.HttpUtils;

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

	public String getAppAuthToken(String appAuthCode) throws AlipayApiException {
		callback4UserId(appAuthCode);
		alipayGateWay = "https://openapi.alipay.com/gateway.do";
		appId = "2016082201786470";
		privateKey = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDHaKUHE1t/gbOlvfvh/lajinXLMJXF16MlhcRtPYU1xDXspTN4dieEjABKNuGxYc72Jiu6MVdG0Jo3j6/bsQsekeRtTjwDpyzxwEnGMs+hbhxdJ4l7RpRb9f3WzGhlSQzVjIPsq0+RgWpLV1+hJ/CCUYbmuXjmbp51y5prJswv8drqKHi5Vp97Vsdm//Tx1bHcQEGCv4UhNVdDVgGYCRmGSHwNbpR+NXp1BS6SLIjjUpIKOJfgrBFKnAtPm4f7hnf+ch0DHw7SJVb4z/W1fEmy2VJuAgigNdEzhSaOC/2mHj3zO6Qu/cMkp6C3ZB4QdO5jjhixctka7fh9TBoy9+OzAgMBAAECggEBAJrTAO7RdMU3KwiQzbTWCObJZlPw0RjfKGjFx5EZpwfr5AJs8d2hv2UGXmNA4VMcIdi0IldaX+03ocVp5pyohX2iwLytdaNQdXDuX1lnshYgrB0XyYLRdfdAs2nHI3cglLppczSafhj3ZlRA7prtq6tDKX6SmvAvCxFil+6CHR9LRgzKMMHtHrkGCqxCnVzTDLadoDVdV66oLolJN9/fzDxa8B++uEItCYps/O6rR8yWA0eZj9/74XUhIimrtjCGQAh0BMykOjOIupFVHX3bQURBGmcOdGZeHFhCQtts7CQhf0oXh1+oXVgwtbdaSUpqQfkDexbC72tGhL4Wln8PQeECgYEA61dBOqJFgAzM+Sd2yUnrJQGL5ZIRuw56Hzae3DvNhzbc1eVpLmw8DaAwSZS4IjoaNwO5iBRaqu1ia/t30mUWrC9geCQs1yiXaucr7Fe8DcCupZr2vpz6FM0DC2G/ZFig/JGStX4mfYkIIafCdeiPfvxmnSpMq4mGS5eBpnfMsycCgYEA2OnmJeZ1Hd8W0UZ+CJ9cgX7yTq7yBndFxKFGBZ5xZzk6SC7UMeyEQ3BHQ3mUZ/1pIycIbqK+GoLN1zg3wPTlwuulIMW0iuWw3ggvGcrw60U/Y8CqmFT3GUgl11Nr+YLe3FcFJ3xB9yQOB2wfTySZH0rKusRbVc4mc+cfVUE6MpUCgYB3p4hxsFmXzmpU75y0oRGeYhfn5AmBys2KmzHQFdPcwKctVZPR15P89Yo3jxbDyDjV6d3l8ztaFpHpBxUX8u/BIeFxlCr8RhYec2TgqIbjkyj46D42EggQlV/L5Knz8h//6l/mSTp2rzpkKjBz1IdI8DmYBQ6wVJ7kRibUJar9FwKBgQDB1nshYUlFcWeNikX0ZJyg6BWWdMW5O3qwVUIioU1L5hsMCDT9jBNBxKMaTBpvt0ft7uKdI3dob32MlyNTnkV4I2ZD3AkTmmCseXnFvdH/HLmh5blNOZg7Ensmadjydp27H1RuzZ6RQLn8rgVojxb6nLZ/ohrQmdFI/7/DSu+75QKBgQCua4XB5i3kZ0oz9/ykFxlNZl/Cbm+aqaIlQ8Mrd7eKlEDP8HmQ8OhuJL14jpIFt1h/vgmlVwfIQY6B7qED8sNAETNoVNDDoIZwVJt1QuEoOxN1vxRfEg0W7Tn/p0jOPfsG/0SgXYtyAcbBkasuFV5GSAqbjJIjckGtm3a+PBIqKQ==";
		charset = "utf-8";
		alipayPublicKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1enDJFrhV3BXiXli2MKBRc3GPlag17iKdnZ7uEr4sCUUAh4oz1dnU3ukSTrl98YCB7BNaOarDMwwtph226BquIzuVuL9bIGkwarrZddR+rlC6/sbrIBiYzDqFvXnqPddV04QtgMyBNsjPSneDn0bxamjUI80vZImYITznzJGNQNdCXQCGm9N8aBX/S1mG7ycPogHr/HTszvIB144My/SwbHubMqIczn9BsZEfvoAuVH4P+vddjcPIeow4FgOQSBHRfRH9LYfvxiE1aTDr7WRzpAJL2n4VJoXf30amd956F9fnCWlNXJ3qKbQWUomLP7GRqqRgHjG4oUEyqEKwJqaHwIDAQAB";
		signType = "RSA2";
		AlipayClient alipayClient = new DefaultAlipayClient(alipayGateWay, appId, privateKey, "json", charset,
				alipayPublicKey, signType);
		AlipaySystemOauthTokenRequest request1 = new AlipaySystemOauthTokenRequest();// 创建API对应的request类

		request1.setGrantType(grantType);
		request1.setCode(appAuthCode);
		AlipaySystemOauthTokenResponse response1 = null;
		try {
			response1 = alipayClient.execute(request1);// 通过alipayClient调用API，获得对应的response类
		} catch (Exception e) {
			logger.error(e);
		}
		System.out.print(response1);
		// 根据response中的结果继续业务逻辑处理
		String access_token = response1.getAccessToken();
		String alipayUserId = response1.getAlipayUserId();
		String refreshToken = response1.getRefreshToken();

		AlipayUserUserinfoShareRequest request = new AlipayUserUserinfoShareRequest();// 创建API对应的request类
		AlipayUserUserinfoShareResponse response = alipayClient.execute(request, access_token);// 在请求方法中传入上一步获得的access_token
		System.out.print(response);
		// TODO 根据response中的结果继续业务逻辑处理
		return null;

		/*
		 * AlipayClient alipayClient = new DefaultAlipayClient(alipayGateWay,
		 * appId, privateKey, "json", charset,alipayPublicKey, signType);
		 * AlipayOpenAuthTokenAppRequest requestLogin1 = new
		 * AlipayOpenAuthTokenAppRequest(); requestLogin1.setBizContent("{" +
		 * "\"grant_type\":\"authorization_code\"," + "\"code\":\"" +
		 * appAuthCode + "\"" + "}"); AlipayOpenAuthTokenAppResponse
		 * alipayOpenAuthTokenAppResponse = alipayClient.execute(requestLogin1);
		 * 
		 * if (alipayOpenAuthTokenAppResponse.isSuccess()) {
		 * System.out.println("调用成功"); } else { System.out.println("调用失败"); }
		 * String appAuthToken =
		 * alipayOpenAuthTokenAppResponse.getAppAuthToken();
		 * 
		 * AlipayClient alipayClient11 = new DefaultAlipayClient(alipayGateWay,
		 * appId, privateKey, "json", charset,alipayPublicKey, signType);
		 * AlipayUserUserinfoShareRequest request = new
		 * AlipayUserUserinfoShareRequest(); requestLogin1.setBizContent("{" +
		 * "\"auth_token\":\"6e36a61321074f0daefebaeaf1beTX13\",");
		 * 
		 * AlipayUserUserinfoShareResponse response =
		 * alipayClient11.execute(request); if(response.isSuccess()){
		 * System.out.println("调用成功"); } else { System.out.println("调用失败"); }
		 * 
		 * 
		 * return appAuthToken;
		 */

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
			parameters.put("charset", "UTF-8");
			parameters.put("sign_type", "RSA2");
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
			param.put("charset", "UTF-8");
			param.put("version", "1.0");
			param.put("sign_type", "RSA2");
			param.put("sign", AlipaySignature.rsaSign(param, AlipayServiceEnvConstants.PRIVATE_KEY, "UTF-8"));
			content2 = WebUtils.doPost(AlipayServiceEnvConstants.ALIPAY_GATEWAY, param, 100000, 100000);
			logger.info("alipay.user.userinfo.share：" + content2);
			
			
			Map<String, String> param11 = new LinkedHashMap<String, String>();
			param11.put("method", "alipay.user.info.share");
			param11.put("timestamp", dateFormat.format(new Date()));
			param11.put("app_id", AlipayServiceEnvConstants.APP_ID);
			param11.put("auth_token", accessToken);
			param11.put("charset", "UTF-8");
			param11.put("version", "1.0");
			param11.put("sign_type", "RSA2");
			param11.put("sign", AlipaySignature.rsaSign(param11, AlipayServiceEnvConstants.PRIVATE_KEY, "UTF-8"));
			content3 = WebUtils.doPost(AlipayServiceEnvConstants.ALIPAY_GATEWAY, param, 100000, 100000);
			logger.info("alipay.user.info.share：" + content3);
			
			
			AlipayClient alipayClient = AlipayAPIClientFactory.getAlipayClient();
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
			}
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

	public static void main(String[] args) {
		String xx = "{\"alipay_system_oauth_token_response\": {\"access_token\": \"authusrB0bed4977307f43ce84ba1bcdd6c45X13\", \"alipay_user_id\": \"20880057743797774564222911313713\", \"expires_in\": 600, \"re_expires_in\": 2592000, \"refresh_token\": \"authusrBa05faa911c61490cab22280a6a9feX13\", \"user_id\": \"2088012107137130\"}, \"sign\": \"wm2tMto94t5VSXAU/WEburuaWUOpRSpdQVlioNTOq7diWKp34E4M0TrxnAgOnIJaoFbkPcXpe0d/JuGenUOXz+Pxnez78B9kHjsgcm0bg2usOOiXNd7Ka6I78uDG9WEe+yUM4u0Jn/b5ARBaovpnAYy1LZL8hdJ62rbljCy7mGM=\"}";

		JSONObject jsonObject = JSON.parseObject(xx);
		String xddd = jsonObject.getString("alipay_system_oauth_token_response");
		jsonObject = JSON.parseObject(xddd);
		String access_token = jsonObject.getString("access_token");
		System.out.println(access_token);
	}
}
