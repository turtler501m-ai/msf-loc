package com.ktmmobile.mcp.texting.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.texting.dto.FormDtlDTO;

@Repository
public class FormDtlDaoImpl implements FormDtlDao {
    @Autowired
    SqlSessionTemplate sqlSessionTemplate;

    @Override
    public FormDtlDTO FormDtlView(FormDtlDTO formDtlDTO){
        return sqlSessionTemplate.selectOne("FormDtlMapper.FormDtlView", formDtlDTO);
    }

    @Override
    public List<FormDtlDTO> getFormList(FormDtlDTO formDtlDTO) {
        return sqlSessionTemplate.selectList("FormDtlMapper.getFormList", formDtlDTO);
    }

}
