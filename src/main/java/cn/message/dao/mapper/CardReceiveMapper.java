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
	
	/**
	 * 修改卡包状态为已删除
	 * @param alipayUserId 支付宝唯一标识
	 * @param certNo 身份证
	 * @param keepType 原来状态 car/driver
	 * @param deleteType 删除状态 deleted_car/deleted_driver
	 * @return
	 */
	int updateCardReceiveType(
			@Param("alipayUserId")String alipayUserId,
			@Param("certNo")String certNo,
			@Param("keepType")String keepType,
			@Param("deleteType")String deleteType);
}
