package com.ktmmobile.mcp.prepia.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.msp.dto.MspSaleSubsdMstDto;
import com.ktmmobile.mcp.prepia.mapper.PrepiaMapper;

@RestController
public class PrepiaController {
	
	private static final Logger logger = LoggerFactory.getLogger(PrepiaController.class);
	
	@Autowired
	PrepiaMapper prepiaMapper;
	
	/**
	 * 정책 판매중인 핸드폰 리스트 정보 조회 only msp db링크를 통한 조회만 한다.
	 * @param CommonSearchDto param
	 * @return
	 */
	@RequestMapping(value = "/prepia/ratePrepia", method = RequestMethod.GET)
	public List<MspSaleSubsdMstDto> ratePrepia() {
		
		List<MspSaleSubsdMstDto> result = null;
		
		try {
			// Database 에서 조회함.
			result = prepiaMapper.selectRatePrepia();
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return result;
	}

	/**
	 * 정책 판매중인 핸드폰 리스트 정보 조회 only msp db링크를 통한 조회만 한다.
	 * @param CommonSearchDto param
	 * @return
	 */
	@RequestMapping(value = "/prepia/rateList", method = RequestMethod.POST)
	public List<MspSaleSubsdMstDto> rateList(@RequestBody Map<String, String> map) {
		
		List<MspSaleSubsdMstDto> result = null;
		
		try {
			// Database 에서 조회함.
			result = prepiaMapper.getRateList(map);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return result;
	}
	
}
