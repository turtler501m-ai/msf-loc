package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.HashMap;
import java.util.List;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimRequest;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimResponse;
import com.ktmmobile.msf.domains.form.form.common.dto.RateInfoResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.common.repository.smartform.ProductSmartInfoWriteMapper;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgDtlVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.ProductInventoryRequest;
import com.ktmmobile.msf.domains.form.form.newchange.service.ProductInfoService;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Request;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Response;

@Service
@RequiredArgsConstructor
@Slf4j
public class MsfUsimChangeSvcServiceImpl {

    private final McpApiClient mcpApiClient;
    private final McpRequestRepositoryImpl mcpRequestRepository;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;
    private final ProductSmartInfoWriteMapper productSmartInfoWriteMapper;
    private final ProductSmartInfoReadMapper productSmartInfoReadMapper;
    private final ProductInfoReadMapper productInfoReadMapper;
    private final ProductInfoService productInfoService;

    public FormResponse<UsimChangeUC0Response> usimCheck(UsimChangeUC0Request request) {
        List<String> minorList = List.of("NM", "FM");

        if (minorList.contains(request.getCstmrTypeCd())) {
            return FormResponse.of(ResSvcChgMessage.MINOR_UNCHANGE_USIM, null);
        }

        HashMap<String, String> params = new HashMap<String, String>();
        params.put("svcCntrNo", request.getNcn());
        params.put("cntrMobileNo", request.getCtn());

        McpUserCntrMngDto mcpUserCntrMngDto = mcpApiClient.post("/mypage/cntrListNoLogin", params, McpUserCntrMngDto.class);

        if (mcpUserCntrMngDto == null) {
            return FormResponse.of(ResSvcChgMessage.EMPTY, null);
        }

        // 정지회선일때
        if ("S".equals(mcpUserCntrMngDto.getSubStatus())) {
            return FormResponse.of(ResSvcChgMessage.USIM_STATUS_STOP, null);
        }
        // 미납회원일때
        if ("D".equals(mcpUserCntrMngDto.getColDelinqStatus())) {
            return FormResponse.of(ResSvcChgMessage.USIM_STATUS_NON_PAY, null);
        }
        return FormResponse.ok(null);
    }

    @Transactional(transactionManager = MspDataSourceConfig.MSP_TX_MANAGER)
    @BusinessContextBoundary
    public FormResponse<UsimChangeUC0Response> usimChange(@Valid UsimChangeUC0Request request) {
        BusinessContextHolder.setParentScanId(request != null ? request.getParentScanId() : null);

        FormResponse<UsimChangeUC0Response> checkResponse = usimCheck(request);

        if (!ResponseMessage.SUCCESS.getCode().equals(checkResponse.resCode())) {
            return checkResponse;
        }

        // 기본 값 설정
        request.transPymnMthCd();
        request.setSlsPrsnId(AuthenticationUtils.getUser().getUserId());
        mcpRequestRepository.insertMcpSelfUsimChg(request);

        // UC0 유심변경 처리
        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(request),
            MplatformOsstServiceType.USIM_SELF_CHG.getEventCd(),
            request.getAgentCd(),
            request.getMvnoOrdNo());

        try {
            UsimChangeUC0Response usimChangeUC0Response = XmlConvertUtils.xmlReturnParser(mspPrxSoapResponse.rawXml(), UsimChangeUC0Response.class);
            if (!usimChangeUC0Response.getCommHeader().isSuccess()) {
                return FormResponse.of(usimChangeUC0Response.getCommHeader().getResponseCode(),
                    usimChangeUC0Response.getCommHeader().getResponseBasic(),
                    null);
            }

            if (!ResSvcChgMessage.RSLT_SUCCESS.getCode().equals(usimChangeUC0Response.getOutDto().getRsltCd())) {
                return FormResponse.of(usimChangeUC0Response.getCommHeader().getResponseCode(), usimChangeUC0Response.getOutDto().getRsltMsg(), null);
            }

            request.setOsstOrdNo(usimChangeUC0Response.getOutDto().getOsstOrdNo());
            mcpRequestRepository.updateMcpSelfUsimChgUC0(request);

            // 재고정리
            //신청서 등록 완료한 경우 휴대폰/USIM 재고 MSF_PROD_STOR_INVENTORY_TXN 재고 데이터 ‘접수완료’로 변경 처리
            //use_sttus_cd  = ‘R’ ----(N : 미사용 / R : 접수완료 / A : 사용완료)
            if (!request.isUsimSucc()) {
                String agentCd = request.getAgentCd();
                String iccId = request.getIccId();

                ProductInventoryRequest productInventoryRequest = new ProductInventoryRequest();
                productInventoryRequest.setUseSttusCd("R"); //접수완료
                productInventoryRequest.setAgentCd(agentCd); //신청서에 저장된 조직코드
                productInventoryRequest.setProdSn(iccId);
                productSmartInfoWriteMapper.updateMsfProdStorInventoryTxn(productInventoryRequest);
            }

            return FormResponse.ok(usimChangeUC0Response);
        } catch (Exception e) {
            log.warn("[usimChange] exception: ncn={}, ctn={}, msg={}", request.getNcn(), request.getCtn(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ERROR);
        }
    }

    public PriceJoinUsimResponse selectRateInfo(MsfRequestSvcChgDtlVo vo) {

        RateInfoResponse rateInfoResponse = productInfoReadMapper.selectRateInfo(vo);
        // 가입비 및 유심비 조회
        String dataType = rateInfoResponse != null && org.springframework.util.StringUtils.hasText(rateInfoResponse.getDataType())
            ? rateInfoResponse.getDataType()
            : "LTE";
        PriceJoinUsimRequest priceJoinUsimRequest = new PriceJoinUsimRequest();
        priceJoinUsimRequest.setDataType(dataType);
        priceJoinUsimRequest.setPriceGubun("NAC3" + dataType);

        return productInfoReadMapper.selectJoinUsimPrice(priceJoinUsimRequest);
    }

}
