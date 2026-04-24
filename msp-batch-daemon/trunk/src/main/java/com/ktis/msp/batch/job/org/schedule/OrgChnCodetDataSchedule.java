package com.ktis.msp.batch.job.org.schedule;

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
import com.ktis.msp.batch.job.org.orgmgmt.service.OrgnChnCodeDataService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * M유통 대리점 및 판매점 조직연동 
 */
@Component
public class OrgChnCodetDataSchedule extends BaseSchedule {
	
	@Autowired
	private OrgnChnCodeDataService orgnChnCodeDataService;
	
	/**
	 * Job 실행
	 * 청구별수납항복 자료생성
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${orgChnCodetDataSchedule}")
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
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
			
			Calendar cal = Calendar.getInstance();
			Calendar today = Calendar.getInstance();
			
			today.add(today.DATE , 0);
			cal.add(cal.DATE, -1);
			
			if(batchCommonVO.getExecParam() != null && !batchCommonVO.getExecParam().isEmpty()) {
				param = JacksonUtil.makeMapFromJson(batchCommonVO.getExecParam());
			} else {
				String date = sdf.format(cal.getTime()).substring(0,8);
				
				param.put("i_DATE", date);
				param.put("O_CODE" , "1000");
			}
			
			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));
				
			//대리점 프로시저 서비스 호출			
			orgnChnCodeDataService.callOrgnChnCodeData(param);
						
			//매월 30일 공통코드 테이블 삭제(전일제외)
			String day = sdf.format(today.getTime()).substring(6,8);
			if(day.equals("30")){
				LOGGER.debug("====================================");
				LOGGER.debug("=========MDS공통코드 데이터 삭제(전일제외)========");
				LOGGER.debug("====================================");
				orgnChnCodeDataService.deleteOrgnChnCodeData(param);
			}
			
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

