/**
 *
 */
package com.ktmmobile.mcp.mypage.dao;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.common.dto.db.McpRequestAgrmDto;
import com.ktmmobile.mcp.mypage.dto.BenefitSearchDto;
import com.ktmmobile.mcp.mypage.dto.CommendStateDto;
import com.ktmmobile.mcp.mypage.dto.JehuDto;
import com.ktmmobile.mcp.mypage.dto.JuoFeatureDto;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.McpRateChgDto;
import com.ktmmobile.mcp.mypage.dto.McpRegServiceDto;
import com.ktmmobile.mcp.mypage.dto.McpRetvRststnDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MspJuoAddInfoDto;
import com.ktmmobile.mcp.mypage.dto.MyGiftDto;
import com.ktmmobile.mcp.mypage.dto.NmcpProdImgDtlDto;
import com.ktmmobile.mcp.mypage.dto.SuspenChgTmlDto;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;

/**
 * @author ANT_FX700_02
 *
 */
public interface MyBenefitDao {
	/**
	 * @Description : 월별 포인트 이용현황 조회
	 * @param 
	 * @return 
	 * @Author : ant
	 * @Create Date :
	 */
	public List<CustPointTxnDto> selectMothlyPointList(String cntrNo);

	/**
	 * @Description : 고객 포인트 조회
	 * @param 
	 * @return 
	 * @Author : ant
	 * @Create Date :
	 */
	public CustPointDto selectCustPoint(String cntrNo);


	public List<CustPointTxnDto> getSelectPointDetailList(BenefitSearchDto vo, int skipResult, int maxResult);

	public int getSelectPointDetailListCnt(BenefitSearchDto vo);

	// My 사은품 목록 및 수 - api 호출
	public List<MyGiftDto> getGiftCustList(MyGiftDto myGiftDto, int skipResult, int maxResult);
	public int getCustListCount(MyGiftDto myGiftDto);

}
