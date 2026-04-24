package com.ktis.msp.batch.job.rcp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.RcpNowDlvryUsimService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class RcpNowDlvryUsimSchedule extends BaseSchedule {
	
	@Autowired
	private RcpNowDlvryUsimService rcpNowDlvryUsimService;
	
	@Scheduled(cron="${rcpNowDlvryUsimSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		int procCnt = 0;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			procCnt = rcpNowDlvryUsimService.procRcpNowDlvryMgmt();
			
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
