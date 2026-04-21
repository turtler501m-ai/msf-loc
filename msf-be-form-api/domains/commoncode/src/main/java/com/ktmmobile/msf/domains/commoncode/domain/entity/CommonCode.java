package com.ktmmobile.msf.domains.commoncode.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import com.ktmmobile.msf.commons.common.data.type.UseYn;

@Getter
@NoArgsConstructor
public class CommonCode {

    private String groupId;
    private String code;
    private String title;
    private UseYn useYn = UseYn.UNDEFINED;
    private Detail detail;

    @Getter
    @NoArgsConstructor
    public static class Detail {

        private String abbrName;
        private int sortOrder;
        private String etcValue1;
        private String etcValue2;
        private String etcValue3;
        private String startDate;
        private String endDate;
    }

    public boolean isUsed() {
        return this.useYn.isUsed();
    }

    public boolean isUnused() {
        return this.useYn.isUnused();
    }
}
