package cn.message.dao.mapper;

import java.util.List;

import cn.message.bean.SendTemplateRecord;

public interface SendTemplateRecordMapper {
	/**
	 * 插入发送模板消息记录
	 * @param sendTemplateRecord
	 * @return
	 */
	int insertSendTemplateRecord(SendTemplateRecord sendTemplateRecord);
	
	/**
	 * 批量插入发送模板消息记录
	 * @param list
	 * @return
	 */
	int batchInsertSendTemplateRecord(List<SendTemplateRecord> list);
	
	/**
	 * 查询发送记录
	 * @param state
	 * @return
	 */
	List<SendTemplateRecord> querySendTemplateRecordByState(String state);
}
