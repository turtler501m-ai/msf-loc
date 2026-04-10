package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.StringUtil;

public class NiceLogDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    /** PK */
    private long niceHistSeq =-1;
    /** 일련번호 기본키 */
    private String reqSeq;
    /** 요청 일련번호 */
    private String resSeq;
    /** 요청 구분 */
    private String authType;
    /** 인증성명 */
    private String name;
    /** 인증생년월일 */
    private String birthDate;
    /** GENDER */
    private String gender;
    /** NATIONAL_INFO */
    private String nationalInfo;
    /** DUP_INFO */
    private String dupInfo;
    /** CONN_INFO */
    private String connInfo;
    /** 파라메터 1 */
    private String paramR1;
    /** 파라메터 2 */
    private String paramR2;
    /** 파라메터 3 */
    private String paramR3;
    /** 종료여부 */
    private String endYn;
    /** 등록아이피 */
    private String rip;
    /** 등록일 */
    private String sysRdateDt;
    /** 등록일시 */
    private Date sysRdate;
    /** 인증 시간 시간  */
    private long startTime ;



    /** 수정일 */
    private Date rvisnDttm;
    /** 본인인증 성공 여부  */
    private String complYn ;

    /** 변경 할 인증성명 */
    private String toName;
    /** 변경 할  인증생년월일 */
    private String toBirthDate;



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

    /** PK */
    private long selfSmsAuthSeq =-1;
    private String requestKey;
    private int limitMinute;
    private String menuType;
    private String userId;

    /** 대리인구분값(0:미성년, 1:대리인)*/
    private String ncType;

    public String getNcType() {
        return ncType;
    }
    public void setNcType(String ncType) {
        this.ncType = ncType;
    }
    public long getSelfSmsAuthSeq() {
        return selfSmsAuthSeq;
    }
    public void setSelfSmsAuthSeq(long selfSmsAuthSeq) {
        this.selfSmsAuthSeq = selfSmsAuthSeq;
    }
    public String getRequestKey() {
        return requestKey;
    }
    public void setRequestKey(String requestKey) {
        this.requestKey = requestKey;
    }
    public String getsMobileNo() {
        return sMobileNo;
    }
    public void setsMobileNo(String sMobileNo) {
        this.sMobileNo = sMobileNo;
    }
    public String getsMobileCo() {
        return sMobileCo;
    }
    public void setsMobileCo(String sMobileCo) {
        this.sMobileCo = sMobileCo;
    }
    /**
     * @return the nCertify
     */
    public String getnCertify() {
        return nCertify;
    }
    /**
     * @param nCertify the nCertify to set
     */
    public void setnCertify(String nCertify) {
        this.nCertify = nCertify;
    }
    /**
     * @return the nAuthType
     */
    public String getnAuthType() {
        return nAuthType;
    }
    /**
     * @param nAuthType the nAuthType to set
     */
    public void setnAuthType(String nAuthType) {
        this.nAuthType = nAuthType;
    }
    /**
     * @return the nIp
     */
    public String getnIp() {
        return nIp;
    }
    /**
     * @param nIp the nIp to set
     */
    public void setnIp(String nIp) {
        this.nIp = nIp;
    }
    /**
     * @return the nName
     */
    public String getnName() {
        return nName;
    }
    /**
     * @param nName the nName to set
     */
    public void setnName(String nName) {
        this.nName = StringUtil.substringByBytes(nName,0,60) ;
    }
    /**
     * @return the nGender
     */
    public String getnGender() {
        return nGender;
    }
    /**
     * @param nGender the nGender to set
     */
    public void setnGender(String nGender) {
        this.nGender = nGender;
    }
    /**
     * @return the nBirthDate
     */
    public String getnBirthDate() {
        return nBirthDate;
    }
    /**
     * @param nBirthDate the nBirthDate to set
     */
    public void setnBirthDate(String nBirthDate) {
        this.nBirthDate = nBirthDate;
    }
    /**
     * @return the nBankCode
     */
    public String getnBankCode() {
        return nBankCode;
    }
    /**
     * @param nBankCode the nBankCode to set
     */
    public void setnBankCode(String nBankCode) {
        this.nBankCode = nBankCode;
    }
    /**
     * @return the nAccountNo
     */
    public String getnAccountNo() {
        return nAccountNo;
    }
    /**
     * @param nAccountNo the nAccountNo to set
     */
    public void setnAccountNo(String nAccountNo) {
        this.nAccountNo = nAccountNo;
    }
    /**
     * @return the nResult
     */
    public String getnResult() {
        return nResult;
    }
    /**
     * @param nResult the nResult to set
     */
    public void setnResult(String nResult) {
        this.nResult = nResult;
    }
    /**
     * @return the nErrorCode
     */
    public String getnErrorCode() {
        return nErrorCode;
    }
    /**
     * @param nErrorCode the nErrorCode to set
     */
    public void setnErrorCode(String nErrorCode) {
        this.nErrorCode = nErrorCode;
    }
    /**
     * @return the nCretDt
     */
    public String getnCretDt() {
        return nCretDt;
    }
    /**
     * @param nCretDt the nCretDt to set
     */
    public void setnCretDt(String nCretDt) {
        this.nCretDt = nCretDt;
    }
    /**
     * @return the nReferer
     */
    public String getnReferer() {
        return nReferer;
    }
    /**
     * @param nReferer the nReferer to set
     */
    public void setnReferer(String nReferer) {
        this.nReferer = nReferer;
    }
    public long getNiceHistSeq() {
        return niceHistSeq;
    }
    public void setNiceHistSeq(long niceHistSeq) {
        this.niceHistSeq = niceHistSeq;
    }
    public String getReqSeq() {
        return reqSeq;
    }
    public void setReqSeq(String reqSeq) {
        this.reqSeq = reqSeq;
    }
    public String getResSeq() {
        return resSeq;
    }
    public void setResSeq(String resSeq) {
        this.resSeq = resSeq;
    }
    public String getAuthType() {
        return authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBirthDate() {
        return birthDate;
    }
    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }
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
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNationalInfo() {
        return nationalInfo;
    }
    public void setNationalInfo(String nationalInfo) {
        this.nationalInfo = nationalInfo;
    }
    public String getDupInfo() {
        return dupInfo;
    }
    public void setDupInfo(String dupInfo) {
        this.dupInfo = dupInfo;
    }
    public String getConnInfo() {
        return connInfo;
    }
    public void setConnInfo(String connInfo) {
        this.connInfo = connInfo;
    }
    public String getParamR1() {
        return paramR1;
    }
    public void setParamR1(String paramR1) {
        this.paramR1 = paramR1;
    }
    public String getParamR2() {
        return paramR2;
    }
    public void setParamR2(String paramR2) {
        this.paramR2 = paramR2;
    }
    public String getParamR3() {
        return paramR3;
    }
    public void setParamR3(String paramR3) {
        this.paramR3 = paramR3;
    }
    public String getEndYn() {
        return endYn;
    }
    public void setEndYn(String endYn) {
        this.endYn = endYn;
    }
    public String getRip() {
        return rip;
    }
    public void setRip(String rip) {
        this.rip = rip;
    }
    public String getSysRdateDt() {
        return sysRdateDt;
    }
    public void setSysRdateDt(String sysRdateDt) {
        this.sysRdateDt = sysRdateDt;
    }
    public Date getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(Date sysRdate) {
        this.sysRdate = sysRdate;
    }
    public Date getRvisnDttm() {
        return rvisnDttm;
    }
    public void setRvisnDttm(Date rvisnDttm) {
        this.rvisnDttm = rvisnDttm;
    }
    public String getComplYn() {
        return complYn;
    }
    public void setComplYn(String complYn) {
        this.complYn = complYn;
    }
    public String getToName() {
        return toName;
    }
    public void setToName(String toName) {
        this.toName = toName;
    }
    public String getToBirthDate() {
        return toBirthDate;
    }
    public void setToBirthDate(String toBirthDate) {
        this.toBirthDate = toBirthDate;
    }
    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public Date getStartTimeToDate() {

        Date renDate = null;
        if (startTime > 0) {
            renDate = new Date(startTime);
        } else {
            Calendar cal = Calendar.getInstance();
            renDate = new Date(cal.getTimeInMillis());
        }

        return  renDate ;

    }

    public int getLimitMinute() {
        return limitMinute;
    }

    public void setLimitMinute(int limitMinute) {
        this.limitMinute = limitMinute;
    }
    public String getMenuType() {
        return menuType;
    }
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
}
