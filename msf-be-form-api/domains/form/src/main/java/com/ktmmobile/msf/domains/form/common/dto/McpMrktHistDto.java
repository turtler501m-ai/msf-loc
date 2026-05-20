package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpMrktHistDto implements Serializable {

    private String userid;
    private String gubun;
    private String strtDttm;
    private String endDttm;
    private String agrYn;
    private String regstId;
    private String regstDttm;
    private String rvisnId;
    private String rvisnDttm;
    private String newAgrYn;
    private String newStrtDttm;
    private String newEndDttm;
    private String mtkAgrReferer;

}
