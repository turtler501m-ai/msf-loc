package com.ktmmobile.msf.form.servicechange.service;

import java.util.List;
import java.util.Map;

import com.ktmmobile.msf.common.mplatform.dto.CoupInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.common.mplatform.vo.MpPerMyktfInfoVO;

public interface MsfMyinfoService {

	public Map<String,Object> farMonBillingInfo(String ncn, String ctn, String custId, String sysDate);

	public MpPerMyktfInfoVO perMyktfInfo(String ncn, String ctn, String custId);

	public MpFarChangewayInfoVO farChangewayInfo(String ncn, String ctn, String custId);

	public MpMoscBilEmailInfoInVO kosMoscBillInfo(String ncn, String ctn, String custId);

	public int moscCombStatMgmtInfo(String ncn, String ctn, String custId,String soc);

	public MpAddSvcInfoDto getAddSvcInfoDto(String ncn, String ctn, String custId);

	public List<CoupInfoDto> inqrCoupInfo(String ncn, String ctn, String custId);

	public MpFarRealtimePayInfoVO farRealtimePayInfo(String ncn, String ctn, String custId);

	Map<String, Object> combinePayData(MpFarChangewayInfoVO farChgWayInfo, MpMoscBilEmailInfoInVO bilEmailInfo);
}
