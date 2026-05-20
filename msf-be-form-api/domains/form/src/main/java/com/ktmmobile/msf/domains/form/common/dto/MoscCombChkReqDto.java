package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * MVNO 결합 사전 체크 (x78)
 */
@Getter
@Setter
@NoArgsConstructor
public class MoscCombChkReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custId;
    private String ncn;
    private String ctn;
    private String jobGubun;       // 작업구분코드 N:신규결합신청, A:회선추가, D:회선삭제
    private String svcNoCd;        // 회선사업자코드 M:MVNO회선, K:KT회선
    private String svcNoTypeCd;    // 회선구분코드 인터넷:IT 모바일:MB (MVNO회선일 경우 MB만 가능)
    private String cmbStndSvcNo;   // 결합서비스번호 모바일:전화번호, 인터넷:ID
    private String custNm;         // 고객이름 (KT회선일경우 필수)
    private String svcIdfyTypeCd;  // 서비스확인번호 타입 생년월일:1, 법인번호:3, 사업자번호:7, 여권번호:2, 기타:99
    private String svcIdfyNo;      // 서비스확인번호 (KT회선일경우 필수)
    private String sexCd;          // 성별코드 (KT회선일경우 필수) 1:남성, 2:여성
    private String aplyMethCd;     // 신청방법 전화:01, FAX:02, 우편:03, 창구방문:04, iPad접수:20, 스마트서식지:21
    private String aplyRelatnCd;   // 신청관계 본인:01, 대리인:11, 대표자:12, KT직원:05
    private String aplyNm;         // 신청자이름
    private String homeCombTerm;   // 홈결합 할인 기간 무약정:N, 1년:1, 2년:2, 3년:3

}
