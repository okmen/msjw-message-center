package cn.message.utils.wechat;

/**
 * 修改uroad 原来创建的卡
 * @author gaoxigang
 *
 */
public class CardUtilUroad {
	
	static String token  = "fnMV6I5MzDkZqU8UAEedwNCjoGsGV-VVsbehajWiXaqqh_gDd0Q8ablrNfu7cZD_7T_I3ktXFVlM1P8DoOeZwCTD55aDktGGF5ENnwjsq4NSW4CZFGa5cFgB5nJiiabwAYJdCBAWUF";
	
	/**
	 * pPyqQjvEAr-_y7JHHf9LzE4PefTQ
	 * 2017-07-28 00:57:43
	 * @return
	 */
	public static void update1(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjvEAr-_y7JHHf9LzE4PefTQ",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/violink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/ectlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ememberActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/emembercarlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceselink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card1:"+json);
		
	}
	
	/**
	 * pPyqQjj4vZHdL4InOYCG3saUlC3E
	 * @return
	 */
	public static void update2(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjj4vZHdL4InOYCG3saUlC3E",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/violink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/ectlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ememberActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/emembercarlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceselink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card2:"+json);
	}
	
	/**
	 * pPyqQjgz99DJNN-W671xyOa0rCco
	 * @return
	 */
	public static void update3(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjgz99DJNN-W671xyOa0rCco",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/violink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/ectlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ememberActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/emembercarlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceselink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card3:"+json);
	}
	
	
	/**
	 * pPyqQjn-nIVKpi7ZGlS1J4vNrSFE
	 * @return
	 */
	public static void update4(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjn-nIVKpi7ZGlS1J4vNrSFE",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/violink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/ectlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ememberActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/emembercarlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceselink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card4:"+json);
	}
	
	/**
	 * pPyqQjuKtgtz98UlbcBHg-DAZ7Mc
	 * @return
	 */
	public static void update5(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjuKtgtz98UlbcBHg-DAZ7Mc",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/violink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/ectlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ememberActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/emembercarlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceselink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card5:"+json);
	}
	
	/**
	 * pPyqQjkOxVe00GFtJY6dC12Z-jnE
	 * @return
	 */
	public static void update6(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjkOxVe00GFtJY6dC12Z-jnE",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/violink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/ectlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ememberActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/emembercarlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceselink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card6:"+json);
	}
	
	/**
	 * pPyqQjtqlHDnC4uk3XrQsXyusPOE
	 * @return
	 */
	public static void update7(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjtqlHDnC4uk3XrQsXyusPOE",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/ectlink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ememberActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card7:"+json);
	}
	
	/**
	 * pPyqQjlF257dK1a9zfiIXV9HIM70
	 * @return
	 */
	public static void update8(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjlF257dK1a9zfiIXV9HIM70",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/driverInfo",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ejszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceseyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card8:"+json);
	}
	
	/**
	 * pPyqQjnNyymMB87KHQSilixWjOPc
	 * @return
	 */
	public static void update9(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjnNyymMB87KHQSilixWjOPc",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList3",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/exszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszbanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList2");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card9:"+json);
	}
	
	/**
	 * pPyqQjmI-9sWzh0-tfLtvZBXmmzA
	 * @return
	 */
	public static void update10(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjmI-9sWzh0-tfLtvZBXmmzA",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceseyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/driverInfo",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ejszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card10:"+json);
	}
	
	/**
	 * pPyqQjjkzuE4qy1GiFsFXOusDSXs
	 * @return
	 */
	public static void update11(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjjkzuE4qy1GiFsFXOusDSXs",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/exszActive",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList3",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszbanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card11:"+json);
	}
	
	/**
	 * pPyqQjjZxRV3rUIxvesJ5gOKMW1M
	 * @return
	 */
	public static void update12(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjjZxRV3rUIxvesJ5gOKMW1M",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/exszActive",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList3",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszbanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card12:"+json);
	}
	
	/**
	 * pPyqQjrR803vlNZ_OH5boSny_Iqw
	 * @return
	 */
	public static void update13(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjrR803vlNZ_OH5boSny_Iqw",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceseyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/driverInfo",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ejszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card13:"+json);
	}
	
	/**
	 * pPyqQjvgBIM0Rrn275Uql0etTBxw
	 * @return
	 */
	public static void update14(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjvgBIM0Rrn275Uql0etTBxw",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/exszActive",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList3",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszbanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card14:"+json);
	}
	
	/**
	 * pPyqQjrcJKvZE101fQYHx2xyMKeA
	 * @return
	 */
	public static void update15(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjrcJKvZE101fQYHx2xyMKeA",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceseyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/driverInfo",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ejszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card15:"+json);
	}
	
	/**
	 * pPyqQjqaxJb2kALY1hAY7YKqEcTg
	 * @return
	 */
	public static void update16(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjqaxJb2kALY1hAY7YKqEcTg",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceseyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/driverInfo",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ejszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card16:"+json);
	}
	
	/**
	 * pPyqQjqhH_2GbJQRPEjzGQRDYsy0
	 * @return
	 */
	public static void update17(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjqhH_2GbJQRPEjzGQRDYsy0",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/exszActive",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList3",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszbanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card17:"+json);
	}
	
	/**
	 * 目前正在使用的卡
	 * pPyqQjq_2LnZeey0y5XK-ArtZDSo
	 * 驾驶证
	 * @return
	 */
	public static void update18(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjq_2LnZeey0y5XK-ArtZDSo",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceseyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/driverInfo",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ejszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("pPyqQjq_2LnZeey0y5XK-ArtZDSo updateJSZ:"+json);
	}
	
	/**
	 * 目前正在使用的卡
	 * pPyqQjvbE2LpZReeUHlNN2ReV8w0
	 * 行驶证
	 * @return
	 */
	public static void update19(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjvbE2LpZReeUHlNN2ReV8w0",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/exszActive",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/carList3",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/exszbanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("pPyqQjvbE2LpZReeUHlNN2ReV8w0 updateXSZ:"+json);
		
	}
	
	/**
	 * pPyqQjvniOt9Scwflb8w83m8RaI0
	 * @return
	 */
	public static void update20(){
		String url = "https://api.weixin.qq.com/card/update?access_token="+token;
		String card1Json = CardGeneral.update("pPyqQjvniOt9Scwflb8w83m8RaI0",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elinceseyuyuelink&response_type=code&scope=snsapi_base&state=0#wechat_redirect",
				"http://gzh.stc.gov.cn/szjjpro/index.php/Business/Member/unlogin/driverInfo",
				"http://gzh.stc.gov.cn/szjjpro/ectroniclicense/unlogin/ejszActive",
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxc2b699cf2f919b58&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/ectroniclicense/elincesebanlilink&response_type=code&scope=snsapi_base&state=0#wechat_redirect");
		String json = HttpRequest.sendPost(url, card1Json);
		System.out.println("card20:"+json);
	}
	
	public static void main(String[] args) {
	}
}
