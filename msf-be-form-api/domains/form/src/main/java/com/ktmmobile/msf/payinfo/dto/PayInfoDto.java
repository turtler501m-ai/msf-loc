// TOBESKIP: TOBE 미구현 — 결제정보 이미지 생성용 DTO (ASIS 필드 보존, 기능 미구현)
package com.ktmmobile.msf.payinfo.dto;

/**
 * 결제정보 DTO (자동이체 동의서 이미지 생성용)
 * TOBESKIP: TOBE에서 결제정보 이미지 생성 기능은 미구현이나 컴파일 호환을 위해 DTO 구조만 보존한다.
 */
public class PayInfoDto {

    private String paySeq;
    private String cstmrName;
    private String birthDate;
    private String ctn;
    private String reqBank;
    private String reqAccountNumber;
    private String resNo;
    private String createYn;
    private String userId;
    private Boolean result;
    private String endFilePath;
    private String contractNum;

    public String getPaySeq() { return paySeq; }
    public void setPaySeq(String paySeq) { this.paySeq = paySeq; }

    public String getCstmrName() { return cstmrName; }
    public void setCstmrName(String cstmrName) { this.cstmrName = cstmrName; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }

    public String getReqBank() { return reqBank; }
    public void setReqBank(String reqBank) { this.reqBank = reqBank; }

    public String getReqAccountNumber() { return reqAccountNumber; }
    public void setReqAccountNumber(String reqAccountNumber) { this.reqAccountNumber = reqAccountNumber; }

    public String getResNo() { return resNo; }
    public void setResNo(String resNo) { this.resNo = resNo; }
    /** ASIS: reqSeq + resSeq 조합으로 resNo 설정 */
    public void setResNo(String reqSeq, String resSeq) { this.resNo = reqSeq + "_" + resSeq; }

    public String getCreateYn() { return createYn; }
    public void setCreateYn(String createYn) { this.createYn = createYn; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public Boolean getResult() { return result; }
    public void setResult(Boolean result) { this.result = result; }

    public String getEndFilePath() { return endFilePath; }
    public void setEndFilePath(String endFilePath) { this.endFilePath = endFilePath; }

    public String getContractNum() { return contractNum; }
    public void setContractNum(String contractNum) { this.contractNum = contractNum; }
}
