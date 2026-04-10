package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import java.io.Serializable;

/**
 * @author 주태강
 *
 */
public class McpRateChgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * SEQ
     */
    private int chgSeq;

    /**
     * 계약번호
     */
    private String contractNum;

    /**
     * 고객명
     */
    private String cstmrName;

    /**
     * 나이스 인증 서명
     */
    private String resNo;

    /**
     * 파일경로
     */
    private String filePath;

    /**
     * 파일 확장자
     */
    private String ext;

    /**
     * 파일명
     */
    private String fileNm;

    /**
     * 생성여부
     */
    private String createYn;

    /**
     * 등록자 ID
     */
    private String regstId;

    /**
     * 등록일시
     */
    private String regstDttm;

    /**
     * 수정자ID
     */
    private String rvisnId;

    /**
     * 수정일시
     */
    private String rvisnDttm;

    /**
     * 오류메시지
     */
    private String errorDesc;

    /**
     * 요금제 코드
     */
    private String rateCd;

    private boolean resultFlag;
    private String resultMsg;
    private String endImgFullPath;
    private String birthDate;

	public String getRateCd() {
		return rateCd;
	}
	public void setRateCd(String rateCd) {
		this.rateCd = rateCd;
	}
	public String getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}
	public String getEndImgFullPath() {
		return endImgFullPath;
	}
	public void setEndImgFullPath(String endImgFullPath) {
		this.endImgFullPath = endImgFullPath;
	}
	public String getResultMsg() {
		return resultMsg;
	}
	public void setResultMsg(String resultMsg) {
		this.resultMsg = resultMsg;
	}
	public String getErrorDesc() {
		return errorDesc;
	}
	public void setErrorDesc(String errorDesc) {
		this.errorDesc = errorDesc;
	}
	public boolean isResultFlag() {
		return resultFlag;
	}
	public void setResultFlag(boolean resultFlag) {
		this.resultFlag = resultFlag;
	}
	public int getChgSeq() {
		return chgSeq;
	}
	public void setChgSeq(int chgSeq) {
		this.chgSeq = chgSeq;
	}
	public String getContractNum() {
		return contractNum;
	}
	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}
	public String getCstmrName() {
		return cstmrName;
	}
	public void setCstmrName(String cstmrName) {
		this.cstmrName = cstmrName;
	}
	public String getResNo() {
		return resNo;
	}
	public void setResNo(String resNo) {
		this.resNo = resNo;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getExt() {
		return ext;
	}
	public void setExt(String ext) {
		this.ext = ext;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getCreateYn() {
		return createYn;
	}
	public void setCreateYn(String createYn) {
		this.createYn = createYn;
	}
	public String getRegstId() {
		return regstId;
	}
	public void setRegstId(String regstId) {
		this.regstId = regstId;
	}
	public String getRegstDttm() {
		return regstDttm;
	}
	public void setRegstDttm(String regstDttm) {
		this.regstDttm = regstDttm;
	}
	public String getRvisnId() {
		return rvisnId;
	}
	public void setRvisnId(String rvisnId) {
		this.rvisnId = rvisnId;
	}
	public String getRvisnDttm() {
		return rvisnDttm;
	}
	public void setRvisnDttm(String rvisnDttm) {
		this.rvisnDttm = rvisnDttm;
	}
}