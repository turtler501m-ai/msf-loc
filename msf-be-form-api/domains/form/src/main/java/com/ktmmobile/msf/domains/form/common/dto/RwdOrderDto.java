package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RwdOrderDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long rwdSeq;         // 보상서비스시퀀스
    private String rwdProdCd;    // 보상서비스 코드
    private String resNo;        // 예약번호
    private long requestKey;     // 요청키
    private String contractNum;  // 계약번호
    private String ifTrgtCd;     // 연동대상코드 (CMN0250)
    private String chnCd;        // 가입채널코드 (CMN0249)
    private String imei;         // 단말고유식별번호
    private String imeiTwo;      // 단말고유식별번호2
    private String buyPric;      // 단말기 구입가
    private String fileId;       // 이미지 파일 아이디
    private String fileExt;      // 이미지 파일 확장자
    private String fileDir;      // 이미지 파일 경로
    private String vrfyRsltCd;   // 적격심사결과코드 (Y:적격, N:부적격, Z:심사중)
    private String vrfyDttm;     // 적격심사일자
    private String rmk;          // 검증결과내용
    private String regstId;      // 등록자ID
    private String regstDttm;    // 등록일시
    private String rvisnId;      // 수정자ID
    private String rvisnDttm;    // 수정일시
    private String rwdAuthInfo;  // 자급제보상서비스 인증정보
    private String cstmrType;    // 고객유형
    private String reqBuyType;   // 구매유형
    private String rwdStatCd;    // 보상서비스상태코드 (CMN0251)
    private String canRsltCd;    // 해지사유코드
    private String endDttm;      // 서비스 종료 일시
    private String rwdProdNm;    // 보상서비스 명
    private String smsRcvNo;     // 고객 전화번호 (문자발송용)

}
