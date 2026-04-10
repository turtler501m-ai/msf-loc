package com.ktis.msp.rcp.canCustMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : CanCustVO
 * @Description : 해지정보VO
 * @
 * @ 수정일      수정자 수정내용
 * @ ------------- -------- -----------------------------
 * @ 2016.08.10  장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2016. 8. 10.
 */

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="canCustVO")
public class CanCustVO extends BaseVo implements Serializable{
	
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -726168016609509440L;

	
    private String canDt;
    private String reqBuyTypeNm;
    private String contractNum;
    private String fstRateNm;
    private String salePlcyNm;
    private String fstModelNm;
    private String subStatusNm;
    private String canRsn;
    private String onOffTypeNm;
    private String agentNm;
    private String openDt;
    private String sprtTpNm;
    private String searchGbn;
    private String searchName;
    private String searchStartDt;
    private String searchEndDt;
    private String searchStatus;
    private String cntpntShopId;
    
    private String custReqSeq;
    private String procCd;
    private String newScanId;
	private String oldScanId;
	private String fileNum;
	private String memo;
	private String maxFileCnt;
	
	private String svcCntrNo;
	private String rclDttm;
	private String rclRsn;
	private String rclCanDttm;
	private String rclCanRsn;
	private String canDttm;
	private String dataType;
	private String fstModel;
	private String fstRateCd;
	private String openAgntNm;
	private String lstComActvDate;
	private String operTypeNm;
	private String bfCommCmpnNm;
	private String npCommCmpnNm;
	private String shopNm;
	private String openMarketReferer;
	private String age;
	private String gender;
	private String modelName;
	private String lstRateCd;
	private String lstRateNm;
	private String usimOrgNm;
	private String fstUsimOrgNm;
	private String esimYn;
	private String fstEsimYn;
    
    
	public String getCntpntShopId() {
		return cntpntShopId;
	}

	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}

	@Override
    public String toString() {
       return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
    }

	/**
	 * @return the canDt
	 */
	public String getCanDt() {
		return canDt;
	}

	/**
	 * @param canDt the canDt to set
	 */
	public void setCanDt(String canDt) {
		this.canDt = canDt;
	}

	/**
	 * @return the reqBuyTypeNm
	 */
	public String getReqBuyTypeNm() {
		return reqBuyTypeNm;
	}

	/**
	 * @param reqBuyTypeNm the reqBuyTypeNm to set
	 */
	public void setReqBuyTypeNm(String reqBuyTypeNm) {
		this.reqBuyTypeNm = reqBuyTypeNm;
	}

	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}

	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	/**
	 * @return the fstRateNm
	 */
	public String getFstRateNm() {
		return fstRateNm;
	}

	/**
	 * @param fstRateNm the fstRateNm to set
	 */
	public void setFstRateNm(String fstRateNm) {
		this.fstRateNm = fstRateNm;
	}

	/**
	 * @return the salePlcyNm
	 */
	public String getSalePlcyNm() {
		return salePlcyNm;
	}

	/**
	 * @param salePlcyNm the salePlcyNm to set
	 */
	public void setSalePlcyNm(String salePlcyNm) {
		this.salePlcyNm = salePlcyNm;
	}

	/**
	 * @return the fstModelNm
	 */
	public String getFstModelNm() {
		return fstModelNm;
	}

	/**
	 * @param fstModelNm the fstModelNm to set
	 */
	public void setFstModelNm(String fstModelNm) {
		this.fstModelNm = fstModelNm;
	}

	/**
	 * @return the subStatusNm
	 */
	public String getSubStatusNm() {
		return subStatusNm;
	}

	/**
	 * @param subStatusNm the subStatusNm to set
	 */
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}

	/**
	 * @return the canRsn
	 */
	public String getCanRsn() {
		return canRsn;
	}

	/**
	 * @param canRsn the canRsn to set
	 */
	public void setCanRsn(String canRsn) {
		this.canRsn = canRsn;
	}


	/**
	 * @return the agentNm
	 */
	public String getAgentNm() {
		return agentNm;
	}

	/**
	 * @param agentNm the agentNm to set
	 */
	public void setAgentNm(String agentNm) {
		this.agentNm = agentNm;
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
	 * @return the sprtTpNm
	 */
	public String getSprtTpNm() {
		return sprtTpNm;
	}

	/**
	 * @param sprtTpNm the sprtTpNm to set
	 */
	public void setSprtTpNm(String sprtTpNm) {
		this.sprtTpNm = sprtTpNm;
	}

	/**
	 * @return the searchGbn
	 */
	public String getSearchGbn() {
		return searchGbn;
	}

	/**
	 * @param searchGbn the searchGbn to set
	 */
	public void setSearchGbn(String searchGbn) {
		this.searchGbn = searchGbn;
	}

	/**
	 * @return the searchName
	 */
	public String getSearchName() {
		return searchName;
	}

	/**
	 * @param searchName the searchName to set
	 */
	public void setSearchName(String searchName) {
		this.searchName = searchName;
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
	 * @return the onOffTypeNm
	 */
	public String getOnOffTypeNm() {
		return onOffTypeNm;
	}

	/**
	 * @param onOffTypeNm the onOffTypeNm to set
	 */
	public void setOnOffTypeNm(String onOffTypeNm) {
		this.onOffTypeNm = onOffTypeNm;
	}

	public String getSearchStatus() {
		return searchStatus;
	}

	public void setSearchStatus(String searchStatus) {
		this.searchStatus = searchStatus;
	}

	public String getProcCd() {
		return procCd;
	}

	public void setProcCd(String procCd) {
		this.procCd = procCd;
	}

	public String getNewScanId() {
		return newScanId;
	}

	public void setNewScanId(String newScanId) {
		this.newScanId = newScanId;
	}

	public String getOldScanId() {
		return oldScanId;
	}

	public void setOldScanId(String oldScanId) {
		this.oldScanId = oldScanId;
	}

	public String getFileNum() {
		return fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getCustReqSeq() {
		return custReqSeq;
	}

	public void setCustReqSeq(String custReqSeq) {
		this.custReqSeq = custReqSeq;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getMaxFileCnt() {
		return maxFileCnt;
	}

	public void setMaxFileCnt(String maxFileCnt) {
		this.maxFileCnt = maxFileCnt;
	}

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}

	public String getRclDttm() {
		return rclDttm;
	}

	public void setRclDttm(String rclDttm) {
		this.rclDttm = rclDttm;
	}

	public String getRclRsn() {
		return rclRsn;
	}

	public void setRclRsn(String rclRsn) {
		this.rclRsn = rclRsn;
	}

	public String getRclCanDttm() {
		return rclCanDttm;
	}

	public void setRclCanDttm(String rclCanDttm) {
		this.rclCanDttm = rclCanDttm;
	}

	public String getRclCanRsn() {
		return rclCanRsn;
	}

	public void setRclCanRsn(String rclCanRsn) {
		this.rclCanRsn = rclCanRsn;
	}

	public String getCanDttm() {
		return canDttm;
	}

	public void setCanDttm(String canDttm) {
		this.canDttm = canDttm;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getFstModel() {
		return fstModel;
	}

	public void setFstModel(String fstModel) {
		this.fstModel = fstModel;
	}

	public String getFstRateCd() {
		return fstRateCd;
	}

	public void setFstRateCd(String fstRateCd) {
		this.fstRateCd = fstRateCd;
	}

	public String getOpenAgntNm() {
		return openAgntNm;
	}

	public void setOpenAgntNm(String openAgntNm) {
		this.openAgntNm = openAgntNm;
	}

	public String getLstComActvDate() {
		return lstComActvDate;
	}

	public void setLstComActvDate(String lstComActvDate) {
		this.lstComActvDate = lstComActvDate;
	}

	public String getOperTypeNm() {
		return operTypeNm;
	}

	public void setOperTypeNm(String operTypeNm) {
		this.operTypeNm = operTypeNm;
	}

	public String getBfCommCmpnNm() {
		return bfCommCmpnNm;
	}

	public void setBfCommCmpnNm(String bfCommCmpnNm) {
		this.bfCommCmpnNm = bfCommCmpnNm;
	}

	public String getNpCommCmpnNm() {
		return npCommCmpnNm;
	}

	public void setNpCommCmpnNm(String npCommCmpnNm) {
		this.npCommCmpnNm = npCommCmpnNm;
	}

	public String getShopNm() {
		return shopNm;
	}

	public void setShopNm(String shopNm) {
		this.shopNm = shopNm;
	}

	public String getOpenMarketReferer() {
		return openMarketReferer;
	}

	public void setOpenMarketReferer(String openMarketReferer) {
		this.openMarketReferer = openMarketReferer;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getLstRateCd() {
		return lstRateCd;
	}

	public void setLstRateCd(String lstRateCd) {
		this.lstRateCd = lstRateCd;
	}

	public String getLstRateNm() {
		return lstRateNm;
	}

	public void setLstRateNm(String lstRateNm) {
		this.lstRateNm = lstRateNm;
	}

	public String getUsimOrgNm() {
		return usimOrgNm;
	}

	public void setUsimOrgNm(String usimOrgNm) {
		this.usimOrgNm = usimOrgNm;
	}

	public String getFstUsimOrgNm() {
		return fstUsimOrgNm;
	}

	public void setFstUsimOrgNm(String fstUsimOrgNm) {
		this.fstUsimOrgNm = fstUsimOrgNm;
	}

	public String getEsimYn() {
		return esimYn;
	}

	public void setEsimYn(String esimYn) {
		this.esimYn = esimYn;
	}

	public String getFstEsimYn() {
		return fstEsimYn;
	}

	public void setFstEsimYn(String fstEsimYn) {
		this.fstEsimYn = fstEsimYn;
	}
	
}