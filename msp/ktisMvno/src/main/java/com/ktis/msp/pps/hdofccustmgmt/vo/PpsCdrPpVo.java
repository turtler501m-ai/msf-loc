package com.ktis.msp.pps.hdofccustmgmt.vo;


import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsCdrPpVo
 * @Description : 선불통화내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsCdrPpVo")
public class PpsCdrPpVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_CDR_PP";

	/**
	 * 계약번호:number(9)
	 */
	private int contractNum;

	/**
	 * 전화번호:varchar2(50)
	 */
	private String msisdn;

	/**
	 * 착신번호:varchar2(22)
	 */
	private String calledNum;

	/**
	 * SVC_KEY:varchar2(2)
	 */
	private String svcKey;

	/**
	 * SVC_TYPE:varchar2(3)
	 */
	private String svcType;

	/**
	 * 음성,영상=V or A / SMS=S or M / Packet= D or I/:varchar2(1)
	 */
	private String callType;

	/**
	 * 국제=I, 국내=N:varchar2(1)
	 */
	private String intCall;

	/**
	 * 통화종료사유:varchar2(10)
	 */
	private String callTermCause;

	/**
	 * START_TIME:date(0)
	 */
	private String startTime;

	/**
	 * END_TIME:date(0)
	 */
	private String endTime;

	/**
	 * TIME_CHARGE:number(8)
	 */
	private int timeCharge;

	/**
	 * EVENT_CHARGE:number(8)
	 */
	private int eventCharge;

	/**
	 * PKT_CHARGE:number(8)
	 */
	private int pktCharge;

	/**
	 * 114통화료 같은 부가요금:number(8)
	 */
	private int ipCharge;

	/**
	 * CATEGORY_ID:varchar2(9)
	 */
	private String categoryId;

	/**
	 * 패킷 사용량:number(8)
	 */
	private int pkt;

	/**
	 * SVC:varchar2(1)
	 */
	private String svc;

	/**
	 * SYSTEM_ID:varchar2(3)
	 */
	private String systemId;

	/**
	 * 통화후 음성 기본잔액:number(8)
	 */
	private int remains;

	/**
	 * 부가데이타 사용 후 데이타 남은 사용량:number(15)
	 */
	private int dataRemains;

	/**
	 * 부가서비스 데이타 만료일:date(0)
	 */
	private String dataExpire;

	/**
	 * 국내음성 VOC-N, 국제음성 VOC-I, 국내문자=SMS-N, 국제문자=SMS-I, 국제데이타=PKT-I, 국내영상 VID-N, 국제영상 VID-I, 기본료=DAY, 기타=ETC:varchar2(5)
	 */
	private String callGubun;
	/**
	 * 통화구분명
	 */
	private String callGubunName;
	

	/**
	 * 사용초:number(8)
	 */
	private int usedTime;

	/**
	 * 과금액:number(11,3)
	 */
	private double charge;

	/**
	 * 국제전화사업자 PREFIX:varchar2(10)
	 */
	private String telcoPx;

	/**
	 * RECORD_DATE:date(0)
	 */
	private String recordDate;

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
	 * 국제인경우 국가코드:varchar2(20)
	 */
	private String prefix;
	
	/**
	 * 국가코드명
	 */
    private String korName;


	
	
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
	 * @return the calledNum
	 */
	public String getCalledNum() {
		return calledNum;
	}




	/**
	 * @param calledNum the calledNum to set
	 */
	public void setCalledNum(String calledNum) {
		this.calledNum = calledNum;
	}




	/**
	 * @return the svcKey
	 */
	public String getSvcKey() {
		return svcKey;
	}




	/**
	 * @param svcKey the svcKey to set
	 */
	public void setSvcKey(String svcKey) {
		this.svcKey = svcKey;
	}




	/**
	 * @return the svcType
	 */
	public String getSvcType() {
		return svcType;
	}




	/**
	 * @param svcType the svcType to set
	 */
	public void setSvcType(String svcType) {
		this.svcType = svcType;
	}




	/**
	 * @return the callType
	 */
	public String getCallType() {
		return callType;
	}




	/**
	 * @param callType the callType to set
	 */
	public void setCallType(String callType) {
		this.callType = callType;
	}




	/**
	 * @return the intCall
	 */
	public String getIntCall() {
		return intCall;
	}




	/**
	 * @param intCall the intCall to set
	 */
	public void setIntCall(String intCall) {
		this.intCall = intCall;
	}




	/**
	 * @return the callTermCause
	 */
	public String getCallTermCause() {
		return callTermCause;
	}




	/**
	 * @param callTermCause the callTermCause to set
	 */
	public void setCallTermCause(String callTermCause) {
		this.callTermCause = callTermCause;
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
	 * @return the timeCharge
	 */
	public int getTimeCharge() {
		return timeCharge;
	}




	/**
	 * @param timeCharge the timeCharge to set
	 */
	public void setTimeCharge(int timeCharge) {
		this.timeCharge = timeCharge;
	}




	/**
	 * @return the eventCharge
	 */
	public int getEventCharge() {
		return eventCharge;
	}




	/**
	 * @param eventCharge the eventCharge to set
	 */
	public void setEventCharge(int eventCharge) {
		this.eventCharge = eventCharge;
	}




	/**
	 * @return the pktCharge
	 */
	public int getPktCharge() {
		return pktCharge;
	}




	/**
	 * @param pktCharge the pktCharge to set
	 */
	public void setPktCharge(int pktCharge) {
		this.pktCharge = pktCharge;
	}




	/**
	 * @return the ipCharge
	 */
	public int getIpCharge() {
		return ipCharge;
	}




	/**
	 * @param ipCharge the ipCharge to set
	 */
	public void setIpCharge(int ipCharge) {
		this.ipCharge = ipCharge;
	}




	/**
	 * @return the categoryId
	 */
	public String getCategoryId() {
		return categoryId;
	}




	/**
	 * @param categoryId the categoryId to set
	 */
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
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
	 * @return the svc
	 */
	public String getSvc() {
		return svc;
	}




	/**
	 * @param svc the svc to set
	 */
	public void setSvc(String svc) {
		this.svc = svc;
	}




	/**
	 * @return the systemId
	 */
	public String getSystemId() {
		return systemId;
	}




	/**
	 * @param systemId the systemId to set
	 */
	public void setSystemId(String systemId) {
		this.systemId = systemId;
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
	 * @return the callGubunName
	 */
	public String getCallGubunName() {
		return callGubunName;
	}




	/**
	 * @param callGubunName the callGubunName to set
	 */
	public void setCallGubunName(String callGubunName) {
		this.callGubunName = callGubunName;
	}




	/**
	 * @return the usedTime
	 */
	public int getUsedTime() {
		return usedTime;
	}




	/**
	 * @param usedTime the usedTime to set
	 */
	public void setUsedTime(int usedTime) {
		this.usedTime = usedTime;
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
	 * @return the telcoPx
	 */
	public String getTelcoPx() {
		return telcoPx;
	}




	/**
	 * @param telcoPx the telcoPx to set
	 */
	public void setTelcoPx(String telcoPx) {
		this.telcoPx = telcoPx;
	}




	/**
	 * @return the recordDate
	 */
	public String getRecordDate() {
		return recordDate;
	}




	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(String recordDate) {
		this.recordDate = recordDate;
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
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}




	/**
	 * @param prefix the prefix to set
	 */
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}




	/**
	 * @return the korName
	 */
	public String getKorName() {
		return korName;
	}




	/**
	 * @param korName the korName to set
	 */
	public void setKorName(String korName) {
		this.korName = korName;
	}




	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
