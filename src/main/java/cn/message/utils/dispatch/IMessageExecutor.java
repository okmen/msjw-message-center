package cn.message.utils.dispatch;

import cn.message.bean.WechatPostMessageModel;
import cn.message.bean.message.IMessage;

public interface IMessageExecutor {
	/**
	 * 执行消息处理
	 * @param model
	 */
	public IMessage execute(WechatPostMessageModel model);
}
