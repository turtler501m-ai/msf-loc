package com.ktmmobile.mcp.jehuSugg.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.jehuSugg.dao.JehuSuggDao;
import com.ktmmobile.mcp.jehuSugg.dto.JehuSuggDto;

@Service
public class JehuSuggServiceImpl implements JehuSuggService {

	@Autowired
	JehuSuggDao jehuSuggDao;

	@Override
	public int insertJehuSuggAjax(JehuSuggDto jehuSuggDto) {
		return jehuSuggDao.insertJehuSuggAjax(jehuSuggDto);
	}

}
