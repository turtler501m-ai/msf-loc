package com.ktis.msp.batch.manager.vo;

public class VacCommonVO {
	
	// P_VAC_ONCE
	private String opCode;			// REG:등록, CAN:회수
	private String gubun;			// 요청종류별 구분 - 단말보험:INSR
	private String orderKey;		// 요청종류의 키
	private String vacBankCd;		// 가상계좌 코드 SELECT CD FROM PPS_KT_COMMON_CD WHERE TABLE_NM = 'PPS_VAC' AND COL = 'BANK_CODE'
	private String amount;			// 입금금액
	private String expireDt;		// 입금만료일자  기본 +10 최대 +30 EX) 0이면 오늘까지 1이면 내일까지
	private String remark;			// 메모
	private String adminId;			// 요청자ID
	
	private String retCd;			// 0000:성공, 그 외 실패
	private String retMsg;
	private String vacId;		// 가상계좌
	
	
	public String getOpCode() {
		return opCode;
	}
	public void setOpCode(String opCode) {
		this.opCode = opCode;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public String getOrderKey() {
		return orderKey;
	}
	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}
	public String getVacBankCd() {
		return vacBankCd;
	}
	public void setVacBankCd(String vacBankCd) {
		this.vacBankCd = vacBankCd;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getExpireDt() {
		return expireDt;
	}
	public void setExpireDt(String expireDt) {
		this.expireDt = expireDt;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAdminId() {
		return adminId;
	}
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}
	public String getRetCd() {
		return retCd;
	}
	public void setRetCd(String retCd) {
		this.retCd = retCd;
	}
	public String getRetMsg() {
		return retMsg;
	}
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}
	public String getVacId() {
		return vacId;
	}
	public void setVacId(String vacId) {
		this.vacId = vacId;
	}
		
}
