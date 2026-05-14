package com.ktmmobile.msf.domains.form.extra.receipt.domain.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = lombok.AccessLevel.PRIVATE)
@NoArgsConstructor
public class ReceiptPage {

    private Long requestKey;
    private LocalDateTime cretDt;
    private String formTypeCd;
    private String reqBuyTypeCd;
    private String operTypeCd;
    private String procCd;
    private String cstmrNm;
    private String cstmrNativeBirth;
    private String cstmrTypeCd;
    private String identityCertTypeCd;
    private String agentCd;
    private String agentNm;
    private String shopCd;
    private String shopNm;
    private String cretId;
}
