package com.ktmmobile.msf.domains.form.form.servicechange.dto;

import jakarta.validation.constraints.NotNull;

public record ResumeSearchRequest(
    @NotNull
    String ncn, // 계약번호
    @NotNull
    String ctn, // 회선번호
    @NotNull
    String custId // 고객ID
) {
}
