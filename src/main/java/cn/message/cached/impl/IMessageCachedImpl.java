package cn.message.cached.impl;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import cn.message.cached.IMessageCached;
import cn.message.config.IConfig;
import cn.message.utils.wechat.WebService4Wechat;
import cn.sdk.cache.ICacheManger;
import cn.sdk.serialization.ISerializeManager;
@Service
public class IMessageCachedImpl implements IMessageCached{
	protected Logger log = Logger.getLogger(this.getClass());

	/**
	 * 用户id
	 */
	@Value("${userid}")
	private String userid;

	/**
	 * 用户密码
	 */
	@Value("${userpwd}")
	private String userpwd;

	/**
	 * 请求地址
	 */
	@Value("${url}")
	private String url;

	/**
	 * 方法
	 */
	@Value("${method}")
	private String method;

	/**
	 * 秘钥
	 */
	@Value("${key}")
	private String key;

	/**
	 * 微信appId
	 */
	@Value("${appid}")
	private String appid;

	/**
	 * 微信token
	 */
	@Value("${token}")
	private String token;

	/**
	 * 微信appsecret
	 */
	@Value("${appsecret}")
	private String appsecret;
	
	/**
	 * 微信 accessTokentime
	 */
	@Value("${accessTokentime}")
	private int accessTokentime;
	
	/**
	 * javaDomain java 环境域名
	 */
	@Value("${javaDomain}")
	private String javaDomain;
	
	/**
	 * h5环境域名
	 */
	@Value("${h5Domain}")
	private String h5Domain;
	
	@Value("${menuFile}")
	private String menuFile;
	
	/**
	 * 模板详情域名地址
	 */
	@Value("${templateSendUrl}")
    private String templateSendUrl;
    public String getTemplateSendUrl() {
		return templateSendUrl;
	}

	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private ICacheManger<String> cacheManger;

	@Autowired
	@Qualifier("jedisCacheManagerImpl")
	private ICacheManger<Object> objectcacheManger;

	@Autowired
	private ISerializeManager<Map<String, String>> serializeManager;

	public String insertAccessToken(String token) {
		cacheManger.set(IConfig.ACCESS_TOKEN_REDIS, token, accessTokentime);
		return token;
	}

	@Override
	public String getAccessToken() {
		String token = cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
		if(null == token || "".equals(token)){
			initTokenAndTicket();
		}
		return cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
	}


	@Override
	public String insertTicket(String ticket) {
		cacheManger.set(IConfig.TICKET_REDIS, ticket, accessTokentime);
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
	
	@Override
	public String insertApiTicket(String ticket) {
		cacheManger.set(IConfig.API_TICKET_REDIS, ticket, accessTokentime);
		return ticket;
	}

	@Override
	public String getApiTicket() {
		String ticket = cacheManger.get(IConfig.API_TICKET_REDIS);
		if(null == ticket || "".equals(ticket)){
			initTokenAndTicket();
		}
		return cacheManger.get(IConfig.API_TICKET_REDIS);
	}
	
	/**
	 * 从微信段获取 token 和 ticket
	 * @return
	 */
	public String initTokenAndTicket() {
		try {
			Map<String, Object> map = WebService4Wechat.getAccessToken(appid, appsecret);
			Object token = map.get("access_token");
			if(null != token && !"".equals(token)){
				//存入redis
				insertAccessToken(token.toString());
				log.info("获取新token:"+token);
				Map<String, Object> jsapMap = WebService4Wechat.getJsapiTicket(token.toString());
				Object ticket = jsapMap.get("ticket");
				if(null != ticket && !"".equals(ticket)){
					insertTicket(ticket.toString());
					log.info("获取新jsapi ticket:"+ticket);
				}
				
				Map<String, Object> apiMap = WebService4Wechat.getApiTicket(token.toString());
				Object apiTicket = apiMap.get("ticket");
				if(null != apiTicket && !"".equals(apiTicket)){
					insertApiTicket(apiTicket.toString());
					log.info("获取新api ticket:"+apiTicket);
				}
			}
		} catch (Exception e) {
			log.error("获取 token and ticket失败",e);
		}
		return cacheManger.get(IConfig.ACCESS_TOKEN_REDIS);
	}
	
	public String getUserid() {
		return userid;
	}
	public String getUserpwd() {
		return userpwd;
	}
	public String getUrl() {
		return url;
	}
	public String getMethod() {
		return method;
	}
	public String getKey() {
		return key;
	}
	public String getAppid() {
		return appid;
	}
	public String getToken() {
		return token;
	}
	public String getAppsecret() {
		return appsecret;
	}
	public int getAccessTokentime() {
		return accessTokentime;
	}
	public String getJavaDomain() {
		return javaDomain;
	}
	public String getH5Domain() {
		return h5Domain;
	}
	public String getMenuFile() {
		return menuFile;
	}
}
