package com.ktmmobile.mcp.content.service;

import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.content.dao.KtDcDao;
import com.ktmmobile.mcp.content.dto.KtDcDto;
import com.ktmmobile.mcp.etc.dto.GiftPromotionDto;

@Service
public class KtDcSvclmpl implements KtDcSvc {

    private static final Logger logger = LoggerFactory.getLogger(KtDcSvclmpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Autowired
    private KtDcDao ktDcDao;

    @Override
    public List<KtDcDto> selectRateNmList(KtDcDto ktDcDto) {
         RestTemplate restTemplate = new RestTemplate();
         KtDcDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/ktDc/selectRateNmList", ktDcDto, KtDcDto[].class);
         List<KtDcDto> result  = Arrays.asList(rtnList);
        return result;
    }

    @Override
    public KtDcDto selectKtDc(KtDcDto ktDcDto) {
        return ktDcDao.selectKtDc(ktDcDto);
    }

    @Override
    public int insertKtDc(KtDcDto ktDcDto) {
        return ktDcDao.insertKtDc(ktDcDto);
    }

}
