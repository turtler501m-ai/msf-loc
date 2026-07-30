package com.ktmmobile.msf.commons.crypto.support.processor;

import com.ktmmobile.msf.commons.crypto.support.util.KisaSeedUtils;

/**
 * MSP PRX 레거시 KISA SEED-CBC 암복호화 처리기
 *
 * <p>{@link KisaSeedUtils} 전역 키/IV 설정 사용
 * PRX 직렬화와 필드 암복호화 공통 구현 어댑터</p>
 */
public class LegacyKisaSeedCbcTextEncryptor implements TextEncryptor {

    /** 레거시 SEED-CBC 암호문 생성 위임 */
    @Override
    public String encrypt(String plainText) {
        return KisaSeedUtils.encrypt(plainText);
    }

    /** 레거시 SEED-CBC 암호문 복호화 위임 */
    @Override
    public String decrypt(String cipherText) {
        return KisaSeedUtils.decrypt(cipherText);
    }
}
