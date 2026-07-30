package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 대리점 조회 Response
 */
@Getter
@Setter
@NoArgsConstructor
public class AgentInfoResponse {

    private String ktOrgId;
    private String orgnId;
    private String orgnNm;
    private String shopOrgnId;
    private String shopNm;
    private String realShopNm;
    private String managerNm;
    private String telephone;
    private String representativeTelephone;

    private String canBulkCorporateOpenYn; //대량 개통 가능여부
}
