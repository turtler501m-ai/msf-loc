package com.ktis.msp.batch.job.rcp.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.custmgmt.service.CustGiftMngService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class CustGiftMngListExcelExecutor extends BaseSchedule  {
	
	@Autowired
	private CustGiftMngService custGiftMngService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("고객 사은품관리 엑셀 저장 START.");
			LOGGER.debug("Return Vo [CustGiftVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			custGiftMngService.getCustGiftMngExcel(batchCommonVO);
			
			//배치 성공시 
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
