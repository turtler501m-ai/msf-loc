package com.ktmmobile.mcp.jehuSugg.dao;

import com.ktmmobile.mcp.jehuSugg.dto.JehuSuggDto;

public interface JehuSuggDao {

	/**
     * @Description : 제휴제안 등록
     * @param param
     * @return
     */
	int insertJehuSuggAjax(JehuSuggDto jehuSuggDto);
}
