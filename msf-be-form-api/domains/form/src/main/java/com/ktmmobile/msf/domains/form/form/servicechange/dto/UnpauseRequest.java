package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 일시정지 해지신청
 */
@Getter
@Setter
@NoArgsConstructor
public class UnpauseRequest {


    private String custId; // 고객번호
    private String ncn; // 사용자 서비스계약번호
    private String ctn; // 사용자 전화번호 (암호화)
    private String clntIp; // Client IP
    private String clntUsrId; // 사용자 User ID


    // TEST 일시정지 신청 관련 변수들
    private String stopRsnCd;
    private String reasonCode;
    private String userMemo;
    private String cpDateYn;
    private String cpEndDt;
    private String cpStartDt;
    private String cpPwdInsert;

}
