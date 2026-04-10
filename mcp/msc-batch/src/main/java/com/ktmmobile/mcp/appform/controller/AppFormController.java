package com.ktmmobile.mcp.appform.controller;

import com.ktmmobile.mcp.appform.Service.AppFormService;
import com.ktmmobile.mcp.appform.dto.AppFormDto;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.mstore.controller.MstoreController;
import lombok.extern.log4j.Log4j2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@EnableAsync
@EnableScheduling
@Log4j2
public class AppFormController {

    private static final Logger logger = LoggerFactory.getLogger(AppFormController.class);

    @Autowired
    private AppFormService appFormService;

    @Autowired
    private BathHistService bathHistService;

    @GetMapping("/deleteAppFromTest")
    public void deleteAppFromTest(){
        //테스트 가능 여부 확인
        this.deleteAppFrom();
    }

    @Scheduled(cron = "0 0 23 * * *") // 하루 1회 23시
    //@Scheduled(cron = "0 2/5 * * * *")
    public void deleteAppFrom() {

        BathHistDto bathHistDto = new BathHistDto();
        bathHistDto.setBatchExeStDate(DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
        bathHistDto.setBatchExeDate(DateTimeUtil.getFormatString("yyyyMMdd"));

        List<AppFormDto> appFormDtos = this.appFormService.selectDeleteMaterials();
        //logger.info("appFormDtos.size()==>" + appFormDtos.size());
        if ( !CollectionUtils.isEmpty(appFormDtos) ) {
            bathHistDto.setSuccessCascnt(bathHistDto.getSuccessCascnt() < 1  ? 0 : bathHistDto.getSuccessCascnt());
            bathHistDto.setFailCascnt(ObjectUtils.isEmpty(bathHistDto.getFailCascnt()) ? 0 : bathHistDto.getFailCascnt());
            appFormDtos.forEach(appfrom -> {
                try {
                    int cnt = 0;
                    cnt += this.appFormService.deleteMcpRequest(appfrom.getRequestKey());          // 가입신청
                    cnt += this.appFormService.deleteMcpRequestCstmr(appfrom.getRequestKey());     // 가입신청 고객정보
                    cnt += this.appFormService.deleteMcpRequestSaleinfo(appfrom.getRequestKey());  // 고객상품정보
                    cnt += this.appFormService.deleteMcpRequestDlvry(appfrom.getRequestKey());     // 배송정보
                    cnt += this.appFormService.deleteMcpRequestReq(appfrom.getRequestKey());       // 가입신청 정보
                    cnt += this.appFormService.deleteMcpRequestMove(appfrom.getRequestKey());      // MNP정보
                    cnt += this.appFormService.deleteMcpRequestPayment(appfrom.getRequestKey());   // ??
                    cnt += this.appFormService.deleteMcpRequestAgent(appfrom.getRequestKey());     // 가입 대리인정보
                    cnt += this.appFormService.deleteMcpRequestState(appfrom.getRequestKey());     // 가입진행정보a
                    cnt += this.appFormService.deleteMcpRequestChange(appfrom.getRequestKey());    // 명변정보
                    cnt += this.appFormService.deleteMcpRequestDvcChg(appfrom.getRequestKey());    // 기변정보
                    cnt += this.appFormService.deleteMcpRequestAddition(appfrom.getRequestKey());  // 가입신청
                    cnt += this.appFormService.deleteMcpRequestOsst(appfrom.getResNo());           // OSST연동
                    cnt += this.appFormService.deleteMcpRequestKtInter(appfrom.getResNo());        // KT 인터넷 관련 정보
                    cnt += this.appFormService.deleteMcpRequestDtl(appfrom.getRequestKey());            // MCP_REQUEST_DTL
                    bathHistDto.setSuccessCascnt(bathHistDto.getSuccessCascnt() + cnt);

                } catch (Exception e) {
                    if (e.getCause() != null) {
                        bathHistDto.setMsgDtl(bathHistDto.getMsgDtl() + " requestKey : " + appfrom.getRequestKey() + " Exception Cause : " + e.getCause());
                        bathHistDto.setFailCascnt(bathHistDto.getFailCascnt() + 1);
                        e = new Exception();
                    }
                }
            });
            bathHistDto.setBatchCd("06");
            bathHistDto.setExeMthd("deleteAppFrom");
            bathHistDto.setBatchExeCascnt(bathHistDto.getSuccessCascnt() + bathHistDto.getFailCascnt());
            bathHistDto.setBatchExeEndDate(DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
             this.bathHistService.insertHist(bathHistDto);
        }
    }
}
