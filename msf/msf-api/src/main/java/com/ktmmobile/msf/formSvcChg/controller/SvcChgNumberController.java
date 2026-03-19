package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formSvcChg.dto.NumberChangeReqDto;
import com.ktmmobile.msf.formSvcChg.dto.NumberReserveReqDto;
import com.ktmmobile.msf.formSvcChg.dto.NumberSearchReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgNumberSvc;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 번호변경 Controller.
 * ASIS AppformController searchNumberAjax(NU1) / setNumberAjax(NU2) / numChgeChg(X32) 와 동일 역할.
 *
 * 처리 흐름:
 * 1. POST /search     → NU1 희망번호 조회 (가능한 번호 목록)
 * 2. POST /reserve    → NU2 번호 예약
 * 3. POST /reserve-cancel → NU2 번호 예약 취소
 * 4. POST /change     → X32 번호변경 처리
 */
@RestController
@RequestMapping("/api/v1/service-change/number")
public class SvcChgNumberController {

    private final SvcChgNumberSvc numberSvc;

    public SvcChgNumberController(SvcChgNumberSvc numberSvc) {
        this.numberSvc = numberSvc;
    }

    /**
     * NU1 희망번호 조회.
     * ASIS: /appform/searchNumberAjax.do
     * 요청: { ncn, ctn, custId, wantNo }
     * 응답: [ "01012341234", "01056785678", ... ]
     */
    @PostMapping("/search")
    public ResponseEntity<List<String>> searchNewNumber(
            @RequestBody(required = false) NumberSearchReqDto req) {
        List<String> phoneList = numberSvc.searchNewNumber(req);
        return ResponseEntity.ok(phoneList);
    }

    /**
     * NU2 희망번호 예약.
     * ASIS: /appform/setNumberAjax.do
     * 요청: { ncn, ctn, custId, tlphNo }
     * 응답: { success, resultCode, globalNo, reservedTlphNo }
     */
    @PostMapping("/reserve")
    public ResponseEntity<Map<String, Object>> reserveNumber(
            @RequestBody(required = false) NumberReserveReqDto req) {
        Map<String, Object> result = numberSvc.reserveNumber(req);
        return ResponseEntity.ok(result);
    }

    /**
     * NU2 희망번호 예약 취소.
     * ASIS: /appform/cancelNumberAjax.do
     * 요청: { ncn, ctn, custId, tlphNo }
     * 응답: { success, resultCode, globalNo }
     */
    @PostMapping("/reserve-cancel")
    public ResponseEntity<Map<String, Object>> cancelReservedNumber(
            @RequestBody(required = false) NumberReserveReqDto req) {
        Map<String, Object> result = numberSvc.cancelReservedNumber(req);
        return ResponseEntity.ok(result);
    }

    /**
     * X32 번호변경 처리.
     * ASIS: MplatFormService.numChgeChg(X32)
     * 요청: { ncn, ctn, custId, newTlphNo, reservedTlphNo }
     * 응답: { success, resultCode, globalNo, newTlphNo }
     */
    @PostMapping("/change")
    public ResponseEntity<Map<String, Object>> changeNumber(
            @RequestBody(required = false) NumberChangeReqDto req) {
        Map<String, Object> result = numberSvc.changeNumber(req);
        return ResponseEntity.ok(result);
    }
}
