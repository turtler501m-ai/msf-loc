package com.ktmmobile.msf.domains.shared.form.common.complete.application.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import lombok.With;

import com.ktmmobile.msf.domains.shared.form.common.complete.domain.code.RequestFormType;

public record CompletedFormResponse(
    RequestFormType formType,
    String requestKey,
    String baseDocumentId,
    List<String> documentId,
    String name,
    @With List<CompletedFormMobileInfo> mobiles
) {

    public static CompletedFormResponse of(RequestFormType formType, Long requestKey, String baseDocumentId, String documentId, String name) {
        return CompletedFormResponse.of(formType, requestKey, baseDocumentId, documentId, name, null);
    }

    public static CompletedFormResponse of(
        RequestFormType formType,
        Long requestKey,
        String baseDocumentId,
        String documentId,
        String name,
        List<CompletedFormMobileInfo> mobiles
    ) {
        List<String> documentIds = normalizeDocumentIds(baseDocumentId, documentId);
        return new CompletedFormResponse(formType, String.valueOf(requestKey), baseDocumentId, documentIds, name, mobiles);
    }

    private static List<String> normalizeDocumentIds(String baseDocumentId, String documentId) {
        Set<String> ids = new LinkedHashSet<>();
        addIfPresent(ids, baseDocumentId);
        if (documentId != null) {
            Arrays.stream(documentId.split(","))
                .forEach(id -> addIfPresent(ids, id));
        }
        return new ArrayList<>(ids);
    }

    private static void addIfPresent(Set<String> ids, String id) {
        if (id != null && !id.isBlank()) {
            ids.add(id);
        }
    }

    public record CompletedFormMobileInfo(
        String name,
        String mobile
    ) {

        public static CompletedFormMobileInfo of(String name, String mobile) {
            return new CompletedFormMobileInfo(name, mobile);
        }
    }
}
