package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestAgentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;                // 가입신청_키
    private String minorAgentName;          // 미성년자_법정대리인_성명
    private String minorAgentRrn;           // 미성년자_법정대리인_주민등록번호
    private String minorAgentTelFn;         // 미성년자_법정대리인_연락처_앞자리
    private String minorAgentTelMn;         // 미성년자_법정대리인_연락처_중간자리
    private String minorAgentTelRn;         // 미성년자_법정대리인_끝자리
    private String minorAgentRelation;      // 미성년자_법정대리인_관계
    private String jrdclAgentName;          // 법인_대리인_성명
    private String jrdclAgentRrn;           // 법인_대리인_주민등록번호
    private String jrdclAgentTelFn;         // 법인_대리인_연락처_앞자리
    private String jrdclAgentTelMn;         // 법인_대리인_연락처_중간자리
    private String jrdclAgentTelRn;         // 법인_대리인_연락처_끝자리
    private String entrustReqNm;            // 법정대리인_위임하는분
    private String entrustResNm;            // 법정대리인_위임받는분
    private String entrustReqRelation;      // 법정대리인_위임관계
    private String entrustResRrn;           // 법정대리인_위임_주민등록번호
    private String entrustResTelFn;         // 법정대리인_위임_전화번호_앞자리
    private String entrustResTelMn;         // 법정대리인_위임_전화번호_중간자리
    private String entrustResTelRn;         // 법정대리인_위임_전화번호_뒷자리
    private Date sysRdate;                  // 등록일시
    private String minorSelfInqryAgrmYn;    // 본인인증조회동의
    private String minorSelfCertType;       // 본인인증유형
    private String minorSelfIssuExprDt;     // 발급/만료일자
    private String minorSelfIssuNum;        // 발급번호

}
