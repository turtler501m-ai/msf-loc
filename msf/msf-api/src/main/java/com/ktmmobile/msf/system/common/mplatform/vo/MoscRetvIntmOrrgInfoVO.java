package com.ktmmobile.msf.system.common.mplatform.vo;

import com.ktmmobile.msf.system.common.util.XmlParse;

public class MoscRetvIntmOrrgInfoVO extends com.ktmmobile.msf.system.common.mplatform.vo.CommonXmlNoSelfServiceException {

	private String athnKeyId; // 인증키아이디
	private String atmtHndchnYn; // 자동기변여부
	private String clrCd; // 색상코드
	private String dlvrOrgnId; // 배송조직아이디
	private String imei; // 국제이동단말기식별번호
	private String indCd; // 조회구분
	private String intmJtsIndCd; // 기기자타사구분코드
	private String intmKindCd; // 기기종류코드
	private String intmMdlId; // 기기모델아이디
	private String intmSrlNo; // 기기일련번호
	private String intmUniqIdntNo; // 기기유일식별번호
	private String intmuseindcd; // 기기용도구분코드
	private String lastIntmStatCd; // 최종기기상태코드
	private String lastIntmStatChngDt; // 최종기기상태변경일시
	private String mkngDate; // 제조일자
	private String mkngVndrId; // 제조업체아이디
	private String moveCmncGnrtIndCd; // 이동통신세대구분코드
	private String newScnhndIndCd; // 신규중고구분코드
	private String nmngPsblCd; // Naming가능코드
	private String openRstrYn; // 개통제한여부
	private String rfIdntNo; // 무선주파수식별번호
	private String tchnBaseCnfrCrtfNo; // 기술기준확인증명번호
	private String wibroImei; // WIBRO국제이동단말기식별번호
	private String wibroMacId; // WIBRO MAC 아이디
	private String wifiMacId; // WIFI MAC아이디
	private String eUiccId; // EID
	private String rlthnIntmSeq; // 실물기기일련번호
	private String intmSimOdrg; // 기기SIM 순서

	@Override
	public void parse()  {
		this.athnKeyId = XmlParse.getChildValue(this.body, "athnKeyId");
		this.atmtHndchnYn = XmlParse.getChildValue(this.body, "atmtHndchnYn");
		this.clrCd = XmlParse.getChildValue(this.body, "clrCd");
		this.dlvrOrgnId = XmlParse.getChildValue(this.body, "dlvrOrgnId");
		this.imei = XmlParse.getChildValue(this.body, "imei");
		this.indCd = XmlParse.getChildValue(this.body, "indCd");
		this.intmJtsIndCd = XmlParse.getChildValue(this.body, "intmJtsIndCd");
		this.intmKindCd = XmlParse.getChildValue(this.body, "intmKindCd");
		this.intmMdlId = XmlParse.getChildValue(this.body, "intmMdlId");
		this.intmSrlNo = XmlParse.getChildValue(this.body, "intmSrlNo");
		this.intmUniqIdntNo = XmlParse.getChildValue(this.body, "intmUniqIdntNo");
		this.intmuseindcd = XmlParse.getChildValue(this.body, "intmuseindcd");
		this.lastIntmStatCd = XmlParse.getChildValue(this.body, "lastIntmStatCd");
		this.lastIntmStatChngDt = XmlParse.getChildValue(this.body, "lastIntmStatChngDt");
		this.mkngDate = XmlParse.getChildValue(this.body, "mkngDate");
		this.mkngVndrId = XmlParse.getChildValue(this.body, "mkngVndrId");
		this.moveCmncGnrtIndCd = XmlParse.getChildValue(this.body, "moveCmncGnrtIndCd");
		this.newScnhndIndCd = XmlParse.getChildValue(this.body, "newScnhndIndCd");
		this.nmngPsblCd = XmlParse.getChildValue(this.body, "nmngPsblCd");
		this.openRstrYn = XmlParse.getChildValue(this.body, "openRstrYn");
		this.rfIdntNo = XmlParse.getChildValue(this.body, "rfIdntNo");
		this.tchnBaseCnfrCrtfNo = XmlParse.getChildValue(this.body, "tchnBaseCnfrCrtfNo");
		this.wibroImei = XmlParse.getChildValue(this.body, "wibroImei");
		this.wibroMacId = XmlParse.getChildValue(this.body, "wibroMacId");
		this.wifiMacId = XmlParse.getChildValue(this.body, "wifiMacId");
		this.eUiccId = XmlParse.getChildValue(this.body, "eUiccId");
		this.rlthnIntmSeq = XmlParse.getChildValue(this.body, "rlthnIntmSeq");
		this.intmSimOdrg = XmlParse.getChildValue(this.body, "intmSimOdrg");
	}

	public String getAthnKeyId() {
		return athnKeyId;
	}

	public void setAthnKeyId(String athnKeyId) {
		this.athnKeyId = athnKeyId;
	}

	public String getAtmtHndchnYn() {
		return atmtHndchnYn;
	}

	public void setAtmtHndchnYn(String atmtHndchnYn) {
		this.atmtHndchnYn = atmtHndchnYn;
	}

	public String getClrCd() {
		return clrCd;
	}

	public void setClrCd(String clrCd) {
		this.clrCd = clrCd;
	}

	public String getDlvrOrgnId() {
		return dlvrOrgnId;
	}

	public void setDlvrOrgnId(String dlvrOrgnId) {
		this.dlvrOrgnId = dlvrOrgnId;
	}

	public String getImei() {
		return imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getIndCd() {
		return indCd;
	}

	public void setIndCd(String indCd) {
		this.indCd = indCd;
	}

	public String getIntmJtsIndCd() {
		return intmJtsIndCd;
	}

	public void setIntmJtsIndCd(String intmJtsIndCd) {
		this.intmJtsIndCd = intmJtsIndCd;
	}

	public String getIntmKindCd() {
		return intmKindCd;
	}

	public void setIntmKindCd(String intmKindCd) {
		this.intmKindCd = intmKindCd;
	}

	public String getIntmMdlId() {
		return intmMdlId;
	}

	public void setIntmMdlId(String intmMdlId) {
		this.intmMdlId = intmMdlId;
	}

	public String getIntmSrlNo() {
		return intmSrlNo;
	}

	public void setIntmSrlNo(String intmSrlNo) {
		this.intmSrlNo = intmSrlNo;
	}

	public String getIntmUniqIdntNo() {
		return intmUniqIdntNo;
	}

	public void setIntmUniqIdntNo(String intmUniqIdntNo) {
		this.intmUniqIdntNo = intmUniqIdntNo;
	}

	public String getIntmuseindcd() {
		return intmuseindcd;
	}

	public void setIntmuseindcd(String intmuseindcd) {
		this.intmuseindcd = intmuseindcd;
	}

	public String getLastIntmStatCd() {
		return lastIntmStatCd;
	}

	public void setLastIntmStatCd(String lastIntmStatCd) {
		this.lastIntmStatCd = lastIntmStatCd;
	}

	public String getLastIntmStatChngDt() {
		return lastIntmStatChngDt;
	}

	public void setLastIntmStatChngDt(String lastIntmStatChngDt) {
		this.lastIntmStatChngDt = lastIntmStatChngDt;
	}

	public String getMkngDate() {
		return mkngDate;
	}

	public void setMkngDate(String mkngDate) {
		this.mkngDate = mkngDate;
	}

	public String getMkngVndrId() {
		return mkngVndrId;
	}

	public void setMkngVndrId(String mkngVndrId) {
		this.mkngVndrId = mkngVndrId;
	}

	public String getMoveCmncGnrtIndCd() {
		return moveCmncGnrtIndCd;
	}

	public void setMoveCmncGnrtIndCd(String moveCmncGnrtIndCd) {
		this.moveCmncGnrtIndCd = moveCmncGnrtIndCd;
	}

	public String getNewScnhndIndCd() {
		return newScnhndIndCd;
	}

	public void setNewScnhndIndCd(String newScnhndIndCd) {
		this.newScnhndIndCd = newScnhndIndCd;
	}

	public String getNmngPsblCd() {
		return nmngPsblCd;
	}

	public void setNmngPsblCd(String nmngPsblCd) {
		this.nmngPsblCd = nmngPsblCd;
	}

	public String getOpenRstrYn() {
		return openRstrYn;
	}

	public void setOpenRstrYn(String openRstrYn) {
		this.openRstrYn = openRstrYn;
	}

	public String getRfIdntNo() {
		return rfIdntNo;
	}

	public void setRfIdntNo(String rfIdntNo) {
		this.rfIdntNo = rfIdntNo;
	}

	public String getTchnBaseCnfrCrtfNo() {
		return tchnBaseCnfrCrtfNo;
	}

	public void setTchnBaseCnfrCrtfNo(String tchnBaseCnfrCrtfNo) {
		this.tchnBaseCnfrCrtfNo = tchnBaseCnfrCrtfNo;
	}

	public String getWibroImei() {
		return wibroImei;
	}

	public void setWibroImei(String wibroImei) {
		this.wibroImei = wibroImei;
	}

	public String getWibroMacId() {
		return wibroMacId;
	}

	public void setWibroMacId(String wibroMacId) {
		this.wibroMacId = wibroMacId;
	}

	public String getWifiMacId() {
		return wifiMacId;
	}

	public void setWifiMacId(String wifiMacId) {
		this.wifiMacId = wifiMacId;
	}

	public String geteUiccId() {
		return eUiccId;
	}

	public void seteUiccId(String eUiccId) {
		this.eUiccId = eUiccId;
	}

	public String getRlthnIntmSeq() {
		return rlthnIntmSeq;
	}

	public void setRlthnIntmSeq(String rlthnIntmSeq) {
		this.rlthnIntmSeq = rlthnIntmSeq;
	}

	public String getIntmSimOdrg() {
		return intmSimOdrg;
	}

	public void setIntmSimOdrg(String intmSimOdrg) {
		this.intmSimOdrg = intmSimOdrg;
	}





}
