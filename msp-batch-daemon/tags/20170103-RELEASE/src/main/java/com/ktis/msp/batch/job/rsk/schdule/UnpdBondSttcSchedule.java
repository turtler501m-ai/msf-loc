package com.ktis.msp.batch.job.rsk.schdule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rsk.unpaybondmgmt.service.UnpayBondMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 미납채권생성스케줄러
 * 매월 6일 6시에 실행되며, 프로시져 내에서 로그 및 데이터를 생성한다.
 */
public class UnpdBondSttcSchedule extends BaseSchedule {
	
	@Autowired
	private UnpayBondMgmtService unpdService;
	
	/**
	 * Job 실행
	 * @throws Exception
	 */
	@Scheduled(cron="${unpdBondSttcSchedule}")
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
			unpdService.callUnpdBondSttc();
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

