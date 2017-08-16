package cn.message.utils.alipay;

import cn.message.model.alipay.AlipayServiceEnvConstants;
import cn.message.utils.EncodeUtil;

/**
 * 获取用户授权url
 * @author gaoxigang
 *
 */
public class OauthUrlMain {
	public static void main(String[] args) {
		//ckuckUrl是验证的入口
		String clickUrlToSellerList = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm" +
					"?app_id=" +AlipayServiceEnvConstants.APP_ID+
					"&scope=auth_user" +
					"&redirect_uri="+EncodeUtil.encodeUTF8("http://gzh.stc.gov.cn/api/oauthAlipay/callback.html")+
					"&state=http://gzh.stc.gov.cn";
		
		
		String clickUrlToSellerList1 = "https://openauth.alipay.com/oauth2/publicAppAuthorize.htm" +
				"?app_id=" +AlipayServiceEnvConstants.APP_ID+
				"&scope=auth_userinfo" +
				"&redirect_uri="+EncodeUtil.encodeUTF8("http://gzh.stc.gov.cn/api/oauthAlipay/callback.html")+
				"&state=http://gzh.stc.gov.cn";
		
		
					
		//System.out.println("支付宝 出道科技:"+clickUrlToSellerList);
		System.out.println("支付宝 深圳交警:"+clickUrlToSellerList1);
		
	}
}
