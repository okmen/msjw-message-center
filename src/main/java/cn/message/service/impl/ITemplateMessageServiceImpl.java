package cn.message.service.impl;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.message.bean.HmdahsJ1;
import cn.message.bean.LoginLog;
import cn.message.bean.SendTemplateRecord;
import cn.message.bean.UserBind;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.MsgChannelResultModel;
import cn.message.model.TemplateMessageModel;
import cn.message.model.alipay.TemplateDataAlipayModel;
import cn.message.model.wechat.MessageChannelModel;
import cn.message.model.wechat.TemplateDataModel;
import cn.message.model.wechat.TemplateDataModel.Property;
import cn.message.service.ITemplateMessageService;
import cn.message.utils.GsonUtil;
import cn.message.utils.hmdahs.AlipayHmdahsJ1;
import cn.message.utils.hmdahs.BaseHmdahsJ1;
import cn.message.utils.hmdahs.WechatHmdahsJ1;
import cn.message.utils.wechat.WebService4Wechat;
import cn.sdk.bean.BaseBean;
import cn.sdk.util.GsonBuilderUtil;
import cn.sdk.util.StringUtil;

import com.alibaba.fastjson.JSON;
import com.alipay.api.response.AlipayOpenPublicMessageSingleSendResponse;
/**
 * 消息中心
 * @author gxg
 *
 */
@Service("templateMessageService")
@SuppressWarnings(value="all")
public class ITemplateMessageServiceImpl implements ITemplateMessageService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private IMessageDao messageDao;

	@Autowired
	private IMessageCachedImpl iMessageCached;

	@Override
	public boolean sendMessage(String openId,String templateId,String url,Map<String, Property> map) {
		try {
			TemplateDataModel model = new TemplateDataModel();
			model.setTouser(openId);
			model.setTemplate_id(templateId);
			model.setUrl(url);
			model.setData(map);
			String json = WebService4Wechat.sendTemplateMessage(iMessageCached.getAccessToken(),model);
			
			logger.info("模板消息发送结果:"+json);
			TemplateMessageModel result = GsonUtil.fromJson(json, TemplateMessageModel.class);
			if(null != result){ 
				int errcode = result.getErrcode();
				if(0 == errcode){
					return true;
				}
				
				//token失效返回值 重新获取token
				if(40001 == errcode){
					 logger.info("返回值40001,重新获取token并重发该条记录");
					 String newToken = iMessageCached.initTokenAndTicket();
					 json = WebService4Wechat.sendTemplateMessage(newToken,model);
					 result = GsonUtil.fromJson(json, TemplateMessageModel.class);
					 if(null != result){ 
						errcode = result.getErrcode();
						if(0 == errcode){
							return true;
						}
					 }
					 return false;
				}
			}
		} catch (Exception e) {
			logger.error("发送模板消息异常:"+"openId="+openId+",templateId="+url+"map="+map,e);
		}
		return false;
	}
	
	/**
	 * 发送服务通知：未关注“深圳交警”公众号发送服务通知，已关注则发送模板消息
	 * @param messageChannelModel 消息通路请求参数
	 */
	public BaseBean sendServiceMessage(MessageChannelModel model) {
		BaseBean refBean = new BaseBean();
		try {
			String json = WebService4Wechat.sendServiceMessage(iMessageCached.getAccessToken(), model);
			logger.info("消息通路发送结果:"+json);
			
			MsgChannelResultModel result = GsonUtil.fromJson(json, MsgChannelResultModel.class);
			if(null != result){ 
				int errcode = result.getErrcode();
				if(0 == errcode){
					refBean.setCode(result.getErrcode()+"");
					refBean.setMsg(result.getErrmsg());
					refBean.setData(result.getResult_page_url());
					return refBean;
				}
				
				//token失效返回值 重新获取token
				if(40001 == errcode){
					logger.info("返回值40001,重新获取token并重发该条记录");
					String newToken = iMessageCached.initTokenAndTicket();
					json = WebService4Wechat.sendServiceMessage(newToken, model);
					logger.info("重新获取token后发送结果："+json);
					
					result = GsonUtil.fromJson(json, MsgChannelResultModel.class);
					if(null != result){ 
						errcode = result.getErrcode();
						if(0 == errcode){
							refBean.setCode(result.getErrcode()+"");
							refBean.setMsg(result.getErrmsg());
							refBean.setData(result.getResult_page_url());
							return refBean;
						}
					}
				}
				
				//发送失败
				refBean.setCode(result.getErrcode()+"");
				refBean.setMsg(result.getErrmsg());
				
			}
		} catch (Exception e) {
			logger.error("发送消息通路异常:"+JSON.toJSONString(model), e);
		}
		return refBean;
	}
	
	/**
	 * 获取域名地址
	 * @return
	 */
	public String getTemplateSendUrl() {
		String url = iMessageCached.getTemplateSendUrl();
		logger.info("获取到的域名地址是：" + url);
		return url;
	}

	@Override
	public void gainHmdahsData() throws Exception {
		List<HmdahsJ1> list = new ArrayList<HmdahsJ1>();
		
		BaseHmdahsJ1 templateHmdahsJ1 = new BaseHmdahsJ1(messageDao, iMessageCached);
		templateHmdahsJ1.getHmdahsJ1(0);
	}
	
	@Override
	public int sendMessageWechat4Timer() throws Exception {
		//执行总数
		int sum = 0;
		//成功总数
		int sendCount = 0;
		try {
			WechatHmdahsJ1 templateHmdahsJ1 = new WechatHmdahsJ1(messageDao, iMessageCached);
			List<HmdahsJ1> list = messageDao.queryHmdahsJ14Wechat();
			if(null == list || list.size() == 0) {
				logger.info("queryHmdahsJ14Wechat 无待发送数据");
				return 0;
			}
				
			//获取到需要发送模板消息的用户对象
			Set<UserBind> userBindSet = templateHmdahsJ1.getAllUserBinds(list);
			Map<String, List<UserBind>> userBindMap = templateHmdahsJ1.group(userBindSet);
				
			//执行总数
			sum = list.size();
			//真正发送模板消息
			for (int i = 0; i < list.size(); i++) {
				HmdahsJ1 hmdahsJ1 = list.get(i);
				String xh = hmdahsJ1.getXh();
				String pch = hmdahsJ1.getPch(); 
				String message = hmdahsJ1.getMessage();
				String hslx = hmdahsJ1.getHslx();
				String hphm = hmdahsJ1.getHphm();
				String lxdh = hmdahsJ1.getLxdh();
				String jszhm = hmdahsJ1.getJszhm();
					
				List<UserBind> userBinds = userBindMap.get(lxdh);
				if(null == userBinds){
					//已发送,手机号码无法在用户表中匹配openId
					int hmdahsJ1sCount = messageDao.updateHmdahsJ1State4Wechat(hmdahsJ1.getId(), WechatHmdahsJ1.STATE_1);
					continue;
				} 
					
				for (UserBind userBind : userBinds) {
					String mobile = userBind.getMobileNumber();
					String openId = userBind.getOpenId();
					//组装模板消息数据对象
					TemplateDataModel model = templateHmdahsJ1.getTemplateModel(openId, message,hslx,mobile,hphm);	
					if(null == model) {
						//已发送,精准推送 message 内容无法匹配模板规则
						int hmdahsJ1sCount = messageDao.updateHmdahsJ1State4Wechat(hmdahsJ1.getId(), WechatHmdahsJ1.STATE_2);
						continue;
					}
						
					//设置为发送完成
					messageDao.updateHmdahsJ1State4Wechat(hmdahsJ1.getId(), WechatHmdahsJ1.STATE_3);
					sendCount ++;
						
					String request = GsonBuilderUtil.toJson(model);
					String response = templateHmdahsJ1.sendMessage4Hmdahs(model);
					String state = "";
					if(StringUtil.isNotBlank(response)){
						TemplateMessageModel result = GsonBuilderUtil.fromJson(response, TemplateMessageModel.class);
						state = result.getErrcode()+"";
					}
					int sendTemplateRecordsCount = messageDao.insertSendTemplateRecord(new SendTemplateRecord(openId,jszhm,lxdh,xh,pch,response,request,new Date(),state,WechatHmdahsJ1.TYPE));
				}
			}
		} catch (Exception e) {
			logger.error("定时发送模板消息出现异常",e);
			throw e;
		}finally{
			logger.info("本次共处理微信数据："+sum);
			return sendCount;
		}
	}

	@Override
	public int sendMessageAlipay4Timer() throws Exception {
		// TODO Auto-generated method stub
		//执行总数
		int sum = 0;
		//成功总数
		int sendCount = 0;
		try {
			AlipayHmdahsJ1 alipayHmdahsJ1 = new AlipayHmdahsJ1(messageDao, iMessageCached);
			List<HmdahsJ1> list = messageDao.queryHmdahsJ14Alipay();
			if(null == list || list.size() == 0) {
				logger.info("queryHmdahsJ14Alipay 无待发送数据");
				return 0;
			}
				
			//获取到需要发送模板消息的用户对象
			Set<LoginLog> loginLogSet = alipayHmdahsJ1.getAllLoginLogs(list);
			Map<String, List<LoginLog>> loginLogMap = alipayHmdahsJ1.group(loginLogSet);
				
			//执行总数
			sum = list.size();
			//真正发送模板消息
			for (int i = 0; i < list.size(); i++) {
				HmdahsJ1 hmdahsJ1 = list.get(i);
				String xh = hmdahsJ1.getXh();
				String pch = hmdahsJ1.getPch(); 
				String message = hmdahsJ1.getMessage();
				String hslx = hmdahsJ1.getHslx();
				String hphm = hmdahsJ1.getHphm();
				String lxdh = hmdahsJ1.getLxdh();
				String jszhm = hmdahsJ1.getJszhm();
					
				List<LoginLog> loginLogs = loginLogMap.get(lxdh);
				if(null == loginLogs){
					//设置为已发送
					int hmdahsJ1sCount = messageDao.updateHmdahsJ1State4Alipay(hmdahsJ1.getId(), AlipayHmdahsJ1.STATE_1);
					continue;
				} 
					
				for (LoginLog loginLog : loginLogs) {
					String mobile = loginLog.getPhone();
					String uid = loginLog.getUid();
					
					if(StringUtil.isBlank(uid)){
						//已发送,uid 为空
						int hmdahsJ1sCount = messageDao.updateHmdahsJ1State4Alipay(hmdahsJ1.getId(), AlipayHmdahsJ1.STATE_4);
						continue;
					}
					
					//组装模板消息数据对象
					TemplateDataAlipayModel model = alipayHmdahsJ1.getTemplateModel(uid, message,hslx,mobile,hphm);	
					if(null == model) {
						//已发送,精准推送 message 内容无法匹配模板规则
						int hmdahsJ1sCount = messageDao.updateHmdahsJ1State4Alipay(hmdahsJ1.getId(), AlipayHmdahsJ1.STATE_2);
						continue;
					}
						
					//设置为发送完成
					messageDao.updateHmdahsJ1State4Alipay(hmdahsJ1.getId(), AlipayHmdahsJ1.STATE_3);
					sendCount ++;
						
					String request = GsonBuilderUtil.toJson(model);
					AlipayOpenPublicMessageSingleSendResponse response = alipayHmdahsJ1.sendMessage4Hmdahs(model);
					String state = "";
					String responseBody = "";
					if(null != response){
						state = response.getCode();
						responseBody = response.getBody();
					}
					int sendTemplateRecordsCount = messageDao.insertSendTemplateRecord(new SendTemplateRecord(uid,jszhm,lxdh,xh,pch,responseBody,request,new Date(),state,AlipayHmdahsJ1.TYPE));
				}
			}
		} catch (Exception e) {
			logger.error("定时发送模板消息出现异常",e);
			throw e;
		}finally{
			logger.info("本次共处理支付宝数据："+sum);
			return sendCount;
		}
	}
}