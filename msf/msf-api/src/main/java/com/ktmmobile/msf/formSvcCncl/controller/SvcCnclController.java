package com.ktmmobile.msf.formSvcCncl.controller;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formComm.service.FormCommSvc;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclApplyVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclConsultReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclDetailResVO;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclProcReqDto;
import com.ktmmobile.msf.formSvcCncl.dto.SvcCnclRemainChargeResVO;
import com.ktmmobile.msf.formSvcCncl.service.SvcCnclSvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Autowired
    private FormCommSvc formCommSvc;

    /**
     * 해지 사전체크.
     * ASIS: CancelConsultController.cancelConsultAjax() 동일 체크 로직.
     * POST /api/v1/cancel/cancelConsult
     */
    @PostMapping("/cancelConsult")
    public Map<String, Object> cancelConsult(@RequestBody SvcCnclConsultReqDto req) {
        return svcCnclSvc.cancelConsult(req);
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

    /**
     * 서비스해지 신청서 단건 상세 조회.
     * M전산 SOURCE_CD='MSF' 분기 처리 시 계약정보 제공용.
     * GET /api/v1/cancel/detail/{requestKey}
     */
    @GetMapping("/detail/{requestKey}")
    public SvcCnclDetailResVO detail(@PathVariable Long requestKey) {
        return svcCnclSvc.getDetail(requestKey);
    }

    /**
     * MSP 처리완료 통보 — MSF_REQUEST_CANCEL.PROC_CD 업데이트.
     * MSP에서 해지 처리 완료 후 호출 (선택적). R4 배치와 병행.
     * POST /api/v1/cancel/proc
     */
    @PostMapping("/proc")
    public Map<String, Object> proc(@RequestBody SvcCnclProcReqDto req) {
        return svcCnclSvc.updateProc(req);
    }

    /**
     * 대리점 목록 조회 (업무공통 FormCommSvc 위임).
     * GET /api/v1/cancel/agencies
     * 공통 엔드포인트: GET /api/v1/comm/agencies
     */
    @GetMapping("/agencies")
    public Map<String, Object> agencies() {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("agencies", formCommSvc.getAgencyList());
        return result;
    }
}
