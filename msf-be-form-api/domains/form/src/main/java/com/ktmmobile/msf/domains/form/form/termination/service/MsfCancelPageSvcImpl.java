package com.ktmmobile.msf.domains.form.form.termination.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.form.common.code.ResTermMessage;
import com.ktmmobile.msf.domains.form.common.code.ResponseMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpFarMonBillingInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpFarMonDetailInfoDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MpMonthPayMentDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscSpnsrItgInfoInVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.service.FormCommService;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCancelVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestClauseVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMstVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoRequest;
import com.ktmmobile.msf.domains.form.form.newchange.dto.AgentInfoResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfSvcChgPageServiceImpl;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationApplyResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationRemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.TerminationSettlementDto;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelPageRepositoryImpl;


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

    /**
     * 위약금/잔여할부 조회에 필요한 서비스변경 공통 조회 기능을 사용한다.
     */
    @Autowired
    private MsfSvcChgPageServiceImpl msfSvcChgPageService;

    @Autowired
    private McpApiClient mcpApiClient;

    @Autowired
    private FormCommService formCommService;

    @Autowired
    private CommonCodeReader commonCodeReader;

    @Autowired
    private TransactionTemplate transactionTemplate;

    @Autowired
    @Qualifier(MspDataSourceConfig.MSP_TX_MANAGER)
    private PlatformTransactionManager mspTransactionManager;

    @Override
    public List<AgentInfoResponse> getTerminationAgentInfo(AgentInfoRequest request) {
        logger.info("[해지] 대리점 정보 조회 요청 — shopOrgnId={}", request.getShopOrgnId());
        List<AgentInfoResponse> result = formCommService.getAgentList(request);
        logger.info("[해지] 대리점 정보 조회 완료 - shopOrgnId={}, count={}", request.getShopOrgnId(), result != null ? result.size() : 0);
        return result;
    }


    /**
     * 계약번호(ncn)로 회선 정보를 보강한 뒤 X18 잔여요금과 위약금/잔여할부 정보를 조회한다.
     * 외부 연동 완료 전까지 화면 테스트용 mock 금액 보정은 유지한다.
     */
    @Override
    public FormResponse<TerminationRemainChargeResVO> getRemainCharge(TerminationRemainChargeReqDto reqDto) {
        logger.debug("[getRemainCharge] selectCntrListNoLogin: ncn={}", safe(reqDto.getNcn()));
        McpUserCntrMngDto cntrInfo = msfSvcChgPageService.selectCntrListNoLogin(reqDto.getNcn());
        if (cntrInfo == null) {
            return FormResponse.of(ResTermMessage.REMAIN_CONTRACT_NOT_FOUND);
        }
        reqDto.setCtn(cntrInfo.getCntrMobileNo());
        reqDto.setCustId(cntrInfo.getCustId());

        // AS-IS getRealTimePriceAjax와 동일한 X18 실시간 잔여요금 조회 흐름이다.
        logger.debug("[getRemainCharge] start: ncn={}, ctn={}, custIdPresent={}",
            safe(reqDto.getNcn()), maskPhone(reqDto.getCtn()), !isBlank(reqDto.getCustId()));

        TerminationRemainChargeResVO resVO = new TerminationRemainChargeResVO();
        try {
            MpFarRealtimePayInfoVO mpVO = msfMplatFormService.farRealtimePayInfo(
                reqDto.getNcn(), reqDto.getCtn(), reqDto.getCustId());

            if (mpVO == null) {
                return FormResponse.of(ResTermMessage.REMAIN_API_EMPTY);
            }

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
            applyMockAmountData(resVO);
            return FormResponse.of(ResTermMessage.REMAIN_SELF_SERVICE_ERROR, resVO);
        } catch (java.net.SocketTimeoutException e) {
            logger.info("X18 ERROR (Timeout): ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            return FormResponse.of(ResTermMessage.REMAIN_TIMEOUT);
        } catch (Exception e) {
            logger.error("X18 잔여요금 조회 오류: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn(), e);
            return FormResponse.of(ResTermMessage.REMAIN_ERROR);
        }
        applyMockAmountData(resVO);
        return FormResponse.of(ResTermMessage.SUCCESS, resVO);
    }

    @Override
    public FormResponse<Void> checkInProgressApplication(String requestMobileNo) {
        String mobileNo = normalizeDigits(requestMobileNo);
        logger.debug("[checkInProgressApplication] start: mobileNo={}", maskPhone(mobileNo));

        if (isBlank(mobileNo)) {
            logger.warn("[checkInProgressApplication] fail: mobileNo is blank");
            return FormResponse.of(ResTermMessage.APPLY_CANCEL_PHONE_REQUIRED);
        }
        if (!isValidPhoneNumber(mobileNo)) {
            logger.warn("[checkInProgressApplication] fail: invalid mobileNo={}", maskPhone(mobileNo));
            return FormResponse.of(ResTermMessage.APPLY_CANCEL_PHONE_REQUIRED);
        }
        if (cancelPageRepository.existsInProgressApplicationByMobileNo(mobileNo)) {
            logger.warn("[checkInProgressApplication] fail: in-progress application exists, mobileNo={}", maskPhone(mobileNo));
            //현재 진행중인 신청서가 있어 신청할 수 없습니다.
            return FormResponse.of(ResTermMessage.APPLY_IN_PROGRESS_EXISTS);
        }
        return FormResponse.of(ResTermMessage.SUCCESS);
    }

    // TEST_SKIP: 외부 금액 연동 완료 전까지 화면 테스트용 금액을 강제로 내려준다.
    private void applyMockAmountData(TerminationRemainChargeResVO resVO) {
        if (resVO == null) {
            return;
        }

        // 외부 연동 완료 전까지 임시 mock 데이터를 반환한다.
        resVO.setSearchDay("20260416");
        resVO.setSearchTime("101500");
        resVO.setSumAmt("15870"); // 사용요금

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
        settlement.setTrmnForecBprmsAmt("42000"); // 위약금
        settlement.setRtrnAmtAndChageDcAmt("57870"); // 최종 정산요금
        settlement.setInstallmentAmt("180000"); // 잔여할부금
        settlement.setRemainPay(180000);
        settlement.setRemainMonth(6);

        applySettlementFields(resVO, settlement);
    }

    /**
     * 위약금/잔여할부 조회 결과를 화면 응답 필드에 맞춰 반영한다.
     */
    private void applySettlementFields(TerminationRemainChargeResVO resVO, TerminationSettlementDto settlement) {
        if (resVO == null || settlement == null) {
            return;
        }
        resVO.setPenaltyFee(settlement.getTrmnForecBprmsAmt()); // 위약금
        resVO.setSettlementFee(settlement.getRtrnAmtAndChageDcAmt()); // 최종 정산요금
        resVO.setRemainPeriod(String.valueOf(settlement.getRemainMonth())); // 잔여할부 기간(개월)
        resVO.setRemainAmount(String.valueOf(settlement.getRemainPay())); // 잔여할부 금액
    }

    /**
     * AS-IS MyOllehController requestView의 위약금 블록을 분리한 함수다.
     * SRM18062741675 기준으로 X54/X16/mspAddInfo 조회 흐름을 유지한다.
     */
    private TerminationSettlementDto getTerminationSettlement(TerminationRemainChargeReqDto reqDto) {
        // SRM18062741675 / AS-IS MyOllehController requestView 위약금 블록 이관
        // 조회 순서: 선불 여부 확인 -> X54 -> (saleEngtNm 존재 시) X16 + mspAddInfo
        if (reqDto == null) {
            return null;
        }
        TerminationSettlementDto settlement = new TerminationSettlementDto();
        try {
            String ncn = reqDto.getNcn();
            String ctn = reqDto.getCtn();
            String custId = reqDto.getCustId();

            // 1) 선불요금제 사용 여부 확인
            boolean prePayment = isPrePayment(ncn);
            settlement.setPrePayment(prePayment);
            if (prePayment) {
                return settlement;
            }

            // 2) 스폰서 약정 정보 조회(X54)
            MpMoscSpnsrItgInfoInVO moscSpnsrItgInfo = msfMplatFormService.kosMoscSpnsrItgInfo(ncn, ctn, custId);
            if (moscSpnsrItgInfo == null) {
                return settlement;
            }

            // AS-IS와 동일하게 null 값을 "0"으로 보정
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmt())) moscSpnsrItgInfo.setChageDcAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getTrmnForecBprmsAmt())) moscSpnsrItgInfo.setTrmnForecBprmsAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getRtrnAmtAndChageDcAmt()))
                moscSpnsrItgInfo.setRtrnAmtAndChageDcAmt("0");
            if (StringUtil.isEmpty(moscSpnsrItgInfo.getChageDcAmtSuprtRtrnAmt()))
                moscSpnsrItgInfo.setChageDcAmtSuprtRtrnAmt("0");

            // X54 응답값을 settlement DTO에 매핑
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

            // 3) saleEngtNm 존재 시 잔여 할부금(X16), 잔여할부 기간(mspAddInfo) 조회
            // TEST_SKIP: 외부 연동 완료 전까지 saleEngtNm 조건을 우회한다.
            try {
                // X16 조회에 필요한 최신 청구정보 조회(X15)
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
                // 잔여할부 기간 조회(MSP_JUO_ADD_INFO)
                // [AS-IS] MyOllehController.requestView()에서는 mcp-api REST를 직접 호출했다.
                //   RestTemplate restTemplate = new RestTemplate();
                //   mspJuoAddInfoDto = restTemplate.postForObject(apiInterfaceServer + "/mypage/mspAddInfo", searchVO.getNcn(), MspJuoAddInfoDto.class);
                // [TOBE] McpApiClient.post()에서 use-mcp 정책/연결 실패 시 MspApiDirectRepository(mspSqlSession)로 자동 전환한다.
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
        } catch (Exception e) {
            logger.warn("[getTerminationSettlement] error: ncn={}, ctn={}",
                safe(reqDto.getNcn()), safe(reqDto.getCtn()), e);
        }
        return settlement;
    }

    /**
     * 선불요금제 사용 여부를 조회한다.
     *
     * @param contractNum 계약번호
     * @return 선불요금제 여부
     */
    private boolean isPrePayment(String contractNum) {
        logger.debug("[isPrePayment] call: contractNum={}", safe(contractNum));

        // [AS-IS] MypageController.prePayment()은 mcp-api REST를 직접 호출해 MSP DB를 조회했다.
        //   POST /mypage/prePayment -> mypageMapper.selectPrePayment(contractNum)
        //   RestTemplate restTemplate = new RestTemplate();
        //   int cnt = restTemplate.postForObject(apiInterfaceServer + "/mypage/prePayment", contractNum, int.class);
        // [TOBE] McpApiClient.post()에서 use-mcp 정책/연결 실패(TEST) 시 MspApiDirectRepository(mspSqlSession)로 자동 전환한다.
        int cnt = mcpApiClient.post("/mypage/prePayment", contractNum, int.class);

        logger.debug("[isPrePayment] result: cnt={}", cnt);
        return cnt >= 1;
    }

    /**
     * 작성완료 요청 처리 시간과 결과를 로깅하고 실제 저장 처리는 apply에 위임한다.
    */
    @Override
    public FormResponse<TerminationApplyResVO> complete(String applicationKey, TerminationApplyReqDto reqDto) {
        long startedAt = System.currentTimeMillis();
        String ncn = reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "";

        logger.info("서비스해지 작성완료 요청: applicationKey={}, ncn={}", safe(applicationKey), ncn);

        FormResponse<TerminationApplyResVO> res = apply(reqDto);
        long elapsed = System.currentTimeMillis() - startedAt;

        if (res != null && ResponseMessage.SUCCESS.getCode().equals(res.resCode())) {
            logger.info("서비스해지 작성완료 결과: applicationKey={}, ncn={}, success={}, applicationNo={}, elapsedMs={}",
                safe(applicationKey), ncn, true, res.resData() != null ? res.resData().getApplicationNo() : "", elapsed);
        } else {
            String resCode = res != null ? res.resCode() : "";
            String resMessage = res != null ? res.resMessage() : "null response";
            logger.warn("서비스해지 작성완료 실패: applicationKey={}, ncn={}, success={}, resCode={}, resMessage={}, elapsedMs={}",
                safe(applicationKey), ncn, false, resCode, resMessage, elapsed);
        }

        return res;
    }

    /**
     * 서비스해지 신청 데이터를 smartform(MSF) 테이블에 먼저 저장하고,
     * 저장된 데이터를 다시 조회해 MCP DB link 테이블로 이관한다.
     */
    @Override
    public FormResponse<TerminationApplyResVO> apply(TerminationApplyReqDto reqDto) {
        logger.info("[apply] start: ncn={}, customerType={}, postMethod={}, cancelUseCompanyCd={}",
            reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "",
            reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getCustomerType()) : "",
            reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getPostMethod()) : "",
            reqDto != null && reqDto.getProduct() != null ? safe(reqDto.getProduct().getCancelUseCompanyCd()) : "");

        ValidationResult validationResult = validateApplyRequest(reqDto);
        if (validationResult != null) {
            logger.warn("[apply] validation failed: ncn={}, resCode={}, reason={}",
                reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "",
                validationResult.resCode(),
                validationResult.resMessage());
            return FormResponse.of(validationResult.responseMessage(), validationResult.resMessage(), null);
        }

        try {
            // 화면 입력값을 저장 컨텍스트로 정규화한다.
            prepareSaveRequest(reqDto);

            // MSF 저장은 smartform 트랜잭션에서만 처리한다.
            Long requestKey = transactionTemplate.execute(status -> saveMsfApplication(reqDto));

            // MCP 이관은 MSF 커밋 이후 별도 MSP 트랜잭션으로 처리한다.
            FormResponse<Void> transferResponse = transferToMcp(requestKey);
            if (!ResTermMessage.SUCCESS.getCode().equals(transferResponse.resCode())) {
                return new FormResponse<>(transferResponse.resCode(), transferResponse.resMessage(), null);
            }
            return FormResponse.of(ResTermMessage.SUCCESS, TerminationApplyResVO.ok(String.valueOf(requestKey)));
        } catch (ApplyFailureException e) {
            return FormResponse.of(e.responseMessage);
        } catch (Exception e) {
            logger.error("[apply] exception: ncn={}", reqDto != null && reqDto.getCustomer() != null ? safe(reqDto.getCustomer().getNcn()) : "", e);
            return FormResponse.of(ResTermMessage.APPLY_ERROR);
        }
    }

    private void prepareSaveRequest(TerminationApplyReqDto reqDto) {
        TerminationApplyReqDto.Customer customer = reqDto.getCustomer();
        String ncn = safe(customer.getNcn());

        requireText(customer.getAgentCd(), ResTermMessage.APPLY_AGENT_REQUIRED, "agentCd", ncn);
        requireText(customer.getManagerCd(), ResTermMessage.APPLY_MANAGER_REQUIRED, "managerCd", ncn);
        String cstmrTypeCd = requireText(reqDto.getCstmrTypeCd(), ResTermMessage.APPLY_CUSTOMER_TYPE_REQUIRED, "cstmrTypeCd", ncn);
        String receiveWayCd = requireText(reqDto.getReceiveWayCd(), ResTermMessage.APPLY_POST_METHOD_REQUIRED, "receiveWayCd", ncn);
        String cancelMobileNo = normalizeDigits(reqDto.getCancelMobileNo());
        String receiveMobileNo = normalizeDigits(reqDto.getReceiveMobileNo());
        requireText(cancelMobileNo, ResTermMessage.APPLY_CANCEL_PHONE_REQUIRED, "cancelMobileNo", ncn);
        requireText(receiveMobileNo, ResTermMessage.APPLY_RECEIVE_PHONE_REQUIRED, "receiveMobileNo", ncn);

        logger.debug("[apply] contact normalized: ncn={}, cancelMobileNo={}, receiveMobileNo={}",
            ncn, maskPhone(cancelMobileNo), maskPhone(receiveMobileNo));

        if (cancelPageRepository.existsInProgressApplicationByMobileNo(cancelMobileNo)) {
            logger.warn("[apply] fail: in-progress application exists, ncn={}, cancelMobileNo={}", ncn, maskPhone(cancelMobileNo));
            //TEST_SKIP 현재 진행중인 신청서가 있어 신청할 수 없습니다.
            //TEST_SKIP throw new ApplyFailureException(ResTermMessage.APPLY_IN_PROGRESS_EXISTS);
        }

        reqDto.setCstmrTypeCd(cstmrTypeCd);
        reqDto.setReceiveWayCd(receiveWayCd);
        reqDto.setCancelMobileNo(cancelMobileNo);
        reqDto.setReceiveMobileNo(receiveMobileNo);
    }

    private Long saveMsfApplication(TerminationApplyReqDto reqDto) {
        // MSF 원장 테이블 저장만 담당한다.
        String cstmrTypeCd = reqDto.getCstmrTypeCd();
        String cancelMobileNo = reqDto.getCancelMobileNo();
        String receiveMobileNo = reqDto.getReceiveMobileNo();

        Long requestKey = cancelPageRepository.nextRequestKey();
        if (requestKey == null) {
            logger.error("[apply] request key generation failed: ncn={}", safe(reqDto.getCustomer().getNcn()));
            throw new ApplyFailureException(ResTermMessage.APPLY_REQUEST_KEY_FAILED);
        }
        logger.debug("[apply] request key generated: requestKey={}, ncn={}", requestKey, safe(reqDto.getCustomer().getNcn()));

        MsfRequestCancelVo vo = toMsfRequestCancelVo(requestKey, reqDto, cstmrTypeCd, reqDto.getReceiveWayCd(), cancelMobileNo);
        logCancelSavePayload(requestKey, vo);

        insertMsfCancel(vo);
        insertMsfCustomer(toMsfRequestCstmrVo(requestKey, reqDto));
        insertMsfAgentIfPresent(toMsfRequestAgentVo(requestKey, reqDto, cstmrTypeCd));
        insertMsfMaster(toMsfRequestMstVo(requestKey, reqDto, cancelMobileNo, receiveMobileNo, cstmrTypeCd));
        insertMsfClauses(requestKey, toMsfRequestClauseVos(requestKey, reqDto));

        logger.info("[apply] success: requestKey={}, ncn={}", requestKey, safe(vo.getContractNum()));
        return requestKey;
    }

    private void logCancelSavePayload(Long requestKey, MsfRequestCancelVo vo) {
        logger.debug("[apply] insert payload ready: requestKey={}, ncn={}, customerTypeCd={}, receiveWayCd={}, cancelUseCompanyCd={}, payAmt={}, pnltAmt={}, lastSumAmt={}",
            requestKey, safe(vo.getContractNum()), safe(vo.getCstmrTypeCd()), safe(vo.getReceiveWayCd()),
            safe(vo.getCancelUseCompanyCd()), vo.getPayAmt(), vo.getPnltAmt(), vo.getLastSumAmt());
    }

    private void insertMsfCancel(MsfRequestCancelVo vo) {
        requireInserted(
            msfRequestRepository.insertMsfRequestCancel(vo),
            ResTermMessage.APPLY_MSF_SAVE_FAILED,
            "insert cancel",
            vo.getRequestKey()
        );
    }

    private void insertMsfCustomer(MsfRequestCstmrVo vo) {
        requireInserted(
            msfRequestRepository.insertMsfRequestCstmr(vo),
            ResTermMessage.APPLY_MSF_SAVE_FAILED,
            "insert customer",
            vo.getRequestKey()
        );
    }

    private void insertMsfAgentIfPresent(MsfRequestAgentVo vo) {
        if (!hasAgentData(vo)) {
            return;
        }
        requireInserted(
            msfRequestRepository.insertMsfRequestAgent(vo),
            ResTermMessage.APPLY_MSF_SAVE_FAILED,
            "insert agent",
            vo.getRequestKey()
        );
    }

    private void insertMsfMaster(MsfRequestMstVo vo) {
        requireInserted(
            msfRequestRepository.insertMsfRequestMst(vo),
            ResTermMessage.APPLY_MSF_SAVE_FAILED,
            "insert request mst",
            vo.getRequestKey()
        );
    }

    private void insertMsfClauses(Long requestKey, List<MsfRequestClauseVo> clauseVos) {
        for (MsfRequestClauseVo clauseVo : clauseVos) {
            requireInserted(
                msfRequestRepository.insertMsfRequestClause(clauseVo),
                ResTermMessage.APPLY_MSF_SAVE_FAILED,
                "insert clause:" + safe(clauseVo.getCdGroupId()),
                requestKey
            );
        }
    }

    /**
     * MCP 이관은 MSF 저장 트랜잭션과 분리해서 단독 실행 가능하게 둔다.
     */
    @Override
    public FormResponse<Void> transferToMcp(Long requestKey) {
        if (requestKey == null) {
            return FormResponse.of(ResTermMessage.ADMIN_REQUEST_KEY_REQUIRED);
        }
        try {
            // requestKey 기준으로 MCP 이관만 재실행할 수 있다.
            new TransactionTemplate(mspTransactionManager).execute(status -> {
                transferToMcpInTransaction(requestKey);
                return null;
            });
            return FormResponse.of(ResTermMessage.SUCCESS);
        } catch (ApplyFailureException e) {
            return FormResponse.of(e.responseMessage);
        } catch (Exception e) {
            logger.error("[transferToMcp] exception: requestKey={}", requestKey, e);
            return FormResponse.of(ResTermMessage.APPLY_MCP_SAVE_FAILED);
        }
    }

    private void transferToMcpInTransaction(Long requestKey) {
        requireInserted(
            mcpRequestRepository.insertMcpRequestCstmr(requestKey),
            ResTermMessage.APPLY_MCP_CUSTOMER_SAVE_FAILED,
            "transfer customer to MCP",
            requestKey
        );

        requireInserted(
            mcpRequestRepository.insertMcpCancelRequest(requestKey),
            ResTermMessage.APPLY_MCP_SAVE_FAILED,
            "transfer cancel to MCP",
            requestKey
        );
    }

    /**
     * 화면 입력 DTO를 서비스해지 신청 VO로 변환한다.
     */
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
        vo.setIdentityCertTypeCd(customer.getIdentityCertTypeCd());
        vo.setIdentityTypeCd(safe(customer.getIdentityTypeCd()));
        vo.setIdentityIssuDate(normalizeDigits(customer.getIdentityIssuDate()));
        vo.setIdentityIssuRegion(customer.getIdentityIssuRegion());
        vo.setSelfIssuNo(customer.getSelfIssuNo());
        vo.setDriveLicnsNo(customer.getDriveLicnsNo());
        vo.setCancelMobileNo(cancelMobileNo);
        vo.setContractNum(customer.getNcn());
        vo.setReceiveWayCd(receiveWayCd);
        vo.setCancelUseCompanyCd(safe(product.getCancelUseCompanyCd()));
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
        return vo;
    }

    /**
     * 화면 고객 정보를 신청 고객 VO로 변환한다.
     */
    private MsfRequestCstmrVo toMsfRequestCstmrVo(
        Long requestKey,
        TerminationApplyReqDto reqDto
    ) {
        TerminationApplyReqDto.Customer customer = reqDto.getCustomer();

        MsfRequestCstmrVo cstmrVo = new MsfRequestCstmrVo();
        initializeRequestCstmrDefaults(cstmrVo);
        cstmrVo.setRequestKey(requestKey);
        cstmrVo.setCstmrNm(safe(customer.getUserName()));
        cstmrVo.setCstmrNativeRrn(firstNonBlank(customer.getCstmrNativeRrn(), joinParts(customer.getCstmrNativeRrn1(), customer.getCstmrNativeRrn2())));
        cstmrVo.setCstmrNativeBirth(safe(customer.getUserBirthDate()));
        cstmrVo.setCstmrNativeGenderCd(safe(customer.getUserGender()));
        cstmrVo.setCstmrPrivateCname(safe(customer.getCstmrPrivateCname()));
        cstmrVo.setCstmrPrivateBizNo(firstNonBlank(customer.getCstmrPrivateBizNo(), joinParts(
            customer.getCstmrPrivateBizNo1(),
            customer.getCstmrPrivateBizNo2(),
            customer.getCstmrPrivateBizNo3()
        )));
        cstmrVo.setCstmrForeignerRrn(firstNonBlank(customer.getCstmrForeignerRrn(), joinParts(customer.getCstmrForeignerRrn1(), customer.getCstmrForeignerRrn2())));
        cstmrVo.setCstmrForeignerBirth(safe(customer.getUserBirthDate()));
        cstmrVo.setCstmrForeignerGenderCd(safe(customer.getUserGender()));
        cstmrVo.setCstmrForeignerPn(safe(customer.getCstmrForeignerPn()));
        cstmrVo.setCstmrForeignerCountryCd(safe(customer.getCstmrForeignerCountryCd()));
        cstmrVo.setCstmrForeignerNation(safe(customer.getCstmrForeignerNation()));
        cstmrVo.setCstmrForeignerVisaNo(safe(customer.getCstmrForeignerVisaNo()));
        cstmrVo.setCstmrForeignerVdateStartDate(safe(customer.getCstmrForeignerVdateStartDate()));
        cstmrVo.setCstmrForeignerVdateEndDate(safe(customer.getCstmrForeignerVdateEndDate()));
        cstmrVo.setCstmrJuridicalCname(safe(customer.getUserName()));
        cstmrVo.setCstmrJuridicalRrn(firstNonBlank(customer.getCstmrJuridicalRrn(), joinParts(customer.getCstmrJuridicalRrn1(), customer.getCstmrJuridicalRrn2())));
        cstmrVo.setCstmrJuridicalBizNo(firstNonBlank(customer.getCstmrJuridicalBizNo(), joinParts(
            customer.getCstmrJuridicalBizNo1(),
            customer.getCstmrJuridicalBizNo2(),
            customer.getCstmrJuridicalBizNo3()
        )));
        cstmrVo.setCstmrJuridicalRepNm(safe(customer.getCstmrJuridicalRepNm()));
        cstmrVo.setUpjnCd(safe(customer.getUpjnCd()));
        cstmrVo.setBcuSbst(safe(customer.getBcuSbst()));
        cstmrVo.setCstmrJuridicalUserNm(safe(customer.getCstmrJuridicalUserNm()));
        cstmrVo.setCstmrJuridicalBirth(safe(customer.getCstmrJuridicalBirth()));
        cstmrVo.setCstmrVisitTypeCd(safe(customer.getCstmrVisitTypeCd()));
        cstmrVo.setCstmrTelFnNo(safe(customer.getCstmrTelFnNo()));
        cstmrVo.setCstmrTelMnNo(safe(customer.getCstmrTelMnNo()));
        cstmrVo.setCstmrTelRnNo(safe(customer.getCstmrTelRnNo()));
        cstmrVo.setCstmrMobileFnNo(safe(customer.getCancelPhone1()));
        cstmrVo.setCstmrMobileMnNo(safe(customer.getCancelPhone2()));
        cstmrVo.setCstmrMobileRnNo(safe(customer.getCancelPhone3()));
        cstmrVo.setCstmrZipcd(safe(customer.getCstmrZipcd()));
        cstmrVo.setCstmrAdr(safe(customer.getCstmrAdr()));
        cstmrVo.setCstmrAdrDtl(safe(customer.getCstmrAdrDtl()));
        cstmrVo.setCstmrAdrBjd(safe(customer.getCstmrAdrBjd()));
        cstmrVo.setCstmrEmailAdr(safe(customer.getCstmrEmailAdr()));
        cstmrVo.setCstmrEmailReceiveYn(firstNonBlank(customer.getCstmrEmailReceiveYn(), "N"));
        cstmrVo.setCstmrReceiveTelFnNo(safe(customer.getAfterTel1()));
        cstmrVo.setCstmrReceiveTelNmNo(safe(customer.getAfterTel2()));
        cstmrVo.setCstmrReceiveTelRnNo(safe(customer.getAfterTel3()));
        return cstmrVo;
    }

    private MsfRequestAgentVo toMsfRequestAgentVo(
        Long requestKey,
        TerminationApplyReqDto reqDto,
        String cstmrTypeCd
    ) {
        TerminationApplyReqDto.Customer customer = reqDto.getCustomer();
        MsfRequestAgentVo agentVo = new MsfRequestAgentVo();
        agentVo.setRequestKey(requestKey);
        agentVo.setMinorAgentSelfInqryAgrmYn("N");

        if (isMinorCustomerType(cstmrTypeCd)) {
            agentVo.setMinorAgentNm(firstNonBlank(customer.getRepName(), customer.getMinorAgentNm()));
            agentVo.setMinorAgentRrn(joinParts(customer.getRepRegistrationNo1(), customer.getRepRegistrationNo2()));
            agentVo.setMinorAgentBirth(safe(customer.getRepBirthDate()));
            agentVo.setMinorAgentGenderCd(safe(customer.getAgentGender()));
            agentVo.setMinorAgentRelTypeCd(safe(customer.getMinorAgentRelTypeCd()));
            agentVo.setMinorAgentTelFnNo(safe(customer.getMinorAgentTelFnNo()));
            agentVo.setMinorAgentTelMnNo(safe(customer.getMinorAgentTelMnNo()));
            agentVo.setMinorAgentTelRnNo(safe(customer.getMinorAgentTelRnNo()));
            agentVo.setMinorAgentAgrmYn(toYn(customer.isRepAgree()));
        }

        if ("V2".equals(safe(customer.getCstmrVisitTypeCd()))) {
            agentVo.setMinorAgentAgrmYn("N");
            agentVo.setJrdclAgentNm(safe(customer.getMinorAgentNm()));
            agentVo.setJrdclAgentRelTypeCd(safe(customer.getMinorAgentRelTypeCd()));
            agentVo.setJrdclAgentTelFnNo(safe(customer.getMinorAgentTelFnNo()));
            agentVo.setJrdclAgentTelMnNo(safe(customer.getMinorAgentTelMnNo()));
            agentVo.setJrdclAgentTelRnNo(safe(customer.getMinorAgentTelRnNo()));
        }

        return agentVo;
    }

    private MsfRequestMstVo toMsfRequestMstVo(
        Long requestKey,
        TerminationApplyReqDto reqDto,
        String cancelMobileNo,
        String receiveMobileNo,
        String cstmrTypeCd
    ) {
        TerminationApplyReqDto.Customer customer = reqDto.getCustomer();
        MsfRequestMstVo mstVo = new MsfRequestMstVo();
        mstVo.setRequestKey(requestKey);
        mstVo.setCretIp("127.0.0.1");
        mstVo.setCretId("MSF_FORM");
        mstVo.setReqTypeCd("CC");
        mstVo.setUserId(firstNonBlank(customer.getManagerCd(), "MSF_FORM"));
        mstVo.setCstmrNm(safe(customer.getUserName()));
        mstVo.setMobileNo(cancelMobileNo);
        mstVo.setContractNum(safe(customer.getNcn()));
        mstVo.setCstmrTypeCd(cstmrTypeCd);
        mstVo.setOnlineAuthTypeCd(safe(customer.getIdentityCertTypeCd()));
        mstVo.setOnlineAuthInfo("MSF:" + requestKey);
        mstVo.setEtcMobileNo(receiveMobileNo);
        return mstVo;
    }

    private List<MsfRequestClauseVo> toMsfRequestClauseVos(Long requestKey, TerminationApplyReqDto reqDto) {
        List<MsfRequestClauseVo> clauseVos = new ArrayList<>();
        TerminationApplyReqDto.Agreement agreement = reqDto.getAgreement();
        if (agreement == null || agreement.getClauses() == null) {
            return clauseVos;
        }

        for (TerminationApplyReqDto.Clause clause : agreement.getClauses()) {
            if (clause == null || isBlank(clause.getCode()) || !isChecked(clause.getChecked())) {
                continue;
            }
            MsfRequestClauseVo clauseVo = new MsfRequestClauseVo();
            clauseVo.setRequestKey(requestKey);
            clauseVo.setCdGroupId(resolveClauseGroupId(clause));
            clauseVo.setCdGroupId2(resolveClauseGroupId2(clause));
            clauseVo.setVersion(safe(clause.getVersion()));
            clauseVos.add(clauseVo);
        }
        return clauseVos;
    }

    private static String resolveClauseGroupId(TerminationApplyReqDto.Clause clause) {
        return firstNonBlank(clause.getTermsGroupCd(), clause.getCdGroupId(), "CLAUSE_FORM_04");
    }

    private static String resolveClauseGroupId2(TerminationApplyReqDto.Clause clause) {
        String termsItemCd = firstNonBlank(clause.getTermsItemCd(), clause.getCdGroupId2());
        if (!isBlank(termsItemCd)) {
            return termsItemCd;
        }
        return switch (safe(clause.getCode())) {
            case "CLAUSE_INFO_09" -> "09";
            case "CLAUSE_CNTR_DEL_01" -> "cntrDelFlag";
            case "CLAUSE_INFO_08" -> "08";
            default -> safe(clause.getCode());
        };
    }

    private ValidationResult validateApplyRequest(TerminationApplyReqDto reqDto) {
        if (reqDto == null) {
            logger.debug("[validateApplyRequest] reqDto is null");
            return ValidationResult.error(ResTermMessage.APPLY_REQUEST_INVALID, "요청 정보가 없습니다.");
        }
        String customerStepError = validateCustomerStep(reqDto.getCustomer());
        if (!isBlank(customerStepError)) {
            logger.debug("[validateApplyRequest] customer step invalid: {}", customerStepError);
            return ValidationResult.error(ResTermMessage.APPLY_CUSTOMER_INVALID, customerStepError);
        }
        String productStepError = validateProductStep(reqDto.getProduct());
        if (!isBlank(productStepError)) {
            logger.debug("[validateApplyRequest] product step invalid: {}", productStepError);
            return ValidationResult.error(ResTermMessage.APPLY_PRODUCT_INVALID, productStepError);
        }
        String agreementStepError = validateAgreementStep(reqDto.getAgreement());
        if (!isBlank(agreementStepError)) {
            logger.debug("[validateApplyRequest] agreement step invalid: {}", agreementStepError);
            return ValidationResult.error(ResTermMessage.APPLY_AGREEMENT_INVALID, agreementStepError);
        }
        return null;
    }

    private String validateCustomerStep(TerminationApplyReqDto.Customer customer) {
        if (customer == null) {
            return "고객 정보가 없습니다.";
        }
        if (isBlank(customer.getCustomerType())) {
            return "고객 유형을 선택해 주세요.";
        }
        if (!isCommonCodeValue("CSTMR_TYPE_CD", customer.getCustomerType())) {
            return "고객 유형이 올바르지 않습니다.";
        }
        if (!isCommonCodeValue("IDENTITY_CERT_TYPE_CD", customer.getIdentityCertTypeCd())) {
            return "신분증 인증유형이 올바르지 않습니다.";
        }
        if (!isIdentityTypeCodeValue(customer.getIdentityCertTypeCd(), customer.getIdentityTypeCd())) {
            return "신분증 유형이 올바르지 않습니다.";
        }
        String identityIssuDate = normalizeDigits(customer.getIdentityIssuDate());
        if (!isBlank(identityIssuDate) && !is8DigitDate(identityIssuDate)) {
            return "신분증 발급일자 형식이 올바르지 않습니다.";
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
        if (!isAllowedValue(customer.getPostMethod(), "P", "E")) {
            return "수령 방법이 올바르지 않습니다.";
        }
        return null;
    }

    private String validateProductStep(TerminationApplyReqDto.Product product) {
        if (product == null) {
            return "상품 정보가 없습니다.";
        }
        if (isBlank(product.getCancelUseCompanyCd())) {
            return "해지 후 사용 통신사를 선택해 주세요.";
        }
        if (!isAllowedValue(product.getCancelUseCompanyCd(), "KTM", "KT", "SKT", "LGT", "ETC")) {
            return "해지 후 사용 통신사가 올바르지 않습니다.";
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
        String remainPeriod = normalizeNumber(product.getRemainPeriod());
        if (!isBlank(remainPeriod)) {
            try {
                int value = Integer.parseInt(remainPeriod);
                if (value < 0) {
                    return "잔여 할부 개월 수를 확인해 주세요.";
                }
            } catch (NumberFormatException e) {
                return "잔여 할부 개월 수를 확인해 주세요.";
            }
        }

        if (!isBlank(normalizeNumber(product.getRemainAmount()))) {
            amountError = validateNonNegativeNumber(product.getRemainAmount(), "잔여 할부 금액을 확인해 주세요.");
            if (!isBlank(amountError)) {
                return amountError;
            }
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
            return "서비스 해지 동의가 필요합니다.";
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

    private record ValidationResult(ResTermMessage responseMessage, String resMessage) {
        private String resCode() {
            return responseMessage.getCode();
        }

        private static ValidationResult error(ResTermMessage responseMessage, String resMessage) {
            return new ValidationResult(responseMessage, resMessage);
        }
    }

    private boolean isCommonCodeValue(String groupId, String value) {
        String code = safe(value);
        if (isBlank(code)) {
            return false;
        }
        List<CommonCodeData> codes = commonCodeReader.getCommonCodes(CommonCodesRequest.of(groupId)).get(groupId);
        if (codes == null || codes.isEmpty()) {
            logger.warn("[commonCode] code group is empty: groupId={}, value={}", groupId, code);
            return false;
        }
        return codes.stream().anyMatch(commonCode -> code.equals(commonCode.code()));
    }

    private boolean isIdentityTypeCodeValue(String identityCertTypeCd, String identityTypeCd) {
        String groupId = "F".equals(safe(identityCertTypeCd)) ? "fathCertIdType" : "RCP2006";
        return isCommonCodeValue(groupId, identityTypeCd);
    }

    private static String requireText(String value, ResTermMessage responseMessage, String fieldName, String ncn) {
        if (isBlank(value)) {
            logger.error("[apply] fail: {} is blank, ncn={}", fieldName, safe(ncn));
            throw new ApplyFailureException(responseMessage);
        }
        return safe(value);
    }

    private static void requireInserted(int inserted, ResTermMessage responseMessage, String stepName, Long requestKey) {
        if (inserted <= 0) {
            logger.error("[apply] {} failed: requestKey={}, inserted={}", stepName, requestKey, inserted);
            throw new ApplyFailureException(responseMessage);
        }
    }

    private static boolean hasAgentData(MsfRequestAgentVo agentVo) {
        return agentVo != null && (
            !isBlank(agentVo.getMinorAgentNm())
                || !isBlank(agentVo.getMinorAgentRrn())
                || !isBlank(agentVo.getMinorAgentBirth())
                || !isBlank(agentVo.getMinorAgentTelFnNo())
                || !isBlank(agentVo.getJrdclAgentNm())
                || !isBlank(agentVo.getJrdclAgentTelFnNo())
        );
    }

    private static boolean isMinorCustomerType(String cstmrTypeCd) {
        return "NM".equals(cstmrTypeCd) || "FM".equals(cstmrTypeCd);
    }

    private static String firstNonBlank(String... values) {
        if (values == null) {
            return "";
        }
        for (String value : values) {
            if (!isBlank(value)) {
                return value;
            }
        }
        return "";
    }

    private static boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }

    private static String joinParts(String... values) {
        if (values == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String value : values) {
            builder.append(normalizeDigits(value));
        }
        return builder.toString();
    }

    private static boolean isChecked(Object value) {
        if (value instanceof Boolean checked) {
            return checked;
        }
        String normalized = safe(value == null ? null : String.valueOf(value));
        return "Y".equalsIgnoreCase(normalized) || "true".equalsIgnoreCase(normalized) || "1".equals(normalized);
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

    private static String normalizeDigits(String value) {
        return safe(value).replaceAll("[^0-9]", "");
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

    private static class ApplyFailureException extends RuntimeException {
        private final ResTermMessage responseMessage;

        private ApplyFailureException(ResTermMessage responseMessage) {
            super(responseMessage.getMessage());
            this.responseMessage = responseMessage;
        }
    }
}
