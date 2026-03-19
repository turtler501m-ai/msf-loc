package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.UsimChangeReqDto;
import com.ktmmobile.msf.formSvcChg.dto.UsimCheckReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgUsimSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * USIM 변경 Controller.
 * ASIS AppformController moscIntmMgmtAjax(X85) / usimChangeUC0(UC0) 와 동일 역할.
 *
 * 처리 흐름:
 * 1. POST /check  → X85 USIM 유효성 확인
 * 2. POST /change → X85 사전확인 → UC0 USIM 변경
 */
@RestController
@RequestMapping("/api/v1/service-change/usim")
public class SvcChgUsimController {

    private final SvcChgUsimSvc usimSvc;

    public SvcChgUsimController(SvcChgUsimSvc usimSvc) {
        this.usimSvc = usimSvc;
    }

    /**
     * X85 USIM 유효성 체크.
     * ASIS: /msp/moscIntmMgmtAjax.do
     * 요청: { ncn, ctn, custId, usimNo }
     * 응답: { success, resultCode, usimNo, usimSts, usimStsCd, usimType }
     */
    @PostMapping("/check")
    public ResponseEntity<Map<String, Object>> checkUsim(
            @RequestBody(required = false) UsimCheckReqDto req) {
        Map<String, Object> result = usimSvc.checkUsim(req);
        return ResponseEntity.ok(result);
    }

    /**
     * UC0 USIM 변경 처리 (X85 사전확인 포함).
     * ASIS: AppformSvcImpl.usimChangeUC0()
     * 요청: { ncn, ctn, custId, newUsimNo, currentUsimNo }
     * 응답: { success, resultCode, globalNo, message }
     */
    @PostMapping("/change")
    public ResponseEntity<Map<String, Object>> changeUsim(
            @RequestBody(required = false) UsimChangeReqDto req) {
        Map<String, Object> result = usimSvc.changeUsim(req);
        return ResponseEntity.ok(result);
    }
}
