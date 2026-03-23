package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.UsimChangeReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgUsimSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * USIM 변경 Controller.
 * ASIS AppformController usimChangeUC0(UC0) 와 동일 역할.
 * X85 USIM 유효성 체크는 공통 엔드포인트 POST /api/v1/comm/usim-check 사용.
 *
 * 처리 흐름:
 * POST /change → X85 사전확인(changeUsim 내부) → UC0 USIM 변경
 */
@RestController
@RequestMapping("/api/v1/service-change/usim")
public class SvcChgUsimController {

    private final SvcChgUsimSvc usimSvc;

    public SvcChgUsimController(SvcChgUsimSvc usimSvc) {
        this.usimSvc = usimSvc;
    }

    /**
     * UC0 USIM 변경 처리 (X85 사전확인 포함).
     * POST /api/v1/service-change/usim/change
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
