package com.ktmmobile.mcp.content.dto;

import java.util.Date;

public class KtDcDto {

    private String rateCd;	//요금제 코드
    private String rateNm; //부가서비스명
    private String ncn; // 사용자 계약번호
    private String ctn; // 사용자 전화번호
    private String custId; //고객ID
    private String prcsMdlInd; //처리모듈구분
    private String KtInternetId; //kt인터넷ID
    private String soc; // soc 코드
    private String contractNum; // 계약번호
    private String baseAmt; //할인금액
    private String additionCd; //혜택(부가서비스)코드
    private String installDd; //인터넷설치일지
    private String appRoute; //신청경로
    private String cretDd; //신청일
    private String cretId; //생성아이디
    private String amdId; //수정아이디
    private Date cretDt; //신청일시
    private Date amdDt; //수정일시
    private String rip; //등록아이피
    private String combYn; //mvno회선정보/결합대상  값
    private String succYn; //성공/실패 여부 (Y/N)
    private String reasonFail; //실패사유

    /* 실패시 고객센터에서 처리 */
    private String procYn; //처리여부(Y/N)
    private String procId; //처리자ID
    private String procDt; //처리일시

    public String getRateCd() {
        return rateCd;
    }

    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }

    public String getRateNm() {
        return rateNm;
    }

    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public String getNcn() {
        return ncn;
    }

    public void setNcn(String ncn) {
        this.ncn = ncn;
    }

    public String getCtn() {
        return ctn;
    }

    public void setCtn(String ctn) {
        this.ctn = ctn;
    }

    public String getCustId() {
        return custId;
    }

    public void setCustId(String custId) {
        this.custId = custId;
    }

    public String getPrcsMdlInd() {
        return prcsMdlInd;
    }

    public void setPrcsMdlInd(String prcsMdlInd) {
        this.prcsMdlInd = prcsMdlInd;
    }


    public String getKtInternetId() {
        return KtInternetId;
    }

    public void setKtInternetId(String ktInternetId) {
        KtInternetId = ktInternetId;
    }

    public String getSoc() {
        return soc;
    }

    public void setSoc(String soc) {
        this.soc = soc;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getBaseAmt() {
        return baseAmt;
    }

    public void setBaseAmt(String baseAmt) {
        this.baseAmt = baseAmt;
    }

    public String getAdditionCd() {
        return additionCd;
    }

    public void setAdditionCd(String additionCd) {
        this.additionCd = additionCd;
    }

    public String getInstallDd() {
        return installDd;
    }

    public void setInstallDd(String installDd) {
        this.installDd = installDd;
    }

    public String getAppRoute() {
        return appRoute;
    }

    public void setAppRoute(String appRoute) {
        this.appRoute = appRoute;
    }

    public String getCretDd() {
        return cretDd;
    }

    public void setCretDd(String cretDd) {
        this.cretDd = cretDd;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public Date getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Date amdDt) {
        this.amdDt = amdDt;
    }

    public String getRip() {
        return rip;
    }

    public void setRip(String rip) {
        this.rip = rip;
    }

    public String getCombYn() {
        return combYn;
    }

    public void setCombYn(String combYn) {
        this.combYn = combYn;
    }

    public String getSuccYn() {
        return succYn;
    }

    public void setSuccYn(String succYn) {
        this.succYn = succYn;
    }

    public String getReasonFail() {
        return reasonFail;
    }

    public void setReasonFail(String reasonFail) {
        this.reasonFail = reasonFail;
    }

    public String getProcYn() {
        return procYn;
    }

    public void setProcYn(String procYn) {
        this.procYn = procYn;
    }

    public String getProcId() {
        return procId;
    }

    public void setProcId(String procId) {
        this.procId = procId;
    }

    public String getProcDt() {
        return procDt;
    }

    public void setProcDt(String procDt) {
        this.procDt = procDt;
    }
}
