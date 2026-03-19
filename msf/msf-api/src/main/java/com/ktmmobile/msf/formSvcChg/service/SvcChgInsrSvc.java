package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;

import java.util.Map;

/**
 * 단말보험 서비스 인터페이스.
 * ASIS AppformSvcImpl Y24(가입가능여부) / X21(신청) + MSF_CUST_REQUEST_INSR 저장.
 * 상품 목록: MSP_INTM_INSR_MST@DL_MSP (DB링크)
 */
public interface SvcChgInsrSvc {

    /**
     * 단말보험 상품 목록 조회 (DB링크: MSP_INTM_INSR_MST@DL_MSP).
     * ASIS: AppformSvcImpl.selectInsrProdList()
     * @param reqBuyType UU=최초구매, MM=재구매
     */
    Map<String, Object> getInsuranceProducts(String reqBuyType);

    /**
     * 단말보험 가입 가능 여부 확인 (Y24 사전체크).
     */
    Map<String, Object> checkInsuranceEligibility(InsrApplyReqDto req);

    /**
     * 단말보험 가입 신청 (Y24 사전체크 → X21 신청 → MSF_CUST_REQUEST_INSR 저장).
     */
    Map<String, Object> applyInsurance(InsrApplyReqDto req);
}
