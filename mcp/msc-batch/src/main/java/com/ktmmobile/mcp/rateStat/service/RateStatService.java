package com.ktmmobile.mcp.rateStat.service;

import com.ktmmobile.mcp.rateStat.dao.RateStatDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

@Service
public class RateStatService {

    @Value("${api.interface.server}")
    private String apiInterfaceServer;
    private static final Logger logger = LoggerFactory.getLogger(RateStatService.class);

    @Autowired
    private RateStatDao rateStatDao;

    public Map<String, Object> insertUseRateInfo(String baseMon) {
        Map<String,Object> resultMap = new HashMap<>();

        RestTemplate restTemplate = new RestTemplate();
        int rateInfoCnt = 0;
        logger.error("### 당월 유지고객 정보 저장 START");
        
        int cnt = rateStatDao.getNmcpMonUseRateInfoCnt(baseMon);
        if (cnt > 0) {
            resultMap.put("succCnt", 0);
            resultMap.put("msgDtl", "기 생성된 유지고객 데이터: " + cnt + "건");
            logger.error("### 이미 생성된 데이터가 존재합니다.");
            return resultMap;
        }
        
        // 당월 유지고객 정보 저장
        int addMon = 3;
        String strtDt = "20130301";
        String endDt = "";

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        LocalDate startDate = LocalDate.parse(strtDt, formatter);
        LocalDate today = LocalDate.now();        
        
        Map<String, String> paramMap = new HashMap<>();
        while (!startDate.isAfter(today)) { // 시작일이 오늘보다 크지 않을 때만 반복
            startDate = startDate.plusMonths(addMon);
            endDt = startDate.format(formatter);

            paramMap.put("strtDt", strtDt);
            paramMap.put("endDt", endDt);
            rateInfoCnt = rateInfoCnt + restTemplate.postForObject(apiInterfaceServer + "/msp/insertNmcpUseRateInfo", paramMap, int.class);
            logger.error("### 당월 유지고객 정보 저장 ING >> 시작일: " + strtDt + ", 종료일: " + endDt + ", 저장건수:" + rateInfoCnt + "건");
            strtDt = endDt;
            startDate = LocalDate.parse(strtDt, formatter);
        }
        logger.error("### 당월 유지고객 정보 저장 END >> " + rateInfoCnt + "건");

        resultMap.put("succCnt", rateInfoCnt);
        resultMap.put("msgDtl", "유지고객 정보: " + rateInfoCnt + "건");
        return resultMap;
    }

    public Map<String, Object> insertUseRateStat(String baseMon) {
        Map<String,Object> resultMap = new HashMap<>();
        
        logger.error("### 유지요금제 집계 저장 START");
        int rateStatCnt = 0;

        int cnt = rateStatDao.getNmcpMonUseRateStatCnt(baseMon);
        if (cnt > 0) {
            resultMap.put("succCnt", 0);
            resultMap.put("msgDtl", "기 생성된 집계 데이터: " + cnt + "건");
            logger.error("### 이미 생성된 데이터가 존재합니다.");
            return resultMap;
        }
        
        // 유지요금제 집계 저장
        rateStatCnt = rateStatDao.insertNmcpMonUseRateStat(baseMon);
        logger.error("### 유지요금제 집계 저장 END >> " + rateStatCnt + "건");

        resultMap.put("succCnt", rateStatCnt);
        resultMap.put("msgDtl", "유지요금제 집계: " + rateStatCnt + "건");
        return resultMap;
    }

    public Map<String, Object> deleteUseRateInfo(String baseMon) {
        Map<String,Object> resultMap = new HashMap<>();

        int deleteCnt = 0;
        logger.error("### 이전 유지고객 정보 삭제 START");
        // 유지고객 정보 삭제
        deleteCnt = rateStatDao.deleteNmcpMonUseRateInfo(baseMon);
        logger.error("### 이전 유지고객 정보 삭제 END >> " + deleteCnt + "건");

        resultMap.put("succCnt", deleteCnt);
        resultMap.put("msgDtl", "이전 유지고객 정보 삭제 : " + deleteCnt);
        return resultMap;
    }
}
