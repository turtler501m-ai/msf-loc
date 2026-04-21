package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

public class ApiMapDto implements Serializable {

    private static final long serialVersionUID = -4417006022458054488L;

    private String SMS_SEQ;
    private String I_SUBJECT;        //kakao LMS SMS
    private String I_MSG_TYPE;        //kakao(6) LMS(3) SMS(1)
    private String I_CTN;            //kakao LMS SMS
    private String I_CALLBACK;        //kakao LMS SMS
    private String I_MSG;            //kakao LMS SMS
    private String I_NEXT_TYPE;        //kakao(9)
    private String I_TEMPLATE_CD;    //kakao
    private String I_SENDER_KEY;    //kakao
    private String Success;            //LMS(100) SMS
    private String I_EVENT;            // SMS
    private String I_CUST_YN;        // SMS
    private String O_RET_CODE;        // SMS
    private String O_RET_MSG;        // SMS
    private String RESERVED02;        // 발송목적 ex)OTP
    private String RESERVED03;        // 발송자
    private String IS_REAL;            // 발송 서버(local,dev,stg : N, real : Y)
    private String SCHEDULE_TIME;
    private long sendSeq;           // 발송이력채번
    private String mobileNo;         // 수신자 전화번호
    private String templateId;      // 템플릿 ID
    private int sendCnt;             // 발송 횟수
    private String message;          // 발송 메시지 내용

    private String I_FILECNT; // 파일개수
    private String I_FILECNT_CHECKUP; //실제 체크된 파일개수
    private String I_FILELOC1; // 파일1위치 (URL) (MMS)
    private String I_FILESIZE1; // 파일1 사이즈 (MMS)
    private String IS_FILE = "N"; // 첨부파일 여부
    //mspApplyCount
    private String cstmrName;
    private String cstmrMobileFn;
    private String cstmrMobileMn;
    private String cstmrMobileRn;

    //appPps
    private String opCode;
    private String subscriberNo;
    private String userName;
    private String smsAuthCode;
    private String LANG;
    private String appType;
    private String appVer;
    private String iUrl;
    private String accessIp;
    private String smsSeq;
    private String O_CODE;
    private String O_MSG;
    private String O_DATA;
    private String contractNum;
    private String sessionId;
    private String bankCd;
    private String pinNo;
    private String mngCd;
    private String ccdNo;
    private String ccdExpire;
    private String ccdPW;
    private String ccdBirth;
    private String ccdRcgAmt;
    private String ccdPayAmt;
    private String rcgReq;
    private String rcgAmt;
    private String payAmt;
    private String cardPayCode;
    private String cardErrMsg;
    private String cardOrderNum;
    private String cardAmount;
    private String cardDouTrx;
    private String cardAuthNo;
    private String cardAuthDate;
    private String cardNointFlag;
    private String cardCpName;
    private String cardCpUrl;
    private String cardDouRsv1;
    private String cardDouRsv2;
    private String TMP;
    private String reqServer;

    /**
     * @return the i_SUBJECT
     */
    public String getI_SUBJECT() {
        return I_SUBJECT;
    }

    /**
     * @param i_SUBJECT the i_SUBJECT to set
     */
    public void setI_SUBJECT(String i_SUBJECT) {
        I_SUBJECT = i_SUBJECT;
    }

    /**
     * @return the i_MSG_TYPE
     */
    public String getI_MSG_TYPE() {
        return I_MSG_TYPE;
    }

    /**
     * @param i_MSG_TYPE the i_MSG_TYPE to set
     */
    public void setI_MSG_TYPE(String i_MSG_TYPE) {
        I_MSG_TYPE = i_MSG_TYPE;
    }

    /**
     * @return the i_CTN
     */
    public String getI_CTN() {
        return I_CTN;
    }

    /**
     * @param i_CTN the i_CTN to set
     */
    public void setI_CTN(String i_CTN) {
        I_CTN = i_CTN;
    }

    /**
     * @return the i_CALLBACK
     */
    public String getI_CALLBACK() {
        return I_CALLBACK;
    }

    /**
     * @param i_CALLBACK the i_CALLBACK to set
     */
    public void setI_CALLBACK(String i_CALLBACK) {
        I_CALLBACK = i_CALLBACK;
    }

    /**
     * @return the i_MSG
     */
    public String getI_MSG() {
        return I_MSG;
    }

    /**
     * @param i_MSG the i_MSG to set
     */
    public void setI_MSG(String i_MSG) {
        I_MSG = i_MSG;
    }

    /**
     * @return the i_NEXT_TYPE
     */
    public String getI_NEXT_TYPE() {
        return I_NEXT_TYPE;
    }

    /**
     * @param i_NEXT_TYPE the i_NEXT_TYPE to set
     */
    public void setI_NEXT_TYPE(String i_NEXT_TYPE) {
        I_NEXT_TYPE = i_NEXT_TYPE;
    }

    /**
     * @return the i_TEMPLATE_CD
     */
    public String getI_TEMPLATE_CD() {
        return I_TEMPLATE_CD;
    }

    /**
     * @param i_TEMPLATE_CD the i_TEMPLATE_CD to set
     */
    public void setI_TEMPLATE_CD(String i_TEMPLATE_CD) {
        I_TEMPLATE_CD = i_TEMPLATE_CD;
    }

    /**
     * @return the i_SENDER_KEY
     */
    public String getI_SENDER_KEY() {
        return I_SENDER_KEY;
    }

    /**
     * @param i_SENDER_KEY the i_SENDER_KEY to set
     */
    public void setI_SENDER_KEY(String i_SENDER_KEY) {
        I_SENDER_KEY = i_SENDER_KEY;
    }

    /**
     * @return the success
     */
    public String getSuccess() {
        return Success;
    }

    /**
     * @param success the success to set
     */
    public void setSuccess(String success) {
        Success = success;
    }

    /**
     * @return the i_EVENT
     */
    public String getI_EVENT() {
        return I_EVENT;
    }

    /**
     * @param i_EVENT the i_EVENT to set
     */
    public void setI_EVENT(String i_EVENT) {
        I_EVENT = i_EVENT;
    }

    /**
     * @return the i_CUST_YN
     */
    public String getI_CUST_YN() {
        return I_CUST_YN;
    }

    /**
     * @param i_CUST_YN the i_CUST_YN to set
     */
    public void setI_CUST_YN(String i_CUST_YN) {
        I_CUST_YN = i_CUST_YN;
    }

    /**
     * @return the o_RET_CODE
     */
    public String getO_RET_CODE() {
        return O_RET_CODE;
    }

    /**
     * @param o_RET_CODE the o_RET_CODE to set
     */
    public void setO_RET_CODE(String o_RET_CODE) {
        O_RET_CODE = o_RET_CODE;
    }

    /**
     * @return the o_RET_MSG
     */
    public String getO_RET_MSG() {
        return O_RET_MSG;
    }

    /**
     * @param o_RET_MSG the o_RET_MSG to set
     */
    public void setO_RET_MSG(String o_RET_MSG) {
        O_RET_MSG = o_RET_MSG;
    }

    public String getSMS_SEQ() {
        return SMS_SEQ;
    }

    public void setSMS_SEQ(String sMS_SEQ) {
        SMS_SEQ = sMS_SEQ;
    }

    public String getOpCode() {
        return opCode;
    }

    public void setOpCode(String opCode) {
        this.opCode = opCode;
    }

    public String getSubscriberNo() {
        return subscriberNo;
    }

    public void setSubscriberNo(String subscriberNo) {
        this.subscriberNo = subscriberNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSmsAuthCode() {
        return smsAuthCode;
    }

    public void setSmsAuthCode(String smsAuthCode) {
        this.smsAuthCode = smsAuthCode;
    }

    public String getLANG() {
        return LANG;
    }

    public void setLANG(String lANG) {
        LANG = lANG;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getAppVer() {
        return appVer;
    }

    public void setAppVer(String appVer) {
        this.appVer = appVer;
    }

    public String getiUrl() {
        return iUrl;
    }

    public void setiUrl(String iUrl) {
        this.iUrl = iUrl;
    }

    public String getAccessIp() {
        return accessIp;
    }

    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }

    public String getSmsSeq() {
        return smsSeq;
    }

    public void setSmsSeq(String smsSeq) {
        this.smsSeq = smsSeq;
    }

    public String getO_CODE() {
        return O_CODE;
    }

    public void setO_CODE(String o_CODE) {
        O_CODE = o_CODE;
    }

    public String getO_MSG() {
        return O_MSG;
    }

    public void setO_MSG(String o_MSG) {
        O_MSG = o_MSG;
    }

    public String getO_DATA() {
        return O_DATA;
    }

    public void setO_DATA(String o_DATA) {
        O_DATA = o_DATA;
    }

    public String getContractNum() {
        return contractNum;
    }

    public void setContractNum(String contractNum) {
        this.contractNum = contractNum;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getBankCd() {
        return bankCd;
    }

    public void setBankCd(String bankCd) {
        this.bankCd = bankCd;
    }

    public String getPinNo() {
        return pinNo;
    }

    public void setPinNo(String pinNo) {
        this.pinNo = pinNo;
    }

    public String getMngCd() {
        return mngCd;
    }

    public void setMngCd(String mngCd) {
        this.mngCd = mngCd;
    }

    public String getCcdNo() {
        return ccdNo;
    }

    public void setCcdNo(String ccdNo) {
        this.ccdNo = ccdNo;
    }

    public String getCcdExpire() {
        return ccdExpire;
    }

    public void setCcdExpire(String ccdExpire) {
        this.ccdExpire = ccdExpire;
    }

    public String getCcdPW() {
        return ccdPW;
    }

    public void setCcdPW(String ccdPW) {
        this.ccdPW = ccdPW;
    }

    public String getCcdBirth() {
        return ccdBirth;
    }

    public void setCcdBirth(String ccdBirth) {
        this.ccdBirth = ccdBirth;
    }

    public String getCcdRcgAmt() {
        return ccdRcgAmt;
    }

    public void setCcdRcgAmt(String ccdRcgAmt) {
        this.ccdRcgAmt = ccdRcgAmt;
    }

    public String getCcdPayAmt() {
        return ccdPayAmt;
    }

    public void setCcdPayAmt(String ccdPayAmt) {
        this.ccdPayAmt = ccdPayAmt;
    }

    public String getRcgReq() {
        return rcgReq;
    }

    public void setRcgReq(String rcgReq) {
        this.rcgReq = rcgReq;
    }

    public String getRcgAmt() {
        return rcgAmt;
    }

    public void setRcgAmt(String rcgAmt) {
        this.rcgAmt = rcgAmt;
    }

    public String getPayAmt() {
        return payAmt;
    }

    public void setPayAmt(String payAmt) {
        this.payAmt = payAmt;
    }

    public String getCardPayCode() {
        return cardPayCode;
    }

    public void setCardPayCode(String cardPayCode) {
        this.cardPayCode = cardPayCode;
    }

    public String getCardErrMsg() {
        return cardErrMsg;
    }

    public void setCardErrMsg(String cardErrMsg) {
        this.cardErrMsg = cardErrMsg;
    }

    public String getCardOrderNum() {
        return cardOrderNum;
    }

    public void setCardOrderNum(String cardOrderNum) {
        this.cardOrderNum = cardOrderNum;
    }

    public String getCardAmount() {
        return cardAmount;
    }

    public void setCardAmount(String cardAmount) {
        this.cardAmount = cardAmount;
    }

    public String getCardDouTrx() {
        return cardDouTrx;
    }

    public void setCardDouTrx(String cardDouTrx) {
        this.cardDouTrx = cardDouTrx;
    }

    public String getCardAuthNo() {
        return cardAuthNo;
    }

    public void setCardAuthNo(String cardAuthNo) {
        this.cardAuthNo = cardAuthNo;
    }

    public String getCardAuthDate() {
        return cardAuthDate;
    }

    public void setCardAuthDate(String cardAuthDate) {
        this.cardAuthDate = cardAuthDate;
    }

    public String getCardNointFlag() {
        return cardNointFlag;
    }

    public void setCardNointFlag(String cardNointFlag) {
        this.cardNointFlag = cardNointFlag;
    }

    public String getCardCpName() {
        return cardCpName;
    }

    public void setCardCpName(String cardCpName) {
        this.cardCpName = cardCpName;
    }

    public String getCardCpUrl() {
        return cardCpUrl;
    }

    public void setCardCpUrl(String cardCpUrl) {
        this.cardCpUrl = cardCpUrl;
    }

    public String getCardDouRsv1() {
        return cardDouRsv1;
    }

    public void setCardDouRsv1(String cardDouRsv1) {
        this.cardDouRsv1 = cardDouRsv1;
    }

    public String getCardDouRsv2() {
        return cardDouRsv2;
    }

    public void setCardDouRsv2(String cardDouRsv2) {
        this.cardDouRsv2 = cardDouRsv2;
    }

    public String getTMP() {
        return TMP;
    }

    public void setTMP(String tMP) {
        TMP = tMP;
    }

    public String getCstmrName() {
        return cstmrName;
    }

    public void setCstmrName(String cstmrName) {
        this.cstmrName = cstmrName;
    }

    public String getCstmrMobileFn() {
        return cstmrMobileFn;
    }

    public void setCstmrMobileFn(String cstmrMobileFn) {
        this.cstmrMobileFn = cstmrMobileFn;
    }

    public String getCstmrMobileMn() {
        return cstmrMobileMn;
    }

    public void setCstmrMobileMn(String cstmrMobileMn) {
        this.cstmrMobileMn = cstmrMobileMn;
    }

    public String getCstmrMobileRn() {
        return cstmrMobileRn;
    }

    public void setCstmrMobileRn(String cstmrMobileRn) {
        this.cstmrMobileRn = cstmrMobileRn;
    }

    public String getReqServer() {
        return reqServer;
    }

    public void setReqServer(String reqServer) {
        this.reqServer = reqServer;
    }

    public String getI_FILECNT() {
        return I_FILECNT;
    }

    public void setI_FILECNT(String i_FILECNT) {
        I_FILECNT = i_FILECNT;
    }

    public String getI_FILECNT_CHECKUP() {
        return I_FILECNT_CHECKUP;
    }

    public void setI_FILECNT_CHECKUP(String i_FILECNT_CHECKUP) {
        I_FILECNT_CHECKUP = i_FILECNT_CHECKUP;
    }

    public String getI_FILELOC1() {
        return I_FILELOC1;
    }

    public void setI_FILELOC1(String i_FILELOC1) {
        I_FILELOC1 = i_FILELOC1;
    }

    public String getI_FILESIZE1() {
        return I_FILESIZE1;
    }

    public void setI_FILESIZE1(String i_FILESIZE1) {
        I_FILESIZE1 = i_FILESIZE1;
    }

    public String getIS_FILE() {
        return IS_FILE;
    }

    public void setIS_FILE(String iS_FILE) {
        IS_FILE = iS_FILE;
    }

    public String getRESERVED02() {
        return RESERVED02;
    }

    public void setRESERVED02(String rESERVED02) {
        RESERVED02 = rESERVED02;
    }

    public String getRESERVED03() {
        return RESERVED03;
    }

    public void setRESERVED03(String rESERVED03) {
        RESERVED03 = rESERVED03;
    }

    public String getIS_REAL() {
        return IS_REAL;
    }

    public void setIS_REAL(String iS_REAL) {
        IS_REAL = iS_REAL;
    }

    public String getSCHEDULE_TIME() {
        return SCHEDULE_TIME;
    }

    public void setSCHEDULE_TIME(String SCHEDULE_TIME) {
        this.SCHEDULE_TIME = SCHEDULE_TIME;
    }

    public long getSendSeq() {
        return sendSeq;
    }

    public void setSendSeq(long sendSeq) {
        this.sendSeq = sendSeq;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public int getSendCnt() {
        return sendCnt;
    }

    public void setSendCnt(int sendCnt) {
        this.sendCnt = sendCnt;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}