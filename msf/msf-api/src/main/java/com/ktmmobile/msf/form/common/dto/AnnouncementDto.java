package com.ktmmobile.msf.form.common.dto;

import java.sql.Date;
import java.io.Serializable;


/**
 * @Class Name : Announcement
 * @Description : 공시지원금 정보
 * 공시지원금 정보 ;파일첨부 정보를 포함하고있다.
 * @author : ant
 * @Create Date : 2016. 2. 2.
 */
public class AnnouncementDto  implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 게시물 SEQ */
    private int boardSeq;

    /** 게시물제목 */
    private String boardSubject;

    /** 게시물 작성일 */
    Date boardWriteDt;

    /** 게시물 본문 */
    private String boardContents;

    /** 게시물 작성자 */
      private String boardWriterNm;

      /** 파일경로  */
      private String filePathNm;

      /** 파일형태 */
      private String fileType;

      /** 파일용량 */
      private String fileCapa;

      /** 첨부파일 다운로드 건수 */
      int downloadCount;

      /** 다운로드 만료일*/
      Date downloadExpireDate;

      /** 다운로드 갯구 제한 */
      int downloadLimitCount;

      /** 게시물속성 SEQ */
      int attSeq;

    public int getBoardSeq() {
        return boardSeq;
    }

    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
    }

    public String getBoardSubject() {
        return boardSubject;
    }

    public void setBoardSubject(String boardSubject) {
        this.boardSubject = boardSubject;
    }

    public Date getBoardWriteDt() {
        return boardWriteDt;
    }

    public void setBoardWriteDt(Date boardWriteDt) {
        this.boardWriteDt = boardWriteDt;
    }

    public String getBoardContents() {
        return boardContents;
    }

    public void setBoardContents(String boardContents) {
        this.boardContents = boardContents;
    }

    public String getBoardWriterNm() {
        return boardWriterNm;
    }

    public void setBoardWriterNm(String boardWriterNm) {
        this.boardWriterNm = boardWriterNm;
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

    public String getFileCapa() {
        return fileCapa;
    }

    public void setFileCapa(String fileCapa) {
        this.fileCapa = fileCapa;
    }

    public int getDownloadCount() {
        return downloadCount;
    }

    public void setDownloadCount(int downloadCount) {
        this.downloadCount = downloadCount;
    }

    public Date getDownloadExpireDate() {
        return downloadExpireDate;
    }

    public void setDownloadExpireDate(Date downloadExpireDate) {
        this.downloadExpireDate = downloadExpireDate;
    }

    public int getDownloadLimitCount() {
        return downloadLimitCount;
    }

    public void setDownloadLimitCount(int downloadLimitCount) {
        this.downloadLimitCount = downloadLimitCount;
    }

    public int getAttSeq() {
        return attSeq;
    }

    public void setAttSeq(int attSeq) {
        this.attSeq = attSeq;
    }
}
