package cn.message.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.message.bean.UserBind;

public interface UserBindMapper {
	/**
	 * 根据身份证查询用户
	 * @param idCard
	 * @return
	 */
	List<UserBind> queryUserBindByIdCard(String[] idCards);
	
	/**
	 * 根据手机号码查询用户
	 * @param mobiles
	 * @return
	 */
	List<UserBind> queryUserBindByMobile(List<String> mobiles);
	
	/**
	 * 根据openId获取身份证号
	 * @param openId
	 * @return
	 */
	String queryIdCardByOpenId(@Param("openId")String openId);
}
