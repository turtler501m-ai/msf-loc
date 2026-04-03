package com.ktmmobile.msf.system.common.mspservice.dto;

import java.io.Serializable;

import com.ktmmobile.msf.common.dto.CommonSearchDto;

/**
 * @Class Name : MspNoticSupportMstDto
 * @Description :
 * MSP 의 공시지원금 DTO 이다.
 * @author : ant
 * @Create Date : 2016. 9. 5.
 */
public class MspNoticSupportMstDto extends CommonSearchDto implements Serializable {
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

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType() {
		return dataType;
	}

}
