package com.ktmmobile.msf.domains.cache.commoncode.application.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;

/**
 * 데이터소스별 로더와 통합 조회 서비스의 공통코드 정렬/그룹핑 규칙
 */
@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
final class CommonCodeCacheValues {

    // CRD 캐시 적재 시 지정한 카드사를 먼저 노출
    private static final String CREDIT_CARD_GROUP_ID = "CRD";
    private static final List<String> CREDIT_CARD_PRIORITY_CODES = List.of(
        "SS", // 삼성카드
        "DY", // 현대카드
        "GM", // 국민카드
        "SH", // 신한카드
        "WO"  // 우리카드
    );

    // BNK 캐시 적재 시 지정한 은행을 먼저 노출
    private static final String BANK_GROUP_ID = "BNK";
    private static final List<String> BANK_PRIORITY_CODES = List.of(
        "88", // 신한은행
        "81", // 하나은행
        "04", // 국민은행
        "11", // 농협
        "20"  // 우리은행
    );

    private static final int NO_PRIORITY = Integer.MAX_VALUE;

    private static final Comparator<CommonCode> COMMON_CODE_COMPARATOR = Comparator
        .comparing(CommonCode::getGroupId, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCodeCacheValues::resolveCreditCardPriority)
        .thenComparing(CommonCodeCacheValues::resolveBankPriority)
        .thenComparing(CommonCodeCacheValues::resolveSortOrder, Comparator.nullsLast(Integer::compareTo))
        .thenComparing(CommonCode::getCode, Comparator.nullsLast(String::compareTo))
        .thenComparing(CommonCode::getTitle, Comparator.nullsLast(String::compareTo));

    /** 데이터소스 공통 응답 순서 정렬 */
    static List<CommonCode> sort(List<CommonCode> commonCodes) {
        return commonCodes.stream()
            .sorted(COMMON_CODE_COMPARATOR)
            .toList();
    }

    /** Redis Hash field용 groupId 기준 공통코드 그룹핑 */
    static Map<String, List<CommonCode>> groupByGroupId(List<CommonCode> commonCodes) {
        Map<String, List<CommonCode>> commonCodesByGroupId = new LinkedHashMap<>();
        for (CommonCode commonCode: sort(commonCodes)) {
            commonCodesByGroupId.computeIfAbsent(commonCode.getGroupId(), _ -> new ArrayList<>())
                .add(commonCode);
        }

        Map<String, List<CommonCode>> immutableCommonCodesByGroupId = new LinkedHashMap<>();
        commonCodesByGroupId.forEach((groupId, groupCommonCodes) ->
            immutableCommonCodesByGroupId.put(groupId, List.copyOf(groupCommonCodes)));
        return Map.copyOf(immutableCommonCodesByGroupId);
    }

    private static Integer resolveSortOrder(CommonCode commonCode) {
        return commonCode.getDetail() != null ? commonCode.getDetail().getSortOrder() : null;
    }

    private static int resolveCreditCardPriority(CommonCode commonCode) {
        if (!CREDIT_CARD_GROUP_ID.equals(commonCode.getGroupId())) {
            return NO_PRIORITY;
        }

        String creditCardCode = resolveCreditCardCode(commonCode);
        if (!StringUtils.hasText(creditCardCode)) {
            return NO_PRIORITY;
        }

        for (int i = 0; i < CREDIT_CARD_PRIORITY_CODES.size(); i++) {
            if (creditCardCode.equals(CREDIT_CARD_PRIORITY_CODES.get(i))) {
                return i;
            }
        }
        return NO_PRIORITY;
    }

    private static String resolveCreditCardCode(CommonCode commonCode) {
        return commonCode.getDetail() != null ? commonCode.getDetail().getEtcValue1() : null;
    }

    private static int resolveBankPriority(CommonCode commonCode) {
        if (!BANK_GROUP_ID.equals(commonCode.getGroupId()) || !StringUtils.hasText(commonCode.getCode())) {
            return NO_PRIORITY;
        }

        for (int i = 0; i < BANK_PRIORITY_CODES.size(); i++) {
            if (commonCode.getCode().equals(BANK_PRIORITY_CODES.get(i))) {
                return i;
            }
        }
        return NO_PRIORITY;
    }
}
