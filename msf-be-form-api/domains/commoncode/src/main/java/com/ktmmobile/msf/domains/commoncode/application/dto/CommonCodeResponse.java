package com.ktmmobile.msf.domains.commoncode.application.dto;

import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

public record CommonCodeResponse(
    String groupId,
    String code,
    String codeName,
    String useYn,
    DetailResponse detail
) {

    public static CommonCodeResponse of(CommonCode commonCode) {
        return new CommonCodeResponse(
            commonCode.getGroupId(),
            commonCode.getCode(),
            commonCode.getCodeName(),
            commonCode.getUseYn(),
            DetailResponse.of(commonCode.getDetail())
        );
    }

    public record DetailResponse(
        String abbrName,
        String description,
        String upperGroupId,
        int sortOrder,
        String etcValue1,
        String etcValue2,
        String etcValue3,
        String filePathName,
        String startDate,
        String endDate
    ) {

        static DetailResponse of(CommonCode.Detail detail) {
            if (detail == null) {
                return null;
            }

            return new DetailResponse(
                detail.getAbbrName(),
                detail.getDescription(),
                detail.getUpperGroupId(),
                detail.getSortOrder(),
                detail.getEtcValue1(),
                detail.getEtcValue2(),
                detail.getEtcValue3(),
                detail.getFilePathName(),
                detail.getStartDate(),
                detail.getEndDate()
            );
        }
    }
}
