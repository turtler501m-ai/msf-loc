package com.ktmmobile.mcp.banner.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.banner.dto.BannerDto;
import com.ktmmobile.mcp.banner.mapper.BannerMapper;
import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;

@RestController
public class BannerController {
	
	@Autowired
	BannerMapper bannerMapper;
	
	/**
	 * 컨텐츠 이미지 배너 선택
	 * @param bannerDto
	 * @return
	 */
	@RequestMapping(value = "/banner/contentBanner", method = RequestMethod.POST)
	public BannerDto contentBanner(@RequestBody BannerDto bannerDto) {

		BannerDto contentBanner = null;
		
		try {
			contentBanner = bannerMapper.selectContentBanner(bannerDto);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return contentBanner;
	}
	
	/**
	 * 배너 하위 관리 선택 
	 * @param bannerDto
	 * @return
	 */
	@RequestMapping(value = "/banner/bannerDtl", method = RequestMethod.POST)
	public BannerDto bannerDtl(@RequestBody BannerDto bannerDto) {

		BannerDto bannerDtl = null;
		
		try {
			bannerDtl = bannerMapper.selectBannerDtl(bannerDto);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return bannerDtl;
	}
	
	/**
	 * 조건에 해당하는 배너 리스트를 가져온다. (메인용)
	 * @param bannerDto
	 * @return
	 */
	@RequestMapping(value = "/banner/listBannerBySubCtg", method = RequestMethod.POST)
	public List<BannerDto> listBannerBySubCtg(@RequestBody BannerDto bannerDto) {

		List<BannerDto> listBannerBySubCtg = null;
		
		try {
			listBannerBySubCtg = bannerMapper.selectListBannerBySubCtg(bannerDto);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return listBannerBySubCtg;
	}
	
}
