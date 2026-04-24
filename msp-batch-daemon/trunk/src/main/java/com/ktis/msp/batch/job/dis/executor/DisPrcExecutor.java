package com.ktis.msp.batch.job.dis.executor;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.dis.dismgmt.service.DisPrcService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.common.JacksonUtil;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DisPrcExecutor extends BaseSchedule {

    @Autowired
    private DisPrcService disPrcService;


    /**
     * 평생할인 프로모션 부가서비스 가입 배치
     * @param batchCommonVO
     * @return String
     */
    @Override
    public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {

        String result = BatchConstants.BATCH_RESULT_OK;
        int procCnt = 0;

        try {

            batchStart(batchCommonVO);
            LOGGER.info("## 평생할인 프로모션 부가서비스 가입 배치 실행 START");

            String trgFnlSeq = (String) JacksonUtil.makeMapFromJson(batchCommonVO.getExecParam()).get("trgFnlSeq");
            if(!"".equals(StringUtil.NVL(trgFnlSeq, ""))){
                LOGGER.info("#### 평생할인 프로모션 부가서비스 FNL SEQ >> {}", trgFnlSeq);
                procCnt = disPrcService.addDisPrcSoc(trgFnlSeq);
            }

            LOGGER.info("## 평생할인 프로모션 부가서비스 가입 배치 실행 End >> {}", procCnt);

            batchCommonVO.setExecCount(procCnt);
            batchEnd(batchCommonVO);

        } catch (MvnoServiceException e) {
            result = BatchConstants.BATCH_RESULT_NOK;
            batchError(batchCommonVO, e);
        }
        return result;
    }


}
