package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.util.List;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.MspSaleSubsdMstRequest;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;
import com.ktmmobile.msf.domains.form.form.ownerchange.field.OwnerChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.ownerchange.repository.OwnerChangeRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerChgMcpSvc {

    private final McpRequestRepositoryImpl mcpRequestRepository;
    private final OwnerChangeRepository ownerChangeRepository;
    private final OwnerChangeFieldMapper ownerChangeFieldMapper;

    @Transactional(transactionManager = MspDataSourceConfig.MSP_TX_MANAGER)
    @SuppressWarnings("PMD.EmptyControlStatement")
    public void save(MsfRequestNameChgVo request) {

        log.info("명의변경 MCP 테이블 insert ************");
        log.info("명의변경 parameter MsfRequestNameChgVo => {}", request);

        request.setUserId(AuthenticationUtils.getUser().getUserId());
        request.setReqType("NC");

        /* 임시 암호화 어노테이션으로 변경 예정 */
        // tempEncrypt(request);

        //안면인증
        String fathTrgYn = request.getTrFathTrgYn();
        if ("Y".equals(fathTrgYn)) {
            // FathSessionDto fathSessionDto = fathService.validateFathSession();
            // myNameChgReqDto.setFathTransacId(fathSessionDto.getTransacId());
            // myNameChgReqDto.setFathCmpltNtfyDt(fathSessionDto.getCmpltNtfyDt());
            // myNameChgReqDto.setFathTelNo(myNameChgReqDto.getCstmrReceiveTelFn() + myNameChgReqDto.getCstmrReceiveTelMn() + myNameChgReqDto.getCstmrReceiveTelRn());
            //안면인증 관련 OSST 연동이력 MVNO_ORD_NO 컬럼데이터 '임시예약번호'를 -> '실제예약번호'로 업데이트
            // fathService.updateFathMcpRequestOsst(myNameChgReqDto.getMcnResNo());
        }

        // 가입신청인증정보 저장
        McpCustRequestMstVo mcpCustRequestMstVo = ownerChangeFieldMapper.toMcpCustRequestMstVo(request);
        mcpCustRequestMstVo.setupData();
        mcpRequestRepository.insertNmcpCustReqMst(mcpCustRequestMstVo);

        // MCP_REQUEST_CHANGE 추가 여부 확인 필요
        // McpCustRequestChangeVo mcpCustRequestChangeVo = ownerChangeFieldMapper.toMcpCustRequestChangeVo(request);
        // mcpCustRequestChangeVo.setup(request);
        // mcpRequestRepository.insertNmcpCustReqChange(mcpCustRequestChangeVo);

        // 명의변경정보 저장
        McpCustRequestNameChgVo mcpCustRequestNameChgVo = ownerChangeFieldMapper.toMcpCustRequestNameChgVo(request);
        // name_chg 데이터 세팅
        mcpCustRequestNameChgVo.setup();
        mcpRequestRepository.insertNmcpCustReqNameChg(mcpCustRequestNameChgVo);

        McpCustRequestNameChgAgentVo mcpCustRequestNameChgAgentVo = ownerChangeFieldMapper.toMcpCustRequestNameChgAgentVo(request);
        McpCustRequestNameChgAgentVo mcpCustRequestNameChgAgentGovernVo = ownerChangeFieldMapper.toMcpCustRequestNameChgAgentGovernVo(request);
        mcpCustRequestNameChgAgentGovernVo.setMinorAgentSelfInqryAgrmYn("Y");

        List<String> government = List.of("JP", "GO"); // 법인, 공공기관

        // 법인, 공공기관 대리인인 경우
        if (government.contains(request.getCstmrTypeCd()) && "VDP".equals(request.getCstmrVisitTypeCd())) {
            String gender = "M".equals(request.getJrdclAgentGender()) ? "1" : "2";
            String rrn = request.getJrdclAgentRrn() + gender;
            mcpCustRequestNameChgAgentGovernVo.setMinorAgentRrn(StringUtil.rpad("0", rrn, 13));
            mcpRequestRepository.insertNmcpCustReqNameChgAgent(mcpCustRequestNameChgAgentGovernVo);
        }

        // 미성년자 신청일때
        if ("NM".equals(request.getTrnsCstmrTypeCd()) || "NM".equals(request.getCstmrTypeCd())
            || "FM".equals(request.getTrnsCstmrTypeCd()) || "FM".equals(request.getCstmrTypeCd())) {
            mcpRequestRepository.insertNmcpCustReqNameChgAgent(mcpCustRequestNameChgAgentVo);
            // 양도인 미성년자
            if ("NM".equals(request.getTrnsCstmrTypeCd()) || "FM".equals(request.getTrnsCstmrTypeCd())) {
                request.setTrAuthInfo(""); // ONLINE_AUTH_INFO -> AUTH_INFO
                //request.setGrOnlineAuthType(""); // 없음
                // 양도인 법정대리인 인증정보는 NMCP_CUST_REQUEST_MST 에 넣지 않는다.
                mcpRequestRepository.updateNmcpCustReqMst(mcpCustRequestMstVo);
            }
            // 양수인 미성년자
            if ("NM".equals(request.getCstmrTypeCd()) || "FM".equals(request.getCstmrTypeCd())) {
                request.setTeAuthInfo("");
                // request.setTeAuthType("");
                // request.setTeIdentityIssuDate(""); //  SELF_ISSU_EXPR_DT -> IDENTITY_ISSU_DATE
                // request.setTeSelfIssuNo(""); // SELF_ISSU_NUM -> SELF_ISSU_NO
                // request.setSelfCertType(""); // 없음
                // request.setSelfCstmrCi(""); // 없음
                // 양수인 법정대리인 인증정보는 NMCP_CUST_REQUEST_NAME_CHG 에 넣지 않는다.
                mcpRequestRepository.updateNmcpCustReqNameChg(mcpCustRequestNameChgVo);
            }
        }
    }

    public void updateRequestOsst(MsfRequestOsstVo msfRequestOsstVo) {
        mcpRequestRepository.updateRequestOsst(msfRequestOsstVo);
    }

    public MspRateMstDto selectRateInfo(String rateCd) { return ownerChangeRepository.selectRateInfo(rateCd); }

    public Long ownerChangeFormPrmtAmtGet(MspSaleSubsdMstRequest request) {
        String disPrmtId = mcpRequestRepository.getDisPrmtId(request);
        request.setDisPrmtId(disPrmtId);

        if (StringUtils.hasText(disPrmtId)) {
            return mcpRequestRepository.selectPromoBaseAmt(disPrmtId);
        }
        return 0L;
    }
}
