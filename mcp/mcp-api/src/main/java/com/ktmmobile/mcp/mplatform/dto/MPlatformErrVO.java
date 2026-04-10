package com.ktmmobile.mcp.mplatform.dto;

import java.io.Serializable;

public class MPlatformErrVO implements Serializable {

  private static final long serialVersionUID = 1L;

  private Long seq;               // 일련번호
  private String resNo;           // 예약번호
  private String prgrStatCd;      // 진행상태코드
  private String prntsContractNo; // 모회선 계약번호
  private String customerId;      // 고객 아이디
  private String errType;         // 오류타입
  private String errMsg;          // 오류 메시지

  public Long getSeq() {
    return seq;
  }

  public void setSeq(Long seq) {
    this.seq = seq;
  }

  public String getResNo() {
    return resNo;
  }

  public void setResNo(String resNo) {
    this.resNo = resNo;
  }

  public String getPrgrStatCd() {
    return prgrStatCd;
  }

  public void setPrgrStatCd(String prgrStatCd) {
    this.prgrStatCd = prgrStatCd;
  }

  public String getPrntsContractNo() {
    return prntsContractNo;
  }

  public void setPrntsContractNo(String prntsContractNo) {
    this.prntsContractNo = prntsContractNo;
  }

  public String getCustomerId() {
    return customerId;
  }

  public void setCustomerId(String customerId) {
    this.customerId = customerId;
  }

  public String getErrType() {
    return errType;
  }

  public void setErrType(String errType) {
    this.errType = errType;
  }

  public String getErrMsg() {
    return errMsg;
  }

  public void setErrMsg(String errMsg) {
    this.errMsg = errMsg;
  }

}
