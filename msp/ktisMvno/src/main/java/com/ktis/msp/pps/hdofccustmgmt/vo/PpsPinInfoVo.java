package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsPinInfoVo
 * @Description : pin 정보 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsPinInfoVo")
public class PpsPinInfoVo extends BaseVo  implements Serializable {

	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	
	public static final String TABLE = "PPS_PIN_INFO";

	/**
	 * 관리번호(15자리)-YYMM+회차2자리+연번9자리 예)140400000000000~140400000000999, 140401000000000~140401000009999:varchar2(15) <Primary Key>
	 */
	private String mngCode;

	/**
	 * 핀번호 - 암호화(11자리 기본):varchar2(50)
	 */
	private String pinNumber;

	/**
	 * 만료일자 - 기본2년:date(0)
	 */
	private String expireDate;

	/**
	 * D = 데이타 카드, V = 기본음성카드:varchar2(2)
	 */
	private String pinGubun;
	
	private String pinGubunNm;

	/**
	 * O:ONLINE, F:OFFLINE:varchar2(2)
	 */
	private String onoffGubun;
	
	private String onoffGubunNm;

	/**
	 * 상태(CR:Create, OT:출고, OP:Open개통, RE:Recharge, CL:Close, ST:Stop):varchar2(5)
	 */
	private String status;
	
	private String statusNm;

	/**
	 * 액면가 - 충전이 이루어지는 금액:number(8)
	 */
	private int startPrice;

	/**
	 * 현재가격, 충전 후 0원:number(8)
	 */
	private int nowPrice;

	/**
	 * 핀 생성관리자:varchar2(20)
	 */
	private String crAdminId;
	
	private String crAdminNm;

	/**
	 * 핀 생성일자:date(0)
	 */
	private String crDate;

	/**
	 * 핀 생성 순번--GROUP단위, 한번에 여러장 생성할때 한 SEQ:number(20)
	 */
	private int crSn;

	/**
	 * 핀 생성고유번호(SUMMARY NUMBER):number(20)
	 */
	private int crSeq;

	/**
	 * 출고여부, Y=출고완료, N=미출고 , 딜러인경우 출고된것중에 개통 가능:char(1)
	 */
	private String outFlag;
	
	private String outFlagNm;

	/**
	 * 출고일자:date(0)
	 */
	private String outDate;

	/**
	 * 핀 출고관리자:varchar2(20)
	 */
	private String outAdminId;
	
	private String outAdminNm;

	/**
	 * 출고대리점구분 A=일반대리점출고, C=딜러점출고:varchar2(1)
	 */
	private String outAgentType;
	
	private String outAgentTypeNm;

	/**
	 * 일반대리점코드:varchar2(20)
	 */
	private String outAgentId;
	
	private String outAgentNm;

	/**
	 * 핀 출고된 딜러점:varchar2(20)
	 */
	private String outDealerId;
	
	private String outDealerNm;

	/**
	 * 출고가, 즉판매가, 회계반영금액:number(8)
	 */
	private int outPrice;

	/**
	 * 출고처리고유번호:number(20)
	 */
	private int outSeq;

	/**
	 * 개통여부, N=미개통, Y=개통(수금완료), 판매점은 출고와 동시에 개통됨.:char(1)
	 */
	private String openFlag;
	
	private String openFlagNm;

	/**
	 * 개통관리자:varchar2(20)
	 */
	private String openAdminId;
	
	private String openAdminNm;

	/**
	 * 개통일자:date(0)
	 */
	private String openDate;

	/**
	 * 개통처리고유번호:number(20)
	 */
	private int openSeq;

	/**
	 * 온라인판매여부:char(1)
	 */
	private String saleFlag;
	
	private String saleFlagNm;

	/**
	 * 온라인판매관리자:varchar2(20)
	 */
	private String saleAdminId;
	
	private String saleAdminNm;

	/**
	 * 온라인판매일자:date(0)
	 */
	private String saleDate;

	/**
	 * 온라인판매고유번호:number(20)
	 */
	private int saleSeq;

	/**
	 * 충전여부(Y:충전완료, N:미충전):char(1)
	 */
	private String rcgFlag;
	
	private String rcgFlagNm;

	/**
	 * 충전일자:date(0)
	 */
	private String rcgDate;

	/**
	 * 충전 처리된 SEQ = RCG TABLE의 SEQ:number(9)
	 */
	private int rcgSeq;

	/**
	 * 충전 휴대폰 계정번호 :number(9)
	 */
	private int contractNum;

	/**
	 * 특이사항:varchar2(1000)
	 */
	private String remark;
	
	
	private String retCode;
	
	private String retMsg;
	
	/**
	 * 무료여부 
	 */
	
	private String freeFlag;
	
	private String freeFlagNm;
	
	
	
	
	/**
	 * @return the mngCode
	 */
	public String getMngCode() {
		return mngCode;
	}







	/**
	 * @param mngCode the mngCode to set
	 */
	public void setMngCode(String mngCode) {
		this.mngCode = mngCode;
	}







	/**
	 * @return the pinNumber
	 */
	public String getPinNumber() {
		return pinNumber;
	}







	/**
	 * @param pinNumber the pinNumber to set
	 */
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}







	/**
	 * @return the expireDate
	 */
	public String getExpireDate() {
		return expireDate;
	}







	/**
	 * @param expireDate the expireDate to set
	 */
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}







	/**
	 * @return the pinGubun
	 */
	public String getPinGubun() {
		return pinGubun;
	}







	/**
	 * @param pinGubun the pinGubun to set
	 */
	public void setPinGubun(String pinGubun) {
		this.pinGubun = pinGubun;
	}







	/**
	 * @return the pinGubunNm
	 */
	public String getPinGubunNm() {
		return pinGubunNm;
	}







	/**
	 * @param pinGubunNm the pinGubunNm to set
	 */
	public void setPinGubunNm(String pinGubunNm) {
		this.pinGubunNm = pinGubunNm;
	}







	/**
	 * @return the onoffGubun
	 */
	public String getOnoffGubun() {
		return onoffGubun;
	}







	/**
	 * @param onoffGubun the onoffGubun to set
	 */
	public void setOnoffGubun(String onoffGubun) {
		this.onoffGubun = onoffGubun;
	}







	/**
	 * @return the onoffGubunNm
	 */
	public String getOnoffGubunNm() {
		return onoffGubunNm;
	}







	/**
	 * @param onoffGubunNm the onoffGubunNm to set
	 */
	public void setOnoffGubunNm(String onoffGubunNm) {
		this.onoffGubunNm = onoffGubunNm;
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
	 * @return the startPrice
	 */
	public int getStartPrice() {
		return startPrice;
	}







	/**
	 * @param startPrice the startPrice to set
	 */
	public void setStartPrice(int startPrice) {
		this.startPrice = startPrice;
	}







	/**
	 * @return the nowPrice
	 */
	public int getNowPrice() {
		return nowPrice;
	}







	/**
	 * @param nowPrice the nowPrice to set
	 */
	public void setNowPrice(int nowPrice) {
		this.nowPrice = nowPrice;
	}







	/**
	 * @return the crAdminId
	 */
	public String getCrAdminId() {
		return crAdminId;
	}







	/**
	 * @param crAdminId the crAdminId to set
	 */
	public void setCrAdminId(String crAdminId) {
		this.crAdminId = crAdminId;
	}







	/**
	 * @return the crAdminNm
	 */
	public String getCrAdminNm() {
		return crAdminNm;
	}







	/**
	 * @param crAdminNm the crAdminNm to set
	 */
	public void setCrAdminNm(String crAdminNm) {
		this.crAdminNm = crAdminNm;
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
	 * @return the crSn
	 */
	public int getCrSn() {
		return crSn;
	}







	/**
	 * @param crSn the crSn to set
	 */
	public void setCrSn(int crSn) {
		this.crSn = crSn;
	}







	/**
	 * @return the crSeq
	 */
	public int getCrSeq() {
		return crSeq;
	}







	/**
	 * @param crSeq the crSeq to set
	 */
	public void setCrSeq(int crSeq) {
		this.crSeq = crSeq;
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
	 * @return the outAdminId
	 */
	public String getOutAdminId() {
		return outAdminId;
	}







	/**
	 * @param outAdminId the outAdminId to set
	 */
	public void setOutAdminId(String outAdminId) {
		this.outAdminId = outAdminId;
	}







	/**
	 * @return the outAdminNm
	 */
	public String getOutAdminNm() {
		return outAdminNm;
	}







	/**
	 * @param outAdminNm the outAdminNm to set
	 */
	public void setOutAdminNm(String outAdminNm) {
		this.outAdminNm = outAdminNm;
	}







	/**
	 * @return the outAgentType
	 */
	public String getOutAgentType() {
		return outAgentType;
	}







	/**
	 * @param outAgentType the outAgentType to set
	 */
	public void setOutAgentType(String outAgentType) {
		this.outAgentType = outAgentType;
	}







	/**
	 * @return the outAgentTypeNm
	 */
	public String getOutAgentTypeNm() {
		return outAgentTypeNm;
	}







	/**
	 * @param outAgentTypeNm the outAgentTypeNm to set
	 */
	public void setOutAgentTypeNm(String outAgentTypeNm) {
		this.outAgentTypeNm = outAgentTypeNm;
	}







	/**
	 * @return the outAgentId
	 */
	public String getOutAgentId() {
		return outAgentId;
	}







	/**
	 * @param outAgentId the outAgentId to set
	 */
	public void setOutAgentId(String outAgentId) {
		this.outAgentId = outAgentId;
	}







	/**
	 * @return the outAgentNm
	 */
	public String getOutAgentNm() {
		return outAgentNm;
	}







	/**
	 * @param outAgentNm the outAgentNm to set
	 */
	public void setOutAgentNm(String outAgentNm) {
		this.outAgentNm = outAgentNm;
	}







	/**
	 * @return the outDealerId
	 */
	public String getOutDealerId() {
		return outDealerId;
	}







	/**
	 * @param outDealerId the outDealerId to set
	 */
	public void setOutDealerId(String outDealerId) {
		this.outDealerId = outDealerId;
	}







	/**
	 * @return the outDealerNm
	 */
	public String getOutDealerNm() {
		return outDealerNm;
	}







	/**
	 * @param outDealerNm the outDealerNm to set
	 */
	public void setOutDealerNm(String outDealerNm) {
		this.outDealerNm = outDealerNm;
	}







	/**
	 * @return the outPrice
	 */
	public int getOutPrice() {
		return outPrice;
	}







	/**
	 * @param outPrice the outPrice to set
	 */
	public void setOutPrice(int outPrice) {
		this.outPrice = outPrice;
	}







	/**
	 * @return the outSeq
	 */
	public int getOutSeq() {
		return outSeq;
	}







	/**
	 * @param outSeq the outSeq to set
	 */
	public void setOutSeq(int outSeq) {
		this.outSeq = outSeq;
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
	 * @return the openAdminId
	 */
	public String getOpenAdminId() {
		return openAdminId;
	}







	/**
	 * @param openAdminId the openAdminId to set
	 */
	public void setOpenAdminId(String openAdminId) {
		this.openAdminId = openAdminId;
	}







	/**
	 * @return the openAdminNm
	 */
	public String getOpenAdminNm() {
		return openAdminNm;
	}







	/**
	 * @param openAdminNm the openAdminNm to set
	 */
	public void setOpenAdminNm(String openAdminNm) {
		this.openAdminNm = openAdminNm;
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
	 * @return the openSeq
	 */
	public int getOpenSeq() {
		return openSeq;
	}







	/**
	 * @param openSeq the openSeq to set
	 */
	public void setOpenSeq(int openSeq) {
		this.openSeq = openSeq;
	}







	/**
	 * @return the saleFlag
	 */
	public String getSaleFlag() {
		return saleFlag;
	}







	/**
	 * @param saleFlag the saleFlag to set
	 */
	public void setSaleFlag(String saleFlag) {
		this.saleFlag = saleFlag;
	}







	/**
	 * @return the saleFlagNm
	 */
	public String getSaleFlagNm() {
		return saleFlagNm;
	}







	/**
	 * @param saleFlagNm the saleFlagNm to set
	 */
	public void setSaleFlagNm(String saleFlagNm) {
		this.saleFlagNm = saleFlagNm;
	}







	/**
	 * @return the saleAdminId
	 */
	public String getSaleAdminId() {
		return saleAdminId;
	}







	/**
	 * @param saleAdminId the saleAdminId to set
	 */
	public void setSaleAdminId(String saleAdminId) {
		this.saleAdminId = saleAdminId;
	}







	/**
	 * @return the saleAdminNm
	 */
	public String getSaleAdminNm() {
		return saleAdminNm;
	}







	/**
	 * @param saleAdminNm the saleAdminNm to set
	 */
	public void setSaleAdminNm(String saleAdminNm) {
		this.saleAdminNm = saleAdminNm;
	}







	/**
	 * @return the saleDate
	 */
	public String getSaleDate() {
		return saleDate;
	}







	/**
	 * @param saleDate the saleDate to set
	 */
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}







	/**
	 * @return the saleSeq
	 */
	public int getSaleSeq() {
		return saleSeq;
	}







	/**
	 * @param saleSeq the saleSeq to set
	 */
	public void setSaleSeq(int saleSeq) {
		this.saleSeq = saleSeq;
	}







	/**
	 * @return the rcgFlag
	 */
	public String getRcgFlag() {
		return rcgFlag;
	}







	/**
	 * @param rcgFlag the rcgFlag to set
	 */
	public void setRcgFlag(String rcgFlag) {
		this.rcgFlag = rcgFlag;
	}







	/**
	 * @return the rcgFlagNm
	 */
	public String getRcgFlagNm() {
		return rcgFlagNm;
	}







	/**
	 * @param rcgFlagNm the rcgFlagNm to set
	 */
	public void setRcgFlagNm(String rcgFlagNm) {
		this.rcgFlagNm = rcgFlagNm;
	}







	/**
	 * @return the rcgDate
	 */
	public String getRcgDate() {
		return rcgDate;
	}







	/**
	 * @param rcgDate the rcgDate to set
	 */
	public void setRcgDate(String rcgDate) {
		this.rcgDate = rcgDate;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}







	/**
	 * @return the freeFlag
	 */
	public String getFreeFlag() {
		return freeFlag;
	}







	/**
	 * @param freeFlag the freeFlag to set
	 */
	public void setFreeFlag(String freeFlag) {
		this.freeFlag = freeFlag;
	}







	/**
	 * @return the freeFlagNm
	 */
	public String getFreeFlagNm() {
		return freeFlagNm;
	}







	/**
	 * @param freeFlagNm the freeFlagNm to set
	 */
	public void setFreeFlagNm(String freeFlagNm) {
		this.freeFlagNm = freeFlagNm;
	}







	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
