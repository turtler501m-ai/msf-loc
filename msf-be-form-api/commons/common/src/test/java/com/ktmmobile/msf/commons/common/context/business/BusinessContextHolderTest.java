package com.ktmmobile.msf.commons.common.context.business;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BusinessContextHolderTest {

    @AfterEach
    void tearDown() {
        BusinessContextHolder.clear();
        BusinessContextHolder.restoreBoundaryDepth(0);
    }

    @Test
    void getParentScanIdReturnsEmptyWhenContextDoesNotExist() {
        BusinessContextHolder.enterBoundary();

        assertThat(BusinessContextHolder.getParentScanId()).isEmpty();
    }

    @Test
    void getParentScanIdReturnsEmptyWhenBoundaryDoesNotExist() {
        BusinessContext context = BusinessContext.empty()
            .put(BusinessContextKey.PARENT_SCAN_ID, "parent-scan-id");
        BusinessContextHolder.restore(context);

        assertThat(BusinessContextHolder.getParentScanId()).isEmpty();
    }

    @Test
    void setParentScanIdDoesNothingWhenBoundaryDoesNotExist() {
        BusinessContextHolder.setParentScanId("parent-scan-id");

        assertThat(BusinessContextHolder.getParentScanId()).isEmpty();
    }

    @Test
    void setParentScanIdStoresParentScanId() {
        BusinessContextHolder.enterBoundary();

        BusinessContextHolder.setParentScanId("parent-scan-id");

        assertThat(BusinessContextHolder.getParentScanId()).hasValue("parent-scan-id");
    }

    @Test
    void setParentScanIdClearsContextWhenValueIsBlank() {
        BusinessContextHolder.enterBoundary();
        BusinessContextHolder.setParentScanId("parent-scan-id");

        BusinessContextHolder.setParentScanId(" ");

        assertThat(BusinessContextHolder.getParentScanId()).isEmpty();
    }

    @Test
    void restoreRestoresSnapshot() {
        BusinessContextHolder.enterBoundary();
        BusinessContextHolder.setParentScanId("previous");
        BusinessContext snapshot = BusinessContextHolder.snapshot();
        BusinessContextHolder.setParentScanId("current");

        BusinessContextHolder.restore(snapshot);

        assertThat(BusinessContextHolder.getParentScanId()).hasValue("previous");
    }
}
