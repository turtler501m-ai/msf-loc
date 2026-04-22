package com.ktmmobile.msf.domains.form.form.common.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PhoneSerialCondition {
    @NotBlank
    private String orgnId;
    private String prodId;
    private String prodSn;
}
