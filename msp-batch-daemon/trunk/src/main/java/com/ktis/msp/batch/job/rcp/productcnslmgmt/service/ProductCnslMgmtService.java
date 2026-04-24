package com.ktis.msp.batch.job.rcp.productcnslmgmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktis.msp.base.BaseService;
import com.ktis.msp.batch.job.rcp.productcnslmgmt.mapper.ProductCnslMgmtMapper;

@Service
public class ProductCnslMgmtService extends BaseService {

	@Autowired
	private ProductCnslMgmtMapper productMapper;
	
	/**
	 * CEO패키지 상담내용 개인정보 삭제
	 */
	public int updateProductCounselCustInfo() {
		int i = productMapper.updateProductCounselCustInfo();
		
		LOGGER.debug("처리건수=" + i);
		
		return i;
	}
	
}
