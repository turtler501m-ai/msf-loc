package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestMoveVo {

    private Long requestKey;

    private LocalDateTime sysRdate = LocalDateTime.now();

    private String rvisnId;

    private LocalDateTime rvisnDttm;

    private String moveCompany;

    private String moveMobileFn;

    private String moveMobileMn;

    private String moveMobileRn;

    private String moveAuthType;

    private String moveAuthNumber;

    private String moveThismonthPayType;

    private String moveAllotmentStat;

    private String moveRefundAgreeFlag;

    private String reqGuideFlag;

    private String reqGuideFn;

    private String reqGuideRn;

    private String reqGuideMn;

    private String osstPayDay;

    private String osstPayType;

    private String movePenalty;

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public void setCretDt(LocalDateTime cretDt) { this.sysRdate = cretDt; }
    public void setAmdId(String amdId) { this.rvisnId = amdId; }
    public void setAmdDt(LocalDateTime amdDt) { this.rvisnDttm = amdDt; }
    public void setMoveCompanyCd(String moveCompanyCd) { this.moveCompany = moveCompanyCd; }
    public void setMoveMobileFnNo(String moveMobileFnNo) { this.moveMobileFn = moveMobileFnNo; }
    public void setMoveMobileMnNo(String moveMobileMnNo) { this.moveMobileMn = moveMobileMnNo; }
    public void setMoveMobileRnNo(String moveMobileRnNo) { this.moveMobileRn = moveMobileRnNo; }
    public void setMoveAuthTypeCd(String moveAuthTypeCd) { this.moveAuthType = moveAuthTypeCd; }
    public void setMoveAuthNo(String moveAuthNo) { this.moveAuthNumber = moveAuthNo; }
    public void setMoveThismonthPayTypeCd(String moveThismonthPayTypeCd) { this.moveThismonthPayType = moveThismonthPayTypeCd; }
    public void setMoveAllotmentSttusCd(String moveAllotmentSttusCd) { this.moveAllotmentStat = moveAllotmentSttusCd; }
    public void setMoveRefundAgreeYn(String moveRefundAgreeYn) { this.moveRefundAgreeFlag = moveRefundAgreeYn; }
    public void setReqGuideYn(String reqGuideYn) { this.reqGuideFlag = reqGuideYn; }
    public void setReqGuideFnNo(String reqGuideFnNo) { this.reqGuideFn = reqGuideFnNo; }
    public void setReqGuideRnNo(String reqGuideRnNo) { this.reqGuideRn = reqGuideRnNo; }
    public void setReqGuideMnNo(String reqGuideMnNo) { this.reqGuideMn = reqGuideMnNo; }
    public void setOsstPayDate(String osstPayDate) { this.osstPayDay = osstPayDate; }
    public void setOsstPayTypeCd(String osstPayTypeCd) { this.osstPayType = osstPayTypeCd; }
    public void setMovePenalty(String movePenalty) { this.movePenalty = movePenalty; }
}
