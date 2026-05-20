package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpServiceAlterTraceDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String ncn;             // New 계약번호
    private String contractNum;     // 계약번호
    private long seq;               // 일련번호
    private String subscriberNo;    // 전화번호 CTN
    private String eventCode;       // 이벤트 코드
    private String prcsMdlInd;      // 처리 모듈 구분
    private String trtmRsltSmst;    // 호출 내용
    private String prcsSbst;        // 처리 결과 내용
    private String rsltCd;          // 처리결과코드
    private String aSocCode;        // 이전 코드 값
    private String tSocCode;        // 이후 코드 값
    private String parameter;       // 인자값 설정값
    private String accessIp;        // 접속 IP
    private String accessUrl;       // REFERER URL
    private String userId;          // 아이디
    private String globalNo;        // mplatform 연동 globalNo
    private int aSocAmnt = 0;       // 변경전 요금제 할인 월정액
    private int tSocAmnt = 0;       // 변경후 요금제 할인 월정액
    private String chgType;         // CHG_TYPE (즉시:I, 예약:R)
    private String succYn;          // SUCC_YN (성공:Y, 실패:N)
    private String procMemo;        // 처리메모
    private String procYn;          // 처리여부
    private String procId;          // 처리자 ID
    private Timestamp procDate;     // 처리자 일시

    // 필드명 소문자 시작 약어 — Lombok은 getASocCode() 생성, 기존 호출부 호환을 위해 유지
    public String getaSocCode() { return aSocCode; }
    public void setaSocCode(String aSocCode) { this.aSocCode = aSocCode; }
    public String gettSocCode() { return tSocCode; }
    public void settSocCode(String tSocCode) { this.tSocCode = tSocCode; }
    public int getaSocAmnt() { return aSocAmnt; }
    public void setaSocAmnt(int aSocAmnt) { this.aSocAmnt = aSocAmnt; }
    public int gettSocAmnt() { return tSocAmnt; }
    public void settSocAmnt(int tSocAmnt) { this.tSocAmnt = tSocAmnt; }

}
