package com.ktmmobile.msf.commons.common.context.business;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BusinessContextTaskDecoratorTest {

    private final BusinessContextTaskDecorator taskDecorator = new BusinessContextTaskDecorator();

    @AfterEach
    void tearDown() {
        BusinessContextHolder.clear();
        BusinessContextHolder.restoreBoundaryDepth(0);
    }

    @Test
    void decoratePropagatesCapturedBusinessContext() {
        BusinessContextHolder.enterBoundary();
        BusinessContextHolder.setParentScanId("captured");
        Runnable decorated = taskDecorator.decorate(() ->
            assertThat(BusinessContextHolder.getParentScanId()).hasValue("captured")
        );
        BusinessContextHolder.setParentScanId("current");

        decorated.run();

        assertThat(BusinessContextHolder.getParentScanId()).hasValue("current");
    }

    @Test
    void decorateClearsBusinessContextWhenCapturedContextDoesNotExist() {
        Runnable decorated = taskDecorator.decorate(() ->
            assertThat(BusinessContextHolder.getParentScanId()).isEmpty()
        );
        BusinessContextHolder.enterBoundary();
        BusinessContextHolder.setParentScanId("current");

        decorated.run();

        assertThat(BusinessContextHolder.getParentScanId()).hasValue("current");
    }
}
