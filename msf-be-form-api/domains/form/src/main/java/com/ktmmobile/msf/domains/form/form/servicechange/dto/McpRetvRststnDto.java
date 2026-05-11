package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class McpRetvRststnDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String svcCntrNo;//NCN
    private String connDate;//접속날짜
    private int tmscnt;//접속제한횟수
    private String code;//서비스코드
    private String amdDt;//수정일자

}