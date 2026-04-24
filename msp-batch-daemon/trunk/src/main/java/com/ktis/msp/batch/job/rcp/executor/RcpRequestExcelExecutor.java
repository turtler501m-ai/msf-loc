package com.ktis.msp.batch.job.rcp.executor;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.RcpMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RcpRequestExcelExecutor extends BaseSchedule {

    @Autowired
    private RcpMgmtService rcpMgmtService;

    @Override
    public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        String result = BatchConstants.BATCH_RESULT_OK;
        try {
            batchStart(batchCommonVO);

            LOGGER.debug("=================================================================");
            LOGGER.debug("신청관리(New) 엑셀 저장 START.");
            LOGGER.debug("Return Vo [RcpRentalListVO] = {}", batchCommonVO.getExecParam());
            LOGGER.debug("=================================================================");

            rcpMgmtService.selectRcpRequestListExcel(batchCommonVO);

            batchEnd(batchCommonVO);
        } catch (MvnoServiceException e) {
            result = BatchConstants.BATCH_RESULT_NOK;
            batchError(batchCommonVO, e);
        }

        return result;
    }
}
