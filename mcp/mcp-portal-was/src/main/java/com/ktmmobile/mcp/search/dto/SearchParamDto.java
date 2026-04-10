package com.ktmmobile.mcp.search.dto;

import java.io.Serializable;

public class SearchParamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String searchKeyword;
    private int searchTotal;
    private String searchCategory;
    private String searchRange;
    private String xmlYn;
    private String cretDate;
	private String userId;   /** 사용자ID */
	private String cretIp;   /** 생성IP */
	private String cretDt;   /** 생성일시 */
	private String cretId;   /** 생성자ID */
	private String amdIp;   /** 수정IP */
	private String amdDt;   /** 수정일시 */
	private String amdId;   /** 수정자ID */
    private int page;
    
    
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
	public String getCretDate() {
		return cretDate;
	}
	public void setCretDate(String cretDate) {
		this.cretDate = cretDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchCategory() {
		return searchCategory;
	}
	public void setSearchCategory(String searchCategory) {
		this.searchCategory = searchCategory;
	}
	public String getSearchRange() {
		return searchRange;
	}
	public void setSearchRange(String searchRange) {
		this.searchRange = searchRange;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getXmlYn() {
		return xmlYn;
	}
	public void setXmlYn(String xmlYn) {
		this.xmlYn = xmlYn;
	}
	public int getSearchTotal() {
		return searchTotal;
	}
	public void setSearchTotal(int searchTotal) {
		this.searchTotal = searchTotal;
	}
	
    
}
