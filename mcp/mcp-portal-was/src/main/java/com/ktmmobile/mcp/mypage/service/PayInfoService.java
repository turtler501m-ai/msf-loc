package com.ktmmobile.mcp.mypage.service;

import com.ktmmobile.mcp.payinfo.dto.PayInfoDto;

public interface PayInfoService {


	public void makePayInfoImage(PayInfoDto dto);

	public String remakePayInfoImage(PayInfoDto dto);

	public void insetPayInfo(PayInfoDto dto);

	/**
	 * payInfo 상태 저장
	 * @param payInfoDto
	 * @return
	 */
	public PayInfoDto savePayInfoHist(PayInfoDto payInfoDto);

}
