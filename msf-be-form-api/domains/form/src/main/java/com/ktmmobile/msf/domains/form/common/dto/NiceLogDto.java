package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Getter
@Setter
@NoArgsConstructor
public class NiceLogDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long niceHistSeq = -1;  // PK
    private String reqSeq;          // 일련번호 기본키
    private String resSeq;          // 요청 일련번호
    private String authType;        // 요청 구분
    private String name;            // 인증성명
    private String birthDate;       // 인증생년월일
    private String gender;          // GENDER
    private String nationalInfo;    // NATIONAL_INFO
    private String dupInfo;         // DUP_INFO
    private String connInfo;        // CONN_INFO
    private String paramR1;         // 파라메터 1
    private String paramR2;         // 파라메터 2
    private String paramR3;         // 파라메터 3
    private String endYn;           // 종료여부
    private String rip;             // 등록아이피
    private String sysRdateDt;      // 등록일
    private Date sysRdate;          // 등록일시
    private long startTime;         // 인증 시간
    private Date rvisnDttm;         // 수정일
    private String complYn;         // 본인인증 성공 여부
    private String toName;          // 변경 할 인증성명
    private String toBirthDate;     // 변경 할 인증생년월일
    private String nCertify;
    private String nAuthType;
    private String nIp;
    private String nName;
    private String nGender;
    private String nBirthDate;
    private String nBankCode;
    private String nAccountNo;
    private String nResult;
    private String nErrorCode;
    private String nCretDt;
    private String nReferer;
    private String sMobileNo;
    private String sMobileCo;
    private long selfSmsAuthSeq = -1; // PK
    private String requestKey;
    private int limitMinute;
    private String menuType;
    private String userId;
    private String ncType;          // 대리인구분값 (0:미성년, 1:대리인)

    // n 접두사 필드들 비표준 getter/setter 유지
    public String getnCertify() { return nCertify; }
    public void setnCertify(String nCertify) { this.nCertify = nCertify; }

    public String getnAuthType() { return nAuthType; }
    public void setnAuthType(String nAuthType) { this.nAuthType = nAuthType; }

    public String getnIp() { return nIp; }
    public void setnIp(String nIp) { this.nIp = nIp; }

    // nName setter에 byte 제한 로직 포함
    public String getnName() { return nName; }
    public void setnName(String nName) { this.nName = StringUtil.substringByBytes(nName, 0, 60); }

    public String getnGender() { return nGender; }
    public void setnGender(String nGender) { this.nGender = nGender; }

    public String getnBirthDate() { return nBirthDate; }
    public void setnBirthDate(String nBirthDate) { this.nBirthDate = nBirthDate; }

    public String getnBankCode() { return nBankCode; }
    public void setnBankCode(String nBankCode) { this.nBankCode = nBankCode; }

    public String getnAccountNo() { return nAccountNo; }
    public void setnAccountNo(String nAccountNo) { this.nAccountNo = nAccountNo; }

    public String getnResult() { return nResult; }
    public void setnResult(String nResult) { this.nResult = nResult; }

    public String getnErrorCode() { return nErrorCode; }
    public void setnErrorCode(String nErrorCode) { this.nErrorCode = nErrorCode; }

    public String getnCretDt() { return nCretDt; }
    public void setnCretDt(String nCretDt) { this.nCretDt = nCretDt; }

    public String getnReferer() { return nReferer; }
    public void setnReferer(String nReferer) { this.nReferer = nReferer; }

    // s 접두사 필드들 비표준 getter/setter 유지
    public String getsMobileNo() { return sMobileNo; }
    public void setsMobileNo(String sMobileNo) { this.sMobileNo = sMobileNo; }

    public String getsMobileCo() { return sMobileCo; }
    public void setsMobileCo(String sMobileCo) { this.sMobileCo = sMobileCo; }

    // birthDate 복호화하여 반환
    public String getBirthDateDec() {
        if (birthDate != null && !"".equals(birthDate)) {
            try {
                return EncryptUtil.ace256Dec(birthDate);
            } catch (CryptoException e) {
                return "";
            }
        } else {
            return "";
        }
    }

    public Date getStartTimeToDate() {
        Date renDate;
        if (startTime > 0) {
            renDate = new Date(startTime);
        } else {
            Calendar cal = Calendar.getInstance();
            renDate = new Date(cal.getTimeInMillis());
        }
        return renDate;
    }

}
