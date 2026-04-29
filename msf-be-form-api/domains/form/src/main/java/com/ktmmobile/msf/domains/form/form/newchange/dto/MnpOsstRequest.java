package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MnpOsstRequest {

    private String npTlphNo; // 번호이동 전화번호	11	M	번호이동할 전화번호

    private String bchngNpCommCmpnCd; // 변경전번호이동사업자코드	3	M	* 코드정의서 참조

    private String slsCmpnCd; // 판매회사코드	3	M

    private String custTypeCd; // 고객유형코드	2	M	* 코드정의서 참조

    private String indvBizrYn; // 개인사업자 여부	1	M	개인사업자인 경우 Y, defult N

    private String custIdntNoIndCd; // 고객식별번호구분코드	2	M	* 코드정의서 참조

    private String custIdntNo; // 고객식별번호	20	C

    private String custNm; // 고객명	60	M	법인인 경우 법인명
}
