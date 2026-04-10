package com.ktmmobile.mcp.common.terms.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.appform.dto.FormDtlDTO;

@Repository
public class TermsDaoImpl implements TermsDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    
    @Override
    public FormDtlDTO getTermsDtl(FormDtlDTO formDtlDTO){
        return sqlSessionTemplate.selectOne("TermsMapper.getTermsDtl", formDtlDTO);
    }

   


}
