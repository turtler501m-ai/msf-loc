package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestStateDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestStateKey;       // 가입진행_키
    private String resNo;               // 예약번호
    private long requestKey;            // 가입신청_키
    private String requestStateCode;    // 가입진행_코드
    private String dlvryNo;             // 송장번호
    private String openNo;              // 개통번호
    private String memo;                // 메모
    private Date sysRdate;              // 등록일시
    private String rip;                 // 등록자아이피
    private String rid;                 // 등록자아이디
    private String viewFlag;            // 화면표시_여부
    private String tbCd;

}
