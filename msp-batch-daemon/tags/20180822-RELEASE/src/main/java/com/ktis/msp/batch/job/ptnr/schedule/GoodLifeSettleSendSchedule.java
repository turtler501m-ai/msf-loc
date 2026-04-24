package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.GoodLifeSettleService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 좋은라이프정산송신
 * IF_NO = GOODLIFE_003
 */

@Component
public class GoodLifeSettleSendSchedule extends BaseSchedule {
	
	@Autowired
	private GoodLifeSettleService gdService;
	
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
//	@Scheduled(cron="${goodLifeSettleSendSchedule}")
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
			LOGGER.info("--- 좋은라이프 정산정보 파일생성 및 FTP 업로드 START ---");
			
			// 좋은라이프 가입자정보 파일 생성 및 FTP 업로드
			gdService.saveGoodLifeSettleFile(PointConstants.STR_GOODLIFE, PointConstants.GOODLIFE_003);
			
			// ========== BATCH MAIN ==================
			
			LOGGER.info("--- 좋은라이프 정산정보 파일생성 및 FTP 업로드 END ---");
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
	
}

