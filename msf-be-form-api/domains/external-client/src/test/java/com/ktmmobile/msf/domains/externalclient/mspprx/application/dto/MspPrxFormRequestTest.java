package com.ktmmobile.msf.domains.externalclient.mspprx.application.dto;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("MSP PRX Form 요청")
class MspPrxFormRequestTest {

    @Test
    @DisplayName("PRX serviceCall.do와 osstServiceCall.do 요청용 getURL form data를 만든다")
    void convertToGetUrlFormData() {
        MspPrxFormRequest request = MspPrxFormRequest.builder()
            .parameter("appEventCd", "X25")
            .parameter("custNm", "홍길동")
            .parameter("ctn", "01012345678")
            .build();

        MultiValueMap<String, String> formData = request.toGetUrlFormData();

        assertThat(formData).containsOnlyKeys("getURL");
        assertThat(URLDecoder.decode(formData.getFirst("getURL"), StandardCharsets.UTF_8))
            .isEqualTo("appEventCd=X25&custNm=홍길동&ctn=01012345678");
    }

    @Test
    @DisplayName("PRX simpleOpenServiceCall.do 요청용 일반 form data를 만든다")
    void convertToFormData() {
        MspPrxFormRequest request = MspPrxFormRequest.builder()
            .parameter("appEventCd", "NP1")
            .parameter("custNm", "홍길동")
            .build();

        assertThat(request.toFormData())
            .containsEntry("appEventCd", java.util.List.of("NP1"))
            .containsEntry("custNm", java.util.List.of("홍길동"));
    }

    @Test
    @DisplayName("빈 파라미터명과 null 값은 form 요청에서 제외한다")
    void filterBlankKeyAndNullValue() {
        MspPrxFormRequest request = MspPrxFormRequest.builder()
            .parameter("appEventCd", "NP1")
            .parameter(" ", "blank-key")
            .parameter("ignored", null)
            .build();

        assertThat(request.parameters()).containsOnly(Map.entry("appEventCd", "NP1"));
    }
}
