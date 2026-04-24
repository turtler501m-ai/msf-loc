package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PointFileFTPService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.RwdService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 보상서비스 가입신청서 결과 정보 연동 받은 후 생성 스케줄러
 * 매일 3시 45분 생성
 */

@Component
public class RwdMemberResSchedule extends BaseSchedule {
	
	@Autowired
	private PointFileFTPService ftpService;
		
	@Autowired
	private RwdService rwdService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${rwdMemberResSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	/**
	 * 보상서비스 가입신청서 처리 결과 생성 스케줄러
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			LOGGER.info("--- 신청 결과 파일 다운로드 시작 ---");
			PointFileVO pointFileVO = rwdService.regRwdFileInfo(PointConstants.STR_WINIA, PointConstants.WINIA_004, "res");
			
			ftpService.downloadPointFile(PointConstants.STR_WINIA, pointFileVO.getFileNm());
			
			// 신청 처리 결과 파일 읽기
			rwdService.rwdReadFile(pointFileVO.getFileNm(), "3");
			LOGGER.info("--- 보상서비스 연동정보 파일 다운로드 종료 ---");
			
			// 보상서비스 가입자 생성
			rwdService.setRwdSubList();
			LOGGER.info("--- 보상서비스 가입자 생성 종료 ---");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
	}
}