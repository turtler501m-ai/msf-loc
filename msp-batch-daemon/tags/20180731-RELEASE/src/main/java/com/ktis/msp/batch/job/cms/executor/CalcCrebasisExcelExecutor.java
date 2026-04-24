package com.ktis.msp.batch.job.cms.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cms.cmsnMgmt.service.CmsnMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 판매_ARPU수수료내역 엑셀 다운로드
 */
@Component
public class CalcCrebasisExcelExecutor extends BaseSchedule {
	
	@Autowired
	private CmsnMgmtService cmsnMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("판매_ARPU수수료내역 엑셀 저장 START.");
			LOGGER.debug("Return Vo [BondMgmtVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			cmsnMgmtService.getCalcCrebasisListExcel(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

