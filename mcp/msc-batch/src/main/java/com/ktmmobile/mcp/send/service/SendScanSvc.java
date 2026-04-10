package com.ktmmobile.mcp.send.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.send.dto.SendScanDto;
import com.ktmmobile.mcp.send.mapper.SendScanMapper;

@Service
public class SendScanSvc {

	@Autowired
	private SendScanMapper sendScanMapper;

	public List<SendScanDto> selectSendScanList(){
		return sendScanMapper.selectSendScanList();
	}

}
