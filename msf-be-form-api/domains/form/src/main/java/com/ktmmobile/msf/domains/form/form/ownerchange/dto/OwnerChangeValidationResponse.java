package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerChangeValidationResponse {

    private String resultCd;
    private String message;
    private OwnerChangeJoinInfoResponse response;
}
