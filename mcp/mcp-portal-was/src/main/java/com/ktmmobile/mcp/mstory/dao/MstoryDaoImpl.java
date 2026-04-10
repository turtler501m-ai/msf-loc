package com.ktmmobile.mcp.mstory.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.mstory.dto.MstoryAttDto;
import com.ktmmobile.mcp.mstory.dto.MstoryDto;

@Repository
public class MstoryDaoImpl implements MstoryDao{

	@Autowired
	SqlSessionTemplate sqlSessionTemplate;

	@Override
	public List<MstoryDto> getMonthlyUsimList(String yY) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("MstoryMapper.getMonthlyUsimList", yY);
	}

	@Override
	public List<String> getMonthlyUsimYearList() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("MstoryMapper.getMonthlyUsimYearList");
	}

	@Override
	public MstoryDto getMonthlyUsimDetail(MstoryDto mstoryDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("MstoryMapper.getMonthlyUsimDetail", mstoryDto);
	}

	@Override
	public List<MstoryAttDto> getMonthlyUsimAttList(MstoryDto mstoryDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("MstoryMapper.getMonthlyUsimAttList", mstoryDto);
	}

	@Override
	public List<MstoryDto> getMonthlyUsimDetailList(MstoryDto mstoryDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("MstoryMapper.getMonthlyUsimDetailList", mstoryDto);
	}

	@Override
	public void modifyMonthlyUsimHit(MstoryDto mstoryDto) {
		// TODO Auto-generated method stub
		sqlSessionTemplate.update("MstoryMapper.modifyMonthlyUsimHit", mstoryDto);
	}

	@Override
	public List<MstoryDto> getSnsList(MstoryDto mstoryDto) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("MstoryMapper.getSnsList",mstoryDto);
	}
	
}
