package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;

@Getter
@RequiredArgsConstructor
public enum FaceAuthPrxResponseKey implements CommonEnum {
    OUT_DTO("outDto", "응답내용"),
    TRT_RESULT_CODE("trtResltCd", "처리결과코드"),
    TRT_RESULT_MESSAGE("trtResltSbst", "처리결과내용"),
    STABILIZATION_PERIOD_YN("stbznPerdYn", "안정화기간여부"),
    RESULT_CODE("resltCd", "결과코드"),
    RESULT_MESSAGE("resltSbst", "결과내용"),
    S_RESULT_CODE("rsltCd", "결과코드"),
    RESULT_MSG("rsltMsg", "결과 메시지"),
    TRANSACTION_ID("fathTransacId", "안면인증 트랜잭션 아이디"),
    URL("urlAdr", "URL주소"),
    FATH_DECIDE_CODE("fathDecideCd", "안면인증최종결과코드"),
    FATH_RESULT_CODE("fathResltCd", "안면인증결과코드"),
    FATH_RESULT_MESSAGE("fathResltMsgSbst", "안면인증결과메시지내용"),
    FATH_COMPLETE_DATE("fathCmpltNtfyDt", "안면인증완료통지일시"),
    FATH_IDCARD_TYPE_CODE("fathIdcardTypeCd", "안면인증신분증유형코드"),
    FATH_IDCARD_ISSUE_DATE("fathIdcardIssDate", "안면인증신분증발급일자"),
    FATH_DRIVE_LICENSE_NO("fathDriveLicnsNo", "안면인증운전면허번호"),
    FATH_CUSTOMER_NAME("fathCustNm", "안면인증고객명"),
    FATH_CUSTOMER_IDENDIFY_NO("fathCustIdfyNo", "안면인증고객식별번호"),
    FATH_CUSTOMER_NATION_CODE("fathEngCitizCd", "안면인증영문국적코드"),
    SALE_COMPANY_ID("saleCmpnId", "판매회사아이디"),
    IDCARD_PHOTO_IMAGE("idcardPhotoImg", "신분증 사진"),
    IDCARD_COPIES_IMAGE("idcardCopiesImg", "신분증 사본"),
    MOBILE_IDCARD_QR_IMAGE("mblIdcardQrImg", "모바일 신분증QR"),
    FATH_IDENTITY_CONFIG_WAY_CODE("fathIdntyConfWayCd", "안면인증신원확인수단코드"),
    DISTANCE_RESTRICTION_YN("distRstrtnYn", "거리제한여부"),
    FATH_PROGRESS_STEP_CODE("fathProgrStepCd", "안면인증진행단계코드"),
    FATH_URL_REQUEST_DATE("fathUrlRqtDt", "안면인증URL요청일시"),
    RETV_DIV_CD("retvDivCd", ""),
    OSST_ORD_NO("osstOrdNo", ""),
    FATH_BIRTH_DATE("fathBthday", "안면인증생년월일"),
    SKIP_POSABLE_YN("skipPsblYn", "스킵 가능 여부"),
    PHOTO_ATHN_TXN_SEQ("photoAthnTxnSeq", "사진인증내역일련번호"),
    PHOTO_ATHN_DT("photoAthnDt", "사진인증일시"),
    RECEIVED_SMS_TEL_NO("smsRcvTelNo", "단문메시지수신전화번호");

    private final String code;
    private final String title;
}
