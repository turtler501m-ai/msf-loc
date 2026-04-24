package com.ktis.msp.batch.job.voc.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.voc.vocMgmt.service.VocListMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class VocListExcelExecutor extends BaseSchedule {
	@Autowired
	private VocListMgmtService vocListMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("상담목록 엑셀 저장 START.");
			LOGGER.debug("Return Vo [VocListMgmtVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			vocListMgmtService.selectVocListExcel(batchCommonVO);
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
