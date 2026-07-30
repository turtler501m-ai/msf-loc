package com.ktmmobile.msf.commons.common.context.business;

import org.springframework.core.task.TaskDecorator;

/**
 * Business Context 비동기 전파
 */
public class BusinessContextTaskDecorator implements TaskDecorator {

    /**
     * Business Context 캡처 작업
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        BusinessContext captured = BusinessContextHolder.snapshot();
        int capturedBoundaryDepth = BusinessContextHolder.boundaryDepthSnapshot();
        return () -> {
            BusinessContext previous = BusinessContextHolder.snapshot();
            int previousBoundaryDepth = BusinessContextHolder.boundaryDepthSnapshot();
            try {
                BusinessContextHolder.restore(captured);
                BusinessContextHolder.restoreBoundaryDepth(capturedBoundaryDepth);
                runnable.run();
            } finally {
                BusinessContextHolder.restore(previous);
                BusinessContextHolder.restoreBoundaryDepth(previousBoundaryDepth);
            }
        };
    }
}
