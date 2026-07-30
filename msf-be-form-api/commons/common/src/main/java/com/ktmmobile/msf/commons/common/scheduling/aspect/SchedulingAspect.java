package com.ktmmobile.msf.commons.common.scheduling.aspect;

import java.lang.reflect.Method;
import java.util.stream.Stream;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.scheduling.annotation.SchedulingGroup;
import com.ktmmobile.msf.commons.common.scheduling.properties.SchedulingProperties;

/**
 * 전역 및 그룹 설정에 따라 {@code @Scheduled} 메서드 실행 제어
 */
@Slf4j
@RequiredArgsConstructor
@Aspect
@Component
public class SchedulingAspect {

    private final SchedulingProperties schedulingProperties;

    @Around("@annotation(org.springframework.scheduling.annotation.Scheduled)")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {
        String classMethodName = joinPoint.getSignature().toShortString();
        boolean schedulingEnabled = false;
        try {
            schedulingEnabled = isSchedulingEnabled(joinPoint, classMethodName);
            if (!schedulingEnabled) {
                return null;
            }

            log.info("Scheduler started: {}", classMethodName);
            return joinPoint.proceed();
        } catch (Exception e) {
            log.error("Scheduler failed: {}", classMethodName, e);
            throw e;
        } finally {
            if (schedulingEnabled) {
                log.info("Scheduler finished: {}", classMethodName);
            }
        }
    }

    private boolean isSchedulingEnabled(JoinPoint joinPoint, String classMethodName) {
        return isAllSchedulingEnabled(classMethodName)
            && isGroupSchedulingEnabled(joinPoint, classMethodName);
    }

    private boolean isAllSchedulingEnabled(String classMethodName) {
        boolean enabled = schedulingProperties.enabled();
        if (!enabled) {
            log.info("Scheduler disabled: {} -> scheduling.enabled=false", classMethodName);
        }
        return enabled;
    }

    private boolean isGroupSchedulingEnabled(JoinPoint joinPoint, String classMethodName) {
        SchedulingGroup annotation = getSchedulingGroupAnnotation(joinPoint);
        if (annotation == null) {
            return true;
        }

        boolean enabled = Stream.of(annotation.groupNames()).allMatch(schedulingProperties::isGroupEnabled);
        if (!enabled) {
            log.info("Scheduler disabled: {} -> @SchedulingGroup, scheduling.groups.*.enabled=false", classMethodName);
        }
        return enabled;
    }

    private static SchedulingGroup getSchedulingGroupAnnotation(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        return AnnotationUtils.findAnnotation(method.getDeclaringClass(), SchedulingGroup.class);
    }
}
