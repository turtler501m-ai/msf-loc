package com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId;

public record MobileIdVerificationSaveRequest(
    boolean result,        // 결과
    String trxcode,        // 트랜잭션 코드
    String idType,         // 신분증 유형
    String customerNm,     // 고객명
    String customerBirth,  // 고객 생년월일
    String customerRrn,    // 고객 주민번호
    String imageBase64,    // 가상 신분증 이미지
    String errcode,        // 에러 코드
    String errmsg          // 에러 메시지
) {
}
