package com.ktmmobile.mcp.rate.dto;

import java.io.Serializable;

public class McpUserCntrMngDto implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -1037188916390123245L;
	
	private String unUserSSn;
    private String userid;//사용자 web ID
    private String cntrMobileNo;//휴배폰 번호CNTR_MOBILE_NO SUBSCRIBER_NO
    private String sysRdate;//입력일
    private String svcCntrNo;//서비스 계약번호
    private String custId;//고객 ID
    private String soc;//요금제 코드
    private String rateNm;//요금제 이름
    private int baseAmt;//요금제 기본 요금
    private int vatAmt;//요금제 기본 요금 + 부가세
    private String rmk;//비고
    private int enggMnthCnt;//약정개월수
    private int dcAmt;//할인금액
    private String modelName;//단말기 모델명
    private String modelId;//단말기 모델ID
    private String intmSrlNo;//단말기일련번호
    private String unIntmSrlNo;//단말기일련번호 마스킹 안된값
    private String dobyyyymmdd;//생년월일
    private String pointId;//제주항공 회원번호
    private String contractNum;//계약번호
    private String userSSn ; //주민등록 번호
    private String subLinkName; // 실사용자이름
    private String subStatus; // 상태값  SUB_STATUS
    private String retvGubunCd; // 주기회선인지 받기 회선인지 구분
    private String mkMobileNo;
    private String mkNm;
    private String mkId;
    private String unSvcNo;
    private String lstComActvDate; // 최초개통일자

    private String pppo; //후불선불

    /** 요금제 할인 금액 */
    private int promotionDcAmt;

    /** 요금제코드 */
    private String socCode;

    /** soc 명 */
    private String socNm;

    /** soc 가격 SOC_PRICE*/
    private String socPrice;

    private String customerId;
    private String customerType;

    private String age;
    private String userName; //고객명
    private String cstmrType; //고객유형
    private String birth;
    private String openAgntCd; //개통대리점

    private String freeDataCnt; //무료데이터사용량
    private String freeCallCnt; //무료통화사용량
    private String freeSmsCnt; //무료문자사용량
    private String onOffType;
    private String colDelinqStatus; //미납여부

    private String prmtId; //평생할인프로모션ID

    public String getUnUserSSn() {
		return unUserSSn;
	}


	public void setUnUserSSn(String unUserSSn) {
		this.unUserSSn = unUserSSn;
	}


	public String getUserid() {
		return userid;
	}


	public void setUserid(String userid) {
		this.userid = userid;
	}


	public String getCntrMobileNo() {
		return cntrMobileNo;
	}


	public void setCntrMobileNo(String cntrMobileNo) {
		this.cntrMobileNo = cntrMobileNo;
	}


	public String getSysRdate() {
		return sysRdate;
	}


	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}


	public String getSvcCntrNo() {
		return svcCntrNo;
	}


	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}


	public String getCustId() {
		return custId;
	}


	public void setCustId(String custId) {
		this.custId = custId;
	}


	public String getSoc() {
		return soc;
	}


	public void setSoc(String soc) {
		this.soc = soc;
	}


	public String getRateNm() {
		return rateNm;
	}


	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}


	public int getBaseAmt() {
		return baseAmt;
	}


	public void setBaseAmt(int baseAmt) {
		this.baseAmt = baseAmt;
	}


	public int getVatAmt() {
		return vatAmt;
	}


	public void setVatAmt(int vatAmt) {
		this.vatAmt = vatAmt;
	}


	public String getRmk() {
		return rmk;
	}


	public void setRmk(String rmk) {
		this.rmk = rmk;
	}


	public int getEnggMnthCnt() {
		return enggMnthCnt;
	}


	public void setEnggMnthCnt(int enggMnthCnt) {
		this.enggMnthCnt = enggMnthCnt;
	}


	public int getDcAmt() {
		return dcAmt;
	}


	public void setDcAmt(int dcAmt) {
		this.dcAmt = dcAmt;
	}


	public String getModelName() {
		return modelName;
	}


	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


	public String getModelId() {
		return modelId;
	}


	public void setModelId(String modelId) {
		this.modelId = modelId;
	}


	public String getIntmSrlNo() {
		return intmSrlNo;
	}


	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}


	public String getUnIntmSrlNo() {
		return unIntmSrlNo;
	}


	public void setUnIntmSrlNo(String unIntmSrlNo) {
		this.unIntmSrlNo = unIntmSrlNo;
	}


	public String getDobyyyymmdd() {
		return dobyyyymmdd;
	}


	public void setDobyyyymmdd(String dobyyyymmdd) {
		this.dobyyyymmdd = dobyyyymmdd;
	}


	public String getPointId() {
		return pointId;
	}


	public void setPointId(String pointId) {
		this.pointId = pointId;
	}


	public String getContractNum() {
		return contractNum;
	}


	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}


	public String getUserSSn() {
		return userSSn;
	}


	public void setUserSSn(String userSSn) {
		this.userSSn = userSSn;
	}


	public String getSubLinkName() {
		return subLinkName;
	}


	public void setSubLinkName(String subLinkName) {
		this.subLinkName = subLinkName;
	}


	public String getSubStatus() {
		return subStatus;
	}


	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}


	public String getRetvGubunCd() {
		return retvGubunCd;
	}


	public void setRetvGubunCd(String retvGubunCd) {
		this.retvGubunCd = retvGubunCd;
	}


	public String getMkMobileNo() {
		return mkMobileNo;
	}


	public void setMkMobileNo(String mkMobileNo) {
		this.mkMobileNo = mkMobileNo;
	}


	public String getMkNm() {
		return mkNm;
	}


	public void setMkNm(String mkNm) {
		this.mkNm = mkNm;
	}


	public String getMkId() {
		return mkId;
	}


	public void setMkId(String mkId) {
		this.mkId = mkId;
	}


	public String getUnSvcNo() {
		return unSvcNo;
	}


	public void setUnSvcNo(String unSvcNo) {
		this.unSvcNo = unSvcNo;
	}


	public String getLstComActvDate() {
		return lstComActvDate;
	}


	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}


	public String getPppo() {
		return pppo;
	}


	public void setPppo(String pppo) {
		this.pppo = pppo;
	}


	public int getPromotionDcAmt() {
		return promotionDcAmt;
	}


	public void setPromotionDcAmt(int promotionDcAmt) {
		this.promotionDcAmt = promotionDcAmt;
	}


	public String getSocCode() {
		return socCode;
	}


	public void setSocCode(String socCode) {
		this.socCode = socCode;
	}


	public String getSocNm() {
		return socNm;
	}


	public void setSocNm(String socNm) {
		this.socNm = socNm;
	}


	public String getSocPrice() {
		return socPrice;
	}


	public void setSocPrice(String socPrice) {
		this.socPrice = socPrice;
	}


	public String getCustomerId() {
		return customerId;
	}


	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}


	public String getCustomerType() {
		return customerType;
	}


	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}


	public String getAge() {
		return age;
	}


	public void setAge(String age) {
		this.age = age;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getCstmrType() {
		return cstmrType;
	}


	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}


	public String getBirth() {
		return birth;
	}


	public void setBirth(String birth) {
		this.birth = birth;
	}


	public String getOpenAgntCd() {
		return openAgntCd;
	}


	public void setOpenAgntCd(String openAgntCd) {
		this.openAgntCd = openAgntCd;
	}


	public String getFreeDataCnt() {
		return freeDataCnt;
	}


	public void setFreeDataCnt(String freeDataCnt) {
		this.freeDataCnt = freeDataCnt;
	}


	public String getFreeCallCnt() {
		return freeCallCnt;
	}


	public void setFreeCallCnt(String freeCallCnt) {
		this.freeCallCnt = freeCallCnt;
	}


	public String getFreeSmsCnt() {
		return freeSmsCnt;
	}


	public void setFreeSmsCnt(String freeSmsCnt) {
		this.freeSmsCnt = freeSmsCnt;
	}


	public String getOnOffType() {
		return onOffType;
	}


	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}


	public String getColDelinqStatus() {
		return colDelinqStatus;
	}

	public void setColDelinqStatus(String colDelinqStatus) {
		this.colDelinqStatus = colDelinqStatus;
	}
	
	public String getPrmtId() {
		return prmtId;
	}

	public void setPrmtId(String prmtId) {
		this.prmtId = prmtId;
	}

	@Override
    public String toString() {
        return "McpUserCntrMngDto{" +"unUserSSn='" + unUserSSn + '\'' +", userid='" + userid + '\'' +", cntrMobileNo='" + cntrMobileNo + '\'' +
                ", sysRdate='" + sysRdate + '\'' +", svcCntrNo='" + svcCntrNo + '\'' +", custId='" + custId + '\'' +", soc='" + soc + '\'' +
                ", rateNm='" + rateNm + '\'' +", baseAmt=" + baseAmt +", vatAmt=" + vatAmt +", rmk='" + rmk + '\'' +", enggMnthCnt=" + enggMnthCnt +
                ", dcAmt=" + dcAmt +", modelName='" + modelName + '\'' +", modelId='" + modelId + '\'' +", intmSrlNo='" + intmSrlNo + '\'' +
                ", unIntmSrlNo='" + unIntmSrlNo + '\'' +", dobyyyymmdd='" + dobyyyymmdd + '\'' +", pointId='" + pointId + '\'' +
                ", contractNum='" + contractNum + '\'' +", userSSn='" + userSSn + '\'' +", subLinkName='" + subLinkName + '\'' +
                ", subStatus='" + subStatus + '\'' +", retvGubunCd='" + retvGubunCd + '\'' +", mkMobileNo='" + mkMobileNo + '\'' +
                ", mkNm='" + mkNm + '\'' +", mkId='" + mkId + '\'' +", unSvcNo='" + unSvcNo + '\'' +", lstComActvDate='" + lstComActvDate + '\'' +
                ", pppo='" + pppo + '\'' +", promotionDcAmt=" + promotionDcAmt +", socCode='" + socCode + '\'' +", socNm='" + socNm + '\'' +
                ", socPrice='" + socPrice + '\'' +", customerId='" + customerId + '\'' +", customerType='" + customerType + '\'' +", age='" + age + '\'' +
                ", userName='" + userName + '\'' +", cstmrType='" + cstmrType + '\'' +", birth='" + birth + '\'' +", openAgntCd='" + openAgntCd + '\'' +
                ", freeDataCnt='" + freeDataCnt + '\'' +", freeCallCnt='" + freeCallCnt + '\'' +", freeSmsCnt='" + freeSmsCnt + '\'' +
                ", onOffType='" + onOffType + '\'' +", colDelinqStatus='" + colDelinqStatus + '\'' +'}';
    }
}
