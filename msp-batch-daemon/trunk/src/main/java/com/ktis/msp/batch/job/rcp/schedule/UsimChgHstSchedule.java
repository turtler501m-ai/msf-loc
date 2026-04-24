package com.ktis.msp.batch.job.rcp.schedule;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.statsmgmt.service.UsimChgHstService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsimChgHstSchedule extends BaseSchedule {

    @Autowired
    private UsimChgHstService usimChgHstService;

    /**
     * Job 실행
     * 유심 변경 현황 이력 생성
     * @throws MvnoServiceException
     */
    @Scheduled(cron="${usimChgHstSchedule}")
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
            LOGGER.info("유심 변경 현황 이력 생성 START");
            List<String> contNumList = usimChgHstService.selectUsimChgList();
            
            for(String contractNum : contNumList) {
            	
            	usimChgHstService.insertUsimChgHst(contractNum);
            	
            	procCnt++;
            }
            
            batchCommonVO.setExecCount(procCnt);
            LOGGER.info("유심 변경 현황 이력 생성 END");
            // ========== BATCH MAIN ==================

            batchEnd(batchCommonVO);
        } catch (MvnoServiceException e) {
            result = BatchConstants.BATCH_RESULT_NOK;
            batchError(batchCommonVO, e);
        }

        return result;
    }
}
