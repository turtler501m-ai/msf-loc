package com.ktis.msp.batch.job.ptnr.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.PointConstants;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.GoodLifeSettleService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;


/**
 * 좋은라이프 지급여부 수신 스케줄러
 * 
 * 1.FTP 서버에서 GOODLIFE_004_YYYYMM.DAT 다운로드한다.
 * 2.다운로드한 파일을 읽어서 결과를 업데이트 한다.
 * 
 */
@Component
public class GoodLifeGetSettleSchedule extends BaseSchedule {
	
	@Autowired
	private GoodLifeSettleService goodlifeService;
	
	/**
     * 생성자
     */
	public GoodLifeGetSettleSchedule() {
		
	}
		
	/**
	 * download Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
//	@Scheduled(cron="${goodLifeGetSettleSchedule}")
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
			Map<String, Object> param = new HashMap<String, Object>();
			
			if(batchCommonVO.getExecParam() != null && !batchCommonVO.getExecParam().isEmpty()) {
				param = JacksonUtil.makeMapFromJson(batchCommonVO.getExecParam());
			} else {
				SimpleDateFormat formatter = new SimpleDateFormat("yyyyMM", Locale.KOREA);
				Date currentTime = new Date();
				String nowYm = formatter.format(currentTime);
				
				param.put("ifYm", nowYm);
			}
			// 제휴사 포인트 계산
			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));
			
			param.put("ifNo", PointConstants.GOODLIFE_004);
			
			String rmk = goodlifeService.checkGoodlifeFile(param);
			
			// ========== BATCH MAIN ==================
			
			batchCommonVO.setRemrk(rmk);
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

