package com.ktis.msp.batch.manager.mapper;

import com.ktis.msp.batch.manager.vo.SmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsCommonMapper")
public interface SmsCommonMapper {
	
	int insertMsgQueue(SmsCommonVO paramVO);
	
	int insertSmsSendMst(SmsCommonVO paramVO);
	
	int updateSmsSendMst(SmsCommonVO paramVO);
	
	SmsCommonVO getTemplateText(String templateId);
	
	int insertTemplateMsgQueue(SmsCommonVO paramVO);
	
}
