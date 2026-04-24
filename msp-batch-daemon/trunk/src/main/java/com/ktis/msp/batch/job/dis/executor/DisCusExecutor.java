package com.ktis.msp.batch.job.dis.executor;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.service.DisCusService;
import com.ktis.msp.batch.job.dis.dismgmt.service.DisUtilService;
import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisCusExecutor extends BaseSchedule {
        
    @Autowired
	private DisCusService disCusService;
	
	@Autowired
	private DisUtilService disUtilService;


	/**
	 * @Description : 평생할인 부가서비스 가입된 대상을 찾아서 MSP_DIS_CUS_HST insert
	 * @Param : BatchCommonVO
	 * @Return : String
	 * @Author : wooki
	 */
	@Override
    public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

		String result = BatchConstants.BATCH_RESULT_OK;
		try {
			
			batchStart(batchCommonVO);
			
			LOGGER.info("====== 고객별 평생할인 부가서비스 가입된 대상을 MSP_DIS_CUS_HST insert START ======");
			
			//1. 날짜 설정
			DisVO disVo = disUtilService.getStrEndDate(batchCommonVO);
			if(disVo == null) throw new MvnoServiceException("EDIS1001", "날짜설정DisVO에러");

			//2. MSP_DIS_CUS_HST insert, MSP_REQUEST_DTL update
			int cnt = disCusService.setDisCustHst(disVo);
			
			LOGGER.info("====== 고객별 평생할인 부가서비스 가입된 대상을 MSP_DIS_CUS_HST insert END ======{}", cnt);
			
			batchCommonVO.setExecCount(cnt); //MSP_DIS_CUS_HST insert count set			
			batchEnd(batchCommonVO);
			
		}catch(MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		return result;
    }


}
