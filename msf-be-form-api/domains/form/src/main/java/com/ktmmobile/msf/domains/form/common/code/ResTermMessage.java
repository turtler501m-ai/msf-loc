package com.ktmmobile.msf.domains.form.common.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ResTermMessage {
    REMAIN_CONTRACT_NOT_FOUND("3101", "계약 정보를 찾을 수 없습니다."),
    REMAIN_API_EMPTY("3102", "잔여요금 조회 응답이 없습니다."),
    REMAIN_SELF_SERVICE_ERROR("3103", "잔여요금 조회 중 오류가 발생했습니다."),
    REMAIN_TIMEOUT("3104", "잔여요금 조회 시간이 초과되었습니다."),
    REMAIN_ERROR("3105", "잔여요금 조회 중 오류가 발생했습니다."),

    APPLY_REQUEST_INVALID("4101", "요청 정보가 없습니다."),
    APPLY_CUSTOMER_INVALID("4102", ""),
    APPLY_PRODUCT_INVALID("4103", ""),
    APPLY_AGREEMENT_INVALID("4104", ""),
    APPLY_AGENT_REQUIRED("4201", "agentCd is required"),
    APPLY_MANAGER_REQUIRED("4202", "managerCd is required"),
    APPLY_CUSTOMER_TYPE_REQUIRED("4203", "customerType is required"),
    APPLY_POST_METHOD_REQUIRED("4204", "postMethod is required"),
    APPLY_CANCEL_PHONE_REQUIRED("4205", "해지 대상 전화번호를 입력해 주세요."),
    APPLY_RECEIVE_PHONE_REQUIRED("4206", "해지 후 연락처를 입력해 주세요."),
    APPLY_REQUEST_KEY_FAILED("4301", "요청 번호 발급에 실패했습니다."),
    APPLY_MSF_SAVE_FAILED("4302", "서비스해지 요청 저장에 실패했습니다."),
    APPLY_MCP_CUSTOMER_SAVE_FAILED("4303", "M포탈 고객정보 저장에 실패했습니다."),
    APPLY_MCP_SAVE_FAILED("4304", "M포탈 데이터 저장에 실패했습니다."),
    APPLY_ERROR("4900", "서비스해지 요청 처리 중 오류가 발생했습니다."),

    ADMIN_REQUEST_KEY_REQUIRED("5101", "신청번호(requestKey)가 없습니다."),
    ADMIN_REQUEST_NOT_FOUND("5102", "해지신청 건을 찾을 수 없습니다."),
    ADMIN_ALREADY_COMPLETED("5103", "이미 처리완료된 건입니다."),
    ADMIN_CANCEL_REASON_REQUIRED("5201", "해지사유코드(itgOderWhyCd)가 없습니다."),
    ADMIN_AFTER_INCLINATION_REQUIRED("5202", "해지후성향코드(aftmnIncInCd)가 없습니다."),
    ADMIN_REL_TYPE_REQUIRED("5203", "고객접촉매체코드(apyRelTypeCd)가 없습니다."),
    ADMIN_TOUCH_MEDIA_REQUIRED("5204", "신청관계유형코드(custTchMediCd)가 없습니다."),
    ADMIN_DETAIL_NOT_FOUND("5301", "요청 상세 정보를 찾을 수 없습니다."),
    ADMIN_EP0_ERROR("5401", "EP0 처리 중 오류가 발생했습니다."),
    ADMIN_EP0_EMPTY("5402", "EP0 응답이 없습니다."),
    ADMIN_EP0_FAILED("5403", "EP0 처리 실패"),
    ADMIN_COMPLETE_SAVE_FAILED("5501", "처리완료 저장에 실패했습니다."),
    ADMIN_COMPLETE_ONLY_REVERT("5601", "처리완료 상태인 건만 완료취소할 수 있습니다."),
    ADMIN_REVERT_SAVE_FAILED("5602", "완료취소 저장에 실패했습니다.");

    private final String code;
    private final String message;
}
