package com.ktis.msp.batch.job.rsk.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rsk.statmgmt.service.StatMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 청구수납자료 리스트 엑셀 다운로드
 */
@Component
public class InvPymDataExcelExecutor extends BaseSchedule {
	
	@Autowired
	private StatMgmtService statMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("청구수납자료 리스트 엑셀 저장 START.");
			LOGGER.debug("Request PARAM = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			statMgmtService.setInvPymDataList(batchCommonVO);
			
//			bondMgmtService.callPayCheck();
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		} catch (Exception e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			MvnoServiceException ex = new MvnoServiceException("ERSK1007");
			batchError(batchCommonVO, ex);
		}
		
		return result;
	}
	
}

