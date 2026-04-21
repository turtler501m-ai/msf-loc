package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : McpRequestDto.java
 * 날짜     : 2016. 1. 15. 오후 2:28:01
 * 작성자   : papier
 * 설명     : 번호이동정보 테이블(MCP_REQUEST_MOVE)
 * </pre>
 */
public class McpRequestMoveDto implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 가입신청_키 */
    private long requestKey;
    /** 번호이동정보_변경전통신사 */
    private String moveCompany;
    /** 번호이동정보_이동할번호_앞자리 */
    private String moveMobileFn;
    /** 번호이동정보_이동할번호_중간자리 */
    private String moveMobileMn;
    /** 번호이동정보_이동할번호_끝자리 */
    private String moveMobileRn;
    /** 번호이동정보_인증유형 */
    private String moveAuthType;
    /** 번호이동정보_인증번호4자리 */
    private String moveAuthNumber;
    /** 번호이동정보_이달사용요금납부방법 */
    private String moveThismonthPayType;
    /** 번호이동정보_휴대폰_할부금상태 */
    private String moveAllotmentStat;
    /** 번호이동정보_미환급액요금상계동의_여부 */
    private String moveRefundAgreeFlag;
    /** 등록일시 */
    private Date sysRdate;
    private String reqGuideFlag;
    private String reqGuideFn;
    private String reqGuideRn;
    private String reqGuideMn;

    /** 번호이동 납부방법코드 */
    private String osstPayType;

    /** 번호이동 납부주장일자 */
    private String osstPayDay;


    public long getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(long requestKey) {
        this.requestKey = requestKey;
    }
    public String getMoveCompany() {
        return moveCompany;
    }
    public void setMoveCompany(String moveCompany) {
        this.moveCompany = moveCompany;
    }
    public String getMoveMobileFn() {
        return moveMobileFn;
    }
    public void setMoveMobileFn(String moveMobileFn) {
        this.moveMobileFn = moveMobileFn;
    }
    public String getMoveMobileMn() {
        return moveMobileMn;
    }
    public void setMoveMobileMn(String moveMobileMn) {
        this.moveMobileMn = moveMobileMn;
    }
    public String getMoveMobileRn() {
        return moveMobileRn;
    }
    public void setMoveMobileRn(String moveMobileRn) {
        this.moveMobileRn = moveMobileRn;
    }
    public String getMoveAuthType() {
        return moveAuthType;
    }
    public void setMoveAuthType(String moveAuthType) {
        this.moveAuthType = moveAuthType;
    }
    public String getMoveAuthNumber() {
        return moveAuthNumber;
    }
    public void setMoveAuthNumber(String moveAuthNumber) {
        this.moveAuthNumber = moveAuthNumber;
    }
    public String getMoveThismonthPayType() {
        return moveThismonthPayType;
    }
    public void setMoveThismonthPayType(String moveThismonthPayType) {
        this.moveThismonthPayType = moveThismonthPayType;
    }
    public String getMoveAllotmentStat() {
        return moveAllotmentStat;
    }
    public void setMoveAllotmentStat(String moveAllotmentStat) {
        this.moveAllotmentStat = moveAllotmentStat;
    }
    public String getMoveRefundAgreeFlag() {
        return moveRefundAgreeFlag;
    }
    public void setMoveRefundAgreeFlag(String moveRefundAgreeFlag) {
        this.moveRefundAgreeFlag = moveRefundAgreeFlag;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getReqGuideFlag() {
        return reqGuideFlag;
    }
    public void setReqGuideFlag(String reqGuideFlag) {
        this.reqGuideFlag = reqGuideFlag;
    }
    public String getReqGuideFn() {
        return reqGuideFn;
    }
    public void setReqGuideFn(String reqGuideFn) {
        this.reqGuideFn = reqGuideFn;
    }
    public String getReqGuideRn() {
        return reqGuideRn;
    }
    public void setReqGuideRn(String reqGuideRn) {
        this.reqGuideRn = reqGuideRn;
    }
    public String getReqGuideMn() {
        return reqGuideMn;
    }
    public void setReqGuideMn(String reqGuideMn) {
        this.reqGuideMn = reqGuideMn;
    }
	public String getOsstPayType() {
		return osstPayType;
	}
	public void setOsstPayType(String osstPayType) {
		this.osstPayType = osstPayType;
	}
	public String getOsstPayDay() {
		return osstPayDay;
	}
	public void setOsstPayDay(String osstPayDay) {
		this.osstPayDay = osstPayDay;
	}






}
