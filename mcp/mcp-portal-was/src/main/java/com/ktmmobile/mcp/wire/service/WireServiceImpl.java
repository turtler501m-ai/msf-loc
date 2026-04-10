/**
 *
 */
package com.ktmmobile.mcp.wire.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.wire.dao.WireDao;
import com.ktmmobile.mcp.wire.dto.NmcpWireCounselDto;
import com.ktmmobile.mcp.wire.dto.NmcpWireProdDtlDto;

@Service
public class WireServiceImpl implements WireService{

    @Autowired
    WireDao wireDao;

	@Override
	public List<NmcpWireProdDtlDto> selectWireProdDtlList(NmcpWireProdDtlDto nmcpWireProdDtlDto) {
		return wireDao.selectWireProdDtlList(nmcpWireProdDtlDto);
	}

	@Override
	public NmcpWireProdDtlDto selectWireProdCont(String wireProdCd) {	
		return wireDao.selectWireProdCont(wireProdCd);
	}

	@Override
	public NmcpWireProdDtlDto selectWireProdDtl(NmcpWireProdDtlDto nmcpWireProdDtlDto) {
		return wireDao.selectWireProdDtl(nmcpWireProdDtlDto);
	}

	@Override
	public void insertNmcpWireCounsel(NmcpWireCounselDto nmcpWireCounselDto) {
		wireDao.insertNmcpWireCounsel(nmcpWireCounselDto);
		
	}

}
