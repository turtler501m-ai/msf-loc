package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestBillReqVo {

    Long requestKey;
    String formTypeCd;
    String reqPayTypeCd;
    String reqBankCd;
    String reqAccountNm;
    String reqAccountRrn;
    String reqAccountRelTypeCd;
    String reqAccountNo;
    String reqCardNm;
    String reqCardRrn;
    String reqCardCompanyCd;
    String reqCardNo;
    String reqCardYy;
    String reqCardMm;
    String reqWireTypeCd;
    String othersPaymentYn;
    String othersPaymentTelFnNo;
    String othersPaymentTelMnNo;
    String othersPaymentTelRnNo;
    String othersPaymentNm;
    String othersPaymentRrn;
    String othersPaymentRelTypeCd;
    String othersPaymentReqNm;
    String prntsBillNo;
    String cstmrBillSendTypeCd;
    String cstmrEmailAdr;

}
