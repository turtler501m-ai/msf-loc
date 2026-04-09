package com.ktmmobile.msf.common.mplatform.vo;

import org.jdom.Element;

import com.ktmmobile.msf.common.util.StringMakerUtil;
import com.ktmmobile.msf.common.util.XmlParse;

public class MpBilEmailBillReqInfo extends CommonXmlVO {
	private String currentState;//명세서 등록상태
	private String eMail;//이메일 주소
	private String sendGubun;//명세서 발송 여부
	private String securMailYn;//보안메일 여부
	private String ecRcvAgreYn;//이벤트 수신동의 여부
	private String option;//청구서 이메일 유무
	private String svcName;//일시정지 고객일 경우에 알림 메시지

	private String applyStatus;//청구구분 (신청;//1 ,변경;//9)
	private String applyEmail;//이메일
	private String applySafetyEmailFlag;//이메일 주소 유무
	private String applySendGubun;//명세서 발송여부
	private String applyIsLine;//Y 로 하드코딩되어 보내짐
	private String applySecurMailYn;//보안메일 여부
	private String applyEcRcvAgreYn;//이벤트 수신동의 여부

	private String chgStatus;//청구구분 (신청;//1 ,변경;//9)
	private String chgEmail;//이메일
	private String chgOriemail;//청구서 발송 이메일
	private String chgEffectiveDate;//변경날짜
	private String chgSendGubun;//명세서 발송여부
	private String chgOrisendGubun;//변경된 명세서 발송여부
	private String chgGiroGubun;//지로구분
	private String chgMsgGubun;//처리완료 메시지
	private String chgIsLine;//Y 로 하드코딩되어 보내짐
	private String chgSecurMailYn;//보안메일 여부
	private String chgEcRcvAgreYn;//이벤트 수신동의 여부
	private String chgOrisecurMailYn;//변경후 보안메일 여부
	private String chgOriecRcvAgreYn;//변경후 이벤트 수신동의 여부

	private String termStatus;//청구구분 (신청;//1 ,변경;//9)
	private String termEmail;//이메일
	private String termIsLine;//Y 로 하드코딩되어 보내짐

	private String valueOrgEmail;//청구서 발송 이메일
	private String valueOption;//청구서 이메일 유무
	private String valueIsLine;//Y 로 하드코딩되어 보내짐
	@Override
	public void parse()  {
		Element outApplyChgDto = XmlParse.getChildElement(this.body, "outApplyChgDto");
		this.currentState = XmlParse.getChildValue(outApplyChgDto, "currentState");
//		this.eMail = StringMakerUtil.getEmail(XmlParse.getChildValue(CyberbillApplyChg, "eMail"));
		this.eMail = XmlParse.getChildValue(outApplyChgDto, "email");
		this.sendGubun = XmlParse.getChildValue(outApplyChgDto, "sendGubun");
		this.securMailYn = XmlParse.getChildValue(outApplyChgDto, "securMailYn");
		this.ecRcvAgreYn = XmlParse.getChildValue(outApplyChgDto, "ecRcvAgreYn");
		this.option = XmlParse.getChildValue(outApplyChgDto, "option");
		this.svcName = XmlParse.getChildValue(outApplyChgDto, "svcMsg");

		Element outApplyDto = XmlParse.getChildElement(this.body, "outApplyDto");
		this.applyStatus = XmlParse.getChildValue(outApplyDto, "status");
//		this.applyEmail = StringMakerUtil.getEmail(XmlParse.getChildValue(outApplyDto, "email"));
		this.applyEmail = XmlParse.getChildValue(outApplyDto, "email");
		this.applySafetyEmailFlag = XmlParse.getChildValue(outApplyDto, "safetyEmailFlag");
		this.applySendGubun = XmlParse.getChildValue(outApplyDto, "sendGubun");
//		this.applyIsLine = XmlParse.getChildValue(outApplyDto, "isLine");
		this.applySecurMailYn = XmlParse.getChildValue(outApplyDto, "securMailYn");
		this.applyEcRcvAgreYn = XmlParse.getChildValue(outApplyDto, "ecRcvAgreYn");

		Element outChgDto = XmlParse.getChildElement(this.body, "outChgDto");
		this.chgStatus = XmlParse.getChildValue(outChgDto, "status");
//		this.chgEmail = StringMakerUtil.getEmail(XmlParse.getChildValue(outChgDto, "email"));
		this.chgEmail = XmlParse.getChildValue(outChgDto, "email");
//		this.chgOriemail = StringMakerUtil.getEmail(XmlParse.getChildValue(outChgDto, "Oriemail"));
		this.chgOriemail = XmlParse.getChildValue(outChgDto, "orieMail");
		this.chgEffectiveDate = XmlParse.getChildValue(outChgDto, "effectiveDate");
		this.chgSendGubun = XmlParse.getChildValue(outChgDto, "sendGubun");
		this.chgOrisendGubun = XmlParse.getChildValue(outChgDto, "oriSendGubun");
		this.chgGiroGubun = XmlParse.getChildValue(outChgDto, "giroGubun");
		this.chgMsgGubun = XmlParse.getChildValue(outChgDto, "msgGubun");
//		this.chgIsLine = XmlParse.getChildValue(outChgDto, "isLine");
		this.chgSecurMailYn = XmlParse.getChildValue(outChgDto, "securMailYn");
		this.chgEcRcvAgreYn = XmlParse.getChildValue(outChgDto, "ecRcvAgreYn");
		this.chgOrisecurMailYn = XmlParse.getChildValue(outChgDto, "oriSecurMailYn");
		this.chgOriecRcvAgreYn = XmlParse.getChildValue(outChgDto, "oriEcRcvAgreYn");

		Element outTermDto = XmlParse.getChildElement(this.body, "outTermDto");
		this.termStatus = XmlParse.getChildValue(outTermDto, "status");
//		this.termEmail = StringMakerUtil.getEmail(XmlParse.getChildValue(outTermDto, "email"));
		this.termEmail = XmlParse.getChildValue(outTermDto, "email");
//		this.termIsLine = XmlParse.getChildValue(outTermDto, "isLine");

		Element outOrgDto = XmlParse.getChildElement(this.body, "outOrgDto");
		this.valueOrgEmail = StringMakerUtil.getEmail(XmlParse.getChildValue(outOrgDto, "orgEmail"));
		this.valueOption = XmlParse.getChildValue(outOrgDto, "option");
//		this.valueIsLine = XmlParse.getChildValue(outOrgDto, "isLine");
	}
	public String getCurrentState() {
		return currentState;
	}
	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}
	public String geteMail() {
		return eMail;
	}
	public void seteMail(String eMail) {
		this.eMail = eMail;
	}
	public String getSendGubun() {
		return sendGubun;
	}
	public void setSendGubun(String sendGubun) {
		this.sendGubun = sendGubun;
	}
	public String getSecurMailYn() {
		return securMailYn;
	}
	public void setSecurMailYn(String securMailYn) {
		this.securMailYn = securMailYn;
	}
	public String getEcRcvAgreYn() {
		return ecRcvAgreYn;
	}
	public void setEcRcvAgreYn(String ecRcvAgreYn) {
		this.ecRcvAgreYn = ecRcvAgreYn;
	}
	public String getOption() {
		return option;
	}
	public void setOption(String option) {
		this.option = option;
	}
	public String getSvcName() {
		return svcName;
	}
	public void setSvcName(String svcName) {
		this.svcName = svcName;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getApplyEmail() {
		return applyEmail;
	}
	public void setApplyEmail(String applyEmail) {
		this.applyEmail = applyEmail;
	}
	public String getApplySafetyEmailFlag() {
		return applySafetyEmailFlag;
	}
	public void setApplySafetyEmailFlag(String applySafetyEmailFlag) {
		this.applySafetyEmailFlag = applySafetyEmailFlag;
	}
	public String getApplySendGubun() {
		return applySendGubun;
	}
	public void setApplySendGubun(String applySendGubun) {
		this.applySendGubun = applySendGubun;
	}
	public String getApplyIsLine() {
		return applyIsLine;
	}
	public void setApplyIsLine(String applyIsLine) {
		this.applyIsLine = applyIsLine;
	}
	public String getApplySecurMailYn() {
		return applySecurMailYn;
	}
	public void setApplySecurMailYn(String applySecurMailYn) {
		this.applySecurMailYn = applySecurMailYn;
	}
	public String getApplyEcRcvAgreYn() {
		return applyEcRcvAgreYn;
	}
	public void setApplyEcRcvAgreYn(String applyEcRcvAgreYn) {
		this.applyEcRcvAgreYn = applyEcRcvAgreYn;
	}
	public String getChgStatus() {
		return chgStatus;
	}
	public void setChgStatus(String chgStatus) {
		this.chgStatus = chgStatus;
	}
	public String getChgEmail() {
		return chgEmail;
	}
	public void setChgEmail(String chgEmail) {
		this.chgEmail = chgEmail;
	}
	public String getChgOriemail() {
		return chgOriemail;
	}
	public void setChgOriemail(String chgOriemail) {
		this.chgOriemail = chgOriemail;
	}
	public String getChgEffectiveDate() {
		return chgEffectiveDate;
	}
	public void setChgEffectiveDate(String chgEffectiveDate) {
		this.chgEffectiveDate = chgEffectiveDate;
	}
	public String getChgSendGubun() {
		return chgSendGubun;
	}
	public void setChgSendGubun(String chgSendGubun) {
		this.chgSendGubun = chgSendGubun;
	}
	public String getChgOrisendGubun() {
		return chgOrisendGubun;
	}
	public void setChgOrisendGubun(String chgOrisendGubun) {
		this.chgOrisendGubun = chgOrisendGubun;
	}
	public String getChgGiroGubun() {
		return chgGiroGubun;
	}
	public void setChgGiroGubun(String chgGiroGubun) {
		this.chgGiroGubun = chgGiroGubun;
	}
	public String getChgMsgGubun() {
		return chgMsgGubun;
	}
	public void setChgMsgGubun(String chgMsgGubun) {
		this.chgMsgGubun = chgMsgGubun;
	}
	public String getChgIsLine() {
		return chgIsLine;
	}
	public void setChgIsLine(String chgIsLine) {
		this.chgIsLine = chgIsLine;
	}
	public String getChgSecurMailYn() {
		return chgSecurMailYn;
	}
	public void setChgSecurMailYn(String chgSecurMailYn) {
		this.chgSecurMailYn = chgSecurMailYn;
	}
	public String getChgEcRcvAgreYn() {
		return chgEcRcvAgreYn;
	}
	public void setChgEcRcvAgreYn(String chgEcRcvAgreYn) {
		this.chgEcRcvAgreYn = chgEcRcvAgreYn;
	}
	public String getChgOrisecurMailYn() {
		return chgOrisecurMailYn;
	}
	public void setChgOrisecurMailYn(String chgOrisecurMailYn) {
		this.chgOrisecurMailYn = chgOrisecurMailYn;
	}
	public String getChgOriecRcvAgreYn() {
		return chgOriecRcvAgreYn;
	}
	public void setChgOriecRcvAgreYn(String chgOriecRcvAgreYn) {
		this.chgOriecRcvAgreYn = chgOriecRcvAgreYn;
	}
	public String getTermStatus() {
		return termStatus;
	}
	public void setTermStatus(String termStatus) {
		this.termStatus = termStatus;
	}
	public String getTermEmail() {
		return termEmail;
	}
	public void setTermEmail(String termEmail) {
		this.termEmail = termEmail;
	}
	public String getTermIsLine() {
		return termIsLine;
	}
	public void setTermIsLine(String termIsLine) {
		this.termIsLine = termIsLine;
	}
	public String getValueOrgEmail() {
		return valueOrgEmail;
	}
	public void setValueOrgEmail(String valueOrgEmail) {
		this.valueOrgEmail = valueOrgEmail;
	}
	public String getValueOption() {
		return valueOption;
	}
	public void setValueOption(String valueOption) {
		this.valueOption = valueOption;
	}
	public String getValueIsLine() {
		return valueIsLine;
	}
	public void setValueIsLine(String valueIsLine) {
		this.valueIsLine = valueIsLine;
	}

}
