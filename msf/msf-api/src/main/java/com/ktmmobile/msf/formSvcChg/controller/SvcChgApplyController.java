package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.SvcChgApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgApplyResVO;
import com.ktmmobile.msf.formSvcChg.service.SvcChgApplySvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 서비스변경 통합 신청 REST 컨트롤러.
 * POST /api/v1/service-change/apply — 선택된 모든 서비스변경 항목 처리 + DB 신청서 저장.
 */
@RestController
@RequestMapping("/api/v1/service-change")
public class SvcChgApplyController {

    @Autowired
    private SvcChgApplySvc svcChgApplySvc;

    /**
     * 서비스변경 통합 신청.
     * POST /api/v1/service-change/apply
     */
    @PostMapping("/apply")
    public SvcChgApplyResVO apply(@RequestBody SvcChgApplyReqDto req) {
        return svcChgApplySvc.apply(req);
    }
}
