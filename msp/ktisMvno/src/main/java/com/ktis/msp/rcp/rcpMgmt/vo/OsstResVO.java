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
package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

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
public class OsstResVO extends BaseVo implements Serializable {
	
	public String osstOrdNo;
	public String rsltCd;	// S:정상접수, F:접수에러
	public String rsltMsg;
	// 번호조회
	public String tlphNoStatCd;
	public String asgnAgncId;
	public String tlphNoOwnCmncCmpnCd;
	public String openSvcIndCd;
	public String encdTlphNo;
	public String tlphNo;
	public String fvrtnoAqcsPsblYn;
	public String rsrvCustNo;
	public String statMntnEndPrrnDate;
	public String tlphNoChrcCd;
	public String tlphNoStatChngDt;
	public String tlphNoUseCd;
	public String tlphNoUseMntCd;
	// 상태조회
	public String mvnoOrdNo;
	public String prgrStatCd;
	public String prgrStatNm;
	public String custId;
	public String svcCntrNo;
	public String rsltDt;
	public String nstepGlobalId;
	public String prdcChkNotiMsg;
	//납부주장
	private String npNchrgAmt;
	private String npPnltAmt;
	private String npUnpayAmt;
	private String npHndstInstAmt;
	//번호이동사전인증
	public String npTlphNo;
	public String moveCompany;
	public String cstmrType;
	public String selfCertType;
	public String cstmrName;
	public String custIdntNo;
	public String crprNo;
	public String cntpntShopId;
	
	public String getOsstOrdNo() {
		return osstOrdNo;
	}
	public void setOsstOrdNo(String osstOrdNo) {
		this.osstOrdNo = osstOrdNo;
	}
	public String getRsltCd() {
		return rsltCd;
	}
	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}
	public String getRsltMsg() {
		return rsltMsg;
	}
	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}
	public String getTlphNoStatCd() {
		return tlphNoStatCd;
	}
	public void setTlphNoStatCd(String tlphNoStatCd) {
		this.tlphNoStatCd = tlphNoStatCd;
	}
	public String getAsgnAgncId() {
		return asgnAgncId;
	}
	public void setAsgnAgncId(String asgnAgncId) {
		this.asgnAgncId = asgnAgncId;
	}
	public String getTlphNoOwnCmncCmpnCd() {
		return tlphNoOwnCmncCmpnCd;
	}
	public void setTlphNoOwnCmncCmpnCd(String tlphNoOwnCmncCmpnCd) {
		this.tlphNoOwnCmncCmpnCd = tlphNoOwnCmncCmpnCd;
	}
	public String getOpenSvcIndCd() {
		return openSvcIndCd;
	}
	public void setOpenSvcIndCd(String openSvcIndCd) {
		this.openSvcIndCd = openSvcIndCd;
	}
	public String getEncdTlphNo() {
		return encdTlphNo;
	}
	public void setEncdTlphNo(String encdTlphNo) {
		this.encdTlphNo = encdTlphNo;
	}
	public String getTlphNo() {
		return tlphNo;
	}
	public void setTlphNo(String tlphNo) {
		this.tlphNo = tlphNo;
	}
	public String getFvrtnoAqcsPsblYn() {
		return fvrtnoAqcsPsblYn;
	}
	public void setFvrtnoAqcsPsblYn(String fvrtnoAqcsPsblYn) {
		this.fvrtnoAqcsPsblYn = fvrtnoAqcsPsblYn;
	}
	public String getRsrvCustNo() {
		return rsrvCustNo;
	}
	public void setRsrvCustNo(String rsrvCustNo) {
		this.rsrvCustNo = rsrvCustNo;
	}
	public String getStatMntnEndPrrnDate() {
		return statMntnEndPrrnDate;
	}
	public void setStatMntnEndPrrnDate(String statMntnEndPrrnDate) {
		this.statMntnEndPrrnDate = statMntnEndPrrnDate;
	}
	public String getTlphNoChrcCd() {
		return tlphNoChrcCd;
	}
	public void setTlphNoChrcCd(String tlphNoChrcCd) {
		this.tlphNoChrcCd = tlphNoChrcCd;
	}
	public String getTlphNoStatChngDt() {
		return tlphNoStatChngDt;
	}
	public void setTlphNoStatChngDt(String tlphNoStatChngDt) {
		this.tlphNoStatChngDt = tlphNoStatChngDt;
	}
	public String getTlphNoUseCd() {
		return tlphNoUseCd;
	}
	public void setTlphNoUseCd(String tlphNoUseCd) {
		this.tlphNoUseCd = tlphNoUseCd;
	}
	public String getTlphNoUseMntCd() {
		return tlphNoUseMntCd;
	}
	public void setTlphNoUseMntCd(String tlphNoUseMntCd) {
		this.tlphNoUseMntCd = tlphNoUseMntCd;
	}
	public String getMvnoOrdNo() {
		return mvnoOrdNo;
	}
	public void setMvnoOrdNo(String mvnoOrdNo) {
		this.mvnoOrdNo = mvnoOrdNo;
	}
	public String getPrgrStatCd() {
		return prgrStatCd;
	}
	public void setPrgrStatCd(String prgrStatCd) {
		this.prgrStatCd = prgrStatCd;
	}
	public String getPrgrStatNm() {
		return prgrStatNm;
	}
	public void setPrgrStatNm(String prgrStatNm) {
		this.prgrStatNm = prgrStatNm;
	}
	public String getCustId() {
		return custId;
	}
	public void setCustId(String custId) {
		this.custId = custId;
	}
	public String getSvcCntrNo() {
		return svcCntrNo;
	}
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}
	public String getRsltDt() {
		return rsltDt;
	}
	public void setRsltDt(String rsltDt) {
		this.rsltDt = rsltDt;
	}
	public String getNstepGlobalId() {
		return nstepGlobalId;
	}
	public void setNstepGlobalId(String nstepGlobalId) {
		this.nstepGlobalId = nstepGlobalId;
	}
	public String getPrdcChkNotiMsg() {
		return prdcChkNotiMsg;
	}
	public void setPrdcChkNotiMsg(String prdcChkNotiMsg) {
		this.prdcChkNotiMsg = prdcChkNotiMsg;
	}
	public String getNpNchrgAmt() {
		return npNchrgAmt;
	}
	public void setNpNchrgAmt(String npNchrgAmt) {
		this.npNchrgAmt = npNchrgAmt;
	}
	public String getNpPnltAmt() {
		return npPnltAmt;
	}
	public void setNpPnltAmt(String npPnltAmt) {
		this.npPnltAmt = npPnltAmt;
	}
	public String getNpUnpayAmt() {
		return npUnpayAmt;
	}
	public void setNpUnpayAmt(String npUnpayAmt) {
		this.npUnpayAmt = npUnpayAmt;
	}
	public String getNpHndstInstAmt() {
		return npHndstInstAmt;
	}
	public void setNpHndstInstAmt(String npHndstInstAmt) {
		this.npHndstInstAmt = npHndstInstAmt;
	}
	public String getNpTlphNo() {
		return npTlphNo;
	}
	public void setNpTlphNo(String npTlphNo) {
		this.npTlphNo = npTlphNo;
	}
	public String getMoveCompany() {
		return moveCompany;
	}
	public void setMoveCompany(String moveCompany) {
		this.moveCompany = moveCompany;
	}
	public String getCstmrType() {
		return cstmrType;
	}
	public void setCstmrType(String cstmrType) {
		this.cstmrType = cstmrType;
	}
	public String getSelfCertType() {
		return selfCertType;
	}
	public void setSelfCertType(String selfCertType) {
		this.selfCertType = selfCertType;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getCustIdntNo() {
		return custIdntNo;
	}
	public void setCustIdntNo(String custIdntNo) {
		this.custIdntNo = custIdntNo;
	}
	public String getCrprNo() {
		return crprNo;
	}
	public void setCrprNo(String crprNo) {
		this.crprNo = crprNo;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	
	
}
