package com.ktmmobile.mcp.fath.controller;


import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.fath.dto.FathDto;
import com.ktmmobile.mcp.fath.service.FathService;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.texting.service.TextingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@RestController
@EnableScheduling
@EnableAsync
public class FathController {

    private static final Logger logger = LoggerFactory.getLogger(FathController.class);

    @Autowired
    private FathService fathService;

    @Autowired
    private BathHistService bathHistService;

    @Autowired
    private TextingService textingService;

    @Value("${sms.callcenter}")
    private String callCenter;
    
    @Value("${fath.url}")
    private String fathSelfUrl;
    /**
     * 설명 : 안면인증 문자전송 및 상태변경 (5분간격으로 실행)
     * @Date : 2025.12.19
     */
    /*ACEN 대상 안면인증 대상 배치 로직변경으로 미사용*/
    // 08:00 ~ 19:55 (5분 간격)
    //@Scheduled(cron = "0 0/5 8-19 * * *")

    // 20:00 ~ 20:30 (5분 간격)
    //@Scheduled(cron = "0 0-30/5 20 * * *")
    public void fathBatch(){

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

            //셀프안면인증 URL 발송 대상조회 
            List<FathDto> fathSelfTrgList = fathService.selectFathSelfTrg();
            if (fathSelfTrgList == null || fathSelfTrgList.isEmpty()) {
                logger.error("=========== [fathBatch] fathSelfUrlList is empty");
                return;
            }
            batchExeCascnt = fathSelfTrgList.size();
            
            for(FathDto dto : fathSelfTrgList){
                String uuid = UUID.randomUUID().toString();
                String url = fathSelfUrl + "?u=" + uuid;
                dto.setUuid(uuid);
                dto.setUrl(url);
                fathService.insertFathSelfUrl(dto);
                
                String smsRcvTelNo = dto.getSmsRcvTelNo();
                
                int templateId = 443;
                
                MspSmsTemplateMstDto mspSmsTemplateMstDto = textingService.getMspSmsTemplateMst(templateId);
                String text = mspSmsTemplateMstDto.getText()
                        .replaceAll(Pattern.quote("#{url}"), url);
                //문자전송
                textingService.sendLms(
                        mspSmsTemplateMstDto.getSubject(),
                        smsRcvTelNo,
                        text,
                        callCenter,
                        "fath",
                        "fathBatch");
                //상태값 변경
                fathService.updateReqStateCode(dto.getResNo());
                successCascnt++;

            }
        }catch (Exception e) {
            logger.error("fathBatch() Exception = {}", e.getMessage());
            failCascnt++;
        }finally {
            try{
                batchCd = "26";
                exeMthd = "fathBatch";
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
                logger.debug("fathBatch error");
            }
        }
    }
}
