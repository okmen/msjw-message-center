package cn.message.utils.dispatch;
import cn.message.bean.message.IMessage;
import cn.message.utils.dispatch.executor.AbstractGeneralExecutor;
import cn.message.utils.dispatch.executor.EventMessageExecutor;
import cn.message.utils.dispatch.executor.TextMessageExecutor;

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
		
		if (msgType.equals(IMessage.MESSAGE_TYPE_TEXT)) {
			return new TextMessageExecutor();
		}

		if (msgType.equals(IMessage.MESSAGE_TYPE_EVENT)) {
			return new EventMessageExecutor();
		}
		return null;
	}
}
