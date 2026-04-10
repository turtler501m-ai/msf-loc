package com.ktis.msp.cmn.authmgmt.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**  
 * @Class Name : SttcMgmtVO.java
 * @Description : SttcMgmtVO Class
 * @Modification Information  
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2014.08.13   임지혜           최초생성
 * 
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 * 
 *  Copyright (C) by MOPAS All right reserved.
 */

public class AuthMgmtVO extends BaseVo implements Serializable {
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 7983199718132470857L;
	private String prgmId;
	private String menuId;
	private String prgmNm;
	private String prgmDesc;
	private String prgmUrl;
	private char usgYn;
	private String regstId;
	private String regstDttm;
	private String rvisnId;
	private String rvisnDttm;
	private String prgmType;
	
	/**
	 * 메뉴 정보 추가 (트리 그리드)
	 */
	private String rowIndex;
	private String id;
	private String menuLvl; 
	private String menuNm;
	private String menuDsc;
	private String hghrMenuId;
	private String algnSeq;
	private String lmtOrgnLvlCd;
	private String dutyNm;
	private String delYn;
	private String dutyId;
	private String url;
	private String menuLvlPrior;
	private String xmlkidsBo;
	private boolean xmlkids;

	private String searchMenuNm;
	private String searchMenuLvl;

	public String getPrgmId() {
		return prgmId;
	}


	public void setPrgmId(String prgmId) {
		this.prgmId = prgmId;
	}


	public String getMenuId() {
		return menuId;
	}


	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}


	public String getPrgmNm() {
		return prgmNm;
	}


	public void setPrgmNm(String prgmNm) {
		this.prgmNm = prgmNm;
	}


	public String getPrgmDesc() {
		return prgmDesc;
	}


	public void setPrgmDesc(String prgmDesc) {
		this.prgmDesc = prgmDesc;
	}


	public String getPrgmUrl() {
		return prgmUrl;
	}


	public void setPrgmUrl(String prgmUrl) {
		this.prgmUrl = prgmUrl;
	}


	public char getUsgYn() {
		return usgYn;
	}


	public void setUsgYn(char usgYn) {
		this.usgYn = usgYn;
	}


	public String getRegstId() {
		return regstId;
	}


	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}


	public String getRegstDttm() {
		return regstDttm;
	}


	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}


	public String getRvisnId() {
		return rvisnId;
	}


	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}


	public String getRvisnDttm() {
		return rvisnDttm;
	}


	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}


	public String getPrgmType() {
		return prgmType;
	}


	public void setPrgmType(String prgmType) {
		this.prgmType = prgmType;
	}


	public String getRowIndex() {
		return rowIndex;
	}


	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMenuLvl() {
		return menuLvl;
	}


	public void setMenuLvl(String menuLvl) {
		this.menuLvl = menuLvl;
	}


	public String getMenuNm() {
		return menuNm;
	}


	public void setMenuNm(String menuNm) {
		this.menuNm = menuNm;
	}


	public String getMenuDsc() {
		return menuDsc;
	}


	public void setMenuDsc(String menuDsc) {
		this.menuDsc = menuDsc;
	}


	public String getHghrMenuId() {
		return hghrMenuId;
	}


	public void setHghrMenuId(String hghrMenuId) {
		this.hghrMenuId = hghrMenuId;
	}


	public String getAlgnSeq() {
		return algnSeq;
	}


	public void setAlgnSeq(String algnSeq) {
		this.algnSeq = algnSeq;
	}


	public String getLmtOrgnLvlCd() {
		return lmtOrgnLvlCd;
	}


	public void setLmtOrgnLvlCd(String lmtOrgnLvlCd) {
		this.lmtOrgnLvlCd = lmtOrgnLvlCd;
	}


	public String getDutyNm() {
		return dutyNm;
	}


	public void setDutyNm(String dutyNm) {
		this.dutyNm = dutyNm;
	}


	public String getDelYn() {
		return delYn;
	}


	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}


	public String getDutyId() {
		return dutyId;
	}


	public void setDutyId(String dutyId) {
		this.dutyId = dutyId;
	}


	public String getUrl() {
		return url;
	}


	public void setUrl(String url) {
		this.url = url;
	}


	public String getMenuLvlPrior() {
		return menuLvlPrior;
	}


	public void setMenuLvlPrior(String menuLvlPrior) {
		this.menuLvlPrior = menuLvlPrior;
	}


	public String getXmlkidsBo() {
		return xmlkidsBo;
	}


	public void setXmlkidsBo(String xmlkidsBo) {
		this.xmlkidsBo = xmlkidsBo;
	}


	public boolean isXmlkids() {
		return xmlkids;
	}


	public void setXmlkids(boolean xmlkids) {
		this.xmlkids = xmlkids;
	}

	

	public String getSearchMenuNm() {
		return searchMenuNm;
	}


	public void setSearchMenuNm(String searchMenuNm) {
		this.searchMenuNm = searchMenuNm;
	}


	public String getSearchMenuLvl() {
		return searchMenuLvl;
	}


	public void setSearchMenuLvl(String searchMenuLvl) {
		this.searchMenuLvl = searchMenuLvl;
	}


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}

