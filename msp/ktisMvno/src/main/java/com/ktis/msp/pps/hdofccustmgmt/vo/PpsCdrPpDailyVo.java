package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsCdrPpDaily2014Vo
 * @Description : 선불 일사용내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsCdrPpDailyVo")
public class PpsCdrPpDailyVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	public static final String TABLE = "PPS_CDR_PP_DAILY";

	/**
	 * CONTRACT_NUM:number(9)
	 */
	private int contractNum;

	/**
	 * 암호화:varchar2(50) <Primary Key>
	 */
	private String msisdn;

	/**
	 * 접속일자 YYYY-MM-DD 00:00:00:date(0) <Primary Key>
	 */
	private String accessDt;

	/**
	 * 최초 사용시작 일자:date(0)
	 */
	private String startTime;

	/**
	 * 마지막 사용 종료시간:date(0)
	 */
	private String endTime;

	/**
	 * 하루 총 누적 사용량(BYTE):number(15)
	 */
	private int pkt;

	/**
	 * 총 패킷사용금액:number(15,4)
	 */
	private double charge;

	/**
	 * 마지막 잔액:number(15)
	 */
	private int remains;

	/**
	 * 마지막 패킷 잔량:number(15)
	 */
	private int dataRemains;

	/**
	 * 마지막 데이타 만료일:date(0)
	 */
	private String dataExpire;

	/**
	 * 콜구분 : PKT-N(국내데이타) , DAY=일차감데이타:varchar2(5) <Primary Key>
	 */
	private String callGubun;
	
	/**
	 * 콜구분명
	 */
	private String callGubunNm;

	/**
	 * 마지막 UPDATE 일자:date(0)
	 */
	private String lastUpdate;

	/**
	 * 소속대리점ID:varchar2(20)
	 */
	private String agentId;
	
	/**
	 * 소속대리점명
	 */
    private String agentNm;
    
    	/**
	 * 요금제:varchar2(9)
	 */
	private String soc;
	
	/**
	 * 요금제명
	 */
	private String socNm;
	
	
	
	/**
	 * @return the contractNum
	 */
	public int getContractNum() {
		return contractNum;
	}



	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(int contractNum) {
		this.contractNum = contractNum;
	}



	/**
	 * @return the msisdn
	 */
	public String getMsisdn() {
		return msisdn;
	}



	/**
	 * @param msisdn the msisdn to set
	 */
	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}



	/**
	 * @return the accessDt
	 */
	public String getAccessDt() {
		return accessDt;
	}



	/**
	 * @param accessDt the accessDt to set
	 */
	public void setAccessDt(String accessDt) {
		this.accessDt = accessDt;
	}



	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}



	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}



	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}



	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}



	/**
	 * @return the pkt
	 */
	public int getPkt() {
		return pkt;
	}



	/**
	 * @param pkt the pkt to set
	 */
	public void setPkt(int pkt) {
		this.pkt = pkt;
	}



	/**
	 * @return the charge
	 */
	public double getCharge() {
		return charge;
	}



	/**
	 * @param charge the charge to set
	 */
	public void setCharge(double charge) {
		this.charge = charge;
	}



	/**
	 * @return the remains
	 */
	public int getRemains() {
		return remains;
	}



	/**
	 * @param remains the remains to set
	 */
	public void setRemains(int remains) {
		this.remains = remains;
	}



	/**
	 * @return the dataRemains
	 */
	public int getDataRemains() {
		return dataRemains;
	}



	/**
	 * @param dataRemains the dataRemains to set
	 */
	public void setDataRemains(int dataRemains) {
		this.dataRemains = dataRemains;
	}



	/**
	 * @return the dataExpire
	 */
	public String getDataExpire() {
		return dataExpire;
	}



	/**
	 * @param dataExpire the dataExpire to set
	 */
	public void setDataExpire(String dataExpire) {
		this.dataExpire = dataExpire;
	}



	/**
	 * @return the callGubun
	 */
	public String getCallGubun() {
		return callGubun;
	}



	/**
	 * @param callGubun the callGubun to set
	 */
	public void setCallGubun(String callGubun) {
		this.callGubun = callGubun;
	}



	/**
	 * @return the callGubunNm
	 */
	public String getCallGubunNm() {
		return callGubunNm;
	}



	/**
	 * @param callGubunNm the callGubunNm to set
	 */
	public void setCallGubunNm(String callGubunNm) {
		this.callGubunNm = callGubunNm;
	}



	/**
	 * @return the lastUpdate
	 */
	public String getLastUpdate() {
		return lastUpdate;
	}



	/**
	 * @param lastUpdate the lastUpdate to set
	 */
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}



	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}



	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
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
	 * @return the soc
	 */
	public String getSoc() {
		return soc;
	}



	/**
	 * @param soc the soc to set
	 */
	public void setSoc(String soc) {
		this.soc = soc;
	}



	/**
	 * @return the socNm
	 */
	public String getSocNm() {
		return socNm;
	}



	/**
	 * @param socNm the socNm to set
	 */
	public void setSocNm(String socNm) {
		this.socNm = socNm;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
