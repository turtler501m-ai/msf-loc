package com.ktmmobile.mcp.cs.dto;

import java.util.Date;
import java.io.Serializable;

public class NflChgTrnsDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String trnsSeq;					//명의변경일련번호
    private int trnsApyNo;					//명의변경신청번호
    private String trnsNm;					//양도인 이름
    private String trnsMobileNo;			//명의변경할 휴대폰번호
    private String trnsPhoneNo;				//양도인 연락처
    private String trnsPass;				//명의변경용 비밀번호
    private String trnsMyslfConfMeth;		//양도인 본인확인방법
    private String trnsResidRegIssGovof;	//양도인 주민증 발급처
    private String trnsResidRegIssDate;		//양도인 주민증 발급일
    private String trnsDriveLicnsNo;		//양도인 운전면허 발급번호
    private String trnsDriveLicnsIssDate;	//양도인 운전면허 발급일
    private String trnsTrnsfeNm;			//양도인이 입력한 양수인 이름
    private String trnsTrnsfeMobileNo;		//양도인이 입력한 양수인 휴대폰번호
    private String cretId;					//생성자아이디
    private String amdId;					//수정자아이디
    private Date cretDt;					//생성일시
    private Date amdDt;						//수정일시
    private String trnsStatusVal;			//명의변경신청 상태값(1:대기중, 2:신청완료, 3:처리완료, 4:부재중, 5:기한초과)
    private String authNum;					//sms인증번호
    private String authValue;				//사용자가 입력한 인증번호
    private String timer0;					//sms인증 3분제한이 지나면 "0"으로 셋팅됨

    public String getTrnsSeq() {
        return trnsSeq;
    }
    public void setTrnsSeq(String trnsSeq) {
        this.trnsSeq = trnsSeq;
    }
    public int getTrnsApyNo() {
        return trnsApyNo;
    }
    public void setTrnsApyNo(int trnsApyNo) {
        this.trnsApyNo = trnsApyNo;
    }
    public String getTrnsNm() {
        return trnsNm;
    }
    public void setTrnsNm(String trnsNm) {
        this.trnsNm = trnsNm;
    }
    public String getTrnsMobileNo() {
        return trnsMobileNo;
    }
    public void setTrnsMobileNo(String trnsMobileNo) {
        this.trnsMobileNo = trnsMobileNo;
    }
    public String getTrnsPhoneNo() {
        return trnsPhoneNo;
    }
    public void setTrnsPhoneNo(String trnsPhoneNo) {
        this.trnsPhoneNo = trnsPhoneNo;
    }
    public String getTrnsPass() {
        return trnsPass;
    }
    public void setTrnsPass(String trnsPass) {
        this.trnsPass = trnsPass;
    }
    public String getTrnsMyslfConfMeth() {
        return trnsMyslfConfMeth;
    }
    public void setTrnsMyslfConfMeth(String trnsMyslfConfMeth) {
        this.trnsMyslfConfMeth = trnsMyslfConfMeth;
    }
    public String getTrnsResidRegIssGovof() {
        return trnsResidRegIssGovof;
    }
    public void setTrnsResidRegIssGovof(String trnsResidRegIssGovof) {
        this.trnsResidRegIssGovof = trnsResidRegIssGovof;
    }
    public String getTrnsResidRegIssDate() {
        return trnsResidRegIssDate;
    }
    public void setTrnsResidRegIssDate(String trnsResidRegIssDate) {
        this.trnsResidRegIssDate = trnsResidRegIssDate;
    }
    public String getTrnsDriveLicnsNo() {
        return trnsDriveLicnsNo;
    }
    public void setTrnsDriveLicnsNo(String trnsDriveLicnsNo) {
        this.trnsDriveLicnsNo = trnsDriveLicnsNo;
    }
    public String getTrnsDriveLicnsIssDate() {
        return trnsDriveLicnsIssDate;
    }
    public void setTrnsDriveLicnsIssDate(String trnsDriveLicnsIssDate) {
        this.trnsDriveLicnsIssDate = trnsDriveLicnsIssDate;
    }
    public String getTrnsTrnsfeNm() {
        return trnsTrnsfeNm;
    }
    public void setTrnsTrnsfeNm(String trnsTrnsfeNm) {
        this.trnsTrnsfeNm = trnsTrnsfeNm;
    }
    public String getTrnsTrnsfeMobileNo() {
        return trnsTrnsfeMobileNo;
    }
    public void setTrnsTrnsfeMobileNo(String trnsTrnsfeMobileNo) {
        this.trnsTrnsfeMobileNo = trnsTrnsfeMobileNo;
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
    public String getTrnsStatusVal() {
        return trnsStatusVal;
    }
    public void setTrnsStatusVal(String trnsStatusVal) {
        this.trnsStatusVal = trnsStatusVal;
    }
    public String getAuthNum() {
        return authNum;
    }
    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }
    public String getTimer0() {
        return timer0;
    }
    public String getAuthValue() {
        return authValue;
    }
    public void setAuthValue(String authValue) {
        this.authValue = authValue;
    }
    public void setTimer0(String timer0) {
        this.timer0 = timer0;
    }



}
