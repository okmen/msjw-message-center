package cn.message.utils.alipay.dispatch;

import cn.message.model.alipay.message.IEvent;
import cn.message.utils.alipay.dispatch.executor.AbstractGeneralExecutor;
import cn.message.utils.alipay.dispatch.executor.event.EventClickExecutor;

/**
 * 事件消息分发器
 * 
 * @author gaoxigang
 * 
 */
public class EventDispatch {
	/**
	 * 分发
	 * @param eventType
	 * @return
	 */
	public static AbstractGeneralExecutor dispatch(String eventType) {

		if (IEvent.EVENT_TYPE_CLICK.equals(eventType)) {
			return new EventClickExecutor();
		}
		
		return null;
	}
}
