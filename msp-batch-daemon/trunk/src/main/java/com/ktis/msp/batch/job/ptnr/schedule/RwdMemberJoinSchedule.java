package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PointFileFTPService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.RwdService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 보상서비스 신청 정보 연동 파일 생성 스케줄러
 * 매일 2시 15분 생성
 */
@Component
public class RwdMemberJoinSchedule extends BaseSchedule {
	
	@Autowired
	private RwdService rwdService;
	
	@Autowired
	private PointFileFTPService ftpService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${rwdMemberJoinSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================	
			LOGGER.info("--- 보상서비스 신청자 리스트 생성 ---");
			rwdService.setRwdMemberList();
			
			LOGGER.info("--- 보상서비스 신청자 파일 업로드 ---");
			ftpService.uploadPointFile(PointConstants.STR_WINIA, PointConstants.WINIA_001);
			
			LOGGER.info("--- 보상서비스 신청자 구매영수증 업로드 ---");
			ftpService.uploadImageFile();
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
	}
}