package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpRegServiceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscRegSvcCanChgInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcChgPageRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionAvailableResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionMyListResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionPreCheckReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionPreCheckResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionReqDto;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;

@Service
public class MsfRegSvcServiceImpl {

    private static Logger logger = LoggerFactory.getLogger(MsfRegSvcServiceImpl.class);

    /** M플랫폼 연동 서비스 (X97/X38/Y25 등) */
    @Autowired
    private MsfMplatFormService mPlatFormService;

    @Autowired
    private MspApiDirectRepository mspApiDirectRepository;

    @Autowired
    private SvcChgPageRepositoryImpl svcChgPageRepository;

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
            if (!vo.isSuccess()) {
                logger.warn("[myAddSvcList] X97 failed (빈 목록으로 진행): ncn={}, resultCode={}, svcMsg={}",
                        req.getNcn(), vo.getResultCode(), vo.getSvcMsg());
            } else if (vo.getList() != null) {
                mSocVoList = vo.getList();
                int beforeCount = mSocVoList.size();
                mSocVoList.removeIf(item -> "PL249Q800".equals(item.getSoc()));
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

        // TODO: 설정 팝업 테스트용 목 데이터. 실제 X97 응답 연동 후 제거.
        Set<String> existingSocs = mSocVoList.stream().map(MpSocVO::getSoc).collect(Collectors.toSet());
        buildMockSettingServices().stream()
                .filter(m -> !existingSocs.contains(m.getSoc()))
                .forEach(mSocVoList::add);

        AdditionMyListResVO res = new AdditionMyListResVO();
        res.setList(mSocVoList);
        logger.debug("[myAddSvcList] end: ncn={}, ctn={}, custId={}, resultCount={}",
                req.getNcn(), req.getCtn(), req.getCustId(), res.getList() == null ? 0 : res.getList().size());
        return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
    }

    // TODO: 설정 팝업 테스트용 목 데이터. 실제 X97 응답 연동 후 제거.
    private List<MpSocVO> buildMockSettingServices() {
        List<MpSocVO> list = new ArrayList<>();
        list.add(mockSoc("NOSPAM4",   "불법TM수신차단 50개",       0,    "Y"));
        list.add(mockSoc("NOSPAM2",   "특정번호수신차단 100개",     0,    "Y"));
        list.add(mockSoc("NOSPAM3",   "정보제공사업자번호차단",     0,    "Y"));
        list.add(mockSoc("STLPVTPHN", "번호도용차단서비스",         0,    "Y"));
        list.add(mockSoc("DATAROM01", "로밍데이터(시작일)",         3300, "Y"));
        list.add(mockSoc("DYDTROM05", "로밍데이터(기간설정)",       3300, "Y"));
        list.add(mockSoc("PL2079771", "로밍 하루종일ON 플러스",     13000, "Y",
                "STRT_DT=20230729000000|END_DT=20230729235959|PRDC_SRL_NO=1|"));
        list.add(mockSoc("PL2079778", "로밍 하루종일ON 투게더(서브)", 5000, "Y",
                "STRT_DT=20230818000000|END_DT=20230818235959|SHARE_MAIN_CONTID=626506218|SHARE_MAIN_PROD_HST_SEQ=300001091066712|PRDC_SRL_NO=1|"));
        list.add(mockSoc("DATAROMSM", "로밍알림전화번호",           0,    "Y"));
        list.add(mockSoc("SENOINFR1", "무료통화수신번호",           0,    "Y"));
        //list.add(mockSoc("FCARVLSMS", "차량관제알림수신번호",       2200, "Y"));
        list.add(mockSoc("PL253A854", "군인요금제(복무기간)",       0,    "Y"));
        return list;
    }

    private MpSocVO mockSoc(String soc, String desc, int rateVat, String settingYn) {
        MpSocVO vo = new MpSocVO();
        vo.setSoc(soc);
        vo.setSocDescription(desc);
        vo.setSocRateVat(rateVat);
        vo.setSettingYn(settingYn);
        vo.setOnlineCanYn("Y");
        return vo;
    }

    private MpSocVO mockSoc(String soc, String desc, int rateVat, String settingYn, String paramSbst) {
        MpSocVO vo = mockSoc(soc, desc, rateVat, settingYn);
        vo.setParamSbst(paramSbst);
        vo.parseParamSbst();
        return vo;
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
            if (!vo.isSuccess()) {
                logger.warn("[selectAddSvcInfoDto] X97 failed: ncn={}, resultCode={}, svcMsg={}", req.getNcn(), vo.getResultCode(), vo.getSvcMsg());
                throw new McpCommonException(COMMON_EXCEPTION);
            }
            List<MpSocVO> mSocVoList = vo.getList();
            if (mSocVoList != null) {
                for (MpSocVO mSocVo : mSocVoList) {
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

        boolean wirelessBlockInUse = false;
        for (McpRegServiceDto item : list) {
            if (isWirelessBlockSoc(item)) {
                wirelessBlockInUse = true;
                break;
            }
        }
        logger.debug("[selectAddSvcInfoDto] wirelessBlockInUse from regService: {}", wirelessBlockInUse);

        List<McpRegServiceDto> listA = new ArrayList<>(); // 유료
        List<McpRegServiceDto> listC = new ArrayList<>(); // 무료/번들

        // [4] "PL249Q800" 더미 SOC 필터링 — 아무나SOLO 내부 SOC, 가입 화면에 노출 금지
        int beforeFilter = list.size();
        list.removeIf(item -> "PL249Q800".equals(item.getRateCd()));
        logger.debug("[selectAddSvcInfoDto] dummy SOC filter: before={}, after={}", beforeFilter, list.size());

        for (McpRegServiceDto item : list) {
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

    private boolean isWirelessBlockSoc(McpRegServiceDto service) {
        if (service == null) {
            return false;
        }
        if ("WIRELESSC".equals(StringUtil.NVL(service.getRateCd(), ""))) {
            return true;
        }
        String rateNm = StringUtil.NVL(service.getRateNm(), "");
        return rateNm.contains("무선") && rateNm.contains("차단");
    }

    private List<McpRegServiceDto> toMcpRegServiceList(Object value) {
        List<McpRegServiceDto> result = new ArrayList<>();
        if (value == null) {
            return result;
        }
        if (value instanceof List<?> items) {
            for (Object item : items) {
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
        HashMap<String, String> params = new HashMap<>();
        params.put("ncn", StringUtil.NVL(req.getNcn(), ""));
        params.put("ctn", StringUtil.NVL(req.getCtn(), ""));
        params.put("custId", StringUtil.NVL(req.getCustId(), ""));
        params.put("actCode", StringUtil.NVL(req.getActCode(), "SRG"));
        params.put("prmtId", StringUtil.NVL(req.getPrmtId(), ""));
        params.put("appEventCd", "Y24");
        params.put("eventCd", "Y24");

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
                String prefix = "prdcList[" + i + "].";
                params.put(prefix + "prdcCd", StringUtil.NVL(productInfo.getPrdcCd(), ""));
                params.put(prefix + "prdcSbscTrtmCd", StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), ""));
                params.put(prefix + "prdcTypeCd", StringUtil.NVL(productInfo.getPrdcTypeCd(), ""));
                params.put(prefix + "prdcSeqNo", StringUtil.NVL(productInfo.getPrdcSeqNo(), ""));
                params.put(prefix + "ftrNewParam", StringUtil.NVL(productInfo.getFtrNewParam(), ""));
            }
        }

        try {
            AdditionPreCheckResVO res = mPlatFormService.commonMplatform(params, "Y24", AdditionPreCheckResVO.class);
            if (res == null) {
                logger.warn("[moscPrdcTrtmPreChk] Y24 response is null: ncn={}, actCode={}", req.getNcn(), req.getActCode());
                return FormResponse.of(
                    ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                    "부가서비스 가입 가능 여부를 확인할 수 없습니다.",
                    null);
            }
            if (!isMoscPrdcTrtmPreChkSuccess(res)) {
                String message = getMoscPrdcTrtmPreChkMessage(res);
                logger.warn("[moscPrdcTrtmPreChk] Y24 failed: ncn={}, rsltCd={}, resultCode={}, sbscYn={}, message={}",
                        req.getNcn(), res.getRsltCd(), res.getResultCode(), res.getSbscYn(), message);
                if ("".equals(StringUtil.NVL(res.getPrdcCd(), "")) && prdcList != null && prdcList.size() == 1) {
                    res.setPrdcCd(StringUtil.NVL(prdcList.get(0).getPrdcCd(), ""));
                }
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, message, res);
            }
            logger.debug("[moscPrdcTrtmPreChk] Y24 success: ncn={}, rsltCd={}, resultCode={}, sbscYn={}",
                    req.getNcn(), res.getRsltCd(), res.getResultCode(), res.getSbscYn());
            return FormResponse.of(ResSvcChgMessage.SUCCESS, res);
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

    /**
     * 해지 대상 부가서비스의 온라인 해지 가능 여부를 MSP_RATE_MST 기준으로 확인한다.
     */
    private FormResponse<AdditionPreCheckResVO> validateCancelServicesByMspRateMst(
            List<AdditionPreCheckReqDto.ProductInfo> prdcList) {
        if (prdcList == null || prdcList.isEmpty()) {
            return null;
        }

        List<String> rateNotFoundSocList = new ArrayList<>();
        List<String> rateNotFoundMessageList = new ArrayList<>();
        List<String> onlineCancelUnavailableSocList = new ArrayList<>();
        List<String> onlineCancelUnavailableMessageList = new ArrayList<>();
        String onlineCancelUnavailableMessage = "";

        for (AdditionPreCheckReqDto.ProductInfo productInfo : prdcList) {
            if (productInfo == null || !"C".equals(StringUtil.NVL(productInfo.getPrdcSbscTrtmCd(), ""))) {
                continue;
            }

            String soc = StringUtil.NVL(productInfo.getPrdcCd(), "");
            MspRateMstDto mspRateMstDto = mspApiDirectRepository.query("/msp/mspRateMst", soc, MspRateMstDto.class);
            if (mspRateMstDto == null) {
                logger.warn("[moscPrdcTrtmPreChk] MSP_RATE_MST not found for cancel precheck: soc={}", soc);
                String message = ResSvcChgMessage.ADDITION_RATE_NOT_FOUND.getMessage();
                rateNotFoundSocList.add(soc);
                rateNotFoundMessageList.add(message);
                continue;
            }

            String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            logger.debug("[moscPrdcTrtmPreChk] cancel MSP_RATE_MST: soc={}, onlineCanYn={}, canCmnt={}",
                    soc, onlineCanYn, mspRateMstDto.getCanCmnt());

            //20260515 확인 온라인 해지가능여부 체크여부(일단SKIP) EX) NOSPAM4:불법 TM 수신차단 등
            if (!"Y".equals(onlineCanYn)) {
                String canCmnt = StringUtil.NVL(mspRateMstDto.getCanCmnt(), "");
                String message = !"".equals(canCmnt)
                        ? canCmnt
                        : ResSvcChgMessage.ADDITION_ONLINE_CANCEL_UNAVAILABLE.getMessage();
                logger.warn("[moscPrdcTrtmPreChk] 확인 온라인 해지가능여부 체크여부: soc={}, onlineCanYn={}", soc, onlineCanYn);
                //TEST_SKIP onlineCancelUnavailableSocList.add(soc);
                //TEST_SKIP onlineCancelUnavailableMessageList.add(message);
                //TEST_SKIP if ("".equals(onlineCancelUnavailableMessage)) {
                //TEST_SKIP     onlineCancelUnavailableMessage = message;
                //TEST_SKIP }
                //TEST_SKIP continue;
            }
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

            String message = !"".equals(onlineCancelUnavailableMessage)
                    ? onlineCancelUnavailableMessage
                    : ResSvcChgMessage.ADDITION_ONLINE_CANCEL_UNAVAILABLE.getMessage();
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
            String message) {
        AdditionPreCheckResVO res = new AdditionPreCheckResVO();
        res.setPrdcCd(prdcCdList.get(0));
        res.setPrdcCdList(prdcCdList);
        res.setPreCheckFailedPrdcCdList(preCheckFailedPrdcCdList);
        res.setOnlineCancelUnavailablePrdcCdList(onlineCancelUnavailablePrdcCdList);
        res.setResltMsgList(messageList);
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

    /**
     * Y24 실패 응답에서 화면에 표시할 메시지를 추출한다.
     */
    private String getMoscPrdcTrtmPreChkMessage(AdditionPreCheckResVO res) {
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


    /**
     * 부가서비스 해지 (MSP_RATE_MST 검증 + X38)
     *
     * [처리 순서]
     * 1. MSP_RATE_MST@DL_MSP 조회
     *    - null → NO_EXSIST_RATE: 요금제 정보 없음 (해지 불가 처리)
     *    - onlineCanYn ≠ "Y" → NO_ONLINE_CAN_CHANGE_ADD: 온라인 해지 불가
     *      (해지 가능한 부가서비스만 온라인 처리, 나머지는 고객센터 안내)
     * 2. X38 해지 호출
     *    - prodHstSeq 있음 → moscRegSvcCanChgSeq: 특정 이력 번호 기준 해지
     *      (로밍처럼 동일 SOC를 여러 번 가입한 경우, 특정 건만 해지)
     *    - prodHstSeq 없음 → moscRegSvcCanChg: 단순 SOC 기준 해지
     *
     * ASIS 참조: moscRegSvcCanChg() / moscRegSvcCanChgSeq() — MyPageSearchDto 세션 의존,
     *           Map<String,Object> 반환 → TOBE에서 AdditionApplyResVO로 교체
     */

    public FormResponse<AdditionApplyResVO> moscRegSvcCanChg(AdditionApplyReqDto req) {
        logger.debug("[moscRegSvcCanChg] start: ncn={}, ctn={}, custId={}, soc={}, prodHstSeq={}",
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getProdHstSeq());
        try {
            // [1] MSP_RATE_MST@DL_MSP — 온라인 해지 가능 여부 사전 검증
            MspRateMstDto mspRateMstDto = mspApiDirectRepository.query("/msp/mspRateMst", req.getSoc(), MspRateMstDto.class);
            if (mspRateMstDto == null) {
                // 요금제 정보 자체가 없는 경우 — 해지 진행 불가
                logger.warn("[moscRegSvcCanChg] MSP_RATE_MST not found: soc={}", req.getSoc());
                return FormResponse.of(ResSvcChgMessage.ADDITION_RATE_NOT_FOUND);
            }
            String onlineCanYn = StringUtil.NVL(mspRateMstDto.getOnlineCanYn(), "");
            logger.debug("[moscRegSvcCanChg] MSP_RATE_MST: soc={}, onlineCanYn={}, canCmnt={}",
                    req.getSoc(), onlineCanYn, mspRateMstDto.getCanCmnt());

            //20260515 확인 온라인 해지가능여부 체크여부(일단SKIP) EX) NOSPAM4:불법 TM 수신차단 등
            if (!"Y".equals(onlineCanYn)) {
                // 온라인 해지 불가 SOC — 고객센터 통해 해지 안내
                logger.warn("[moscRegSvcCanChg] 확인필요 온라인 해지가능여부 체크여부: soc={}, onlineCanYn={}", req.getSoc(), onlineCanYn);
                //TEST_SKIP return FormResponse.of(ResSvcChgMessage.ADDITION_ONLINE_CANCEL_UNAVAILABLE);
            }

            // [2] M플랫폼 X38 — 부가서비스 해지
            MpMoscRegSvcCanChgInVO vo;
            boolean hasProdHstSeq = req.getProdHstSeq() != null && !req.getProdHstSeq().isEmpty();
            logger.debug("[moscRegSvcCanChg] X38 call: hasProdHstSeq={}, soc={}", hasProdHstSeq, req.getSoc());
            if (hasProdHstSeq) {
                // prodHstSeq 있음: 특정 이력 건 해지 (로밍 등 동일 SOC 복수 가입 케이스)
                vo = mPlatFormService.moscRegSvcCanChgSeq(
                    req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getProdHstSeq());
            } else {
                // prodHstSeq 없음: SOC 기준 단순 해지
                vo = mPlatFormService.moscRegSvcCanChg(
                    req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc());
            }

            logger.debug("[moscRegSvcCanChg] X38 response: success={}, soc={}", vo.isSuccess(), req.getSoc());
            if (!vo.isSuccess()) {
                // M플랫폼 응답 실패
                logger.warn("[moscRegSvcCanChg] X38 failed: ncn={}, soc={}", req.getNcn(), req.getSoc());
                throw new McpCommonException(COMMON_EXCEPTION);
            }

        } catch (SocketTimeoutException e) {
            logger.warn("[moscRegSvcCanChg] socket timeout: ncn={}, soc={}", req.getNcn(), req.getSoc());
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        } catch (SelfServiceException e) {
            logger.warn("[moscRegSvcCanChg] SelfServiceException: ncn={}, soc={}, msg={}", req.getNcn(), req.getSoc(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            logger.warn("[moscRegSvcCanChg] exception: ncn={}, soc={}, msg={}", req.getNcn(), req.getSoc(), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        }

        logger.debug("[moscRegSvcCanChg] success: ncn={}, soc={}", req.getNcn(), req.getSoc());
        return FormResponse.of(ResSvcChgMessage.SUCCESS, AdditionApplyResVO.of(req.getSoc()));
    }

    /**
     * 부가서비스 신청 (Y25, 선해지 포함)
     *
     * [처리 순서]
     * 1. flag="Y"이면 선해지 (cancelAddSvc 내부 호출)
     *    → 실패 시 즉시 반환 (신청 진행 중단)
     *    → 로밍 등 동일 SOC를 해지 후 재가입하는 "변경" 시나리오
     * 2. M플랫폼 Y25 호출 — 상품변경처리(multi)
     *    ASIS X21(단건)에서 Y25(multi)로 교체.
     *    Y25는 복수 SOC 처리 및 선/후처리 조합 지원.
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
    public FormResponse<AdditionApplyResVO> regSvcChg(AdditionApplyReqDto req) {
        logger.debug("[regSvcChg] start: ncn={}, ctn={}, custId={}, soc={}, flag={}, ftrNewParam={}",
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFlag(), req.getFtrNewParam());
        try {
            // [1] 선해지 (flag="Y": 동일 SOC 해지 후 재가입 — 로밍 변경 등)
            if ("Y".equals(req.getFlag())) {
                logger.debug("[regSvcChg] 선해지 진행: soc={}", req.getSoc());
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

            // [2] M플랫폼 Y25 — 부가서비스 신청 (X21 대체)
            logger.debug("[regSvcChg] Y25 call: ncn={}, soc={}", req.getNcn(), req.getSoc());
            mPlatFormService.regSvcChgY25(
                req.getNcn(), req.getCtn(), req.getCustId(), req.getSoc(), req.getFtrNewParam());
            logger.debug("[regSvcChg] Y25 success: ncn={}, soc={}", req.getNcn(), req.getSoc());

            // [ASIS] 포인트 처리 — 포인트 기능 미이관
            // 포인트할인(REG_SVC_CD_4) 신청 시 포인트 사용 처리 (pointService.editPoint)
            // if (Constants.REG_SVC_CD_4.equals(req.getSoc())) {
            //     CustPointDto custPoint = myBenefitService.selectCustPoint(req.getNcn());
            //     if (custPoint != null) { pointService.editPoint(custPointTxnDto); }
            // }

        } catch (SocketTimeoutException e) {
            logger.warn("[regSvcChg] socket timeout: ncn={}, soc={}", req.getNcn(), req.getSoc());
            throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
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
        String mtCd    = StringUtil.NVL(req.getMtCd(), "");
        String strtDt  = StringUtil.NVL(req.getStrtDt(), "");
        String endDt   = StringUtil.NVL(req.getEndDt(), "");
        String subNcn  = StringUtil.NVL(req.getNcn(), "");

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

            String mtNcn    = StringUtil.NVL(userDto.getSvcCntrNo(), "");
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
            LocalDate subEndDate  = LocalDate.parse(endDt, dateFmt);

            String mtProdHstSeq = "";
            for (MpSocVO mpSoc : x97Res.getList()) {
                if (!mtProdHstSeq.isEmpty()) break;

                // ① 대표상품코드 일치
                if (!mtCd.equals(mpSoc.getSoc())) continue;

                // ② 대표상품의 shareSubContidList에 신청자 계약번호 포함 확인
                List<String> shareSubContidList = mpSoc.getShareSubContidList();
                if (shareSubContidList == null || shareSubContidList.isEmpty()) continue;
                if (shareSubContidList.stream().noneMatch(subNcn::equals)) continue;

                // ③ 서브 신청기간 ⊆ 대표상품 기간
                String mpStrtDt  = StringUtil.NVL(mpSoc.getStrtDt(), "");
                String mpEndDttm = StringUtil.NVL(mpSoc.getEndDttm(), "");
                if (mpStrtDt.isEmpty() || mpEndDttm.isEmpty()) {
                    logger.warn("[getMtProdHstSeq] 대표상품 기간정보 없음, skip: soc={}, prodHstSeq={}", mpSoc.getSoc(), mpSoc.getProdHstSeq());
                    continue;
                }
                LocalDate mtStrtDate = LocalDate.parse(mpStrtDt.substring(0, 8), dateFmt);
                LocalDate mtEndDate  = LocalDate.parse(mpEndDttm.substring(0, 8), dateFmt);
                if (subStrtDate.isBefore(mtStrtDate) || subEndDate.isAfter(mtEndDate)) continue;

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
                            LocalDateTime nowDt   = LocalDateTime.now();
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

}
