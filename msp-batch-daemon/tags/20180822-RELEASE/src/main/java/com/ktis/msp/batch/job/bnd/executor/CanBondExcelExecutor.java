package com.ktis.msp.batch.job.bnd.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.bnd.bondmgmt.service.BondMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 단말유동화>채권판매관리> 회수채권 엑셀 다운로드
 */
@Component
public class CanBondExcelExecutor extends BaseSchedule {
	
	@Autowired
	private BondMgmtService bondMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("단말유동화>채권판매관리> 회수채권 리스트 엑셀 저장 START.");
			LOGGER.debug("Return Vo [BondMgmtVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			bondMgmtService.saveExcelCanBond(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

