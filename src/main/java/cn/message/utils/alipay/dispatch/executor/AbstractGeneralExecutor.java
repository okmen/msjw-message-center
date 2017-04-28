package cn.message.utils.alipay.dispatch.executor;

import cn.message.model.alipay.AlipayPostMessageModel;
import cn.message.model.alipay.message.IMessage;
import cn.message.model.alipay.message.response.BaseMessage;

/**
 * 处理器基类
 * @author gaoxigang
 *
 */
public abstract class AbstractGeneralExecutor{

	/**
	 * 消息处理执行
	 * @param model
	 */
	public IMessage invoke(AlipayPostMessageModel model){
		BaseMessage responseMessage = execute(model);
		if(null == responseMessage)  return null;
		// 开发者微信号
		String toUserName = model.getFromAlipayUserId();
		responseMessage.setTo_user_id(toUserName);
		return responseMessage;
	}
	
	/**
	 * 消息处理实现
	 * @param model
	 */
	protected abstract BaseMessage execute(AlipayPostMessageModel model);
}
