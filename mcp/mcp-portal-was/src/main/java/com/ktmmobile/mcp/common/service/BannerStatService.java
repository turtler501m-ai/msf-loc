package com.ktmmobile.mcp.common.service;

import com.ktmmobile.mcp.common.dto.db.BannAccessTxnDto;

public interface BannerStatService {

	/**
	 * 설명 : 배너 클릭 정보 저장
	 * @Author : 박정웅
	 * @Date : 2021.12.30
	 * @param bannAccessTxnDto
	 */
	public void insertBannAccessTxn(BannAccessTxnDto bannAccessTxnDto);

}
