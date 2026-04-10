package com.ktmmobile.mcp.benefit.dao;

import com.ktmmobile.mcp.benefit.dto.BenefitTabViewDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BenefitDaoImpl implements BenefitDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Override
    public List<BenefitTabViewDto> getBenefitTabViewCountList() {
        return sqlSessionTemplate.selectList("BenefitMapper.getBenefitTabViewCountList");
    }

    @Override
    public int increaseTabViewCount(String tabName) {
        return sqlSessionTemplate.update("BenefitMapper.increaseTabViewCount", tabName);
    }
}
