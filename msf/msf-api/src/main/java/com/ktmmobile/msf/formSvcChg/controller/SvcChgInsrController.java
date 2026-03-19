package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgInsrSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 단말보험 Controller.
 * ASIS AppformController Y24(가입가능여부) / X21(신청) 와 동일 역할.
 *
 * 처리 흐름:
 * 1. POST /list         → MSP_INTM_INSR_MST@DL_MSP 상품 목록 조회
 * 2. POST /eligibility  → Y24 가입 가능 여부 확인
 * 3. POST /apply        → Y24 사전체크 → X21 신청 → MSF_CUST_REQUEST_INSR 저장
 */
@RestController
@RequestMapping("/api/v1/service-change/insurance")
public class SvcChgInsrController {

    private final SvcChgInsrSvc insrSvc;

    public SvcChgInsrController(SvcChgInsrSvc insrSvc) {
        this.insrSvc = insrSvc;
    }

    /**
     * 단말보험 상품 목록 조회 (DB링크: MSP_INTM_INSR_MST@DL_MSP).
     * ASIS: AppformController → selectInsrProdList()
     * 요청: { reqBuyType } — UU=최초구매, MM=재구매
     * 응답: { success, items: [...] }
     */
    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getInsuranceProducts(
            @RequestBody(required = false) Map<String, String> req) {
        String reqBuyType = (req != null) ? req.get("reqBuyType") : "UU";
        Map<String, Object> result = insrSvc.getInsuranceProducts(reqBuyType);
        return ResponseEntity.ok(result);
    }

    /**
     * 단말보험 가입 가능 여부 (Y24 사전체크).
     * ASIS: AppformSvcImpl 가입가능여부 검증
     * 요청: { ncn, ctn, custId, soc }
     * 응답: { success, eligible, resultCode, message }
     */
    @PostMapping("/eligibility")
    public ResponseEntity<Map<String, Object>> checkInsuranceEligibility(
            @RequestBody(required = false) InsrApplyReqDto req) {
        Map<String, Object> result = insrSvc.checkInsuranceEligibility(req);
        return ResponseEntity.ok(result);
    }

    /**
     * 단말보험 가입 신청 (Y24 → X21 → DB).
     * ASIS: AppformController → regSvcChg(X21) + insertInsrApplyMst
     * 요청: { ncn, ctn, custId, soc, insrProdCd, requestKey, clauseInsuranceYn, clauseInsrProdYn }
     * 응답: { success, resultCode, globalNo, message }
     */
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyInsurance(
            @RequestBody(required = false) InsrApplyReqDto req) {
        Map<String, Object> result = insrSvc.applyInsurance(req);
        return ResponseEntity.ok(result);
    }
}
