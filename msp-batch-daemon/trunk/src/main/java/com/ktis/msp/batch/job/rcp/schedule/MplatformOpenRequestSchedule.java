package com.ktis.msp.batch.job.rcp.schedule;

import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.MplatformOpenResVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.MplatformOpenService;
import com.ktis.msp.batch.job.rcp.rcpmgmt.vo.MplatformOpenReqVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import java.util.List;

/**
 * Mplatform 개통 후 신청정보 MSP 처리
 * 
 */
@Component
public class MplatformOpenRequestSchedule extends BaseSchedule {
	
	@Autowired
	private MplatformOpenService openService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${mplatformOpenRequestSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}

	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		int procCnt = 0;
		
		try {
			LOGGER.info("개통간소화 신청정보 처리 START");
			batchStart(batchCommonVO);
			
			// ========== BATCH MAIN ==================
			MplatformOpenReqVO searchVO = new MplatformOpenReqVO();

			List<MplatformOpenResVO> procList = openService.getOpenRequestProcList(searchVO);
			
			for (MplatformOpenResVO vo : procList) {
				// OSST 개통후 신청정보 처리
				procCnt = procCnt + openService.setOpenRequestProc(vo);
			}
			
			/**
			 * [SRM18113000814] ATM즉시개통고객대상 단체상해보험가입을 위한 서식지합본 전산개발요청
			 */
			openService.setAtmOpenOnOff();
			
			/**
			 * TOSS 개통시 스캔ID 정보가 없으므로 해당 계약에 대하여 ON_OFF_TYPE UPDATE
			 */
			openService.setTossOpenOnOff();
			
			/**
			 * 2022.11.22.wooki
			 * KMV 개통시 서식지 정보가 없으므로 해당 계약에 대하여 ON_OFF_TYPE UPDATE
			 */
			openService.setKmvOpenOnOff();
			
			batchCommonVO.setExecCount(procCnt);
			// ========== BATCH MAIN ==================
			
			batchEnd(batchCommonVO);
			LOGGER.info("개통간소화 신청정보 처리 END");
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
}