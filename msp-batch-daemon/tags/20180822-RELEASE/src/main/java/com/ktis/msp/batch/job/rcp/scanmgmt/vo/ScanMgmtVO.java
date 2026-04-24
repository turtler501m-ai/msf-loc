package com.ktis.msp.batch.job.rcp.scanmgmt.vo;

import java.sql.Timestamp;

public class ScanMgmtVO {
	
	private String fileId;			// 파일아이디
	private String fileNm;			// 파일명
	private String scanId;			// 스캔아이디
	private String docId;			// 서식지코드
	private String fileSize;		// 파일크기
	private String fileExt;			// 파일확장자
	private Timestamp rgstDt;		// 서식지등록일자
	private String rgstPrsnId;		// 등록처리자아이디
	private String originDir;		// 원본디렉토리경로
	private String backupDir;		// 백업디렉토리경로
	private String procStatus;		// 처리상태 R:대기 S:처리완료 E:오류
	private String procRgstCd;		// 등록사유코드
	private String procRsltCd;		// 처리결과코드
	private Timestamp procDttm;		// 처리일자
	
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getScanId() {
		return scanId;
	}
	public void setScanId(String scanId) {
		this.scanId = scanId;
	}
	public String getDocId() {
		return docId;
	}
	public void setDocId(String docId) {
		this.docId = docId;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public String getFileExt() {
		return fileExt;
	}
	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}
	public Timestamp getRgstDt() {
		return rgstDt;
	}
	public void setRgstDt(Timestamp rgstDt) {
		this.rgstDt = rgstDt;
	}
	public String getRgstPrsnId() {
		return rgstPrsnId;
	}
	public void setRgstPrsnId(String rgstPrsnId) {
		this.rgstPrsnId = rgstPrsnId;
	}
	public String getOriginDir() {
		return originDir;
	}
	public void setOriginDir(String originDir) {
		this.originDir = originDir;
	}
	public String getBackupDir() {
		return backupDir;
	}
	public void setBackupDir(String backupDir) {
		this.backupDir = backupDir;
	}
	public String getProcStatus() {
		return procStatus;
	}
	public void setProcStatus(String procStatus) {
		this.procStatus = procStatus;
	}
	public String getProcRgstCd() {
		return procRgstCd;
	}
	public void setProcRgstCd(String procRgstCd) {
		this.procRgstCd = procRgstCd;
	}
	public String getProcRsltCd() {
		return procRsltCd;
	}
	public void setProcRsltCd(String procRsltCd) {
		this.procRsltCd = procRsltCd;
	}
	public Timestamp getProcDttm() {
		return procDttm;
	}
	public void setProcDttm(Timestamp procDttm) {
		this.procDttm = procDttm;
	}
}
