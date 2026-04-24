package com.ktis.msp.batch.job.rcp.giftprmt.mapper;

import java.util.List;
import java.util.Map;

import com.ktis.msp.batch.manager.vo.SmsCommonVO;
import com.ktis.msp.batch.manager.vo.SmsSendVO;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("giftPrmtLmsMapper")
public interface GiftPrmtLmsMapper {

	// 대상 고객 추출
	int insertGiftPrmtLmsTrgt();
	
	// LMS 전송 대상 조회
	List<Map<String, Object>> selectLmsSendList(int maxCnt);	
	
	//첫 문자 전송 이후 update
	int updateLmsSendList(SmsSendVO paramVO);
	
	
	// LMS 재 전송 대상 조회
	List<Map<String, Object>> selectLmsReSendList(int maxCnt);	

	//문자 재전송 이후 update
	int updateLmsReSendList(SmsSendVO paramVO);
	
	// M포털 가입한 고객 추출
	int insertGiftPrmtMPortalTrgt();

	// M포털 가입한 고객 사은품 프로모션 결과 추출
	int insertGiftPrmtResultMPortalTrgt();

	// M포털 가입한 고객 추출(USIM 제휴처)
	int insertGiftPrmtMPortalUsimTrgt();

	// 대상 고객 추출(USIM 제휴처)
	int insertGiftPrmtLmsUsimTrgt();

	// 대상고객 기본 사은품 프로모션 결과 추출
	int insertGiftPrmtResultTrgt();

	// 대상고객 사은품 프로모션 결과 이력 저장
	int insertGiftPrmtResultHistTrgt();

	// M포털 이벤트 코드 적용 대상 고객 추출
	int insertGiftPrmtEventCodeTrgt();
}
