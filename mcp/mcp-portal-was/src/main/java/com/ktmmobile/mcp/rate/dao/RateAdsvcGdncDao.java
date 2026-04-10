package com.ktmmobile.mcp.rate.dao;

import java.util.List;

import com.ktmmobile.mcp.rate.dto.RateAdsvcBnfitGdncDtlDTO;
import com.ktmmobile.mcp.rate.dto.RateAdsvcCtgBasDTO;
import com.ktmmobile.mcp.rate.dto.RateAgreeDTO;
import com.ktmmobile.mcp.requestReview.dto.RequestReviewDto;

public interface RateAdsvcGdncDao {
	
	/**
	 * 설명 : 사용후기 목록 총갯수 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param rateAdsvcCtgBasDTO
	 * @return
	 */ 
	public int getRequestreviewTotalCnt(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);
	
	/**
	 * 설명 : 사용후기 목록 조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param rateAdsvcCtgBasDTO
	 * @return
	 */ 
	public List<RequestReviewDto> getRequestreviewList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);
	
	/**
	 * 설명 : 약정할인 프로그램 및 할인반환금 안내 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 */ 
	public RateAgreeDTO getRateAgreeView();
	
	/**
	 * 설명 : 요금제부가서비스혜택안내 목록 조회 
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param rateAdsvcCtgBasDTO
	 * @return
	 */ 
	public List<RateAdsvcBnfitGdncDtlDTO> getRateAdsvcBnfitGdncDtlList(RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO);
}
