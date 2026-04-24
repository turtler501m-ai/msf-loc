package com.ktis.msp.batch.job.rcp.wirecnslmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.batch.job.rcp.wirecnslmgmt.mapper.WireCnslMgmtMapper;

@Service
public class WireCnslMgmtService extends BaseService {

	@Autowired
	private WireCnslMgmtMapper wireMapper;
	
	/**
	 * 유선판매 상담내용 개인정보 삭제
	 */
	public int updateWireCounselCustInfo() {
		int i = wireMapper.updateWireCounselCustInfo();
		
		LOGGER.debug("처리건수=" + i);
		
		return i;
	}
	
}
