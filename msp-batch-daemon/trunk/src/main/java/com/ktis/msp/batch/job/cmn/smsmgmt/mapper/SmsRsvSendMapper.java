package com.ktis.msp.batch.job.cmn.smsmgmt.mapper;

import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("smsRsvSendMapper")
public interface SmsRsvSendMapper {

	int isSmsRsvList();
	
	KtSmsCommonVO selectSmsRsvInfo();
	
	int insertSmsRsvSendList(KtSmsCommonVO vo);
	
	int updateSmsRsvInfo(KtSmsCommonVO vo);
	
	int updateSmsRsvErrInfo(KtSmsCommonVO vo);
	
	int insertKakaoRsvSendList(KtSmsCommonVO vo);
	
}
