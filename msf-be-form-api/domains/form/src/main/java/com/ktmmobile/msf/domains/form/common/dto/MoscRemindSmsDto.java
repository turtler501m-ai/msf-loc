package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 제휴상품 리마인드 SMS 수신 상태 조회 및 변경 (Y42)
 */
@Getter
@Setter
@NoArgsConstructor
public class MoscRemindSmsDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String custId;       // 고객번호
    private String ncn;          // 사용자 서비스계약번호
    private String ctn;          // 사용자 전화번호
    private String prodGubun;    // 조회할 상품 구분값 MI:밀리의서재, CU:씨유
    private String wrkjobCd;     // R:조회, U:변경
    private String smsRcvBlckYn; // Y:차단, N:수신

}
