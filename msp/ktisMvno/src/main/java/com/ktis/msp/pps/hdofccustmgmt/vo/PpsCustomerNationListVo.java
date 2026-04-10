package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : PpsVacVo
 * @Description : 가상계좌 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="PpsCustomerNationListVo")
public class PpsCustomerNationListVo  extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_KT_COMMOM_CD";

	/**
	 * 코드
	 */
	private String value;
	
	/**
	 * 코드네임 
	 */
	private String label;

	

	



	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}







	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}







	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}







	/**
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}







	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
