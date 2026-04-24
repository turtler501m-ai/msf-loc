package com.ktis.msp.batch.job.msp.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.msp.ifrs.service.IfrsTransmitService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * FDH 연동
 */
@Component
public class FdhCmsCalcExecutor extends BaseSchedule {
	
	@Autowired
	private IfrsTransmitService ifrsTransmitService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("FDH 연동 START.");
			LOGGER.debug("=================================================================");
			
			ifrsTransmitService.getCmsPrvdTrgtAgncyCttt();
			
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

