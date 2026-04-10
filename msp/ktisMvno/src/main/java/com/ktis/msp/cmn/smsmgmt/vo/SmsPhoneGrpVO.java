package com.ktis.msp.cmn.smsmgmt.vo;

import com.ktis.msp.base.mvc.BaseVo;

public class SmsPhoneGrpVO extends BaseVo {
	
	public String grpId;
	public String grpNm;
	public String usrId;
	public String usrNm;
	public String mblphnNum;
	
	public String getGrpId() {
		return grpId;
	}
	public void setGrpId(String grpId) {
		this.grpId = grpId;
	}
	public String getGrpNm() {
		return grpNm;
	}
	public void setGrpNm(String grpNm) {
		this.grpNm = grpNm;
	}
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
	public String getMblphnNum() {
		return mblphnNum;
	}
	public void setMblphnNum(String mblphnNum) {
		this.mblphnNum = mblphnNum;
	}
	
	
}
