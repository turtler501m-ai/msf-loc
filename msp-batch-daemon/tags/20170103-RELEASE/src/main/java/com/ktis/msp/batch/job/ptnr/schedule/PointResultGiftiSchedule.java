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
 * 기프티쇼 포인트적립 결과 확인 스케줄러
 * 
 * 1.제휴사 포인트 적립에 대한 결과 파일을 FTP 서버에서 다운로드한다.
 * 2.다운로드한 파일을 읽어서 결과를 업데이트 한다.
 * 매월 스케줄러가 실행된다
 * 
 */
@Component
public class PointResultGiftiSchedule extends BaseSchedule {
	
	@Autowired
	private PointService pointService;
	
	/**
     * 생성자
     */
	public PointResultGiftiSchedule() {
//		LOGGER.debug("Modified Date: " + MODDATE + ", Desc: " + MODDESC);
	}
		
	/**
	 * download Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${pointResultGiftiSchedule}")
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
			
			// 결과 파일 다운로드
			pointService.downloadPointFile(PointConstants.STR_GIFTI, "");
			
			//  파일 읽기
			pointService.readFile(PointConstants.STR_GIFTI, "");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

