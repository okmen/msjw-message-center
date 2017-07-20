package cn.message.utils.wechat.dispatch.executor;

import java.util.Date;

import cn.message.dao.IMessageDao;
import cn.message.model.wechat.WechatPostMessageModel;
import cn.message.model.wechat.message.IMessage;
import cn.message.model.wechat.message.response.BaseMessage;

public abstract class AbstractGeneralExecutor{

	/**
	 * 消息处理执行
	 * @param model
	 */
	public IMessage invoke(WechatPostMessageModel model,IMessageDao iMessageDao){
		BaseMessage responseMessage = execute(model,iMessageDao);
		if(null == responseMessage)  return null;
		//用户微信号
		String fromUserName = model.getFromUserName();
		// 开发者微信号
		String toUserName = model.getToUserName();
		responseMessage.setFromUserName(toUserName);
		responseMessage.setToUserName(fromUserName);
		responseMessage.setCreateTime(new Date().getTime());
		return responseMessage;
	}
	
	/**
	 * 消息处理实现
	 * @param model
	 */
	protected abstract BaseMessage execute(WechatPostMessageModel model,IMessageDao iMessageDao);
}
