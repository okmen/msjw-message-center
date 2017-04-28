package cn.message.utils.alipay.dispatch;

import cn.message.model.alipay.message.IMessage;
import cn.message.utils.alipay.dispatch.executor.AbstractGeneralExecutor;
import cn.message.utils.alipay.dispatch.executor.TextMessageExecutor;

/**
 * 分发器
 * @author gaoxigang
 *
 */
public class MessageDispatch {
	
	/**
	 * 分发
	 * @param msgType
	 * @param eventType
	 * @return
	 */
	public static AbstractGeneralExecutor dispatch(String msgType,String eventType) {
		
		if(IMessage.MESSAGE_TYPE_TEXT.equals(msgType)){
			return new TextMessageExecutor();
		}
		
		if(IMessage.MESSAGE_TYPE_EVENT.equals(msgType)){
			return EventDispatch.dispatch(eventType);
		}
		return null;
	}
}
