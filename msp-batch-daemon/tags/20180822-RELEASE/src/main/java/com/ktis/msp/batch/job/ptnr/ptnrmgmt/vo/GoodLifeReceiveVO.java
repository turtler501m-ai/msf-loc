package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

public class GoodLifeReceiveVO {
	
	private String ifNo;		// 인터페이스ID
	private String ifYmd;		// 연동일자
	private String svcCntrNo;	// 서비스계약번호
	private String ctn;			// 전화번호
	private String statYn;		// 상조가입여부
	private String pymnYn;		// 상조수납여부
	private String goodlifeId;	// 상조식별번호
	private int payCnt;			// 좋은상조수납차수
	private String ifId;
	
	private String usgYm;		// 사용월
	private String billYm;		// 청구월
	private String sttlYm;		// 정산월
	private int dcAmt;			// 엠모바일 할인금액
	private int cbAmt;			// 좋은상조 할인금액
	private int dcCnt;			// 엠모바일 할인차수
	
	public String getIfNo() {
		return ifNo;
	}
	public void setIfNo(String ifNo) {
		this.ifNo = ifNo;
	}
	public String getIfYmd() {
		return ifYmd;
	}
	public void setIfYmd(String ifYmd) {
		this.ifYmd = ifYmd;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getCtn() {
		return ctn;
	}
	public void setCtn(String ctn) {
		this.ctn = ctn;
	}
	public String getStatYn() {
		return statYn;
	}
	public void setStatYn(String statYn) {
		this.statYn = statYn;
	}
	public String getPymnYn() {
		return pymnYn;
	}
	public void setPymnYn(String pymnYn) {
		this.pymnYn = pymnYn;
	}
	public String getGoodlifeId() {
		return goodlifeId;
	}
	public void setGoodlifeId(String goodlifeId) {
		this.goodlifeId = goodlifeId;
	}
	public int getPayCnt() {
		return payCnt;
	}
	public void setPayCnt(int payCnt) {
		this.payCnt = payCnt;
	}
	public String getIfId() {
		return ifId;
	}
	public void setIfId(String ifId) {
		this.ifId = ifId;
	}
	public String getUsgYm() {
		return usgYm;
	}
	public void setUsgYm(String usgYm) {
		this.usgYm = usgYm;
	}
	public String getBillYm() {
		return billYm;
	}
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	public String getSttlYm() {
		return sttlYm;
	}
	public void setSttlYm(String sttlYm) {
		this.sttlYm = sttlYm;
	}
	public int getDcAmt() {
		return dcAmt;
	}
	public void setDcAmt(int dcAmt) {
		this.dcAmt = dcAmt;
	}
	public int getCbAmt() {
		return cbAmt;
	}
	public void setCbAmt(int cbAmt) {
		this.cbAmt = cbAmt;
	}
	public int getDcCnt() {
		return dcCnt;
	}
	public void setDcCnt(int dcCnt) {
		this.dcCnt = dcCnt;
	}
	
}
