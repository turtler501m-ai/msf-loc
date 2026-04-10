package com.ktmmobile.mcp.board.dto;

import java.io.Serializable;

public class FileDownloadDto implements Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = -8304926827432262258L;
	private int fileSeq;  //파일 seq
	private String filePath; // 파일 경로(웹접근 경로)
	private String fileNm;   // 파일 실제 이름(확장자포함)
	private String fileSize;   // 파일 용량
	private String flag; // 파일 db 종류

	public int getFileSeq() {
		return fileSeq;
	}
	public void setFileSeq(int fileSeq) {
		this.fileSeq = fileSeq;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getFileNm() {
		return fileNm;
	}
	public void setFileNm(String fileNm) {
		this.fileNm = fileNm;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}




}
