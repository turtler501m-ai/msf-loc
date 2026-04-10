package com.ktmmobile.msf.domains.form.common.mplatform;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_REPLACE_USIM_PRE_CHK;
import java.net.SocketTimeoutException;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.RetvUsimChgAcceptPsblVO;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Service
public class MsfMplatFormOsstWebService {

	@Autowired
	private MsfMplatFormOsstWebServerAdapter mplatFormOsstWebServerAdapter;


	private static final Logger logger = LoggerFactory.getLogger(MsfMplatFormOsstWebService.class);

	/** T01.유심무상교체 접수 가능 여부조회 */
	public RetvUsimChgAcceptPsblVO retvUsimChgAcceptPsbl(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
		RetvUsimChgAcceptPsblVO vo = new RetvUsimChgAcceptPsblVO();
		HashMap<String, String> param = getParamMap(ncn, ctn, custId, EVENT_CODE_REPLACE_USIM_PRE_CHK);
		mplatFormOsstWebServerAdapter.callService(param, vo);
		return vo;
	}

	private HashMap<String, String> getParamMap(String ncn, String ctn, String custId, String eventCd) {

		HashMap<String, String> param = new HashMap<>();
		String userId = this.sesUserId();

		try {
			param.put("ncn", ncn);
			param.put("ctn", ctn);
			param.put("custId", custId);
			param.put("userid", userId);
			param.put("appEventCd", eventCd);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		return param;
	}

	private String sesUserId() {
		String retId = "";

		UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
		if (userSessionDto != null) {
			retId = StringUtil.NVL(userSessionDto.getUserId(), "");
		}

		return retId;
	}

}
