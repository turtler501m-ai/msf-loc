package com.ktis.msp.batch.job.org.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.termssendmgmt.service.TermsSendMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class TermsSendSchedule extends BaseSchedule {
	
	@Autowired
	private TermsSendMgmtService termsSendMgmtService;
	
	@Scheduled(cron="${termsSendSchedule}")
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
			// termsSendMgmtService.insertTermsLmsSendTrgt();
			// ========== BATCH MAIN ==================
			
			// 2019년도 부터는 아래 로직으로 수정 바랍니다.
			termsSendMgmtService.insertTermsLmsSendTrgt2();
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
