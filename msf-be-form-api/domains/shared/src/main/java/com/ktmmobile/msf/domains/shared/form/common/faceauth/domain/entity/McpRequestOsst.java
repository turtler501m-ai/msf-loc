package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity;

import java.time.LocalDateTime;

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
public class McpRequestOsst {
    private String mvnoOrdNo;
    private Integer seq;
    private String osstOrdNo;
    private String prgrStatCd;
    private String custId;
    private String svcCntrNo;
    private String rsltCd;
    private String rsltMsg;
    private String rsltDt;
    private String nstepGlobalId;
    private String prdcChkNotiMsg;
    private String npBcntrTypeCd;
    private Long npFee;
    private Long npNchrgAmt;
    private Long npPnltAmt;
    private Long npUnpayAmt;
    private Long npHndstInstAmt;
    private Long npPrepayAmt;
    private Long npBaseChrgAmt;
    private Long npNtnlChrgAmt;
    private Long npIntlChrgAmt;
    private Long npAddChrgAmt;
    private Long npEtcChrgAmt;
    private Long npVat;
    private String npRmnStrtDt;
    private String npRmnEndDt;
    private String asgnAgncId;
    private String tlphNoOwnCmpnCd;
    private String openSvcIndCd;
    private String encdTlphNo;
    private String tlphNo;
    private String ifType;
    private LocalDateTime regstDttm;
    private String dclaDeedEftDt;
    private String sbscLmtQnty;
    private String sbscCircuitNum;
    private String dlnqAmt;
    private String eiccId;
}
