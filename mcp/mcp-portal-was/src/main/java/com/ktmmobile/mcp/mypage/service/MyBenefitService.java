/**
 *
 */
package com.ktmmobile.mcp.mypage.service;

import java.util.List;

import com.ktmmobile.mcp.common.mspservice.dto.MspNoticSupportMstDto;
import com.ktmmobile.mcp.mypage.dto.BenefitSearchDto;
import com.ktmmobile.mcp.mypage.dto.MyGiftDto;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;

/**
 * @author ANT_FX700_02
 *
 */
public interface MyBenefitService {
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
	
	public String insertCouponGift(CustPointTxnDto custPointTxnDto);
	
	// My 사은품 목록 및 수 - api 호출
	public List<MyGiftDto> getGiftCustList(MyGiftDto myGiftDto, int skipResult, int maxResult);
	public int getCustListCount(MyGiftDto myGiftDto);


}
