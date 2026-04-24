package com.ktis.msp.batch.job.rcp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.openmgmt.service.OpenCstmrLmsService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 개통고객 LMS 전송
 * 
 */
@Component
public class OpenCstmrLmsSchedule extends BaseSchedule {
	
	@Autowired
	private OpenCstmrLmsService openCstmrLmsService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${openCstmrLmsSchedule}")
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
			int procCnt = openCstmrLmsService.setOpenCstmrLms();
			
			batchCommonVO.setExecCount(procCnt);
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}