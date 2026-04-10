package com.ktmmobile.mcp.search.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 추천검색어 관리 dto 
 */
public class SearchRecomDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String searchWord;   /** 검색어 */
    
	private int searchWordRank;   /** 1~30 */
	    
	private String useYn;   /** 코드값(Y : 유효 / N : 유효하지않음) */
	    
	private String pstngStartDate;   /** 게시시작일자 */
	    
	private String pstngEndDate;   /** 게시종료일자 */
	    
	private String cretIp;   /** 생성IP */
	    
	private String cretDt;   /** 생성일시 */
	    
	private String cretId;   /** 생성자ID */
	    
	private String amdIp;   /** 수정IP */
	    
	private String amdDt;   /** 수정일시 */
	    
	private String amdId;   /** 수정자ID */
	
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
	
	@Override
	public String toString() {
	  return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}	
	


}
