package com.ktmmobile.msf.domains.form.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MaskingDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long maskingReleaseSeq;
    private String userId;
    private String authType;
    private String ci;
    private Date unmaskingStratDt;
    private String cretDd;
    private String accessIp;
    private String cretId;
    private String amdId;
    private Date cretDt;
    private Date amdDt;
    private long seq;
    private String unmaskingInfo;
    private String accessUrl;
}
