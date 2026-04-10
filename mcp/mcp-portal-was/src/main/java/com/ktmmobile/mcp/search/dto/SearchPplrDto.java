package com.ktmmobile.mcp.search.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 인기검색어 관리 dto 
 */
public class SearchPplrDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String cretDate;   /** YYYYMMDD 형식으로 일자별 30개 관리 */
    
	private String searchWord;   /** 검색어 */
	    
	private int searchWordRank;   /** 1~30 */
	    
	private int searchRepeatCnt;   /** 검색반복횟수 */
	    
	private int searchRiseNval;   /** -30 ~ 30 */
	    
	private String cretIp;   /** 생성IP */
	    
	private String cretDt;   /** 생성일시 */
	    
	private String cretId;   /** 생성자ID */
	    
	private String amdIp;   /** 수정IP */
	    
	private String amdDt;   /** 수정일시 */
	    
	private String amdId;   /** 수정자ID */

	public String getCretDate() {
		return cretDate;
	}

	public void setCretDate(String cretDate) {
		this.cretDate = cretDate;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public int getSearchWordRank() {
		return searchWordRank;
	}

	public void setSearchWordRank(int searchWordRank) {
		this.searchWordRank = searchWordRank;
	}

	public int getSearchRepeatCnt() {
		return searchRepeatCnt;
	}

	public void setSearchRepeatCnt(int searchRepeatCnt) {
		this.searchRepeatCnt = searchRepeatCnt;
	}

	public int getSearchRiseNval() {
		return searchRiseNval;
	}

	public void setSearchRiseNval(int searchRiseNval) {
		this.searchRiseNval = searchRiseNval;
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
	
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}


}
