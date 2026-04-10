package com.ktmmobile.mcp.banner.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ktmmobile.mcp.banner.dto.BannerDto;

@Mapper
public interface BannerMapper {

	/**
	 * 컨텐츠 이미지 배너 선택
	 * @param bannerDto
	 * @return
	 * @throws Exception
	 */
	BannerDto selectContentBanner(BannerDto bannerDto);

	BannerDto selectBannerDtl(BannerDto bannerDto);

	List<BannerDto> selectListBannerBySubCtg(BannerDto bannerDto);
}
