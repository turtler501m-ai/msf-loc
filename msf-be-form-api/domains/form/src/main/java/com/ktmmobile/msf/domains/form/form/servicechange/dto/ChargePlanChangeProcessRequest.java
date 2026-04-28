package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Data;

@Data
public class ChargePlanChangeProcessRequest {

    private String custId; // 고객번호
    private String ncn; // 사용자 서비스계약번호
    private String ctn; // 사용자 전화번호 (암호화)
    private String clntIp; // Client IP
    private String clntUsrId; // 사용자 User ID
    private String soc; // 상품코드
    private String ftrNewParam; // 부가파람정보
}
