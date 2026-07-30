package com.ktmmobile.msf.domains.form.form.servicechange.repository;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.ktmmobile.msf.commons.mybatis.config.MspMyBatisConfig;
import com.ktmmobile.msf.commons.mybatis.config.SmartFormMyBatisConfig;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.McpServiceAlterTraceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Repository
public class SvcChgPageRepositoryImpl {

    private final SqlSessionTemplate mspSqlSession;
    private final SqlSessionTemplate smartFormSqlSession;

    public SvcChgPageRepositoryImpl(
        @Qualifier(MspMyBatisConfig.SQL_SESSION_TEMPLATE) SqlSessionTemplate mspSqlSession,
        @Qualifier(SmartFormMyBatisConfig.SQL_SESSION_TEMPLATE) SqlSessionTemplate smartFormSqlSession
    ) {
        this.mspSqlSession = mspSqlSession;
        this.smartFormSqlSession = smartFormSqlSession;
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        return mspSqlSession.selectOne("MspSvcChgPageMapper.selectCntrListNoLogin", userCntrMngDto);
    }

    public McpUserCntrMngDto selectRprsPrdtInfo(McpUserCntrMngDto userCntrMngDto) {
        return mspSqlSession.selectOne("MspSvcChgPageMapper.selectRprsPrdtInfo", userCntrMngDto);
    }

    public void insertRateResChgAccessTrace(McpIpStatisticDto mcpIpStatisticDto) {
        log.debug("[SvcChgPageRepository][insertRateResChgAccessTrace] query: getEventCode={}", mcpIpStatisticDto.getEventCode());
        mspSqlSession.insert("MspSvcChgPageMapper.insertRateResChgAccessTrace", mcpIpStatisticDto);
    }

    public boolean insertServiceAlterTrace(McpServiceAlterTraceDto serviceAlterTrace) {
        int affected = smartFormSqlSession.insert("SvcChgPageMapper.insertServiceAlterTrace", serviceAlterTrace);
        return 0 < affected;
    }

    public int checkAllreadPlanchgCount(McpServiceAlterTraceDto serviceAlterTrace) {
        return smartFormSqlSession.selectOne("SvcChgPageMapper.checkAllreadPlanchgCount", serviceAlterTrace);
    }

}
