package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestJoinFormVo {

    Long requestRecvSeq;
    String formTypeCd;
    Long requestKey;
    String cretIp;
    String cretDt;
    String cretId;
    String amdIp;
    String amdId;
    String amdDt;
    String recvTypeCd;
    String faxNo;
    String cstmrZipcd;
    String cstmrAdr;
    String cstmrAdrDtl;
    String cstmrEmailAdr;
    String cstmrMobileNo;
    String procCd;
    String etcMemo;

}
