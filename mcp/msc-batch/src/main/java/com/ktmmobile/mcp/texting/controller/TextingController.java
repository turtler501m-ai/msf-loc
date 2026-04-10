package com.ktmmobile.mcp.texting.controller;
import java.time.LocalDate;
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

import com.ktmmobile.mcp.acen.service.AcenService;
import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.texting.dto.FormDtlDTO;
import com.ktmmobile.mcp.texting.dto.TextingDto;
import com.ktmmobile.mcp.texting.service.FormDtlSvc;
import com.ktmmobile.mcp.texting.service.TextingService;



@RestController
@EnableScheduling
public class TextingController {

    private static final Logger logger = LoggerFactory.getLogger(TextingController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private TextingService textingService;

    @Autowired
    private AcenService  acenService;

    @Autowired
    private FormDtlSvc  formDtlSvc;



    @Value("${sms.callcenter}")
    private String callCenter;

//http://localhost:9080/textingBatch
    @RequestMapping("/textingBatch")
    public String textingLmsBatch() {

        textingBatch();

        return "textingController";
    }

    //매일 오후 17시에 상담사 개통 접수대기 대상 문자 자동 발송 배치(일요일/신정/설/추석 제외)
    @Scheduled(cron = "0 0 17 * * MON-SAT")
    public void textingBatch(){

        //배치 on/off
        String onOffVal = "";
        //템플릿 번호
        int templateNo = 0;
        //신청기간 시작일
        String startDt = "";

        //BatchSwitch
      FormDtlDTO formDtlDTO = new FormDtlDTO();
      formDtlDTO.setCdGroupId1("WaitingSelfOpening");
      List<FormDtlDTO> listFormReq = formDtlSvc.FormDtlLists(formDtlDTO) ;
      for (FormDtlDTO formDtl : listFormReq) {
              if("BatchSwitch".equals(formDtl.getDtlCd())){

                  LocalDateTime now = LocalDateTime.now();

                  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

                  LocalDateTime  startDate = LocalDateTime .parse(formDtl.getPstngStartDate(), formatter);
                  LocalDateTime  endDate = LocalDateTime .parse(formDtl.getPstngEndDate(), formatter);

                  if ((now.isEqual(startDate) ||now.isAfter(startDate)) &&
                          (now.isEqual(endDate) || now.isBefore(endDate))) {
                              onOffVal = formDtl.getDtlCdNm();
                      }

              }

              if("templateNo".equals(formDtl.getDtlCd())) {
                  templateNo=Integer.parseInt( formDtl.getDtlCdNm());
              }

              if("startDt".equals(formDtl.getDtlCd())) {
                  startDt = formDtl.getDtlCdNm();
              }
      }

      //신정/설/추석 제외
       int holiday = textingService.getHolidayLimit();
       if(1 > holiday) {
            if("Y".equals(onOffVal)) {
                try {

                    MspSmsTemplateMstDto mspSmsTemplateMstDto = textingService.getMspSmsTemplateMst(templateNo);

                    //상담사 개통 접수대기 대상 회선번호 리스트
                    List<TextingDto> textingNoList = textingService.selectTextingNoList(startDt);

                    if (textingNoList != null && mspSmsTemplateMstDto != null) {
                        for (TextingDto dto : textingNoList) {
                            try {
                                String mobile = dto.getMobileNo();  // TextingDto에서 전화번호 가져오기
                                textingService.sendLms(mspSmsTemplateMstDto.getSubject(), mobile,mspSmsTemplateMstDto.getText(), callCenter, "texting", "textingBatch");
                                logger.info("textingBatch 발송 성공 - 번호: " + mobile);
                            } catch (Exception e) {
                                logger.error("textingBatch 발송 실패 - 번호: " + dto.getMobileNo(),  e.getMessage());
                            }
                        }
                    }

                } catch (Exception e) {
                    logger.error("textingBatch() Exception = {}", e.getMessage());
                }
            }
       }
    }
}
