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
import cn.message.bean.message.IMessage;
import cn.message.bean.message.response.NewsResponseMessage;
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
	         //文本
	         String content = model.getContent();
	 
	         if(msgType.equals(IMessage.MESSAGE_TYPE_TEXT)){
	        	 message =  new TextResponseMessage(fromUserName,toUserName,new Date().getTime(),"未知关键字");
	        	 
	        	 if(IMessage.KEYWORD_SZ.equals(content.trim())){
	        		 logger.info(IMessage.KEYWORD_SZ.equals(content.trim()));
	        		 message = new NewsResponseMessage(fromUserName, toUserName, new Date().getTime(), 
	        				 1, new String[]{"材料一 ▍ 深圳，改革开放的前沿"}, new String[]{""},
	        				 new String[]{"https://wallet.chudaokeji.com/szjj/assets/images/conference/1.jpg"}, 
	        				 	new String[]{"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213953035&idx=1&sn=e6338f3fb27a00fb3b3c4446d16fa92e&scene=1&srcid=0909O5806wQ56HEHfGYkDxIs&from=singlemessage&isappinstalled=0#rd"});
	        	 }
	        	 
	        	 if(IMessage.KEYWORD_SZJJ.equals(content.trim())){
	        		 message = new NewsResponseMessage(fromUserName, toUserName, new Date().getTime(), 
	        				 1, new String[]{"材料二 ▍ 深圳交警：2200人队伍、6500公里道路、320万辆车"}, new String[]{""},
	        				 new String[]{"https://wallet.chudaokeji.com/szjj/assets/images/conference/2.jpg"}, 
	        				 	new String[]{"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213959266&idx=1&sn=5dd757eb432a12afe782a64a6cd57307&scene=1&srcid=0909nDN7yYsMkV8iNeOkDZdp&from=singlemessage&isappinstalled=0#rd"});
	        	 }
	         }
	         
	         if(msgType.equals(IMessage.MESSAGE_TYPE_EVENT)){
	        	 if("CLICK".equals(event)){
	        		 message =  new TextResponseMessage(fromUserName,toUserName,new Date().getTime(),"事件消息");
	        	 }
	        	 //用户打开公众号会推送这个包过来 (第一次推送的包)
	        	 if("LOCATION".equals(event)){
	        	 }
	         }
		} catch (Exception e) {
			logger.error("处理微信消息包异常:"+model.toString(),e);
			return null;
		}
		return message;
	}

	@Override
	public String createMenu(String json) {
		try {
			return WebService4Wechat.createMenu(iMessageCached.getAccessToken(),json);
		} catch (Exception e) {
			logger.error("创建菜单异常:"+json);
		}
		return "";
	}

	@Override
	public WechatUserInfo callback4OpenId(String code, String state) {
		if (!"authdeny".equals(code)) {
			try {
				WeiXinOauth2Token weiXinOauth2Token = OpenIdUtil
						.getOauth2AccessToken(iMessageCached.getAppid(),
								iMessageCached.getAppsecret(), code);
				String openId = weiXinOauth2Token.getOpenId();
				//这个accessToken不同于之前的
				String oauthToken = weiXinOauth2Token.getAccessToken();
				Map<String, Object> map = WebService4Wechat.getUserInfo(oauthToken, openId);
				String nickname = null != map.get("nickname") ? map.get("nickname").toString() : "";
				String headimgurl = null != map.get("headimgurl") ? map.get("headimgurl").toString() : "";
				WechatUserInfo userInfo = new WechatUserInfo();
				userInfo.setOpenId(openId);
				userInfo.setNickName(nickname);
				userInfo.setHeadUrlImg(headimgurl);
				return userInfo;
			} catch (Exception e) {
				logger.error("callback获取openId以及用户信息异常 :"+"code="+code+",state="+state,e);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> sdkConfig(String url) {
		try {
			//生成签名
			Map<String, Object> map = Sign.sign(iMessageCached.getTicket(), url , iMessageCached.getAppid());
			return map;
		} catch (Exception e) {
			logger.error("获取sdk签名算法异常："+"url="+url,e);
		}
		return null;
	}

	@Override
	public String queryMenu() {
		try {
			String json = WebService4Wechat.queryMenu(iMessageCached.getAccessToken());
			return json;
		} catch (Exception e) {
			logger.error("查询菜单异常",e);
		}
		return "";
	}
}
