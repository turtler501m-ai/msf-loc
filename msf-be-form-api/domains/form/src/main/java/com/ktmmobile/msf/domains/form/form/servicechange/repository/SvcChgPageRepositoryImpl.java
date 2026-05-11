package com.ktmmobile.msf.domains.form.form.servicechange.repository;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;

@Repository
public class SvcChgPageRepositoryImpl {

    @Autowired
    @Qualifier("mspSqlSession")
    private SqlSessionTemplate mspSqlSession;

    @Autowired
    @Qualifier("sqlSessionTemplate")
    private SqlSessionTemplate smartformSqlSession;

    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        return mspSqlSession.selectOne("MspMyPageMapper.selectMspAddInfo", svcCntrNo);
    }

    public List<McpUserCntrMngDto> selectCntrList(Map<String, String> params) {
        return mspSqlSession.selectList("MspMyPageMapper.selectCntrList", params);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        return mspSqlSession.selectOne("MspMyPageMapper.selectCntrListNoLogin", userCntrMngDto);
    }

    public Long nextRequestKey() {
        return smartformSqlSession.selectOne("SvcChgPageMapper.nextRequestKey");
    }

    public Long nextSvcChgDtlSeq() {
        return smartformSqlSession.selectOne("SvcChgPageMapper.nextSvcChgDtlSeq");
    }
}
