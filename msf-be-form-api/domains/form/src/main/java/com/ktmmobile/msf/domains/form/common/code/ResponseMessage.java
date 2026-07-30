package com.ktmmobile.msf.domains.form.common.code;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnum;
import com.ktmmobile.msf.commons.common.commonenum.core.CommonEnumConstant;

@Getter
@RequiredArgsConstructor
public enum ResponseMessage implements CommonEnum {
    SUCCESS("0000", "성공"),
    COMMON_EXCEPTION("1111", "서비스 처리중 오류가 발생 하였습니다."),
    F_BIND_EXCEPTION("2222", "비정상적인 접근입니다."),
    DB_EXCEPTION("3333", "DB 처리중 오류가 발생 하였습니다."),
    NO_DATA("4444", "요청하신 정보가 존재하지 않습니다."),

    /* 신청서 저장 요청 데이타 유효성체크 */
    VALID_INPUT_INVALID("1111", "유효성체크 실패"),
    VALID_INPUT_EMPTY("2222", "필수 입력값이 누락되었습니다."),
    VALID_INPUT_NOT_CORRECT("3000", "입력 정보 자릿수를 확인해 주세요."),
    VALID_SAVE_FAIL_PRE_CHECK("3001", "개통전 사전체크에 실패하셨습니다."),

    /** 신분증 목록 조회 **/
    VALID_KNOTE_SCAN_LIST_SUCCESS("0000", "신분증 목록 조회가 성공하였습니다."),
    VALID_KNOTE_SCAN_LIST_FAIL("1000", "신분증 목록 조회에 실패하였습니다."),

    /** 신분증 상태 조회 **/
    VALID_KNOTE_SCAN_STATUS_SUCCESS("0000", "신분증 상태 조회에 성공하였습니다."),
    VALID_KNOTE_SCAN_STATUS_FAIL("1000", "신분증 상태 조회에 실패하였습니다."),
    VALID_KNOTE_SCAN_STATUS_NO_DATE("1001", "서식지 정보가 존재하지 않습니다."),

    /** KT M모바일 고객인증 **/
    VALID_KTM_MOBILE_MEMBER_SUCCESS("0000", "휴대폰번호 인증이 완료되었습니다."),
    VALID_KTM_MOBILE_MEMBER_NECESSARY("1200", "입력값이 부족합니다."),
    VALID_KTM_MOBILE_MEMBER_FAIL("1000", "고객정보와 휴대폰번호가 일치하지 않습니다.\n휴대폰번호를 다시 확인해 주세요."),
    VALID_KTM_MOBILE_MEMBER_CANNOT_APPLY("1100", "현재 진행중인 신청서가 있어 신청할 수 없습니다."),
    VALID_KTM_MOBILE_MEMBER_NOT_FOUND("1001", "고객정보가 존재하지 않습니다."),
    VALID_KTM_MOBILE_MEMBER_NOT_FOUND_CONTRACT("1003", "계약정보가 존재하지 않습니다."),
    VALID_KTM_MOBILE_MEMBER_TERMINATED("1002", "해지 고객입니다."),

    /** 대량 법인 개통 가입조건조회 **/
    VALID_BULK_CORPORATE_SUCCESS("0000", "가입조건조회에 성공하였습니다."),
    VALID_BULK_CORPORATE_FAIL("1000", "가입조건조회에 실패하였습니다."),

    /* 계좌번호 인증 */
    VALID_ACCOUNT_SUCCESS("0000", "계좌번호 인증이 완료되었습니다."),
    VALID_ACCOUNT_FAIL("1000", "유효하지 않은 계좌번호 입니다.\n다시 확인 후 입력 해주세요."),
    VALID_ACCOUNT_FAIL_OTHERS("1001", "개통이력이 존재하지 않아 타인납부가 불가합니다."),
    VALID_ACCOUNT_NEED_MORE_INPUT("10002", "요청 데이타가 불충분합니다."),

    /* 신용카드번호 인증 */
    VALID_CREDIT_SUCCESS("0000", "신용카드 인증이 완료되었습니다."),
    VALID_CREDIT_FAIL("1000", "유효하지 않은 신용카드 입니다.\n다시 확인 후 입력 해주세요."),
    VALID_CREDIT_FAIL_OTHERS("1001", "개통이력이 존재하지 않아 타인납부가 불가합니다."),
    VALID_CREDIT_NEED_MORE_INPUT("10002", "요청 데이타가 불충분합니다."),
    VALID_CREDIT_CANNOT_CHECK("10003", "미성년자는 본인납부 신용카드인증이 불가합니다."),

    /* 청구계정아이디 인증 */
    VALID_BAN_SUCCESS("0000", "청구계정ID 인증이 완료되었습니다.\n"),
    VALID_BAN_FAIL("1000", "유효하지 않은 청구계정ID 입니다. \n다시 확인 후 입력 해주세요."),
    VALID_BAN_NEED_MORE_INPUT("10002", "요청 데이타가 불충분합니다."),

    /* 휴대폰 일련번호 인증 */
    VALID_PHONE_SERIAL_SUCCESS("0000", "사용 가능한 휴대폰 일련번호 입니다."),
    VALID_PHONE_SERIAL_NODATA("1000", "휴대폰 재고를 확인해 주시기 바랍니다."),
    VALID_PHONE_SERIAL_FAIL("1000", "유효하지 않은 휴대폰 일련번호 입니다."),
    VALID_PHONE_SERIAL_MISS("2000", "휴대폰 일련번호를 입력해 주세요."),
    VALID_PHONE_SERIAL_ABUSE("3000", "유효하지 않은 휴대폰 정보 입니다."),

    /* USIM 유효성 체크 */
    VALID_USIM_SUCCESS("0000", "사용 가능한 USIM 번호 입니다."),
    VALID_USIM_NO_DATA("0001", "재고가 없습니다."),
    VALID_USIM_FAIL("1000", "유효하지 않은 USIM 번호 입니다.\n사용 불가한 USIM 입니다.\n새 USIM을 구매하여 재 시도 바랍니다."),

    /* ESIM 유효성 체크 */
    VALID_ESIM_SUCCESS("0000", "휴대폰 정보 유효성 체크가 완료되었습니다."),
    VALID_ESIM_FAIL("1000", "휴대폰 정보 유효성 체크가 실패하였습니다. \n휴대폰 정보를 다시 확인해 주세요."),
    VALID_ESIM_NEED_INPUT("2000", "입력값이 누락되었습니다."),
    VALID_ESIM_ABUSE("3000", "유효하지 않은 휴대폰 정보 입니다."),
    VALID_ESIM_OUTOFSTOCK("4000", "재고가 없습니다."),
    VALID_ESIM_UPLOAD_FAIL("5000", "단말정보 저장이 정상적이지 않습니다. \n재시도 부탁드립니다."),

    VALID_ESIM_Y12_SUCCESS("0000", "단말기 스펙정보 조회에 성공하였습니다."),
    VALID_ESIM_Y12_FAIL("5000", "단말기 스펙정보 조회에 실패하였습니다."),

    VALID_ESIM_Y13_SUCCESS("0000", "기기원부조회에 성공하였습니다."),
    VALID_ESIM_Y13_FAIL("5000", "기기원부조회에 실패하였습니다."),

    VALID_ESIM_Y14_SUCCESS("0000", "OMD 단말 처리 사전체크에 성공하였습니다."),
    VALID_ESIM_Y14_FAIL("5000", "OMD 단말 처리 사전체크에 실패하였습니다."),

    VALID_ESIM_Y15_SUCCESS("0000", "OMD 단말 처리에 성공하였습니다."),
    VALID_ESIM_Y15_FAIL("5000", "OMD 단말 처리에 실패하였습니다."),

    VALID_ESIM_SRL_NO_FAIL("1000", "휴대폰 이미지 일련번호가 존재하지 않습니다."),
    VALID_ESIM_EID_FAIL("2000", "휴대폰 EID 정보가 없습니다."),
    VALID_ESIM_EQ_EID_FAIL("3000", "휴대폰 EID 정보가 일치하지 않습니다."),
    VALID_ESIM_EQ_IMEI1_FAIL("4000", "휴대폰 IMEI1 정보가 일치하지 않습니다."),
    VALID_ESIM_EQ_IMEI2_FAIL("5000", "휴대폰 IMEI2 정보가 일치하지 않습니다."),
    VALID_ESIM_EQ_REQPHONESN_FAIL("6000", "휴대폰 일련번호가 일치하지 않습니다."),
    VALID_ESIM_EQ_ESIMPHONEID_FAIL("7000", "기기 모델아이디가 일치하지 않습니다."),


    /* 신규가입 희망번호 조회 */
    VALID_SEARCH_NUMBER_SUCCESS("0000", "희망번호 조회 성공"),
    VALID_SEARCH_NUMBER_NEED_PRECHECK("1111", "개통전 사전체크를 진행해주세요."),
    VALID_SEARCH_NUMBER_NOT_CORRECT("2222", "희망번호 입력 값 4자리를 입력해 주세요."),
    VALID_SEARCH_NUMBER_EXCEPTION("2200", "희망번호 조회 시 오류가 발생하였습니다. \n재시도해주시기 바랍니다."),
    VALID_SEARCH_NUMBER_FAIL("1000", "희망번호에 해당하는 신규 번호가 없습니다.\n다른 번호로 다시 조회해 주세요."),
    VALID_SEARCH_NUMBER_OVER_LIMIT("1001", "개통희망번호 조회 20회 초과하셨습니다.\n신청서를 처음부터 다시 작성해 주십시요."),

    /* 신규가입 희망번호 예약 */
    VALID_RESERVE_NUMBER_SUCCESS("0000", "희망번호 예약 성공"),
    VALID_RESERVE_NUMBER_FAIL("1000", "희망번호 예약 실패"),

    /* 신규가입 희망번호 취소 */
    VALID_CANCEL_NUMBER_SUCCESS("0000", "희망번호 취소 성공"),
    VALID_CANCEL_NUMBER_FAIL("1000", "희망번호 취소 실패"),

    /* 번호이동 사전동의 */
    VALID_REQ_NP_PRECHECK_SUCCESS("0000", "번호이동 사전동의 요청이 완료되었습니다. 문자 수신 후 동의를 완료해 주세요."),
    VALID_REQ_NP_PRECHECK_FAIL("1000", "번호이동 사전동의 요청이 실패하였습니다."),

    /* 번호이동 납부주장 */
    VALID_REQ_NP_PAY_OPEN_SUCCESS("0000", "번호이동 사전동의 납부주장 성공"),
    VALID_REQ_NP_PAY_OPEN_FAIL("1000", "번호이동 사전동의 납부주장이 실패하였습니다."),

    /* 번호이동 사전동의 결과조회 */
    VALID_REQ_NP_AGREE_SUCCESS("0000", "번호이동 사전 동의가 완료되었습니다."),
    VALID_REQ_NP_AGREE_IN_PROGRESS("0001", "번호이동 사전 동의 요청이 진행중입니다."),
    VALID_REQ_NP_AGREE_FAIL("1000", "번호이동 사전동의 결과조회가 실패하였습니다."),

    /* 안심보험 목록 조회 */
    VALID_SELECT_INSR_SUCCESS("0000", "안심보험 조회 성공"),
    VALID_SELECT_INSR_FAIL("1000", "안심보험 조회 실패"),

    /* 고객유형으로 나이 체크 (미성년자와 성인) */
    REGNO_TEEN_FAIL("1000", "청소년 주민등록 번호가 아닙니다."),
    REGNO_ADULT_FAIL("1000", "성인 주민등록 번호가 아닙니다."),

    /* 신규개통 이력 체크 */
    VALID_SELF_LIMIT_FAIL("1000", "신규가입은 명의당 30일이내 1회선만 가입 가능합니다."),

    /* 청구서 발송 유형 체신규개통 이력 체크 */
    VALID_BILL_CONTRACT_CHECK_FAIL("1000", "계약번호 정보를 확인 할 수 없습니다."),
    VALID_BILL_CHECK_FAIL("1000", "청구서 정보를 확인할 수 없습니다."),

    /* 골드번호 체크 */
    VALID_CONTAINS_GOLD_NUMBER_FAIL("1000", "입력하신 가입희망번호 중 골드번호가 포함되어 있습니다. 희망번호 수정 후 다시 시도 부탁드립니다."),

    /* 고객인증 체크 */
    VALID_BINDING_CHANGE_AUT_FAIL("1000", "기기변경 휴대폰 번호가 인증한 정보와 일치하지 않습니다."),

    /* 바인딩 체크 */
    VALID_BINDING_REQBUYTYPE_FAIL("1000", "바인딩 처리중 오류가 발생하였습니다.(구매타입)"),
    VALID_BINDING_PRICE_FAIL("1000", "바인딩 처리중 오류가 발생하였습니다.(금액 정보 설정)"),
    VALID_BINDING_REQ_BUY_TYPE_PHONE_FAIL("1000", "바인딩 처리중 오류가 발생하였습니다.(단말구매)"),
    VALID_BINDING_REQ_BUY_TYPE_USIM_FAIL("1000", "바인딩 처리중 오류가 발생하였습니다.(USIM(유심)단독 구매)"),
    VALID_BINDING_USIM_INFO_FAIL("1000", "바인딩 처리중 오류가 발생하였습니다.(USIM정보)"),


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
