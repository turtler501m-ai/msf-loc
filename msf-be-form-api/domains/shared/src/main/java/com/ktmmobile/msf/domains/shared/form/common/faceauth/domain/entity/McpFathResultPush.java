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
public class McpFathResultPush {

    private String fathTransacId;
    private Long seq;
    private String slsCmpcCd;
    private String retvCdVal;
    @Encrypted(FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC)
    private String custNm;
    @Encrypted(FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC)
    private String custIdfyNo;
    private String issDateVal;
    @Encrypted(FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC)
    private String driveLicnsNo;
    private String idcardPhotoImg;
    private String idcardCopiesImg;
    private String mblIdcardQrImg;
    private String idcardConfWay;
    private String distRsrtnYn;
    private String fathProgrStepCd;
    private String fathCmpltNtfyDt;
    private String fathUrlRqtDt;
    private String fathResltCd;
    private String fathResltSbst;
    private String fathRqtrId;
    private String skipPsblYn;
    @Encrypted(FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC)
    private String smsRcvTelNo;
}
