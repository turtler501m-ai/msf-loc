package com.ktmmobile.mcp.alliance.dao;

import com.ktmmobile.mcp.alliance.dto.AlliCardDtlContDtoXML;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class AllianceCardDao {
    @Autowired
    private SqlSession bootoraSqlSession;

    public AlliCardDtlContDtoXML getPlanBannerEnabledAllianceCardXml() {
        return bootoraSqlSession.selectOne("getPlanBannerEnabledAllianceCardXml");
    }
}
