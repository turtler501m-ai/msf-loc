package com.ktmmobile.mcp.common.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.mcp.common.dao.BannerStatDao;
import com.ktmmobile.mcp.common.dto.db.BannAccessTxnDto;

@Service
public class BannerStatServiceImpl implements BannerStatService{

	private static Logger logger = LoggerFactory.getLogger(BannerStatServiceImpl.class);

	@Autowired
	BannerStatDao bannerStatDao;


	/**
	 * 설명 : 배너 클릭 정보 저장
	 * @Author : 박정웅
	 * @Date : 2021.12.30
	 * @param bannAccessTxnDto
	 */
	@Override
	public void insertBannAccessTxn(BannAccessTxnDto bannAccessTxnDto) {
		int retVal = bannerStatDao.insertBannAccessTxn(bannAccessTxnDto);
		//logger.debug("insertBannAccessTxn:{}", retVal);
	}
}
