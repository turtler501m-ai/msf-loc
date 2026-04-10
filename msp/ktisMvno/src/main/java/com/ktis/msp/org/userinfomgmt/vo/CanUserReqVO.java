package com.ktis.msp.org.userinfomgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : CanUserReqVO
 * @Description : CAN 사용자 관리 REQ VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2017.06.09 TREXSHIN 최초생성
 * @
 * @author : TREXSHIN
 * @Create Date : 2017.06.09.
 */
public class CanUserReqVO extends BaseVo {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2996575756805753526L;
	
	private String searchStartDate = "";
	private String searchEndDate = "";
	private String searchCode = "";
	private String searchVal = "";
	
	public String getSearchStartDate() {
		return searchStartDate;
	}
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	public String getSearchEndDate() {
		return searchEndDate;
	}
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	public String getSearchCode() {
		return searchCode;
	}
	public void setSearchCode(String searchCode) {
		this.searchCode = searchCode;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	
}
