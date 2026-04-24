package com.ktis.msp.batch.job.dis.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.service.DisPrmtMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class DisPrmtMgmtExcelExecutor extends BaseSchedule {
	
	@Autowired
	private DisPrmtMgmtService disPrmtMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("프로모션관리 엑셀 저장 START.");
			LOGGER.debug("Return Vo [DisPrmtMgmtVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			disPrmtMgmtService.selectDisListExcel(batchCommonVO);
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
