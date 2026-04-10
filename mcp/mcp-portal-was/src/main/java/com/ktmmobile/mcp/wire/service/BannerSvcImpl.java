package com.ktmmobile.mcp.wire.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.wire.dto.WireBannerDto;

@Service
public class BannerSvcImpl implements BannerSvc{

    private static Logger logger = LoggerFactory.getLogger(BannerSvcImpl.class);

	@Value("${api.interface.server}")
	private String apiInterfaceServer;

	@Override
    public List<WireBannerDto> listBannerBySubCtg(WireBannerDto bannerDto) {
		RestTemplate restTemplate = new RestTemplate();
		WireBannerDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/banner/listBannerBySubCtg", bannerDto, WireBannerDto[].class);
		List<WireBannerDto> list = Arrays.asList(resultList);
		return list;
    }

}
