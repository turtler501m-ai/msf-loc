package com.ktmmobile.mcp.rate.controller;


import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.rate.service.RateSvc;

@RestController
@EnableScheduling
public class RateController {

    private static final Logger logger = LoggerFactory.getLogger(RateController.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;
    
    @Value("${SERVER_NAME}")
    private String serverName;

	@Autowired
    private RateSvc rateSvc;
	
    @Autowired
    private BathHistService bathHistService;

    @RequestMapping("/rate/{mod}")
    public String rateMod(@PathVariable int mod) {
        int batchMod = mod % 4;
        Map<String, Object> processResult = rateSvc.processRateResChgByTrg(batchMod);

        return "rateMod[" + batchMod + "] send:"+serverName + " success : " + processResult.get("SUCCESS_CNT") + " fail : " + processResult.get("FAIL_CNT");
    }

    /**
     *  요금제 예약변경 후속 처리
     *  @Scheduled(cron="0 30 0 1 * *") //매월 1일 00:30에 실행
     */
    @Scheduled(cron="0 30 0 1 * *")
//    @Scheduled(cron="0 * * * * *")
    @Async
    public void rateBatch0() {

    	logger.info("요금제 예약변경 후속 처리 rateBatch0() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "1200";
        String exeMthd = "rateBatch0";
        final int batchMod = 0;

        boolean runFlag = true; // 배치를 계속 실행할지 확인하는 플래그
        
        try {
            while(runFlag) {
            	//NMCP_RATE_RES_CHG_TRG에 처리할 대상이 있는지 확인(batchMod에 따라 조회대상이 다름)
            	if(!rateSvc.checkRateResChgTrgByMod(batchMod)) {
            		
            		//배치 가동 시간 확인
            		if(rateSvc.checkBatchRunTime()) {
            			Thread.sleep(30000); //30초 대기 후 처음으로
            		} else {
            			runFlag = false;
            		}
            		
            	} else {
            		//NMCP_RATE_RES_CHG_TRG의 대상으로 요금제 예약 변경 후처리 프로세스 진행
            		Map<String, Object> processResult = rateSvc.processRateResChgByTrg(batchMod);
            		
            		successCascnt += (int)(processResult.get("SUCCESS_CNT"));
            		failCascnt += (int)(processResult.get("FAIL_CNT"));
            	}
            }
        } catch(Exception e) {
        	failCascnt++;
    		e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);
                bathHistDto.setExeMthd(exeMthd);
                bathHistDto.setBatchExeDate(batchExeDate);
                bathHistDto.setBatchExeStDate(batchExeStDate);
                bathHistDto.setBatchExeCascnt(batchExeCascnt);
                bathHistDto.setSuccessCascnt(successCascnt);
                bathHistDto.setFailCascnt(failCascnt);
                bathHistDto.setMsgDtl(msgDtl);
                bathHistService.insertHist(bathHistDto);
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }

    /**
     *  요금제 예약변경 후속 처리
     *  @Scheduled(cron="0 31 0 1 * *") //매월 1일 00:31에 실행
     */
    @Scheduled(cron="0 31 0 1 * *")
//    @Scheduled(cron="1 1 * * * *")
    @Async
    public void rateBatch1() {

    	logger.info("요금제 예약변경 후속 처리 rateBatch1() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "1201";
        String exeMthd = "rateBatch1";
        final int batchMod = 1;

        boolean runFlag = true; // 배치를 계속 실행할지 확인하는 플래그
        
        try {
            while(runFlag) {
            	//NMCP_RATE_RES_CHG_TRG에 처리할 대상이 있는지 확인(batchMod에 따라 조회대상이 다름)
            	if(!rateSvc.checkRateResChgTrgByMod(batchMod)) {
            		
            		//배치 가동 시간 확인
            		if(rateSvc.checkBatchRunTime()) {
            			Thread.sleep(30000); //30초 대기 후 처음으로
            		} else {
            			runFlag = false;
            		}
            		
            	} else {
            		//NMCP_RATE_RES_CHG_TRG의 대상으로 요금제 예약 변경 후처리 프로세스 진행
            		Map<String, Object> processResult = rateSvc.processRateResChgByTrg(batchMod);
            		
            		successCascnt += (int)(processResult.get("SUCCESS_CNT"));
            		failCascnt += (int)(processResult.get("FAIL_CNT"));
            	}
            }
        } catch(Exception e) {
        	failCascnt++;
    		e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);
                bathHistDto.setExeMthd(exeMthd);
                bathHistDto.setBatchExeDate(batchExeDate);
                bathHistDto.setBatchExeStDate(batchExeStDate);
                bathHistDto.setBatchExeCascnt(batchExeCascnt);
                bathHistDto.setSuccessCascnt(successCascnt);
                bathHistDto.setFailCascnt(failCascnt);
                bathHistDto.setMsgDtl(msgDtl);
                bathHistService.insertHist(bathHistDto);
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }

    /**
     *  요금제 예약변경 후속 처리
     *  @Scheduled(cron="0 32 0 1 * *") //매월 1일 00:32에 실행
     */
    @Scheduled(cron="0 32 0 1 * *")
//    @Scheduled(cron="2 * * * * *")
    @Async
    public void rateBatch2() {

    	logger.info("요금제 예약변경 후속 처리 rateBatch2() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "1202";
        String exeMthd = "rateBatch2";
        final int batchMod = 2;

        boolean runFlag = true; // 배치를 계속 실행할지 확인하는 플래그
        
        try {
            while(runFlag) {
            	//NMCP_RATE_RES_CHG_TRG에 처리할 대상이 있는지 확인(batchMod에 따라 조회대상이 다름)
            	if(!rateSvc.checkRateResChgTrgByMod(batchMod)) {
            		
            		//배치 가동 시간 확인
            		if(rateSvc.checkBatchRunTime()) {
            			Thread.sleep(30000); //30초 대기 후 처음으로
            		} else {
            			runFlag = false;
            		}
            		
            	} else {
            		//NMCP_RATE_RES_CHG_TRG의 대상으로 요금제 예약 변경 후처리 프로세스 진행
            		Map<String, Object> processResult = rateSvc.processRateResChgByTrg(batchMod);
            		
            		successCascnt += (int)(processResult.get("SUCCESS_CNT"));
            		failCascnt += (int)(processResult.get("FAIL_CNT"));
            	}
            }
        } catch(Exception e) {
        	failCascnt++;
    		e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);
                bathHistDto.setExeMthd(exeMthd);
                bathHistDto.setBatchExeDate(batchExeDate);
                bathHistDto.setBatchExeStDate(batchExeStDate);
                bathHistDto.setBatchExeCascnt(batchExeCascnt);
                bathHistDto.setSuccessCascnt(successCascnt);
                bathHistDto.setFailCascnt(failCascnt);
                bathHistDto.setMsgDtl(msgDtl);
                bathHistService.insertHist(bathHistDto);
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }

    /**
     *  요금제 예약변경 후속 처리
     *  @Scheduled(cron="0 33 0 1 * *") //매월 1일 00:33에 실행
     */
    @Scheduled(cron="0 33 0 1 * *")
//    @Scheduled(cron="3 * * * * *")
    @Async
    public void rateBatch3() {

    	logger.info("요금제 예약변경 후속 처리 rateBatch3() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "1203";
        String exeMthd = "rateBatch3";
        final int batchMod = 3;

        boolean runFlag = true; // 배치를 계속 실행할지 확인하는 플래그
        
        try {
            while(runFlag) {
            	//NMCP_RATE_RES_CHG_TRG에 처리할 대상이 있는지 확인(batchMod에 따라 조회대상이 다름)
            	if(!rateSvc.checkRateResChgTrgByMod(batchMod)) {
            		
            		//배치 가동 시간 확인
            		if(rateSvc.checkBatchRunTime()) {
            			Thread.sleep(30000); //30초 대기 후 처음으로
            		} else {
            			runFlag = false;
            		}
            		
            	} else {
            		//NMCP_RATE_RES_CHG_TRG의 대상으로 요금제 예약 변경 후처리 프로세스 진행
            		Map<String, Object> processResult = rateSvc.processRateResChgByTrg(batchMod);
            		
            		successCascnt += (int)(processResult.get("SUCCESS_CNT"));
            		failCascnt += (int)(processResult.get("FAIL_CNT"));
            	}
            }
        } catch(Exception e) {
        	failCascnt++;
    		e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);
                bathHistDto.setExeMthd(exeMthd);
                bathHistDto.setBatchExeDate(batchExeDate);
                bathHistDto.setBatchExeStDate(batchExeStDate);
                bathHistDto.setBatchExeCascnt(batchExeCascnt);
                bathHistDto.setSuccessCascnt(successCascnt);
                bathHistDto.setFailCascnt(failCascnt);
                bathHistDto.setMsgDtl(msgDtl);
                bathHistService.insertHist(bathHistDto);
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }
    

    /**
     *  첫 번째 재처리 배치
     *  
     *  요금제 예약 변경 미처리 대상과(BATCH_RSLT_CD = '-1')
     *  요금제 변경 처리 미완료 대상(BATCH_RSLT_CD = '99999')
     *  에 대해 재검증 및 후처리
     *  
     *  @Scheduled(cron="0 01 6 1 * *") //매월 1일 06:01에 실행
     */
    @Scheduled(cron="0 01 6 1 * *")
//    @Scheduled(cron="0 37 09 * * *")
    @Async
    public void rateBatchNotProcess() {

    	logger.info("요금제 예약변경 미처리, 처리 미완료 대상 재처리 rateBatchNotProcess() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "1204";
        String exeMthd = "rateBatchNotProcess";
        
        try {
    		//요금제 예약 변경 미처리 대상과 요금제 변경 처리 미완료 대상에 대해 재처리 진행
    		Map<String, Object> processResult = rateSvc.reprocessRateResChgNotProcess();
    		
    		successCascnt += (int)(processResult.get("SUCCESS_CNT"));
    		failCascnt += (int)(processResult.get("FAIL_CNT"));
        } catch(Exception e) {
        	failCascnt++;
    		e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);
                bathHistDto.setExeMthd(exeMthd);
                bathHistDto.setBatchExeDate(batchExeDate);
                bathHistDto.setBatchExeStDate(batchExeStDate);
                bathHistDto.setBatchExeCascnt(batchExeCascnt);
                bathHistDto.setSuccessCascnt(successCascnt);
                bathHistDto.setFailCascnt(failCascnt);
                bathHistDto.setMsgDtl(msgDtl);
                bathHistService.insertHist(bathHistDto);
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }    

    /**
     *  두 번째 재처리 배치
     *  
     *  요금제 예약 변경 후처리 중 선 해지 부가서비스 해지 실패 대상
     *  해지 재시도 및 평생할인 프로모션 부가서비스 가입 진행
     *  
     *  @Scheduled(cron="0 20 6 1 * *") //매월 1일 06:20에 실행
     */
    @Scheduled(cron="0 20 6 1 * *")
//    @Scheduled(cron="0 0 10 * * *")
    @Async
    public void rateBatchFailCancel() {

    	logger.info("요금제 예약 변경 후처리 중 선해지 부가서비스 해지 실패 대상 재처리 rateBatchFailCancel() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "1205";
        String exeMthd = "rateBatchFailCancel";
        
        try {
    		//요금제 예약 변경 후처리 중 선 해지 부가서비스 해지 실패 대상 재처리 진행
    		Map<String, Object> processResult = rateSvc.reprocessRateResChgFailCancel();
    		
    		successCascnt += (int)(processResult.get("SUCCESS_CNT"));
    		failCascnt += (int)(processResult.get("FAIL_CNT"));
        } catch(Exception e) {
        	failCascnt++;
    		e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);
                bathHistDto.setExeMthd(exeMthd);
                bathHistDto.setBatchExeDate(batchExeDate);
                bathHistDto.setBatchExeStDate(batchExeStDate);
                bathHistDto.setBatchExeCascnt(batchExeCascnt);
                bathHistDto.setSuccessCascnt(successCascnt);
                bathHistDto.setFailCascnt(failCascnt);
                bathHistDto.setMsgDtl(msgDtl);
                bathHistService.insertHist(bathHistDto);
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }    
    
    /**
     *  세 번째 재처리 배치
     *  
     *  요금제 예약 변경 후처리 중 부가서비스 가입 실패 대상
     *  가입 실패한 부가서비스를 하나씩 가입 재시도
     *  
     *  @Scheduled(cron="0 40 6 1 * *") //매월 1일 06:40에 실행
     */
    @Scheduled(cron="0 40 6 1 * *")
//    @Scheduled(cron="30 00 11 * * *")
    @Async
    public void rateBatchFailSubscribe() {
    	
    	logger.info("요금제 예약 변경 후처리 중 부가서비스 가입 실패 대상 재처리 rateBatchFailSubscribe() START===============================================");
    	String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
    	String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
    	int batchExeCascnt = 1;
    	String msgDtl = "";
    	int failCascnt = 0;
    	int successCascnt = 0;
    	String batchCd = "1206";
    	String exeMthd = "rateBatchFailSubscribe";
    	
    	try {
    		//요금제 예약 변경 후처리 중 부가서비스 가입 실패 대상 재처리 진행
    		Map<String, Object> processResult = rateSvc.reprocessRateResChgFailSubscribe();
    		
    		successCascnt += (int)(processResult.get("SUCCESS_CNT"));
    		failCascnt += (int)(processResult.get("FAIL_CNT"));
    	} catch(Exception e) {
    		failCascnt++;
    		e.printStackTrace();
    		msgDtl = e.getMessage();
    	} finally {
    		try {
    			BathHistDto bathHistDto = new BathHistDto();
    			bathHistDto.setBatchCd(batchCd);
    			bathHistDto.setExeMthd(exeMthd);
    			bathHistDto.setBatchExeDate(batchExeDate);
    			bathHistDto.setBatchExeStDate(batchExeStDate);
    			bathHistDto.setBatchExeCascnt(batchExeCascnt);
    			bathHistDto.setSuccessCascnt(successCascnt);
    			bathHistDto.setFailCascnt(failCascnt);
    			bathHistDto.setMsgDtl(msgDtl);
    			bathHistService.insertHist(bathHistDto);
    		} catch(Exception e) {
    			logger.error(exeMthd + " error");
    		}
    	}
    }    
}