package com.ktis.msp.batch.job.rcp.productcnslmgmt.mapper;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("ProductCnslMapper")
public interface ProductCnslMgmtMapper {
	/**
	 * 취소 또는 상담완료 고객정보 변경
	 */
	int updateProductCounselCustInfo();
}
