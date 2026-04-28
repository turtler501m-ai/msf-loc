package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpCancelRequestVo {

    Long custReqSeq;
    String rip;
    String rvisnDttm;
    String rvisnId;
    String cancelMobileNo;
    String contractNum;
    String onlineAuthType;
    String onlineAuthInfo;
    String cstmrName;
    String receiveMobileNo;
    String benefitAgreeFlag;
    String clauseCntrDelFlag;
    String etcAgreeFlag;
    String survey1Cd;
    String survey1Text;
    String survey2Cd;
    String survey2Text;
    String surveyScore;
    String surveySuggestion;
    String memo;
    String sysRdateDd;
    String regstId;
    String regstDttm;
    String procCd;
    String scanId;
}
