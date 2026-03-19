package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formSvcChg.dto.InsrApplyReqDto;

import java.util.Map;

/**
 * 단말보험 서비스 인터페이스.
 * ASIS CustRequestController/AppformController 처리 흐름과 동일.
 * - 보험상태 조회: DB링크(MSP_REQUEST_DTL, MSP_INTM_INSR_MEMBER 등) → insrViewType 결정
 * - 상품 목록: MSP_INTM_INSR_MST@DL_MSP (DB링크)
 * - 신청: X21 직접 (Y24 없음)
 */
public interface SvcChgInsrSvc {

    /**
     * 보험 가입 상태 조회 및 화면 타입 결정.
     * ASIS: CustRequestController.reqInsr() → getInsrInfo() → insrViewType 결정.
     * @param ncn 서비스계약번호
     * @return insrViewType(A/B/C/D/E/F/ING), reqBuyType, insrStatCd 등
     */
    Map<String, Object> getInsrInfo(String ncn);

    /**
     * 단말보험 상품 목록 조회 (DB링크: MSP_INTM_INSR_MST@DL_MSP).
     * ASIS: AppformSvcImpl.selectInsrProdList()
     * @param reqBuyType UU=유심전용, MM=단말
     * @param rprsPrdtId 단말ID (MM일 때 사용)
     */
    Map<String, Object> getInsuranceProducts(String reqBuyType, String rprsPrdtId);

    /**
     * 단말보험 가입 신청 (X21 → MSF_CUST_REQUEST_INSR 저장).
     * ASIS: CustRequestController.custRequestAjax(reqType=IS) → regSvcChg(X21).
     * Y24 사전체크 없음.
     */
    Map<String, Object> applyInsurance(InsrApplyReqDto req);
}
