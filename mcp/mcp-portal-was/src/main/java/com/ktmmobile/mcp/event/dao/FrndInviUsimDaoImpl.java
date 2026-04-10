package com.ktmmobile.mcp.event.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.event.dto.FrndInviUsimDto;

@Repository
public class FrndInviUsimDaoImpl implements FrndInviUsimDao {

	@Autowired
    SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int chkDup(FrndInviUsimDto frndInviUsimDto) {
        return sqlSessionTemplate.selectOne("FrndInviUsimMapper.chkDup", frndInviUsimDto);
	}

	@Override
	public int frndInviUsimReg(FrndInviUsimDto frndInviUsimDto) {
        return (Integer)sqlSessionTemplate.insert("FrndInviUsimMapper.frndInviUsimReg", frndInviUsimDto);
	}

}
