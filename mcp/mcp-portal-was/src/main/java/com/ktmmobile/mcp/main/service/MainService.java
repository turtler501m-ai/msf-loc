package com.ktmmobile.mcp.main.service;

import java.util.List;

import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrRelDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgRelDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdDTO;

public interface MainService {
 
	/**
	 * 설명 : 추천 상품 카테고리 2Depth 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	public List<NmcpRecommendProdCtgBasDTO> getRecommendProdCtgScndList(NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO);
	
	/**
	 * 설명 : 추천 상품 카테고리 요금제 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	public List<NmcpRecommendProdCtgRelDTO> getRecommendProdCtgRelList(NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO);
	
	/**
	 * 설명 : 추천 휴대폰/유심 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgRelDTO
	 * @return
	 */ 
	public NmcpRecommendProdDTO selectNmcpProdCommend(NmcpRecommendProdCtgRelDTO nmcpRecommendProdCtgRelDTO);
	
	/**
	 * 설명 : 요금제 추천 결과 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param mspRateMstDto
	 * @return
	 */ 
	public MspRateMstDto findMspRateMst(MspRateMstDto mspRateMstDto);
		
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
