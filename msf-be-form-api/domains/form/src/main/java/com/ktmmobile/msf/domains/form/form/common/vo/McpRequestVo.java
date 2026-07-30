package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestVo {

    private Long requestKey;

    private String rip;

    private LocalDateTime sysRdate = LocalDateTime.now();

    private String cretId;

    private LocalDateTime rvisnDttm;

    private String rvisnId;

    private String managerCode;

    private String agentCode;

    private String shopCd;

    private String shopNm;

    private String realShopNm;

    private String cpntId;

    private String cntpntShopId;

    private String reqBuyType;

    private String serviceType;

    private String operType;

    private String cstmrType;

    private String fathTrgYn;

    private String fathTransacId;

    private String fathCmpltNtfyDt;

    private String onlineAuthType;

    private String onlineAuthInfo;

    private String openNo;

    private String contractNum;

    private String prodType;

    private String prodId;

    private String prodNm;

    private String reqPhoneSn;

    private String reqModelName;

    private String sntyCapacCd;

    private String sntyColorCd;

    private String reqModelColor;

    private String shopUsmId;

    private String usimKindsCd;

    private String reqUsimSn;

    private String reqUsimName;

    private String eid;

    private String imei1;

    private String imei2;

    private String esimPhoneId;

    private Long uploadPhoneSrlNo;

    private String rwdProdCd = "";

    private String rwdAuthInfo = "";

    private String reqWantNumber;

    private String reqWantNumber2;

    private String reqWantNumber3;

    private String insrCd;

    private String insrProdCd;

    private String clauseInsuranceFlag;

    private String clauseInsrProdFlag;

    private String insrAuthInfo;

    private String prntsContractNo;

    private String jehuPartnerType;

    private String jehuProdType;

    private String reqAddition;

    private Integer reqAdditionPrice;

    private String phonePayment;

    private String reqPayType;

    private String onOffType = "0";

    private String soCd = "M";

    private LocalDateTime openReqDate;

    private LocalDateTime reqInDay;

    private String ktmReferer = "";

    private String openMarketReferer = "";

    private String spcCode = "";

    private String bannerCd = "";

    private String promotionCd = "";

    private String countryCode = "";

    private String disPrmtId = "";

    private Integer hubOrderSeq = 0;

    private String openMarketId = "";

    private String evntCdPrmt = "";

    private String clausePriCollectFlag;

    private String clausePriOfferFlag;

    private String clauseEssCollectFlag;

    private String clausePriTrustFlag;

    private String clausePriAdFlag;

    private String clauseConfidenceFlag;

    private String clauseFathFlag;

    private String nwBlckAgrmYn;

    private String appBlckAgrmYn;

    private String appCd;

    private String soTrnsAgrmYn;

    private String clauseJehuFlag;

    private String clauseRentalModelCp;

    private String clauseRentalModelCpPr;

    private String clauseRentalService;

    private String clauseMpps35Flag;

    private String clauseFinanceFlag;

    private String clause5gCoverageFlag;

    private String personalInfoCollectAgree;

    private String othersTrnsAgree;

    private String clauseSensiCollectFlag;

    private String clauseSensiOfferFlag;

    private String clausePartnerOfferFlag;

    private String othersTrnsKtAgree;

    private String othersAdReceiveAgree;

    private String ktCounselAgree;

    private String combineSoloType;

    private String combineSoloFlag;

    private String clauseRwdFlag = "";

    private String etcSpecial;

    private String memo;

    private String recYn = "N";

    private String resCode;

    private String resMsg;

    private String resNo;

    private String pstate = "";

    private String requestStateCode;

    private String scanId;

    private String appFormYn;

    private String appFormXmlYn;

    private String file01;

    private String file01Mask;

    private String faxyn;

    private String faxnum;

    private String indvLocaPrvAgree;

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }

    public void setCretIp(String cretIp) { this.rip = cretIp; }

    public void setCretDt(LocalDateTime cretDt) { this.sysRdate = cretDt; }

    public void setCretId(String cretId) { this.cretId = cretId; }

    public void setAmdDt(LocalDateTime amdDt) { this.rvisnDttm = amdDt; }

    public void setAmdId(String amdId) { this.rvisnId = amdId; }

    public void setManagerCd(String managerCd) { this.managerCode = managerCd; }

    public void setAgentCd(String agentCd) { this.agentCode = agentCd; }

    public void setShopCd(String shopCd) { this.shopCd = shopCd; }

    public void setShopNm(String shopNm) { this.shopNm = shopNm; }

    public void setRealShopNm(String realShopNm) { this.realShopNm = realShopNm; }

    public void setCpntId(String cpntId) { this.cpntId = cpntId; }

    public void setCntpntShopCd(String cntpntShopCd) { this.cntpntShopId = cntpntShopCd; }

    public void setReqBuyTypeCd(String reqBuyTypeCd) { this.reqBuyType = reqBuyTypeCd; }

    public void setServiceTypeCd(String serviceTypeCd) { this.serviceType = serviceTypeCd; }

    public void setOperTypeCd(String operTypeCd) { this.operType = operTypeCd; }

    public void setCstmrTypeCd(String cstmrTypeCd) {
        this.cstmrType = cstmrTypeCd;
    }

    public void setFathTrgYn(String fathTrgYn) { this.fathTrgYn = fathTrgYn; }

    public void setFathTransacId(String fathTransacId) { this.fathTransacId = fathTransacId; }

    public void setFathCmpltNtfyDate(String fathCmpltNtfyDate) { this.fathCmpltNtfyDt = fathCmpltNtfyDate; }

    public void setAuthInfo(String authInfo) { this.onlineAuthInfo = authInfo; }

    public void setOpenNo(String openNo) { this.openNo = openNo; }

    public void setContractNum(String contractNum) { this.contractNum = contractNum; }

    public void setProdTypeCd(String prodTypeCd) { this.prodType = prodTypeCd; }

    public void setProdId(String prodId) { this.prodId = prodId; }

    public void setProdNm(String prodNm) { this.prodNm = prodNm; }

    public void setReqPhoneSn(String reqPhoneSn) { this.reqPhoneSn = reqPhoneSn; }

    public void setReqModelNm(String reqModelNm) { this.reqModelName = reqModelNm; }

    public void setSntyCapacCd(String sntyCapacCd) { this.sntyCapacCd = sntyCapacCd; }

    public void setSntyColorCd(String sntyColorCd) { this.sntyColorCd = sntyColorCd; }

    public void setReqModelColor(String reqModelColor) { this.reqModelColor = reqModelColor; }

    public void setShopUsmId(String shopUsmId) { this.shopUsmId = shopUsmId; }

    public void setUsimKindsCd(String usimKindsCd) { this.usimKindsCd = usimKindsCd; }

    public void setReqUsimSn(String reqUsimSn) { this.reqUsimSn = reqUsimSn; }

    public void setReqUsimNm(String reqUsimNm) { this.reqUsimName = reqUsimNm; }

    public void setEid(String eid) { this.eid = eid; }

    public void setImei1(String imei1) { this.imei1 = imei1; }

    public void setImei2(String imei2) { this.imei2 = imei2; }

    public void setEsimPhoneId(String esimPhoneId) { this.esimPhoneId = esimPhoneId; }

    public void setUploadPhoneSrlNo(Long uploadPhoneSrlNo) { this.uploadPhoneSrlNo = uploadPhoneSrlNo; }

    public void setReqWantFnNo(String reqWantFnNo) { this.reqWantNumber = reqWantFnNo; }

    public void setReqWantMnNo(String reqWantMnNo) { this.reqWantNumber2 = reqWantMnNo; }

    public void setReqWantRnNo(String reqWantRnNo) { this.reqWantNumber3 = reqWantRnNo; }

    public void setInsrCd(String insrCd) { this.insrCd = insrCd; }

    public void setInsrProdCd(String insrProdCd) { this.insrProdCd = insrProdCd; }

    public void setClauseInsuranceYn(String clauseInsuranceYn) { this.clauseInsuranceFlag = clauseInsuranceYn; }

    public void setClauseInsrProdYn(String clauseInsrProdYn) { this.clauseInsrProdFlag = clauseInsrProdYn; }

    public void setInsrAuthInfo(String insrAuthInfo) { this.insrAuthInfo = insrAuthInfo; }

    public void setPrntsContractNum(String prntsContractNum) { this.prntsContractNo = prntsContractNum; }

    public void setJehuPartnerTypeCd(String jehuPartnerTypeCd) { this.jehuPartnerType = jehuPartnerTypeCd; }

    public void setJehuProdTypeCd(String jehuProdTypeCd) { this.jehuProdType = jehuProdTypeCd; }

    public void setReqAdditionListNm(String reqAdditionListNm) { this.reqAddition = reqAdditionListNm; }

    public void setReqAdditionPrice(Integer reqAdditionPrice) { this.reqAdditionPrice = reqAdditionPrice; }

    public void setPhonePaymentYn(String phonePaymentYn) { this.phonePayment = phonePaymentYn; }

    public void setReqPayTypeCd(String reqPayTypeCd) { this.reqPayType = reqPayTypeCd; }

    public void setOnOffTypeCd(String onOffTypeCd) { this.onOffType = onOffTypeCd; }

    public void setSoCd(String soCd) { this.soCd = soCd; }

    public void setOpenReqDt(LocalDateTime openReqDt) { this.openReqDate = openReqDt; }

    LocalDateTime getReqInDay() {
        return reqInDay;
    }

    public void setReqInDt(LocalDateTime reqInDt) { this.reqInDay = reqInDt; }

    public void setClausePriCollectYn(String clausePriCollectYn) { this.clausePriCollectFlag = clausePriCollectYn; }

    public void setClausePriOfferYn(String clausePriOfferYn) { this.clausePriOfferFlag = clausePriOfferYn; }

    public void setClauseEssCollectYn(String clauseEssCollectYn) { this.clauseEssCollectFlag = clauseEssCollectYn; }

    public void setClausePriTrustYn(String clausePriTrustYn) { this.clausePriTrustFlag = clausePriTrustYn; }

    public void setClausePriAdYn(String clausePriAdYn) { this.clausePriAdFlag = clausePriAdYn; }

    public void setClauseConfidenceYn(String clauseConfidenceYn) { this.clauseConfidenceFlag = clauseConfidenceYn; }

    public void setClauseFathYn(String clauseFathYn) { this.clauseFathFlag = clauseFathYn; }

    public void setNwBlckAgrmYn(String nwBlckAgrmYn) { this.nwBlckAgrmYn = nwBlckAgrmYn; }

    public void setAppBlckAgrmYn(String appBlckAgrmYn) { this.appBlckAgrmYn = appBlckAgrmYn; }

    public void setBlckAppDivCd(String blckAppDivCd) { this.appCd = blckAppDivCd; }

    public void setSoTrnsAgrmYn(String soTrnsAgrmYn) { this.soTrnsAgrmYn = soTrnsAgrmYn; }

    public void setClauseJehuYn(String clauseJehuYn) { this.clauseJehuFlag = clauseJehuYn; }

    public void setClauseRentalModelCpYn(String clauseRentalModelCpYn) { this.clauseRentalModelCp = clauseRentalModelCpYn; }

    public void setClauseRentalModelCpPrYn(String clauseRentalModelCpPrYn) { this.clauseRentalModelCpPr = clauseRentalModelCpPrYn; }

    public void setClauseRentalServiceYn(String clauseRentalServiceYn) { this.clauseRentalService = clauseRentalServiceYn; }

    public void setClauseMpps35Yn(String clauseMpps35Yn) { this.clauseMpps35Flag = clauseMpps35Yn; }

    public void setClauseFinanceYn(String clauseFinanceYn) { this.clauseFinanceFlag = clauseFinanceYn; }

    public void setClause5gCoverageYn(String clause5gCoverageYn) { this.clause5gCoverageFlag = clause5gCoverageYn; }

    public void setPersonalInfoCollectAgreeYn(String personalInfoCollectAgreeYn) { this.personalInfoCollectAgree = personalInfoCollectAgreeYn; }

    public void setOthersTrnsAgreeYn(String othersTrnsAgreeYn) { this.othersTrnsAgree = othersTrnsAgreeYn; }

    public void setClauseSensiCollectYn(String clauseSensiCollectYn) { this.clauseSensiCollectFlag = clauseSensiCollectYn; }

    public void setClauseSensiOfferYn(String clauseSensiOfferYn) { this.clauseSensiOfferFlag = clauseSensiOfferYn; }

    public void setClausePartnerOfferYn(String clausePartnerOfferYn) { this.clausePartnerOfferFlag = clausePartnerOfferYn; }

    public void setOthersTrnsKtAgreeYn(String othersTrnsKtAgreeYn) { this.othersTrnsKtAgree = othersTrnsKtAgreeYn; }

    public void setOthersAdReceiveAgreeYn(String othersAdReceiveAgreeYn) { this.othersAdReceiveAgree = othersAdReceiveAgreeYn; }

    public void setKtCounselAgreeYn(String ktCounselAgreeYn) { this.ktCounselAgree = ktCounselAgreeYn; }

    public void setCombineSoloTypeYn(String combineSoloTypeYn) { this.combineSoloType = combineSoloTypeYn; }

    public void setCombineSoloYn(String combineSoloYn) { this.combineSoloFlag = combineSoloYn; }

    public void setEtcSpecialSbst(String etcSpecialSbst) { this.etcSpecial = etcSpecialSbst; }

    public void setMemo(String memo) { this.memo = memo; }

    public void setRecYn(String recYn) { this.recYn = recYn; }

    public void setResCd(String resCd) { this.resCode = resCd; }

    public void setResMsg(String resMsg) { this.resMsg = resMsg; }

    public void setResNo(String resNo) { this.resNo = resNo; }

    public void setProSttusCd(String proSttusCd) { this.pstate = proSttusCd; }

    public void setSbscProCd(String sbscProCd) { this.requestStateCode = sbscProCd; }

    public void setParentScanId(String parentScanId) { this.scanId = parentScanId; }

    public void setAppFormYn(String appFormYn) { this.appFormYn = appFormYn; }

    public void setAppFormXmlYn(String appFormXmlYn) { this.appFormXmlYn = appFormXmlYn; }

    public void setFileNm(String fileNm) { this.file01 = fileNm; }

    public void setFileMaskNm(String fileMaskNm) { this.file01Mask = fileMaskNm; }

    public void setFaxYn(String faxYn) { this.faxyn = faxYn; }

    public void setFaxNo(String faxNo) { this.faxnum = faxNo; }

    public void setIndvLocaPrvAgreeYn(String indvLocaPrvAgreeYn) { this.indvLocaPrvAgree = indvLocaPrvAgreeYn; }

    public void setDisPrmtId(String disPrmtId) { this.disPrmtId = disPrmtId; }
}
