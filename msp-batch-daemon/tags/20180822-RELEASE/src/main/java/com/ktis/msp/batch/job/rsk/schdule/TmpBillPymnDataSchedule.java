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
import com.ktis.msp.batch.job.rsk.statmgmt.vo.StatMgmtVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 일마감집계
 */
@Component
public class TmpBillPymnDataSchedule extends BaseSchedule {
	
	@Autowired
	private StatMgmtService statService;
	
	/**
	 * Job 실행
	 * 청구별수납항복 자료생성
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${tmpBillPymnDataSchedule}")
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
			LOGGER.debug("=================================================================");
			LOGGER.debug("청구항목별수납자료 생성 START.");
			LOGGER.debug("=================================================================");
			Map<String, Object> param = new HashMap<String, Object>();
			
			StatMgmtVO statMgmtVO = new StatMgmtVO();
			
			SimpleDateFormat day = new SimpleDateFormat("dd", Locale.getDefault());
			SimpleDateFormat yearMonth = new SimpleDateFormat("yyyyMM", Locale.getDefault());
			Calendar cal = Calendar.getInstance(); 
			//cal.add(cal.DATE, -1);
			String today = day.format(cal.getTime()).substring(0,2);
			String strym = yearMonth.format(cal.getTime()).substring(0,6);
			String endym = yearMonth.format(cal.getTime()).substring(0,6);
			
			LOGGER.info("오늘날짜 = " +  today);
			LOGGER.info("이번년월 = " +  strym);
			
			//배치실행일자가 14일이전이면 배치실행이력 기간을 1일부터 14일로 set
			if(Integer.parseInt(today) < 14 ){
				statMgmtVO.setOprtStrtDt(strym += "01" );	//배치실행시작일자
				statMgmtVO.setOprtEndDt(endym += "14");		//배치실행종료일자
				LOGGER.info("배치실행시작일자 >> 이번년월  + 1 = " +  statMgmtVO.getOprtStrtDt());
				LOGGER.info("배치실행종료일자 >> 이번년월  + 14 = " +  statMgmtVO.getOprtEndDt());
			}else{
				statMgmtVO.setOprtStrtDt(strym += "15");	//배치실행시작일자
				statMgmtVO.setOprtEndDt(endym += "30");		//배치실행종료일자
				LOGGER.info("배치실행시작일자 >> 이번년월  + 15 = " +  statMgmtVO.getOprtStrtDt());
				LOGGER.info("배치실행종료일자 >> 이번년월  + 30 = " +  statMgmtVO.getOprtEndDt());
			}
			
			statMgmtVO.setOprtSctnCd("BILL");				//청구수납배치실행코드
			statMgmtVO.setOprtStatCd("0000");			//성공
			
			LOGGER.info("이번년월  + 1 = " +  statMgmtVO.getOprtStrtDt());
			LOGGER.info("이번년월  + 14 = " +  statMgmtVO.getOprtEndDt());
			
			statService.callTmpBillPymnData(statMgmtVO);
			// ========== BATCH MAIN ==================
			
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

