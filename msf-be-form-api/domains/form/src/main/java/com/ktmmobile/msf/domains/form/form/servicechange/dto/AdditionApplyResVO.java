package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionApplyResVO {

    private String soc;

    public static AdditionApplyResVO of(String soc) {
        AdditionApplyResVO response = new AdditionApplyResVO();
        response.setSoc(soc);
        return response;
    }
}
