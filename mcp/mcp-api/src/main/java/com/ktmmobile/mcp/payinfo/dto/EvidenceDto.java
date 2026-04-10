package com.ktmmobile.mcp.payinfo.dto;

import java.io.Serializable;

public class EvidenceDto implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public String kftcEvidenceSeq;	//'증거파일 SEQ'
	public String rgstDt;			//'등록일자'
	public String validDt;			//'유효일자'
	public String contractNum;		//'가입계약번호'
	public String ban;				//'청구번호'
	public String realFilePath;		//'실 파일 경로'
	public String ext;				//'파일확장자'
	public String regYn;			//'파일등록여부'
	public String rgstInflowCd;		//'등록 유입 경로 코드[CMN0203]'
	public String rgstId;			//'등록ID'
	public String realFileNm;		//'실 파일명'
	public String regstId;			//'등록자ID'
	public String rvisnId;			//수정자ID
	public String regstDttm;		//'등록일시'
	/**
	 * @return the kftcEvidenceSeq
	 */
	public String getKftcEvidenceSeq() {
		return kftcEvidenceSeq;
	}
	/**
	 * @param kftcEvidenceSeq the kftcEvidenceSeq to set
	 */
	public void setKftcEvidenceSeq(String kftcEvidenceSeq) {
		this.kftcEvidenceSeq = kftcEvidenceSeq;
	}
	/**
	 * @return the rgstDt
	 */
	public String getRgstDt() {
		return rgstDt;
	}
	/**
	 * @param rgstDt the rgstDt to set
	 */
	public void setRgstDt(String rgstDt) {
		this.rgstDt = rgstDt;
	}
	/**
	 * @return the validDt
	 */
	public String getValidDt() {
		return validDt;
	}
	/**
	 * @param validDt the validDt to set
	 */
	public void setValidDt(String validDt) {
		this.validDt = validDt;
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
	 * @return the ban
	 */
	public String getBan() {
		return ban;
	}
	/**
	 * @param ban the ban to set
	 */
	public void setBan(String ban) {
		this.ban = ban;
	}
	/**
	 * @return the realFilePath
	 */
	public String getRealFilePath() {
		return realFilePath;
	}
	/**
	 * @param realFilePath the realFilePath to set
	 */
	public void setRealFilePath(String realFilePath) {
		this.realFilePath = realFilePath;
	}
	/**
	 * @return the ext
	 */
	public String getExt() {
		return ext;
	}
	/**
	 * @param ext the ext to set
	 */
	public void setExt(String ext) {
		this.ext = ext;
	}
	/**
	 * @return the regYn
	 */
	public String getRegYn() {
		return regYn;
	}
	/**
	 * @param regYn the regYn to set
	 */
	public void setRegYn(String regYn) {
		this.regYn = regYn;
	}
	/**
	 * @return the rgstInflowCd
	 */
	public String getRgstInflowCd() {
		return rgstInflowCd;
	}
	/**
	 * @param rgstInflowCd the rgstInflowCd to set
	 */
	public void setRgstInflowCd(String rgstInflowCd) {
		this.rgstInflowCd = rgstInflowCd;
	}
	/**
	 * @return the rgstId
	 */
	public String getRgstId() {
		return rgstId;
	}
	/**
	 * @param rgstId the rgstId to set
	 */
	public void setRgstId(String rgstId) {
		this.rgstId = rgstId;
	}
	/**
	 * @return the realFileNm
	 */
	public String getRealFileNm() {
		return realFileNm;
	}
	/**
	 * @param realFileNm the realFileNm to set
	 */
	public void setRealFileNm(String realFileNm) {
		this.realFileNm = realFileNm;
	}
	/**
	 * @return the regstId
	 */
	public String getRegstId() {
		return regstId;
	}
	/**
	 * @param regstId the regstId to set
	 */
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	/**
	 * @return the regstDttm
	 */
	public String getRegstDttm() {
		return regstDttm;
	}
	/**
	 * @param regstDttm the regstDttm to set
	 */
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	/**
	 * @return the rvisnId
	 */
	public String getRvisnId() {
		return rvisnId;
	}
	/**
	 * @param rvisnId the rvisnId to set
	 */
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	
}
