package com.ktmmobile.mcp.common.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.common.dto.db.BannAccessTxnDto;

@Repository
public class BannerStatDaoImpl implements BannerStatDao {

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertBannAccessTxn(BannAccessTxnDto bannAccessTxnDto) {
		return sqlSessionTemplate.insert("BannerMapper.insertBannAccessTxn",bannAccessTxnDto);
	}
}
