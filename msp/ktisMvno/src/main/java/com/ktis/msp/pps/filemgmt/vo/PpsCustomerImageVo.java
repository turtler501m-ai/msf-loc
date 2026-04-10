package com.ktis.msp.pps.filemgmt.vo;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;


/**
 * @Class Name : PpsCustomerImageVo
 * @Description : 서식지 VO
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsCustomerImageVo")
public class PpsCustomerImageVo extends BaseVo  implements Serializable {
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;

	public static final String TABLE = "PPS_CUSTOMER_IMAGE";
	
	/**
	 * 이미지 일련번호 (KEY 값):number(10)<Primary Key>
	 */
	private int imgSeq;
	
	private String imgSeqStr;
	
	
	/**
	 * 서비스 계약 번호 :number(9) 
	 */
	private int contractNum;
	
	private String contractNumStr;
	
	/**
	 * 이미지 구분 char(2)
	 * 00:모름, 10:개통서류, 11:변경서류, 12:해지서류, 
	 * 20:여권, 21:주민등록증, 22:면허증, 23:외국인등록증, 24:외교관신분증, 
	 * 99:기타
	 * 
	 */
	private String imgGubun;
	
	/**
	 * 이미지 구분명
	 */
	private String imgGubunNm;
	
	/**
	 * 이미지파일명
	 */
	private String imgFile;
	
	/**
	 * 이미지 경로명
	 */
	private String imgPath;
	
	
	/**
	 * 이미지 상태 (A: 정상 , D:삭제)
	 */
	private String status;
	
	/**
	 * 이미지상태명
	 */
	private String statusNm;
	
	/**
	 * 메모
	 */
	private String memo;
	
	/**
	 * 등록자
	 */
	private String recId;
	
	/**
	 * 등록자명
	 */
	private String recIdNm;
	
	/**
	 * 등록일
	 */
	private String recDt;
	/**
	 * 상태 코드 A(사용) / S(정지) /C(해지):varchar2(1)
	 */
	private String subStatus;
	/**
	 * 상태코명
	 */
	private String subStatusNm;
	
	/**
	 * 판매점 코드 
	 */
	private String dealerCode;
	/**
	 * 판매점 명 
	 */
	private String dealerCodeNm;
	
	/**
	 * 개통대리점 코드 
	 */
	private String agentId;
	
	/**
	 * 개통대리점명
	 */
	private String agentIdNm;
	
	/**
	 * 전화번호-암호화:varchar2(50)
	 */
	private String subscriberNo;
	
	/**
	 * 삭제버튼 
	 */
	private String delBtn;
	
	/**
	 * 개통일자
	 */
	private String enterDate;
	
	private String imgFileStr;
	
	
	
	/**
	 * @return the imgSeq
	 */
	public int getImgSeq() {
		return imgSeq;
	}



	/**
	 * @param imgSeq the imgSeq to set
	 */
	public void setImgSeq(int imgSeq) {
		this.imgSeq = imgSeq;
	}



	/**
	 * @return the contractNum
	 */
	public int getContractNum() {
		return contractNum;
	}



	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(int contractNum) {
		this.contractNum = contractNum;
	}



	/**
	 * @return the imgGubun
	 */
	public String getImgGubun() {
		return imgGubun;
	}



	/**
	 * @param imgGubun the imgGubun to set
	 */
	public void setImgGubun(String imgGubun) {
		this.imgGubun = imgGubun;
	}



	/**
	 * @return the imgGubunNm
	 */
	public String getImgGubunNm() {
		return imgGubunNm;
	}



	/**
	 * @param imgGubunNm the imgGubunNm to set
	 */
	public void setImgGubunNm(String imgGubunNm) {
		this.imgGubunNm = imgGubunNm;
	}



	/**
	 * @return the imgFile
	 */
	public String getImgFile() {
		return imgFile;
	}



	/**
	 * @param imgFile the imgFile to set
	 */
	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}



	/**
	 * @return the imgPath
	 */
	public String getImgPath() {
		return imgPath;
	}



	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}



	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}



	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}



	/**
	 * @return the statusNm
	 */
	public String getStatusNm() {
		return statusNm;
	}



	/**
	 * @param statusNm the statusNm to set
	 */
	public void setStatusNm(String statusNm) {
		this.statusNm = statusNm;
	}



	/**
	 * @return the memo
	 */
	public String getMemo() {
		return memo;
	}



	/**
	 * @param memo the memo to set
	 */
	public void setMemo(String memo) {
		this.memo = memo;
	}



	/**
	 * @return the recId
	 */
	public String getRecId() {
		return recId;
	}



	/**
	 * @param recId the recId to set
	 */
	public void setRecId(String recId) {
		this.recId = recId;
	}



	/**
	 * @return the recIdNm
	 */
	public String getRecIdNm() {
		return recIdNm;
	}



	/**
	 * @param recIdNm the recIdNm to set
	 */
	public void setRecIdNm(String recIdNm) {
		this.recIdNm = recIdNm;
	}



	/**
	 * @return the recDt
	 */
	public String getRecDt() {
		return recDt;
	}



	/**
	 * @param recDt the recDt to set
	 */
	public void setRecDt(String recDt) {
		this.recDt = recDt;
	}



	/**
	 * @return the subStatus
	 */
	public String getSubStatus() {
		return subStatus;
	}



	/**
	 * @param subStatus the subStatus to set
	 */
	public void setSubStatus(String subStatus) {
		this.subStatus = subStatus;
	}



	/**
	 * @return the subStatusNm
	 */
	public String getSubStatusNm() {
		return subStatusNm;
	}



	/**
	 * @param subStatusNm the subStatusNm to set
	 */
	public void setSubStatusNm(String subStatusNm) {
		this.subStatusNm = subStatusNm;
	}



	/**
	 * @return the dealerCode
	 */
	public String getDealerCode() {
		return dealerCode;
	}



	/**
	 * @param dealerCode the dealerCode to set
	 */
	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}



	/**
	 * @return the dealerCodeNm
	 */
	public String getDealerCodeNm() {
		return dealerCodeNm;
	}



	/**
	 * @param dealerCodeNm the dealerCodeNm to set
	 */
	public void setDealerCodeNm(String dealerCodeNm) {
		this.dealerCodeNm = dealerCodeNm;
	}



	/**
	 * @return the agentId
	 */
	public String getAgentId() {
		return agentId;
	}



	/**
	 * @param agentId the agentId to set
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}






	/**
	 * @return the agentIdNm
	 */
	public String getAgentIdNm() {
		return agentIdNm;
	}



	/**
	 * @param agentIdNm the agentIdNm to set
	 */
	public void setAgentIdNm(String agentIdNm) {
		this.agentIdNm = agentIdNm;
	}



	/**
	 * @return the subscriberNo
	 */
	public String getSubscriberNo() {
		return subscriberNo;
	}



	/**
	 * @param subscriberNo the subscriberNo to set
	 */
	public void setSubscriberNo(String subscriberNo) {
		this.subscriberNo = subscriberNo;
	}



	/**
	 * @return the delBtn
	 */
	public String getDelBtn() {
		return delBtn;
	}



	/**
	 * @param delBtn the delBtn to set
	 */
	public void setDelBtn(String delBtn) {
		this.delBtn = delBtn;
	}



	/**
	 * @return the enterDate
	 */
	public String getEnterDate() {
		return enterDate;
	}



	/**
	 * @param enterDate the enterDate to set
	 */
	public void setEnterDate(String enterDate) {
		this.enterDate = enterDate;
	}



	/**
	 * @return the imgSeqStr
	 */
	public String getImgSeqStr() {
		return imgSeqStr;
	}



	/**
	 * @param imgSeqStr the imgSeqStr to set
	 */
	public void setImgSeqStr(String imgSeqStr) {
		this.imgSeqStr = imgSeqStr;
	}



	/**
	 * @return the contractNumStr
	 */
	public String getContractNumStr() {
		return contractNumStr;
	}



	/**
	 * @param contractNumStr the contractNumStr to set
	 */
	public void setContractNumStr(String contractNumStr) {
		this.contractNumStr = contractNumStr;
	}
	
	
	



	/**
	 * @return the imgFileStr
	 */
	public String getImgFileStr() {
		return imgFileStr;
	}



	/**
	 * @param imgFileStr the imgFileStr to set
	 */
	public void setImgFileStr(String imgFileStr) {
		this.imgFileStr = imgFileStr;
	}



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
