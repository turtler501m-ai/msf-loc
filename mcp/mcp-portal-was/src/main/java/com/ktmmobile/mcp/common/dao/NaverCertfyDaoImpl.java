package com.ktmmobile.mcp.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.dto.NaverDto;

@Repository
public class NaverCertfyDaoImpl implements NaverCertfyDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public boolean updateMcpAuthData(NaverDto naverDto){
        return  0 < sqlSessionTemplate.update("NaverCertfyMapper.updateMcpAuth", naverDto);
    }

    @Override
    public NaverDto getMcpAuthData(NaverDto naverDto){
        return sqlSessionTemplate.selectOne("NaverCertfyMapper.getMcpAuth" ,naverDto);
    }
}
