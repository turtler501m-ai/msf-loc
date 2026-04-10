package com.ktmmobile.msf.domains.form.system.cert.dto;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

public class CertDto implements Serializable  {

    private static final long serialVersionUID = 1L;


	private long crtSeq = -1; 			//시퀀스
	private String resgtDt;				//등록일자(YYYYMMDD)
	private String authType;			//본인인증종류
	private String moduType;			//모듈종류
	private String cReferer;			//페이지url
	private Integer step;				//단계
	private String name;				//이름
	private String birthDate;			//생년월일
	private String mobileNo;			//휴대폰번호
	private String connInfo; 			//CI
	private String rip;					//등록아이피
	private String regstId;				//등록자
	private String regstDttm;			//등록일시
	private String rvisnId;				//수정자
	private String rvisnDttm;    		//수정일시
	private String compType;			//비교타입
	private String requestKey;			//리퀘스트키
	private String veriRslt; 			//비교결과값
	private String contractNum;			//계약번호
	private String stepEndYn; 			//마지막단계여부	
	private String reqUsimSn; 			//유심번호
	private String reqBank;				//은행코드
	private String reqAccountNumber;	//계좌번호
	private String uploadPhoneSrlNo;	//ESIM SEQ	
	private long rtnCrtSeq;             //쿼리에서 사용하는 crtSeq
	private String reqSeq;				//요청일련번호
	private String resSeq;              //응답일련번호
	private String ncType;				//명의변경 양수인 양도인 타입
	private String crtEtc;				//기타
	private String dupInfo;				//DI
	private String urlType;				//url구분값
	private String subStep;				//서브스텝
	private String reqCardCompany;		//카드회사코드
	private String reqCardNo;			//카드번호
	private String crdtCardTermDay;		//카드유효기간(YYYYMM)
	
	public long getCrtSeq() {
		return crtSeq;
	}

	public void setCrtSeq(long crtSeq) {
		this.crtSeq = crtSeq;
	}

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getResSeq() {
		return resSeq;
	}

	public void setResSeq(String resSeq) {
		this.resSeq = resSeq;
	}

	public String getResgtDt() {
		return resgtDt;
	}

	public void setResgtDt(String resgtDt) {
		this.resgtDt = resgtDt;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public String getModuType() {
		return moduType;
	}

	public void setModuType(String moduType) {
		this.moduType = moduType;
	}

	public Integer getStep() {
		return step;
	}

	public void setStep(Integer step) {
		this.step = step;
	}

	public String getName() {
		if(!StringUtils.isBlank(name)) {
			return name.toUpperCase().replaceAll(" ", "");
		}
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	
	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	

	public String getConnInfo() {
		return connInfo;
	}

	public void setConnInfo(String connInfo) {
		this.connInfo = connInfo;
	}

	public String getRip() {
		return rip;
	}

	public void setRip(String rip) {
		this.rip = rip;
	}

	public String getRegstId() {
		return regstId;
	}

	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}

	public String getRegstDttm() {
		return regstDttm;
	}

	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}

	public String getRvisnId() {
		return rvisnId;
	}

	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}

	public String getRvisnDttm() {
		return rvisnDttm;
	}

	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	
	public String getCompType() {
		return compType;
	}

	public void setCompType(String compType) {
		this.compType = compType;
	}

	public String getRequestKey() {
		return requestKey;
	}

	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}

	public String getVeriRslt() {
		return veriRslt;
	}

	public void setVeriRslt(String veriRslt) {
		this.veriRslt = veriRslt;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getcReferer() {
		return cReferer;
	}

	public void setcReferer(String cReferer) {
		this.cReferer = cReferer;
	}

	public String getStepEndYn() {
		return stepEndYn;
	}

	public void setStepEndYn(String stepEndYn) {
		this.stepEndYn = stepEndYn;
	}

	public String getReqUsimSn() {
		return reqUsimSn;
	}

	public void setReqUsimSn(String reqUsimSn) {
		this.reqUsimSn = reqUsimSn;
	}

	public String getReqBank() {
		return reqBank;
	}

	public void setReqBank(String reqBank) {
		this.reqBank = reqBank;
	}

	public String getReqAccountNumber() {
		return reqAccountNumber;
	}

	public void setReqAccountNumber(String reqAccountNumber) {
		this.reqAccountNumber = reqAccountNumber;
	}

	public String getUploadPhoneSrlNo() {
		return uploadPhoneSrlNo;
	}

	public void setUploadPhoneSrlNo(String uploadPhoneSrlNo) {
		this.uploadPhoneSrlNo = uploadPhoneSrlNo;
	}

	public long getRtnCrtSeq() {
		return rtnCrtSeq;
	}

	public void setRtnCrtSeq(long rtnCrtSeq) {
		this.rtnCrtSeq = rtnCrtSeq;
	}

	public String getNcType() {
		return ncType;
	}

	public void setNcType(String ncType) {
		this.ncType = ncType;
	}

	public String getCrtEtc() {
		return crtEtc;
	}

	public void setCrtEtc(String crtEtc) {
		this.crtEtc = crtEtc;
	}

	public String getDupInfo() {
		return dupInfo;
	}

	public void setDupInfo(String dupInfo) {
		this.dupInfo = dupInfo;
	}

	public String getUrlType() {
		return urlType;
	}

	public void setUrlType(String urlType) {
		this.urlType = urlType;
	}

	public String getSubStep() {
		return subStep;
	}

	public void setSubStep(String subStep) {
		this.subStep = subStep;
	}

	public String getReqCardCompany() {
		return reqCardCompany;
	}

	public void setReqCardCompany(String reqCardCompany) {
		this.reqCardCompany = reqCardCompany;
	}

	public String getReqCardNo() {
		return reqCardNo;
	}

	public void setReqCardNo(String reqCardNo) {
		this.reqCardNo = reqCardNo;
	}

	public String getCrdtCardTermDay() {
		return crdtCardTermDay;
	}

	public void setCrdtCardTermDay(String crdtCardTermDay) {
		this.crdtCardTermDay = crdtCardTermDay;
	}
}
