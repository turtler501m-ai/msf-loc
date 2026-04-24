package com.ktis.msp.batch.job.rcp.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.combmgmt.service.CombMgmtService;

import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

@Component
public class CombCustSchedule extends BaseSchedule {

	@Autowired
	private CombMgmtService combMgmtService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${combCustSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	// 결합 승인 프로세스
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		int resultN = 0;
		int resultC = 0;
		try {
			batchStart(batchCommonVO);
			
			LOGGER.info("--- 결합승인상태관리 처리 시작 ---");
			// 신청일 기준 5일 경과 (6일째 처리)
			// 상태값이 승인대기(R) 인 상태
			// 상태값을 미제출(N) 로 변경
			resultN = combMgmtService.setResultCdN();
			
			// 수정일 기준 2일 경과 (3일째 처리)
			// 상태값이 미제출(N) 또는 부적격(B) 인 상태
			// 상태값을 신청취소(C) 로 변경
			resultC = combMgmtService.setResultCdC();
			
			batchCommonVO.setExecCount(resultN+resultC);
			batchCommonVO.setRemrk("미제출 "+resultN+"건, 신청취소 "+resultC+"건 처리");
			
			LOGGER.info("--- 결합승인상태관리 처리 종료 ---");
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}
