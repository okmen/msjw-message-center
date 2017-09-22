package cn.message.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.message.bean.HmdahsJ1;

public interface HmdahsJ1Mapper {
	/**
	 * 批量新增hmdahs J1 接口记录
	 * @param list
	 * @return
	 */
	int batchInsertHmdahsJ1(List<HmdahsJ1> list);
	
	/**
	 * 修改发送状态微信
	 * @param list
	 * @return
	 */
	int updateHmdahsJ1State4Wechat(@Param("id")Integer id,@Param("state")Integer state);
	
	/**
	 * 修改发送状态支付宝
	 * @param list
	 * @return
	 */
	int updateHmdahsJ1State4Alipay(@Param("id")Integer id,@Param("state")Integer state);
	
	/**
	 * 查询待发送的数据wechat
	 * @return
	 */
	List<HmdahsJ1> queryHmdahsJ14Wechat();
	
	/**
	 * 查询待发送的数据alipay
	 * @return
	 */
	List<HmdahsJ1> queryHmdahsJ14Alipay();
}
