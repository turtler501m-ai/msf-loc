package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestOsstRequest {
    private String mvnoOrdNo;
    private String prgrStatCd;
    private String rsltCd;

    //
    private long requestKey;
    private String reqWantNumber;

    private String managerCode;
    private String agentCode;
    private String serviceType;
    private String operType;
    //private String cstmrType;
    private String pstate;
    private String onOffType;

    //번호이동 사전동의 요청 MP호출
    private String eventCd;
    private String npTlphNo;
    private String moveCompany;
    private String cstmrType;
    private String selfCertType;
    private String custIdntNo;
    private String cstmrName;
    private String cntpntShopId;
}
