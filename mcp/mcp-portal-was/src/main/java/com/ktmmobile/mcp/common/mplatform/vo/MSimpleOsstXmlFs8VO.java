package com.ktmmobile.mcp.common.mplatform.vo;

import com.ktmmobile.mcp.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MSimpleOsstXmlFs8VO extends CommonXmlVO{
	
	private String urlAdr; 			// URL주소
	private String fathTransacId;   // 안면인증 트랜잭션 아이디
	private String resltCd;    		// 결과코드 
									// 0000 정상
									// CD01 오픈일자 오픈일자 미도래
									// CD02 안면인증 스킵 권한 보유자
									// CD03 안면인증 미오픈 대면 사업자
									// CD04 안면인증 미오픈 비대면 사업자
									// CD05 MIS 장애 스킵
									// CD06 안면인증 미대상 접점
	private String resltSbst;   	// 결과내용 (정상처리가 아닐때 사유)
	
    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.urlAdr = XmlParse.getChildValue(this.body, "urlAdr");
        this.fathTransacId = XmlParse.getChildValue(this.body, "fathTransacId");
        this.resltCd = XmlParse.getChildValue(this.body, "resltCd");
        this.resltSbst = XmlParse.getChildValue(this.body, "resltSbst");
    }

	public String getUrlAdr() {
		return urlAdr;
	}

	public void setUrlAdr(String urlAdr) {
		this.urlAdr = urlAdr;
	}

	public String getFathTransacId() {
		return fathTransacId;
	}

	public void setFathTransacId(String fathTransacId) {
		this.fathTransacId = fathTransacId;
	}

	public String getResltCd() {
		return resltCd;
	}

	public void setResltCd(String resltCd) {
		this.resltCd = resltCd;
	}

	public String getResltSbst() {
		return resltSbst;
	}

	public void setResltSbst(String resltSbst) {
		this.resltSbst = resltSbst;
	}
}