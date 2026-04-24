package com.ktis.msp.batch.job.rcp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.cantransfermgmt.service.CanTransferService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
/**
 * 명의도용주장 IP 설정 고객 데이터 삭제
 */
@Component
public class ReuseRipDeleteSchedule extends BaseSchedule {
	
	@Autowired
	private CanTransferService canTransferService;

	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${reuseRipDeleteSchedule}")
	public void schedule() throws MvnoServiceException {
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
	}
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		int procCnt = 0;
		
		try {
			batchStart(batchCommonVO);
			
			LOGGER.debug("=================================================================");
			LOGGER.debug("명의도용주장 IP 설정 고객 데이터 삭제 START.");
			LOGGER.debug("=================================================================");

			// ========== BATCH MAIN ==================
			procCnt = canTransferService.deleteReuseRipData();
			batchCommonVO.setExecCount(procCnt);
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
