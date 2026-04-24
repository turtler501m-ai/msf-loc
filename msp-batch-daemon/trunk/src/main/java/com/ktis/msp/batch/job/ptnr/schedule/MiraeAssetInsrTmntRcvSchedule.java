package com.ktis.msp.batch.job.ptnr.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.MiraeAssetService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PointFileFTPService;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo.PointFileVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 미래에셋 광고 제휴 보험 해지 수신 스케줄러
 */

@Component
public class MiraeAssetInsrTmntRcvSchedule extends BaseSchedule {
	
	/**
	 * 
	 */
	@Autowired
	private MiraeAssetService assetService;
	
	@Autowired
	private PointFileFTPService ftpService;
	
	/**
	 * download Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${miraeAssetInsrTmntRcvSchedule}")
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
			
			PointFileVO pointFileVO = assetService.regPointFileInfo(PointConstants.MIRAE_IF_04, PointConstants.TRADE_CODE04, PointConstants.RCV_BIZ_CD);
			
			ftpService.downloadPointFile(PointConstants.STR_MIRAE, pointFileVO.getFileNm());
			
			assetService.readFile(pointFileVO.getFileNm());
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
	
}

