package com.ktmmobile.msf.commons.common.context.business;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * Business Context 경계 처리
 */
@Aspect
@Component
public class BusinessContextAspect {

    /**
     * Business Context 복원
     */
    @Around("@annotation(businessContextBoundary)")
    public Object restoreBusinessContext(
        ProceedingJoinPoint joinPoint,
        BusinessContextBoundary businessContextBoundary
    ) throws Throwable {
        BusinessContext previous = BusinessContextHolder.snapshot();
        int previousBoundaryDepth = BusinessContextHolder.boundaryDepthSnapshot();
        BusinessContextHolder.enterBoundary();
        try {
            return joinPoint.proceed();
        } finally {
            BusinessContextHolder.restore(previous);
            BusinessContextHolder.restoreBoundaryDepth(previousBoundaryDepth);
        }
    }
}
