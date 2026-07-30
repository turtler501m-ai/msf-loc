package com.ktmmobile.msf.domains.shared.form.common.complete.domain.entity;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.ktmmobile.msf.domains.shared.form.common.complete.domain.code.RequestFormCstmrType;
import com.ktmmobile.msf.domains.shared.form.common.complete.domain.code.RequestFormType;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class CompletedRequestForm {

    private RequestFormType formType;
    private Long requestKey;
    private String cretId;
    private String scanId;
    private String baseScanId;
    private String procCd;
    private RequestFormCstmrType cstmrTypeCd;
    private String operTypeCd;
    private String cstmrVisitTypeCd;
    private String cstmrNm;
    private String cstmrNativeBirth;
    private String cstmrForeignerBirth;
    private String cstmrJuridicalBizNo;
    private String cstmrJuridicalRrn;
    private String mobileNo;
    private String cstmrMobileNo;
    private String cstmrReceiveTelNo;
    private String minorAgentNm;
    private String minorAgentTelNo;
    private String jrdclAgentNm;
    private String jrdclAgentTelNo;

    private List<CompletedRequestJoinForm> joinForms;
}
