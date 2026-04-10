package com.ktmmobile.mcp.appform.dto;

import java.io.Serializable;

public class OsstReqDto implements Serializable {



    private static final long serialVersionUID = 1L;


    /** 번호이동 전화번호	11	M	번호이동할 전화번호 */
    private String npTlphNo	 ; //

    /** 변경전번호이동사업자코드	3	M	* 코드정의서 참조 */
    private String bchngNpCommCmpnCd; //

    /** 판매회사코드	3	M	 */
    private String slsCmpnCd; //

    /** 고객유형코드	2	M	* 코드정의서 참조 */
    private String custTypeCd;

    /** 개인사업자 여부	1	M	개인사업자인 경우 Y, defult N */
    private String indvBizrYn;

    /** 고객식별번호구분코드	2	M	* 코드정의서 참조 */
    private String custIdntNoIndCd;

    /** 고객식별번호	20	C	 */
    private String custIdntNo;

    /** 고객명	60	M	법인인 경우 법인명*/
    private String custNm;

	public String getNpTlphNo() {
		return npTlphNo;
	}

	public void setNpTlphNo(String npTlphNo) {
		this.npTlphNo = npTlphNo;
	}

	public String getBchngNpCommCmpnCd() {
		return bchngNpCommCmpnCd;
	}

	public void setBchngNpCommCmpnCd(String bchngNpCommCmpnCd) {
		this.bchngNpCommCmpnCd = bchngNpCommCmpnCd;
	}

	public String getSlsCmpnCd() {
		return slsCmpnCd;
	}

	public void setSlsCmpnCd(String slsCmpnCd) {
		this.slsCmpnCd = slsCmpnCd;
	}

	public String getCustTypeCd() {
		return custTypeCd;
	}

	public void setCustTypeCd(String custTypeCd) {
		this.custTypeCd = custTypeCd;
	}

	public String getIndvBizrYn() {
		return indvBizrYn;
	}

	public void setIndvBizrYn(String indvBizrYn) {
		this.indvBizrYn = indvBizrYn;
	}

	public String getCustIdntNoIndCd() {
		return custIdntNoIndCd;
	}

	public void setCustIdntNoIndCd(String custIdntNoIndCd) {
		this.custIdntNoIndCd = custIdntNoIndCd;
	}

	public String getCustIdntNo() {
		return custIdntNo;
	}

	public void setCustIdntNo(String custIdntNo) {
		this.custIdntNo = custIdntNo;
	}

	public String getCustNm() {
		return custNm;
	}

	public void setCustNm(String custNm) {
		this.custNm = custNm;
	}





}
