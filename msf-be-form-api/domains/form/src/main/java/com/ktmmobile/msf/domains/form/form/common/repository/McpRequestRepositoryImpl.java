package com.ktmmobile.msf.domains.form.form.common.repository;

import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class McpRequestRepositoryImpl {

    private final MsfRequestReadMapper msfRequestReadMapper;
    private final McpRequestWriteMapper mcpRequestWriteMapper;

    public int insertMcpCancelRequest(Long requestKey) {
        McpCancelRequestVo vo = msfRequestReadMapper.selectMcpCancelRequest(requestKey);
        if (vo == null) {
            return 0;
        }
        return mcpRequestWriteMapper.insertMcpCancelRequest(vo);
    }
}
