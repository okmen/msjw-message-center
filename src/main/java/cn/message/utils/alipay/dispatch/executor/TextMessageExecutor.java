package cn.message.utils.alipay.dispatch.executor;

import cn.message.model.KeyWord;
import cn.message.model.alipay.AlipayPostMessageModel;
import cn.message.model.alipay.message.response.BaseMessage;
import cn.message.model.alipay.message.response.ImageTextMessage;

/**
 * 文本消息处理器
 * 
 * @author gaoxigang
 * 
 */
public class TextMessageExecutor extends AbstractGeneralExecutor {

	@Override
	public BaseMessage execute(AlipayPostMessageModel model) {
		BaseMessage message = null;
		String content = model.getContent();
		if (KeyWord.KEYWORD_SZ.equals(content.trim())) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] { new ImageTextMessage.ImageText(
							"立即查看",
							"",
							"http://szjj.u-road.com/szjj/assets/images/conference/1.jpg",
							"材料一 ▍ 深圳，改革开放的前沿",
							"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213953035&idx=1&sn=e6338f3fb27a00fb3b3c4446d16fa92e&scene=1&srcid=0909O5806wQ56HEHfGYkDxIs&from=singlemessage&isappinstalled=0#rd") });
		}

		if (KeyWord.KEYWORD_SZJJ.equals(content.trim())) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] { new ImageTextMessage.ImageText(
							"立即查看",
							"",
							"http://szjj.u-road.com/szjj/assets/images/conference/2.jpg",
							"材料二 ▍ 深圳交警：2200人队伍、6500公里道路、320万辆车",
							"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213959266&idx=1&sn=5dd757eb432a12afe782a64a6cd57307&scene=1&srcid=0909nDN7yYsMkV8iNeOkDZdp&from=singlemessage&isappinstalled=0#rd") });
		}

		if (KeyWord.KEYWORD_XJYH.equals(content.trim())) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] { new ImageTextMessage.ImageText(
							"立即查看",
							"",
							"http://szjj.u-road.com/szjj/assets/images/conference/3.jpg",
							"材料三 ▍ 注册星级用户，认证手机号码，尽享交警掌上便利服务",
							"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213965708&idx=1&sn=4bed9b51edd5dcfc6b82862579693960&scene=1&srcid=09091ofOOIfzTMtXtJU9tEC7&from=singlemessage&isappinstalled=0#rd") });
		}

		if (KeyWord.KEYWORD_TX.equals(content.trim())) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] { new ImageTextMessage.ImageText(
							"立即查看",
							"",
							"http://szjj.u-road.com/szjj/assets/images/conference/4.jpg",
							"材料四 ▍ 微信“互联网+交管”解决方案",
							"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213927506&idx=1&sn=0028358cf0c580d64a7deec8f29be3ac&scene=1&srcid=0909OYaFJoTgzOTWhAsFbcHl&from=singlemessage&isappinstalled=0#rd") });
		}

		if (KeyWord.KEYWORD_HW.equals(content.trim())) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] { new ImageTextMessage.ImageText(
							"立即查看",
							"",
							"http://szjj.u-road.com/szjj/assets/images/conference/5.jpg",
							"材料五 ▍ 华为，中国通信行业的龙头",
							"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213961974&idx=1&sn=9ca231f6190d6b99f2cb52f62bc091ea&scene=1&srcid=0909vfdQh0TEOI2uqkmZmdfG&from=singlemessage&isappinstalled=0#rd") });
		}

		if (KeyWord.KEYWORD_SP.equals(content.trim())) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] { new ImageTextMessage.ImageText(
							"立即查看",
							"",
							"http://szjj.u-road.com/szjj/assets/images/conference/6.jpg",
							"材料六 ▍ “互联网+”时代的深圳交通管理",
							"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213970033&idx=1&sn=d251e116480fa1fc0f81a78c0bc297ae&scene=1&srcid=0909PANCV9meKKeyjFLKtrQM&from=singlemessage&isappinstalled=0#rd") });
		}

		if (KeyWord.KEYWORD_XCDCWJ.equals(content.trim())) {
			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] {
							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/conference/1.pic_hd.jpg",
									"",
									"http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213970033&idx=1&sn=d251e116480fa1fc0f81a78c0bc297ae&scene=1&srcid=0909PANCV9meKKeyjFLKtrQM&from=singlemessage&isappinstalled=0#rd"),

							new ImageTextMessage.ImageText(
									"立即查看",
									"",
									"http://szjj.u-road.com/szjj/assets/images/conference/2.pic_hd.jpg",
									"问卷调查",
									"http://www.diaochapai.com/survey1688778") });
		}

		if (KeyWord.KEYWORD_DYZP.equals(content.trim())) {

			message = new ImageTextMessage(
					new ImageTextMessage.ImageText[] { new ImageTextMessage.ImageText(
							"立即查看",
							"",
							"http://szjj.u-road.com/szjjpro/assets/images/conference/dyzp_dcbc8ba0.jpeg",
							"深圳交警，一直在路上",
							"http://www.wechatpicture.com/server/weixinweb/dispense.do?type=webPrint&appId=wx48a8104946507c1e") });
		}
		return message;
	}
}
