package com.ktmmobile.mcp.telcounsel.dto;

import java.io.Serializable;
import java.sql.Timestamp;


/**
 * @Class Name : TelCounselDtlDto
 * @Description : 전화상담 상세 Dto
 *
 * @author : ant
 * @Create Date : 2016. 3. 4.
 */
public class TelCounselDtlDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 전화상담상세 일련번호 */
    private int counselDtlSeq;

    /** 전화상담 일련번호 fk*/
    private int counselSeq;

    /** 상담내용 */
    private String replyContent;

    /** 상담상대코드(공통코드) */
    private String replyStatCd;

    /** 삭제여부 */
    private String delYn;

    /** 작성자 id */
    private String cretId;

    /** 작성자 명 */
    private String cretNm;

    /** 수정자 id */
    private String amdId;

    /** 작성일 */
    private Timestamp cretDt;

    /** 수정일 */
    private Timestamp amdDt;

    public int getCounselDtlSeq() {
        return counselDtlSeq;
    }

    public void setCounselDtlSeq(int counselDtlSeq) {
        this.counselDtlSeq = counselDtlSeq;
    }

    public int getCounselSeq() {
        return counselSeq;
    }

    public void setCounselSeq(int counselSeq) {
        this.counselSeq = counselSeq;
    }

    public String getReplyContent() {
        return replyContent;
    }

    public void setReplyContent(String replyContent) {
        this.replyContent = replyContent;
    }

    public String getReplyStatCd() {
        return replyStatCd;
    }

    public void setReplyStatCd(String replyStatCd) {
        this.replyStatCd = replyStatCd;
    }

    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public String getAmdId() {
        return amdId;
    }

    public void setAmdId(String amdId) {
        this.amdId = amdId;
    }

    public Timestamp getCretDt() {
        return cretDt;
    }

    public void setCretDt(Timestamp cretDt) {
        this.cretDt = cretDt;
    }

    public Timestamp getAmdDt() {
        return amdDt;
    }

    public void setAmdDt(Timestamp amdDt) {
        this.amdDt = amdDt;
    }

    public String getCretNm() {
        return cretNm;
    }

    public void setCretNm(String cretNm) {
        this.cretNm = cretNm;
    }
}
