package com.ktis.msp.batch.job.lgs.schedule;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.KtisUtil;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.lgs.lgsbatchmgmt.service.InvtrDaySumService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 *  일재고 생성 
 * 
 */
@Component
public class LgsInvtrDaySumSchedule extends BaseSchedule {
	
	@Autowired
	private InvtrDaySumService invtrDaySumService;
	
//	@Scheduled(cron="${lgsInvtrDaySumSchedule}")
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
				String sInvtrDt = KtisUtil.toStr(new Date(), "yyyyMMdd");
				param.put("stdDt", sInvtrDt);
			}
			
			batchCommonVO.setExecParam(JacksonUtil.makeJsonFromMap(param));
			
			batchCommonVO.setBatchDate(KtisUtil.toStr(new Date(), "yyyyMMdd"));
			int dailyBatchEndCount = batchCommonService.getDailyBatchEndCount(batchCommonVO);
			if(dailyBatchEndCount <= 0) {
				// 일재고 생성 P_LGS_INVTR_DAY_SUM
				invtrDaySumService.createTodayInvtrDayMst(param, batchCommonVO);
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

