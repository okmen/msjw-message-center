package cn.message.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.model.wechat.WechatPostMessageModel;
import cn.message.model.wechat.WechatUserInfo;
import cn.message.model.wechat.WeiXinOauth2Token;
import cn.message.model.wechat.message.IMessage;
import cn.message.model.wechat.message.response.NewsMessage;
import cn.message.model.wechat.message.response.TextMessage;
import cn.message.service.IWechatService;
import cn.message.utils.wechat.MenuFileUtil;
import cn.message.utils.wechat.OpenIdUtil;
import cn.message.utils.wechat.SHA1;
import cn.message.utils.wechat.Sign;
import cn.message.utils.wechat.WebService4Wechat;
import cn.message.utils.wechat.dispatch.MessageDispatch;
import cn.message.utils.wechat.dispatch.executor.AbstractGeneralExecutor;
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
			//选择消息类型执行器
			AbstractGeneralExecutor executor = MessageDispatch.dispatch(model.getMsgType());
			
			//无法识别的消息类型? 不处理 直接返回null 抛给web 返回给微信
			if(null == executor) return null;
			
			//具体执行
			message = executor.invoke(model);
		} catch (Exception e) {
			logger.error("处理微信消息包异常:"+model.toString(),e);
			message =  null;
		}
		return message;
	}

	@Override
	public String createMenu() {
		String json = "";
		try {
			String filePath = StringUtils.class.getClassLoader().getResource("/").getPath()+ iMessageCached.getMenuFile();  
			json = MenuFileUtil.readFileByChars(filePath);
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
