package com.ktmmobile.msf.form.servicechange.dto;


import java.io.Serializable;

/**
 * 데이터 쉐어링  req
 * @author bsj
 *
 */
public class MyShareDataReqDto implements Serializable  {

	private static final long serialVersionUID = 987580018599047263L;
	private String iccId; //유심유효성
	private String custId;	//고객번호
	private String ncn;	//사용자 서비스계약번호
	private String ctn;	//사용자 전화번호
	private String clntIp;	//Client IP
	private String clntUsrId;	//사용자 User ID
	private String crprCtn; //결합할 전화번호
	private String opmdSvcNo; //데이터쉐어링 대상 전화번호
	private String opmdWorkDivCd; //처리구분코드 A:결합, C:해지
	private String birthday; //생년월일
	private String name; // 성명
	private String contractNum; // 계약번호
	private String selfShareYn; //셀프개통 사전체크 Y , 쉐어링 가입 N
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
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
	public String getClntIp() {
		return clntIp;
	}
	public void setClntIp(String clntIp) {
		this.clntIp = clntIp;
	}
	public String getClntUsrId() {
		return clntUsrId;
	}
	public void setClntUsrId(String clntUsrId) {
		this.clntUsrId = clntUsrId;
	}
	public String getOpmdSvcNo() {
		return opmdSvcNo;
	}
	public void setOpmdSvcNo(String opmdSvcNo) {
		this.opmdSvcNo = opmdSvcNo;
	}
	public String getOpmdWorkDivCd() {
		return opmdWorkDivCd;
	}
	public void setOpmdWorkDivCd(String opmdWorkDivCd) {
		this.opmdWorkDivCd = opmdWorkDivCd;
	}
	public String getCrprCtn() {
		return crprCtn;
	}
	public void setCrprCtn(String crprCtn) {
		this.crprCtn = crprCtn;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getIccId() {
		return iccId;
	}
	public void setIccId(String iccId) {
		this.iccId = iccId;
	}
	public String getSelfShareYn() {
		return selfShareYn;
	}
	public void setSelfShareYn(String selfShareYn) {
		this.selfShareYn = selfShareYn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
}
