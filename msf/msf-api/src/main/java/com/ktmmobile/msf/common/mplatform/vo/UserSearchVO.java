package com.ktmmobile.msf.common.mplatform.vo;


/**
 * @project : default
 * @file 	: UserSearchVO.java
 * @author	: HanNamSik
 * @date	: 2013. 4. 2. 오후 2:47:50
 * @history	:
 *
 * @comment :
 *
 *
 *
 */
public class UserSearchVO /*extends CommonVO*/{

	private String searchStatus;
	private String searchAuthCode;
	private String searchValue;
	private Integer searchGsUserGrpKey;
	private String searchAge;
	private String searchMemberFlag;
	private String searchNameFlag;
	private String searchUseridFlag;
	private String searchArea;
	private String searchSex;
	private String orderBy;
	private String sdate;
	private String edate;
	private Integer menuKey;

	/**
	 * @return the searchStatus
	 */
	public String getSearchStatus() {
		return searchStatus;
	}
	/**
	 * @param searchStatus the searchStatus to set
	 */
	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}
	/**
	 * @return the searchAuthCode
	 */
	public String getSearchAuthCode() {
		return searchAuthCode;
	}
	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}
	/**
	 * @param searchAuthCode the searchAuthCode to set
	 */
	public void setSearchAuthCode(String searchAuthCode) {
		this.searchAuthCode = searchAuthCode;
	}
	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}
	/**
	 * @return the searchGsUserGrpKey
	 */
	public Integer getSearchGsUserGrpKey() {
		return searchGsUserGrpKey;
	}
	/**
	 * @param searchGsUserGrpKey the searchGsUserGrpKey to set
	 */
	public void setSearchGsUserGrpKey(Integer searchGsUserGrpKey) {
		this.searchGsUserGrpKey = searchGsUserGrpKey;
	}
	/**
	 * @return the searchAge
	 */
	public String getSearchAge() {
		return searchAge;
	}
	/**
	 * @param searchAge the searchAge to set
	 */
	public void setSearchAge(String searchAge) {
		this.searchAge = searchAge;
	}
	/**
	 * @return the searchMemberFlag
	 */
	public String getSearchMemberFlag() {
		return searchMemberFlag;
	}
	/**
	 * @param searchMemberFlag the searchMemberFlag to set
	 */
	public void setSearchMemberFlag(String searchMemberFlag) {
		this.searchMemberFlag = searchMemberFlag;
	}
	/**
	 * @return the searchArea
	 */
	public String getSearchArea() {
		return searchArea;
	}
	/**
	 * @param searchArea the searchArea to set
	 */
	public void setSearchArea(String searchArea) {
		this.searchArea = searchArea;
	}

	/**
	 * @return the orderBy
	 */
	public String getOrderBy() {
		return orderBy;
	}
	/**
	 * @param orderBy the orderBy to set
	 */
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	/**
	 * @return the searchSex
	 */
	public String getSearchSex() {
		return searchSex;
	}
	/**
	 * @param searchSex the searchSex to set
	 */
	public void setSearchSex(String searchSex) {
		this.searchSex = searchSex;
	}

	public String getSearchNameFlag() {
		return searchNameFlag;
	}

	public void setSearchNameFlag(String searchNameFlag) {
		this.searchNameFlag = searchNameFlag;
	}

	public String getSearchUseridFlag() {
		return searchUseridFlag;
	}

	public void setSearchUseridFlag(String searchUseridFlag) {
		this.searchUseridFlag = searchUseridFlag;
	}
	public String getSdate() {
		return sdate;
	}
	public void setSdate(String sdate) {
		this.sdate = sdate;
	}
	public String getEdate() {
		return edate;
	}
	public void setEdate(String edate) {
		this.edate = edate;
	}
	public Integer getMenuKey() {
		return menuKey;
	}
	public void setMenuKey(Integer menuKey) {
		this.menuKey = menuKey;
	}


}
