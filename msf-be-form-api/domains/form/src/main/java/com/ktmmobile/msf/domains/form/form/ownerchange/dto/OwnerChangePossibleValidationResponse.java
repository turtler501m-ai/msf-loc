package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.Data;

@Data
@JsonRootName("outDto")
public class OwnerChangePossibleValidationResponse {

    private String resultCd;
    private String custTypeCd;
    private String custPtclTypeCd;
    private String custNm;
    private String bthdayDate;
    private String contPurpCd;
    private String intmModelId;
    private String intmSeq;
    private String intmModelNm;
    private String mngmAgncId;
    private String usimOpenYn;
}
