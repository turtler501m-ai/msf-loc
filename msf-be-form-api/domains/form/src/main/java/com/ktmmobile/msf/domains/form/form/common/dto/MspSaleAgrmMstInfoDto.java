package com.ktmmobile.msf.domains.form.form.common.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MspSaleAgrmMstInfoDto {

    /** 정책코드 */
    private String salePlcyCd;

    /** 약정기간 */
    private String agrmTrm;

    /** 약정기간 label */
    private String agrmTrmLabel;

    /** 할부기간  */
    private String instNom;

    /** 등록자id */
    private String regstId;

    /** 등록일시  */
    private Date regstDttm;

    /** 수정자id */
    private String rvisnId;

    /** 수정일시  */
    private Date rvisnDttm;

    /** 조직코드 */
    private String orgnId;
}
