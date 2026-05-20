package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseSuccessDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Date requestTime;   // 요청한 시간
    private String successMsg;  // 성공 메세지
    private String redirectUrl; // redirect 할 URL
}
