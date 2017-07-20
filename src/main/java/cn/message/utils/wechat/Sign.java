package cn.message.utils.wechat;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 微信签名算法
 * @author gaoxigang
 *
 */
public class Sign {
	/**
	 * 获取sdk授权信息
	 */
	public static Map<String, Object> sign(String ticket,String url,String appId){
		Map<String, Object> map = new HashMap<String, Object>();
		String noncestr = UUID.randomUUID().toString();
		Long tempTimestamp = System.currentTimeMillis();
		String timestamp = tempTimestamp.toString().substring(0, 10);
		String string = "jsapi_ticket="+ticket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&url="+url;
		String sha1 = SHA1.SHA1Digest(string);
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("signature", sha1);
		map.put("appId", appId);
		return map;
	}
	
	/**
	 * 获取sdk授权信息
	 */
	public static Map<String, Object> sign4Card(String apiTicket,String openId,String cardId){
		Map<String, Object> map = new HashMap<String, Object>();
		String noncestr = UUID.randomUUID().toString();
		Long tempTimestamp = System.currentTimeMillis();
		String timestamp = tempTimestamp.toString().substring(0, 10);
		String string = "api_ticket="+apiTicket+"&noncestr="+noncestr+"&timestamp="+timestamp+"&card_id="+cardId+"&openid="+openId;
		String sha1 = SHA1.SHA1Digest(string);
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("openId", openId);
		map.put("cardId", cardId);
		map.put("signature", sha1);
		return map;
	}
}
