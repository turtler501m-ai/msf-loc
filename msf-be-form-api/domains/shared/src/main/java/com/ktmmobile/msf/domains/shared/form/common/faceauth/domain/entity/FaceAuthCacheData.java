package com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity;

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
public class FaceAuthCacheData {

    private String transactionId;
    private String url;
    private String resultCode;
    private String resultMessage;
    private String resNo;
    private String formId;
    private String requestDate;
}
