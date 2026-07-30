package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import lombok.With;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthCustomerType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthFormType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityForm;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthJoinType;

public record FaceAuthResultRequest(
    FaceAuthFormType formType,
    FaceAuthJoinType joinType,
    FaceAuthCustomerType customerType,
    String agentCode,
    String path,
    String formId,
    String resNo,
    FaceAuthIdentityForm identityForm,
    @With String ktAgentCode,

    // 개발서버에서 강제 msf_fath_reslt_push 테이블에 데이터를 저장하기 위한 파라메터
    FaceAuthIdentityType identityType,
    String customerName,
    String customerNo,
    String customerDriveNo,
    String customerIssueDate
) {
}
