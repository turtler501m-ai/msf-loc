package com.ktmmobile.msf.formSvcChg.controller;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgFarPriceReqDto;
import com.ktmmobile.msf.formSvcChg.service.SvcChgFarPriceSvc;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 요금제 변경 Controller.
 * ASIS FarPricePlanController 역할.
 * X19(즉시변경), X84(예약일즉시), X88(예약변경익월), X89(예약조회), X90(예약취소).
 */
@RestController
@RequestMapping("/api/v1/service-change/far-price")
public class SvcChgFarPriceController {

    private final SvcChgFarPriceSvc farPriceSvc;

    public SvcChgFarPriceController(SvcChgFarPriceSvc farPriceSvc) {
        this.farPriceSvc = farPriceSvc;
    }

    /**
     * 변경 가능 요금제 목록 조회 (X20 현재 가입SOC 기반).
     * POST /api/v1/service-change/far-price/list
     */
    @PostMapping("/list")
    public Map<String, Object> getFarPriceList(@RequestBody SvcChgInfoDto req) {
        return farPriceSvc.getFarPriceList(req);
    }

    /**
     * X89 현재 요금제 예약변경 조회.
     * POST /api/v1/service-change/far-price/reservation
     */
    @PostMapping("/reservation")
    public Map<String, Object> getFarPriceReservation(@RequestBody SvcChgInfoDto req) {
        return farPriceSvc.getFarPriceReservation(req);
    }

    /**
     * 요금제 변경 처리 (즉시 X19 또는 예약 X88).
     * POST /api/v1/service-change/far-price/change
     * 요청: { ncn, ctn, custId, soc, schedule: "immediate"|"reserve" }
     */
    @PostMapping("/change")
    public Map<String, Object> applyFarPriceChange(@RequestBody SvcChgFarPriceReqDto req) {
        return farPriceSvc.applyFarPriceChange(req);
    }

    /**
     * X90 요금제 예약변경 취소.
     * POST /api/v1/service-change/far-price/reservation/cancel
     */
    @PostMapping("/reservation/cancel")
    public Map<String, Object> cancelFarPriceReservation(@RequestBody SvcChgInfoDto req) {
        return farPriceSvc.cancelFarPriceReservation(req);
    }

    /**
     * 5006-07 possibleStateCheck — 요금제 변경 화면 진입 사전체크.
     * 현재 요금제, 약정정보, 적용일, 이용중 부가서비스, 중복변경 여부 한번에 조회.
     * POST /api/v1/service-change/far-price/possible-check
     */
    @PostMapping("/possible-check")
    public Map<String, Object> possibleStateCheck(@RequestBody SvcChgInfoDto req) {
        return farPriceSvc.possibleStateCheck(req);
    }

    /**
     * 5006-08 doRegServicePop — 변경 요금제 상세 팝업.
     * 변경할 요금제(soc) 기준 프로모션 부가서비스 및 해지 대상 부가서비스 조회.
     * POST /api/v1/service-change/far-price/reg-service-pop
     */
    @PostMapping("/reg-service-pop")
    public Map<String, Object> getRegServicePop(@RequestBody SvcChgFarPriceReqDto req) {
        return farPriceSvc.getRegServicePop(req);
    }
}
