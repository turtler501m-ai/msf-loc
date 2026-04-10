package com.ktis.msp.gift.taxmgmt.vo;

import java.io.Serializable;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import com.ktis.msp.base.mvc.BaseVo;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="taxMgmtVO")
public class TaxMgmtVO extends BaseVo implements Serializable {
	
	private static final long serialVersionUID = -1928039562143346172L;
	
	//제세공과금관리 조회
	private String srchStrtDt;
    private String srchEndDt;
	private String searchName;
	
	private String taxNo;				 /** 제세공과금 NO */
	private String subject;				 /** 제목 */
	private String sendCtn;			 /** 전송요청건수 */
	private String requestTime;		 /** 발송요청일시 */
	private String regstId;				 /** 등록자ID */
	private String taxTmpId;			 /** 템플릿ID */
	private String kakaoYn;			 /** 카카오여부 */
	private String addressYn;			 /** 주소여부 */
	private String resultYn;			 /** 처리결과 */
	private String agentYn;			 /** 법정대리인 문자발송 여부*/
	private String agentTmpId;		 /** 법정대리인 템플릿ID */
	
	private String taxType;			 /** 메세지 타입 */
	
	private String contractNum;		 /** 계약번호 */
	private String custName;			 /** 고객명 */
	private String telNo;				 /** 수신번호 */
	private String ssn;		 			 /** 주민번호 */
	private String addr;				 /** 주소 */
	private String recvTime;			 /** 회신일자 */
	private String text;					 /** 전송내용 */
	private String agentText;			 /** (법정대리인) 전송내용 */
	private String agentTelNum;		 /** 법정대리인 연락처 */
	
	
	private String fileName;
	private String sessionUserId;
	List<TaxMgmtVO> items;
	
	public String getSrchStrtDt() {
		return srchStrtDt;
	}
	public void setSrchStrtDt(String srchStrtDt) {
		this.srchStrtDt = srchStrtDt;
	}
	public String getSrchEndDt() {
		return srchEndDt;
	}
	public void setSrchEndDt(String srchEndDt) {
		this.srchEndDt = srchEndDt;
	}
	public String getSearchName() {
		return searchName;
	}
	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}
	public String getTaxNo() {
		return taxNo;
	}
	public void setTaxNo(String taxNo) {
		this.taxNo = taxNo;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getSendCtn() {
		return sendCtn;
	}
	public void setSendCtn(String sendCtn) {
		this.sendCtn = sendCtn;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getTaxTmpId() {
		return taxTmpId;
	}
	public void setTaxTmpId(String taxTmpId) {
		this.taxTmpId = taxTmpId;
	}
	public String getKakaoYn() {
		return kakaoYn;
	}
	public void setKakaoYn(String kakaoYn) {
		this.kakaoYn = kakaoYn;
	}
	public String getAddressYn() {
		return addressYn;
	}
	public void setAddressYn(String addressYn) {
		this.addressYn = addressYn;
	}
	public String getResultYn() {
		return resultYn;
	}
	public void setResultYn(String resultYn) {
		this.resultYn = resultYn;
	}
	public String getTaxType() {
		return taxType;
	}
	public void setTaxType(String taxType) {
		this.taxType = taxType;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getTelNo() {
		return telNo;
	}
	public void setTelNo(String telNo) {
		this.telNo = telNo;
	}
	public String getSsn() {
		return ssn;
	}
	public void setSsn(String ssn) {
		this.ssn = ssn;
	}
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	public String getRecvTime() {
		return recvTime;
	}
	public void setRecvTime(String recvTime) {
		this.recvTime = recvTime;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	@Override
	public String getSessionUserId() {
		return sessionUserId;
	}
	@Override
	public void setSessionUserId(String sessionUserId) {
		this.sessionUserId = sessionUserId;
	}
	public List<TaxMgmtVO> getItems() {
		return items;
	}
	public void setItems(List<TaxMgmtVO> items) {
		this.items = items;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getAgentYn() {
		return agentYn;
	}
	public void setAgentYn(String agentYn) {
		this.agentYn = agentYn;
	}
	public String getAgentTmpId() {
		return agentTmpId;
	}
	public void setAgentTmpId(String agentTmpId) {
		this.agentTmpId = agentTmpId;
	}
	public String getAgentTelNum() {
		return agentTelNum;
	}
	public void setAgentTelNum(String agentTelNum) {
		this.agentTelNum = agentTelNum;
	}
	public String getAgentText() {
		return agentText;
	}
	public void setAgentText(String agentText) {
		this.agentText = agentText;
	}
	
	
}