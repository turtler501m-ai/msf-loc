package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsRcgVo
 * @Description : 선불 충전내역 VO 
 * @ 수정일 수정자 수정내용 
 * @ ---------- ----------------------------------- 
 * @ 2014.08.27 장익준 최초생성 @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "PpsRcgVo")
public class PpsRcgVo extends BaseVo implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_RCG";

	/**
	 * 고유번호(시퀀스참조):number(20)
	 */
	private int seq;

	/**
	 * 충전요청일자, 입력일자:date(0)
	 */
	private String rechargeDate;

	/**
	 * Customer의 PK:number(9)
	 */
	private int contractNum;

	/**
	 * 전화번호(암호화):varchar2(200)
	 */
	private String subscriberNo;

	/**
	 * V=음성충전, D=데이타충전 - 충전구분:char(1)
	 */
	private String chgType;

	/**
	 * RCG_TYPE 테이블 참조:varchar2(20)
	 */
	private String reqType;

	/**
	 * WEB,BAR,ARS,BILL,ETC (WEB으로 고정) -충전요청:varchar2(20)
	 */
	private String reqSrc;

	/**
	 * 결재방식(종류별 문자열 입력, 향후 정의):varchar2(30)
	 */
	private String rechargeMethod;

	/**
	 * 결재방식에 따른 세부정보(PIN, 가상계좌ID, 신용카드, 등등)끝에 5자리만 보여주기:varchar2(50)
	 */
	private String rechargeInfo;

	/**
	 * 실 입금금액 ==> 회계 반영금액:number(10)
	 */
	private int inAmount;

	/**
	 * 사용 포인트 금액:number(10)
	 */
	private int point;

	/**
	 * 실 충전금액, 잔액 설정금액(KT 지능망 요청금액), (+),(-) 가능:number(10)
	 */
	private int amount;

	/**
	 * 회계적용여부(Y=적용, N=미적용)/Recharge_Type Table 참조:char(1)
	 */
	private String fineFlag;

	/**
	 * 충전 성공여부(0000=성공, XXXX는 KT 에러값, -XXX는 내부에러):varchar2(5)
	 */
	private String ktResCode;

	/**
	 * KT 회신일자:date(0)
	 */
	private String ktResDt;

	/**
	 * N 취소(환불) 여부(Y=취소처리 OR N=취소안함):char(1)
	 */
	private String cancelFlag;

	/**
	 * 취소(환불) 일자:date(0)
	 */
	private String cancelDt;

	/**
	 * 이전 음성 잔액:number(10,2)
	 */
	private double basicOldRemains;

	/**
	 * 충전후 음성 잔액:number(10,2)
	 */
	private double basicRemains;

	/**
	 * 이전 음성 잔액 유효기간:date(0)
	 */
	private String basicOldExpire;

	/**
	 * 음성 유효기간:date(0)
	 */
	private String basicExpire;

	/**
	 * 데이터 이전 남은 용량용량:number(20)
	 */
	private double dataOldRemains;

	/**
	 * 데이터 충전 용량:number(20)
	 */
	private double dataQuota;

	/**
	 * 데이터 충전 후 용량:number(20)
	 */
	private double dataRemains;

	/**
	 * 데이터 이전 유효기간:date(0)
	 */
	private String dataOldExpire;

	/**
	 * 데이타요금 유효기간:date(0)
	 */
	private String dataExpire;

	/**
	 * 개통대리점:varchar2(20)
	 */
	private String agentId;

	/**
	 * 충전대리점:varchar2(20)
	 */
	private String rechargeAgent;

	/**
	 * 충전대리점예치금차감액(추가):number(20,2)
	 */
	private double agentCharge;

	/**
	 * 충전 관리자:varchar2(20)
	 */
	private String adminId;
	
	/**
	 * 충전 관리자명
	 */
	private String adminNm;

	

	/**
	 * APP이면 APP계정 또는 전화번호, WWW이면 로그인계정:varchar2(20)
	 */
	private String accessId;

	/**
	 * 최초 Access 공인 IP:varchar2(20)
	 */
	private String accessIp;

	/**
	 * 특이사항:varchar2(2000)
	 */
	private String remark;

	/**
	 * 핀사용여부(추가됨. 04-16):char(1)
	 */
	private String pinUseFlag;

	/**
	 * 총충전횟수
	 */
	private int rcgCnt;

	/**
	 * 총충전금액
	 */
	private int sumAmount;
	
	/**
	 * 충전취소버튼
	 */
	private String cancelBtn;

	/**
	 * 가상계좌번호
	 * */
	private String actnNum;
	
	private String isAcntnum;
	
	/**
	 * 가상계좌은행코드
	 * */
	private String bankcd;
	
	/**
	 * 가상계좌은행이름
	 * */
	private String banknm;
	
	
	/**
	 * 개통대리점명
	 */
	private int openAgentNm;

	public String getIsAcntnum() {
		return isAcntnum;
	}

	public void setIsAcntnum(String isAcntnum) {
		this.isAcntnum = isAcntnum;
	}

	public String getActnNum() {
		return actnNum;
	}

	public void setActnNum(String actnNum) {
		this.actnNum = actnNum;
	}

	public String getBankcd() {
		return bankcd;
	}

	public void setBankcd(String bankcd) {
		this.bankcd = bankcd;
	}

	public String getBanknm() {
		return banknm;
	}

	public void setBanknm(String banknm) {
		this.banknm = banknm;
	}

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}

	/**
	 * @param seq
	 *            the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}

	/**
	 * @return the rechargeDate
	 */
	public String getRechargeDate() {
		return rechargeDate;
	}

	/**
	 * @param rechargeDate
	 *            the rechargeDate to set
	 */
	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}

	/**
	 * @return the contractNum
	 */
	public int getContractNum() {
		return contractNum;
	}

	/**
	 * @param contractNum
	 *            the contractNum to set
	 */
	public void setContractNum(int contractNum) {
		this.contractNum = contractNum;
	}

	/**
	 * @return the subscriberNo
	 */
	public String getSubscriberNo() {
		return subscriberNo;
	}

	/**
	 * @param subscriberNo
	 *            the subscriberNo to set
	 */
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}

	/**
	 * @return the chgType
	 */
	public String getChgType() {
		return chgType;
	}

	/**
	 * @param chgType
	 *            the chgType to set
	 */
	public void setChgType(String chgType) {
		this.chgType = chgType;
	}

	/**
	 * @return the reqType
	 */
	public String getReqType() {
		return reqType;
	}

	/**
	 * @param reqType
	 *            the reqType to set
	 */
	public void setReqType(String reqType) {
		this.reqType = reqType;
	}

	/**
	 * @return the reqSrc
	 */
	public String getReqSrc() {
		return reqSrc;
	}

	/**
	 * @param reqSrc
	 *            the reqSrc to set
	 */
	public void setReqSrc(String reqSrc) {
		this.reqSrc = reqSrc;
	}

	/**
	 * @return the rechargeMethod
	 */
	public String getRechargeMethod() {
		return rechargeMethod;
	}

	/**
	 * @param rechargeMethod
	 *            the rechargeMethod to set
	 */
	public void setRechargeMethod(String rechargeMethod) {
		this.rechargeMethod = rechargeMethod;
	}

	/**
	 * @return the rechargeInfo
	 */
	public String getRechargeInfo() {
		return rechargeInfo;
	}

	/**
	 * @param rechargeInfo
	 *            the rechargeInfo to set
	 */
	public void setRechargeInfo(String rechargeInfo) {
		this.rechargeInfo = rechargeInfo;
	}

	/**
	 * @return the inAmount
	 */
	public int getInAmount() {
		return inAmount;
	}

	/**
	 * @param inAmount
	 *            the inAmount to set
	 */
	public void setInAmount(int inAmount) {
		this.inAmount = inAmount;
	}

	/**
	 * @return the point
	 */
	public int getPoint() {
		return point;
	}

	/**
	 * @param point
	 *            the point to set
	 */
	public void setPoint(int point) {
		this.point = point;
	}

	/**
	 * @return the amount
	 */
	public int getAmount() {
		return amount;
	}

	/**
	 * @param amount
	 *            the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	/**
	 * @return the fineFlag
	 */
	public String getFineFlag() {
		return fineFlag;
	}

	/**
	 * @param fineFlag
	 *            the fineFlag to set
	 */
	public void setFineFlag(String fineFlag) {
		this.fineFlag = fineFlag;
	}

	/**
	 * @return the ktResCode
	 */
	public String getKtResCode() {
		return ktResCode;
	}

	/**
	 * @param ktResCode
	 *            the ktResCode to set
	 */
	public void setKtResCode(String ktResCode) {
		this.ktResCode = ktResCode;
	}

	/**
	 * @return the ktResDt
	 */
	public String getKtResDt() {
		return ktResDt;
	}

	/**
	 * @param ktResDt
	 *            the ktResDt to set
	 */
	public void setKtResDt(String ktResDt) {
		this.ktResDt = ktResDt;
	}

	/**
	 * @return the cancelFlag
	 */
	public String getCancelFlag() {
		return cancelFlag;
	}

	/**
	 * @param cancelFlag
	 *            the cancelFlag to set
	 */
	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	/**
	 * @return the cancelDt
	 */
	public String getCancelDt() {
		return cancelDt;
	}

	/**
	 * @param cancelDt
	 *            the cancelDt to set
	 */
	public void setCancelDt(String cancelDt) {
		this.cancelDt = cancelDt;
	}

	/**
	 * @return the basicOldRemains
	 */
	public double getBasicOldRemains() {
		return basicOldRemains;
	}

	/**
	 * @param basicOldRemains
	 *            the basicOldRemains to set
	 */
	public void setBasicOldRemains(double basicOldRemains) {
		this.basicOldRemains = basicOldRemains;
	}

	/**
	 * @return the basicRemains
	 */
	public double getBasicRemains() {
		return basicRemains;
	}

	/**
	 * @param basicRemains
	 *            the basicRemains to set
	 */
	public void setBasicRemains(double basicRemains) {
		this.basicRemains = basicRemains;
	}

	/**
	 * @return the basicOldExpire
	 */
	public String getBasicOldExpire() {
		return basicOldExpire;
	}

	/**
	 * @param basicOldExpire
	 *            the basicOldExpire to set
	 */
	public void setBasicOldExpire(String basicOldExpire) {
		this.basicOldExpire = basicOldExpire;
	}

	/**
	 * @return the basicExpire
	 */
	public String getBasicExpire() {
		return basicExpire;
	}

	/**
	 * @param basicExpire
	 *            the basicExpire to set
	 */
	public void setBasicExpire(String basicExpire) {
		this.basicExpire = basicExpire;
	}

	/**
	 * @return the dataOldRemains
	 */
	public double getDataOldRemains() {
		return dataOldRemains;
	}

	/**
	 * @param dataOldRemains
	 *            the dataOldRemains to set
	 */
	public void setDataOldRemains(double dataOldRemains) {
		this.dataOldRemains = dataOldRemains;
	}

	/**
	 * @return the dataQuota
	 */
	public double getDataQuota() {
		return dataQuota;
	}

	/**
	 * @param dataQuota
	 *            the dataQuota to set
	 */
	public void setDataQuota(double dataQuota) {
		this.dataQuota = dataQuota;
	}

	/**
	 * @return the dataRemains
	 */
	public double getDataRemains() {
		return dataRemains;
	}

	/**
	 * @param dataRemains
	 *            the dataRemains to set
	 */
	public void setDataRemains(double dataRemains) {
		this.dataRemains = dataRemains;
	}

	/**
	 * @return the dataOldExpire
	 */
	public String getDataOldExpire() {
		return dataOldExpire;
	}

	/**
	 * @param dataOldExpire
	 *            the dataOldExpire to set
	 */
	public void setDataOldExpire(String dataOldExpire) {
		this.dataOldExpire = dataOldExpire;
	}

	/**
	 * @return the dataExpire
	 */
	public String getDataExpire() {
		return dataExpire;
	}

	/**
	 * @param dataExpire
	 *            the dataExpire to set
	 */
	public void setDataExpire(String dataExpire) {
		this.dataExpire = dataExpire;
	}

	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}

	/**
	 * @param agentId
	 *            the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	/**
	 * @return the rechargeAgent
	 */
	public String getRechargeAgent() {
		return rechargeAgent;
	}

	/**
	 * @param rechargeAgent
	 *            the rechargeAgent to set
	 */
	public void setRechargeAgent(String rechargeAgent) {
		this.rechargeAgent = rechargeAgent;
	}

	/**
	 * @return the agentCharge
	 */
	public double getAgentCharge() {
		return agentCharge;
	}

	/**
	 * @param agentCharge
	 *            the agentCharge to set
	 */
	public void setAgentCharge(double agentCharge) {
		this.agentCharge = agentCharge;
	}

	/**
	 * @return the adminId
	 */
	public String getAdminId() {
		return adminId;
	}

	/**
	 * @param adminId
	 *            the adminId to set
	 */
	public void setAdminId(String adminId) {
		this.adminId = adminId;
	}

	/**
	 * @return the accessId
	 */
	public String getAccessId() {
		return accessId;
	}

	/**
	 * @param accessId
	 *            the accessId to set
	 */
	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	/**
	 * @return the accessIp
	 */
	public String getAccessIp() {
		return accessIp;
	}

	/**
	 * @param accessIp
	 *            the accessIp to set
	 */
	public void setAccessIp(String accessIp) {
		this.accessIp = accessIp;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark
	 *            the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the pinUseFlag
	 */
	public String getPinUseFlag() {
		return pinUseFlag;
	}

	/**
	 * @param pinUseFlag
	 *            the pinUseFlag to set
	 */
	public void setPinUseFlag(String pinUseFlag) {
		this.pinUseFlag = pinUseFlag;
	}

	/**
	 * @return the rcgCnt
	 */
	public int getRcgCnt() {
		return rcgCnt;
	}

	/**
	 * @param rcgCnt
	 *            the rcgCnt to set
	 */
	public void setRcgCnt(int rcgCnt) {
		this.rcgCnt = rcgCnt;
	}

	/**
	 * @param cancelBtn
	 *            the rcgCnt to get
	 */	
	public String getCancelBtn() {
		return cancelBtn;
	}

	/**
	 * @param cancelBtn
	 *            the rcgCnt to set
	 */	
	public void setCancelBtn(String cancelBtn) {
		this.cancelBtn = cancelBtn;
	}

	/**
	 * @return the sumlAmount
	 */
	public int getSumAmount() {
		return sumAmount;
	}

	/**
	 * @param sumlAmount
	 *            the sumlAmount to set
	 */
	public void setSumAmount(int sumAmount) {
		this.sumAmount = sumAmount;
	}
	
	public String getAdminNm() {
		return adminNm;
	}

	public void setAdminNm(String adminNm) {
		this.adminNm = adminNm;
	}
	
	public int getOpenAgentNm() {
		return openAgentNm;
	}





	public void setOpenAgentNm(int openAgentNm) {
		this.openAgentNm = openAgentNm;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,	ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
