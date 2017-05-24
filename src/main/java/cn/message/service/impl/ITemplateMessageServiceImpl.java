package cn.message.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.TemplateMessageModel;
import cn.message.model.wechat.TemplateDataModel;
import cn.message.model.wechat.TemplateDataModel.Property;
import cn.message.service.ITemplateMessageService;
import cn.message.utils.GsonUtil;
import cn.message.utils.wechat.WebService4Wechat;
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
}
