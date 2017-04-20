package cn.message.service.impl;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.message.bean.TemplateDataModel;
import cn.message.bean.TemplateDataModel.Property;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.TemplateMessageModel;
import cn.message.service.ITemplateMessageService;
import cn.message.utils.GsonUtil;
import cn.message.utils.WebService4Wechat;
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
		TemplateDataModel model = new TemplateDataModel();
		model.setTouser(openId);
		model.setTemplate_id(templateId);
		model.setUrl(url);
		model.setData(map);
		String json = WebService4Wechat.sendTemplateMessage(
				iMessageCached.getAccessToken(),
				model);
		TemplateMessageModel result = GsonUtil.fromJson(json, TemplateMessageModel.class);
		if(null != result){ 
			int errcode = result.getErrcode();
			if(0 == errcode){
				return true;
			}
		}
		return false;
	}
}
