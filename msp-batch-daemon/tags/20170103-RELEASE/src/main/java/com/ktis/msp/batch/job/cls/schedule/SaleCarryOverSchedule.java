package com.ktis.msp.batch.job.cls.schedule;

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
import com.ktis.msp.batch.job.cls.clsacntclosemgmt.service.ClsAcntCloseService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 매출이월 스케줄러
 * 매월 1일 01시에 실행되며, 전월 수납정보를 당월 정보로 이월시킨다.
 */
@Component
public class SaleCarryOverSchedule extends BaseSchedule {
	
	@Autowired
	private ClsAcntCloseService schdService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${saleCarryOverSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	@SuppressWarnings("static-access")
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
				
				String workYm = sdf.format(cal.getTime()).substring(0,6);
				
				param.put("WORK_YM", workYm);
			}
			
			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));
			
			schdService.setSaleCarryOver(param);
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

