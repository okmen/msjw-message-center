package cn.message.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.message.bean.WechatPostMessageModel;
import cn.message.bean.WechatUserInfo;
import cn.message.bean.WeiXinOauth2Token;
import cn.message.bean.message.request.IMessage;
import cn.message.bean.message.response.TextResponseMessage;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.service.IWechatService;
import cn.message.utils.OpenIdUtil;
import cn.message.utils.SHA1;
import cn.message.utils.Sign;
import cn.message.utils.WebService4Wechat;
/**
 * 处理微信逻辑
 * @author gxg
 *
 */
@Service("wechatService")
@SuppressWarnings(value="all")
public class IWechatServiceImpl implements IWechatService {
	private Logger logger = Logger.getLogger(IWechatServiceImpl.class);
	
	@Autowired
	private IMessageCachedImpl iMessageCached;
	
	@Override
	public boolean checkServer(String signature, String timestamp, String nonce) {
		String [] array = new String[]{iMessageCached.getToken(),timestamp,nonce};
		Arrays.sort(array);
		String str = "";
		for (int i = 0; i < array.length; i++) {
			str += array[i];
		}
		SHA1 sha1 = new SHA1();
		String sha1Str = sha1.Digest(str,"UTF-8").toLowerCase();
		return signature.equals(sha1Str) ? true : false;
	}

	@Override
	public IMessage processPostMessage(WechatPostMessageModel model) {
		String xml = "";
		IMessage message = null;
		try {
			 String fromUserName = model.getFromUserName();
	         // 开发者微信号
	         String toUserName = model.getToUserName();
	         // 消息类型
	         String msgType = model.getMsgType();
	         //事件类型
	         String event = model.getEvent(); 
	 
	         message = new TextResponseMessage(fromUserName,toUserName,new Date().getTime(),"敬请期待");
	         
	         if(msgType.equals(IMessage.MESSAGE_TYPE_TEXT)){
	        	 message =  new TextResponseMessage(fromUserName,toUserName,new Date().getTime(),"在线客服待开通");
	         }
	         
	         if(msgType.equals(IMessage.MESSAGE_TYPE_EVENT)){
	        	 if("CLICK".equals(event)){
	        		 message =  new TextResponseMessage(fromUserName,toUserName,new Date().getTime(),"敬请期待");
	        	 }
	        	 //用户打开公众号会推送这个包过来 (第一次推送的包)
	        	 if("LOCATION".equals(event)){
	        	 }
	         }
	         xml = message.toXml();
		} catch (Exception e) {
			logger.error("处理微信消息包异常",e);
			return null;
		}
		return message;
	}

	@Override
	public String createMenu() {
		return WebService4Wechat.createMenu(iMessageCached.getAccessToken(),iMessageCached.getAppid(),iMessageCached.getJavaDomain(),iMessageCached.getH5Domain());
	}

	@Override
	public WechatUserInfo callback4OpenId(String code, String state) {
		logger.info("state:"+state);
		String openId ="";
		if (!"authdeny".equals(code)) {
			WeiXinOauth2Token weiXinOauth2Token = OpenIdUtil
					.getOauth2AccessToken(iMessageCached.getAppid(),
							iMessageCached.getAppsecret(), code);
			openId = weiXinOauth2Token.getOpenId();
			//这个accessToken不同于之前的
			String oauthToken = weiXinOauth2Token.getAccessToken();
			try {
				
				Map<String, Object> map = WebService4Wechat.getUserInfo(oauthToken, openId);
				String nickname = null != map.get("nickname") ? map.get("nickname").toString() : "";
				String headimgurl = null != map.get("headimgurl") ? map.get("headimgurl").toString() : "";
				logger.info("---------------openId:"+openId);
				logger.info("---------------nickname:"+nickname);
				logger.info("---------------headimgurl:"+headimgurl);
				
				WechatUserInfo userInfo = new WechatUserInfo();
				userInfo.setOpenId(openId);
				userInfo.setNickName(nickname);
				userInfo.setHeadUrlImg(headimgurl);
				return userInfo;
			} catch (Exception e) {
				logger.error("获取openId异常 ,展示错误页面",e);
				return null;
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> sdkConfig(String url) {
		//生成签名
		Map<String, Object> map = Sign.sign(iMessageCached.getTicket(), url , iMessageCached.getAppid());
		return map;
	}
}
