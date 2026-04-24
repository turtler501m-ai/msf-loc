package com.ktis.msp.batch.job.fdh.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.fdh.iif.service.FdhTransmitService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class FdhInf020Schedule extends BaseSchedule {
	
   @Autowired
   private FdhTransmitService fdhTransmitService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${fdhInf020Schedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	/**
	 * M전산 > 선불 >본사 > 선불조직관리 > 예치금사용내역
	 * 예치금사용내역
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			fdhTransmitService.getFdhInf020(); //예치금사용내역
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
	}
}