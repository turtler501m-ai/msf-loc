package com.ktmmobile.mcp.main.dao;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.ktmmobile.mcp.main.dto.NmcpRecommendProdDTO;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrRelDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgRelDTO;

@Repository
public class MainDaoImpl implements MainDao {
	
    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;
	
    /**
	 * 설명 : 추천 상품 카테고리 2Depth 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	@Override
	public List<NmcpRecommendProdCtgBasDTO> selectRecommendProdCtgScndList(NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO) {
		return sqlSessionTemplate.selectList("MainMapper.selectRecommendProdCtgScndList", nmcpRecommendProdCtgBasDTO);
	}

	/**
	 * 설명 : 추천 상품 카테고리 요금제 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	@Override
	public List<NmcpRecommendProdCtgRelDTO> selectRecommendProdCtgRelList(NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO) {
		return sqlSessionTemplate.selectList("MainMapper.selectRecommendProdCtgRelList", nmcpRecommendProdCtgBasDTO);
	}

	/**
	 * 설명 : 추천 휴대폰/유심 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgRelDTO
	 * @return
	 */ 
	@Override
	public NmcpRecommendProdDTO selectNmcpProdCommend(NmcpRecommendProdCtgRelDTO nmcpRecommendProdCtgRelDTO) {
		return sqlSessionTemplate.selectOne("MainMapper.selectNmcpRecommendProd", nmcpRecommendProdCtgRelDTO);
	}

	/**
	 * 설명 : 요금제 추천 질문 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 */ 
	@Override
	public List<NmcpRateRecommendInqrBasDTO> selectRateRecommendInqrBasList() {		
		return sqlSessionTemplate.selectList("MainMapper.selectRateRecommendInqrBasList");
	}

	/**
	 * 설명 : 요금제 추천 결과 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRateRecommendInqrRelDTO
	 * @return
	 */ 
	@Override
	public List<NmcpRateRecommendInqrRelDTO> selectRateRecommendInqrRel(NmcpRateRecommendInqrRelDTO nmcpRateRecommendInqrRelDTO) {
		return sqlSessionTemplate.selectList("MainMapper.selectRateRecommendInqrRel", nmcpRateRecommendInqrRelDTO);
	}
}
