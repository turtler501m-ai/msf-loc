package com.ktmmobile.mcp.requestReview.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class McpRequestReviewImgDto implements Serializable{

    private static final long serialVersionUID = 1L;
    private MultipartFile[] file;
    private List<MultipartFile> files;
    private List<Integer> imdSeqList ;


    /** 서식지 키 */
    private long requestKey;
    /** 이미지 일련번호 */
    private int imdSeq ;
    /** 파일경로명 */
    private String filePathNm;
    /** 파일타입 */
    private String fileType;
    /** 파일용량 */
    private int fileCapa;

    private String fileAlt;

    /** 사용후기 일련번호 */
    private int reviewId;


    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public int getImdSeq() {
        return imdSeq;
    }
    public void setImdSeq(int imdSeq) {
        this.imdSeq = imdSeq;
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
    public List<Integer> getImdSeqList() {
        return imdSeqList;
    }
    public void setImdSeqList(List<Integer> imdSeqList) {
        this.imdSeqList = imdSeqList;
    }
    public String getFileAlt() {
        return fileAlt;
    }
    public void setFileAlt(String fileAlt) {
        this.fileAlt = fileAlt;
    }
    public int getReviewId() {
        return reviewId;
    }
    public void setReviewId(int reviewId) {
        this.reviewId = reviewId;
    }

}
