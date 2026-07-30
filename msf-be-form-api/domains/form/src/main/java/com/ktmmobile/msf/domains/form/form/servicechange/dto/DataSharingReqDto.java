package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataSharingReqDto {
    private String custId;
    private String ncn;
    private String contractNum;
    private String ctn;
    private String subStatus;
    private String opmdSvcNo;
    private String opmdWorkDivCd;
}
