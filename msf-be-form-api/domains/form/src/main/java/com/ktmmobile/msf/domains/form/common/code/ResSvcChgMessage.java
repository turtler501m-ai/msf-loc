package com.ktmmobile.msf.domains.form.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResSvcChgMessage {
    SUCCESS("0000", null),
    RSLT_SUCCESS("S", "prx 연동 결과 성공"),

    CHANGE_REQUEST_INVALID("2101", "서비스변경 조회 요청 정보가 없습니다."),
    CHANGE_CONTRACT_NOT_FOUND("2102", "계약 정보를 찾을 수 없습니다."),
    CHANGE_PLAN_NOT_FOUND("2102", "요금제를 찾을 수 없습니다."),
    CHANGE_INFO_ERROR("2103", "서비스변경 조회 중 오류가 발생했습니다."),
    CHANGE_PROCESS_ERROR("2104", "서비스변경 처리 중 오류가 발생했습니다."),
    CHANGE_NUMBER_CHANGE_TIME_ERROR("2105", "번호변경 가능한 시간은 평일 오전10시~오후8시까지 가능합니다.(주말 공휴일은 변경불가)"),
    CHANGE_NUMBER_CHANGE_DAY_ERROR("2106", "번호변경은 개통 후 일정 기간이 지난 이후에 가능합니다."),

    ADDITION_RATE_NOT_FOUND("6101", "요금제 정보가 존재하지 않습니다."),
    ADDITION_ONLINE_CANCEL_UNAVAILABLE("6102", "해지할 수 없는 부가서비스는 고객센터를 통해 해지 가능합니다."),
    ADDITION_SELF_SERVICE_ERROR("6103", "부가서비스 처리 중 오류가 발생했습니다."),
    ADDITION_SELF_CARE_CANCEL_UNAVAILABLE("6104", "판매점 해지가 불가한 상품입니다."),
    ADDITION_STORE_ONLINE_CANCEL_UNAVAILABLE("6105", "판매점 온라인해지가 불가한 부가서비스가 있습니다."),

    APPLY_MCP_CUSTOMER_SAVE_FAILED("6201", "M포탈 고객정보 저장에 실패했습니다."),
    APPLY_MCP_SAVE_FAILED("6202", "M포탈 데이터 저장에 실패했습니다."),
    APPLY_MCP_TRANSFER_ERROR("6203", "M포탈 이관 중 오류가 발생했습니다."),

    PRICE_CHANGE_REQUEST_INVALID("6301", "요금제변경 조회 요청 정보가 없습니다."),
    PRICE_SELF_SERVICE_ERROR("6302", "요금제 처리 중 오류가 발생했습니다."),
    PRICE_CHANGE_OUTSIDEOFPROCESSHOUR_INVALID("6303", "해당 시간은 상품변경이 불가 합니다. (23:30분 ~ 익일 00:30분, 1시간)"),
    PRICE_CHANGE_NOADULT_INVALID("6304", "만 19세이상 성인고객만 가능합니다."),
    PRICE_CHANGE_CONTRACT_EGG_INVALID("6305", "단말/유심 약정고객 요금제 변경이 불가능 합니다."),

    COMBINE_ING("8001", "이미 신청 이력이 존재합니다."),
    COMBINE_INVALID_STATUS("8002", "현재 회선을 사용 중인 고객만 결합이 가능합니다."),
    COMBINE_UNABLE_SOC("8003", "해당 상품은 결합이 불가합니다."),
    COMBINE_UNABLE_SOC_EMPTY("8004", "해당 상품은 결합이 불가합니다.(EMPTY)"),

    ALREADY_REGISTERED("9001", "이미 가입되어 있습니다."),
    INSUR_ING("9002", "보험가입이 진행중입니다."),
    NEED_NOTICE("9003", "미성년자, 법인인 경우 고객센터 안내"),
    EMPTY("9009", "고객 정보와 휴대폰번호가 일치하지 않습니다.\n 휴대폰번호를 다시 확인해 주세요."),
    CHANGE_USIM_ERROR("9010", "유심 변경 중 오류가 발생했습니다."),
    USIM_STATUS_STOP("9011", "정지 회선은 유심변경이 불가합니다."),
    USIM_STATUS_NON_PAY("9012", "미납 회선은 유심변경이 불가합니다."),
    MINOR_UNCHANGE_USIM("9013", "미성년자는 USIM 변경이 불가능합니다."),
    JOIN_FAIL("9101", "안심보험 부가서비스 가입 처리 실패"),
    ACTIVATION_PERIOD_EXPIRED("9201", "개통 일자가 45일 이내만 가능합니다."),

    ERROR("9999", "에러");

    private final String code;
    private final String message;

    public static ResSvcChgMessage fromCode(String code) {
        for (ResSvcChgMessage r: values()) {
            if (r.code.equals(code)) {
                return r;
            }
        }
        return ERROR;
    }
}
