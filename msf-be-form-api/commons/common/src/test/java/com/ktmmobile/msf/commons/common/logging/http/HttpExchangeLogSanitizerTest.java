package com.ktmmobile.msf.commons.common.logging.http;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import static org.assertj.core.api.Assertions.assertThat;

class HttpExchangeLogSanitizerTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Test
    @DisplayName("헤더 값은 include 규칙의 노출 길이에 맞춰 마스킹된다")
    void masksHeaderValueByIncludeRule() {
        HttpExchangeLogSanitizer sanitizer = newSanitizer(
            matchRules(List.of("Authorization[2]")),
            emptyRules(),
            emptyRules()
        );

        Map<String, List<String>> headers = sanitizer.copyHeaders(Map.of(
            "Authorization", List.of("1234567890"),
            "User-Agent", List.of("test-agent")
        ));

        assertThat(headers)
            .containsEntry("Authorization", List.of("12...90(length=10)"))
            .doesNotContainKey("User-Agent");
    }

    @Test
    @DisplayName("JSON Body는 민감 필드를 마스킹하고 제외 필드와 축약 필드를 구분한다")
    void masksAndTruncatesJsonBodyFields() throws Exception {
        HttpExchangeLogSanitizer sanitizer = newSanitizer(
            emptyRules(),
            matchRules(List.of("password", "token", "fieldResults.value"), List.of("tokenType")),
            truncateRules(List.of("message[3]", "payload[8]", "fieldResults.displayName[2]"))
        );
        Map<String, List<String>> headers = Map.of("Content-Type", List.of("application/json"));
        String body = """
            {
              "password": "secret-value",
              "tokenType": "Bearer",
              "message": "abcdefghijklmnopqrstuvwxyz",
              "payload": {
                "content": "abcdefghijklmnopqrstuvwxyz"
              },
              "plain": {
                "value": "visible"
              },
              "fieldResults": [
                {
                  "displayName": "주민등록번호",
                  "value": "920225-1234567"
                }
              ]
            }
            """;

        String sanitizedBody = sanitizer.toText(headers, body.getBytes(StandardCharsets.UTF_8));
        JsonNode rootNode = objectMapper.readTree(sanitizedBody);

        assertThat(rootNode.get("password").asString()).isEqualTo("******(length=12)");
        assertThat(rootNode.get("tokenType").asString()).isEqualTo("Bearer");
        assertThat(rootNode.get("message").asString()).isEqualTo("abc...xyz(length=26)");
        assertThat(rootNode.get("payload").asString()).startsWith("{\"conten");
        assertThat(rootNode.get("payload").asString()).contains("(length=");
        assertThat(rootNode.get("plain").get("value").asString()).isEqualTo("visible");
        assertThat(rootNode.get("fieldResults").get(0).get("displayName").asString()).isEqualTo("주민...번호(length=6)");
        assertThat(rootNode.get("fieldResults").get(0).get("value").asString()).isEqualTo("******(length=14)");
    }

    @Test
    @DisplayName("잘린 JSON Body도 문자열 필드 패턴을 기준으로 민감 필드를 마스킹하고 축약한다")
    void masksAndTruncatesPartialJsonBodyFields() {
        HttpExchangeLogSanitizer sanitizer = newSanitizer(
            emptyRules(),
            matchRules(List.of("password")),
            truncateRules(List.of("customerNm[1]", "customerBirth[:4]", "customerRrn[7:]", "imageBase64[10]"))
        );
        Map<String, List<String>> headers = Map.of("Content-Type", List.of("application/json"));
        String body = """
            {"result":true,"password":"secret-value","customerNm":"홍길동","customerBirth":"19950707","customerRrn":"90121211234567","imageBase64":"iVBORw0KGgoAAAANSUhEUgAABCYAAAKh
            """;

        String sanitizedBody = sanitizer.toText(headers, body.getBytes(StandardCharsets.UTF_8));

        assertThat(sanitizedBody)
            .contains("\"password\":\"******(length=12)\"")
            .contains("\"customerNm\":\"홍...동(length=3)\"")
            .contains("\"customerBirth\":\"...0707(length=8)\"")
            .contains("\"customerRrn\":\"9012121...(length=14)\"")
            .contains("\"imageBase64\":\"iVBORw0KGg...(length>=")
            .doesNotContain("secret-value")
            .doesNotContain("90121211234567");
    }

    @Test
    @DisplayName("suffix 규칙의 JSON 문자열 값이 잘린 경우에는 알 수 없는 suffix를 원문으로 남기지 않는다")
    void doesNotExposeIncompleteSuffixOnlyJsonValue() {
        HttpExchangeLogSanitizer sanitizer = newSanitizer(
            emptyRules(),
            emptyRules(),
            truncateRules(List.of("customerBirth[:4]"))
        );
        Map<String, List<String>> headers = Map.of("Content-Type", List.of("application/json"));
        String body = "{\"customerBirth\":\"1995";

        String sanitizedBody = sanitizer.toText(headers, body.getBytes(StandardCharsets.UTF_8));

        assertThat(sanitizedBody)
            .contains("\"customerBirth\":\"...(length>=4)")
            .doesNotContain("1995");
    }

    private HttpExchangeLogSanitizer newSanitizer(
        HttpLogMatchRule headerNames,
        HttpLogMatchRule bodyMaskedFields,
        HttpLogMatchRule bodyTruncatedFields
    ) {
        return new HttpExchangeLogSanitizer(objectMapper, headerNames, bodyMaskedFields, bodyTruncatedFields);
    }

    private HttpLogMatchRule matchRules(List<String> include) {
        return matchRules(include, List.of());
    }

    private HttpLogMatchRule matchRules(List<String> include, List<String> exclude) {
        return new HttpLogMatchRule(include, Map.of(), exclude, Map.of(), 0);
    }

    private HttpLogMatchRule emptyRules() {
        return new HttpLogMatchRule(List.of(), Map.of(), List.of(), Map.of(), 0);
    }

    private HttpLogMatchRule truncateRules(List<String> include) {
        return new HttpLogMatchRule(include, Map.of(), List.of(), Map.of(), 0);
    }
}
