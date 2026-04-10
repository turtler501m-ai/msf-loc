package com.ktis.msp.pps.usimmgmt.vo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsUsimVo
 * @Description : 유심내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2016.03.21 최초생성
 * @
 * @author : 
 * @Create Date : 2016.03.21
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsUsimVo")
public class PpsUsimVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	
	
	private String telcoCd;
	private String usimModel;
	private String sn;
	private String usimSn;
	private String status;
	private String statusNm;
	private String crSeq;
	private int crPrice;
	private String crDate;
	private String crAdmin;
	private String outFlag;
	private String outFlagNm;
	private String outSeq;
	private String outAgent;
	private String outAdmin;
	private String outDate;
	private String openFlag;
	private String openFlagNm;
	private String openDate;
	
	private String contractNum;
	private String backFlag;
	private String backFlagNm;
	private String backSeq;
	private String backDate;
	private String backAdmin;
	private String remark;

	private String startSn;
	private String endSn;
	private String usimCnt;
	private String cancelFlag;
	private String cancelDate;
	private String cancelAdmin;
	
	private String retCode;
	private String retMsg;
	
	private String usimFullSn;

	/**
	 * @return the telcoCd
	 */
	public String getTelcoCd() {
		return telcoCd;
	}




	/**
	 * @param telcoCd the telcoCd to set
	 */
	public void setTelcoCd(String telcoCd) {
		this.telcoCd = telcoCd;
	}




	/**
	 * @return the usimModel
	 */
	public String getUsimModel() {
		return usimModel;
	}




	/**
	 * @param usimModel the usimModel to set
	 */
	public void setUsimModel(String usimModel) {
		this.usimModel = usimModel;
	}




	/**
	 * @return the sn
	 */
	public String getSn() {
		return sn;
	}




	/**
	 * @param sn the sn to set
	 */
	public void setSn(String sn) {
		this.sn = sn;
	}




	/**
	 * @return the usimSn
	 */
	public String getUsimSn() {
		return usimSn;
	}




	/**
	 * @param usimSn the usimSn to set
	 */
	public void setUsimSn(String usimSn) {
		this.usimSn = usimSn;
	}




	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}




	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}




	/**
	 * @return the crPrice
	 */
	public int getCrPrice() {
		return crPrice;
	}




	/**
	 * @param crPrice the crPrice to set
	 */
	public void setCrPrice(int crPrice) {
		this.crPrice = crPrice;
	}




	/**
	 * @return the crDate
	 */
	public String getCrDate() {
		return crDate;
	}




	/**
	 * @param crDate the crDate to set
	 */
	public void setCrDate(String crDate) {
		this.crDate = crDate;
	}




	/**
	 * @return the crAdmin
	 */
	public String getCrAdmin() {
		return crAdmin;
	}




	/**
	 * @param crAdmin the crAdmin to set
	 */
	public void setCrAdmin(String crAdmin) {
		this.crAdmin = crAdmin;
	}




	/**
	 * @return the outFlag
	 */
	public String getOutFlag() {
		return outFlag;
	}




	/**
	 * @param outFlag the outFlag to set
	 */
	public void setOutFlag(String outFlag) {
		this.outFlag = outFlag;
	}



	/**
	 * @return the outDate
	 */
	public String getOutDate() {
		return outDate;
	}




	/**
	 * @param outDate the outDate to set
	 */
	public void setOutDate(String outDate) {
		this.outDate = outDate;
	}




	/**
	 * @return the openFlag
	 */
	public String getOpenFlag() {
		return openFlag;
	}




	/**
	 * @param openFlag the openFlag to set
	 */
	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}




	/**
	 * @return the openDate
	 */
	public String getOpenDate() {
		return openDate;
	}




	/**
	 * @param openDate the openDate to set
	 */
	public void setOpenDate(String openDate) {
		this.openDate = openDate;
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
	 * @return the backFlag
	 */
	public String getBackFlag() {
		return backFlag;
	}




	/**
	 * @param backFlag the backFlag to set
	 */
	public void setBackFlag(String backFlag) {
		this.backFlag = backFlag;
	}



	/**
	 * @return the backAdmin
	 */
	public String getBackAdmin() {
		return backAdmin;
	}




	/**
	 * @param backAdmin the backAdmin to set
	 */
	public void setBackAdmin(String backAdmin) {
		this.backAdmin = backAdmin;
	}




	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}




	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}




	/**
	 * @return the outFlagNm
	 */
	public String getOutFlagNm() {
		return outFlagNm;
	}




	/**
	 * @param outFlagNm the outFlagNm to set
	 */
	public void setOutFlagNm(String outFlagNm) {
		this.outFlagNm = outFlagNm;
	}




	/**
	 * @return the openFlagNm
	 */
	public String getOpenFlagNm() {
		return openFlagNm;
	}




	/**
	 * @param openFlagNm the openFlagNm to set
	 */
	public void setOpenFlagNm(String openFlagNm) {
		this.openFlagNm = openFlagNm;
	}




	/**
	 * @return the backFlagNm
	 */
	public String getBackFlagNm() {
		return backFlagNm;
	}




	/**
	 * @param backFlagNm the backFlagNm to set
	 */
	public void setBackFlagNm(String backFlagNm) {
		this.backFlagNm = backFlagNm;
	}




	/**
	 * @return the retCode
	 */
	public String getRetCode() {
		return retCode;
	}




	/**
	 * @param retCode the retCode to set
	 */
	public void setRetCode(String retCode) {
		this.retCode = retCode;
	}




	/**
	 * @return the retMsg
	 */
	public String getRetMsg() {
		return retMsg;
	}




	/**
	 * @param retMsg the retMsg to set
	 */
	public void setRetMsg(String retMsg) {
		this.retMsg = retMsg;
	}




	/**
	 * @return the outAgent
	 */
	public String getOutAgent() {
		return outAgent;
	}




	/**
	 * @param outAgent the outAgent to set
	 */
	public void setOutAgent(String outAgent) {
		this.outAgent = outAgent;
	}




	/**
	 * @return the backDate
	 */
	public String getBackDate() {
		return backDate;
	}




	/**
	 * @param backDate the backDate to set
	 */
	public void setBackDate(String backDate) {
		this.backDate = backDate;
	}




	/**
	 * @return the outAdmint
	 */
	public String getOutAdmin() {
		return outAdmin;
	}




	/**
	 * @param outAdmint the outAdmint to set
	 */
	public void setOutAdmin(String outAdmin) {
		this.outAdmin = outAdmin;
	}




	/**
	 * @return the startSn
	 */
	public String getStartSn() {
		return startSn;
	}




	/**
	 * @param startSn the startSn to set
	 */
	public void setStartSn(String startSn) {
		this.startSn = startSn;
	}




	/**
	 * @return the endSn
	 */
	public String getEndSn() {
		return endSn;
	}




	/**
	 * @param endSn the endSn to set
	 */
	public void setEndSn(String endSn) {
		this.endSn = endSn;
	}




	/**
	 * @return the usimCnt
	 */
	public String getUsimCnt() {
		return usimCnt;
	}




	/**
	 * @param usimCnt the usimCnt to set
	 */
	public void setUsimCnt(String usimCnt) {
		this.usimCnt = usimCnt;
	}




	/**
	 * @return the cancelFlag
	 */
	public String getCancelFlag() {
		return cancelFlag;
	}




	/**
	 * @param cancelFlag the cancelFlag to set
	 */
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}




	/**
	 * @return the cancelDate
	 */
	public String getCancelDate() {
		return cancelDate;
	}




	/**
	 * @param cancelDate the cancelDate to set
	 */
	public void setCancelDate(String cancelDate) {
		this.cancelDate = cancelDate;
	}




	/**
	 * @return the cancelAdmin
	 */
	public String getCancelAdmin() {
		return cancelAdmin;
	}




	/**
	 * @param cancelAdmin the cancelAdmin to set
	 */
	public void setCancelAdmin(String cancelAdmin) {
		this.cancelAdmin = cancelAdmin;
	}




	/**
	 * @return the statusNm
	 */
	public String getStatusNm() {
		return statusNm;
	}




	/**
	 * @param statusNm the statusNm to set
	 */
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}




	/**
	 * @return the crSeq
	 */
	public String getCrSeq() {
		return crSeq;
	}




	/**
	 * @param crSeq the crSeq to set
	 */
	public void setCrSeq(String crSeq) {
		this.crSeq = crSeq;
	}




	/**
	 * @return the outSeq
	 */
	public String getOutSeq() {
		return outSeq;
	}




	/**
	 * @param outSeq the outSeq to set
	 */
	public void setOutSeq(String outSeq) {
		this.outSeq = outSeq;
	}




	/**
	 * @return the backSeq
	 */
	public String getBackSeq() {
		return backSeq;
	}




	/**
	 * @param backSeq the backSeq to set
	 */
	public void setBackSeq(String backSeq) {
		this.backSeq = backSeq;
	}




	/**
	 * @return the usimFullSn
	 */
	public String getUsimFullSn() {
		return usimFullSn;
	}




	/**
	 * @param usimFullSn the usimFullSn to set
	 */
	public void setUsimFullSn(String usimFullSn) {
		this.usimFullSn = usimFullSn;
	}
	
	

}
