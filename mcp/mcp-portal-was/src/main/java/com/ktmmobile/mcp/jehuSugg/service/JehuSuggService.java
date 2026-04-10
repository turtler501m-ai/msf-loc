package com.ktmmobile.mcp.jehuSugg.service;

import com.ktmmobile.mcp.jehuSugg.dto.JehuSuggDto;

public interface JehuSuggService {

	/**
     * <pre>
     * 설명     : 제휴제안 등록
     * @param JehuSuggDto :
     * @return
     * </pre>
     */
	int insertJehuSuggAjax(JehuSuggDto jehuSuggDto);
}
