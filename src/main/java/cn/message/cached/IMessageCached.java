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
     * 获取Ticket
     * @return
     */
    public String getTicket();
    
    
    /**
     * 插入apiTicket信息
     */
    public String insertApiTicket(String ticket);
    
    /**
     * 获取apiTicket
     * @return
     */
    public String getApiTicket();
}
