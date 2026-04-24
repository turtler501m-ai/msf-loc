package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.RwdService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 보상서비스 부가서비스 가입 스케줄러
 * 매일 9시 3분 실행
 */

@Component
public class RwdOrderSchedule extends BaseSchedule {
	
	@Autowired
	private RwdService rwdService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${rwdOrderSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	/**
	 * 보상서비스 부가서비스 가입 스케줄러
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			LOGGER.info("--- 보상서비스 부가서비스 가입 ---");
			rwdService.subRwdOrder();
			LOGGER.info("--- 보상서비스 부가서비스 종료 ---");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
	}
}