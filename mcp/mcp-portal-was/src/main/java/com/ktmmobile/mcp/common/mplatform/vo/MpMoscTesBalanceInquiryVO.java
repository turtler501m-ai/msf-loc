package com.ktmmobile.mcp.common.mplatform.vo;

import com.ktmmobile.mcp.common.util.XmlParse;

public class MpMoscTesBalanceInquiryVO extends CommonXmlVO{

	private String ctn;				//전화번호
	private String efctStrtDt;		//지능망 등록 일자
	private String cntrStatCd;		//가입자 상태
	private String fndtAl;			//기본알의 잔액
	private String rcvAl;			//선물 받은 알의 잔액
	private String freeSmsAl;		//문자로만 소진되는 알의 잔액
	private String apndRchrAl;	//긴급 충전한 알의 잔액
	private String alzakNo;		//알짝 번호
	private String xtnAl;			//기본알 소진시 자동으로 충전되는 알의 잔액
	private String imgnAl;			//영상통화시 소진되는 알의 잔액
	private String dataAl;			//데이터 사용시 소진되는 알의 잔액
	private String ipmaxAl;		//정보료 상한알
	private String ipvasAl;			//정보료 부가알
	private String forwardR;		//이월알

	@Override
    public void parse() {

        this.ctn = XmlParse.getChildValue(this.body, "ctn");
        this.efctStrtDt = XmlParse.getChildValue(this.body, "efctStrtDt");
        this.cntrStatCd = XmlParse.getChildValue(this.body, "cntrStatCd");
        this.fndtAl = XmlParse.getChildValue(this.body, "fndtAl");
        this.rcvAl = XmlParse.getChildValue(this.body, "rcvAl");
        this.freeSmsAl = XmlParse.getChildValue(this.body, "freeSmsAl");
        this.apndRchrAl = XmlParse.getChildValue(this.body, "apndRchrAl");
        this.alzakNo = XmlParse.getChildValue(this.body, "alzakNo");
        this.xtnAl = XmlParse.getChildValue(this.body, "xtnAl");
        this.imgnAl = XmlParse.getChildValue(this.body, "imgnAl");
        this.dataAl = XmlParse.getChildValue(this.body, "dataAl");
        this.ipmaxAl = XmlParse.getChildValue(this.body, "ipmaxAl");
        this.ipvasAl = XmlParse.getChildValue(this.body, "ipvasAl");
        this.forwardR = XmlParse.getChildValue(this.body, "forwardR");

    }

    public String getCtn() {
		return ctn;
	}

	public void setCtn(String ctn) {
		this.ctn = ctn;
	}

	public String getEfctStrtDt() {
		return efctStrtDt;
	}

	public void setEfctStrtDt(String efctStrtDt) {
		this.efctStrtDt = efctStrtDt;
	}

	public String getCntrStatCd() {
		return cntrStatCd;
	}

	public void setCntrStatCd(String cntrStatCd) {
		this.cntrStatCd = cntrStatCd;
	}

	public String getFndtAl() {
		return fndtAl;
	}

	public void setFndtAl(String fndtAl) {
		this.fndtAl = fndtAl;
	}

	public String getRcvAl() {
		return rcvAl;
	}

	public void setRcvAl(String rcvAl) {
		this.rcvAl = rcvAl;
	}

	public String getFreeSmsAl() {
		return freeSmsAl;
	}

	public void setFreeSmsAl(String freeSmsAl) {
		this.freeSmsAl = freeSmsAl;
	}

	public String getApndRchrAl() {
		return apndRchrAl;
	}

	public void setApndRchrAl(String apndRchrAl) {
		this.apndRchrAl = apndRchrAl;
	}

	public String getAlzakNo() {
		return alzakNo;
	}

	public void setAlzakNo(String alzakNo) {
		this.alzakNo = alzakNo;
	}

	public String getXtnAl() {
		return xtnAl;
	}

	public void setXtnAl(String xtnAl) {
		this.xtnAl = xtnAl;
	}

	public String getImgnAl() {
		return imgnAl;
	}

	public void setImgnAl(String imgnAl) {
		this.imgnAl = imgnAl;
	}

	public String getDataAl() {
		return dataAl;
	}

	public void setDataAl(String dataAl) {
		this.dataAl = dataAl;
	}

	public String getIpmaxAl() {
		return ipmaxAl;
	}

	public void setIpmaxAl(String ipmaxAl) {
		this.ipmaxAl = ipmaxAl;
	}

	public String getIpvasAl() {
		return ipvasAl;
	}

	public void setIpvasAl(String ipvasAl) {
		this.ipvasAl = ipvasAl;
	}

	public String getForwardR() {
		return forwardR;
	}

	public void setForwardR(String forwardR) {
		this.forwardR = forwardR;
	}
}
