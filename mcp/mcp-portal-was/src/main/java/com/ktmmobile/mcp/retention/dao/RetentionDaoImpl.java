package com.ktmmobile.mcp.retention.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.appform.dto.AppformReqDto;
import com.ktmmobile.mcp.retention.dto.RetentionDto;

@Repository
public class RetentionDaoImpl implements RetentionDao {

    @Autowired
    SqlSessionTemplate sqlSessionTemplate;


    @Override
    public String selectRetentionUserId(RetentionDto retentionDto) {
        return sqlSessionTemplate.selectOne("RetentionMapper.selectRetentionUserId", retentionDto);
    }


    @Override
    public int insertRetentionUserId(RetentionDto retentionDto) {

        return sqlSessionTemplate.insert("RetentionMapper.insertRetentionUserId", retentionDto);
    }


    @Override
    public int updateRetentionNotice(RetentionDto retentionDto) {
        // TODO Auto-generated method stub
        return sqlSessionTemplate.update("RetentionMapper.updateRetentionNotice", retentionDto);
    }


    @Override
    public List<RetentionDto> selectApyNotiTxtList(RetentionDto retentionDto){
        return sqlSessionTemplate.selectList("RetentionMapper.selectApyNotiTxtList", retentionDto);
    }

}
