package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCancelReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formSvcChg.dto.AdditionPreCheckReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionRegReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgRegSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 부가서비스 신청/해지 REST 컨트롤러.
 * REQ_010202~010204 핵심 엔드포인트 제공.
 */
@RestController
@RequestMapping("/api/v1/addition")
public class SvcChgRegController {

    @Autowired
    private SvcChgRegSvc svcChgRegSvc;

    /**
     * 현재 가입 부가서비스 조회 (X20).
     * POST /api/v1/addition/current
     */
    @PostMapping("/current")
    public AdditionCurrentResVO additionCurrent(@RequestBody SvcChgInfoDto req) {
        return svcChgRegSvc.selectAdditionCurrent(req);
    }

    /**
     * 부가서비스 변경 사전체크 (Y24 — 무선데이터차단, 정보료상한 [확인] 버튼).
     * POST /api/v1/addition/pre-check
     */
    @PostMapping("/pre-check")
    public Map<String, Object> preCheckAddition(@RequestBody AdditionPreCheckReqDto req) {
        return svcChgRegSvc.preCheckAddition(req);
    }

    /**
     * 부가서비스 신청 (X21).
     * POST /api/v1/addition/reg
     */
    @PostMapping("/reg")
    public Map<String, Object> additionReg(@RequestBody AdditionRegReqDto req) {
        return svcChgRegSvc.additionReg(req);
    }

    /**
     * 부가서비스 해지 (X38).
     * POST /api/v1/addition/cancel
     */
    @PostMapping("/cancel")
    public Map<String, Object> additionCancel(@RequestBody AdditionCancelReqDto req) {
        return svcChgRegSvc.additionCancel(req);
    }
}
