package com.ktmmobile.msf.domains.form.form.termination.service;

import java.util.ArrayList;
import java.util.List;

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
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfChangPageSvc;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.TerminationSettlementDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelPageRepositoryImpl;


@Service
public class MsfCancelPageSvcImpl implements MsfCancelPageSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfCancelPageSvcImpl.class);

    @Autowired
    private CancelPageRepositoryImpl cancelPageRepository;

    @Autowired
    private MsfMplatFormService msfMplatFormService;

    /** requestView 위약금 블록(X54/X16/mspAddInfo) 조회용 마이올레 서비스 */
    @Autowired
    private MsfChangPageSvc msfChangPageSvc;

    @Autowired
    private McpApiClient mcpApiClient;

    @Override
    public RemainChargeResVO getRemainCharge(RemainChargeReqDto reqDto) {
        // AS-IS reference: getRealTimePriceAjax -> X18 real-time remain charge
        logger.debug("[getRemainCharge] start: ncn={}, ctn={}, custIdPresent={}",
            safe(reqDto.getNcn()), maskPhone(reqDto.getCtn()), !isBlank(reqDto.getCustId()));

        RemainChargeResVO resVO = new RemainChargeResVO();
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

            List<RemainChargeResVO.FareItem> items = new ArrayList<>();
            if (mpVO.getList() != null) {
                for (MpFarRealtimePayInfoVO.RealFareVO realFare : mpVO.getList()) {
                    if ("중단위약금".equals(realFare.getGubun())) {
                        continue;
                    }
                    RemainChargeResVO.FareItem item = new RemainChargeResVO.FareItem();
                    item.setGubun(realFare.getGubun());
                    item.setPayment(realFare.getPayment());
                    items.add(item);
                }
            }
            resVO.setItems(items);
            logger.info("[getRemainCharge] X18 success: ncn={}, itemCount={}, sumAmt={}",
                safe(reqDto.getNcn()), items.size(), safe(resVO.getSumAmt()));

            // 화면 응답 포함: AS-IS requestView 위약금 블록(X54/X16/mspAddInfo)
            // AS-IS(requestView 위약금 블록) 결과를 화면 응답(settlement)에 포함
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

    //TEST_SKIP 연동전 테스트용도
    private void applyMockAmountData(RemainChargeResVO resVO) {
        if (resVO == null) {
            return;
        }

        // Temporary mock data before external integrations are ready
        resVO.setSearchDay("20260416");
        resVO.setSearchTime("101500");
        resVO.setSumAmt("15870"); // usage fee

        List<RemainChargeResVO.FareItem> items = new ArrayList<>();
        RemainChargeResVO.FareItem usageFare = new RemainChargeResVO.FareItem();
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

    /** settlement DTO → RemainChargeResVO 단일 필드 세팅 */
    private void applySettlementFields(RemainChargeResVO resVO, TerminationSettlementDto settlement) {
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
    private TerminationSettlementDto getTerminationSettlement(RemainChargeReqDto reqDto) {
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
                    // [TOBE] McpApiClient.post() — use-mcp 정책·연결실패 시 MspApiDirectRepository(mcpSqlSession)로 자동 전환
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
        // [TOBE] McpApiClient.post() — use-mcp 정책·연결실패(TEST) MspApiDirectRepository(mcpSqlSession)로 자동 전환
        int cnt = mcpApiClient.post("/mypage/prePayment", contractNum, int.class);

        logger.debug("[isPrePayment] result: cnt={}", cnt);
        return cnt >= 1;
    }

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
        String agentCd = safe(reqDto.getCustomer().getAgentCd());
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
        String receiveWayCd = reqDto.getCustomer().getPostMethod();
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

            TerminationApplyReqDto dto = reqDto;
            dto.setRequestKey(requestKey);
            dto.setManagerCd(managerCd);
            dto.setAgentCd(agentCd);
            dto.setOperTypeCd("CC");
            dto.setCstmrTypeCd(cstmrTypeCd);
            dto.setCancelMobileNo(cancelMobileNo);
            dto.setContractNum(reqDto.getCustomer().getNcn());
            dto.setCstmrNm(reqDto.getCustomer().getUserName());
            dto.setReceiveMobileNo(receiveMobileNo);
            dto.setReceiveWayCd(receiveWayCd);
            dto.setCancelUseCompanyCd(normalizeUseType(reqDto.getProduct().getIsActive()));
            dto.setPayAmt(parseLong(reqDto.getProduct().getUsageFee()));
            dto.setPnltAmt(parseLong(reqDto.getProduct().getPenaltyFee()));
            dto.setLastSumAmt(parseLong(reqDto.getProduct().getFinalAmount()));
            dto.setInstamtMnthCnt(parseInteger(reqDto.getProduct().getRemainPeriod()));
            dto.setInstamtMnthAmt(parseLong(reqDto.getProduct().getRemainAmount()));
            dto.setBenefitAgreeYn(toYn(reqDto.getAgreement().isAgreeCheck1()));
            dto.setClauseCntrDelYn(toYn(reqDto.getAgreement().isAgreeCheck2()));
            dto.setEtcAgreeYn(toYn(reqDto.getAgreement().isAgreeCheck3()));
            dto.setMemo(reqDto.getProduct().getMemo());
            dto.setRecYn("N");
            dto.setAppFormYn("N");
            dto.setAppFormXmlYn("N");
            dto.setRegstId("MSF_FORM");
            dto.setProcCd("RC");
            dto.setCretId("MSF_FORM");
            dto.setAmdId("MSF_FORM");
            dto.setCretIp("127.0.0.1");
            dto.setAmdIp("127.0.0.1");

            logger.debug("[apply] insert payload ready: requestKey={}, ncn={}, customerTypeCd={}, receiveWayCd={}, cancelUseCompanyCd={}, payAmt={}, pnltAmt={}, lastSumAmt={}",
                requestKey, safe(dto.getContractNum()), safe(dto.getCstmrTypeCd()), safe(dto.getReceiveWayCd()),
                safe(dto.getCancelUseCompanyCd()), dto.getPayAmt(), dto.getPnltAmt(), dto.getLastSumAmt());

            int inserted = cancelPageRepository.insertRequestCancel(dto);
            if (inserted <= 0) {
                logger.error("[apply] insert failed: requestKey={}, ncn={}, inserted={}", requestKey, safe(dto.getContractNum()), inserted);
                return TerminationApplyResVO.fail("서비스해지 요청 저장에 실패했습니다.");
            }

            logger.info("[apply] success: requestKey={}, ncn={}", requestKey, safe(dto.getContractNum()));
            return TerminationApplyResVO.ok(String.valueOf(requestKey));
        } catch (Exception e) {
            logger.error("[apply] exception: ncn={}", reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "", e);
            return TerminationApplyResVO.fail("서비스해지 요청 처리 중 오류가 발생했습니다.");
        }
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
