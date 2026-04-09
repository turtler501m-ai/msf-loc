package com.ktmmobile.msf.common.dto;

import java.io.Serializable;

import com.ktmmobile.msf.common.util.StringUtil;

public class UserSessionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String passWord;
    private String name;
    private String authCode;
    private String pin;
    private String email;
    private String post;
    private String address;
    private String addressDtl;
    private String mobileNo;
    private String mobileNo1;
    private String mobileNo2;
    private String contactNo;
    private String birthday;
    private String gender;
    private String sysRdate;
    private String sysUdate;
    private String sysLdate;
    private String loginCount;
    private String status;
    private String custNum;
    private String authKey;
    private String accessIp;
    private int limitTime;

    /*
     * 01 : 정회원
     */
    private String userDivision;
    private String emailCode;
    private String addressBjd;
    private String revocationDate;
    private String answer;	//캡챠가 생성한 코드
    private String authNum;	//sms인증번호
    private String timer0;	//sms인증 3분제한이 지나면 "0"으로 셋팅됨
    private String authType;	//M:아이디찾기, P:비밀번호찾기
    private String tmpPasswrod;
    private String returnUrl;
    private String pwdChgDt;
    private int monCnt;	//비번변경 3개월 경과체크용 dto
    private int dayCnt; //비번 노출 2주간 체크

    //자동로그인-지문,간편비밀번호
    private String autoLoginSeq;
    private String token;
    private String tokenValidPeriod;
    private String loginDivCd;

    private int loginFailCount; //로그인 실패 횟수
    private boolean isLogin = false;

    private String loginType;

    /** sms 인증번호 */
    private String authSmsNo ;

    private String uuid;

    private String customerId;

    private boolean appIsFirst = true;

    private String recaptchaToken; // recaptcha token (매번 다름)

    private String limitType; // 차단 유형

    private String sysCdate; // 본인인증 일시

    private String personalInfoCollectAgree; /** 개인정보수집및이용동의*/
    private String clausePriAdFlag;	/** 약관_개인정보_광고전송_동의 */
    private String othersTrnsAgree;	/** 제 3자 제공 동의 */
    private String smsRcvYn;	/** 이메일 수신여부 */
    private String emailRcvYn;  /** sms 수신여부*/
    public int getLimitTime() {
        return limitTime;
    }
    public void setLimitTime(int limitTime) {
        this.limitTime = limitTime;
    }
    public String getAccessIp() {
        return accessIp;
    }
    public void setAccessIp(String accessIp) {
        this.accessIp = accessIp;
    }
    public boolean isAppIsFirst() {
        return appIsFirst;
    }
    public void setAppIsFirst(boolean appIsFirst) {
        this.appIsFirst = appIsFirst;
    }
    public String getUserId() {
        if(userId == null) {
            return "";
        }
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassWord() {
        return passWord;
    }
    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }
    public String getName() {
        if(name == null) {
            return "";
        }
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthCode() {
        return authCode;
    }
    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }
    public String getPin() {
        return pin;
    }
    public void setPin(String pin) {
        this.pin = pin;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPost() {
        return post;
    }
    public void setPost(String post) {
        this.post = post;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getAddressDtl() {
        return addressDtl;
    }
    public void setAddressDtl(String addressDtl) {
        this.addressDtl = addressDtl;
    }
    public String getMobileNo() {
        return mobileNo;
    }
    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }
    public String getMobileNo1() {
        return mobileNo1;
    }
    public void setMobileNo1(String mobileNo1) {
        this.mobileNo1 = mobileNo1;
    }
    public String getMobileNo2() {
        return mobileNo2;
    }
    public void setMobileNo2(String mobileNo2) {
        this.mobileNo2 = mobileNo2;
    }
    public String getContactNo() {
        return contactNo;
    }
    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getSysRdate() {
        return sysRdate;
    }
    public void setSysRdate(String sysRdate) {
        this.sysRdate = sysRdate;
    }
    public String getSysUdate() {
        return sysUdate;
    }
    public void setSysUdate(String sysUdate) {
        this.sysUdate = sysUdate;
    }
    public String getSysLdate() {
        return sysLdate;
    }
    public void setSysLdate(String sysLdate) {
        this.sysLdate = sysLdate;
    }
    public String getLoginCount() {
        return loginCount;
    }
    public void setLoginCount(String loginCount) {
        this.loginCount = loginCount;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCustNum() {
        return custNum;
    }
    public void setCustNum(String custNum) {
        this.custNum = custNum;
    }
    public String getAuthKey() {
        return authKey;
    }
    public void setAuthKey(String authKey) {
        this.authKey = authKey;
    }
    public String getUserDivision() {
        return StringUtil.NVL(userDivision,"00");
    }
    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }
    public String getEmailCode() {
        return emailCode;
    }
    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }
    public String getAddressBjd() {
        return addressBjd;
    }
    public void setAddressBjd(String addressBjd) {
        this.addressBjd = addressBjd;
    }
    public String getRevocationDate() {
        return revocationDate;
    }
    public void setRevocationDate(String revocationDate) {
        this.revocationDate = revocationDate;
    }
    public String getAuthSmsNo() {
        return authSmsNo;
    }
    public void setAuthSmsNo(String authSmsNo) {
        this.authSmsNo = authSmsNo;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getAuthNum() {
        return authNum;
    }
    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }
    public String getTimer0() {
        return timer0;
    }
    public void setTimer0(String timer0) {
        this.timer0 = timer0;
    }
    public String getAuthType() {
        return authType;
    }
    public void setAuthType(String authType) {
        this.authType = authType;
    }
    public String getTmpPasswrod() {
        return tmpPasswrod;
    }
    public void setTmpPasswrod(String tmpPasswrod) {
        this.tmpPasswrod = tmpPasswrod;
    }
    public String getReturnUrl() {
        return returnUrl;
    }
    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }
    public String getPwdChgDt() {
        return pwdChgDt;
    }
    public void setPwdChgDt(String pwdChgDt) {
        this.pwdChgDt = pwdChgDt;
    }
    public int getMonCnt() {
        return monCnt;
    }
    public void setMonCnt(int monCnt) {
        this.monCnt = monCnt;
    }
    public int getDayCnt() {
        return dayCnt;
    }
    public void setDayCnt(int dayCnt) {
        this.dayCnt = dayCnt;
    }
    public int getLoginFailCount() {
        return loginFailCount;
    }
    public void setLoginFailCount(int loginFailCount) {
        this.loginFailCount = loginFailCount;
    }
    public boolean isLogin() {
        return isLogin;
    }
    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }

    /* 로그인 10회 이상 여부 확인 */
    public boolean isLoginFailCntOver() {
        if (this.loginFailCount >= 5) {
            return true;
        } else {
            return false;
        }
    }

    /*무작위 대입공격*/
    public boolean isLoginFailAttack() {
        if (this.loginFailCount >= 999) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * @return the autoLoginSeq
     */
    public String getAutoLoginSeq() {
        return autoLoginSeq;
    }
    /**
     * @param autoLoginSeq the autoLoginSeq to set
     */
    public void setAutoLoginSeq(String autoLoginSeq) {
        this.autoLoginSeq = autoLoginSeq;
    }
    /**
     * @return the token
     */
    public String getToken() {
        return token;
    }
    /**
     * @param token the token to set
     */
    public void setToken(String token) {
        this.token = token;
    }
    /**
     * @return the tokenValidPeriod
     */
    public String getTokenValidPeriod() {
        return tokenValidPeriod;
    }
    /**
     * @param tokenValidPeriod the tokenValidPeriod to set
     */
    public void setTokenValidPeriod(String tokenValidPeriod) {
        this.tokenValidPeriod = tokenValidPeriod;
    }
    public String getLoginType() {
        return loginType;
    }
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getLoginDivCd() {
        return loginDivCd;
    }
    public void setLoginDivCd(String loginDivCd) {
        this.loginDivCd = loginDivCd;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getRecaptchaToken() {
        return recaptchaToken;
    }

    public void setRecaptchaToken(String recaptchaToken) {
        this.recaptchaToken = recaptchaToken;
    }

    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }
    public String getSysCdate() {
        return sysCdate;
    }

    public void setSysCdate(String sysCdate) {
        this.sysCdate = sysCdate;
    }

    public String getPersonalInfoCollectAgree() {
        return personalInfoCollectAgree;
    }

    public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) {
        this.personalInfoCollectAgree = personalInfoCollectAgree;
    }
    public String getClausePriAdFlag() {
        return clausePriAdFlag;
    }

    public void setClausePriAdFlag(String clausePriAdFlag) {
        this.clausePriAdFlag = clausePriAdFlag;
    }
    public String getOthersTrnsAgree() {
        return othersTrnsAgree;
    }
    public void setOthersTrnsAgree(String othersTrnsAgree) {
        this.othersTrnsAgree = othersTrnsAgree;
    }

    public String getSmsRcvYn() {
        return smsRcvYn;
    }

    public void setSmsRcvYn(String smsRcvYn) {
        this.smsRcvYn = smsRcvYn;
    }

    public String getEmailRcvYn() {
        return emailRcvYn;
    }

    public void setEmailRcvYn(String emailRcvYn) {
        this.emailRcvYn = emailRcvYn;
    }

}
