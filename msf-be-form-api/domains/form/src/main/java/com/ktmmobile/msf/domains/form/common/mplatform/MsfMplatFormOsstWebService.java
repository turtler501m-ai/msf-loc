package com.ktmmobile.msf.domains.form.common.mplatform;


import java.net.SocketTimeoutException;
import java.util.HashMap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.OsstMcnChgPrecheckResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.RetvUsimChgAcceptPsblVO;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_NAME_CHG_PRE_CHK;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.EVENT_CODE_REPLACE_USIM_PRE_CHK;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfMplatFormOsstWebService {

    private final MsfMplatFormOsstWebServerAdapter mplatFormOsstWebServerAdapter;
    private final ObjectMapper objectMapper;

    /** T01.유심무상교체 접수 가능 여부조회 */
    public RetvUsimChgAcceptPsblVO retvUsimChgAcceptPsbl(String ncn, String ctn, String custId) throws SelfServiceException, SocketTimeoutException {
        RetvUsimChgAcceptPsblVO vo = new RetvUsimChgAcceptPsblVO();
        HashMap<String, String> param = getParamMap(ncn, ctn, custId, EVENT_CODE_REPLACE_USIM_PRE_CHK);
        mplatFormOsstWebServerAdapter.callService(param, vo);
        return vo;
    }

    /** FMC0 서식지 명의변경 사전 체크 **/
    public OsstMcnChgPrecheckResponse mcnChgPreCheck(MplatFormFMC0InfoResponse response) throws SelfServiceException, SocketTimeoutException {
        OsstMcnChgPrecheckResponse vo = new OsstMcnChgPrecheckResponse();
        HashMap<String, String> param = objectMapper.convertValue(response, HashMap.class);
        param.put("appEventCd", EVENT_CODE_NAME_CHG_PRE_CHK);
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
            log.error(e.getMessage());
        }

        return param;
    }

    private String sesUserId() {
        try {
            return StringUtil.NVL(AuthenticationUtils.getUser().getUserId(), "");
        } catch (RuntimeException e) {
            return "";
        }
    }

}
