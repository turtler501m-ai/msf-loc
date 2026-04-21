package com.ktmmobile.msf.commons.common.startup;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class StartupLoadRunner implements ApplicationRunner {

    private final List<StartupLoadTask> startupLoadTasks;
    private final StartupLoadProperties startupLoadProperties;

    @Override
    public void run(@NonNull ApplicationArguments args) {
        startupLoadTasks.stream()
            .sorted(AnnotationAwareOrderComparator.INSTANCE)
            .forEach(this::runTask);
    }

    private void runTask(StartupLoadTask startupLoadTask) {
        if (startupLoadProperties.isExcluded(startupLoadTask.key())) {
            log.info(">>> Startup Load Skip. key={}", startupLoadTask.key());
            return;
        }

        log.info(">>> Startup Load Start. key={}", startupLoadTask.key());
        startupLoadTask.load();
    }
}
