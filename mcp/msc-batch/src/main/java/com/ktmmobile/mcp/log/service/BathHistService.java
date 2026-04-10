package com.ktmmobile.mcp.log.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.log.dao.BathHistDao;
import com.ktmmobile.mcp.log.dto.BathHistDto;

@Service
public class BathHistService {

	@Autowired
	private BathHistDao bathHistDao;

	public void insertHist(BathHistDto bathHistDto) {

		String batchExeEndDate = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
		bathHistDto.setBatchExeEndDate(batchExeEndDate);
		bathHistDao.insertHist(bathHistDto);
	}

}
