package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import lombok.With;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthCustomerType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthFormType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityForm;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthJoinType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthVisitType;

public record FaceAuthSendRequest(
    FaceAuthFormType formType,
    FaceAuthJoinType joinType,
    FaceAuthIdentityForm identityForm,
    FaceAuthIdentityType identityType,
    FaceAuthCustomerType customerType,
    FaceAuthVisitType visitType,
    String resNo,
    String agentCode,
    String path,
    String formId,
    String phone,
    String bizNumber,
    String minorAgentName,
    String minorAgentBirthday,
    @With String ktAgentCode
) {
}
