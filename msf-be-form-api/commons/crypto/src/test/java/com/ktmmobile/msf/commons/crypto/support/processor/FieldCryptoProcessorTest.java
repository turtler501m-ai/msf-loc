package com.ktmmobile.msf.commons.crypto.support.processor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.annotation.Encrypted;
import com.ktmmobile.msf.commons.crypto.support.exception.CryptoException;
import com.ktmmobile.msf.commons.crypto.support.util.KisaSeedUtils;

class FieldCryptoProcessorTest {

    private static final String TEST_LEGACY_KEY = "MDEyMzQ1Njc4OWFiY2RlZjAxMjM0NTY3ODlhYmNkZWY=";
    private static final String TEST_LEGACY_IV = "YWJjZGVmOTg3NjU0MzIxMA==";
    private static final String TEST_LEGACY_ENCRYPTED_PHONE_NUMBER = "7vQbNbpVVeQpcSwmNSF4Ow==";
    private static final String TEST_KISA_SEED_KEY = "0123456789abcdef";
    private static final String TEST_KISA_SEED_IV = "MDEyMzQ1Njc4OWFiY2RlZg==";

    private final FieldCryptoProcessor processor =
        new FieldCryptoProcessor(new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:"));
    private final FieldCryptoProcessor legacyCompatibleProcessor = new FieldCryptoProcessor(
        new TextEncryptorRegistry(Map.of(
            FieldCryptoAlgorithm.AES_GCM,
            new AesGcmTextEncryptor("12345678901234567890123456789012", "ENC:"),
            FieldCryptoAlgorithm.AES_GCM_SEARCHABLE,
            new SearchableAesGcmTextEncryptor("12345678901234567890123456789012", "ENC:", "aes-gcm-v1"),
            FieldCryptoAlgorithm.LEGACY_AES256_CBC,
            new LegacyAes256CbcTextEncryptor(
                TEST_LEGACY_KEY,
                TEST_LEGACY_IV
            ),
            FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC,
            new LegacyKisaSeedCbcTextEncryptor()
        ))
    );

    @BeforeEach
    void setUp() {
        KisaSeedUtils.initialize(TEST_KISA_SEED_KEY, TEST_KISA_SEED_IV);
    }

    @Test
    @DisplayName("@Encrypted 문자열 필드는 암호화 후 다시 복호화할 수 있다")
    void encryptAndDecryptAnnotatedStringField() {
        TestRequest request = new TestRequest("01012345678", "visible");

        processor.encrypt(request);

        assertThat(request.phoneNumber).startsWith("ENC:");
        assertThat(request.memo).isEqualTo("visible");

        processor.decrypt(request);

        assertThat(request.phoneNumber).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("@Encrypted 필드 값이 null 또는 빈 문자열이면 암복호화하지 않는다")
    void keepNullAndEmptyEncryptedFieldValues() {
        TestRequest nullRequest = new TestRequest(null, "visible");
        TestRequest emptyRequest = new TestRequest("", "visible");

        processor.encrypt(nullRequest);
        processor.encrypt(emptyRequest);

        assertThat(nullRequest.phoneNumber).isNull();
        assertThat(emptyRequest.phoneNumber).isEmpty();

        processor.decrypt(nullRequest);
        processor.decrypt(emptyRequest);

        assertThat(nullRequest.phoneNumber).isNull();
        assertThat(emptyRequest.phoneNumber).isEmpty();
    }

    @Test
    @DisplayName("Map과 Collection 내부 객체의 @Encrypted 필드도 처리한다")
    void processMapAndCollectionValues() {
        TestRequest first = new TestRequest("first", "memo");
        TestRequest second = new TestRequest("second", "memo");

        processor.encrypt(Map.of("list", List.of(first, second)));

        assertThat(first.phoneNumber).startsWith("ENC:");
        assertThat(second.phoneNumber).startsWith("ENC:");
    }

    @Test
    @DisplayName("AES_GCM_SEARCHABLE 필드는 같은 평문을 같은 암호문으로 암호화한다")
    void encryptSearchableAesGcmAnnotatedField() {
        SearchableRequest first = new SearchableRequest("01012345678");
        SearchableRequest second = new SearchableRequest("01012345678");

        legacyCompatibleProcessor.encrypt(first);
        legacyCompatibleProcessor.encrypt(second);

        assertThat(first.phoneNumber).isEqualTo(second.phoneNumber);

        legacyCompatibleProcessor.decrypt(first);

        assertThat(first.phoneNumber).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("@Encrypted value alias로 지정한 AES_GCM_SEARCHABLE 알고리즘을 인식한다")
    void encryptSearchableAesGcmValueAliasField() {
        SearchableAliasRequest first = new SearchableAliasRequest("01012345678");
        SearchableAliasRequest second = new SearchableAliasRequest("01012345678");

        legacyCompatibleProcessor.encrypt(first);
        legacyCompatibleProcessor.encrypt(second);

        assertThat(first.phoneNumber).isEqualTo(second.phoneNumber);
        assertThat(first.phoneNumber).startsWith("ENC:sgcm1:aes-gcm-v1:");
    }

    @Test
    @DisplayName("record 검색 조건은 원본을 유지하고 검색 가능 필드만 암호화한 복사본을 만든다")
    void encryptSearchableCopySupportsRecordWithoutChangingOriginal() {
        Object page = new Object();
        SearchableCondition condition = new SearchableCondition("01012345678", "memo", page);

        SearchableCondition copied = (SearchableCondition) legacyCompatibleProcessor.encryptSearchableCopy(condition);

        assertThat(copied).isNotSameAs(condition);
        assertThat(copied.phoneNumber()).startsWith("ENC:sgcm1:aes-gcm-v1:");
        assertThat(copied.memo()).isEqualTo("memo");
        assertThat(copied.page()).isSameAs(page);
        assertThat(condition.phoneNumber()).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("@Encrypted는 String 타입 필드에만 사용할 수 있다")
    void rejectNonStringEncryptedField() {
        assertThatThrownBy(() -> processor.encrypt(new InvalidRequest()))
            .isInstanceOf(CryptoException.class);
    }

    @Test
    @DisplayName("LEGACY_AES256_CBC 필드는 기존 MSP/MCP 암호문과 호환된다")
    void encryptAndDecryptLegacyAes256AnnotatedField() {
        LegacyRequest request = new LegacyRequest("01012345678");

        legacyCompatibleProcessor.encrypt(request);

        assertThat(request.residentRegistrationNumber).isEqualTo(TEST_LEGACY_ENCRYPTED_PHONE_NUMBER);

        legacyCompatibleProcessor.decrypt(request);

        assertThat(request.residentRegistrationNumber).isEqualTo("01012345678");
    }

    @Test
    @DisplayName("LEGACY_KISA_SEED_CBC 필드는 KisaSeedUtils 방식으로 암복호화한다")
    void encryptAndDecryptKisaSeedCbcAnnotatedField() {
        KisaSeedRequest request = new KisaSeedRequest("01012345678");

        legacyCompatibleProcessor.encrypt(request);

        assertThat(request.phoneNumber).isNotEqualTo("01012345678");

        legacyCompatibleProcessor.decrypt(request);

        assertThat(request.phoneNumber).isEqualTo("01012345678");
    }

    private static class TestRequest {

        @Encrypted
        private String phoneNumber;

        private String memo;

        private TestRequest(String phoneNumber, String memo) {
            this.phoneNumber = phoneNumber;
            this.memo = memo;
        }
    }

    private static class InvalidRequest {

        @Encrypted
        private Integer number = 1;
    }

    private static class LegacyRequest {

        @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_AES256_CBC)
        private String residentRegistrationNumber;

        private LegacyRequest(String residentRegistrationNumber) {
            this.residentRegistrationNumber = residentRegistrationNumber;
        }
    }

    private static class KisaSeedRequest {

        @Encrypted(algorithm = FieldCryptoAlgorithm.LEGACY_KISA_SEED_CBC)
        private String phoneNumber;

        private KisaSeedRequest(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    private static class SearchableRequest {

        @Encrypted(algorithm = FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
        private String phoneNumber;

        private SearchableRequest(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    private static class SearchableAliasRequest {

        @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
        private String phoneNumber;

        private SearchableAliasRequest(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }
    }

    private record SearchableCondition(
        @Encrypted(FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
        String phoneNumber,
        @Encrypted
        String memo,
        Object page
    ) {
    }
}
