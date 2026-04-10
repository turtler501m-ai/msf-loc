package com.ktmmobile.mcp.event.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.event.dto.PromotionDto;

@Repository
public class PromotionDaoImpl implements PromotionDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public int getDoubleCheckCtn(PromotionDto promotionDto) {
        return (Integer)sqlSessionTemplate.selectOne("PromotionMapper.getDoubleCheckCtn", promotionDto);
    }

    @Override
    public int promotionReg(PromotionDto promotionDto) {
        return (Integer)sqlSessionTemplate.insert("PromotionMapper.promotionReg", promotionDto);
    }

}
