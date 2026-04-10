package com.ktmmobile.mcp.cprt.dto;

import org.springframework.web.multipart.MultipartFile;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="root")
public class AlliCardDtlContDtoXML {

	/**제휴카드 기본등록 */
	private int cprtCardGdncSeq;				/* 제휴카드안내일련번호 */
	private String cprtCardGdncNm;				/* 제휴카드명 */
	private String cprtCardCd;					/* 코드관리(Co0008) */
	private String cprtCardBasDesc;				/* 제휴카드기본설명 */
	private String cprtCardThumbImgNm;			/* 제휴카드섬네일이미지명 */
	private String cprtCardLargeImgNm;			/* 제휴카드대이미지명 */
	private String gdncFileNm;					/* 안내파일명 */
	private String useYn;						/* 코드값(Y : 유효 / N : 유효하지않음) */
	private String pstngStartDate;				/* 게시시작일자 */
	private String pstngEndDate	;				/* 게시종료일자 */
	private String cretIp;
	private String cretDt;
	private String cretId;
	private String amdIp;
	private String amdDt;
	private String amdId;
	private String cprtCardThumbImgNmFile;
	private String cprtCardLargeImgNmFile;

	public int getCprtCardGdncSeq() {
		return cprtCardGdncSeq;
	}

	@XmlElement
	public void setCprtCardGdncSeq(int cprtCardGdncSeq) {
		this.cprtCardGdncSeq = cprtCardGdncSeq;
	}
	public String getCprtCardGdncNm() {
		return cprtCardGdncNm;
	}

	@XmlElement
	public void setCprtCardGdncNm(String cprtCardGdncNm) {
		this.cprtCardGdncNm = cprtCardGdncNm;
	}
	public String getCprtCardCd() {
		return cprtCardCd;
	}

	@XmlElement
	public void setCprtCardCd(String cprtCardCd) {
		this.cprtCardCd = cprtCardCd;
	}
	public String getCprtCardBasDesc() {
		return cprtCardBasDesc;
	}

	@XmlElement
	public void setCprtCardBasDesc(String cprtCardBasDesc) {
		this.cprtCardBasDesc = cprtCardBasDesc;
	}
	public String getCprtCardThumbImgNm() {
		return cprtCardThumbImgNm;
	}

	@XmlElement
	public void setCprtCardThumbImgNm(String cprtCardThumbImgNm) {
		this.cprtCardThumbImgNm = cprtCardThumbImgNm;
	}
	public String getCprtCardLargeImgNm() {
		return cprtCardLargeImgNm;
	}

	@XmlElement
	public void setCprtCardLargeImgNm(String cprtCardLargeImgNm) {
		this.cprtCardLargeImgNm = cprtCardLargeImgNm;
	}
	public String getGdncFileNm() {
		return gdncFileNm;
	}

	@XmlElement
	public void setGdncFileNm(String gdncFileNm) {
		this.gdncFileNm = gdncFileNm;
	}
	public String getUseYn() {
		return useYn;
	}

	@XmlElement
	public void setUseYn(String useYn) {
		this.useYn = useYn;
	}
	public String getPstngStartDate() {
		return pstngStartDate;
	}

	@XmlElement
	public void setPstngStartDate(String pstngStartDate) {
		this.pstngStartDate = pstngStartDate;
	}
	public String getPstngEndDate() {
		return pstngEndDate;
	}

	@XmlElement
	public void setPstngEndDate(String pstngEndDate) {
		this.pstngEndDate = pstngEndDate;
	}
	public String getCretIp() {
		return cretIp;
	}

	@XmlElement
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}
	public String getCretDt() {
		return cretDt;
	}

	@XmlElement
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	public String getCretId() {
		return cretId;
	}

	@XmlElement
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getAmdIp() {
		return amdIp;
	}

	@XmlElement
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	public String getAmdDt() {
		return amdDt;
	}

	@XmlElement
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	public String getAmdId() {
		return amdId;
	}

	@XmlElement
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}




}
