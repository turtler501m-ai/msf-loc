package com.ktmmobile.msf.domains.commoncode.domain.dto;

import java.util.List;
import java.util.Optional;

import com.ktmmobile.msf.commons.common.data.type.UseYn;
import com.ktmmobile.msf.domains.commoncode.domain.entity.CommonCode;

public record CommonCodeData(
    String groupId,
    String code,
    String title,
    UseYn useYn,
    Detail detail
) {

    public static CommonCodeData from(CommonCode commonCode) {
        return new CommonCodeData(
            commonCode.getGroupId(),
            commonCode.getCode(),
            commonCode.getTitle(),
            commonCode.getUseYn(),
            Detail.from(commonCode.getDetail())
        );
    }

    public static Optional<CommonCodeData> get(List<CommonCodeData> commonCodes, String code) {
        return commonCodes.stream()
            .filter(commonCode -> commonCode.code().equals(code))
            .findFirst();
    }

    public boolean isUsed() {
        return this.useYn.isUsed();
    }

    public boolean isUnused() {
        return this.useYn.isUnused();
    }


    public record Detail(
        String abbrName,
        String description,
        String etcValue1,
        String etcValue2,
        String etcValue3,
        String startDate,
        String endDate
    ) {

        static Detail from(CommonCode.Detail detail) {
            if (detail == null) {
                return null;
            }

            return new Detail(
                detail.getAbbrName(),
                detail.getDescription(),
                detail.getEtcValue1(),
                detail.getEtcValue2(),
                detail.getEtcValue3(),
                detail.getStartDate(),
                detail.getEndDate()
            );
        }
    }
}
