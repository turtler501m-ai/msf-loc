package com.ktmmobile.mcp.search.dto;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
/**
 * 연관검색어 관리 dto 
 */
public class SearchCrrlDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String searchWord;   /** 검색어 */
    
	private String crrlSearchWord;   /** 연관검색어 */
	    
	private String crrlSearchWordDivCd;   /** 코드관리(SE0001) */
	    
	private int crrlSearchWordDtlGdncSeq;   /** 연관검색어상세안내일련번호 */
	    
	private String useYn;   /** 코드값(Y : 유효 / N : 유효하지않음) */
	    
	private String pstngStartDate;   /** 게시시작일자 */
	    
	private String pstngEndDate;   /** 게시종료일자 */
	    
	private String cretIp;   /** 생성IP */
	    
	private String cretDt;   /** 생성일시 */
	    
	private String cretId;   /** 생성자ID */
	    
	private String amdIp;   /** 수정IP */
	    
	private String amdDt;   /** 수정일시 */
	    
	private String amdId;   /** 수정자ID */
	
	
	
	private String duplYn;   // 중복체크 여부	
	
	//페이징
	private int rnum; 
    private int skipResult;   // 셀렉트 하지 않고 뛰어넘을 만큼의 rownum
    private int maxResult;    // Pagesize
    private int pageNo;
    private int pagingPosition;		//<<  <   1 2 3 4 5   >  >>
    private int pagingStartNo;		//페이지네이션 시작 변수
    private int pagingEndNo;		//페이지네이션 끝 변수
    private int pagingStart;		//페이지 처음 <<
    private int pagingFront;		//페이지 앞을호 <
    private int pagingNext;			//페이지 다음 >
    private int pagingEnd;			//페이지 마지막 >>
    private int pagingSize;			//페이지 사이즈>>
	
    
    
    
	public String getDuplYn() {
		return duplYn;
	}

	public void setDuplYn(String duplYn) {
		this.duplYn = duplYn;
	}

	public int getRnum() {
		return rnum;
	}

	public void setRnum(int rnum) {
		this.rnum = rnum;
	}

	public int getSkipResult() {
		return skipResult;
	}

	public void setSkipResult(int skipResult) {
		this.skipResult = skipResult;
	}

	public int getMaxResult() {
		return maxResult;
	}

	public void setMaxResult(int maxResult) {
		this.maxResult = maxResult;
	}

	public int getPageNo() {
		return pageNo;
	}

	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}

	public int getPagingPosition() {
		return pagingPosition;
	}

	public void setPagingPosition(int pagingPosition) {
		this.pagingPosition = pagingPosition;
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

	public int getPagingStart() {
		return pagingStart;
	}

	public void setPagingStart(int pagingStart) {
		this.pagingStart = pagingStart;
	}

	public int getPagingFront() {
		return pagingFront;
	}

	public void setPagingFront(int pagingFront) {
		this.pagingFront = pagingFront;
	}

	public int getPagingNext() {
		return pagingNext;
	}

	public void setPagingNext(int pagingNext) {
		this.pagingNext = pagingNext;
	}

	public int getPagingEnd() {
		return pagingEnd;
	}

	public void setPagingEnd(int pagingEnd) {
		this.pagingEnd = pagingEnd;
	}

	public int getPagingSize() {
		return pagingSize;
	}

	public void setPagingSize(int pagingSize) {
		this.pagingSize = pagingSize;
	}

	public String getSearchWord() {
		return searchWord;
	}

	public void setSearchWord(String searchWord) {
		this.searchWord = searchWord;
	}

	public String getCrrlSearchWord() {
		return crrlSearchWord;
	}

	public void setCrrlSearchWord(String crrlSearchWord) {
		this.crrlSearchWord = crrlSearchWord;
	}

	public String getCrrlSearchWordDivCd() {
		return crrlSearchWordDivCd;
	}

	public void setCrrlSearchWordDivCd(String crrlSearchWordDivCd) {
		this.crrlSearchWordDivCd = crrlSearchWordDivCd;
	}

	public int getCrrlSearchWordDtlGdncSeq() {
		return crrlSearchWordDtlGdncSeq;
	}

	public void setCrrlSearchWordDtlGdncSeq(int crrlSearchWordDtlGdncSeq) {
		this.crrlSearchWordDtlGdncSeq = crrlSearchWordDtlGdncSeq;
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
