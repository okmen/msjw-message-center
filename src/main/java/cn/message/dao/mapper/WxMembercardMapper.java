package cn.message.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.message.bean.WxMembercard;

@Repository
public interface WxMembercardMapper {
	
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
	WxMembercard selectWxMembercard(@Param("openId")String openId,@Param("cardId")String cardId,@Param("code")String code);
	
	/**
	 * 修改会员卡
	 * @param wxMembercard
	 * @return
	 */
	int updateWxMembercard(WxMembercard wxMembercard);
}
