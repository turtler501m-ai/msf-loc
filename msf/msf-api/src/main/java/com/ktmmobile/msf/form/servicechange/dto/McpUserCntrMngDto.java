package com.ktmmobile.msf.form.servicechange.dto;

import java.io.Serializable;
import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.MaskingUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;

public class McpUserCntrMngDto implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String formatUnSvcNo;
    private String lstComActvDate; // 최초개통일자

    private String pppo; //후불선불

    private String prmtId;
    private String remindYn; //리마인드 문자 수신/차단 대상
    private String remindProdType;//리마인드 문자 조회할 상품구분값(MI: 밀리의 서재 , CU: 씨유)

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

    private String banAdrZip;
    private String banAdrPrimaryLn;
    private String banAdrSecondaryLn;

    private String subscriberNo;	//고객휴대폰번호

    public String getUnIntmSrlNo() {
        return unIntmSrlNo;
    }
    public void setUnIntmSrlNo(String unIntmSrlNo) {
        this.unIntmSrlNo = unIntmSrlNo;
    }
    public String getColDelinqStatus() {
        return colDelinqStatus;
    }
    public void setColDelinqStatus(String colDelinqStatus) {
        this.colDelinqStatus = colDelinqStatus;
    }
    public String getOnOffType() {
        return onOffType;
    }
    public void setOnOffType(String onOffType) {
        this.onOffType = onOffType;
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
    public String getOpenAgntCd() {
        return openAgntCd;
    }
    public void setOpenAgntCd(String openAgntCd) {
        this.openAgntCd = openAgntCd;
    }
    public String getBirth() {
        return birth;
    }
    public void setBirth(String birth) {
        this.birth = birth;
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
    public String getAge() {
        return age;
    }
    public void setAge(String age) {
        this.age = age;
    }

    public String getRetvGubunCd() {
        return retvGubunCd;
    }
    public void setRetvGubunCd(String retvGubunCd) {
        this.retvGubunCd = retvGubunCd;
    }
    public String getSubLinkName() {
        return subLinkName;
    }
    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
        this.mkNm = MaskingUtil.getMaskedName(this.subLinkName);
    }
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
        this.mkId = MaskingUtil.getMaskedId(userid);
    }
    public String getCntrMobileNo() {
        return cntrMobileNo;
    }
    public void setCntrMobileNo(String cntrMobileNo) {
        this.cntrMobileNo = cntrMobileNo;
        // this.mkMobileNo = MaskingUtil.getMaskedValue(cntrMobileNo,5,8);
        this.mkMobileNo= MaskingUtil.getMaskedTelNo(cntrMobileNo);
    }

    public String getCntrMobileNoMasking() {
        if (cntrMobileNo == null ) {
            return "";
        }
        return MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(cntrMobileNo));
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
    /**
     * @return the soc
     */
    public String getSoc() {
        return soc;
    }
    /**
     * @param soc the soc to set
     */
    public void setSoc(String soc) {
        this.soc = soc;
    }
    /**
     * @return the rateNm
     */
    public String getRateNm() {
        return rateNm;
    }
    /**
     * @param rateNm the rateNm to set
     */
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }
    /**
     * @return the baseAmt
     */
    public int getBaseAmt() {
        return baseAmt;
    }
    /**
     * @param baseAmt the baseAmt to set
     */
    public void setBaseAmt(int baseAmt) {
        this.baseAmt = baseAmt;
    }
    /**
     * @return the vatAmt
     */
    public int getVatAmt() {
        vatAmt = baseAmt + (int)(baseAmt * 0.1);
        return vatAmt;
    }
    /**
     * @param vatAmt the vatAmt to set
     */
    public void setVatAmt(int vatAmt) {
        this.vatAmt = vatAmt;
    }


    public String getRmk() {
        return rmk;
    }
    public void setRmk(String rmk) {
        this.rmk = rmk;
    }
    /**
     * @return the enggMnthCnt
     */
    public int getEnggMnthCnt() {
        return enggMnthCnt;
    }
    /**
     * @param enggMnthCnt the enggMnthCnt to set
     */
    public void setEnggMnthCnt(int enggMnthCnt) {
        this.enggMnthCnt = enggMnthCnt;
    }
    /**
     * @return the dcAmt
     */
    public int getDcAmt() {
        return dcAmt;
    }
    /**
     * @param dcAmt the dcAmt to set
     */
    public void setDcAmt(int dcAmt) {
        this.dcAmt = dcAmt;
    }
    /**
     * @return the modelName
     */
    public String getModelName() {
        return modelName;
    }
    /**
     * @param modelName the modelName to set
     */
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    /**
     * @return the dobyyyymmdd
     */
    public String getDobyyyymmdd() {
        return dobyyyymmdd;
    }
    /**
     * @param dobyyyymmdd the dobyyyymmdd to set
     */
    public void setDobyyyymmdd(String dobyyyymmdd) {
        this.dobyyyymmdd = dobyyyymmdd;
    }
    /**
     * @return the intmSrlNo
     */
    public String getIntmSrlNo() {
        return intmSrlNo;
    }
    /**
     * @param intmSrlNo the intmSrlNo to set
     */
    public void setIntmSrlNo(String intmSrlNo) {
        // this.intmSrlNo = intmSrlNo;
        // 일련번호 마스킹 적용 2022.10.05
        this.intmSrlNo = MaskingUtil.getMaskedBySerialNumber(intmSrlNo);
    }
    /**
     * @return the modelId
     */
    public String getModelId() {
        return modelId;
    }
    /**
     * @param modelId the modelId to set
     */
    public void setModelId(String modelId) {
        this.modelId = modelId;
    }
    /**
     * @return the pointId
     */
    public String getPointId() {
        return pointId;
    }
    /**
     * @param pointId the pointId to set
     */
    public void setPointId(String pointId) {
        this.pointId = pointId;
    }
    /**
     * @return the contractNum
     */
    public String getContractNum() {
        return contractNum;
    }
    /**
     * @param contractNum the contractNum to set
     */
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }
    public String getUserSSn() {
        try {
            return NmcpServiceUtils.getSsnDate(EncryptUtil.ace256Dec(userSSn));
        } catch (CryptoException e) {
            return "" ;
        }
    }
    public void setUserSSn(String userSSn) {
        this.userSSn = userSSn;
    }
    public String getSubStatus() {
        return subStatus;
    }
    public void setSubStatus(String subStatus) {
        this.subStatus = subStatus;
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
    public int getPromotionDcAmt() {
        return promotionDcAmt;
    }
    public void setPromotionDcAmt(int promotionDcAmt) {
        this.promotionDcAmt = promotionDcAmt;
    }
    public String getMkMobileNo() {
        return mkMobileNo;
    }
    public void setMkMobileNo(String mkMobileNo) {
        this.mkMobileNo = mkMobileNo;
    }


    /** @Description :
     * 월요금
     */
     public int getInstMnthAmt() {
         int rtnInt = getVatAmt()  - getPromotionDcAmt();
         if (rtnInt > 0) {
             return rtnInt;
         } else {
             return 0 ;
         }

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
        if(unSvcNo == null) {
            return "";
        }
        return unSvcNo;
    }
    public void setUnSvcNo(String unSvcNo) {
        this.unSvcNo = unSvcNo;
    }
    public String getUnUserSSn() {
        if (unUserSSn == null ) {
            return "";
        }
        return unUserSSn;
    }
    public void setUnUserSSn(String unUserSSn) {
        if (StringUtil.isBlank(unUserSSn)) {
            this.unUserSSn = "";
        } else {
            if (StringUtil.isNumeric(unUserSSn)) {
                this.unUserSSn = EncryptUtil.ace256Enc(unUserSSn);
            } else {
                try {
                    this.unUserSSn =  EncryptUtil.ace256Dec(unUserSSn);
                } catch (CryptoException e) {
                    this.unUserSSn = unUserSSn;
                }
            }

        }
    }
    public String getCustomerType() {
        return customerType;
    }
    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getLstComActvDate() {
        return lstComActvDate;
    }
    public void setLstComActvDate(String lstComActvDate) {
        this.lstComActvDate = lstComActvDate;
    }

    public String getFormatUnSvcNo() {
        return formatUnSvcNo;
    }
    public void setFormatUnSvcNo(String formatUnSvcNo) {
        this.formatUnSvcNo = formatUnSvcNo;
    }
    public String getPppo() {return pppo; }
    public void setPppo(String pppo) {this.pppo = pppo;}

    public String getRemindYn() {
        return remindYn;
    }
    public void setRemindYn(String remindYn) {
        this.remindYn = remindYn;
    }
    public String getRemindProdType() {
        return remindProdType;
    }
    public void setRemindProdType(String remindProdType) {
        this.remindProdType = remindProdType;
    }

    public String getPrmtId() {
        return prmtId;
    }
    public void setPrmtId(String prmtId) {
        this.prmtId = prmtId;
    }

    public String getBanAdrZip() {
        return banAdrZip;
    }

    public void setBanAdrZip(String banAdrZip) {
        this.banAdrZip = banAdrZip;
    }

    public String getBanAdrPrimaryLn() {
        return banAdrPrimaryLn;
    }

    public void setBanAdrPrimaryLn(String banAdrPrimaryLn) {
        this.banAdrPrimaryLn = banAdrPrimaryLn;
    }

    public String getBanAdrSecondaryLn() {
        return banAdrSecondaryLn;
    }

    public void setBanAdrSecondaryLn(String banAdrSecondaryLn) {
        this.banAdrSecondaryLn = banAdrSecondaryLn;
    }

    public String getSubscriberNo() {
        return subscriberNo;
    }

    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
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
