package com.ktmmobile.mcp.wire.service;

import java.util.List;

import com.ktmmobile.mcp.wire.dto.WireBannerDto;

public interface BannerSvc {

	/**
	* @Description : 배너리스트정보를 조회한다.
	* @param bannerDto
	* @return
	* @Author : ant
	* @Create Date : 2016. 2. 16.
	*/
	public List<WireBannerDto> listBannerBySubCtg(WireBannerDto bannerDto);

}
