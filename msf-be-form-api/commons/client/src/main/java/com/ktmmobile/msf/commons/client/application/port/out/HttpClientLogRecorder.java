package com.ktmmobile.msf.commons.client.application.port.out;

import com.ktmmobile.msf.commons.client.domain.dto.HttpClientLog;

public interface HttpClientLogRecorder {

    void recordLog(HttpClientLog httpClientLog);
}
