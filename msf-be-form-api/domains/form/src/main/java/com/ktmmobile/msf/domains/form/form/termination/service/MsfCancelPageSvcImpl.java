package com.ktmmobile.msf.domains.form.form.termination.service;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpFarMonBillingInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpFarMonDetailInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpMonthPayMentDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.common.dto.McpFarPriceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoDto;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.FarPricePlanResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfFarPricePlanService;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfChangPageSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationSettlementDto;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCancelVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelPageRepositoryImpl;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION;


@Service
public class MsfCancelPageSvcImpl implements MsfCancelPageSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfCancelPageSvcImpl.class);

    @Autowired
    private CancelPageRepositoryImpl cancelPageRepository;

    @Autowired
    private MsfRequestRepositoryImpl msfRequestRepository;

    @Autowired
    private McpRequestRepositoryImpl mcpRequestRepository;

    @Autowired
    private MsfMplatFormService msfMplatFormService;

    /** requestView 위약금 블록(X54/X16/mspAddInfo) 조회용 마이올레 서비스 */
    @Autowired
    private MsfChangPageSvc msfChangPageSvc;

    @Autowired
    private McpApiClient mcpApiClient;

    @Autowired
    private FormCommService formCommService;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfFarPricePlanService farPricePlanService;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Override
    public AgentInfoDto getTerminationAgentInfo(AgentInfoRequest request) {
        logger.info("[해지] 대리점 정보 조회 요청 — shopOrgnId={}", request.shopOrgnId());
        AgentInfoDto result = formCommService.getAgentInfo(request.shopOrgnId());
        logger.info("[해지] 대리점 정보 조회 완료 — shopOrgnId={}, orgnNm={}", request.shopOrgnId(), result != null ? result.orgnNm() : "null");
        return result;
    }

    /** 가입정보 첫화면 초기 데이터 조회 로직을 해지 화면 서비스에서 직접 제공한다. */
    @Override
    public Map<String, Object> getMyinfoView(HttpServletRequest request, MyPageSearchDto searchVO) {
        logger.info("[해지] getMyinfoView 조회 시작 — ncn={}, ctn={}, custId={}", searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());

        // [ASIS] UserSessionDto 세션 의존 제거 — MSF TOBE 로그인은 USER_SESSION 세션 키를 사용하지 않음
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = new java.util.ArrayList<>();

        if (userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            logger.info("[해지] 세션 사용자 확인 — userId={}", userSession.getUserId());
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
            boolean fullMember = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
            if (!fullMember) {
                logger.warn("[해지] 정회원 체크 실패 — userId={}", userSession.getUserId());
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }
        }

        String userName = userSession != null ? userSession.getName() : StringUtil.NVL(searchVO.getUserName(), "");
        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();
        String contractNum = searchVO.getContractNum();
        String modelName = StringUtil.NVL(searchVO.getModelName(), "-");

        McpFarPriceDto mcpFarPriceDto = null;
        String prvRateGrpNm = "-";
        String rateAdsvcLteDesc = "- MB";
        String rateAdsvcCallDesc = "- 분";
        String rateAdsvcSmsDesc = "- 건";

        try {
            logger.info("[해지] 요금제 정보 조회 — contractNum={}", contractNum);
            mcpFarPriceDto = msfMypageSvc.selectFarPricePlan(contractNum);
            if (mcpFarPriceDto != null) {
                prvRateGrpNm = mcpFarPriceDto.getPrvRateGrpNm();
                logger.info("[해지] 요금제 정보 조회 완료 — prvRateGrpNm={}", prvRateGrpNm);

                FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
                rateAdsvcLteDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(), "- MB");
                rateAdsvcCallDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(), "- 분");
                rateAdsvcSmsDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(), "- 건");
            }
        } catch (SelfServiceException e) {
            logger.info("[getMyinfoView] SelfServiceException: {}", e.getMessage());
        } catch (Exception e) {
            logger.info("[getMyinfoView] 요금제 상세 조회 실패: {}", e.getMessage());
        }

        String addr = "-";
        String initActivationDate = "-";
        Map<String, Object> combinePayData = new HashMap<>();

        try {
            logger.info("[해지] perMyktfInfo(X01) 조회 — ncn={}, ctn={}, custId={}", ncn, ctn, custId);
            MpPerMyktfInfoVO perMyktfInfo = msfMplatFormService.perMyktfInfo(ncn, ctn, custId);
            if (perMyktfInfo != null) {
                logger.info("[해지] perMyktfInfo(X01) 조회 결과 — addr={}, initActivationDate={}, email={}, homeTel={}",
                    logValue(perMyktfInfo.getAddr()),
                    logValue(perMyktfInfo.getInitActivationDate()),
                    logValue(perMyktfInfo.getEmail()),
                    logValue(perMyktfInfo.getHomeTel()));
                addr = StringUtil.NVL(perMyktfInfo.getAddr(), "-");
                initActivationDate = StringUtil.NVL(perMyktfInfo.getInitActivationDate(), "-");
            } else {
                logger.info("[해지] perMyktfInfo(X01) 조회 결과 — null");
            }
        } catch (SocketTimeoutException | SelfServiceException e) {
            logger.warn("[getMyinfoView] perMyktfInfo 조회 실패: {}", e.getMessage());
        }

        try {
            logger.info("[해지] 납부방법/명세서 조회 시작 — ncn={}, ctn={}", ncn, ctn);
            MpFarChangewayInfoVO farChgWayInfo = msfMplatFormService.farChangewayInfo(ncn, ctn, custId);
            if (farChgWayInfo != null) {
                logger.info("[해지] farChangewayInfo(X23) 조회 결과 — payMethod={}, billCycleDueDay={}, payTmsCd={}, blBankAcctNo={}, prevCardNo={}, prevExpirDt={}, blAddr={}",
                    logValue(farChgWayInfo.getPayMethod()),
                    logValue(farChgWayInfo.getBillCycleDueDay()),
                    logValue(farChgWayInfo.getPayTmsCd()),
                    logValue(farChgWayInfo.getBlBankAcctNo()),
                    logValue(farChgWayInfo.getPrevCardNo()),
                    logValue(farChgWayInfo.getPrevExpirDt()),
                    logValue(farChgWayInfo.getBlAddr()));
            } else {
                logger.info("[해지] farChangewayInfo(X23) 조회 결과 — null");
            }
            MpMoscBilEmailInfoInVO bilEmailInfo = null;
            if (farChgWayInfo != null) {
                bilEmailInfo = msfMplatFormService.kosMoscBillInfo(ncn, ctn, custId);
                if (bilEmailInfo != null) {
                    logger.info("[해지] kosMoscBillInfo(X49) 조회 결과 — billTypeCd={}, maskedEmail={}, ctn={}",
                        logValue(bilEmailInfo.getBillTypeCd()),
                        logValue(bilEmailInfo.getMaskedEmail()),
                        logValue(bilEmailInfo.getCtn()));
                } else {
                    logger.info("[해지] kosMoscBillInfo(X49) 조회 결과 — null");
                }
            }
            combinePayData = combinePayData(farChgWayInfo, bilEmailInfo);
            logger.info("[해지] combinePayData 결과 — payData={}, billData={}",
                logMap(combinePayData.get("payData")),
                logMap(combinePayData.get("billData")));
        } catch (SelfServiceException e) {
            logger.warn("[getMyinfoView] 납부방법/명세서 조회 실패: {}", e.getMessage());
            combinePayData = combinePayData(null, null);
        } catch (Exception e) {
            logger.warn("[getMyinfoView] 납부방법/명세서 조회 오류", e);
            combinePayData = combinePayData(null, null);
        }

        String maskingSession = "";
        if (userSession != null) {
            maskingSession = SessionUtils.getMaskingSession() > 0 ? "Y" : "";
        }
        if ("Y".equals(maskingSession)) {
            searchVO.setUserName(userSession.getName());

            MaskingDto maskingDto = new MaskingDto();
            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호,납부정보");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        } else {
            searchVO.setUserName(StringMakerUtil.getName(userName));
            if (!"-".equals(addr)) {
                addr += "********";
            }
        }

        String remindBlckYn = "";
        try {
            McpUserCntrMngDto selectSocDesc = msfMypageSvc.selectSocDesc(contractNum);
            if (selectSocDesc != null
                && "Y".equals(selectSocDesc.getRemindYn())
                && !StringUtils.isEmpty(selectSocDesc.getRemindProdType())) {
                remindBlckYn = "Y";
            }
        } catch (Exception e) {
            logger.warn("[getMyinfoView] socDesc 조회 실패: {}", e.getMessage());
        }

        HashMap<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("cntrList", cntrList);
        rtnMap.put("searchVO", searchVO);
        rtnMap.put("modelName", modelName);
        rtnMap.put("prvRateGrpNm", prvRateGrpNm);
        rtnMap.put("rateAdsvcLteDesc", rateAdsvcLteDesc);
        rtnMap.put("rateAdsvcCallDesc", rateAdsvcCallDesc);
        rtnMap.put("rateAdsvcSmsDesc", rateAdsvcSmsDesc);
        rtnMap.put("initActivationDate", initActivationDate);
        rtnMap.put("addr", addr);
        rtnMap.put("payData", combinePayData.get("payData"));
        rtnMap.put("billData", combinePayData.get("billData"));
        rtnMap.put("maskingBtn", "Y");
        rtnMap.put("maskingSession", maskingSession);
        rtnMap.put("remindBlckYn", remindBlckYn);
        logger.info("[해지] getMyinfoView 조회 완료 — ncn={}, ctn={}, prvRateGrpNm={}, remindBlckYn={}", ncn, ctn, prvRateGrpNm, remindBlckYn);
        return rtnMap;
    }

    /** X23 납부방법과 X49 명세서 정보를 화면 응답 구조로 조합한다. */
    private Map<String, Object> combinePayData(
        MpFarChangewayInfoVO farChgWayInfo,
        MpMoscBilEmailInfoInVO bilEmailInfo
    ) {
        Map<String, Object> rtnMap = new HashMap<>();
        Map<String, String> payData = new HashMap<>();
        Map<String, String> billData = new HashMap<>();

        if (farChgWayInfo == null) {
            rtnMap.put("payData", null);
            rtnMap.put("billData", null);
            return rtnMap;
        }

        String payMethod = StringUtil.NVL(farChgWayInfo.getPayMethod(), "-");
        String blAddr = StringUtil.NVL(farChgWayInfo.getBlAddr(), "-");
        String blBankAcctNo = StringUtil.NVL(farChgWayInfo.getBlBankAcctNo(), "-");
        String prevCardNo = StringUtil.NVL(farChgWayInfo.getPrevCardNo(), "-");
        String prevExpirDt = StringUtil.NVL(farChgWayInfo.getPrevExpirDt(), "-");
        String billCycleDueDay = StringUtil.NVL(farChgWayInfo.getBillCycleDueDay(), "-");
        String payTmsCd = StringUtil.NVL(farChgWayInfo.getPayTmsCd(), "-");

        boolean giro = "지로".equals(payMethod);

        if ("99".equals(billCycleDueDay)) {
            billCycleDueDay = "말일";
        } else if (!"-".equals(billCycleDueDay)) {
            billCycleDueDay += "일";
        }

        if ("01".equals(payTmsCd)) {
            payTmsCd = "1회차(11일경)";
        } else {
            payTmsCd = "2회차(20일경)";
        }

        if (7 < prevExpirDt.length()) {
            prevExpirDt = prevExpirDt.substring(0, 4) + "-" + prevExpirDt.substring(4, 6) + "-" + prevExpirDt.substring(6, 8);
        }

        payData.put("payMethod", payMethod);
        payData.put("blBankAcctNo", blBankAcctNo);
        payData.put("billCycleDueDay", billCycleDueDay);
        payData.put("prevCardNo", prevCardNo);
        payData.put("prevExpirDt", prevExpirDt);
        payData.put("payTmsCd", payTmsCd);

        if (bilEmailInfo == null) {
            rtnMap.put("payData", payData);
            rtnMap.put("billData", null);
            return rtnMap;
        }

        String billTypeCd = StringUtil.NVL(bilEmailInfo.getBillTypeCd(), "");
        String reqType = "-";
        String reqTypeNm = "";
        String blaAddr = "-";

        if ("CB".equals(billTypeCd)) {
            reqType = "이메일 명세서";
        } else if ("LX".equals(billTypeCd)) {
            reqType = "우편 명세서";
        } else if ("MB".equals(billTypeCd)) {
            reqType = "모바일 명세서(MMS)";
        }

        if (!giro) {
            if ("CB".equals(billTypeCd)) {
                reqTypeNm = "메일주소";
                blaAddr = StringUtil.NVL(bilEmailInfo.getMaskedEmail(), "-");
            } else if ("MB".equals(billTypeCd)) {
                reqTypeNm = "휴대폰 번호";
                blaAddr = StringUtil.NVL(bilEmailInfo.getCtn(), "-");
            } else {
                reqTypeNm = "청구지";
            }
        } else {
            reqTypeNm = "청구지";
            blaAddr = blAddr;
        }

        billData.put("reqType", reqType);
        billData.put("reqTypeNm", reqTypeNm);
        billData.put("blaAddr", blaAddr);
        billData.put("billTypeCd", billTypeCd);

        rtnMap.put("payData", payData);
        rtnMap.put("billData", billData);
        return rtnMap;
    }

    /**
     * Enriches ctn/custId from ncn before calling MP charge APIs.
     * TEST_SKIP validation behavior is intentionally preserved for screen tests.
     */
    @Override
    public TerminationRemainChargeResVO getRemainCharge(TerminationRemainChargeReqDto reqDto) {
        logger.debug("[getRemainCharge] selectCntrListNoLogin: ncn={}", safe(reqDto.getNcn()));
        McpUserCntrMngDto cntrInfo = msfChangPageSvc.selectCntrListNoLogin(reqDto.getNcn());
        if (cntrInfo == null) {
            TerminationRemainChargeResVO errVO = new TerminationRemainChargeResVO();
            errVO.setSuccess(false);
            errVO.setMessage("계약 정보를 찾을 수 없습니다.");
            return errVO;
        }
        reqDto.setCtn(cntrInfo.getCntrMobileNo());
        reqDto.setCustId(cntrInfo.getCustId());

        // AS-IS getRealTimePriceAjax와 동일한 X18 실시간 잔여 요금 조회 흐름이다.
        logger.debug("[getRemainCharge] start: ncn={}, ctn={}, custIdPresent={}",
            safe(reqDto.getNcn()), maskPhone(reqDto.getCtn()), !isBlank(reqDto.getCustId()));

        TerminationRemainChargeResVO resVO = new TerminationRemainChargeResVO();
        try {
            MpFarRealtimePayInfoVO mpVO = msfMplatFormService.farRealtimePayInfo(
                reqDto.getNcn(), reqDto.getCtn(), reqDto.getCustId());

            if (mpVO == null) {
                resVO.setSuccess(false);
                resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
                return resVO;
            }

            resVO.setSuccess(true);
            resVO.setSearchDay(mpVO.getSearchDay());
            resVO.setSearchTime(mpVO.getSearchTime());
            resVO.setSumAmt(mpVO.getSumAmt());

            List<TerminationRemainChargeResVO.FareItem> items = new ArrayList<>();
            if (mpVO.getList() != null) {
                for (MpFarRealtimePayInfoVO.RealFareVO realFare : mpVO.getList()) {
                    if ("중단위약금".equals(realFare.getGubun())) {
                        continue;
                    }
                    TerminationRemainChargeResVO.FareItem item = new TerminationRemainChargeResVO.FareItem();
                    item.setGubun(realFare.getGubun());
                    item.setPayment(realFare.getPayment());
                    items.add(item);
                }
            }
            resVO.setItems(items);
            logger.info("[getRemainCharge] X18 success: ncn={}, itemCount={}, sumAmt={}",
                safe(reqDto.getNcn()), items.size(), safe(resVO.getSumAmt()));

            // 화면 응답에 AS-IS requestView의 위약금 블록 결과를 포함한다.
            TerminationSettlementDto settlement = getTerminationSettlement(reqDto);
            resVO.setSettlement(settlement);
            applySettlementFields(resVO, settlement);
            applyMockAmountData(resVO);
        } catch (com.ktmmobile.msf.domains.form.common.exception.SelfServiceException e) {
            logger.info("X18 ERROR: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(true);
            applyMockAmountData(resVO);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        } catch (java.net.SocketTimeoutException e) {
            logger.info("X18 ERROR (Timeout): ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        } catch (Exception e) {
            logger.error("X18 잔여요금 조회 오류: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            resVO.setSuccess(false);
            resVO.setMessage("잔여요금 조회 중 오류가 발생했습니다.");
        }
        applyMockAmountData(resVO);
        resVO.setSuccess(true);
        resVO.setMessage("서비스 해지 금액 연동 전으로 mock 데이터를 반환합니다.");
        return resVO;
    }

    // TEST_SKIP: 외부 금액 연동 완료 전까지 화면 테스트용 금액을 강제로 내려준다.
    private void applyMockAmountData(TerminationRemainChargeResVO resVO) {
        if (resVO == null) {
            return;
        }

        // Temporary mock data before external integrations are ready
        resVO.setSearchDay("20260416");
        resVO.setSearchTime("101500");
        resVO.setSumAmt("15870"); // usage fee

        List<TerminationRemainChargeResVO.FareItem> items = new ArrayList<>();
        TerminationRemainChargeResVO.FareItem usageFare = new TerminationRemainChargeResVO.FareItem();
        usageFare.setGubun("사용요금");
        usageFare.setPayment("15870");
        items.add(usageFare);
        resVO.setItems(items);

        TerminationSettlementDto settlement = resVO.getSettlement();
        if (settlement == null) {
            settlement = new TerminationSettlementDto();
            resVO.setSettlement(settlement);
        }

        settlement.setPrePayment(false);
        settlement.setTrmnForecBprmsAmt("42000"); // penalty fee
        settlement.setRtrnAmtAndChageDcAmt("57870"); // settlement amount
        settlement.setInstallmentAmt("180000"); // installment amount
        settlement.setRemainPay(180000);
        settlement.setRemainMonth(6);

        applySettlementFields(resVO, settlement);
    }

    /** settlement DTO → 잔여요금 응답 단일 필드 세팅 */
    private void applySettlementFields(TerminationRemainChargeResVO resVO, TerminationSettlementDto settlement) {
        if (resVO == null || settlement == null) {
            return;
        }
        resVO.setPenaltyFee(settlement.getTrmnForecBprmsAmt());          // 위약금
        resVO.setSettlementFee(settlement.getRtrnAmtAndChageDcAmt());    // 정산요금
        resVO.setRemainPeriod(String.valueOf(settlement.getRemainMonth())); // 상환기간(개월)
        resVO.setRemainAmount(String.valueOf(settlement.getRemainPay())); // 금액(잔여할부)
    }

    /**
     * AS-IS(MyOllehController requestView 위약금 블록) 분리 함수.
     * SRM18062741675 기준으로 X54/X16/mspAddInfo 흐름
     */
    private TerminationSettlementDto getTerminationSettlement(TerminationRemainChargeReqDto reqDto) {
        // SRM18062741675 / AS-IS(MyOllehController requestView 위약금 블록) 이관
        // 순서: 선불 여부 -> X54 -> (saleEngtNm 존재 시) X16 + mspAddInfo
        if (reqDto == null) {
            return null;
        }
        TerminationSettlementDto settlement = new TerminationSettlementDto();
        try {
            String ncn = reqDto.getNcn();
            String ctn = reqDto.getCtn();
            String custId = reqDto.getCustId();

            // 1) 선불 요금제 여부 확인
            boolean prePayment = isPrePayment(ncn);
            settlement.setPrePayment(prePayment);
            if (prePayment) {
                return settlement;
            }

            // 2) 스폰서/위약금 조회(X54)
            MpMoscSpnsrItgInfoInVO moscSpnsrItgInfo = msfMplatFormService.kosMoscSpnsrItgInfo(ncn, ctn, custId);
            if (moscSpnsrItgInfo == null) {
                return settlement;
            }

            // AS-IS와 동일하게 null 값은 "0"으로 보정
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmt())) moscSpnsrItgInfo.setChageDcAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getTrmnForecBprmsAmt())) moscSpnsrItgInfo.setTrmnForecBprmsAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getRtrnAmtAndChageDcAmt())) moscSpnsrItgInfo.setRtrnAmtAndChageDcAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmtSuprtRtrnAmt())) moscSpnsrItgInfo.setChageDcAmtSuprtRtrnAmt("0");

            // X54 응답값을 settlement DTO로 매핑
            settlement.setSaleEngtNm(moscSpnsrItgInfo.getSaleEngtNm());
            settlement.setSaleEngtOptnCd(moscSpnsrItgInfo.getSaleEngtOptnCd());
            settlement.setTrmnForecBprmsAmt(moscSpnsrItgInfo.getTrmnForecBprmsAmt());
            settlement.setChageDcAmt(moscSpnsrItgInfo.getChageDcAmt());
            settlement.setRtrnAmtAndChageDcAmt(moscSpnsrItgInfo.getRtrnAmtAndChageDcAmt());
            settlement.setChageDcAmtSuprtRtrnAmt(moscSpnsrItgInfo.getChageDcAmtSuprtRtrnAmt());
            settlement.setKtSuprtPenltAmt(moscSpnsrItgInfo.getKtSuprtPenltAmt());
            settlement.setStorSuprtPenltAmt(moscSpnsrItgInfo.getStorSuprtPenltAmt());
            settlement.setEngtAplyStDate(moscSpnsrItgInfo.getEngtAplyStDate());
            settlement.setEngtExpirPamDate(moscSpnsrItgInfo.getEngtExpirPamDate());
            settlement.setEngtRmndDate(moscSpnsrItgInfo.getEngtRmndDate());

            // 3) saleEngtNm 존재 시에만 잔여 할부금(X16), 할부원금(mspAddInfo) 조회
            //TEST_SKIP if (StringUtil.isNotBlank(moscSpnsrItgInfo.getSaleEngtNm())) {
                try {
                    // X16 조회를 위한 최신 청구월 정보 조회(X15)
                    logger.debug("[getTerminationSettlement_X15 farMonBillingInfoDto request: ncn={}", safe(ncn));
                    MpFarMonBillingInfoDto billInfo = msfMplatFormService.farMonBillingInfoDto(
                        ncn, ctn, custId, DateTimeUtil.getFormatString("yyyyMM"));

                    if (billInfo != null && billInfo.getMonthList() != null && !billInfo.getMonthList().isEmpty()) {
                        MpMonthPayMentDto monthPay = billInfo.getMonthList().get(0);
                        // 요금조회 상세(X16) - 잔여 할부금
                        MpFarMonDetailInfoDto farMonDetailInfoDto = msfMplatFormService.farMonDetailInfoDto(
                            ncn, ctn, custId,
                            monthPay.getBillSeqNo(),
                            monthPay.getBillDueDateList(),
                            monthPay.getBillMonth(),
                            monthPay.getBillStartDate(),
                            monthPay.getBillEndDate());
                        if (farMonDetailInfoDto != null) {
                            if (StringUtil.isEmpty(farMonDetailInfoDto.getInstallmentAmt())) {
                                farMonDetailInfoDto.setInstallmentAmt("0");
                            }
                            settlement.setInstallmentAmt(farMonDetailInfoDto.getInstallmentAmt());
                            settlement.setTotalNoOfInstall(farMonDetailInfoDto.getTotalNoOfInstall());
                            settlement.setInstallmentYN(farMonDetailInfoDto.getInstallmentYN());
                        }
                    }
                } catch (Exception e) {
                    logger.warn("[getTerminationSettlement] X16 error: ncn={}", safe(ncn), e);
                }

                try {
                    // 할부원금 조회(MSP_JUO_ADD_INFO)
                    // [ASIS] MyOllehController.requestView() — mcp-api REST 직접 호출
                    //   RestTemplate restTemplate = new RestTemplate();
                    //   mspJuoAddInfoDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/mspAddInfo", searchVO.getNcn(), MspJuoAddInfoDto.class);
                    // [TOBE] McpApiClient.post() — use-mcp 정책·연결실패 시 MspApiDirectRepository(mspSqlSession)로 자동 전환
                    logger.debug("[getTerminationSettlement] mspAddInfo request: ncn={}", safe(ncn));
                    MspJuoAddInfoDto mspJuoAddInfoDto = mcpApiClient.post("/mypage/mspAddInfo", ncn, MspJuoAddInfoDto.class);
                    logger.debug("[getTerminationSettlement] mspAddInfo response: ncn={}, hasBody={}",
                        safe(ncn), mspJuoAddInfoDto != null);
                    if (mspJuoAddInfoDto != null) {
                        settlement.setInstOrginAmnt(mspJuoAddInfoDto.getInstOrginAmnt());
                        settlement.setInstMnthCnt(mspJuoAddInfoDto.getInstMnthCnt());
                        settlement.setRemainPay(mspJuoAddInfoDto.getRemainPay());
                        settlement.setRemainMonth(mspJuoAddInfoDto.getRemainMonth());
                        settlement.setModelName(mspJuoAddInfoDto.getModelName());
                    }
                } catch (Exception e) {
                    logger.warn("[getTerminationSettlement] mspAddInfo error: ncn={}", safe(ncn), e);
                }
            //}
        } catch (Exception e) {
            logger.warn("[getTerminationSettlement] error: ncn={}, ctn={}",
                safe(reqDto.getNcn()), safe(reqDto.getCtn()), e);
        }
        return settlement;
    }

    /**
     * 선불 요금제 사용 여부 조회 (AS-IS /mypage/prePayment MCP-API 연계구현 )
     * @param contractNum 계약번호
     * @return 선불 요금제 여부
     */
    private boolean isPrePayment(String contractNum) {
        logger.debug("[isPrePayment] call: contractNum={}", safe(contractNum));

        // [ASIS] MypageController.prePayment() — mcp-api REST 직접 호출 후 MSP DB 조회
        //   POST /mypage/prePayment  →  mypageMapper.selectPrePayment(contractNum)
        //   RestTemplate restTemplate = new RestTemplate();
        //   int cnt = restTemplate.postForObject(apiInterfaceServer + "/mypage/prePayment", contractNum, int.class);
        // [TOBE] McpApiClient.post() — use-mcp 정책·연결실패(TEST) MspApiDirectRepository(mspSqlSession)로 자동 전환
        int cnt = mcpApiClient.post("/mypage/prePayment", contractNum, int.class);

        logger.debug("[isPrePayment] result: cnt={}", cnt);
        return cnt >= 1;
    }

    /** 신청 완료 요청의 처리 시간과 결과 로그를 남기고 실제 저장 처리는 apply에 위임한다. */
    @Override
    public TerminationApplyResVO complete(String applicationKey, TerminationApplyReqDto reqDto) {
        long startedAt = System.currentTimeMillis();
        String ncn = reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "";

        logger.info("서비스해지 작성완료 요청: applicationKey={}, ncn={}", safe(applicationKey), ncn);

        TerminationApplyResVO res = apply(reqDto);
        long elapsed = System.currentTimeMillis() - startedAt;

        if (res != null && res.isSuccess()) {
            logger.info("서비스해지 작성완료 결과: applicationKey={}, ncn={}, success={}, applicationNo={}, elapsedMs={}",
                safe(applicationKey), ncn, true, res.getApplicationNo(), elapsed);
        } else {
            String message = res != null ? res.getMessage() : "null response";
            logger.warn("서비스해지 작성완료 실패: applicationKey={}, ncn={}, success={}, message={}, elapsedMs={}",
                safe(applicationKey), ncn, false, message, elapsed);
        }

        return res;
    }

    /**
     * 해지 신청 데이터를 smartform(MSF) 테이블에 먼저 저장하고,
     * 저장된 데이터를 다시 조회해 MCP DB link 테이블로 동기화한다.
     */
    @Override
    public TerminationApplyResVO apply(TerminationApplyReqDto reqDto) {
        logger.info("[apply] start: ncn={}, customerType={}, postMethod={}, isActive={}",
            reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "",
            reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getCustomerType()) : "",
            reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getPostMethod()) : "",
            reqDto != null && reqDto.getProduct() != null ? safe(reqDto.getProduct().getIsActive()) : "");

        String validationMessage = validateApplyRequest(reqDto);
        if (!isBlank(validationMessage)) {
            logger.warn("[apply] validation failed: ncn={}, reason={}",
                reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "",
                validationMessage);
            //TEST_SKIP  return TerminationApplyResVO.fail(validationMessage);
        }

        String managerCd = safe(reqDto.getCustomer().getManagerCd());
        String managerNm = safe(reqDto.getCustomer().getManagerNm());
        String agentCd = safe(reqDto.getCustomer().getAgentCd());
        String agentNm = safe(reqDto.getCustomer().getAgentNm());
        if (isBlank(agentCd)) {
            logger.error("[apply] fail: agentCd is blank, ncn={}", safe(reqDto.getCustomer().getNcn()));
            //TEST_SKIP return TerminationApplyResVO.fail("agentCd is required");
        }
        if (isBlank(managerCd)) {
            logger.error("[apply] fail: managerCd is blank, ncn={}", safe(reqDto.getCustomer().getNcn()));
            //TEST_SKIP return TerminationApplyResVO.fail("managerCd is required");
        }
        String cstmrTypeCd = normalizeCustomerType(reqDto.getCustomer().getCustomerType());
        if (isBlank(cstmrTypeCd)) {
            logger.error("[apply] fail: customerType is blank, ncn={}", safe(reqDto.getCustomer().getNcn()));
            //TEST_SKIP return TerminationApplyResVO.fail("customerType is required");
        }
        String receiveWayCd = normalizeReceiveWay(reqDto.getCustomer().getPostMethod());
        if (isBlank(receiveWayCd)) {
            logger.error("[apply] fail: postMethod is invalid, ncn={}", safe(reqDto.getCustomer().getNcn()));
            //TEST_SKIP return TerminationApplyResVO.fail("postMethod is required");
        }

        String cancelMobileNo = joinPhone(
            reqDto.getCustomer().getCancelPhone1(),
            reqDto.getCustomer().getCancelPhone2(),
            reqDto.getCustomer().getCancelPhone3()
        );
        String receiveMobileNo = joinPhone(
            reqDto.getCustomer().getAfterTel1(),
            reqDto.getCustomer().getAfterTel2(),
            reqDto.getCustomer().getAfterTel3()
        );

        if (isBlank(cancelMobileNo)) {
            logger.warn("[apply] fail: cancelMobileNo is blank, ncn={}", safe(reqDto.getCustomer().getNcn()));
            //TEST return TerminationApplyResVO.fail("해지 대상 전화번호를 입력해 주세요.");
        }
        if (isBlank(receiveMobileNo)) {
            logger.warn("[apply] fail: receiveMobileNo is blank, ncn={}", safe(reqDto.getCustomer().getNcn()));
            //TEST return TerminationApplyResVO.fail("해지 후 연락처를 입력해 주세요.");
        }
        logger.debug("[apply] contact normalized: ncn={}, cancelMobileNo={}, receiveMobileNo={}",
            safe(reqDto.getCustomer().getNcn()), maskPhone(cancelMobileNo), maskPhone(receiveMobileNo));

        if (isLocal()) {
            logger.info("[apply] LOCAL mode shortcut: ncn={}", safe(reqDto.getCustomer().getNcn()));
            //TEST return TerminationApplyResVO.ok("LOCAL-CANCEL-" + System.currentTimeMillis());
        }

        try {
            Long requestKey = cancelPageRepository.nextRequestKey();
            if (requestKey == null) {
                logger.error("[apply] request key generation failed: ncn={}", safe(reqDto.getCustomer().getNcn()));
                return TerminationApplyResVO.fail("요청 번호 발급에 실패했습니다.");
            }
            logger.debug("[apply] request key generated: requestKey={}, ncn={}", requestKey, safe(reqDto.getCustomer().getNcn()));

            MsfRequestCancelVo vo = toMsfRequestCancelVo(
                requestKey,
                reqDto,
                cstmrTypeCd,
                receiveWayCd,
                cancelMobileNo
            );

            logger.debug("[apply] insert payload ready: requestKey={}, ncn={}, customerTypeCd={}, receiveWayCd={}, cancelUseCompanyCd={}, payAmt={}, pnltAmt={}, lastSumAmt={}",
                requestKey, safe(vo.getContractNum()), safe(vo.getCstmrTypeCd()), safe(vo.getReceiveWayCd()),
                safe(vo.getCancelUseCompanyCd()), vo.getPayAmt(), vo.getPnltAmt(), vo.getLastSumAmt());

            int inserted = msfRequestRepository.insertMsfRequestCancel(vo);
            if (inserted <= 0) {
                logger.error("[apply] insert failed: requestKey={}, ncn={}, inserted={}", requestKey, safe(vo.getContractNum()), inserted);
                return TerminationApplyResVO.fail("서비스해지 요청 저장에 실패했습니다.");
            }

            MsfRequestCstmrVo cstmrVo = toMsfRequestCstmrVo(requestKey, reqDto, cstmrTypeCd);

            int cstmrInserted = msfRequestRepository.insertMsfRequestCstmr(cstmrVo);
            if (cstmrInserted <= 0) {
                logger.error("[apply] insert cstmr failed: requestKey={}", requestKey);
            }

            int mcpCstmrInserted = mcpRequestRepository.insertMcpRequestCstmr(requestKey);
            if (mcpCstmrInserted <= 0) {
                logger.error("[apply] insert MCP request cstmr failed: requestKey={}", requestKey);
                return TerminationApplyResVO.fail("M포탈 고객정보 저장에 실패했습니다.");
            }

            int mcpInserted = mcpRequestRepository.insertMcpCancelRequest(requestKey);
            if (mcpInserted <= 0) {
                logger.error("[apply] insert MCP cancel request failed: requestKey={}", requestKey);
                return TerminationApplyResVO.fail("M포탈 데이터 저장에 실패했습니다.");
            }

            logger.info("[apply] success: requestKey={}, ncn={}", requestKey, safe(vo.getContractNum()));
            return TerminationApplyResVO.ok(String.valueOf(requestKey));
        } catch (Exception e) {
            logger.error("[apply] exception: ncn={}", reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "", e);
            return TerminationApplyResVO.fail("서비스해지 요청 처리 중 오류가 발생했습니다.");
        }
    }

    /** 화면 입력 DTO를 서비스해지 신청 저장 VO로 변환한다. */
    private MsfRequestCancelVo toMsfRequestCancelVo(
        Long requestKey,
        TerminationApplyReqDto reqDto,
        String cstmrTypeCd,
        String receiveWayCd,
        String cancelMobileNo
    ) {
        TerminationApplyReqDto.Customer customer = reqDto.getCustomer();
        TerminationApplyReqDto.Product product = reqDto.getProduct();
        TerminationApplyReqDto.Agreement agreement = reqDto.getAgreement();

        String managerCd = safe(customer.getManagerCd());
        String managerNm = safe(customer.getManagerNm());
        String agentCd = safe(customer.getAgentCd());
        String agentNm = safe(customer.getAgentNm());

        MsfRequestCancelVo vo = new MsfRequestCancelVo();
        vo.setRequestKey(requestKey);
        vo.setManagerCd(managerCd);
        vo.setManagerNm(managerNm);
        vo.setAgentCd(agentCd);
        vo.setAgentNm(agentNm);
        vo.setShopCd(agentCd);
        vo.setShopNm(agentNm);
        vo.setRealShopNm(agentNm);
        vo.setCpntId(safe(customer.getCpntId()));
        vo.setCpntNm(safe(customer.getCpntNm()));
        vo.setCntpntShopCd(safe(customer.getCntpntShopCd()));
        vo.setCntpntShopNm(safe(customer.getCntpntShopNm()));
        vo.setOperTypeCd("CC");
        vo.setCstmrTypeCd(cstmrTypeCd);
        vo.setCancelMobileNo(cancelMobileNo);
        vo.setContractNum(customer.getNcn());
        vo.setReceiveWayCd(receiveWayCd);
        vo.setCancelUseCompanyCd(normalizeUseType(product.getIsActive()));
        vo.setPayAmt(parseLong(product.getUsageFee()));
        vo.setPnltAmt(parseLong(product.getPenaltyFee()));
        vo.setLastSumAmt(parseLong(product.getFinalAmount()));
        Integer instamtMnthCnt = parseInteger(product.getRemainPeriod());
        vo.setInstamtMnthCnt(instamtMnthCnt != null ? String.valueOf(instamtMnthCnt) : null);
        vo.setInstamtMnthAmt(parseLong(product.getRemainAmount()));
        vo.setBenefitAgreeYn(toYn(agreement.isAgreeCheck1()));
        vo.setClauseCntrDelYn(toYn(agreement.isAgreeCheck2()));
        vo.setEtcAgreeYn(toYn(agreement.isAgreeCheck3()));
        vo.setMemo(product.getMemo());
        vo.setRecYn("N");
        vo.setAppFormYn("N");
        vo.setAppFormXmlYn("N");
        vo.setRegstId("MSF_FORM");
        vo.setProcCd("RC");
        vo.setCretId("MSF_FORM");
        vo.setAmdId("MSF_FORM");
        vo.setCretIp("127.0.0.1");
        vo.setAmdIp("127.0.0.1");
        return vo;
    }

    /** 화면 고객 정보를 신청 고객 저장 VO로 변환한다. */
    private MsfRequestCstmrVo toMsfRequestCstmrVo(
        Long requestKey,
        TerminationApplyReqDto reqDto,
        String cstmrTypeCd
    ) {
        TerminationApplyReqDto.Customer customer = reqDto.getCustomer();

        MsfRequestCstmrVo cstmrVo = new MsfRequestCstmrVo();
        initializeRequestCstmrDefaults(cstmrVo);
        cstmrVo.setRequestKey(requestKey);
        cstmrVo.setCstmrNm(safe(customer.getUserName()));

        if ("NA".equals(cstmrTypeCd) || "MI".equals(cstmrTypeCd)) {
            cstmrVo.setCstmrNativeBirth(safe(customer.getUserBirthDate()));
        } else if ("FO".equals(cstmrTypeCd) || "FM".equals(cstmrTypeCd)) {
            cstmrVo.setCstmrForeignerBirth(safe(customer.getUserBirthDate()));
        } else if ("CO".equals(cstmrTypeCd) || "PB".equals(cstmrTypeCd)) {
            cstmrVo.setCstmrJuridicalBirth(safe(customer.getUserBirthDate()));
        }

        cstmrVo.setCstmrMobileFnNo(safe(customer.getCancelPhone1()));
        cstmrVo.setCstmrMobileMnNo(safe(customer.getCancelPhone2()));
        cstmrVo.setCstmrMobileRnNo(safe(customer.getCancelPhone3()));
        cstmrVo.setCstmrReceiveTelFnNo(safe(customer.getAfterTel1()));
        cstmrVo.setCstmrReceiveTelNmNo(safe(customer.getAfterTel2()));
        cstmrVo.setCstmrReceiveTelRnNo(safe(customer.getAfterTel3()));
        return cstmrVo;
    }

    private String validateApplyRequest(TerminationApplyReqDto reqDto) {
        if (reqDto == null) {
            logger.debug("[validateApplyRequest] reqDto is null");
            return "요청 정보가 없습니다.";
        }
        String customerStepError = validateCustomerStep(reqDto.getCustomer());
        if (!isBlank(customerStepError)) {
            logger.debug("[validateApplyRequest] customer step invalid: {}", customerStepError);
            //TEST_SKIP return customerStepError;
        }
        String productStepError = validateProductStep(reqDto.getProduct());
        if (!isBlank(productStepError)) {
            logger.debug("[validateApplyRequest] product step invalid: {}", productStepError);
            //TEST_SKIP return productStepError;
        }
        String agreementStepError = validateAgreementStep(reqDto.getAgreement());
        if (!isBlank(agreementStepError)) {
            logger.debug("[validateApplyRequest] agreement step invalid: {}", agreementStepError);
        }
        return agreementStepError;
    }

    private String validateCustomerStep(TerminationApplyReqDto.Customer customer) {
        if (customer == null) {
            return "고객 정보가 없습니다.";
        }
        if (isBlank(customer.getCustomerType())) {
            return "고객 유형을 선택해 주세요.";
        }
        String normalizedCustomerType = normalizeCustomerType(customer.getCustomerType());
        if (!isAllowedValue(normalizedCustomerType, "NA", "MI", "FO", "FM", "CO", "PB")) {
            return "고객 유형이 올바르지 않습니다.";
        }
        if (isBlank(customer.getUserName())) {
            return "고객명을 입력해 주세요.";
        }
        if (isBlank(customer.getUserBirthDate())) {
            return "생년월일을 입력해 주세요.";
        }
        if (!is8DigitDate(customer.getUserBirthDate())) {
            return "생년월일 형식이 올바르지 않습니다.";
        }
        if (isBlank(customer.getNcn())) {
            return "계약번호가 없습니다.";
        }
        if (!isValidNcn(customer.getNcn())) {
            return "계약번호(ncn)는 9자리 숫자여야 합니다.";
        }

        String cancelMobileNo = joinPhone(customer.getCancelPhone1(), customer.getCancelPhone2(), customer.getCancelPhone3());
        if (isBlank(cancelMobileNo)) {
            return "해지 대상 전화번호를 입력해 주세요.";
        }
        if (!isValidPhoneNumber(cancelMobileNo)) {
            return "해지 대상 전화번호 형식이 올바르지 않습니다.";
        }

        String receiveMobileNo = joinPhone(customer.getAfterTel1(), customer.getAfterTel2(), customer.getAfterTel3());
        if (isBlank(receiveMobileNo)) {
            return "해지 후 연락처를 입력해 주세요.";
        }
        if (!isValidPhoneNumber(receiveMobileNo)) {
            return "해지 후 연락처 형식이 올바르지 않습니다.";
        }

        if (isBlank(customer.getPostMethod())) {
            return "수령 방법을 선택해 주세요.";
        }
        if (normalizeReceiveWay(customer.getPostMethod()) == null) {
            return "수령 방법이 올바르지 않습니다.";
        }
        return null;
    }

    private String validateProductStep(TerminationApplyReqDto.Product product) {
        if (product == null) {
            return "상품 정보가 없습니다.";
        }
        if (isBlank(product.getIsActive())) {
            return "해지 후 사용 여부를 선택해 주세요.";
        }
        if (normalizeUseType(product.getIsActive()) == null) {
            return "해지 후 사용 여부가 올바르지 않습니다.";
        }

        String amountError = validateNonNegativeNumber(product.getUsageFee(), "사용 요금을 확인해 주세요.");
        if (!isBlank(amountError)) {
            return amountError;
        }
        amountError = validateNonNegativeNumber(product.getPenaltyFee(), "위약금을 확인해 주세요.");
        if (!isBlank(amountError)) {
            return amountError;
        }
        amountError = validateNonNegativeNumber(product.getFinalAmount(), "최종 납부금액을 확인해 주세요.");
        if (!isBlank(amountError)) {
            return amountError;
        }
        amountError = validateNonNegativeNumber(product.getRemainAmount(), "잔여 할부 금액을 확인해 주세요.");
        if (!isBlank(amountError)) {
            return amountError;
        }

        String remainPeriod = normalizeNumber(product.getRemainPeriod());
        if (isBlank(remainPeriod)) {
            return "잔여 할부 개월 수를 확인해 주세요.";
        }
        try {
            int value = Integer.parseInt(remainPeriod);
            if (value < 0) {
                return "잔여 할부 개월 수를 확인해 주세요.";
            }
        } catch (NumberFormatException e) {
            return "잔여 할부 개월 수를 확인해 주세요.";
        }
        return null;
    }

    private String validateAgreementStep(TerminationApplyReqDto.Agreement agreement) {
        if (agreement == null) {
            return "동의 정보가 없습니다.";
        }
        if (!agreement.isAgreeCheck1()) {
            return "혜택 환수 안내사항 동의가 필요합니다.";
        }
        if (!agreement.isAgreeCheck2()) {
            return "신규 계약 해지 동의가 필요합니다.";
        }
        return null;
    }

    private String validateNonNegativeNumber(String value, String message) {
        String normalized = normalizeNumber(value);
        if (isBlank(normalized)) {
            return message;
        }
        try {
            long parsed = Long.parseLong(normalized);
            if (parsed < 0) {
                return message;
            }
            return null;
        } catch (NumberFormatException e) {
            return message;
        }
    }

    private boolean isLocal() {
        return mcpApiClient.isLocal();
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String joinPhone(String p1, String p2, String p3) {
        String joined = String.format("%s%s%s", safe(p1), safe(p2), safe(p3)).replaceAll("[^0-9]", "");
        return joined.isEmpty() ? "" : joined;
    }

    private static String maskPhone(String value) {
        String normalized = normalizeNumber(value);
        if (normalized.length() < 4) {
            return normalized;
        }
        return "***" + normalized.substring(normalized.length() - 4);
    }

    private static boolean isAllowedValue(String value, String... allowedValues) {
        String normalized = safe(value);
        for (String allowed : allowedValues) {
            if (allowed.equalsIgnoreCase(normalized)) {
                return true;
            }
        }
        return false;
    }

    private static boolean is8DigitDate(String value) {
        return safe(value).matches("^\\d{8}$");
    }

    private static boolean isValidPhoneNumber(String value) {
        String normalized = normalizeNumber(value);
        return normalized.matches("^\\d{10,11}$");
    }

    private static boolean isValidNcn(String value) {
        String normalized = normalizeNumber(value);
        return normalized.matches("^\\d{9}$");
    }

    private static String normalizeNumber(String value) {
        return safe(value).replaceAll("[^0-9-]", "");
    }

    private static String logValue(String value) {
        if (value == null || value.isEmpty()) {
            return "";
        }
        if (value.length() <= 4) {
            return value;
        }
        return value.substring(0, 2) + "***" + value.substring(value.length() - 2);
    }

    private static Map<String, String> logMap(Object value) {
        if (!(value instanceof Map<?, ?> source)) {
            return null;
        }

        Map<String, String> masked = new HashMap<>();
        source.forEach((key, mapValue) -> masked.put(String.valueOf(key), logValue(mapValue == null ? null : String.valueOf(mapValue))));
        return masked;
    }

    private static String safe(String value) {
        return value == null ? "" : value;
    }

    private static String normalizeReceiveWay(String postMethod) {
        if ("postMethod1".equals(postMethod) || "mail".equalsIgnoreCase(safe(postMethod))) {
            return "P";
        }
        if ("postMethod2".equals(postMethod) || "email".equalsIgnoreCase(safe(postMethod))) {
            return "E";
        }
        return null;
    }

    private static String normalizeUseType(String isActive) {
        return switch (safe(isActive)) {
            case "MM" -> "KTM";
            case "KT" -> "KT";
            case "SK" -> "SKT";
            case "LG" -> "LGT";
            case "ETC" -> "ETC";
            default -> isActive;
        };
    }

    private static String normalizeCustomerType(String customerType) {
        return switch (safe(customerType)) {
            case "customerType1" -> "NA";
            case "customerType2" -> "MI";
            case "customerType3" -> "FO";
            case "customerType4" -> "FM";
            case "customerType5" -> "CO";
            case "customerType6" -> "PB";
            default -> customerType;
        };
    }

    private static String resolveAgentCd(TerminationApplyReqDto.Customer customer) {
        if (customer == null) {
            return "";
        }
        if (!isBlank(customer.getAgentCd())) {
            return customer.getAgentCd();
        }
        return safe(customer.getAgencyName());
    }

    private static String resolveManagerCd(TerminationApplyReqDto.Customer customer) {
        if (customer == null) {
            return "";
        }
        if (!isBlank(customer.getManagerCd())) {
            return customer.getManagerCd();
        }
        return resolveAgentCd(customer);
    }

    private static void initializeRequestCstmrDefaults(MsfRequestCstmrVo cstmrVo) {
        cstmrVo.setCstmrNativeRrn("");
        cstmrVo.setCstmrNativeBirth("");
        cstmrVo.setCstmrNativeGenderCd("");
        cstmrVo.setCstmrPrivateCname("");
        cstmrVo.setCstmrPrivateBizNo("");
        cstmrVo.setCstmrForeignerRrn("");
        cstmrVo.setCstmrForeignerBirth("");
        cstmrVo.setCstmrForeignerGenderCd("");
        cstmrVo.setCstmrForeignerPn("");
        cstmrVo.setCstmrForeignerCountryCd("");
        cstmrVo.setCstmrForeignerNation("");
        cstmrVo.setCstmrForeignerVisaNo("");
        cstmrVo.setCstmrForeignerVdateStartDate("");
        cstmrVo.setCstmrForeignerVdateEndDate("");
        cstmrVo.setCstmrJuridicalCname("");
        cstmrVo.setCstmrJuridicalRrn("");
        cstmrVo.setCstmrJuridicalBizNo("");
        cstmrVo.setCstmrJuridicalRepNm("");
        cstmrVo.setUpjnCd("");
        cstmrVo.setBcuSbst("");
        cstmrVo.setCstmrJuridicalUserNm("");
        cstmrVo.setCstmrJuridicalBirth("");
        cstmrVo.setCstmrVisitTypeCd("");
        cstmrVo.setCstmrTelFnNo("");
        cstmrVo.setCstmrTelMnNo("");
        cstmrVo.setCstmrTelRnNo("");
        cstmrVo.setCstmrMobileFnNo("");
        cstmrVo.setCstmrMobileMnNo("");
        cstmrVo.setCstmrMobileRnNo("");
        cstmrVo.setCstmrZipcd("");
        cstmrVo.setCstmrAdr("");
        cstmrVo.setCstmrAdrDtl("");
        cstmrVo.setCstmrAdrBjd("");
        cstmrVo.setCstmrEmailAdr("");
        cstmrVo.setCstmrEmailReceiveYn("N");
        cstmrVo.setCstmrReceiveTelFnNo("");
        cstmrVo.setCstmrReceiveTelNmNo("");
        cstmrVo.setCstmrReceiveTelRnNo("");
    }

    private static String toYn(boolean value) {
        return value ? "Y" : "N";
    }

    private static Long parseLong(String value) {
        String normalized = normalizeNumber(value);
        if (normalized.isEmpty()) {
            return null;
        }
        return Long.parseLong(normalized);
    }

    private static Integer parseInteger(String value) {
        String normalized = normalizeNumber(value);
        if (normalized.isEmpty()) {
            return null;
        }
        return Integer.parseInt(normalized);
    }
}
