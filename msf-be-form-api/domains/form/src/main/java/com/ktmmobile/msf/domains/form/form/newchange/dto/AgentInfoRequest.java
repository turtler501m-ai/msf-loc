package com.ktmmobile.msf.domains.form.form.newchange.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AgentInfoRequest {
    @NotBlank
    private String shopOrgnId;
}
