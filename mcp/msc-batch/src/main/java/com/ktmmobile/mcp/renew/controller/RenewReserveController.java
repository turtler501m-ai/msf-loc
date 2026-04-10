package com.ktmmobile.mcp.renew.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.mcash.dto.McashDto;
import com.ktmmobile.mcp.mcash.service.McashService;
import com.ktmmobile.mcp.renew.service.RenewReserveService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@EnableScheduling
@EnableAsync
public class RenewReserveController {
    private static final Logger logger = LoggerFactory.getLogger(RenewReserveController.class);

    @Autowired
    private BathHistService bathHistService;
    @Autowired
    private RenewReserveService renewReserveService;

    @RequestMapping("/renew/processRenewReserveList")
    public String excuteProcessRenewReserveList() {

        processRenewReserveList();

        return "renewReserveController";
    }

    /**
     * 설명 :   현행화 예약 처리 (매일 00시 00분 30초)
     * @Date : 2025.05.07
     */
    @Scheduled(cron = "30 0 0 * * *")
//    @Scheduled(cron = "0 0/1 * * * *")
    public void processRenewReserveList(){
        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "";
        String exeMthd = "";
        String reserveDate = "";

        try {
            Map<String, Object> result = renewReserveService.processRenewReserveList(reserveDate);
            successCascnt = (int) result.get("successCnt");
            failCascnt = (int) result.get("failCnt");
            batchExeCascnt = (int) result.get("totalCnt");

        } catch (Exception e) {
            logger.error("processRenewReserveList() Exception = {}", e.getMessage());
            msgDtl += e.getMessage();
        } finally {
            try{
                batchCd = "18";
                exeMthd = "processRenewReserveList";
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
            }catch (Exception e){
                logger.debug("processRenewReserveList error");
            }
        }
    }
}
