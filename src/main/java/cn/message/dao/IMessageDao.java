package cn.message.dao;

import cn.message.bean.WxMembercard;

public interface IMessageDao {
	/**
	 * 插入会员卡
	 * @param wxMembercard
	 * @return
	 */
	int insertWxMembercard(WxMembercard wxMembercard);
	
	/**
	 * 查询会员卡
	 * @param wxMembercard
	 * @return
	 */
	WxMembercard selectWxMembercard(String openId,String cardId);
	
	/**
	 * 修改会员卡
	 * @param wxMembercard
	 * @return
	 */
	int updateWxMembercard(WxMembercard wxMembercard);
}
