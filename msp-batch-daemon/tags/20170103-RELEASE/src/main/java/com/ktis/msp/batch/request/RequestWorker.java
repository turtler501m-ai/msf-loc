package com.ktis.msp.batch.request;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.common.ApplicationContextProvider;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.request.mapper.RequestMapper;

public class RequestWorker implements Callable<String> {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	private final ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
	
	private final RequestMapper requestMapper;
	
	private final BatchCommonVO batchCommonVO;
	
	public RequestWorker(BatchCommonVO batchCommonVO) {
		this.batchCommonVO = batchCommonVO;
		requestMapper = (RequestMapper) applicationContext.getBean("requestMapper");
	}
	
	public String call() throws MvnoServiceException {
		
		String result = BatchConstants.BATCH_RESULT_OK;
		LOGGER.info("Thread [{}] RequestWorker Start.. JOB ID [{}], REQ ID [{}]"
						, Thread.currentThread().getId(), batchCommonVO.getBatchJobId(), batchCommonVO.getBatchReqId());
		
		batchCommonVO.setUserId(BatchConstants.BATCH_USER_ID);
		batchCommonVO.setExecType(BatchConstants.BATCH_TYPE_ONE_TIME);
		
		// CMN_BATCH_REQUEST 배치실행여부를 'Y'로 변경.
		requestMapper.updateRequest(batchCommonVO);
		
		LOGGER.info("CMN_BATCH_REQUEST Update.. JOB ID [{}], REQ ID [{}]", batchCommonVO.getBatchJobId(), batchCommonVO.getBatchReqId());
		
		String execService = batchCommonVO.getExecService();
		
		if(execService.isEmpty()) {
			LOGGER.error("Table CMN_BATCH_INFO Column EXEC_SERVICE IS EMPTY.. JOB ID [{}], REQ ID [{}]", batchCommonVO.getBatchJobId(), batchCommonVO.getBatchReqId());
			result = BatchConstants.BATCH_RESULT_NOK;
		} else {
			String serviceName2 = execService.substring(1);
			String serviceName1 = (execService.substring(0, 1)).toLowerCase();
			execService = serviceName1 + serviceName2;
			BaseSchedule service = (BaseSchedule) applicationContext.getBean(execService, BaseSchedule.class);
			
			result = service.execute(batchCommonVO);
		}
		
		return result;
	}
}
