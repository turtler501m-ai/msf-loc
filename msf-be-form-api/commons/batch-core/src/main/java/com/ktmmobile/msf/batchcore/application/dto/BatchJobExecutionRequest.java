package com.ktmmobile.msf.batchcore.application.dto;

import java.time.Instant;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.util.StringUtils;

public record BatchJobExecutionRequest(
    String jobName,
    Map<String, String> parameters
) {

    private static final String DEFAULT_UNIQUE_PARAMETER_NAME = "requestedAt";

    public void validate() {
        if (!StringUtils.hasText(jobName)) {
            throw new IllegalArgumentException("배치 Job 이름은 필수입니다.");
        }
    }

    public JobParameters toJobParameters() {
        JobParametersBuilder builder = new JobParametersBuilder();

        new LinkedHashMap<>(parameters == null ? Map.of() : parameters)
            .forEach((key, value) -> {
                if (StringUtils.hasText(key) && value != null) {
                    builder.addString(key, value);
                }
            });

        if (parameters == null || !parameters.containsKey(DEFAULT_UNIQUE_PARAMETER_NAME)) {
            builder.addString(DEFAULT_UNIQUE_PARAMETER_NAME, Instant.now().toString());
        }

        return builder.toJobParameters();
    }
}
