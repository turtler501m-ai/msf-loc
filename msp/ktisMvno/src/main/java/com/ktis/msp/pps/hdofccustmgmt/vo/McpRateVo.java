package com.ktis.msp.pps.hdofccustmgmt.vo;

/* Code Generator Information.
 * generator Version 1.0.0 release 2007/10/10
 * generated Date Tue Sep 16 10:46:44 JST 2014
 */
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : McpRateVo
 * @Description : 요금제 내역 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="McpRateVo")
public class McpRateVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "MCP_RATE";

	/**
	 * RATE_KEY:number(0) <Primary Key>
	 */
	private int rateKey;

	/**
	 * RATE_NAME:varchar2(100)
	 */
	private String rateName;

	/**
	 * RATE_DESC:varchar2(1000)
	 */
	private String rateDesc;

	/**
	 * ACT_FLAG:char(1)
	 */
	private String actFlag;

	/**
	 * RID:varchar2(20)
	 */
	private String rid;

	/**
	 * SYS_RDATE:date(0)
	 */
	private String sysRdate;

	/**
	 * UID:varchar2(20)
	 */
	private String uid;

	/**
	 * SYS_UDATE:date(0)
	 */
	private String sysUdate;

	/**
	 * MONTHLY_FEE:number(0)
	 */
	private int monthlyFee;

	/**
	 * DISCOUNT_PRICE:number(0)
	 */
	private int discountPrice;

	/**
	 * CHARGES:number(0)
	 */
	private int charges;

	/**
	 * VOICE_IN_NETWORK:varchar2(100)
	 */
	private String voiceInNetwork;

	/**
	 * VOICE_MANGOE:varchar2(100)
	 */
	private String voiceMangoe;

	/**
	 * LETTER_SIZE:varchar2(100)
	 */
	private String letterSize;

	/**
	 * DATA_SIZE:varchar2(100)
	 */
	private String dataSize;

	/**
	 * AGREEMENT:varchar2(4)
	 */
	private String agreement;

	/**
	 * TYPE_CODE:varchar2(10)
	 */
	private String typeCode;

	/**
	 * KT_RATE_NAME:varchar2(100)
	 */
	private String ktRateName;

	/**
	 * DATA_TYPE_CODE:varchar2(10)
	 */
	private String dataTypeCode;

	/**
	 * KT_RATE_CODE:varchar2(20)
	 */
	private String ktRateCode;

	/**
	 * PROPORTION01:number(0)
	 */
	private int proportion01;

	/**
	 * PROPORTION02:number(0)
	 */
	private int proportion02;

	/**
	 * PROPORTION03:number(0)
	 */
	private int proportion03;

	/**
	 * PROPORTION04:number(0)
	 */
	private int proportion04;

	/**
	 * RANK_NO:number(0)
	 */
	private int rankNo;
	
	

	/**
	 * @return the rateKey
	 */
	public int getRateKey() {
		return rateKey;
	}



	/**
	 * @param rateKey the rateKey to set
	 */
	public void setRateKey(int rateKey) {
		this.rateKey = rateKey;
	}



	/**
	 * @return the rateName
	 */
	public String getRateName() {
		return rateName;
	}



	/**
	 * @param rateName the rateName to set
	 */
	public void setRateName(String rateName) {
		this.rateName = rateName;
	}



	/**
	 * @return the rateDesc
	 */
	public String getRateDesc() {
		return rateDesc;
	}



	/**
	 * @param rateDesc the rateDesc to set
	 */
	public void setRateDesc(String rateDesc) {
		this.rateDesc = rateDesc;
	}



	/**
	 * @return the actFlag
	 */
	public String getActFlag() {
		return actFlag;
	}



	/**
	 * @param actFlag the actFlag to set
	 */
	public void setActFlag(String actFlag) {
		this.actFlag = actFlag;
	}



	/**
	 * @return the rid
	 */
	public String getRid() {
		return rid;
	}



	/**
	 * @param rid the rid to set
	 */
	public void setRid(String rid) {
		this.rid = rid;
	}



	/**
	 * @return the sysRdate
	 */
	public String getSysRdate() {
		return sysRdate;
	}



	/**
	 * @param sysRdate the sysRdate to set
	 */
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}



	/**
	 * @return the uid
	 */
	public String getUid() {
		return uid;
	}



	/**
	 * @param uid the uid to set
	 */
	public void setUid(String uid) {
		this.uid = uid;
	}



	/**
	 * @return the sysUdate
	 */
	public String getSysUdate() {
		return sysUdate;
	}



	/**
	 * @param sysUdate the sysUdate to set
	 */
	public void setSysUdate(String sysUdate) {
		this.sysUdate = sysUdate;
	}



	/**
	 * @return the monthlyFee
	 */
	public int getMonthlyFee() {
		return monthlyFee;
	}



	/**
	 * @param monthlyFee the monthlyFee to set
	 */
	public void setMonthlyFee(int monthlyFee) {
		this.monthlyFee = monthlyFee;
	}



	/**
	 * @return the discountPrice
	 */
	public int getDiscountPrice() {
		return discountPrice;
	}



	/**
	 * @param discountPrice the discountPrice to set
	 */
	public void setDiscountPrice(int discountPrice) {
		this.discountPrice = discountPrice;
	}



	/**
	 * @return the charges
	 */
	public int getCharges() {
		return charges;
	}



	/**
	 * @param charges the charges to set
	 */
	public void setCharges(int charges) {
		this.charges = charges;
	}



	/**
	 * @return the voiceInNetwork
	 */
	public String getVoiceInNetwork() {
		return voiceInNetwork;
	}



	/**
	 * @param voiceInNetwork the voiceInNetwork to set
	 */
	public void setVoiceInNetwork(String voiceInNetwork) {
		this.voiceInNetwork = voiceInNetwork;
	}



	/**
	 * @return the voiceMangoe
	 */
	public String getVoiceMangoe() {
		return voiceMangoe;
	}



	/**
	 * @param voiceMangoe the voiceMangoe to set
	 */
	public void setVoiceMangoe(String voiceMangoe) {
		this.voiceMangoe = voiceMangoe;
	}



	/**
	 * @return the letterSize
	 */
	public String getLetterSize() {
		return letterSize;
	}



	/**
	 * @param letterSize the letterSize to set
	 */
	public void setLetterSize(String letterSize) {
		this.letterSize = letterSize;
	}



	/**
	 * @return the dataSize
	 */
	public String getDataSize() {
		return dataSize;
	}



	/**
	 * @param dataSize the dataSize to set
	 */
	public void setDataSize(String dataSize) {
		this.dataSize = dataSize;
	}



	/**
	 * @return the agreement
	 */
	public String getAgreement() {
		return agreement;
	}



	/**
	 * @param agreement the agreement to set
	 */
	public void setAgreement(String agreement) {
		this.agreement = agreement;
	}



	/**
	 * @return the typeCode
	 */
	public String getTypeCode() {
		return typeCode;
	}



	/**
	 * @param typeCode the typeCode to set
	 */
	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}



	/**
	 * @return the ktRateName
	 */
	public String getKtRateName() {
		return ktRateName;
	}



	/**
	 * @param ktRateName the ktRateName to set
	 */
	public void setKtRateName(String ktRateName) {
		this.ktRateName = ktRateName;
	}



	/**
	 * @return the dataTypeCode
	 */
	public String getDataTypeCode() {
		return dataTypeCode;
	}



	/**
	 * @param dataTypeCode the dataTypeCode to set
	 */
	public void setDataTypeCode(String dataTypeCode) {
		this.dataTypeCode = dataTypeCode;
	}



	/**
	 * @return the ktRateCode
	 */
	public String getKtRateCode() {
		return ktRateCode;
	}



	/**
	 * @param ktRateCode the ktRateCode to set
	 */
	public void setKtRateCode(String ktRateCode) {
		this.ktRateCode = ktRateCode;
	}



	/**
	 * @return the proportion01
	 */
	public int getProportion01() {
		return proportion01;
	}



	/**
	 * @param proportion01 the proportion01 to set
	 */
	public void setProportion01(int proportion01) {
		this.proportion01 = proportion01;
	}



	/**
	 * @return the proportion02
	 */
	public int getProportion02() {
		return proportion02;
	}



	/**
	 * @param proportion02 the proportion02 to set
	 */
	public void setProportion02(int proportion02) {
		this.proportion02 = proportion02;
	}



	/**
	 * @return the proportion03
	 */
	public int getProportion03() {
		return proportion03;
	}



	/**
	 * @param proportion03 the proportion03 to set
	 */
	public void setProportion03(int proportion03) {
		this.proportion03 = proportion03;
	}



	/**
	 * @return the proportion04
	 */
	public int getProportion04() {
		return proportion04;
	}



	/**
	 * @param proportion04 the proportion04 to set
	 */
	public void setProportion04(int proportion04) {
		this.proportion04 = proportion04;
	}



	/**
	 * @return the rankNo
	 */
	public int getRankNo() {
		return rankNo;
	}



	/**
	 * @param rankNo the rankNo to set
	 */
	public void setRankNo(int rankNo) {
		this.rankNo = rankNo;
	}





	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
