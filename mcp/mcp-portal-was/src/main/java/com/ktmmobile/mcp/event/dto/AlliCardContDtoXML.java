package com.ktmmobile.mcp.event.dto;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="item")
public class AlliCardContDtoXML {
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

	/**제휴카드 기본상세 */
	private int cprtCardBnfitSeq;
	private String cprtCardBnfitItemCd;
	private int sortKey;
	private String cprtCardItemImgNm;
	private String cprtCardItemNm;
	private String cprtCardItemDesc;

	/** 제휴카드 항목 */
	private String cprtCardItemCd; //제휴카드항목코드
	private int cprtCardGdncDtlSeq; // 제휴카드상세안내일련번호
	private int itemSortKey; // 항목정렬키

	private String fontStyleCd; //폰트스타일
	private String cprtCardItemSbst; //제휴카드항목내용

	/**제휴카드 링크 상세*/
	private int cprtCardLinkSeq;	/* 제휴카드링크일련번호*/
	private String linkCd;	 /*코드관리(CO0002)*/
	private String linkNm;	/* 링크명 */
	private String linkUrl;	/* 링크URL*/

	/**제휴카드 할인 금액 상세 */
	private int cprtCardDcAmtSeq; //제휴카드할인금액 일련번호
	private String dcFormlCd; //할인방식코드
	private String dcSectionStAmt;//할인구간시작금액
	private String dcSectionEndAmt;//할인구간종료금액
	private String dcAmt; //할인금액
	private int dcLimitAmt; //할인금액한도

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

	public int getCprtCardBnfitSeq() {
		return cprtCardBnfitSeq;
	}

	@XmlElement
	public void setCprtCardBnfitSeq(int cprtCardBnfitSeq) {
		this.cprtCardBnfitSeq = cprtCardBnfitSeq;
	}
	public String getCprtCardBnfitItemCd() {
		return cprtCardBnfitItemCd;
	}

	@XmlElement
	public void setCprtCardBnfitItemCd(String cprtCardBnfitItemCd) {
		this.cprtCardBnfitItemCd = cprtCardBnfitItemCd;
	}
	public int getSortKey() {
		return sortKey;
	}

	@XmlElement
	public void setSortKey(int sortKey) {
		this.sortKey = sortKey;
	}
	public String getCprtCardItemImgNm() {
		return cprtCardItemImgNm;
	}

	@XmlElement
	public void setCprtCardItemImgNm(String cprtCardItemImgNm) {
		this.cprtCardItemImgNm = cprtCardItemImgNm;
	}
	public String getCprtCardItemNm() {
		return cprtCardItemNm;
	}

	@XmlElement
	public void setCprtCardItemNm(String cprtCardItemNm) {
		this.cprtCardItemNm = cprtCardItemNm;
	}
	public String getCprtCardItemDesc() {
		return cprtCardItemDesc;
	}

	@XmlElement
	public void setCprtCardItemDesc(String cprtCardItemDesc) {
		this.cprtCardItemDesc = cprtCardItemDesc;
	}

	public String getCprtCardItemCd() {
		return cprtCardItemCd;
	}

	@XmlElement
	public void setCprtCardItemCd(String cprtCardItemCd) {
		this.cprtCardItemCd = cprtCardItemCd;
	}
	public int getCprtCardGdncDtlSeq() {
		return cprtCardGdncDtlSeq;
	}

	@XmlElement
	public void setCprtCardGdncDtlSeq(int cprtCardGdncDtlSeq) {
		this.cprtCardGdncDtlSeq = cprtCardGdncDtlSeq;
	}
	public int getItemSortKey() {
		return itemSortKey;
	}

	@XmlElement
	public void setItemSortKey(int itemSortKey) {
		this.itemSortKey = itemSortKey;
	}
	public String getFontStyleCd() {
		return fontStyleCd;
	}

	@XmlElement
	public void setFontStyleCd(String fontStyleCd) {
		this.fontStyleCd = fontStyleCd;
	}
	public String getCprtCardItemSbst() {
		return cprtCardItemSbst;
	}

	@XmlElement
	public void setCprtCardItemSbst(String cprtCardItemSbst) {
		this.cprtCardItemSbst = cprtCardItemSbst;
	}
	public int getCprtCardLinkSeq() {
		return cprtCardLinkSeq;
	}

	@XmlElement
	public void setCprtCardLinkSeq(int cprtCardLinkSeq) {
		this.cprtCardLinkSeq = cprtCardLinkSeq;
	}
	public String getLinkCd() {
		return linkCd;
	}

	@XmlElement
	public void setLinkCd(String linkCd) {
		this.linkCd = linkCd;
	}
	public String getLinkNm() {
		return linkNm;
	}

	@XmlElement
	public void setLinkNm(String linkNm) {
		this.linkNm = linkNm;
	}
	public String getLinkUrl() {
		return linkUrl;
	}

	@XmlElement
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public int getCprtCardDcAmtSeq() {
		return cprtCardDcAmtSeq;
	}

	@XmlElement
	public void setCprtCardDcAmtSeq(int cprtCardDcAmtSeq) {
		this.cprtCardDcAmtSeq = cprtCardDcAmtSeq;
	}
	public String getDcFormlCd() {
		return dcFormlCd;
	}

	@XmlElement
	public void setDcFormlCd(String dcFormlCd) {
		this.dcFormlCd = dcFormlCd;
	}
	public String getDcSectionStAmt() {
		return dcSectionStAmt;
	}

	@XmlElement
	public void setDcSectionStAmt(String dcSectionStAmt) {
		this.dcSectionStAmt = dcSectionStAmt;
	}
	public String getDcSectionEndAmt() {
		return dcSectionEndAmt;
	}

	@XmlElement
	public void setDcSectionEndAmt(String dcSectionEndAmt) {
		this.dcSectionEndAmt = dcSectionEndAmt;
	}
	public String getDcAmt() {
		return dcAmt;
	}
	@XmlElement
	public void setDcAmt(String dcAmt) {
		this.dcAmt = dcAmt;
	}
	public int getDcLimitAmt() {
		return dcLimitAmt;
	}
	@XmlElement
	public void setDcLimitAmt(int dcLimitAmt) {
		this.dcLimitAmt = dcLimitAmt;
	}
}
