package com.ktis.msp.batch.job.rcp.schedule;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.UsimDlvryCanService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UsimDlvryCanTransferSchedule extends BaseSchedule {

    @Autowired
    private UsimDlvryCanService usimDlvryCanService;

    /**
     * Job 실행
     * 유심 구매 고객 정보 해지 이관
     * @throws MvnoServiceException
     */
    @Scheduled(cron="${usimDlvryCanTransferSchedule}")
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
            Map<String, Integer> resultMap = usimDlvryCanService.procUsimDlvryCanTransfer();

            int usimDlvryCnt = resultMap.get("usimDlvryCnt");
            int usimDlvryNowCnt = resultMap.get("usimDlvryNowCnt");

            batchCommonVO.setExecCount(usimDlvryCnt + usimDlvryNowCnt);
            batchCommonVO.setRemrk("택배 " + usimDlvryCnt + "건, 바로배송 " + usimDlvryNowCnt + "건 처리");
            // ========== BATCH MAIN ==================

            batchEnd(batchCommonVO);
        } catch (MvnoServiceException e) {
            result = BatchConstants.BATCH_RESULT_NOK;
            batchError(batchCommonVO, e);
        }

        return result;
    }
}
