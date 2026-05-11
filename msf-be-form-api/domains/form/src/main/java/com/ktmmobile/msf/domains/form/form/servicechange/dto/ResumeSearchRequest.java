package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import jakarta.validation.constraints.NotNull;
@Getter
@Setter
@NoArgsConstructor
public class ResumeSearchRequest {
    @NotNull
    private String ncn; // 계약번호
    @NotNull
    private String ctn; // 회선번호
    @NotNull
    private String custId; // 고객ID
}
