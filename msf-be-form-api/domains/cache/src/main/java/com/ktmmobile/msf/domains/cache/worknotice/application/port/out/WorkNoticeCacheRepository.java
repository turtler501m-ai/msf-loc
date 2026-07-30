package com.ktmmobile.msf.domains.cache.worknotice.application.port.out;

import java.util.List;

import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.AccessAllowedIp;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCache;

public interface WorkNoticeCacheRepository {

    List<WorkNoticeCache> getListWorkNoticeCache();

    List<AccessAllowedIp> getAccessAllowedIp(String ip);
}
