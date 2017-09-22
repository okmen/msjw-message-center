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
import cn.message.bean.UserBind;
import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.dao.IMessageDao;
import cn.message.model.TemplateMessageModel;
import cn.message.model.UserCarInfo.Data.Car;
import cn.message.model.wechat.TemplateDataModel;
import cn.message.model.wechat.TemplateDataModel.Property;
import cn.message.model.wechat.TemplateDataModel.WechatTemlate1;
import cn.message.utils.EncodeUtil;
import cn.message.utils.GsonUtil;
import cn.message.utils.wechat.WebService4Wechat;
import cn.sdk.util.DateUtil;
import cn.sdk.util.StringUtil;

public class WechatHmdahsJ1 extends BaseHmdahsJ1{

	/**
	 * 待发送
	 */
	public static final int STATE_0 = 0;
	
	/**
	 * 已发送,手机号码无法在用户表中匹配openId
	 */
	public static final int STATE_1 = 1;
	
	/**
	 * 已发送,精准推送 message 内容无法匹配模板规则
	 */
	public static final int STATE_2 = 2;
	
	/**
	 * 发送完成,发送结果需查看send_message_record
	 */
	public static final int STATE_3 = 3;
	
	/**
	 * 类型 微信
	 */
	public static final String TYPE = "w";
	
	/**
	 * 违法推送 template_id
	 */
	protected static final String WF_TEMPLATE_ID = "Cwi_5FWbVmJd5faWECiG7clOt4gts6hOxRHO8w4fdMU";
	
	/**
	 * 精准推送template_id
	 */
	protected static final String JZ_TEMPLATE_ID = "Cwi_5FWbVmJd5faWECiG7clOt4gts6hOxRHO8w4fdMU";
	
	/**
	 * 违法查询链接
	 */
	protected static final String ILLEGAL_URL = "http://gzh.stc.gov.cn/h5/#/newqueryByCar_manual";
	
	
	public WechatHmdahsJ1(IMessageDao messageDao,
			IMessageCachedImpl iMessageCached) {
		super(messageDao, iMessageCached);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 发送模板消息
	 * @param model
	 * @return
	 */
	public String sendMessage4Hmdahs(TemplateDataModel model) {
		try {
			String json = WebService4Wechat.sendTemplateMessage(iMessageCached.getAccessToken(),model);
			logger.info("微信模板消息发送结果:"+json);
			TemplateMessageModel result = GsonUtil.fromJson(json, TemplateMessageModel.class);
			if(null != result){ 
				int errcode = result.getErrcode();
				
				//token失效返回值 重新获取token
				if(40001 == errcode){
					 logger.info("返回值40001,重新获取token并重发该条记录");
					 String newToken = iMessageCached.initTokenAndTicket();
					 json = WebService4Wechat.sendTemplateMessage(newToken,model);
				}
				return json;
			}
		} catch (Exception e) {
			logger.error("发送模板消息异常:"+model.toString(),e);
		}
		return "";
	}


	/**
	 * 分批次获取用户数据
	 * @param list
	 * @param userBindSet
	 * @param begin
	 * @param total
	 */
	public void getUserBindsByIdCards(List<HmdahsJ1> list,Set<UserBind> userBindSet,List<String> mobiles) throws Exception{
		try {
			//根据手机号码查找用户
			List<UserBind> userBinds = messageDao.queryUserBindByMobile(mobiles);
			if(null != userBinds && 0 != userBinds.size()){
				for (UserBind userBind : userBinds) {
					userBindSet.add(userBind);
				}
			} 
		} catch (Exception e) {
			logger.error("getUserBindsByIdCards 异常,mobiles="+mobiles,e);
			throw e;
		}
	}
	
	/**
	 * 根据HmdahsJ1的数据 取出所有相关联的用户（手机号码关联） 
	 * @param list
	 * @return
	 */
	public Set<UserBind> getAllUserBinds(List<HmdahsJ1> list) throws Exception{
		//构建一个set集合 根据openId排重重复的用户数据 然后 根据 警示通返回的身份证号码去我们自己数据库查询用户数据 
		Set<UserBind> userBindSet = new HashSet<UserBind>();
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
				getUserBindsByIdCards(list,userBindSet, mobiles);
				break;
			}
			
			//100取模
			if (i % 100 == 0) {
				getUserBindsByIdCards(list,userBindSet, mobiles);  
				mobiles = new ArrayList<String>();
			}
		}
		return userBindSet;
	}
	
	/**
	 * 分组userBind数据
	 * @param userBindSet
	 * @return
	 */
	public Map<String, List<UserBind>> group(Set<UserBind> userBindSet){
		Map<String, List<UserBind>> userBindMap = new HashMap<String, List<UserBind>>();
		if(null == userBindSet || 0 == userBindSet.size()) return userBindMap;
		
		Iterator it = userBindSet.iterator();
		while(it.hasNext()){
			UserBind userBind = (UserBind)it.next();
			String k = userBind.getMobileNumber();
			if(userBindMap.containsKey(k)){
				userBindMap.get(k).add(userBind);
				continue;
			}
			//第一次添加到map中  创建新list 并将用户对象存入
			List<UserBind> tempList = new ArrayList<UserBind>();
			tempList.add(userBind);
			userBindMap.put(k, tempList);
		}
		return userBindMap;
	}
	
	/**
	 * 组装模板消息数据对象
	 * @param openId
	 * @param message
	 * @return
	 */
	public TemplateDataModel getTemplateModel(String openId,String message,String hslx,String mobile,String hphm){
		//hslx 为4表示是违法推送 需要增加url
		if(HSLX_4.equals(hslx)){
			return getWfModel(openId, message, mobile, hphm);
		}
		return getJzModel(openId, message, hslx);
	}

	/**
	 * 获取违法推送url
	 * @param mobile
	 * @param hphm
	 * @return
	 */
	public String getTemplateUrl(String mobile,String hphm){
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
	 * @param openId
	 * @param message
	 * @param hslx
	 * @return
	 */
	private TemplateDataModel getJzModel(String openId, String message,
			String hslx) {
		TemplateDataModel model = new TemplateDataModel();
		model.setTouser(openId);
		model.setTemplate_id(JZ_TEMPLATE_ID);
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
		map.put("first", new TemplateDataModel().new Property(message, ""));
		map.put("keyword1", new TemplateDataModel().new Property(wechatTemlate1.getFirst(), ""));
		map.put("keyword2", new TemplateDataModel().new Property(DateUtil.format(new Date(), new SimpleDateFormat("yyyy年MM月dd日")), ""));
		map.put("remark", new TemplateDataModel().new Property(wechatTemlate1.getKeyword2(), ""));
		model.setData(map);
		return model;
	}

	/**
	 * 获取违法推送模板对象
	 * @param openId
	 * @param message
	 * @param mobile
	 * @param hphm
	 * @return
	 */
	private TemplateDataModel getWfModel(String openId, String message,
			String mobile, String hphm) {
		TemplateDataModel model = new TemplateDataModel();
		model.setTouser(openId);
		model.setTemplate_id(WF_TEMPLATE_ID);
		String url = getTemplateUrl(mobile, hphm);
		if(StringUtil.isNotBlank(url))
			model.setUrl(url);
		
		Map<String, Property> map = new HashMap<String, Property>();
		map.put("first", new TemplateDataModel().new Property(message, ""));
		map.put("keyword1", new TemplateDataModel().new Property("交通违法信息通知", ""));
		map.put("keyword2", new TemplateDataModel().new Property(DateUtil.format(new Date(), new SimpleDateFormat("yyyy年MM月dd日")), ""));
		map.put("remark", new TemplateDataModel().new Property("在线办理,请点击详情", ""));
		model.setData(map);
		return model;
	}
}
