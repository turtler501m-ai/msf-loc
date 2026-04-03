package com.ktmmobile.msf.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.dto.MpVirtualAccountNoDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpBilPrintInfoVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.payinfo.dto.PaywayDto;

public interface MsfPaywayService {

	public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId);

	public MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId);

	public MpMoscBilEmailInfoInVO kosMoscBillInfo(String ncn, String ctn, String custId);

	public MpBilPrintInfoVO bilPrintInfo(String ncn, String ctn, String custId);

	public Map<String,Object> getPayData(String ncn, String ctn, String custId);

	public Map<String,Object> farChgWayChg(PaywayDto paywayDto);

	public Map<String,Object> smsFarChgWayChg(PaywayDto paywayDto);

	List<MpVirtualAccountNoDto> getVirtualAccountNoList(String svcCntrNo, String cntrMobileNo, String customerId) throws SelfServiceException, SocketTimeoutException;
}
