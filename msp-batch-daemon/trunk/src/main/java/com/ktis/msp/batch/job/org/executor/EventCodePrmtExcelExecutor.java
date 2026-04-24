package com.ktis.msp.batch.job.org.executor;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.prmtmgmt.service.EventCodePrmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EventCodePrmtExcelExecutor extends BaseSchedule {

    @Autowired
    private EventCodePrmtService eventCodePrmtService;

    @Override
    public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        String result = BatchConstants.BATCH_RESULT_OK;

        try {
            batchStart(batchCommonVO);

            LOGGER.debug("=================================================================");
            LOGGER.debug("이벤트코드 이력 관리 엑셀 저장 START.");
            LOGGER.debug("Return Vo [EventCodePrmtVO] = {}", batchCommonVO.getExecParam());
            LOGGER.debug("=================================================================");

            eventCodePrmtService.getEventCodePrmtListExcel(batchCommonVO);

            batchEnd(batchCommonVO);
        } catch (MvnoServiceException e) {
            result = BatchConstants.BATCH_RESULT_NOK;
            batchError(batchCommonVO, e);
        }

        return result;
    }
}
