package com.ktis.msp.batch.job.ptnr.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.ptnr.ptnrmgmt.service.PartnerPointSettleService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 제휴사 요금제 사용량 계산 및 포인트 계산 스케줄러
 * 
 * 매월 스케줄러가 실행된다
 * 
 */
@Component
public class PartnerUsgPointSchedule extends BaseSchedule {
	
	@Autowired
	private PartnerPointSettleService pointService;
	
	/**
     * 생성자
     */
	public PartnerUsgPointSchedule() {
//		LOGGER.debug("Modified Date: " + MODDATE + ", Desc: " + MODDESC);
	}
		
	/**
	 * Upload Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${partnerUsgPointSchedule}")
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM", Locale.getDefault());
				Calendar cal = Calendar.getInstance(); 
				cal.add(cal.MONTH, -1);
				
				param.put("USG_YM", sdf.format(cal.getTime()).substring(0,6));
			}
			// 제휴사 포인트 계산
			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));
			
			pointService.callPartnerUsgPoint(param);
			// ========== BATCH MAIN ==================
			
			// 제휴사 사용량 계산 파라미터 처리 개선
			LOGGER.debug("오류메세지 처리 param=" + param);
			
			batchCommonVO.setErrCd((String) param.get("O_CODE"));
			batchCommonVO.setErrMsg((String) param.get("O_MSG"));
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

