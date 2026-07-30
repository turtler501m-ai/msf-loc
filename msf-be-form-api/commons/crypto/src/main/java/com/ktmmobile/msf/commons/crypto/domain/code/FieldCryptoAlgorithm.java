package com.ktmmobile.msf.commons.crypto.domain.code;

/**
 * DB 필드 암복호화 알고리즘
 */
public enum FieldCryptoAlgorithm {
    AES_GCM,             // 신규 저장 기본 방식: AES-GCM + 랜덤 nonce + 인증 태그
    AES_GCM_SEARCHABLE,  // 동등 조건 검색 컬럼용: AES-GCM + 평문 기반 결정적 nonce + 인증 태그
    LEGACY_AES256_CBC,   // 레거시 호환 방식: AES-256-CBC + 고정 IV + Base64
    LEGACY_KISA_SEED_CBC, // MSP PRX 레거시 호환 방식: KISA SEED-CBC + 고정 IV + Base64
}
