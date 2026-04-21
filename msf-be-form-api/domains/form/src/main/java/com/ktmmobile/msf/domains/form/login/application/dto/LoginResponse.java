package com.ktmmobile.msf.domains.form.login.application.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String userId;
    private String userNm;
    private String agentCd;
    private String shopCd;
    private String mobileNo;
    private String ip;
    private String userSttusCd;
    private String accessLimitYn;
    private Integer loginChkCnt;
    private String pwd;
}
