/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ktis.msp.pps.orgmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : FaxHstVO
 * @Description : 펙스 이력 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.14 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 14.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="faxHstVO")
public class FaxHstVO extends BaseVo implements Serializable{
	
    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 2497114198411602808L;
	
	private String orgnId; /** 조직 ID */
    private String rvisnId; /** 수정자ID */    
    private String rvisnNm; /** 수정자명 */
    private String fax; /** FAX번호 */
    private String fax1; /** FAX번호 */
    private String fax2; /** FAX번호 */
    private String fax3; /** FAX번호 */
    private String orgnNm;
    private String searchStartDate;
    private String searchEndDate;
    private String faxSeqNum;
    private String regId;
    private String applStrtDttm;
    private String regDttm;
    private String rvisnDttm;
    private String applCmpltDttm;
    private String rprsenNm;
    private int pageIndex;
    private int pageSize;

    @Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/**
	 * @return the orgnId
	 */
	public String getOrgnId() {
		return orgnId;
	}

	/**
	 * @param orgnId the orgnId to set
	 */
	public void setOrgnId(String orgnId) {
		this.orgnId = orgnId;
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
	

	public String getRvisnNm() {
        return rvisnNm;
    }

    public void setRvisnNm(String rvisnNm) {
        this.rvisnNm = rvisnNm;
    }

    /**
	 * @return the fax
	 */
	public String getFax() {
		return fax;
	}

	/**
	 * @param fax the fax to set
	 */
	public void setFax(String fax) {
		this.fax = fax;
	}

	
	public String getFax1() {
        return fax1;
    }

    public void setFax1(String fax1) {
        this.fax1 = fax1;
    }

    public String getFax2() {
        return fax2;
    }

    public void setFax2(String fax2) {
        this.fax2 = fax2;
    }

    public String getFax3() {
        return fax3;
    }

    public void setFax3(String fax3) {
        this.fax3 = fax3;
    }

    /**
	 * @return the orgnNm
	 */
	public String getOrgnNm() {
		return orgnNm;
	}

	/**
	 * @param orgnNm the orgnNm to set
	 */
	public void setOrgnNm(String orgnNm) {
		this.orgnNm = orgnNm;
	}

	/**
	 * @return the searchStartDate
	 */
	public String getSearchStartDate() {
		return searchStartDate;
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

	/**
	 * @param searchEndDate the searchEndDate to set
	 */
	public void setSearchEndDate(String searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	/**
	 * @return the faxSeqNum
	 */
	public String getFaxSeqNum() {
		return faxSeqNum;
	}

	/**
	 * @param faxSeqNum the faxSeqNum to set
	 */
	public void setFaxSeqNum(String faxSeqNum) {
		this.faxSeqNum = faxSeqNum;
	}

	/**
	 * @return the regId
	 */
	public String getRegId() {
		return regId;
	}

	/**
	 * @param regId the regId to set
	 */
	public void setRegId(String regId) {
		this.regId = regId;
	}

	/**
	 * @return the applStrtDttm
	 */
	public String getApplStrtDttm() {
		return applStrtDttm;
	}

	/**
	 * @param applStrtDttm the applStrtDttm to set
	 */
	public void setApplStrtDttm(String applStrtDttm) {
		this.applStrtDttm = applStrtDttm;
	}

	/**
	 * @return the regDttm
	 */
	public String getRegDttm() {
		return regDttm;
	}

	/**
	 * @param regDttm the regDttm to set
	 */
	public void setRegDttm(String regDttm) {
		this.regDttm = regDttm;
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
	 * @return the applCmpltDttm
	 */
	public String getApplCmpltDttm() {
		return applCmpltDttm;
	}

	/**
	 * @param applCmpltDttm the applCmpltDttm to set
	 */
	public void setApplCmpltDttm(String applCmpltDttm) {
		this.applCmpltDttm = applCmpltDttm;
	}

	/**
	 * @return the rprsenNm
	 */
	public String getRprsenNm() {
		return rprsenNm;
	}

	/**
	 * @param rprsenNm the rprsenNm to set
	 */
	public void setRprsenNm(String rprsenNm) {
		this.rprsenNm = rprsenNm;
	}

	/**
	 * @return the pageIndex
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	/**
	 * @param pageIndex the pageIndex to set
	 */
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
    
    
}
