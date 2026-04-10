package com.ktmmobile.mcp.send.controller;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.search.dto.SearchDto;
import com.ktmmobile.mcp.send.dto.SendScanDto;
import com.ktmmobile.mcp.send.service.SendScanSvc;

@RestController
@EnableScheduling
public class SendScanController {

    private static final Logger logger = LoggerFactory.getLogger(SendScanController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${scan.url}")
    private String scanUrl;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private SendScanSvc sendScanSvc;

    @Autowired
    private BathHistService bathHistService;

    @RequestMapping("/sendScan")
    public String sendScan() {

        sendScanBatch();

        return "sendScan send:"+serverName;
    }


    /**
     * 미생성 서식지건 자동 생성 배치
     */
    @Scheduled(cron = "0 50 0-9,19-23 * * *") // 50분 // 초 분 시 일 월 주/년
    public void sendScanBatch() {
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "";
        String exeMthd = "";

        try {
            RestTemplate restTemplate = new RestTemplate();
            List<SendScanDto> list = sendScanSvc.selectSendScanList();

            if(list !=null && !list.isEmpty()) {
                batchExeCascnt = list.size();
                for (SendScanDto sendScanDto : list) {
                    try {
                        String reqUrl = "http://"+scanUrl+sendScanDto.getRequestKey();
                        restTemplate.postForObject(reqUrl, null, Void.class);
                        Thread.sleep(500); //0.5초 sleep
                    }catch(Exception e) {
                        msgDtl = e.getMessage();
                        failCascnt++;
                        continue;
                    }
                    successCascnt++;
                }
            }

            Thread.sleep(10000); //10초 sleep


            //MSP_REQUEST_DTL@DL_MSP  T1.SCAN_ID = '999999' 업데이트 처리
            //return restTemplate.postForObject(apiInterfaceServer + "/msp/mspOfficialNoticeSupportCount", mspNoticSupportMstDto, int.class);
            int updateCntDtl = restTemplate.postForObject(apiInterfaceServer + "/msp/updateScanIdForDtl", Void.class, int.class);

            logger.info("updateCntDtl===>"+ updateCntDtl);

            //MSP_JUO_SUB_INFO@DL_MSP  T1.SCAN_ID = '999999' 업데이트 처리
            int updateCntSubInfo = restTemplate.postForObject(apiInterfaceServer + "/msp/updateScanIdForSubInfo", Void.class, int.class);
            logger.info("updateCntSubInfo===>"+ updateCntSubInfo);


        }catch(Exception e) {
            logger.error("sendScanBatch() Exception = {}", e.getMessage());
        } finally {
            try {
                batchCd = "04";
                exeMthd = "sendScanBatch";
                BathHistDto bathHistDto = new BathHistDto();
                bathHistDto.setBatchCd(batchCd);
                bathHistDto.setExeMthd(exeMthd);
                bathHistDto.setBatchExeDate(batchExeDate);
                bathHistDto.setBatchExeStDate(batchExeStDate);
                bathHistDto.setBatchExeCascnt(batchExeCascnt);
                bathHistDto.setSuccessCascnt(successCascnt);
                bathHistDto.setFailCascnt(failCascnt);
                bathHistDto.setMsgDtl(msgDtl);
                bathHistService.insertHist(bathHistDto);
            }catch(Exception e) {
                logger.debug("sendScanBatch error");
            }
        }
    }

}
