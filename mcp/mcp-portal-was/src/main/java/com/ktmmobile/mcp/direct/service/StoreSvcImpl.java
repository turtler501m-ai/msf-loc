package com.ktmmobile.mcp.direct.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.direct.dto.StoreDto;
import com.ktmmobile.mcp.common.dto.ComCdDto;
import com.ktmmobile.mcp.direct.dao.StoreDao;

@Service
public class StoreSvcImpl implements StoreSvc {

	@Autowired
	StoreDao storeDao;
	
	@Override
	public List<ComCdDto> storeComCd() {
		// TODO Auto-generated method stub
		return storeDao.storeComCd();
	}
	
	@Override
	public int countStoreList(StoreDto storeDto) {
		// TODO Auto-generated method stub
		return storeDao.countStoreList(storeDto);
	}
	
	@Override
	public List<StoreDto> storeListSelect(StoreDto storeDto, int skipResult, int maxResult) {
		// TODO Auto-generated method stub
		return storeDao.storeListSelect(storeDto, skipResult, maxResult);
	}
	
	@Override
	public StoreDto storeDetail(int storSeq) {
		// TODO Auto-generated method stub
		return storeDao.storeDetail(storSeq);
	}
	
	@Override
	public List<StoreDto> selectSubAddr(StoreDto storeDto){
		return storeDao.selectSubAddr(storeDto);
	}

}
