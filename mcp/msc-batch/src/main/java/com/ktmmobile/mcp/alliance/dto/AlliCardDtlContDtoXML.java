package com.ktmmobile.mcp.alliance.dto;


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

	private String cprtCardNm;

	private String isPlanBannerEnabled	;				/* 요금제 배너 설정 여부(Y:설정) */
	private String planBannerTitle	;				/* 요금제 배너 제목 */
	private String planBannerText	;				/* 요금제 배너 문구) */

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

	public String getCprtCardThumbImgNmFile() {
		return cprtCardThumbImgNmFile;
	}
	@XmlElement
	public void setCprtCardThumbImgNmFile(String cprtCardThumbImgNmFile) {
		this.cprtCardThumbImgNmFile = cprtCardThumbImgNmFile;
	}

	public String getCprtCardLargeImgNmFile() {
		return cprtCardLargeImgNmFile;
	}
	@XmlElement
	public void setCprtCardLargeImgNmFile(String cprtCardLargeImgNmFile) {
		this.cprtCardLargeImgNmFile = cprtCardLargeImgNmFile;
	}

	public String getCprtCardNm() {
		return cprtCardNm;
	}
	@XmlElement
	public void setCprtCardNm(String cprtCardNm) {
		this.cprtCardNm = cprtCardNm;
	}

	public String getIsPlanBannerEnabled() {
		return isPlanBannerEnabled;
	}

	public void setIsPlanBannerEnabled(String isPlanBannerEnabled) {
		this.isPlanBannerEnabled = isPlanBannerEnabled;
	}

	public String getPlanBannerTitle() {
		return planBannerTitle;
	}

	public void setPlanBannerTitle(String planBannerTitle) {
		this.planBannerTitle = planBannerTitle;
	}

	public String getPlanBannerText() {
		return planBannerText;
	}

	public void setPlanBannerText(String planBannerText) {
		this.planBannerText = planBannerText;
	}
}
