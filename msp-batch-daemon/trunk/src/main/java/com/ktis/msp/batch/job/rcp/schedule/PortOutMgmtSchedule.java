package com.ktis.msp.batch.job.rcp.schedule;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.jcraft.jsch.Logger;
import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.portoutmgmt.service.PortOutMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * MNP 해지고객의 해지 후 사업자 정보
 */
@Component
public class PortOutMgmtSchedule extends BaseSchedule {
	
	@Autowired
	private PortOutMgmtService portOutMgmtService;
	
	/**
	 * Job 실행
	 * 청구별수납항복 자료생성
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${portOutMgmtSchedule}")
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
	        
	        cal.add(cal.DATE , -1);
	        String date = sdf.format(cal.getTime()).substring(0,8);
			
			param.put("i_DATE", date);
			
			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));
			
			//대리점 프로시저 서비스 호출			
			portOutMgmtService.callPortOutInfo(param);
			
			// ========== BATCH MAIN ==================
			batchCommonVO.setExecCount(Integer.parseInt((String)param.get("O_CNT")));
			batchCommonVO.setErrCd((String) param.get("O_CODE"));
			batchCommonVO.setErrMsg((String) param.get("O_MSG"));
			
			LOGGER.debug("처리건수===" + batchCommonVO.getExecCount());
			
			//20200106 처리건수가 0건이면 에러
			if(batchCommonVO.getExecCount() < 1){
				batchCommonVO.setErrCd("9999");
			}
			
			if(!batchCommonVO.getErrCd().equals("0000")){
			    throw new MvnoServiceException(batchCommonVO.getErrCd(), batchCommonVO.getErrMsg());
			} else {
			    batchEnd(batchCommonVO);
			} 
			
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

