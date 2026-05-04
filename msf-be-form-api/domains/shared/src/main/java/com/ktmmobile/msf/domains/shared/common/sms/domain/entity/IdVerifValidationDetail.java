package com.ktmmobile.msf.domains.shared.common.sms.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.shared.common.sms.domain.code.StepEndStatus;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class IdVerifValidationDetail {
    private Long crtVldDtlSeq; // 본인인증검증일련번호
    private Integer step; // 단계(순서)
    private Integer subStep; // 서브스텝
    private String  regDate; // 등록일자: (YYYYMMDD)
    private String authTypeCd; // 본인인증유형코드: 인증종류(RCP2001)
    private String moduTypeCd; // 모듈종류코드: 모듈종류(certModuType)
    private String compTypeCd; // 비교타입코드: 비교타입(certCompType)
    private StepEndStatus stepEndYn; // 스텝종료여부: (Y,N)
    private String veriRsltSbst; // 검증결과내용: 검증결과(certVeriRslt)
    private String referer; // REFERER: 페이지url(certReferer)
    private String authNm; // 인증성명
    private String authBirth; // 인증생년월일
    private String mobileNo; // 휴대폰번호
    private String ci; // CI
    private Long requestKey; // 가입신청키
    private String contractNum; // 계약번호
    private String reqUsimSn; // USIM번호
    private String reqBankCd; // 은행코드
    private String reqAccountNo; // 요청계좌번호
    private Long uploadPhoneSrlNo; // 업로드휴대폰일련번호
    private Long reqSeq; // 요청일련번호
    private Long resSeq; // 응답일련번호
    private String ncTypeCd; // 대상자구분코드: 대상자구분(certNcType)
    private String di; // DI
    private String crtEtc; // 기타
    private String rip; // 등록IP
    private String urlTypeCd; // URL유형코드: URL구분값
    private String reqCardCompanyCd; // 카드회사코드
    private String reqCardNo; // 요청신용카드번호
    private String crdtCardPeriod; // 카드유효기간: (YYYYMM)
}
