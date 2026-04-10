package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class MoscPymnReqDto  implements Serializable  {

	/**
	 * 미납요금 즉시수납 처리
	 * 인터페이스번호 : x68
	/*실시간 계좌이체 일 경우 필수
	 *가능한 은행
	- 기업은행, 국민은행, 농협, 우리은행, 제일은행,우체국, KEB 하나은행, 신한은행
	- 코드정의서 참조(BANK_MEMBERSPLAZA_CD)

	* 은행별 이용 불가능 시간
	- 23:50 ~ 00:10 동안 은행 공동망 마감/완료시간으로 전 은행 거래불가능
	- 기업은행 : 23:59 ~ 00:30 동안 계좌이체가 불가능
	- 국민은행 : 23:40 ~ 00:10 동안 계좌이체가 불가능
	- 농협 : 23:00 ~ 04:00 동안 계좌이체가 불가능
	- 우리은행 : 23:59 ~ 01:00 동안 계좌이체가 불가능
	- 제일은행 : 23:30 ~ 00:30 동안 계좌이체가 불가능
	- 신한(구) : 23:40 ~ 00:10 동안 계좌이체가 불가능
	- 하나은행 : 23:59 ~ 01:00 동안 계좌이체가 불가능 (월요일 ~ 토요일), 23:59 ~ 06:40 동안 계좌이체가 불가능 (일요일)
	- 신한은행 : 23:40 ~ 00:10 동안 계좌이체가 불가능" */

	private static final long serialVersionUID = 1L;

	private String custId; //고객번호
	private String ncn; //사용자 서비스계약번호
	private String ctn; //사용자 전화번호
	private String clntIp; //Client IP
	private String clntUsrId;	//사용자 User ID
	private String payMentMoney; //수납금액 단위:원
	private String blMethod; //수납방법
	private String blBankCode; //은행코드


	private String bankAcctNo; //계좌번호 실시간 계좌이체 일 경우 필수
	private String agrDivCd; 	//동의유형
	private String myslfAthnTypeItgCd;//본인인증유형
	private String cardNo; //카드번호
	private String cardExpirDate; //카드 유효기간
	private String cardPwd; //	카드 비밀번호
	private String cardInstMnthCnt; //할부기간
	private String rmnyChId; //수납채널
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
	public String getPayMentMoney() {
		return payMentMoney;
	}
	public void setPayMentMoney(String payMentMoney) {
		this.payMentMoney = payMentMoney;
	}
	public String getBlMethod() {
		return blMethod;
	}
	public void setBlMethod(String blMethod) {
		this.blMethod = blMethod;
	}
	public String getBlBankCode() {
		return blBankCode;
	}
	public void setBlBankCode(String blBankCode) {
		this.blBankCode = blBankCode;
	}
	public String getBankAcctNo() {
		return bankAcctNo;
	}
	public void setBankAcctNo(String bankAcctNo) {
		this.bankAcctNo = bankAcctNo;
	}
	public String getAgrDivCd() {
		return agrDivCd;
	}
	public void setAgrDivCd(String agrDivCd) {
		this.agrDivCd = agrDivCd;
	}
	public String getMyslfAthnTypeItgCd() {
		return myslfAthnTypeItgCd;
	}
	public void setMyslfAthnTypeItgCd(String myslfAthnTypeItgCd) {
		this.myslfAthnTypeItgCd = myslfAthnTypeItgCd;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getCardExpirDate() {
		return cardExpirDate;
	}
	public void setCardExpirDate(String cardExpirDate) {
		this.cardExpirDate = cardExpirDate;
	}
	public String getCardPwd() {
		return cardPwd;
	}
	public void setCardPwd(String cardPwd) {
		this.cardPwd = cardPwd;
	}
	public String getCardInstMnthCnt() {
		return cardInstMnthCnt;
	}
	public void setCardInstMnthCnt(String cardInstMnthCnt) {
		this.cardInstMnthCnt = cardInstMnthCnt;
	}
	public String getRmnyChId() {
		return rmnyChId;
	}
	public void setRmnyChId(String rmnyChId) {
		this.rmnyChId = rmnyChId;
	}




}
