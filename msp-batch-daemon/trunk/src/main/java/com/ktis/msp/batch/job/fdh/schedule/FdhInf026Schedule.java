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
public class FdhInf026Schedule extends BaseSchedule {
	
   @Autowired
   private FdhTransmitService fdhTransmitService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${fdhInf026Schedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	/**
	 * M전산 > 선불 > 본사 > 데이타관리 > 실시간선불정산대상 없어짐.. 그래도 모르니 소스는 지우지 않음.
	 * 실시간선불정산대상
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			fdhTransmitService.getFdhInf026(); //실시간선불정산대상
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
	}
}