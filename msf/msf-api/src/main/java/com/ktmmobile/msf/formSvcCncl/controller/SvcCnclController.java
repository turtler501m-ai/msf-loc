package com.ktmmobile.msf.formSvcCncl.controller;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclRemainChargeResVO;
import com.ktmmobile.msf.formSvcCncl.service.SvcCnclSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 서비스해지 신청서 REST 컨트롤러.
 * 설계서 S104010101~S104040101.
 */
@RestController
@RequestMapping("/api/v1/cancel")
public class SvcCnclController {

    @Autowired
    private SvcCnclSvc svcCnclSvc;

    /**
     * 해지 가능 여부 조회.
     * POST /api/v1/cancel/eligible
     */
    @PostMapping("/eligible")
    public Map<String, Object> eligible(@RequestBody SvcChgInfoDto req) {
        Map<String, Object> result = new HashMap<>();
        boolean ok = svcCnclSvc.eligible(req);
        result.put("eligible", ok);
        result.put("success", true);
        return result;
    }

    /**
     * 잔여요금/위약금 조회 (X18 연동 예정).
     * POST /api/v1/cancel/remain-charge
     */
    @PostMapping("/remain-charge")
    public SvcCnclRemainChargeResVO remainCharge(@RequestBody SvcChgInfoDto req) {
        return svcCnclSvc.getRemainCharge(req);
    }

    /**
     * 서비스해지 신청서 등록.
     * POST /api/v1/cancel/apply
     */
    @PostMapping("/apply")
    public SvcCnclApplyVO apply(@RequestBody SvcCnclApplyDto req) {
        return svcCnclSvc.apply(req);
    }
}
