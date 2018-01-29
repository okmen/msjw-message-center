package cn.message.dao.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import cn.message.bean.CardReceive;
import cn.message.bean.HmdahsJ1;
import cn.message.bean.LoginLog;
import cn.message.bean.SendTemplateRecord;
import cn.message.bean.UserBind;
import cn.message.bean.WxMembercard;
import cn.message.dao.IMessageDao;
import cn.message.dao.mapper.CardReceiveMapper;
import cn.message.dao.mapper.HmdahsJ1Mapper;
import cn.message.dao.mapper.LoginLogMapper;
import cn.message.dao.mapper.SendTemplateRecordMapper;
import cn.message.dao.mapper.UserBindMapper;
import cn.message.dao.mapper.WxMembercardMapper;

@Repository
public class IMessageDaoImpl implements IMessageDao {
	@Autowired
	private CardReceiveMapper cardReceiveMapper;
	
	@Autowired
	private WxMembercardMapper wxMembercardMapper;
	
	@Autowired
	private UserBindMapper userBindMapper;
	
	@Autowired
	private SendTemplateRecordMapper sendTemplateRecordMapper;
	
	@Autowired
	private HmdahsJ1Mapper hmdahsJ1Mapper;
	
	@Autowired
	private LoginLogMapper loginLogMapper;
	
	
	@Override
	public int updateCardReceiveType(String alipayUserId, String certNo, String keepType, String deleteType) {
		return cardReceiveMapper.updateCardReceiveType(alipayUserId, certNo, keepType, deleteType);
	}

	@Override
	public int insertCardReceive(CardReceive cardReceive) {
		return cardReceiveMapper.insertCardReceive(cardReceive);
	}

	@Override
	public int queryReceiveCardCount(String certNo, String type) {
		return cardReceiveMapper.queryReceiveCardCount(certNo, type);
	}

	@Override
	public String queryIdCardByOpenId(String openId) {
		return userBindMapper.queryIdCardByOpenId(openId);
	}

	@Override
	public int insertWxMembercard(WxMembercard wxMembercard) {
		return wxMembercardMapper.insertWxMembercard(wxMembercard);
	}

	@Override
	public WxMembercard selectWxMembercard(String openId,String cardId) {
		return wxMembercardMapper.selectWxMembercard(openId, cardId);
	}

	@Override
	public int updateWxMembercard(WxMembercard wxMembercard) {
		return wxMembercardMapper.updateWxMembercard(wxMembercard);
	}

	@Override
	public List<UserBind> queryUserBindByIdCard(String[] idCards) {
		return userBindMapper.queryUserBindByIdCard(idCards);
	}

	@Override
	public int insertSendTemplateRecord(SendTemplateRecord sendTemplateRecord) {
		return sendTemplateRecordMapper.insertSendTemplateRecord(sendTemplateRecord);
	}

	@Override
	public int batchInsertHmdahsJ1(List<HmdahsJ1> list) {
		return hmdahsJ1Mapper.batchInsertHmdahsJ1(list);
	}

	@Override
	public List<HmdahsJ1> queryHmdahsJ14Wechat() {
		return hmdahsJ1Mapper.queryHmdahsJ14Wechat();
	}

	@Override
	public int updateHmdahsJ1State4Wechat(Integer id, Integer state) {
		return hmdahsJ1Mapper.updateHmdahsJ1State4Wechat(id, state);
	}
	
	@Override
	public int updateHmdahsJ1State4Alipay(Integer id, Integer state) {
		return hmdahsJ1Mapper.updateHmdahsJ1State4Alipay(id, state);
	}

	@Override
	public List<UserBind> queryUserBindByMobile(List<String> mobiles) {
		// TODO Auto-generated method stub
		return userBindMapper.queryUserBindByMobile(mobiles);
	}

	@Override
	public List<LoginLog> queryLoginByMobile(List<String> mobiles) {
		// TODO Auto-generated method stub
		return loginLogMapper.queryLoginByMobile(mobiles);
	}

	@Override
	public List<HmdahsJ1> queryHmdahsJ14Alipay() {
		// TODO Auto-generated method stub
		return hmdahsJ1Mapper.queryHmdahsJ14Alipay();
	}

	@Override
	public List<SendTemplateRecord> querySendTemplateRecordByState(String state) {
		// TODO Auto-generated method stub
		return sendTemplateRecordMapper.querySendTemplateRecordByState(state);
	}
}
