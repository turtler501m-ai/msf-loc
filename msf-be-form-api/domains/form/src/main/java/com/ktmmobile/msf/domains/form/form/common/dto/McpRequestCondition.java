package com.ktmmobile.msf.domains.form.form.common.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class McpRequestCondition {
    private Long requestKey;
    private String reqWantNumber; //희망번호 입력 4자리
    //private String reqWantNumber2;
    //private String reqWantNumber3;
}
