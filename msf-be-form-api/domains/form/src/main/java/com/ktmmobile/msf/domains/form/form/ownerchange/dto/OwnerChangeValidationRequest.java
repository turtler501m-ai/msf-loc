package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Data;

@Data
public class OwnerChangeValidationRequest {

    private String clntIp;
    private String clntUsrId;
    private String ncn;
    private String ctn;
    private String custId;
}
