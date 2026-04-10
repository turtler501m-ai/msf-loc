package com.ktmmobile.mcp.rateStat.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.rateStat.service.RateStatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Map;

@RestController
@EnableScheduling
@EnableAsync
public class RateStatController {
    private static final Logger logger = LoggerFactory.getLogger(RateStatController.class);

    @Autowired
    private RateStatService rateStatService;

    @Autowired
    private BathHistService bathHistService;

    @RequestMapping("/useRateStat")
    public void useRateStat() {
        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "50";
        String exeMthd = "useRateStat";

        String baseMon = batchExeDate.substring(0,6);
        try {
            Map<String, Object> result = rateStatService.insertUseRateInfo(baseMon);
            successCascnt = Integer.parseInt(String.valueOf(result.get("succCnt")));
            msgDtl = String.valueOf(result.get("msgDtl"));

        } catch (Exception e) {
            logger.error("useRateInfo() Exception = {}", e.getMessage());
            msgDtl = e.getMessage();
        } finally {
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
            } catch (Exception e) {
                logger.debug("useRateStat error");
            }
        }
    }

    @RequestMapping("/useRateStatAdd")
    public void useRateStatAdd() throws ParseException {
        insertUseRateStat();
    }

    @RequestMapping("/useRateInfoDel")
    public void useRateInfoDel() throws ParseException {
        deleteUseRateInfo();
    }

    /**
     *  유지요금제 집계 데이터 생성
     *	 1) 월말 기준 유지요금제
     * 	 2) M-DNA 기준과 통일
     * 	  - 당일개통 취소와  14일 이내 해지 건은 제외(일반해지는 포함)
     * 	 3) 유지가입자에 최근1개월 가입자도 포함
     *  @Scheduled(cron="0 0 20 28-31 * *") //매월말 20:00에 실행
     */
    @Scheduled(cron = "0 0 20 28-31 * *", zone = "Asia/Seoul")
    public void insertUseRateInfo(){
        logger.error("## 월별 유지고객 정보 저장 START");
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Seoul"));
        if (today.plusDays(1).getDayOfMonth() != 1) {
            logger.error("## 월말이 아닙니다. SKIP");
            return;
        }

        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "50";
        String exeMthd = "insertUseRateInfo";

        String baseMon = batchExeDate.substring(0,6);
        try {
            Map<String, Object> result = rateStatService.insertUseRateInfo(baseMon);
            successCascnt = Integer.parseInt(String.valueOf(result.get("succCnt")));
            msgDtl = String.valueOf(result.get("msgDtl"));

        } catch (Exception e) {
            logger.error("insertUseRateInfo() Exception = {}", e.getMessage());
            msgDtl = e.getMessage();
        } finally {
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
            } catch (Exception e) {
                logger.debug("insertUseRateInfo error");
            }
        }
    }

    /**
     *  유지 고객 요금제 집계
     *   1) 전월말에 생성된 유지고객정보로 집계를 한다.
     *  @Scheduled(cron="0 0 2 1 * *") //매월 1일 6:00에 실행
     */
    @Scheduled(cron = "0 0 6 1 * *", zone = "Asia/Seoul")
    public void insertUseRateStat() throws ParseException {
        logger.error("## 유지요금제 집계 저장 START");

        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "51";
        String exeMthd = "insertUseRateStat";

        // 전월 생성된 유지 고객 데이터로 집계
        String baseMon = DateTimeUtil.addMonths(batchExeDate, -1).substring(0,6);
        try {
            Map<String, Object> result = rateStatService.insertUseRateStat(baseMon);
            successCascnt = Integer.parseInt(String.valueOf(result.get("succCnt")));
            msgDtl = String.valueOf(result.get("msgDtl"));

        } catch (Exception e) {
            logger.error("insertUseRateStat() Exception = {}", e.getMessage());
            msgDtl = e.getMessage();
        } finally {
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
            } catch (Exception e) {
                logger.debug("insertUseRateStat error");
            }
        }
    }

    /**
     *  이전 유지 고객 정보 삭제
     *   1) 유지 고객 정보가 매월 3,000,000 만건씩 쌓이고 있음
     *   2) M-2, M-1 데이터만 유지하기 위해 M-3 이하 데이터 삭제
     *  @Scheduled(cron="0 0 2 1 * *") //매월 1일 5:00에 실행
     */
    @Scheduled(cron = "0 0 5 1 * *", zone = "Asia/Seoul")
    public void deleteUseRateInfo() throws ParseException {
        logger.error("## 이전 유지 고객 정보 삭제 START");

        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "52";
        String exeMthd = "deleteUseRateInfo";

        // 3개월 전에 생성된 데이터 삭제
        String baseMon = DateTimeUtil.addMonths(batchExeDate, -3).substring(0,6);
        try {
            Map<String, Object> result = rateStatService.deleteUseRateInfo(baseMon);
            successCascnt = Integer.parseInt(String.valueOf(result.get("succCnt")));
            msgDtl = String.valueOf(result.get("msgDtl"));

        } catch (Exception e) {
            logger.error("deleteUseRateInfo() Exception = {}", e.getMessage());
            msgDtl = e.getMessage();
        } finally {
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
            } catch (Exception e) {
                logger.debug("deleteUseRateInfo error");
            }
        }
    }
}
