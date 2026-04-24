package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PointService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 제주항공 포인트정산 업로드 스케줄러
 * 
 * 1.제주항공에서 포인트 적립에 대한 결과 파일을 FTP 서버에서 다운로드한다.
 * 2.다운로드한 파일을 읽어서 결과를 업데이트 한다.
 * 매월 스케줄러가 실행된다
 * 
 */

@Component
public class PointSendJejuairSchedule extends BaseSchedule {
	
	/**
	 * 
	 */
	@Autowired
	private PointService pointService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${pointSendJejuairSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	
	/**
	 * 
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			LOGGER.info("--- 제주항공 -- 포인트정산파일 저장 및 업로드 시작 ---");
			
			// 제주항공 포인트 파일 저장
			pointService.savePointFile(PointConstants.STR_JEJUAIR);
			
			// 저장된 파일 업로드
			pointService.uploadPointFile(PointConstants.STR_JEJUAIR,"");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
	
}

