package com.ktmmobile.mcp.acen.controller;

import com.ktmmobile.mcp.acen.service.AcenService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@EnableScheduling
@EnableAsync
public class AcenController {

    private static final Logger logger = LoggerFactory.getLogger(AcenController.class);

    @Autowired
    private AcenService acenService;

    @Autowired
    private BathHistService bathHistService;

    /**
     * 설명 : Acen 연동 대상 조회 및 연동 요청 (5분간격으로 실행)
     * @Date : 2024.05.09
     */
    @Scheduled(cron = "0 1/5 8-22 * * *")
    public void acenBatch(){

        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "";
        String exeMthd = "";

        try{

            // ACEN 연동 제약조건
            Map<String,Object> acenLimit= acenService.getAcenLimit();

            // ACEN 연동 대상 상태 'P' UPDATE
            // ※ endStatus : N[미종결] , Y[종결], P[작업중]
            int acenTrgCnt= acenService.updAcenBatchTrg(acenLimit);

            if(acenTrgCnt <= 0){
                logger.debug("## [acenBatch] Acen Batch Target is empty");
                return;
            }

            // 해피콜 타입 유형 별 처리
            Map<String, Integer> result = acenService.procAcenRequest();

            batchExeCascnt= result.get("exeCasCnt");
            successCascnt= result.get("succCascnt");
            failCascnt= result.get("failCascnt");

        }catch (Exception e) {
            logger.error("acenBatch() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "12";
                exeMthd = "acenBatch";
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
                logger.debug("acenBatch error");
            }
        }
    }


    /**
     * 설명 : Acen 연동 종결 배치
     * @Date : 2024.06.12
     */
    @Scheduled(cron = "0 0 3 * * *")
    public void acenEndBatch(){

        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "";
        String exeMthd = "";

        try{

            // ACEN 연동 미완료건 종결 처리 (미완료= 사전체크 요청은 진행됐으나 개통완료가 되지 않은 케이스)
            Map<String, Integer> result= acenService.updAcenEndTrg("01");

            batchExeCascnt= result.get("exeCasCnt");
            successCascnt= result.get("succCascnt");
            failCascnt= result.get("failCascnt");

            // ACEN 연동 기간 종결건 처리 (14일이 지났지만 종료되지 않은 건)
            result= acenService.updAcenEndTrg("02");

            batchExeCascnt+= result.get("exeCasCnt");
            successCascnt+= result.get("succCascnt");
            failCascnt+= result.get("failCascnt");

            int endCnt = batchExeCascnt; // 종결처리 건수

            // 신청서 상태 정상 복구 (자동화실패+개통완료 > 정상+개통완료 처리)
            result= acenService.updateNormalPState();

            batchExeCascnt+= result.get("exeCasCnt");
            successCascnt+= result.get("succCascnt");
            failCascnt+= result.get("failCascnt");

            int normalCnt = result.get("exeCasCnt"); // 정상원복 건수

            msgDtl = "종결처리 " + endCnt + "건, 정상복구 " + normalCnt + "건";

        }catch (Exception e) {
            logger.error("acenEndBatch() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "13";
                exeMthd = "acenEndBatch";
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
                logger.debug("acenEndBatch error");
            }
        }
    }

    /**
     * 설명 : Acen 응답결과 미존재건 → 실패(고객부재) 처리
     * @Date : 2025.04.17
     */
    @Scheduled(cron = "0 8/10 9-22 * * *")
    public void acenNoResponseBatch(){

        //배치 실행 내역
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "";
        String exeMthd = "";

        try{

            // 특정 시간 이내에 A'Cen 응답결과 미존재 시 → 실패(고객부재) 처리
            Map<String, Integer> result= acenService.updAcenNoResponseTrg();

            batchExeCascnt= result.get("exeCasCnt");
            successCascnt= result.get("succCascnt");
            failCascnt= result.get("failCascnt");

        }catch (Exception e) {
            logger.error("acenNoResponseBatch() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "17";
                exeMthd = "acenNoResponseBatch";
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
                logger.debug("acenNoResponseBatch error");
            }
        }
    }

}
