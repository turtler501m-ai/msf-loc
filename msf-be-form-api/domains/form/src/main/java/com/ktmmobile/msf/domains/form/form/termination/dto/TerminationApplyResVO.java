package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TerminationApplyResVO {

    private String requestKey;

    public static TerminationApplyResVO ok(String requestKey) {
        TerminationApplyResVO vo = new TerminationApplyResVO();
        vo.requestKey = requestKey;
        return vo;
    }
}
