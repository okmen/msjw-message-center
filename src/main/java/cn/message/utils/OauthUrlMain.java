package cn.message.utils;

/**
 * 获取用户授权url
 * @author gaoxigang
 *
 */
public class OauthUrlMain {
	public static void main(String[] args) {
		//ckuckUrl是验证的入口
		String clickUrlToSellerList=
				"https://open.weixin.qq.com/connect/oauth2/authorize" +
						"?appid=" +"wx65a828b7abc4516d" +//测试号 gxgdaydayup
						"&redirect_uri=" + EncodeUtil.encodeUTF8("http://gxg.tunnel.qydev.com/oauth/callback.html")+
						"&response_type=code" +
						//"&scope=snsapi_base" +  
						"&scope=snsapi_userinfo" +
						"&state=STATE" +
						"#wechat_redirect";
		System.out.println("clickUrlToSellerList:"+clickUrlToSellerList);
	}
}
