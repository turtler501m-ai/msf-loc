package com.ktmmobile.msf.domains.eformsign.support.util;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.ktmmobile.msf.domains.eformsign.feature.adapter.client.dto.EformValidateHttpResponse;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateRequest;
import com.ktmmobile.msf.domains.eformsign.feature.application.dto.EformValidateResponse;

public final class EformUtils {

    public static EformValidateResponse createValidateResponse(EformValidateRequest request, EformValidateHttpResponse response) {
        List<EformValidateHttpResponse.DocumentComponent> components = getDocumentComponents(response);

        Map<String, String> signatureValueMap = createSignatureValueMap(components);

        List<String> requestedComponentNames = getRequestedComponentNames(request);

        boolean signed = hasAllRequestedSignatures(requestedComponentNames, signatureValueMap);

        List<String> signatureValues = getSignatureValues(requestedComponentNames, signatureValueMap);

        return new EformValidateResponse(signed, signatureValues);
    }

    private static List<EformValidateHttpResponse.DocumentComponent> getDocumentComponents(EformValidateHttpResponse response) {
        if (response == null
            || response.result() == null
            || response.result().documentComponentList() == null) {
            return List.of();
        }

        return response.result().documentComponentList();
    }

    private static Map<String, String> createSignatureValueMap(List<EformValidateHttpResponse.DocumentComponent> components) {
        return components.stream()
            .filter(Objects::nonNull)
            .filter(component -> component.name() != null)
            .collect(Collectors.toMap(
                EformValidateHttpResponse.DocumentComponent::name,
                component -> Objects.toString(
                    component.value(),
                    ""
                ),
                (existing, replacement) -> existing
            ));
    }

    private static List<String> getRequestedComponentNames(EformValidateRequest request) {
        if (request == null || request.componentIds() == null) {
            return List.of();
        }

        return request.componentIds().stream()
            .filter(Objects::nonNull)
            .map(component -> component.name())
            .filter(Objects::nonNull)
            .distinct()
            .toList();
    }

    private static boolean hasAllRequestedSignatures(List<String> requestedComponentNames, Map<String, String> signatureValueMap) {
        if (requestedComponentNames.isEmpty()) {
            return false;
        }

        return requestedComponentNames.stream()
            .allMatch(componentName -> {
                String signatureValue = signatureValueMap.get(componentName);
                return signatureValue != null && !signatureValue.isBlank();
            });
    }

    private static List<String> getSignatureValues(
        List<String> requestedComponentNames,
        Map<String, String> signatureValueMap
    ) {
        return requestedComponentNames.stream()
            .map(signatureValueMap::get)
            .filter(value -> value != null && !value.isBlank())
            .toList();
    }
}
