package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class McpPrepaidDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int tesSeq;
    private String reqType;
    private String contractNum;
    private String rechargeAgent;
    private int recharge;
    private String retCode;
    private String retMsg;
    private int rcgSeq;
    private int oAmount;
    private String oTesChargeMax;
    private String oTesBaser;
    private String oTesChgr;
    private String oTesMagicr;
    private String oTesFsmsr;
    private String oTesVideor;
    private String oTesIpvasr;
    private String oTesIpmaxr;
    private String oTesSmsm;
    private String oTesDataplusv;
    private String rechargeIp;
    private String reqDate;
    private String adminId;

}
