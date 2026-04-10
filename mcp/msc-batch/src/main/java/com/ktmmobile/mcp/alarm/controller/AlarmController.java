package com.ktmmobile.mcp.alarm.controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.alarm.dto.AlarmDto;
import com.ktmmobile.mcp.alarm.service.AlarmSvc;
import com.ktmmobile.mcp.common.dto.MspSmsTemplateMstDto;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.ObjectUtils;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;

@RestController
@EnableScheduling
public class AlarmController {

    private static final Logger logger = LoggerFactory.getLogger(AlarmController.class);

    public static final int SMS_RETENTION_EXPIRATION = 230;
    public static final String KAKAO_SENDER_KEY = "4d20cd61006833cfc264543493d47797f5e310b8";

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private AlarmSvc alarmSvc;

    @Autowired
    private BathHistService bathHistService;

    @RequestMapping("/alarm")
    public String alarm() {

        alarmBatch();

        return "alarm send:"+serverName;
    }


    /**
     * 약정 만료 알림 발송
     */
    @Scheduled(cron = "0 10 8 * * *") // 일 1회	 08:10:00
    @Transactional
    public void alarmBatch() {

        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 0;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "";
        String exeMthd = "";

        try {
            MspSmsTemplateMstDto mspSmsTemplateMstDto = alarmSvc.getMspSmsTemplateMst(SMS_RETENTION_EXPIRATION);

            List<AlarmDto> list = alarmSvc.selectNotiInfo();

            if (mspSmsTemplateMstDto != null && list !=null && !list.isEmpty()){
                batchExeCascnt = list.size();
                for (AlarmDto alarmDto : list) {
                    String rMobile = alarmDto.getMobileNo();
                    try {
                        //int chkCnt = alarmSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMobile, mspSmsTemplateMstDto.getText(),mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY);
                        int chkCnt = alarmSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMobile, mspSmsTemplateMstDto.getText(),
                                mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), KAKAO_SENDER_KEY, String.valueOf(SMS_RETENTION_EXPIRATION));
                        String sttsCd = "";
                        if(chkCnt <= 0){
                            sttsCd = "F";
                         }else {
                             sttsCd = "S";
                         }
                        alarmDto.setAmdIp(getIp());
                        alarmDto.setNotiTrtSttusCd(sttsCd);
                        alarmSvc.updateNotiInfo(alarmDto);
                    }catch(Exception e) {
                        msgDtl = e.getMessage();
                        failCascnt++;
                        continue;
                    }
                    successCascnt++;
                }
            }
            alarmSvc.deleteNotiInfo();
        }catch(Exception e) {
            logger.error("alarmBatch() Exception = {}", e.getMessage());
        } finally {
            try {
                batchCd = "02";
                exeMthd = "alarmBatch";
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
                logger.debug("alarmBatch error");
            }
        }
    }

    private String getIp() {

        InetAddress local = null;
        try {
            local = InetAddress.getLocalHost();
        }
        catch ( UnknownHostException e ) {
            e.printStackTrace();
        }

        if( local == null ) {
            return "";
        }
        else {
            String ip = local.getHostAddress();
            return ip;
        }

    }

}
