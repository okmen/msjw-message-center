package cn.message.service.impl;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.MsgChannelResultModel;
import cn.message.model.TemplateMessageModel;
import cn.message.model.wechat.MessageChannelModel;
import cn.message.model.wechat.TemplateDataModel;
import cn.message.model.wechat.TemplateDataModel.Property;
import cn.message.service.ITemplateMessageService;
import cn.message.utils.GsonUtil;
import cn.message.utils.wechat.WebService4Wechat;
import cn.sdk.bean.BaseBean;
import cn.sdk.webservice.WebServiceClient;
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
			String json = WebService4Wechat.sendTemplateMessage(
					iMessageCached.getAccessToken(),
					model);
			
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
					 json = WebService4Wechat.sendTemplateMessage(
							 newToken,model);
					 logger.info("重发返回值："+json);
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
	public boolean hmdahs() throws Exception{
		String interfaceNumber = "HM_DAHS";  //接口编号
		
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			.append("<request>")
				.append("<head>")   
					.append("<yhdh>").append("C").append("</yhdh>")  
					.append("<ip>").append("183.14.132.26").append("</ip>")    
					.append("<lsh>").append("1").append("</lsh>")   
					.append("<code>").append("J1").append("</code>")    
				.append("</head>")   
				.append("<body>")   
					.append("<sqm>").append("A95670E4F5A0E66453480D2681A9CCCC").append("</sqm>")    
				.append("</body>")   					
			.append("</request>");
			
		try {
			JSONObject respStr = WebServiceClient.getInstance().requestWebService(iMessageCached.getUrl(), iMessageCached.getMethod(), 
					interfaceNumber,sb.toString(),iMessageCached.getUserid(),iMessageCached.getUserpwd(),iMessageCached.getKey());
		} catch (Exception e) {
			throw e;
		}
		return false;
	}
}
