package com.ktis.msp.batch.job.dis.schedule;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.service.DisTrgFnlService;
import com.ktis.msp.batch.job.dis.dismgmt.service.DisUtilService;
import com.ktis.msp.batch.job.dis.dismgmt.vo.DisVO;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


/**
 * 평생할인 부가서비스 가입 대상 최종 수집
 */
@Component
public class DisTrgFnlSchedule extends BaseSchedule {

    @Autowired
    private DisTrgFnlService disTrgFnlService;
    
    @Autowired
    private DisUtilService disUtilService;

    /**
     * Job 실행
     * @throws MvnoServiceException
     */
//    @Scheduled(cron="${disTrgFnlSchedule}")
    public void schedule() throws MvnoServiceException {
        BatchCommonVO batchCommonVO = new BatchCommonVO();
        this.execute(batchCommonVO);
    }

    @Override
    public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        String result = BatchConstants.BATCH_RESULT_OK;
        int procCnt = 0; // 평생할인 프로모션 처리대상 insert cnt (MSP_DIS_TRG_FNL)

        try {

            batchStart(batchCommonVO);
            LOGGER.info("## 평생할인 부가서비스 가입 대상 최종 수집 START");

            // 1. 프로모션 적용대상 추출 날짜 설정
            DisVO disVo = disUtilService.getStrEndDate(batchCommonVO);
            if(disVo == null) throw new MvnoServiceException("EDIS1001", "날짜설정DisVO에러");

            // 2. 프로모션 적용 대상 추출 > 프로모션 적용대상 상태 update (T: 진행중)
            int trgUpCnt= disTrgFnlService.updateDisTrgMstStatusIng(disVo);

            // 3. 평생할인 프로모션 처리 (MSP_DIS_TRG_FNL_INFO, MSP_DIS_TRG_FNL)
            if(trgUpCnt > 0){
                procCnt= disTrgFnlService.procDisTrgFnl(disVo);
            }
            
            // 4. 평생할인 정책 적용된 고객정보 insert 배치호출 (BATCH4)
            disTrgFnlService.insertBatchCus(disVo);

            LOGGER.info("## 평생할인 부가서비스 가입 대상 최종 수집 End >> {}", procCnt);

            batchCommonVO.setExecCount(procCnt);
            batchEnd(batchCommonVO);

        } catch (MvnoServiceException e) {
            result = BatchConstants.BATCH_RESULT_NOK;
            batchError(batchCommonVO, e);
        }

        return result;
    }

}
