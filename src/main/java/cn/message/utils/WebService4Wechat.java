package cn.message.utils;

import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;

import cn.message.bean.TemplateDataModel;
import cn.message.bean.TemplateDataModel.Property;
import cn.message.model.MenuModel;

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
		String json = GsonUtil.toJson(templateDataModel);
		return HttpRequest.sendPost4Wechat(url, json);
	}
	
	/**
	 * 创建菜单
	 * @param accessToken
	 * @return
	 */
	public static String createMenu(String accessToken,String appId,String javaDomain,String h5Domain){
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token="+accessToken;
		return HttpRequest.sendPost4Wechat(url, new MenuModel().init(appId,javaDomain,h5Domain));
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

	public static void main(String[] args) {
		TemplateDataModel model = new TemplateDataModel();
		model.setTouser("oTlk3s3LVb9RYwlBNF-Euurz9mS4");
		model.setTemplate_id("NPDRVXV-5SA2P2CK_rDeL4xRLg3aR3og1Sq3lcCuxOE");
		model.setUrl("http://gxgnet.6655.la/recharge/index.jsp");
		Map<String, TemplateDataModel.Property> map = new HashMap<String, TemplateDataModel.Property>();
		Property property = model.new Property();
		property.setValue("动感地带");
		property.setColor("#173177");
		map.put("serviceName", property);
		model.setData(map);
		sendTemplateMessage(
				"BRgkTrrzhEeidLg_VXtvKiNHYOjuFo-00GOuTLS4UpyoodY1MVnhOp4-Rsw0bwK3QTdPoypTMEU_ZIPm80FfMXPK7AsNkQ16UIG9Z0H3Jx-GdL5emSNwccB7LwgYTs99MIMdAHAHDH",
				model);
	}
}
