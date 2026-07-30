package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MsfFathResultPush {

    private String fathTransacId;
    private Long seq;
    private String slsCmpcCd;
    private String identityCd;
    @Encrypted(FieldCryptoAlgorithm.AES_GCM)
    private String custNm;
    @Encrypted(FieldCryptoAlgorithm.AES_GCM)
    private String custIdfyNo;
    private String issueDate;
    @Encrypted(FieldCryptoAlgorithm.AES_GCM)
    private String driveLicnsNo;
    private String idcardPhotoImgNm;
    private String idcardCopiesImgNm;
    private String mblIdcardQrImgNm;
    private String idcardConfWayCd;
    private String distRsrtnYn;
    private String fathProgrStepCd;
    private String fathCmpltNtfyDate;
    private String fathUrlRqtDate;
    private String fathResltCd;
    private String fathResltSbst;
    private String fathRqtrId;
    private String birth;
    private String skipPsblYn;
    private String photoAthnTxnSeq;
    private String photoAthnDt;
    private String photoAthnDecideCd;
    private String photoAthnResltCd;
    private String photoAthnResltDtlCd;
    private String userId;
    @Encrypted(FieldCryptoAlgorithm.AES_GCM)
    private String smsRcvTelNo;
}
