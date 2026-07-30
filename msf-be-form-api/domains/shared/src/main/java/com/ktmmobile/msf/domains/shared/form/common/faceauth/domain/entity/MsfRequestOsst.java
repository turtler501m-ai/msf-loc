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
public class MsfRequestOsst {
    private String mvnoOrdNo;
    private Long seq;
    private String osstOrdNo;
    private String prgrStatCd;
    private String rsltCd;
    private String rsltMsg;
    private String rsltDate;
    private String nstepGlobalId;
    private String prdcChkNotiMsg;
    private String ifTypeCd;
    private LocalDateTime regDt;
}
