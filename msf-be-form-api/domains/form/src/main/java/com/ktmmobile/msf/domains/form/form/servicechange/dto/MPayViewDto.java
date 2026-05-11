package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class MPayViewDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String svcCntrNo;    // 계약번호
    private String mPayPeriod;   // 조회기간
    
    private String settlDealNo;  // 결제거래번호
    private String settleNo;     // 결제번호
    private String payStatus; 	 // 결재구분 (PRTLPAY, FULLPAY, PRTLCNCL, FULLCNCL)
    private String payStatusNm;  // 결재구분명 
    private String payStatusCd;  // 결제구분 순서코드
    private String settlDt; 	 // 결제일시  YYYYMMDDHH24MISS
    private String settlDtFmt; 	 // 결제일시 YYYY-MM-DD HH24:MI:SS
    private String settlNm; 	 // 결제명
    private String amt; 	     // 결제금액 또는 취소금액
    private String tmonLmtAmt; 	 // 당월한도금액
    private String rmndLmtAmt; 	 // 잔여한도금액
    private String ptnrPgId;  	 // 파트너 pg 아이디
    private String ptnrPgNm; 	 // 파트너 pg명
    private String ptnrCpId; 	 // 파트너 cp 아이디
    private String ptnrCpNm; 	 // 파트너 cp명
    private String ptnrSvcId; 	 // 파트너 서비스 아이디
    private String ptnrSvcNm; 	 // 파트너 서비스명
 
}
