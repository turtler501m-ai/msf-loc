package com.ktmmobile.msf.domains.externalclient.common.adapter;

import java.util.Base64;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.crypto.support.util.KisaSeedUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.adapter.EncryptAdapter;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("XML 암복호화 어댑터")
class EncryptAdapterTest {

    private final EncryptAdapter adapter = new EncryptAdapter();

    @BeforeAll
    static void setUp() {
        KisaSeedUtils.initialize("1234567890123456", Base64.getEncoder().encodeToString("1234567890123456".getBytes()));
    }

    @Test
    @DisplayName("marshal 시 문자열 암호화")
    void marshalEncryptsValue() {
        String encrypted = adapter.marshal("홍길동");

        assertThat(encrypted).isNotBlank();
        assertThat(encrypted).isNotEqualTo("홍길동");
    }

    @Test
    @DisplayName("unmarshal 시 문자열 복호화")
    void unmarshalDecryptsValue() {
        String encrypted = adapter.marshal("홍길동");

        assertThat(adapter.unmarshal(encrypted)).isEqualTo("홍길동");
    }

    @Test
    @DisplayName("null 값 유지")
    void keepNullValue() {
        assertThat(adapter.marshal(null)).isNull();
        assertThat(adapter.unmarshal(null)).isNull();
    }
}
