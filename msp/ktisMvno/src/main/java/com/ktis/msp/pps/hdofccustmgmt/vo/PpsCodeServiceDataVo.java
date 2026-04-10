package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsCodeServiceDataVo
 * @Description : 요금제 용량정보  VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsCodeServiceDataVo")
public class PpsCodeServiceDataVo  extends BaseVo  implements Serializable {

/**
 * serialVersion UID
 */
	private static final long serialVersionUID = 6546990189880359258L;


	public static final String TABLE = "PPS_CODE_SERVICE_DATA";

	/**
	 * 요금제코드(100M 11, 300M 12, 500M 13 ...):varchar2(15)
	 */
	private String serviceDataId;

	/**
	 * 데이타용량(100M, 1G, ...):varchar2(150)
	 */
	private String serviceDataAmount;

	/**
	 * 충전금액:number(10)
	 */
	private int serviceCharge;

	/**
	 * 부가서비스코드:varchar2(300)
	 */
	private String soc;

	
	/**
	 * @return the serviceDataId
	 */
	public String getServiceDataId() {
		return serviceDataId;
	}


	/**
	 * @param serviceDataId the serviceDataId to set
	 */
	public void setServiceDataId(String serviceDataId) {
		this.serviceDataId = serviceDataId;
	}


	/**
	 * @return the serviceDataAmount
	 */
	public String getServiceDataAmount() {
		return serviceDataAmount;
	}


	/**
	 * @param serviceDataAmount the serviceDataAmount to set
	 */
	public void setServiceDataAmount(String serviceDataAmount) {
		this.serviceDataAmount = serviceDataAmount;
	}


	/**
	 * @return the serviceCharge
	 */
	public int getServiceCharge() {
		return serviceCharge;
	}


	/**
	 * @param serviceCharge the serviceCharge to set
	 */
	public void setServiceCharge(int serviceCharge) {
		this.serviceCharge = serviceCharge;
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


	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
