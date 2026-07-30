package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestBillReqVo {

    Long requestKey;
    String reqPayTypeCd;
    String reqBankCd;

    String reqAccountNm; //계좌 예금주명

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String reqAccountRrn; //계좌 예금주 주민번호

    String reqAccountRelTypeCd;

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String reqAccountNo; //계좌번호

    String reqCardNm; //신용카드 명의자명

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String reqCardRrn; //신용카드 명의자 주민번호

    String reqCardCompanyCd;

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String reqCardNo; //신용카드번호

    String reqCardYy;
    String reqCardMm;
    String reqWireTypeCd;
    String othersPaymentYn;
    String othersPaymentTelFnNo; //타인납부자 연락처
    String othersPaymentTelMnNo; //타인납부자 연락처
    String othersPaymentTelRnNo; //타인납부자 연락처
    String othersPaymentNm; //타인납부자명

    //@Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String othersPaymentRrn; //타인납부자 식별번호

    String othersPaymentRelTypeCd;

    String othersPaymentReqNm; //타인납부 신청인명
    String othersPaymentAgrYn;
    String prntsBillNo;
    String cstmrBillSendTypeCd;

    String cstmrEmailAdr; //명세서 이메일주소

}
