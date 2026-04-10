package com.ktmmobile.mcp.event.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.event.dto.AgreeTermsEventDto;

@Repository
public class AgreeTermsEventDaoImpl implements AgreeTermsEventDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int agreeJoinInsert(AgreeTermsEventDto agreeTermsEventDto) {
        sqlSessionTemplate.insert("AgreeTermsEventMapper.agreeJoinInsert",agreeTermsEventDto);
        return 1;
    }

}
