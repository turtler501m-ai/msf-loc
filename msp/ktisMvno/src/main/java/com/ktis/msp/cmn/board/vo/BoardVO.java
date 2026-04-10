package com.ktis.msp.cmn.board.vo;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : BoardVO.java
 * @Description : BoardVO Class
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

public class BoardVO extends BaseVo implements Serializable {

	private static final long serialVersionUID = 3393564583412316437L;


	private String searchType;
	private String searchVal;
	private String stdrDt;
	private String userAuth;

	private String boardCd;
	private Integer srlNum;
	private String title;
	private String cntt;
	private String postStrtDt;
	private String postEndDt;
	private String delYn;
	private String orgnTpCd1;
	private String orgnTpCd2;
	private String orgnTpCd3;
	private String orgnTpCd4;
	private String orgnTpCd5;
	private String orgnId;
	private String regId;


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


	public String getSearchType() {
		return searchType;
	}
	public void setSearchType(String searchType) {
		this.searchType = searchType;
	}
	public String getSearchVal() {
		return searchVal;
	}
	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}
	public String getBoardCd() {
		return boardCd;
	}
	public void setBoardCd(String boardCd) {
		this.boardCd = boardCd;
	}
	public Integer getSrlNum() {
		return srlNum;
	}
	public void setSrlNum(Integer srlNum) {
		this.srlNum = srlNum;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCntt() {
		return cntt;
	}
	public void setCntt(String cntt) {
		this.cntt = cntt;
	}
	public String getPostStrtDt() {
		return postStrtDt;
	}
	public void setPostStrtDt(String postStrtDt) {
		this.postStrtDt = postStrtDt;
	}
	public String getPostEndDt() {
		return postEndDt;
	}
	public void setPostEndDt(String postEndDt) {
		this.postEndDt = postEndDt;
	}
	public String getDelYn() {
		return delYn;
	}
	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}
	public String getOrgnTpCd1() {
		return orgnTpCd1;
	}
	public void setOrgnTpCd1(String orgnTpCd1) {
		this.orgnTpCd1 = orgnTpCd1;
	}
	public String getOrgnTpCd2() {
		return orgnTpCd2;
	}
	public void setOrgnTpCd2(String orgnTpCd2) {
		this.orgnTpCd2 = orgnTpCd2;
	}
	public String getOrgnTpCd3() {
		return orgnTpCd3;
	}
	public void setOrgnTpCd3(String orgnTpCd3) {
		this.orgnTpCd3 = orgnTpCd3;
	}
	public String getOrgnTpCd4() {
		return orgnTpCd4;
	}
	public void setOrgnTpCd4(String orgnTpCd4) {
		this.orgnTpCd4 = orgnTpCd4;
	}
	public String getOrgnTpCd5() {
		return orgnTpCd5;
	}
	public void setOrgnTpCd5(String orgnTpCd5) {
		this.orgnTpCd5 = orgnTpCd5;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getStdrDt() {
		return stdrDt;
	}
	public void setStdrDt(String stdrDt) {
		this.stdrDt = stdrDt;
	}
	public String getUserAuth() {
		return userAuth;
	}
	public void setUserAuth(String userAuth) {
		this.userAuth = userAuth;
	}
	public String getOrgnId() {
		return orgnId;
	}
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
	}
	public String getRegId() {
		return regId;
	}
	public void setRegId(String regId) {
		this.regId = regId;
	}

}

