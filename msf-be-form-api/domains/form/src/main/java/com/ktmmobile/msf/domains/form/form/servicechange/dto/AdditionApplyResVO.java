package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AdditionApplyResVO {

    private String soc;
    private String mtProdHstSeq; // 대표상품 일련번호 (로밍 서브 신청 시)
    private String mtNcn;        // 대표 계약번호 (로밍 서브 신청 시)

    public static AdditionApplyResVO of(String soc) {
        AdditionApplyResVO response = new AdditionApplyResVO();
        response.setSoc(soc);
        return response;
    }
}
