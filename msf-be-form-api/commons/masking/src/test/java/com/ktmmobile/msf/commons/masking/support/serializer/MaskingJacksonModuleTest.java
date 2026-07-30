package com.ktmmobile.msf.commons.masking.support.serializer;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;
import com.ktmmobile.msf.commons.masking.support.annotation.Masked;
import com.ktmmobile.msf.commons.masking.support.processor.MaskingProcessorRegistryTest;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MaskingJacksonModule")
class MaskingJacksonModuleTest {

    private final ObjectMapper objectMapper = JsonMapper.builder()
        .addModule(new MaskingJacksonModule(MaskingProcessorRegistryTest.createRegistry()))
        .build();

    @Test
    @DisplayName("@Masked 애너테이션이 붙은 응답 필드를 JSON 직렬화 시 마스킹한다")
    void masksAnnotatedResponseFields() throws Exception {
        TestResponse response = new TestResponse(
            "password",
            "홍길동",
            "010-1234-5678",
            "user@example.com",
            "900101-1234567",
            "900101-3234567",
            "123-456789-01234",
            "1234-5678-9012-5678",
            "192.168.0.1",
            "서울특별시 강남구 테헤란로 123, 456동 789호",
            "visible"
        );

        String json = objectMapper.writeValueAsString(response);

        assertThat(JsonPath.<String>read(json, "$.password")).isEqualTo("********");
        assertThat(JsonPath.<String>read(json, "$.name")).isEqualTo("홍*동");
        assertThat(JsonPath.<String>read(json, "$.mobilePhoneNumber")).isEqualTo("010-****-5678");
        assertThat(JsonPath.<String>read(json, "$.email")).isEqualTo("u***@example.com");
        assertThat(JsonPath.<String>read(json, "$.residentRegistrationNumber")).isEqualTo("900101-1******");
        assertThat(JsonPath.<String>read(json, "$.foreignRegistrationNumber")).isEqualTo("900101-3******");
        assertThat(JsonPath.<String>read(json, "$.bankAccountNumber")).isEqualTo("123-******-**234");
        assertThat(JsonPath.<String>read(json, "$.creditCardNumber")).isEqualTo("1234-56**-****-5678");
        assertThat(JsonPath.<String>read(json, "$.ipAddress")).isEqualTo("192.168.*.1");
        assertThat(JsonPath.<String>read(json, "$.address")).isEqualTo("서울특별시 강남구 테헤란로 ***, *******");
        assertThat(JsonPath.<String>read(json, "$.plainText")).isEqualTo("visible");
    }


    /** 마스킹 직렬화 테스트용 응답 DTO */
    record TestResponse(
        @Masked(type = MaskingType.PASSWORD)
        String password,

        @Masked(type = MaskingType.NAME)
        String name,

        @Masked(type = MaskingType.MOBILE_PHONE)
        String mobilePhoneNumber,

        @Masked(type = MaskingType.EMAIL)
        String email,

        @Masked(type = MaskingType.RESIDENT_REGISTRATION_NUMBER)
        String residentRegistrationNumber,

        @Masked(type = MaskingType.FOREIGN_REGISTRATION_NUMBER)
        String foreignRegistrationNumber,

        @Masked(type = MaskingType.BANK_ACCOUNT_NUMBER)
        String bankAccountNumber,

        @Masked(type = MaskingType.CREDIT_CARD_NUMBER)
        String creditCardNumber,

        @Masked(type = MaskingType.IP_ADDRESS)
        String ipAddress,

        @Masked(type = MaskingType.ADDRESS)
        String address,

        String plainText
    ) {
    }
}
