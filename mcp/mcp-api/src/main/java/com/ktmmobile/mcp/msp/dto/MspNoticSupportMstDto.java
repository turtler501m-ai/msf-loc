package com.ktmmobile.mcp.msp.dto;

import java.io.Serializable;

/**
 * @Class Name : MspNoticSupportMstDto
 * @Description :
 * MSP 의 공시지원금 DTO 이다.
 * @author : ant
 * @Create Date : 2016. 9. 5.
 */
public class MspNoticSupportMstDto implements Serializable {
	private static final long serialVersionUID = 1L;

	 /** 요금제 코드 */
	private String rateCd;

	 /** 요금제 명 */
	private String rateNm;

	 /** 모델명 */
	private String prdtNm;

	 /** 단말유형 */
	private String prdtIndCd;

	 /** 출고가 */
	private String outUnitPric;

	 /** 공시지원급 */
	private String subsdAmt;

	 /** 판매가 */
	private String pricAmt;

	 /** 공시일 */
	private String applStrtDt;

	 /** 정렬방식 */
	private String sortType;

	private String dataType;

	
    private int apiParam1 = 0;
    private int apiParam2 = 0;

	
	public String getSortType() {
		return sortType;
	}

	public void setSortType(String sortType) {
		this.sortType = sortType;
	}

	public String getOutUnitPric() {
		return outUnitPric;
	}

	public void setOutUnitPric(String outUnitPric) {
		this.outUnitPric = outUnitPric;
	}

	public String getSubsdAmt() {
		return subsdAmt;
	}

	public void setSubsdAmt(String subsdAmt) {
		this.subsdAmt = subsdAmt;
	}

	public String getPricAmt() {
		return pricAmt;
	}

	public void setPricAmt(String pricAmt) {
		this.pricAmt = pricAmt;
	}

	public String getApplStrtDt() {
		return applStrtDt;
	}

	public void setApplStrtDt(String applStrtDt) {
		this.applStrtDt = applStrtDt;
	}

	public String getPrdtNm() {
		return prdtNm;
	}

	public void setPrdtNm(String prdtNm) {
		this.prdtNm = prdtNm;
	}

	public String getPrdtIndCd() {
		return prdtIndCd;
	}

	public void setPrdtIndCd(String prdtIndCd) {
		this.prdtIndCd = prdtIndCd;
	}

	public String getRateCd() {
		return rateCd;
	}

	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}

	public String getRateNm() {
		return rateNm;
	}

	public void setRateNm(String rateNm) {
		this.rateNm = rateNm;
	}

	public int getApiParam1() {
		return apiParam1;
	}

	public void setApiParam1(int apiParam1) {
		this.apiParam1 = apiParam1;
	}

	public int getApiParam2() {
		return apiParam2;
	}

	public void setApiParam2(int apiParam2) {
		this.apiParam2 = apiParam2;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType() {
		return dataType;
	}
}
