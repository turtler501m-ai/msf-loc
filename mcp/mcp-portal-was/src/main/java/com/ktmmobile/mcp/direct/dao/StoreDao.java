package com.ktmmobile.mcp.direct.dao;

import java.util.List;

import com.ktmmobile.mcp.direct.dto.StoreDto;
import com.ktmmobile.mcp.common.dto.ComCdDto;

public interface StoreDao {
	
	public List<ComCdDto> storeComCd();

	public int countStoreList(StoreDto storeDto);
	
	public List<StoreDto> storeListSelect(StoreDto storeDto, int skipResult, int maxResult);
	
	public StoreDto storeDetail(int storSeq);
	
	public List<StoreDto> selectSubAddr(StoreDto storeDto);
}
