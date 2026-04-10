package com.ktmmobile.msf.commons.common.task;

import java.util.concurrent.ThreadPoolExecutor;

import io.micrometer.tracing.Tracer;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.task.TaskExecutionProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@EnableAsync
@RequiredArgsConstructor
@Configuration(proxyBeanMethods = false)
public class TaskExecutorConfig {

    private final TaskExecutionProperties taskExecutionProperties;
    private final ObjectProvider<Tracer> tracerProvider;

    @Primary
    @Bean
    public AsyncTaskExecutor defaultTaskExecutor() {
        ThreadPoolTaskExecutor threadPoolExecutor = new ThreadPoolTaskExecutor();
        threadPoolExecutor.setCorePoolSize(taskExecutionProperties.getPool().getCoreSize());
        threadPoolExecutor.setMaxPoolSize(taskExecutionProperties.getPool().getMaxSize());
        threadPoolExecutor.setQueueCapacity(taskExecutionProperties.getPool().getQueueCapacity());
        threadPoolExecutor.setThreadNamePrefix(taskExecutionProperties.getThreadNamePrefix());
        threadPoolExecutor.setWaitForTasksToCompleteOnShutdown(taskExecutionProperties.getShutdown().isAwaitTermination());
        threadPoolExecutor.setAwaitTerminationSeconds((int) taskExecutionProperties.getShutdown().getAwaitTerminationPeriod().toSeconds());
        threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        threadPoolExecutor.initialize();
        Tracer tracer = tracerProvider.getIfAvailable();
        if (tracer == null) {
            return threadPoolExecutor;
        }
        return new TracingExecutorService(threadPoolExecutor, tracer);
    }
}
