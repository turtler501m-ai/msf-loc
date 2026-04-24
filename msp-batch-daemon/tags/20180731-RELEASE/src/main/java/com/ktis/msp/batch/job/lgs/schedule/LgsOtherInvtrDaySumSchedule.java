package com.ktis.msp.batch.job.lgs.schedule;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.lgs.othercompmgmt.service.OtherCompInvService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 *  타사단말 일재고 생성 
 * 
 */
@Component
public class LgsOtherInvtrDaySumSchedule extends BaseSchedule {
	
	@Autowired
	private OtherCompInvService otherCompInvService;
	
//	@Scheduled(cron="${lgsOtherInvtrDaySumSchedule}")
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
			batchCommonVO.setBatchDate(KtisUtil.toStr(new Date(), "yyyyMMdd"));
			int dailyBatchEndCount = batchCommonService.getDailyBatchEndCount(batchCommonVO);
			if(dailyBatchEndCount <= 0) {
				// 타사단말 일재고 생성
				otherCompInvService.makeDayInvtr(batchCommonVO);
			} else {
				batchCommonVO.setRemrk("이미 완료되었습니다.");
			}
			
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}

