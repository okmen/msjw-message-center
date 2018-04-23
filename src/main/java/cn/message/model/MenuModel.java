package cn.message.model;

import org.apache.log4j.Logger;

import cn.message.utils.EncodeUtil;
import cn.message.utils.GsonBuilderUtil;

public class MenuModel {
	private static Logger logger = Logger.getLogger(MenuModel.class);
	private Button [] button;
	public Button[] getButton() {
		return button;
	}
	public void setButton(Button[] button) {
		this.button = button;
	}
	
	public class Button{
		private String key;
		private String name;
		private String type;
		private String url;
		private String media_id;
		private Button [] sub_button;
		public String getKey() {
			return key;
		}
		public void setKey(String key) {
			this.key = key;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getUrl() {
			return url;
		}
		public void setUrl(String url) {
			this.url = url;
		}
		public String getMedia_id() {
			return media_id;
		}
		public void setMedia_id(String media_id) {
			this.media_id = media_id;
		}
		public Button[] getSub_button() {
			return sub_button;
		}
		public void setSub_button(Button[] sub_button) {
			this.sub_button = sub_button;
		}
		public Button(String key, String name, String type, String url) {
			this.key = key;
			this.name = name;
			this.type = type;
			this.url = url;
		}
		public Button(){}
	}
	
	/**
	 * 初始化菜单
	 * @return
	 */
	public static String init(String appId,String javaDomain,String h5Domain){
		String url=
				"https://open.weixin.qq.com/connect/oauth2/authorize" +
						"?appid="+appId +
						"&redirect_uri=" + EncodeUtil.encodeUTF8("http://"+javaDomain+"/oauth/callback.html")+
						"&response_type=code" +
						"&scope=snsapi_userinfo" +
						"&state=_STATE_"+
						"#wechat_redirect";
		String mainUrl = "http://"+h5Domain;
		MenuModel menuModel = new MenuModel();
		Button button = menuModel.new Button("start_level_user","星级用户","view", url.replace("_STATE_", mainUrl));
		Button button2 = menuModel.new Button("info_query","信息查询",null,null);
		button2.setSub_button(new Button[]{
				menuModel.new Button("convenience_information","便民信息","click",null),
				menuModel.new Button("service_guide","办事指南","click",null),
				menuModel.new Button("trailer_query","拖车查询","view", url.replace("_STATE_", mainUrl+"/#/moveCar")),
				menuModel.new Button("electronic_police_distributed_query","电子警察分布查询","view", url.replace("_STATE_", mainUrl)),
				menuModel.new Button("traffic_information_query","交通违法信息查询","view",url.replace("_STATE_", mainUrl+"/#/queryLawless")),
				menuModel.new Button("fault_reporting","故障报错和使用建议","view",url.replace("_STATE_", mainUrl))
		});
		
		
		Button button3 = menuModel.new Button("traffic_police_interaction","交警互动",null,null);
		button3.setSub_button(new Button[]{
			menuModel.new Button("user_center","个人中心","view",	url.replace("_STATE_", mainUrl+"/#/personalCenter")),
			menuModel.new Button("dbyy","大鹏通行预约","view",url.replace("_STATE_", "http://gzh.stc.gov.cn/dbyy/#/")),
			menuModel.new Button("report_information_inquiry","举报信息查询","view",url.replace("_STATE_", mainUrl)),
			menuModel.new Button("readily_report","随手拍举报","view", url.replace("_STATE_", mainUrl+"/#/takePicturesInform")),
			menuModel.new Button("emergency_traffic","突发路况","view",url.replace("_STATE_", mainUrl))
		});
		
		menuModel.setButton(new Button[]{button,button2,button3});
		
		String json = GsonBuilderUtil.toJson(menuModel);
		logger.info(json);
		return json;
	}
	
	
	
	/**
	 * 初始化菜单
	 * @return
	 */
	public static String initFromH5(String appId,String h5Domain){
		MenuModel menuModel = new MenuModel();
		Button button = menuModel.new Button("start_level_user","星级用户","view", h5Domain);
		Button button2 = menuModel.new Button("info_query","信息查询",null,null);
		button2.setSub_button(new Button[]{
				menuModel.new Button("convenience_information","便民信息","click",null),
				menuModel.new Button("service_guide","办事指南","click",null),
				menuModel.new Button("trailer_query","拖车查询","view", h5Domain+"#/moveCar"),
				menuModel.new Button("electronic_police_distributed_query","电子警察分布查询","view", "http://gzh.stc.gov.cn/szjjpro/index.php/infoquery/robotpolicequery/chooseArea"),
				menuModel.new Button("traffic_information_query","交通违法信息查询","view",h5Domain+"#/newqueryLawless?type=nologin"),
				menuModel.new Button("fault_reporting","故障报错和使用建议","view","https://open.weixin.qq.com/connect/oauth2/authorize?appid="+appId+"&redirect_uri=http://gzh.stc.gov.cn/szjjpro/index.php/infoquery/policeinteraction/failureReport&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect")
		});
		
		
		Button button3 = menuModel.new Button("traffic_police_interaction","交警互动",null,null);
		button3.setSub_button(new Button[]{
			menuModel.new Button("user_center","个人中心","view",	h5Domain+"#/personalCenter"),
			menuModel.new Button("dbyy","大鹏通行预约","view", "http://gzh.stc.gov.cn/dbyy/#/"),
			menuModel.new Button("report_information_inquiry","举报信息查询","view", h5Domain+"#/takePicturesQuery"),
			menuModel.new Button("readily_report","随手拍举报","view", h5Domain+"#/takePicturesInform"),
			menuModel.new Button("emergency_traffic","突发路况","view", "http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=214709888&idx=1&sn=3e0921863520169e0435d643725f2904")
		});
		
		menuModel.setButton(new Button[]{button,button2,button3});
		
		String json = GsonBuilderUtil.toJson(menuModel);
		logger.info(json);
		return json;
	}
	public static void main(String[] args) {
		//initFromH5("wx65a828b7abc4516d", "http://gxg.tunnel.qydev.com", "http://np.tunnel.qydev.com");
		//initFromH5("wx48a8104946507c1e", "http://testjava.chudaokeji.com" ,"testh5.chudaokeji.com");
		
		//initFromH5("wxc2b699cf2f919b58", "https://szjjapi.stc.gov.cn","https://gzh.stc.gov.cn");
		//initFromH5("wxc2b699cf2f919b58","http://szjj.u-road.com/h5");
		
		logger.info("ok");
	}
}	
