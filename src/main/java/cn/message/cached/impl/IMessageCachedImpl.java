package cn.message.cached.impl;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.message.cached.IMessageCached;
import cn.message.config.IConfig;
import cn.message.utils.WebService4Wechat;
import cn.sdk.cache.ICacheManger;
import cn.sdk.serialization.ISerializeManager;
@Service
public class IMessageCachedImpl implements IMessageCached{
	protected Logger log = Logger.getLogger(this.getClass());
	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private ICacheManger<String> cacheManger;
	
	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private ICacheManger<Object> objectcacheManger;
	
	@Autowired
	private ISerializeManager< Map<String, String> > serializeManager;
	
	public String insertToken(String token) {
	    cacheManger.set(IConfig.ACCESS_TOKEN_REDIS, token, tokenExprieTime);
        return token;
    }

	@Override
	public String getToken() {
		String token = cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
		if(null == token || "".equals(token)){
			initTokenAndTicket();
		}
		return cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
	}


	@Override
	public String insertTicket(String ticket) {
		cacheManger.set(IConfig.TICKET_REDIS, ticket, tokenExprieTime);
		return ticket;
	}

	@Override
	public String getTicket() {
		String ticket = cacheManger.get(IConfig.TICKET_REDIS);
		if(null == ticket || "".equals(ticket)){
			initTokenAndTicket();
		}
		return cacheManger.get(IConfig.TICKET_REDIS);
	}
	
	/**
	 * 从微信段获取 token 和 ticket
	 * @return
	 */
	private void initTokenAndTicket() {
		try {
			Map<String, Object> map = WebService4Wechat.getAccessToken();
			Object token = map.get("access_token");
			if(null != token && !"".equals(token)){
				//存入redis
				insertToken(token.toString());
				
				Map<String, Object> jsapMap = WebService4Wechat.getJsapiTicket(token.toString());
				Object ticket = jsapMap.get("ticket");
				if(null != ticket && !"".equals(ticket)){
					insertTicket(ticket.toString());
					log.info("ticket："+ticket);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage()+"获取 token失败");
		}
	}
}
