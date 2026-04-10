package com.ktmmobile.mcp.jehu.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;


@Repository
public class JehuDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSessionTemplate bootoraSqlSession;


    public int selectJehuSuggCount() {
        return this.bootoraSqlSession.selectOne("JehuMapper.selectJehuSuggCount");
    }

    public String selectJehuManager() {
        return this.bootoraSqlSession.selectOne("JehuMapper.selectJehuManager");
    }

    public int selectDelCount() {
        return this.bootoraSqlSession.selectOne("JehuMapper.selectDelCount");
    }

    public int deleteJehuData() {
        return bootoraSqlSession.delete("JehuMapper.deleteJehuData");
    }
}
