package com.ktmmobile.mcp.requestreview.controller;

import static com.ktmmobile.mcp.cmmn.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ktmmobile.mcp.cmmn.exception.McpCommonJsonException;
import com.ktmmobile.mcp.requestreview.dto.RequestReviewDto;
import com.ktmmobile.mcp.requestreview.mapper.RequestReviewMapper;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RequestReviewController {

	@Autowired
	RequestReviewMapper requestReviewMapper;
	
	/**
	 * 서식지 개통 여부 조회
	 * @param contractNum
	 * @return
	 */
	@RequestMapping(value = "/requestreview/mcpRequestData", method = RequestMethod.POST)
	public RequestReviewDto mcpRequestData(String contractNum) {

		RequestReviewDto mcpRequestData = null;
		
		try {
			mcpRequestData = requestReviewMapper.selectMcpRequestData(contractNum);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return mcpRequestData;
	}
	
	/**
	 * 서비스계약번호 개통일 조회
	 * @param contractNum
	 * @return
	 */
	@RequestMapping(value = "/requestreview/RequestReviewDto", method = RequestMethod.POST)
	public RequestReviewDto mspJuoSubInfo(String contractNum) {

		RequestReviewDto mspJuoSubInfo = null;
		
		try {
			mspJuoSubInfo = requestReviewMapper.selectMspJuoSubInfo(contractNum);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return mspJuoSubInfo;
	}
	
	/**
	 * 서비스계약번호 요금제 조회
	 * @param contractNum
	 * @return
	 */
	@RequestMapping(value = "/requestreview/mcpJuoFeatSocInfo", method = RequestMethod.POST)
	public String mcpJuoFeatureInfo(String contractNum) {

		String soc = "";
		try {
			soc = requestReviewMapper.selectMcpJuoFeatSocInfo(contractNum);
			
		} catch(Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		
		return soc;
	}

	@RequestMapping(value = "/mypage/selectReviewExcel")
	public List<RequestReviewDto> selectReviewExcel(@RequestBody RequestReviewDto requestReviewDto) {
		List<RequestReviewDto> requestReviewDtoList = new ArrayList<>();
		try {
			requestReviewDtoList = this.requestReviewMapper.selectReviewExcel(requestReviewDto);
		} catch (Exception e) {
			throw new McpCommonJsonException(COMMON_EXCEPTION);
		}
		return requestReviewDtoList;
	}
	
}
