package com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CompletedRequestJoinForm {

    private Long requestRecvSeq;
    private Long requestKey;
    private String recvTypeCd;
    private String faxNo;
    private String cstmrZipcd;
    private String cstmrAdr;
    private String cstmrAdrDtl;
    private String cstmrEmailAdr;
    private String cstmrMobileNo;
    private String procCd;
    private String etcMemo;

    private String joinCstmrMobileNo;
    private String joinProcCd;
}
