package com.ktmmobile.mcp.cprt.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class AlliCardDcAmtDto implements Serializable {

	private static final long serialVersionUID = -8982577100701413844L;

	/**제휴카드 할인 금액 상세 */
	private int cprtCardDcAmtSeq; //제휴카드할인금액 일련번호
	private String dcFormlCd; //할인방식코드
	private String dcSectionStAmt;//할인구간시작금액
	private String dcSectionEndAmt;//할인구간종료금액
	private String dcAmt; //할인금액
	private int dcLimitAmt; //할인금액한도
	
	public int getCprtCardDcAmtSeq() {
		return cprtCardDcAmtSeq;
	}
	public void setCprtCardDcAmtSeq(int cprtCardDcAmtSeq) {
		this.cprtCardDcAmtSeq = cprtCardDcAmtSeq;
	}
	public String getDcFormlCd() {
		return dcFormlCd;
	}
	public void setDcFormlCd(String dcFormlCd) {
		this.dcFormlCd = dcFormlCd;
	}
	public String getDcSectionStAmt() {
		return dcSectionStAmt;
	}
	public void setDcSectionStAmt(String dcSectionStAmt) {
		this.dcSectionStAmt = dcSectionStAmt;
	}
	public String getDcSectionEndAmt() {
		return dcSectionEndAmt;
	}
	public void setDcSectionEndAmt(String dcSectionEndAmt) {
		this.dcSectionEndAmt = dcSectionEndAmt;
	}
	public String getDcAmt() {
		return dcAmt;
	}
	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}
	public int getDcLimitAmt() {
		return dcLimitAmt;
	}
	public void setDcLimitAmt(int dcLimitAmt) {
		this.dcLimitAmt = dcLimitAmt;
	}
	
	

}
