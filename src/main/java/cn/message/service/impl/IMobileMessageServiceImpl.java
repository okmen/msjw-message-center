package cn.message.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.service.IMobileMessageService;
import cn.sdk.webservice.WebServiceClient;

import com.alibaba.fastjson.JSONObject;
/**
 * 消息中心
 * @author gxg
 *
 */
@Service("mobileMessageService")
@SuppressWarnings(value="all")
public class IMobileMessageServiceImpl implements IMobileMessageService{
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final static String _SUCCESS = "1";
	@Autowired
	private IMessageDao messageDao;

	@Autowired
	private IMessageCachedImpl iMessageCached;

	@Override
	public boolean sendMessage(String mobile, String content) {
		try {
			String url = iMessageCached.getUrl();
			String method = iMessageCached.getMethod();
			String jkid = "sms";// 接口id jkid
			String userId = iMessageCached.getUserid();
			String userPwd = iMessageCached.getUserpwd();
			String key = iMessageCached.getKey();

			StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
			sb.append("<request>");
			sb.append("<userZh>wx</userZh>");
			sb.append("<userMy>jstwx</userMy>");
			sb.append("<mobile>.$mobile.</mobile>");
			sb.append("<content>.$content.</content>");
			sb.append("</request>");

			String xml = sb.toString().replace(".$mobile.", mobile)
					.replace(".$content.", content);
			JSONObject jsonObject;
			jsonObject = WebServiceClient.getInstance().requestWebService(url,
					method, jkid, xml.toString(), userId, userPwd, key);
			
			logger.info(mobile + "短信发送结果:" + jsonObject.toJSONString());
			
			if (StringUtils.isNotBlank(jsonObject.getString("code"))
					&& _SUCCESS.equals(jsonObject.getString("code"))) {
				return true;
			}
		} catch (Exception e) {
			logger.error("手机短信发送异常:" + "mobile=" + mobile + ",content="
					+ content, e);
		}
		return false;
	}
}
