package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;

@Service
@RequiredArgsConstructor
public class OwnerChgMcpSaveSvc {

    private final McpRequestRepositoryImpl mcpRequestRepository;

    @Transactional(transactionManager = MspDataSourceConfig.MSP_TX_MANAGER)
    public void save(MsfRequestNameChgVo request) {

        request.setUserId("82311997");

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

        mcpRequestRepository.insertNmcpCustReqMst(request);
        mcpRequestRepository.insertNmcpCustReqNameChg(request);


        // 미성년자 신청일때
        if ("NM".equals(request.getTrnsCstmrTypeCd()) || "NM".equals(request.getCstmrTypeCd())) {
            mcpRequestRepository.insertNmcpCustReqNameChgAgent(request);
            // 양도인 미성년자
            if ("NM".equals(request.getTrnsCstmrTypeCd())) {
                request.setTrAuthInfo(""); // ONLINE_AUTH_INFO -> AUTH_INFO
                //request.setGrOnlineAuthType(""); // 없음
                // 양도인 법정대리인 인증정보는 NMCP_CUST_REQUEST_MST 에 넣지 않는다.
                mcpRequestRepository.updateNmcpCustReqMst(request);
            }
            // 양수인 미성년자
            if ("NM".equals(request.getCstmrTypeCd())) {
                request.setTeAuthInfo("");
                // request.setTeAuthType("");
                request.setTeIdentityIssuDate(""); //  SELF_ISSU_EXPR_DT -> IDENTITY_ISSU_DATE
                request.setTeSelfIssuNo(""); // SELF_ISSU_NUM -> SELF_ISSU_NO
                // request.setSelfCertType(""); // 없음
                // request.setSelfCstmrCi(""); // 없음
                // 양수인 법정대리인 인증정보는 NMCP_CUST_REQUEST_NAME_CHG 에 넣지 않는다.
                mcpRequestRepository.updateNmcpCustReqNameChg(request);
            }
        }
    }
}
