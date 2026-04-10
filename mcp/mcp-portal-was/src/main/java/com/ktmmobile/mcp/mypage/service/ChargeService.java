package com.ktmmobile.mcp.mypage.service;

import java.text.ParseException;

import com.ktmmobile.mcp.common.mplatform.vo.MpFarMonBillingInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarMonDetailInfoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarPriceInfoDetailItemVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarRealtimePayInfoVO;

/**
 * 요금조회
 * 실시간요금조회
 * @author bsj
 * @Date : 2021.12.30
 */
public interface ChargeService {


	/**
	 * x15 요금조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param productionDate
	 * @return
	 */
	
	public MpFarMonBillingInfoDto farMonBillingInfoDto(String ncn, String ctn, String custId, String productionDate);
	
	/**
	 * x16 요금 상세조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param billSeqNo
	 * @param billDueDateList
	 * @param billMonth
	 * @param billStartDate
	 * @param billEndDate
	 * @return
	 */
	
	public MpFarMonDetailInfoDto farMonDetailInfoDto(String ncn, String ctn, String custId, String billSeqNo,
			String billDueDateList, String billMonth , String billStartDate, String billEndDate);
	
	/**
	 * x17 요금 항목별 조회 연동
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @param billSeqNo
	 * @param billMonth
	 * @param messageLine
	 * @return
	 */
	
	public MpFarPriceInfoDetailItemVO farPriceInfoDetailItem(String ncn, String ctn, String custId,
            String billSeqNo, String billMonth, String messageLine);
	
	/**
	 *  x18 실시간 요금조회
	 * @author bsj
	 * @Date : 2021.12.30
	 * @param ncn
	 * @param ctn
	 * @param custId
	 * @return
	 * @throws ParseException
	 */
	
	public MpFarRealtimePayInfoVO farRealtimePayInfo(String ncn, String ctn, String custId) throws ParseException;
}
