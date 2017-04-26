package cn.message.utils.dispatch.executor;

import cn.message.bean.WechatPostMessageModel;
import cn.message.bean.message.IEvent;
import cn.message.bean.message.response.BaseResponseMessage;
import cn.message.bean.message.response.NewsResponseMessage;

/**
 * 事件处理器
 * @author gaoxigang
 *
 */
public class EventMessageExecutor extends AbstractGeneralExecutor {

	@Override
	public BaseResponseMessage execute(WechatPostMessageModel model) {
		//事件
		String event = model.getEvent();
		//事件key
		String eventKey = model.getEventKey();
		BaseResponseMessage message = null;
		
		 if(IEvent.EVENT_TYPE_CLICK.equals(event)){
			 //便民信息
			 if(IEvent.EVENT_KEY_CONVENIENCE_INFOMATION.equals(eventKey)){
				 
				 String [] titles = new String[]{"交警24小时拖车电话","车管所及各分所地点","各区扣车场地点","各区中队地点及电话"};
				 String [] descriptions = new String[]{"","","","",};
				 String [] picUrls = new String[]{"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0018_tc-75.png",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0013_xz-164-cb-6.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0015_xz-133.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0012_xz-134.jpg"};
				 String [] urls = new String[]{"http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/teamAddr",
						 "http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/vehicleAdrr",
						 "http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/depotAdrr",
						 "http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/lochusAdrr"};
				 message =  new NewsResponseMessage(4, titles, descriptions, picUrls, urls);
			 }
			 
			 //便民信息
			 if(IEvent.EVENT_KEY_SERVICE_GUIDE.equals(eventKey)){
				 String [] titles = new String[]{"深圳交警星级用户认证业务","严重交通违法举报有奖业务","车驾管业务","交通违法处理业务","道路交通事故处理业务","行政许可业务","法制业务"};
				 String [] descriptions = new String[]{"","","","","","",""};
				 String [] picUrls = new String[]{"http://szjj.u-road.com/szjjpro/assets/images/handbook/1.pic_9ccbda5.jpg",
						 "http://szjj.u-road.com/szjjpro/assets/images/handbook/reward_a2303b1.png",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0008_xz-164-cb-6.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0009_w.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0007_xz-18.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0005_z.jpg",
						 "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0006_f.jpg"};
				 
				 String [] urls = new String[]{"http://szjj.u-road.com/szjjpro/index.php/handbook/show",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/informantsReward",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/vehiclePilotManage",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/vehicleIllgalServices",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/roadAccidentServices",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/administrativeLicense",
						 "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/legalServices"};
				 message = new NewsResponseMessage(7, titles, descriptions, picUrls, urls);
			 }
    	 }
		 
    	 //用户打开公众号会推送这个包过来 (第一次推送的包)
    	 if(IEvent.EVENT_TYPE_LOCATION.equals(event)){
    		 
    	 }
    	 return message;
	}
}
