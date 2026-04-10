package com.ktmmobile.mcp.mcash.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.mcash.dto.McashDto;
import com.ktmmobile.mcp.mcash.service.McashService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableScheduling
@EnableAsync
public class McashController {
    private static final Logger logger = LoggerFactory.getLogger(McashController.class);

    @Autowired
    private McashService mcashService;

    @Autowired
    private BathHistService bathHistService;

    /**
     * 설명 :   Mcash 연동 (매일 22시 30분)
     * @Date : 2024.07.30
     */
    @Scheduled(cron = "0 30 22 * * *")
    public void mcashCanBatch(){
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
            int baseDate = 5;

            try {
                int result = mcashService.getBaseDate();
                if(result > 0) {
                    baseDate = result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 0) MCASH 미처리 대상 T(처리중)으로 변경
            int mcashAllTrgCnt = mcashService.updateAllMcashCanTrg(baseDate);
            logger.error("=========== [mcashCanBatch] mcashAllTrgCnt [" + mcashAllTrgCnt + "]");

            if(mcashAllTrgCnt == 0 ) {
                logger.error("=========== [mcashCanBatch] mcashAllTrgCnt is empty");
                return;
            }

            // 1) MCASH 미연동 대상(USER 테이블에 동일한 계약번호로 존재하지 않고 가장 최신이 아닌 전문으로 발생된) 리스트 미대상처리 (X)
            int mcashNotCanTrgCnt = mcashService.updateMcashNotCanTrg();
            logger.error("=========== [mcashCanBatch] notMcashTrgCnt [" + mcashNotCanTrgCnt + "]");

            // 2) MCASH USER 테이블 정보로 해지 연동 테이블 업데이트
            int userInfoUpdateCnt = mcashService.updateMcashCanTrgInfo();
            logger.error("=========== [mcashCanBatch] userInfoUpdateCnt [" + userInfoUpdateCnt + "]");

            // 3) MCASH 연동 대상 리스트 추출
            List<McashDto> mcashCanTrgList =  mcashService.getMcashCanTrg();

            logger.error("=========== [mcashCanBatch] mcashTrgListCnt [" + mcashCanTrgList.size() + "]");

            if(mcashCanTrgList.size() == 0 || mcashCanTrgList == null){
                logger.error("=========== [mcashCanBatch] MCASH CAN List is empty");
                return;
            }

            batchExeCascnt = mcashCanTrgList.size(); // 탈퇴연동 건수 세팅

            for(McashDto mcashDto : mcashCanTrgList){

                try{
                    // 4) mcash 탈퇴 연동
                    boolean rst = mcashService.syncUserInfo(mcashDto);

                    if( rst ) {
                        successCascnt ++;
                    } else{
                        failCascnt ++;
                    }

                }catch(Exception e) {
                    failCascnt ++;
                }
            }
        }catch (Exception e) {
            logger.error("mcashCanBatch() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "14";
                exeMthd = "mcashCanBatch";
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
                logger.debug("mcashCanBatch error");
            }
        }
    }


    /**
     * 설명 :   Mcash 재처리 연동 (매일 02시 30분)
     * @Date : 2024.08.20
     */
    @Scheduled(cron = "0 30 2 * * *")
    public void mcashCanRetryBatch(){
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
            int retryDate = 1;

            try {
                int result = mcashService.getRetryDate();
                if(result > 0) {
                    retryDate = result;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            // 0) MCASH 미처리 대상 T(처리중)으로 변경
            int mcashAllRetryTrgCnt = mcashService.updateAllMcashCanRetryTrg(retryDate);
            logger.error("=========== [mcashCanRetryBatch] mcashAllTrgCnt [" + mcashAllRetryTrgCnt + "]");

            if(mcashAllRetryTrgCnt == 0 ) {
                logger.error("=========== [mcashCanRetryBatch] mcashAllRetryTrgCnt is empty");
                return;
            }

            // 1) MCASH 연동 대상 리스트 추출
            List<McashDto> mcashCanTrgList =  mcashService.getMcashCanTrg();
            logger.error("=========== [mcashCanRetryBatch] mcashTrgListCnt [" + mcashCanTrgList.size() + "]");

            if(mcashCanTrgList.size() == 0 || mcashCanTrgList == null){
                logger.error("=========== [mcashCanRetryBatch] MCASH CAN List is empty");
                return;
            }

            batchExeCascnt = mcashCanTrgList.size(); // 탈퇴연동 건수 세팅

            for(McashDto mcashDto : mcashCanTrgList){

                try{
                    // 2) mcash 탈퇴 연동
                    boolean rst = mcashService.syncUserInfo(mcashDto);

                    if( rst ) {
                        successCascnt ++;
                    } else{
                        failCascnt ++;
                    }

                }catch(Exception e) {
                    failCascnt++;
                }
            }
        }catch (Exception e) {
            logger.error("mcashCanRetryBatch() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "15";
                exeMthd = "mcashCanRetryBatch";
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
                logger.debug("mcashCanRetryBatch error");
            }
        }
    }
}
