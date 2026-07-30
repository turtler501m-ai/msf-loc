package com.ktmmobile.msf.domains.shared.form.common.faceauth.application.dto;

import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthCustomerType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthIdentityType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.code.FaceAuthJoinType;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.McpFathResultPush;
import com.ktmmobile.msf.domains.shared.form.common.faceauth.domain.entity.MsfFathResultPush;

public record FaceAuthResultResponse(
    String transactionId,
    String formId,
    String resNo,
    FaceAuthJoinType joinType,
    FaceAuthCustomerType customerType,
    String completeDate,
    FaceAuthIdentityType identityType,
    String customerName,
    String customerNo,
    String customerDriveNo,
    String customerIssueDate,
    String skipPosableYn,
    String receivedSmsTelNo,
    String resultTypeCode
) {

    public static FaceAuthResultResponse of(
        String transactionId,
        String formId,
        String resNo,
        FaceAuthJoinType joinType,
        FaceAuthCustomerType customerType
    ) {
        return FaceAuthResultResponse.of(
            transactionId,
            formId,
            resNo,
            joinType,
            customerType,
            (MsfFathResultPush) null,
            null
        );
    }

    public static FaceAuthResultResponse of(
        String transactionId,
        String formId,
        String resNo,
        FaceAuthJoinType joinType,
        FaceAuthCustomerType customerType,
        String decideCode
    ) {
        return FaceAuthResultResponse.of(
            transactionId,
            formId,
            resNo,
            joinType,
            customerType,
            (MsfFathResultPush) null,
            decideCode
        );
    }

    public static FaceAuthResultResponse of(
        String transactionId,
        String formId,
        String resNo,
        FaceAuthJoinType joinType,
        FaceAuthCustomerType customerType,
        McpFathResultPush push
    ) {
        return FaceAuthResultResponse.of(
            transactionId,
            formId,
            resNo,
            joinType,
            customerType,
            push,
            null
        );
    }

    public static FaceAuthResultResponse of(
        String transactionId,
        String formId,
        String resNo,
        FaceAuthJoinType joinType,
        FaceAuthCustomerType customerType,
        MsfFathResultPush push
    ) {
        return FaceAuthResultResponse.of(
            transactionId,
            formId,
            resNo,
            joinType,
            customerType,
            push,
            null
        );
    }

    public static FaceAuthResultResponse of(
        String transactionId,
        String formId,
        String resNo,
        FaceAuthJoinType joinType,
        FaceAuthCustomerType customerType,
        McpFathResultPush push,
        String decideCode
    ) {
        if (push == null) {
            return new FaceAuthResultResponse(
                transactionId,
                formId,
                resNo,
                joinType,
                customerType,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                decideCode
            );
        }
        return new FaceAuthResultResponse(
            transactionId,
            formId,
            resNo,
            joinType,
            customerType,
            push.getFathCmpltNtfyDt(),
            "I".equals(push.getRetvCdVal()) ? FaceAuthIdentityType.REGID :
                "D".equals(push.getRetvCdVal()) ? FaceAuthIdentityType.DRIVE :
                    FaceAuthIdentityType.from(push.getRetvCdVal()),
            push.getCustNm(),
            push.getCustIdfyNo(),
            push.getDriveLicnsNo(),
            push.getIssDateVal(),
            push.getSkipPsblYn(),
            push.getSmsRcvTelNo(),
            decideCode
        );
    }

    public static FaceAuthResultResponse of(
        String transactionId,
        String formId,
        String resNo,
        FaceAuthJoinType joinType,
        FaceAuthCustomerType customerType,
        MsfFathResultPush push,
        String decideCode
    ) {
        if (push == null) {
            return new FaceAuthResultResponse(
                transactionId,
                formId,
                resNo,
                joinType,
                customerType,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                null,
                decideCode
            );
        }
        return new FaceAuthResultResponse(
            transactionId,
            formId,
            resNo,
            joinType,
            customerType,
            push.getFathCmpltNtfyDate(),
            "I".equals(push.getIdentityCd()) ? FaceAuthIdentityType.REGID :
                "D".equals(push.getIdentityCd()) ? FaceAuthIdentityType.DRIVE :
                    FaceAuthIdentityType.from(push.getIdentityCd()),
            push.getCustNm(),
            push.getCustIdfyNo(),
            push.getDriveLicnsNo(),
            push.getIssueDate(),
            push.getSkipPsblYn(),
            push.getSmsRcvTelNo(),
            decideCode
        );
    }
}
