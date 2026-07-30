package com.ktmmobile.msf.domains.koiidcard.application.dto.mobileId;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record MobileIdVerificationSaveResponse(
    String status,
    String result,
    String idType,
    String customerNm,
    String customerBirth,
    String customerRrn
) {

    private static final String STATUS_PROCESSING = "PROCESSING";
    private static final String STATUS_SUCCESS = "SUCCESS";
    private static final String STATUS_FAIL = "FAIL";

    public static MobileIdVerificationSaveResponse from(
        MobileIdCardHttpResponse response
    ) {
        if (response == null || response.isProcessing()) {
            return processing();
        }

        if (response.isFail()) {
            return fail(response.result());
        }

        return success(response);
    }

    public static MobileIdVerificationSaveResponse processing() {
        return new MobileIdVerificationSaveResponse(
            STATUS_PROCESSING,
            null,
            null,
            null,
            null,
            null
        );
    }

    public static MobileIdVerificationSaveResponse fail(String result) {
        return new MobileIdVerificationSaveResponse(
            STATUS_FAIL,
            result,
            null,
            null,
            null,
            null
        );
    }

    private static MobileIdVerificationSaveResponse success(
        MobileIdCardHttpResponse response
    ) {
        return new MobileIdVerificationSaveResponse(
            STATUS_SUCCESS,
            response.result(),
            response.idType(),
            response.customerNm(),
            response.customerBirth(),
            response.customerRrn()
        );
    }
}