package cn.message.utils.wechat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.codec.digest.DigestUtils;


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
		String [] array = {apiTicket,noncestr,timestamp,cardId,openId};
		Arrays.sort(array);
		
		String string = "";
		for (String s : array) {
			string += s;
		}
		String sha1 = DigestUtils.shaHex(string);
		map.put("apiTicket", apiTicket);
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("openId", openId);
		map.put("cardId", cardId);
		map.put("signature", sha1);
		return map;
	}
	
	public static void main(String[] args) {
		String apiTicket = "m7RQzjA_ljjEkt-JCoklRHpkob55r1Dc9Lk2eduWUvfw8rikPbRr2sqiFs5nqaFKiBmlVEUL6mOekZ3lEr37mQ";
		String cardId = "pPyqQjq_2LnZeey0y5XK-ArtZDSo";
		String openId = "oPyqQjhnNYEZ1nGT-6XizgLOTfAM";
		Map<String, Object> map = new HashMap<String, Object>();
		String noncestr = UUID.randomUUID().toString();
		Long tempTimestamp = System.currentTimeMillis();
		String timestamp = tempTimestamp.toString().substring(0, 10);
		String [] array = {apiTicket,noncestr,timestamp,cardId,openId};
		Arrays.sort(array);
		
		String string = "";
		for (String s : array) {
			string += s;
		}
		String sha1 = DigestUtils.shaHex(string);
		map.put("apiTicket", apiTicket);
		map.put("timestamp", timestamp);
		map.put("noncestr", noncestr);
		map.put("openId", openId);
		map.put("cardId", cardId);
		map.put("signature", sha1);
		System.out.println(map.toString());
	}
}
