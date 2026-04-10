package com.ktmmobile.mcp.requestreview.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.requestreview.dto.RequestReviewDto;

import java.util.List;

@Mapper
public interface RequestReviewMapper {
	
	/**
	 * 
	 * @param contractNum
	 * @return
	 * @throws Exception
	 */
	RequestReviewDto selectMcpRequestData(String contractNum);
	
	/**
	 * 
	 * @param contractNum
	 * @return
	 * @throws Exception
	 */
	RequestReviewDto selectMspJuoSubInfo(String contractNum);

	String selectMcpJuoFeatSocInfo(String contractNum);

    List<RequestReviewDto> selectReviewExcel(RequestReviewDto requestReviewDto);
}
