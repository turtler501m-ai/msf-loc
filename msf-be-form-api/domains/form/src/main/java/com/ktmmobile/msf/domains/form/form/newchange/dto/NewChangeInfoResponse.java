package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신규/변경 신청서 select response parameter 정의
 * 2026.04.
 */

@Getter
@Setter
@NoArgsConstructor
public class NewChangeInfoResponse {
    String tmpStepCd; //임시저장 단계

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
    String knoteIdentityEssNo;
    String knoteIdentityTypeCd;
    String knoteIdentityScanDt;
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
    String openReqDt;
    String reqInDay;
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
    String clause5gCoverageYn = "N";
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

    //MSF_REQUEST_CSTMR
    String cstmrNm;
    String cstmrNativeRrn;
    String cstmrNativeBirth;
    String cstmrNativeGenderCd;
    String cstmrPrivateCname;
    String cstmrPrivateBizNo;
    String cstmrForeignerRrn;
    String cstmrForeignerBirth;
    String cstmrForeignerGenderCd;
    String cstmrForeignerPn;
    String cstmrForeignerCountryCd;
    String cstmrForeignerNation;
    String cstmrForeignerVisaNo;
    String cstmrForeignerVdateStartDate;
    String cstmrForeignerVdateEndDate;
    String cstmrJuridicalCname;
    String cstmrJuridicalRrn;
    String cstmrJuridicalBizNo;
    String cstmrJuridicalRepNm;
    String upjnCd;
    String bcuSbst;
    String cstmrJuridicalUserNm;
    String cstmrJuridicalBirth;
    String cstmrVisitTypeCd;
    String cstmrTelFnNo;
    String cstmrTelMnNo;
    String cstmrTelRnNo;
    String cstmrMobileFnNo;
    String cstmrMobileMnNo;
    String cstmrMobileRnNo;
    String cstmrZipcd;
    String cstmrAdr;
    String cstmrAdrDtl;
    String cstmrAdrBjd;
    String cstmrEmailAdr;
    String cstmrEmailReceiveYn;
    String cstmrReceiveTelFnNo;
    String cstmrReceiveTelNmNo;
    String cstmrReceiveTelRnNo;

    //MSF_REQUEST_AGENT
    String minorAgentNm;
    String minorAgentRrn;
    String minorAgentBirth;
    String minorAgentGenderCd;
    String minorAgentRelTypeCd;
    String minorAgentTelFnNo;
    String minorAgentTelMnNo;
    String minorAgentTelRnNo;
    String minorAgentAgrmYn;
    String minorAgentSelfInqryAgrmYn;
    String minorAgentSelfCertTypeCd;
    String minorAgentCiInfo;
    String jrdclAgentNm;
    String jrdclAgentRrn;
    String jrdclAgentRelTypeCd;
    String jrdclAgentTelFnNo;
    String jrdclAgentTelMnNo;
    String jrdclAgentTelRnNo;

    //MSF_REQUEST_SALE
    String modelId;
    String modelMonthly;
    Long modelInstamt;
    String modelSalePolicyCd;
    Long modelPriceVat;
    Long modelDiscount1;
    Long modelSprt;
    Long modelPrice;
    Long modelDiscount3;
    Long realMdlInstamt;
    Long hndsetSalePrice;
    String sprtTypeCd;
    Long dcAmt;
    Long maxApdSprt;
    Long addDcAmt;
    Long enggMnthCnt;
    String recycleYn;
    String usimPriceTypeCd;
    Long usimPrice;
    String usimPayMthdCd;
    String sesplsYn;
    String joinPriceTypeCd;
    String joinPayMthdCd;
    Long joinPrice;
    String socCode;
    String socNm;
    Long socBaseChrgAmt;

    //MSF_REQUEST_BILL_REQ
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
    //String cstmrEmailAdr;

    //MSF_REQUEST_MOVE
    String moveCompanyCd;
    String moveMobileFnNo;
    String moveMobileMnNo;
    String moveMobileRnNo;
    String moveAuthTypeCd;
    String moveAuthNo;
    String moveThismonthPayTypeCd;
    String moveAllotmentSttusCd;
    String moveRefundAgreeYn;
    String reqGuideYn;
    String reqGuideFnNo;
    String reqGuideRnNo;
    String reqGuideMnNo;
    String osstPayDate;
    String osstPayTypeCd;
    Long movePenalty;

    //MSF_REQUEST_DOC
    String fileTypeCd;
    String filePathNm;
    //String fileNm;
}
