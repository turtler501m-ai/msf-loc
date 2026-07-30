package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class EformParameter {

    private String name;
    private String value;
}
