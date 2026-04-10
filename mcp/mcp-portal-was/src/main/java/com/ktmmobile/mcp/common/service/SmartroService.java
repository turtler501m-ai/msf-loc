package com.ktmmobile.mcp.common.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.mcp.common.dto.SmartroDto;

public interface SmartroService {

	Map<String, String> smartroPayInit(HttpServletRequest req);

	HashMap<String, Object> callApi(HttpServletRequest req);

	public String insertSmartroOrder(SmartroDto smartroDto);

	public List<SmartroDto> getSmartroDataList(String orderno);

	Map<String, Object> cancelPay(HttpServletRequest req);

}
