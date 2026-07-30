package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity;

import java.time.LocalDateTime;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class MsfFathSelfUrl {
    private String fathKey;
    private String resNo;
    private String urlAdr;
    private LocalDateTime expireDt;
    private String requestKey;
}
