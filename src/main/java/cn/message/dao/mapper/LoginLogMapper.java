package cn.message.dao.mapper;

import java.util.List;

import cn.message.bean.LoginLog;

public interface LoginLogMapper {
	
	/**
	 * 根据手机号码查询支付宝用户
	 * @param mobiles
	 * @return
	 */
	List<LoginLog> queryLoginByMobile(List<String> mobiles);
}
