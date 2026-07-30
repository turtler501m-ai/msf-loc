package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPrxRequestKey implements CommonEnum {
    APP_EVENT_CODE("appEventCd", "업무코드"),
    SERVICE_NAME("serviceName", ""),
    SERVICE_INFO("serviceInfo", ""),
    SERVICE_VO("serviceVo", ""),
    XML("xml", ""),
    RES_NO("resNo", ""),
    ASGN_AGNC_ID("asgnAgncId", "대리점아이디"),
    ONLINE_OFFLINE_DIV_CD("onlineOfflnDivCd", "온라인오프라인구분코드"),
    ORG_ID("orgId", "대리점아이디"),
    RETV_DIV_CD("retvDivCd", "조회 구분 코드"),
    RETV_CD_VAL("retvCdVal", "조회코드값"),
    CPNT_ID("cpntId", "접점아이디"),
    FATH_RGLS_ENV_TEST_YN("fathRglsEnvTestYn", "안면인증상용환경테스트여부"),
    CUST_IDFY_NO_TYPE_CD("custIdfyNoTypeCd", "고객식별번호유형코드"),
    FATH_SBSC_DIV_CD("fathSbscDivCd", "안면인증 가입 구분 코드"),
    FATH_TRANSAC_ID("fathTransacId", "안면인증트랜잭션아이디"),
    SMS_RECV_TEL_NO("smsRcvTelNo", "SMS수신전화번호"),
    PHOTO_ATHN_NCST_YN("photoAthnNcstYn", ""),
    SCAN_TYPE_CD("scanTypeCd", ""),
    CRPR_AGNT_YN("crprAgntYn", ""),
    FATH_BIZR_NO("fathBizrNo", ""),
    FATH_AGNT_CUST_NM("fathAgntCustNm", ""),
    FATH_AGNT_BTHDAY("fathAgntBthday", "")
    ;

    private final String code;
    private final String title;
}
