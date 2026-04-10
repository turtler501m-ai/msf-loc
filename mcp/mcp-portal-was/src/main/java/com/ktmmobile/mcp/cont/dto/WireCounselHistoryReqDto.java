package com.ktmmobile.mcp.cont.dto;

import java.io.Serializable;
import java.util.Date;

public class WireCounselHistoryReqDto  implements Serializable{

    private static final long serialVersionUID = 1L;

    /** 상담번호 */
    private int counselSeq;
    /** 상담일련번호 */
    private int historyIdx;
    /** 등록자아이디 */
    private String adminId;
    /** 등록자명 */
    private String adminNm;
    /** 상담 내용 */
    private String counselMemo;
    /** 등록일 */
    private Date regDt;
    public int getCounselSeq() {
        return counselSeq;
    }
    public void setCounselSeq(int counselSeq) {
        this.counselSeq = counselSeq;
    }
    public int getHistoryIdx() {
        return historyIdx;
    }
    public void setHistoryIdx(int historyIdx) {
        this.historyIdx = historyIdx;
    }
    public String getAdminId() {
        return adminId;
    }
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    public String getAdminNm() {
        return adminNm;
    }
    public void setAdminNm(String adminNm) {
        this.adminNm = adminNm;
    }
    public String getCounselMemo() {
        return counselMemo;
    }
    public void setCounselMemo(String counselMemo) {
        this.counselMemo = counselMemo;
    }
    public Date getRegDt() {
        return regDt;
    }
    public void setRegDt(Date regDt) {
        this.regDt = regDt;
    }





}
