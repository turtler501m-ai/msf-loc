package com.ktis.msp.batch.manager.mapper;

import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsCommonMapper")
public interface SmsCommonMapper {
	
	int insertMsgQueue(SmsCommonVO paramVO);
	
	int insertKtMsgQueue(KtSmsCommonVO paramVO);
	
	int insertSmsSendMst(SmsSendVO paramVO);
	
	int updateSmsSendMst(KtSmsCommonVO paramVO);
	
	SmsCommonVO getTemplateText(String templateId);
	
	int insertTemplateMsgQueue(SmsCommonVO paramVO);

	int insertTemplateKakaoMsgQueue(SmsCommonVO paramVO);
	
	String getMblphnNum(String userId);
	
	int insertKtMsgTmpQueue(KtSmsCommonVO paramVO);
	
	int insertKtSmsSendMst(SmsSendVO paramVO);
	
	KtSmsCommonVO getKtTemplateText(String templateId);
}
