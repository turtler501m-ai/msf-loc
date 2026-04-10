package com.ktmmobile.mcp.rate.service;

import java.util.List;

import com.ktmmobile.mcp.rate.dto.RateDTO;

public interface RateSvc {
	public List<RateDTO> selectRateList(RateDTO dto);
	
	public void insertRate(RateDTO dto);
	
	public RateDTO selectRate(RateDTO dto);
	
	public void deleteRate(RateDTO dto);
	
	public void updateRate(RateDTO dto);
	
	public RateDTO getDetail(RateDTO dto);
	
}
