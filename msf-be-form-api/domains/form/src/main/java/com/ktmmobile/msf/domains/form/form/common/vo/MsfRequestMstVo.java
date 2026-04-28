package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestMstVo {

    Long requestKey;
    String formTypeCd;
    String cretIp;
    String cretDt;
    String cretId;
    String reqTypeCd;
    String userId;
    String cstmrNm;
    String mobileNo;
    String cstmrNativeRrn;
    String contractNum;
    String cstmrTypeCd;
    String onlineAuthTypeCd;
    String onlineAuthInfo;
    String etcMobileNo;

}
