package com.ktmmobile.msf.domains.cache.worknotice.application.port.in;

import com.ktmmobile.msf.domains.cache.worknotice.application.dto.WorkNoticeCacheRequest;
import com.ktmmobile.msf.domains.cache.worknotice.application.dto.WorkNoticeCacheResponse;

public interface WorkNoticeCacheReader {

    WorkNoticeCacheResponse getListWorkNotice(WorkNoticeCacheRequest request);
}
