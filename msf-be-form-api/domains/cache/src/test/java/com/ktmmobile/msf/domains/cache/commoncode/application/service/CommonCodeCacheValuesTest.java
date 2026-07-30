package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

import static org.assertj.core.api.Assertions.assertThat;

class CommonCodeCacheValuesTest {

    @Test
    @DisplayName("CRD 공통코드는 주요 카드사를 우선순위대로 먼저 정렬한다.")
    void sortPrioritizesCreditCardCompanies() {
        List<CommonCode> codes = List.of(
            commonCode("CRD", "롯데카드", "롯데카드", 1, "AM"),
            commonCode("CRD", "우리카드", "우리카드", 29, "WO"),
            commonCode("CRD", "현대카드", "현대카드", 16, "DY"),
            commonCode("CRD", "하나카드", "하나카드", 14, "HS"),
            commonCode("CRD", "프리텔삼성카드", "프리텔삼성카드", 6, "SF"),
            commonCode("CRD", "국민카드", "국민카드", 2, "GM"),
            commonCode("CRD", "삼성카드", "삼성카드", 18, "SS"),
            commonCode("CRD", "신한카드", "신한카드", 27, "SH"),
            commonCode("CRD", "KTF맴버스 국민카드", "KTF맴버스 국민카드", 22, "GK")
        );

        List<CommonCode> sorted = CommonCodeCacheValues.sort(codes);

        assertThat(sorted)
            .extracting(CommonCode::getTitle)
            .containsExactly(
                "삼성카드",
                "현대카드",
                "국민카드",
                "신한카드",
                "우리카드",
                "롯데카드",
                "프리텔삼성카드",
                "하나카드",
                "KTF맴버스 국민카드"
            );
        assertThat(sorted)
            .filteredOn(commonCode -> "삼성카드".equals(commonCode.getTitle()))
            .singleElement()
            .extracting(commonCode -> commonCode.getDetail().getSortOrder())
            .isEqualTo(18);
    }

    @Test
    @DisplayName("Redis 캐시 그룹 목록도 CRD 주요 카드사 우선순위대로 저장한다.")
    void groupByGroupIdStoresCreditCardCompaniesInPriorityOrder() {
        List<CommonCode> codes = List.of(
            commonCode("CRD", "롯데카드", "롯데카드", 1, "AM"),
            commonCode("PAYM", "C", "신용카드", 2, ""),
            commonCode("CRD", "우리카드", "우리카드", 29, "WO"),
            commonCode("CRD", "현대카드", "현대카드", 16, "DY"),
            commonCode("CRD", "프리텔삼성카드", "프리텔삼성카드", 6, "SF"),
            commonCode("CRD", "국민카드", "국민카드", 2, "GM"),
            commonCode("CRD", "삼성카드", "삼성카드", 18, "SS"),
            commonCode("CRD", "신한카드", "신한카드", 27, "SH")
        );

        Map<String, List<CommonCode>> grouped = CommonCodeCacheValues.groupByGroupId(codes);

        assertThat(grouped.get("CRD"))
            .extracting(CommonCode::getTitle)
            .containsExactly("삼성카드", "현대카드", "국민카드", "신한카드", "우리카드", "롯데카드", "프리텔삼성카드");
    }

    @Test
    @DisplayName("Redis 캐시 그룹 목록도 BNK 주요 은행 우선순위대로 저장한다.")
    void groupByGroupIdStoresBanksInPriorityOrder() {
        List<CommonCode> codes = List.of(
            commonCode("BNK", "88", "신한은행", 30, ""),
            commonCode("BNK", "04", "국민은행", 40, ""),
            commonCode("BNK", "81", "KEB하나은행", 50, ""),
            commonCode("BNK", "20", "우리은행", 60, ""),
            commonCode("BNK", "11", "농협", 70, ""),
            commonCode("BNK", "03", "기업은행", 1, ""),
            commonCode("BNK", "71", "우체국", 2, "")
        );

        Map<String, List<CommonCode>> grouped = CommonCodeCacheValues.groupByGroupId(codes);

        assertThat(grouped.get("BNK"))
            .extracting(CommonCode::getTitle)
            .containsExactly("신한은행", "KEB하나은행", "국민은행", "농협", "우리은행", "기업은행", "우체국");
    }

    @Test
    @DisplayName("CRD가 아닌 공통코드는 기존 sortOrder 정렬을 유지한다.")
    void sortKeepsDefaultOrderForOtherGroups() {
        List<CommonCode> codes = List.of(
            commonCode("AGR", "02", "배우자", 30, "DY"),
            commonCode("AGR", "01", "부모", 20, "SS"),
            commonCode("AGR", "03", "자녀", 10, "GM")
        );

        List<CommonCode> sorted = CommonCodeCacheValues.sort(codes);

        assertThat(sorted)
            .extracting(CommonCode::getTitle)
            .containsExactly("자녀", "부모", "배우자");
    }

    private static CommonCode commonCode(String groupId, String code, String title, int sortOrder, String etcValue1) {
        CommonCode commonCode = new CommonCode();
        CommonCode.Detail detail = new CommonCode.Detail();
        setField(commonCode, "groupId", groupId);
        setField(commonCode, "code", code);
        setField(commonCode, "title", title);
        setField(detail, "sortOrder", sortOrder);
        setField(detail, "etcValue1", etcValue1);
        setField(commonCode, "detail", detail);
        return commonCode;
    }

    private static void setField(Object target, String fieldName, Object value) {
        try {
            Field field = target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(target, value);
        } catch (ReflectiveOperationException e) {
            throw new IllegalStateException(e);
        }
    }
}
