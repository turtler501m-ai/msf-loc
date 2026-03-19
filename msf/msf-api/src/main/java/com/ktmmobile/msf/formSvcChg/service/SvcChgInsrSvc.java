package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;
import com.ktmmobile.msf.formSvcChg.dto.InsrProductDto;

import java.util.List;
import java.util.Map;

/**
 * 단말보험 서비스 인터페이스.
 * ASIS AppformSvcImpl.getInsrProdList() / Y24 가입가능여부 / X21 신청 와 동일 역할.
 */
public interface SvcChgInsrSvc {

    /**
     * 단말보험 상품 목록 조회.
     * ASIS: AppformController.selectInsrProdList() → DB 조회
     */
    List<InsrProductDto> getInsuranceProducts(String reqBuyType);

    /**
     * 단말보험 가입 가능 여부 확인 (Y24 사전체크).
     * ASIS: AppformSvcImpl 가입가능여부 검증 로직
     */
    Map<String, Object> checkInsuranceEligibility(InsrApplyReqDto req);

    /**
     * 단말보험 가입 신청 (X21 부가서비스 신청 + DB 저장).
     * ASIS: AppformController → regSvcChg(X21) + insertInsrApplyMst
     */
    Map<String, Object> applyInsurance(InsrApplyReqDto req);
}
