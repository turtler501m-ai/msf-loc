package com.ktmmobile.msf.domains.form.form.common.repository;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.crypto.domain.code.FieldCryptoAlgorithm;
import com.ktmmobile.msf.commons.crypto.support.util.CryptoUtils;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestOsstDto;
import com.ktmmobile.msf.domains.form.common.dto.McpRequestStateDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpErrVO;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.McpRequestWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.MsfRequestReadMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCancelRequestVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestChangeVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;
import com.ktmmobile.msf.domains.form.form.newchange.repository.msp.McpRequestAgentWriteMapper;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpReqCombineDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Request;

@Repository
@RequiredArgsConstructor
public class McpRequestRepositoryImpl {

    private final MsfRequestReadMapper msfRequestReadMapper;
    private final McpRequestWriteMapper mcpRequestWriteMapper;
    private final McpRequestReadMapper mcpRequestReadMapper;
    private final McpRequestAgentWriteMapper mcpRequestAgentWriteMapper;

    public int insertMcpCancelRequest(Long requestKey) {
        /* 채지신청서관련 MSF 조회후 MCP INSERT 처리 */
        McpCancelRequestVo vo = msfRequestReadMapper.selectMcpCancelRequest(requestKey);
        if (vo == null) {
            return 0;
        }
        return mcpRequestWriteMapper.insertMcpCancelRequest(vo);
    }

    public int insertMcpRequestCstmr(Long requestKey) {
        McpRequestCstmrVo vo = msfRequestReadMapper.selectMcpCancelRequestCstmr(requestKey);
        if (vo == null) {
            return 0;
        }
        vo.setCstmrNativeRrn(encryptCustomerRrn(vo.getCstmrNativeRrn()));
        vo.setCstmrForeignerRrn(encryptCustomerRrn(vo.getCstmrForeignerRrn()));
        return mcpRequestWriteMapper.insertMcpRequestCstmr(vo);
    }

    public int insertMcpCancelCustRequestMst(Long requestKey) {
        McpCustRequestMstVo vo = msfRequestReadMapper.selectMcpCancelCustRequestMst(requestKey);
        if (vo == null) {
            return 0;
        }
        fillCustRequestNativeRrn(vo);
        mcpRequestWriteMapper.insertNmcpCustReqMst(vo);
        return 1;
    }

    private static void fillCustRequestNativeRrn(McpCustRequestMstVo vo) {
        if (vo.getCstmrNativeRrn() != null && !vo.getCstmrNativeRrn().isBlank()) {
            return;
        }
        String nativeBirth = normalizeDigits(vo.getCstmrNativeBirth());
        if (!nativeBirth.isBlank()) {
            vo.setCstmrNativeRrn(nativeBirth);
        }
    }

    private static String normalizeDigits(String value) {
        return value == null ? "" : value.replaceAll("\\D", "");
    }

    private static String encryptCustomerRrn(String customerRrn) {
        if (customerRrn == null || customerRrn.isBlank()
            || CryptoUtils.isEncrypted(customerRrn, FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)) {
            return customerRrn;
        }
        return CryptoUtils.encrypt(customerRrn, FieldCryptoAlgorithm.AES_GCM_SEARCHABLE);
    }

    public int insertMcpSvcChgRequestCstmr(Long requestKey) {
        McpRequestCstmrVo vo = msfRequestReadMapper.selectMcpSvcChgRequestCstmr(requestKey);
        if (vo == null) {
            return 0;
        }
        vo.setCstmrNativeRrn(encryptCustomerRrn(vo.getCstmrNativeRrn()));
        vo.setCstmrForeignerRrn(encryptCustomerRrn(vo.getCstmrForeignerRrn()));
        return mcpRequestWriteMapper.insertMcpRequestCstmr(vo);
    }

    public int insertMcpSvcChgRequestAgent(Long requestKey) {
        McpRequestAgentVo vo = msfRequestReadMapper.selectMcpSvcChgRequestAgent(requestKey);
        if (vo == null) {
            return 0;
        }
        vo.setMinorAgentRrn(encryptPaddedAgentRrn(vo.getMinorAgentRrn()));
        vo.setJrdclAgentRrn(encryptPaddedAgentRrn(vo.getJrdclAgentRrn()));
        mcpRequestAgentWriteMapper.insertMcpRequestAgent(vo);
        return 1;
    }

    private static String encryptPaddedAgentRrn(String agentRrn) {
        if (agentRrn == null || agentRrn.isBlank()) {
            return agentRrn;
        }
        String plainAgentRrn = CryptoUtils.isEncrypted(agentRrn, FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
            ? CryptoUtils.decrypt(agentRrn, FieldCryptoAlgorithm.AES_GCM_SEARCHABLE)
            : agentRrn;
        String paddedAgentRrn = plainAgentRrn.length() < 13
            ? plainAgentRrn + "0".repeat(13 - plainAgentRrn.length())
            : plainAgentRrn;
        return CryptoUtils.encrypt(paddedAgentRrn, FieldCryptoAlgorithm.AES_GCM_SEARCHABLE);
    }

    @Transactional(transactionManager = MspDataSourceConfig.MSP_TX_MANAGER)
    public int insertMcpSvcChgCustRequestMst(Long requestKey, InsuranceProcessRequest insuranceRequest) {
        McpCustRequestMstVo vo = msfRequestReadMapper.selectMcpSvcChgCustRequestMst(requestKey);
        if (vo == null) {
            return 0;
        }
        fillCustRequestNativeRrn(vo);
        mcpRequestWriteMapper.insertNmcpCustReqMst(vo);
        if (insuranceRequest != null) {
            mcpRequestWriteMapper.insertCustRequestInsr(insuranceRequest);
        }
        return 1;
    }

    public int insertMcpRequestState(McpRequestStateDto dto) {
        return mcpRequestWriteMapper.insertMcpRequestState(dto);
    }

    public int insertMcpRequestOsstIfAbsent(McpRequestOsstDto dto) {
        return mcpRequestWriteMapper.insertMcpRequestOsstIfAbsent(dto);
    }

    public int insertOsstErrLog(MpErrVO vo) {
        return mcpRequestWriteMapper.insertOsstErrLog(vo);
    }

    public String selectGenerateResNo() { return mcpRequestReadMapper.generateResNo(); }

    public void insertNmcpCustReqMst(McpCustRequestMstVo request) {
        mcpRequestWriteMapper.insertNmcpCustReqMst(request);
    }

    public void insertNmcpCustReqNameChg(McpCustRequestNameChgVo request) { mcpRequestWriteMapper.insertNmcpCustReqNameChg(request); }

    public void insertNmcpCustReqNameChgAgent(McpCustRequestNameChgAgentVo request) { mcpRequestWriteMapper.insertNmcpCustReqNameChgAgent(request); }

    public void updateNmcpCustReqMst(McpCustRequestMstVo request) { mcpRequestWriteMapper.updateNmcpCustReqMst(request); }

    public void updateNmcpCustReqNameChg(McpCustRequestNameChgVo request) { mcpRequestWriteMapper.updateNmcpCustReqNameChg(request); }

    public void insertCustRequestInsr(InsuranceProcessRequest request) { mcpRequestWriteMapper.insertCustRequestInsr(request); }

    public void insertMcpSelfUsimChg(@Valid UsimChangeUC0Request request) { mcpRequestWriteMapper.insertMcpSelfUsimChg(request); }

    public void updateMcpSelfUsimChgUC0(@Valid UsimChangeUC0Request request) { mcpRequestWriteMapper.updateMcpSelfUsimChgUC0(request); }

    public void insertMcpReqCombine(McpReqCombineDto request) { mcpRequestWriteMapper.insertMcpReqCombine(request); }

    public void updateRequestOsst(MsfRequestOsstVo msfRequestOsstVo) { mcpRequestWriteMapper.updateRequestOsst(msfRequestOsstVo); }

    public void insertNmcpCustReqChange(McpCustRequestChangeVo mcpCustRequestChangeVo) {
        mcpRequestWriteMapper.insertNmcpCustReqChange(mcpCustRequestChangeVo);
    }

    public String getDisPrmtId(MspSaleSubsdMstRequest request) { return mcpRequestWriteMapper.getDisPrmtId(request); }

    public Long selectPromoBaseAmt(String prmtId) { return mcpRequestWriteMapper.selectPromoBaseAmt(prmtId); }
}
