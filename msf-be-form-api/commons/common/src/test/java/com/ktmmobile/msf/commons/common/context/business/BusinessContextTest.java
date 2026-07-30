package com.ktmmobile.msf.commons.common.context.business;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BusinessContextTest {

    @Test
    void putStoresValueByKey() {
        BusinessContext context = BusinessContext.empty()
            .put(BusinessContextKey.PARENT_SCAN_ID, "parent-scan-id");

        assertThat(context.get(BusinessContextKey.PARENT_SCAN_ID)).hasValue("parent-scan-id");
    }

    @Test
    void putRemovesValueWhenValueIsBlank() {
        BusinessContext context = BusinessContext.empty()
            .put(BusinessContextKey.PARENT_SCAN_ID, "parent-scan-id");

        BusinessContext removed = context.put(BusinessContextKey.PARENT_SCAN_ID, " ");

        assertThat(removed.get(BusinessContextKey.PARENT_SCAN_ID)).isEmpty();
        assertThat(removed.isEmpty()).isTrue();
    }

    @Test
    void valuesAreCopiedOnCreate() {
        Map<BusinessContextKey, String> values = new EnumMap<>(BusinessContextKey.class);
        values.put(BusinessContextKey.PARENT_SCAN_ID, "parent-scan-id");

        BusinessContext context = new BusinessContext(values);
        values.put(BusinessContextKey.PARENT_SCAN_ID, "changed");

        assertThat(context.get(BusinessContextKey.PARENT_SCAN_ID)).hasValue("parent-scan-id");
    }

    @Test
    void valuesAreImmutable() {
        BusinessContext context = BusinessContext.empty()
            .put(BusinessContextKey.PARENT_SCAN_ID, "parent-scan-id");

        assertThatThrownBy(() -> context.values().put(BusinessContextKey.PARENT_SCAN_ID, "changed"))
            .isInstanceOf(UnsupportedOperationException.class);
    }
}
