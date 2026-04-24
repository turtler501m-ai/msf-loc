package com.ktis.msp.batch.job.rcp.executor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.selfhpc.service.SelfHpcMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class RcpSelfHpcListExcelExecutor extends BaseSchedule  {
	
	@Autowired
	private SelfHpcMgmtService selfHpcMgmtService;
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("010셀프개통 대상 리스트 엑셀 START.");
			LOGGER.debug("Return Vo [SelfHpcVO] = {}", batchCommonVO.getExecParam());
			LOGGER.debug("=================================================================");
			
			selfHpcMgmtService.getRcpSelfHpcListExcel(batchCommonVO);
			
			//배치 성공시 
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
