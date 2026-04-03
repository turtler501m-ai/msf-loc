package com.ktmmobile.msf.form.newchange.dto;

import java.io.Serializable;

public class OsstFathReqDto implements Serializable {
	
	private static final long serialVersionUID = -8308674020011917350L;
	
	private String asgnAgncId;		 //할당대리점	 
	private String resNo;			 //예약번호
	private String onlineOfflnDivCd; //온라인오프라인구분코드 (ONLINE:온라인 채널(비대면), OFFLINE:오프라인 채널(대면))
	private String orgId;            //대리점ID
	private String cpntId;           //접점ID
	private String retvCdVal;        //조회코드값 신분증유형코드 (REGID:주민등록증, DRIVE:운전면허증, HANDI:장애인등록증, MERIT:국가유공자증, NAMEC:대한민국여권)
	private String smsRcvTelNo;      //SMS수신전화번호 (비대면일때 SMS로 URL을 받을 전화번호)
	private String fathSbscDivCd;    //안면인증 가입 구분 코드 (1:신규가입, 2:번호이동, 3:명의변경, 4:기기변경, 5:고객정보변경)
	private String fathTransacId;    //안면인증트랜잭션아이디
	private String retvDivCd;        //조회구분코드 (1 : 안면인증 트랜잭션 아이디로 조회, 2 : 서식지아이디의 최종 안면인증 내역 조회)

	private Long requestKey;		 //가입신청키

	private String operType;		 //가입유형
	private String selfCertType;	 //본인인증유형
	private String contractNum;	 	 //계약번호
	private String cstmrType;	 	 //고객타입
	private String onOffType;	 	 //모집경로
	private String gubun;	 	 	 //개인화URL(P), 신청서 작성구분(NULL) 

	public String getAsgnAgncId() {
		return asgnAgncId;
	}

	public void setAsgnAgncId(String asgnAgncId) {
		this.asgnAgncId = asgnAgncId;
	}

	public String getOnlineOfflnDivCd() {
		return onlineOfflnDivCd;
	}

	public void setOnlineOfflnDivCd(String onlineOfflnDivCd) {
		this.onlineOfflnDivCd = onlineOfflnDivCd;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getCpntId() {
		return cpntId;
	}

	public void setCpntId(String cpntId) {
		this.cpntId = cpntId;
	}

	public String getRetvCdVal() {
		return retvCdVal;
	}

	public void setRetvCdVal(String retvCdVal) {
		this.retvCdVal = retvCdVal;
	}

	public String getSmsRcvTelNo() {
		return smsRcvTelNo;
	}

	public void setSmsRcvTelNo(String smsRcvTelNo) {
		this.smsRcvTelNo = smsRcvTelNo;
	}

	public String getFathSbscDivCd() {
		return fathSbscDivCd;
	}

	public void setFathSbscDivCd(String fathSbscDivCd) {
		this.fathSbscDivCd = fathSbscDivCd;
	}

	public String getFathTransacId() {
		return fathTransacId;
	}

	public void setFathTransacId(String fathTransacId) {
		this.fathTransacId = fathTransacId;
	}

	public String getRetvDivCd() {
		return retvDivCd;
	}

	public void setRetvDivCd(String retvDivCd) {
		this.retvDivCd = retvDivCd;
	}

	public Long getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(Long requestKey) {
		this.requestKey = requestKey;
	}

	public String getResNo() {
		return resNo;
	}

	public void setResNo(String resNo) {
		this.resNo = resNo;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getSelfCertType() {
		return selfCertType;
	}

	public void setSelfCertType(String selfCertType) {
		this.selfCertType = selfCertType;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getCstmrType() {
		return cstmrType;
	}

	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}

	public String getOnOffType() {
		return onOffType;
	}

	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
}
