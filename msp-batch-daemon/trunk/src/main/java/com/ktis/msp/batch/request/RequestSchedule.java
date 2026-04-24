package com.ktis.msp.batch.request;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;
import com.ktis.msp.batch.request.mapper.RequestMapper;

@Component
public class RequestSchedule {
	
	private final Logger LOGGER = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private RequestMapper requestMapper;
	
	@Scheduled(cron="${requestSchedule}")
//	@Scheduled(fixedDelay=1000000)
	@Async
	public void executeJob() throws MvnoServiceException, InterruptedException {
		LOGGER.info("RequestSchedule executeJob START!!!!");
		
		/*
		 * 1. CMN_BATCH_REQUEST 에 요청건이 있는지 확인.
		 * 2. 조회된 내용을 실행시킴.
		 */
		
		List<BatchCommonVO> targetList = new ArrayList<BatchCommonVO>();
		
		try {
			targetList = requestMapper.getRequestList();
		} catch(Exception e) {
			LOGGER.error("Get RequestList ERROR.... {}", e.getMessage());
			throw new MvnoServiceException("EREQ1001", e);
		}
		
		if(targetList.isEmpty()) {
			LOGGER.info("Batch Request is Empty... !!!!");
		} else {
			LOGGER.info("Batch Request Count[{}]", targetList.size());
			
			List<Future<String>> futureList = new ArrayList<Future<String>>();
			
			// target list를 thread 실행한다.
			ExecutorService executorService = Executors.newCachedThreadPool();
			
			for(BatchCommonVO batchCommonVO : targetList) {
				
				Future<String> future = executorService.submit(new RequestWorker(batchCommonVO));
				
				futureList.add(future);
				
			}
			
			List<Future<String>> resultList = new ArrayList<Future<String>>();
			// thread 실행 결과를 받아서 확인.
			// 결과 값으로 별도로 post action 할 것이 있으면... 
			// ERR 에 대한 처리는 어떻게 할 것인지...
			while(true) {
				
				for(Future<String> future : futureList) {
					
					if(future.isDone()) {
						//LOGGER.debug("future is Done....");
						resultList.add(future);
					} 
					
				}
				
				if(resultList.size() == futureList.size()) {
					break;
				} else {
					Thread.sleep(1000);
				}
			}
			
			executorService.shutdown();
			
		}
	}
}
