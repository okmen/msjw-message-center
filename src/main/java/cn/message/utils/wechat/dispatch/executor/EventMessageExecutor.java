package cn.message.utils.wechat.dispatch.executor;

import java.util.Date;

import org.apache.log4j.Logger;

import cn.message.bean.WxMembercard;
import cn.message.dao.IMessageDao;
import cn.message.model.EventKey;
import cn.message.model.wechat.WechatPostMessageModel;
import cn.message.model.wechat.message.IEvent;
import cn.message.model.wechat.message.response.BaseMessage;
import cn.message.model.wechat.message.response.NewsMessage;
import cn.sdk.util.StringUtil;

/**
 * 事件处理器
 * @author gaoxigang
 *
 */
public class EventMessageExecutor extends AbstractGeneralExecutor {
	Logger logger = Logger.getLogger(EventMessageExecutor.class);
	@Override
	public BaseMessage execute(WechatPostMessageModel model,IMessageDao iMessageDao) {
		//事件
		String event = model.getEvent();
		//事件key
		String eventKey = model.getEventKey();
		BaseMessage message = null;
		
		 if(IEvent.EVENT_TYPE_CLICK.equals(event)){
			 //便民信息
			 if(EventKey.CONVENIENCE_INFOMATION.equals(eventKey)){
				 
				 String [] titles = new String[]{"交警24小时拖车电话","车管所及各分所地点","各区扣车场地点","各区中队地点及电话"};
				 String [] descriptions = new String[]{"","","","",};
				 String [] picUrls = new String[]{"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0018_tc-75.png",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0013_xz-164-cb-6.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0015_xz-133.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0012_xz-134.jpg"};
				 /*String [] urls = new String[]{"http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/teamAddr",
						 "http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/vehicleAdrr",
						 "http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/depotAdrr",
						 "http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/lochusAdrr"};*/
				 String [] urls = new String[]{"http://gzh.stc.gov.cn/bianminh5/#/cvtInfoQuery/teamAddr",
						 "http://gzh.stc.gov.cn/bianminh5/#/cvtInfoQuery/vehicleAdrr",
						 "http://gzh.stc.gov.cn/bianminh5/#/cvtInfoQuery/depotAdrr",
				 		 "http://gzh.stc.gov.cn/bianminh5/#/cvtInfoQuery/lochusAdrr"};
				 message =  new NewsMessage(4, titles, descriptions, picUrls, urls);
			 }
			 
			// 办事指南
			 if(EventKey.SERVICE_GUIDE.equals(eventKey)){
				 String [] titles = new String[]{"深圳交警星级用户认证业务","严重交通违法举报有奖业务","车驾管业务","交通违法处理业务","道路交通事故处理业务","行政许可业务","法制业务"};
				 String [] descriptions = new String[]{"","","","","","",""};
				 String [] picUrls = new String[]{"http://szjj.u-road.com/szjjpro/assets/images/handbook/1.pic_9ccbda5.jpg",
						 "http://szjj.u-road.com/szjjpro/assets/images/handbook/reward_a2303b1.png",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0008_xz-164-cb-6.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0009_w.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0007_xz-18.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0005_z.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0006_f.jpg"};
				 
				 /*String [] urls = new String[]{"http://szjj.u-road.com/szjjpro/index.php/handbook/show",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/informantsReward",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/vehiclePilotManage",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/vehicleIllgalServices",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/roadAccidentServices",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/administrativeLicense",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/legalServices"};*/
				 String [] urls = new String[]{"http://szjj.u-road.com/szjjpro/index.php/handbook/show",
						 "http://gzh.stc.gov.cn/bianminh5/#/informantsReward",
						 "http://gzh.stc.gov.cn/bianminh5/#/vehiclePilotManage",
						 "http://gzh.stc.gov.cn/bianminh5/#/vehicleIllegal",
						 "http://gzh.stc.gov.cn/bianminh5/#/roadAccident",
						 "http://gzh.stc.gov.cn/bianminh5/#/administrativeLicense",
				 		 "http://gzh.stc.gov.cn/bianminh5/#/legalServices"};
				 message = new NewsMessage(7, titles, descriptions, picUrls, urls);
			 }
    	 }
		 
		 //领卡事件
		 if(IEvent.EVENT_USER_GET_CARD.equals(event)){
			 logger.info("【微信卡包】触发领卡事件，openId="+model.getFromUserName());
			 String openId = model.getFromUserName();
			 String cardId = model.getCardId();
			 String code = model.getCode();
			 Integer isGiveByFriend = StringUtil.isNotBlank(model.getIsGiveByFriend()) ? Integer.parseInt(model.getIsGiveByFriend()) : null;
			 String giveOpenId = model.getGiveOpenId();
			 //logger.info("用户领取卡券:code="+code);
			 WxMembercard wxMembercard = iMessageDao.selectWxMembercard(openId, cardId);
			 if(null == wxMembercard){
				 WxMembercard newWxMembercard = new WxMembercard();
				 newWxMembercard.setOpenid(openId);
				 newWxMembercard.setCode(code);
				 newWxMembercard.setCardid(cardId);
				 newWxMembercard.setIsgivebyfriend(isGiveByFriend);
				 newWxMembercard.setGiveopenid(giveOpenId);
				 newWxMembercard.setState(0);
				 newWxMembercard.setIntime(new Date());
				 int addcount = iMessageDao.insertWxMembercard(newWxMembercard);
				 logger.info("【微信卡包】新增领卡记录结果："+addcount);
			 }else{
				 Integer state = wxMembercard.getState();
				 if(state == 0){//未激活
					 logger.info("【微信卡包】已领过卡但未激活，openId="+openId+"，cardId="+cardId);
				 }else if(state == 1){//已激活
					 logger.info("【微信卡包】已激活卡，openId="+openId+"，cardId="+cardId);
				 }else if(state == 2){//已删卡
					 WxMembercard updateWxMembercard = new WxMembercard();
					 updateWxMembercard.setState(1);//修改为重新领卡激活状态state=1
					 updateWxMembercard.setIdno(wxMembercard.getIdno());
					 updateWxMembercard.setOpenid(openId);
					 updateWxMembercard.setCardid(cardId);
					 int updatecount = iMessageDao.updateWxMembercard(updateWxMembercard);
					 logger.info("【微信卡包】重新领卡修改状态结果："+updatecount);
				 }else{
					 logger.info("【微信卡包】其他卡状态，state="+state+"，openId="+openId+"，cardId="+cardId);
				 }
			 }
			 message = null;
		 }
		 
		 //删卡事件
		 if(IEvent.EVENT_USER_DEL_CARD.equals(event)){
			 logger.info("【微信卡包】触发删卡事件，openId="+model.getFromUserName());
			 String openId = model.getFromUserName();
			 String cardId = model.getCardId();
			 //更新卡状态为state=2
			 WxMembercard wxMembercard = new WxMembercard();
			 wxMembercard.setState(2);
			 String idno = iMessageDao.queryIdCardByOpenId(openId);
			 wxMembercard.setIdno(idno);
			 wxMembercard.setOpenid(openId);
			 wxMembercard.setCardid(cardId);
			 int updatecount = iMessageDao.updateWxMembercard(wxMembercard);
			 logger.info("【微信卡包】删卡修改状态结果："+updatecount);
			 message = null;
		 }
		 
    	 //用户打开公众号会推送这个包过来 (第一次推送的包)
    	 if(IEvent.EVENT_TYPE_LOCATION.equals(event)){
    		 
    	 }
    	 return message;
	}
}
