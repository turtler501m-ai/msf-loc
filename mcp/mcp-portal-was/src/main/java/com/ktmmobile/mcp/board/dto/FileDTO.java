package com.ktmmobile.mcp.board.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class FileDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private MultipartFile file;
	private List<MultipartFile> files;
	private String boardSeq;
	
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getBoardSeq() {
		return boardSeq;
	}
	public void setBoardSeq(String boardSeq) {
		this.boardSeq = boardSeq;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	
	
}
