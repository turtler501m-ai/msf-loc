package com.ktmmobile.mcp.cs.dto;

import java.util.Date;
import java.io.Serializable;

public class TransferDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /*양도인 vo*/
    private String trnsSeq;					//명의변경일련번호
    private int trnsApyNo = 0;					//명의변경신청번호
    private String trnsNm;					//양도인 이름
    private String trnsMobileNo;			//명의변경할 휴대폰번호
    private String trnsPhoneNo;				//양도인 연락처
    private String trnsPass;				//명의변경용 비밀번호
    private String trnsMyslfConfMeth;		//양도인 본인확인방법
    private String trnsResidRegIssGovof;	//양도인 주민증 발급처
    private String trnsResidRegIssDate;		//양도인 주민증 발급일
    private String trnsDriveLicnsNo;		//양도인 운전면허 발급번호
    private String trnsDriveLicnsIssDate;		//양도인 운전면허 발급일
    private String trnsTrnsfeNm;			//양도인이 입력한 양수인 이름
    private String trnsTrnsfeMobileNo;		//양도인이 입력한 양수인 휴대폰번호
    private String trnsStatusVal;			//명의변경신청 상태값(1:대기중, 2:신청완료, 3:처리완료, 4:부재중, 5:기한초과)
    /*양수인 vo*/
    private int trnsfeSeq = 0;						//명의변경일련번호
    private int trnsfeApyNo = 0;					//명의변경신청번호
    private String trnsfeNm;					//양수인 이름
    private String trnsfeMobileNo;				//양수인 전화번호
    private String trnsfeResno;					//양수인 주민등록번호
    private String trnsfeMyslfConfMeth;			//양수인 본인확인방법
    private String trnsfeResidRegIssGovof;		//양수인 주민증 발급처
    private String trnsfeResidRegIssDate;		//양수인 주민증 발급일
    private String trnsfeDriveLicnsNo;			//양수인 운전면허 발급번호
    private String trnsfeDriveLicnsIssDate;		//양수인 운전면허 발급일

    private String cretId;					//생성자아이디
    private String amdId;					//수정자아이디
    private Date cretDt;					//생성일시
    private Date amdDt;					//수정일시

    private String fcretId;					//리스트용 양수인 테이블 생성자아이디
    private String famdId;					//리스트용 양수인 테이블 수정자아이디
    private Date fcretDt;					//리스트용 양수인 테이블 생성일시
    private Date famdDt;					//리스트용 양수인 테이블 수정일시

    private String authDelYn;				//개인정보 삭제여부
    private String confirmMemo;				//처리시 메모
    private String treatVal;				//0:처리상태,1:대기중, 2:신청완료, 3:처리완료, 4:부재중, 5:기한초과
    private String divVal;					//용도 폐기됨
    private String multiVal;				//0:전체, 1:작성자, 2:접수번호, 3:신청일, 4:처리일
    private String searchValue;				//검색어
    private int boardNum = 0;					//리스트의 역순 일련번호
    private String startDt;					//검색용 시작날짜
    private String endDt;					//검색용 종료날짜
    private String telCtn;					//전화번호 형식('-')

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
    public String getTrnsStatusVal() {
        return trnsStatusVal;
    }
    public void setTrnsStatusVal(String trnsStatusVal) {
        this.trnsStatusVal = trnsStatusVal;
    }
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
    public String getFcretId() {
        return fcretId;
    }
    public void setFcretId(String fcretId) {
        this.fcretId = fcretId;
    }
    public String getFamdId() {
        return famdId;
    }
    public void setFamdId(String famdId) {
        this.famdId = famdId;
    }
    public Date getFcretDt() {
        return fcretDt;
    }
    public void setFcretDt(Date fcretDt) {
        this.fcretDt = fcretDt;
    }
    public Date getFamdDt() {
        return famdDt;
    }
    public void setFamdDt(Date famdDt) {
        this.famdDt = famdDt;
    }
    public String getAuthDelYn() {
        return authDelYn;
    }
    public void setAuthDelYn(String authDelYn) {
        this.authDelYn = authDelYn;
    }
    public String getConfirmMemo() {
        return confirmMemo;
    }
    public void setConfirmMemo(String confirmMemo) {
        this.confirmMemo = confirmMemo;
    }


    public String getTreatVal() {
        return treatVal;
    }
    public void setTreatVal(String treatVal) {
        this.treatVal = treatVal;
    }
    public String getDivVal() {
        return divVal;
    }
    public void setDivVal(String divVal) {
        this.divVal = divVal;
    }

    public String getMultiVal() {
        return multiVal;
    }
    public void setMultiVal(String multiVal) {
        this.multiVal = multiVal;
    }
    public String getSearchValue() {
        return searchValue;
    }
    public void setSearchValue(String searchValue) {
        this.searchValue = searchValue;
    }
    public int getBoardNum() {
        return boardNum;
    }
    public void setBoardNum(int boardNum) {
        this.boardNum = boardNum;
    }

    public String getStartDt() {
        return startDt;
    }
    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }
    public String getEndDt() {
        return endDt;
    }
    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }
    public String getTelCtn() {
        return telCtn;
    }
    public void setTelCtn(String telCtn) {
        this.telCtn = telCtn;
    }

}
