package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.IntmInsrService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PointFileFTPService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 단말보험 정보 연동 파일 생성 스케줄러
 * 매일 2시 생성
 */

@Component
public class IntmInsrMemberSchedule extends BaseSchedule {
	
	@Autowired
	private IntmInsrService insrService;
	
	@Autowired
	private PointFileFTPService ftpService;
	
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${intmInsrMemberSchedule}")
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
			LOGGER.info("--- 단말보험 가입자 연동정보 파일 생성 시작 ---");
			insrService.setIntmInsrMemberList();
			LOGGER.info("--- 단말보험 가입자 연동정보 파일 생성 종료 ---");
			
			LOGGER.info("--- 단말보험 가입자 연동정보 파일 업로드 시작 ---");
			ftpService.uploadPointFile(PointConstants.STR_MOBINS, "");
			ftpService.uploadPointFile(PointConstants.STR_DBINS, "");
			LOGGER.info("--- 단말보험 가입자 연동정보 파일 업로드 종료 ---");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
	
}

