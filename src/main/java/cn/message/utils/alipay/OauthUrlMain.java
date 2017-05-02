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
					"&redirect_uri="+EncodeUtil.encodeUTF8("https://szjjapi.stc.gov.cn/oauthAlipay/callback.html")+
					"&state=https://gzh.stc.gov.cn";
					
		System.out.println("支付宝 出道科技:"+clickUrlToSellerList);
	}
}
