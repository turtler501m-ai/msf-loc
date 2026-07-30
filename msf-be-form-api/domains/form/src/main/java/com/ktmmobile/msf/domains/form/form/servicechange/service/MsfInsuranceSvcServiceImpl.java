package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxJsonRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.McpCustRequestMstVo;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MplatFormY24Y25Response;
import com.ktmmobile.msf.domains.form.form.servicechange.field.ServiceChangeFieldMapper;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfInsuranceSvcServiceImpl {

    private final McpApiClient mcpApiClient;
    private final McpRequestRepositoryImpl mcpRequestRepository;
    private final MspPrxClient mspPrxClient;
    private final ServiceChangeFieldMapper serviceChangeFieldMapper;

    /** 단말 보험 유효성 체크 **/
    public FormResponse<InsuranceProcessResponse> insurAvailable(InsuranceProcessRequest request) {
        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("ncn", request.getNcn());
        mapData.put("cntrMobileNo", request.getCtn());

        // 계약 정보 조회
        List<McpUserCntrMngDto> list = mcpApiClient.post("/mypage/cntrList", mapData, List.class);
        McpUserCntrMngDto mcpUserCntrMngDto = list.getFirst();

        if (mcpUserCntrMngDto == null) {
            // 회선 없음
            return FormResponse.of(ResSvcChgMessage.EMPTY, ResSvcChgMessage.EMPTY.getMessage(), null);
        }

        /**************** 계약번호로 안심보험 가입정보 조회하여 보험타입 구하기 start  ***************************/
        //String strNcn = searchVO.getNcn(); //계약번호
        //String strContractNum = request.getNcn();
        String strActiveDate = mcpUserCntrMngDto.getLstComActvDate(); //개통일자
        String insrStatCd = ""; //보험가입상태(0:처리중,1:가입,2:해지..)
        String orderExist = ""; //보험가입진행중
        String requestExist = ""; //이미보험가입신청
        // String reqBuyType = ""; //구매유형
        // String custType = mcpUserCntrMngDto.getCstmrType(); //고객타입(I:개인,G:공공기관,B:법인)
        //String unUserSsn = mcpUserCntrMngDto.getUnUserSSn(); //고객주민번호
        // String customerType = ""; //고객종류(NA,NM,FN)
        long diffDays = 0; //개통일과 오늘의 일 수 차이
        //계약번호로 보험가입정보조회
        Map<String, String> insrInfo = mcpApiClient.post("/mypage/getInsrInfo", request.getNcn(), Map.class);

        //보헙가입정보
        // customerType = (String) insrInfo.get("CSTMR_TYPE"); //고객유형
        // reqBuyType = (String) insrInfo.get("REQ_BUY_TYPE"); //구매유형
        insrStatCd = (String) insrInfo.get("INSR_STAT_CD");  //보험가입상태
        orderExist = (String) insrInfo.get("ORDER_EXIST");  //보험가입진행중
        requestExist = (String) insrInfo.get("REQUEST_EXIST"); //이미보험가입신청

        //instStatCd가 0이나 1이면 보험가입 중 (가입 중인 경우 대상아님)
        if ("0".equals(insrStatCd) || "1".equals(insrStatCd)) {
            return FormResponse.of(ResSvcChgMessage.ALREADY_REGISTERED, ResSvcChgMessage.ALREADY_REGISTERED.getMessage(), null);
        }
        //orderExist나 requestExist가 null이 아니면 보험가입진행중 (신청 중 가입 불가)
        if (StringUtil.isNotNull(orderExist) || StringUtil.isNotNull(requestExist)) {
            return FormResponse.of(ResSvcChgMessage.INSUR_ING, ResSvcChgMessage.INSUR_ING.getMessage(), null);
        }

        /* 2026-06-18 법인, 미성년자 체크 로직 주석 요청 */
        // 신청상이나 개인고객이 아니거나 미성년자인 경우 고객센터안내 (법인, 미성년자, 신청 상이: 구매유형 없을 경우)
        // if ((StringUtil.isBlank(reqBuyType) || !"I".equals(custType)) ||
        //     ("NM".equals(customerType) || StringUtil.isBlank(customerType))) {
        //     return FormResponse.of(ResSvcChgMessage.NEED_NOTICE, ResSvcChgMessage.NEED_NOTICE.getMessage(), null);
        // }

        //개통일자로부터 오늘까지 날짜 계산
        if (StringUtil.isNotBlank(strActiveDate)) {
            try {
                Date format = new SimpleDateFormat("yyyyMMdd", Locale.ROOT).parse(strActiveDate);
                Date today = new Date();
                diffDays = ((today.getTime() - format.getTime()) / 1000) / (24 * 60 * 60);
            } catch (ParseException e) {
                log.error("Exception e : {}", e.getMessage());
            }

            if (diffDays > 45) {
                return FormResponse.of(ResSvcChgMessage.ACTIVATION_PERIOD_EXPIRED, ResSvcChgMessage.ACTIVATION_PERIOD_EXPIRED.getMessage(), null);
            }
        }

        // 상품변경 사전체크 Y24 (보험 가입 가능 여부 체크)
        MspPrxJsonRequest.MspPrxJsonRequestBuilder builder = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y24")
            .property("ncn", StringUtil.NVL(request.getNcn(), ""))
            .property("ctn", StringUtil.NVL(request.getCtn(), ""))
            .property("custId", StringUtil.NVL(request.getCustId(), ""))
            .property("actCode", "SRG")
            .property("ip", RequestUtils.getClientIp())
            .property("mdlInd", "MSP")
            .property("prdcList", request.getPrdcList())
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn(StringUtil.NVL(request.getNcn(), ""))
                .subscriberNo(StringUtil.NVL(request.getCtn(), ""))
                .eventCd("Y24")
                .trtmRsltSbst("보험 가입 가능 여부 체크")
                .build());

        try {
            MspPrxSoapResponse jsonRes = mspPrxClient.callServiceJson(builder.build());
            String jsonXml = jsonRes.rawXml();
            MplatFormY24Y25Response mplatFormY24Y25Response = XmlConvertUtils.xmlReturnParser(jsonXml, MplatFormY24Y25Response.class);

            if (!mplatFormY24Y25Response.getCommHeader().isSuccess()) {
                return FormResponse.of(ResSvcChgMessage.JOIN_FAIL, mplatFormY24Y25Response.getCommHeader().getResponseBasic(), null);
            }

            if (!ResSvcChgMessage.SUCCESS.getCode().equals(mplatFormY24Y25Response.getOutDto().getRsltCd())) {

                if (mplatFormY24Y25Response.getOutDto().getRuleList() != null) {
                    return FormResponse.of(ResSvcChgMessage.JOIN_FAIL, mplatFormY24Y25Response.getOutDto().getRuleList().getRuleMsgSbst(), null);
                }

                return FormResponse.of(ResSvcChgMessage.JOIN_FAIL, mplatFormY24Y25Response.getOutDto().getRsltMsg(), null);
            }

        } catch (Exception e) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }


        return FormResponse.of(ResSvcChgMessage.SUCCESS, null);
    }

    /** 단말 보험 신청 **/
    @BusinessContextBoundary
    public FormResponse<InsuranceProcessResponse> insurProcess(InsuranceProcessRequest request) {
        FormResponse<InsuranceProcessResponse> prepareResponse = prepareInsuranceRequest(request);
        if (prepareResponse != null) {
            return prepareResponse;
        }

        saveStandaloneInsuranceRequest(request);
        return requestInsuranceSubscription(request);
    }

    /** 서비스변경 신청서 내 단말보험 처리: MCP 신청 데이터는 서비스변경 메인에서 저장한다. */
    public FormResponse<InsuranceProcessResponse> insurProcessForServiceChange(InsuranceProcessRequest request) {
        FormResponse<InsuranceProcessResponse> prepareResponse = prepareInsuranceRequest(request);
        if (prepareResponse != null) {
            return prepareResponse;
        }

        return requestInsuranceSubscription(request);
    }

    private FormResponse<InsuranceProcessResponse> prepareInsuranceRequest(InsuranceProcessRequest request) {
        BusinessContextHolder.setParentScanId(request.getParentScanId());

        List<InsuranceProcessRequest.PrdcList> prdcList = List.of(InsuranceProcessRequest.PrdcList.builder().prdcCd(request.getInsrProdCd())
            .prdcSbscTrtmCd("A").prdcTypeCd("R").build());
        request.setPrdcList(prdcList);

        FormResponse<InsuranceProcessResponse> validResponse = insurAvailable(request);
        String resCode = validResponse.resCode();

        if (!ResSvcChgMessage.SUCCESS.getCode().equals(resCode)) {
            return validResponse;
        }

        HashMap<String, String> mapData = new HashMap<>();
        mapData.put("ncn", request.getNcn());
        mapData.put("cntrMobileNo", request.getCtn());

        // 계약 정보 조회
        List<McpUserCntrMngDto> list = mcpApiClient.post("/mypage/cntrList", mapData, List.class);
        McpUserCntrMngDto mcpUserCntrMngDto = list.getFirst();

        if (mcpUserCntrMngDto == null) {
            // 회선 없음
            return FormResponse.of(ResSvcChgMessage.EMPTY, null);
        }

        /**************** 계약번호로 안심보험 가입정보 조회하여 보험타입 구하기 end  ***************************/

        //1. CUST_REQUEST_SEQ 추출
        // long custReqSeq = formCommService.getCustRequestSeq();
        // request.setCustReqSeq(custReqSeq);
        request.setCstmrNativeRrn(mcpUserCntrMngDto.getUnUserSSn());
        request.setCretId(AuthenticationUtils.getUser().getUserId());
        request.setReqType("IS");

        return null;
    }

    private void saveStandaloneInsuranceRequest(InsuranceProcessRequest request) {
        McpCustRequestMstVo mcpCustRequestMstVo = serviceChangeFieldMapper.toMcpCustRequestMstVo(request);
        mcpRequestRepository.insertNmcpCustReqMst(mcpCustRequestMstVo);
        mcpRequestRepository.insertCustRequestInsr(request);
    }

    private FormResponse<InsuranceProcessResponse> requestInsuranceSubscription(InsuranceProcessRequest request) {
        //안심보험 부가서비스 가입 처리(Y25)
        MspPrxJsonRequest.MspPrxJsonRequestBuilder builder = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y25")
            .property("ncn", StringUtil.NVL(request.getNcn(), ""))
            .property("ctn", StringUtil.NVL(request.getCtn(), ""))
            .property("custId", StringUtil.NVL(request.getCustId(), ""))
            .property("actCode", "SRG")
            .property("ip", RequestUtils.getClientIp())
            .property("mdlInd", "MSP")
            .property("prdcList", request.getPrdcList())
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn(StringUtil.NVL(request.getNcn(), ""))
                .subscriberNo(StringUtil.NVL(request.getCtn(), ""))
                .eventCd("Y25")
                .trtmRsltSbst("안심보험 부가서비스 가입")
                .build());

        try {
            BusinessContextHolder.setParentScanId(request != null ? request.getParentScanId() : null);
            MspPrxSoapResponse jsonRes = mspPrxClient.callServiceJson(builder.build());
            String jsonXml = jsonRes.rawXml();
            MplatFormY24Y25Response mplatFormY24Y25Response = XmlConvertUtils.xmlReturnParser(jsonXml, MplatFormY24Y25Response.class);

            if (!mplatFormY24Y25Response.getCommHeader().isSuccess()) {
                return FormResponse.of(ResSvcChgMessage.JOIN_FAIL, mplatFormY24Y25Response.getCommHeader().getResponseBasic(), null);
            }

            if (!ResSvcChgMessage.SUCCESS.getCode().equals(mplatFormY24Y25Response.getOutDto().getRsltCd())) {

                if (mplatFormY24Y25Response.getOutDto().getRuleList() != null) {
                    return FormResponse.of(ResSvcChgMessage.JOIN_FAIL, mplatFormY24Y25Response.getOutDto().getRuleList().getRuleMsgSbst(), null);
                }

                return FormResponse.of(ResSvcChgMessage.JOIN_FAIL, mplatFormY24Y25Response.getOutDto().getRsltMsg(), null);
            }

        } catch (Exception _) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        // return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        return FormResponse.of(ResSvcChgMessage.SUCCESS, null);
    }
}
