package com.ktis.msp.rcp.old.vo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="adviceVo")
public class RequestOldVo extends CommonVO  implements Serializable {
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;


	
	private String searchServiceType;
	
	private String searchPstate;
	
	private String searchRequestStateCode;
	
	private String searchStartDate;
	
	private String searchEndDate;
	
	private String searchAgentCode;
	
	private String searchOnlineAuthType;
	
	private String searchSelect;
	
	private String searchValue;
	
	private String searchCntpntShopId;
	
	private String requestKey;
	
	private int currentPageNo;
	
	private int totalRecordCount;
	
	/**
	 * @return the requestKey
	 */
	public String getRequestKey() {
		return requestKey;
	}
	/**
	 * @param requestKey the requestKey to set
	 */
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	/**
	 * @return the searchCntpntShopId
	 */
	public String getSearchCntpntShopId() {
		return searchCntpntShopId;
	}
	/**
	 * @param searchCntpntShopId the searchCntpntShopId to set
	 */
	public void setSearchCntpntShopId(String searchCntpntShopId) {
		this.searchCntpntShopId = searchCntpntShopId;
	}
	/**
	 * @return the searchServiceType
	 */
	public String getSearchServiceType() {
		return searchServiceType;
	}
	/**
	 * @param searchServiceType the searchServiceType to set
	 */
	public void setSearchServiceType(String searchServiceType) {
		this.searchServiceType = searchServiceType;
	}
	/**
	 * @return the searchPstate
	 */
	public String getSearchPstate() {
		return searchPstate;
	}
	/**
	 * @param searchPstate the searchPstate to set
	 */
	public void setSearchPstate(String searchPstate) {
		this.searchPstate = searchPstate;
	}
	/**
	 * @return the seachRequestStateCode
	 */
	public String getSearchRequestStateCode() {
		return searchRequestStateCode;
	}
	/**
	 * @param seachRequestStateCode the seachRequestStateCode to set
	 */
	public void setSearchRequestStateCode(String seachRequestStateCode) {
		this.searchRequestStateCode = seachRequestStateCode;
	}
	/**
	 * @return the searchSelect
	 */
	public String getSearchSelect() {
		return searchSelect;
	}
	/**
	 * @param searchSelect the searchSelect to set
	 */
	public void setSearchSelect(String searchSelect) {
		this.searchSelect = searchSelect;
	}
	/**
	 * @return the searchValue
	 */
	public String getSearchValue() {
		return searchValue;
	}
	/**
	 * @param searchValue the searchValue to set
	 */
	public void setSearchValue(String searchValue) {
		this.searchValue = searchValue;
	}

	/**
	 * @return the searchAgentCode
	 */
	public String getSearchAgentCode() {
		return searchAgentCode;
	}
	/**
	 * @param searchAgentCode the searchAgentCode to set
	 */
	public void setSearchAgentCode(String searchAgentCode) {
		this.searchAgentCode = searchAgentCode;
	}

	/**
	 * @return the searchOnlineAuthType
	 */
	public String getSearchOnlineAuthType() {
		return searchOnlineAuthType;
	}
	/**
	 * @param searchOnlineAuthType the searchOnlineAuthType to set
	 */
	public void setSearchOnlineAuthType(String searchOnlineAuthType) {
		this.searchOnlineAuthType = searchOnlineAuthType;
	}
	
	/**
	 * @return the searchStartDate
	 */
	public String getSearchStartDate() {
		return searchStartDate;
	}
	
	public String getSearchStartDateOnlyNumber() {
		return searchStartDate.replaceAll("[-./]", "");
	}
	/**
	 * @param searchStartDate the searchStartDate to set
	 */
	public void setSearchStartDate(String searchStartDate) {
		this.searchStartDate = searchStartDate;
	}
	/**
	 * @return the searchEndDate
	 */
	public String getSearchEndDate() {
		return searchEndDate;
	}
	
	public String getSearchEndDateOnlyNumber() {
		return searchEndDate.replaceAll("[-./]", "");
	}
	/**
	 * @param searchEndDate the searchEndDate to set
	 */
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}
	/**
	 * @return the currentPageNo
	 */
	public int getCurrentPageNo() {
		return currentPageNo;
	}
	/**
	 * @param currentPageNo the currentPageNo to set
	 */
	public void setCurrentPageNo(int currentPageNo) {
		this.currentPageNo = currentPageNo;
	}
	/**
	 * @return the totalRecordCount
	 */
	public int getTotalRecordCount() {
		return totalRecordCount;
	}
	/**
	 * @param totalRecordCount the totalRecordCount to set
	 */
	public void setTotalRecordCount(int totalRecordCount) {
		this.totalRecordCount = totalRecordCount;
	}

}
