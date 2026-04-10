package com.ktmmobile.mcp.document.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DocumentReceiveDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSession bootoraSqlSession;

    public int expireDocRcvUrl(String batchExeDate) {
        return bootoraSqlSession.update("DocumentReceiveMapper.expireDocRcvUrl", batchExeDate);
    }

    public List<String> getExpiredDocRcvIds(String batchExeDate) {
        return bootoraSqlSession.selectList("DocumentReceiveMapper.getExpiredDocRcvIds", batchExeDate);
    }

    public int syncDocRcvStatusExpired(List<String> docRcvIds) {
        return bootoraSqlSession.update("DocumentReceiveMapper.syncDocRcvStatusExpired", docRcvIds);
    }

    public int syncDocRcvStatusIncompleted(List<String> docRcvIds) {
        return bootoraSqlSession.update("DocumentReceiveMapper.syncDocRcvStatusIncompleted", docRcvIds);
    }
}
