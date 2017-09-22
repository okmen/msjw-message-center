package cn.message.dao.mapper;

import java.util.List;

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
}
