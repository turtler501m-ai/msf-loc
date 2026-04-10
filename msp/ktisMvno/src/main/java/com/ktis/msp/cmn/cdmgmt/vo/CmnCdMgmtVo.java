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
package com.ktis.msp.cmn.cdmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : SampleDefaultVO.java
 * @Description : SampleDefaultVO Class
 * @Modification Information
 * @
 * @  수정일      수정자              수정내용
 * @ ---------   ---------   -------------------------------
 * @ 2009.03.16           최초생성
 *
 * @author 개발프레임웍크 실행환경 개발팀
 * @since 2009. 03.16
 * @version 1.0
 * @see
 *
 *  Copyright (C) by MOPAS All right reserved.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="cmnCdMgmtVo")
public class CmnCdMgmtVo extends BaseVo implements Serializable {

	/**
	 *  serialVersion UID
	 */
	private static final long serialVersionUID = -858838578081269359L;

	/** cdVal */
    private String cdVal = "";

    /** cdDsc */
    private String cdDsc = "";
    
    private String grpId;			/** 그룹ID */
	private String etc1;			/** 기타1 */
	private String etc2;			/** 기타2 */
	private String etc3;			/** 기타3 */
	private String etc4;			/** 기타4 */
	private String etc5;			/** 기타5 */
	private String etc6;			/** 기타6 */

	private String orderBy;
	
    
	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getCdVal() {
		return cdVal;
	}

	public void setCdVal(String cdVal) {
		this.cdVal = cdVal;
	}

	public String getCdDsc() {
		return cdDsc;
	}

	public void setCdDsc(String cdDsc) {
		this.cdDsc = cdDsc;
	}
		
	public String getGrpId() {
		return grpId;
	}

	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}

	public String getEtc1() {
		return etc1;
	}

	public void setEtc1(String etc1) {
		this.etc1 = etc1;
	}

	public String getEtc2() {
		return etc2;
	}

	public void setEtc2(String etc2) {
		this.etc2 = etc2;
	}

	public String getEtc3() {
		return etc3;
	}

	public void setEtc3(String etc3) {
		this.etc3 = etc3;
	}

	public String getEtc4() {
		return etc4;
	}

	public void setEtc4(String etc4) {
		this.etc4 = etc4;
	}

	public String getEtc5() {
		return etc5;
	}

	public void setEtc5(String etc5) {
		this.etc5 = etc5;
	}

	public String getEtc6() {
		return etc6;
	}

	public void setEtc6(String etc6) {
		this.etc6 = etc6;
	}

	public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
	
	

}
