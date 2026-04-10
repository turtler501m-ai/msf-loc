package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;

public class RateAdsvcGdncBasDTO implements Serializable{

    private static final long serialVersionUID = 1L;
	
    /////// 요금제 부가서비스 안내 관리 //////////////////////    
	/** 요금제부가서비스안내일련번호 */    
	private int rateAdsvcGdncSeq;
	/** 요금제부가서비스구분 */
	private String rateAdsvcDivCd;
	/** 요금제구분코드 */
	private String rateDivCd;
	/** 요금제부가서비스구분명 */
	private String rateAdsvcDivNm;
	/** 요금제부가서비스명 */
	private String rateAdsvcNm;
	/** 요금제부가서비스추가명 */
	private String rateAdsvcApdNm;
	/** 요금제부가서비스기본설명 */
	private String rateAdsvcBasDesc;	
	/** 요금제부가서비스이미지명 */
	private String rateAdsvcImgNm;	
	/** 월기본금액설명 */
	private String mmBasAmtDesc;	
	/** 월기본금액vat(포함)설명 */
	private String mmBasAmtVatDesc;	
    /** 프로모션요금설명 */
	private String promotionAmtDesc;	
    /** 프로모션요금vat(포함)설명 */
	private String promotionAmtVatDesc;	
	
	/** 약정시 기본료 */
	private String contractAmtVatDesc;
	/** 요금할인 시 기본료 */
	private String rateDiscntAmtVatDesc;
	/** 시니어 할인 기본료 */
	private String seniorDiscntAmtVatDesc;
	
    /** 안내파일명(xml) */
	private String gdncFileNm;	
	/** 사용유효여부 */
	private String useYn;
	/** 게시시작일 */
	private String pstngStartDate;
	/** 게시종료일 */
	private String pstngEndDate;	
    
    ////////////////////////////////////////////////
    /** 등록아이피 */
	private String cretIp;
	/** 생성일시 */
	private String cretDt;
	/** 생성자아이디 */
	private String cretId;
	/** 변경아이피 */
	private String amdIp;
	/** 수정일시 */
	private String amdDt;
	/** 수정자아이디 */
	private String amdId;
	
    
    //////// 페이징 설정 //////////////////////////////
    /**  */
    private int rownum;
    /**  */
    private int pageNo=0;
    /** 페이지 시작 변수 */
    private int pagingStartNo;
    /** 페이지 끝 변수 */
    private int pagingEndNo;
    /** 페이지 사이즈 */
    private int pagingSize;
    
	////////검색 설정 //////////////////////////////
	private String searchRrateAdsvcDivCd;
	private String searchRateAdsvcCtgCd1;
	private String searchRateAdsvcCtgCd2;
	private String searchInput;

    //////// 기타 설정 //////////////////////////////
    /** I:쓰기, U:수정 */
    private String modIu;
    /** 중복 유무 */
    private String dupYn;
    /** 카테고리  갯수 */
    private String ctgCnt;
        
	public int getRateAdsvcGdncSeq() {
		return rateAdsvcGdncSeq;
	}
	public void setRateAdsvcGdncSeq(int rateAdsvcGdncSeq) {
		this.rateAdsvcGdncSeq = rateAdsvcGdncSeq;
	}
	
	public String getRateAdsvcDivCd() {
		return rateAdsvcDivCd;
	}
	public void setRateAdsvcDivCd(String rateAdsvcDivCd) {
		this.rateAdsvcDivCd = rateAdsvcDivCd;
	}
	
	public String getRateDivCd() {
		return rateDivCd;
	}
	public void setRateDivCd(String rateDivCd) {
		this.rateDivCd = rateDivCd;
	}
	
	public String getRateAdsvcDivNm() {
		return rateAdsvcDivNm;
	}
	public void setRateAdsvcDivNm(String rateAdsvcDivNm) {
		this.rateAdsvcDivNm = rateAdsvcDivNm;
	}
	
	public String getRateAdsvcNm() {
		return rateAdsvcNm;
	}
	public void setRateAdsvcNm(String rateAdsvcNm) {
		this.rateAdsvcNm = rateAdsvcNm;
	}
		
	public String getRateAdsvcApdNm() {
		return rateAdsvcApdNm;
	}
	public void setRateAdsvcApdNm(String rateAdsvcApdNm) {
		this.rateAdsvcApdNm = rateAdsvcApdNm;
	}
	
	public String getRateAdsvcBasDesc() {
		return rateAdsvcBasDesc;
	}
	public void setRateAdsvcBasDesc(String rateAdsvcBasDesc) {
		this.rateAdsvcBasDesc = rateAdsvcBasDesc;
	}
	
	public String getRateAdsvcImgNm() {
		return rateAdsvcImgNm;
	}
	public void setRateAdsvcImgNm(String rateAdsvcImgNm) {
		this.rateAdsvcImgNm = rateAdsvcImgNm;
	}
	
	public String getMmBasAmtDesc() {
		return mmBasAmtDesc;
	}
	public void setMmBasAmtDesc(String mmBasAmtDesc) {
		this.mmBasAmtDesc = mmBasAmtDesc;
	}
	
	public String getMmBasAmtVatDesc() {
		return mmBasAmtVatDesc;
	}
	public void setMmBasAmtVatDesc(String mmBasAmtVatDesc) {
		this.mmBasAmtVatDesc = mmBasAmtVatDesc;
	}
	
	public String getPromotionAmtDesc() {
		return promotionAmtDesc;
	}
	public void setPromotionAmtDesc(String promotionAmtDesc) {
		this.promotionAmtDesc = promotionAmtDesc;
	}
	
	public String getPromotionAmtVatDesc() {
		return promotionAmtVatDesc;
	}
	public void setPromotionAmtVatDesc(String promotionAmtVatDesc) {
		this.promotionAmtVatDesc = promotionAmtVatDesc;
	}
	
	public String getContractAmtVatDesc() {
		return contractAmtVatDesc;
	}
	public void setContractAmtVatDesc(String contractAmtVatDesc) {
		this.contractAmtVatDesc = contractAmtVatDesc;
	}
	public String getRateDiscntAmtVatDesc() {
		return rateDiscntAmtVatDesc;
	}
	public void setRateDiscntAmtVatDesc(String rateDiscntAmtVatDesc) {
		this.rateDiscntAmtVatDesc = rateDiscntAmtVatDesc;
	}
	public String getSeniorDiscntAmtVatDesc() {
		return seniorDiscntAmtVatDesc;
	}
	public void setSeniorDiscntAmtVatDesc(String seniorDiscntAmtVatDesc) {
		this.seniorDiscntAmtVatDesc = seniorDiscntAmtVatDesc;
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
	
	public String getModIu() {
		return modIu;
	}
	public void setModIu(String modIu) {
		this.modIu = modIu;
	}
	
	public int getRownum() {
		return rownum;
	}
	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	
	public int getPagingStartNo() {
		return pagingStartNo;
	}
	public void setPagingStartNo(int pagingStartNo) {
		this.pagingStartNo = pagingStartNo;
	}
	
	public int getPagingEndNo() {
		return pagingEndNo;
	}
	public void setPagingEndNo(int pagingEndNo) {
		this.pagingEndNo = pagingEndNo;
	}
	
	public String getDupYn() {
		return dupYn;
	}
	public void setDupYn(String dupYn) {
		this.dupYn = dupYn;
	}
	
	public String getCtgCnt() {
		return ctgCnt;
	}
	public void setCtgCnt(String ctgCnt) {
		this.ctgCnt = ctgCnt;
	}
	
	public int getPagingSize() {
		return pagingSize;
	}
	public void setPagingSize(int pagingSize) {
		this.pagingSize = pagingSize;
	}
	
	public String getSearchRrateAdsvcDivCd() {
		return searchRrateAdsvcDivCd;
	}
	public void setSearchRrateAdsvcDivCd(String searchRrateAdsvcDivCd) {
		this.searchRrateAdsvcDivCd = searchRrateAdsvcDivCd;
	}
	
	public String getSearchRateAdsvcCtgCd1() {
		return searchRateAdsvcCtgCd1;
	}
	public void setSearchRateAdsvcCtgCd1(String searchRateAdsvcCtgCd1) {
		this.searchRateAdsvcCtgCd1 = searchRateAdsvcCtgCd1;
	}
	
	public String getSearchRateAdsvcCtgCd2() {
		return searchRateAdsvcCtgCd2;
	}
	public void setSearchRateAdsvcCtgCd2(String searchRateAdsvcCtgCd2) {
		this.searchRateAdsvcCtgCd2 = searchRateAdsvcCtgCd2;
	}
	
	public String getSearchInput() {
		return searchInput;
	}
	public void setSearchInput(String searchInput) {
		this.searchInput = searchInput;
	}
}
