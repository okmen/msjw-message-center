package cn.message.dao;

import java.util.List;

import cn.message.bean.CardReceive;
import cn.message.bean.HmdahsJ1;
import cn.message.bean.LoginLog;
import cn.message.bean.SendTemplateRecord;
import cn.message.bean.UserBind;
import cn.message.bean.WxMembercard;

public interface IMessageDao {
	
	/**
	 * 修改卡包状态为已删除
	 * @param alipayUserId 支付宝唯一标识
	 * @param certNo 身份证
	 * @param keepType 原来状态 car/driver
	 * @param deleteType 删除状态 deleted_car/deleted_driver
	 * @return
	 */
	int updateCardReceiveType(String alipayUserId, String certNo, String keepType, String deleteType);
	
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
	int queryReceiveCardCount(String certNo, String type);
	
	/**
	 * 根据openId获取身份证号
	 * @param openId
	 * @return
	 */
	String queryIdCardByOpenId(String openId);
	
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
	
	/**
	 * 根据身份证查询用户
	 * @param wxMembercard
	 * @return
	 */
	List<UserBind> queryUserBindByIdCard(String [] idCards);
	
	/**
	 * 插入发送模板消息记录
	 * @param sendTemplateRecord
	 * @return
	 */
	int insertSendTemplateRecord(SendTemplateRecord sendTemplateRecord);
	
	/**
	 * 批量新增hmdahs J1 接口记录
	 * @param list
	 * @return
	 */
	int batchInsertHmdahsJ1(List<HmdahsJ1> list);
	
	/**
	 * 查询待发送的数据
	 * @return
	 */
	List<HmdahsJ1> queryHmdahsJ14Wechat();
	
	/**
	 * 查询待发送的数据alipay
	 * @return
	 */
	List<HmdahsJ1> queryHmdahsJ14Alipay();
	
	/**
	 * 修改发送状态
	 * @param list
	 * @return
	 */
	int updateHmdahsJ1State4Wechat(Integer id,Integer state);
	
	/**
	 * 修改发送状态
	 * @param list
	 * @return
	 */
	int updateHmdahsJ1State4Alipay(Integer id,Integer stateAlipay);
	
	/**
	 * 根据手机号码查询用户
	 * @param mobiles
	 * @return
	 */
	List<UserBind> queryUserBindByMobile(List<String> mobiles);
	
	/**
	 * 根据手机号码查询支付宝用户
	 * @param mobiles
	 * @return
	 */
	List<LoginLog> queryLoginByMobile(List<String> mobiles);
	
	/**
	 * 查询发送记录
	 * @param state
	 * @return
	 */
	List<SendTemplateRecord> querySendTemplateRecordByState(String state);
	/**
	 * 修改发送状态
	 * @param list
	 * @return
	 */

	int updateHmdahsJ1State4Msjw(Integer id, Integer state);
	/**
	 * 查询待发送的数据msjw
	 * @return
	 */

	List<HmdahsJ1> queryHmdahsJ14Msjw();
}
