package com.ktis.msp.batch.job.rcp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;

import com.ktis.msp.batch.job.rcp.giftprmt.service.GiftPrmtLmsService;

import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class GiftPrmtLmsSchedule extends BaseSchedule {

	@Autowired
	private GiftPrmtLmsService giftPrmtLmsService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${giftPrmtLmsSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	// 사은품프로모션 고객 LMS발송
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		int ret = 0;
		try {
			batchStart(batchCommonVO);
			// M포털 가입 고객 추출(M포털에서 가입 시 사은품 미리 선택)
			// M포털 이벤트 코드 적용 대상 고객 추출
//			giftPrmtLmsService.insertGiftPrmtEventCodeTrgt();
			
			// M포털 USIM 제휴처 대상 고객 추출
			giftPrmtLmsService.insertGiftPrmtMPortalUsimTrgt();
			
			// M포털 대상 고객 추출
			giftPrmtLmsService.insertGiftPrmtMPortalTrgt();

			// M포털 가입 고객 사은품 프로모션 결과 추출 (USIM 제휴처도 포함되서 결과 저장)
			giftPrmtLmsService.insertGiftPrmtResultMPortalTrgt();

			// 대상 고객 추출
			// 0. 개통일 포함 3일 경과한 사용중인 고객(USIM 제휴처)
			// 1. 개통일 포함 3일 경과한 사용중인 고객
			// 2. 사은품 프로모션에 해당하는 고객
			// 3. 고객 추출은 중복불가
			
			// 대상고객추출(USIM 제휴처)
			giftPrmtLmsService.insertGiftPrmtLmsUsimTrgt();
			
			// 대상고객추출
			giftPrmtLmsService.insertGiftPrmtLmsTrgt();

			// 대상고객 기본 사은품 프로모션 결과 추출 (USIM 제휴처도 포함되서 결과 저장)
			giftPrmtLmsService.insertGiftPrmtResultTrgt();

			// (M포털 가입 고객 사은품 프로모션 결과 이력 + 대상고객 기본 사은품 프로모션 결과 이력) 저장
			giftPrmtLmsService.insertGiftPrmtResultHistTrgt();
			
			// LMS 전송 (선택 사은품 프로모션 건만 전송)
			// 매일 9시 부터 18시 까지 20분 간격  500건 	
			ret = giftPrmtLmsService.sendLmsGiftTrgt(batchCommonVO);

			// LMS 재전송 (선택 사은품 프로모션 건만 전송)
			// 매일 9시 부터 18시 까지 20분 간격  500건 
			giftPrmtLmsService.reSendLmsGiftTrgt(batchCommonVO);
			
			
			batchCommonVO.setExecCount(ret);
			batchCommonVO.setRemrk("사은품 프로모션 대상자 SMS발송");
						
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
