package com.ktmmobile.mcp.cert.controller;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import org.codehaus.plexus.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;


@RestController
@EnableScheduling
@EnableAsync
public class CertController {

	private static final Logger logger = LoggerFactory.getLogger(CertController.class);

	@Autowired
    private CertService certSvc;

    @Autowired
    private BathHistService bathHistService;
  
    /**
     * NMCP_CRT_VLD_DTL n일(공통코드CDD) 전 데이터 삭제 - 매일 1회 23:30:00
     * @author wooki
     * @Date : 2023.12
     */
    @Scheduled(cron = "0 30 23 * * *") //초 분 시 일 월 주,년(1분마다 0 0/1 * * * *)
    public void certDelBatch() {

        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "11"; 
        String exeMthd = "certDelBatch";
       
        try{
        	
        	//1.공통코드 등록된 기준 삭제일 가져오기
        	String delDay = null;
        	int intDelDay = 0;
        	try {
        		delDay = certSvc.getCertDelDay();
        		
        		if(!StringUtils.isEmpty(delDay)) {
        			try {
        				intDelDay = Integer.valueOf(delDay);
        			}catch(NumberFormatException e) {
        				intDelDay = 0;
        			} 
        		}
       			
    			logger.info("[certDelBatch] intDelDay : " + intDelDay);
    			
        	}catch(Exception e) {
        		logger.error("[certDelBatch] getCertDelDay error");
        		msgDtl = e.getMessage(); //오류메세지
        		failCascnt++;
        		e.printStackTrace();        		
        		return;
        	} 
        	
        	//2.데이터 삭제
        	int cntDel = 0;
        	try {

				// NMCP_CRT_VLD_DTL 데이터 삭제
        		cntDel = certSvc.deleteCertFailData(intDelDay);

            	if(cntDel > 0) { //삭제 카운트 set
            		successCascnt = cntDel;
            	}
            	
            	logger.info("[certDelBatch] cntDel : " + cntDel);
            			
        	}catch(Exception e) {
        		logger.error("[certDelBatch] deleteCertFailData error");
        		msgDtl = e.getMessage();
        		failCascnt++;
        		e.printStackTrace();
        		return;
        	}
        	
        	//에러 메시지 없다면 삭제기준일, 삭제건수 set - 확인용
        	if(StringUtils.isBlank(msgDtl)) {
        		msgDtl = "intDelDay:" + intDelDay + " cntDel:" + cntDel; 
        	}
        	
        }catch(Exception e) {
            logger.error("certDelBatch() Exception = {}", e.getMessage());
        }finally {
            try {
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);                // 배치 코드
                bathHistDto.setExeMthd(exeMthd);                // 배치 메소드
                bathHistDto.setBatchExeDate(batchExeDate);      // 시작일
                bathHistDto.setBatchExeStDate(batchExeStDate);  // 시작일시
                bathHistDto.setBatchExeCascnt(batchExeCascnt);  // 배치실행건수
                bathHistDto.setSuccessCascnt(successCascnt);    // 성공건수
                bathHistDto.setFailCascnt(failCascnt);          // 실패건수
                bathHistDto.setMsgDtl(msgDtl);                  // 실패 시 에러메세지
                bathHistService.insertHist(bathHistDto);
            }catch(Exception e) {
                logger.debug("certDelBatch error");
            }
        }
    }
}
