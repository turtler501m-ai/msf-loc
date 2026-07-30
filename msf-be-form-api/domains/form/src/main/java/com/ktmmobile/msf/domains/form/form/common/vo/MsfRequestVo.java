package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestVo {

    String tmpStepCd;
    Long requestKey;
    String managerCd;
    String managerNm;
    String agentCd;
    String agentNm;
    String shopCd;
    String shopNm;
    String realShopNm;
    String cpntId;
    String cpntNm;
    String cntpntShopCd;
    String cntpntShopNm;
    String reqBuyTypeCd;
    String openTypeCd;
    String serviceTypeCd;
    String operTypeCd;
    String cstmrTypeCd;
    String identityCertTypeCd;
    String knoteIdentityScanCstmrNm;

    @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
    String knoteIdentityEssNo;

    String knoteIdentityTypeCd;
    LocalDateTime knoteIdentityScanDt;
    String knoteScanId;
    String fathTrgYn;
    String fathTrgIdentityCertTypeCd;
    String fathTransacId;
    String fathCmpltNtfyDate;
    String fathTelNo;
    String fathMobileFnNo;
    String fathMobileMnNo;
    String fathMobileRnNo;
    String authInfo;
    String identityTypeCd;
    String identityIssuDate;
    String identityIssuRegion;
    String selfIssuNo;
    String driveLicnsNo;
    String openNo;
    String contractNum;
    String custId;
    String billAcntNo;
    String prodTypeCd;
    String prodId;
    String prodNm;
    String reqPhoneSn;
    String reqModelNm;
    String sntyCapacCd;
    String sntyColorCd;
    String reqModelColor;
    String shopUsmId;
    String usimKindsCd;
    String reqUsimSn;
    String reqUsimNm;
    String eid;
    String imei1;
    String imei2;
    String esimPhoneId;
    Long uploadPhoneSrlNo;
    String reqWantFnNo;
    String reqWantMnNo;
    String reqWantRnNo;
    String insrCd;
    String insrProdCd;
    String clauseInsuranceYn;
    String clauseInsrProdYn;
    String insrAuthInfo;
    String prntsContractNum;
    String prntsMobileNo;
    String jehuPartnerTypeCd;
    String jehuProdTypeCd;
    String reqAdditionListNm;
    Long reqAdditionPrice;
    String phonePaymentYn;
    String onOffTypeCd;
    String soCd;
    LocalDateTime openReqDt;
    LocalDateTime reqInDt;
    String clausePriCollectYn;
    String clausePriOfferYn;
    String clauseEssCollectYn;
    String clausePriTrustYn;
    String clausePriAdYn;
    String clauseConfidenceYn;
    String clauseFathYn;
    String nwBlckAgrmYn;
    String appBlckAgrmYn;
    String blckAppDivCd;
    String soTrnsAgrmYn;
    String clauseJehuYn;
    String clauseRentalModelCpYn;
    String clauseRentalModelCpPrYn;
    String clauseRentalServiceYn;
    String clauseMpps35Yn;
    String clauseFinanceYn;
    String clause5gCoverageYn;
    String othersTrnsAllAgreeYn;
    String personalInfoCollectAgreeYn;
    String othersTrnsAgreeYn;
    String clauseSensiCollectYn;
    String clauseSensiOfferYn;
    String clausePartnerOfferYn;
    String othersTrnsKtAgreeYn;
    String othersAdReceiveAgreeYn;
    String ktCounselAgreeYn;
    String combineSoloTypeYn;
    String combineSoloYn;
    String etcSpecialSbst;
    String memo;
    String recYn;
    String resCd;
    String resMsg;
    String resNo;
    String procDt;
    String procCd;
    String proSttusCd;
    String sbscProCd;
    String scanId;
    String appFormYn;
    String appFormXmlYn;
    String fileNm;
    String fileMaskNm;
    String faxYn;
    String faxNo;
    String parentScanId;
    String clauseMoveCode;
    String indvLocaPrvAgreeYn;
    String disPrmtId;

    Long volumeMobileNoQnty;
    String volumeRepMobileNoYn;
    String volumeRepMobileNo;
}

