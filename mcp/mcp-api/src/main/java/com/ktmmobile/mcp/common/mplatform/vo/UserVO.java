package com.ktmmobile.mcp.common.mplatform.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @project : default
 * @file 	: UserVO.java
 * @author	: HanNamSik
 * @date	: 2013. 3. 27. 오후 3:29:59
 * @history	:
 *
 * @comment :
 *
 *
 *
 */
public class UserVO /*extends CommonVO*/{

	private String userid;
	private String password;
	private String passwordtmp;
	private String name;
	private String authCode;
	private String pin;
	private String email;
	private String email1;
	private String email2;
	private String post;
	private String post1;
	private String post2;
	private String address1;
	private String address2;
	private String phone1;
	private String phone2;
	private String phone3;
	private String mobileNo;
	private String contractNo;
	private String birthday;
	private String gender;
	private String sysRdate;
	private String sysUdate;
	private String sysLdate;
	private Integer loginCount;
	private String status;
	private String juminNo;
	private Integer gsUserGrpKey;
	private String gsMemberFlag;
	private String age;
	private String mailFlag;
	private Integer no;
	private Integer buyCount;
	private String leaveFlag;
	private String leaveDate;
	private String leaveReason;
	private String agreementYn;
	private String token;
	private String mailFoot1;
	private String userDivision;
	private String cntrNo;
	private String uName;
	private String secedeReason;
	private String secedeReasonDesc;
	private String recDesc;
	private String isIdCheck;
	private Integer menuKey;
	private String smsRcvYn;
	private String emailRcvYn;
	private String repNo;
	private String modelName;
	private String smsRcvHist;
	private String emailRcvHist;
	private String contractNum;

	public String getContractNum() {
		return contractNum;
	}

	public void setContractNum(String contractNum) {
		this.contractNum = contractNum;
	}

	public String getSmsRcvHist() {
		return smsRcvHist;
	}

	public void setSmsRcvHist(String smsRcvHist) {
		this.smsRcvHist = smsRcvHist;
	}

	public String getEmailRcvHist() {
		return emailRcvHist;
	}

	public void setEmailRcvHist(String emailRcvHist) {
		this.emailRcvHist = emailRcvHist;
	}

	/**
	 *
	 * @return the userId
	 */
	public String getUserid() {
		return userid;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserid(String userid) {
		this.userid = userid;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the authCode
	 */
	public String getAuthCode() {
		return authCode;
	}

	/**
	 * @return the pin
	 */
	public String getPin() {
		return pin;
	}

	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @return the address1
	 */
	public String getAddress1() {
		return address1;
	}

	/**
	 * @return the address2
	 */
	public String getAddress2() {
		return address2;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @return the birthday
	 */
	public String getBirthday() {
		return birthday;
	}

	/**
	 * @return the gender
	 */
	public String getGender() {
		return gender;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param authCode the authCode to set
	 */
	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	/**
	 * @param pin the pin to set
	 */
	public void setPin(String pin) {
		this.pin = pin;
	}

	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @param address1 the address1 to set
	 */
	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	/**
	 * @param address2 the address2 to set
	 */
	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @param birthday the birthday to set
	 */
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	/**
	 * @param gender the gender to set
	 */
	public void setGender(String gender) {
		this.gender = gender;
	}

	/**
	 * @return the sysRdate
	 */
	public String getSysRdate() {
		return sysRdate;
	}

	/**
	 * @return the sysUdate
	 */
	public String getSysUdate() {
		return sysUdate;
	}

	/**
	 * @return the sysLdate
	 */
	public String getSysLdate() {
		return sysLdate;
	}

	/**
	 * @return the loginCount
	 */
	public Integer getLoginCount() {
		return loginCount;
	}

	/**
	 * @param sysRdate the sysRdate to set
	 */
	public void setSysRdate(String sysRdate) {
		this.sysRdate = sysRdate;
	}

	/**
	 * @param sysUdate the sysUdate to set
	 */
	public void setSysUdate(String sysUdate) {
		this.sysUdate = sysUdate;
	}

	/**
	 * @param sysLdate the sysLdate to set
	 */
	public void setSysLdate(String sysLdate) {
		this.sysLdate = sysLdate;
	}

	/**
	 * @param loginCount the loginCount to set
	 */
	public void setLoginCount(Integer loginCount) {
		this.loginCount = loginCount;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	public String getJuminNo() {
		return juminNo;
	}

	public void setJuminNo(String juminNo) {
		this.juminNo = juminNo;
	}

	/**
	 * @return the contractNo
	 */
	public String getContractNo() {
		return contractNo;
	}

	/**
	 * @param contractNo the contractNo to set
	 */
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	/**
	 * @return the gsUserGrpKey
	 */
	public Integer getGsUserGrpKey() {
		return gsUserGrpKey;
	}

	/**
	 * @param gsUserGrpKey the gsUserGrpKey to set
	 */
	public void setGsUserGrpKey(Integer gsUserGrpKey) {
		this.gsUserGrpKey = gsUserGrpKey;
	}

	/**
	 * @return the gsMemberFlag
	 */
	public String getGsMemberFlag() {
		return gsMemberFlag;
	}

	/**
	 * @param gsMemberFlag the gsMemberFlag to set
	 */
	public void setGsMemberFlag(String gsMemberFlag) {
		this.gsMemberFlag = gsMemberFlag;
	}

	/**
	 * @return the age
	 */
	public String getAge() {
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public void setAge(String age) {
		this.age = age;
	}

	/**
	 * @return the mailFlag
	 */
	public String getMailFlag() {
		return mailFlag;
	}

	/**
	 * @param mailFlag the mailFlag to set
	 */
	public void setMailFlag(String mailFlag) {
		this.mailFlag = mailFlag;
	}

	/**
	 * @return the no
	 */
	public Integer getNo() {
		return no;
	}

	/**
	 * @param no the no to set
	 */
	public void setNo(Integer no) {
		this.no = no;
	}
	/**
	 * @return the buyCount
	 */
	public Integer getBuyCount() {
		return buyCount;
	}

	/**
	 * @param buyCount the buyCount to set
	 */
	public void setBuyCount(Integer buyCount) {
		this.buyCount = buyCount;
	}
	/**
	 * @return the leaveFlag
	 */
	public String getLeaveFlag() {
		return leaveFlag;
	}

	/**
	 * @param leaveFlag the leaveFlag to set
	 */
	public void setLeaveFlag(String leaveFlag) {
		this.leaveFlag = leaveFlag;
	}

	/**
	 * @return the leaveDate
	 */
	public String getLeaveDate() {
		return leaveDate;
	}

	/**
	 * @param leaveDate the leaveDate to set
	 */
	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	/**
	 * @return the leaveReason
	 */
	public String getLeaveReason() {
		return leaveReason;
	}

	/**
	 * @param leaveReason the leaveReason to set
	 */
	public void setLeaveReason(String leaveReason) {
		this.leaveReason = leaveReason;
	}

	/**
	 * @return the post
	 */
	public String getPost() {
		return post;
	}

	/**
	 * @param post the post to set
	 */
	public void setPost(String post) {
		this.post = post;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getPost1() {
		return post1;
	}

	public void setPost1(String post1) {
		this.post1 = post1;
	}

	public String getPost2() {
		return post2;
	}

	public void setPost2(String post2) {
		this.post2 = post2;
	}

	public String getPhone1() {
		return phone1;
	}

	public void setPhone1(String phone1) {
		this.phone1 = phone1;
	}

	public String getPhone2() {
		return phone2;
	}

	public void setPhone2(String phone2) {
		this.phone2 = phone2;
	}

	public String getPhone3() {
		return phone3;
	}

	public void setPhone3(String phone3) {
		this.phone3 = phone3;
	}

	public String getEmail1() {
		return email1;
	}

	public void setEmail1(String email1) {
		this.email1 = email1;
	}

	public String getEmail2() {
		return email2;
	}

	public void setEmail2(String email2) {
		this.email2 = email2;
	}

	public String getAgreementYn() {
		return agreementYn;
	}

	public void setAgreementYn(String agreementYn) {
		this.agreementYn = agreementYn;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getMailFoot1() {
		return mailFoot1;
	}

	public void setMailFoot1(String mailFoot1) {
		this.mailFoot1 = mailFoot1;
	}

	public String getUserDivision() {
		return userDivision;
	}

	public void setUserDivision(String userDivision) {
		this.userDivision = userDivision;
	}

	public String getCntrNo() {
		return cntrNo;
	}

	public void setCntrNo(String cntrNo) {
		this.cntrNo = cntrNo;
	}

	public String getuName() {
		return uName;
	}

	public void setuName(String uName) {
		this.uName = uName;
	}

	public String getPasswordtmp() {
		return passwordtmp;
	}

	public void setPasswordtmp(String passwordtmp) {
		this.passwordtmp = passwordtmp;
	}

	public String getSecedeReason() {
		return secedeReason;
	}

	public void setSecedeReason(String secedeReason) {
		this.secedeReason = secedeReason;
	}

	public String getSecedeReasonDesc() {
		return secedeReasonDesc;
	}

	public void setSecedeReasonDesc(String secedeReasonDesc) {
		this.secedeReasonDesc = secedeReasonDesc;
	}

	public String getRecDesc() {
		return recDesc;
	}

	public void setRecDesc(String recDesc) {
		this.recDesc = recDesc;
	}

	public String getIsIdCheck() {
		return isIdCheck;
	}

	public void setIsIdCheck(String isIdCheck) {
		this.isIdCheck = isIdCheck;
	}

	public Integer getMenuKey() {
		return menuKey;
	}

	public void setMenuKey(Integer menuKey) {
		this.menuKey = menuKey;
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

	public String getRepNo() {
		return repNo;
	}

	public void setRepNo(String repNo) {
		this.repNo = repNo;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}


}
