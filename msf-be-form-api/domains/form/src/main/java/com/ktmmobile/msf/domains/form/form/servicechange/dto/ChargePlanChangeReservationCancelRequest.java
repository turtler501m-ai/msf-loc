package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChargePlanChangeReservationCancelRequest {

    private String custId; // 고객번호
    private String ncn; // 사용자 서비스계약번호
    private String ctn; // 사용자 전화번호 (암호화)
    private String clntIp; // Client IP
    private String clntUsrId; // 사용자 User ID
}
