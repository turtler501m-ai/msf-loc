package com.ktmmobile.msf.domains.externalclient.mspprx.application.dto;

import lombok.Builder;
import lombok.Getter;

/**
 * 서비스 변경 이력 저장 요청
 */
@Getter
@Builder(toBuilder = true)
public class ServiceAlterTraceRequest {

    private final String ncn;              // New 계약번호
    private final String contractNum;      // 계약번호
    private final String globalNo;         // mplatform 연동 globalNo
    private final String subscriberNo;     // 전화번호 CTN
    private final String eventCd;          // 이벤트 코드
    private final String prcsMdlDivCd;     // 처리 모듈 구분
    private final String trtmRsltSbst;     // 호출 내용
    private final String prcsSbst;         // 처리 결과 내용
    private final String rsltCd;           // 처리결과코드
    private final String aSocCode;         // 이전 코드 값
    private final String tSocCode;         // 이후 코드 값

    @Builder.Default
    private final Integer aSocAmt = 0;     // 변경전 요금제 할인 월정액
    @Builder.Default
    private final Integer tSocAmt = 0;     // 변경후 요금제 할인 월정액

    private final String parameter;        // 인자값 설정값
    private final String accessIp;         // 접속 IP
    private final String accessUrlAdr;     // REFERER URL
    private final String userId;           // 아이디
}
