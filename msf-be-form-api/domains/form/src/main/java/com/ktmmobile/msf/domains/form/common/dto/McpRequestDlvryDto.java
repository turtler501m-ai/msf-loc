package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestDlvryDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private long requestKey;    // 가입신청_키
    private String dlvryName;   // 배송정보_이름
    private String dlvryTelFn;  // 배송정보_전화번호_앞자리
    private String dlvryTelMn;  // 배송정보_전화번호_중간자리
    private String dlvryTelRn;  // 배송정보_전화번호_뒷자리
    private String dlvryMobileFn;   // 배송정보_휴대폰번호_앞자리
    private String dlvryMobileMn;   // 배송정보_휴대폰번호_중간자리
    private String dlvryMobileRn;   // 배송정보_휴대폰번호_뒷자리
    private String dlvryPost;       // 배송정보_우편번호
    private String dlvryAddr;       // 배송정보_주소
    private String dlvryAddrDtl;    // 배송정보_상세주소
    private String dlvryAddrBjd;    // 배송정보_법정동주소
    private String dlvryMemo;       // 배송정보_요청사항
    private Date sysRdate;          // 등록일시
    private String tbCd;

}
