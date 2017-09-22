package cn.message.utils.hmdahs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.message.bean.HmdahsJ1;
import cn.message.bean.LoginLog;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.UserCarInfo.Data.Car;
import cn.message.model.alipay.TemplateDataAlipayModel;
import cn.message.model.alipay.TemplateDataAlipayModel.Property;
import cn.message.model.alipay.TemplateDataAlipayModel.Template;
import cn.message.model.wechat.TemplateDataModel.WechatTemlate1;
import cn.message.utils.EncodeUtil;
import cn.message.utils.alipay.AlipayAPIClientFactory;
import cn.sdk.util.DateUtil;
import cn.sdk.util.GsonBuilderUtil;
import cn.sdk.util.StringUtil;

import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayOpenPublicMessageSingleSendRequest;
import com.alipay.api.response.AlipayOpenPublicMessageSingleSendResponse;

public class AlipayHmdahsJ1 extends BaseHmdahsJ1{

	/**
	 * 待发送
	 */
	public static final int STATE_0 = 0;
	
	/**
	 * 已发送,手机号码无法在日志表中匹配支付宝userId
	 */
	public static final int STATE_1 = 1;
	
	/**
	 * 已发送,精准推送 message 内容无法匹配模板规则
	 */
	public static final int STATE_2 = 2;
	
	/**
	 * 发送完成,发送结果需查看send_message_record_alipay
	 */
	public static final int STATE_3 = 3;
	
	/**
	 * login_log 表 uid为null
	 */
	public static final int STATE_4 = 4;
	
	/**
	 * 违法推送 template_id
	 */
	protected static final String WF_TEMPLATE_ID = "62a773bd975b42e2a304ed4578e62b03";
	
	/**
	 * 精准推送template_id
	 */
	protected static final String JZ_TEMPLATE_ID = "62a773bd975b42e2a304ed4578e62b03";
	
	/**
	 * 类型 微信
	 */
	public static final String TYPE = "z";
	
	/**
	 * 违法查询链接
	 */
	protected static final String ILLEGAL_URL = "http://gzh.stc.gov.cn/alih5/#/trafficPushNews";
	
	public AlipayHmdahsJ1(IMessageDao messageDao,
			IMessageCachedImpl iMessageCached) {
		super(messageDao, iMessageCached);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 发送模板消息
	 * @param model
	 * @return
	 */
	public AlipayOpenPublicMessageSingleSendResponse sendMessage4Hmdahs(TemplateDataAlipayModel model) {
		try {
			AlipayClient alipayClient = AlipayAPIClientFactory
					.getAlipayClient();
			AlipayOpenPublicMessageSingleSendRequest request = new AlipayOpenPublicMessageSingleSendRequest();
			request.setBizContent(GsonBuilderUtil.toJson(model));
			AlipayOpenPublicMessageSingleSendResponse response = alipayClient.execute(request);
			
			return response;
		} catch (Exception e) {
			logger.error("发送模板消息异常:"+model.toString(),e);
		}
		return null;
	}


	/**
	 * 分批次获取支付宝用户数据
	 * @param list
	 * @param LoginLogSet
	 * @param begin
	 * @param total
	 */
	public void getLoginLogsByIdCards(List<HmdahsJ1> list,Set<LoginLog> LoginLogSet,List<String> mobiles) throws Exception{
		try {
			//根据手机号码查找用户
			List<LoginLog> loginLogs = messageDao.queryLoginByMobile(mobiles);
			if(null != loginLogs && 0 != loginLogs.size()){
				for (LoginLog LoginLog : loginLogs) {
					LoginLogSet.add(LoginLog);
				}
			} 
		} catch (Exception e) {
			logger.error("getLoginLogsByIdCards 异常,mobiles="+mobiles,e);
			throw e;
		}
	}
	
	/**
	 * 根据HmdahsJ1的数据 取出所有相关联的用户（手机号码关联） 
	 * @param list
	 * @return
	 */
	public Set<LoginLog> getAllLoginLogs(List<HmdahsJ1> list) throws Exception{
		//构建一个set集合 根据openId排重重复的用户数据 然后 根据 警示通返回的身份证号码去我们自己数据库查询用户数据 
		Set<LoginLog> loginLogSet = new HashSet<LoginLog>();
		List<String> mobiles = new ArrayList<String>();
		
		//把数据按100条的批次查询出openId
		for (int i = 0; i < list.size(); i++) {
			HmdahsJ1 item = list.get(i);
			
			//加入待查询集合中
			if(StringUtil.isNotBlank(item.getLxdh()))
				mobiles.add(item.getLxdh());
			
			if(0 == i) continue;
			
			//最后一个
			if(i == list.size() -1){
				getLoginLogsByIdCards(list,loginLogSet, mobiles);
				break;
			}
			
			//100取模
			if (i % 100 == 0) {
				getLoginLogsByIdCards(list,loginLogSet, mobiles);  
				mobiles = new ArrayList<String>();
			}
		}
		return loginLogSet;
	}
	
	/**
	 * 分组LoginLog数据
	 * @param LoginLogSet
	 * @return
	 */
	public Map<String, List<LoginLog>> group(Set<LoginLog> LoginLogSet){
		Map<String, List<LoginLog>> LoginLogMap = new HashMap<String, List<LoginLog>>();
		if(null == LoginLogSet || 0 == LoginLogSet.size()) return LoginLogMap;
		
		Iterator it = LoginLogSet.iterator();
		while(it.hasNext()){
			LoginLog loginLog = (LoginLog)it.next();
			String k = loginLog.getPhone();
			if(LoginLogMap.containsKey(k)){
				LoginLogMap.get(k).add(loginLog);
				continue;
			}
			//第一次添加到map中  创建新list 并将用户对象存入
			List<LoginLog> tempList = new ArrayList<LoginLog>();
			tempList.add(loginLog);
			LoginLogMap.put(k, tempList);
		}
		return LoginLogMap;
	}
	
	/**
	 * 组装模板消息数据对象
	 * @param openId
	 * @param message
	 * @return
	 */
	public TemplateDataAlipayModel getTemplateModel(String userId,String message,String hslx,String mobile,String hphm){
		
		//hslx 为4表示是违法推送 需要增加url
		if(HSLX_4.equals(hslx)){
			return getWfModel(userId, message, mobile, hphm);
		}
		
		return getJzModel(userId, message, hslx);
	}

	/**
	 * 获取违法推送url
	 * @param mobile
	 * @param hphm
	 * @return
	 */
	private String getTemplateUrl(String mobile,String hphm){
		Car car = getUserCarInfo(mobile, hphm);
		if(null != car){
			//车架号
			String digits = car.getBehindTheFrame4Digits();
			//车牌号
			String numberPlate = car.getMyNumberPlate();
			//车牌类型
			String plateType = car.getPlateType();
			
			if(StringUtil.isNotBlank(digits) && StringUtil.isNotBlank(numberPlate) && StringUtil.isNotBlank(plateType)){
				numberPlate = EncodeUtil.encodeUTF8(numberPlate);
				String url = ILLEGAL_URL +
						"?digits="+digits+
						"&numberPlate="+numberPlate+
						"&plateType="+plateType;
				
				return url;
			}
		}
		return ILLEGAL_URL;
	}
	
	/**
	 * 获取精准推送模板对象
	 * @param userId
	 * @param message
	 * @param hslx
	 * @return
	 */
	private TemplateDataAlipayModel getJzModel(String userId, String message,
			String hslx) {
		//筛选 不同 hslx 以及 不同关键字的first 和 keyword2
		WechatTemlate1 wechatTemlate1 = null;
		Map<String, WechatTemlate1> keyMap = HSLX_MAP.get(hslx);
		if(null == keyMap) return null;
		for (String key : keyMap.keySet()) {
			if(message.contains(key)){
				wechatTemlate1 = keyMap.get(key);
				break;
			}
		}
		
		if(null == wechatTemlate1){
			logger.info("hslx="+hslx +",message="+message + ",无法找到相应wechatTemlate1");
			return null;
		} 
		
		Map<String, Property> map = new HashMap<String, Property>();
		map.put("first", new Property(message, ""));
		map.put("keyword1", new Property(wechatTemlate1.getFirst(), ""));
		map.put("keyword2", new Property(DateUtil.format(new Date(), new SimpleDateFormat("yyyy年MM月dd日")), ""));
		map.put("remark", new Property(wechatTemlate1.getKeyword2(), ""));
		Template template = new Template(JZ_TEMPLATE_ID, "", "", "", map);
		TemplateDataAlipayModel alipayModel = new TemplateDataAlipayModel(userId,template);
		return alipayModel;
	}

	/**
	 * 获取违法推送模板对象
	 * @param userId
	 * @param message
	 * @param mobile
	 * @param hphm
	 * @return
	 */
	private TemplateDataAlipayModel getWfModel(String userId, String message,
			String mobile, String hphm) {
		String url = getTemplateUrl(mobile, hphm);
		
		Map<String, Property> map = new HashMap<String, Property>();
		map.put("first", new Property(message, ""));
		map.put("keyword1", new Property("交通违法信息通知", ""));
		map.put("keyword2", new Property(DateUtil.format(new Date(), new SimpleDateFormat("yyyy年MM月dd日")), ""));
		map.put("remark", new Property("在线办理,请点击详情", ""));
		Template template = new Template(WF_TEMPLATE_ID, "", url, "查看详情", map);
		TemplateDataAlipayModel alipayModel = new TemplateDataAlipayModel(userId,template);
		return alipayModel;
	}
}
