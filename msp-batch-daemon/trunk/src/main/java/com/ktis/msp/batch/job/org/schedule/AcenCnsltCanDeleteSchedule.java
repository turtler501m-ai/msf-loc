package com.ktis.msp.batch.job.org.schedule;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.service.AcenCnsltCanService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class AcenCnsltCanDeleteSchedule extends BaseSchedule {

    @Autowired
    private AcenCnsltCanService acenCnsltCanService;

    /**
     * Job 실행
     * A'Cen 일배치 상담이력 고객 민감정보 삭제
     * @throws MvnoServiceException
     * @throws Exception
     */
    @Scheduled(cron="${acenCnsltCanDeleteSchedule}")
    public void schedule() throws MvnoServiceException {
        BatchCommonVO batchCommonVO = new BatchCommonVO();
        this.execute(batchCommonVO);

    }

    @Override
    public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        String result = BatchConstants.BATCH_RESULT_OK;
        int procCnt = 0;

        try {
            batchStart(batchCommonVO);

            // ========== BATCH MAIN ==================
            procCnt = acenCnsltCanService.procAcenCnsltDelete();
            batchCommonVO.setExecCount(procCnt);
            // ========== BATCH MAIN ==================

            batchEnd(batchCommonVO);
        } catch (MvnoServiceException e) {
            result = BatchConstants.BATCH_RESULT_NOK;
            batchError(batchCommonVO, e);
        }

        return result;
    }

}
