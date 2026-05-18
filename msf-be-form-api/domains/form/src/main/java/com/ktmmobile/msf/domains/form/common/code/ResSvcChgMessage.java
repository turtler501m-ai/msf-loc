package com.ktmmobile.msf.domains.form.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResSvcChgMessage {
    SUCCESS("0000", null),

    CHANGE_REQUEST_INVALID("2101", "서비스변경 조회 요청 정보가 없습니다."),
    CHANGE_CONTRACT_NOT_FOUND("2102", "계약 정보를 찾을 수 없습니다."),
    CHANGE_INFO_ERROR("2103", "서비스변경 조회 중 오류가 발생했습니다."),

    ADDITION_RATE_NOT_FOUND("6101", "요금제 정보가 존재하지 않습니다."),
    ADDITION_ONLINE_CANCEL_UNAVAILABLE("6102", "해지할 수 없는 부가서비스는 고객센터를 통해 해지 가능합니다."),
    ADDITION_SELF_SERVICE_ERROR("6103", "부가서비스 처리 중 오류가 발생했습니다."),

    APPLY_MCP_CUSTOMER_SAVE_FAILED("6201", "M포탈 고객정보 저장에 실패했습니다."),
    APPLY_MCP_SAVE_FAILED("6202", "M포탈 데이터 저장에 실패했습니다."),
    APPLY_MCP_TRANSFER_ERROR("6203", "M포탈 이관 중 오류가 발생했습니다.");

    private final String code;
    private final String message;
}
