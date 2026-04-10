/**
 *
 */
package com.ktmmobile.mcp.wire.service;

import java.util.List;

import com.ktmmobile.mcp.wire.dto.NmcpWireCounselDto;
import com.ktmmobile.mcp.wire.dto.NmcpWireProdDtlDto;


public interface WireService {
	
	/**
	 * 유선상품 목록 조회
	 * @return
	 */
	public List<NmcpWireProdDtlDto> selectWireProdDtlList(NmcpWireProdDtlDto nmcpWireProdDtlDto);
	
	/**
	 * 유선 상품별 한눈에 보기 조회
	 * @param wireProdCd
	 * @return
	 */
	public NmcpWireProdDtlDto selectWireProdCont(String wireProdCd);
	
	/**
	 * 유선상품 상세보기
	 * @param nmcpWireProdDtlDto
	 * @return
	 */
	public NmcpWireProdDtlDto selectWireProdDtl(NmcpWireProdDtlDto nmcpWireProdDtlDto);
	
	/**
	 * 유선상품 간편가입상담 신청
	 * @param nmcpWireCounselDto
	 */
	public void insertNmcpWireCounsel(NmcpWireCounselDto nmcpWireCounselDto);
	
}
