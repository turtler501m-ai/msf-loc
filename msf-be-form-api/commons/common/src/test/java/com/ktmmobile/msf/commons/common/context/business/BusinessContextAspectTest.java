package com.ktmmobile.msf.commons.common.context.business;

import org.aspectj.lang.ProceedingJoinPoint;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class BusinessContextAspectTest {

    private final BusinessContextAspect aspect = new BusinessContextAspect();

    @AfterEach
    void tearDown() {
        BusinessContextHolder.clear();
        BusinessContextHolder.restoreBoundaryDepth(0);
    }

    @Test
    void restoreBusinessContextRestoresPreviousContext() throws Throwable {
        BusinessContextHolder.enterBoundary();
        BusinessContextHolder.setParentScanId("previous");
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.proceed()).thenAnswer(invocation -> {
            BusinessContextHolder.setParentScanId("current");
            return "result";
        });

        Object result = aspect.restoreBusinessContext(joinPoint, null);

        assertThat(result).isEqualTo("result");
        assertThat(BusinessContextHolder.getParentScanId()).hasValue("previous");
    }

    @Test
    void restoreBusinessContextClearsContextWhenPreviousContextDoesNotExist() throws Throwable {
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.proceed()).thenAnswer(invocation -> {
            BusinessContextHolder.setParentScanId("current");
            return null;
        });

        aspect.restoreBusinessContext(joinPoint, null);

        assertThat(BusinessContextHolder.getParentScanId()).isEmpty();
    }

    @Test
    void restoreBusinessContextRestoresPreviousContextWhenExceptionOccurs() throws Throwable {
        BusinessContextHolder.enterBoundary();
        BusinessContextHolder.setParentScanId("previous");
        ProceedingJoinPoint joinPoint = mock(ProceedingJoinPoint.class);
        when(joinPoint.proceed()).thenAnswer(invocation -> {
            BusinessContextHolder.setParentScanId("current");
            throw new IllegalStateException("failed");
        });

        assertThatThrownBy(() -> aspect.restoreBusinessContext(joinPoint, null))
            .isInstanceOf(IllegalStateException.class)
            .hasMessage("failed");
        assertThat(BusinessContextHolder.getParentScanId()).hasValue("previous");
    }
}
