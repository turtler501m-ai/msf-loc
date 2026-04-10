package com.ktmmobile.mcp.main.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.main.dao.MainDao;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRateRecommendInqrRelDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgBasDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdCtgRelDTO;
import com.ktmmobile.mcp.main.dto.NmcpRecommendProdDTO;

@Service
public class MainServiceImpl implements MainService {
	
	@Autowired
	MainDao mainDao;
	
	@Value("${api.interface.server}")
	private String apiInterfaceServer;
    	
	/**
	 * 설명 : 추천 상품 카테고리 2Depth 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	@Override
	public List<NmcpRecommendProdCtgBasDTO> getRecommendProdCtgScndList( NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO) {
		return mainDao.selectRecommendProdCtgScndList(nmcpRecommendProdCtgBasDTO);
	}

	/**
	 * 설명 : 추천 상품 카테고리 요금제 목록 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgBasDTO
	 * @return
	 */ 
	@Override
	public List<NmcpRecommendProdCtgRelDTO> getRecommendProdCtgRelList(NmcpRecommendProdCtgBasDTO nmcpRecommendProdCtgBasDTO) {
		return mainDao.selectRecommendProdCtgRelList(nmcpRecommendProdCtgBasDTO);
	}
	
	/**
	 * 설명 : 추천 휴대폰/유심 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRecommendProdCtgRelDTO
	 * @return
	 */ 
	@Override
    public NmcpRecommendProdDTO selectNmcpProdCommend(NmcpRecommendProdCtgRelDTO nmcpRecommendProdCtgRelDTO) {
        return mainDao.selectNmcpProdCommend(nmcpRecommendProdCtgRelDTO);
    }
	
	/**
	 * 설명 : 요금제 추천 결과 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param mspRateMstDto
	 * @return
	 */ 
	@Override
	public MspRateMstDto findMspRateMst(MspRateMstDto mspRateMstDto) {
		RestTemplate restTemplate = new RestTemplate();
		MspRateMstDto apiResult = restTemplate.postForObject(apiInterfaceServer + "/msp/findMspRateMst", mspRateMstDto, MspRateMstDto.class);
    	
		return apiResult;
	}
	
	/**
	 * 설명 : 요금제 추천 질문 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @return
	 */ 
	@Override
	public List<NmcpRateRecommendInqrBasDTO> selectRateRecommendInqrBasList() {
		return mainDao.selectRateRecommendInqrBasList();
	}

	/**
	 * 설명 : 요금제 추천 결과 조회
	 * @Author : 강채신
	 * @Date : 2021.12.30
	 * @param nmcpRateRecommendInqrRelDTO
	 * @return
	 */ 
	@Override
	public List<NmcpRateRecommendInqrRelDTO> selectRateRecommendInqrRel(NmcpRateRecommendInqrRelDTO nmcpRateRecommendInqrRelDTO) {
		return mainDao.selectRateRecommendInqrRel(nmcpRateRecommendInqrRelDTO);
	}

}
