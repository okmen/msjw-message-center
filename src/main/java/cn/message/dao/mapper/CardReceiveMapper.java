package cn.message.dao.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cn.message.bean.CardReceive;

@Repository
public interface CardReceiveMapper {
	
	/**
	 * 支付宝领卡
	 * @param cardReceive
	 * @return
	 */
	int insertCardReceive(CardReceive cardReceive);
	
	/**
	 * 支付宝查询领卡数量
	 * @param certNo 身份证号
	 * @param type 类型
	 * @return
	 */
	int queryReceiveCardCount(@Param("certNo")String certNo, @Param("type")String type);
}
