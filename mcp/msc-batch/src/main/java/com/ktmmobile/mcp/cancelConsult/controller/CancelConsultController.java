package com.ktmmobile.mcp.cancelConsult.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cancelConsult.service.CancelConsultService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;

@RestController
@EnableScheduling
@EnableAsync
public class CancelConsultController {
    private static final Logger logger = LoggerFactory.getLogger(CancelConsultController.class);

    @Autowired
    private BathHistService bathHistService;
    @Autowired
    private CancelConsultService cancelConsultService;

    @RequestMapping("/cancelConsult/cancelConsultDelBatch")
    public String excuteCancelConsultDelBatch() {

        cancelConsultDelBatch();

        return "CancelConsultController";
    }

    /*해지상담 신청 삭제 처리 배치
     * 매월 1일 02:30에 실행
     */
    @Scheduled(cron="0 30 2 1 * *")
    public void cancelConsultDelBatch(){

        logger.info("해지상담 신청 삭제 처리 cancelConsultDelBatch() ===============================================");
        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "23";
        String exeMthd = "cancelConsultDelBatch";

        try {

            //1. 해지상담 신청 월 기준 6개월 지난 데이터 있는지 확인(완료건 제외)
            int cancelConsultDelCount = cancelConsultService.selectCancelConsultCount();

            if(cancelConsultDelCount > 0) {

                 int cancelDel = 0;
                 try {

                      //2.확인 된 데이터 삭제
                     cancelDel = cancelConsultService.deleteCancelConsult();

                     if(cancelDel > 0) { //삭제 카운트 set
                         successCascnt = cancelDel;
                     }

                     logger.info("[cancelConsultDelBatch] cancelConsultDel : " + cancelDel);

                 }catch(Exception e) {
                     logger.error("[cancelConsultDelBatch] cancelConsultDelBatch error");
                     msgDtl = e.getMessage();
                     failCascnt++;
                     return;
                 }
            }

        } catch (Exception e) {
            logger.error("cancelConsultDelBatch() Exception = {}", e.getMessage());
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
                logger.debug("cancelConsultDelBatch error");
            }
        }
    }
}
