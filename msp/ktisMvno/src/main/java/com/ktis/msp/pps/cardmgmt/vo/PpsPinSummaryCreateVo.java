package com.ktis.msp.pps.cardmgmt.vo;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsPinSummaryCreateVo
 * @Description : 핀생성내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsPinSummaryCreateVo")
public class PpsPinSummaryCreateVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	
	/**
	 * PIN생성고유번호:number(20)
	 */
	private int crSeq;

	/**
	 * PIN시작관리번호:varchar2(20)
	 */
	private String startMngCode;

	/**
	 * PIN종료관리번호:varchar2(20)
	 */
	private String endMngCode;

	/**
	 * D = 데이타 카드, V = 기본음성카드:varchar2(2)
	 */
	private String pinGubun;

	/**
	 * O:ONLINE, F:OFFLINE:varchar2(2)
	 */
	private String onoffGubun;

	/**
	 * PIN 길이(11자리기본):number(20)
	 */
	private int pinLength;

	/**
	 * PIN 개수:number(20)
	 */
	private int crCount;

	/**
	 * 등록관리자:varchar2(20)
	 */
	private String crAdminId;

	/**
	 * 등록일자:date(0)
	 */
	private String crDate;

	/**
	 * PIN 금액:number(20)
	 */
	private int startPrice;

	/**
	 * 비고:varchar2(1000)
	 */
	private String remark;
	/**
	 * 마스터코드링크명
	 */
    private String mngCodeStr;
    
    /**
     * 계약번호 링크명
     */
    private String contractNumStr;
    
    /**
     * 계약번호
     */
    private String contractNum;
    
    /**
     * PIN 무료지급여부 (Y:무료지급)
     */
    private String freeFlag;
 	
    /**
	 * 핀무료지급명
	 */
	private String freeFlagNm;

	
	/**
	 * @return the contractNumStr
	 */
	public String getContractNumStr() {
		return contractNumStr;
	}




	/**
	 * @param contractNumStr the contractNumStr to set
	 */
	public void setContractNumStr(String contractNumStr) {
		this.contractNumStr = contractNumStr;
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
	 * @param mngCodeStr the mngCodeStr to set
	 */
	public void setMngCodeStr(String mngCodeStr) {
		this.mngCodeStr = mngCodeStr;
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
	 * @return the startMngCode
	 */
	public String getStartMngCode() {
		return startMngCode;
	}




	/**
	 * @param startMngCode the startMngCode to set
	 */
	public void setStartMngCode(String startMngCode) {
		this.startMngCode = startMngCode;
	}




	/**
	 * @return the endMngCode
	 */
	public String getEndMngCode() {
		return endMngCode;
	}




	/**
	 * @param endMngCode the endMngCode to set
	 */
	public void setEndMngCode(String endMngCode) {
		this.endMngCode = endMngCode;
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
	 * @return the pinLength
	 */
	public int getPinLength() {
		return pinLength;
	}




	/**
	 * @param pinLength the pinLength to set
	 */
	public void setPinLength(int pinLength) {
		this.pinLength = pinLength;
	}




	/**
	 * @return the crCount
	 */
	public int getCrCount() {
		return crCount;
	}




	/**
	 * @param crCount the crCount to set
	 */
	public void setCrCount(int crCount) {
		this.crCount = crCount;
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
	 * @return the mngCodeStr
	 */
	public String getMngCodeStr() {
		return mngCodeStr;
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
