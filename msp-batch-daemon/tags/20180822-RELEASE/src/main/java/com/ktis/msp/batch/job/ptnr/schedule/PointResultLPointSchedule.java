package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PartnerPointSettleService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PointFileFTPService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;


/**
 * 롯데멤버스 포인트적립 결과 확인 스케줄러
 * 
 * 1.제휴사 포인트 적립에 대한 결과 파일을 FTP 서버에서 다운로드한다.
 * 2.다운로드한 파일을 읽어서 결과를 업데이트 한다.
 * 매월 스케줄러가 실행된다
 * 
 */
@Component
public class PointResultLPointSchedule extends BaseSchedule {
	
	@Autowired
	private PartnerPointSettleService pointService;
	
	@Autowired
	private PointFileFTPService ftpService;
	
	/**
     * 생성자
     */
	public PointResultLPointSchedule() {
//		LOGGER.debug("Modified Date: " + MODDATE + ", Desc: " + MODDESC);
	}
		
	/**
	 * download Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${pointResultLPointSchedule}")
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
			ftpService.downloadPointFile(PointConstants.STR_LPOINT, "");
			
			//  파일 읽기
			pointService.readFile(PointConstants.STR_LPOINT, "");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

