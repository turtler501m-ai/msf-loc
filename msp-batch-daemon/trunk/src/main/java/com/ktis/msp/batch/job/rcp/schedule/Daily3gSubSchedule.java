package com.ktis.msp.batch.job.rcp.schedule;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.job.rcp.rcpmgmt.service.Daily3gSubService;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

import egovframework.rte.fdl.property.EgovPropertyService;

/**
 * 3G 유지 가입자 등록
 */
@Component
public class Daily3gSubSchedule extends BaseSchedule {
	
	@Autowired
	private Daily3gSubService daily3gSubService;
	
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;
	
	/**
	 * Job 실행
	 * @throws MvnoServiceException 
	 * @throws Exception
	 */
	@Scheduled(cron="${daily3gSubSchedule}")
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
			LOGGER.info("## 3G 유지 가입자 등록 START");
			
			procCnt = daily3gSubService.insertDaily3gSub();

			LOGGER.info("## 3G 유지 가입자 등록 End >> {}", procCnt);
			batchCommonVO.setExecCount(procCnt);
			batchEnd(batchCommonVO);
			
		} catch (MvnoServiceException e) {
			result = BatchConstants.BATCH_RESULT_NOK;
			batchError(batchCommonVO, e);
		}
		
		return result;
	}
	
}

