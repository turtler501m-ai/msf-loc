package com.ktmmobile.mcp.etc.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GiftDaoImpl implements GiftDao {

	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<Long> selectRateGiftPrmtSeqByIdList(List<String> prmtIdList) {
		return sqlSessionTemplate.selectList("GiftMapper.selectRateGiftPrmtSeqByIdList", prmtIdList);
	}

}
