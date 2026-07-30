package com.ktmmobile.msf.domains.form.form.common.vo;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestReqVo {

    private Long requestKey;

    private LocalDateTime sysRdate = LocalDateTime.now();

    private String rvisnId;

    private LocalDateTime rvisnDttm;

    private String reqBank;

    private String reqAccountName;

    private String reqAccountRrn;

    private String reqAccountRelation;

    private String reqAccountNumber;

    private String reqCardName;

    private String reqCardRrn;

    private String reqCardCompany;

    private String reqCardNo;

    private String reqCardYy;

    private String reqCardMm;

    private String reqWireType;

    private String reqPayOtherFlag;

    private String reqPayOtherTelFn;

    private String reqPayOtherTelMn;

    private String reqPayOtherTelRn;

    private String prntsBillNo;

    // MyBatis 매핑 전용 가짜(Dummy) Setter
    public void setRequestKey(Long requestKey) { this.requestKey = requestKey; }
    public void setCretDt(LocalDateTime cretDt) { this.sysRdate = cretDt; }
    public void setAmdId(String amdId) { this.rvisnId = amdId; }
    public void setAmdDt(LocalDateTime amdDt) { this.rvisnDttm = amdDt; }
    public void setReqBankCd(String reqBankCd) { this.reqBank = reqBankCd; }
    public void setReqAccountNm(String reqAccountNm) { this.reqAccountName = reqAccountNm; }
    public void setReqAccountRrn(String reqAccountRrn) { this.reqAccountRrn = reqAccountRrn; }
    public void setReqAccountRelTypeCd(String reqAccountRelTypeCd) { this.reqAccountRelation = reqAccountRelTypeCd; }
    public void setReqAccountNo(String reqAccountNo) { this.reqAccountNumber = reqAccountNo; }
    public void setReqCardNm(String reqCardNm) { this.reqCardName = reqCardNm; }
    public void setReqCardRrn(String reqCardRrn) { this.reqCardRrn = reqCardRrn; }
    public void setReqCardCompanyCd(String reqCardCompanyCd) { this.reqCardCompany = reqCardCompanyCd; }
    public void setReqCardNo(String reqCardNo) { this.reqCardNo = reqCardNo; }
    public void setReqCardYy(String reqCardYy) { this.reqCardYy = reqCardYy; }
    public void setReqCardMm(String reqCardMm) { this.reqCardMm = reqCardMm; }
    public void setReqWireTypeCd(String reqWireTypeCd) { this.reqWireType = reqWireTypeCd; }
    public void setOthersPaymentYn(String othersPaymentYn) { this.reqPayOtherFlag = othersPaymentYn; }
    public void setOthersPaymentTelFnNo(String othersPaymentTelFnNo) { this.reqPayOtherTelFn = othersPaymentTelFnNo; }
    public void setOthersPaymentTelMnNo(String othersPaymentTelMnNo) { this.reqPayOtherTelMn = othersPaymentTelMnNo; }
    public void setOthersPaymentTelRnNo(String othersPaymentTelRnNo) { this.reqPayOtherTelRn = othersPaymentTelRnNo; }
    public void setPrntsBillNo(String prntsBillNo) { this.prntsBillNo = prntsBillNo; }
}
