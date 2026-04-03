package com.ktmmobile.msf.system.common.legacy.join.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.ktmmobile.msf.system.common.dto.NiceResDto;

public class JoinDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    private String userId;
    private String password;
    private String name;
    private String authCode;
    private String pin;
    private String email;
    private String post;
    private String address;
    private String addressDtl;
    private String mobileNo;
    private String birthday;
    private int loginCnt;
    private String status;
    private String userDivision;
    private String agreeEmail;
    private String agreeSMS;
    private String gender;
    private String mapping;
    private String passwordChk;

    /** 약관_개인정보_광고전송_동의 */
    private String clausePriAdFlag;
    private String personalInfoCollectAgree;
    private String othersTrnsAgree;

    /** 본인인증한 일련번호 */
    private long niceHistSeq =-1;

    /** 법정 대리인 인증한 일련번호 */
    private long niceAgentHistSeq = -1;

    /** 법정 대리인 법정대리인 관계 */
    private String minorAgentRelation = "";

    private String snsAddYn;
    private String snsCd;
    private String snsId;
    private String diVal;
    private String encUserId;

    /** 관심사 */
    private String chkInterest;

    /** 인증 통신사 */
    private String authTelCode;
    private String minorAgentName;

    private String mtkAgrReferer;

    /** 미성년자 법정대리인 개인정보 수집이용 동의여부 */
    private String minorAgentAgree;

    /**
     * 위변조 체크를 하고 정상시
     *  JoinDto 에 값을 할당한다
     * @param niceResDto
     * @return 위변조여부 (위변조시 true 리턴)
     */
    public boolean isChkAndPut(NiceResDto niceResDto) {
        if (StringUtils.isEmpty(niceResDto.getBirthDate())
                || StringUtils.isEmpty(birthday)
                || !niceResDto.getBirthDate().equals(birthday)) {
            return true;
        }
        //TODO:위변조 검증 추가

        birthday = niceResDto.getBirthDate();
        return false;
    }



    public String getMapping() {
        return mapping;
    }
    public void setMapping(String mapping) {
        this.mapping = mapping;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
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
    public String getBirthday() {
        return birthday;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public int getLoginCnt() {
        return loginCnt;
    }
    public void setLoginCnt(int loginCnt) {
        this.loginCnt = loginCnt;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getUserDivision() {
        return userDivision;
    }
    public void setUserDivision(String userDivision) {
        this.userDivision = userDivision;
    }
    public String getAgreeEmail() {
        return agreeEmail;
    }
    public void setAgreeEmail(String agreeEmail) {
        this.agreeEmail = agreeEmail;
    }
    public String getAgreeSMS() {
        return agreeSMS;
    }
    public void setAgreeSMS(String agreeSMS) {
        this.agreeSMS = agreeSMS;
    }
    public String getPasswordChk() {
        return passwordChk;
    }
    public void setPasswordChk(String passwordChk) {
        this.passwordChk = passwordChk;
    }
    public String getSnsCd() {
        return snsCd;
    }
    public void setSnsCd(String snsCd) {
        this.snsCd = snsCd;
    }
    public String getSnsId() {
        return snsId;
    }
    public void setSnsId(String snsId) {
        this.snsId = snsId;
    }
    public String getDiVal() {
        return diVal;
    }
    public void setDiVal(String diVal) {
        this.diVal = diVal;
    }
    public String getSnsAddYn() {
        return snsAddYn;
    }
    public void setSnsAddYn(String snsAddYn) {
        this.snsAddYn = snsAddYn;
    }
    public String getEncUserId() {
        return encUserId;
    }
    public void setEncUserId(String encUserId) {
        this.encUserId = encUserId;
    }
    public String getClausePriAdFlag() {
        return clausePriAdFlag;
    }
    public void setClausePriAdFlag(String clausePriAdFlag) {
        this.clausePriAdFlag = clausePriAdFlag;
    }
    public long getNiceHistSeq() {
        return niceHistSeq;
    }
    public void setNiceHistSeq(long niceHistSeq) {
        this.niceHistSeq = niceHistSeq;
    }
    public long getNiceAgentHistSeq() {
        return niceAgentHistSeq;
    }
    public void setNiceAgentHistSeq(long niceAgentHistSeq) {
        this.niceAgentHistSeq = niceAgentHistSeq;
    }
    public String getMinorAgentRelation() {
        return minorAgentRelation;
    }
    public void setMinorAgentRelation(String minorAgentRelation) {
        this.minorAgentRelation = minorAgentRelation;
    }

    public void setPersonalInfoCollectAgree(String personalInfoCollectAgree) {
        this.personalInfoCollectAgree = personalInfoCollectAgree;
    }

    public String getPersonalInfoCollectAgree() {
        return personalInfoCollectAgree;
    }

    public void setOthersTrnsAgree(String othersTrnsAgree) {
        this.othersTrnsAgree = othersTrnsAgree;
    }

    public String getOthersTrnsAgree() {
        return othersTrnsAgree;
    }
    public String getChkInterest() {
        return chkInterest;
    }
    public void setChkInterest(String chkInterest) {
        this.chkInterest = chkInterest;
    }
    public String getAuthTelCode() {
        return authTelCode;
    }
    public void setAuthTelCode(String authTelCode) {
        this.authTelCode = authTelCode;
    }

    public String getMinorAgentAgree() {
        return minorAgentAgree;
    }

    public void setMinorAgentAgree(String minorAgentAgree) {
        this.minorAgentAgree = minorAgentAgree;
    }

    public String getMinorAgentName() {
        return minorAgentName;
    }

    public void setMinorAgentName(String minorAgentName) {
        this.minorAgentName = minorAgentName;
    }

    public String getMtkAgrReferer() {
        return mtkAgrReferer;
    }

    public void setMtkAgrReferer(String mtkAgrReferer) {
        this.mtkAgrReferer = mtkAgrReferer;
    }
}
