package com.ktmmobile.mcp.payinfo.dto;

import java.io.Serializable;

public class PayInfoDto implements Serializable {
    private static final long serialVersionUID = 1L;

    public String paySeq;			//시퀀스
    public String cstmrName;		//고객명
    public String birthDate;		//생년월일
    public String ctn;				//전화번호
    public String reqBank;			//은행명
    public String reqAccountNumber;	//계좌번호
    public String resNo;			//나이스인증값
    public String createYn;			//파일생성 여부
    public String userId;			//사용자 아이디
    public Boolean result;
    public String endFilePath ;      //파일명
    public String contractNum ;

    public Boolean getResult() {
        return result;
    }
    public void setResult(Boolean result) {
        this.result = result;
    }
    /**
     * @return the paySeq
     */
    public String getPaySeq() {
        return paySeq;
    }
    /**
     * @param paySeq the paySeq to set
     */
    public void setPaySeq(String paySeq) {
        this.paySeq = paySeq;
    }
    /**
     * @return the cstmrName
     */
    public String getCstmrName() {
        return cstmrName;
    }
    /**
     * @param cstmrName the cstmrName to set
     */
    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }
    /**
     * @return the birthDate
     */
    public String getBirthDate() {
        return birthDate;
    }
    /**
     * @param birthDate the birthDate to set
     */
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
    /**
     * @return the ctn
     */
    public String getCtn() {
        return ctn;
    }
    /**
     * @param ctn the ctn to set
     */
    public void setCtn(String ctn) {
        this.ctn = ctn;
    }
    /**
     * @return the reqBank
     */
    public String getReqBank() {
        return reqBank;
    }
    /**
     * @param reqBank the reqBank to set
     */
    public void setReqBank(String reqBank) {
        this.reqBank = reqBank;
    }
    /**
     * @return the reqAccountNumber
     */
    public String getReqAccountNumber() {
        return reqAccountNumber;
    }
    /**
     * @param reqAccountNumber the reqAccountNumber to set
     */
    public void setReqAccountNumber(String reqAccountNumber) {
        this.reqAccountNumber = reqAccountNumber;
    }
    /**
     * @return the resNo
     */
    public String getResNo() {
        return resNo;
    }
    /**
     * @param resNo the resNo to set
     */
    public void setResNo(String reqno ,String resno) {
        this.resNo = "ReqNo:" +reqno+", ResNo:" + resno;
    }
    /**
     * @return the createYn
     */
    public String getCreateYn() {
        return createYn;
    }
    /**
     * @param createYn the createYn to set
     */
    public void setCreateYn(String createYn) {
        this.createYn = createYn;
    }
    /**
     * @return the userId
     */
    public String getUserId() {
        return userId;
    }
    /**
     * @param userId the userId to set
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getEndFilePath() {
        return endFilePath;
    }
    public void setEndFilePath(String endFilePath) {
        this.endFilePath = endFilePath;
    }
    public String getContractNum() {
        return contractNum;
    }
    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

}
