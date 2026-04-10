package com.ktmmobile.mcp.acenars.dto;

import com.ktmmobile.mcp.common.util.EncryptUtil;

import java.io.Serializable;

public class ArsDto implements Serializable {

	private static final long serialVersionUID = 1L;

  private String reqSeq;
	private String vocSeq;
	private String contractNum;
	private String vocAgntCd;
	private String status;
	private String vocSubCtgCd;
	private String vocContent;
	private String dueDt;
	private String ansContent;
	private String rvisnDttm;
	private String rvisnId;

	public String getReqSeq() {
		return reqSeq;
	}

	public void setReqSeq(String reqSeq) {
		this.reqSeq = reqSeq;
	}

	public String getVocSeq() {
		return vocSeq;
	}

	public void setVocSeq(String vocSeq) {
		this.vocSeq = vocSeq;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getVocAgntCd() {
		return vocAgntCd;
	}

	public void setVocAgntCd(String vocAgntCd) {
		this.vocAgntCd = vocAgntCd;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getVocSubCtgCd() {
		return vocSubCtgCd;
	}

	public void setVocSubCtgCd(String vocSubCtgCd) {
		this.vocSubCtgCd = vocSubCtgCd;
	}

	public String getVocContent() {
		return vocContent;
	}

	public void setVocContent(String vocContent) {
		this.vocContent = vocContent;
	}

	public String getDueDt() {
		return dueDt;
	}

	public void setDueDt(String dueDt) {
		this.dueDt = dueDt;
	}

	public String getAnsContent() {
		return ansContent;
	}

	public void setAnsContent(String ansContent) {
		this.ansContent = ansContent;
	}

	public String getRvisnDttm() {
		return rvisnDttm;
	}

	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}

	public String getRvisnId() {
		return rvisnId;
	}

	public void setRvisnId(String rvisnId) {
		this.rvisnId = EncryptUtil.ace256Enc(rvisnId);
	}

}
