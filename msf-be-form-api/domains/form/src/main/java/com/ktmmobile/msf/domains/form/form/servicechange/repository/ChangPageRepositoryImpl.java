package com.ktmmobile.msf.domains.form.form.servicechange.repository;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.msp.ChangPageMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
@RequiredArgsConstructor
public class ChangPageRepositoryImpl {

    private final ChangPageMapper changPageMapper;

    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        return changPageMapper.selectMspAddInfo(svcCntrNo);
    }

    public List<McpUserCntrMngDto> selectCntrList(Map<String, String> params) {
        return changPageMapper.selectCntrList(params);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        return changPageMapper.selectCntrListNoLogin(userCntrMngDto);
    }
}
