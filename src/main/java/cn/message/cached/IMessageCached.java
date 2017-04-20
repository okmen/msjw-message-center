package cn.message.cached;
public interface IMessageCached extends ICacheKey {
	
	/**
     * 插入token信息
     */
    public String insertAccessToken(String token);
    
    /**
     * 获取token
     * @return
     */
    public String getAccessToken();
    
    /**
     * 插入ticket信息
     */
    public String insertTicket(String ticket);
    
    /**
     * 获取token
     * @return
     */
    public String getTicket();
}
