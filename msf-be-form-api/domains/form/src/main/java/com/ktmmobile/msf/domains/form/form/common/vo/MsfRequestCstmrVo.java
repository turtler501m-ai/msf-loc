package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;


@Getter
@Setter
@NoArgsConstructor
public class MsfRequestCstmrVo {

    Long requestKey;

    String cstmrNm; //고객명
    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String cstmrNativeRrn; //주민등록번호

    String cstmrNativeBirth;
    String cstmrNativeGenderCd;
    String cstmrPrivateCname;
    String cstmrPrivateBizNo;
    String cstmrPrivateBizNoIssuDate;

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String cstmrForeignerRrn; //외국인등록번호
    String cstmrForeignerBirth;
    String cstmrForeignerGenderCd;

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String cstmrForeignerPn; //여권번호

    String cstmrForeignerCountryCd;
    String cstmrForeignerNation;

    String cstmrForeignerVisaNo; //외국인비자번호
    String cstmrForeignerVdateStartDate;
    String cstmrForeignerVdateEndDate;
    String cstmrJuridicalCname;
    String cstmrJuridicalRrn;
    String cstmrJuridicalBizNo;
    String cstmrJuridicalBizNoIssuDate;
    String cstmrJuridicalRepNm;
    String upjnCd;
    String bcuSbst;
    String cstmrJuridicalUserNm;
    String cstmrJuridicalBirth;
    String cstmrVisitTypeCd;
    String cstmrTelFnNo; //연락처
    String cstmrTelMnNo; //연락처
    String cstmrTelRnNo; //연락처
    String cstmrMobileFnNo; //핸드폰번호
    String cstmrMobileMnNo; //핸드폰번호
    String cstmrMobileRnNo; //핸드폰번호
    String cstmrZipcd; //우편번호
    String cstmrAdr; //주소
    String cstmrAdrDtl; //상세주소
    String cstmrAdrBjd; //법정동주소
    String cstmrEmailAdr; //이메일주소
    String cstmrEmailReceiveYn;
    String cstmrReceiveTelFnNo; //고객 연락번호
    String cstmrReceiveTelNmNo; //고객 연락번호
    String cstmrReceiveTelRnNo; //고객 연락번호

}
