package com.ktmmobile.mcp.main.dao;

import java.util.List;

import com.ktmmobile.mcp.main.dto.NmcpRecommendProdDTO;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrRelDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgRelDTO;

public interface MainDao {

	/**
	 * 설명 : 추천 상품 카테고리 2Depth 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	public List<NmcpRecommendProdCtgBasDTO> selectRecommendProdCtgScndList(NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO);
	
	/**
	 * 설명 : 추천 상품 카테고리 요금제 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	public List<NmcpRecommendProdCtgRelDTO> selectRecommendProdCtgRelList(NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO);
	
	/**
	 * 설명 : 추천 휴대폰/유심 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgRelDTO
	 * @return
	 */ 
	public NmcpRecommendProdDTO selectNmcpProdCommend(NmcpRecommendProdCtgRelDTO nmcpRecommendProdCtgRelDTO);

	/**
	 * 설명 : 요금제 추천 질문 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 */ 
	public List<NmcpRateRecommendInqrBasDTO> selectRateRecommendInqrBasList();

	/**
	 * 설명 : 요금제 추천 결과 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRateRecommendInqrRelDTO
	 * @return
	 */ 
	public List<NmcpRateRecommendInqrRelDTO> selectRateRecommendInqrRel(NmcpRateRecommendInqrRelDTO nmcpRateRecommendInqrRelDTO);
}
