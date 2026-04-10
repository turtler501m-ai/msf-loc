package com.ktmmobile.mcp.search.dto;

import java.io.Serializable;


public class SearchParamDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String searchKeyword;
    
    private String searchType;
    private String searchTerm;
    private String searchTerm1;
    private String searchTerm2;
    private String searchRange;
    private int page;
    
	public String getSearchKeyword() {
		return searchKeyword;
	}
	public void setSearchKeyword(String searchKeyword) {
		this.searchKeyword = searchKeyword;
	}
	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchTerm() {
		return searchTerm;
	}
	public void setSearchTerm(String searchTerm) {
		this.searchTerm = searchTerm;
	}
	public String getSearchTerm1() {
		return searchTerm1;
	}
	public void setSearchTerm1(String searchTerm1) {
		this.searchTerm1 = searchTerm1;
	}
	public String getSearchTerm2() {
		return searchTerm2;
	}
	public void setSearchTerm2(String searchTerm2) {
		this.searchTerm2 = searchTerm2;
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
	
}
