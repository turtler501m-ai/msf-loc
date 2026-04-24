package com.ktis.msp.batch.job.dis.schedule;

import com.ktis.msp.batch.job.dis.dismgmt.service.DisUtilService;
import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.service.DisTrgService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 평생할인 정책 적용 대상 수집
 */
@Component
public class DisTrgSchedule extends BaseSchedule {
	
	@Autowired
	private DisTrgService disTrgService;

	@Autowired
	private DisUtilService disUtilService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
//	@Scheduled(cron="${disTrgSchedule}")
	public void schedule() throws MvnoServiceException {
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		this.execute(batchCommonVO);
		
	}
	
	/**
	 * @Description : 평생할인 정책 적용 대상 수집
	 * @Param : BatchCommonVO
	 * @Return : String
	 * @Author : wooki
	 * @CreateDate : 2023.10.11
	 */
	@Override
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		try {

			batchStart(batchCommonVO);
			LOGGER.info("====== 평생할인 정책 적용 대상 수집 START ======");

			//1. 날짜 설정
			DisVO disVo = disUtilService.getStrEndDate(batchCommonVO);
			if(disVo == null) throw new MvnoServiceException("EDIS1001", "날짜설정DisVO에러");

			//2. MSP_DIS_TRG_DTL 에서 대상 추출하여 MSP_DIS_TRG_MST에 넣기
			int cnt = disTrgService.setDisTrgMst(disVo);
			
			LOGGER.info("====== 평생할인 정책 적용 대상 수집 END ======{}", cnt);
			
			batchCommonVO.setExecCount(cnt); //실행카운트 set
			batchEnd(batchCommonVO);

		}catch(MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
	}
}