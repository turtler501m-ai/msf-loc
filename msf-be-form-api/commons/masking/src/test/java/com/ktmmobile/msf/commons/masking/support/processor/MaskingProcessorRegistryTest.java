package com.ktmmobile.msf.commons.masking.support.processor;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.ktmmobile.msf.commons.masking.domain.code.MaskingType;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MaskingProcessorRegistry")
public class MaskingProcessorRegistryTest {

    @Test
    @DisplayName("모든 MaskingType은 대응하는 MaskingProcessor를 가진다")
    void hasProcessorForEveryMaskingType() {
        MaskingProcessorRegistry registry = createRegistry();

        for (MaskingType type: MaskingType.values()) {
            assertThat(registry.get(type).type()).isEqualTo(type);
        }
    }

    @Test
    @DisplayName("MaskingProcessor 구현체는 Reflections 스캔으로 자동 등록된다")
    void registersMaskingProcessorsFromReflectionsScan() {
        MaskingProcessorRegistry registry = new MaskingProcessorRegistry();

        for (MaskingType type: MaskingType.values()) {
            assertThat(registry.get(type).type()).isEqualTo(type);
        }
    }

    @Test
    @DisplayName("마스킹 타입별 기본 정책을 적용한다")
    void masksByProcessorType() {
        MaskingProcessorRegistry registry = createRegistry();

        assertThat(registry.mask("password", MaskingType.PASSWORD)).isEqualTo("********");

        assertThat(registry.mask("홍길동", MaskingType.NAME)).isEqualTo("홍*동");
        assertThat(registry.mask("John", MaskingType.NAME)).isEqualTo("J**n");

        assertThat(registry.mask("02-1234-5678", MaskingType.TELEPHONE)).isEqualTo("02-****-5678");
        assertThat(registry.mask("02-123-4567", MaskingType.TELEPHONE)).isEqualTo("02-****-4567");
        assertThat(registry.mask("03156781234", MaskingType.TELEPHONE)).isEqualTo("031****1234");
        assertThat(registry.mask("0505-5678-1234", MaskingType.TELEPHONE)).isEqualTo("0505-****-1234");
        assertThat(registry.mask("050556781234", MaskingType.TELEPHONE)).isEqualTo("0505****1234");
        assertThat(registry.mask("070-5678-1234", MaskingType.TELEPHONE)).isEqualTo("070-****-1234");
        assertThat(registry.mask("080-5678-1234", MaskingType.TELEPHONE)).isEqualTo("080-****-1234");
        assertThat(registry.mask("1588-1234", MaskingType.TELEPHONE)).isEqualTo("1588-****");
        assertThat(registry.mask("15881234", MaskingType.TELEPHONE)).isEqualTo("1588****");

        assertThat(registry.mask("010-1234-5678", MaskingType.MOBILE_PHONE)).isEqualTo("010-****-5678");
        assertThat(registry.mask("011-123-4567", MaskingType.MOBILE_PHONE)).isEqualTo("011-***-4567");
        assertThat(registry.mask("01012345678", MaskingType.MOBILE_PHONE)).isEqualTo("010****5678");
        assertThat(registry.mask("0111234567", MaskingType.MOBILE_PHONE)).isEqualTo("011***4567");

        assertThat(registry.mask("user@example.com", MaskingType.EMAIL)).isEqualTo("u***@example.com");

        assertThat(registry.mask("900101-1234567", MaskingType.RESIDENT_REGISTRATION_NUMBER)).isEqualTo("900101-1******");
        assertThat(registry.mask("9001011234567", MaskingType.RESIDENT_REGISTRATION_NUMBER)).isEqualTo("9001011******");

        assertThat(registry.mask("900101-3234567", MaskingType.FOREIGN_REGISTRATION_NUMBER)).isEqualTo("900101-3******");
        assertThat(registry.mask("9001013234567", MaskingType.FOREIGN_REGISTRATION_NUMBER)).isEqualTo("9001013******");

        assertThat(registry.mask("123-45-67890", MaskingType.BUSINESS_REGISTRATION_NUMBER)).isEqualTo("123-**-*****");
        assertThat(registry.mask("1234567890", MaskingType.BUSINESS_REGISTRATION_NUMBER)).isEqualTo("123*******");

        assertThat(registry.mask("110111-1234567", MaskingType.CORPORATE_REGISTRATION_NUMBER)).isEqualTo("110111-*******");
        assertThat(registry.mask("1101111234567", MaskingType.CORPORATE_REGISTRATION_NUMBER)).isEqualTo("110111*******");

        assertThat(registry.mask("12-34-567890-12", MaskingType.DRIVER_LICENSE_NUMBER)).isEqualTo("12-**-******-12");
        assertThat(registry.mask("123456789012", MaskingType.DRIVER_LICENSE_NUMBER)).isEqualTo("12********12");

        assertThat(registry.mask("123-456789-01234", MaskingType.BANK_ACCOUNT_NUMBER)).isEqualTo("123-******-**234");
        assertThat(registry.mask("12345678901234", MaskingType.BANK_ACCOUNT_NUMBER)).isEqualTo("123********234");

        assertThat(registry.mask("1234-5678-9012-5678", MaskingType.CREDIT_CARD_NUMBER)).isEqualTo("1234-56**-****-5678");
        assertThat(registry.mask("1234567890125678", MaskingType.CREDIT_CARD_NUMBER)).isEqualTo("123456******5678");

        assertThat(registry.mask("192.168.0.1", MaskingType.IP_ADDRESS)).isEqualTo("192.168.*.1");
        assertThat(registry.mask("10.20.123.40", MaskingType.IP_ADDRESS)).isEqualTo("10.20.***.40");
        assertThat(registry.mask("2001:db8::1", MaskingType.IP_ADDRESS))
            .isEqualTo("2001:db8:0:0:****:****:****:1");
        assertThat(registry.mask("2001:0db8:85a3:0000:0000:8a2e:0370:7334", MaskingType.IP_ADDRESS))
            .isEqualTo("2001:db8:85a3:0:****:****:****:7334");

        assertThat(registry.mask("서울특별시 강남구 테헤란로 123, 456동 789호", MaskingType.ADDRESS))
            .isEqualTo("서울특별시 강남구 테헤란로 ***, *******");
        assertThat(registry.mask("세종특별자치시 한누리대로 411", MaskingType.ADDRESS))
            .isEqualTo("세종특별자치시 한누리대로 ***");
        assertThat(registry.mask("부산광역시 해운대구 센텀중앙로 97-1, 1201호", MaskingType.ADDRESS))
            .isEqualTo("부산광역시 해운대구 센텀중앙로 ****, *******");
        assertThat(registry.mask("서울특별시 강남구 테헤란로 123 456동 789호", MaskingType.ADDRESS))
            .isEqualTo("서울특별시 강남구 테헤란로 *** *******");
        assertThat(registry.mask("경기도 수원시 영통구 매탄동 416, 삼정아파트 101동 202호", MaskingType.ADDRESS))
            .isEqualTo("경기도 수원시 영통구 매탄동 ***, **********");
        assertThat(registry.mask("제주특별자치도 제주시 애월읍 하귀1리 산115, 2층", MaskingType.ADDRESS))
            .isEqualTo("제주특별자치도 제주시 애월읍 하귀1리 ****, **********");
        assertThat(registry.mask("서울특별시 중구 충무로1가 24-1, 상가 101호", MaskingType.ADDRESS))
            .isEqualTo("서울특별시 중구 충무로1가 ****, **********");
    }

    @Test
    @DisplayName("형식이 맞지 않는 값도 오류 없이 처리한다")
    void masksWithoutErrorWhenValueDoesNotMatchExpectedFormat() {
        MaskingProcessorRegistry registry = createRegistry();

        assertThat(registry.mask("", MaskingType.PASSWORD)).isEqualTo("********");
        assertThat(registry.mask("홍", MaskingType.NAME)).isEqualTo("*");
        assertThat(registry.mask("홍길", MaskingType.NAME)).isEqualTo("홍*");
        assertThat(registry.mask("12", MaskingType.TELEPHONE)).isEqualTo("12");
        assertThat(registry.mask("abc", MaskingType.TELEPHONE)).isEqualTo("abc");
        assertThat(registry.mask("010", MaskingType.MOBILE_PHONE)).isEqualTo("010");
        assertThat(registry.mask("010-abcd", MaskingType.MOBILE_PHONE)).isEqualTo("010-abcd");
        assertThat(registry.mask("a@example.com", MaskingType.EMAIL)).isEqualTo("*@example.com");
        assertThat(registry.mask("invalid-email", MaskingType.EMAIL)).isEqualTo("i************");
        assertThat(registry.mask("1234567", MaskingType.RESIDENT_REGISTRATION_NUMBER)).isEqualTo("1234567");
        assertThat(registry.mask("invalid-rrn", MaskingType.RESIDENT_REGISTRATION_NUMBER)).isEqualTo("invalid-rrn");
        assertThat(registry.mask("1234567", MaskingType.FOREIGN_REGISTRATION_NUMBER)).isEqualTo("1234567");
        assertThat(registry.mask("123", MaskingType.BUSINESS_REGISTRATION_NUMBER)).isEqualTo("123");
        assertThat(registry.mask("123456", MaskingType.CORPORATE_REGISTRATION_NUMBER)).isEqualTo("123456");
        assertThat(registry.mask("1234", MaskingType.DRIVER_LICENSE_NUMBER)).isEqualTo("1234");
        assertThat(registry.mask("123456", MaskingType.BANK_ACCOUNT_NUMBER)).isEqualTo("123456");
        assertThat(registry.mask("1234-5678", MaskingType.CREDIT_CARD_NUMBER)).isEqualTo("1234-5678");
        assertThat(registry.mask("192.168.0", MaskingType.IP_ADDRESS)).isEqualTo("192.168.0");
        assertThat(registry.mask("999.168.0.1", MaskingType.IP_ADDRESS)).isEqualTo("999.168.0.1");
        assertThat(registry.mask("2001:::1", MaskingType.IP_ADDRESS)).isEqualTo("2001:::1");
        assertThat(registry.mask("localhost", MaskingType.IP_ADDRESS)).isEqualTo("localhost");
        assertThat(registry.mask("주소형식아님", MaskingType.ADDRESS)).isEqualTo("******");
    }

    public static MaskingProcessorRegistry createRegistry() {
        return new MaskingProcessorRegistry();
    }
}
