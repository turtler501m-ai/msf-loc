package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.InsrProductDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgInsrSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 단말보험 Controller.
 * ASIS AppformController selectInsrProdListAjax(DB조회) / Y24(가입가능여부) / X21(신청) 와 동일 역할.
 *
 * 처리 흐름:
 * 1. POST /list         → 보험 상품 목록 조회 (DB)
 * 2. POST /eligibility  → Y24 가입 가능 여부 확인
 * 3. POST /apply        → Y24 사전체크 → X21 신청 → DB 저장
 */
@RestController
@RequestMapping("/api/v1/service-change/insurance")
public class SvcChgInsrController {

    private final SvcChgInsrSvc insrSvc;

    public SvcChgInsrController(SvcChgInsrSvc insrSvc) {
        this.insrSvc = insrSvc;
    }

    /**
     * 단말보험 상품 목록 조회.
     * ASIS: /appform/selectInsrProdListAjax.do → DB 조회
     * 요청: { reqBuyType }  (null이면 전체)
     * 응답: [ { insrProdCd, insrProdNm, soc, monthlyFee, ... }, ... ]
     */
    @PostMapping("/list")
    public ResponseEntity<List<InsrProductDto>> getInsuranceProducts(
            @RequestBody(required = false) InsrApplyReqDto req) {
        String reqBuyType = req != null ? req.getReqBuyType() : null;
        List<InsrProductDto> products = insrSvc.getInsuranceProducts(reqBuyType);
        return ResponseEntity.ok(products);
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
     * 요청: { ncn, ctn, custId, soc, insrProdCd, imei, reqBuyType }
     * 응답: { success, resultCode, globalNo, message }
     */
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyInsurance(
            @RequestBody(required = false) InsrApplyReqDto req) {
        Map<String, Object> result = insrSvc.applyInsurance(req);
        return ResponseEntity.ok(result);
    }
}
