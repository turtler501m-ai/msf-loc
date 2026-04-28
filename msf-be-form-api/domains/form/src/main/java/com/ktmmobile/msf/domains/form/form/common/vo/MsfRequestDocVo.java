package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestDocVo {

    Long requestDocSeq;
    String formTypeCd;
    Long requestKey;
    String cretIp;
    String cretDt;
    String cretId;
    String amdIp;
    String amdDt;
    String amdId;
    String fileTypeCd;
    String filePathNm;
    String fileNm;

}
