package com.ktis.msp.pps.tesmgmt.vo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsRcgTesVo
 * @Description : 청소년요금제 충전/조회내역 group VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsRcgTesVo")
public class PpsRcgTesVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	/**
	 * PK:number(20)
	 */
	private int tesSeq;

	/**
	 * 충전타입 : varchar2(30)
	 */
	private String reqType;

	/**
	 * 계약번호 : varchar2(10)
	 */
    private String contractNum;
    
    /**
     * 충전대리점 : varchar2(20)
     */
    private String rechargeAgent;
    
    /**
     * 충전금액 : number(20)
     */
    private int recharge;
    
    /**
     * 결과코드 : varchar2(20)
     */
    private String retCode;
    
    /**
     * 결과메세지 : varchar2(200)
     */
    private String retMsg;
    
    /**
     * 충전 seq : numver(20)
     */
    private int rcgSeq;
    
    /**
     * 충전된금액 : number(8)
     */
    private int oAmount;
    
    /**
     * 최대충전가능금액 : varchar2(15)
     */
    private String oTesChargeMax;
    
    /**
     * 기본알 : varchar2(15)
     */
    private String oTesBaser;
    
    /**
     * 충전알 : varchar2(15)
     */
    private String oTesChgr;
    
    /**
     * 데이타알 : varchar2(15)
     */
    private String oTesMagicr;
    
    /**
     * 문자알 : varchar2(15)
     */
    private String oTesFsmsr;
    
    /**
     * 영상알 : varchar2(15)
     */
    private String oTesVideor;
    
    /**
     * 정보전송용알 : varchar2(15)
     */
    private String oTesIpvasr;
    
    /**
     * 알캡상한알 : varchar2(15)
     */
    private String oTesIpmaxr;
    
    /**
     * 문자건수 : varchar2(15)
     */
    private String oTesSmsm;
    
    /**
     * 데이타계정(단위:Byte) : varchar2(15)
     */
    private String oTesDataplusv;
    
    /**
     * 요청ip : varchar2(20)
     */
    private String rechargeIp;
    
    /**
     * 요청일자 : date(0)
     */
    private String reqDate;
    
    /**
     * 요청관리자 : varchar2(20)
     */
    private String adminId;
    
    
    /**
	 * @return the smsTitle
	 */
    public int getTesSeq() {
		return tesSeq;
	}




    /**
	 * @param tesSeq the tesSeq to set
	 */
	public void setTesSeq(int tesSeq) {
		this.tesSeq = tesSeq;
	}




	/**
	 * @return the reqType
	 */
	public String getReqType() {
		return reqType;
	}




	/**
	 * @param reqType the reqType to set
	 */
	public void setReqType(String reqType) {
		this.reqType = reqType;
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
	 * @return the rechargeAgent
	 */
	public String getRechargeAgent() {
		return rechargeAgent;
	}




	/**
	 * @param rechargeAgent the rechargeAgent to set
	 */
	public void setRechargeAgent(String rechargeAgent) {
		this.rechargeAgent = rechargeAgent;
	}




	/**
	 * @return the recharge
	 */
	public int getRecharge() {
		return recharge;
	}




	/**
	 * @param recharge the recharge to set
	 */
	public void setRecharge(int recharge) {
		this.recharge = recharge;
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
	 * @return the rcgSeq
	 */
	public int getRcgSeq() {
		return rcgSeq;
	}




	/**
	 * @param rcgSeq the rcgSeq to set
	 */
	public void setRcgSeq(int rcgSeq) {
		this.rcgSeq = rcgSeq;
	}




	/**
	 * @return the oAmount
	 */
	public int getoAmount() {
		return oAmount;
	}




	/**
	 * @param oAmount the oAmount to set
	 */
	public void setoAmount(int oAmount) {
		this.oAmount = oAmount;
	}




	/**
	 * @return the oTesChargeMax
	 */
	public String getoTesChargeMax() {
		return oTesChargeMax;
	}




	/**
	 * @param oTesChargeMax the oTesChargeMax to set
	 */
	public void setoTesChargeMax(String oTesChargeMax) {
		this.oTesChargeMax = oTesChargeMax;
	}




	/**
	 * @return the oTesBaser
	 */
	public String getoTesBaser() {
		return oTesBaser;
	}




	/**
	 * @param oTesBaser the oTesBaser to set
	 */
	public void setoTesBaser(String oTesBaser) {
		this.oTesBaser = oTesBaser;
	}




	/**
	 * @return the oTesChgr
	 */
	public String getoTesChgr() {
		return oTesChgr;
	}




	/**
	 * @param oTesChgr the oTesChgr to set
	 */
	public void setoTesChgr(String oTesChgr) {
		this.oTesChgr = oTesChgr;
	}




	/**
	 * @return the oTesMagicr
	 */
	public String getoTesMagicr() {
		return oTesMagicr;
	}




	/**
	 * @param oTesMagicr the oTesMagicr to set
	 */
	public void setoTesMagicr(String oTesMagicr) {
		this.oTesMagicr = oTesMagicr;
	}




	/**
	 * @return the oTesFsmsr
	 */
	public String getoTesFsmsr() {
		return oTesFsmsr;
	}




	/**
	 * @param oTesFsmsr the oTesFsmsr to set
	 */
	public void setoTesFsmsr(String oTesFsmsr) {
		this.oTesFsmsr = oTesFsmsr;
	}




	/**
	 * @return the oTesVideor
	 */
	public String getoTesVideor() {
		return oTesVideor;
	}




	/**
	 * @param oTesVideor the oTesVideor to set
	 */
	public void setoTesVideor(String oTesVideor) {
		this.oTesVideor = oTesVideor;
	}




	/**
	 * @return the oTesIpvasr
	 */
	public String getoTesIpvasr() {
		return oTesIpvasr;
	}




	/**
	 * @param oTesIpvasr the oTesIpvasr to set
	 */
	public void setoTesIpvasr(String oTesIpvasr) {
		this.oTesIpvasr = oTesIpvasr;
	}




	/**
	 * @return the oTesIpmaxr
	 */
	public String getoTesIpmaxr() {
		return oTesIpmaxr;
	}




	/**
	 * @param oTesIpmaxr the oTesIpmaxr to set
	 */
	public void setoTesIpmaxr(String oTesIpmaxr) {
		this.oTesIpmaxr = oTesIpmaxr;
	}




	/**
	 * @return the oTesSmsm
	 */
	public String getoTesSmsm() {
		return oTesSmsm;
	}




	/**
	 * @param oTesSmsm the oTesSmsm to set
	 */
	public void setoTesSmsm(String oTesSmsm) {
		this.oTesSmsm = oTesSmsm;
	}




	/**
	 * @return the oTesDataplusv
	 */
	public String getoTesDataplusv() {
		return oTesDataplusv;
	}




	/**
	 * @param oTesDataplusv the oTesDataplusv to set
	 */
	public void setoTesDataplusv(String oTesDataplusv) {
		this.oTesDataplusv = oTesDataplusv;
	}




	/**
	 * @return the rechargeIp
	 */
	public String getRechargeIp() {
		return rechargeIp;
	}




	/**
	 * @param rechargeIp the rechargeIp to set
	 */
	public void setRechargeIp(String rechargeIp) {
		this.rechargeIp = rechargeIp;
	}




	/**
	 * @return the reqDate
	 */
	public String getReqDate() {
		return reqDate;
	}




	/**
	 * @param reqDate the reqDate to set
	 */
	public void setReqDate(String reqDate) {
		this.reqDate = reqDate;
	}




	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}




	/**
	 * @param adminId the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
