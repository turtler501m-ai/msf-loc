/**
 *
 */
package com.ktmmobile.mcp.mypage.service;

import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.mypage.dto.MPayViewDto;

public interface MPayViewService {
    

	/**
	    * @Description : 소액결제 내역 
	    * @param mPayViewDto
	    * @return
	    * @Create Date : 2022. 07. 11.
	*/
	public List<MPayViewDto> selectMPayList(MPayViewDto mPayViewDto);

	/**
	    * @Description : 개통일로부터 날짜 리스트 조회
	    * @param openingDate
	    * @return
	    * @Create Date : 2022. 07. 11.
	*/
	public List<Map<String,String>> getDateListFromOpening(String openingDate);

	/**
	    * @Description : 이번달 소액결제 한도 조회
	    * @param mPayViewDto
	    * @return
	    * @Create Date : 2022. 07. 11.
	*/
	public String getTmonLmtAmt(MPayViewDto mPayViewDto);
	
	
	

   
}
