package com.ktmmobile.msf.domains.form.common.dto;

/**
 * MVNO 결합 사전 체크 (x78)
 */
public class MoscCombChkReqDto {

	private String custId;
	private String ncn;
	private String ctn;

	private String jobGubun; //작업구분코드 N:신규결합신청, A:회선추가, D:회선 삭제
	private String svcNoCd; //회선사업자코드 M: MVNO회선, K:KT 회선
	private String svcNoTypeCd; //회선구분코드 인터넷:IT  모바일:MB MVNO회선일 경우 MB만 가능
	private String cmbStndSvcNo; //결합서비스번호 모바일 회선: 전화번호 인터넷: ID
	private String custNm; //고객이름 (KT회선일경우 필수 )
	private String svcIdfyTypeCd; //서비스 확인 번호 타입/  (KT회선일경우 필수) KT 회선, 회선추가, KT모바일일 경우 아래의 코드 중 선택하여 연동 / 생년월일:1, 법인번호:3, 사업자번호:7, 여권번호:2, 기타:99 그 외의 경우 생년월일코드 1으로 연동
	private String svcIdfyNo; //서비스확인번호 /(KT회선일경우 필수 ) 	svcIdfyTypeCd의 값으로 넣은 확인번호 연동	생년월일 8자리 ex)19821120	법인번호 13자리	사업자등록번호 뒷 5자리	여권번호 20자리	기타 20자리
	private String sexCd; //성별코드 (KT회선일경우 필수 ) 1: 남성, 2: 여성
	private String aplyMethCd; //신청방법 전화 01 	FAX, 02	우편 03,	창구방문 04,	E-MAIL 07,	직원방문접수 14,	iPad 접수 20,	스마트서식지 접수 21,	M-KOS 접수 MB
	private String aplyRelatnCd; //신청관계 01 본인,	11 대리인,	12 대표자,	05 KT직원,	06 유지보수업체직원,	10 키맨,	13 채권담당자,	KT KT,
	private String aplyNm; // 신청자이름
	private String homeCombTerm; //홈 결합(인터넷 + MVNO 무선) 할인 기간 / 홈 결합(인터넷 + MVNO 무선) 신규결합 신청일 경우 연동,	무약정: N , 1년 :1 2년: 2 3년: 3	미입력시 3년 default 처리


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
	public String getAplyNm() {
		return aplyNm;
	}
	public void setAplyNm(String aplyNm) {
		this.aplyNm = aplyNm;
	}
	public String getJobGubun() {
		return jobGubun;
	}
	public void setJobGubun(String jobGubun) {
		this.jobGubun = jobGubun;
	}
	public String getSvcNoCd() {
		return svcNoCd;
	}
	public void setSvcNoCd(String svcNoCd) {
		this.svcNoCd = svcNoCd;
	}
	public String getSvcNoTypeCd() {
		return svcNoTypeCd;
	}
	public void setSvcNoTypeCd(String svcNoTypeCd) {
		this.svcNoTypeCd = svcNoTypeCd;
	}
	public String getCmbStndSvcNo() {
		return cmbStndSvcNo;
	}
	public void setCmbStndSvcNo(String cmbStndSvcNo) {
		this.cmbStndSvcNo = cmbStndSvcNo;
	}
	public String getCustNm() {
		return custNm;
	}
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}
	public String getSvcIdfyTypeCd() {
		return svcIdfyTypeCd;
	}
	public void setSvcIdfyTypeCd(String svcIdfyTypeCd) {
		this.svcIdfyTypeCd = svcIdfyTypeCd;
	}
	public String getSvcIdfyNo() {
		return svcIdfyNo;
	}
	public void setSvcIdfyNo(String svcIdfyNo) {
		this.svcIdfyNo = svcIdfyNo;
	}
	public String getSexCd() {
		return sexCd;
	}
	public void setSexCd(String sexCd) {
		this.sexCd = sexCd;
	}
	public String getAplyMethCd() {
		return aplyMethCd;
	}
	public void setAplyMethCd(String aplyMethCd) {
		this.aplyMethCd = aplyMethCd;
	}
	public String getAplyRelatnCd() {
		return aplyRelatnCd;
	}
	public void setAplyRelatnCd(String aplyRelatnCd) {
		this.aplyRelatnCd = aplyRelatnCd;
	}
	public String getHomeCombTerm() {
		return homeCombTerm;
	}
	public void setHomeCombTerm(String homeCombTerm) {
		this.homeCombTerm = homeCombTerm;
	}



}
