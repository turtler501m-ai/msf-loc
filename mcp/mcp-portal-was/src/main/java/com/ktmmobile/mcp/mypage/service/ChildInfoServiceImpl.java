package com.ktmmobile.mcp.mypage.service;

import com.ktmmobile.mcp.mypage.dto.ChildInfoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class ChildInfoServiceImpl implements ChildInfoService {

    private static Logger logger = LoggerFactory.getLogger(ChildInfoServiceImpl.class);

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**
     * 법정대리인 정보 조희
     * @param minorAgentSvcCntrNo
     * @return ChildInfoDto
     */
    public ChildInfoDto getMinorAgentInfo(String minorAgentSvcCntrNo) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        //---- API 호출 E ----//
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/getMinorAgentInfo", minorAgentSvcCntrNo, ChildInfoDto.class);
    }

    /**
     * 자녀 회선 목록 조회
     * @param famSeq
     * @return List<ChildInfoDto>
     */
    public List<ChildInfoDto> selectChildCntrList(String famSeq) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        ChildInfoDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/mypage/childCntrList", famSeq, ChildInfoDto[].class);
        List<ChildInfoDto> list = Arrays.asList(rtnList);
        //---- API 호출 E ----//
        return list;
    }

    /**
     * 자녀 회선 조회
     * @param childInfoDto
     * @return ChildInfoDto
     */
    public ChildInfoDto selectChildCntr(ChildInfoDto childInfoDto) {
        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        //---- API 호출 E ----//
        return restTemplate.postForObject(apiInterfaceServer + "/mypage/selectChildCntr", childInfoDto, ChildInfoDto.class);
    }

}
