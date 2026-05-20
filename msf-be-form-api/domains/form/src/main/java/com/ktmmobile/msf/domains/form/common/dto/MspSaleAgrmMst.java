package com.ktmmobile.msf.domains.form.common.dto;

import java.io.Serializable;
import java.sql.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspSaleAgrmMst implements Serializable {

    private static final long serialVersionUID = 1L;

    private String salePlcyCd;     // 정책코드
    private String agrmTrm;        // 약정기간
    private String agrmTrmLabel;   // 약정기간 label
    private String instNom;        // 할부기간
    private String regstId;        // 등록자id
    private Date regstDttm;        // 등록일시
    private String rvisnId;        // 수정자id
    private Date rvisnDttm;        // 수정일시
    private String orgnId;         // 조직코드

    public String getAgrmTrmLabel() {
        if ("0".equals(agrmTrm)) {
            agrmTrmLabel = "무약정";
        } else {
            agrmTrmLabel = agrmTrm + "개월";
        }
        return agrmTrmLabel;
    }
}
