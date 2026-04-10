package com.ktmmobile.mcp.main.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NmcpRecommendProdCtgBasDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	
	/** 추천상품카테고리코드 */
	private String recommendProdCtgCd;
	/** 추천상품카테고리명 */
	private String recommendProdCtgNm;
	/** 추천상품카테고리기본설명 */
	private String recommendProdCtgBasDesc;
	/** 추천상품카테고리상세설명 */
	private String recommendProdCtgDtlDesc;
	/** DEPTH키 */
	private int depthKey;
	/** 상위추천상품카테고리코드 */
	private String upRecommendProdCtgCd;
	/** 추천상품위치 */
	private String recommendProdOutputCd;
	/** 상품유형 */
	private String prodDivCd;
	/** 정렬우선순위 */
	private String sortOdrg;
	/** 사용유효여부 */
	private String useYn;
	/** 게시시작일 */
	private String pstngStartDate;
	/** 게시종료일 */
	private String pstngEndDate;

	/** 대표 이미지 경로 */
	private String recommendProdCtgImgPath ;

	/** URL 사용여부 */
	private String linkUseYn ;

	/** PC URL */
	private String linkUrlPc ;

	/**MOBILE URL*/
	private String linkUrlMo ;


	/** 등록IP */
    private Date cretIp;
	/** 등록일시 */
    private Date cretDt;
	 /** 등록자 아이디 */
    private String cretId;
    /** 수정IP */
    private Date amdIp;
    /** 수정일시 */
    private Date amdDt;
    /** 수정자 아이디 */
    private String amdId;
    
    //
    private List<NmcpRecommendProdCtgRelDTO> lists = new ArrayList<NmcpRecommendProdCtgRelDTO>();
    
	public String getRecommendProdCtgCd() {
		return recommendProdCtgCd;
	}
	public void setRecommendProdCtgCd(String recommendProdCtgCd) {
		this.recommendProdCtgCd = recommendProdCtgCd;
	}
	
	public String getRecommendProdCtgNm() {
		return recommendProdCtgNm;
	}
	public void setRecommendProdCtgNm(String recommendProdCtgNm) {
		this.recommendProdCtgNm = recommendProdCtgNm;
	}
	
	public String getRecommendProdCtgBasDesc() {
		return recommendProdCtgBasDesc;
	}
	public void setRecommendProdCtgBasDesc(String recommendProdCtgBasDesc) {
		this.recommendProdCtgBasDesc = recommendProdCtgBasDesc;
	}
	
	public String getRecommendProdCtgDtlDesc() {
		return recommendProdCtgDtlDesc;
	}
	public void setRecommendProdCtgDtlDesc(String recommendProdCtgDtlDesc) {
		this.recommendProdCtgDtlDesc = recommendProdCtgDtlDesc;
	}
	
	public int getDepthKey() {
		return depthKey;
	}
	public void setDepthKey(int depthKey) {
		this.depthKey = depthKey;
	}
	
	public String getUpRecommendProdCtgCd() {
		return upRecommendProdCtgCd;
	}
	public void setUpRecommendProdCtgCd(String upRecommendProdCtgCd) {
		this.upRecommendProdCtgCd = upRecommendProdCtgCd;
	}
	
	public String getRecommendProdOutputCd() {
		return recommendProdOutputCd;
	}
	public void setRecommendProdOutputCd(String recommendProdOutputCd) {
		this.recommendProdOutputCd = recommendProdOutputCd;
	}
	
	public String getProdDivCd() {
		return prodDivCd;
	}
	public void setProdDivCd(String prodDivCd) {
		this.prodDivCd = prodDivCd;
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
	
	public Date getCretIp() {
		return cretIp;
	}
	public void setCretIp(Date cretIp) {
		this.cretIp = cretIp;
	}
	
	public Date getCretDt() {
		return cretDt;
	}
	public void setCretDt(Date cretDt) {
		this.cretDt = cretDt;
	}
	
	public String getCretId() {
		return cretId;
	}
	public void setCretId(String cretId) {
		this.cretId = cretId;
	}
	
	public Date getAmdIp() {
		return amdIp;
	}
	public void setAmdIp(Date amdIp) {
		this.amdIp = amdIp;
	}
	
	public Date getAmdDt() {
		return amdDt;
	}
	public void setAmdDt(Date amdDt) {
		this.amdDt = amdDt;
	}
	
	public String getAmdId() {
		return amdId;
	}
	public void setAmdId(String amdId) {
		this.amdId = amdId;
	}
	
	public List<NmcpRecommendProdCtgRelDTO> getLists() {
		return lists;
	}
	public void setLists(List<NmcpRecommendProdCtgRelDTO> lists) {
		this.lists = lists;
	}

	public String getRecommendProdCtgImgPath() {
		return recommendProdCtgImgPath;
	}

	public void setRecommendProdCtgImgPath(String recommendProdCtgImgPath) {
		this.recommendProdCtgImgPath = recommendProdCtgImgPath;
	}

	public String getLinkUseYn() {
		return linkUseYn;
	}

	public void setLinkUseYn(String linkUseYn) {
		this.linkUseYn = linkUseYn;
	}

	public String getLinkUrlPc() {
		return linkUrlPc;
	}

	public void setLinkUrlPc(String linkUrlPc) {
		this.linkUrlPc = linkUrlPc;
	}

	public String getLinkUrlMo() {
		return linkUrlMo;
	}

	public void setLinkUrlMo(String linkUrlMo) {
		this.linkUrlMo = linkUrlMo;
	}
}
