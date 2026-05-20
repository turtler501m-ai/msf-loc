package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FileBoardDTO implements Serializable {

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

    // 필드명 대문자 약어 포함 — Lombok은 getCretId() 생성, 기존 호출부 호환을 위해 유지
    public String getFilePathNM() { return filePathNM; }
    public void setFilePathNM(String filePathNM) { this.filePathNM = filePathNM; }
    public String getCretID() { return cretID; }
    public void setCretID(String cretID) { this.cretID = cretID; }
    public String getAmdID() { return amdID; }
    public void setAmdID(String amdID) { this.amdID = amdID; }
    public Date getCretDT() { return cretDT; }
    public void setCretDT(Date cretDT) { this.cretDT = cretDT; }
    public Date getAmdDT() { return amdDT; }
    public void setAmdDT(Date amdDT) { this.amdDT = amdDT; }
    public String[] getDelFileNMArr() { return delFileNMArr; }
    public void setDelFileNMArr(String[] delFileNMArr) { this.delFileNMArr = delFileNMArr; }

    // 비표준 setter명 — 기존 호출부 호환을 위해 유지
    public void setBoard_Seq(int boardSeq) { this.boardSeq = boardSeq; }

    public static long getSerialversionuid() { return serialVersionUID; }

}
