package com.ktmmobile.mcp.review.dto;

import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

public class ReviewImgDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private MultipartFile[] file;
    private List<MultipartFile> files;
    private List<Integer> imgSeqList;

    /* 사용후기 일련번호 */
    private int reviewSeq;

    /* 이미지 일련번호 */
    private int imgSeq;

    /* 파일경로명 */
    private String filePathNm;

    /* 파일타입 */
    private String fileType;

    /* 파일용량 */
    private int fileCapa;

    /* 파일 설명 */
    private String fileAlt;

    /* 등록자 */
    private String regstId;

    /* 등록일시 */
    private String regstDttm;

    public MultipartFile[] getFile() {
        return file;
    }

    public void setFile(MultipartFile[] file) {
        this.file = file;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }

    public void setFiles(List<MultipartFile> files) {
        this.files = files;
    }

    public List<Integer> getImgSeqList() {
        return imgSeqList;
    }

    public void setImgSeqList(List<Integer> imgSeqList) {
        this.imgSeqList = imgSeqList;
    }

    public int getReviewSeq() {
        return reviewSeq;
    }

    public void setReviewSeq(int reviewSeq) {
        this.reviewSeq = reviewSeq;
    }

    public int getImgSeq() {
        return imgSeq;
    }

    public void setImgSeq(int imgSeq) {
        this.imgSeq = imgSeq;
    }

    public String getFilePathNm() {
        return filePathNm;
    }

    public void setFilePathNm(String filePathNm) {
        this.filePathNm = filePathNm;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public int getFileCapa() {
        return fileCapa;
    }

    public void setFileCapa(int fileCapa) {
        this.fileCapa = fileCapa;
    }

    public String getFileAlt() {
        return fileAlt;
    }

    public void setFileAlt(String fileAlt) {
        this.fileAlt = fileAlt;
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
}
