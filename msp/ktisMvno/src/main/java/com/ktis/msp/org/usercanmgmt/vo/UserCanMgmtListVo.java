package com.ktis.msp.org.usercanmgmt.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : UserCanMgmtVo
 * @Description : 해지자 관리 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2015.08.18 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2015. 8. 18.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="userCanMgmtVo")
public class UserCanMgmtListVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 3538837071592352274L;

    private String userid; /** 사용자 ID */
    private String name; /** 사용자 명 */
    private String mobileNo; /** 핸드폰 번호 */
    private String firstLoginDt; /** 최초 로그인 일시 */
    private String lastLoginDt; /** 최종 로그인 일시 */
    private String email; /** 이메일 */
    private String address; /** 주소 */
    private String addressDtl; /** 상세 주소 */
    private String zipcd; /** 우편 번호 */
    private String resultYn; /** 처리여부 */
    private String regstId; /** 등록자 ID */
    private String regstDttm;/** 등록일시 */
    private String rvisnId; /** 수정자 ID */
    private String rvisnNm; /** 수정자명 */
    private String rvisnDttm;/** 수정일시 */
    
    private String searchStartDt; /** 조회시작일자 */
    private String searchEndDt; /** 조회종료일자 */
    
    
    private String sessionUserId;
    
    List<UserCanMgmtVo> items;
    
    
    
	/**
	 * @return the userid
	 */
	public String getUserid() {
		return userid;
	}
	/**
	 * @param userid the userid to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	/**
	 * @return the firstLoginDt
	 */
	public String getFirstLoginDt() {
		return firstLoginDt;
	}
	/**
	 * @param firstLoginDt the firstLoginDt to set
	 */
	public void setFirstLoginDt(String firstLoginDt) {
		this.firstLoginDt = firstLoginDt;
	}
	/**
	 * @return the lastLoginDt
	 */
	public String getLastLoginDt() {
		return lastLoginDt;
	}
	/**
	 * @param lastLoginDt the lastLoginDt to set
	 */
	public void setLastLoginDt(String lastLoginDt) {
		this.lastLoginDt = lastLoginDt;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}
	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}
	/**
	 * @return the addressDtl
	 */
	public String getAddressDtl() {
		return addressDtl;
	}
	/**
	 * @param addressDtl the addressDtl to set
	 */
	public void setAddressDtl(String addressDtl) {
		this.addressDtl = addressDtl;
	}
	/**
	 * @return the zipcd
	 */
	public String getZipcd() {
		return zipcd;
	}
	/**
	 * @param zipcd the zipcd to set
	 */
	public void setZipcd(String zipcd) {
		this.zipcd = zipcd;
	}
	/**
	 * @return the resultYn
	 */
	public String getResultYn() {
		return resultYn;
	}
	/**
	 * @param resultYn the resultYn to set
	 */
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}
	/**
	 * @return the regstId
	 */
	public String getRegstId() {
		return regstId;
	}
	/**
	 * @param regstId the regstId to set
	 */
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	/**
	 * @return the regstDttm
	 */
	public String getRegstDttm() {
		return regstDttm;
	}
	/**
	 * @param regstDttm the regstDttm to set
	 */
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	/**
	 * @return the rvisnId
	 */
	public String getRvisnId() {
		return rvisnId;
	}
	/**
	 * @param rvisnId the rvisnId to set
	 */
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	/**
	 * @return the rvisnNm
	 */
	public String getRvisnNm() {
		return rvisnNm;
	}
	/**
	 * @param rvisnNm the rvisnNm to set
	 */
	public void setRvisnNm(String rvisnNm) {
		this.rvisnNm = rvisnNm;
	}
	/**
	 * @return the rvisnDttm
	 */
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	/**
	 * @param rvisnDttm the rvisnDttm to set
	 */
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
	/**
	 * @return the searchStartDt
	 */
	public String getSearchStartDt() {
		return searchStartDt;
	}
	/**
	 * @param searchStartDt the searchStartDt to set
	 */
	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}
	/**
	 * @return the searchEndDt
	 */
	public String getSearchEndDt() {
		return searchEndDt;
	}
	/**
	 * @param searchEndDt the searchEndDt to set
	 */
	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}
	/**
	 * @return the sessionUserId
	 */
	public String getSessionUserId() {
		return sessionUserId;
	}
	/**
	 * @param sessionUserId the sessionUserId to set
	 */
	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}
	/**
	 * @return the items
	 */
	public List<UserCanMgmtVo> getItems() {
		return items;
	}
	/**
	 * @param items the items to set
	 */
	public void setItems(List<UserCanMgmtVo> items) {
		this.items = items;
	}
}
