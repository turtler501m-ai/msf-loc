package com.ktmmobile.msf.domains.form.form.common.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MsfRequestOsstVo {

    String mvnoOrdNo;
    Long seq;
    String osstOrdNo;
    String prgrStatCd;
    String custId;
    String svcCntrNo;
    String rsltCd;
    String rsltMsg;
    String rsltDate;
    String nstepGlobalId;
    String prdcChkNotiMsg;
    String npBcntrTypeCd;
    Long npFee = 0L;
    Long npNchrgAmt = 0L;
    Long npPnltAmt = 0L;
    Long npUnpayAmt = 0L;
    Long npHndstInstAmt = 0L;
    Long npPrepayAmt = 0L;
    Long npBaseChrgAmt = 0L;
    Long npNtnlChrgAmt = 0L;
    Long npIntlChrgAmt = 0L;
    Long npAddChrgAmt = 0L;
    Long npEtcChrgAmt = 0L;
    Long npVat = 0L;
    String npRmnStrtDate;
    String npRmnEndDate;
    String tlphNoStatCd;
    String asgnAgncId;
    String tlphNoOwnCmpnCd;
    String openSvcIndCd;
    String encdTlphNo;
    String tlphNo;
    String ifTypeCd;
    String regDt;
    String dclaDeedEftDate;
    String sbscLmtQnty;
    String sbscCircuitQnty;
    Long dlnqAmt;
    String eiccId;
}
