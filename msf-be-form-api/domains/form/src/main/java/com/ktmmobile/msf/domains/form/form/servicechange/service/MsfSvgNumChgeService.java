package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.util.HashMap;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumChgeRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumChgeResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumberChgeProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumberChgeProcessResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfSvgNumChgeService {

    private final static String APIMENUNM = "번호목록조회";

    private final MspPrxClient mspPrxClient;
    private final ObjectMapper objectMapper;

    private final CommonCodeReader commonCodeReader;
    private final McpApiClient mcpApiClient;

    @Autowired
    private MspApiDirectRepository mspApiDirectRepository;

    /**
     * numChgeList
     * 번호목록조회(X31)
     */
    public FormResponse<NumChgeResponse> numChgeList(NumChgeRequest req) {
        // 필수값 체크
        if (!hasValidKey(req)) {
            log.warn("[numChgeList] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
                req != null ? StringUtil.NVL(req.getNcn(), "") : "",
                req != null ? StringUtil.NVL(req.getCustId(), "") : "",
                req != null && !"".equals(normalizePhone(req.getCtn()))
            );
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        // 번호변경 가능한 시간은 평일 오전10시~오후8시까지 가능합니다. (주말 공휴일은 변경불가)
        int nowHour = DateTimeUtil.getHour();
        if (nowHour < 10 || nowHour >= 20) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_NUMBER_CHANGE_TIME_ERROR, "번호변경 가능한 시간은 평일 오전10시~오후8시까지 가능합니다.(주말 공휴일은 변경불가)", null);
        }

        try {
            // 개통일로부터 날짜 조회
            McpUserCntrMngDto out = new McpUserCntrMngDto();
            out.setSvcCntrNo(req.getNcn());
            out.setContractNum(req.getCtn());
            out.setCntrMobileNo(req.getCtn());
            McpUserCntrMngDto resultOut = selectCntrListNoLogin(out);
            if (resultOut == null) {
                return FormResponse.of(ResSvcChgMessage.CHANGE_NUMBER_CHANGE_TIME_ERROR, "가입 정보가 없습니다.", null);
            }

            int moreDay = 0;
            String openingDate = resultOut.getLstComActvDate();
            // 개통일 기준 + N일
            CommonCodesRequest commonCodesRequest = CommonCodesRequest.withIncludeAll("ChangeNumberException"); //조건설정
            CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(commonCodesRequest); // 공통코드 조회 요청
            Optional<CommonCodeData> cmnPeriodLimit = commonCodeGroups.getSingleGroup("moreDay"); // 특정 공통코드 그룹의 특정 DTL_CD 내용 가져오기
            if (cmnPeriodLimit.isPresent()) {
                CommonCodeData.Detail detail = cmnPeriodLimit.get().detail();
                moreDay = Integer.parseInt(StringUtil.NVL(detail.etcValue1(), "0"));
            }

            if (moreDay != 0) {
                boolean isBlocked = DateTimeUtil.isBlocked(openingDate, moreDay);
                if (isBlocked) {
                    return FormResponse.of(ResSvcChgMessage.CHANGE_NUMBER_CHANGE_DAY_ERROR, "번호변경은 개통 후 " + moreDay + "일 이후에 가능합니다.", null);
                }
            }


            // 관련 번수 세팅
            req.setInqrBase("0");
            req.setInqrCascnt("20");

            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X31");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            NumChgeResponse res = XmlConvertUtils.xmlReturnParser(rawXml, NumChgeResponse.class);

            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
        } catch (Exception e) {
            log.error("[numChgeList] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, APIMENUNM + " 조회 중 오류가 발생했습니다.", null);
        }
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        HashMap<String, String> params = new HashMap<>();
        params.put("svcCntrNo", userCntrMngDto.getSvcCntrNo());
        params.put("cntrMobileNo", userCntrMngDto.getCntrMobileNo());
        params.put("subLinkName", userCntrMngDto.getSubLinkName());
        return mspApiDirectRepository.query("/mypage/cntrListNoLogin", params, McpUserCntrMngDto.class);
    }

    /**
     * numberChgeProcess
     * 번호변경(X32)
     */
    @BusinessContextBoundary
    public FormResponse<NumberChgeProcessResponse> numberChgeProcess(NumberChgeProcessRequest req) {
        log.warn("[numberChgeProcess] invalid lookup key: ncn={}, custId={}, ctnPresent={}",
            req != null ? StringUtil.NVL(req.getNcn(), "") : "",
            req != null ? StringUtil.NVL(req.getCustId(), "") : "",
            req != null ? StringUtil.NVL(req.getCtn(), "") : ""
        );

        try {
            // 선불요금제 여부 조회
            int cnt = mcpApiClient.post("/mypage/prePayment", req.getCtn(), int.class);
            if (cnt >= 1) {
                return FormResponse.of("99", ExceptionMsgConstant.NUMBER_CHANGE_PREPAYMENT_EXCEPTION, null);
            }

            // 번호 변경 처리
            BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
            HashMap<String, String> paramMap = objectMapper.convertValue(req, HashMap.class);
            paramMap.put("appEventCd", "X32");
            MspPrxSoapResponse mspResult = mspPrxClient.callService(MspPrxFormRequest.builder()
                .parameters(paramMap)
                .build());
            String rawXml = mspResult.rawXml();
            NumberChgeProcessResponse res = XmlConvertUtils.xmlReturnParser(rawXml, NumberChgeProcessResponse.class);
            if (res == null || res.getCommHeader() == null || !res.getCommHeader().isSuccess()) {
                String responseCode = ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR.getCode();
                String responseMessage = "번호변경 처리 중 오류가 발생했습니다.";
                if (res != null && res.getCommHeader() != null) {
                    String mpResponseCode = StringUtil.NVL(res.getCommHeader().getResponseCode(), "").trim();
                    String mpResponseBasic = StringUtil.NVL(res.getCommHeader().getResponseBasic(), "").trim();
                    if (!mpResponseCode.isEmpty()) {
                        responseCode = mpResponseCode;
                    }
                    if (!mpResponseBasic.isEmpty()) {
                        responseMessage = mpResponseBasic;
                    }
                    log.warn("[numberChgeProcess] mplatform failed: ncn={}, responseType={}, responseCode={}, responseBasic={}",
                        req.getNcn(),
                        res.getCommHeader().getResponseType(),
                        res.getCommHeader().getResponseCode(),
                        res.getCommHeader().getResponseBasic());
                } else {
                    log.warn("[numberChgeProcess] mplatform response missing commHeader: ncn={}", req.getNcn());
                }
                return FormResponse.of(responseCode, responseMessage, res);
            }
            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);

        } catch (Exception e) {
            log.error("[numberChgeProcess] failed: ncn={}", req.getNcn(), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "번호변경 처리 중 오류가 발생했습니다.", null);
        }
    }

    // -- UTIL

    private String normalizePhone(String value) {
        return StringUtil.NVL(value, "").replaceAll("[^0-9]", "");
    }

    private boolean hasValidKey(NumChgeRequest req) {
        return req != null
            && (!"".equals(StringUtil.NVL(req.getNcn(), ""))
            || !"".equals(StringUtil.NVL(req.getCustId(), ""))
            || !"".equals(normalizePhone(req.getCtn())));
    }

}
