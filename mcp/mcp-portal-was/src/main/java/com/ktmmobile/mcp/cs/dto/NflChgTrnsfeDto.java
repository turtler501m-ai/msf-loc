package com.ktmmobile.mcp.cs.dto;

import java.util.Date;
import java.io.Serializable;

public class NflChgTrnsfeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int trnsfeSeq;						//명의변경일련번호
    private int trnsfeApyNo;					//명의변경신청번호
    private String trnsfeNm;					//양수인 이름
    private String trnsfeMobileNo;				//양수인 전화번호
    private String trnsfeResno;					//양수인 주민등록번호
    private String trnsfeMyslfConfMeth;			//양수인 본인확인방법
    private String trnsfeResidRegIssGovof;		//양수인 주민증 발급처
    private String trnsfeResidRegIssDate;		//양수인 주민증 발급일
    private String trnsfeDriveLicnsNo;			//양수인 운전면허 발급번호
    private String trnsfeDriveLicnsIssDate;		//양수인 운전면허 발급일
    private String cretId;						//생성자아이디
    private String amdId;						//수정자아이디
    private Date cretDt;						//생성일시
    private Date amdDt;							//수정일시
    private int tmpTrnsApyNo;					//(양수인 임시)명의변경신청번호
    private String tmpTrnsPass;					//(양수인 임시)명의변경용 비밀번호
    private String tmpBirthDay;					//Nice본인인증을 위한 임시 생일VO
    private String tmpStatusVal;				//(양수인 임시)명의변경신청 상태값(1:대기중, 2:신청완료, 3:처리완료, 4:부재중, 5:기한초과)

    public int getTrnsfeSeq() {
        return trnsfeSeq;
    }
    public void setTrnsfeSeq(int trnsfeSeq) {
        this.trnsfeSeq = trnsfeSeq;
    }
    public int getTrnsfeApyNo() {
        return trnsfeApyNo;
    }
    public void setTrnsfeApyNo(int trnsfeApyNo) {
        this.trnsfeApyNo = trnsfeApyNo;
    }
    public String getTrnsfeNm() {
        return trnsfeNm;
    }
    public void setTrnsfeNm(String trnsfeNm) {
        this.trnsfeNm = trnsfeNm;
    }
    public String getTrnsfeMobileNo() {
        return trnsfeMobileNo;
    }
    public void setTrnsfeMobileNo(String trnsfeMobileNo) {
        this.trnsfeMobileNo = trnsfeMobileNo;
    }
    public String getTrnsfeResno() {
        return trnsfeResno;
    }
    public void setTrnsfeResno(String trnsfeResno) {
        this.trnsfeResno = trnsfeResno;
    }
    public String getTrnsfeMyslfConfMeth() {
        return trnsfeMyslfConfMeth;
    }
    public void setTrnsfeMyslfConfMeth(String trnsfeMyslfConfMeth) {
        this.trnsfeMyslfConfMeth = trnsfeMyslfConfMeth;
    }
    public String getTrnsfeResidRegIssGovof() {
        return trnsfeResidRegIssGovof;
    }
    public void setTrnsfeResidRegIssGovof(String trnsfeResidRegIssGovof) {
        this.trnsfeResidRegIssGovof = trnsfeResidRegIssGovof;
    }
    public String getTrnsfeResidRegIssDate() {
        return trnsfeResidRegIssDate;
    }
    public void setTrnsfeResidRegIssDate(String trnsfeResidRegIssDate) {
        this.trnsfeResidRegIssDate = trnsfeResidRegIssDate;
    }
    public String getTrnsfeDriveLicnsNo() {
        return trnsfeDriveLicnsNo;
    }
    public void setTrnsfeDriveLicnsNo(String trnsfeDriveLicnsNo) {
        this.trnsfeDriveLicnsNo = trnsfeDriveLicnsNo;
    }
    public String getTrnsfeDriveLicnsIssDate() {
        return trnsfeDriveLicnsIssDate;
    }
    public void setTrnsfeDriveLicnsIssDate(String trnsfeDriveLicnsIssDate) {
        this.trnsfeDriveLicnsIssDate = trnsfeDriveLicnsIssDate;
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
    public int getTmpTrnsApyNo() {
        return tmpTrnsApyNo;
    }
    public void setTmpTrnsApyNo(int tmpTrnsApyNo) {
        this.tmpTrnsApyNo = tmpTrnsApyNo;
    }
    public String getTmpTrnsPass() {
        return tmpTrnsPass;
    }
    public void setTmpTrnsPass(String tmpTrnsPass) {
        this.tmpTrnsPass = tmpTrnsPass;
    }
    public String getTmpBirthDay() {
        return tmpBirthDay;
    }
    public void setTmpBirthDay(String tmpBirthDay) {
        this.tmpBirthDay = tmpBirthDay;
    }
    public String getTmpStatusVal() {
        return tmpStatusVal;
    }
    public void setTmpStatusVal(String tmpStatusVal) {
        this.tmpStatusVal = tmpStatusVal;
    }


}
