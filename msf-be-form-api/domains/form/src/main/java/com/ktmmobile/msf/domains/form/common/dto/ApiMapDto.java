package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ApiMapDto implements Serializable {

    private static final long serialVersionUID = -4417006022458054488L;

    private String SMS_SEQ;
    private String I_SUBJECT;          // kakao LMS SMS
    private String I_MSG_TYPE;         // kakao(6), LMS(3), SMS(1)
    private String I_CTN;              // kakao LMS SMS
    private String I_CALLBACK;         // kakao LMS SMS
    private String I_MSG;              // kakao LMS SMS
    private String I_NEXT_TYPE;        // kakao(9)
    private String I_TEMPLATE_CD;      // kakao
    private String I_SENDER_KEY;       // kakao
    private String Success;            // LMS(100), SMS
    private String I_EVENT;            // SMS
    private String I_CUST_YN;          // SMS
    private String O_RET_CODE;         // SMS
    private String O_RET_MSG;          // SMS
    private String RESERVED02;         // 발송목적 ex)OTP
    private String RESERVED03;         // 발송자
    private String IS_REAL;            // 발송서버 local,dev,stg:N, real:Y
    private String SCHEDULE_TIME;
    private long sendSeq;              // 발송이력채번
    private String mobileNo;           // 수신자 전화번호
    private String templateId;         // 템플릿 ID
    private int sendCnt;               // 발송 횟수
    private String message;            // 발송 메시지 내용
    private String I_FILECNT;          // 파일개수
    private String I_FILECNT_CHECKUP;  // 실제 체크된 파일개수
    private String I_FILELOC1;         // 파일1위치 URL (MMS)
    private String I_FILESIZE1;        // 파일1 사이즈 (MMS)
    private String IS_FILE = "N";      // 첨부파일 여부
    private String cstmrName;
    private String cstmrMobileFn;
    private String cstmrMobileMn;
    private String cstmrMobileRn;
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

    // 대문자 시작 필드들 비표준 getter/setter 유지
    public String getI_SUBJECT() { return I_SUBJECT; }
    public void setI_SUBJECT(String i_SUBJECT) { I_SUBJECT = i_SUBJECT; }

    public String getI_MSG_TYPE() { return I_MSG_TYPE; }
    public void setI_MSG_TYPE(String i_MSG_TYPE) { I_MSG_TYPE = i_MSG_TYPE; }

    public String getI_CTN() { return I_CTN; }
    public void setI_CTN(String i_CTN) { I_CTN = i_CTN; }

    public String getI_CALLBACK() { return I_CALLBACK; }
    public void setI_CALLBACK(String i_CALLBACK) { I_CALLBACK = i_CALLBACK; }

    public String getI_MSG() { return I_MSG; }
    public void setI_MSG(String i_MSG) { I_MSG = i_MSG; }

    public String getI_NEXT_TYPE() { return I_NEXT_TYPE; }
    public void setI_NEXT_TYPE(String i_NEXT_TYPE) { I_NEXT_TYPE = i_NEXT_TYPE; }

    public String getI_TEMPLATE_CD() { return I_TEMPLATE_CD; }
    public void setI_TEMPLATE_CD(String i_TEMPLATE_CD) { I_TEMPLATE_CD = i_TEMPLATE_CD; }

    public String getI_SENDER_KEY() { return I_SENDER_KEY; }
    public void setI_SENDER_KEY(String i_SENDER_KEY) { I_SENDER_KEY = i_SENDER_KEY; }

    public String getSuccess() { return Success; }
    public void setSuccess(String success) { Success = success; }

    public String getI_EVENT() { return I_EVENT; }
    public void setI_EVENT(String i_EVENT) { I_EVENT = i_EVENT; }

    public String getI_CUST_YN() { return I_CUST_YN; }
    public void setI_CUST_YN(String i_CUST_YN) { I_CUST_YN = i_CUST_YN; }

    public String getO_RET_CODE() { return O_RET_CODE; }
    public void setO_RET_CODE(String o_RET_CODE) { O_RET_CODE = o_RET_CODE; }

    public String getO_RET_MSG() { return O_RET_MSG; }
    public void setO_RET_MSG(String o_RET_MSG) { O_RET_MSG = o_RET_MSG; }

    public String getSMS_SEQ() { return SMS_SEQ; }
    public void setSMS_SEQ(String sMS_SEQ) { SMS_SEQ = sMS_SEQ; }

    public String getO_CODE() { return O_CODE; }
    public void setO_CODE(String o_CODE) { O_CODE = o_CODE; }

    public String getO_MSG() { return O_MSG; }
    public void setO_MSG(String o_MSG) { O_MSG = o_MSG; }

    public String getO_DATA() { return O_DATA; }
    public void setO_DATA(String o_DATA) { O_DATA = o_DATA; }

    public String getLANG() { return LANG; }
    public void setLANG(String lANG) { LANG = lANG; }

    public String getTMP() { return TMP; }
    public void setTMP(String tMP) { TMP = tMP; }

    public String getRESERVED02() { return RESERVED02; }
    public void setRESERVED02(String rESERVED02) { RESERVED02 = rESERVED02; }

    public String getRESERVED03() { return RESERVED03; }
    public void setRESERVED03(String rESERVED03) { RESERVED03 = rESERVED03; }

    public String getIS_REAL() { return IS_REAL; }
    public void setIS_REAL(String iS_REAL) { IS_REAL = iS_REAL; }

    public String getSCHEDULE_TIME() { return SCHEDULE_TIME; }
    public void setSCHEDULE_TIME(String SCHEDULE_TIME) { this.SCHEDULE_TIME = SCHEDULE_TIME; }

    public String getI_FILECNT() { return I_FILECNT; }
    public void setI_FILECNT(String i_FILECNT) { I_FILECNT = i_FILECNT; }

    public String getI_FILECNT_CHECKUP() { return I_FILECNT_CHECKUP; }
    public void setI_FILECNT_CHECKUP(String i_FILECNT_CHECKUP) { I_FILECNT_CHECKUP = i_FILECNT_CHECKUP; }

    public String getI_FILELOC1() { return I_FILELOC1; }
    public void setI_FILELOC1(String i_FILELOC1) { I_FILELOC1 = i_FILELOC1; }

    public String getI_FILESIZE1() { return I_FILESIZE1; }
    public void setI_FILESIZE1(String i_FILESIZE1) { I_FILESIZE1 = i_FILESIZE1; }

    public String getIS_FILE() { return IS_FILE; }
    public void setIS_FILE(String iS_FILE) { IS_FILE = iS_FILE; }

    // i 접두사 필드 비표준 getter/setter 유지
    public String getiUrl() { return iUrl; }
    public void setiUrl(String iUrl) { this.iUrl = iUrl; }

    // s 접두사 필드 비표준 getter/setter 유지 (sMobileNo → Lombok은 getSMobileNo 생성)
    public String getsMobileNo() { return mobileNo; }
    public void setsMobileNo(String mobileNo) { this.mobileNo = mobileNo; }

}
