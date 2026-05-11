package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestDvcChgVo {

    Long requestKey;
    String cretIp;
    String cretDt;
    String cretId;
    String amdIp;
    String amdDt;
    String amdId;
    String dvcChgTypeCd;
    String dvcChgRsnCd;
    String dvcChgRsnDtlCd;
    String instamtPayMthdCd;

}
