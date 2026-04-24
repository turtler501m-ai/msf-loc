package com.ktis.msp.batch.job.cms.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cms.cmsnMgmt.service.CmsnMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 보증보험관리 리스트 엑셀 다운로드
 */
@Component
public class CmsnDailyExcelExecutor extends BaseSchedule {
	
	@Autowired
	private CmsnMgmtService cmsnMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("수수료일정산 엑셀 다운로드 저장 START.");
			LOGGER.debug("Return Vo [BondMgmtVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			cmsnMgmtService.getCmsnDailyMgmtListExcel(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

