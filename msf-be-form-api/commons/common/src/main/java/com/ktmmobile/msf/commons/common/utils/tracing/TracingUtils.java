package com.ktmmobile.msf.commons.common.utils.tracing;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TracingUtils {

    private static Tracer tracer;

    static void initialize(Tracer tracer) {
        TracingUtils.tracer = tracer;
    }

    public static String getTraceId() {
        if (tracer == null) {
            return "";
        }
        Span currentSpan = tracer.currentSpan();
        if (currentSpan != null) {
            return currentSpan.context().traceId();
        }
        return "";
    }
}
