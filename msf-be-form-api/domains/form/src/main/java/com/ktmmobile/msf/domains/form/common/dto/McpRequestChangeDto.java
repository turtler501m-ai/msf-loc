package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestChangeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String nameChangeNm;            // 명의변경_고객명
    private String nameChangeTelFn;         // 명의변경_전화번호_앞자리
    private String nameChangeTelMn;         // 명의변경_전화번호_중간자리
    private String nameChangeTelRn;         // 명의변경_전화번호_끝자리
    private String nameChangeRrn;           // 명의변경_주민등록번호
    private String nameChangePinstallment;  // 완납=P,승계=C
    private String requestKey;              // 가입신청_키
    private Date sysRdate;                  // 등록일시

}
