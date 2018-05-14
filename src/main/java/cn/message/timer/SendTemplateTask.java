package cn.message.timer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import cn.message.service.impl.ITemplateMessageServiceImpl;
import cn.message.utils.NetWorkIp;

/**
 * 定时发送模板消息
 * @author gaoxigang
 */
@Component
public class SendTemplateTask {
	private Logger logger = Logger.getLogger(SendTemplateTask.class);
	
	@Value("${runTimerIp}")
	private String runTimerIp;
	
	@Autowired
    @Qualifier("templateMessageService")
	private ITemplateMessageServiceImpl templateMessageService;
	
	@Scheduled(cron="0 00 12 * * ?") 
	public void execute(){
		long begin = System.currentTimeMillis();
		int wechatCount = 0;
		int aliCount = 0;
		int msjwCount = 0;
		String curIp = NetWorkIp.getIp();
		try {
			if (runTimerIp.equals(curIp)) {
				logger.info(curIp+"开始定时发送模板消息数据----------------------------------------------------------------------------");
				wechatCount = templateMessageService.sendMessageWechat4Timer();
				aliCount = templateMessageService.sendMessageAlipay4Timer();
				msjwCount = templateMessageService.sendMessageMSJW4Timer();
			}
		} catch (Exception e) {
			logger.error(curIp+"定时发送模板消息数据异常", e);
		}finally{
			long end = System.currentTimeMillis();
			logger.info(curIp+"本次发送微信数据："+wechatCount);
			logger.info(curIp+"本次发送支付宝数据："+aliCount);
			logger.info(curIp+"本次发送民生警务数据："+msjwCount);
			logger.info(curIp+"结束定时模板消息数据,耗时："+(end-begin)+"----------------------------------------------------------------------------");
		}
	}
}
