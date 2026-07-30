package com.ktmmobile.msf.domains.form.form.ownerchange.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OwnerChangeSaveResponse {

    private boolean success;
    private Long requestKey;
    private String xml;
}
