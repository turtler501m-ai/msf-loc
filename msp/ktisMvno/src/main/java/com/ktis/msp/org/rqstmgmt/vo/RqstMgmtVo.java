package com.ktis.msp.org.rqstmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PowerTmntMgmtVo
 * @Description : 청구/수납 조회 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.22 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 22.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rqstMgmtVo")
public class RqstMgmtVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 2535255822850142912L;

	
    private int billPymnSeqNum; /** 청구수납일련번호 */
    private String fileNm; /** 파일명 */
    private String billYm; /** 청구월 */
    private String svcCntrNo; /** 서비스계약번호 */
    private String contractNum; /** 계약번호 */
    private String billAcntNo; /** 청구계정번호 */
    private String slsCmpnCd; /** 판매회사코드 */
    private String duedatDate; /** 납기일자 */
    private String aplyDate; /** 적용일자 */
    private String blpymDate; /** 실수납일자 */
    private String pymnCd; /** 수납코드 */
    private String billItemCd; /** 청구항목코드 */
    private String itemCdNm; /** 청구항목코드명 */
    private String pymnMthdCd; /** 수납방법코드 */
    private String pymnMthdNm; /** 수납방법명 */
    private String pymnDtlMthdCd; /** 수납세부방법코드 */
    private String pymnDtlMthdNm; /** 수납세부방법명 */
    private String pymnAgncId; /** 수납대리점ID */
    private String adjsRsnCd; /** 조정사유코드 */
    private String adjsRsnNm; /** 조정사유명 */
    private String billRflcIndCd; /** 청구반영구분코드 */
    private int pymnAmnt; /** 청구수납금액 */
    private int vatAmnt; /** 부가세 */
    private String rvisnDttm; /** 등록일시 */
    
    /*사용량 조회*/
    private String mvnoSaleId; /*MVNO판매자코드*/
    private String useDate; /*사용일*/
    private String serviceGubnNm;
    private String prepayTypeNm;
    private String networkType;
    private String rateName;
    private String featureCdNm;
    private String userStartTime;
    private String userStopTime;
    private int count;
    private int packetAmount;
    private int rcAmount;
    private int ocAmount;
    
    /*번호이동 내역 조회*/
    private String mnpSeqNum;
    private String infoTypeCd; /**업무구분  CMN0038*/
    private String mnpDate; /**번호이동일자*/
    private String mnpTypeCd; /**번호이동구분  CMN0039 */
    private String cmncCmpnCd; /**통신회사코드 */
    private String sttsYm; /**정산월 */
    private String mnpCnt; /**정산건수 */
    private String mnpAmnt; /**정산금액 */
    private String payGarntDate; /**지불보증지급일자 */
    private String crdtCardCmsnAmnt; /**신용카드수수료금액 */
    private String hndsetBillAmnt; /**단말기금액 */
    private String infoType; /**업무구분*/
    private String mnpType; /**번호이동구분 */
    private String searchStartDt; /** 검색시작일자 */
    private String searchEndDt; /** 검색종료일자 */
    
    /*청구상세(TAX&VAT용) 조회*/
    private String pblsDate;         /**작성일자 */
    private String bizrRgstNo;       /**등록번호 */
//    private String billAcntNo;       /**청구계정(BAN) */
    private String soCd;             /**판매자코드 */
    private String gubunCode;        /**서비스구분 01:온라인,81:선불,82후불 */
    private String eseroType;        /**전자(세금)계산서종류 01:일반,02:영세율 */
    private String tlphNo;           /**대표번호 */
    private int splyPricAmt;         /**공급가액 */
    private int vatAmt;              /**세액 */
    private int vndrNm ;              /**업체명 */
    private int rprsPrsnNm;              /**대표자명 */
    
    private String svcCntrNoYn; /** 서비스계약번호 유무 */
    

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}


	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}


	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}


	/**
	 * @return the billPymnSeqNum
	 */
	public int getBillPymnSeqNum() {
		return billPymnSeqNum;
	}


	/**
	 * @param billPymnSeqNum the billPymnSeqNum to set
	 */
	public void setBillPymnSeqNum(int billPymnSeqNum) {
		this.billPymnSeqNum = billPymnSeqNum;
	}


	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return fileNm;
	}


	/**
	 * @param fileNm the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}


	/**
	 * @return the svcCntrNo
	 */
	public String getSvcCntrNo() {
		return svcCntrNo;
	}


	/**
	 * @param svcCntrNo the svcCntrNo to set
	 */
	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}


	/**
	 * @return the billAcntNo
	 */
	public String getBillAcntNo() {
		return billAcntNo;
	}


	/**
	 * @param billAcntNo the billAcntNo to set
	 */
	public void setBillAcntNo(String billAcntNo) {
		this.billAcntNo = billAcntNo;
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
	 * @return the billYm
	 */
	public String getBillYm() {
		return billYm;
	}


	/**
	 * @param billYm the billYm to set
	 */
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}


	/**
	 * @return the duedatDate
	 */
	public String getDuedatDate() {
		return duedatDate;
	}


	/**
	 * @param duedatDate the duedatDate to set
	 */
	public void setDuedatDate(String duedatDate) {
		this.duedatDate = duedatDate;
	}


	/**
	 * @return the aplyDate
	 */
	public String getAplyDate() {
		return aplyDate;
	}


	/**
	 * @param aplyDate the aplyDate to set
	 */
	public void setAplyDate(String aplyDate) {
		this.aplyDate = aplyDate;
	}


	/**
	 * @return the blpymDate
	 */
	public String getBlpymDate() {
		return blpymDate;
	}


	/**
	 * @param blpymDate the blpymDate to set
	 */
	public void setBlpymDate(String blpymDate) {
		this.blpymDate = blpymDate;
	}


	/**
	 * @return the pymnCd
	 */
	public String getPymnCd() {
		return pymnCd;
	}


	/**
	 * @param pymnCd the pymnCd to set
	 */
	public void setPymnCd(String pymnCd) {
		this.pymnCd = pymnCd;
	}


	/**
	 * @return the billItemCd
	 */
	public String getBillItemCd() {
		return billItemCd;
	}


	/**
	 * @param billItemCd the billItemCd to set
	 */
	public void setBillItemCd(String billItemCd) {
		this.billItemCd = billItemCd;
	}


	/**
	 * @return the itemCdNm
	 */
	public String getItemCdNm() {
		return itemCdNm;
	}


	/**
	 * @param itemCdNm the itemCdNm to set
	 */
	public void setItemCdNm(String itemCdNm) {
		this.itemCdNm = itemCdNm;
	}


	/**
	 * @return the pymnMthdCd
	 */
	public String getPymnMthdCd() {
		return pymnMthdCd;
	}


	/**
	 * @param pymnMthdCd the pymnMthdCd to set
	 */
	public void setPymnMthdCd(String pymnMthdCd) {
		this.pymnMthdCd = pymnMthdCd;
	}


	/**
	 * @return the pymnMthdNm
	 */
	public String getPymnMthdNm() {
		return pymnMthdNm;
	}


	/**
	 * @param pymnMthdNm the pymnMthdNm to set
	 */
	public void setPymnMthdNm(String pymnMthdNm) {
		this.pymnMthdNm = pymnMthdNm;
	}


	/**
	 * @return the pymnDtlMthdCd
	 */
	public String getPymnDtlMthdCd() {
		return pymnDtlMthdCd;
	}


	/**
	 * @param pymnDtlMthdCd the pymnDtlMthdCd to set
	 */
	public void setPymnDtlMthdCd(String pymnDtlMthdCd) {
		this.pymnDtlMthdCd = pymnDtlMthdCd;
	}


	/**
	 * @return the pymnDtlMthdNm
	 */
	public String getPymnDtlMthdNm() {
		return pymnDtlMthdNm;
	}


	/**
	 * @param pymnDtlMthdNm the pymnDtlMthdNm to set
	 */
	public void setPymnDtlMthdNm(String pymnDtlMthdNm) {
		this.pymnDtlMthdNm = pymnDtlMthdNm;
	}


	/**
	 * @return the pymnAgncId
	 */
	public String getPymnAgncId() {
		return pymnAgncId;
	}


	/**
	 * @param pymnAgncId the pymnAgncId to set
	 */
	public void setPymnAgncId(String pymnAgncId) {
		this.pymnAgncId = pymnAgncId;
	}


	/**
	 * @return the adjsRsnCd
	 */
	public String getAdjsRsnCd() {
		return adjsRsnCd;
	}


	/**
	 * @param adjsRsnCd the adjsRsnCd to set
	 */
	public void setAdjsRsnCd(String adjsRsnCd) {
		this.adjsRsnCd = adjsRsnCd;
	}


	/**
	 * @return the adjsRsnNm
	 */
	public String getAdjsRsnNm() {
		return adjsRsnNm;
	}


	/**
	 * @param adjsRsnNm the adjsRsnNm to set
	 */
	public void setAdjsRsnNm(String adjsRsnNm) {
		this.adjsRsnNm = adjsRsnNm;
	}


	/**
	 * @return the billRflcIndCd
	 */
	public String getBillRflcIndCd() {
		return billRflcIndCd;
	}


	/**
	 * @param billRflcIndCd the billRflcIndCd to set
	 */
	public void setBillRflcIndCd(String billRflcIndCd) {
		this.billRflcIndCd = billRflcIndCd;
	}


	/**
	 * @return the pymnAmnt
	 */
	public int getPymnAmnt() {
		return pymnAmnt;
	}


	/**
	 * @param pymnAmnt the pymnAmnt to set
	 */
	public void setPymnAmnt(int pymnAmnt) {
		this.pymnAmnt = pymnAmnt;
	}


	/**
	 * @return the vatAmnt
	 */
	public int getVatAmnt() {
		return vatAmnt;
	}


	/**
	 * @param vatAmnt the vatAmnt to set
	 */
	public void setVatAmnt(int vatAmnt) {
		this.vatAmnt = vatAmnt;
	}


	public String getMvnoSaleId() {
		return mvnoSaleId;
	}


	public void setMvnoSaleId(String mvnoSaleId) {
		this.mvnoSaleId = mvnoSaleId;
	}


	public String getUseDate() {
		return useDate;
	}


	public void setUseDate(String useDate) {
		this.useDate = useDate;
	}


	public String getServiceGubnNm() {
		return serviceGubnNm;
	}


	public void setServiceGubnNm(String serviceGubnNm) {
		this.serviceGubnNm = serviceGubnNm;
	}


	public String getPrepayTypeNm() {
		return prepayTypeNm;
	}


	public void setPrepayTypeNm(String prepayTypeNm) {
		this.prepayTypeNm = prepayTypeNm;
	}


	public String getNetworkType() {
		return networkType;
	}


	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}


	public String getRateName() {
		return rateName;
	}


	public void setRateName(String rateName) {
		this.rateName = rateName;
	}


	public String getFeatureCdNm() {
		return featureCdNm;
	}


	public void setFeatureCdNm(String featureCdNm) {
		this.featureCdNm = featureCdNm;
	}


	public String getUserStartTime() {
		return userStartTime;
	}


	public void setUserStartTime(String userStartTime) {
		this.userStartTime = userStartTime;
	}


	public String getUserStopTime() {
		return userStopTime;
	}


	public void setUserStopTime(String userStopTime) {
		this.userStopTime = userStopTime;
	}


	public int getCount() {
		return count;
	}


	public void setCount(int count) {
		this.count = count;
	}


	public int getPacketAmount() {
		return packetAmount;
	}


	public void setPacketAmount(int packetAmount) {
		this.packetAmount = packetAmount;
	}


	public int getRcAmount() {
		return rcAmount;
	}


	public void setRcAmount(int rcAmount) {
		this.rcAmount = rcAmount;
	}


	public int getOcAmount() {
		return ocAmount;
	}


	public void setOcAmount(int ocAmount) {
		this.ocAmount = ocAmount;
	}


	public String getMnpSeqNum() {
		return mnpSeqNum;
	}


	public void setMnpSeqNum(String mnpSeqNum) {
		this.mnpSeqNum = mnpSeqNum;
	}


	public String getInfoTypeCd() {
		return infoTypeCd;
	}


	public void setInfoTypeCd(String infoTypeCd) {
		this.infoTypeCd = infoTypeCd;
	}


	public String getMnpDate() {
		return mnpDate;
	}


	public void setMnpDate(String mnpDate) {
		this.mnpDate = mnpDate;
	}


	public String getMnpTypeCd() {
		return mnpTypeCd;
	}


	public void setMnpTypeCd(String mnpTypeCd) {
		this.mnpTypeCd = mnpTypeCd;
	}


	public String getCmncCmpnCd() {
		return cmncCmpnCd;
	}


	public void setCmncCmpnCd(String cmncCmpnCd) {
		this.cmncCmpnCd = cmncCmpnCd;
	}


	public String getSttsYm() {
		return sttsYm;
	}


	public void setSttsYm(String sttsYm) {
		this.sttsYm = sttsYm;
	}


	public String getMnpCnt() {
		return mnpCnt;
	}


	public void setMnpCnt(String mnpCnt) {
		this.mnpCnt = mnpCnt;
	}


	public String getMnpAmnt() {
		return mnpAmnt;
	}


	public void setMnpAmnt(String mnpAmnt) {
		this.mnpAmnt = mnpAmnt;
	}


	public String getPayGarntDate() {
		return payGarntDate;
	}


	public void setPayGarntDate(String payGarntDate) {
		this.payGarntDate = payGarntDate;
	}


	public String getCrdtCardCmsnAmnt() {
		return crdtCardCmsnAmnt;
	}


	public void setCrdtCardCmsnAmnt(String crdtCardCmsnAmnt) {
		this.crdtCardCmsnAmnt = crdtCardCmsnAmnt;
	}


	public String getHndsetBillAmnt() {
		return hndsetBillAmnt;
	}


	public void setHndsetBillAmnt(String hndsetBillAmnt) {
		this.hndsetBillAmnt = hndsetBillAmnt;
	}


	public String getInfoType() {
		return infoType;
	}


	public void setInfoType(String infoType) {
		this.infoType = infoType;
	}


	public String getMnpType() {
		return mnpType;
	}


	public void setMnpType(String mnpType) {
		this.mnpType = mnpType;
	}


	public String getSearchStartDt() {
		return searchStartDt;
	}


	public void setSearchStartDt(String searchStartDt) {
		this.searchStartDt = searchStartDt;
	}


	public String getSearchEndDt() {
		return searchEndDt;
	}


	public void setSearchEndDt(String searchEndDt) {
		this.searchEndDt = searchEndDt;
	}


	/**
	 * @return the pblsDate
	 */
	public String getPblsDate() {
		return pblsDate;
	}


	/**
	 * @param pblsDate the pblsDate to set
	 */
	public void setPblsDate(String pblsDate) {
		this.pblsDate = pblsDate;
	}


	/**
	 * @return the bizrRgstNo
	 */
	public String getBizrRgstNo() {
		return bizrRgstNo;
	}


	/**
	 * @param bizrRgstNo the bizrRgstNo to set
	 */
	public void setBizrRgstNo(String bizrRgstNo) {
		this.bizrRgstNo = bizrRgstNo;
	}


	/**
	 * @return the soCd
	 */
	public String getSoCd() {
		return soCd;
	}


	/**
	 * @param soCd the soCd to set
	 */
	public void setSoCd(String soCd) {
		this.soCd = soCd;
	}


	/**
	 * @return the gubunCode
	 */
	public String getGubunCode() {
		return gubunCode;
	}


	/**
	 * @param gubunCode the gubunCode to set
	 */
	public void setGubunCode(String gubunCode) {
		this.gubunCode = gubunCode;
	}


	/**
	 * @return the eseroType
	 */
	public String getEseroType() {
		return eseroType;
	}


	/**
	 * @param eseroType the eseroType to set
	 */
	public void setEseroType(String eseroType) {
		this.eseroType = eseroType;
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
	 * @return the splyPricAmt
	 */
	public int getSplyPricAmt() {
		return splyPricAmt;
	}


	/**
	 * @param splyPricAmt the splyPricAmt to set
	 */
	public void setSplyPricAmt(int splyPricAmt) {
		this.splyPricAmt = splyPricAmt;
	}


	/**
	 * @return the vatAmt
	 */
	public int getVatAmt() {
		return vatAmt;
	}


	/**
	 * @param vatAmt the vatAmt to set
	 */
	public void setVatAmt(int vatAmt) {
		this.vatAmt = vatAmt;
	}


	/**
	 * @return the vndrNm
	 */
	public int getVndrNm() {
		return vndrNm;
	}


	/**
	 * @param vndrNm the vndrNm to set
	 */
	public void setVndrNm(int vndrNm) {
		this.vndrNm = vndrNm;
	}


	/**
	 * @return the rprsPrsnNm
	 */
	public int getRprsPrsnNm() {
		return rprsPrsnNm;
	}


	/**
	 * @param rprsPrsnNm the rprsPrsnNm to set
	 */
	public void setRprsPrsnNm(int rprsPrsnNm) {
		this.rprsPrsnNm = rprsPrsnNm;
	}


	public String getSvcCntrNoYn() {
		return svcCntrNoYn;
	}


	public void setSvcCntrNoYn(String svcCntrNoYn) {
		this.svcCntrNoYn = svcCntrNoYn;
	}
	
    
}
