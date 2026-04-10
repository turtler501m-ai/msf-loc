package com.ktmmobile.mcp.texting.dao;

import java.util.List;


import org.mybatis.spring.SqlSessionTemplate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import com.ktmmobile.mcp.texting.dto.TextingDto;


@Repository
public class TextingDao {

    @Autowired
    @Qualifier(value="bootoraSqlSession")
    private SqlSessionTemplate bootoraSqlSession;

    public List<TextingDto> selectTextingNoList(String startDt) {
        return bootoraSqlSession.selectList("TextingMapper.selectTextingNoList",startDt);
    }

    public int getMnpLimitDayCnt(String nowDate) {
        return bootoraSqlSession.selectOne("AcenMapper.getMnpLimitDayCnt", nowDate);
    }

}
