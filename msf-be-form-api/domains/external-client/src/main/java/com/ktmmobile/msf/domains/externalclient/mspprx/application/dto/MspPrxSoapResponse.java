package com.ktmmobile.msf.domains.externalclient.mspprx.application.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * MSP PRX SOAP 응답 값 객체
 *
 * @param serviceAlterTraceId 저장된 서비스 변경 이력 식별자
 * @param operationName SOAP Body의 응답 operation 이름
 * @param bizHeader bizHeader 하위 응답 값
 * @param commHeader commHeader 하위 응답 값
 * @param payload return 하위 업무 응답 값
 * @param rawXml PRX에서 받은 원문 XML
 */
public record MspPrxSoapResponse(
    ServiceAlterTraceId serviceAlterTraceId,
    String operationName,
    Map<String, String> bizHeader,
    Map<String, String> commHeader,
    Map<String, Object> payload,
    String rawXml
) {

    private static final String SUCCESS_RESPONSE_TYPE = "N";

    public MspPrxSoapResponse {
        bizHeader = bizHeader == null ? Map.of() : Map.copyOf(bizHeader);
        commHeader = commHeader == null ? Map.of() : Map.copyOf(commHeader);
        payload = payload == null ? Map.of() : Map.copyOf(payload);
    }

    public boolean success() {
        return SUCCESS_RESPONSE_TYPE.equals(responseType());
    }

    public String responseType() {
        return commHeader.get("responseType");
    }

    public String responseCode() {
        return commHeader.get("responseCode");
    }

    public String responseBasic() {
        return commHeader.get("responseBasic");
    }

    public String globalNo() {
        return commHeader.get("globalNo");
    }

    public MspPrxSoapResponse withServiceAlterTraceId(ServiceAlterTraceId serviceAlterTraceId) {
        return new MspPrxSoapResponse(serviceAlterTraceId, operationName, bizHeader, commHeader, payload, rawXml);
    }

    /**
     * payload 문자열 값 조회
     * 반복 노드 경로의 첫 번째 문자열 값
     *
     * @param firstName payload 최상위 노드명
     * @param childNames 하위 노드 경로
     * @return 문자열 payload 값
     */
    public Optional<String> payloadText(String firstName, String... childNames) {
        return payloadValue(firstName, childNames)
            .flatMap(this::firstText);
    }

    /**
     * payload 객체 값 조회
     *
     * @param firstName payload 최상위 노드명
     * @param childNames 하위 노드 경로
     * @return 객체 payload 값
     */
    @SuppressWarnings("unchecked")
    public Optional<Map<String, Object>> payloadObject(String firstName, String... childNames) {
        return payloadValue(firstName, childNames)
            .filter(Map.class::isInstance)
            .map(value -> (Map<String, Object>) value);
    }

    /**
     * payload 반복 값 조회
     * 중간 반복 노드의 하위 경로 결과 평탄화
     *
     * @param firstName payload 최상위 노드명
     * @param childNames 하위 노드 경로
     * @return 반복 payload 값
     */
    @SuppressWarnings("unchecked")
    public Optional<List<Object>> payloadList(String firstName, String... childNames) {
        return payloadValue(firstName, childNames)
            .filter(List.class::isInstance)
            .map(value -> (List<Object>) value);
    }

    /**
     * payload 값 조회
     * 동일 이름 XML 노드는 List 반환
     *
     * @param firstName payload 최상위 노드명
     * @param childNames 하위 노드 경로
     * @return payload 값
     */
    public Optional<Object> payloadValue(String firstName, String... childNames) {
        return resolveValue(payload.get(firstName), childNames, 0);
    }

    private Optional<Object> resolveValue(Object current, String[] childNames, int depth) {
        if (current == null) {
            return Optional.empty();
        }

        if (depth == childNames.length) {
            return Optional.of(current);
        }

        if (current instanceof Map<?, ?> currentMap) {
            return resolveValue(currentMap.get(childNames[depth]), childNames, depth + 1);
        }

        if (current instanceof List<?> currentList) {
            List<Object> values = new ArrayList<>();
            for (Object item: currentList) {
                resolveValue(item, childNames, depth).ifPresent(value -> addValue(values, value));
            }
            return values.isEmpty() ? Optional.empty() : Optional.of(values);
        }

        return Optional.empty();
    }

    private void addValue(List<Object> values, Object value) {
        if (value instanceof List<?> list) {
            values.addAll(list);
            return;
        }
        values.add(value);
    }

    private Optional<String> firstText(Object value) {
        if (value instanceof String text) {
            return Optional.of(text);
        }

        if (value instanceof List<?> list) {
            return list.stream()
                .filter(String.class::isInstance)
                .map(String.class::cast)
                .findFirst();
        }

        return Optional.empty();
    }
}
