package com.ktis.msp.org.powertmntmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PowerTmntMgmtVo
 * @Description : 직권 해지 조회 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="powerTmntMgmtVo")
public class PowerTmntMgmtVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = -6555446188018454363L;
	
    private String slsCmpnCd; /** 판매회사코드 */
    private int svcCntrNo; /** 서비스계약번호 */
    private int billAcntNo; /** 청구계정번호 */
    private int custNo; /** 고객번호 */
    private String tlphNo; /** 전화번호 */
    private String custNm; /** 고객명 */
    private String openDt; /** 개통일시 */
    private String svcCntrStopDt; /** 이용정지일시  */
    private String bankNm; /** 은행코드명 */
    private String bnkacnNo; /** 가상계좌번호 */
    private String prdcNm; /** 상품명 */
    private String address; /**주소  */
    private String zipNo; /** 우편번호 */
    private String empNm; /** 담당자명  */
    private String telNo1; /** 담당자연락처  */
    private String inputDate; /** 데이터 추출일자 */
    private int unpdAmnt; /** 미납금액 */

    private String reqBuyTypeNm; /** 구매유형*/
    private String orgNm; /** 대리점*/
    private String fstUsimOrgNm; /** 유심접점*/

    private String searchStartDt; /** 조회시작일자 */
    private String searchEndDt; /** 조회종료일자 */
    private String strtYm;
    private String endYm;
    private String searchGb;
    private String searchVal;

    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
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
	 * @return the slsCmpnCd
	 */
	public String getSlsCmpnCd() {
		return slsCmpnCd;
	}


	/**
	 * @param slsCmpnCd the slsCmpnCd to set
	 */
	public void setSlsCmpnCd(String slsCmpnCd) {
		this.slsCmpnCd = slsCmpnCd;
	}


	/**
	 * @return the svcCntrNo
	 */
	public int getSvcCntrNo() {
		return svcCntrNo;
	}


	/**
	 * @param svcCntrNo the svcCntrNo to set
	 */
	public void setSvcCntrNo(int svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}


	/**
	 * @return the billAcntNo
	 */
	public int getBillAcntNo() {
		return billAcntNo;
	}


	/**
	 * @param billAcntNo the billAcntNo to set
	 */
	public void setBillAcntNo(int billAcntNo) {
		this.billAcntNo = billAcntNo;
	}


	/**
	 * @return the custNo
	 */
	public int getCustNo() {
		return custNo;
	}


	/**
	 * @param custNo the custNo to set
	 */
	public void setCustNo(int custNo) {
		this.custNo = custNo;
	}


	/**
	 * @return the tlphNo
	 */
	public String getTlphNo() {
		return tlphNo;
	}


	/**
	 * @param tlphNo the tlphNo to set
	 */
	public void setTlphNo(String tlphNo) {
		this.tlphNo = tlphNo;
	}


	/**
	 * @return the custNm
	 */
	public String getCustNm() {
		return custNm;
	}


	/**
	 * @param custNm the custNm to set
	 */
	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}


	/**
	 * @return the openDt
	 */
	public String getOpenDt() {
		return openDt;
	}


	/**
	 * @param openDt the openDt to set
	 */
	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}


	/**
	 * @return the svcCntrStopDt
	 */
	public String getSvcCntrStopDt() {
		return svcCntrStopDt;
	}


	/**
	 * @param svcCntrStopDt the svcCntrStopDt to set
	 */
	public void setSvcCntrStopDt(String svcCntrStopDt) {
		this.svcCntrStopDt = svcCntrStopDt;
	}


	/**
	 * @return the bankNm
	 */
	public String getBankNm() {
		return bankNm;
	}


	/**
	 * @param bankNm the bankNm to set
	 */
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}


	/**
	 * @return the bnkacnNo
	 */
	public String getBnkacnNo() {
		return bnkacnNo;
	}


	/**
	 * @param bnkacnNo the bnkacnNo to set
	 */
	public void setBnkacnNo(String bnkacnNo) {
		this.bnkacnNo = bnkacnNo;
	}


	/**
	 * @return the prdcNm
	 */
	public String getPrdcNm() {
		return prdcNm;
	}


	/**
	 * @param prdcNm the prdcNm to set
	 */
	public void setPrdcNm(String prdcNm) {
		this.prdcNm = prdcNm;
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
	 * @return the zipNo
	 */
	public String getZipNo() {
		return zipNo;
	}


	/**
	 * @param zipNo the zipNo to set
	 */
	public void setZipNo(String zipNo) {
		this.zipNo = zipNo;
	}


	/**
	 * @return the empNm
	 */
	public String getEmpNm() {
		return empNm;
	}


	/**
	 * @param empNm the empNm to set
	 */
	public void setEmpNm(String empNm) {
		this.empNm = empNm;
	}


	/**
	 * @return the telNo1
	 */
	public String getTelNo1() {
		return telNo1;
	}


	/**
	 * @param telNo1 the telNo1 to set
	 */
	public void setTelNo1(String telNo1) {
		this.telNo1 = telNo1;
	}


	/**
	 * @return the inputDate
	 */
	public String getInputDate() {
		return inputDate;
	}


	/**
	 * @param inputDate the inputDate to set
	 */
	public void setInputDate(String inputDate) {
		this.inputDate = inputDate;
	}


	/**
	 * @return the unpdAmnt
	 */
	public int getUnpdAmnt() {
		return unpdAmnt;
	}


	/**
	 * @param unpdAmnt the unpdAmnt to set
	 */
	public void setUnpdAmnt(int unpdAmnt) {
		this.unpdAmnt = unpdAmnt;
	}


	public String getStrtYm() {
		return strtYm;
	}


	public void setStrtYm(String strtYm) {
		this.strtYm = strtYm;
	}


	public String getEndYm() {
		return endYm;
	}


	public void setEndYm(String endYm) {
		this.endYm = endYm;
	}


	public String getSearchGb() {
		return searchGb;
	}


	public void setSearchGb(String searchGb) {
		this.searchGb = searchGb;
	}


	public String getSearchVal() {
		return searchVal;
	}


	public void setSearchVal(String searchVal) {
		this.searchVal = searchVal;
	}


	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}


	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
	}


	public String getOrgNm() {
		return orgNm;
	}


	public void setOrgNm(String orgNm) {
		this.orgNm = orgNm;
	}


	public String getFstUsimOrgNm() {
		return fstUsimOrgNm;
	}


	public void setFstUsimOrgNm(String fstUsimOrgNm) {
		this.fstUsimOrgNm = fstUsimOrgNm;
	}

	
}
