package com.ktmmobile.msf.domains.cache.terms.domain.entity;

import java.util.List;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 약관 상세 캐시 원천 데이터
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class TermsDetail {
    Long seq;
    String cdGroupId1;
    String cdGroupId2;
    List<TermsDetailContent> contents;
}
