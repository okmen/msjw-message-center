package cn.message.service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSON;

import cn.message.cached.impl.IMessageCachedImpl;
import cn.message.config.IConfig;
import cn.message.model.MsgChannelResultModel;
import cn.message.model.wechat.MessageChannelModel;
import cn.message.utils.GsonUtil;
import cn.message.utils.wechat.WebService4Wechat;
import cn.sdk.bean.BaseBean;
import cn.sdk.cache.ICacheManger;
import cn.sdk.util.DateUtil2;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:junit-test.xml" })
public class TestMessageService {
	
	private static Logger logger = Logger.getLogger(TestMessageService.class);
	
	@Autowired
	private IMobileMessageService mobileMessageService;
	@Autowired
    @Qualifier("templateMessageService")
	private ITemplateMessageService templateMessageService;
	@Autowired
	private IMessageCachedImpl iMessageCached;
	@Test
	public void sendMessage(){
		mobileMessageService.sendMessage("13652311206", "test");
	}
	
	@Test
	public void testChannel(){
		MessageChannelModel model = new MessageChannelModel();
		
		/*model.setOpenid("oPyqQjpxF91kxJUg-jTuKrpZ62CY");
		model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbug42B1mdjrfnGyqfgbmMb8");
		model.setResult_page_style_id("4P3yuc5LgEgbuQ6w2ZEZzZw0J4Cpz8_qtEszelOARpU");
		model.setDeal_msg_style_id("4P3yuc5LgEgbuQ6w2ZEZzbEZz3IWDGV7iJiPSpYQCDw");
		model.setCard_style_id("");
		model.setOrder_no("888888");
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=2&title=createVehicleInfo_JD33&waterNumber=888888&orgName=深圳市车管所&orgAddr=深圳市南山区龙井路128号&appointmentDate=2017-09-06&appointmentTime=12:00-17:00&name=开发测试");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，您的业务办理预约申请已成功提交，具体信息如下。","#212121"));
		map.put("business", new MessageChannelModel().new Property("机动车在线预约-档案更正","#212121"));
		map.put("order", new MessageChannelModel().new Property("888888","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-09 12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("深圳市车管所","#212121"));
		map.put("remark", new MessageChannelModel().new Property("请您持身份证及业务办理所需材料在预约办理时间段内完成取号，不能办理业务请及时取消。","#212121"));
		model.setData(map);*/
		
		/*model.setOpenid("oPyqQjpxF91kxJUg-jTuKrpZ62CY");
		model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbv3QTn_7dV2OuKaPDHYBrNQ");
		model.setResult_page_style_id("4P3yuc5LgEgbuQ6w2ZEZzZw0J4Cpz8_qtEszelOARpU");
		model.setDeal_msg_style_id("4P3yuc5LgEgbuQ6w2ZEZzbEZz3IWDGV7iJiPSpYQCDw");
		model.setCard_style_id("");
		model.setOrder_no("L12345678");
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=2&title=illegalAppointment&waterNumber=L12345678&orgName=龙华交警大队违法处理点&serviceCall=13800138000&orgAddr=龙华新区大道创业花园向荣大厦188栋&appointmentDate=2017-09-21&appointmentTime=12:00-17:00");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，您的业务办理预约申请已成功提交，具体信息如下。","#212121"));
		map.put("business", new MessageChannelModel().new Property("交通违法办理-违法处理预约","#212121"));
		map.put("order", new MessageChannelModel().new Property("L12345678","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-21" + " " + "12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("龙华交警大队违法处理点","#212121"));
		map.put("remark", new MessageChannelModel().new Property("请您持身份证及业务办理所需材料在预约办理时间段内完成取号，不能办理业务请及时取消。","#212121"));
		model.setData(map);*/
		
		model.setOpenid("oPyqQjpxF91kxJUg-jTuKrpZ62CY");
		model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbmdPgvKIb6VMfR1zxNIe_fw");
		model.setResult_page_style_id("PMw9-nhDOOQuMzL7-cVZ3DqyaaLEvpIWsopaXE1qvC0");
		model.setDeal_msg_style_id("PMw9-nhDOOQuMzL7-cVZ3CZoVDr0ojGdWvwZf7SZK6A");
		model.setCard_style_id("");
		model.setOrder_no("L12345678");
		model.setUrl("");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，您的预约申请已取消，具体信息如下","#212121"));
		map.put("businessType", new MessageChannelModel().new Property("交通违法办理","#212121"));
		map.put("business", new MessageChannelModel().new Property("违法处理预约","#212121"));
		map.put("order", new MessageChannelModel().new Property("L12345678","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-21" + " " + "12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("龙华交警大队违法处理点","#212121"));
		map.put("remark", new MessageChannelModel().new Property("","#212121"));
		model.setData(map);
		
		BaseBean baseBean = templateMessageService.sendServiceMessage(model);
		System.out.println("发送结果："+JSON.toJSONString(baseBean));
	}
	
	@Test
	public void testMsgChannel(){
		//改成自己的openid
		String openid = "oPyqQjpxF91kxJUg-jTuKrpZ62CY";
		/*boolean isFollow = judgeIsFollow(token, openid);
		System.out.println(isFollow);*/
		MessageChannelModel model = new MessageChannelModel();
		model.setOpenid(openid);
		
		
		//驾驶证-业务办理通知
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbn3H9wpHz8dKjXPL9J_xC5s");
		model.setResult_page_style_id("23ClyLHM5Fr790uz7t-fxiodPnL9ohRzcnlGWEudkL8");
		model.setDeal_msg_style_id("23ClyLHM5Fr790uz7t-fxlzJePTelFGvOKtKR4udm1o");
		model.setCard_style_id("");
		model.setOrder_no("123123123");
		String dateStr = DateUtil2.date2str(new Date());
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=1&title=repairDriverLicense&waterNumber=123123123&bidDate=" + dateStr);
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的业务办理申请已成功提交，具体信息如下。","#212121"));
		map.put("keyword1", new MessageChannelModel().new Property(dateStr,"#212121"));
		map.put("keyword2", new MessageChannelModel().new Property("驾驶证补证申请","#212121"));
		map.put("keyword3", new MessageChannelModel().new Property("待受理","#212121"));
		map.put("remark", new MessageChannelModel().new Property("","#212121"));
		model.setData(map);*/
		
		//驾驶证-预约成功提醒
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbsPL8FV6yk3G67Y4HQKo3tA");
		model.setResult_page_style_id("4P3yuc5LgEgbuQ6w2ZEZzZw0J4Cpz8_qtEszelOARpU");
		model.setDeal_msg_style_id("4P3yuc5LgEgbuQ6w2ZEZzbEZz3IWDGV7iJiPSpYQCDw");
		model.setCard_style_id("");
		model.setOrder_no("1234567890");
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=2&title=createDriveInfo_ZJ11&waterNumber=1234567890&orgName=深圳市车管所&orgAddr=深圳市南山区龙井路128号&appointmentDate=2017-09-06&appointmentTime=12:00-17:00&name=开发测试");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的业务办理预约申请已成功提交，具体信息如下。","#212121"));
		map.put("business", new MessageChannelModel().new Property("驾驶证业务-部队车驾驶证申领","#212121"));
		map.put("order", new MessageChannelModel().new Property("1234567890","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-06 12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("深圳市车管所","#212121"));
		map.put("remark", new MessageChannelModel().new Property("请您持身份证及业务办理所需材料在预约办理时间段内完成取号，不能办理业务请及时取消。","#212121"));
		model.setData(map);*/
		
		//驾驶证-取消预约提醒
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbhCx1GDBVM-D9RlY_RkA01E");
		model.setResult_page_style_id("PMw9-nhDOOQuMzL7-cVZ3DqyaaLEvpIWsopaXE1qvC0");
		model.setDeal_msg_style_id("PMw9-nhDOOQuMzL7-cVZ3CZoVDr0ojGdWvwZf7SZK6A");
		model.setCard_style_id("");
		model.setOrder_no("1234567890");
		model.setUrl("");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的预约申请已取消，具体信息如下","#212121"));
		map.put("businessType", new MessageChannelModel().new Property("驾驶证业务","#212121"));
		map.put("business", new MessageChannelModel().new Property("部队车驾驶证申领","#212121"));
		map.put("order", new MessageChannelModel().new Property("1234567890","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-06 12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("深圳市车管所","#212121"));
		map.put("remark", new MessageChannelModel().new Property("","#212121"));
		model.setData(map);*/
		
		//机动车-预约成功提醒
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbug42B1mdjrfnGyqfgbmMb8");
		model.setResult_page_style_id("4P3yuc5LgEgbuQ6w2ZEZzZw0J4Cpz8_qtEszelOARpU");
		model.setDeal_msg_style_id("4P3yuc5LgEgbuQ6w2ZEZzbEZz3IWDGV7iJiPSpYQCDw");
		model.setCard_style_id("");
		model.setOrder_no("13800138000");
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=2&title=createVehicleInfo_JD17&waterNumber=13800138000&orgName=深圳市车管所&orgAddr=深圳市南山区龙井路128号&appointmentDate=2017-09-06&appointmentTime=12:00-17:00&name=开发测试");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的业务办理预约申请已成功提交，具体信息如下。","#212121"));
		map.put("business", new MessageChannelModel().new Property("机动车业务-机动车注册登记","#212121"));
		map.put("order", new MessageChannelModel().new Property("13800138000","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-06 12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("深圳市车管所","#212121"));
		map.put("remark", new MessageChannelModel().new Property("请您持身份证及业务办理所需材料在预约办理时间段内完成取号，不能办理业务请及时取消。","#212121"));
		model.setData(map);*/
		
		//机动车-取消预约提醒
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIblH0oc9K9CXnemA0qAazPdI");
		model.setResult_page_style_id("PMw9-nhDOOQuMzL7-cVZ3DqyaaLEvpIWsopaXE1qvC0");
		model.setDeal_msg_style_id("PMw9-nhDOOQuMzL7-cVZ3CZoVDr0ojGdWvwZf7SZK6A");
		model.setCard_style_id("");
		model.setOrder_no("13800138000");
		model.setUrl("");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的预约申请已取消，具体信息如下","#212121"));
		map.put("businessType", new MessageChannelModel().new Property("机动车业务","#212121"));
		map.put("business", new MessageChannelModel().new Property("机动车注册登记","#212121"));
		map.put("order", new MessageChannelModel().new Property("13800138000","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-06 12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("深圳市车管所","#212121"));
		map.put("remark", new MessageChannelModel().new Property("","#212121"));
		model.setData(map);*/
		
		//违法-业务办理通知
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbn3H9wpHz8dKjXPL9J_xC5s");
		model.setResult_page_style_id("23ClyLHM5Fr790uz7t-fxiodPnL9ohRzcnlGWEudkL8");
		model.setDeal_msg_style_id("23ClyLHM5Fr790uz7t-fxlzJePTelFGvOKtKR4udm1o");
		model.setCard_style_id("");
		model.setOrder_no("123123123");
		String dateStr = DateUtil2.date2str(new Date());
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=1&title=repairDriverLicense&waterNumber=123123123&bidDate=" + dateStr);
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的业务办理申请已成功提交，具体信息如下。","#212121"));
		map.put("keyword1", new MessageChannelModel().new Property(dateStr,"#212121"));
		map.put("keyword2", new MessageChannelModel().new Property("驾驶证补证申请","#212121"));
		map.put("keyword3", new MessageChannelModel().new Property("待受理","#212121"));
		map.put("remark", new MessageChannelModel().new Property("","#212121"));
		model.setData(map);*/
		
		//交通违法办理-预约成功提醒
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbv3QTn_7dV2OuKaPDHYBrNQ");
		model.setResult_page_style_id("4P3yuc5LgEgbuQ6w2ZEZzZw0J4Cpz8_qtEszelOARpU");
		model.setDeal_msg_style_id("4P3yuc5LgEgbuQ6w2ZEZzbEZz3IWDGV7iJiPSpYQCDw");
		model.setCard_style_id("");
		model.setOrder_no("L123456");
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=2&title=illegalAppointment&waterNumber=L123456&orgName=龙华交警大队违法处理点&serviceCall=13800138000&orgAddr=龙华新区大道创业花园向荣大厦188栋&appointmentDate=2017-09-21&appointmentTime=12:00-17:00");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的业务办理预约申请已成功提交，具体信息如下。","#212121"));
		map.put("business", new MessageChannelModel().new Property("交通违法办理-违法处理预约","#212121"));
		map.put("order", new MessageChannelModel().new Property("L123456","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-13 12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("龙华交警大队违法处理点","#212121"));
		map.put("remark", new MessageChannelModel().new Property("请您持身份证及业务办理所需材料在预约办理时间段内完成取号，不能办理业务请及时取消。","#212121"));
		model.setData(map);*/
		
		//交通违法办理-取消预约提醒
		/*model.setBiz_template_id("s4ia2sLd4C-0IpkLLbGIbmdPgvKIb6VMfR1zxNIe_fw");
		model.setResult_page_style_id("PMw9-nhDOOQuMzL7-cVZ3DqyaaLEvpIWsopaXE1qvC0");
		model.setDeal_msg_style_id("PMw9-nhDOOQuMzL7-cVZ3CZoVDr0ojGdWvwZf7SZK6A");
		model.setCard_style_id("");
		model.setOrder_no("123456");
		model.setUrl("");
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试，您的预约申请已取消，具体信息如下","#212121"));
		map.put("businessType", new MessageChannelModel().new Property("交通违法办理","#212121"));
		map.put("business", new MessageChannelModel().new Property("交通违法办理预约","#212121"));
		map.put("order", new MessageChannelModel().new Property("123456","#212121"));
		map.put("time", new MessageChannelModel().new Property("2017-09-06 12:00-17:00","#212121"));
		map.put("address", new MessageChannelModel().new Property("深圳市车管所","#212121"));
		map.put("remark", new MessageChannelModel().new Property("","#212121"));
		model.setData(map);*/
		
		//随手拍举报-业务办理通知
		model.setBiz_template_id("z0HL_wBSsoF7AR42tGzuFMalEi3RKowXmg9nKNTC0BM");
		model.setResult_page_style_id("23ClyLHM5Fr790uz7t-fxiodPnL9ohRzcnlGWEudkL8");
		model.setDeal_msg_style_id("23ClyLHM5Fr790uz7t-fxlzJePTelFGvOKtKR4udm1o");
		model.setCard_style_id("");
		model.setOrder_no("rz123456");
		String dateStr = DateUtil2.date2str(new Date());
		model.setUrl("http://gzh.stc.gov.cn/h5/#/submitSuccess?type=1&title=starUserAuth&waterNumber=rz123456&bidDate=" + dateStr);
		Map<String, cn.message.model.wechat.MessageChannelModel.Property> map = new HashMap<String, cn.message.model.wechat.MessageChannelModel.Property>();
		map.put("first", new MessageChannelModel().new Property("您好，开发测试666，您的业务办理申请已成功提交，具体信息如下。","#212121"));
		map.put("keyword1", new MessageChannelModel().new Property(dateStr,"#212121"));
		map.put("keyword2", new MessageChannelModel().new Property("星级用户认证","#212121"));
		map.put("keyword3", new MessageChannelModel().new Property("待初审","#212121"));
		map.put("remark", new MessageChannelModel().new Property("","#212121"));
		model.setData(map);
		
		sendServiceMessage(model);
	}
	
	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private ICacheManger<String> cacheManger;
	
	public String getAccessToken() {
		String token = cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
		if(null == token || "".equals(token)){
			initTokenAndTicket();
		}
		return cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
	}
	/**
	 * 从微信段获取 token 和 ticket
	 * @return
	 */
	public String initTokenAndTicket() {
		try {
			Map<String, Object> map = WebService4Wechat.getAccessToken("wxc2b699cf2f919b58", "cbfabef2717b14e416de93dd83498862");
			Object token = map.get("access_token");
			if(null != token && !"".equals(token)){
				//存入redis
				cacheManger.set(IConfig.ACCESS_TOKEN_REDIS, token.toString(), 3600);
				logger.info("获取新token:"+token);
				Map<String, Object> jsapMap = WebService4Wechat.getJsapiTicket(token.toString());
				Object ticket = jsapMap.get("ticket");
				if(null != ticket && !"".equals(ticket)){
					cacheManger.set(IConfig.TICKET_REDIS, ticket.toString(), 3600);
					logger.info("获取新jsapi ticket:"+ticket);
				}
				
				Map<String, Object> apiMap = WebService4Wechat.getApiTicket(token.toString());
				Object apiTicket = apiMap.get("ticket");
				if(null != apiTicket && !"".equals(apiTicket)){
					cacheManger.set(IConfig.API_TICKET_REDIS, apiTicket.toString(), 3600);
					logger.info("获取新api ticket:"+apiTicket);
				}
			}
		} catch (Exception e) {
			logger.error("获取 token and ticket失败",e);
		}
		return cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
	}
	
	/**
	 * 发送服务通知
	 */
	public boolean sendServiceMessage(MessageChannelModel model) {
		try {
			// 请求http://szjj.u-road.com/api/wechat/asdasdasdas.html?key=qxwsed@!s1334  获取token
			String token = "EVvMvkxjJFIqk9L6XPvL4qcBlibIrtEBUoPcL0DFqyeyTLAQWSJEKfwsoX7uTSxoCL4nJJ1qH6GwHEHVG1hw-7z5H_PhGuFhbK--GDu958nItk-FcbDYwXqSYq3JKsWjSHKgCGADSV";
			String json = WebService4Wechat.sendServiceMessage(token, model);
			
			logger.info("消息通路发送结果:"+json);
			MsgChannelResultModel result = GsonUtil.fromJson(json, MsgChannelResultModel.class);
			if(null != result){ 
				int errcode = result.getErrcode();
				if(0 == errcode){
					return true;
				}
				
				//token失效返回值 重新获取token
				if(40001 == errcode){
					 logger.info("返回值40001,重新获取token并重发该条记录");
					 String newToken = initTokenAndTicket();
					 json = WebService4Wechat.sendServiceMessage(newToken, model);
					 logger.info("重发返回值："+json);
					 result = GsonUtil.fromJson(json, MsgChannelResultModel.class);
					 if(null != result){ 
						errcode = result.getErrcode();
						if(0 == errcode){
							return true;
						}
					 }
					 return false;
				}
			}
		} catch (Exception e) {
			logger.error("发送模板消息异常:"+"openId="+model.getOpenid()+",templateId="+model.getBiz_template_id()+",map="+model.getData(),e);
		}
		return false;
	}
}
