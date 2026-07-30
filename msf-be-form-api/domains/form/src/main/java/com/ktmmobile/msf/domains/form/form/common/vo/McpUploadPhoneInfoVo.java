package com.ktmmobile.msf.domains.form.form.common.vo;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpUploadPhoneInfoVo {

    private String uploadPhoneSrlNo;
    private String reqModelNm;
    private String reqModelName;
    private String modelId;
    private String reqPhoneSn;
    private String eid;
    private String imei1;
    private String imei2;
    private String uploadPhoneImg;
    private String accessIp;
    private String userId;
    private String cretDt;
    private String sysDt;
    private Timestamp sysRdate;
    private String moveTlcmIndCd;
    private String moveCmncGnrtIndCd;
    private String rstCd;
    private String evntCd;
    private String rsltCd;
    private String rsltYn;
    private String rsltMsg;
    private String prntsContractNo;


}
