package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.IntmInsrService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PointFileFTPService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 단말보험 정보 연동 파일 생성 스케줄러
 * 매일 3시 생성
 */

@Component
public class IntmInsrCmpnSchedule extends BaseSchedule {
	
	@Autowired
	private IntmInsrService insrService;
	
	@Autowired
	private PointFileFTPService ftpService;
		
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${intmInsrCmpnSchedule}")
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
			LOGGER.info("--- 단말보험 분손 연동정보 파일 다운로드 시작 ---");
			PointFileVO pointFileVO = insrService.regMovinsFileInfo(PointConstants.STR_MOBINS, PointConstants.MOBINS_003, "res");
			
			ftpService.downloadPointFile(PointConstants.STR_MOBINS, pointFileVO.getFileNm());
			//  분손 파일 읽기
			insrService.readFile(pointFileVO.getFileNm(), "3");
			LOGGER.info("--- 단말보험 분손 연동정보 파일 다운로드 종료 ---");
			
			LOGGER.info("--- 단말보험 전손 연동정보 파일 다운로드 시작 ---");
			pointFileVO = insrService.regMovinsFileInfo(PointConstants.STR_MOBINS, PointConstants.MOBINS_002, "res");
			
			ftpService.downloadPointFile(PointConstants.STR_MOBINS, pointFileVO.getFileNm());
			//  전손 파일 읽기
			insrService.readFile(pointFileVO.getFileNm(), "2");
			LOGGER.info("--- 단말보험 전손 연동정보 파일 다운로드 종료 ---");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
	
}

