package com.ktmmobile.msf.commons.common.config;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.ktmmobile.msf.commons.common.datasource.common.DataSourceFactory;

@Slf4j
@Profile({"dev", "local"})
@Component
@RequiredArgsConstructor
public class StartupLogLevelRestorer {

    private static final String LOGGER_NAME = "jdbc.sqltiming";

    @EventListener(ApplicationReadyEvent.class)
    public void restoreJdbcSqlTimingLevel() {
        restoreToDebugAndRefreshPools("application-ready");
        CompletableFuture.delayedExecutor(1, TimeUnit.SECONDS).execute(() -> restoreToDebugAndRefreshPools("application-ready-delayed"));
    }

    private void restoreToDebugAndRefreshPools(String phase) {
        LogLevel targetLevel = LogLevel.DEBUG;
        LoggingSystem.get(getClass().getClassLoader()).setLogLevel(LOGGER_NAME, targetLevel);
        DataSourceFactory.getCreatedDataSources().forEach(dataSource -> {
            if (dataSource.getHikariPoolMXBean() != null) {
                dataSource.getHikariPoolMXBean().softEvictConnections();
            }
        });
        log.info("Restored logger '{}' to {} and refreshed hikari pools at {}", LOGGER_NAME, targetLevel, phase);
    }
}
