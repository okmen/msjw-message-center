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
	public boolean sendMessage(String openId,String url,Map<String, String> map) {
		TemplateDataModel model = new TemplateDataModel();
		model.setTouser(openId);
		model.setTemplate_id("NPDRVXV-5SA2P2CK_rDeL4xRLg3aR3og1Sq3lcCuxOE");
		model.setUrl(url);
		Map<String, TemplateDataModel.Property> propertys = new HashMap<String, TemplateDataModel.Property>();
		for (String key : map.keySet()) {
			//取出参数中的值 组装property
			String s = map.get(key);
			Property property = model.new Property();
			property.setValue(s);
			property.setColor("#173177");
			//加入propertys中
			propertys.put(key, property);
		}
		model.setData(propertys);
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
