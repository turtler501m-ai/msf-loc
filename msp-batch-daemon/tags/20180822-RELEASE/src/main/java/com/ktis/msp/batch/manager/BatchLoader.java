package com.ktis.msp.batch.manager;

import org.apache.logging.log4j.core.config.Configurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericXmlApplicationContext;

import com.ktis.msp.base.BaseSchedule;
import com.ktis.msp.base.exception.MvnoServiceException;
import com.ktis.msp.batch.manager.common.ApplicationContextProvider;
import com.ktis.msp.batch.manager.common.BatchConstants;
import com.ktis.msp.batch.manager.vo.BatchCommonVO;

public class BatchLoader {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(BatchLoader.class);
	
	private final ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
	
	/*
	 * args1 - batch.server.type (local, dev, live)
	 * args2 - batch.run.type (schedule, onetime)
	 * args3 - batch.load.package (bnd, cls, lgs, org, ptnr)
	 * args4 - 실행할 스케줄러 (BondSchedule, LgsInvtrDaySumSchedule, .....)
	 * args5 - 실행시필요한 파라미터
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) throws MvnoServiceException {
		
		// local test용
		if(args == null || args.length <= 0) {
			System.setProperty("batch.server.type", "local");
		} else {
			System.setProperty("batch.server.type", args[0]);
		}
		
		Configurator.initialize(null, "classpath:logs/"+System.getProperty("batch.server.type")+"/log4j2.xml");
		LOGGER.debug("Starting Logging.....");
		
		// local test용
		if(args == null || args.length <= 1) {
			System.setProperty("batch.run.type", "schedule");
		} else {
			System.setProperty("batch.run.type", args[1]);
		}
		
		String launchFile = "/launch-context.xml";
		if("onetime".equals(System.getProperty("batch.run.type"))) {
			launchFile = "/launch-manager-context.xml";
			
			// local test용
			if(args == null || args.length <= 2) {
				System.setProperty("batch.load.package", "test");
			} else {
				System.setProperty("batch.load.package", args[2]);
			}
		} else if("test".equals(System.getProperty("batch.run.type"))) {
			launchFile = "/launch-context-test.xml";
			
			// local test용
			if(args == null || args.length <= 2) {
				System.setProperty("batch.load.package", "org");
			} else {
				System.setProperty("batch.load.package", args[2]);
			}
		}
		
		@SuppressWarnings("unused")
		ApplicationContext ctx = new GenericXmlApplicationContext(launchFile);
		LOGGER.debug("Loading Context..... ");
		
		if("onetime".equals(System.getProperty("batch.run.type"))) {
			BatchLoader loader = new BatchLoader();
			loader.execute(args);
		}
		
		
	}
	
	private void execute(String[] args) throws MvnoServiceException {
		
		String execService = "";
		// local test용
		if(args == null || args.length <= 3) {
			execService = "SampleSchedule";
		} else {
			execService = args[3];
		}
		
		String serviceName2 = execService.substring(1);
		String serviceName1 = (execService.substring(0, 1)).toLowerCase();
		execService = serviceName1 + serviceName2;
		
		BaseSchedule service = (BaseSchedule) applicationContext.getBean(execService, BaseSchedule.class);
		
		BatchCommonVO batchCommonVO = new BatchCommonVO();
		batchCommonVO.setExecType(BatchConstants.BATCH_TYPE_ONE_TIME);
		batchCommonVO.setExecTypeCd(BatchConstants.BATCH_EXEC_TYPE_REQ);
		
		if(args != null && args.length > 4) {
			batchCommonVO.setExecParam(args[4]);
		}
		service.execute(batchCommonVO);
		
	}

}
