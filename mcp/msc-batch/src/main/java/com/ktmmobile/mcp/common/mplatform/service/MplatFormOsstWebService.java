package com.ktmmobile.mcp.common.mplatform.service;

import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.UsimChgAcceptVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.SocketTimeoutException;
import java.util.HashMap;

import static com.ktmmobile.mcp.common.constant.Constants.EVENT_CODE_REPLACE_USIM_RCP_SEND;

@Service
public class MplatFormOsstWebService {

	@Autowired
	private MplatFormOsstWebServerAdapter mplatFormOsstWebServerAdapter;

	/** T02.유심무상교체 접수 가능 여부조회 */
	public UsimChgAcceptVO usimChgAccept(String ncn, String ctn, String custId, String acceptDt, String acceptChCd, String deliveryDivCd) throws SelfServiceException, SocketTimeoutException {

		UsimChgAcceptVO vo = new UsimChgAcceptVO();

		HashMap<String, String> param = new HashMap<>();
		param.put("ncn", ncn);
		param.put("ctn", ctn);
		param.put("custId", custId);
		param.put("acceptDt", acceptDt);
		param.put("acceptChCd", acceptChCd);
		param.put("deliveryDivCd", deliveryDivCd);
		param.put("userid", "");
		param.put("appEventCd", EVENT_CODE_REPLACE_USIM_RCP_SEND);

		mplatFormOsstWebServerAdapter.callService(param, vo);
		return vo;
	}
}
