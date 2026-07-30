package com.ktmmobile.msf.batchcore.application.dto;

public record BatchJobExecutionResponse(
    String jobName,
    Long executionId
) {

    public static BatchJobExecutionResponse of(String jobName, Long executionId) {
        return new BatchJobExecutionResponse(jobName, executionId);
    }
}
