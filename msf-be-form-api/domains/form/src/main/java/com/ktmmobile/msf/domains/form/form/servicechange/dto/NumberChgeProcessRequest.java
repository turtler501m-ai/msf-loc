package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Data;

/**
 * 번호 변경
 */
@Data
public class NumberChgeProcessRequest {

    private String parentScanId;

    private String custId; // 고객번호
    private String ncn; // 사용자 서비스계약번호
    private String ctn; // 사용자 전화번호 (암호화)
    private String clntIp; // Client IP
    private String clntUsrId; // 사용자 User ID

    private String resvHkCtn;   // 채번한 번호
    private String resvHkSCtn;  // 채번한 번호 암호화
    private String resvHkMarketGubun; // 채번한 번호 KT/KTF 구분

}
