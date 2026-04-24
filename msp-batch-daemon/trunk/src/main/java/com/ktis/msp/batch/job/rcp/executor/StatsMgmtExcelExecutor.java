package com.ktis.msp.batch.job.rcp.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.statsmgmt.service.StatsMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class StatsMgmtExcelExecutor extends BaseSchedule  {
	
	@Autowired
	private StatsMgmtService statsMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("재약정현황 엑셀 저장 START.");
			LOGGER.debug("Return Vo [OpenMgmtListVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			statsMgmtService.selectStatsMgmtListExcel(batchCommonVO);
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
