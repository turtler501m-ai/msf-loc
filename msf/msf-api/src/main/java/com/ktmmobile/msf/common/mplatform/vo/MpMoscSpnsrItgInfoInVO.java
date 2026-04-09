package com.ktmmobile.msf.common.mplatform.vo;

import java.io.UnsupportedEncodingException;
import java.text.ParseException;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.XmlParse;

public class MpMoscSpnsrItgInfoInVO extends CommonXmlVO{

	private String apdSuprtAmt;						//추가지원금
	private String chageDcAmt;						//요금할인(월)
	private String chageDcAmtSuprtMonth;		//요금할인(지원금)(월)
	private String chageDcAmtSuprtRtrnAmt;	//요금할인(지원금)반환금
	private String engtAplyStDate;					//가입일
	private String engtExpirPamDate;				//만료예정일
	private String engtRmndDate;					//잔여약정기간(정지포함)
	private String engtUseDayNum;				//경과기간
	private String firstSuprtAmt;						//최초지원금
	private String intmDcAmt;						//핸드폰할인금액
	private String intmPenltTotAmt;				//핸드폰위약금
	private String ktSuprtPenltAmt;					//위약금(공시지원금)
	private String monthDcAmt;						//월기본할인금액
	private String punoSuprtAmt;					//공시지원금
	private String realDcAmt;							//현재까지 받은 금액 금일기준
	private String rtrnAmtAndChageDcAmt;		//반환금(요금할인)
	private String saleEngtNm;						//스폰서유형명
	private String saleEngtOptnCd;					//스폰서유형옵션코드
	private String saleEngtTypeDivCd;				//스폰서유형구분코드
	private String storSuprtPenltAmt;				//위약금(추가지원금)
	private String tgtKtSuprtPenltAmt;			//위약금대상(공시지원금)
	private String tgtStorSuprtPenltAmt;			//위약금대상(추가지원금)
	private String trmnForecBprmsAmt;			//(해지시)예상위약금

	@Override
	public void parse() throws UnsupportedEncodingException, ParseException {
		
		Element outBasInfoDto = XmlParse.getChildElement(this.body, "outBasInfoDto");
				
		this.setSaleEngtNm(XmlParse.getChildValue(outBasInfoDto, "saleEngtNm"));		
		this.setSaleEngtTypeDivCd(XmlParse.getChildValue(outBasInfoDto, "saleEngtTypeDivCd"));		
		this.setSaleEngtOptnCd(XmlParse.getChildValue(outBasInfoDto, "saleEngtOptnCd"));		
		this.setEngtAplyStDate(XmlParse.getChildValue(outBasInfoDto, "engtAplyStDate"));		
		this.setEngtUseDayNum(XmlParse.getChildValue(outBasInfoDto, "engtUseDayNum"));		
		this.setEngtExpirPamDate(XmlParse.getChildValue(outBasInfoDto, "engtExpirPamDate"));		
			
		if (saleEngtOptnCd.equals("KD")) {
			Element outKDDto = XmlParse.getChildElement(this.body, "outKDDto");
			this.setEngtRmndDate(XmlParse.getChildValue(outKDDto, "engtRmndDate"));
			this.setFirstSuprtAmt(XmlParse.getChildValue(outKDDto, "firstSuprtAmt"));
			this.setPunoSuprtAmt(XmlParse.getChildValue(outKDDto, "punoSuprtAmt"));
			this.setApdSuprtAmt(XmlParse.getChildValue(outKDDto, "apdSuprtAmt"));
			this.setTrmnForecBprmsAmt(XmlParse.getChildValue(outKDDto, "trmnForecBprmsAmt"));
			this.setKtSuprtPenltAmt(XmlParse.getChildValue(outKDDto, "ktSuprtPenltAmt"));
			this.setStorSuprtPenltAmt(XmlParse.getChildValue(outKDDto, "storSuprtPenltAmt"));
			this.setTgtKtSuprtPenltAmt(XmlParse.getChildValue(outKDDto, "tgtKtSuprtPenltAmt"));
			this.setTgtStorSuprtPenltAmt(XmlParse.getChildValue(outKDDto, "tgtStorSuprtPenltAmt"));
			this.setRtrnAmtAndChageDcAmt(XmlParse.getChildValue(outKDDto, "rtrnAmtAndChageDcAmt"));
			this.setChageDcAmt(XmlParse.getChildValue(outKDDto, "chageDcAmt"));
			this.setRealDcAmt(XmlParse.getChildValue(outKDDto, "realDcAmt"));
		} else if (saleEngtOptnCd.equals("PM")) {
			Element outPMDto = XmlParse.getChildElement(this.body, "outPMDto");
			this.setEngtRmndDate(XmlParse.getChildValue(outPMDto, "engtRmndDate"));
			this.setRtrnAmtAndChageDcAmt(XmlParse.getChildValue(outPMDto, "rtrnAmtAndChageDcAmt"));
			this.setChageDcAmt(XmlParse.getChildValue(outPMDto, "chageDcAmt"));
			this.setChageDcAmtSuprtMonth(XmlParse.getChildValue(outPMDto, "chageDcAmtSuprtMonth"));
			this.setChageDcAmtSuprtRtrnAmt(XmlParse.getChildValue(outPMDto, "chageDcAmtSuprtRtrnAmt"));
			this.setRealDcAmt(XmlParse.getChildValue(outPMDto, "realDcAmt"));
		}
		
	}

	public String getApdSuprtAmt() {
		return apdSuprtAmt;
	}

	public void setApdSuprtAmt(String apdSuprtAmt) {
		this.apdSuprtAmt = apdSuprtAmt;
	}

	public String getChageDcAmt() {
		return chageDcAmt;
	}

	public void setChageDcAmt(String chageDcAmt) {
		this.chageDcAmt = chageDcAmt;
	}

	public String getChageDcAmtSuprtMonth() {
		return chageDcAmtSuprtMonth;
	}

	public void setChageDcAmtSuprtMonth(String chageDcAmtSuprtMonth) {
		this.chageDcAmtSuprtMonth = chageDcAmtSuprtMonth;
	}

	public String getChageDcAmtSuprtRtrnAmt() {
		return chageDcAmtSuprtRtrnAmt;
	}

	public void setChageDcAmtSuprtRtrnAmt(String chageDcAmtSuprtRtrnAmt) {
		this.chageDcAmtSuprtRtrnAmt = chageDcAmtSuprtRtrnAmt;
	}

	public String getEngtAplyStDate() {
		return engtAplyStDate;
	}

	public void setEngtAplyStDate(String engtAplyStDate) {
		this.engtAplyStDate = engtAplyStDate;
	}

	public String getEngtExpirPamDate() {
		return engtExpirPamDate;
	}

	public void setEngtExpirPamDate(String engtExpirPamDate) {
		this.engtExpirPamDate = engtExpirPamDate;
	}

	public String getEngtRmndDate() {
		return engtRmndDate;
	}

	public void setEngtRmndDate(String engtRmndDate) {
		this.engtRmndDate = engtRmndDate;
	}

	public String getEngtUseDayNum() {
		return engtUseDayNum;
	}

	public void setEngtUseDayNum(String engtUseDayNum) {
		this.engtUseDayNum = engtUseDayNum;
	}

	public String getFirstSuprtAmt() {
		return firstSuprtAmt;
	}

	public void setFirstSuprtAmt(String firstSuprtAmt) {
		this.firstSuprtAmt = firstSuprtAmt;
	}

	public String getIntmDcAmt() {
		return intmDcAmt;
	}

	public void setIntmDcAmt(String intmDcAmt) {
		this.intmDcAmt = intmDcAmt;
	}

	public String getIntmPenltTotAmt() {
		return intmPenltTotAmt;
	}

	public void setIntmPenltTotAmt(String intmPenltTotAmt) {
		this.intmPenltTotAmt = intmPenltTotAmt;
	}

	public String getKtSuprtPenltAmt() {
		return ktSuprtPenltAmt;
	}

	public void setKtSuprtPenltAmt(String ktSuprtPenltAmt) {
		this.ktSuprtPenltAmt = ktSuprtPenltAmt;
	}

	public String getMonthDcAmt() {
		return monthDcAmt;
	}

	public void setMonthDcAmt(String monthDcAmt) {
		this.monthDcAmt = monthDcAmt;
	}

	public String getPunoSuprtAmt() {
		return punoSuprtAmt;
	}

	public void setPunoSuprtAmt(String punoSuprtAmt) {
		this.punoSuprtAmt = punoSuprtAmt;
	}

	public String getRealDcAmt() {
		return realDcAmt;
	}

	public void setRealDcAmt(String realDcAmt) {
		this.realDcAmt = realDcAmt;
	}

	public String getRtrnAmtAndChageDcAmt() {
		return rtrnAmtAndChageDcAmt;
	}

	public void setRtrnAmtAndChageDcAmt(String rtrnAmtAndChageDcAmt) {
		this.rtrnAmtAndChageDcAmt = rtrnAmtAndChageDcAmt;
	}

	public String getSaleEngtNm() {
		return saleEngtNm;
	}

	public void setSaleEngtNm(String saleEngtNm) {
		this.saleEngtNm = saleEngtNm;
	}

	public String getSaleEngtOptnCd() {
		return saleEngtOptnCd;
	}

	public void setSaleEngtOptnCd(String saleEngtOptnCd) {
		this.saleEngtOptnCd = saleEngtOptnCd;
	}

	public String getSaleEngtTypeDivCd() {
		return saleEngtTypeDivCd;
	}

	public void setSaleEngtTypeDivCd(String saleEngtTypeDivCd) {
		this.saleEngtTypeDivCd = saleEngtTypeDivCd;
	}

	public String getStorSuprtPenltAmt() {
		return storSuprtPenltAmt;
	}

	public void setStorSuprtPenltAmt(String storSuprtPenltAmt) {
		this.storSuprtPenltAmt = storSuprtPenltAmt;
	}

	public String getTgtKtSuprtPenltAmt() {
		return tgtKtSuprtPenltAmt;
	}

	public void setTgtKtSuprtPenltAmt(String tgtKtSuprtPenltAmt) {
		this.tgtKtSuprtPenltAmt = tgtKtSuprtPenltAmt;
	}

	public String getTgtStorSuprtPenltAmt() {
		return tgtStorSuprtPenltAmt;
	}

	public void setTgtStorSuprtPenltAmt(String tgtStorSuprtPenltAmt) {
		this.tgtStorSuprtPenltAmt = tgtStorSuprtPenltAmt;
	}

	public String getTrmnForecBprmsAmt() {
		return trmnForecBprmsAmt;
	}

	public void setTrmnForecBprmsAmt(String trmnForecBprmsAmt) {
		this.trmnForecBprmsAmt = trmnForecBprmsAmt;
	}

	@Override
	public String toString() {
		return "MpMoscSpnsrItgInfoInVO [apdSuprtAmt=" + apdSuprtAmt
				+ ", chageDcAmt=" + chageDcAmt + ", chageDcAmtSuprtMonth="
				+ chageDcAmtSuprtMonth + ", chageDcAmtSuprtRtrnAmt="
				+ chageDcAmtSuprtRtrnAmt + ", engtAplyStDate=" + engtAplyStDate
				+ ", engtExpirPamDate=" + engtExpirPamDate + ", engtRmndDate="
				+ engtRmndDate + ", engtUseDayNum=" + engtUseDayNum
				+ ", firstSuprtAmt=" + firstSuprtAmt + ", intmDcAmt="
				+ intmDcAmt + ", intmPenltTotAmt=" + intmPenltTotAmt
				+ ", ktSuprtPenltAmt=" + ktSuprtPenltAmt + ", monthDcAmt="
				+ monthDcAmt + ", punoSuprtAmt=" + punoSuprtAmt
				+ ", realDcAmt=" + realDcAmt + ", rtrnAmtAndChageDcAmt="
				+ rtrnAmtAndChageDcAmt + ", saleEngtNm=" + saleEngtNm
				+ ", saleEngtOptnCd=" + saleEngtOptnCd + ", saleEngtTypeDivCd="
				+ saleEngtTypeDivCd + ", storSuprtPenltAmt="
				+ storSuprtPenltAmt + ", tgtKtSuprtPenltAmt="
				+ tgtKtSuprtPenltAmt + ", tgtStorSuprtPenltAmt="
				+ tgtStorSuprtPenltAmt + ", trmnForecBprmsAmt="
				+ trmnForecBprmsAmt + "]";
	}
	
	
	
}
