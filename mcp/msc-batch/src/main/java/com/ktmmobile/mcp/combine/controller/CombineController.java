package com.ktmmobile.mcp.combine.controller;

import com.ktmmobile.mcp.combine.service.CombineService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dto.BathHistDto;
import com.ktmmobile.mcp.log.service.BathHistService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableScheduling
public class CombineController {
    private static final Logger logger = LoggerFactory.getLogger(CombineController.class);

    private final CombineService combineService;
    private final BathHistService bathHistService;

    public CombineController(CombineService combineService, BathHistService bathHistService) {
        this.combineService = combineService;
        this.bathHistService = bathHistService;
    }

    @RequestMapping("/combine/syncCombineSoloRequest")
    public void syncCombineSoloRequestManual(@RequestParam(required = false, value = "baseDate") int baseDate) {
        int syncBaseDate = 3;
        if (baseDate >= 1) {
            syncBaseDate = baseDate;
        }

        this.syncCombineSoloRequest(false, syncBaseDate);
    }

    @Scheduled(cron="0 5 0 * * *")
    public void syncCombineSoloRequestScheduled() {
        this.syncCombineSoloRequest(true, 3);
    }

    public void syncCombineSoloRequest(boolean isScheduled, int baseDate) {
        String exeMthd = isScheduled ? "syncCombineSoloRequestScheduled" : "syncCombineSoloRequestManual";

        logger.info("아무나 SOLO 결합 포함 개통 대상 이력 현행화 배치 {}() START===============================================", exeMthd);
        String batchExeDate = DateTimeUtil.getFormatString("yyyyMMdd");
        String batchExeStDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        int batchExeCascnt = 1;
        String msgDtl = "";
        int failCascnt = 0;
        int successCascnt = 0;
        String batchCd = "29";

        try {
            BathHistDto bathHistDto = combineService.syncCombineSoloRequest(baseDate);
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
