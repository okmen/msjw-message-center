package cn.message.utils.hmdahs;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import cn.message.bean.HmdahsJ1;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.UserCarInfo;
import cn.message.model.UserCarInfo.Data.Car;
import cn.message.model.wechat.TemplateDataModel.WechatTemlate1;
import cn.message.utils.GsonUtil;
import cn.message.utils.wechat.HttpRequest;
import cn.sdk.util.StringUtil;
import cn.sdk.webservice.WebServiceClient;

/**0
 * 模板消息定时推送工具类
 * @author gaoxigang
 *
 */
public class BaseHmdahsJ1 {
	Logger logger = Logger.getLogger(BaseHmdahsJ1.class);

	protected static final String HSLX_1 = "1";
	protected static final String HSLX_2 = "2";
	protected static final String HSLX_3 = "3";
	protected static final String HSLX_4 = "4";
	protected static final String HSLX_5 = "5";
	
	private static final String JDCSYYQTZ_KEY = "逾期未检审验";
	private static final String JSZLJHZTX_KEY = "有效期止为";
	private static final String JSZYQWHZTX_KEY = "逾期未换证";
	private static final String JSRSYTX_KEY = "审验有效期止为";
	private static final String JSRYQWSYTX_KEY = "逾期未审验状态";
	private static final String JSZZX_KEY = "注销可恢复";
	
	private static final String JDCSYYQTZ_FIRST = "机动车审核提醒";
	private static final String JSZLJHZTX_FIRST = "驾驶证临近换证提醒";
	private static final String JSZYQWHZTX_FIRST = "驾驶证逾期未换证提醒";
	private static final String JSRSYTX_FIRST = "驾驶人审验提醒";
	private static final String JSRYQWSYTX_FIRST = "驾驶人逾期未审验提醒";
	private static final String JSZZX_FIRST = "驾驶证注销";
	
	private static final String JDCSYYQTZ_KEYWORD2 = "办理方式：1、车辆注册未超过六年，点击进入“办理类业务”，选择“机动车业务”办理六年免检业务。（超过六年，需到检测站进行车辆检测过线）2、登录“深圳网上车管所”，点击业务预约服务，预约到全市各车管分所、大队窗口办理，到窗口办理机动车业务必须先预约。";
	private static final String JSZLJHZTX_KEYWORD2= "办理方式：1、点击进入“办理类业务”，选择“驾驶证业务”办理驾驶证期满换证业务。2、登录“深圳网上车管所”，点击业务预约服务，预约到全市各车管分所、大队窗口办理，到窗口办理驾驶证业务必须先预约。";
	private static final String JSZYQWHZTX_KEYWORD2 = "办理方式：1、点击进入“办理类业务”，选择“驾驶证业务”办理驾驶证期满换证业务。2、登录“深圳网上车管所”，点击业务预约服务，预约到全市各车管分所、大队窗口办理，到窗口办理驾驶证业务必须先预约。";
	private static final String JSRSYTX_KEYWORD2 = "办理方式：1、点击进入“办理类业务”，选择“驾驶证业务”办理驾驶证年审。2、登录“深圳网上车管所”，点击业务预约服务，预约到全市各车管分所、大队窗口办理，到窗口办理驾驶证业务必须先预约。";
	private static final String JSRYQWSYTX_KEYWORD2 = "办理方式：1、点击进入“办理类业务”，选择“驾驶证业务”办理驾驶证年审。2、登录“深圳网上车管所”，点击业务预约服务，预约到全市各车管分所、大队窗口办理，到窗口办理驾驶证业务必须先预约。";
	private static final String JSZZX_KEYWORD2 = "办理方式：1、点击进入“预约类业务”，选择“驾驶证业务”办理恢复驾驶资格业务。2、登录“深圳网上车管所”，点击业务预约服务，预约到西丽总所窗口办理，到窗口办理驾驶证业务必须先预约。";
	
	/**
	 * 核算类型 规则筛选map
	 */
	protected static Map<String, Map<String, WechatTemlate1>> HSLX_MAP = new HashMap<String, Map<String, WechatTemlate1>>();
	
	static {
		WechatTemlate1 JDCSYYQTZ_KEY_MODEL = new WechatTemlate1(JDCSYYQTZ_FIRST, JDCSYYQTZ_KEYWORD2);
		WechatTemlate1 JSZLJHZTX_KEY_MODEL = new WechatTemlate1(JSZLJHZTX_FIRST, JSZLJHZTX_KEYWORD2);
		WechatTemlate1 JSZYQWHZTX_KEY_MODEL = new WechatTemlate1(JSZYQWHZTX_FIRST, JSZYQWHZTX_KEYWORD2);
		WechatTemlate1 JSRSYTX_KEY_MODEL = new WechatTemlate1(JSRSYTX_FIRST, JSRSYTX_KEYWORD2);
		WechatTemlate1 JSRYQWSYTX_KEY_MODEL = new WechatTemlate1(JSRYQWSYTX_FIRST, JSRYQWSYTX_KEYWORD2);
		WechatTemlate1 JSZZX_KEY_MODEL = new WechatTemlate1(JSZZX_FIRST, JSZZX_KEYWORD2);
		
		Map<String, WechatTemlate1> HSLX_3_MAP = new HashMap<String, WechatTemlate1>();
		HSLX_3_MAP.put(JDCSYYQTZ_KEY, JDCSYYQTZ_KEY_MODEL);
		
		Map<String, WechatTemlate1> HSLX_2_MAP = new HashMap<String, WechatTemlate1>();
		HSLX_2_MAP.put(JSZLJHZTX_KEY, JSZLJHZTX_KEY_MODEL);
		
		Map<String, WechatTemlate1> HSLX_1_MAP = new HashMap<String, WechatTemlate1>();
		HSLX_1_MAP.put(JSZYQWHZTX_KEY, JSZYQWHZTX_KEY_MODEL);
		HSLX_1_MAP.put(JSRYQWSYTX_KEY, JSRYQWSYTX_KEY_MODEL);
		HSLX_1_MAP.put(JSZZX_KEY, JSZZX_KEY_MODEL);
		
		Map<String, WechatTemlate1> HSLX_5_MAP = new HashMap<String, WechatTemlate1>();
		HSLX_5_MAP.put(JSRSYTX_KEY, JSRSYTX_KEY_MODEL);
		
		HSLX_MAP.put(HSLX_3, HSLX_3_MAP);
		HSLX_MAP.put(HSLX_2, HSLX_2_MAP);
		HSLX_MAP.put(HSLX_1, HSLX_1_MAP);
		HSLX_MAP.put(HSLX_5, HSLX_5_MAP);
	}
	protected IMessageDao messageDao;
	protected IMessageCachedImpl iMessageCached;
	public IMessageDao getMessageDao() {
		return messageDao;
	}
	public void setMessageDao(IMessageDao messageDao) {
		this.messageDao = messageDao;
	}
	public IMessageCachedImpl getiMessageCached() {
		return iMessageCached;
	}
	public void setiMessageCached(IMessageCachedImpl iMessageCached) {
		this.iMessageCached = iMessageCached;
	}
	
	public BaseHmdahsJ1(IMessageDao messageDao,
			IMessageCachedImpl iMessageCached) {
		this.messageDao = messageDao;
		this.iMessageCached = iMessageCached;
	}
	
	/**
	 * 递归实时取数据 取空为止
	 * @param level 第几层
	 * @throws Exception
	 */
	public void getHmdahsJ1(int level)
			throws Exception {
		logger.info("当前递归第" + (level + 1) + "次");
		
		String interfaceNumber = "HM_DAHS"; // 接口编号
		// 拼装xml数据
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
				.append("<request>").append("<head>").append("<yhdh>")
				.append("C").append("</yhdh>").append("<ip>")
				.append("123.56.180.216").append("</ip>").append("<lsh>")
				.append("1").append("</lsh>").append("<code>").append("J1")
				.append("</code>").append("</head>").append("<body>")
				.append("<sqm>").append("A95670E4F5A0E66453480D2681A9CCCC")
				.append("</sqm>").append("</body>").append("</request>");

		String xml = WebServiceClient.requestWebServiceReturnXml(
				iMessageCached.getUrl(), iMessageCached.getMethod(),
				interfaceNumber, sb.toString(), iMessageCached.getUserid(),
				iMessageCached.getUserpwd(), iMessageCached.getKey());
		logger.info("解密后xml:" + xml);
		Document doc = DocumentHelper.parseText(xml);
		Element response = doc.getRootElement();
		Element body = response.element("body");
		Element pch = body.element("pch");
		List<Element> elements = body.elements("rec");

		// 警示通不返回数据了 即可退出
		if (null == elements || elements.size() == 0){
			logger.info("HM_DAHS J1 接口未返回数据 停止递归");
			return;
		}
			

		// 把数据保存起来到集合中
		List<HmdahsJ1> list = new ArrayList<HmdahsJ1>();
		for (Element element : elements) {
			Element xh = element.element("xh");
			Element u_id = element.element("u_id");
			Element lxdh = element.element("lxdh");
			Element jszhm = element.element("jszhm");
			Element hphm = element.element("hphm");
			Element hpzl = element.element("hpzl");
			Element message = element.element("message");
			Element hslx = element.element("hslx");

			HmdahsJ1 hmdahsJ1 = new HmdahsJ1(pch, xh, u_id, lxdh, jszhm, hphm,hpzl, message, hslx, new Date(), WechatHmdahsJ1.STATE_0, 0);
			list.add(hmdahsJ1);
		}
		if (null != list && 0 != list.size()) {
			int i = messageDao.batchInsertHmdahsJ1(list);
			if (i == 0) {
				logger.error("当前递归第" + (level + 1) + "次"+"取回Hm_dahs J1 接口插入数据失败");
				throw new Exception();
			}
			logger.info("本次调用Hm_dahs J1 接口共插入数据:" + i);
			// 进入递归
			getHmdahsJ1(level + 1);
		}
	}
	
	/**
	 * HM_DAHS J3 接口调用
	 * 根据日期查询批次号 最多为6个小时区间 0-5 6-11 12-17 18-23
	 * @return
	 * @throws Exception
	 */
	public void getHmdahsJ2() throws Exception {
		String interfaceNumber = "HM_DAHS";  //接口编号
		
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			.append("<request>")
				.append("<head>")   
					.append("<yhdh>").append("C").append("</yhdh>")  
					.append("<ip>").append("183.14.132.26").append("</ip>")    
					.append("<lsh>").append("1").append("</lsh>")   
					.append("<code>").append("J2").append("</code>")    
				.append("</head>")   
				.append("<body>")   
					.append("<sqm>").append("A95670E4F5A0E66453480D2681A9CCCC").append("</sqm>")    
					.append("<stime>2017072412</stime>")   			
					.append("<etime>2017072417</etime>")   			
				.append("</body>")   					
			.append("</request>");
			
		try {
			String xml = WebServiceClient.requestWebServiceReturnXml(iMessageCached.getUrl(), iMessageCached.getMethod(), 
					interfaceNumber,sb.toString(),iMessageCached.getUserid(),iMessageCached.getUserpwd(),iMessageCached.getKey());
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * HM_DAHS J3 接口调用
	 * 根据批次号查询数据
	 * @param pch
	 * @return
	 * @throws Exception
	 */
	public void getHmdahsJ3(String pch) throws Exception {
		String interfaceNumber = "HM_DAHS";  //接口编号
		
		//拼装xml数据
		StringBuffer sb = new StringBuffer();
			sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>")
			.append("<request>")
				.append("<head>")   
					.append("<yhdh>").append("C").append("</yhdh>")  
					.append("<ip>").append("183.14.132.26").append("</ip>")    
					.append("<lsh>").append("1").append("</lsh>")   
					.append("<code>").append("J1").append("</code>")    
				.append("</head>")   
				.append("<body>")   
					.append("<sqm>").append("A95670E4F5A0E66453480D2681A9CCCC").append("</sqm>")    
					.append("<pch></pch>")
				.append("</body>")   					
			.append("</request>");
			
		try {
			String xml = WebServiceClient.requestWebServiceReturnXml(iMessageCached.getUrl(), iMessageCached.getMethod(), 
					interfaceNumber,sb.toString(),iMessageCached.getUserid(),iMessageCached.getUserpwd(),iMessageCached.getKey());
		} catch (Exception e) {
			throw e;
		}
	}
	
	/**
	 * 获取个人车辆信息
	 * @param mobile
	 * @return
	 */
	public Car getUserCarInfo(String mobile,String numberPlate){
		try {
			if(StringUtil.isBlank(mobile)) return null;
			
			String url = "http://192.168.219/user/getLoginInfoByLoginName.html?loginName="+mobile+"&sourceOfCertification=Z";
			String result = HttpRequest.sendGet(url,10000);
			
			if(StringUtil.isBlank(result)) return null;
			if(StringUtil.isBlank(numberPlate)) return null;
			
			UserCarInfo userCarInfo = GsonUtil.fromJson(result, UserCarInfo.class);
			if(null == userCarInfo) return null;
			
			if(!"0000".equals(userCarInfo.getCode())){
				return null;
			}
			List<Car> cars = userCarInfo.getData().getCars();
			for (Car car : cars) {
				if(numberPlate.equals(car.getMyNumberPlate()))
					return car;
			}
		} catch (Exception e) {
			logger.error("getUserCarInfo 异常 ,mobile="+mobile+",numberPlate="+numberPlate);
		}
		logger.info("查询个人车辆信息,无法匹配到违法车辆，mobile="+mobile+",numberPlate="+numberPlate);
		return null;
	}
}
