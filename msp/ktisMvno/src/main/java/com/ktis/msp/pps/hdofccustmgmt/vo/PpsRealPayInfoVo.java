package com.ktis.msp.pps.hdofccustmgmt.vo;

import java.io.Serializable;


import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import com.ktis.msp.base.mvc.BaseVo;

/**
 * @Class Name : ppsRealPayInfoVo
 * @Description : 실시간이체 PayInfo
 * @
 * @ 수정일	    수정자 수정내용
 * @ ---------- ------ -----------------------------
 * @ 2014.08.27 장익준 최초생성
 * @
 * @author : 장익준
 * @Create Date : 2014. 8. 27.
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name="ppsRealPayInfoVo")
public class PpsRealPayInfoVo extends BaseVo  implements Serializable {
	
	/**
	 * serialVersion UID
	 */
	private static final long serialVersionUID = 6546990189880359258L;
	

	public static final String TABLE = "PPS_REAL_PAYINFO";

	/**
	 * 고유번호:number(20)
	 */
	private int seq;
	
	/**
	 * 계약번호:varchar2(10)
	 */
	private String contractNum;
	
	/**
	 * 상태:varchar2(2)
	 */
	private String status;
	
	/**
	 * 회원증빙방법:varchar2(1)
	 */
	private String cmsType;
	
	/**
	 * 파일종류:varchar2(2)
	 */
	private String regType;	
	
	/**
	 * 파일종류명:varchar2(2)
	 */
	private String regTypeNm;	
	
	/**
	 * 파일타입:varchar2(5)
	 */
	private String fileType;
	
	/**
	 * 증빙데이타길이:varchar2(6)
	 */
	private String fileLen;
	
	/**
	 * 암호화값:varchar2(255)
	 */
	private String filePath;
	
	/**
	 * 고유번호:clob
	 */
	private String fileEnc;	
	
	/**
	 * 회원등록일자:date
	 */
	private String userDt;	
	
	/**
	 * 등록결과값:varchar2(2)
	 */
	private String userStatus;	
	
	/**
	 * 증빙데이타입력일자:date
	 */
	private String fileDt;	
	
	/**
	 * 증빙데이타취소일자:date
	 */
	private String fileCanDt;	
	
	/**
	 * 파일등록결과값:varchar2(5)
	 */
	private String fileStatus;	
	
	/**
	 * 등록자:varchar2(20)
	 */
	private String recId;	
	
	/**
	 * 등록일자:date
	 */
	private String recDt;	

	

	/**
	 * @return the seq
	 */
	public int getSeq() {
		return seq;
	}



	/**
	 * @param seq the seq to set
	 */
	public void setSeq(int seq) {
		this.seq = seq;
	}



	/**
	 * @return the contractNum
	 */
	public String getContractNum() {
		return contractNum;
	}



	/**
	 * @param contractNum the contractNum to set
	 */
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
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
	 * @return the cmsType
	 */
	public String getCmsType() {
		return cmsType;
	}



	/**
	 * @param cmsType the cmsType to set
	 */
	public void setCmsType(String cmsType) {
		this.cmsType = cmsType;
	}



	/**
	 * @return the regType
	 */
	public String getRegType() {
		return regType;
	}



	/**
	 * @param regType the regType to set
	 */
	public void setRegType(String regType) {
		this.regType = regType;
	}
	
	
	/**
	 * @return the regTypeNm
	 */
	public String getRegTypeNm() {
		return regTypeNm;
	}



	/**
	 * @param regTypeNm the regTypeNm to set
	 */
	public void setRegTypeNm(String regTypeNm) {
		this.regTypeNm = regTypeNm;
	}



	/**
	 * @return the fileType
	 */
	public String getFileType() {
		return fileType;
	}



	/**
	 * @param fileType the fileType to set
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}



	/**
	 * @return the fileLen
	 */
	public String getFileLen() {
		return fileLen;
	}



	/**
	 * @param fileLen the fileLen to set
	 */
	public void setFileLen(String fileLen) {
		this.fileLen = fileLen;
	}



	/**
	 * @return the filePath
	 */
	public String getFilePath() {
		return filePath;
	}



	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}



	/**
	 * @return the fileEnc
	 */
	public String getFileEnc() {
		return fileEnc;
	}



	/**
	 * @param fileEnc the fileEnc to set
	 */
	public void setFileEnc(String fileEnc) {
		this.fileEnc = fileEnc;
	}



	/**
	 * @return the userDt
	 */
	public String getUserDt() {
		return userDt;
	}



	/**
	 * @param userDt the userDt to set
	 */
	public void setUserDt(String userDt) {
		this.userDt = userDt;
	}



	/**
	 * @return the userStatus
	 */
	public String getUserStatus() {
		return userStatus;
	}



	/**
	 * @param userStatus the userStatus to set
	 */
	public void setUserStatus(String userStatus) {
		this.userStatus = userStatus;
	}



	/**
	 * @return the fileDt
	 */
	public String getFileDt() {
		return fileDt;
	}



	/**
	 * @param fileDt the fileDt to set
	 */
	public void setFileDt(String fileDt) {
		this.fileDt = fileDt;
	}



	/**
	 * @return the fileCanDt
	 */
	public String getFileCanDt() {
		return fileCanDt;
	}



	/**
	 * @param fileCanDt the fileCanDt to set
	 */
	public void setFileCanDt(String fileCanDt) {
		this.fileCanDt = fileCanDt;
	}



	/**
	 * @return the fileStatus
	 */
	public String getFileStatus() {
		return fileStatus;
	}



	/**
	 * @param fileStatus the fileStatus to set
	 */
	public void setFileStatus(String fileStatus) {
		this.fileStatus = fileStatus;
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



	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
	

}
