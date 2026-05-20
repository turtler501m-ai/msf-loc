package com.ktmmobile.msf.domains.form.common.mspservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Date;

/**
 * @Class Name : CmnGrpCdMst
 * @Description : MSP CMN_GRP_CD_MST 테이블과 대응되는 DTO
 * MSP 코드 테이블정보이다.
 *
 * @author : ant
 * @Create Date : 2016. 1. 25.
 */
@Getter
@Setter
@NoArgsConstructor
public class CmnGrpCdMst implements Serializable {
    private static final long serialVersionUID = 1L;

    private String grpId;
    private String cdVal;
    private String cdDsc;
    private String usgYn;
    private String regstId;
    private Date regstDttm;
    private String rvisnId;
    private Date rvisnDttm;
    private String etc1;
    private String etc2;
    private String etc3;
    private String etc4;
    private String etc5;
    private String etc6;
}
