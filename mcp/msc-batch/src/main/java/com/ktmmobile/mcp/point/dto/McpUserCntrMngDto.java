package com.ktmmobile.mcp.point.dto;

import java.io.Serializable;

public class McpUserCntrMngDto implements Serializable {

    private static final long serialVersionUID = 1L;


    private String userid;//사용자 web ID
    private String cntrMobileNo;//휴배폰 번호CNTR_MOBILE_NO SUBSCRIBER_NO
    private String sysRdate;//입력일
    private String svcCntrNo;//서비스 계약번호
    private String custId;//고객 ID
    private String soc;//요금제 코드
    private String rateNm;//요금제 이름
    private int baseAmt;//요금제 기본 요금
    private int vatAmt;//요금제 기본 요금 + 부가세
    //private String rmk;//비고
    private int enggMnthCnt;//약정개월수
    private int dcAmt;//할인금액
    private String modelName;//단말기 모델명
    private String modelId;//단말기 모델ID
    private String intmSrlNo;//단말기일련번호
    private String dobyyyymmdd;//생년월일
    private String pointId;//제주항공 회원번호
    private String contractNum;//계약번호
    private String userSSn ; //주민등록 번호
    private String subLinkName; // 실사용자이름
    private String subStatus; // 상태값  SUB_STATUS


    public String getSubLinkName() {
        return subLinkName;
    }
    public void setSubLinkName(String subLinkName) {
        this.subLinkName = subLinkName;
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

    public String getCntrMobileNoMasking() {
        //if (cntrMobileNo == null ) {
            return "";
        //}
        //return MaskingUtil.getMaskedTelNo(StringUtil.getMobileFullNum(cntrMobileNo));
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
    /**
     * @return the rmk
     */
    //public String getRmk() {
    //    String[] tmp = this.rmk.split("===");
    //    return tmp[0];
    //}
    /**
     * @param rmk the rmk to set
     */
    //public void setRmk(String rmk) {
    //    this.rmk = rmk;
    //}
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
        this.intmSrlNo = intmSrlNo;
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
        //try {
            //return NmcpServiceUtils.getSsnDate(EncryptUtil.ace256Dec(userSSn));
        //} catch (CryptoException e) {
            return "" ;
        //}
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





}
