package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.jdom.Element;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;


/**
 *
 * x88
 * 요금상품예약변경(X88)
 *
 * x89
 * 요금상품예약변경조회(X89)
 */
public class MoscFarPriceResDto extends com.ktmmobile.msf.domains.form.common.mplatform.vo.CommonXmlVO {

	

	//x89
	private String prdcCd;	//상품코드
	private String prdcNm; //상품명
	private String basicAmt; //요금제 변경 대상 기본료
	private String aplyDate; //요금제 변경 신청일자
	private String efctStDate; //요금제 변경 적응일자




	public String getPrdcCd() {
		return prdcCd;
	}

	public void setPrdcCd(String prdcCd) {
		this.prdcCd = prdcCd;
	}

	public String getPrdcNm() {
		return prdcNm;
	}

	public void setPrdcNm(String prdcNm) {
		this.prdcNm = prdcNm;
	}

	public String getBasicAmt() {
		return basicAmt;
	}

	public void setBasicAmt(String basicAmt) {
		this.basicAmt = basicAmt;
	}

	public String getAplyDate() {
		return aplyDate;
	}

	public void setAplyDate(String aplyDate) {
		this.aplyDate = aplyDate;
	}

	public String getEfctStDate() {
		return efctStDate;
	}

	public void setEfctStDate(String efctStDate) {
		this.efctStDate = efctStDate;
	}



	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {


		this.prdcCd = XmlParse.getChildValue(this.body, "prdcCd"); //상품코드
		this.prdcNm = XmlParse.getChildValue(this.body, "prdcNm"); //상품명
		this.basicAmt = XmlParse.getChildValue(this.body, "basicAmt"); //요금제 변경 대상 기본료
		this.aplyDate = XmlParse.getChildValue(this.body, "aplyDate"); //요금제 변경 신청일자
		this.efctStDate = XmlParse.getChildValue(this.body, "efctStDate"); //요금제 변경 적응일자

		}

	
}

