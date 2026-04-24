package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.IntmInsrService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 단말 보험가입자 부가서비스 해지 처리 스케줄러
 * 매일 3시 생성
 */

@Component
public class IntmInsrCanSchedule extends BaseSchedule {
	
	@Autowired
	private IntmInsrService insrService;
		
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${intmInsrCanSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	
	/**
	 * 
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			LOGGER.info("--- 단말 보험가입자 부가서비스 해지 처리 ---");
			insrService.expireIntmInsrOrder();
			
			LOGGER.info("--- 단말 보험가입자 기간만료 해지 처리 ---");
			insrService.expireIntmInsrMember();
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
	
}

