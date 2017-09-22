package cn.message.timer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import cn.message.dao.IMessageDao;
import cn.message.service.impl.ITemplateMessageServiceImpl;
import cn.message.utils.NetWorkIp;


/**
 * 获取hm_dahs J1 接口数据
 * @author gaoxigang
 *
 */
@Component
public class GetHmdahsJ1DataTask {
	private Logger logger = Logger.getLogger(GetHmdahsJ1DataTask.class);
	
	@Value("${runTimerIp}")
	private String runTimerIp;
	
	@Autowired
    @Qualifier("templateMessageService")
	private ITemplateMessageServiceImpl templateMessageService;
	
	@Scheduled(cron="0 01 01 * * ?") 
	public void execute(){
		long begin = System.currentTimeMillis();
		String curIp = NetWorkIp.getIp();
		try {
			if (runTimerIp.equals(curIp)) {
				logger.info("开始获取hm_dahs J1 数据----------------------------------------------------------------------------");
				templateMessageService.gainHmdahsData();
			}
		} catch (Exception e) {
			logger.error(curIp + "定时获取待发送消息数据 hm_dahs J1异常", e);
		}finally{
			long end = System.currentTimeMillis();
			logger.info(curIp+"结束获取hm_dahs J1 数据,耗时"+(end-begin)+"----------------------------------------------------------------------------");
		}
	}
}
