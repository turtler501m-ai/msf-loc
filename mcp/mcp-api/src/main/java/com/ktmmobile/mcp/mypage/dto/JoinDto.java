package com.ktmmobile.mcp.mypage.dto;

import java.io.Serializable;

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
    
    private String snsAddYn;
    private String snsCd;
    private String snsId;
    private String diVal;
    private String encUserId;
    private String clausePriAdFlag;

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
	
    

}
