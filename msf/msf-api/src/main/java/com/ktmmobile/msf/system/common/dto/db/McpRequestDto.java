package com.ktmmobile.msf.system.common.dto.db;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestDto.java
 * 날짜     : 2016. 1. 15. 오후 2:28:01
 * 작성자   : papier
 * 설명     : MCP_REQUEST DTO
 * </pre>
 */
public class McpRequestDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;

    /** 업무구분 */
    private String operType;
    private String operTypeNm;
    /** 고객구분 */
    private String cstmrType;
    private String cstmrTypeNm;
    /** 예약등록_코드 */
    private String resCode;
    /** 예약등록_메세지 */
    private String resMsg;
    /** 예약등록_예약번호 */
    private String resNo;
    /** 약관_개인정보_수집_동의 */
    private String clausePriCollectFlag;
    /** 약관_개인정보_제공_동의 */
    private String clausePriOfferFlag;
    /** 약관_고유식별정보_수집이용제공_동의 */
    private String clauseEssCollectFlag;
    /** 약관_개인정보_위탁_동의 */
    private String clausePriTrustFlag;
    /** 약관_신용정보_이용_동의 */
    private String clauseConfidenceFlag;
    /** 제휴_서비스동의 */
    private String clauseJehuFlag;
    /** M PPS 35 제약사항 동의 */
    private String clauseMpps35Flag;
    /** 개인(신용)정보 처리 및 보험가입 동의 동의 */
    private String clauseFinanceFlag;
    /** 개인정보 수집 및 이용 동의 */
    private String personalInfoCollectAgree;
    /** 약관_개인정보_광고전송_동의 */
    private String clausePriAdFlag;
    /** 제 3자 제공 동의 */
    private String othersTrnsAgree;
    /** 혜택 제공을 위한 제3자 제공 동의 : KT */
    private String othersTrnsKtAgree;
    /** 제3자 제공관련 광고 수신 동의 */
    private String othersAdReceiveAgree;


    /** 온라인_인증방식
     * C : 카드 본인 인증
     * X : 범용
     * M : 모바일
     * P : 서식지 인증
     * */
    private String onlineAuthType;
    /** 온라인_인증정보 */
    private String onlineAuthInfo;
    /** 신청서_상태 */
    private String pstate;
    /** 가입진행_코드 */
    private String requestStateCode;
    /** 개통번호 */
    private String openNo;
    /** 파일01 */
    private String file01;
    /** 파일01_마스크 */
    private String file01Mask;
    /** 팩스사용여부 */
    private String faxyn;
    /** 팩스번호 */
    private String faxnum;
    /** 스캐너아이디 */
    private String scanId;
    /** 온라인오프라인구분 */
    private String onOffType;
    /** 등록아이피 */
    private String rip;
    private Date openReqDate;
    /** 등록일시 */
    private Date sysRdate;
    private String reqWantNumber;
    private String reqWantNumber2;
    private String reqWantNumber3;
    private String reqBuyType;
    private String reqModelName;
    private String reqModelColor;
    private String reqPhoneSn;
    private String reqUsimSn;
    private String reqPayType;
    private String reqAddition;
    private String shopCd;
    private String appFormYn;
    /** 가입신청일 */
    private Date reqInDay;
    private String contractNum;
    /** 기타/특약사항 */
    private String etcSpecial;
    /** USIM 모델명  */
    private String reqUsimName;
    /** 휴대폰결제 이용여부 */
    private String phonePayment;
    /** 부가서비스금액 */
    private long reqAdditionPrice;
    private String appFormXmlYn;
    private String spcCode;
    /** 채널점아이디_판매점코드 */
    private String cntpntShopId;
    private String shopUsmId;
    private String memo;
    /** 녹취여부 */
    private String recYn;
    private String openMarketReferer;
    /** 사업자코드(I:KTIS, M:M모바일) */
    private String soCd;
    /** 네트워크차단동의여부 */
    private String nwBlckAgrmYn;
    /** 어플리케이션차단동의여부 */
    private String appBlckAgrmYn;
    /** APP구분코드 */
    private String appCd;

    /** 매니저_코드 */
    private String managerCode;
    /** 대리점_코드 */
    private String agentCode;
    /** 서비스구분 */
    private String serviceType;
    /** 상품아이디 */
    private String prodId ;

    /** 생성자아이디(로그인아이디) */
    private String cretId ;
    /** 배너코드 */
    private String bannerCd ;

    /** 단말기모델아이디_색상검색용*/
    private String sntyColorCd;
    /** 단품용량코드*/
    private String sntyCapacCd ;

    /** 상품 분류 (일반 :01, 0원 상품 :02) */
    private String prodType;
    /** 중고렌탈 프로그램 서비스 이용에 대한 동의서 */
    private String clauseRentalService;
    /** 단말배상금 안내사항 */
    private String clauseRentalModelCp;
    /** 단말배상금(부분파손) 안내사항 */
    private String clauseRentalModelCpPr;
    /** 프로모션코드 */
    private String promotionCd;

    /** 상품명(NMCP_PROD_BAS.PROD_NM) */
    private String prodNm;

    /** DB선택보험*/
    private String insrCd;
    private String clauseInsuranceFlag;
    /** 단말보험 CD */
    private String insrProdCd;
    /** 단말보험가입동의 */
    private String clauseInsrProdFlag;
    /** 단말보험인증정보 */
    private String insrAuthInfo;

    /** 5g 커버리지 확인 및 가입 동의 */
    private String clause5gCoverageFlag;

    /** 최초유입경 인자값 */
    private String ktmReferer;

    /** 유심종류(RCP2035) 06 */
    private String usimKindsCd;


    private String eid ;
    private String imei1 ;
    private String imei2 ;
    private String esimPhoneId ;//--ESIM_PHONE_ID;

    /** MCP_UPLOAD_PHONE_INFO _FK
     * 휴대폰 이미지 등록 EID 등록한 테이블 FK
     *  */
    private long uploadPhoneSrlNo = 0;

    /** 모회선 계약번호*/
    private String prntsContractNo ;

    /** 자급제 보상 서비스 코드 */
    private String rwdProdCd;
    /** 자급제 보상 서비스 가입 동의 */
    private String clauseRwdFlag;
    /** 자급제 보상 서비스 인증 정보 */
    private String rwdAuthInfo;

    /** 평생할인 프로모션 id */
    private String prmtId;

    /** 데이터쉐어링 고객아이디 */
    private String customerId;
    /**  제휴사 제공 동의 */
    private String clausePartnerOfferFlag;
    /**  요금제 제휴처 */
    private String jehuProdType;

    /** 오픈마켓유입아이디 */
    private String openMarketId;

    /** 제휴사 구분 코드 */
    private String jehuPartnerType;

    /** 사은품 - 이벤트 코드 */
    private String evntCdPrmt;
    private String fathTrgYn;       //안면인증 대상여부
    private String clauseFathFlag;  //안면인증 동의여부
    private String fathTransacId;   //안면인증 트랜잭션 ID
    private String fathCmpltNtfyDt;   //안면인증 완료일
    private String cpntId;          //접점 아이디

    /** 아무나 SOLO 결합 신청 여부*/
    private String combineSoloType;

    /** 아무나 SOLO 결합 신청 동의 여부*/
    private String combineSoloFlag;

    public String getInsrCd() {
        return insrCd;
    }
    public void setInsrCd(String insrCd) {
        this.insrCd = insrCd;
    }
    public String getClauseInsuranceFlag() {
        return clauseInsuranceFlag;
    }
    public void setClauseInsuranceFlag(String clauseInsuranceFlag) {
        this.clauseInsuranceFlag = clauseInsuranceFlag;
    }


    public String getOperType() {
        return operType;
    }
    public void setOperType(String operType) {
        this.operType = operType;
    }

    public String getOperTypeNm() {
        return operTypeNm;
    }
    public void setOperTypeNm(String operTypeNm) {
        this.operTypeNm = operTypeNm;
    }
    public String getCstmrTypeNm() {
        return cstmrTypeNm;
    }
    public void setCstmrTypeNm(String cstmrTypeNm) {
        this.cstmrTypeNm = cstmrTypeNm;
    }
    public String getCstmrType() {
        return cstmrType;
    }
    public void setCstmrType(String cstmrType) {
        this.cstmrType = cstmrType;
    }
    public String getResCode() {
        return resCode;
    }
    public void setResCode(String resCode) {
        this.resCode = resCode;
    }
    public String getResMsg() {
        return resMsg;
    }
    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }
    public String getResNo() {
        return resNo;
    }
    public void setResNo(String resNo) {
        this.resNo = resNo;
    }
    public String getClausePriCollectFlag() {
        return clausePriCollectFlag;
    }
    public void setClausePriCollectFlag(String clausePriCollectFlag) {
        this.clausePriCollectFlag = clausePriCollectFlag;
    }
    public String getClausePriOfferFlag() {
        return clausePriOfferFlag;
    }
    public void setClausePriOfferFlag(String clausePriOfferFlag) {
        this.clausePriOfferFlag = clausePriOfferFlag;
    }
    public String getClauseEssCollectFlag() {
        return clauseEssCollectFlag;
    }
    public void setClauseEssCollectFlag(String clauseEssCollectFlag) {
        this.clauseEssCollectFlag = clauseEssCollectFlag;
    }
    public String getClausePriTrustFlag() {
        return clausePriTrustFlag;
    }
    public void setClausePriTrustFlag(String clausePriTrustFlag) {
        this.clausePriTrustFlag = clausePriTrustFlag;
    }
    public String getClausePriAdFlag() {
        return clausePriAdFlag;
    }
    public void setClausePriAdFlag(String clausePriAdFlag) {
        this.clausePriAdFlag = clausePriAdFlag;
    }
    public String getClauseConfidenceFlag() {
        return clauseConfidenceFlag;
    }
    public void setClauseConfidenceFlag(String clauseConfidenceFlag) {
        this.clauseConfidenceFlag = clauseConfidenceFlag;
    }

    public String getClauseJehuFlag() {
        return clauseJehuFlag;
    }
    public void setClauseJehuFlag(String clauseJehuFlag) {
        this.clauseJehuFlag = clauseJehuFlag;
    }
    public String getClauseMpps35Flag() {
        return clauseMpps35Flag;
    }
    public void setClauseMpps35Flag(String clauseMpps35Flag) {
        this.clauseMpps35Flag = clauseMpps35Flag;
    }
    public String getOnlineAuthType() {
        return onlineAuthType;
    }
    public void setOnlineAuthType(String onlineAuthType) {
        this.onlineAuthType = onlineAuthType;
    }
    public String getOnlineAuthInfo() {
        return onlineAuthInfo;
    }
    public void setOnlineAuthInfo(String onlineAuthInfo) {
        this.onlineAuthInfo = onlineAuthInfo;
    }
    public String getPstate() {
        return pstate;
    }
    public void setPstate(String pstate) {
        this.pstate = pstate;
    }
    public String getRequestStateCode() {
        return requestStateCode;
    }
    public void setRequestStateCode(String requestStateCode) {
        this.requestStateCode = requestStateCode;
    }
    public String getOpenNo() {
        return openNo;
    }
    public void setOpenNo(String openNo) {
        this.openNo = openNo;
    }
    public String getFile01() {
        return file01;
    }
    public void setFile01(String file01) {
        this.file01 = file01;
    }
    public String getFile01Mask() {
        return file01Mask;
    }
    public void setFile01Mask(String file01Mask) {
        this.file01Mask = file01Mask;
    }
    public String getFaxyn() {
        return faxyn;
    }
    public void setFaxyn(String faxyn) {
        this.faxyn = faxyn;
    }
    public String getFaxnum() {
        return faxnum;
    }
    public void setFaxnum(String faxnum) {
        this.faxnum = faxnum;
    }
    public String getScanId() {
        return scanId;
    }
    public void setScanId(String scanId) {
        this.scanId = scanId;
    }
    public String getOnOffType() {
        return onOffType;
    }
    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
    }
    public String getRip() {
        return rip;
    }
    public void setRip(String rip) {
        this.rip = rip;
    }
    public Date getOpenReqDate() {
        return openReqDate;
    }
    public void setOpenReqDate(Date openReqDate) {
        this.openReqDate = openReqDate;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getReqWantNumber() {
        return reqWantNumber;
    }
    public void setReqWantNumber(String reqWantNumber) {
        this.reqWantNumber = reqWantNumber;
    }
    public String getReqWantNumber2() {
        return reqWantNumber2;
    }
    public void setReqWantNumber2(String reqWantNumber2) {
        this.reqWantNumber2 = reqWantNumber2;
    }
    public String getReqWantNumber3() {
        return reqWantNumber3;
    }
    public void setReqWantNumber3(String reqWantNumber3) {
        this.reqWantNumber3 = reqWantNumber3;
    }
    public String getReqBuyType() {
        return reqBuyType;
    }
    public void setReqBuyType(String reqBuyType) {
        this.reqBuyType = reqBuyType;
    }
    public String getReqModelName() {
        return reqModelName;
    }
    public void setReqModelName(String reqModelName) {
        this.reqModelName = reqModelName;
    }
    public String getReqModelColor() {
        return reqModelColor;
    }
    public void setReqModelColor(String reqModelColor) {
        this.reqModelColor = reqModelColor;
    }
    public String getReqPhoneSn() {
        return reqPhoneSn;
    }
    public void setReqPhoneSn(String reqPhoneSn) {
        this.reqPhoneSn = reqPhoneSn;
    }
    public String getReqUsimSn() {
        return reqUsimSn;
    }
    public void setReqUsimSn(String reqUsimSn) {
        this.reqUsimSn = reqUsimSn;
    }
    public String getReqPayType() {
        return reqPayType;
    }
    public void setReqPayType(String reqPayType) {
        this.reqPayType = reqPayType;
    }
    public String getReqAddition() {
        return reqAddition;
    }
    public void setReqAddition(String reqAddition) {
        this.reqAddition = reqAddition;
    }
    public String getShopCd() {
        return shopCd;
    }
    public void setShopCd(String shopCd) {
        this.shopCd = shopCd;
    }
    public String getAppFormYn() {
        return appFormYn;
    }
    public void setAppFormYn(String appFormYn) {
        this.appFormYn = appFormYn;
    }
    public Date getReqInDay() {
        return reqInDay;
    }
    public void setReqInDay(Date reqInDay) {
        this.reqInDay = reqInDay;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getEtcSpecial() {
        return etcSpecial;
    }
    public void setEtcSpecial(String etcSpecial) {
        this.etcSpecial = etcSpecial;
    }
    public String getReqUsimName() {
        return reqUsimName;
    }
    public void setReqUsimName(String reqUsimName) {
        this.reqUsimName = reqUsimName;
    }
    public String getPhonePayment() {
        return phonePayment;
    }
    public void setPhonePayment(String phonePayment) {
        this.phonePayment = phonePayment;
    }
    public long getReqAdditionPrice() {
        return reqAdditionPrice;
    }
    public void setReqAdditionPrice(long reqAdditionPrice) {
        this.reqAdditionPrice = reqAdditionPrice;
    }
    public String getAppFormXmlYn() {
        return appFormXmlYn;
    }
    public void setAppFormXmlYn(String appFormXmlYn) {
        this.appFormXmlYn = appFormXmlYn;
    }
    public String getSpcCode() {
        return spcCode;
    }
    public void setSpcCode(String spcCode) {
        this.spcCode = spcCode;
    }
    public String getCntpntShopId() {
        return cntpntShopId;
    }
    public void setCntpntShopId(String cntpntShopId) {
        this.cntpntShopId = cntpntShopId;
    }
    public String getShopUsmId() {
        return shopUsmId;
    }
    public void setShopUsmId(String shopUsmId) {
        this.shopUsmId = shopUsmId;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getRecYn() {
        return recYn;
    }
    public void setRecYn(String recYn) {
        this.recYn = recYn;
    }
    public String getOpenMarketReferer() {
        return openMarketReferer;
    }
    public void setOpenMarketReferer(String openMarketReferer) {
        this.openMarketReferer = openMarketReferer;
    }
    public String getSoCd() {
        return soCd;
    }
    public void setSoCd(String soCd) {
        this.soCd = soCd;
    }
    public String getNwBlckAgrmYn() {
        return nwBlckAgrmYn;
    }
    public void setNwBlckAgrmYn(String nwBlckAgrmYn) {
        this.nwBlckAgrmYn = nwBlckAgrmYn;
    }
    public String getAppBlckAgrmYn() {
        return appBlckAgrmYn;
    }
    public void setAppBlckAgrmYn(String appBlckAgrmYn) {
        this.appBlckAgrmYn = appBlckAgrmYn;
    }
    public String getAppCd() {
        return appCd;
    }
    public void setAppCd(String appCd) {
        this.appCd = appCd;
    }
    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getManagerCode() {
        return managerCode;
    }
    public void setManagerCode(String managerCode) {
        this.managerCode = managerCode;
    }
    public String getAgentCode() {
        return agentCode;
    }
    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }
    public String getServiceType() {
        return serviceType;
    }
    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }
    public String getProdId() {
        return prodId;
    }
    public void setProdId(String prodId) {
        this.prodId = prodId;
    }
    public String getCretId() {
        return cretId;
    }
    public void setCretId(String cretId) {
        this.cretId = cretId;
    }
    public String getBannerCd() {
        return bannerCd;
    }
    public void setBannerCd(String bannerCd) {
        this.bannerCd = bannerCd;
    }
    public String getSntyColorCd() {
        return sntyColorCd;
    }
    public void setSntyColorCd(String sntyColorCd) {
        this.sntyColorCd = sntyColorCd;
    }
    public String getSntyCapacCd() {
        return sntyCapacCd;
    }
    public void setSntyCapacCd(String sntyCapacCd) {
        this.sntyCapacCd = sntyCapacCd;
    }
    public String getProdType() {
        return prodType;
    }
    public void setProdType(String prodType) {
        this.prodType = prodType;
    }
    public String getClauseRentalService() {
        return clauseRentalService;
    }
    public void setClauseRentalService(String clauseRentalService) {
        this.clauseRentalService = clauseRentalService;
    }
    public String getClauseRentalModelCp() {
        return clauseRentalModelCp;
    }
    public void setClauseRentalModelCp(String clauseRentalModelCp) {
        this.clauseRentalModelCp = clauseRentalModelCp;
    }
    public String getClauseRentalModelCpPr() {
        return clauseRentalModelCpPr;
    }
    public void setClauseRentalModelCpPr(String clauseRentalModelCpPr) {
        this.clauseRentalModelCpPr = clauseRentalModelCpPr;
    }
    public String getClauseFinanceFlag() {
        return clauseFinanceFlag;
    }
    public void setClauseFinanceFlag(String clauseFinanceFlag) {
        this.clauseFinanceFlag = clauseFinanceFlag;
    }
    public String getPromotionCd() {
        return promotionCd;
    }
    public void setPromotionCd(String promotionCd) {
        this.promotionCd = promotionCd;
    }
    public String getProdNm() {
        return prodNm;
    }
    public void setProdNm(String prodNm) {
        this.prodNm = prodNm;
    }
    public String getInsrProdCd() {
        return insrProdCd;
    }
    public void setInsrProdCd(String insrProdCd) {
        this.insrProdCd = insrProdCd;
    }
    public String getClauseInsrProdFlag() {
        return clauseInsrProdFlag;
    }
    public void setClauseInsrProdFlag(String clauseInsrProdFlag) {
        this.clauseInsrProdFlag = clauseInsrProdFlag;
    }
    public String getInsrAuthInfo() {
        return insrAuthInfo;
    }
    public void setInsrAuthInfo(String insrAuthInfo) {
        this.insrAuthInfo = insrAuthInfo;
    }
    public String getClause5gCoverageFlag() {
        return clause5gCoverageFlag;
    }
    public void setClause5gCoverageFlag(String clause5gCoverageFlag) {
        this.clause5gCoverageFlag = clause5gCoverageFlag;
    }
    public String getKtmReferer() {
        return ktmReferer;
    }
    public void setKtmReferer(String ktmReferer) {
        this.ktmReferer = ktmReferer;
    }
    public String getUsimKindsCd() {
        return usimKindsCd;
    }
    public void setUsimKindsCd(String usimKindsCd) {
        this.usimKindsCd = usimKindsCd;
    }
    public String getEid() {
        return eid;
    }
    public void setEid(String eid) {
        this.eid = eid;
    }
    public String getImei1() {
        return imei1;
    }
    public void setImei1(String imei1) {
        this.imei1 = imei1;
    }
    public String getImei2() {
        return imei2;
    }
    public void setImei2(String imei2) {
        this.imei2 = imei2;
    }
    public long getUploadPhoneSrlNo() {
        return uploadPhoneSrlNo;
    }
    public void setUploadPhoneSrlNo(long uploadPhoneSrlNo) {
        this.uploadPhoneSrlNo = uploadPhoneSrlNo;
    }
    public String getEsimPhoneId() {
        return esimPhoneId;
    }
    public void setEsimPhoneId(String esimPhoneId) {
        this.esimPhoneId = esimPhoneId;
    }


    public String getPrntsContractNo() {
        return prntsContractNo;
    }

    public void setPrntsContractNo(String prntsContractNo) {
        this.prntsContractNo = prntsContractNo;
    }

    public String getRwdProdCd() {
        return rwdProdCd;
    }

    public void setRwdProdCd(String rwdProdCd) {
        this.rwdProdCd = rwdProdCd;
    }

    public String getClauseRwdFlag() {
        return clauseRwdFlag;
    }

    public void setClauseRwdFlag(String clauseRwdFlag) {
        this.clauseRwdFlag = clauseRwdFlag;
    }

    public String getRwdAuthInfo() {
        return rwdAuthInfo;
    }

    public void setRwdAuthInfo(String rwdAuthInfo) {
        this.rwdAuthInfo = rwdAuthInfo;
    }

    public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) {
        this.personalInfoCollectAgree = personalInfoCollectAgree;
    }

    public String getPersonalInfoCollectAgree() {
        return personalInfoCollectAgree;
    }

    public void setOthersTrnsAgree(String othersTrnsAgree) {
        this.othersTrnsAgree = othersTrnsAgree;
    }

    public String getOthersTrnsAgree() {
        return othersTrnsAgree;
    }

    public String getOthersTrnsKtAgree() {
        return othersTrnsKtAgree;
    }

    public void setOthersTrnsKtAgree(String othersTrnsKtAgree) {
        this.othersTrnsKtAgree = othersTrnsKtAgree;
    }

    public String getOthersAdReceiveAgree() {
        return othersAdReceiveAgree;
    }

    public void setOthersAdReceiveAgree(String othersAdReceiveAgree) {
        this.othersAdReceiveAgree = othersAdReceiveAgree;
    }

    public String getPrmtId() {
        return prmtId;
    }

    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getClausePartnerOfferFlag() {
        return clausePartnerOfferFlag;
    }

    public void setClausePartnerOfferFlag(String clausePartnerOfferFlag) {
        this.clausePartnerOfferFlag = clausePartnerOfferFlag;
    }

    public String getJehuProdType() {
        return jehuProdType;
    }

    public void setJehuProdType(String jehuProdType) {
        this.jehuProdType = jehuProdType;
    }

    public String getOpenMarketId() {
        return openMarketId;
    }

    public void setOpenMarketId(String openMarketId) {
        this.openMarketId = openMarketId;
    }

    public String getJehuPartnerType() {
        return jehuPartnerType;
    }

    public void setJehuPartnerType(String jehuPartnerType) {
        this.jehuPartnerType = jehuPartnerType;
    }

    public String getEvntCdPrmt() {
        return evntCdPrmt;
    }

    public void setEvntCdPrmt(String evntCdPrmt) {
        this.evntCdPrmt = evntCdPrmt;
    }

    public String getFathTrgYn() {
        return fathTrgYn;
    }

    public void setFathTrgYn(String fathTrgYn) {
        this.fathTrgYn = fathTrgYn;
    }

    public String getClauseFathFlag() {
        return clauseFathFlag;
    }

    public void setClauseFathFlag(String clauseFathFlag) {
        this.clauseFathFlag = clauseFathFlag;
    }

    public String getFathTransacId() {
        return fathTransacId;
    }

    public void setFathTransacId(String fathTransacId) {
        this.fathTransacId = fathTransacId;
    }

    public String getFathCmpltNtfyDt() {
        return fathCmpltNtfyDt;
    }

    public void setFathCmpltNtfyDt(String fathCmpltNtfyDt) {
        this.fathCmpltNtfyDt = fathCmpltNtfyDt;
    }

    public String getCpntId() {
        return cpntId;
    }

    public void setCpntId(String cpntId) {
        this.cpntId = cpntId;
    }

    public String getCombineSoloType() {
        return combineSoloType;
    }

    public void setCombineSoloType(String combineSoloType) {
        this.combineSoloType = combineSoloType;
    }

    public String getCombineSoloFlag() {
        return combineSoloFlag;
    }

    public void setCombineSoloFlag(String combineSoloFlag) {
        this.combineSoloFlag = combineSoloFlag;
    }
}
