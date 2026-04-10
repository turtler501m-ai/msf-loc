package com.ktis.msp.rcp.rcpMgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="rcpRateVO")
public class RcpRateVO extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	private String usrId; /** 사용자 ID */
	private String usrNm; /** 사용자 명 */
	
	private String additionName = "";
	private String additionChecked = "";
	private String requestKey = "";
	private String grpAddition = ""; 
	private String additionKey = "";
	private String rantal;
	private String grpNm;
	private String cntpntShopId;
	private String reqBuyType;
	private String operType;
	private String rateCd;
	private String agrmTrm;
	private String reqInDay;
	// 부가서비스배타관계
	private String exclusiveKey;
	private String msg;
	// 모집경로
	private String onOffType;

	private String combineSoloType;

	
	public String getUsrId() {
		return usrId;
	}
	public void setUsrId(String usrId) {
		this.usrId = usrId;
	}
	public String getUsrNm() {
		return usrNm;
	}
	public void setUsrNm(String usrNm) {
		this.usrNm = usrNm;
	}
	public String getAdditionName() {
		return additionName;
	}
	public void setAdditionName(String additionName) {
		this.additionName = additionName;
	}
	public String getAdditionChecked() {
		return additionChecked;
	}
	public void setAdditionChecked(String additionChecked) {
		this.additionChecked = additionChecked;
	}
	public String getRequestKey() {
		return requestKey;
	}
	public void setRequestKey(String requestKey) {
		this.requestKey = requestKey;
	}
	public String getGrpAddition() {
		return grpAddition;
	}
	public void setGrpAddition(String grpAddition) {
		this.grpAddition = grpAddition;
	}
	public String getAdditionKey() {
		return additionKey;
	}
	public void setAdditionKey(String additionKey) {
		this.additionKey = additionKey;
	}
	public String getRantal() {
		return rantal;
	}
	public void setRantal(String rantal) {
		this.rantal = rantal;
	}
	public String getGrpNm() {
		return grpNm;
	}
	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
	}
	public String getCntpntShopId() {
		return cntpntShopId;
	}
	public void setCntpntShopId(String cntpntShopId) {
		this.cntpntShopId = cntpntShopId;
	}
	public String getReqBuyType() {
		return reqBuyType;
	}
	public void setReqBuyType(String reqBuyType) {
		this.reqBuyType = reqBuyType;
	}
	public String getOperType() {
		return operType;
	}
	public void setOperType(String operType) {
		this.operType = operType;
	}
	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getAgrmTrm() {
		return agrmTrm;
	}
	public void setAgrmTrm(String agrmTrm) {
		this.agrmTrm = agrmTrm;
	}
	public String getReqInDay() {
		return reqInDay;
	}
	public void setReqInDay(String reqInDay) {
		this.reqInDay = reqInDay;
	}
	public String getExclusiveKey() {
		return exclusiveKey;
	}
	public void setExclusiveKey(String exclusiveKey) {
		this.exclusiveKey = exclusiveKey;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getOnOffType() {
		return onOffType;
	}
	public void setOnOffType(String onOffType) {
		this.onOffType = onOffType;
	}

	public String getCombineSoloType() {
		return combineSoloType;
	}

	public void setCombineSoloType(String combineSoloType) {
		this.combineSoloType = combineSoloType;
	}
}
