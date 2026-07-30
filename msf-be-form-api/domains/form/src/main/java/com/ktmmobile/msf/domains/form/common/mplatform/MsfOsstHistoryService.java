package com.ktmmobile.msf.domains.form.common.mplatform;

import java.util.Map;
import jakarta.annotation.PostConstruct;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.common.datasource.smartform.SmartFormDataSourceConfig;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestStateDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpErrVO;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfOsstHistoryService {

    private final MsfOsstResponseExtractor msfOsstResponseExtractor;
    private final McpRequestRepositoryImpl mcpRequestRepository;
    private final MsfRequestRepositoryImpl msfRequestRepository;
    @Qualifier(MspDataSourceConfig.MSP_TX_MANAGER)
    private final PlatformTransactionManager mspTransactionManager;
    private TransactionTemplate mspTransactionTemplate;
    @Qualifier(SmartFormDataSourceConfig.SMARTFORM_TX_MANAGER)
    private final PlatformTransactionManager msfTransactionManager;
    private TransactionTemplate msfTransactionTemplate;

    // 💡 의존성 주입이 완료된 후, 딱 한 번만 템플릿을 안전하게 생성해 둡니다.
    @PostConstruct
    public void init() {
        this.mspTransactionTemplate = new TransactionTemplate(mspTransactionManager);
        this.msfTransactionTemplate = new TransactionTemplate(msfTransactionManager);
    }


    public void saveResponse(Map<String, String> params, MspPrxSoapResponse response) {
        msfOsstResponseExtractor.extract(params, response).ifPresent(osst -> saveHistories(params, osst));
    }

    public void saveFallback(
        Map<String, String> params,
        String osstOrdNo,
        String rsltCd,
        String rsltMsg,
        String nstepGlobalId
    ) {
        String appEventCd = value(params, "appEventCd");
        if (!msfOsstResponseExtractor.supports(appEventCd)) {
            return;
        }

        String mvnoOrdNo = resolveMvnoOrdNo(params);
        if (StringUtils.isBlank(mvnoOrdNo)) {
            return;
        }

        McpRequestOsstDto osst = new McpRequestOsstDto();
        osst.setMvnoOrdNo(mvnoOrdNo);
        osst.setOsstOrdNo(osstOrdNo);
        osst.setPrgrStatCd(appEventCd);
        osst.setRsltCd(StringUtils.defaultIfBlank(rsltCd, appEventCd + "_FAIL"));
        osst.setRsltMsg(rsltMsg);
        osst.setNstepGlobalId(nstepGlobalId);
        osst.setIfType("WEB");
        saveHistories(params, osst);
    }

    public void saveFailure(Map<String, String> params, Exception exception) {
        String appEventCd = value(params, "appEventCd");
        if (!msfOsstResponseExtractor.supports(appEventCd)) {
            return;
        }
        if (!msfOsstResponseExtractor.supports(resolveMvnoOrdNo(params))) {
            return;
        }
        try {
            MpErrVO errVO = new MpErrVO(resolveMvnoOrdNo(params), appEventCd);
            errVO.setPrntsContractNo(value(params, "prntsContractNo"));
            errVO.setCustomerId(firstNotBlank(value(params, "custNo"), value(params, "custId")));
            errVO.setErrInfo(exception);
            mcpRequestRepository.insertOsstErrLog(errVO);
        } catch (Exception historyException) {
            log.error("[OSST history] error log save failed: appEventCd={}, resNo={}",
                appEventCd, resolveMvnoOrdNo(params), historyException);
        }
    }

    private void saveHistories(Map<String, String> params, McpRequestOsstDto osst) {

        try {
            msfTransactionTemplate.execute(status -> {
                return msfRequestRepository.insertMsfRequestOsst(toMsfRequestOsstVo(osst));
            });
            log.info("[msf OSST history] saved: resNo={}, appEventCd={}, prgrStatCd={}, rsltCd={}",
                osst.getMvnoOrdNo(), value(params, "appEventCd"), osst.getPrgrStatCd(), osst.getRsltCd());
        } catch (Exception e) {
            log.error("[msf OSST history] save failed: resNo={}, appEventCd={}, prgrStatCd={}, rsltCd={}",
                osst.getMvnoOrdNo(), value(params, "appEventCd"), osst.getPrgrStatCd(), osst.getRsltCd(), e);
        }

        try {
            mspTransactionTemplate.execute(status -> {
                int inserted = mcpRequestRepository.insertMcpRequestOsstIfAbsent(osst);
                if (inserted <= 0) {
                    return null;
                }
                // 서비스해지(EP0)는 배송 상태를 관리하지 않으므로 MCP_REQUEST_STATE 등록 대상에서 제외한다.
                if (!"EP0".equals(value(params, "appEventCd"))) {
                    McpRequestStateDto state = buildState(params, osst);
                    if (state != null) {
                        mcpRequestRepository.insertMcpRequestState(state);
                    }
                }
                return null;
            });
            log.info("[OSST history] saved: resNo={}, appEventCd={}, prgrStatCd={}, rsltCd={}",
                osst.getMvnoOrdNo(), value(params, "appEventCd"), osst.getPrgrStatCd(), osst.getRsltCd());
        } catch (Exception e) {
            log.error("[OSST history] save failed: resNo={}, appEventCd={}, prgrStatCd={}, rsltCd={}",
                osst.getMvnoOrdNo(), value(params, "appEventCd"), osst.getPrgrStatCd(), osst.getRsltCd(), e);
        }

    }

    private McpRequestStateDto buildState(Map<String, String> params, McpRequestOsstDto osst) {
        Long requestKey = parseLong(value(params, "requestKey"));
        if (requestKey == null) {
            return null;
        }

        McpRequestStateDto dto = new McpRequestStateDto();
        dto.setRequestKey(requestKey);
        dto.setResNo(osst.getMvnoOrdNo());
        dto.setRequestStateCode(isSuccess(osst.getRsltCd()) ? "21" : "30");
        dto.setOpenNo(firstNotBlank(value(params, "ctn"), value(params, "tlphNo"), value(params, "npTlphNo")));
        dto.setMemo(firstNotBlank(osst.getOsstOrdNo(), value(params, "appEventCd")));
        dto.setRid(firstNotBlank(value(params, "userid"), "SYSTEM"));
        dto.setRip(value(params, "ip"));
        dto.setViewFlag("Y");
        return dto;
    }

    private boolean isSuccess(String rsltCd) {
        return "0000".equals(rsltCd) || "00".equals(rsltCd) || "S".equals(rsltCd) || "N".equals(rsltCd);
    }

    private Long parseLong(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        try {
            return Long.valueOf(value);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    private String resolveMvnoOrdNo(Map<String, String> params) {
        return firstNotBlank(value(params, "resNo"), value(params, "mvnoOrdNo"), value(params, "mcnResNo"), value(params, "requestKey"));
    }

    private String value(Map<String, String> params, String key) {
        return params == null ? null : params.get(key);
    }

    private String firstNotBlank(String... values) {
        for (String value: values) {
            if (StringUtils.isNotBlank(value)) {
                return value;
            }
        }
        return null;
    }

    private MsfRequestOsstVo toMsfRequestOsstVo(McpRequestOsstDto dto) {
        MsfRequestOsstVo msfRequestOsstVo = new MsfRequestOsstVo();
        msfRequestOsstVo.setOsstOrdNo(dto.getOsstOrdNo());
        msfRequestOsstVo.setMvnoOrdNo(dto.getMvnoOrdNo());
        msfRequestOsstVo.setPrgrStatCd(dto.getPrgrStatCd());
        msfRequestOsstVo.setRsltCd(dto.getRsltCd());
        msfRequestOsstVo.setRsltMsg(dto.getRsltMsg());
        msfRequestOsstVo.setNstepGlobalId(dto.getNstepGlobalId());
        msfRequestOsstVo.setPrdcChkNotiMsg(dto.getPrdcChkNotiMsg());
        msfRequestOsstVo.setIfTypeCd(dto.getIfType());

        return msfRequestOsstVo;
    }
}
