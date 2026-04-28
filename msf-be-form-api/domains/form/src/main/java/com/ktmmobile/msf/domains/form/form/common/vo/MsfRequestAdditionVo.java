package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestAdditionVo {

    Long requestKey;
    String formTypeCd;
    String additionId; //MCP 키가 없는경우 부가서비스 아이디
    String cretIp;
    String cretDt;
    String cretId;
    String amdIp;
    String amdDt;
    String amdId;
    Long additionKey; //MCP 키가 있는경우 부가서비스 키
    String additionNm;
    Long rantal;
    String procYn;

}
