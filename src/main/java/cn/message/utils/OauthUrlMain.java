package cn.message.utils;

/**
 * 获取用户授权url
 * @author gaoxigang
 *
 */
public class OauthUrlMain {
	public static void main(String[] args) {
		String clickUrlToSellerList=
				"https://open.weixin.qq.com/connect/oauth2/authorize" +
						//"?appid=" +"wx65a828b7abc4516d" +//测试号 gxgdaydayup
						"?appid=wx48a8104946507c1e"+ //一摇惊喜
						"&redirect_uri=" + EncodeUtil.encodeUTF8("http://testjava.chudaokeji.com/oauth/callback.html")+
						"&response_type=code" +
						//"&scope=snsapi_base" +  
						"&scope=snsapi_userinfo" +
						"&state=http://testh5.chudaokeji.com" +
						"#wechat_redirect";
		System.out.println("clickUrlToSellerList:"+clickUrlToSellerList);
	}
}
