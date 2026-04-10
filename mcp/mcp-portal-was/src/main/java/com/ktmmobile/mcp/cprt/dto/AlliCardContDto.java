package com.ktmmobile.mcp.cprt.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;


public class AlliCardContDto implements Serializable {

	private static final long serialVersionUID = -7864517723912081002L;

	/**제휴카드 기본등록 */
	private boolean addCardCtgFlag;

	private String ctgCnt;
	private String cprtCardCtgCd;
	private String cprtCardCtgNm;
	
	private String sortOdrg;
	private int cprtCardGdncSeq;				/* 제휴카드안내일련번호 */
	private String cprtCardGdncNm;				/* 제휴카드명 */
	private String cprtCardCd;					/* 코드관리(Co0008) */
	private String cprtCardBasDesc;				/* 제휴카드기본설명 */
	private String cprtCardSimpleDesc;			/* 제휴카드간단설명 */
	private String cprtCardThumbImgNm;			/* 제휴카드섬네일이미지명 */
	private String cprtCardLargeImgNm;			/* 제휴카드대이미지명 */
	private String gdncFileNm;					/* 안내파일명 */
	private String useYn;						/* 코드값(Y : 유효 / N : 유효하지않음) */
	private String pstngStartDate;				/* 게시시작일자 */
	private String pstngEndDate	;				/* 게시종료일자 */
	
	private String cprtCardCtgBasDesc;
	private String cprtCardCtgDtlDesc;
	private String upCprtCardCtgCd;
	private String depthKey;
	
	private String cretIp;
	private String cretDt;
	private String cretId;
	private String amdIp;
	
	private String amdDt;
	private String amdId;
	private MultipartFile cprtCardThumbImgNmFile;
	private MultipartFile cprtCardLargeImgNmFile;

	/**제휴카드 기본상세 */
	private int cprtCardBnfitSeq;
	private String cprtCardBnfitItemCd;
	private int sortKey;
	private String cprtCardItemImgNm;
	private String cprtCardItemNm;
	private String cprtCardItemDesc;
	private List<MultipartFile> cprtCardItemImgNmFile;

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
	
	private List<AlliCardDcAmtDto> alliCardDcAmtDtoList;
	// 통신비할인금액
	private List<AlliCardContDtoXML> bnfitLists1 = new ArrayList<AlliCardContDtoXML>();
	// 연회비(할인적용기간)
	private List<AlliCardContDtoXML> bnfitLists2 = new ArrayList<AlliCardContDtoXML>();

	private String annualFeeItemDesc; //연회비 문구
	
	public int getCprtCardGdncSeq() {
		return cprtCardGdncSeq;
	}
	public void setCprtCardGdncSeq(int cprtCardGdncSeq) {
		this.cprtCardGdncSeq = cprtCardGdncSeq;
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
	public String getCprtCardSimpleDesc() {
		return cprtCardSimpleDesc;
	}
	public void setCprtCardSimpleDesc(String cprtCardSimpleDesc) {
		this.cprtCardSimpleDesc = cprtCardSimpleDesc;
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
	public String getCprtCardCtgNm() {
		return cprtCardCtgNm;
	}
	public void setCprtCardCtgNm(String cprtCardCtgNm) {
		this.cprtCardCtgNm = cprtCardCtgNm;
	}
	public String getUpCprtCardCtgCd() {
		return upCprtCardCtgCd;
	}
	public void setUpCprtCardCtgCd(String upCprtCardCtgCd) {
		this.upCprtCardCtgCd = upCprtCardCtgCd;
	}
	public String getDepthKey() {
		return depthKey;
	}
	public void setDepthKey(String depthKey) {
		this.depthKey = depthKey;
	}
	public String getCretIp() {
		return cretIp;
	}
	public void setCretIp(String cretIp) {
		this.cretIp = cretIp;
	}
	public String getCretDt() {
		return cretDt;
	}
	public void setCretDt(String cretDt) {
		this.cretDt = cretDt;
	}
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	public String getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(String amdIp) {
		this.amdIp = amdIp;
	}
	public String getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(String amdDt) {
		this.amdDt = amdDt;
	}
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
	public MultipartFile getCprtCardThumbImgNmFile() {
		return cprtCardThumbImgNmFile;
	}
	public void setCprtCardThumbImgNmFile(MultipartFile cprtCardThumbImgNmFile) {
		this.cprtCardThumbImgNmFile = cprtCardThumbImgNmFile;
	}
	public MultipartFile getCprtCardLargeImgNmFile() {
		return cprtCardLargeImgNmFile;
	}
	public void setCprtCardLargeImgNmFile(MultipartFile cprtCardLargeImgNmFile) {
		this.cprtCardLargeImgNmFile = cprtCardLargeImgNmFile;
	}
	public int getCprtCardBnfitSeq() {
		return cprtCardBnfitSeq;
	}
	public void setCprtCardBnfitSeq(int cprtCardBnfitSeq) {
		this.cprtCardBnfitSeq = cprtCardBnfitSeq;
	}
	public String getCprtCardBnfitItemCd() {
		return cprtCardBnfitItemCd;
	}
	public void setCprtCardBnfitItemCd(String cprtCardBnfitItemCd) {
		this.cprtCardBnfitItemCd = cprtCardBnfitItemCd;
	}
	public int getSortKey() {
		return sortKey;
	}
	public void setSortKey(int sortKey) {
		this.sortKey = sortKey;
	}
	public String getCprtCardItemImgNm() {
		return cprtCardItemImgNm;
	}
	public void setCprtCardItemImgNm(String cprtCardItemImgNm) {
		this.cprtCardItemImgNm = cprtCardItemImgNm;
	}
	public String getCprtCardItemNm() {
		return cprtCardItemNm;
	}
	public void setCprtCardItemNm(String cprtCardItemNm) {
		this.cprtCardItemNm = cprtCardItemNm;
	}
	public String getCprtCardItemDesc() {
		return cprtCardItemDesc;
	}
	public void setCprtCardItemDesc(String cprtCardItemDesc) {
		this.cprtCardItemDesc = cprtCardItemDesc;
	}
	public List<MultipartFile> getCprtCardItemImgNmFile() {
		return cprtCardItemImgNmFile;
	}
	public void setCprtCardItemImgNmFile(List<MultipartFile> cprtCardItemImgNmFile) {
		this.cprtCardItemImgNmFile = cprtCardItemImgNmFile;
	}
	public String getCprtCardItemCd() {
		return cprtCardItemCd;
	}
	public void setCprtCardItemCd(String cprtCardItemCd) {
		this.cprtCardItemCd = cprtCardItemCd;
	}
	public int getCprtCardGdncDtlSeq() {
		return cprtCardGdncDtlSeq;
	}
	public void setCprtCardGdncDtlSeq(int cprtCardGdncDtlSeq) {
		this.cprtCardGdncDtlSeq = cprtCardGdncDtlSeq;
	}
	public int getItemSortKey() {
		return itemSortKey;
	}
	public void setItemSortKey(int itemSortKey) {
		this.itemSortKey = itemSortKey;
	}
	public String getFontStyleCd() {
		return fontStyleCd;
	}
	public void setFontStyleCd(String fontStyleCd) {
		this.fontStyleCd = fontStyleCd;
	}
	public String getCprtCardItemSbst() {
		return cprtCardItemSbst;
	}
	public void setCprtCardItemSbst(String cprtCardItemSbst) {
		this.cprtCardItemSbst = cprtCardItemSbst;
	}
	public int getCprtCardLinkSeq() {
		return cprtCardLinkSeq;
	}
	public void setCprtCardLinkSeq(int cprtCardLinkSeq) {
		this.cprtCardLinkSeq = cprtCardLinkSeq;
	}
	public String getLinkCd() {
		return linkCd;
	}
	public void setLinkCd(String linkCd) {
		this.linkCd = linkCd;
	}
	public String getLinkNm() {
		return linkNm;
	}
	public void setLinkNm(String linkNm) {
		this.linkNm = linkNm;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
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
	public boolean isAddCardCtgFlag() {
		return addCardCtgFlag;
	}
	public void setAddCardCtgFlag(boolean addCardCtgFlag) {
		this.addCardCtgFlag = addCardCtgFlag;
	}
	public String getSortOdrg() {
		return sortOdrg;
	}
	public void setSortOdrg(String sortOdrg) {
		this.sortOdrg = sortOdrg;
	}
	public String getCprtCardCtgCd() {
		return cprtCardCtgCd;
	}
	public void setCprtCardCtgCd(String cprtCardCtgCd) {
		this.cprtCardCtgCd = cprtCardCtgCd;
	}
	public String getCtgCnt() {
		return ctgCnt;
	}
	public void setCtgCnt(String ctgCnt) {
		this.ctgCnt = ctgCnt;
	}
	public List<AlliCardDcAmtDto> getAlliCardDcAmtDtoList() {
		return alliCardDcAmtDtoList;
	}
	public void setAlliCardDcAmtDtoList(List<AlliCardDcAmtDto> alliCardDcAmtDtoList) {
		this.alliCardDcAmtDtoList = alliCardDcAmtDtoList;
	}

	public List<AlliCardContDtoXML> getBnfitLists1() {
		return bnfitLists1;
	}
	public void setBnfitLists1(List<AlliCardContDtoXML> bnfitLists1) {
		this.bnfitLists1 = bnfitLists1;
	}
	public List<AlliCardContDtoXML> getBnfitLists2() {
		return bnfitLists2;
	}
	public void setBnfitLists2(List<AlliCardContDtoXML> bnfitLists2) {
		this.bnfitLists2 = bnfitLists2;
	}

	public String getAnnualFeeItemDesc() {
		return annualFeeItemDesc;
	}

	public void setAnnualFeeItemDesc(String annualFeeItemDesc) {
		this.annualFeeItemDesc = annualFeeItemDesc;
	}
}
