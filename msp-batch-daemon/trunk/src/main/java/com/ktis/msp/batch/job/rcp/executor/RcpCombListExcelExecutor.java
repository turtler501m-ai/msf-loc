package com.ktis.msp.batch.job.rcp.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.RcpCombMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class RcpCombListExcelExecutor extends BaseSchedule  {
	
	@Autowired
	private RcpCombMgmtService rcpCombMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("신청관리(결합서비스 엑셀 저장 START.");
			LOGGER.debug("Return Vo [RcpCombMgmtListVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			rcpCombMgmtService.selectRcpCombMgmtListExcel(batchCommonVO);
			
			batchEnd(batchCommonVO);
			
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
