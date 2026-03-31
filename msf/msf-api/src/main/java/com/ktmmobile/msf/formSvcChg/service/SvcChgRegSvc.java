package com.ktmmobile.msf.formSvcChg.service;

import com.ktmmobile.msf.formComm.dto.SvcChgInfoDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCancelReqDto;
import com.ktmmobile.msf.formSvcChg.dto.AdditionCurrentResVO;
import com.ktmmobile.msf.formSvcChg.dto.AdditionRegReqDto;

import java.util.Map;

/**
 * 부가서비스 신청/해지 서비스 인터페이스.
 * ASIS RegSvcService + RegSvcController 로직 통합.
 *
 * -----------------------------------------------------------------------
 * ASIS 참조 소스
 * -----------------------------------------------------------------------
 * Controller : mcp-portal-was
 *   com.ktmmobile.mcp.mypage.controller.RegSvcController
 *   - regServiceView      : /m/mypage/regServiceView.do          (5007-01)
 *   - addSvcListAjax      : /m/mypage/addSvcListAjax.do          (5007-02)
 *   - myAddSvcListAjax    : /m/mypage/myAddSvcListAjax.do        (5007-03)
 *   - getContRateAdditionAjax : /m/mypage/getContRateAdditionAjax.do (5007-04)
 *   - moscRegSvcCanChgAjax: /m/mypage/moscRegSvcCanChgAjax.do   (5007-05 X38)
 *   - addSvcViewPop       : /m/mypage/addSvcViewPop.do           (5007-04/07/08/09)
 *   - regSvcChgAjax       : /m/mypage/regSvcChgAjax.do          (5007-06 Y25)
 */
public interface SvcChgRegSvc {

    /**
     * X97 현재 이용중 부가서비스 조회 (wirelessBlock/infoLimit 체크 포함).
     * ASIS selectAddSvcInfoDto(X20) → selectAddSvcInfoParamDto(X97) 대응.
     * POST /api/v1/addition/current
     */
    AdditionCurrentResVO selectAdditionCurrent(SvcChgInfoDto req);

    /**
     * 5007-01 regServiceView — 부가서비스 신청 화면 초기화.
     * ASIS: RegSvcController.regServiceView — 회원/미성년자 체크 → TOBE 제거 (스마트서식지 비회원).
     * POST /api/v1/addition/reg-service-view
     */
    Map<String, Object> regServiceView(SvcChgInfoDto req);

    /**
     * 5007-03 myAddSvcListAjax — 이용중 부가서비스 목록 조회 (ASIS 함수명 동일).
     * ASIS RegSvcController.myAddSvcListAjax 컨트롤러 로직 + RegSvcServiceImpl.selectmyAddSvcList 완전 구현.
     * X97 → getMspRateMst(canCmnt/onlineCanYn/onlineCanDay) enrichment → PL249Q800 제거 → getMpSocListByDiv.
     * POST /api/v1/addition/my-add-svc-list
     *
     * @return {outList: List&lt;MpSocVO&gt;, contractNum: String}
     */
    Map<String, Object> myAddSvcListAjax(SvcChgInfoDto req);

    /**
     * 5007-02 addSvcListAjax — 가입 가능 부가서비스 카탈로그 조회.
     * X97(이용중 SOC코드) + selectRegService(DB 카탈로그) + useYn 세팅 + listA/listC 분류.
     * POST /api/v1/addition/add-svc-list
     *
     * @return {listC, listA, list, ctn, contractNum}
     */
    Map<String, Object> addSvcListAjax(SvcChgInfoDto req);

    /**
     * 5007-04 getContRateAdditionAjax — 부가서비스 안내 상세 조회.
     * ASIS: selectAddSvcDtl(rateAdsvcCd) → XML 캐시 조회 → TOBE: DB(MSF_RATE_ADSVC_GDNC_*) 조회.
     * req.getSoc() = rateAdsvcCd 로 사용.
     * POST /api/v1/addition/get-cont-rate-addition
     *
     * @return {rateDtl: Map}
     */
    Map<String, Object> getContRateAdditionAjax(AdditionCancelReqDto req);

    /**
     * 5007-05 moscRegSvcCanChgAjax — 부가서비스 해지 (X38).
     * ASIS: moscRegSvcCanChg(searchVO, rateAdsvcCd).
     * POST /api/v1/addition/mosc-reg-svc-can-chg
     *
     * @return {RESULT_CODE: "S"/"E", message, globalNo}
     */
    Map<String, Object> moscRegSvcCanChgAjax(AdditionCancelReqDto req);

    /**
     * 5007-04/07/08/09 addSvcViewPop — 부가서비스 신청 팝업 분기 정보 조회.
     * ASIS: SOC코드별 팝업 타입 분기 (TM차단/번호도용/로밍/포인트할인/일반).
     * req.getSoc() = rateAdsvcCd 로 사용.
     * POST /api/v1/addition/add-svc-view-pop
     *
     * @return {popType, rateAdsvcCd, baseVatAmt, rateAdsvcNm, flag}
     */
    Map<String, Object> addSvcViewPop(AdditionCancelReqDto req);

    /**
     * 5007-06 regSvcChgAjax — 부가서비스 신청 (Y25).
     * ASIS: flag="Y"이면 X38 해지 후 Y25 신청 (로밍 변경).
     * POST /api/v1/addition/reg-svc-chg
     *
     * @return {resultCode: "00"/"E"/"99", message}
     */
    Map<String, Object> regSvcChgAjax(AdditionRegReqDto req);
}
