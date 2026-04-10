package com.ktmmobile.mcp.combine.dao;

import com.ktmmobile.mcp.combine.dto.ReqCombineDto;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CombineDao {
    private final SqlSession bootoraSqlSession;

    public CombineDao(@Qualifier(value = "bootoraSqlSession") SqlSession bootoraSqlSession) {
        this.bootoraSqlSession = bootoraSqlSession;
    }

    public ReqCombineDto getMasterCombineLineInfo() {
        return bootoraSqlSession.selectOne("CombineMapper.getMasterCombineLineInfo");
    }

    public int insertReqCombine(ReqCombineDto reqCombineDto) {
        return bootoraSqlSession.insert("CombineMapper.insertReqCombine", reqCombineDto);
    }
}
