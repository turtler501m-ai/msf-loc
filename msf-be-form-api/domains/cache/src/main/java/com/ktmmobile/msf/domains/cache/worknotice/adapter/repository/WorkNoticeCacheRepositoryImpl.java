package com.ktmmobile.msf.domains.cache.worknotice.adapter.repository;

import java.util.List;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.cache.worknotice.adapter.repository.mybatis.smartform.mapper.WorkNoticeCacheMapper;
import com.ktmmobile.msf.domains.cache.worknotice.application.port.out.WorkNoticeCacheRepository;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.AccessAllowedIp;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCache;

@RequiredArgsConstructor
@Repository
public class WorkNoticeCacheRepositoryImpl implements WorkNoticeCacheRepository {

    private final WorkNoticeCacheMapper workNoticeCacheMapper;

    @Override public List<WorkNoticeCache> getListWorkNoticeCache() {
        return workNoticeCacheMapper.selectListWorkNoticeCache();
    }

    @Override public List<AccessAllowedIp> getAccessAllowedIp(String ip) {
        return workNoticeCacheMapper.selectAccessAllowedIp(ip);
    }
}
