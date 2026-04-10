package com.ktmmobile.msf.domains.form.common.mplatform.vo;

import com.ktmmobile.msf.domains.form.common.util.XmlParse;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

public class MSimpleOsstXmlFt0VO extends CommonXmlVO{

	
	private String trtResltCd; 		// 처리결과코드 
									// 0000 정상
									// CD01 오픈일자 오픈일자 미도래
									// CD02 안면인증 스킵 권한 보유자
									// CD03 안면인증 미오픈 대면 사업자
									// CD04 안면인증 미오픈 비대면 사업자
									// CD05 MIS 장애 스킵, CD06 안면인증 미대상 접점
	private String trtResltSbst;    // 처리결과내용 (안면인증 미대상 사유)
	private String fathTxnRegYn;    // 안면인증내역등록여부 (Y : 안면인증 내역 등록 (MIS장애, 권한자 스킵), N : 안면인증 내역 미등록 (안면인증 미대상)
	private String stbznPerdYn;   	// 안정화기간여부 (안면인증 내역 실패케이스 오더 업무처리 가능 여부 Y : 안정화 기간 적용, N : 안정화 기간 미적용)
	
    @Override
    public void parse() throws UnsupportedEncodingException, ParseException {
        this.trtResltCd = XmlParse.getChildValue(this.body, "trtResltCd");
        this.trtResltSbst = XmlParse.getChildValue(this.body, "trtResltSbst");
        this.fathTxnRegYn = XmlParse.getChildValue(this.body, "fathTxnRegYn");
        this.stbznPerdYn = XmlParse.getChildValue(this.body, "stbznPerdYn");
    }

	public String getTrtResltCd() {
		return trtResltCd;
	}

	public void setTrtResltCd(String trtResltCd) {
		this.trtResltCd = trtResltCd;
	}

	public String getTrtResltSbst() {
		return trtResltSbst;
	}

	public void setTrtResltSbst(String trtResltSbst) {
		this.trtResltSbst = trtResltSbst;
	}

	public String getFathTxnRegYn() {
		return fathTxnRegYn;
	}

	public void setFathTxnRegYn(String fathTxnRegYn) {
		this.fathTxnRegYn = fathTxnRegYn;
	}

	public String getStbznPerdYn() {
		return stbznPerdYn;
	}

	public void setStbznPerdYn(String stbznPerdYn) {
		this.stbznPerdYn = stbznPerdYn;
	}
}