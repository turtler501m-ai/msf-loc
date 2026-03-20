package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.PauseApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.PauseCheckReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgPauseSvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 분실복구/일시정지해제 Controller.
 * ASIS PauseController 와 동일 역할.
 */
@RestController
@RequestMapping("/api/v1/service-change/pause")
public class SvcChgPauseController {

    private final SvcChgPauseSvc pauseSvc;

    public SvcChgPauseController(SvcChgPauseSvc pauseSvc) {
        this.pauseSvc = pauseSvc;
    }

    /**
     * 분실복구/일시정지해제 조회 (X26 이력 + X28 해제가능여부 + X33 분실신고가능여부).
     * POST /api/v1/service-change/pause/check
     */
    @PostMapping("/check")
    public Map<String, Object> check(@RequestBody PauseCheckReqDto req) {
        return pauseSvc.check(req);
    }

    /**
     * 분실복구(X35) 또는 일시정지해제(X30) 처리.
     * POST /api/v1/service-change/pause/apply
     * 요청: { ncn, ctn, custId, applyType: "PAUSE_CNL"|"LOST_CNL", password, pwdType? }
     */
    @PostMapping("/apply")
    public Map<String, Object> apply(@RequestBody PauseApplyReqDto req) {
        return pauseSvc.apply(req);
    }
}
