package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MyCombinationResDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CombineSelfRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CombineSelfResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpReqCombineDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MplatFormX20Response;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MplatFormX87Response;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MplatFormY44Response;

@Service
@RequiredArgsConstructor
public class MsfCombineSvcServiceImpl {

    private final McpApiClient mcpApiClient;
    private final McpRequestRepositoryImpl mcpRequestRepository;
    private final MspPrxClient mspPrxClient;
    private final ObjectMapper objectMapper;

    @Value("${app.config.comb-svc-no}")
    private String combSvcNo;
    @Value("${app.config.mst-svc-cont-id}")
    private String mstSvcContId;

    /**
     * 아무나SOLO 결합 가능 체크
     *
     * [처리 순서]
     *
     * ASIS 참조: /content/checkCombineSelfAjax.do
     */
    public FormResponse<CombineSelfResponse> combineSelfCheck(@Valid CombineSelfRequest request) throws IOException {

        HashMap<String, String> params = new HashMap<>();
        params.put("ncn", request.getNcn());
        params.put("ctn", request.getCtn());
        params.put("custId", request.getCustId());

        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrList", params, List.class);
        McpUserCntrMngDto sodDesc = mcpApiClient.post("/mypage/socDesc", request.getNcn(), McpUserCntrMngDto.class);

        // 회선 없는 경우
        if (CollectionUtils.isEmpty(cntrList)) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_CONTRACT_NOT_FOUND);
        }

        // 요금제 데이터 없는 경우
        if (Objects.isNull(sodDesc)) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_PLAN_NOT_FOUND);
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();

        // 회선 상태 정지나 미납인 경우
        if (!"A".equals(mcpUserCntrMngDto.getSubStatus())) {
            return FormResponse.of(ResSvcChgMessage.COMBINE_INVALID_STATUS);
        }

        //법인 회선 여부 MasterCombineLineInfo 필요?

        // 이용중인 부가서비스 조회(X20)
        params.put("appEventCd", "X20");
        MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
            .parameters(params)
            .build());

        MplatFormX20Response mplatFormX20Response = XmlConvertUtils.xmlReturnParser(mspResult.rawXml(),
            MplatFormX20Response.class);

        if (!mplatFormX20Response.getCommHeader().isSuccess()) {
            return FormResponse.of(mplatFormX20Response.getCommHeader().getResponseCode(),
                mplatFormX20Response.getCommHeader().getResponseBasic(),
                null);
        }

        List<MplatFormX20Response.OutDto.X20ResultDto> addSvcList = mplatFormX20Response.getOutDto().getOutDto();

        // 신청 이력 존재
        for (MplatFormX20Response.OutDto.X20ResultDto dto: addSvcList) {
            if ("PL249Q800".equals(dto.getSoc())) {
                return FormResponse.of(ResSvcChgMessage.COMBINE_ING);
            }
        }

        //대상 요금제 체크
        String tempRateCd = sodDesc.getSoc();
        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombinationResDto = mcpApiClient.post("/msp/mspCombRateMapp", tempRateCd, MyCombinationResDto.class);

        // 결합 불가 상품
        if (Objects.isNull(myCombinationResDto)) {
            return FormResponse.of(ResSvcChgMessage.COMBINE_UNABLE_SOC);
        }

        // 결합 불가 상품(EMPTY)
        if ("EMPTY".equals(myCombinationResDto.getrRateCd())) {
            return FormResponse.of(ResSvcChgMessage.COMBINE_UNABLE_SOC_EMPTY);
        }

        // 아무나결합/가족결합 여부(X87)
        params.put("appEventCd", "X87");
        MspPrxSoapResponse mspX87Result = mspPrxClient.callService(MspPrxFormRequest.builder()
            .parameters(params)
            .build());

        MplatFormX87Response mplatFormX87Response = XmlConvertUtils.xmlReturnParser(mspX87Result.rawXml(), MplatFormX87Response.class);
        CombineSelfResponse combineSelfResponse = CombineSelfResponse.builder().isCombine(false).rRateNm(myCombinationResDto.getrRateNm()).rRateCd(
            myCombinationResDto.getrRateCd()).rateCd(sodDesc.getSoc()).rateNm(sodDesc.getRateNm()).ctn(mcpUserCntrMngDto.getSubscriberNo()).ncn(
            mcpUserCntrMngDto.getContractNum()).subLinkName(mcpUserCntrMngDto.getUserName()).build();

        if (mplatFormX87Response.getCommHeader().isSuccess()
            && !CollectionUtils.isEmpty(mplatFormX87Response.getOutDto().getMoscCombDtlListOutDTO())) {
            // 결합 정보 있을 경우 (혜택 소멸)
            combineSelfResponse.expireCombinationBenefit();
        }

        return FormResponse.ok(combineSelfResponse);
    }

    public FormResponse<CombineSelfResponse> combineSelfProcess(@Valid CombineSelfRequest request) throws IOException {

        // 마스터 결합 가능 여부 체크
        FormResponse<CombineSelfResponse> comineCheck = combineSelfCheck(request);

        if (!ResponseMessage.SUCCESS.getCode().equals(comineCheck.resCode())) {
            return comineCheck;
        }

        CombineSelfResponse combineSelfResponse = comineCheck.resData();

        // MasterCombineLineInfo 공통 코드에서 가져오는 법인 회선 번호 / 결합회선번호

        HashMap<String, String> params = new HashMap<>();
        params.put("ncn", request.getNcn());
        params.put("ctn", request.getCtn());
        params.put("custId", request.getCustId());
        params.put("mstSvcContId", mstSvcContId);
        params.put("appEventCd", "Y44");
        MspPrxSoapResponse mspY44Result = mspPrxClient.callService(MspPrxFormRequest.builder()
            .parameters(params)
            .build());
        MplatFormY44Response mplatFormY44Response = XmlConvertUtils.xmlReturnParser(mspY44Result.rawXml(), MplatFormY44Response.class);

        if (!mplatFormY44Response.getCommHeader().isSuccess()) {
            return FormResponse.of(mplatFormY44Response.getCommHeader().getResponseCode(),
                mplatFormY44Response.getCommHeader().getResponseBasic(),
                null);
        }

        String resultCd = mplatFormY44Response.getOutDto().getResultCd();
        String resultMsg = mplatFormY44Response.getOutDto().getResultMsg();

        if (!ResponseMessage.SUCCESS.getCode().equals(mplatFormY44Response.getOutDto().getResultCd())) {
            return FormResponse.of(resultCd, resultMsg, null);
        }

        //결과 DB 저장 처리
        McpReqCombineDto reqCombine = new McpReqCombineDto();  //작업결과 DB저장 처리
        reqCombine.setRequestKey(request.getRequestKey());
        reqCombine.setRsltCd("S");            // '승인여부, R:승인대기, N:미제출, S:승인완료, B:승인반려, C:신청취소,, H:임의보류  ';  <=== 확인 필요
        reqCombine.setCombTypeCd("04");  //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03:* ktM+kt유선)
        reqCombine.setCombTgtTypeCd("03");       // '결합대상 (01: 본인, 02: 가족, 03: 타인)';
        reqCombine.setmCtn(combineSelfResponse.getCtn()); //엠모바일 회선번호
        reqCombine.setmCustNm(combineSelfResponse.getSubLinkName()); //엠모바일 고객이름
        reqCombine.setmCustBirth(request.getUserBirthDate()); //  '엠모바일 생년월일';
        reqCombine.setmSexCd("M".equals(request.getUserGender()) ? "01" : "02"); //     엠모바일 성별 (01: 남자, 02: 여자)';
        reqCombine.setmSvcCntrNo(combineSelfResponse.getNcn()); //엠모바일 계약번호
        reqCombine.setmRateCd(combineSelfResponse.getRateCd()); //엠모바일 상품코드  M_RATE_CD
        reqCombine.setmRateNm(combineSelfResponse.getRateNm()); //엠모바일 상품명
        reqCombine.setmRateAdsvcCd(combineSelfResponse.getRRateCd()); //엠모바일 부가코드
        reqCombine.setmRateAdsvcNm(combineSelfResponse.getRRateNm());         //엠모바일 부가서비스명
        reqCombine.setCombSvcNo(combSvcNo);              // '결합 회선번호 (모바일번호 or 인터넷서비스번호)';
        reqCombine.setCombSvcCntrNo(mstSvcContId);    // '무무 자회선 엠모바일 계약번호';
        reqCombine.setCombCustNm("주식회사 케이티엠모바일");     // '결합자 이름';
        //reqCombine.setCombBirth(childAutSession.getBirthdayOfYYYY());   //'결합자 생년월일';
        reqCombine.setCombSexCd("03");   // '결합자 성별 (01: 남자, 02: 여자 , 03: 법인)';
        reqCombine.setCombSocCd("PL249Q804");           // '결합회선 상품코드 무무결합인경우만 해당';
        reqCombine.setCombSocNm("MVNO마스터결합전용 더미요금제 (엠모바일)");           // '결합회선 상품명 무무결합인경우만 해당';
        reqCombine.setCombRateAdsvcCd("EMPTY");     // '결합회선 부가코드 무무결합인경우만 해당';
        reqCombine.setCombRateAdsvcNm("데이터 제공 없음");     // '결합회선 부가서비스명 무무결합인경우만 해당';
        reqCombine.setRvisnId(AuthenticationUtils.getUser().getUserId());

        mcpRequestRepository.insertMcpReqCombine(reqCombine);

        return FormResponse.ok(combineSelfResponse);
    }

}
