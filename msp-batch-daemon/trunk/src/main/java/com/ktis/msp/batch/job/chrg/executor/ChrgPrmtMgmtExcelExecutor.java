package com.ktis.msp.batch.job.chrg.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.chrg.chrgmgmt.service.ChrgPrmtMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class ChrgPrmtMgmtExcelExecutor extends BaseSchedule {
	
	@Autowired
	private ChrgPrmtMgmtService chrgPrmtMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("채널별 요금 할인 엑셀 저장 START.");
			LOGGER.debug("Return Vo [ChrgPrmtMgmtVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			chrgPrmtMgmtService.selectChrgListExcel(batchCommonVO);
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
