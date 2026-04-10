package com.ktmmobile.mcp.jehu.controller;

import java.time.LocalDateTime;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.jehu.service.JehuService;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;


@RestController
@EnableScheduling
public class JehuController {

    private static final Logger logger = LoggerFactory.getLogger(JehuController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private JehuService jehuService;

    @Value("${sms.callcenter}")
    private String callCenter;

    @Autowired
    private BathHistService bathHistService;

    //제휴제안 데이터 있을 경우 15시에 담당자 문자 발송
    @Scheduled(cron = "0 0 15 * * *")
    public void jehuSuggBatch(){
        try {
            int jehuSuggCount = jehuService.selectJehuSuggCount();

            if(jehuSuggCount > 0) {
                String today = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yy-MM-dd"));

                StringBuilder messageBuilder = new StringBuilder();
                messageBuilder.append("금일(")
                              .append(today)
                              .append(") ")
                              .append(jehuSuggCount)
                              .append("건의 신규 제휴 제안이 등록되었습니다. 확인 부탁드립니다.");

                String message = messageBuilder.toString();
                String mobile = jehuService.selectJehuManager();

                jehuService.sendLms("제휴제안 안내", mobile, message, callCenter,"jehuSugg","jehuSuggBatch");
                logger.info("{}건의 신규 제휴 제안 등록 안내 문자 발송 완료.", jehuSuggCount);
            }
        } catch (Exception e) {
            logger.error("jehuSuggBatch 작업 중 오류 발생", e);
        }
    }


    //제휴제안 삭제 배치
    @Scheduled(cron="0 20 2 1 * *") //매월 1일 02:20에 실행
    public void jehuDelBatch(){

        logger.info("제휴제안 삭제 처리 jehuDelBatch() ===============================================");
        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "22";
        String exeMthd = "jehuDelBatch";

        try {

            //1. 제휴 답변완료 월 기준 3개월 지난 데이터 있는지 확인
            int jehuDelCount = jehuService.selectDelCount();

            if(jehuDelCount > 0) {

                 int jehuDel = 0;
                 try {

                      //2.확인 된 데이터 삭제
                     jehuDel = jehuService.deleteJehuData();

                     if(jehuDel > 0) { //삭제 카운트 set
                         successCascnt = jehuDel;
                     }

                     logger.info("[jehuDelBatch] jehuDel : " + jehuDel);

                 }catch(Exception e) {
                     logger.error("[jehuDelBatch] deleteJehuData error");
                     msgDtl = e.getMessage();
                     failCascnt++;
                     e.printStackTrace();
                     return;
                 }
            }

        } catch (Exception e) {
            logger.error("jehuDelBatch() Exception = {}", e.getMessage());
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
                logger.debug("jehuDelBatch error");
            }
        }
    }

}
