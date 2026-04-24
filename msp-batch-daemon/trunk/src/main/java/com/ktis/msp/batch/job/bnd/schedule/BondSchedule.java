package com.ktis.msp.batch.job.bnd.schedule;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.bnd.bondmgmt.service.BondMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

/**
 * 채권정보스케줄러
 * 
 * 채권정보가 있는 경우 insert를 하고 . 개통월별채권정보에 insert, 보증보험정보에 insert 한다.
 * 
 */
@Component
public class BondSchedule extends BaseSchedule {
	
	private static final String MODDATE = "20150729";
	private static final String MODDESC = "채권정보스케줄러";

	@Autowired
	private BondMgmtService bondMgmtService;
	
	/**
	 * 생성자
	 */
	public BondSchedule() {
		LOGGER.debug("Modified Date: {}, Desc: {}", MODDATE, MODDESC);
	}
		
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${bondSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		Map<String, Object> param = new HashMap<String, Object>();
		
		String result = BatchConstants.BATCH_RESULT_OK;
		
		try {
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			bondMgmtService.callCreateBondMst(param);
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

