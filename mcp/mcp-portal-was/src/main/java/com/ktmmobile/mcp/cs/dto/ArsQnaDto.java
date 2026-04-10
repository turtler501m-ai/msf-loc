package com.ktmmobile.mcp.cs.dto;

import java.io.Serializable;

public class ArsQnaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private String ifCode;        // 연동코드

	private String qnaSeq;        // 원본순번

	private String qnaType;       // QNA 유형 (CN: 홈페이지문의, MN: 상담예약)

	private String userNm;        // 사용자명

	public String getIfCode() {
		return ifCode;
	}

	public void setIfCode(String ifCode) {
		this.ifCode = ifCode;
	}

	public String getQnaSeq() {
		return qnaSeq;
	}

	public void setQnaSeq(String qnaSeq) {
		this.qnaSeq = qnaSeq;
	}

	public String getQnaType() {
		return qnaType;
	}

	public void setQnaType(String qnaType) {
		this.qnaType = qnaType;
	}

	public String getUserNm() {
		return userNm;
	}

	public void setUserNm(String userNm) {
		this.userNm = userNm;
	}

}
