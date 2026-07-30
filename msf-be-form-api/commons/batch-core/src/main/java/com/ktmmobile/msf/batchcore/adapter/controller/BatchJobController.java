package com.ktmmobile.msf.batchcore.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.msf.batchcore.application.dto.BatchJobExecutionRequest;
import com.ktmmobile.msf.batchcore.application.dto.BatchJobExecutionResponse;
import com.ktmmobile.msf.commons.websecurity.web.dto.response.CommonResponse;
import com.ktmmobile.msf.commons.websecurity.web.util.response.ResponseUtils;

@RestController
@RequestMapping("/api/batch/jobs")
@RequiredArgsConstructor
public class BatchJobController {

    private final JobRegistry jobRegistry;
    private final JobOperator jobOperator;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/start")
    public CommonResponse<BatchJobExecutionResponse> startJob(
        @RequestBody BatchJobExecutionRequest request
    ) throws NoSuchJobException, InvalidJobParametersException,
        JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobRestartException {

        request.validate();

        Job job = findJob(request.jobName());
        JobExecution jobExecution = jobOperator.start(job, request.toJobParameters());
        return ResponseUtils.ok(BatchJobExecutionResponse.of(request.jobName(), jobExecution.getId()));
    }

    private Job findJob(String jobName) throws NoSuchJobException {
        Job job = jobRegistry.getJob(jobName);
        if (job == null) {
            throw new NoSuchJobException("No such job: " + jobName);
        }
        return job;
    }
}
