package com.ktmmobile.msf.common.dto;

import java.io.Serializable;


/**
 * @Class Name : LoginHistoryDto
 * @Description : 사용자 로그인 정보를 NMCP_LOGIN_HISTORY 테이블로 저장하는 Dto
 *
 * @author : ant
 * @Create Date : 2016. 3. 28
 */
public class LoginHistoryDto implements Serializable  {

    private static final long serialVersionUID = 1L;


    private String userid;//아이디
    private String intype;//인입경로 P:PC, M:Mobile, A:Android, I:IOS
    private String name;//이름
    private String phone;//전화번호
    private String regdate;//생성일

    /**
     * @return the userid
     */
    public String getUserid() {
        return userid;
    }
    /**
     * @param userid the userid to set
     */
    public void setUserid(String userid) {
        this.userid = userid;
    }
    /**
     * @return the intype
     */
    public String getIntype() {
        return intype;
    }
    /**
     * @param intype the intype to set
     */
    public void setIntype(String intype) {
        this.intype = intype;
    }
    /**
     * @return the name
     */
    public String getName() {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }
    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }
    /**
     * @return the regdate
     */
    public String getRegdate() {
        return regdate;
    }
    /**
     * @param regdate the regdate to set
     */
    public void setRegdate(String regdate) {
        this.regdate = regdate;
    }


}
