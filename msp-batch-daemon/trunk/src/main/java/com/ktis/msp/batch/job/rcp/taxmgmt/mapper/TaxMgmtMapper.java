package com.ktis.msp.batch.job.rcp.taxmgmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.manager.vo.KtSmsCommonVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("taxMgmtMapper")
public interface TaxMgmtMapper {
	
	// LMS 전송 대상 조회
	List<Map<String,Object>> selectTaxSendList();		
	//LMS 전송	
	int insertTaxMsgQueue(KtSmsCommonVO paramVO);
	
	int insertKakaoTaxMsgQueue(KtSmsCommonVO paramVO);
	
	int insertAgentMsgQueue(KtSmsCommonVO paramVO);
	
	int insertKakaoAgentMsgQueue(KtSmsCommonVO paramVO);
	
	//첫 문자 전송 이후 update
	int updateTaxSendList(KtSmsCommonVO paramVO);
	//매월 25일 제세공과금 전달 삭제 데이터 확인
	List<Map<String,Object>> selectDelTaxData();
	//매월 25일 제세공과금 전달 삭제
	int deleteTaxSendData();
	//법정대리인 발송 대상자 조회
	int selectAgentCnt(KtSmsCommonVO paramVO);
	// LMS 재전송 대상 조회
	List<Map<String,Object>> selectTaxReSendList();
	//LMS 재전송 
	int insertTaxReMsgQueue(KtSmsCommonVO paramVO);
	
	int insertKakaoTaxReMsgQueue(KtSmsCommonVO paramVO);
	
	int insertAgentReMsgQueue(KtSmsCommonVO paramVO);
	
	int insertKakaoAgentReMsgQueue(KtSmsCommonVO paramVO);	
}
