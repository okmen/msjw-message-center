package cn.message.utils.dispatch.executor;

import java.util.Date;

import cn.message.bean.WechatPostMessageModel;
import cn.message.bean.message.IMessage;
import cn.message.bean.message.response.BaseResponseMessage;

public abstract class AbstractGeneralExecutor{

	/**
	 * 消息处理执行
	 * @param model
	 */
	public IMessage invoke(WechatPostMessageModel model){
		BaseResponseMessage responseMessage = execute(model);
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
	protected abstract BaseResponseMessage execute(WechatPostMessageModel model);
}
