package cn.message.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.message.bean.WxMembercard;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.wechat.WechatPostMessageModel;
import cn.message.model.wechat.WechatUserInfo;
import cn.message.model.wechat.WeiXinOauth2Token;
import cn.message.model.wechat.message.IMessage;
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
	
	@Autowired
	private IMessageDao iMessageDao;
	
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
			message = executor.invoke(model,iMessageDao);
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
			return "";
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

	@Override
	public String queryAccessToken() {
		return iMessageCached.getAccessToken();
	}

	@Override
	public String createAccessToken() {
		return iMessageCached.initTokenAndTicket();
	}

	@Override
	public String getJsapiTicket() {
		return iMessageCached.getTicket();
	}

	@Override
	public Map<String, Object> cardConfig(String openId, String cardId) {
		try {
			return Sign.sign4Card(iMessageCached.getApiTicket(), openId, cardId);
		} catch (Exception e) {
			logger.error("获取cardConfig签名算法异常："+"openId="+openId+",cardId="+cardId,e);
		}
		return null;
	}

	@Override
	public boolean activeJsCard(String openId,String cardId,String decryptCode) throws Exception {
		try {
			String code = WebService4Wechat.decryptCode(iMessageCached.getAccessToken(), decryptCode);
			WxMembercard wxMembercard = iMessageDao.selectWxMembercard(openId, cardId);
			if("".equals(code)) {
				logger.error("decryptCode 解密失败,decryptCode="+decryptCode);
				return false;
			}
			//在激活之前 本来会新建一张卡记录的 , 但是由于是异步的 可能在激活的时候 微信还没有推送消息过来  ,这里做一个新增操作
			if(null == wxMembercard){
				 WxMembercard newWxMembercard = new WxMembercard();
				 newWxMembercard.setOpenid(openId);
				 newWxMembercard.setCode(code);
				 newWxMembercard.setCardid(cardId);
				 newWxMembercard.setIsgivebyfriend(0);
				 newWxMembercard.setGiveopenid("0");
				 newWxMembercard.setState(0);
				 newWxMembercard.setOuterstr("default");
				 newWxMembercard.setIntime(new Date());
				 iMessageDao.insertWxMembercard(newWxMembercard);
			}
			
			boolean bool = WebService4Wechat.activateJsCard(iMessageCached.getAccessToken(), cardId, code, "", "", "");
			if(!bool){
				logger.error("activateCard 激活驾驶证失败,openId="+openId + ",cardId="+cardId + ",code="+code);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("activeJsCard 激活驾驶证出现异常,openId="+openId+",cardId="+cardId+",decryptCode="+decryptCode,e);
			return false;
		}
	}

	@Override
	public boolean updateJsCard(String code, String cardId, String ljjf,
			String zjcx, String syrq) {
		return false;
	}

	@Override
	public boolean activeXsCard(String openId, String cardId, String decryptCode)
			throws Exception {
		try {
			String code = WebService4Wechat.decryptCode(iMessageCached.getAccessToken(), decryptCode);
			WxMembercard wxMembercard = iMessageDao.selectWxMembercard(openId, cardId);
			if("".equals(code)) {
				logger.error("decryptCode 解密失败,decryptCode="+decryptCode);
				return false;
			}
			//在激活之前 本来会新建一张卡记录的 , 但是由于是异步的 可能在激活的时候 微信还没有推送消息过来  ,这里做一个新增操作
			if(null == wxMembercard){
				 WxMembercard newWxMembercard = new WxMembercard();
				 newWxMembercard.setOpenid(openId);
				 newWxMembercard.setCode(code);
				 newWxMembercard.setCardid(cardId);
				 newWxMembercard.setIsgivebyfriend(0);
				 newWxMembercard.setGiveopenid("0");
				 newWxMembercard.setState(0);
				 newWxMembercard.setOuterstr("default");
				 newWxMembercard.setIntime(new Date());
				 iMessageDao.insertWxMembercard(newWxMembercard);
			}
			
			boolean bool = WebService4Wechat.activateXsCard(iMessageCached.getAccessToken(), cardId, code);
			if(!bool){
				logger.error("activateCard 激活行驶证失败,openId="+openId + ",cardId="+cardId + ",code="+code);
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("activeXsCard 激活行驶证出现异常,openId="+openId+",cardId="+cardId+",decryptCode="+decryptCode,e);
			return false;
		}
	}

	@Override
	public String getApiTicket() {
		return iMessageCached.getApiTicket();
	}

	@Override
	public void setAuthOpenid(String key) {
		iMessageCached.setAuthOpenid(key);
	}

	@Override
	public String getAuthOpenid(String key) {
		return iMessageCached.getAuthOpenid(key);
	}
}
