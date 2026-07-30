package com.ktmmobile.msf.domains.externalclient.mspprx.application.dto;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DisplayName("MSP PRX JSON 요청")
class MspPrxJsonRequestTest {

    @Test
    @DisplayName("Builder로 JSON 요청 필드를 하나씩 추가할 수 있다")
    void buildJsonRequestWithSingularProperties() {
        MspPrxJsonRequest request = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y52")
            .property("custId", "1000000001")
            .property("ctn", "01012345678")
            .property("custNm", "홍길동")
            .build();

        assertThat(request.toJsonBody())
            .containsEntry("appEventCd", "Y52")
            .containsEntry("custId", "1000000001")
            .containsEntry("ctn", "01012345678")
            .containsEntry("custNm", "홍길동");
    }

    @Test
    @DisplayName("배열형 필드를 포함한 JSON 요청을 만들 수 있다")
    void buildJsonRequestWithArrayProperty() {
        List<Map<String, Object>> prdcList = List.of(
            Map.of(
                "prdcCd", "PROD001",
                "prdcSeqNo", "1"
            )
        );

        MspPrxJsonRequest request = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y24")
            .property("prdcList", prdcList)
            .build();

        assertThat(request.property("prdcList")).contains(prdcList);
        assertThat(request.toJsonBody()).containsEntry("prdcList", prdcList);
    }

    @Test
    @DisplayName("빈 필드명과 null 값은 JSON 요청에서 제외한다")
    void filterBlankKeyAndNullValue() {
        MspPrxJsonRequest request = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y52")
            .property(" ", "blank-key")
            .property("ignored", null)
            .build();

        assertThat(request.toJsonBody())
            .containsOnly(Map.entry("appEventCd", "Y52"));
    }

    @Test
    @DisplayName("JSON 요청 body는 외부에서 수정할 수 없다")
    void jsonBodyIsImmutable() {
        MspPrxJsonRequest request = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y52")
            .build();

        assertThatThrownBy(() -> request.toJsonBody().put("custId", "1000000001"))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
