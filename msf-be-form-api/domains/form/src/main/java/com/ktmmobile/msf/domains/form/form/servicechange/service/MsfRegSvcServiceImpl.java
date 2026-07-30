package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxJsonRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.ServiceAlterTraceRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformServiceType;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpRegServiceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormXmlSelfcareRequest;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.repository.msp.ProductInfoReadMapper;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionPreCheckReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionPreCheckResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MplatFormX38Request;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcChgPageRepositoryImpl;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

@Service
@RequiredArgsConstructor
public class MsfRegSvcServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(MsfRegSvcServiceImpl.class);
    private static final String ESB_LINK_ERROR_CODE = "ITL_SYS_E0001";
    private static final long ADDITION_RETRY_DELAY_MS = 3000L;

    /** M플랫폼 연동 서비스 (X97/X38/X21/Y25 등) */
    private final MsfMplatFormService mPlatFormService;

    private final MspApiDirectRepository mspApiDirectRepository;

    private final ProductInfoReadMapper productInfoReadMapper;

    private final SvcChgPageRepositoryImpl svcChgPageRepository;

    private final MspPrxClient mspPrxClient;

    private final ObjectMapper objectMapper;

    private final MsfMcpOsstPrxService msfMcpOsstPrxService;

    // =====================================================
    // TOBE 메서드
    // =====================================================

    /** 이용중 부가서비스 목록 조회 (X97) */
    public FormResponse<AdditionMyListResVO> myAddSvcList(AdditionReqDto req) {
        List<MpSocVO> mSocVoList = new ArrayList<>();
        logger.debug("[myAddSvcList] start: ncn={}, ctn={}, custId={}", req.getNcn(), req.getCtn(), req.getCustId());
        try {
            MpAddSvcInfoParamDto vo = mPlatFormService.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            logger.debug("[myAddSvcList] X97 response: success={}, resultCode={}, svcMsg={}, rawCount={}",
                vo.isSuccess(), vo.getResultCode(), vo.getSvcMsg(), vo.getList() == null ? 0 : vo.getList().size());
            logX97SvcList("myAddSvcList", vo);
            if (!vo.isSuccess()) {
                logger.warn("[myAddSvcList] X97 failed (빈 목록으로 진행): ncn={}, resultCode={}, svcMsg={}",
                    req.getNcn(), vo.getResultCode(), vo.getSvcMsg());
            } else if (vo.getList() != null) {
                mSocVoList = vo.getList();
                int beforeCount = mSocVoList.size();
                mSocVoList.removeIf(item -> "PL249Q800".equals(item.getSoc()));
                populateOnlineCancelInfo(mSocVoList);
                populateShareSubCtnList(mSocVoList);
                logger.debug("[myAddSvcList] filtered list: beforeCount={}, afterCount={}, removedDummyCount={}",
                    beforeCount, mSocVoList.size(), beforeCount - mSocVoList.size());
            }
        } catch (SocketTimeoutException e) {
            logger.warn("[myAddSvcList] socket timeout (빈 목록으로 진행): ncn={}", req.getNcn());
        } catch (SelfServiceException e) {
            logger.warn("[myAddSvcList] self service exception (빈 목록으로 진행): {}", e.getMessage());
        } catch (Exception e) {
            logger.warn("[myAddSvcList] unexpected exception (빈 목록으로 진행): {}", e.getMessage());
        }
        AdditionMyListResVO res = new AdditionMyListResVO();
        res.setList(mSocVoList);
        logger.debug("[myAddSvcList] end: ncn={}, ctn={}, custId={}, resultCount={}",
            req.getNcn(), req.getCtn(), req.getCustId(), res.getList() == null ? 0 : res.getList().size());
        return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
    }

    private void populateOnlineCancelInfo(List<MpSocVO> serviceList) {
        if (serviceList == null || serviceList.isEmpty()) {
            return;
        }

        Map<String, MspRateMstDto> rateInfoCache = new HashMap<>();
        for (MpSocVO item: serviceList) {
            String soc = item == null ? "" : StringUtil.NVL(item.getSoc(), "");
            if (soc.isEmpty()) {
                continue;
            }

            try {
                MspRateMstDto rateInfo = rateInfoCache.computeIfAbsent(
                    soc,
                    key -> mspApiDirectRepository.query("/msp/mspRateMst", key, MspRateMstDto.class)
                );
                if (rateInfo == null) {
                    item.setOnlineCanYn("N");
                    item.setCanCmnt("");
                    continue;
                }

                item.setOnlineCanYn(StringUtil.NVL(rateInfo.getOnlineCanYn(), "N"));
                item.setCanCmnt(StringUtil.NVL(rateInfo.getCanCmnt(), ""));
            } catch (Exception e) {
                logger.warn("[myAddSvcList] online cancel info lookup failed: soc={}, message={}", soc, e.getMessage());
            }
        }
    }

    private void populateShareSubCtnList(List<MpSocVO> serviceList) {
        serviceList.stream()
            .filter(item -> item.getShareSubContidList() != null && !item.getShareSubContidList().isEmpty())
            .forEach(item -> {
                List<String> subCtnList = item.getShareSubContidList().stream()
                    .map(this::findCtnBySvcCntrNo)
                    .filter(ctn -> !ctn.isEmpty())
                    .toList();
                item.setShareSubCtnList(subCtnList);
            });

        serviceList.stream()
            .filter(item -> item.getShareMainContid() != null && !item.getShareMainContid().isEmpty())
            .forEach(item -> {
                item.setShareMainCtn(this.findCtnBySvcCntrNo(item.getShareMainContid()));
            });
    }

    private String findCtnBySvcCntrNo(String svcCntrNo) {
        try {
            McpUserCntrMngDto condition = new McpUserCntrMngDto();
            condition.setSvcCntrNo(svcCntrNo);
            McpUserCntrMngDto contract = svcChgPageRepository.selectCntrListNoLogin(condition);
            return contract == null ? "" : StringUtil.NVL(contract.getCntrMobileNo(), "");
        } catch (Exception e) {
            logger.warn("[myAddSvcList] 서브회선 번호 조회 실패: svcCntrNo={}, message={}",
                svcCntrNo, e.getMessage());
            return "";
        }
    }

    /**
     * 가입가능 부가서비스 목록 조회 (X97 + DB)
     *
     * [처리 순서]
     * 1. M플랫폼 X97 호출 → 현재 가입중인 SOC 목록(useSocList) 추출
     * 2. DB selectRegService(ncn) → MSF에서 관리하는 전체 부가서비스 목록
     * 3. useSocList 기준으로 useYn 매핑 ("Y"=이미 가입 / "N"=미가입)
     * 4. "PL249Q800" 더미 SOC 필터링
     * 5. 유료/무료 분류:
     *    - listC (무료/번들): baseAmt="0" AND svcRelTp="C", 또는 svcRelTp="B"
     *    - listA (유료):      그 외 전부
     *
     * ASIS 참조: selectAddSvcInfoDto() — X20으로 이용중 SOC 조회 → TOBE에서 X97로 교체
     *           X20은 기본 SOC 목록만 반환, X97은 상세 이력 포함 반환
     */

    public FormResponse<AdditionAvailableResVO> selectAddSvcInfoDto(AdditionReqDto req) {
        logger.debug("[selectAddSvcInfoDto] start: ncn={}, ctn={}, custId={}", req.getNcn(), req.getCtn(), req.getCustId());
        // [1] X97 — 현재 가입중인 SOC 목록 추출 (useYn 매핑용)
        List<String> useSocList = new ArrayList<>();
        try {
            MpAddSvcInfoParamDto vo = mPlatFormService.getAddSvcInfoParamDto(req.getNcn(), req.getCtn(), req.getCustId());
            logger.debug("[selectAddSvcInfoDto] X97 response: success={}, resultCode={}, rawCount={}",
                vo.isSuccess(), vo.getResultCode(), vo.getList() == null ? 0 : vo.getList().size());
            logX97SvcList("selectAddSvcInfoDto", vo);
            if (!vo.isSuccess()) {
                logger.warn("[selectAddSvcInfoDto] X97 failed: ncn={}, resultCode={}, svcMsg={}", req.getNcn(), vo.getResultCode(), vo.getSvcMsg());
                throw new McpCommonException(COMMON_EXCEPTION);
            }
            List<MpSocVO> mSocVoList = vo.getList();
            if (mSocVoList != null) {
                for (MpSocVO mSocVo: mSocVoList) {
                    useSocList.add(mSocVo.getSoc());
                }
            }
            logger.debug("[selectAddSvcInfoDto] useSocList: count={}, socs={}", useSocList.size(), useSocList);
        } catch (SocketTimeoutException e) {
            logger.warn("[selectAddSvcInfoDto] socket timeout: ncn={}", req.getNcn());
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            logger.warn("[selectAddSvcInfoDto] self service exception: {}", e.getMessage());
            throw new McpCommonException(e.getMessage());
        }

        // [2] DB — MSF 관리 전체 부가서비스 목록 (MSF_REG_SVC_MST 등)
        // iterator remove를 위해 tmpList → 새 ArrayList로 복사
        Object regServiceResult = mspApiDirectRepository.query("/mypage/regService", req.getNcn(), Object.class);
        List<McpRegServiceDto> list = toMcpRegServiceList(regServiceResult);
        logger.debug("[selectAddSvcInfoDto] DB selectRegService: ncn={}, totalCount={}", req.getNcn(), list.size());

        // ASIS와 동일: X97(useSocList) 기반으로 WIRELESSC 가입 여부 판단
        boolean wirelessBlockInUse = useSocList.contains("WIRELESSC");
        logger.debug("[selectAddSvcInfoDto] wirelessBlockInUse from X97 useSocList: {}", wirelessBlockInUse);

        List<McpRegServiceDto> listA = new ArrayList<>(); // 유료
        List<McpRegServiceDto> listC = new ArrayList<>(); // 무료/번들

        // [4] "PL249Q800" 더미 SOC 필터링 — 아무나SOLO 내부 SOC, 가입 화면에 노출 금지
        int beforeFilter = list.size();
        list.removeIf(item -> "PL249Q800".equals(item.getRateCd()));
        logger.debug("[selectAddSvcInfoDto] dummy SOC filter: before={}, after={}", beforeFilter, list.size());

        for (McpRegServiceDto item: list) {
            // [3] 이용중 여부 매핑
            item.setUseYn(useSocList.contains(item.getRateCd()) ? "Y" : "N");

            // [5] 유료/무료 분류
            // 무료: 기본료=0 이면서 서비스관계유형=C(무료구성), 또는 유형=B(번들)
            if (("0".equals(item.getBaseAmt()) && "C".equals(item.getSvcRelTp()))
                || "B".equals(item.getSvcRelTp())) {
                listC.add(item);
            } else {
                listA.add(item); // 유료
            }
        }

        logger.debug("[selectAddSvcInfoDto] end: ncn={}, total={}, listA(유료)={}, listC(무료/번들)={}, wirelessBlockInUse={}",
            req.getNcn(), list.size(), listA.size(), listC.size(), wirelessBlockInUse);
        AdditionAvailableResVO res = new AdditionAvailableResVO();
        res.setList(list);
        res.setListA(listA);
        res.setListC(listC);
        res.setWirelessBlockInUse(wirelessBlockInUse);
        return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
    }

    private void logX97SvcList(String caller, MpAddSvcInfoParamDto vo) {
        if (!logger.isDebugEnabled()) {
            return;
        }
        List<MpSocVO> svcList = vo == null ? null : vo.getList();
        logger.debug("[{}] X97 raw response: globalNo={}, body={}",
            caller,
            vo == null ? "" : StringUtil.NVL(vo.getGlobalNo(), ""),
            vo == null ? "" : StringUtil.NVL(vo.getResponseXml(), ""));
        if (svcList == null || svcList.isEmpty()) {
            logger.debug("[{}] X97 svcList detail: []", caller);
            return;
        }
        List<Map<String, Object>> details = new ArrayList<>();
        for (MpSocVO svc: svcList) {
            if (svc == null) {
                continue;
            }
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("effectiveDate", StringUtil.NVL(svc.getEffectiveDate(), ""));
            item.put("prodHstSeq", StringUtil.NVL(svc.getProdHstSeq(), ""));
            item.put("soc", StringUtil.NVL(svc.getSoc(), ""));
            item.put("socDescription", StringUtil.NVL(svc.getSocDescription(), ""));
            item.put("socRateValue", StringUtil.NVL(svc.getSocRateValue(), ""));
            item.put("paramSbst", StringUtil.NVL(svc.getParamSbst(), ""));
            details.add(item);
        }
        try {
            logger.debug("[{}] X97 svcList detail: {}", caller, objectMapper.writeValueAsString(details));
        } catch (Exception e) {
            logger.debug("[{}] X97 svcList detail: {}", caller, details);
        }
    }

    private List<McpRegServiceDto> toMcpRegServiceList(Object value) {
        List<McpRegServiceDto> result = new ArrayList<>();
        if (value == null) {
            return result;
        }
        if (value instanceof List<?> items) {
            for (Object item: items) {
                if (item instanceof McpRegServiceDto dto) {
                    result.add(dto);
                } else if (item != null) {
                    logger.warn("[selectAddSvcInfoDto] unexpected regService item type: {}", item.getClass().getName());
                }
            }
            return result;
        }
        if (value instanceof McpRegServiceDto dto) {
            result.add(dto);
            return result;
        }
        logger.warn("[selectAddSvcInfoDto] unexpected regService result type: {}", value.getClass().getName());
        return result;
    }

    /**
     * 부가서비스 가입/해지 사전체크.
     * 해지 건은 MSP_RATE_MST 온라인 해지 가능 여부를 먼저 확인하고, 통과 시 Y24를 호출한다.
     */
    public FormResponse<AdditionPreCheckResVO> moscPrdcTrtmPreChk(AdditionPreCheckReqDto req) {
        int prdcListSize = req.getPrdcList() == null ? 0 : req.getPrdcList().size();
        logger.debug("[moscPrdcTrtmPreChk] start: ncn={}, ctn={}, custId={}, actCode={}, prmtId={}, prdcListSize={}",
            req.getNcn(), req.getCtn(), req.getCustId(),
            req.getActCode(), req.getPrmtId(), prdcListSize);

        List<AdditionPreCheckReqDto.ProductInfo> prdcList = req.getPrdcList();
        FormResponse<AdditionPreCheckResVO> cancelPreCheckRes = validateCancelServicesByMspRateMst(prdcList);
        if (cancelPreCheckRes != null) {
            return cancelPreCheckRes;
        }

        if (prdcList != null) {
            for (int i = 0; i < prdcList.size(); i++) {
                AdditionPreCheckReqDto.ProductInfo productInfo = prdcList.get(i);
                logger.debug("[moscPrdcTrtmPreChk] prdcList[{}]: prdcCd={}, prdcSbscTrtmCd={}, prdcTypeCd={}, prdcSeqNo={}",
                    i, productInfo.getPrdcCd(), productInfo.getPrdcSbscTrtmCd(),
                    productInfo.getPrdcTypeCd(), productInfo.getPrdcSeqNo());
            }
        }

        try {
            List<String> requestedPrdcCodes = getProductCodes(prdcList);
            AdditionPreCheckResVO successRes = new AdditionPreCheckResVO();
            List<AdditionPreCheckReqDto.ProductInfo> y24RemainingPrdcList =
                prdcList == null ? List.of() : prdcList.stream()
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            boolean hasFailure = false;
            String failureMessage = "";
            int attemptNo = 1;

            // 전체 상품을 멀티로 호출하고, 실패 원인이 특정되지 않으면 첫 번째 미처리 상품만 실패 처리한 뒤 나머지를 다시 멀티로 호출한다.
            while (!y24RemainingPrdcList.isEmpty()) {
                List<AdditionPreCheckReqDto.ProductInfo> y24RequestPrdcList = y24RemainingPrdcList;
                List<String> requestPrdcCodes = getProductCodes(y24RequestPrdcList);
                logger.debug("[moscPrdcTrtmPreChk] Y24 multi request: ncn={}, attemptNo={}, requestCount={}, requestPrdcCodes={}",
                    req.getNcn(), attemptNo, requestPrdcCodes.size(), requestPrdcCodes);

                AdditionPreCheckResVO multiRes;
                boolean multiFailure;
                MspPrxSoapResponse multiResponse = null;
                try {
                    multiResponse = mspPrxClient.callServiceJson(buildY24PreCheckRequest(req, y24RequestPrdcList));
                    multiRes = toAdditionPreCheckRes(multiResponse);

                    if (multiRes == null) {
                        String firstFailedPrdcCode = getFirstProductCode(y24RequestPrdcList);
                        logger.warn(
                            "[moscPrdcTrtmPreChk] Y24 multi response is null, fail first and retry remaining: ncn={}, attemptNo={}, failedPrdcCode={}, requestPrdcCodes={}",
                            req.getNcn(),
                            attemptNo,
                            firstFailedPrdcCode,
                            requestPrdcCodes);
                        multiRes = createY24MultiFailureResult(
                            getFirstProductList(y24RequestPrdcList),
                            "부가서비스 가입 가능 여부를 확인할 수 없습니다.");
                        multiFailure = true;
                    } else {
                        multiFailure = !multiResponse.success() || !isMoscPrdcTrtmPreChkSuccess(multiRes);
                        logger.debug(
                            "[moscPrdcTrtmPreChk] Y24 multi response: ncn={}, attemptNo={}, requestCount={}, responseType={}, responseCode={}, rsltCd={}, resultCode={}, sbscYn={}, multiFailure={}",
                            req.getNcn(),
                            attemptNo,
                            requestPrdcCodes.size(),
                            multiResponse.responseType(),
                            multiResponse.responseCode(),
                            multiRes.getRsltCd(),
                            multiRes.getResultCode(),
                            multiRes.getSbscYn(),
                            multiFailure);
                    }
                } catch (SelfServiceException e) {
                    String firstFailedPrdcCode = getFirstProductCode(y24RequestPrdcList);
                    logger.warn(
                        "[moscPrdcTrtmPreChk] Y24 multi SelfServiceException, fail first and retry remaining: ncn={}, attemptNo={}, failedPrdcCode={}, requestPrdcCodes={}, msg={}",
                        req.getNcn(),
                        attemptNo,
                        firstFailedPrdcCode,
                        requestPrdcCodes,
                        e.getMessage());
                    multiRes = createY24MultiFailureResult(getFirstProductList(y24RequestPrdcList), e.getMessage());
                    multiFailure = true;
                } catch (Exception e) {
                    String firstFailedPrdcCode = getFirstProductCode(y24RequestPrdcList);
                    logger.warn(
                        "[moscPrdcTrtmPreChk] Y24 multi unexpected exception, fail first and retry remaining: ncn={}, attemptNo={}, failedPrdcCode={}, requestPrdcCodes={}, msg={}",
                        req.getNcn(),
                        attemptNo,
                        firstFailedPrdcCode,
                        requestPrdcCodes,
                        e.getMessage());
                    multiRes = createY24MultiFailureResult(
                        getFirstProductList(y24RequestPrdcList),
                        "부가서비스 가입 가능 여부 확인 중 오류가 발생했습니다.");
                    multiFailure = true;
                }

                if (!multiFailure) {
                    markY24MultiCheckSuccess(multiRes, y24RequestPrdcList);
                    mergeY24PreCheckResult(successRes, multiRes, false);
                    logger.debug(
                        "[moscPrdcTrtmPreChk] Y24 multi success: ncn={}, attemptNo={}, successCount={}, successPrdcCodes={}, rsltCd={}, resultCode={}, sbscYn={}",
                        req.getNcn(),
                        attemptNo,
                        requestPrdcCodes.size(),
                        requestPrdcCodes,
                        multiRes.getRsltCd(),
                        multiRes.getResultCode(),
                        multiRes.getSbscYn());
                    y24RemainingPrdcList = List.of();
                    break;
                }

                String message = multiRes == null
                    ? "부가서비스 가입 가능 여부를 확인할 수 없습니다."
                    : getMoscPrdcTrtmPreChkMessage(multiRes);
                List<String> failedPrdcCodes = findMultiFailedProductCodes(multiRes, y24RequestPrdcList);
                List<String> completedPrdcCodes = findMultiCompletedProductCodes(multiRes, y24RequestPrdcList);
                if (completedPrdcCodes.isEmpty()) {
                    List<String> messageMatchedFailedPrdcCodes =
                        findMessageMatchedProductCodes(message, y24RequestPrdcList);
                    if (!messageMatchedFailedPrdcCodes.isEmpty()) {
                        failedPrdcCodes = messageMatchedFailedPrdcCodes;
                        completedPrdcCodes = failedPrdcCodes;
                        logger.warn(
                            "[moscPrdcTrtmPreChk] Y24 multi aggregate failure, fail message matched products and retry remaining: ncn={}, attemptNo={}, failedPrdcCodes={}, requestPrdcCodes={}, rsltCd={}, resultCode={}, sbscYn={}, message={}",
                            req.getNcn(),
                            attemptNo,
                            failedPrdcCodes,
                            requestPrdcCodes,
                            multiRes.getRsltCd(),
                            multiRes.getResultCode(),
                            multiRes.getSbscYn(),
                            message);
                    } else {
                        String firstFailedPrdcCode = getFirstProductCode(y24RequestPrdcList);
                        failedPrdcCodes = "".equals(firstFailedPrdcCode)
                            ? List.of()
                            : List.of(firstFailedPrdcCode);
                        completedPrdcCodes = failedPrdcCodes;
                        logger.warn(
                            "[moscPrdcTrtmPreChk] Y24 multi aggregate failure, fail first and retry remaining: ncn={}, attemptNo={}, failedPrdcCode={}, requestPrdcCodes={}, rsltCd={}, resultCode={}, sbscYn={}, message={}",
                            req.getNcn(),
                            attemptNo,
                            firstFailedPrdcCode,
                            requestPrdcCodes,
                            multiRes.getRsltCd(),
                            multiRes.getResultCode(),
                            multiRes.getSbscYn(),
                            message);
                    }
                }
                normalizeY24MultiPartialResult(
                    multiRes,
                    completedPrdcCodes,
                    failedPrdcCodes);
                mergeY24PreCheckResult(successRes, multiRes, !failedPrdcCodes.isEmpty());
                if (!failedPrdcCodes.isEmpty()) {
                    hasFailure = true;
                    if ("".equals(failureMessage)) {
                        failureMessage = message;
                    }
                }

                y24RemainingPrdcList = excludeProducts(y24RequestPrdcList, completedPrdcCodes);
                logger.warn(
                    "[moscPrdcTrtmPreChk] Y24 multi classified: ncn={}, attemptNo={}, requestCount={}, completedCount={}, failedCount={}, remainingCount={}, completedPrdcCodes={}, failedPrdcCodes={}, remainingPrdcCodes={}, responseType={}, responseCode={}, rsltCd={}, resultCode={}, sbscYn={}, message={}",
                    req.getNcn(),
                    attemptNo,
                    requestPrdcCodes.size(),
                    completedPrdcCodes.size(),
                    failedPrdcCodes.size(),
                    y24RemainingPrdcList.size(),
                    completedPrdcCodes,
                    failedPrdcCodes,
                    getProductCodes(y24RemainingPrdcList),
                    multiResponse == null ? "" : multiResponse.responseType(),
                    multiResponse == null ? "" : multiResponse.responseCode(),
                    multiRes.getRsltCd(),
                    multiRes.getResultCode(),
                    multiRes.getSbscYn(),
                    message);
                attemptNo++;
            }

            orderY24PreCheckResults(successRes, prdcList);

            if (hasFailure) {
                logger.warn(
                    "[moscPrdcTrtmPreChk] Y24 final partial failure: ncn={}, totalCount={}, successCount={}, failCount={}, resultPrdcCodes={}, failedPrdcCodes={}, message={}",
                    req.getNcn(),
                    requestedPrdcCodes.size(),
                    countY24PreCheckResults(successRes, "Y"),
                    countY24PreCheckResults(successRes, "N"),
                    getY24PreCheckResultCodes(successRes),
                    successRes.getPreCheckFailedPrdcCdList(),
                    failureMessage);
                return FormResponse.of(
                    ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    failureMessage,
                    successRes);
            }

            /*
            // [기존 분리 호출 로직] 처리코드별 멀티 목록 호출 분기
            AdditionPreCheckResVO successRes = null;
            List<List<AdditionPreCheckReqDto.ProductInfo>> y24PrdcGroups = buildY24PreCheckProductGroups(prdcList);
            for (List<AdditionPreCheckReqDto.ProductInfo> y24PrdcList: y24PrdcGroups) {
                MspPrxSoapResponse response = mspPrxClient.callServiceJson(buildY24PreCheckRequest(req, y24PrdcList));
                AdditionPreCheckResVO res = toAdditionPreCheckRes(response);
                if (res == null) {
                    logger.warn("[moscPrdcTrtmPreChk] Y24 response is null: ncn={}, actCode={}", req.getNcn(), req.getActCode());
                    return FormResponse.of(
                        ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                        "부가서비스 가입 가능 여부를 확인할 수 없습니다.",
                        null);
                }
                if (!response.success()) {
                    enrichFailedProductCodes(res, y24PrdcList);
                    String message = getMoscPrdcTrtmPreChkMessage(res);
                    logger.warn("[moscPrdcTrtmPreChk] Y24 responseType failed: ncn={}, responseType={}, responseCode={}, globalNo={}, message={}",
                        req.getNcn(), response.responseType(), response.responseCode(), response.globalNo(), message);
                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, res);
                }
                if (!isMoscPrdcTrtmPreChkSuccess(res)) {
                    enrichFailedProductCodes(res, y24PrdcList);
                    String message = getMoscPrdcTrtmPreChkMessage(res);
                    logger.warn("[moscPrdcTrtmPreChk] Y24 failed: ncn={}, rsltCd={}, resultCode={}, sbscYn={}, message={}",
                        req.getNcn(), res.getRsltCd(), res.getResultCode(), res.getSbscYn(), message);
                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, res);
                }
                successRes = res;
            }
            */
            logger.debug(
                "[moscPrdcTrtmPreChk] Y24 final success: ncn={}, totalCount={}, successCount={}, resultPrdcCodes={}, rsltCd={}, resultCode={}, sbscYn={}",
                req.getNcn(),
                requestedPrdcCodes.size(),
                countY24PreCheckResults(successRes, "Y"),
                getY24PreCheckResultCodes(successRes),
                successRes.getRsltCd(),
                successRes.getResultCode(),
                successRes.getSbscYn());
            return FormResponse.of(ResSvcChgMessage.SUCCESS, successRes);
        } catch (SelfServiceException e) {
            logger.warn("[moscPrdcTrtmPreChk] Y24 SelfServiceException: ncn={}, msg={}", req.getNcn(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            logger.warn("[moscPrdcTrtmPreChk] Y24 unexpected exception: ncn={}, msg={}", req.getNcn(), e.getMessage());
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "부가서비스 가입 가능 여부 확인 중 오류가 발생했습니다.",
                null);
        }
    }

    private List<String> findMultiFailedProductCodes(
        AdditionPreCheckResVO res,
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList
    ) {
        if (res == null || requestedProductList == null || requestedProductList.isEmpty()) {
            return List.of();
        }

        List<String> requestedCodes = requestedProductList.stream()
            .filter(Objects::nonNull)
            .map(AdditionPreCheckReqDto.ProductInfo::getPrdcCd)
            .map(prdcCd -> StringUtil.NVL(prdcCd, ""))
            .filter(prdcCd -> !"".equals(prdcCd))
            .distinct()
            .collect(Collectors.toList());
        List<String> failedCodes = new ArrayList<>();

        if (res.getRuleList() != null) {
            for (AdditionPreCheckResVO.RuleInfo ruleInfo: res.getRuleList()) {
                addRequestedProductCode(failedCodes, requestedCodes, ruleInfo.getPrdcCd());
            }
        }
        if (res.getPreCheckResultList() != null) {
            for (AdditionPreCheckResVO.PreCheckResultInfo resultInfo: res.getPreCheckResultList()) {
                if ("N".equalsIgnoreCase(StringUtil.NVL(resultInfo.getSuccessYn(), ""))) {
                    addRequestedProductCode(failedCodes, requestedCodes, resultInfo.getPrdcCd());
                }
            }
        }
        if (res.getPreCheckFailedPrdcCdList() != null) {
            for (String prdcCd: res.getPreCheckFailedPrdcCdList()) {
                addRequestedProductCode(failedCodes, requestedCodes, prdcCd);
            }
        }
        if (res.getPrdcCdList() != null) {
            for (String prdcCd: res.getPrdcCdList()) {
                addRequestedProductCode(failedCodes, requestedCodes, prdcCd);
            }
        }
        addRequestedProductCode(failedCodes, requestedCodes, res.getPrdcCd());
        return failedCodes;
    }

    private List<String> findMultiCompletedProductCodes(
        AdditionPreCheckResVO res,
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList
    ) {
        if (res == null || requestedProductList == null || requestedProductList.isEmpty()) {
            return List.of();
        }

        List<String> requestedCodes = getProductCodes(requestedProductList);
        List<String> completedCodes = new ArrayList<>();

        if (res.getPreCheckResultList() != null) {
            for (AdditionPreCheckResVO.PreCheckResultInfo resultInfo: res.getPreCheckResultList()) {
                String prdcCd = StringUtil.NVL(resultInfo.getPrdcCd(), "");
                String successYn = StringUtil.NVL(resultInfo.getSuccessYn(), "");
                if (requestedCodes.contains(prdcCd)
                    && !"".equals(successYn)
                    && !completedCodes.contains(prdcCd)) {
                    completedCodes.add(prdcCd);
                }
            }
        }

        for (String prdcCd: findMultiFailedProductCodes(res, requestedProductList)) {
            if (!completedCodes.contains(prdcCd)) {
                completedCodes.add(prdcCd);
            }
        }
        return completedCodes;
    }

    private List<String> getProductCodes(List<AdditionPreCheckReqDto.ProductInfo> productList) {
        if (productList == null || productList.isEmpty()) {
            return List.of();
        }
        return productList.stream()
            .filter(Objects::nonNull)
            .map(AdditionPreCheckReqDto.ProductInfo::getPrdcCd)
            .map(prdcCd -> StringUtil.NVL(prdcCd, ""))
            .filter(prdcCd -> !"".equals(prdcCd))
            .distinct()
            .collect(Collectors.toList());
    }

    private String getFirstProductCode(List<AdditionPreCheckReqDto.ProductInfo> productList) {
        if (productList == null || productList.isEmpty() || productList.get(0) == null) {
            return "";
        }
        return StringUtil.NVL(productList.get(0).getPrdcCd(), "");
    }

    private List<AdditionPreCheckReqDto.ProductInfo> getFirstProductList(
        List<AdditionPreCheckReqDto.ProductInfo> productList
    ) {
        if (productList == null || productList.isEmpty() || productList.get(0) == null) {
            return List.of();
        }
        return List.of(productList.get(0));
    }

    private void addRequestedProductCode(
        List<String> failedCodes,
        List<String> requestedCodes,
        String prdcCd
    ) {
        String normalizedPrdcCd = StringUtil.NVL(prdcCd, "");
        if (requestedCodes.contains(normalizedPrdcCd) && !failedCodes.contains(normalizedPrdcCd)) {
            failedCodes.add(normalizedPrdcCd);
        }
    }

    private List<String> findMessageMatchedProductCodes(
        String message,
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList
    ) {
        String normalizedMessage = StringUtil.NVL(message, "");
        if ("".equals(normalizedMessage)
            || requestedProductList == null
            || requestedProductList.isEmpty()) {
            return List.of();
        }

        List<String> matchedCodes = new ArrayList<>();
        for (String prdcCd: getProductCodes(requestedProductList)) {
            if (normalizedMessage.contains("[" + prdcCd + "]")
                && !matchedCodes.contains(prdcCd)) {
                matchedCodes.add(prdcCd);
            }
        }
        return matchedCodes;
    }

    private void normalizeY24MultiPartialResult(
        AdditionPreCheckResVO res,
        List<String> completedPrdcCodes,
        List<String> failedPrdcCodes
    ) {
        if (res == null) {
            return;
        }

        List<String> completedCodes = completedPrdcCodes == null
            ? List.of()
            : completedPrdcCodes;
        List<String> failedCodes = failedPrdcCodes == null
            ? List.of()
            : failedPrdcCodes;
        if (completedCodes.isEmpty() && !failedCodes.isEmpty()) {
            completedCodes = failedCodes;
        }
        if (completedCodes.isEmpty()) {
            return;
        }

        List<AdditionPreCheckResVO.PreCheckResultInfo> resultList = new ArrayList<>();
        List<String> messageList = new ArrayList<>();
        for (String prdcCd: completedCodes) {
            AdditionPreCheckResVO.PreCheckResultInfo source = findPreCheckResult(res, prdcCd);
            boolean failed = failedCodes.contains(prdcCd)
                || "N".equalsIgnoreCase(StringUtil.NVL(source == null ? "" : source.getSuccessYn(), ""));
            String message = failed
                ? getRuleMessage(res, prdcCd)
                : StringUtil.NVL(source == null ? "" : source.getMessage(), "");
            if (isSelfCareCancelUnavailableMessage(message)) {
                message = ResSvcChgMessage.ADDITION_SELF_CARE_CANCEL_UNAVAILABLE.getMessage();
            }

            AdditionPreCheckResVO.PreCheckResultInfo resultInfo =
                new AdditionPreCheckResVO.PreCheckResultInfo();
            resultInfo.setPrdcCd(prdcCd);
            resultInfo.setSuccessYn(failed ? "N" : "Y");
            resultInfo.setMessage(message);
            resultList.add(resultInfo);
            if (failed) {
                messageList.add(message);
            }
        }

        res.setPrdcCd(failedCodes.isEmpty() ? "" : failedCodes.get(0));
        res.setPrdcCdList(failedCodes.isEmpty() ? null : new ArrayList<>(failedCodes));
        res.setPreCheckFailedPrdcCdList(failedCodes.isEmpty() ? null : new ArrayList<>(failedCodes));
        res.setPreCheckResultList(resultList);
        res.setResltMsgList(messageList.isEmpty() ? null : messageList);
        if (!failedCodes.isEmpty() && "".equals(StringUtil.NVL(res.getResltMsg(), ""))) {
            res.setResltMsg(messageList.isEmpty() ? getMoscPrdcTrtmPreChkMessage(res) : messageList.get(0));
        }
    }

    private AdditionPreCheckResVO.PreCheckResultInfo findPreCheckResult(
        AdditionPreCheckResVO res,
        String prdcCd
    ) {
        if (res == null || res.getPreCheckResultList() == null) {
            return null;
        }
        for (AdditionPreCheckResVO.PreCheckResultInfo resultInfo: res.getPreCheckResultList()) {
            if (prdcCd.equals(StringUtil.NVL(resultInfo.getPrdcCd(), ""))) {
                return resultInfo;
            }
        }
        return null;
    }

    private String getRuleMessage(AdditionPreCheckResVO res, String prdcCd) {
        if (res != null && res.getRuleList() != null) {
            for (AdditionPreCheckResVO.RuleInfo ruleInfo: res.getRuleList()) {
                if (prdcCd.equals(StringUtil.NVL(ruleInfo.getPrdcCd(), ""))) {
                    String message = StringUtil.NVL(ruleInfo.getRuleMsgSbst(), "");
                    if (!"".equals(message)) {
                        return message;
                    }
                }
            }
        }
        return getMoscPrdcTrtmPreChkMessage(res);
    }

    private List<AdditionPreCheckReqDto.ProductInfo> excludeProducts(
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList,
        List<String> excludedPrdcCodes
    ) {
        if (requestedProductList == null || requestedProductList.isEmpty()) {
            return List.of();
        }
        if (excludedPrdcCodes == null || excludedPrdcCodes.isEmpty()) {
            return requestedProductList;
        }
        return requestedProductList.stream()
            .filter(Objects::nonNull)
            .filter(productInfo ->
                !excludedPrdcCodes.contains(StringUtil.NVL(productInfo.getPrdcCd(), "")))
            .collect(Collectors.toList());
    }

    private void orderY24PreCheckResults(
        AdditionPreCheckResVO res,
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList
    ) {
        if (res == null || res.getPreCheckResultList() == null
            || requestedProductList == null || requestedProductList.isEmpty()) {
            return;
        }

        List<AdditionPreCheckResVO.PreCheckResultInfo> orderedResultList = new ArrayList<>();
        for (AdditionPreCheckReqDto.ProductInfo productInfo: requestedProductList) {
            if (productInfo == null) {
                continue;
            }
            String prdcCd = StringUtil.NVL(productInfo.getPrdcCd(), "");
            for (AdditionPreCheckResVO.PreCheckResultInfo resultInfo: res.getPreCheckResultList()) {
                if (prdcCd.equals(StringUtil.NVL(resultInfo.getPrdcCd(), ""))
                    && !orderedResultList.contains(resultInfo)) {
                    orderedResultList.add(resultInfo);
                    break;
                }
            }
        }
        for (AdditionPreCheckResVO.PreCheckResultInfo resultInfo: res.getPreCheckResultList()) {
            if (!orderedResultList.contains(resultInfo)) {
                orderedResultList.add(resultInfo);
            }
        }
        res.setPreCheckResultList(orderedResultList);
    }

    private int countY24PreCheckResults(AdditionPreCheckResVO res, String successYn) {
        if (res == null || res.getPreCheckResultList() == null) {
            return 0;
        }
        String expectedSuccessYn = StringUtil.NVL(successYn, "");
        return (int) res.getPreCheckResultList().stream()
            .filter(Objects::nonNull)
            .filter(resultInfo -> expectedSuccessYn.equalsIgnoreCase(StringUtil.NVL(resultInfo.getSuccessYn(), "")))
            .count();
    }

    private List<String> getY24PreCheckResultCodes(AdditionPreCheckResVO res) {
        if (res == null || res.getPreCheckResultList() == null) {
            return List.of();
        }
        return res.getPreCheckResultList().stream()
            .filter(Objects::nonNull)
            .map(AdditionPreCheckResVO.PreCheckResultInfo::getPrdcCd)
            .map(prdcCd -> StringUtil.NVL(prdcCd, ""))
            .filter(prdcCd -> !"".equals(prdcCd))
            .collect(Collectors.toList());
    }

    private void markY24MultiCheckSuccess(
        AdditionPreCheckResVO res,
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList
    ) {
        if (requestedProductList == null || requestedProductList.isEmpty()) {
            return;
        }

        List<AdditionPreCheckResVO.PreCheckResultInfo> resultList = new ArrayList<>();
        for (AdditionPreCheckReqDto.ProductInfo productInfo: requestedProductList) {
            if (productInfo == null) {
                continue;
            }
            String prdcCd = StringUtil.NVL(productInfo.getPrdcCd(), "");
            if ("".equals(prdcCd)) {
                continue;
            }

            AdditionPreCheckResVO.PreCheckResultInfo resultInfo =
                new AdditionPreCheckResVO.PreCheckResultInfo();
            resultInfo.setPrdcCd(prdcCd);
            resultInfo.setSuccessYn("Y");
            resultInfo.setMessage("");
            resultList.add(resultInfo);
        }
        res.setPreCheckResultList(resultList);
    }

    private AdditionPreCheckResVO createY24MultiFailureResult(
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList,
        String message
    ) {
        AdditionPreCheckResVO res = new AdditionPreCheckResVO();
        List<String> failedProductCodes = getProductCodes(requestedProductList);
        res.setResultCode(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR.getCode());
        res.setSbscYn("N");
        res.setResltMsg(StringUtil.NVL(message, ""));
        res.setPrdcCd(failedProductCodes.isEmpty() ? "" : failedProductCodes.get(0));
        res.setPrdcCdList(failedProductCodes);
        res.setPreCheckFailedPrdcCdList(failedProductCodes);
        res.setResltMsgList(failedProductCodes.stream()
            .map(prdcCd -> StringUtil.NVL(message, ""))
            .collect(Collectors.toList()));
        return res;
    }

    private void mergeY24PreCheckResult(
        AdditionPreCheckResVO merged,
        AdditionPreCheckResVO current,
        boolean currentFailure
    ) {
        if (current == null) {
            return;
        }

        merged.setGlobalNo(firstNotBlank(merged.getGlobalNo(), current.getGlobalNo()));
        merged.setRsltCd(mergeY24ResultValue(merged.getRsltCd(), current.getRsltCd(), currentFailure));
        merged.setResultCode(mergeY24ResultValue(merged.getResultCode(), current.getResultCode(), currentFailure));
        merged.setSbscYn(mergeY24ResultValue(merged.getSbscYn(), current.getSbscYn(), currentFailure));
        merged.setResltMsg(mergeY24ResultValue(merged.getResltMsg(), current.getResltMsg(), currentFailure));
        merged.setSvcMsg(mergeY24ResultValue(merged.getSvcMsg(), current.getSvcMsg(), currentFailure));
        merged.setPrdcCd(firstNotBlank(merged.getPrdcCd(), current.getPrdcCd()));
        merged.setPrdcCdList(mergeDistinctStringList(merged.getPrdcCdList(), current.getPrdcCdList()));
        merged.setPreCheckResultList(mergePreCheckResultList(
            merged.getPreCheckResultList(),
            current.getPreCheckResultList()));
        merged.setPreCheckFailedPrdcCdList(mergeDistinctStringList(
            merged.getPreCheckFailedPrdcCdList(),
            current.getPreCheckFailedPrdcCdList()));
        merged.setOnlineCancelUnavailablePrdcCdList(mergeDistinctStringList(
            merged.getOnlineCancelUnavailablePrdcCdList(),
            current.getOnlineCancelUnavailablePrdcCdList()));
        merged.setResltMsgList(mergeStringList(merged.getResltMsgList(), current.getResltMsgList()));
        merged.setRuleList(mergeRuleList(merged.getRuleList(), current.getRuleList()));
    }

    private String mergeY24ResultValue(String mergedValue, String currentValue, boolean currentFailure) {
        if (currentFailure && !"".equals(StringUtil.NVL(currentValue, ""))) {
            return currentValue;
        }
        return firstNotBlank(mergedValue, currentValue);
    }

    private String firstNotBlank(String first, String second) {
        return !"".equals(StringUtil.NVL(first, "")) ? first : StringUtil.NVL(second, "");
    }

    private List<String> mergeDistinctStringList(List<String> mergedList, List<String> currentList) {
        if ((mergedList == null || mergedList.isEmpty()) && (currentList == null || currentList.isEmpty())) {
            return null;
        }
        List<String> result = mergedList == null ? new ArrayList<>() : new ArrayList<>(mergedList);
        if (currentList != null) {
            for (String value: currentList) {
                if (!result.contains(value)) {
                    result.add(value);
                }
            }
        }
        return result;
    }

    private List<String> mergeStringList(List<String> mergedList, List<String> currentList) {
        if ((mergedList == null || mergedList.isEmpty()) && (currentList == null || currentList.isEmpty())) {
            return null;
        }
        List<String> result = mergedList == null ? new ArrayList<>() : new ArrayList<>(mergedList);
        if (currentList != null) {
            result.addAll(currentList);
        }
        return result;
    }

    private List<AdditionPreCheckResVO.RuleInfo> mergeRuleList(
        List<AdditionPreCheckResVO.RuleInfo> mergedList,
        List<AdditionPreCheckResVO.RuleInfo> currentList
    ) {
        if ((mergedList == null || mergedList.isEmpty()) && (currentList == null || currentList.isEmpty())) {
            return null;
        }
        List<AdditionPreCheckResVO.RuleInfo> result =
            mergedList == null ? new ArrayList<>() : new ArrayList<>(mergedList);
        if (currentList != null) {
            result.addAll(currentList);
        }
        return result;
    }

    private List<AdditionPreCheckResVO.PreCheckResultInfo> mergePreCheckResultList(
        List<AdditionPreCheckResVO.PreCheckResultInfo> mergedList,
        List<AdditionPreCheckResVO.PreCheckResultInfo> currentList
    ) {
        if ((mergedList == null || mergedList.isEmpty()) && (currentList == null || currentList.isEmpty())) {
            return null;
        }
        List<AdditionPreCheckResVO.PreCheckResultInfo> result =
            mergedList == null ? new ArrayList<>() : new ArrayList<>(mergedList);
        if (currentList != null) {
            result.addAll(currentList);
        }
        return result;
    }

    /**
     * Y24 JSON 요청은 prdcList 배열을 유지해서 PRX serviceCallJson.do로 전달한다.
     */
    private MspPrxJsonRequest buildY24PreCheckRequest(
        AdditionPreCheckReqDto req,
        List<AdditionPreCheckReqDto.ProductInfo> prdcList
    ) {
        MspPrxJsonRequest.MspPrxJsonRequestBuilder builder = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y24")
            .property("ncn", StringUtil.NVL(req.getNcn(), ""))
            .property("ctn", StringUtil.NVL(req.getCtn(), ""))
            .property("custId", StringUtil.NVL(req.getCustId(), ""))
            .property("actCode", StringUtil.NVL(req.getActCode(), "SRG"))
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn(StringUtil.NVL(req.getNcn(), ""))
                .subscriberNo(StringUtil.NVL(req.getCtn(), ""))
                .eventCd("Y24")
                .trtmRsltSbst("부가서비스 가입 가능 여부 사전 체크")
                .build());

        addY24LogProperties(builder);
        builder.property("prdcList", prdcList == null
            ? List.of()
            : prdcList.stream()
                .map(this::toY24Product)
                .collect(Collectors.toList()));

        return builder.build();
    }

    private void addY24LogProperties(MspPrxJsonRequest.MspPrxJsonRequestBuilder builder) {
        try {
            if (!(RequestContextHolder.getRequestAttributes() instanceof ServletRequestAttributes attributes)) {
                return;
            }
            HttpServletRequest request = attributes.getRequest();
            builder.property("ip", RequestUtils.getClientIp());
            builder.property("url", request.getRequestURI());
            builder.property("mdlInd", "MSP");

            try {
                builder.property("userid", AuthenticationUtils.getUser().getUserId());
            } catch (RuntimeException e) {
                logger.debug("[moscPrdcTrtmPreChk] authenticated user is unavailable: {}", e.getMessage());
            }
        } catch (Exception e) {
            logger.debug("[moscPrdcTrtmPreChk] failed to add Y24 log properties: {}", e.getMessage());
        }
    }

    //     private List<List<AdditionPreCheckReqDto.ProductInfo>> buildY24PreCheckProductGroups(
    //         List<AdditionPreCheckReqDto.ProductInfo> prdcList
    //     ) {
    //         if (prdcList == null || prdcList.isEmpty()) {
    //             return List.of(List.of());
    //         }
    //
    //         List<AdditionPreCheckReqDto.ProductInfo> validPrdcList = prdcList.stream()
    //             .filter(productInfo -> productInfo != null)
    //             .collect(Collectors.toList());
    //         if (validPrdcList.isEmpty()) {
    //             return List.of(List.of());
    //         }
    //
    //         Map<String, List<AdditionPreCheckReqDto.ProductInfo>> productGroups = new LinkedHashMap<>();
    //         for (String trtmCd: List.of("C", "U", "A")) {
    //             List<AdditionPreCheckReqDto.ProductInfo> group = validPrdcList.stream()
    //                 .filter(productInfo -> trtmCd.equals(StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), "")))
    //                 .collect(Collectors.toList());
    //             if (!group.isEmpty()) {
    //                 productGroups.put(trtmCd, group);
    //             }
    //         }
    //         for (AdditionPreCheckReqDto.ProductInfo productInfo: validPrdcList) {
    //             String trtmCd = StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), "");
    //             if ("C".equals(trtmCd) || "U".equals(trtmCd) || "A".equals(trtmCd)) {
    //                 continue;
    //             }
    //             productGroups.computeIfAbsent(trtmCd, key -> new ArrayList<>()).add(productInfo);
    //         }
    //         if (productGroups.size() > 1) {
    //             logger.debug("[moscPrdcTrtmPreChk] split Y24 request by prdcSbscTrtmCd: groups={}", productGroups.keySet());
    //         }
    //         return new ArrayList<>(productGroups.values());
    //     }

    private Map<String, Object> toY24Product(AdditionPreCheckReqDto.ProductInfo productInfo) {
        Map<String, Object> product = new LinkedHashMap<>();
        if (productInfo == null) {
            return product;
        }
        product.put("prdcCd", StringUtil.NVL(productInfo.getPrdcCd(), ""));
        product.put("prdcSbscTrtmCd", StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), ""));
        product.put("prdcTypeCd", StringUtil.NVL(productInfo.getPrdcTypeCd(), ""));
        product.put("prdcSeqNo", StringUtil.NVL(productInfo.getPrdcSeqNo(), ""));
        product.put("ftrNewParam", StringUtil.NVL(productInfo.getFtrNewParam(), ""));
        return product;
    }

    private AdditionPreCheckResVO toAdditionPreCheckRes(MspPrxSoapResponse response) {
        if (response == null) {
            return null;
        }

        AdditionPreCheckResVO res = new AdditionPreCheckResVO();
        res.setGlobalNo(StringUtil.NVL(response.globalNo(), ""));

        if (!response.success()) {
            String message = StringUtil.NVL(response.responseBasic(), "");
            if (isSelfCareCancelUnavailableMessage(message)) {
                message = ResSvcChgMessage.ADDITION_SELF_CARE_CANCEL_UNAVAILABLE.getMessage();
            }
            res.setResultCode(StringUtil.NVL(response.responseCode(), ""));
            res.setResltMsg(message);
            return res;
        }

        Map<String, Object> outDto = response.payloadObject("outDto").orElse(Map.of());
        String resltMsg = firstText(outDto, "rsltMsg", "resltMsg");
        if (isSelfCareCancelUnavailableMessage(resltMsg)) {
            resltMsg = ResSvcChgMessage.ADDITION_SELF_CARE_CANCEL_UNAVAILABLE.getMessage();
        }
        String svcMsg = text(outDto, "svcMsg");
        if (isSelfCareCancelUnavailableMessage(svcMsg)) {
            svcMsg = ResSvcChgMessage.ADDITION_SELF_CARE_CANCEL_UNAVAILABLE.getMessage();
        }
        List<String> resltMsgList = textList(outDto.get("resltMsgList"));
        if (resltMsgList != null) {
            resltMsgList = resltMsgList.stream()
                .map(message -> isSelfCareCancelUnavailableMessage(message)
                    ? ResSvcChgMessage.ADDITION_SELF_CARE_CANCEL_UNAVAILABLE.getMessage()
                    : message)
                .collect(Collectors.toList());
        }
        res.setRsltCd(text(outDto, "rsltCd"));
        res.setResultCode(text(outDto, "resultCode"));
        res.setSbscYn(text(outDto, "sbscYn"));
        res.setResltMsg(resltMsg);
        res.setSvcMsg(svcMsg);
        res.setPrdcCd(text(outDto, "prdcCd"));
        res.setPrdcCdList(textList(outDto.get("prdcCdList")));
        res.setPreCheckResultList(preCheckResultList(outDto.get("preCheckResultList")));
        res.setPreCheckFailedPrdcCdList(textList(outDto.get("preCheckFailedPrdcCdList")));
        res.setOnlineCancelUnavailablePrdcCdList(textList(outDto.get("onlineCancelUnavailablePrdcCdList")));
        res.setResltMsgList(resltMsgList);
        res.setRuleList(ruleList(outDto.get("ruleList")));
        return res;
    }

    private String firstText(Map<String, Object> map, String... keys) {
        for (String key: keys) {
            String value = text(map, key);
            if (!"".equals(value)) {
                return value;
            }
        }
        return "";
    }

    private String text(Map<String, Object> map, String key) {
        if (map == null) {
            return "";
        }
        Object value = map.get(key);
        return value == null ? "" : String.valueOf(value);
    }

    private List<String> textList(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof List<?> list) {
            return list.stream()
                .map(String::valueOf)
                .collect(Collectors.toList());
        }
        return List.of(String.valueOf(value));
    }

    private List<AdditionPreCheckResVO.RuleInfo> ruleList(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof List<?> list) {
            return list.stream()
                .map(this::ruleInfo)
                .filter(ruleInfo -> ruleInfo != null)
                .collect(Collectors.toList());
        }
        AdditionPreCheckResVO.RuleInfo ruleInfo = ruleInfo(value);
        return ruleInfo == null ? null : List.of(ruleInfo);
    }

    private List<AdditionPreCheckResVO.PreCheckResultInfo> preCheckResultList(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof List<?> list) {
            return list.stream()
                .map(this::preCheckResultInfo)
                .filter(resultInfo -> resultInfo != null)
                .collect(Collectors.toList());
        }
        AdditionPreCheckResVO.PreCheckResultInfo resultInfo = preCheckResultInfo(value);
        return resultInfo == null ? null : List.of(resultInfo);
    }

    @SuppressWarnings("unchecked")
    private AdditionPreCheckResVO.PreCheckResultInfo preCheckResultInfo(Object value) {
        if (!(value instanceof Map<?, ?> valueMap)) {
            return null;
        }
        Map<String, Object> map = (Map<String, Object>) valueMap;
        AdditionPreCheckResVO.PreCheckResultInfo resultInfo =
            new AdditionPreCheckResVO.PreCheckResultInfo();
        resultInfo.setPrdcCd(text(map, "prdcCd"));
        resultInfo.setSuccessYn(text(map, "successYn"));
        String message = firstText(map, "message", "resltMsg", "ruleMsgSbst");
        if (isSelfCareCancelUnavailableMessage(message)) {
            message = ResSvcChgMessage.ADDITION_SELF_CARE_CANCEL_UNAVAILABLE.getMessage();
        }
        resultInfo.setMessage(message);
        return resultInfo;
    }

    @SuppressWarnings("unchecked")
    private AdditionPreCheckResVO.RuleInfo ruleInfo(Object value) {
        if (!(value instanceof Map<?, ?> valueMap)) {
            return null;
        }
        Map<String, Object> map = (Map<String, Object>) valueMap;
        AdditionPreCheckResVO.RuleInfo ruleInfo = new AdditionPreCheckResVO.RuleInfo();
        ruleInfo.setPrdcCd(text(map, "prdcCd"));
        ruleInfo.setPrdcNm(text(map, "prdcNm"));
        ruleInfo.setRuleId(text(map, "ruleId"));
        String ruleMsgSbst = text(map, "ruleMsgSbst");
        if (isSelfCareCancelUnavailableMessage(ruleMsgSbst)) {
            ruleMsgSbst = ResSvcChgMessage.ADDITION_SELF_CARE_CANCEL_UNAVAILABLE.getMessage();
        }
        ruleInfo.setRuleMsgSbst(ruleMsgSbst);
        ruleInfo.setRuleRsltCd(text(map, "ruleRsltCd"));
        ruleInfo.setRuleTypeCd(text(map, "ruleTypeCd"));
        ruleInfo.setTrgtPrdcCd(text(map, "trgtPrdcCd"));
        ruleInfo.setTrgtPrdcNm(text(map, "trgtPrdcNm"));
        return ruleInfo;
    }

    /**
     * 해지 대상 부가서비스의 온라인 해지 가능 여부를 MSP_RATE_MST 기준으로 확인한다.
     */
    private FormResponse<AdditionPreCheckResVO> validateCancelServicesByMspRateMst(
        List<AdditionPreCheckReqDto.ProductInfo> prdcList
    ) {
        if (prdcList == null || prdcList.isEmpty()) {
            return null;
        }

        List<String> rateNotFoundSocList = new ArrayList<>();
        List<String> rateNotFoundMessageList = new ArrayList<>();
        List<String> onlineCancelUnavailableSocList = new ArrayList<>();
        List<String> onlineCancelUnavailableMessageList = new ArrayList<>();

        for (AdditionPreCheckReqDto.ProductInfo productInfo: prdcList) {
            if (productInfo == null || !"C".equals(StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), ""))) {
                continue;
            }

            //20260602 온라인해지가능여부 제외
            // String soc = StringUtil.NVL(productInfo.getPrdcCd(), "");
            // MspRateMstDto mspRateMstDto = mspApiDirectRepository.query("/msp/mspRateMst", soc, MspRateMstDto.class);
            // if (mspRateMstDto == null) {
            //     logger.warn("[moscPrdcTrtmPreChk] MSP_RATE_MST not found for cancel precheck, skip local cancel validation: soc={}", soc);
            //     continue;
            // }
            //
            // String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            // logger.debug("[moscPrdcTrtmPreChk] cancel MSP_RATE_MST: soc={}, onlineCanYn={}, canCmnt={}",
            //     soc, onlineCanYn, mspRateMstDto.getCanCmnt());
            //
            // //20260515 확인 온라인 해지가능여부 체크여부(일단SKIP) EX) NOSPAM4:불법 TM 수신차단 등
            // if (!"Y".equals(onlineCanYn)) {
            //     String canCmnt = StringUtil.NVL(mspRateMstDto.getCanCmnt(), "");
            //     String message = !"".equals(canCmnt)
            //         ? canCmnt
            //         : ResSvcChgMessage.ADDITION_ONLINE_CANCEL_UNAVAILABLE.getMessage();
            //     logger.warn("[moscPrdcTrtmPreChk] 확인 온라인 해지가능여부 체크여부: soc={}, onlineCanYn={}", soc, onlineCanYn);
            //     //TEST_SKIP onlineCancelUnavailableSocList.add(soc);
            //     //TEST_SKIP onlineCancelUnavailableMessageList.add(message);
            //     //TEST_SKIP if ("".equals(onlineCancelUnavailableMessage)) {
            //     //TEST_SKIP     onlineCancelUnavailableMessage = message;
            //     //TEST_SKIP }
            //     //TEST_SKIP continue;
            // }
        }

        if (!rateNotFoundSocList.isEmpty() || !onlineCancelUnavailableSocList.isEmpty()) {
            List<String> failedSocList = new ArrayList<>();
            List<String> failedMessageList = new ArrayList<>();
            failedSocList.addAll(rateNotFoundSocList);
            failedSocList.addAll(onlineCancelUnavailableSocList);
            failedMessageList.addAll(rateNotFoundMessageList);
            failedMessageList.addAll(onlineCancelUnavailableMessageList);

            if (!rateNotFoundSocList.isEmpty()) {
                String message = ResSvcChgMessage.ADDITION_RATE_NOT_FOUND.getMessage();
                return createAdditionPreCheckFailureResponse(
                    ResSvcChgMessage.ADDITION_RATE_NOT_FOUND,
                    failedSocList,
                    rateNotFoundSocList,
                    onlineCancelUnavailableSocList,
                    failedMessageList,
                    message
                );
            }

            String message = ResSvcChgMessage.ADDITION_STORE_ONLINE_CANCEL_UNAVAILABLE.getMessage();
            return createAdditionPreCheckFailureResponse(
                ResSvcChgMessage.ADDITION_ONLINE_CANCEL_UNAVAILABLE,
                failedSocList,
                rateNotFoundSocList,
                onlineCancelUnavailableSocList,
                failedMessageList,
                message
            );
        }

        return null;
    }

    /**
     * 사전체크 실패 응답에 실패 SOC 목록과 메시지 목록을 일관되게 담는다.
     */
    private FormResponse<AdditionPreCheckResVO> createAdditionPreCheckFailureResponse(
        ResSvcChgMessage responseMessage,
        List<String> prdcCdList,
        List<String> preCheckFailedPrdcCdList,
        List<String> onlineCancelUnavailablePrdcCdList,
        List<String> messageList,
        String message
    ) {
        AdditionPreCheckResVO res = new AdditionPreCheckResVO();
        res.setPrdcCd(prdcCdList.get(0));
        res.setPrdcCdList(prdcCdList);
        res.setPreCheckFailedPrdcCdList(preCheckFailedPrdcCdList);
        res.setOnlineCancelUnavailablePrdcCdList(onlineCancelUnavailablePrdcCdList);
        List<String> responseMessageList = responseMessage == ResSvcChgMessage.ADDITION_ONLINE_CANCEL_UNAVAILABLE
            ? prdcCdList.stream().map(prdcCd -> ResSvcChgMessage.ADDITION_STORE_ONLINE_CANCEL_UNAVAILABLE.getMessage()).collect(Collectors.toList())
            : messageList;
        res.setResltMsgList(responseMessageList);
        res.setResultCode(responseMessage.getCode());
        res.setResltMsg(message);
        return FormResponse.of(responseMessage, message, res);
    }

    /**
     * Y24 응답 코드와 가입 가능 여부를 성공 기준으로 판정한다.
     */
    private boolean isMoscPrdcTrtmPreChkSuccess(AdditionPreCheckResVO res) {
        String rsltCd = StringUtil.NVL(res.getRsltCd(), "");
        String resultCode = StringUtil.NVL(res.getResultCode(), "");
        String sbscYn = StringUtil.NVL(res.getSbscYn(), "");

        if (!"".equals(rsltCd) && !"0000".equals(rsltCd)) {
            return false;
        }
        if (!"".equals(resultCode) && !"0000".equals(resultCode)) {
            return false;
        }
        if (!"".equals(sbscYn) && !"Y".equalsIgnoreCase(sbscYn)) {
            return false;
        }
        return true;
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void enrichFailedProductCodes(
        AdditionPreCheckResVO res,
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList
    ) {
        if (res == null || hasFailedProductCodes(res) || requestedProductList == null || requestedProductList.isEmpty()) {
            return;
        }

        List<String> failedProductCodes = findFailedProductCodes(res, requestedProductList);
        if (failedProductCodes.isEmpty() && requestedProductList.size() == 1) {
            String prdcCd = StringUtil.NVL(requestedProductList.get(0).getPrdcCd(), "");
            if (!"".equals(prdcCd)) {
                failedProductCodes = List.of(prdcCd);
            }
        }
        if (failedProductCodes.isEmpty()) {
            return;
        }

        res.setPrdcCd(failedProductCodes.get(0));
        res.setPrdcCdList(failedProductCodes);
        res.setPreCheckFailedPrdcCdList(failedProductCodes);
        if (res.getResltMsgList() == null || res.getResltMsgList().isEmpty()) {
            String message = getMoscPrdcTrtmPreChkMessage(res);
            res.setResltMsgList(failedProductCodes.stream()
                .map(prdcCd -> message)
                .collect(Collectors.toList()));
        }
    }

    private boolean hasFailedProductCodes(AdditionPreCheckResVO res) {
        if (!"".equals(StringUtil.NVL(res.getPrdcCd(), ""))) {
            return true;
        }
        if (res.getPrdcCdList() != null && !res.getPrdcCdList().isEmpty()) {
            return true;
        }
        return res.getPreCheckFailedPrdcCdList() != null && !res.getPreCheckFailedPrdcCdList().isEmpty();
    }

    private List<String> findFailedProductCodes(
        AdditionPreCheckResVO res,
        List<AdditionPreCheckReqDto.ProductInfo> requestedProductList
    ) {
        String message = getMoscPrdcTrtmPreChkMessage(res);
        List<String> failedProductCodes = new ArrayList<>();
        for (AdditionPreCheckReqDto.ProductInfo productInfo: requestedProductList) {
            if (productInfo == null) {
                continue;
            }
            String prdcCd = StringUtil.NVL(productInfo.getPrdcCd(), "");
            if ("".equals(prdcCd) || failedProductCodes.contains(prdcCd)) {
                continue;
            }
            if (message.contains("[" + prdcCd + "]") || message.contains(prdcCd)) {
                failedProductCodes.add(prdcCd);
            }
        }
        return failedProductCodes;
    }

    /**
     * Y24 실패 응답에서 화면에 표시할 메시지를 추출한다.
     */
    private String getMoscPrdcTrtmPreChkMessage(AdditionPreCheckResVO res) {
        if (res.getRuleList() != null) {
            for (AdditionPreCheckResVO.RuleInfo ruleInfo: res.getRuleList()) {
                String ruleMsgSbst = StringUtil.NVL(ruleInfo.getRuleMsgSbst(), "");
                if (!"".equals(ruleMsgSbst)) {
                    return ruleMsgSbst;
                }
            }
        }
        String resltMsg = StringUtil.NVL(res.getResltMsg(), "");
        if (!"".equals(resltMsg)) {
            return resltMsg;
        }
        String svcMsg = StringUtil.NVL(res.getSvcMsg(), "");
        if (!"".equals(svcMsg)) {
            return svcMsg;
        }
        return "부가서비스 가입이 불가합니다.";
    }

    private boolean isSelfCareCancelUnavailableMessage(String message) {
        String text = StringUtil.NVL(message, "");
        return text.contains("셀프케어")
            && text.contains("해지")
            && (text.contains("불가능") || text.contains("불가"));
    }

    /**
     * 부가서비스 해지 (Y25 selfcare 우선, 실패 시 X38 fallback)
     *
     * [처리 순서]
     * 1. MSP_RATE_MST@DL_MSP 조회
     *    - null → NO_EXSIST_RATE: 요금제 정보 없음 (해지 불가 처리)
     *    - onlineCanYn ≠ "Y" → NO_ONLINE_CAN_CHANGE_ADD: 온라인 해지 불가
     *      (해지 가능한 부가서비스만 온라인 처리, 나머지는 고객센터 안내)
     * 2. Y25 selfcare 해지 단건 호출
     * 3. Y25 실패 시 X38 해지 fallback
     *    - prodHstSeq 있음 → moscRegSvcCanChgSeq: 특정 이력 번호 기준 해지
     *      (로밍처럼 동일 SOC를 여러 번 가입한 경우, 특정 건만 해지)
     *    - prodHstSeq 없음 → moscRegSvcCanChg: 단순 SOC 기준 해지
     *
     * ASIS 참조: moscRegSvcCanChg() / moscRegSvcCanChgSeq() — MyPageSearchDto 세션 의존,
     *           Map<String,Object> 반환 → TOBE에서 AdditionApplyResVO로 교체
     */

    @BusinessContextBoundary
    @SuppressWarnings("PMD.UnusedLocalVariable")
    public FormResponse<AdditionApplyResVO> moscRegSvcCanChg(AdditionApplyReqDto req) {
        BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
        logger.debug("[moscRegSvcCanChg] start: ncn={}, ctn={}, custId={}, soc={}, prodHstSeq={}",
            req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getProdHstSeq());
        try {
            //20260602 온라인해지가능여부 제외
            // [1] MSP_RATE_MST@DL_MSP — 온라인 해지 가능 여부 사전 검증
            // MspRateMstDto mspRateMstDto = mspApiDirectRepository.query("/msp/mspRateMst", req.getSoc(), MspRateMstDto.class);
            // if (mspRateMstDto == null) {
            //     logger.warn("[moscRegSvcCanChg] MSP_RATE_MST not found, skip local cancel validation: soc={}", req.getSoc());
            // } else {
            //     String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            //     logger.debug("[moscRegSvcCanChg] MSP_RATE_MST: soc={}, onlineCanYn={}, canCmnt={}",
            //         req.getSoc(), onlineCanYn, mspRateMstDto.getCanCmnt());
            //
            //     //20260515 확인 온라인 해지가능여부 체크여부(일단SKIP) EX) NOSPAM4:불법 TM 수신차단 등
            //     if (!"Y".equals(onlineCanYn)) {
            //         // 온라인 해지 불가 SOC — 고객센터 통해 해지 안내
            //         logger.warn("[moscRegSvcCanChg] 확인필요 온라인 해지가능여부 체크여부: soc={}, onlineCanYn={}", req.getSoc(), onlineCanYn);
            //         //TEST_SKIP return FormResponse.of(ResSvcChgMessage.ADDITION_ONLINE_CANCEL_UNAVAILABLE);
            //     }
            // }

            // [2] M플랫폼 부가서비스 해지: Y25 selfcare 우선, 실패 시 기존 X38로 fallback
            boolean hasProdHstSeq = req.getProdHstSeq() != null && !req.getProdHstSeq().isEmpty();
            logger.debug("[moscRegSvcCanChg] Y25 selfcare cancel call: ncn={}, soc={}", req.getNcn(), req.getSoc());
            FormResponse<AdditionApplyResVO> y25Res;
            try {
                y25Res = callY25Cancel(req);
            } catch (Exception y25Exception) {
                logger.warn("[moscRegSvcCanChg] Y25 selfcare cancel exception, fallback X38: ncn={}, soc={}, msg={}",
                    req.getNcn(), req.getSoc(), y25Exception.getMessage());
                y25Res = FormResponse.of(
                    ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    y25Exception.getMessage(),
                    AdditionApplyResVO.of(req.getSoc()));
            }
            if (y25Res == null) {
                logger.debug("[moscRegSvcCanChg] Y25 selfcare cancel success: ncn={}, soc={}", req.getNcn(), req.getSoc());
                return FormResponse.of(ResSvcChgMessage.SUCCESS, AdditionApplyResVO.of(req.getSoc()));
            }
            logger.warn("[moscRegSvcCanChg] Y25 selfcare cancel failed, skip fallback X38: ncn={}, soc={}, resCode={}, message={}",
                req.getNcn(), req.getSoc(), y25Res.resCode(), y25Res.resMessage());

            /*
            // [보류] Y25 실패 시 X38 fallback 호출 중지.
            // Y25 오류 시 X38도 동일하게 ESB 오류가 발생하여 실제 CALL만 막고 기존 로직은 보존한다.
            if (Boolean.TRUE.equals(req.getSelfCareUnavailable())) {
                String appAgncCd = resolveAdditionAppAgncCd(req, "X38");
                logger.debug("[moscRegSvcCanChg] X38 XML self-service request: hasProdHstSeq={}, ncn={}, soc={}, appAgncCd={}, selfCareUnavailable={}, mdlIndIncluded={}",
                    hasProdHstSeq, req.getNcn(), req.getSoc(), appAgncCd, req.getSelfCareUnavailable(), true);
                MspPrxSoapResponse response = callX38XmlSelfService(req, appAgncCd);
                if (isEsbLinkError(response)) {
                    logger.warn("[moscRegSvcCanChg] X38 XML retry by ESB link error: ncn={}, soc={}, appAgncCd={}, responseCode={}",
                        req.getNcn(), req.getSoc(), appAgncCd, response.responseCode());
                    sleepBeforeAdditionRetry();
                    response = callX38XmlSelfService(req, appAgncCd);
                    logger.debug("[moscRegSvcCanChg] X38 XML retry response: ncn={}, soc={}, appAgncCd={}, responseType={}, responseCode={}, responseBasic={}",
                        req.getNcn(), req.getSoc(), appAgncCd,
                        response == null ? "" : response.responseType(),
                        response == null ? "" : response.responseCode(),
                        response == null ? "" : response.responseBasic());
                }
                if (response == null) {
                    logger.warn("[moscRegSvcCanChg] X38 XML response is null: ncn={}, soc={}, appAgncCd={}",
                        req.getNcn(), req.getSoc(), appAgncCd);
                    return FormResponse.of(
                        ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                        "부가서비스 해지 결과를 확인할 수 없습니다.",
                        AdditionApplyResVO.of(req.getSoc()));
                }
                if (!response.success()) {
                    String message = buildMplatformErrorMessage(response);
                    logger.warn("[moscRegSvcCanChg] X38 XML failed: ncn={}, soc={}, appAgncCd={}, responseType={}, responseCode={}, message={}",
                        req.getNcn(), req.getSoc(), appAgncCd, response.responseType(), response.responseCode(), message);
                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, AdditionApplyResVO.of(req.getSoc()));
                }
                logger.debug("[moscRegSvcCanChg] X38 XML success: ncn={}, soc={}, appAgncCd={}, responseCode={}",
                    req.getNcn(), req.getSoc(), appAgncCd, response.responseCode());
            } else {
                logger.debug("[moscRegSvcCanChg] X38 legacy call: hasProdHstSeq={}, ncn={}, soc={}",
                    hasProdHstSeq, req.getNcn(), req.getSoc());
                FormResponse<AdditionApplyResVO> legacyRes = callX38LegacyCancel(req);
                if (legacyRes != null) {
                    return legacyRes;
                }
                logger.debug("[moscRegSvcCanChg] X38 legacy success: ncn={}, soc={}",
                    req.getNcn(), req.getSoc());
            }
            */
            return y25Res;

        } catch (SelfServiceException e) {
            /*
            // [보류] Y25 실패 시 X38 fallback 호출 중지.
            // 예외 경로에서도 X38 재호출을 막고, 기존 로직은 보존한다.
            if (isEsbLinkError(e)) {
                logger.warn("[moscRegSvcCanChg] X38 retry by ESB link exception: ncn={}, soc={}, resultCode={}",
                    req.getNcn(), req.getSoc(), e.getResultCode());
                sleepBeforeAdditionRetry();
                try {
                    String retryAppAgncCd = resolveAdditionAppAgncCd(req, "X38");
                    MspPrxSoapResponse retryResponse = callX38XmlSelfService(req, retryAppAgncCd);
                    logger.debug("[moscRegSvcCanChg] X38 XML retry exception-path response: ncn={}, soc={}, appAgncCd={}, responseType={}, responseCode={}",
                        req.getNcn(), req.getSoc(), retryAppAgncCd,
                        retryResponse == null ? "" : retryResponse.responseType(),
                        retryResponse == null ? "" : retryResponse.responseCode());
                    if (retryResponse == null) {
                        return FormResponse.of(
                            ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                            "부가서비스 해지 결과를 확인할 수 없습니다.",
                            AdditionApplyResVO.of(req.getSoc()));
                    }
                    if (retryResponse.success()) {
                        return FormResponse.of(ResSvcChgMessage.SUCCESS, AdditionApplyResVO.of(req.getSoc()));
                    }
                    return FormResponse.of(
                        ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                        buildMplatformErrorMessage(retryResponse),
                        AdditionApplyResVO.of(req.getSoc()));
                } catch (SocketTimeoutException retryException) {
                    logger.warn("[moscRegSvcCanChg] X38 retry socket timeout: ncn={}, soc={}", req.getNcn(), req.getSoc());
                    throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
                } catch (SelfServiceException retryException) {
                    logger.warn("[moscRegSvcCanChg] X38 retry SelfServiceException: ncn={}, soc={}, resultCode={}, msg={}",
                        req.getNcn(), req.getSoc(), retryException.getResultCode(), retryException.getMessage());
                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, retryException.getMessage(), null);
                } catch (Exception retryException) {
                    logger.warn("[moscRegSvcCanChg] X38 retry exception: ncn={}, soc={}, msg={}",
                        req.getNcn(), req.getSoc(), retryException.getMessage());
                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, retryException.getMessage(), null);
                }
            }
            */
            logger.warn("[moscRegSvcCanChg] SelfServiceException: ncn={}, soc={}, msg={}", req.getNcn(), req.getSoc(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            logger.warn("[moscRegSvcCanChg] exception: ncn={}, soc={}, msg={}", req.getNcn(), req.getSoc(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        }

        // [보류] Y25 실패 시 X38 fallback 호출을 막으면서 성공/실패 모두 try 블록 내부에서 반환한다.
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private FormResponse<AdditionApplyResVO> callX38LegacyCancel(AdditionApplyReqDto req) throws SocketTimeoutException {
        MpMoscRegSvcCanChgInVO vo;
        try {
            vo = callX38LegacyCancelOnce(req);
        } catch (SelfServiceException e) {
            if (!isEsbLinkError(e)) {
                logger.warn("[moscRegSvcCanChg] X38 legacy SelfServiceException: ncn={}, soc={}, resultCode={}, msg={}",
                    req.getNcn(), req.getSoc(), e.getResultCode(), e.getMessage());
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), AdditionApplyResVO.of(req.getSoc()));
            }
            logger.warn("[moscRegSvcCanChg] X38 legacy retry by ESB link exception: ncn={}, soc={}, resultCode={}",
                req.getNcn(), req.getSoc(), e.getResultCode());
            sleepBeforeAdditionRetry();
            try {
                vo = callX38LegacyCancelOnce(req);
            } catch (SelfServiceException retryException) {
                logger.warn("[moscRegSvcCanChg] X38 legacy retry SelfServiceException: ncn={}, soc={}, resultCode={}, msg={}",
                    req.getNcn(), req.getSoc(), retryException.getResultCode(), retryException.getMessage());
                return FormResponse.of(
                    ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    retryException.getMessage(),
                    AdditionApplyResVO.of(req.getSoc()));
            }
        }

        if (vo == null) {
            logger.warn("[moscRegSvcCanChg] X38 legacy response is null: ncn={}, soc={}", req.getNcn(), req.getSoc());
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "부가서비스 해지 결과를 확인할 수 없습니다.",
                AdditionApplyResVO.of(req.getSoc()));
        }
        if (!vo.isSuccess()) {
            String message = StringUtil.NVL(vo.getSvcMsg(), "부가서비스 해지가 불가합니다.");
            logger.warn("[moscRegSvcCanChg] X38 legacy failed: ncn={}, soc={}, resultCode={}, message={}",
                req.getNcn(), req.getSoc(), vo.getResultCode(), message);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, AdditionApplyResVO.of(req.getSoc()));
        }
        return null;
    }

    private MpMoscRegSvcCanChgInVO callX38LegacyCancelOnce(AdditionApplyReqDto req) throws SocketTimeoutException {
        boolean hasProdHstSeq = req.getProdHstSeq() != null && !req.getProdHstSeq().isEmpty();
        if (hasProdHstSeq) {
            return mPlatFormService.moscRegSvcCanChgSeq(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getProdHstSeq());
        }
        return mPlatFormService.moscRegSvcCanChg(
            req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private MspPrxSoapResponse callX38XmlSelfService(AdditionApplyReqDto req, String appAgncCd)
        throws SocketTimeoutException {
        MplatFormXmlSelfcareRequest selfcareRequest = MplatFormXmlSelfcareRequest.builder()
            .ncn(StringUtil.NVL(req.getNcn(), ""))
            .ctn(StringUtil.NVL(req.getCtn(), ""))
            .custId(StringUtil.NVL(req.getCustId(), ""))
            .appAgncCd(appAgncCd)
            .build();

        MplatFormX38Request request = MplatFormX38Request.builder()
            .soc(StringUtil.NVL(req.getSoc(), ""))
            .prodHstSeq(StringUtil.NVL(req.getProdHstSeq(), ""))
            .mdlInd("MSP")
            .build();

        return msfMcpOsstPrxService.callXmlSelfService(
            List.of(request),
            MplatformServiceType.X38,
            selfcareRequest);
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private String buildMplatformErrorMessage(MspPrxSoapResponse response) {
        String responseCode = response == null ? "" : StringUtil.NVL(response.responseCode(), "");
        String responseBasic = response == null ? "" : StringUtil.NVL(response.responseBasic(), "");
        return "".equals(responseBasic) ? responseCode : responseBasic;
    }

    private FormResponse<AdditionApplyResVO> callY25Apply(AdditionApplyReqDto req) {
        return callY25Treatment(req, "A", "regSvcChg", "부가서비스 신청 결과를 확인할 수 없습니다.");
    }

    private FormResponse<AdditionApplyResVO> callY25Cancel(AdditionApplyReqDto req) {
        return callY25Treatment(req, "C", "moscRegSvcCanChg", "부가서비스 해지 결과를 확인할 수 없습니다.");
    }

    private FormResponse<AdditionApplyResVO> callY25Treatment(
        AdditionApplyReqDto req,
        String prdcSbscTrtmCd,
        String logPrefix,
        String nullResponseMessage
    ) {
        logger.debug("[{}] Y25 selfcare request: ncn={}, soc={}, prdcSbscTrtmCd={}",
            logPrefix, req.getNcn(), req.getSoc(), prdcSbscTrtmCd);
        MspPrxSoapResponse response = mspPrxClient.callServiceJson(buildY25TreatmentRequest(req, prdcSbscTrtmCd));
        AdditionPreCheckResVO res = toAdditionPreCheckRes(response);
        if (isEsbLinkError(response, res)) {
            logger.warn("[{}] Y25 selfcare retry by ESB link error: ncn={}, soc={}, prdcSbscTrtmCd={}, responseCode={}, rsltCd={}, resultCode={}",
                logPrefix, req.getNcn(), req.getSoc(), prdcSbscTrtmCd, response == null ? "" : response.responseCode(),
                res == null ? "" : res.getRsltCd(), res == null ? "" : res.getResultCode());
            sleepBeforeAdditionRetry();
            response = mspPrxClient.callServiceJson(buildY25TreatmentRequest(req, prdcSbscTrtmCd));
            res = toAdditionPreCheckRes(response);
            logger.debug(
                "[{}] Y25 selfcare retry response: ncn={}, soc={}, prdcSbscTrtmCd={}, responseType={}, responseCode={}, rsltCd={}, resultCode={}",
                logPrefix,
                req.getNcn(),
                req.getSoc(),
                prdcSbscTrtmCd,
                response == null ? "" : response.responseType(),
                response == null ? "" : response.responseCode(),
                res == null ? "" : res.getRsltCd(),
                res == null ? "" : res.getResultCode());
        }
        if (response == null || res == null) {
            logger.warn("[{}] Y25 selfcare response is null: ncn={}, soc={}, prdcSbscTrtmCd={}",
                logPrefix, req.getNcn(), req.getSoc(), prdcSbscTrtmCd);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                nullResponseMessage,
                AdditionApplyResVO.of(req.getSoc()));
        }
        if (!response.success()) {
            String message = getMoscPrdcTrtmPreChkMessage(res);
            logger.warn(
                "[{}] Y25 selfcare responseType failed: ncn={}, soc={}, prdcSbscTrtmCd={}, responseType={}, responseCode={}, globalNo={}, message={}",
                logPrefix,
                req.getNcn(),
                req.getSoc(),
                prdcSbscTrtmCd,
                response.responseType(),
                response.responseCode(),
                response.globalNo(),
                message);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, AdditionApplyResVO.of(req.getSoc()));
        }
        if (!isMoscPrdcTrtmPreChkSuccess(res)) {
            String message = getMoscPrdcTrtmPreChkMessage(res);
            logger.warn("[{}] Y25 selfcare failed: ncn={}, soc={}, prdcSbscTrtmCd={}, rsltCd={}, resultCode={}, sbscYn={}, message={}",
                logPrefix, req.getNcn(), req.getSoc(), prdcSbscTrtmCd, res.getRsltCd(), res.getResultCode(), res.getSbscYn(), message);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, AdditionApplyResVO.of(req.getSoc()));
        }
        logger.debug("[{}] Y25 selfcare success: ncn={}, soc={}, prdcSbscTrtmCd={}, rsltCd={}, resultCode={}",
            logPrefix, req.getNcn(), req.getSoc(), prdcSbscTrtmCd, res.getRsltCd(), res.getResultCode());
        return null;
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private String resolveAdditionAppAgncCd(AdditionApplyReqDto req, String eventCd) {
        String appAgncCd = req == null ? "" : StringUtil.NVL(req.getAgentCd(), "").trim();
        if ("".equals(appAgncCd)) {
            try {
                appAgncCd = StringUtil.NVL(AuthenticationUtils.getAgentCode(), "").trim();
            } catch (Exception e) {
                logger.debug("[additionService] failed to resolve auth agentCd for {} XML: msg={}", eventCd, e.getMessage());
            }
        }
        if ("".equals(appAgncCd)) {
            logger.warn("[additionService] {} XML appAgncCd is empty: ncn={}, soc={}",
                eventCd, req == null ? "" : req.getNcn(), req == null ? "" : req.getSoc());
        }
        return appAgncCd;
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private FormResponse<AdditionApplyResVO> callX21Apply(AdditionApplyReqDto req) throws SocketTimeoutException {
        MpRegSvcChgVO vo;
        try {
            vo = mPlatFormService.regSvcChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFtrNewParam());
        } catch (SelfServiceException e) {
            if (!isEsbLinkError(e)) {
                throw e;
            }
            logger.warn("[regSvcChg] X21 retry by ESB link exception: ncn={}, soc={}, resultCode={}",
                req.getNcn(), req.getSoc(), e.getResultCode());
            sleepBeforeAdditionRetry();
            vo = mPlatFormService.regSvcChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFtrNewParam());
        }

        if (isEsbLinkError(vo)) {
            logger.warn("[regSvcChg] X21 retry by ESB link error: ncn={}, soc={}, resultCode={}",
                req.getNcn(), req.getSoc(), vo.getResultCode());
            sleepBeforeAdditionRetry();
            vo = mPlatFormService.regSvcChg(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFtrNewParam());
            if (vo == null) {
                logger.warn("[regSvcChg] X21 retry response is null: ncn={}, soc={}", req.getNcn(), req.getSoc());
                return FormResponse.of(
                    ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    "부가서비스 신청 결과를 확인할 수 없습니다.",
                    AdditionApplyResVO.of(req.getSoc()));
            }
            logger.debug("[regSvcChg] X21 retry response: success={}, soc={}, resultCode={}",
                vo.isSuccess(), req.getSoc(), vo.getResultCode());
        }

        if (vo == null) {
            logger.warn("[regSvcChg] X21 response is null: ncn={}, soc={}", req.getNcn(), req.getSoc());
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "부가서비스 신청 결과를 확인할 수 없습니다.",
                AdditionApplyResVO.of(req.getSoc()));
        }
        if (!vo.isSuccess()) {
            String message = StringUtil.NVL(vo.getSvcMsg(), "부가서비스 가입이 불가합니다.");
            logger.warn("[regSvcChg] X21 failed: ncn={}, soc={}, resultCode={}, message={}",
                req.getNcn(), req.getSoc(), vo.getResultCode(), message);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, AdditionApplyResVO.of(req.getSoc()));
        }
        return null;
    }

    private boolean isEsbLinkError(MpRegSvcChgVO vo) {
        return vo != null && ESB_LINK_ERROR_CODE.equals(StringUtil.NVL(vo.getResultCode(), ""));
    }

    private boolean isEsbLinkError(SelfServiceException e) {
        return e != null && ESB_LINK_ERROR_CODE.equals(StringUtil.NVL(e.getResultCode(), ""));
    }

    private boolean isEsbLinkError(MspPrxSoapResponse response, AdditionPreCheckResVO res) {
        return (response != null && ESB_LINK_ERROR_CODE.equals(StringUtil.NVL(response.responseCode(), "")))
            || (res != null && ESB_LINK_ERROR_CODE.equals(StringUtil.NVL(res.getResultCode(), "")))
            || (res != null && ESB_LINK_ERROR_CODE.equals(StringUtil.NVL(res.getRsltCd(), "")));
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private boolean isEsbLinkError(MspPrxSoapResponse response) {
        return response != null && ESB_LINK_ERROR_CODE.equals(StringUtil.NVL(response.responseCode(), ""));
    }

    private void sleepBeforeAdditionRetry() {
        try {
            Thread.sleep(ADDITION_RETRY_DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new McpCommonException(COMMON_EXCEPTION);
        }
    }

    private MspPrxJsonRequest buildY25TreatmentRequest(AdditionApplyReqDto req, String prdcSbscTrtmCd) {
        String treatmentName = "C".equals(prdcSbscTrtmCd) ? "해지" : "신청";
        MspPrxJsonRequest.MspPrxJsonRequestBuilder builder = MspPrxJsonRequest.builder()
            .property("appEventCd", "Y25")
            .property("ncn", StringUtil.NVL(req.getNcn(), ""))
            .property("ctn", StringUtil.NVL(req.getCtn(), ""))
            .property("custId", StringUtil.NVL(req.getCustId(), ""))
            .property("actCode", "SRG")
            .serviceAlterTrace(ServiceAlterTraceRequest.builder()
                .ncn(StringUtil.NVL(req.getNcn(), ""))
                .subscriberNo(StringUtil.NVL(req.getCtn(), ""))
                .eventCd("Y25")
                .tSocCode(StringUtil.NVL(req.getSoc(), ""))
                .trtmRsltSbst("부가서비스 " + treatmentName)
                .build());

        addY24LogProperties(builder);
        builder.property("prdcList", List.of(toY25Product(req, prdcSbscTrtmCd)));
        return builder.build();
    }

    private Map<String, Object> toY25Product(AdditionApplyReqDto req, String prdcSbscTrtmCd) {
        Map<String, Object> product = new LinkedHashMap<>();
        String soc = StringUtil.NVL(req.getSoc(), "");
        String ftrNewParam = !"A".equals(prdcSbscTrtmCd) || Set.of("RNGTOUPR3", "SKCOREPAC", "XRINGMON", "XRINGWEEK").contains(soc)
            ? ""
            : StringUtil.NVL(req.getFtrNewParam(), "");
        product.put("prdcCd", soc);
        product.put("prdcSbscTrtmCd", prdcSbscTrtmCd); // A=가입/C=해지/U=파람변경
        product.put("prdcTypeCd", "R");      // R=부가서비스(M플랫폼 인터페이스 코드: R부가서비스/P요금제) — DB PROC_TYPE_CD(R예약/C즉시처리)와 무관
        product.put("prdcSeqNo", StringUtil.NVL(req.getProdHstSeq(), ""));
        product.put("ftrNewParam", ftrNewParam);
        logger.debug("[additionService] Y25 selfcare product: prdcCd={}, prdcSbscTrtmCd={}, prdcTypeCd=R, prdcSeqNo={}, ftrNewParam={}",
            product.get("prdcCd"), product.get("prdcSbscTrtmCd"), product.get("prdcSeqNo"), product.get("ftrNewParam"));
        return product;
    }

    /**
     * 부가서비스 신청 (Y25 단건 우선, 실패 시 X21 fallback, 선해지 포함)
     *
     * [처리 순서]
     * 1. flag="Y"이면 선해지 (cancelAddSvc 내부 호출)
     *    → 실패 시 즉시 반환 (신청 진행 중단)
     *    → 로밍 등 동일 SOC를 해지 후 재가입하는 "변경" 시나리오
     * 2. M플랫폼 가입 처리
     *    - Y25 selfcare JSON 단건 호출
     *    - Y25 실패 시 ASIS X21 단건 호출
     *
     * [미이관 항목 — 주석 처리]
     * - 인증 STEP 검증: certService.getStepCnt / vdlCertInfo
     *   당겨쓰기(/pullData01.do) 진입 시에만 STEP 검증이 필요했던 ASIS 로직.
     *   MSF에서 인증 공통 모듈 미구현 상태 → 추후 구현 (31번 §1-3 참조)
     * - 포인트 처리: pointService.editPoint
     *   포인트할인(REG_SVC_CD_4) 신청 시 포인트 차감 처리.
     *   MSF 포인트 기능 미이관 → 추후 구현
     *
     * ASIS 참조: regSvcChg() — X21 사용, 인증 STEP 검증, 포인트 처리 포함
     */
    @BusinessContextBoundary
    public FormResponse<AdditionApplyResVO> regSvcChg(AdditionApplyReqDto req) {
        logger.debug("[regSvcChg] start: ncn={}, ctn={}, custId={}, soc={}, flag={}, selfCareUnavailable={}, ftrNewParam={}",
            req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFlag(), req.getSelfCareUnavailable(), req.getFtrNewParam());
        try {
            // [1] 선해지 (flag="Y": 동일 SOC 해지 후 재가입 — 로밍 변경 등)
            if ("Y".equals(req.getFlag())) {
                BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
                logger.debug("[regSvcChg] 선해지 진행: soc={}, selfCareUnavailable={}", req.getSoc(), req.getSelfCareUnavailable());
                FormResponse<AdditionApplyResVO> cancelRes = moscRegSvcCanChg(req);
                if (!ResSvcChgMessage.SUCCESS.getCode().equals(cancelRes.resCode())) {
                    // 선해지 실패 시 신청 중단
                    logger.warn("[regSvcChg] 선해지 실패로 신청 중단: ncn={}, soc={}, resCode={}", req.getNcn(), req.getSoc(), cancelRes.resCode());
                    return cancelRes;
                }
                logger.debug("[regSvcChg] 선해지 성공: soc={}", req.getSoc());
            }

            // [ASIS] 인증 STEP 검증 — 공통 미구현 (31번 §1-3)
            // 당겨쓰기 진입 시 최소 3스텝 인증 여부 및 계약번호·핸드폰번호 검증
            // if (certService.getStepCnt() < 3) { return STEP01 오류; }
            // Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            // if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) { return STEP02 오류; }

            // [2] M플랫폼 부가서비스 신청: Y25 단건 우선, 실패 시 기존 X21로 fallback
            BusinessContextHolder.setParentScanId(req != null ? req.getParentScanId() : null);
            logger.debug("[regSvcChg] Y25 call: ncn={}, soc={}", req.getNcn(), req.getSoc());
            FormResponse<AdditionApplyResVO> y25Res;
            try {
                y25Res = callY25Apply(req);
            } catch (Exception y25Exception) {
                logger.warn("[regSvcChg] Y25 exception, fallback X21: ncn={}, soc={}, msg={}",
                    req.getNcn(), req.getSoc(), y25Exception.getMessage());
                y25Res = FormResponse.of(
                    ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    y25Exception.getMessage(),
                    AdditionApplyResVO.of(req.getSoc()));
            }
            if (y25Res == null) {
                logger.debug("[regSvcChg] Y25 success: ncn={}, soc={}", req.getNcn(), req.getSoc());
            } else {
                logger.warn("[regSvcChg] Y25 failed, skip fallback X21: ncn={}, soc={}, resCode={}, message={}",
                    req.getNcn(), req.getSoc(), y25Res.resCode(), y25Res.resMessage());
                // [보류] Y25 실패 시 X21 fallback 호출 중지.
                // Y25 오류 시 X21도 동일하게 ESB 오류가 발생하여 실제 CALL만 막고 기존 로직은 보존한다.
                // logger.debug("[regSvcChg] X21 call: ncn={}, soc={}", req.getNcn(), req.getSoc());
                // FormResponse<AdditionApplyResVO> x21Res = callX21Apply(req);
                // if (x21Res != null) {
                //     return x21Res;
                // }
                // logger.debug("[regSvcChg] X21 success: ncn={}, soc={}", req.getNcn(), req.getSoc());
                return y25Res;
            }

            // [ASIS] 포인트 처리 — 포인트 기능 미이관
            // 포인트할인(REG_SVC_CD_4) 신청 시 포인트 사용 처리 (pointService.editPoint)
            // if (Constants.REG_SVC_CD_4.equals(req.getSoc())) {
            //     CustPointDto custPoint = myBenefitService.selectCustPoint(req.getNcn());
            //     if (custPoint != null) { pointService.editPoint(custPointTxnDto); }
            // }

        } catch (SelfServiceException e) {
            logger.warn("[regSvcChg] SelfServiceException: ncn={}, soc={}, msg={}", req.getNcn(), req.getSoc(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            logger.warn("[regSvcChg] exception: ncn={}, soc={}, msg={}", req.getNcn(), req.getSoc(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        }

        return FormResponse.of(ResSvcChgMessage.SUCCESS, AdditionApplyResVO.of(req.getSoc()));
    }

    /**
     * 로밍 서브상품 신청 시 대표상품 일련번호 조회.
     *
     * [처리 순서]
     * 1. 대표 전화번호(mtPhone)로 cntrListNoLogin → 대표 ncn/custId 취득, subStatus=="A" 확인
     * 2. X97 호출 → 대표회선 이용중 부가서비스 목록 조회
     * 3. 목록 순회하며 조건 만족하는 대표상품 검색:
     *    ① soc == mtCd (대표상품코드 일치)
     *    ② shareSubContidList에 신청자 계약번호(subNcn) 포함
     *    ③ 서브 신청기간 ⊆ 대표상품 기간
     *    ④ PL2079777(로밍 하루종일ON 투게더 대표)이면 추가 검증
     * 4. prodHstSeq 반환
     *
     * ASIS 참조: RateAdsvcGdncServiceImpl.getMtProdHstSeq()
     */
    public FormResponse<AdditionApplyResVO> getMtProdHstSeq(AdditionApplyReqDto req) {
        String mtPhone = StringUtil.NVL(req.getMtPhone(), "");
        String mtCd = StringUtil.NVL(req.getMtCd(), "");
        String strtDt = StringUtil.NVL(req.getStrtDt(), "");
        String endDt = StringUtil.NVL(req.getEndDt(), "");
        String subNcn = StringUtil.NVL(req.getNcn(), "");

        logger.debug("[getMtProdHstSeq] start: mtPhone={}, mtCd={}, strtDt={}, endDt={}, subNcn={}", mtPhone, mtCd, strtDt, endDt, subNcn);

        if (mtPhone.isEmpty()) {
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "입력한 번호로 정보를 조회할 수 없습니다. 대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.", null);
        }

        try {
            // [1] cntrListNoLogin — 대표 전화번호로 계약정보 조회
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setCntrMobileNo(mtPhone);
            McpUserCntrMngDto userDto = svcChgPageRepository.selectCntrListNoLogin(userCntrMngDto);

            if (userDto == null || !"A".equals(userDto.getSubStatus())) {
                logger.warn("[getMtProdHstSeq] 대표회선 활성 확인 실패: mtPhone={}, subStatus={}", mtPhone, userDto == null ? "null" : userDto.getSubStatus());
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    "입력한 번호로 정보를 조회할 수 없습니다. 대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.", null);
            }

            String mtNcn = StringUtil.NVL(userDto.getSvcCntrNo(), "");
            String mtCustId = StringUtil.NVL(userDto.getCustId(), "");
            if (mtNcn.isEmpty() || mtCustId.isEmpty()) {
                logger.warn("[getMtProdHstSeq] 대표회선 계약번호/고객번호 없음: mtPhone={}", mtPhone);
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    "입력한 번호로 정보를 조회할 수 없습니다. 대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.", null);
            }

            // [2] X97 — 대표회선 이용중 부가서비스 조회
            MpAddSvcInfoParamDto x97Res = mPlatFormService.getAddSvcInfoParamDto(mtNcn, mtPhone, mtCustId);
            if (!x97Res.isSuccess() || x97Res.getList() == null) {
                logger.warn("[getMtProdHstSeq] X97 조회 실패: mtNcn={}, resultCode={}", mtNcn, x97Res.getResultCode());
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    "입력한 번호로 정보를 조회할 수 없습니다. 대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.", null);
            }

            // [3] 대표상품 검색
            DateTimeFormatter dateFmt = DateTimeFormatter.ofPattern("yyyyMMdd");
            DateTimeFormatter timeFmt = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
            LocalDate subStrtDate = LocalDate.parse(strtDt, dateFmt);
            LocalDate subEndDate = LocalDate.parse(endDt, dateFmt);

            String mtProdHstSeq = "";
            for (MpSocVO mpSoc: x97Res.getList()) {
                if (!mtProdHstSeq.isEmpty()) {
                    break;
                }

                // ① 대표상품코드 일치
                if (!mtCd.equals(mpSoc.getSoc())) {
                    continue;
                }

                // ② 대표상품의 shareSubContidList에 신청자 계약번호 포함 확인
                List<String> shareSubContidList = mpSoc.getShareSubContidList();
                if (shareSubContidList == null || shareSubContidList.isEmpty()) {
                    continue;
                }
                if (shareSubContidList.stream().noneMatch(subNcn::equals)) {
                    continue;
                }

                // ③ 서브 신청기간 ⊆ 대표상품 기간
                String mpStrtDt = StringUtil.NVL(mpSoc.getStrtDt(), "");
                String mpEndDttm = StringUtil.NVL(mpSoc.getEndDttm(), "");
                if (!mpStrtDt.isEmpty() && mpEndDttm.isEmpty()) {
                    mpEndDttm = calculateAdditionEndDttm(mtCd, mpStrtDt, timeFmt);
                }
                if (mpStrtDt.isEmpty() || mpEndDttm.isEmpty()) {
                    logger.warn("[getMtProdHstSeq] 대표상품 기간정보 없음, skip: soc={}, prodHstSeq={}", mpSoc.getSoc(), mpSoc.getProdHstSeq());
                    continue;
                }
                LocalDate mtStrtDate = LocalDate.parse(mpStrtDt.substring(0, 8), dateFmt);
                LocalDate mtEndDate = LocalDate.parse(mpEndDttm.substring(0, 8), dateFmt);
                if (subStrtDate.isBefore(mtStrtDate) || subEndDate.isAfter(mtEndDate)) {
                    continue;
                }

                // ④ PL2079777(로밍 하루종일ON 투게더 대표) 추가 검증
                if ("PL2079777".equals(mtCd)) {
                    String mpEndDt = StringUtil.NVL(mpSoc.getEndDt(), "");
                    if (!mpEndDt.isEmpty()) {
                        // 시작일은 대표상품 종료일보다 이전이어야 함
                        if (!subStrtDate.isBefore(mtEndDate)) {
                            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                                "시작일자는 대표상품의 종료일보다 이전이어야 합니다.", null);
                        }
                        // 현재 시간이 대표상품 시작시간보다 이후이면 불가
                        if (subStrtDate.isEqual(mtStrtDate) && mpStrtDt.length() >= 14) {
                            LocalDateTime nowDt = LocalDateTime.now();
                            LocalDateTime mtStart = LocalDateTime.parse(mpStrtDt, timeFmt);
                            if (nowDt.isAfter(mtStart)) {
                                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                                    "현재시간이 대표상품의 시작시간보다 작아야 합니다.", null);
                            }
                        }
                        // 종료일 추가 검증 (235959=당일 동일 가능, 그 외=시작일 이후여야 함)
                        if (mpEndDt.length() == 14) {
                            LocalDate mtEndDateReal = LocalDate.parse(mpEndDt.substring(0, 8), dateFmt);
                            if ("235959".equals(mpEndDt.substring(8))) {
                                if (subEndDate.isAfter(mtEndDateReal)) {
                                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                                        "종료일자는 대표상품의 종료일보다 작거나 같아야 합니다.", null);
                                }
                            } else {
                                if (!subEndDate.isAfter(subStrtDate)) {
                                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                                        "종료일자는 시작일자 이후여야 합니다.", null);
                                }
                            }
                        }
                    }
                }

                mtProdHstSeq = StringUtil.NVL(mpSoc.getProdHstSeq(), "");
            }

            if (mtProdHstSeq.isEmpty()) {
                String availableSocs = x97Res.getList().stream()
                    .map(mpSoc -> StringUtil.NVL(mpSoc.getSoc(), ""))
                    .collect(Collectors.joining(","));
                logger.warn("[getMtProdHstSeq] 대표상품 일련번호 조회 실패: mtNcn={}, mtCd={}, subNcn={}", mtNcn, mtCd, subNcn);
                logger.warn("[getMtProdHstSeq] representative product not found: mtNcn={}, mtPhone={}, mtCd={}, subNcn={}, availableSocs={}",
                    mtNcn, mtPhone, mtCd, subNcn, availableSocs);
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    "입력한 번호로 정보를 조회할 수 없습니다. 대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.", null);
            }

            logger.debug("[getMtProdHstSeq] success: mtNcn={}, mtCd={}, mtProdHstSeq={}", mtNcn, mtCd, mtProdHstSeq);
            AdditionApplyResVO resVO = new AdditionApplyResVO();
            resVO.setMtProdHstSeq(mtProdHstSeq);
            resVO.setMtNcn(mtNcn);
            return FormResponse.of(ResSvcChgMessage.SUCCESS, resVO);

        } catch (SocketTimeoutException e) {
            logger.warn("[getMtProdHstSeq] socket timeout: mtPhone={}", mtPhone);
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            logger.warn("[getMtProdHstSeq] SelfServiceException: mtPhone={}, msg={}", mtPhone, e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            logger.warn("[getMtProdHstSeq] exception: mtPhone={}, msg={}", mtPhone, e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        }
    }

    private String calculateAdditionEndDttm(String rateCd, String strtDttm, DateTimeFormatter timeFmt) {
        try {
            List<String> usePrdList = productInfoReadMapper.selectMcpAdditionUsePrd(rateCd);
            if (usePrdList == null || usePrdList.isEmpty()) {
                logger.warn("[getMtProdHstSeq] 상품 이용기간 조회 결과 없음: rateCd={}", rateCd);
                return "";
            }

            int usePrd = Integer.parseInt(usePrdList.get(0));
            String endDttm = LocalDateTime.parse(strtDttm, timeFmt)
                .plusDays(usePrd)
                .format(timeFmt);
            logger.debug("[getMtProdHstSeq] 대표상품 종료일 계산: rateCd={}, strtDttm={}, usePrd={}, endDttm={}",
                rateCd, strtDttm, usePrd, endDttm);
            return endDttm;
        } catch (Exception e) {
            logger.warn("[getMtProdHstSeq] 대표상품 종료일 계산 실패: rateCd={}, strtDttm={}, message={}",
                rateCd, strtDttm, e.getMessage());
            return "";
        }
    }


    /**
     * 로밍 서브상품 신청 시 대표상품 일련번호 조회.
     *
     * [처리 순서]
     * 1. 대표 전화번호(mtPhone)로 cntrListNoLogin → 대표 ncn/custId 취득, subStatus=="A" 확인
     * 2. X97 호출 → 대표회선 이용중 부가서비스 목록 조회
     * 3. 목록 순회하며 조건 만족하는 대표상품 검색:
     *    ① soc == mtCd (대표상품코드 일치)
     *    ② shareSubContidList에 신청자 계약번호(subNcn) 포함
     *    ③ 서브 신청기간 ⊆ 대표상품 기간
     *    ④ PL2079777(로밍 하루종일ON 투게더 대표)이면 추가 검증
     * 4. prodHstSeq 반환
     *
     * ASIS 참조: RateAdsvcGdncServiceImpl.getMtProdHstSeq()
     */
    public FormResponse<AdditionApplyResVO> checkMobileJoin(AdditionApplyReqDto req) {
        String joinPhone = StringUtil.NVL(req.getJoinPhone(), "");
        String subNcn = StringUtil.NVL(req.getNcn(), "");

        logger.debug("[checkMobileJoin] start: joinPhone={}, subNcn={}", joinPhone, subNcn);

        if (joinPhone.isEmpty()) {
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "입력한 번호로 정보를 조회할 수 없습니다. 핸드폰 정보 확인 후 다시 시도하시기 바랍니다.", null);
        }

        try {
            // ":" 기준으로 분리
            for (String phone: joinPhone.split(":")) {
                // [1] cntrListNoLogin — 전화번호로 계약정보 조회
                String trimmedPhone = phone.trim(); // 공백 제거
                if (!trimmedPhone.isEmpty()) {
                    McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
                    userCntrMngDto.setCntrMobileNo(trimmedPhone);
                    McpUserCntrMngDto userDto = svcChgPageRepository.selectCntrListNoLogin(userCntrMngDto);

                    if (userDto == null || !"A".equals(userDto.getSubStatus())) {
                        logger.warn("[checkMobileJoin] 활성 확인 실패: trimmedPhone={}, subStatus={}",
                            trimmedPhone,
                            userDto == null ? "null" : userDto.getSubStatus());
                        return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                            "입력한 번호로 정보를 조회할 수 없습니다. 가입 정보 확인 후 다시 시도하시기 바랍니다.[" + trimmedPhone + "]", null);
                    }
                }
            }

            logger.debug("[checkMobileJoin] start: joinPhone={}, subNcn={}", joinPhone, subNcn);
            AdditionApplyResVO resVO = new AdditionApplyResVO();
            resVO.setJoinPhone(joinPhone);
            return FormResponse.of(ResSvcChgMessage.SUCCESS, resVO);

        } catch (Exception e) {
            logger.warn("[checkMobileJoin] exception: joinPhone={}, msg={}", joinPhone, e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        }
    }
}
