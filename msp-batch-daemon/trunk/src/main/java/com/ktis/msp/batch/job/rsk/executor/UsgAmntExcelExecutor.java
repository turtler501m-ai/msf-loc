package com.ktis.msp.batch.job.rsk.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rsk.statmgmt.service.UsgAmntService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class UsgAmntExcelExecutor extends BaseSchedule  {
	
	@Autowired
	private UsgAmntService usgAmntService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("사용량조회(대리점) 엑셀 저장 START.");
			LOGGER.debug("Return Vo [OpenMgmtListVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			usgAmntService.getUsgAmntStatOrgListExcel(batchCommonVO);
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
