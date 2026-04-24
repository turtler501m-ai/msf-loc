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
 * 보상서비스 부가서비스 가입/해지 연동
 * 가입/해지에 대한 이력 찾은 후 MSP_RWD_MEMBER 테이블 정보 변경 처리
 * 매일 2시 30분 생성
 */

@Component
public class RwdAddSvcSchedule extends BaseSchedule {
	
	@Autowired
	private PointFileFTPService ftpService;
		
	@Autowired
	private RwdService rwdService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${rwdAddSvcSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	/**
	 * 보상서비스 부가서비스 가입/해지 연동
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================	
			LOGGER.info("--- 보상서비스 부가서비스 가입/해지/변경 생성 ---");
			rwdService.setRwdAddSvcList();
			
			LOGGER.info("--- 보상서비스 부가서비스 가입/해지/변경 파일 업로드 ---");
			ftpService.uploadPointFile(PointConstants.STR_WINIA, PointConstants.WINIA_002);
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
	}
}