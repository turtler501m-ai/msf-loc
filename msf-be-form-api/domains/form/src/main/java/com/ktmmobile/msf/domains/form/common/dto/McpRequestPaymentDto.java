package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestPaymentDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;        // 가입신청_키
    private long reqAc02Amount;
    private Date sysRdate;
    private String reqAcType;
    private long reqAc01Balance;
    private String reqAc02Day;
    private long reqAc01Amount;

}
