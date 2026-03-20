package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.CombineCheckReqDto;

import java.util.Map;

/**
 * 아무나 SOLO 결합 서비스.
 * ASIS CombineController 역할: X87 체크, DB 할인SOC 조회, Y44 결합 처리.
 */
public interface SvcChgCombineSvc {

    /** 아무나 SOLO 결합 가입 가능 여부 체크 (X87 기존결합 + DB 할인SOC 매핑). */
    Map<String, Object> checkCombineSelf(CombineCheckReqDto req);

    /** 요금제별 아무나 SOLO 결합 할인SOC 목록 (DB 조회). */
    Map<String, Object> getCombineSoloType(String rateCd);

    /** 아무나 SOLO 결합 신청 처리 (Y44). */
    Map<String, Object> regCombineSelf(CombineCheckReqDto req);
}
