package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RoleMenuDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String menuSeq;
    private String menuCode;
    private String menuNm;
    private String groupKey;
    private String prntsKey;
    private String sortKey;
    private String depthKey;
    private String urlAdr;
    private String statVal;
    private String cretId;
    private String amdId;
    private String cretDt;
    private String amdDt;
}
