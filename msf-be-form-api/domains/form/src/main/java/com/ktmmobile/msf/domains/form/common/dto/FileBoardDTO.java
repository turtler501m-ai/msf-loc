package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.sql.Date;

public class FileBoardDTO implements Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int attSeq;
	private int boardCtgSeq;
	private int boardSeq;
	private String filePathNM;
	private String fileType;
	private String fileCapa;
	private String cretID;
	private String amdID;
	private Date cretDT;
	private Date amdDT;
	private String[] delFileArr;
	private String[] delFileNMArr;
	private String[] editorPhotoSeqArr; // 에디터 사진업로드 seqarr
	private int rst;

	public int getAttSeq() {
		return attSeq;
	}
	public void setAttSeq(int attSeq) {
		this.attSeq = attSeq;
	}
	public int getBoardCtgSeq() {
		return boardCtgSeq;
	}
	public void setBoardCtgSeq(int boardCtgSeq) {
		this.boardCtgSeq = boardCtgSeq;
	}
	public int getBoardSeq() {
		return boardSeq;
	}
	public void setBoard_Seq(int boardSeq) {
		this.boardSeq = boardSeq;
	}
	public String getFilePathNM() {
		return filePathNM;
	}
	public void setFilePathNM(String filePathNM) {
		this.filePathNM = filePathNM;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileCapa() {
		return fileCapa;
	}
	public void setFileCapa(String fileCapa) {
		this.fileCapa = fileCapa;
	}
	public String getCretID() {
		return cretID;
	}
	public void setCretID(String cretID) {
		this.cretID = cretID;
	}

	public String getAmdID() {
		return amdID;
	}
	public void setAmdID(String amdID) {
		this.amdID = amdID;
	}
	public Date getCretDT() {
		return cretDT;
	}
	public void setCretDT(Date cretDT) {
		this.cretDT = cretDT;
	}

	public Date getAmdDT() {
		return amdDT;
	}
	public void setAmdDT(Date amdDT) {
		this.amdDT = amdDT;
	}
	public void setBoardSeq(int boardSeq) {
		this.boardSeq = boardSeq;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String[] getDelFileArr() {
		return delFileArr;
	}
	public void setDelFileArr(String[] delFileArr) {
		this.delFileArr = delFileArr;
	}
	public String[] getDelFileNMArr() {
		return delFileNMArr;
	}
	public void setDelFileNMArr(String[] delFileNMArr) {
		this.delFileNMArr = delFileNMArr;
	}
	public int getRst() {
		return rst;
	}
	public void setRst(int rst) {
		this.rst = rst;
	}
	public String[] getEditorPhotoSeqArr() {
		return editorPhotoSeqArr;
	}
	public void setEditorPhotoSeqArr(String[] editorPhotoSeqArr) {
		this.editorPhotoSeqArr = editorPhotoSeqArr;
	}


}
