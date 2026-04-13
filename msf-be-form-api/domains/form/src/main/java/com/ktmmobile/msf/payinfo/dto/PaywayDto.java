// TOBESKIP: TOBE 미구현 — 납부방법 변경용 DTO (ASIS 필드 보존, 기능 미구현)
package com.ktmmobile.msf.payinfo.dto;

/**
 * 납부방법 변경 DTO (X25/X82 연동용)
 * TOBESKIP: TOBE에서 납부방법 변경 기능은 미구현이나 컴파일 호환을 위해 DTO 구조만 보존한다.
 */
public class PaywayDto {

    private String reqPayType;
    private String cycleDueDay;
    private String blBankCode;
    private String bankAcctNo;
    private String ncn;
    private String ctn;
    private String custId;
    private String cstmrName;
    private String cstmrNativeRrn01;
    private String authType;
    private String cardExpirDate;
    private String creditCardNo;
    private String cardCode;
    private String adrZip;
    private String adrPrimaryLn;
    private String adrSecondaryLn;
    private String payBizCd;
    private String blpymTmsIndCd;
    private String paySeq;
    private String birthDate;
    private String createYn;
    private String userId;
    private Boolean result;
    private String endFilePath;
    private String contractNum;
    private String reqSeq;
    private String resSeq;
    private String myslAthnTypeCd;

    public String getReqPayType() { return reqPayType; }
    public void setReqPayType(String reqPayType) { this.reqPayType = reqPayType; }

    public String getCycleDueDay() { return cycleDueDay; }
    public void setCycleDueDay(String cycleDueDay) { this.cycleDueDay = cycleDueDay; }

    public String getBlBankCode() { return blBankCode; }
    public void setBlBankCode(String blBankCode) { this.blBankCode = blBankCode; }

    public String getBankAcctNo() { return bankAcctNo; }
    public void setBankAcctNo(String bankAcctNo) { this.bankAcctNo = bankAcctNo; }

    public String getNcn() { return ncn; }
    public void setNcn(String ncn) { this.ncn = ncn; }

    public String getCtn() { return ctn; }
    public void setCtn(String ctn) { this.ctn = ctn; }

    public String getCustId() { return custId; }
    public void setCustId(String custId) { this.custId = custId; }

    public String getCstmrName() { return cstmrName; }
    public void setCstmrName(String cstmrName) { this.cstmrName = cstmrName; }

    public String getCstmrNativeRrn01() { return cstmrNativeRrn01; }
    public void setCstmrNativeRrn01(String cstmrNativeRrn01) { this.cstmrNativeRrn01 = cstmrNativeRrn01; }

    public String getAuthType() { return authType; }
    public void setAuthType(String authType) { this.authType = authType; }

    public String getCardExpirDate() { return cardExpirDate; }
    public void setCardExpirDate(String cardExpirDate) { this.cardExpirDate = cardExpirDate; }

    public String getCreditCardNo() { return creditCardNo; }
    public void setCreditCardNo(String creditCardNo) { this.creditCardNo = creditCardNo; }

    public String getCardCode() { return cardCode; }
    public void setCardCode(String cardCode) { this.cardCode = cardCode; }

    public String getAdrZip() { return adrZip; }
    public void setAdrZip(String adrZip) { this.adrZip = adrZip; }

    public String getAdrPrimaryLn() { return adrPrimaryLn; }
    public void setAdrPrimaryLn(String adrPrimaryLn) { this.adrPrimaryLn = adrPrimaryLn; }

    public String getAdrSecondaryLn() { return adrSecondaryLn; }
    public void setAdrSecondaryLn(String adrSecondaryLn) { this.adrSecondaryLn = adrSecondaryLn; }

    public String getPayBizCd() { return payBizCd; }
    public void setPayBizCd(String payBizCd) { this.payBizCd = payBizCd; }

    public String getBlpymTmsIndCd() { return blpymTmsIndCd; }
    public void setBlpymTmsIndCd(String blpymTmsIndCd) { this.blpymTmsIndCd = blpymTmsIndCd; }

    public String getPaySeq() { return paySeq; }
    public void setPaySeq(String paySeq) { this.paySeq = paySeq; }

    public String getBirthDate() { return birthDate; }
    public void setBirthDate(String birthDate) { this.birthDate = birthDate; }

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

    public String getReqSeq() { return reqSeq; }
    public void setReqSeq(String reqSeq) { this.reqSeq = reqSeq; }

    public String getResSeq() { return resSeq; }
    public void setResSeq(String resSeq) { this.resSeq = resSeq; }

    public String getMyslAthnTypeCd() { return myslAthnTypeCd; }
    public void setMyslAthnTypeCd(String myslAthnTypeCd) { this.myslAthnTypeCd = myslAthnTypeCd; }
}
