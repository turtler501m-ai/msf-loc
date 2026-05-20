package com.ktmmobile.msf.domains.form.form.newchange.dto;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 신규/변경 신청서 insert 및 update request parameter 정의
 */
@Getter
@Setter
@NoArgsConstructor
public class NewChangeInfoRequest {

    String tmpStepCd; //임시저장 단계
    String preCheck; //신청서번호 존재여부 확인을 위한 변수 (구비서류, 안면인식 등에 request_key 미리 생성이슈)

    List<NewChangeAdditionRequest> additionList;

    Long newRequestKey;

    Long requestKey;
    String cretIp;
    String cretDt;
    String cretId;
    String amdIp;
    String amdDt;
    String amdId;

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
    String serviceTypeCd = "PO";
    String operTypeCd;
    String cstmrTypeCd;
    String identityCertTypeCd;
    String knoteIdentityScanCstmrNm;
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
    String onOffTypeCd = "0"; //고정값 처리가 맞는지~
    String soCd;
    LocalDateTime openReqDt;
    LocalDateTime reqInDay;
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
    String othersPaymentAgrYn;
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

    //MSF_REQUEST_DVC_CHG
    String dvcChgTypeCd;
    String dvcChgRsnCd;
    String dvcChgRsnDtlCd;
    String instamtPayMthdCd;

    //MSF_REQUEST_DOC
    String fileTypeCd;
    String filePathNm;
    //String fileNm;


    @Override
    public String toString() {
        return "NewChangeInfoRequest{" +
            "tmpStepCd='" + tmpStepCd + '\'' +
            ", additionList=" + additionList +
            ", newRequestKey=" + newRequestKey +
            ", requestKey=" + requestKey +
            ", managerCd='" + managerCd + '\'' +
            ", managerNm='" + managerNm + '\'' +
            ", agentCd='" + agentCd + '\'' +
            ", agentNm='" + agentNm + '\'' +
            ", shopCd='" + shopCd + '\'' +
            ", shopNm='" + shopNm + '\'' +
            ", realShopNm='" + realShopNm + '\'' +
            ", cpntId='" + cpntId + '\'' +
            ", cpntNm='" + cpntNm + '\'' +
            ", cntpntShopCd='" + cntpntShopCd + '\'' +
            ", cntpntShopNm='" + cntpntShopNm + '\'' +
            ", reqBuyTypeCd='" + reqBuyTypeCd + '\'' +
            ", openTypeCd='" + openTypeCd + '\'' +
            ", serviceTypeCd='" + serviceTypeCd + '\'' +
            ", operTypeCd='" + operTypeCd + '\'' +
            ", cstmrTypeCd='" + cstmrTypeCd + '\'' +
            ", identityCertTypeCd='" + identityCertTypeCd + '\'' +
            ", knoteIdentityScanCstmrNm='" + knoteIdentityScanCstmrNm + '\'' +
            ", knoteIdentityEssNo='" + knoteIdentityEssNo + '\'' +
            ", knoteIdentityTypeCd='" + knoteIdentityTypeCd + '\'' +
            ", knoteIdentityScanDt=" + knoteIdentityScanDt +
            ", knoteScanId='" + knoteScanId + '\'' +
            ", fathTrgYn='" + fathTrgYn + '\'' +
            ", fathTrgIdentityCertTypeCd='" + fathTrgIdentityCertTypeCd + '\'' +
            ", fathTransacId='" + fathTransacId + '\'' +
            ", fathCmpltNtfyDate='" + fathCmpltNtfyDate + '\'' +
            ", fathTelNo='" + fathTelNo + '\'' +
            ", fathMobileFnNo='" + fathMobileFnNo + '\'' +
            ", fathMobileMnNo='" + fathMobileMnNo + '\'' +
            ", fathMobileRnNo='" + fathMobileRnNo + '\'' +
            ", authInfo='" + authInfo + '\'' +
            ", identityTypeCd='" + identityTypeCd + '\'' +
            ", identityIssuDate='" + identityIssuDate + '\'' +
            ", identityIssuRegion='" + identityIssuRegion + '\'' +
            ", selfIssuNo='" + selfIssuNo + '\'' +
            ", driveLicnsNo='" + driveLicnsNo + '\'' +
            ", openNo='" + openNo + '\'' +
            ", contractNum='" + contractNum + '\'' +
            ", prodTypeCd='" + prodTypeCd + '\'' +
            ", prodId='" + prodId + '\'' +
            ", prodNm='" + prodNm + '\'' +
            ", reqPhoneSn='" + reqPhoneSn + '\'' +
            ", reqModelNm='" + reqModelNm + '\'' +
            ", sntyCapacCd='" + sntyCapacCd + '\'' +
            ", sntyColorCd='" + sntyColorCd + '\'' +
            ", reqModelColor='" + reqModelColor + '\'' +
            ", shopUsmId='" + shopUsmId + '\'' +
            ", usimKindsCd='" + usimKindsCd + '\'' +
            ", reqUsimSn='" + reqUsimSn + '\'' +
            ", reqUsimNm='" + reqUsimNm + '\'' +
            ", eid='" + eid + '\'' +
            ", imei1='" + imei1 + '\'' +
            ", imei2='" + imei2 + '\'' +
            ", esimPhoneId='" + esimPhoneId + '\'' +
            ", uploadPhoneSrlNo=" + uploadPhoneSrlNo +
            ", reqWantFnNo='" + reqWantFnNo + '\'' +
            ", reqWantMnNo='" + reqWantMnNo + '\'' +
            ", reqWantRnNo='" + reqWantRnNo + '\'' +
            ", insrCd='" + insrCd + '\'' +
            ", insrProdCd='" + insrProdCd + '\'' +
            ", clauseInsuranceYn='" + clauseInsuranceYn + '\'' +
            ", clauseInsrProdYn='" + clauseInsrProdYn + '\'' +
            ", insrAuthInfo='" + insrAuthInfo + '\'' +
            ", prntsContractNum='" + prntsContractNum + '\'' +
            ", prntsMobileNo='" + prntsMobileNo + '\'' +
            ", jehuPartnerTypeCd='" + jehuPartnerTypeCd + '\'' +
            ", jehuProdTypeCd='" + jehuProdTypeCd + '\'' +
            ", reqAdditionListNm='" + reqAdditionListNm + '\'' +
            ", reqAdditionPrice=" + reqAdditionPrice +
            ", phonePaymentYn='" + phonePaymentYn + '\'' +
            ", onOffTypeCd='" + onOffTypeCd + '\'' +
            ", soCd='" + soCd + '\'' +
            ", openReqDt=" + openReqDt +
            ", reqInDay=" + reqInDay +
            ", clausePriCollectYn='" + clausePriCollectYn + '\'' +
            ", clausePriOfferYn='" + clausePriOfferYn + '\'' +
            ", clauseEssCollectYn='" + clauseEssCollectYn + '\'' +
            ", clausePriTrustYn='" + clausePriTrustYn + '\'' +
            ", clausePriAdYn='" + clausePriAdYn + '\'' +
            ", clauseConfidenceYn='" + clauseConfidenceYn + '\'' +
            ", clauseFathYn='" + clauseFathYn + '\'' +
            ", nwBlckAgrmYn='" + nwBlckAgrmYn + '\'' +
            ", appBlckAgrmYn='" + appBlckAgrmYn + '\'' +
            ", blckAppDivCd='" + blckAppDivCd + '\'' +
            ", soTrnsAgrmYn='" + soTrnsAgrmYn + '\'' +
            ", clauseJehuYn='" + clauseJehuYn + '\'' +
            ", clauseRentalModelCpYn='" + clauseRentalModelCpYn + '\'' +
            ", clauseRentalModelCpPrYn='" + clauseRentalModelCpPrYn + '\'' +
            ", clauseRentalServiceYn='" + clauseRentalServiceYn + '\'' +
            ", clauseMpps35Yn='" + clauseMpps35Yn + '\'' +
            ", clauseFinanceYn='" + clauseFinanceYn + '\'' +
            ", clause5gCoverageYn='" + clause5gCoverageYn + '\'' +
            ", personalInfoCollectAgreeYn='" + personalInfoCollectAgreeYn + '\'' +
            ", othersTrnsAgreeYn='" + othersTrnsAgreeYn + '\'' +
            ", clauseSensiCollectYn='" + clauseSensiCollectYn + '\'' +
            ", clauseSensiOfferYn='" + clauseSensiOfferYn + '\'' +
            ", clausePartnerOfferYn='" + clausePartnerOfferYn + '\'' +
            ", othersTrnsKtAgreeYn='" + othersTrnsKtAgreeYn + '\'' +
            ", othersAdReceiveAgreeYn='" + othersAdReceiveAgreeYn + '\'' +
            ", ktCounselAgreeYn='" + ktCounselAgreeYn + '\'' +
            ", combineSoloTypeYn='" + combineSoloTypeYn + '\'' +
            ", combineSoloYn='" + combineSoloYn + '\'' +
            ", etcSpecialSbst='" + etcSpecialSbst + '\'' +
            ", memo='" + memo + '\'' +
            ", recYn='" + recYn + '\'' +
            ", resCd='" + resCd + '\'' +
            ", resMsg='" + resMsg + '\'' +
            ", resNo='" + resNo + '\'' +
            ", procDt='" + procDt + '\'' +
            ", procCd='" + procCd + '\'' +
            ", proSttusCd='" + proSttusCd + '\'' +
            ", sbscProCd='" + sbscProCd + '\'' +
            ", scanId='" + scanId + '\'' +
            ", appFormYn='" + appFormYn + '\'' +
            ", appFormXmlYn='" + appFormXmlYn + '\'' +
            ", fileNm='" + fileNm + '\'' +
            ", fileMaskNm='" + fileMaskNm + '\'' +
            ", faxYn='" + faxYn + '\'' +
            ", faxNo='" + faxNo + '\'' +
            ", cstmrNm='" + cstmrNm + '\'' +
            ", cstmrNativeRrn='" + cstmrNativeRrn + '\'' +
            ", cstmrNativeBirth='" + cstmrNativeBirth + '\'' +
            ", cstmrNativeGenderCd='" + cstmrNativeGenderCd + '\'' +
            ", cstmrPrivateCname='" + cstmrPrivateCname + '\'' +
            ", cstmrPrivateBizNo='" + cstmrPrivateBizNo + '\'' +
            ", cstmrForeignerRrn='" + cstmrForeignerRrn + '\'' +
            ", cstmrForeignerBirth='" + cstmrForeignerBirth + '\'' +
            ", cstmrForeignerGenderCd='" + cstmrForeignerGenderCd + '\'' +
            ", cstmrForeignerPn='" + cstmrForeignerPn + '\'' +
            ", cstmrForeignerCountryCd='" + cstmrForeignerCountryCd + '\'' +
            ", cstmrForeignerNation='" + cstmrForeignerNation + '\'' +
            ", cstmrForeignerVisaNo='" + cstmrForeignerVisaNo + '\'' +
            ", cstmrForeignerVdateStartDate='" + cstmrForeignerVdateStartDate + '\'' +
            ", cstmrForeignerVdateEndDate='" + cstmrForeignerVdateEndDate + '\'' +
            ", cstmrJuridicalCname='" + cstmrJuridicalCname + '\'' +
            ", cstmrJuridicalRrn='" + cstmrJuridicalRrn + '\'' +
            ", cstmrJuridicalBizNo='" + cstmrJuridicalBizNo + '\'' +
            ", cstmrJuridicalRepNm='" + cstmrJuridicalRepNm + '\'' +
            ", upjnCd='" + upjnCd + '\'' +
            ", bcuSbst='" + bcuSbst + '\'' +
            ", cstmrJuridicalUserNm='" + cstmrJuridicalUserNm + '\'' +
            ", cstmrJuridicalBirth='" + cstmrJuridicalBirth + '\'' +
            ", cstmrVisitTypeCd='" + cstmrVisitTypeCd + '\'' +
            ", cstmrTelFnNo='" + cstmrTelFnNo + '\'' +
            ", cstmrTelMnNo='" + cstmrTelMnNo + '\'' +
            ", cstmrTelRnNo='" + cstmrTelRnNo + '\'' +
            ", cstmrMobileFnNo='" + cstmrMobileFnNo + '\'' +
            ", cstmrMobileMnNo='" + cstmrMobileMnNo + '\'' +
            ", cstmrMobileRnNo='" + cstmrMobileRnNo + '\'' +
            ", cstmrZipcd='" + cstmrZipcd + '\'' +
            ", cstmrAdr='" + cstmrAdr + '\'' +
            ", cstmrAdrDtl='" + cstmrAdrDtl + '\'' +
            ", cstmrAdrBjd='" + cstmrAdrBjd + '\'' +
            ", cstmrEmailAdr='" + cstmrEmailAdr + '\'' +
            ", cstmrEmailReceiveYn='" + cstmrEmailReceiveYn + '\'' +
            ", cstmrReceiveTelFnNo='" + cstmrReceiveTelFnNo + '\'' +
            ", cstmrReceiveTelNmNo='" + cstmrReceiveTelNmNo + '\'' +
            ", cstmrReceiveTelRnNo='" + cstmrReceiveTelRnNo + '\'' +
            ", minorAgentNm='" + minorAgentNm + '\'' +
            ", minorAgentRrn='" + minorAgentRrn + '\'' +
            ", minorAgentBirth='" + minorAgentBirth + '\'' +
            ", minorAgentGenderCd='" + minorAgentGenderCd + '\'' +
            ", minorAgentRelTypeCd='" + minorAgentRelTypeCd + '\'' +
            ", minorAgentTelFnNo='" + minorAgentTelFnNo + '\'' +
            ", minorAgentTelMnNo='" + minorAgentTelMnNo + '\'' +
            ", minorAgentTelRnNo='" + minorAgentTelRnNo + '\'' +
            ", minorAgentAgrmYn='" + minorAgentAgrmYn + '\'' +
            ", minorAgentSelfInqryAgrmYn='" + minorAgentSelfInqryAgrmYn + '\'' +
            ", minorAgentSelfCertTypeCd='" + minorAgentSelfCertTypeCd + '\'' +
            ", minorAgentCiInfo='" + minorAgentCiInfo + '\'' +
            ", jrdclAgentNm='" + jrdclAgentNm + '\'' +
            ", jrdclAgentRrn='" + jrdclAgentRrn + '\'' +
            ", jrdclAgentRelTypeCd='" + jrdclAgentRelTypeCd + '\'' +
            ", jrdclAgentTelFnNo='" + jrdclAgentTelFnNo + '\'' +
            ", jrdclAgentTelMnNo='" + jrdclAgentTelMnNo + '\'' +
            ", jrdclAgentTelRnNo='" + jrdclAgentTelRnNo + '\'' +
            ", modelId='" + modelId + '\'' +
            ", modelMonthly='" + modelMonthly + '\'' +
            ", modelInstamt=" + modelInstamt +
            ", modelSalePolicyCd='" + modelSalePolicyCd + '\'' +
            ", modelPriceVat=" + modelPriceVat +
            ", modelDiscount1=" + modelDiscount1 +
            ", modelSprt=" + modelSprt +
            ", modelPrice=" + modelPrice +
            ", modelDiscount3=" + modelDiscount3 +
            ", realMdlInstamt=" + realMdlInstamt +
            ", hndsetSalePrice=" + hndsetSalePrice +
            ", sprtTypeCd='" + sprtTypeCd + '\'' +
            ", dcAmt=" + dcAmt +
            ", maxApdSprt=" + maxApdSprt +
            ", addDcAmt=" + addDcAmt +
            ", enggMnthCnt=" + enggMnthCnt +
            ", recycleYn='" + recycleYn + '\'' +
            ", usimPriceTypeCd='" + usimPriceTypeCd + '\'' +
            ", usimPrice=" + usimPrice +
            ", usimPayMthdCd='" + usimPayMthdCd + '\'' +
            ", sesplsYn='" + sesplsYn + '\'' +
            ", joinPriceTypeCd='" + joinPriceTypeCd + '\'' +
            ", joinPayMthdCd='" + joinPayMthdCd + '\'' +
            ", joinPrice=" + joinPrice +
            ", socCode='" + socCode + '\'' +
            ", socNm='" + socNm + '\'' +
            ", socBaseChrgAmt=" + socBaseChrgAmt +
            ", reqPayTypeCd='" + reqPayTypeCd + '\'' +
            ", reqBankCd='" + reqBankCd + '\'' +
            ", reqAccountNm='" + reqAccountNm + '\'' +
            ", reqAccountRrn='" + reqAccountRrn + '\'' +
            ", reqAccountRelTypeCd='" + reqAccountRelTypeCd + '\'' +
            ", reqAccountNo='" + reqAccountNo + '\'' +
            ", reqCardNm='" + reqCardNm + '\'' +
            ", reqCardRrn='" + reqCardRrn + '\'' +
            ", reqCardCompanyCd='" + reqCardCompanyCd + '\'' +
            ", reqCardNo='" + reqCardNo + '\'' +
            ", reqCardYy='" + reqCardYy + '\'' +
            ", reqCardMm='" + reqCardMm + '\'' +
            ", reqWireTypeCd='" + reqWireTypeCd + '\'' +
            ", othersPaymentYn='" + othersPaymentYn + '\'' +
            ", othersPaymentTelFnNo='" + othersPaymentTelFnNo + '\'' +
            ", othersPaymentTelMnNo='" + othersPaymentTelMnNo + '\'' +
            ", othersPaymentTelRnNo='" + othersPaymentTelRnNo + '\'' +
            ", othersPaymentNm='" + othersPaymentNm + '\'' +
            ", othersPaymentRrn='" + othersPaymentRrn + '\'' +
            ", othersPaymentRelTypeCd='" + othersPaymentRelTypeCd + '\'' +
            ", othersPaymentReqNm='" + othersPaymentReqNm + '\'' +
            ", othersPaymentAgrYn='" + othersPaymentAgrYn + '\'' +
            ", prntsBillNo='" + prntsBillNo + '\'' +
            ", cstmrBillSendTypeCd='" + cstmrBillSendTypeCd + '\'' +
            ", moveCompanyCd='" + moveCompanyCd + '\'' +
            ", moveMobileFnNo='" + moveMobileFnNo + '\'' +
            ", moveMobileMnNo='" + moveMobileMnNo + '\'' +
            ", moveMobileRnNo='" + moveMobileRnNo + '\'' +
            ", moveAuthTypeCd='" + moveAuthTypeCd + '\'' +
            ", moveAuthNo='" + moveAuthNo + '\'' +
            ", moveThismonthPayTypeCd='" + moveThismonthPayTypeCd + '\'' +
            ", moveAllotmentSttusCd='" + moveAllotmentSttusCd + '\'' +
            ", moveRefundAgreeYn='" + moveRefundAgreeYn + '\'' +
            ", reqGuideYn='" + reqGuideYn + '\'' +
            ", reqGuideFnNo='" + reqGuideFnNo + '\'' +
            ", reqGuideRnNo='" + reqGuideRnNo + '\'' +
            ", reqGuideMnNo='" + reqGuideMnNo + '\'' +
            ", osstPayDate='" + osstPayDate + '\'' +
            ", osstPayTypeCd='" + osstPayTypeCd + '\'' +
            ", movePenalty=" + movePenalty +
            ", dvcChgTypeCd='" + dvcChgTypeCd + '\'' +
            ", dvcChgRsnCd='" + dvcChgRsnCd + '\'' +
            ", dvcChgRsnDtlCd='" + dvcChgRsnDtlCd + '\'' +
            ", instamtPayMthdCd='" + instamtPayMthdCd + '\'' +
            ", fileTypeCd='" + fileTypeCd + '\'' +
            ", filePathNm='" + filePathNm + '\'' +
            '}';
    }
}
