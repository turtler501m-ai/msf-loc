package com.ktis.msp.batch.job.msp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.msp.ifrs.service.IfrsTransmitService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class IfrsInf007Schedule extends BaseSchedule {
	
	@Autowired
	private IfrsTransmitService ifrsTransmitService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${ifrsInf007Schedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			ifrsTransmitService.getMspRateSpec();
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

