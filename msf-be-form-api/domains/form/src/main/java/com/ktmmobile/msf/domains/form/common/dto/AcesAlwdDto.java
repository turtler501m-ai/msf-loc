package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AcesAlwdDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String acesAlwdIp;
    private String nm;
    private String mobileNo;

}
