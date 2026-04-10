package com.ktmmobile.mcp.common.dto;

import java.io.Serializable;


public class AuthSmsDto  implements Serializable  {
    private static final long serialVersionUID = 1L;


    private String phoneNum;
    private String authNum;
    private String startDate;
    private String endDate;
    private String message;
    private String smsNo;
    private boolean result = false;
    private boolean check = false;
    private boolean delete = false;
    private String menu;
    private String sendTime;
    private int duration;
    private String memberName;
    private String rateCd;
    private String rateNm;

    private String limitMin;
    private String isReal; // loacal,dev,stg : N, prd : Y
    /**
     * @return the phoneNum
     */
    public String getPhoneNum() {
        return phoneNum;
    }
    /**
     * @param phoneNum the phoneNum to set
     */
    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }
    /**
     * @return the authNum
     */
    public String getAuthNum() {
        return authNum;
    }
    /**
     * @param authNum the authNum to set
     */
    public void setAuthNum(String authNum) {
        this.authNum = authNum;
    }
    /**
     * @return the startDate
     */
    public String getStartDate() {
        return startDate;
    }
    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    /**
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    /**
     * @return the result
     */
    public boolean isResult() {
        return result;
    }
    /**
     * @param result the result to set
     */
    public void setResult(boolean result) {
        this.result = result;
    }
    /**
     * @return the check
     */
    public boolean isCheck() {
        return check;
    }
    /**
     * @param check the check to set
     */
    public void setCheck(boolean check) {
        this.check = check;
    }
    /**
     * @return the menu
     */
    public String getMenu() {
        return menu;
    }
    /**
     * @param menu the menu to set
     */
    public void setMenu(String menu) {
        this.menu = menu;
    }
    public String getSmsNo() {
        return smsNo;
    }
    public void setSmsNo(String smsNo) {
        this.smsNo = smsNo;
    }
    public String getSendTime() {
        return sendTime;
    }
    public void setSendTime(String sendTime) {
        this.sendTime = sendTime;
    }
    public int getDuration() {
        return duration;
    }
    public void setDuration(int duration) {
        this.duration = duration;
    }
    public String getMemberName() {
        return memberName;
    }
    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    /**
     * @return the delete
     */
    public boolean isDelete() {
        return delete;
    }
    /**
     * @param delete the delete to set
     */
    public void setDelete(boolean delete) {
        this.delete = delete;
    }
    public String getRateCd() {
        return rateCd;
    }
    public void setRateCd(String rateCd) {
        this.rateCd = rateCd;
    }
    public String getRateNm() {
        return rateNm;
    }
    public void setRateNm(String rateNm) {
        this.rateNm = rateNm;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setLimitMin(String limitMin) {
        this.limitMin = limitMin;
    }

    public String getLimitMin() {
        return limitMin;
    }
    public String getIsReal() {
        return isReal;
    }
    public void setIsReal(String isReal) {
        this.isReal = isReal;
    }
}
