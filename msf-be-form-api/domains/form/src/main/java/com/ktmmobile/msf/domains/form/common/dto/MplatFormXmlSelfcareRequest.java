package com.ktmmobile.msf.domains.form.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MplatFormXmlSelfcareRequest {

    private String ncn;
    private String custId;
    private String ctn;
    private String appAgncCd;
    private String appNstepUserId;
    private String appEntrPrsnId;
}
