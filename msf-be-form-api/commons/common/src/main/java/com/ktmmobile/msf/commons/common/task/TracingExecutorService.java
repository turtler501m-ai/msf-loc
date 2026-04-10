package com.ktmmobile.msf.commons.common.task;

import java.util.concurrent.Executor;

import io.micrometer.tracing.Span;
import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.task.AsyncTaskExecutor;

@RequiredArgsConstructor
public class TracingExecutorService implements AsyncTaskExecutor {

    private final Executor delegate;
    private final Tracer tracer;

    @Override
    public void execute(Runnable command) {
        Span span = tracer.nextSpan();
        delegate.execute(() -> {
            try (var ignored = tracer.withSpan(span.start())) {
                command.run();
            }
        });
    }
}
