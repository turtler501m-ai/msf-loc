package com.ktmmobile.msf.domains.commoncode.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CommonCode {

    private String groupId;
    private String code;
    private String codeName;
    private String useYn;
    private Detail detail;

    @Getter
    @NoArgsConstructor
    public static class Detail {

        private String abbrName;
        private String description;
        private String upperGroupId;
        private int sortOrder;
        private String etcValue1;
        private String etcValue2;
        private String etcValue3;
        private String filePathName;
        private String startDate;
        private String endDate;
    }
}
