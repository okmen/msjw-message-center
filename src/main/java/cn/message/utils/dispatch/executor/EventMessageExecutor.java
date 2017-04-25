package cn.message.utils.dispatch.executor;

import cn.message.bean.WechatPostMessageModel;
import cn.message.bean.message.IMessage;
import cn.message.utils.dispatch.IMessageExecutor;

/**
 * 事件处理器
 * @author gaoxigang
 *
 */
public class EventMessageExecutor implements IMessageExecutor {

	@Override
	public IMessage execute(WechatPostMessageModel model) {
		String fromUserName = model.getFromUserName();
		// 开发者微信号
		String toUserName = model.getToUserName();
		//事件
		String event = model.getEvent();
		
		IMessage message = null;
		
		 if("CLICK".equals(event)){
			 
    	 }
		 
    	 //用户打开公众号会推送这个包过来 (第一次推送的包)
    	 if("LOCATION".equals(event)){
    		 
    	 }
    	 return message;
	}
}
