package com.ktmmobile.mcp.cancelConsult.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class CancelConsultDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;

    public int selectCancelConsultCount() {
        return this.bootoraSqlSession.selectOne("CancelConsultMapper.selectCancelConsultCount");
    }

    public int deleteCancelConsult() {
        return bootoraSqlSession.delete("CancelConsultMapper.deleteCancelConsult");
    }
}
