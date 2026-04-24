package com.ktis.msp.batch.job.rsk.schdule;

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
import com.ktis.msp.batch.job.rsk.statmgmt.service.StatMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 일마감집계
 */
@Component
public class DailyCloseSttcSchedule extends BaseSchedule {
	
	@Autowired
	private StatMgmtService statService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${dailyCloseSttcSchedule}")
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
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
				Calendar cal = Calendar.getInstance(); 
				cal.add(cal.DATE, -1);
				
				String workDt = sdf.format(cal.getTime()).substring(0,8);
				
				param.put("WORK_DT", workDt);
			}
			
			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));
			
			statService.setDailyCloseSttc(param);
			// ========== BATCH MAIN ==================
			
			LOGGER.debug("프로시져 오류메세지 처리 param=" + param);
			
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

