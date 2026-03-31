package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formComm.dto.McpFarPriceDto;
import com.ktmmobile.msf.formComm.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.McpRegServiceDto;
import com.ktmmobile.msf.formSvcChg.dto.SvcChgFarPriceReqDto;

import java.util.List;
import java.util.Map;

/**
 * 요금제 변경 서비스.
 * ASIS FarPricePlanController/FarPricePlanServiceImpl 역할.
 * X19(즉시변경), X84(예약일즉시), X88(예약변경), X89(예약조회), X90(예약취소).
 * DB 조회(selectFarPricePlan, getEnggInfo 등)는 FormMypageSvc 위임.
 */
public interface SvcChgFarPriceSvc {

    /**
     * 5006-07 possibleStateCheck — 변경 전 사전체크.
     * DB: 현재 요금제(selectFarPricePlan), 약정정보(getEnggInfo), 적용일(selectFarPriceAddInfo),
     *     X97 이용중 부가서비스, 60분 내 중복변경 여부(checkAllreadPlanchgCount).
     */
    Map<String, Object> possibleStateCheck(SvcChgInfoDto req);

    /**
     * 5006-08 doRegServicePop — 변경 요금제 상세 팝업 조회.
     * 변경할 요금제(rateCd) 기준 변경 가능 목록과 프로모션 부가서비스 정보 반환.
     */
    Map<String, Object> getRegServicePop(SvcChgFarPriceReqDto req);

    /** 변경 가능 요금제 목록 조회 (현재 요금제 SOC → VW_GROUP_BY_RATE_INFO 기반). */
    Map<String, Object> getFarPriceList(SvcChgInfoDto req);

    /** X89 현재 요금제 예약변경 조회. */
    Map<String, Object> getFarPriceReservation(SvcChgInfoDto req);

    /**
     * 5006-12 proPriceChg — 요금제 변경 처리 (즉시 X19 또는 예약 X88).
     * 흐름: 부가서비스 해지(X38) → 요금제 변경(X19/X88) → 부가서비스 신청(X21) →
     *       이력 저장(insertServiceAlterTrace) → 평생할인(insertDisApd).
     */
    Map<String, Object> applyFarPriceChange(SvcChgFarPriceReqDto req);

    /** X90 요금제 예약변경 취소. */
    Map<String, Object> cancelFarPriceReservation(SvcChgInfoDto req);

    /* -- DB 직접 조회 (5006-07/08 화면 진입 시 필요) -- */

    /** 현재 요금제 정보 DB 조회 (MSP_RATE_MST + MSP_JUO_FEATURE_INFO). */
    McpFarPriceDto selectFarPricePlan(String ncn);

    /** 변경 가능 요금제 목록 DB 조회 (VW_GROUP_BY_RATE_INFO). */
    List<McpFarPriceDto> selectFarPricePlanList(String rateCd);

    /** 약정 정보 조회 (VW_CNTR_ENGG_SELF). */
    MspJuoAddInfoDto getEnggInfo(String contractNum);

    /** 해지해야 할 부가서비스 목록 (MSP_JUO_FEATURE_INFO + CMN_GRP_CD_MST). */
    List<McpRegServiceDto> getCloseSubList(String contractNum);

    /** 가입해야 할 프로모션 부가서비스 목록 (VW_CHRG_PRMT_DTL). */
    List<McpRegServiceDto> getromotionDcList(String toSocCode);

    /**
     * X18 실시간 사용요금 조회 — 즉시변경 확인 팝업용.
     * 응답: { success, searchTime, sumAmt, items:[{gubun, payment}] }
     */
    Map<String, Object> getRemainCharge(SvcChgInfoDto req);
}
