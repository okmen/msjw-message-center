package cn.message.utils.wechat;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.model.MenuModel;
import cn.message.model.wechat.TemplateDataModel;
import cn.message.utils.GsonUtil;
import cn.sdk.util.GsonBuilderUtil;

/**
 * 
 * @author gaoxigang
 *
 */
public class WebService4Wechat {
	static Logger logger = Logger.getLogger(WebService4Wechat.class.getName());
	/**
	 * 获取全局accessToken
	 * @return
	 */
	public static Map<String, Object> getAccessToken(String appId,String appsecret) {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="
				+ appId + "&secret=" + appsecret;
		String result = HttpRequest.sendGet(url);
		Map<String, Object> map = GsonUtil.fromJson(result, Map.class);
		return map;
	}

	/**
	 * 获取全局ticket
	 * @param accessToken
	 * @return
	 */
	public static Map<String, Object> getJsapiTicket(String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ accessToken + "&type=jsapi";
		String result = HttpRequest.sendGet(url);
		Map<String, Object> map = GsonUtil.fromJson(result, Map.class);
		return map;
	}

	/**
	 * 获取api_ticket
	 * @param accessToken
	 * @return
	 */
	public static Map<String, Object> getApiTicket(String accessToken) {
		String url = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="
				+ accessToken + "&type=wx_card";
		String result = HttpRequest.sendGet(url);
		Map<String, Object> map = GsonUtil.fromJson(result, Map.class);
		return map;
	}
	
	/**
	 * 发送获取openId请求
	 * @param appId
	 * @param appSecret
	 * @param code
	 * @return
	 */
	public static Map<String, Object> sendOauth(String appId, String appSecret,
			String code) {
		String codeUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?"
				+ "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
		String requestUrl = codeUrl.replace("APPID", appId)
				.replace("SECRET", appSecret).replace("CODE", code);
		String result = HttpRequest.sendGet(requestUrl);
		Map<String, Object> map = GsonUtil.fromJson(result, Map.class);
		return map;
	}

	/**
	 * 发送模板消息
	 * @param accessToken
	 * @param templateDataModel
	 * @return
	 */
	public static String sendTemplateMessage(String accessToken,
			TemplateDataModel templateDataModel) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ accessToken;
		String json = GsonBuilderUtil.toJson(templateDataModel);
		return HttpRequest.sendPost4Wechat(url, json);
	}
	
	/**
	 * 创建菜单
	 * @param accessToken
	 * @return
	 */
	public static String createMenu(String accessToken,String json){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;
		return HttpRequest.sendPost4Wechat(url, json);
	}
	
	
	public static void main(String[] args) {
		Map<String, Object> map  = getAccessToken("wxc2b699cf2f919b58","cbfabef2717b14e416de93dd83498862");
		String accessToken = map.get("access_token").toString();
		System.out.println(accessToken);
		/*String json = MenuModel.initFromH5("wxc2b699cf2f919b58","http://szjj.u-road.com/h5");
		createMenu("SbW4Lx-UoVehWNJZTOGCH606GzLMLCvpphAFrEe88tUYgUxn2Eo3IhOk8Qv-ZafTfWVjud9DUE8iv0cjFWNGodV8bp5FfX0_xzsrXCyZ0oYNGBeCHAYRM",json);
*/	}
	
	/**
	 * 查询菜单
	 * @param accessToken
	 * @return
	 */
	public static String queryMenu(String accessToken){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token="+accessToken;
		return HttpRequest.sendGet(url);
	}
	
	/**
	 * 获取用户信息
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static Map<String, Object> getUserInfo(String accessToken,String openId){
		String url = "https://api.weixin.qq.com/sns/userinfo?access_token="+accessToken+"&openid="+openId+"&lang=zh_CN";
		String result = HttpRequest.sendGet(url);
		Map<String, Object> map = GsonUtil.fromJson(result, Map.class);
		return map;
	}
	
	/**
	 * 解密code
	 * @param accessToken
	 * @param decryptCode
	 * @return
	 */
	public static String decryptCode(String accessToken,String decryptCode){
		String url = "https://api.weixin.qq.com/card/code/decrypt?access_token="+ accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("encrypt_code", decryptCode);
		String data = GsonUtil.toJson(map);
		
		String result = HttpRequest.sendPost4Wechat(url,data);
		DecryptCodeResult decryptCodeResult = GsonUtil.fromJson(result, DecryptCodeResult.class);
		if(null != decryptCodeResult){
			if(0 == decryptCodeResult.getErrcode()){
				return decryptCodeResult.getCode();
			}
		}
		return "";
	}
	
	/**
	 * 激活卡
	 * @param accessToken
	 * @param decryptCode
	 * @return
	 */
	public static boolean activateCard(String accessToken,String cardId,String code,String ljjf,String syrq,String zjcx){
		String url = "https://api.weixin.qq.com/card/generalcard/activate?access_token="+ accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("card_number", code);
		map.put("code", code);
		map.put("card_id", cardId);
		map.put("init_custom_field_value1", ljjf);
		map.put("init_custom_field_value2", syrq);
		map.put("init_custom_field_value3", zjcx);
		
		String data = GsonUtil.toJson(map);
		
		String result = HttpRequest.sendPost4Wechat(url,data);
		BaseWechatResult baseWechatResult = GsonUtil.fromJson(result, BaseWechatResult.class);
		if(null != baseWechatResult){
			if(0 == baseWechatResult.getErrcode()){
				return true;
			}
		}
		return false;
	}
	
	public static class BaseWechatResult{
		private Integer errcode;
		private String errmsg;
		public Integer getErrcode() {
			return errcode;
		}
		public void setErrcode(Integer errcode) {
			this.errcode = errcode;
		}
		public String getErrmsg() {
			return errmsg;
		}
		public void setErrmsg(String errmsg) {
			this.errmsg = errmsg;
		}
	}
	
	public static class DecryptCodeResult extends BaseWechatResult{
		private String code;
		public String getCode() {
			return code;
		}
		public void setCode(String code) {
			this.code = code;
		}
	}
}
