package cn.message.utils.wechat;

import cn.message.utils.EncodeUtil;

/**
 * 获取用户授权url
 * @author gaoxigang
 *
 */
public class OauthUrlMain {
	public static void main(String[] args) {
		String clickUrlToSellerList=
				"https://open.weixin.qq.com/connect/oauth2/authorize" +
						"?appid=wx48a8104946507c1e"+ //一摇惊喜
						"&redirect_uri=" + EncodeUtil.encodeUTF8("http://testjava.chudaokeji.com/oauth/callback.html")+
						"&response_type=code" +
						//"&scope=snsapi_base" +  
						"&scope=snsapi_userinfo" +
						"&state=http://testh5.chudaokeji.com" +
						"#wechat_redirect";
		System.out.println("一摇惊喜:"+clickUrlToSellerList);
		
		
		String clickUrlToSellerList2=
				"https://open.weixin.qq.com/connect/oauth2/authorize" +
						"?appid=wx629dea91ac256691"+ //一摇惊喜
						"&redirect_uri=" + EncodeUtil.encodeUTF8("https://szjjapi.stc.gov.cn/oauth/callback.html")+
						"&response_type=code" +
						//"&scope=snsapi_base" +  
						"&scope=snsapi_userinfo" +
						"&state=https://gzh.stc.gov.cn" +
						"#wechat_redirect";
		System.out.println("一摇夺宝:"+clickUrlToSellerList2);
		
		
		String clickUrlToSellerList3=
				"https://open.weixin.qq.com/connect/oauth2/authorize" +
						"?appid=wxc2b699cf2f919b58"+ //一摇惊喜
						"&redirect_uri=" + EncodeUtil.encodeUTF8("http://gzh.stc.gov.cn/api/oauth/callback.html")+
						"&response_type=code" +
						"&scope=snsapi_userinfo" +
						"&state=http://gzh.stc.gov.cn/h5/" +
						"#wechat_redirect";
		System.out.println("深圳交警:"+clickUrlToSellerList3);
	}
}
