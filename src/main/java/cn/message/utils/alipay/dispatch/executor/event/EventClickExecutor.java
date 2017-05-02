package cn.message.utils.alipay.dispatch.executor.event;

import cn.message.model.EventKey;
import cn.message.model.alipay.AlipayPostMessageModel;
import cn.message.model.alipay.message.IEvent;
import cn.message.model.alipay.message.response.BaseMessage;
import cn.message.model.alipay.message.response.ImageTextMessage;
import cn.message.utils.alipay.dispatch.executor.AbstractGeneralExecutor;

/**
 * 点击事件处理器
 * @author gaoxigang
 *
 */
public class EventClickExecutor extends AbstractGeneralExecutor {

	@Override
	protected BaseMessage execute(AlipayPostMessageModel model) {
		BaseMessage message = null;
		// 事件key
		String actionParam = model.getActionParam();
		// 便民信息
		if (EventKey.CONVENIENCE_INFOMATION.equals(actionParam)) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] {
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0018_tc-75.png",
									"交警24小时拖车电话",
									"http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/teamAddr"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0013_xz-164-cb-6.jpg",
									"车管所及各分所地点",
									"http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/vehicleAdrr"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0015_xz-133.jpg",
									"各区扣车场地点",
									"http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/depotAdrr"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0012_xz-134.jpg",
									"各区中队地点及电话",
									"http://szjj.u-road.com/szjjpro/infoquery/convenientinfo/lochusAdrr") });
		}

		// 办事指南
		if (EventKey.SERVICE_GUIDE.equals(actionParam)) {
			// 这一条无法加到支付宝,微信目前支持10条 支付宝仅支持六条
			/*
			 * String [] titles = new String[]{"法制业务"}; String [] descriptions =
			 * new String[]{""}; String [] picUrls = new String[]{
			 * "http://szjj.u-road.com/szjj/assets/images/allico/szwx_0006_f.jpg"
			 * };
			 * 
			 * String [] urls = new String[]{
			 * "http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/legalServices"
			 * };
			 */
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] {
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjjpro/assets/images/handbook/1.pic_9ccbda5.jpg",
									"深圳交警星级用户认证业务",
									"http://szjj.u-road.com/szjjpro/index.php/handbook/show"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjjpro/assets/images/handbook/reward_a2303b1.png",
									"严重交通违法举报有奖业务",
									"http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/informantsReward"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0008_xz-164-cb-6.jpg",
									"车驾管业务",
									"http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/vehiclePilotManage"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0009_w.jpg",
									"交通违法处理业务",
									"http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/vehicleIllgalServices"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0007_xz-18.jpg",
									"道路交通事故处理业务",
									"http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/roadAccidentServices"),
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/allico/szwx_0005_z.jpg",
									"行政许可业务",
									"http://szjj.u-road.com/szjjpro/infoquery/lawguidinfo/administrativeLicense"), });
		}
		return message;
	}

}
