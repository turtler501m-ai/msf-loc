package com.ktmmobile.mcp.mypage.service;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.MpVirtualAccountNoDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.mcp.payinfo.dto.PaywayDto;

public interface PaywayService {

	public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId);

	public MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId);

	public MpMoscBilEmailInfoInVO kosMoscBillInfo(String ncn, String ctn, String custId);

	public MpBilPrintInfoVO bilPrintInfo(String ncn, String ctn, String custId);

	public Map<String,Object> getPayData(String ncn, String ctn, String custId);

	public Map<String,Object> farChgWayChg(PaywayDto paywayDto);

	public Map<String,Object> smsFarChgWayChg(PaywayDto paywayDto);

	List<MpVirtualAccountNoDto> getVirtualAccountNoList(String svcCntrNo, String cntrMobileNo, String customerId) throws SelfServiceException, SocketTimeoutException;
}
