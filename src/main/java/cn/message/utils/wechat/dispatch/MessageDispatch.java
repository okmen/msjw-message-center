package cn.message.utils.wechat.dispatch;
import cn.message.model.wechat.message.IMessage;
import cn.message.utils.wechat.dispatch.executor.AbstractGeneralExecutor;
import cn.message.utils.wechat.dispatch.executor.EventMessageExecutor;
import cn.message.utils.wechat.dispatch.executor.TextMessageExecutor;

/**
 * message 转发器
 * 
 * @author gaoxigang
 * 
 */
public class MessageDispatch {

	/**
	 * 分发执行器
	 * @param msgType
	 * @return
	 */
	public static AbstractGeneralExecutor dispatch(String msgType) {
		
		if (IMessage.MESSAGE_TYPE_TEXT.equals(msgType)) {
			return new TextMessageExecutor();
		}

		if (IMessage.MESSAGE_TYPE_EVENT.equals(msgType)) {
			return new EventMessageExecutor();
		}
		return null;
	}
}
