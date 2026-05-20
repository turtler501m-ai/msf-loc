package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestCstmrDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;                // 가입신청_키
    private String cstmrForeignerNation;    // 고객정보_외국인_국적
    private String cstmrForeignerPn;        // 고객정보_외국인_여권번호
    private String cstmrForeignerRrn;       // 고객정보_외국인_외국인등록번호
    private String cstmrForeignerSdate;     // 고객정보_외국인_체류_시작날자
    private String cstmrForeignerEdate;     // 고객정보_외국인_체류_종료날자
    private String cstmrPrivateCname;       // 고객정보_개인사업자_상호명
    private String cstmrPrivateNumber;      // 고객정보_개인사업자_사업자등록번호
    private String cstmrJuridicalCname;     // 고객정보_법인사업자_법인명
    private String cstmrJuridicalRrn;       // 고객정보_법인사업자_법인번호
    private String cstmrJuridicalNumber;    // 고객정보_법인사업자_사업자등록번호
    private String cstmrTelFn;              // 고객정보_전화번호_앞자리
    private String cstmrTelMn;              // 고객정보_전화번호_가운데자리
    private String cstmrTelRn;              // 고객정보_전화번호_끝자리
    private String cstmrMobileFn;           // 고객정보_휴대폰번호_앞자리
    private String cstmrMobileMn;           // 고객정보_휴대폰번호_중간자리
    private String cstmrMobileRn;           // 고객정보_휴대폰번호_끝자리
    private String cstmrPost;               // 고객정보_우편번호
    private String cstmrAddr;               // 고객정보_주소
    private String cstmrAddrDtl;            // 고객정보_상세주소
    private String cstmrAddrBjd;            // 고객정보_법정동주소
    private String cstmrBillSendCode;       // 고객정보_명세서종류
    private String cstmrMail;               // 고객정보_메일
    private String cstmrMailReceiveFlag;    // 고객정보_메일수신_여부
    private String cstmrVisitType;          // 고객정보_방문고객정보
    private String cstmrReceiveTelFn;       // 고객정보_연락받을번호_앞자
    private String cstmrReceiveTelNm;       // 고객정보_연락받을번호_중간자리
    private String cstmrReceiveTelRn;       // 고객정보_연락받을번호_끝자리
    private String othersPaymentAg;         // 고객정보_타인납부동의
    private String othersPaymentNm;         // 고객정보_타인납부_고객명
    private String othersPaymentRrn;        // 고객정보_타인납부_주민번호
    private String othersPaymentRelation;   // 고객정보_타인납부_명의자와의관계
    private String othersPaymentRnm;        // 고객정보_타인납부_신청인
    private Date sysRdate;                  // 등록일시
    private String cstmrName;              // 고객정보_고객명
    private String cstmrNativeRrn;          // 고객정보_내국인_주민등록번호
    private String cstmrForeignerDod;
    private String cstmrForeignerBirth;
    private String selfInqryAgrmYn;         // 본인인증조회동의
    private String selfCertType;            // 본인인증유형
    private String selfIssuExprDt;          // 발급/만료일자
    private String selfIssuNum;             // 발급번호
    private String cstmrJejuId;             // 제주항공ID
    private String selfCstmrCi;             // 본인인증한 CI 정보
    private String recommendFlagCd;         // 추천인 구분 코드
    private String recommendInfo;           // 추천인정보

}
