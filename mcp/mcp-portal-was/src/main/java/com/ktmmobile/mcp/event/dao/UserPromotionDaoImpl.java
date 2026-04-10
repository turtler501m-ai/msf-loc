package com.ktmmobile.mcp.event.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import com.ktmmobile.mcp.event.dto.UserPromotionDto;

@Repository
public class UserPromotionDaoImpl implements UserPromotionDao {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Override
    public int insertUserPromotion(UserPromotionDto userPromotionDto) {
        sqlSessionTemplate.insert("UserPromotionMapper.insertUserPromotion",userPromotionDto);
        return 1;
    }

    @Override
    public int selectUserPromoCnt(UserPromotionDto userPromotionDto) {
        return (Integer)sqlSessionTemplate.selectOne("UserPromotionMapper.selectUserPromoCnt", userPromotionDto);
    }

    @Override
    public int deleteUserPromotion(UserPromotionDto userPromotionDto) {
        return sqlSessionTemplate.delete("UserPromotionMapper.deleteUserPromotion", userPromotionDto);
    }

    @Override
    public List<UserPromotionDto> selectUserPromotionList(UserPromotionDto userPromotiondto){
        return sqlSessionTemplate.selectList("UserPromotionMapper.selectUserPromotionList",userPromotiondto);
    }



}
