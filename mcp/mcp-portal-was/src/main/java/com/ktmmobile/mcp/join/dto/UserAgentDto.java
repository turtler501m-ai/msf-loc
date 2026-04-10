package com.ktmmobile.mcp.join.dto;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.ktmmobile.mcp.common.dto.NiceResDto;

public class UserAgentDto implements Serializable  {

    private static final long serialVersionUID = 1L;

    /** 회원아이디 */
    private String userid;
    /** 미성년자 법정대리인 성명 */
    private String minorAgentName;
    /** 미성년자 법정대리인 생년월일 */
    private String minorAgentRrn;
    /** 미성년자 법정대리인 관계 */
    private String minorAgentRelation;
    /** 미성년자 법정대리인 CI */
    private String minorAgentCi;
    /** 인증정보 */
    private String agentAuthInfo;
    /** 미성년자 법정대리인 개인정보 수집이용 동의여부 */
    private String minorAgentAgree;
    public String getUserid() {
        return userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getMinorAgentName() {
        return minorAgentName;
    }
    public void setMinorAgentName(String minorAgentName) {
        this.minorAgentName = minorAgentName;
    }
    public String getMinorAgentRrn() {
        return minorAgentRrn;
    }
    public void setMinorAgentRrn(String minorAgentRrn) {
        this.minorAgentRrn = minorAgentRrn;
    }
    public String getMinorAgentRelation() {
        return minorAgentRelation;
    }
    public void setMinorAgentRelation(String minorAgentRelation) {
        this.minorAgentRelation = minorAgentRelation;
    }
    public String getMinorAgentCi() {
        return minorAgentCi;
    }
    public void setMinorAgentCi(String minorAgentCi) {
        this.minorAgentCi = minorAgentCi;
    }
    public String getAgentAuthInfo() {
        return agentAuthInfo;
    }
    public void setAgentAuthInfo(String agentAuthInfo) {
        this.agentAuthInfo = agentAuthInfo;
    }
    public String getMinorAgentAgree() {
        return minorAgentAgree;
    }
    public void setMinorAgentAgree(String minorAgentAgree) {
        this.minorAgentAgree = minorAgentAgree;
    }
}
