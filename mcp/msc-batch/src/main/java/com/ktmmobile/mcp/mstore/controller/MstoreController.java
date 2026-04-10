package com.ktmmobile.mcp.mstore.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.mstore.dto.MstoreApiDto;
import com.ktmmobile.mcp.mstore.dto.MstoreCanTrgDto;
import com.ktmmobile.mcp.mstore.service.MstoreService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@EnableScheduling
@EnableAsync
public class MstoreController {
    private static final Logger logger = LoggerFactory.getLogger(MstoreController.class);

    @Autowired
    private MstoreService mstoreService;

    @Autowired
    private BathHistService bathHistService;

    /**
     * 설명 : [TO-BE] Mstore 탈퇴 연동 (5분간격으로 실행)
     * @Date : 2023.12.05
     */
    @Scheduled(cron = "0 3/5 * * * *")
    public void mstoreCanBatchNew(){
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
            // 1) MSTORE 연동 대상 리스트 추출 (최대 50건)
            MstoreCanTrgDto[] rtnResult=  mstoreService.getMstoreCanTrgNew();
            logger.error("==============[mstoreCanBatchNew] :" + ((rtnResult== null) ? 0 : rtnResult.length));

            if(rtnResult==null || rtnResult.length == 0){
                logger.debug("=========== [mstoreCanBatchNew] Mstore Can Trg List is empty");
                return;
            }

            List<MstoreCanTrgDto> mstoreCanTrgList = Arrays.asList(rtnResult);

            // 2) MSTORE 연동 대상 리스트 중 MSTORE 활성회원 추출
            List<MstoreApiDto> users = mstoreService.getMsoreActiveUsers(mstoreCanTrgList);
            if(users==null || users.size() == 0){
                logger.debug("=========== [mstoreCanBatchNew] Mstore users List is empty");
                return;
            }

            // 3) MSTORE 탈퇴 연동
            batchExeCascnt = users.size(); // 탈퇴연동 건수 세팅
            String canResult= null;
            try{
                canResult= mstoreService.updateMstoreUserInfo(users);
            }catch(Exception e){
                msgDtl= e.getMessage();       // 오류메세지
                failCascnt= batchExeCascnt;   // 실패건수
                return;
            }

            // 4) 탈퇴연동 응답결과를 objectMap으로 변환
            Map<String, Object> objectMap= mstoreService.changeApiResultToMap(canResult);
            objectMap.put("procDivStat", users.get(0).getProcDivStat());   // 처리구분
            objectMap.put("totalCnt", batchExeCascnt);                     // 대상건수

            // 5) MSTORE 인사정보 탈퇴 연동 응답 로그 insert
            String histSeq=  mstoreService.insertMstoreProcHist(objectMap);

            // 6) Mstore 연동 결과 update
            Map<String,String> seqMap = new HashMap<>();
            seqMap.put("firstSeq",mstoreCanTrgList.get(0).getCanTrgSeq());
            seqMap.put("lastSeq",mstoreCanTrgList.get(mstoreCanTrgList.size()-1).getCanTrgSeq());
            seqMap.put("histSeq",histSeq);

            mstoreService.updateMstoreCanTrg(seqMap);

            if(!"00".equals(objectMap.get("resultCode"))){
                failCascnt = batchExeCascnt;
                msgDtl = (String) objectMap.get("message");
            }else{
                // 연동결과가 성공인 경우
                successCascnt = batchExeCascnt;

                // 7) 연동 성공 건에 대해서 고객정보 삭제
                mstoreService.deleteMstoreSsoInfo(histSeq, "histSeq");
            }

        }catch (Exception e) {
            logger.error("mstoreCanBatchNew() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "09";
                exeMthd = "mstoreCanBatchNew";
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
                logger.debug("mstoreCanBatchNew error");
            }
        }
    }

    /**
     * 설명 : [TO-BE] Mstore 탈퇴 연동 - 재처리 (21시 5분간격으로 실행)
     * @Date : 2023.12.05
     */
    @Scheduled(cron = "0 4/5 21 * * *")
    public void mstoreCanBatchRty(){

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
            // 1) MSTORE 연동 대상 리스트 추출 (최대 50건)
            MstoreCanTrgDto[] rtnResult=  mstoreService.getMstoreCanTrgRty();
            logger.error("==============[mstoreCanBatchRty] :" + ((rtnResult== null) ? 0 : rtnResult.length));

            if(rtnResult==null || rtnResult.length == 0){
                logger.debug("=========== [mstoreCanBatchRty] Mstore Can Trg List is empty");
                return;
            }

            List<MstoreCanTrgDto> mstoreCanTrgList = Arrays.asList(rtnResult);

            // 2) MSTORE 연동 대상 리스트 중 MSTORE 활성회원 추출
            List<MstoreApiDto> users = mstoreService.getMsoreActiveUsers(mstoreCanTrgList);
            if(users==null || users.size() == 0){
                logger.debug("=========== [mstoreCanBatchRty] Mstore users List is empty");
                return;
            }

            // 3) MSTORE 탈퇴 연동
            batchExeCascnt = users.size(); // 탈퇴연동 건수 세팅
            String canResult= null;
            try{
                canResult= mstoreService.updateMstoreUserInfo(users);
            }catch(Exception e){
                msgDtl= e.getMessage();       // 오류메세지
                failCascnt= batchExeCascnt;   // 실패건수
                return;
            }

            // 4) 탈퇴연동 응답결과를 objectMap으로 변환
            Map<String, Object> objectMap= mstoreService.changeApiResultToMap(canResult);
            objectMap.put("procDivStat", users.get(0).getProcDivStat());   // 처리구분
            objectMap.put("totalCnt", batchExeCascnt);                     // 대상건수

            // 5) MSTORE 인사정보 탈퇴 연동 응답 로그 insert
            String histSeq=  mstoreService.insertMstoreProcHist(objectMap);

            // 6) Mstore 연동 결과 update
            Map<String,String> seqMap = new HashMap<>();
            seqMap.put("firstSeq",mstoreCanTrgList.get(0).getCanTrgSeq());
            seqMap.put("lastSeq",mstoreCanTrgList.get(mstoreCanTrgList.size()-1).getCanTrgSeq());
            seqMap.put("histSeq",histSeq);

            mstoreService.updateMstoreRtyCanTrg(seqMap);

            if(!"00".equals(objectMap.get("resultCode"))){
                failCascnt = batchExeCascnt;
                msgDtl = (String) objectMap.get("message");
            }else{
                // 연동결과가 성공인 경우
                successCascnt = batchExeCascnt;

                // 7) 연동 성공 건에 대해서 고객정보 삭제
                mstoreService.deleteMstoreSsoInfo(histSeq, "histSeq");
            }

        }catch (Exception e) {
            logger.error("mstoreCanBatchRty() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "10";
                exeMthd = "mstoreCanBatchRty";
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
                logger.debug("mstoreCanBatchRty error");
            }
        }
    }

    @RequestMapping(value = "/mstore/saveTodaySalesList")
    @ResponseBody
    public String saveTodaySalesList(@RequestParam String userId) {
        mstoreService.saveTodaySalesList(userId);
        return "success";
    }

    /**
     * 설명 : M마켓 오늘의 특가 상품 목록 조회 및 저장
     * @Date : 2025.11.11
     */
    @Scheduled(cron = "0 0 * * * *")
    public void saveTodaySalesListJob(){

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
            mstoreService.saveTodaySalesList("BATCH");
        }catch (Exception e) {
            logger.error("saveTodaySalesListJob() Exception = {}", e.getMessage());
        }finally {
            try{
                batchCd = "25";
                exeMthd = "saveTodaySalesListJob";
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
                logger.debug("saveTodaySalesListJob error");
            }
        }
    }

}
