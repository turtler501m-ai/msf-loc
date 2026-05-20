package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MoscCombReqDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custId;
    private String ncn;
    private String ctn;
    private String cmbStndSvcNo;        // 결합서비스번호
    private String combSrchId;          // 결합 모회선 조회값
    private String svcIdfyNo;           // 서비스 확인 번호
    private String sexCd;               // 성별코드 (1:남성, 2:여성)
    private String combSvcNoCd;         // 결합대상 회선번호
    private String sameCustKtRetvYn;    // 동일명의로 가입된 KT 회선 정보 조회 여부 (Y:조회)

}
