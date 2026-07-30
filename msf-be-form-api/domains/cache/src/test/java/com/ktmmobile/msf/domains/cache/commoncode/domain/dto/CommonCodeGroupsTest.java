package com.ktmmobile.msf.domains.cache.commoncode.domain.dto;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.common.data.type.UseYn;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.SimpleCommonCode;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommonCodeGroupsTest {

    private static final String GROUP_ID = "CmmPeriodLimit";
    private static final String CODE = "MnpDayLimit";

    @Test
    @DisplayName("단일 그룹 조회 결과에서는 groupId 없이 코드 목록을 조회할 수 있다.")
    void getSingleGroupReturnsSingleGroupCodes() {
        CommonCodeData commonCode = commonCode(CODE);
        CommonCodeGroups groups = new CommonCodeGroups(Map.of(GROUP_ID, List.of(commonCode)));

        assertThat(groups.getSingleGroup()).containsExactly(commonCode);
    }

    @Test
    @DisplayName("단일 그룹 조회 결과에서는 groupId 없이 특정 코드를 조회할 수 있다.")
    void getSingleGroupReturnsSingleGroupCode() {
        CommonCodeData commonCode = commonCode(CODE);
        CommonCodeGroups groups = new CommonCodeGroups(Map.of(GROUP_ID, List.of(commonCode)));

        assertThat(groups.getSingleGroup(CODE)).contains(commonCode);
    }

    @Test
    @DisplayName("단일 그룹 편의 메서드는 조회 결과가 여러 그룹이면 예외를 던진다.")
    void getSingleGroupRejectsMultipleGroups() {
        CommonCodeGroups groups = new CommonCodeGroups(Map.of(
            GROUP_ID, List.of(commonCode(CODE)),
            "AnotherGroup", List.of(commonCode("AnotherCode"))
        ));

        assertThatThrownBy(groups::getSingleGroup)
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("CommonCodeGroups must contain exactly one group.");
    }

    @Test
    @DisplayName("단순 공통코드 조회는 code가 null이면 빈 단순 공통코드를 반환한다.")
    void getSimpleReturnsEmptyWhenCodeIsNull() {
        CommonCodeGroups groups = new CommonCodeGroups(Map.of(GROUP_ID, List.of(commonCode(CODE))));

        assertThat(groups.getSimple(GROUP_ID, null)).isEqualTo(SimpleCommonCode.empty());
        assertThat(groups.getSimple(GROUP_ID, null, "기본 코드명")).isEqualTo(SimpleCommonCode.empty());
        assertThat(groups.getSimple(GROUP_ID, null, Map.of(CODE, "fallback"))).isEqualTo(SimpleCommonCode.empty());
    }

    @Test
    @DisplayName("단순 공통코드 조회는 조회 결과가 없으면 빈 단순 공통코드를 반환한다.")
    void getSimpleReturnsEmptyWhenCodeIsNotFound() {
        CommonCodeGroups groups = new CommonCodeGroups(Map.of(GROUP_ID, List.of(commonCode(CODE))));

        assertThat(groups.getSimple(GROUP_ID, "UnknownCode")).isEqualTo(SimpleCommonCode.empty());
        assertThat(groups.getSimple(GROUP_ID, "UnknownCode", Map.of())).isEqualTo(SimpleCommonCode.empty());
    }

    private static CommonCodeData commonCode(String code) {
        return new CommonCodeData(GROUP_ID, code, "번호이동 일 제한", UseYn.YES, null);
    }
}
