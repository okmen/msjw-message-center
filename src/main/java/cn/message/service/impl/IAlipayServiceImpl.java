package cn.message.service.impl;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.message.model.alipay.AlipayPostMessageModel;
import cn.message.model.alipay.AlipayServiceEnvConstants;
import cn.message.model.alipay.AlipayUserInfo;
import cn.message.model.alipay.message.IMessage;
import cn.message.service.IAlipayService;
import cn.message.utils.GsonUtil;
import cn.message.utils.alipay.AlipayAPIClientFactory;
import cn.message.utils.alipay.dispatch.MessageDispatch;
import cn.message.utils.alipay.dispatch.executor.AbstractGeneralExecutor;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayOpenPublicMessageCustomSendRequest;
import com.alipay.api.request.AlipaySystemOauthTokenRequest;
import com.alipay.api.request.AlipayUserInfoShareRequest;
import com.alipay.api.response.AlipayOpenPublicMessageCustomSendResponse;
import com.alipay.api.response.AlipaySystemOauthTokenResponse;
import com.alipay.api.response.AlipayUserInfoShareResponse;

@Service("alipayService")
@SuppressWarnings(value = "all")
public class IAlipayServiceImpl implements IAlipayService {

	Logger logger = Logger.getLogger(IAlipayServiceImpl.class);

	@Override
	public void processPostMessage(AlipayPostMessageModel model) {
		try {
			AbstractGeneralExecutor executor = MessageDispatch.dispatch(
					model.getMsgType(), model.getEventType());
			//执行消息处理
			IMessage message = executor.invoke(model);

			// 最终转换出来的json格式消息
			String jsonMessage = GsonUtil.toJson(message);

			// 初始化支付宝sdk客户端
			AlipayClient alipayClient = AlipayAPIClientFactory
					.getAlipayClient();
			// 获取发送消息接口实例
			AlipayOpenPublicMessageCustomSendRequest request = new AlipayOpenPublicMessageCustomSendRequest();
			// 将报文放入content中
			request.setBizContent(jsonMessage);
			// 发送消息
			AlipayOpenPublicMessageCustomSendResponse response = alipayClient
					.execute(request);
			if (null == response || !response.isSuccess()) {
				logger.info("异步发送失败 code=" + response.getCode() + "msg：" + response.getMsg());
			}
		} catch (Exception e) {
			logger.info("支付宝异步发送消息异常", e);
		}
	}

	@Override
	public AlipayUserInfo callback4UserId(String code) {
		try {
			// 3. 利用authCode获得authToken
			AlipaySystemOauthTokenRequest oauthTokenRequest = new AlipaySystemOauthTokenRequest();
			oauthTokenRequest.setCode(code);
			oauthTokenRequest
					.setGrantType(AlipayServiceEnvConstants.GRANT_TYPE);
			AlipayClient alipayClient = AlipayAPIClientFactory
					.getAlipayClient();
			AlipaySystemOauthTokenResponse oauthTokenResponse = alipayClient
					.execute(oauthTokenRequest);
			// 成功获得authToken
			if (null != oauthTokenResponse && oauthTokenResponse.isSuccess()) {
				// 4. 利用authToken获取用户信息
				AlipayUserInfoShareRequest userinfoShareRequest = new AlipayUserInfoShareRequest();
				String accessToken = oauthTokenResponse.getAccessToken();
				AlipayUserInfoShareResponse userinfoShareResponse = alipayClient
						.execute(userinfoShareRequest, accessToken);
				// 成功获得用户信息
				if (null != userinfoShareResponse && userinfoShareResponse.isSuccess()) {
					//获取成功 返回给web
					return new AlipayUserInfo(userinfoShareResponse.getUserId(),userinfoShareResponse.getNickName(),userinfoShareResponse.getAvatar());
				} else {
					logger.error("alipay  获取用户信息失败");
				}
			} else {
				logger.error("alipay authCode换取authToken失败");
			}
		} catch (Exception e) {
			logger.info("alipay 获取用户信息失败",e);
		}
		return null;
	}

}
