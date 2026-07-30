package com.ktmmobile.msf.domains.form.common.dao;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpErrVO;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;

@Repository
@RequiredArgsConstructor
public class MplatFormOsstDaoImpl implements MplatFormOsstDao {

    private final McpRequestRepositoryImpl mcpRequestRepository;

    @Override
    public void insertOsstErrLog(MpErrVO mpErrVO) {
        mcpRequestRepository.insertOsstErrLog(mpErrVO);
    }

}
