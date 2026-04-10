package com.ktmmobile.mcp.rate.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.rate.dto.RateDTO;

@Repository
public class RateDaoImpl implements RateDao{
	@Autowired
    SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List<RateDTO> selectRateList(RateDTO dto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("RateMapper.selectRateList", dto);
	}

	@Override
	public void insertRate(RateDTO dto) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.insert("RateMapper.insertRate", dto);
	}

	@Override
	public RateDTO selectRate(RateDTO dto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("RateMapper.selectRate", dto);
	}

	@Override
	public void deleteRate(RateDTO dto) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.delete("RateMapper.deletRate", dto);
	}

	@Override
	public void updateRate(RateDTO dto) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update("RateMapper.updateRate", dto);
	}

	@Override
	public RateDTO getDetail(RateDTO dto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("RateMapper.getDetail", dto);
	}
	
	
}
