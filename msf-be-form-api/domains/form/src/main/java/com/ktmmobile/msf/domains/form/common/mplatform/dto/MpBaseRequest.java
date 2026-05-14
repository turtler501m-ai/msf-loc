package com.ktmmobile.msf.domains.form.common.mplatform.dto;

import lombok.Data;

@Data
public abstract class MpBaseRequest implements MpBaseRequestSpec {

    private String appEventCd;
    private String ncn;
    private String ctn;
    private String custId;
    private String userid;
    private String ip;
    private String url;
    private String mdlInd;
}
