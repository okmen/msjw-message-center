package cn.message.utils.dispatch.executor;

import java.util.Date;

import org.apache.log4j.Logger;

import cn.message.bean.WechatPostMessageModel;
import cn.message.bean.message.IMessage;
import cn.message.bean.message.response.NewsResponseMessage;
import cn.message.bean.message.response.TextResponseMessage;
import cn.message.utils.dispatch.IMessageExecutor;

public class TextMessageExecutor implements IMessageExecutor {
	private Logger logger = Logger.getLogger(TextMessageExecutor.class);

	@Override
	public IMessage execute(WechatPostMessageModel model) {
		String fromUserName = model.getFromUserName();
		// 开发者微信号
		String toUserName = model.getToUserName();
		// 文本
		String content = model.getContent();

		// 返回message
		IMessage message = null;

		message = new TextResponseMessage(fromUserName, toUserName,
				new Date().getTime(), "未知关键字");

		if (IMessage.KEYWORD_SZ.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					1,
					new String[] { "材料一 ▍ 深圳，改革开放的前沿" },
					new String[] { "" },
					new String[] { "https://wallet.chudaokeji.com/szjj/assets/images/conference/1.jpg" },
					new String[] { "http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213953035&idx=1&sn=e6338f3fb27a00fb3b3c4446d16fa92e&scene=1&srcid=0909O5806wQ56HEHfGYkDxIs&from=singlemessage&isappinstalled=0#rd" });
		}

		if (IMessage.KEYWORD_SZJJ.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					1,
					new String[] { "材料二 ▍ 深圳交警：2200人队伍、6500公里道路、320万辆车" },
					new String[] { "" },
					new String[] { "https://wallet.chudaokeji.com/szjj/assets/images/conference/2.jpg" },
					new String[] { "http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213959266&idx=1&sn=5dd757eb432a12afe782a64a6cd57307&scene=1&srcid=0909nDN7yYsMkV8iNeOkDZdp&from=singlemessage&isappinstalled=0#rd" });
		}

		if (IMessage.KEYWORD_XJYH.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					1,
					new String[] { "材料三 ▍ 注册星级用户，认证手机号码，尽享交警掌上便利服务" },
					new String[] { "" },
					new String[] { "https://wallet.chudaokeji.com/szjj/assets/images/conference/3.jpg" },
					new String[] { "http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213965708&idx=1&sn=4bed9b51edd5dcfc6b82862579693960&scene=1&srcid=09091ofOOIfzTMtXtJU9tEC7&from=singlemessage&isappinstalled=0#rd" });
		}

		if (IMessage.KEYWORD_TX.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					1,
					new String[] { "材料四 ▍ 微信“互联网+交管”解决方案" },
					new String[] { "" },
					new String[] { "https://wallet.chudaokeji.com/szjj/assets/images/conference/4.jpg" },
					new String[] { "http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213927506&idx=1&sn=0028358cf0c580d64a7deec8f29be3ac&scene=1&srcid=0909OYaFJoTgzOTWhAsFbcHl&from=singlemessage&isappinstalled=0#rd" });
		}

		if (IMessage.KEYWORD_HW.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					1,
					new String[] { "材料五 ▍ 华为，中国通信行业的龙头" },
					new String[] { "" },
					new String[] { "https://wallet.chudaokeji.com/szjj/assets/images/conference/5.jpg" },
					new String[] { "http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213961974&idx=1&sn=9ca231f6190d6b99f2cb52f62bc091ea&scene=1&srcid=0909vfdQh0TEOI2uqkmZmdfG&from=singlemessage&isappinstalled=0#rd" });

		}

		if (IMessage.KEYWORD_SP.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					1,
					new String[] { "材料六 ▍ “互联网+”时代的深圳交通管理" },
					new String[] { "" },
					new String[] { "https://wallet.chudaokeji.com/szjj/assets/images/conference/6.jpg" },
					new String[] { "http://mp.weixin.qq.com/s?__biz=MjM5MTgxMjY3MQ==&mid=213970033&idx=1&sn=d251e116480fa1fc0f81a78c0bc297ae&scene=1&srcid=0909PANCV9meKKeyjFLKtrQM&from=singlemessage&isappinstalled=0#rd" });

		}

		if (IMessage.KEYWORD_XCDCWJ.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					2,
					new String[] { "", "问卷调查" },
					new String[] { "", "" },
					new String[] {
							"https://wallet.chudaokeji.com/szjj/assets/images/conference/1.pic_hd.jpg",
							"https://wallet.chudaokeji.com/szjj/assets/images/conference/2.pic_hd.jpg" },
					new String[] { "http://u.eqxiu.com/s/NWMa9cWD",
							"http://www.diaochapai.com/survey1688778" });
		}

		if (IMessage.KEYWORD_DYZP.equals(content.trim())) {
			message = new NewsResponseMessage(
					fromUserName,
					toUserName,
					new Date().getTime(),
					1,
					new String[] { "深圳交警，一直在路上" },
					new String[] { "" },
					new String[] { "https://wallet.chudaokeji.com/szjjpro/assets/images/conference/dyzp_dcbc8ba0.jpeg" },
					new String[] { "http://www.wechatpicture.com/server/weixinweb/dispense.do?type=webPrint&appId=wx48a8104946507c1e" });
		}
		return message;
	}
}
