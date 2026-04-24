package com.ktis.msp.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.service.BatchCommonService;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

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
		
		if(!e.getWrappedException().getClass().equals(MvnoServiceException.class)) {
			e.setMessage(e.getMessage() + " ==> " + e.getCause());
		}
		LOGGER.error(e.getMessage());
		batchVO.setStatCd(BatchConstants.STAT_ERR);
		batchVO.setErrCd(e.getMessageKey());
		batchVO.setErrMsg(e.getMessage());
		
		batchCommonService.updateBatchExecHst(batchVO);
	}
	
	public String execute(BatchCommonVO batchCommonVO) throws MvnoServiceException {
		return null;
	}
	
}
