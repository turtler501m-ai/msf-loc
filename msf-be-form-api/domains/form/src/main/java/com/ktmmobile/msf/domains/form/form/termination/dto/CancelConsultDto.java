package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class CancelConsultDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custReqSeq;          // 해지 상담 일련번호
    private String cstmrName;         // 고객명
    private String contractNum;       // 계약번호
    private String onlineAuthType;    // 본인인증 방법
    private String onlineAuthInfo;    // 본인인증 정보
    private String reqSeq;
    private String resSeq;
    private String cstmrType;
    private String cstmrNativeRrn;
    private String receiveMobileNo;   // 연락받을 번호
    private String cancelMobileNo;    // 해지신청 번호
    private String survey1Cd;         // 설문 1
    private String survey1Text;       // 설문 1 답변
    private String survey2Cd;         // 설문 2
    private String survey2Text;       // 설문 2
    private Integer surveyScore;      // 만족도
    private String surveySuggestion;  // 개선사항
    private String procCd;            // 처리결과
    private String memo;              // 메모
    private String scanId;            // 스캔 아이디
    private String sysRdateDd;        // 등록일
    private String rip;               // 등록아이피
    private String regstId;           // 등록자 ID
    private Date regstDttm;           // 등록일시
    private String rvisnId;           // 수정자 ID
    private Date rvisnDttm;           // 수정일시
}
