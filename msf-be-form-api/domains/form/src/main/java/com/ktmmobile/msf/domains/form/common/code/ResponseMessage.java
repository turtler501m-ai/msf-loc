package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage implements CommonEnum {
    SUCCESS("0000", "성공"),
    COMMON_EXCEPTION("1111", "서비스 처리중 오류가 발생 하였습니다."),
    F_BIND_EXCEPTION("2222", "비정상적인 접근입니다."),
    DB_EXCEPTION("3333", "DB 처리중 오류가 발생 하였습니다."),
    NO_DATA("4444", "요청하신 정보가 존재하지 않습니다."),

    /* 계좌번호 인증 */
    VALID_ACCOUNT_SUCCESS("0000", "계좌번호 인증이 완료되었습니다."),
    VALID_ACCOUNT_FAIL("1000", "유효하지 않은 계좌번호 입니다.\n다시 확인 후 입력 해주세요."),

    /* 신용카드번호 인증 */
    VALID_CREDIT_SUCCESS("0000", "신용카드 인증이 완료되었습니다."),
    VALID_CREDIT_FAIL("1000", "유효하지 않은 신용카드 입니다.\n다시 확인 후 입력 해주세요."),

    /* 청구계정아이디 인증 */
    VALID_BAN_SUCCESS("0000", "청구계정ID 인증이 완료되었습니다.\n"),
    VALID_BAN_FAIL("1000", "유효하지 않은 청구계정ID 입니다. \n다시 확인 후 입력 해주세요."),

    /* 휴대폰 일련번호 인증 */
    VALID_PHONE_SERIAL_SUCCESS("0000", "사용 가능한 휴대폰 일련번호 입니다."),
    VALID_PHONE_SERIAL_FAIL("1000", "유효하지 않은 휴대폰 일련번호 입니다."),
    VALID_PHONE_SERIAL_MISS("2000", "휴대폰 일련번호를 입력해 주세요."),
    VALID_PHONE_SERIAL_ABUSE("3000", "유효하지 않은 휴대폰 정보 입니다."),

    /* USIM 유효성 체크 */
    VALID_USIM_SUCCESS("0000", "사용 가능한 USIM 번호 입니다."),
    VALID_USIM_FAIL("1000", "유효하지 않은 USIM 번호 입니다.\n사용 불가한 USIM 입니다.\n새 USIM을 구매하여 재 시도 바랍니다."),

    /* ESIM 유효성 체크 */
    VALID_ESIM_SUCCESS("0000", "휴대폰 정보 인증이 완료되었습니다."),
    VALID_ESIM_FAIL("1000", "휴대폰 정보 인증에 실패하였습니다.\n휴대폰 정보를 다시 확인해 주세요."),
    VALID_ESIM_ABUSE("3000", "유효하지 않은 휴대폰 정보 입니다."),
    VALID_ESIM_OUTOFSTOCK("4000", "재고가 없습니다."),
    VALID_ESIM_UPLOAD_FAIL("5000", "단말정보 저장이 정상적이지 않습니다. \n재시도 부탁드립니다."),

    /* 신규가입 희망번호 조회 */
    VALID_SEARCH_NUMBER_SUCCESS("0000", "희망번호 조회 성공"),
    VALID_SEARCH_NUMBER_FAIL("1000", "희망번호 조회 실패"),
    VALID_SEARCH_NUMBER_OVER_LIMIT("1001", "개통희망번호 조회 20회 초과하셨습니다.\n신청서를 처음부터 다시 작성해 주십시요."),

    /* 신규가입 희망번호 예약 */
    VALID_RESERVE_NUMBER_SUCCESS("0000", "희망번호 예약 성공"),
    VALID_RESERVE_NUMBER_FAIL("1000", "희망번호 예약 실패"),

    /* 신규가입 희망번호 취소 */
    VALID_CANCEL_NUMBER_SUCCESS("0000", "희망번호 취소 성공"),
    VALID_CANCEL_NUMBER_FAIL("1000", "희망번호 취소 실패"),

    /* 번호이동 사전동의 */
    VALID_REQ_NP_PRECHECK_SUCCESS("0000", "번호이동 사전동의 성공"),
    VALID_REQ_NP_PRECHECK_FAIL("1000", "번호이동 사전동의 실패"),

    /* 번호이동 납부주장 */
    VALID_REQ_NP_PAY_OPEN_SUCCESS("0000", "번호이동 사전동의 결과조회 성공"),
    VALID_REQ_NP_PAY_OPEN_FAIL("1000", "번호이동 사전동의 결과조회 실패"),

    /* 번호이동 사전동의 결과조회 */
    VALID_REQ_NP_AGREE_SUCCESS("0000", "번호이동 사전동의 결과조회 성공"),
    VALID_REQ_NP_AGREE_FAIL("1000", "번호이동 사전동의 결과조회 실패"),

    /* 안심보험 목록 조회 */
    VALID_SELECT_INSR_SUCCESS("0000", "안심보험 조회 성공"),
    VALID_SELECT_INSR_FAIL("1000", "안심보험 조회 실패"),

    @Deprecated
    UNDEFINED(CommonEnumConstant.UNDEFINED_CODE, "Invalid ResponseMessage");

    private final String code;
    private final String title;

    @Override
    public boolean isValid() {
        return this != getInvalidValue();
    }

    public String getMessage() {
        return this.title;
    }

    public static ResponseMessage getInvalidValue() {
        return UNDEFINED;
    }

    @JsonCreator
    public static ResponseMessage valueOfCode(String code) {
        return CommonEnum.valueOfCode(ResponseMessage.class, code, getInvalidValue());
    }
}
