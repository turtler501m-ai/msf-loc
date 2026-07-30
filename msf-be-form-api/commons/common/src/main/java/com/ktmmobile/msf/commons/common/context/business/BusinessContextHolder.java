package com.ktmmobile.msf.commons.common.context.business;

import java.util.Optional;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Business Context Holder
 */
@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BusinessContextHolder {

    private static final ThreadLocal<BusinessContext> CONTEXT = new ThreadLocal<>();
    private static final ThreadLocal<Integer> BOUNDARY_DEPTH = new ThreadLocal<>();

    /**
     * Parent Scan ID 조회
     */
    public static Optional<String> getParentScanId() {
        if (!isBoundaryActive()) {
            return Optional.empty();
        }
        return Optional.ofNullable(CONTEXT.get())
            .flatMap(context -> context.get(BusinessContextKey.PARENT_SCAN_ID));
    }

    /**
     * Parent Scan ID 저장
     */
    public static void setParentScanId(String parentScanId) {
        if (!isBoundaryActive()) {
            log.warn("Business Context boundary is not active key={}", BusinessContextKey.PARENT_SCAN_ID);
            return;
        }
        BusinessContext context = current().put(BusinessContextKey.PARENT_SCAN_ID, parentScanId);
        if (context.isEmpty()) {
            CONTEXT.remove();
            return;
        }
        CONTEXT.set(context);
    }

    /**
     * Business Context 제거
     */
    public static void clear() {
        CONTEXT.remove();
    }

    /**
     * Business Context 스냅샷
     */
    static BusinessContext snapshot() {
        return CONTEXT.get();
    }

    /**
     * Business Context Boundary 스냅샷
     */
    static int boundaryDepthSnapshot() {
        Integer depth = BOUNDARY_DEPTH.get();
        return depth == null ? 0 : depth;
    }

    /**
     * Business Context Boundary 진입
     */
    static void enterBoundary() {
        BOUNDARY_DEPTH.set(boundaryDepthSnapshot() + 1);
    }

    /**
     * Business Context 복원
     */
    static void restore(BusinessContext context) {
        if (context == null || context.isEmpty()) {
            clear();
            return;
        }
        CONTEXT.set(context);
    }

    /**
     * Business Context Boundary 복원
     */
    static void restoreBoundaryDepth(int depth) {
        if (depth <= 0) {
            BOUNDARY_DEPTH.remove();
            return;
        }
        BOUNDARY_DEPTH.set(depth);
    }

    /**
     * Business Context Boundary 활성 여부
     */
    private static boolean isBoundaryActive() {
        return boundaryDepthSnapshot() > 0;
    }

    /**
     * 현재 Business Context
     */
    private static BusinessContext current() {
        BusinessContext context = CONTEXT.get();
        return context == null ? BusinessContext.empty() : context;
    }
}
