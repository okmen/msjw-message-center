package cn.message.utils.wechat;
import java.util.HashMap;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.model.MenuModel;
import cn.message.model.wechat.MessageChannelModel;
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
	 * 发送服务通知
	 * @param accessToken
	 * @param messageChannelModel
	 * @return
	 */
	public static String sendServiceMessage(String accessToken,
			MessageChannelModel messageChannelModel) {
		String url = "https://api.weixin.qq.com/cityservice/sendmsgdata?access_token="
				+ accessToken;
		String json = GsonBuilderUtil.toJson(messageChannelModel);
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
	/*	Map<String, Object> map  = getAccessToken("wx65a828b7abc4516d","d4624c36b6795d1d99dcf0547af5443d");
		String accessToken = map.get("access_token").toString();
		System.out.println(accessToken);*/
		String json = MenuModel.initFromH5("wxc2b699cf2f919b58","http://gzh.stc.gov.cn/h5/");
		createMenu("EBV6i34qdsdmsI4SkgXVzMXm0EFEQPZte4RkFyw520EDvChlGu8_G2NUN2SCgPTRb-9w7PvCRjqBxe5zzv_tHtxY-GZrRONqEARnnJsvbpcg1Jc_qpdqFX8PmEaWnLgqXXYeCJAGGH",json);
		
		//kfaccountAdd("_4jN_NIPYK6kM0Cb2S4TGMGttvW2Z4tlSqq7aoMy8wZFYOnTPGzS58TGAjWznEXz-m7TZp9lxBC7bkhxRhoPkHGTGvzE6Fb8hsHT2cUuKkMFJIbAHALPO");
		//String identifyId = "XXIzTtMqCxwOaawoE91+VH1v2SEO57gbqsd2RCMcI1+KGfKDnIdco1Q2e58MsIPcfDVTNWwccPbeEFfMI1sUwiehJ4uK2HsMSIguodUDz02wq2rV7niqH4mil37/tAp7";
		//getImage("g15cVFV1OhVs4FEZoo7k1ISgrHi5gZ0l1AH-R-aEV9N4qIArbKFpfJQ-XvSO4KLXhI_aB3Da6p5CEghNeyCNgS608YOa30eyr2lXpQtnJ9KJwSvZNuY_UGgFGwbu0qBkXJBiCEASEW", identifyId);
	}
	
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
	 * 发送模板消息
	 * @param accessToken
	 * @param templateDataModel
	 * @return
	 */
	public static String sendTemplateMessage(String accessToken,
			String json) {
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token="
				+ accessToken;
		return HttpRequest.sendPost4Wechat(url, json);
	}
	
	/**
	 * 创建客服接口
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static boolean kfaccountAdd(String accessToken){
		Kf kf = new Kf();
		kf.setKf_account("account_1@chudaokeji_kkyq");
		kf.setNickname("测试客服1");
		String kfData = GsonUtil.toJson(kf);
		
		String url = "https://api.weixin.qq.com/customservice/kfaccount/add?access_token="+accessToken;
		String result = HttpRequest.sendPost(url, kfData);
		Map<String, Object> map = GsonUtil.fromJson(result, Map.class);
		BaseWechatResult baseWechatResult = GsonUtil.fromJson(result, BaseWechatResult.class);
		if(null != baseWechatResult){
			if(0 == baseWechatResult.getErrcode()){
				return true;
			}
		}
		return false;
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
		String data = GsonBuilderUtil.toJson(map);
		
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
	 * 激活驾驶证
	 * @param accessToken
	 * @param decryptCode
	 * @return
	 */
	public static boolean activateJsCard(String accessToken,String cardId,String code,String ljjf,String syrq,String zjcx){
		String url = "https://api.weixin.qq.com/card/generalcard/activate?access_token="+ accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("card_number", code);
		map.put("code", code);
		map.put("card_id", cardId);
		map.put("init_custom_field_value1", ljjf);
		map.put("init_custom_field_value2", syrq);
		map.put("init_custom_field_value3", zjcx);
		
		String data = GsonBuilderUtil.toJson(map);
		
		String result = HttpRequest.sendPost4Wechat(url,data);
		BaseWechatResult baseWechatResult = GsonUtil.fromJson(result, BaseWechatResult.class);
		if(null != baseWechatResult){
			if(0 == baseWechatResult.getErrcode()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 激活行驶证
	 * @param accessToken
	 * @param decryptCode
	 * @return
	 */
	public static boolean activateXsCard(String accessToken,String cardId,String code){
		String url = "https://api.weixin.qq.com/card/membercard/activate?access_token="+ accessToken;
		Map<String, String> map = new HashMap<String, String>();
		map.put("membership_number", code);
		map.put("code", code);
		map.put("card_id", cardId);
		
		String data = GsonBuilderUtil.toJson(map);
		
		String result = HttpRequest.sendPost4Wechat(url,data);
		BaseWechatResult baseWechatResult = GsonUtil.fromJson(result, BaseWechatResult.class);
		if(null != baseWechatResult){
			if(0 == baseWechatResult.getErrcode()){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 刷脸获取照片
	 * @param accessToken
	 * @param openId
	 * @return
	 */
	public static void getImage(String accessToken,String identifyId){
		String url = "https://api.weixin.qq.com/cityservice/face/identify/getimage?access_token="+accessToken;
		HttpRequest.sendPost4File(url, "{\"identify_id\":\""+identifyId+"\"}");
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
	
	public static class Kf{
		private String kf_account;
		private String nickname;
		private String password;
		public String getKf_account() {
			return kf_account;
		}
		public void setKf_account(String kf_account) {
			this.kf_account = kf_account;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
	}
}
