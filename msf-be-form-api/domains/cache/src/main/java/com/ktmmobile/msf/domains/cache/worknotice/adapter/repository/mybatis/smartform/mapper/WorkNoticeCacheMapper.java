package com.ktmmobile.msf.domains.cache.worknotice.adapter.repository.mybatis.smartform.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.AccessAllowedIp;
import com.ktmmobile.msf.domains.cache.worknotice.domain.entity.WorkNoticeCache;

@Mapper
public interface WorkNoticeCacheMapper {

    List<WorkNoticeCache> selectListWorkNoticeCache();

    List<AccessAllowedIp> selectAccessAllowedIp(String ip);
}
