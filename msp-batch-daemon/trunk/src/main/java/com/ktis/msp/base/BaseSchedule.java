package com.ktis.msp.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.service.BatchCommonService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.util.StringUtil;

public class BaseSchedule {
	
	@Autowired
	protected BatchCommonService batchCommonService;
	
	String execType = BatchConstants.BATCH_TYPE_SCHEDULE;
	
	protected final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	protected void batchStart(BatchCommonVO batchVO) throws MvnoServiceException {
		
		batchVO.setUserId(BatchConstants.BATCH_USER_ID);
		String serviceName = getClass().getSimpleName();
		batchVO.setExecService(serviceName);
		
//		String batchFlag = "N";
		if(!(BatchConstants.BATCH_TYPE_ONE_TIME).equals(batchVO.getExecType())) {
			batchVO.setExecType(BatchConstants.BATCH_TYPE_SCHEDULE);
			batchVO.setExecTypeCd(BatchConstants.BATCH_EXEC_TYPE_SCHE);
		}
		
//		if("Y".equals(batchFlag)) {
//			String errCode = "ECMN1003";
//			String[] errParam = new String[1];
//			errParam[0] = batchVO.getExecService();
//			throw new MvnoServiceException(errCode, errParam);
//		}
		
		LOGGER.debug("Batch Log Insert===>> [{}]", getClass().getSimpleName());
		batchVO.setStatCd(BatchConstants.STAT_ING);
		batchCommonService.insertBatchExecHst(batchVO);
	}
	
	protected void batchEnd(BatchCommonVO batchVO) throws MvnoServiceException {
		batchVO.setStatCd(BatchConstants.STAT_END);
		batchCommonService.updateBatchExecHst(batchVO);
	}
	
	protected void batchError(BatchCommonVO batchVO, MvnoServiceException e) throws MvnoServiceException {
		try {
			e.setMessage(e.getMessage() + " ==> " + e.getCause());
			LOGGER.error("MessageKey : " + e.getMessageKey());
			LOGGER.error("Message : " + e.getMessage());
		} catch(Exception ex) {
			String strTrace = StringUtil.toTraceStr(e);
			e.setMessage(strTrace);
			LOGGER.error("Message : " + e.getMessage());
		}
		
		try {
			if(e.getMessageKey().length() > 10) {
				e.setMessageKey("9999");
			}
		} catch(Exception ex2) {
			e.setMessageKey("9999");
		}
		
		batchVO.setStatCd(BatchConstants.STAT_ERR);
		batchVO.setErrCd(e.getMessageKey());
		batchVO.setErrMsg(e.getMessage());
		batchCommonService.updateBatchExecHst(batchVO);
	}
	
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		return null;
	}
	
}
