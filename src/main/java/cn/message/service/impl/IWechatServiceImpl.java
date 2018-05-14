package cn.message.service.impl;

import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;

import cn.message.bean.WxMembercard;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.wechat.WechatPostMessageModel;
import cn.message.model.wechat.WechatUserInfo;
import cn.message.model.wechat.WeiXinOauth2Token;
import cn.message.model.wechat.message.IMessage;
import cn.message.service.IWechatService;
import cn.message.utils.wechat.HttpRequest;
import cn.message.utils.wechat.MenuFileUtil;
import cn.message.utils.wechat.OpenIdUtil;
import cn.message.utils.wechat.SHA1;
import cn.message.utils.wechat.Sign;
import cn.message.utils.wechat.WebService4Wechat;
import cn.message.utils.wechat.dispatch.MessageDispatch;
import cn.message.utils.wechat.dispatch.executor.AbstractGeneralExecutor;
import cn.sdk.bean.BaseBean;
import cn.sdk.util.GsonBuilderUtil;
import cn.sdk.util.MsgCode;
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
	public String getCardH5Domain() {
		return iMessageCached.getCardH5Domain();
	}

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
	
	/**
	 * 驾驶证激活（一摇惊喜）
	 */
	public BaseBean activateJsCardTest(String openId,String cardId,String decryptCode,String ljjf,String syrq,String zjcx) throws Exception {
		BaseBean baseBean = new BaseBean();
		try {
			String code = WebService4Wechat.decryptCode(iMessageCached.getAccessToken(), decryptCode);
			if("".equals(code)) {
				logger.error("decryptCode 解密失败,decryptCode="+decryptCode);
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("code非法！");
				return baseBean;
			}
			
			WxMembercard wxMembercard = iMessageDao.selectWxMembercard(openId, cardId);
			//有数据，可能是老数据，也可能是领卡事件新增的
			if(null != wxMembercard){
				Integer state = wxMembercard.getState();
				if(state == 1){//已激活过
					baseBean.setCode(MsgCode.businessError);
					baseBean.setMsg("已激活！");
					return baseBean;
				}else{
					//调微信接口激活（一摇惊喜）
					boolean bool = WebService4Wechat.activateJsCardTest(iMessageCached.getAccessToken(), cardId, code, ljjf, syrq, zjcx);
					if(bool){
						//激活行驶证成功，修改state=1,身份证号idno
						WxMembercard updateWxMembercard = new WxMembercard();
						try {
							updateWxMembercard.setState(1);
							String idno = iMessageDao.queryIdCardByOpenId(openId);
							updateWxMembercard.setIdno(idno);
							updateWxMembercard.setOpenid(openId);
							updateWxMembercard.setCardid(cardId);
							int updatecount = iMessageDao.updateWxMembercard(updateWxMembercard);
							logger.info("【微信卡包】激活电子驾驶证并修改卡结果："+updatecount);
						} catch (Exception e) {
							logger.error("【微信卡包】电子驾驶证修改卡信息异常，WxMembercard=" + JSON.toJSONString(updateWxMembercard));
							e.printStackTrace();
						}
					}else{
						logger.info("【微信卡包】activateJsCardTest 激活驾驶失败,openId="+openId + ",cardId="+cardId + ",code="+code);
						baseBean.setCode(MsgCode.businessError);
						baseBean.setMsg("激活失败，请重试！");
						return baseBean;
					}
				}
			}else{
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("系统繁忙，请稍后重试！");
				return baseBean;
			}
			
			baseBean.setCode(MsgCode.success);
			baseBean.setMsg("激活成功！");
			baseBean.setData(code);
			return baseBean;
		} catch (Exception e) {
			logger.error("【微信卡包】activeJsCardTest 激活驾驶证出现异常,openId="+openId+",cardId="+cardId+",decryptCode="+decryptCode,e);
			e.printStackTrace();
			baseBean.setCode(MsgCode.exception);
			baseBean.setMsg(MsgCode.systemMsg);
			return baseBean;
		}
	}
	
	/**
	 * 驾驶证更新（一摇惊喜）
	 */
	public boolean updateJsCardTest(String code, String cardId, String ljjf, String syrq, String zjcx) {
		try {
			return WebService4Wechat.updateJsCardTest(iMessageCached.getAccessToken(), cardId, code, ljjf, syrq, zjcx);
		} catch (Exception e) {
			logger.error("【微信卡包】updateJsCardTest 更新驾驶证出现异常,cardId="+cardId+",code="+code,e);
			return false;
		}
	}
	
	/**
	 * 行驶证激活（一摇惊喜）
	 */
	public BaseBean activateXsCardTest(String openId, String cardId, String decryptCode) throws Exception {
		BaseBean baseBean = new BaseBean();
		try {
			String code = WebService4Wechat.decryptCode(iMessageCached.getAccessToken(), decryptCode);
			if("".equals(code)) {
				logger.error("decryptCode 解密失败,decryptCode="+decryptCode);
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("code非法！");
				return baseBean;
			}
			
			WxMembercard wxMembercard = iMessageDao.selectWxMembercard(openId, cardId);
			//有数据，可能是老数据，也可能是领卡事件新增的
			if(null != wxMembercard){
				Integer state = wxMembercard.getState();
				if(state == 1){//已激活过
					baseBean.setCode(MsgCode.businessError);
					baseBean.setMsg("已激活！");
					return baseBean;
				}else{
					//调微信接口激活（一摇惊喜）
					boolean bool = WebService4Wechat.activateXsCardTest(iMessageCached.getAccessToken(), cardId, code);
					if(bool){
						//激活行驶证成功，修改state=1,身份证号idno
						WxMembercard updateWxMembercard = new WxMembercard();
						try {
							updateWxMembercard.setState(1);
							String idno = iMessageDao.queryIdCardByOpenId(openId);
							updateWxMembercard.setIdno(idno);
							updateWxMembercard.setOpenid(openId);
							updateWxMembercard.setCardid(cardId);
							int updatecount = iMessageDao.updateWxMembercard(updateWxMembercard);
							logger.info("【微信卡包】激活电子行驶证并修改卡结果："+updatecount);
						} catch (Exception e) {
							logger.error("【微信卡包】电子行驶证修改卡信息异常，WxMembercard=" + JSON.toJSONString(updateWxMembercard));
							e.printStackTrace();
						}
					}else{
						logger.info("【微信卡包】activateXsCardTest 激活行驶证失败,openId="+openId + ",cardId="+cardId + ",code="+code);
						baseBean.setCode(MsgCode.businessError);
						baseBean.setMsg("激活失败，请重试！");
						return baseBean;
					}
				}
			}else{
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("系统繁忙，请稍后重试！");
				return baseBean;
				/*//调微信接口激活
				boolean bool = WebService4Wechat.activateXsCard(iMessageCached.getAccessToken(), cardId, code);
				if(bool){
					//在激活之前 本来会新建一张卡记录的 , 但是由于是异步的 可能在激活的时候 微信还没有推送消息过来  ,这里做一个新增已激活记录
					WxMembercard newWxMembercard = new WxMembercard();
					newWxMembercard.setOpenid(openId);
					String idno = iMessageDao.queryIdCardByOpenId(openId);
					newWxMembercard.setIdno(idno);
					newWxMembercard.setCardid(cardId);
					newWxMembercard.setCode(code);
					newWxMembercard.setIsgivebyfriend(0);
					newWxMembercard.setGiveopenid("");
					newWxMembercard.setState(1);//已激活
					newWxMembercard.setOuterid("0");
					newWxMembercard.setOuterstr("default");
					newWxMembercard.setIntime(new Date());
					int count = iMessageDao.insertWxMembercard(newWxMembercard);
					logger.info("【微信卡包】直接新增激活记录结果："+count);
				}else{
					logger.info("【微信卡包】activateXsCardTest 激活行驶证失败,openId="+openId + ",cardId="+cardId + ",code="+code);
					baseBean.setCode(MsgCode.businessError);
					baseBean.setMsg("激活失败，请重试！");
					return baseBean;
				}*/
			}
			
			baseBean.setCode(MsgCode.success);
			baseBean.setMsg("激活成功！");
			baseBean.setData(code);
			return baseBean;
		} catch (Exception e) {
			logger.error("【微信卡包】activateXsCardTest 激活行驶证出现异常,openId="+openId+",cardId="+cardId+",decryptCode="+decryptCode,e);
			e.printStackTrace();
			baseBean.setCode(MsgCode.exception);
			baseBean.setMsg(MsgCode.systemMsg);
			return baseBean;
		}
	}

	/**
	 * 驾驶证激活（深圳交警）
	 */
	public BaseBean activateJsCard(String openId,String cardId,String decryptCode,String ljjf,String syrq,String zjcx) throws Exception {
		BaseBean baseBean = new BaseBean();
		try {
			String code = WebService4Wechat.decryptCode(iMessageCached.getAccessToken(), decryptCode);
			if("".equals(code)) {
				logger.error("decryptCode 解密失败,decryptCode="+decryptCode);
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("code非法！");
				return baseBean;
			}
			
			WxMembercard wxMembercard = iMessageDao.selectWxMembercard(openId, cardId);
			//有数据，可能是老数据，也可能是领卡事件新增的
			if(null != wxMembercard){
				Integer state = wxMembercard.getState();
				if(state == 1){//已激活过
					baseBean.setCode(MsgCode.businessError);
					baseBean.setMsg("已激活！");
					return baseBean;
				}else{
					//调微信接口激活
					boolean bool = WebService4Wechat.activateJsCard(iMessageCached.getAccessToken(), cardId, code, ljjf, syrq, zjcx);
					if(bool){
						//激活行驶证成功，修改state=1,身份证号idno
						WxMembercard updateWxMembercard = new WxMembercard();
						try {
							updateWxMembercard.setState(1);
							String idno = iMessageDao.queryIdCardByOpenId(openId);
							updateWxMembercard.setIdno(idno);
							updateWxMembercard.setOpenid(openId);
							updateWxMembercard.setCardid(cardId);
							int updatecount = iMessageDao.updateWxMembercard(updateWxMembercard);
							logger.info("【微信卡包】激活电子驾驶证并修改卡结果："+updatecount);
						} catch (Exception e) {
							logger.error("【微信卡包】电子驾驶证修改卡信息异常，WxMembercard=" + JSON.toJSONString(updateWxMembercard));
							e.printStackTrace();
						}
					}else{
						logger.info("【微信卡包】activateJsCard 激活驾驶失败,openId="+openId + ",cardId="+cardId + ",code="+code);
						baseBean.setCode(MsgCode.businessError);
						baseBean.setMsg("激活失败，请重试！");
						return baseBean;
					}
				}
			}else{
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("系统繁忙，请稍后重试！");
				return baseBean;
			}
			
			baseBean.setCode(MsgCode.success);
			baseBean.setMsg("激活成功！");
			baseBean.setData(code);
			return baseBean;
		} catch (Exception e) {
			logger.error("【微信卡包】activateJsCard 激活驾驶证出现异常,openId="+openId+",cardId="+cardId+",decryptCode="+decryptCode,e);
			e.printStackTrace();
			baseBean.setCode(MsgCode.exception);
			baseBean.setMsg(MsgCode.systemMsg);
			return baseBean;
		}
	}

	/**
	 * 驾驶证更新（深圳交警）
	 */
	public boolean updateJsCard(String code, String cardId, String ljjf, String syrq, String zjcx) {
		try {
			return WebService4Wechat.updateJsCard(iMessageCached.getAccessToken(), cardId, code, ljjf, syrq, zjcx);
		} catch (Exception e) {
			logger.error("【微信卡包】updateJsCard 更新驾驶证出现异常,cardId="+cardId+",code="+code,e);
			return false;
		}
	}

	/**
	 * 行驶证激活（深圳交警）
	 */
	public BaseBean activateXsCard(String openId, String cardId, String decryptCode) throws Exception {
		BaseBean baseBean = new BaseBean();
		try {
			String code = WebService4Wechat.decryptCode(iMessageCached.getAccessToken(), decryptCode);
			if("".equals(code)) {
				logger.error("decryptCode 解密失败,decryptCode="+decryptCode);
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("code非法！");
				return baseBean;
			}
			
			WxMembercard wxMembercard = iMessageDao.selectWxMembercard(openId, cardId);
			//有数据，可能是老数据，也可能是领卡事件新增的
			if(null != wxMembercard){
				Integer state = wxMembercard.getState();
				if(state == 1){//已激活过
					baseBean.setCode(MsgCode.businessError);
					baseBean.setMsg("已激活！");
					return baseBean;
				}else{
					//调微信接口激活
					boolean bool = WebService4Wechat.activateXsCard(iMessageCached.getAccessToken(), cardId, code);
					if(bool){
						//激活行驶证成功，修改state=1,身份证号idno
						WxMembercard updateWxMembercard = new WxMembercard();
						try {
							updateWxMembercard.setState(1);
							String idno = iMessageDao.queryIdCardByOpenId(openId);
							updateWxMembercard.setIdno(idno);
							updateWxMembercard.setOpenid(openId);
							updateWxMembercard.setCardid(cardId);
							int updatecount = iMessageDao.updateWxMembercard(updateWxMembercard);
							logger.info("【微信卡包】激活电子行驶证并修改卡结果："+updatecount);
						} catch (Exception e) {
							logger.error("【微信卡包】电子行驶证修改卡信息异常，WxMembercard=" + JSON.toJSONString(updateWxMembercard));
							e.printStackTrace();
						}
					}else{
						logger.info("【微信卡包】activateXsCard 激活行驶证失败,openId="+openId + ",cardId="+cardId + ",code="+code);
						baseBean.setCode(MsgCode.businessError);
						baseBean.setMsg("激活失败，请重试！");
						return baseBean;
					}
				}
			}else{
				baseBean.setCode(MsgCode.businessError);
				baseBean.setMsg("系统繁忙，请稍后重试！");
				return baseBean;
				/*//调微信接口激活
				boolean bool = WebService4Wechat.activateXsCard(iMessageCached.getAccessToken(), cardId, code);
				if(bool){
					//在激活之前 本来会新建一张卡记录的 , 但是由于是异步的 可能在激活的时候 微信还没有推送消息过来  ,这里做一个新增已激活记录
					WxMembercard newWxMembercard = new WxMembercard();
					newWxMembercard.setOpenid(openId);
					String idno = iMessageDao.queryIdCardByOpenId(openId);
					newWxMembercard.setIdno(idno);
					newWxMembercard.setCardid(cardId);
					newWxMembercard.setCode(code);
					newWxMembercard.setIsgivebyfriend(0);
					newWxMembercard.setGiveopenid("");
					newWxMembercard.setState(1);//已激活
					newWxMembercard.setOuterid("0");
					newWxMembercard.setOuterstr("default");
					newWxMembercard.setIntime(new Date());
					int count = iMessageDao.insertWxMembercard(newWxMembercard);
					logger.info("【微信卡包】直接新增激活记录结果："+count);
				}else{
					logger.info("【微信卡包】activateXsCard 激活行驶证失败,openId="+openId + ",cardId="+cardId + ",code="+code);
					baseBean.setCode(MsgCode.businessError);
					baseBean.setMsg("激活失败，请重试！");
					return baseBean;
				}*/
			}
			
			baseBean.setCode(MsgCode.success);
			baseBean.setMsg("激活成功！");
			baseBean.setData(code);
			return baseBean;
		} catch (Exception e) {
			logger.error("【微信卡包】activateXsCard 激活行驶证出现异常,openId="+openId+",cardId="+cardId+",decryptCode="+decryptCode,e);
			e.printStackTrace();
			baseBean.setCode(MsgCode.exception);
			baseBean.setMsg(MsgCode.systemMsg);
			return baseBean;
		}
	}

	@Override
	public String queryIdCardByOpenId(String openId) {
		String idCard = null;
		try {
			idCard = iMessageDao.queryIdCardByOpenId(openId);
		} catch (Exception e) {
			logger.error("【微信卡包】queryIdCardByOpenId异常：openId="+openId);
			e.printStackTrace();
		}
		return idCard;
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
