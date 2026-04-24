package com.ktis.msp.batch.job.org.executor;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.org.orgmgmt.service.AcenRcpMgmtService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AcenRcpMgmtExcelExecutor extends BaseSchedule {

  @Autowired
  private AcenRcpMgmtService acenRcpMgmtService;

  @Override
  public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

    String result = BatchConstants.BATCH_RESULT_OK;
    try {
      batchStart(batchCommonVO);

      LOGGER.debug("=================================================================");
      LOGGER.debug("A'Cen 자동개통 통계 엑셀 저장 START.");
      LOGGER.debug("Return Vo [AcenRcpMgmtVO] = {}", batchCommonVO.getExecParam());
      LOGGER.debug("=================================================================");

      acenRcpMgmtService.selectAcenRcpMgmtListExcel(batchCommonVO);

      batchEnd(batchCommonVO);
    } catch (MvnoServiceException e) {
      result = BatchConstants.BATCH_RESULT_NOK;
      batchError(batchCommonVO, e);
    }

    return result;
  }

}
