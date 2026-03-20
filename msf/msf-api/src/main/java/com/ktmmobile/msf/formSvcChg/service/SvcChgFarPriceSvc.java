package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgFarPriceReqDto;

import java.util.Map;

/**
 * 요금제 변경 서비스.
 * ASIS FarPricePlanController/FarPricePlanServiceImpl 역할.
 * X19(즉시변경), X84(예약일즉시), X88(예약변경), X89(예약조회), X90(예약취소).
 */
public interface SvcChgFarPriceSvc {

    /** 변경 가능 요금제 목록 조회 (X20 기반 현재 SOC → 변경 가능 목록). */
    Map<String, Object> getFarPriceList(SvcChgInfoDto req);

    /** X89 현재 요금제 예약변경 조회. */
    Map<String, Object> getFarPriceReservation(SvcChgInfoDto req);

    /** 요금상품 변경 처리 (즉시 X19 또는 예약 X88). */
    Map<String, Object> applyFarPriceChange(SvcChgFarPriceReqDto req);

    /** X90 요금제 예약변경 취소. */
    Map<String, Object> cancelFarPriceReservation(SvcChgInfoDto req);
}
