package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import lombok.With;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthCustomerType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthFormType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthJoinType;

public record FaceAuthConfirmRequest(
    FaceAuthFormType formType,
    FaceAuthJoinType joinType,
    FaceAuthIdentityType identityType,
    FaceAuthCustomerType customerType,
    String agentCode,
    String path,
    String resNo,
    @With String ktAgentCode
) {
}
