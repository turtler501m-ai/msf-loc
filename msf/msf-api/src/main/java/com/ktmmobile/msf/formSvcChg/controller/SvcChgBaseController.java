package com.ktmmobile.msf.formSvcChg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktmmobile.msf.formComm.dto.ConcurrentCheckReqDto;
import com.ktmmobile.msf.formComm.dto.ConcurrentCheckResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCheckReqDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseCheckResVO;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgBaseTargetResVO;
import com.ktmmobile.msf.formSvcChg.service.SvcChgBaseSvc;

/**
 * 서비스변경 기본 컨트롤러.
 * 업무 목록 조회, 서비스체크, 동시처리 불가 체크 엔드포인트 제공.
 */
@RestController
@RequestMapping("/api/v1/service-change")
public class SvcChgBaseController {

    @Autowired
    private SvcChgBaseSvc svcChgBaseSvc;

    /**
     * 서비스변경 업무 목록 조회.
     * GET /api/v1/service-change/base/targets
     */
    @GetMapping("/base/targets")
    public SvcChgBaseTargetResVO getChangeTargets() {
        return svcChgBaseSvc.getChangeTargets();
    }

    /**
     * 서비스체크 유효성 확인.
     * POST /api/v1/service-change/base/check
     */
    @PostMapping("/base/check")
    public SvcChgBaseCheckResVO validateServiceCheck(@RequestBody SvcChgBaseCheckReqDto req) {
        return svcChgBaseSvc.validateServiceCheck(req);
    }

    /**
     * 동시처리 불가 체크.
     * POST /api/v1/service-change/concurrent-check
     */
    @PostMapping("/concurrent-check")
    public ConcurrentCheckResVO concurrentCheck(@RequestBody ConcurrentCheckReqDto req) {
        return svcChgBaseSvc.concurrentCheck(req);
    }
}
