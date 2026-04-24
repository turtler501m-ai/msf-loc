package com.ktis.msp.batch.job.cmn.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.payinfo.service.PayInfoService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class PayInfoKtExcelExecutor extends BaseSchedule  {
	
	@Autowired
	private PayInfoService payInfoService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("KT연동동의서 조회결과 엑셀 저장 START.");
			LOGGER.debug("Return Vo [PayInfoVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			payInfoService.selectPayInfoKtListExcel(batchCommonVO);
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
