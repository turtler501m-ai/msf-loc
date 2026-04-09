package com.ktmmobile.msf.common.mplatform.vo;

import com.ktmmobile.msf.common.util.XmlParse;
import org.jdom.Element;

import java.io.UnsupportedEncodingException;

public class RetvUsimChgAcceptPsblVO extends CommonXmlVO {

	private String rsltCd;	      // 결과코드
	private String rsltMsg;	      // 결과메세지
	private String openDt;	      // 최초개통일자
	private String usimChgDt;	    // 최근 유심 변경 일자
	private String acceptDt;	    // 유심무상교체 신청 일시
	private String usimTypeCd;	  // 유심구분 코드
	private String usimOnlyYn;	  // 유심단독 여부
	private String ctnStatus;	    // 계약상태
	private long osstNo;          // 유심교체신청 OSST연동 이력 시퀀스
	private String contractNum;   // 계약번호
	private String svcCntrNo;     // 서비스계약번호
	private long seq;             // 유심교체신청 시퀀스
	private String cretId;
	private String nfcUsimYn;	  // NFC 유심 여부
	private String ctn;
	private String usimModelCd;   // 유심모델코드
	private String usimModelId;   // 유심모델ID

	@Override
	public void parse() throws UnsupportedEncodingException {
		Element item = this.body;
		this.rsltCd = XmlParse.getChildValue(item, "rsltCd");
		this.rsltMsg = XmlParse.getChildValue(item, "rsltMsg");
		this.openDt = XmlParse.getChildValue(item, "openDt");
		this.usimChgDt = XmlParse.getChildValue(item, "usimChgDt");
		this.acceptDt = XmlParse.getChildValue(item, "acceptDt");
		this.usimTypeCd = XmlParse.getChildValue(item, "usimTypeCd");
		this.usimOnlyYn = XmlParse.getChildValue(item, "usimOnlyYn");
		this.ctnStatus = XmlParse.getChildValue(item, "ctnStatus");
	}

	public String getRsltCd() {
		return rsltCd;
	}

	public void setRsltCd(String rsltCd) {
		this.rsltCd = rsltCd;
	}

	public String getRsltMsg() {
		return rsltMsg;
	}

	public void setRsltMsg(String rsltMsg) {
		this.rsltMsg = rsltMsg;
	}

	public String getOpenDt() {
		return openDt;
	}

	public void setOpenDt(String openDt) {
		this.openDt = openDt;
	}

	public String getUsimChgDt() {
		return usimChgDt;
	}

	public void setUsimChgDt(String usimChgDt) {
		this.usimChgDt = usimChgDt;
	}

	public String getAcceptDt() {
		return acceptDt;
	}

	public void setAcceptDt(String acceptDt) {
		this.acceptDt = acceptDt;
	}

	public String getUsimTypeCd() {
		return usimTypeCd;
	}

	public void setUsimTypeCd(String usimTypeCd) {
		this.usimTypeCd = usimTypeCd;
	}

	public String getUsimOnlyYn() {
		return usimOnlyYn;
	}

	public void setUsimOnlyYn(String usimOnlyYn) {
		this.usimOnlyYn = usimOnlyYn;
	}

	public String getCtnStatus() {
		return ctnStatus;
	}

	public void setCtnStatus(String ctnStatus) {
		this.ctnStatus = ctnStatus;
	}

	public long getOsstNo() {
		return osstNo;
	}

	public void setOsstNo(long osstNo) {
		this.osstNo = osstNo;
	}

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getSvcCntrNo() {
		return svcCntrNo;
	}

	public void setSvcCntrNo(String svcCntrNo) {
		this.svcCntrNo = svcCntrNo;
	}

	public long getSeq() {
		return seq;
	}

	public void setSeq(long seq) {
		this.seq = seq;
	}

	public String getCretId() {
		return cretId;
	}

	public void setCretId(String cretId) {
		this.cretId = cretId;
	}

	public String getNfcUsimYn() {
		return nfcUsimYn;
	}

	public void setNfcUsimYn(String nfcUsimYn) {
		this.nfcUsimYn = nfcUsimYn;
	}

	public String getCtn() {
		return ctn;
	}

	public void setCtn(String ctn) {
		this.ctn = ctn;
	}

	public String getUsimModelCd() {
		return usimModelCd;
	}

	public void setUsimModelCd(String usimModelCd) {
		this.usimModelCd = usimModelCd;
	}

	public String getUsimModelId() {
		return usimModelId;
	}

	public void setUsimModelId(String usimModelId) {
		this.usimModelId = usimModelId;
	}
}
