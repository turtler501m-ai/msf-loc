package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Data;

/**
 * 일시정지 해지신청
 */
@Data
public class UnpauseProcessRequest {

    private String parentScanId;

    private String custId; // 고객번호
    private String ncn; // 사용자 서비스계약번호
    private String ctn; // 사용자 전화번호 (암호화)
    private String clntIp; // Client IP
    private String clntUsrId; // 사용자 User ID

    /* 일시정지 시 암호 세팅한 경우 필수
       PP: 일시정지, CP: 개인정보 암호 */
    private String pwdType = "PP"; // 비밀번호 타입
    /* 일시정지 시 암호 세팅한 경우 필수
       4-8자리 숫자 */
    private String strPwdNumInsert; // 일시정지 비밀번호
    private String strPwdInsert; // 일시정지 비밀번호

}
