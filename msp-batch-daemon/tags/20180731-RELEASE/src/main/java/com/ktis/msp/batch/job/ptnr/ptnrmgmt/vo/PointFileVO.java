package com.ktis.msp.batch.job.ptnr.ptnrmgmt.vo;

import java.io.Serializable;

import com.ktis.msp.base.BaseVo;

public class PointFileVO extends BaseVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8269649377144448155L;
	
	private String partnerId;
	private String billYm;
	private String fileNm;
	private String sendFlag;
	private String sendResult;
	private String localUpDir;
	private String localDownDir;
	private String hostUpDir;
	private String hostDownDir;
	private String ifNo;
	private String upDir;
	private String downDir;
	private String usgYm;
	private String ifYm;
	
	
	/**
	 * @return the partnerId
	 */
	public String getPartnerId() {
		return partnerId;
	}
	/**
	 * @param partnerId the partnerId to set
	 */
	public void setPartnerId(String partnerId) {
		this.partnerId = partnerId;
	}
	/**
	 * @return the billYm
	 */
	public String getBillYm() {
		return billYm;
	}
	/**
	 * @param billYm the billYm to set
	 */
	public void setBillYm(String billYm) {
		this.billYm = billYm;
	}
	/**
	 * @return the fileNm
	 */
	public String getFileNm() {
		return fileNm;
	}
	/**
	 * @param fileNm the fileNm to set
	 */
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	/**
	 * @return the sendFlag
	 */
	public String getSendFlag() {
		return sendFlag;
	}
	/**
	 * @param sendFlag the sendFlag to set
	 */
	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}
	/**
	 * @return the sendResult
	 */
	public String getSendResult() {
		return sendResult;
	}
	/**
	 * @param sendResult the sendResult to set
	 */
	public void setSendResult(String sendResult) {
		this.sendResult = sendResult;
	}
	/**
	 * @return the localUpDir
	 */
	public String getLocalUpDir() {
		return localUpDir;
	}
	/**
	 * @param localUpDir the localUpDir to set
	 */
	public void setLocalUpDir(String localUpDir) {
		this.localUpDir = localUpDir;
	}
	/**
	 * @return the localDownDir
	 */
	public String getLocalDownDir() {
		return localDownDir;
	}
	/**
	 * @param localDownDir the localDownDir to set
	 */
	public void setLocalDownDir(String localDownDir) {
		this.localDownDir = localDownDir;
	}
	/**
	 * @return the hostDownDir
	 */
	public String getHostDownDir() {
		return hostDownDir;
	}
	/**
	 * @param hostDownDir the hostDownDir to set
	 */
	public void setHostDownDir(String hostDownDir) {
		this.hostDownDir = hostDownDir;
	}
	/**
	 * @return the hostUpDir
	 */
	public String getHostUpDir() {
		return hostUpDir;
	}
	/**
	 * @param hostUpDir the hostUpDir to set
	 */
	public void setHostUpDir(String hostUpDir) {
		this.hostUpDir = hostUpDir;
	}
	/**
	 * @return the ifNo
	 */
	public String getIfNo() {
		return ifNo;
	}
	/**
	 * @param ifNo the ifNo to set
	 */
	public void setIfNo(String ifNo) {
		this.ifNo = ifNo;
	}
	/**
	 * @return the upDir
	 */
	public String getUpDir() {
		return upDir;
	}
	/**
	 * @param upDir the upDir to set
	 */
	public void setUpDir(String upDir) {
		this.upDir = upDir;
	}
	/**
	 * @return the downDir
	 */
	public String getDownDir() {
		return downDir;
	}
	/**
	 * @param downDir the downDir to set
	 */
	public void setDownDir(String downDir) {
		this.downDir = downDir;
	}
	// 사용월
	public String getUsgYm() {
		return usgYm;
	}
	// 사용월
	public void setUsgYm(String usgYm) {
		this.usgYm = usgYm;
	}
	
	public String getIfYm() {
		return ifYm;
	}
	public void setIfYm(String ifYm) {
		this.ifYm = ifYm;
	}

	
}
