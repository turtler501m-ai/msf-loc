package com.ktis.msp.batch.job.rcp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.retention.service.RetentionLmsService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class RetentionLmsSchedule extends BaseSchedule {

	@Autowired
	private RetentionLmsService retentionLmsService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${retentionLmsSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	// 가입후 30개월차 도래고객 LMS발송
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// 대상 고객 추출
			// 1. 3G/LTE 단말 구매고객 중 사용중인 고객
			// 2. 재약정/우수기변 미가입 고객
			// 3. 20~70대 고객 중 30개월차 도래고객 (재도래 고객은 포함하지않음)
			retentionLmsService.insertRetentionLmsTrgt();

			// LMS 전송
			// 매월 7~13일 10시 (500건), 13시 (500건), 15시 (500건), 17시 (200건) 일 최대 1700건
			retentionLmsService.sendLmsRetentionTrgt(batchCommonVO);
			
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
