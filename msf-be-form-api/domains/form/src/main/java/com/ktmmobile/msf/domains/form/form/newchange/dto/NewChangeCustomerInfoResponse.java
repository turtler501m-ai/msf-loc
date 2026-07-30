package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class NewChangeCustomerInfoResponse {

    private String email; //이메일
    private String addr; //주소
    private String homeTel; //집전화번호
    private String initActivationDate; //

    private String payMethod; //납부방법
    private String payBizrCd; //간편결제사업자코드
    private String billCycleDueDay; //납부일
    private String blBankName; //은행명
    private String blBankAcctNo; //계좌번호
    private String bankAcctHolderName; //납부자명
    private String blAddr; //청구지 주소
    private String creditCardName; //카드사명
    private String prevCardNo; //카드번호
    private String prevExpirDt; //카드만료기간
    private String jointBillWithKt; //KT합산구분 아이디
    private String payTmsCd; //납부회차

    private String socCode; //요금제코드
    private String socNm; //요금제명

    //private String prodId; //요금제코드
    //private String prodNm; //요금제명
    //private String famtTarifAmt; //월정액
    //private String efctStDt; //가입일자


    //String svcCntrNo;
    //String contractNum;
    //String customerSsn;
    //String customerLinkName;
    //String subscriberNo;
    //String lstComActvDate;
    //String iccId;
    //String dvcChgYn;
    //String gender;
    //String customerId;
    //String customerType;
    //String legalCi;
    //String lglAgntSsn;
    //String fstEsimYn;
}
