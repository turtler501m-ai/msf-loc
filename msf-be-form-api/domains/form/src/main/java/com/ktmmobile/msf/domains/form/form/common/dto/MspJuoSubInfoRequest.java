package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
//@JsonIgnoreProperties({"body"})
public class MspJuoSubInfoRequest {

    //신규변경 업무 중 기기변경의 핸드폰인증 시 request parameter
    private String cstmrType; //고객유형 (MSF_REQUEST.CSTMR_TYPE_CD)
    private String cstmrSsn; //고객식별번호 (내국인의 주민번호, 외국인의 외국인등록번호, 법인 및 공공기관은 사업자번호)
    private String cstmrJuridicalRrn; //법인 및 공공기관의 법인번호
    private String customerMobileNo; //인증할 고객핸드폰번호
    //private String customerName; //고객명

    //가입계약번호
    private String contractNum;

    //주민등록번호/
    private String customerSsn;

    //고객명/
    private String customerLinkName;

    //전화번호_이전CTN
    private String subscriberNo;

    //최초 개통일자
    private String lstComActvDate;

    //USIM 카드 일련번호
    private String iccId;

    //기변 여부
    private String dvcChgYn;

    //성별
    private String gender;

    //고객아이디 : CUSTOMER_ID
    private String customerId;

    //고객유형
    private String customerType;

    //사업자번호 (법인/공공기관 서비스변경 인증용)
    private String bizNo;

    /* 2025-01-21 다이렉트몰 회원 가입 및 정보 변경시 본인인증 강화 */
    //법정대리인 Ci
    private String legalCi;
    //법정대리인 주민번호
    private String lglAgntSsn;

}
