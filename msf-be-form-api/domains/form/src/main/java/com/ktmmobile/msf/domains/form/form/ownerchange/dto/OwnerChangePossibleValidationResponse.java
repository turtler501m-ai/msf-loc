package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

@Data
@JacksonXmlRootElement(localName = "outDto")
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
