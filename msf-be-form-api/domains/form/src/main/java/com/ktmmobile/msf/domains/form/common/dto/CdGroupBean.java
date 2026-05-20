package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CdGroupBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cdGroupId;   // 코드그룹아이디
    private String cdGroupNm;   // 코드그룹이름
    private List<NmcpCdDtlDto> listCdBean; // 리스트 코드

}
