package com.ktmmobile.mcp.cprt.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class AlliCardCtgaContXML {

	//제휴카드카테고리코드
	private String cprtCardCtgCd;

	//제휴카드안내일련번호
	private String cprtCardGdncSeq;

	//정렬순서
	private String sortOdrg;

	//사용여부
	private String useYn;

	//게시시작일자
	private String pstngStartDate;

	//게시종료일자
	private String pstngEndDate;

	//제휴카테고리명
	private String cprtCardCtgNm;

	//제휴카테고리기본설명
	private String cprtCardCtgBasDesc;

	//제휴카드 카테고리 상세설명
	private String cprtCardCtgDtlDesc;

	//정렬순서
	private String depthKey;

	//상위제휴카드카테고리코드
	private String upCprtCardCtgCd;

	//제휴카드명
	private String cprtCardGdncNm;

	//제휴카드 일련번호
	private String cprtCardCd;

	//제휴카드 기본설명
	private String cprtCardBasDesc;

	//제휴카드 섬네일 이미지명
	private String cprtCardThumbImgNm;

	//제휴카드대이미지명
	private String cprtCardLargeImgNm;

	//안내파일명
	private String gdncFileNm;

	public String getCprtCardCtgCd() {
		return cprtCardCtgCd;
	}

	public void setCprtCardCtgCd(String cprtCardCtgCd) {
		this.cprtCardCtgCd = cprtCardCtgCd;
	}

	public String getCprtCardGdncSeq() {
		return cprtCardGdncSeq;
	}

	public void setCprtCardGdncSeq(String cprtCardGdncSeq) {
		this.cprtCardGdncSeq = cprtCardGdncSeq;
	}

	public String getSortOdrg() {
		return sortOdrg;
	}

	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
	}

	public String getUseYn() {
		return useYn;
	}

	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}

	public String getPstngStartDate() {
		return pstngStartDate;
	}

	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}

	public String getPstngEndDate() {
		return pstngEndDate;
	}

	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}

	public String getCprtCardCtgNm() {
		return cprtCardCtgNm;
	}

	public void setCprtCardCtgNm(String cprtCardCtgNm) {
		this.cprtCardCtgNm = cprtCardCtgNm;
	}

	public String getCprtCardCtgBasDesc() {
		return cprtCardCtgBasDesc;
	}

	public void setCprtCardCtgBasDesc(String cprtCardCtgBasDesc) {
		this.cprtCardCtgBasDesc = cprtCardCtgBasDesc;
	}

	public String getCprtCardCtgDtlDesc() {
		return cprtCardCtgDtlDesc;
	}

	public void setCprtCardCtgDtlDesc(String cprtCardCtgDtlDesc) {
		this.cprtCardCtgDtlDesc = cprtCardCtgDtlDesc;
	}

	public String getDepthKey() {
		return depthKey;
	}

	public void setDepthKey(String depthKey) {
		this.depthKey = depthKey;
	}

	public String getUpCprtCardCtgCd() {
		return upCprtCardCtgCd;
	}

	public void setUpCprtCardCtgCd(String upCprtCardCtgCd) {
		this.upCprtCardCtgCd = upCprtCardCtgCd;
	}

	public String getCprtCardGdncNm() {
		return cprtCardGdncNm;
	}

	public void setCprtCardGdncNm(String cprtCardGdncNm) {
		this.cprtCardGdncNm = cprtCardGdncNm;
	}

	public String getCprtCardCd() {
		return cprtCardCd;
	}

	public void setCprtCardCd(String cprtCardCd) {
		this.cprtCardCd = cprtCardCd;
	}

	public String getCprtCardBasDesc() {
		return cprtCardBasDesc;
	}

	public void setCprtCardBasDesc(String cprtCardBasDesc) {
		this.cprtCardBasDesc = cprtCardBasDesc;
	}

	public String getCprtCardThumbImgNm() {
		return cprtCardThumbImgNm;
	}

	public void setCprtCardThumbImgNm(String cprtCardThumbImgNm) {
		this.cprtCardThumbImgNm = cprtCardThumbImgNm;
	}

	public String getCprtCardLargeImgNm() {
		return cprtCardLargeImgNm;
	}

	public void setCprtCardLargeImgNm(String cprtCardLargeImgNm) {
		this.cprtCardLargeImgNm = cprtCardLargeImgNm;
	}

	public String getGdncFileNm() {
		return gdncFileNm;
	}

	public void setGdncFileNm(String gdncFileNm) {
		this.gdncFileNm = gdncFileNm;
	}




}
