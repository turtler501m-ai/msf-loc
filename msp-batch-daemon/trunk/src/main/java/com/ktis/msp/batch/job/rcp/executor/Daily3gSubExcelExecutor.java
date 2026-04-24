package com.ktis.msp.batch.job.rcp.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.Daily3gSubService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class Daily3gSubExcelExecutor extends BaseSchedule  {
	
	@Autowired
	private Daily3gSubService daily3gSubService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("3G 유지가입자 현황 엑셀 START.");
			LOGGER.debug("Return Vo [SelfHpcVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			daily3gSubService.getDaily3gSubListExcel(batchCommonVO);
			
			//배치 성공시 
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
