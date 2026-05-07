package com.ktmmobile.msf.domains.form.form.termination.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TerminationApplyResVO {

    private String applicationNo;

    public static TerminationApplyResVO ok(String applicationNo) {
        TerminationApplyResVO vo = new TerminationApplyResVO();
        vo.applicationNo = applicationNo;
        return vo;
    }
}
