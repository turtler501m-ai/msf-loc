package com.ktmmobile.msf.domains.form.form.newchange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NewChangeNPInfoResponse {

    //NP1 InDto
    private String npTlphNo; //번호이동 전화번호
    private String bchngNpCommCmpnCd; //변경전번호이동사업자코드
    private String slsCmpnCd; //판매회사코드
    private String custTypeCd; //고객유형코드
    private String indvBizrYn; //개인사업자 여부
    private String custIdntNoIndCd; //고객식별번호구분코드
    private String custIdntNo; //고객식별번호
    private String crprNo; //법인번호
    private String custNm; //고객명
    private String fornBrthDate; //외국인생년월일

    //NP3 InDto
    private String telNo; //전화번호
    //private String bchngNpCommCmpnCd; //변경전번호이동사업자코드
}
