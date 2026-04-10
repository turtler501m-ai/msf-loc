package com.ktmmobile.mcp.event.dto;

import java.io.Serializable;
import java.util.Date;

public class EventBenefitRelationDto implements Serializable {
    private static final long serialVersionUID = 123281459041710055L;

    /** 이벤트 혜택 관리 기본 일련번호  */
    private long eventBenefitSeq;
    /** 이벤트 게시물일련번호 */
    private long ntcartSeq;
    /** 등록아이피 */
    private String cretIp;
    /** 등록일자 */
    private Date cretDt;
    /** 등록 사용자 아이디 */
    private String cretId;


    private EventBoardDto eventBoard ;

    public long getEventBenefitSeq() {
        return eventBenefitSeq;
    }

    public void setEventBenefitSeq(long eventBenefitSeq) {
        this.eventBenefitSeq = eventBenefitSeq;
    }

    public long getNtcartSeq() {
        return ntcartSeq;
    }

    public void setNtcartSeq(long ntcartSeq) {
        this.ntcartSeq = ntcartSeq;
    }

    public String getCretIp() {
        return cretIp;
    }

    public void setCretIp(String cretIp) {
        this.cretIp = cretIp;
    }

    public Date getCretDt() {
        return cretDt;
    }

    public void setCretDt(Date cretDt) {
        this.cretDt = cretDt;
    }

    public String getCretId() {
        return cretId;
    }

    public void setCretId(String cretId) {
        this.cretId = cretId;
    }

    public EventBoardDto getEventBoard() {
        return eventBoard;
    }

    public void setEventBoard(EventBoardDto eventBoard) {
        this.eventBoard = eventBoard;
    }
}