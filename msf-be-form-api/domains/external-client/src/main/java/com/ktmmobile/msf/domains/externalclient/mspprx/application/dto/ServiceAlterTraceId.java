package com.ktmmobile.msf.domains.externalclient.mspprx.application.dto;

/**
 * 서비스 변경 이력 식별자
 *
 * @param ncn 계약 번호
 * @param regDate 등록 일자 (yyyyMMdd)
 * @param traceSeq 일자별 이력 순번
 */
public record ServiceAlterTraceId(
    String ncn,
    String regDate,
    Integer traceSeq
) {
}
