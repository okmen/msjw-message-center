package cn.message.cached;

public interface ICacheKey {
	public int exprieTime = 15 * 24 * 3600;        // redis过期时间全部为15天
	public int maxExpireTime = 30 * 24 * 3600;     // 最大过期时间
}
