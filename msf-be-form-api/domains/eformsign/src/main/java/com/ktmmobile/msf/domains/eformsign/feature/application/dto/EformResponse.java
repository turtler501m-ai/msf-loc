package com.ktmmobile.msf.domains.eformsign.feature.application.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EformResponse {

    private List<EformParameter> formParameters;
}
