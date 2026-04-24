package com.ktis.msp.batch.job.cmn.sellusim.mapper;

import java.util.List;
import java.util.Map;

import egovframework.rte.psl.dataaccess.mapper.Mapper;

@Mapper("sellUsimMapper")
public interface SellUsimMapper {

	List<Map<String, Object>> selectSellUsimList();
	
	int updateSellUsim(String fileNm);
}
