package com.ktmmobile.mcp.event.promotion.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.event.promotion.service.EventPromotionService;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@EnableScheduling
@EnableAsync
public class EventPromotionController {
    private static final Logger logger = LoggerFactory.getLogger(EventPromotionController.class);

    private final BathHistService bathHistService;
    private final EventPromotionService eventPromotionService;

    public EventPromotionController(BathHistService bathHistService, EventPromotionService eventPromotionService) {
        this.bathHistService = bathHistService;
        this.eventPromotionService = eventPromotionService;
    }

    @RequestMapping("/event/promotion/deleteExpiredJoinHist")
    public String excuteDeleteExpiredJoinHist() {

        deleteExpiredJoinHist();

        return "eventPromotionController";
    }

    /**
     * 설명 :   이벤트 종료 6개월 초과 참여 이력 삭제 처리 (매일 01시 30분 00초)
     * @Date : 2025.06.12
     */
    @Scheduled(cron = "0 30 1 * * *")
//    @Scheduled(cron = "0 0/1 * * * *")
    public void deleteExpiredJoinHist(){
        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "";
        String exeMthd = "";

        try {
            Map<String, Object> result = eventPromotionService.deleteExpiredJoinHist();

            successCascnt = (int) result.get("successCnt");
            failCascnt = (int) result.get("failCnt");
            batchExeCascnt = (int) result.get("totalCnt");

        } catch (Exception e) {
            logger.error("deleteExpiredJoinHist() Exception = {}", e.getMessage());
            msgDtl += e.getMessage();
        } finally {
            try{
                batchCd = "20";
                exeMthd = "deleteExpiredJoinHist";
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
                logger.debug("deleteExpiredJoinHist error");
            }
        }
    }
}
