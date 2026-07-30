package com.ktmmobile.msf.domains.externalclient.nice.domain.entity;

import lombok.Builder;

import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

/**
 * NICE 계좌인증 결과를 MSF_NICE_LOG에 저장하기 위한 로그 값
 *
 * @param certifyType 접속 구분: P-PC, M-모바일
 * @param authTypeCode 인증수단: A-계좌인증
 * @param ip 요청자 IP
 * @param name 계좌주명
 * @param genderCode 성별코드. 계좌인증에서는 사용하지 않음
 * @param authBirth 생년월일/주민번호/사업자번호/법인번호 등 요청 식별번호
 * @param bankCode NICE 은행코드
 * @param accountNo 계좌번호
 * @param result 인증결과: O-성공, X-실패
 * @param errorCode NICE 결과코드 또는 호출 실패 코드
 * @param referer 호출 화면 referer. 값이 없으면 null 저장
 */
@Builder
public record NiceApiAccountCheckLog(
    String certifyType,
    String authTypeCode,
    String ip,
    String name,
    String genderCode,

    @Encrypted
    String authBirth,
    String bankCode,

    @Encrypted
    String accountNo,
    String result,
    String errorCode,
    String referer
) {
}
