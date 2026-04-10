package com.ktmmobile.mcp.rate.guide.controller;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import com.ktmmobile.mcp.rate.guide.service.RateGuideService;
import com.ktmmobile.mcp.rate.guide.service.RateGuideVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class RateGuideController {
    private static final Logger logger = LoggerFactory.getLogger(RateGuideController.class);

    private final RateGuideService rateGuideService;
    private final RateGuideVersionService rateGuideVersionService;
    private final BathHistService bathHistService;

    public RateGuideController(RateGuideService rateGuideService, RateGuideVersionService rateGuideVersionService, BathHistService bathHistService) {
        this.rateGuideService = rateGuideService;
        this.rateGuideVersionService = rateGuideVersionService;
        this.bathHistService = bathHistService;
    }

    @RequestMapping("/rate/guide/makeAllXmlFiles")
    public String makeAllXmlFiles() {
        if (rateGuideService.createAndUploadAllXmlFiles()) {
            logger.error("All xml files made");
            return "00000";
        } else {
            logger.error("failed to make xml files");
            return "99999";
        }
    }

    @RequestMapping("/rate/guide/makeGdncProdRelXmlFile")
    public String makeGdncProdRelXmlFile() {
        try {
            if (rateGuideService.createAndUploadGdncProdRelXmlFile()) {
                logger.error("NMCP_RATE_ADSVC_GDNC_PROD_REL.xml files made");
            }
        } catch (Exception e) {
            logger.error("failed to make xml files");
            return "99999";
        }
        return "00000";
    }

    @RequestMapping("/rate/guide/makeRateEventCodePrmtXmlFile")
    public String makeRateEventCodePrmtXmlFile() {
        try {
            //1. NMCP_RATE_EVENT_CODE_PRMT.xml
            if (rateGuideService.createAndUploadRateEventCodePrmtXmlFile()) {
                logger.error("NMCP_RATE_EVENT_CODE_PRMT.xml files made");
            }

            /*
            //2. NMCP_RATE_ADSVC_GDNC_PROD_REL.xml
            if (rateGuideService.createAndUploadGdncProdRelXmlFile()) {
                logger.error("NMCP_RATE_ADSVC_GDNC_PROD_REL.xml files made");
            }
            */
        } catch (Exception e) {
            logger.error("failed to make xml files");
            return "99999";
        }
        return "00000";
    }

    @RequestMapping("/rate/guide/invalidateNotLatestVersions")
    public String invalidateNotLatestVersions() {
        rateGuideVersionService.invalidateNotLatestVersions();
        return "00000";
    }

    @RequestMapping("/rate/guide/cleanupExpiredVersions")
    public String cleanupExpiredVersions() {
        rateGuideVersionService.cleanupExpiredVersions();
        return "00000";
    }


    @Scheduled(cron="0 10 0 * * *")
    public void invalidateNotLatestVersionsScheduled() {
        logger.info("요금제 안내 데이터 오래된 버전 만료 처리 배치 invalidateNotLatestVersionsScheduled() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "27";
        String exeMthd = "invalidateNotLatestVersionsScheduled";

        try {
            BathHistDto bathHistDto = rateGuideVersionService.invalidateNotLatestVersions();
            successCascnt = bathHistDto.getSuccessCascnt();
            failCascnt = bathHistDto.getFailCascnt();
        } catch(Exception e) {
            e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
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
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }

    @Scheduled(cron="0 15 0 * * *")
    public void cleanupExpiredVersionsScheduled() {
        logger.info("요금제 안내 데이터 오래된 버전 만료 처리 배치 cleanupExpiredVersionsScheduled() START===============================================");
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "28";
        String exeMthd = "cleanupExpiredVersionsScheduled";

        try {
            BathHistDto bathHistDto = rateGuideVersionService.cleanupExpiredVersions();
            successCascnt = bathHistDto.getSuccessCascnt();
            failCascnt = bathHistDto.getFailCascnt();
        } catch(Exception e) {
            e.printStackTrace();
            msgDtl = e.getMessage();
        } finally {
            try {
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
            } catch(Exception e) {
                logger.error(exeMthd + " error");
            }
        }
    }
}
