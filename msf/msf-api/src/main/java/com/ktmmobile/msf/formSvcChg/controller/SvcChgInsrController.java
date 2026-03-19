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
 * ASIS CustRequestController(reqInsr.do) / AppformController(selectInsrProdListAjax.do) 동일 역할.
 *
 * 처리 흐름 (ASIS 기준):
 * 1. POST /insr-info → DB조회 → 보험상태·insrViewType 반환 (Y24 없음)
 * 2. POST /list      → MSP_INTM_INSR_MST@DL_MSP 상품 목록 조회
 * 3. POST /apply     → X21 신청 → MSF_CUST_REQUEST_INSR 저장 (Y24 없음)
 */
@RestController
@RequestMapping("/api/v1/service-change/insurance")
public class SvcChgInsrController {

    private final SvcChgInsrSvc insrSvc;

    public SvcChgInsrController(SvcChgInsrSvc insrSvc) {
        this.insrSvc = insrSvc;
    }

    /**
     * 보험 가입 상태 조회 및 화면 타입 결정.
     * ASIS: CustRequestController.reqInsr() → getInsrInfo(contractNum) → insrViewType 결정.
     * 요청: { ncn }
     * 응답: { success, insrViewType, reqBuyType, insrStatCd, orderExist, requestExist }
     */
    @PostMapping("/insr-info")
    public ResponseEntity<Map<String, Object>> getInsrInfo(
            @RequestBody(required = false) Map<String, String> req) {
        String ncn = (req != null) ? req.get("ncn") : null;
        Map<String, Object> result = insrSvc.getInsrInfo(ncn);
        return ResponseEntity.ok(result);
    }

    /**
     * 단말보험 상품 목록 조회 (DB링크: MSP_INTM_INSR_MST@DL_MSP).
     * ASIS: AppformController.selectInsrProdListAjax.do → AppformMapper.selectInsrProdList()
     * 요청: { reqBuyType, rprsPrdtId } — UU=유심전용, MM=단말
     * 응답: { success, items: [...] }
     */
    @PostMapping("/list")
    public ResponseEntity<Map<String, Object>> getInsuranceProducts(
            @RequestBody(required = false) Map<String, String> req) {
        String reqBuyType = (req != null) ? req.get("reqBuyType") : "UU";
        String rprsPrdtId = (req != null) ? req.get("rprsPrdtId") : null;
        Map<String, Object> result = insrSvc.getInsuranceProducts(reqBuyType, rprsPrdtId);
        return ResponseEntity.ok(result);
    }

    /**
     * 단말보험 가입 신청 (X21 → DB 저장).
     * ASIS: CustRequestController.custRequestAjax(reqType=IS) → regSvcChg(X21).
     * Y24 사전체크 없음.
     * 요청: { ncn, ctn, custId, insrProdCd, requestKey, clauseInsuranceYn, clauseInsrProdYn, insrAuthInfo }
     * 응답: { success, resultCode, globalNo, message }
     */
    @PostMapping("/apply")
    public ResponseEntity<Map<String, Object>> applyInsurance(
            @RequestBody(required = false) InsrApplyReqDto req) {
        Map<String, Object> result = insrSvc.applyInsurance(req);
        return ResponseEntity.ok(result);
    }
}
