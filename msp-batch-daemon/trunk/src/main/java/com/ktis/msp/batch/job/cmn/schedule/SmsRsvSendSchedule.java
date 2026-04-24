package com.ktis.msp.batch.job.cmn.schedule;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.cmn.smsmgmt.service.SmsRsvSendService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * Sms 발송 Retry 체크
 * 오전 8시에서 오후 9시 까지
 * 일단 2시간마다로...
 */
@Component
public class SmsRsvSendSchedule extends BaseSchedule {
	
	@Autowired
	private SmsRsvSendService smsRsvSendService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${smsRsvSendSchedule}")
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
			LOGGER.info("--- 대량문자예약발송 시작 ---");
			int procCnt = smsRsvSendService.smsRsvSend();
			
			batchCommonVO.setExecCount(procCnt);
			LOGGER.info("--- 대량문자예약발송 종료 ---");
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

