package com.ktmmobile.msf.domains.externalclient.mspprx.application.service;

import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceId;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.in.ServiceAlterTraceReader;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.in.ServiceAlterTraceRecorder;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.ServiceAlterTraceRepository;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.entity.ServiceAlterTrace;

/**
 * 서비스 변경 이력 서비스
 */
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ServiceAlterTraceService implements ServiceAlterTraceRecorder, ServiceAlterTraceReader {

    private static final String DEFAULT_NCN = "0000000000";
    private static final String DEFAULT_USER_ID = "SYSTEM";
    private static final int NCN_LENGTH = 10;
    private static final int IP_LENGTH = 20;
    private static final int ID_LENGTH = 50;
    private static final int SUBSCRIBER_NO_LENGTH = 13;
    private static final int EVENT_CD_LENGTH = 4;
    private static final int PRCS_MDL_DIV_CD_LENGTH = 100;
    private static final int TRTM_RSLT_SBST_LENGTH = 1000;
    private static final int PRCS_SBST_LENGTH = 6000;
    private static final int RSLT_CD_LENGTH = 15;
    private static final int SOC_CODE_LENGTH = 20;
    private static final int PARAMETER_LENGTH = 1000;
    private static final int ACCESS_URL_ADR_LENGTH = 300;
    private static final int GLOBAL_NO_LENGTH = 30;
    private static final int CONTRACT_NUM_LENGTH = 10;

    private final ServiceAlterTraceRepository serviceAlterTraceRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ServiceAlterTraceId recordTrace(ServiceAlterTraceRequest request) {
        return serviceAlterTraceRepository.recordTrace(toEntity(enrichRequestContext(request)));
    }

    @Override
    public int countRecentSuccessfulPlanChange(String ncn, String targetSocCode) {
        return serviceAlterTraceRepository.countRecentSuccessfulPlanChange(ncn, targetSocCode);
    }

    private ServiceAlterTraceRequest enrichRequestContext(ServiceAlterTraceRequest request) {
        HttpServletRequest httpRequest = RequestUtils.getRequestIfNoRequest();
        if (httpRequest == null) {
            return request;
        }

        ServiceAlterTraceRequest.ServiceAlterTraceRequestBuilder builder = request.toBuilder();
        if (!StringUtils.hasText(request.getAccessIp())) {
            builder.accessIp(RequestUtils.getClientIp());
        }
        if (!StringUtils.hasText(request.getAccessUrlAdr())) {
            builder.accessUrlAdr(RequestUtils.getRequestUri());
        }
        return builder.build();
    }

    private ServiceAlterTrace toEntity(ServiceAlterTraceRequest request) {
        return ServiceAlterTrace.builder()
            .ncn(ncn(request.getNcn()))
            .contractNum(limit(request.getContractNum(), CONTRACT_NUM_LENGTH))
            .globalNo(limit(request.getGlobalNo(), GLOBAL_NO_LENGTH))
            .subscriberNo(limit(request.getSubscriberNo(), SUBSCRIBER_NO_LENGTH))
            .eventCd(limit(request.getEventCd(), EVENT_CD_LENGTH))
            .prcsMdlDivCd(limit(request.getPrcsMdlDivCd(), PRCS_MDL_DIV_CD_LENGTH))
            .trtmRsltSbst(limit(request.getTrtmRsltSbst(), TRTM_RSLT_SBST_LENGTH))
            .prcsSbst(limit(request.getPrcsSbst(), PRCS_SBST_LENGTH))
            .rsltCd(limit(request.getRsltCd(), RSLT_CD_LENGTH))
            .aSocCode(limit(request.getASocCode(), SOC_CODE_LENGTH))
            .tSocCode(limit(request.getTSocCode(), SOC_CODE_LENGTH))
            .aSocAmt(request.getASocAmt())
            .tSocAmt(request.getTSocAmt())
            .parameter(limit(request.getParameter(), PARAMETER_LENGTH))
            .accessIp(limit(request.getAccessIp(), IP_LENGTH))
            .accessUrlAdr(limit(request.getAccessUrlAdr(), ACCESS_URL_ADR_LENGTH))
            .userId(limit(loginUserId(), ID_LENGTH))
            .build();
    }

    private String ncn(String value) {
        if (!StringUtils.hasText(value)) {
            return DEFAULT_NCN;
        }
        return limit(value, NCN_LENGTH);
    }

    private String loginUserId() {
        try {
            String userId = AuthenticationUtils.getUser().getUserId();
            return StringUtils.hasText(userId) ? userId : DEFAULT_USER_ID;
        } catch (RuntimeException _) {
            return DEFAULT_USER_ID;
        }
    }

    private String limit(String value, int length) {
        if (!StringUtils.hasText(value) || value.length() <= length) {
            return value;
        }
        return value.substring(0, length);
    }
}
