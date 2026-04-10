package com.ktmmobile.mcp.document.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.document.service.DocumentReceiveService;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
@EnableAsync
public class DocumentReceiveController {
    private static final Logger logger = LoggerFactory.getLogger(DocumentReceiveController.class);

    @Autowired
    private DocumentReceiveService documentReceiveService;

    @Autowired
    private BathHistService bathHistService;

    @RequestMapping("/document/receive/expire")
    public String expireDocumentReceive(@RequestParam(required = false) String dateParam) {
        String batchExeDate;
        if (StringUtil.isEmpty(dateParam) || !DateTimeUtil.isValid(dateParam, "yyyyMMdd")) {
            batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        } else {
            batchExeDate = dateParam;
        }
        int successCount = documentReceiveService.expireDocumentReceive(batchExeDate);

        return "success count : " + successCount;
    }
    /**
     * 설명 : 서류접수 접수 기간 초과 만료 처리
     * @Date : 2025.09.09
     */
    @Scheduled(cron = "0 0 0 * * *")
    public void expireDocumentReceiveBatch(){
        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        try{
            successCascnt = documentReceiveService.expireDocumentReceive(batchExeDate);
            batchExeCascnt = successCascnt;
        } catch (Exception e) {
            logger.error("expireDocumentReceiveBatch() Exception = {}", e.getMessage());
            msgDtl = e.getMessage();
            failCascnt = 1;
        } finally {
            try{
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd("21");                           // 배치 코드
                bathHistDto.setExeMthd("expireDocumentReceiveBatch");   // 배치 메소드
                bathHistDto.setBatchExeDate(batchExeDate);              // 시작일
                bathHistDto.setBatchExeStDate(batchExeStDate);          // 시작일시
                bathHistDto.setBatchExeCascnt(batchExeCascnt);          // 배치실행건수
                bathHistDto.setSuccessCascnt(successCascnt);            // 성공건수
                bathHistDto.setFailCascnt(failCascnt);                  // 실패건수
                bathHistDto.setMsgDtl(msgDtl);                          // 실패 시 에러메세지
                bathHistService.insertHist(bathHistDto);
            }catch (Exception e){
                logger.debug("expireDocumentReceiveBatch error");
            }
        }
    }
}
