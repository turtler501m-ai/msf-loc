package com.ktis.msp.batch.job.cmn.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.service.SmsNotSendChkService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class SmsNotSendChkSchedule extends BaseSchedule {
	
	@Autowired
	private SmsNotSendChkService smsNotSendChkService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${smsNotSendChkSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	// AM2X_SUBMIT STATUS가 0이면서 20분 이상동안 반응없을때 데이터 삭제 및 BACKUP 
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		int procCnt = 0;
		try {
			batchStart(batchCommonVO);
			// ========== BATCH MAIN ==================
			procCnt = smsNotSendChkService.smsNotSendChk();
			
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
