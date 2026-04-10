package com.ktmmobile.mcp.rate.service;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.rate.dto.McpFarPriceDto;
import com.ktmmobile.mcp.rate.dto.McpServiceAlterTraceDto;
import com.ktmmobile.mcp.rate.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.rate.dto.MspRateMstDto;
import com.ktmmobile.mcp.rate.dto.MspSaleSubsdMstDto;


@Service
public class RateApiSvc {
	private static final Logger logger = LoggerFactory.getLogger(RateApiSvc.class);
	
    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /*
     * API 서버 호출
     * 해지 가능 여부를 조회하기 위한 요금제 정보 조회
     */
    public MspRateMstDto getMspRateMst(String soc) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, String> params = new LinkedMultiValueMap<String, String>();
        params.add("rateCd", soc);
        MspRateMstDto mspRateMstDto = restTemplate.postForObject(apiInterfaceServer + "/common/mspRateMst", params, MspRateMstDto.class);
        
        return mspRateMstDto;
    }
    
	/*
	 * API 서버 호출
	 * 요금제 정보 확인
	 */
    public MspSaleSubsdMstDto getRateInfo(String rateCd) {

		 //---- API 호출 S ----//
    	MspSaleSubsdMstDto rtnRateInfo = null;
  		RestTemplate restTemplate = new RestTemplate();
  		rtnRateInfo = restTemplate.postForObject(apiInterfaceServer + "/mypage/getRateInfo", rateCd, MspSaleSubsdMstDto.class);
  		
        return rtnRateInfo;
    }
    
	/*
	 * API 서버 호출
	 * 요금제 정보 확인
	 */
    public boolean insertSocfailProcMst(McpServiceAlterTraceDto serviceAlterTrace) {

		 //---- API 호출 S ----//
  		RestTemplate restTemplate = new RestTemplate();
  		boolean isSucssesYn = restTemplate.postForObject(apiInterfaceServer + "/mypage/insertSocfailProcMst", serviceAlterTrace, boolean.class);
  		
        return isSucssesYn;
    }
    
	/*
	 * API 서버 호출
	 * MCP 휴대폰 회선관리 리스트를 가지고 온다.
	 */
    public List<McpUserCntrMngDto> selectCntrList(String userId) {

  		Map<String, String> params = new HashMap<String, String>();
  		params.put("userId", userId);
  		
		 //---- API 호출 S ----//
  		RestTemplate restTemplate = new RestTemplate();
  		McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrList", params, McpUserCntrMngDto[].class);

        List<McpUserCntrMngDto> list = null;
        if (Optional.ofNullable(resultList).isPresent() && resultList.length != 0) {
            list = Arrays.asList(resultList);
        }
        
        return list;
    }
    
	/*
	 * API 서버 호출
	 * 현재 요금제 조회 svcCntrNo
	 */
    public McpFarPriceDto selectFarPricePlan(String contractNum) {

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        McpFarPriceDto mcpFarPriceDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/farPricePlan", contractNum, McpFarPriceDto.class);
        //---- API 호출 E ----//

        return mcpFarPriceDto;
    }
    
	/*
	 * API 서버 호출
	 * 요금제 변경 온라인 프로모션 ID 조회
	 */
    public String getChrgPrmtIdSocChg(String rateCd) {

        //---- API 호출 S ----//
        RestTemplate restTemplate = new RestTemplate();
        String prmtId = restTemplate.postForObject(apiInterfaceServer + "/mypage/getChrgPrmtIdSocChg", rateCd, String.class);
        //---- API 호출 E ----//

        return prmtId;
    }

	/*
	 * API 서버 호출
	 * 평생할인 정책 기적용 테이블 INSERT
	 */
    public int insertDisApd(McpUserCntrMngDto mcpUserCntrMngDto) {

		 //---- API 호출 S ----//
  		RestTemplate restTemplate = new RestTemplate();
  		int result = restTemplate.postForObject(apiInterfaceServer + "/mypage/insertDisApd", mcpUserCntrMngDto, int.class);
  		
        return result;
    }
    
    /*
     * API 서버 호출
     * 계약 현행화 정보 조회
     */
    public McpUserCntrMngDto selectCntrListNoLogin(String svcCntrNo) {
    	McpUserCntrMngDto paramDto = new McpUserCntrMngDto();
        paramDto.setSvcCntrNo(svcCntrNo);

    	//---- API 호출 S ----//
    	RestTemplate restTemplate = new RestTemplate();
    	McpUserCntrMngDto mcpUserCntrMngDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", paramDto, McpUserCntrMngDto.class);
    	//---- API 호출 E ----//
    	
    	return mcpUserCntrMngDto;
    }
    
    /*
     * API 서버 호출
     * 선해지 부가서비스 조회
     */
    public List<McpUserCntrMngDto> getCloseSubList(String contractNum) {
    	RestTemplate restTemplate = new RestTemplate();
    	
    	McpUserCntrMngDto[] rtnList = restTemplate.postForObject(apiInterfaceServer + "/mypage/closeSubList", contractNum, McpUserCntrMngDto[].class);
    	List<McpUserCntrMngDto> list = Arrays.asList(rtnList);
    	
    	return list;
    }

	/*
	 * API 서버 호출
	 * 평생할인 부가서비스 목록 조회
	 */
    public List<McpUserCntrMngDto> getromotionDcList(String toSocCode) {

        RestTemplate restTemplate = new RestTemplate();
        McpUserCntrMngDto[] list = restTemplate.postForObject(apiInterfaceServer + "/mypage/romotionDcList", toSocCode, McpUserCntrMngDto[].class);
        List<McpUserCntrMngDto> rtnList = Arrays.asList(list);
        return rtnList;
    }
}
