package cn.message.dao.impl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.message.bean.WxMembercard;
import cn.message.dao.IMessageDao;
import cn.message.dao.mapper.WxMembercardMapper;

@Repository
public class IMessageDaoImpl implements IMessageDao {
	@Autowired
	private WxMembercardMapper wxMembercardMapper;
	
	@Override
	public int insertWxMembercard(WxMembercard wxMembercard) {
		return wxMembercardMapper.insertWxMembercard(wxMembercard);
	}

	@Override
	public WxMembercard selectWxMembercard(String openId,String cardId,String code) {
		return wxMembercardMapper.selectWxMembercard(openId, cardId, code);
	}

	@Override
	public int updateWxMembercard(WxMembercard wxMembercard) {
		return wxMembercardMapper.updateWxMembercard(wxMembercard);
	}
}
