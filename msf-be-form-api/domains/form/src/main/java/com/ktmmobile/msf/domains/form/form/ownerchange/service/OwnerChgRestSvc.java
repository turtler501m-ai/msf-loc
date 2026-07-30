package com.ktmmobile.msf.domains.form.form.ownerchange.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tools.jackson.databind.ObjectMapper;

import com.ktmmobile.msf.commons.common.context.business.BusinessContextBoundary;
import com.ktmmobile.msf.commons.common.context.business.BusinessContextHolder;
import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.common.exception.CommonException;
import com.ktmmobile.msf.commons.common.utils.env.EnvironmentUtils;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxFormRequest;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.port.out.MspPrxClient;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformOsstServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.exception.MspPrxClientException;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResTermMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormCommonRequest;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormX23Response;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormX49Response;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormXmlSelfcareRequest;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0FrmRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormFMC0InfoRequest;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MplatFormOsstFMC0MC0Response;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestNameChgVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestOsstVo;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFS2Request;
import com.ktmmobile.msf.domains.form.form.newchange.dto.mplatform.MplatFormFS2Response;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.BankType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.CustomerType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.IdentityType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeFormDetailRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeInitFormInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeJoinInfoResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeSaveResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeType;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationRequest;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeValidationResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.dto.OwnerChangeWireUseTimeResponse;
import com.ktmmobile.msf.domains.form.form.ownerchange.field.OwnerChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanY02ResDto;
import com.ktmmobile.msf.domains.form.form.termination.repository.CancelPageRepositoryImpl;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class OwnerChgRestSvc {

    private final ObjectMapper objectMapper;
    private final McpApiClient mcpApiClient;
    private final OwnerChangeFieldMapper ownerChangeFieldMapper;
    private final OwnerChgMsfSvc ownerChgMsfSvc;
    private final OwnerChgMcpSvc ownerChgMcpSvc;
    private final MspPrxClient mspPrxClient;
    private final CancelPageRepositoryImpl cancelPageRepository;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;
    private final AgencyCacheReader agencyCacheReader;
    private final GenerateKeyRepository generateKeyRepository;

    /** 명의 변경 가입 가능 유효성 체크 **/
    @SuppressWarnings("PMD.AvoidUsingHardCodedIP")
    public OwnerChangeValidationResponse ownerChangeValidation(OwnerChangeValidationRequest request) throws IOException {

        // 계약정보 유효성 체크(Y04)
        // OwnerChangePossibleValidationResponse possibleValidationResponse = null;
        // try {
        //     possibleValidationResponse = msfMplatFormService.commonMplatform(paramMap, "Y04", OwnerChangePossibleValidationResponse.class);
        // } catch (Exception e) {
        //
        // }
        //
        // if (!"00".equals(possibleValidationResponse.getResultCd())) {
        //     OwnerChangeType ownerChangeType = OwnerChangeType.fromCode(possibleValidationResponse.getResultCd());
        //     return OwnerChangeValidationResponse.builder().resultCd(ownerChangeType.getCode()).message(ownerChangeType.getMessage()).build();
        // }

        if (cancelPageRepository.existsInProgressApplicationByMobileNo(request.getCtn())) {
            log.warn("[checkInProgressApplication] fail: in-progress application exists, mobileNo={}", request.getCtn());
            //현재 진행중인 신청서가 있어 신청할 수 없습니다.
            return OwnerChangeValidationResponse.builder().resultCd(ResTermMessage.APPLY_IN_PROGRESS_EXISTS.getCode())
                .message(ResTermMessage.APPLY_IN_PROGRESS_EXISTS.getMessage()).build();
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("userNm", request.getUserNm());
        params.put("ctn", request.getCtn());
        params.put("userBirth", request.getUserBirth());
        params.put("cstmrType", request.getCstmrType());
        params.put("cstmrJuridicalRrn1", request.getCstmrJuridicalRrn1());
        params.put("cstmrJuridicalRrn2", request.getCstmrJuridicalRrn2());

        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrListNmChg", params, List.class);

        if (cntrList.isEmpty()) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.EMPTY.getCode())
                .message(OwnerChangeType.EMPTY.getMessage()).build();
        }

        McpUserCntrMngDto mcpUserCntrMngDto = cntrList.getFirst();
        List<String> isMinor = List.of("NM", "FM");

        // 정지회선일때
        if ("S".equals(mcpUserCntrMngDto.getSubStatus())) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.STATUS_STOP.getCode())
                .message(OwnerChangeType.STATUS_STOP.getMessage()).build();
        }
        // 미납회원일때
        if ("D".equals(mcpUserCntrMngDto.getColDelinqStatus())) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.NON_PAY.getCode())
                .message(OwnerChangeType.NON_PAY.getMessage()).build();
        }

        request.setCustId(mcpUserCntrMngDto.getCustId());
        request.setNcn(mcpUserCntrMngDto.getContractNum());
        request.setClntIp(RequestUtils.getClientIp());
        request.setClntUsrId(mcpUserCntrMngDto.getUserid());

        OwnerChangeValidationResponse.OwnerChangeInfo ownerChangeInfo = ownerChangeFieldMapper.toOwnerChangeInfo(mcpUserCntrMngDto);
        log.info("ownerChangeInfo {} ", ownerChangeInfo);

        MplatFormCommonRequest mpaltFromRequest = ownerChangeFieldMapper.toMplatFormCommonRequest(request);
        HashMap<String, String> paramMap = objectMapper.convertValue(mpaltFromRequest, HashMap.class);

        // 실사용자 90일 이상인 회선만 명의변경 가능(X83)

        paramMap.put("appEventCd", "X83");
        MspPrxSoapResponse mspX83Result = mspPrxClient.callService(MspPrxFormRequest.builder().parameters(paramMap).build());
        OwnerChangeWireUseTimeResponse wireUseTimeResponse = XmlConvertUtils.xmlReturnParser(mspX83Result.rawXml(),
            OwnerChangeWireUseTimeResponse.class);

        // 실사용자 회선 정보 미존재시
        if (!wireUseTimeResponse.getCommHeader().isSuccess()) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.REAL_USE_DAY_EMPTY.getCode())
                .message(OwnerChangeType.REAL_USE_DAY_EMPTY.getMessage()).build();
        }

        // 90일 안될 경우
        if (wireUseTimeResponse.getOutDto().getRealUseDayNum() < 90) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.REAL_USE_DAY_ERROR.getCode())
                .message(OwnerChangeType.REAL_USE_DAY_ERROR.getMessage()).build();
        }

        // 가입자정보조회(X01)
        paramMap.put("appEventCd", "X01");
        MspPrxSoapResponse mspX01Result = mspPrxClient.callService(MspPrxFormRequest.builder().parameters(paramMap).build());
        OwnerChangeJoinInfoResponse joinResponse = XmlConvertUtils.xmlReturnParser(mspX01Result.rawXml(), OwnerChangeJoinInfoResponse.class);

        if (joinResponse.getCommHeader().isSuccess()) {
            ownerChangeInfo.setEmail(joinResponse.getOutDto().getEmail());
            ownerChangeInfo.setHomeTel(joinResponse.getOutDto().getHomeTel());
        }

        // 가입중인 요금제 조회(Y02)
        MplatFormXmlSelfcareRequest y02Request = MplatFormXmlSelfcareRequest.builder().ncn(request.getNcn()).ctn(request.getCtn())
            .custId(request.getCustId()).build();
        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlSelfService(List.of(), MplatformServiceType.Y02, y02Request);
        PricePlanY02ResDto y02Response = XmlConvertUtils.xmlReturnParser(mspPrxSoapResponse.rawXml(), PricePlanY02ResDto.class);

        if (!y02Response.getCommHeader().isSuccess()) {
            return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.EMPTY_PLAN.getCode())
                .message(OwnerChangeType.EMPTY_PLAN.getMessage()).build();
        }

        ownerChangeInfo.setProdId(y02Response.getOutDto().getProdId());
        ownerChangeInfo.setProdNm(y02Response.getOutDto().getProdNm());
        ownerChangeInfo.setProdAmt(y02Response.getOutDto().getFamtTarifAmt());

        MspRateMstDto mspRateMstDto = ownerChgMcpSvc.selectRateInfo(y02Response.getOutDto().getProdId());

        ownerChangeInfo.setJehuProdNm(mspRateMstDto.getJehuProdNm());
        ownerChangeInfo.setJehuProdType(mspRateMstDto.getJehuProdType());

        // 납부방법 조회(X23)
        paramMap.put("appEventCd", "X23");
        MspPrxSoapResponse mspX23Result = mspPrxClient.callService(MspPrxFormRequest.builder().parameters(paramMap).build());
        MplatFormX23Response mplatFormX23Response = XmlConvertUtils.xmlReturnParser(mspX23Result.rawXml(), MplatFormX23Response.class);

        if (mplatFormX23Response.getCommHeader().isSuccess()) {
            Map<String, String> payMethod = Map.of("신용카드", "C", "자동이체", "D", "통합청구", "0");
            String billingMethod = payMethod.get(mplatFormX23Response.getOutDto().getPayMethod());
            ownerChangeInfo.setBlBillingMethod(StringUtils.hasText(billingMethod) ? billingMethod : "D");
        }

        // 요금 명세서 조회(X49)
        paramMap.put("appEventCd", "X49");
        MspPrxSoapResponse mspX49Result = mspPrxClient.callService(MspPrxFormRequest.builder().parameters(paramMap).build());
        MplatFormX49Response mplatFormX49Response = XmlConvertUtils.xmlReturnParser(mspX49Result.rawXml(), MplatFormX49Response.class);

        if (mplatFormX49Response.getCommHeader().isSuccess()) {
            ownerChangeInfo.setBillTypeCd(mplatFormX49Response.returnBillTypeCd());
            // 미성년자 모바일 명세서 선택 불가로 강제 이메일 선택 처리
            if (isMinor.contains(request.getCstmrType())) {
                ownerChangeInfo.setBillTypeCd("CB");
            }
        }

        // 화면에서 필요하기 때문에 미리 발급(신청서 작성에 쓰임)
        long requestKey = generateKeyRepository.getGeneratedRequestKey();

        ownerChangeInfo.setCntpntCdNm(AuthenticationUtils.getShopName());
        ownerChangeInfo.setUserNm(AuthenticationUtils.getUser().getUserName());
        ownerChangeInfo.setRequestKey(requestKey);

        return OwnerChangeValidationResponse.builder().resultCd(OwnerChangeType.SUCCESS.getCode()).message(OwnerChangeType.SUCCESS.getMessage())
            .response(ownerChangeInfo).build();
    }

    /** 명의 변경 작성 완료 **/
    @BusinessContextBoundary
    @Transactional(transactionManager = MspDataSourceConfig.MSP_TX_MANAGER)
    public OwnerChangeSaveResponse ownerChangeFormSave(@Valid MsfRequestNameChgVo request) throws IOException {
        BusinessContextHolder.setParentScanId(request.getParentScanId());

        McpUserCntrMngDto mcpUserCntrMngDto = ownerChangeValidate(request);
        request.setImei1(mcpUserCntrMngDto.getImei());

        // long requestKey = generateKeyRepository.getGeneratedRequestKey();
        // request.setRequestKey(requestKey);
        // 초기 데이터 세팅 및 조건별 데이터 제거
        request.setupNameChgData();
        // 로그인된 판매점 정보 저장
        applyLoginUserShopInfo(request);
        //예약번호
        // request.setMcnResNo(mcpRequestRepository.selectGenerateResNo());
        request.setMcnResNo(generateKeyRepository.getGeneratedResNo());

        /*** 데이터 유효성 검사 ***/
        ownerChangeValidate(request);

        /*** msf 데이터 저장 ***/
        ownerChgMsfSvc.save(request);

        try {
            /*** mcp 데이터 저장 ***/
            ownerChgMcpSvc.save(request);

            /*** FMC0/MC0 호출 ***/
            String osstOrdNo = callPreCheck(request);
            MsfRequestOsstVo msfRequestOsstVo = toTransRequestOsstVo(request, osstOrdNo);
            // msf는 배치로 수정
            ownerChgMcpSvc.updateRequestOsst(msfRequestOsstVo);

        } catch (Exception e) {
            ownerChgMsfSvc.delete(request.getRequestKey());
            throw e;
        }

        return OwnerChangeSaveResponse.builder().success(true).requestKey(request.getRequestKey()).build();
    }

    public MsfRequestOsstVo toTransRequestOsstVo(MsfRequestNameChgVo vo, String osstOrdNo) {
        MsfRequestOsstVo msfRequestOsstVo = new MsfRequestOsstVo();
        msfRequestOsstVo.setSeq(vo.getRequestKey());
        msfRequestOsstVo.setMvnoOrdNo(vo.getMcnResNo());
        msfRequestOsstVo.setOsstOrdNo(osstOrdNo);
        return msfRequestOsstVo;
    }

    public String callPreCheck(MsfRequestNameChgVo request) {

        String asgnAgncId = request.getAgentCd();
        String shopCd = AuthenticationUtils.getShopCode();

        MplatFormFS2Request fs2Request = MplatFormFS2Request.builder()
            .mngmAgncId(asgnAgncId)
            .cntpntCd(shopCd)
            .frmpapId(request.getFmc0Id())
            .frmpapStatCd("P")
            .mcnResNo(request.getMcnResNo())
            .build();

        // 서식지 스캔 ID가 있는 경우 FS2 서식지 상태 변경 호출
        if (StringUtils.hasText(request.getKnoteScanId())) {
            log.info("서식지 상태변경(FS2) 호출 callFS2 - fs2Request : {}", fs2Request);
            callFS2(fs2Request);
        }

        MplatFormFMC0InfoRequest mplatFormFMC0InfoRequest = createMplatFormFMC0InfoRequest(request);
        MplatFormFMC0FrmRequest mplatFormFMC0FrmRequest = createMplatFormFMC0FrmRequest(request);

        // 사전체크 FMC0 호출 전 데이터 세팅
        mplatFormFMC0InfoRequest.preCheckSetup(request);

        log.info("FMC0 호출 파라미터 mplatFormFMC0InfoRequest >> {}", mplatFormFMC0InfoRequest);
        log.info("FMC0 호출 파라미터 mplatFormFMC0FrmRequest >> {}", mplatFormFMC0FrmRequest);

        MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlOsstService(List.of(mplatFormFMC0InfoRequest,
                mplatFormFMC0FrmRequest),
            MplatformOsstServiceType.OWNER_CHANGE_PRE_CHECK.getEventCd(), asgnAgncId, request.getMcnResNo());

        MplatFormOsstFMC0MC0Response mplatFormResponse = XmlConvertUtils.xmlReturnParser(mspPrxSoapResponse.rawXml(),
            MplatFormOsstFMC0MC0Response.class);
        log.info("FMC0 prx 연동 응답 mplatFormResponse >> {}", mplatFormResponse);

        if (!mplatFormResponse.getCommHeader().isSuccess()) {
            if (StringUtils.hasText(request.getKnoteScanId())) {
                log.info("FMC0 prx 연동 실패 FS2 R 상태 변경");
                fs2Request.setFrmpapStatCd("R");
                callFS2(fs2Request);
            }
            log.info("FMC0 prx 연동 결과 responseCode : {}, responseBasic : {}",
                mplatFormResponse.getCommHeader().getResponseCode(),
                mplatFormResponse.getCommHeader().getResponseBasic());

            // 로컬에서는 통과 하도록
            if (!EnvironmentUtils.isLocal()) {
                throw new MspPrxClientException(mplatFormResponse.getCommHeader().getResponseBasic(),
                    mplatFormResponse.getCommHeader().getResponseCode(),
                    mplatFormResponse.getCommHeader().getGlobalNo());
            }
            return "";
        }

        if (!"S".equals(mplatFormResponse.getOutDto().getRsltCd())) {
            log.info("FMC0 prx 연동 실패 FS2 R 상태 변경");
            fs2Request.setFrmpapStatCd("R");
            callFS2(fs2Request);
            log.info("FMC0 prx 연동 결과 rsltCd : {}, rsltMsg : {}", mplatFormResponse.getRsltCd(), mplatFormResponse.getRsltMsg());

            // 로컬에서는 통과 하도록
            if (!EnvironmentUtils.isLocal()) {
                throw new MspPrxClientException(mplatFormResponse.getRsltMsg(),
                    mplatFormResponse.getRsltCd(),
                    mplatFormResponse.getCommHeader().getGlobalNo());
            }
            return "";
        }

        return mplatFormResponse.getOutDto().getOsstOrdNo();

    }

    private MplatFormFMC0FrmRequest createMplatFormFMC0FrmRequest(MsfRequestNameChgVo request) {
        return MplatFormFMC0FrmRequest.builder().frmpapId(request.getFmc0Id()).cntpntCd(request.getCpntId()).iselfFrmpapYn(request.getIselfFrmpapYn())
            .build();
    }

    private MplatFormFMC0InfoRequest createMplatFormFMC0InfoRequest(MsfRequestNameChgVo request) {
        MplatFormFMC0InfoRequest mplatFormFMC0InfoRequest = new MplatFormFMC0InfoRequest();
        MplatFormFMC0InfoRequest.BaseInfo baseInfo = new MplatFormFMC0InfoRequest.BaseInfo();
        MplatFormFMC0InfoRequest.RcvCustInfo rcvCustInfo = new MplatFormFMC0InfoRequest.RcvCustInfo();
        MplatFormFMC0InfoRequest.RcvBillAcntInfo rcvBillAcntInfo = new MplatFormFMC0InfoRequest.RcvBillAcntInfo();
        MplatFormFMC0InfoRequest.PrdcList prdcList = new MplatFormFMC0InfoRequest.PrdcList();

        boolean isMinor = List.of("NM", "FM").contains(request.getCstmrTypeCd());
        boolean isForeinger = List.of("FN", "FM").contains(request.getCstmrTypeCd());
        boolean isGovernment = List.of("JP", "GO").contains(request.getCstmrTypeCd());
        boolean visitAgentFlag = "VDP".equals(request.getCstmrVisitTypeCd()); // 방문유형 대리인
        IdentityType identityType = IdentityType.getIdentityTypeByCode(request.getTeIdentityTypeCd());
        String nativeCode = identityType != null ? identityType.getTargetCode() : ""; // 주민등록증, 장애인등록증, 국가유공자증, 운전면허증 등
        String custIdntNoIndCd = isGovernment ? "02" : isForeinger ? "05" : "01";
        String custIdntNo = isGovernment
            ? request.getCstmrJuridicalBizNo() // 법인사업자등록번호 (request.getCstmrJuridicalRrn() 법인등록번호에서 사업자등록번호로 변경)
            : isForeinger ? request.getCstmrForeignerRrn() : request.getCstmrNativeRrn(); // 고객 식별 번호

        // ================================================
        // 1. baseInfo (양도인 고객정보)
        // ================================================
        baseInfo.setMvnoOrdNo(StringUtil.lpad("0", request.getMcnResNo(), 14));
        baseInfo.setSlsCmpnCd("KIS");
        baseInfo.setCustNo(request.getCustId());
        baseInfo.setSvcContId(request.getNcn());
        baseInfo.setTlphNo(request.getCtn());
        baseInfo.setMcnStatRsnCd(request.getMcnStatRsnCd());
        baseInfo.setUsimSuccYn(request.getUsimSuccYn());
        baseInfo.setIccId(request.getIccId());

        /* 법인인 경우 */
        if (isGovernment) {
            baseInfo.setRealUseCustNm(request.getCstmrJuridicalUserNm());
            baseInfo.setRealUseCustBrthDate(request.getCstmrJuridicalBirth());
            rcvCustInfo.setCrprNo(request.getCstmrJuridicalRrn()); // 법인번호
            rcvCustInfo.setRprsPrsnNm(request.getCstmrJuridicalRepNm());
            rcvCustInfo.setUpjnCd(request.getUpjnCd());
            rcvCustInfo.setBcuSbst(request.getBcuSbst());
            rcvCustInfo.setZipNo(request.getCstmrZipcd());
            rcvCustInfo.setFndtCntplcSbst(request.getCstmrAdr());
            rcvCustInfo.setMntCntplcSbst(request.getCstmrAdrDtl());
            rcvCustInfo.setCrprUpjnCd(request.getUpjnCd());
            rcvCustInfo.setCrprBcuSbst(request.getBcuSbst());
            rcvCustInfo.setCrprZipNo(request.getCstmrZipcd());
            rcvCustInfo.setCrprFndtCntplcSbst(request.getCstmrAdr());
            rcvCustInfo.setCrprMntCntplcSbst(request.getCstmrAdrDtl());
            rcvCustInfo.setAgntRltnCd("04"); // 위탁대리인
            /* 방문유형 본인인 경우 */
            rcvCustInfo.setAgntCustNm(request.getCstmrJuridicalUserNm()); // 법인 실사용자 이름
            rcvCustInfo.setAgntTelNo(request.getCstmrReceiveTelNo()); // 연락받을 번호
            rcvCustInfo.setAgntBrthDate(request.getCstmrJuridicalBirth());
            /* 방문유형 본인인 경우 */

            /* 방문유형 대리인인 경우 */
            if (visitAgentFlag) {
                rcvCustInfo.setAgntCustNm(request.getJrdclAgentNm());
                rcvCustInfo.setAgntTelNo(request.getJrdclAgentTelNo());
                // 법인대리인은 불필요
                // rcvCustInfo.setAgntTypeCd(request.getJrdclAgentRelTypeCd());
                rcvCustInfo.setAgntBrthDate(request.getJrdclAgentRrn());
            }
        }
        /* 법인인 경우 */

        /* 미성년자인 경우 */
        if (isMinor) {
            rcvCustInfo.setAgntCustNm(request.getMinorAgentNm());
            // agntCustIdfyNoType (내국인1, 외국인4 고정값 조건 시)
            rcvCustInfo.setAgntCustIdfyNoType(isForeinger ? "4" : "1"); // 내국인: "1", 외국인: "4"
            rcvCustInfo.setAgntIdfyNoVal(request.getMinorAgentRrn());
            rcvCustInfo.setAgntPersonSexDiv("M".equals(request.getMinorAgentGenderCd()) ? "1" : "2");
            rcvCustInfo.setAgntAgreYn("Y");
            rcvCustInfo.setAgntTelAthn("M");
            rcvCustInfo.setAgntTelNo(request.getMinorAgentTelNo());
            rcvCustInfo.setAgntTypeCd(request.getMinorAgentRelTypeCd());
            rcvCustInfo.setAgntNationalityCd(isForeinger ? request.getCstmrForeignerNation() : null);
            rcvCustInfo.setAgntRsdcrtIssuDate(request.getTeIdentityIssuDate());
            rcvCustInfo.setAgntRltnCd("03"); // 법정대리인
            rcvCustInfo.setAgntBrthDate(request.getMinorAgentBirth());
            rcvCustInfo.setAgntRlnamAthnEvdnPprCd(nativeCode);

            if ("DRIVE".equals(nativeCode)) {
                rcvCustInfo.setAgntLicnsRgnCd(request.getTeIdentityIssuRegion());
                rcvCustInfo.setAgntLicnsNo(request.getTeDriveLicnsNo());
            }
            /* 미성년자인 경우 */
        }

        if ("DRIVE".equals(nativeCode)) {
            // nativeCode drive 면허인 경우
            rcvCustInfo.setLcnsNo(isMinor ? null : request.getTeDriveLicnsNo());
            rcvCustInfo.setLcnsRgnCd(isMinor ? null : request.getTeIdentityIssuRegion()); // 중복 항목: 필요에 따라 필드명 확인 필요
        }
        if ("MERIT".equals(nativeCode)) {
            // 국가 유공자인 경우
            rcvCustInfo.setMrtrPrsnNo(isMinor ? null : request.getTeDriveLicnsNo());
        }

        // ================================================
        // 2. rcvCustInfo (양수인 고객정보)
        // ================================================
        rcvCustInfo.setCustTypeCd(CustomerType.getCustomerTypeByCode(request.getCstmrTypeCd()).getGroupType());
        rcvCustInfo.setCustIdntNoIndCd(custIdntNoIndCd); // 구분코드 외국인 05, 여권 04, 그 외 01
        rcvCustInfo.setCustIdntNo(custIdntNo); // 고객 식별 번호
        rcvCustInfo.setCustNm(request.getCstmrNm());
        rcvCustInfo.setMyslAgreYn("Y"); // 본인 동의 여부
        // 실명인증증빙서류 코드 - 미성년자는 COURT
        rcvCustInfo.setNativeRlnamAthnEvdnPprCd(isMinor
            ? "COURT" :
            isGovernment ? null : IdentityType.getIdentityTypeByCode(request.getTeIdentityTypeCd()).getTargetCode());
        rcvCustInfo.setAthnRqstcustCntplcNo(StringUtils.hasText(request.getTeFathTelNo()) ? request.getTeFathTelNo() : request.getCtn());
        rcvCustInfo.setRsdcrtIssuDate(isGovernment ? request.getCstmrJuridicalBizNoIssuDate() : request.getTeIdentityIssuDate());

        rcvCustInfo.setNationalityCd(!isForeinger ? null : request.getCstmrForeignerNation());
        rcvCustInfo.setFornBrthDate(!isForeinger ? null : request.getCstmrForeignerBirth());
        rcvCustInfo.setCrdtInfoAgreYn("Y");
        rcvCustInfo.setIndvInfoInerPrcuseAgreYn("Y");
        rcvCustInfo.setCnsgInfoAdvrRcvAgreYn(request.getClausePriTrustYn());
        rcvCustInfo.setOthcmpInfoAdvrRcvAgreYn(request.getPersonalInfoCollectAgreeYn());
        rcvCustInfo.setOthcmpInfoAdvrCnsgAgreYn(request.getClausePriAdYn());
        rcvCustInfo.setGrpAgntBindSvcSbscAgreYn("N");
        rcvCustInfo.setCardInsrPrdcAgreYn("N");
        rcvCustInfo.setOlngDscnHynmtrAgreYn("N");
        rcvCustInfo.setWlfrDscnAplyAgreYn("N");
        rcvCustInfo.setSpamPrvdAgreYn("N");
        rcvCustInfo.setPrttlpStlmUseAgreYn("N");
        rcvCustInfo.setPrttlpStlmPwdUseAgreYn("N");

        // wrlnTlphNo (cstmrTelNo / cstmrMobileNo 중 선택)
        rcvCustInfo.setWrlnTlphNo(StringUtils.hasText(request.getCstmrTelNo()) ? request.getCstmrTelNo() : request.getCstmrMobileNo());

        // brthDate (cstmrNativeBirth / cstmrForeignerBirth 중 선택)
        rcvCustInfo.setBrthDate(isForeinger ? request.getCstmrForeignerBirth() : request.getCstmrNativeBirth());

        rcvCustInfo.setEmlAdrsNm(request.getCstmrEmailAdr());
        rcvCustInfo.setCustInfoChngYn("Y");
        // 자택 번호 이동전화유형으로 들어가야 함 010-xxxx-xxxx
        rcvCustInfo.setHomeTlphNo(request.getCstmrReceiveTelNo());
        rcvCustInfo.setFnncDealAgreeYn(request.getOthersTrnsAllAgreeYn());
        rcvCustInfo.setIndvLoInfoPrvAgreeYn(request.getIndvLocaPrvAgreeYn());


        // ================================================
        // 2. rcvBillAcntInfo (양수인 청구계정정보)
        // ================================================
        rcvBillAcntInfo.setRqsshtPprfrmCd(request.getCstmrBillSendTypeCd());
        rcvBillAcntInfo.setRqsshtTlphNo(request.getCtn()); // 청구서 발송 번호 => 양도인 전화번호
        rcvBillAcntInfo.setRqsshtEmlAdrsNm(request.getCstmrEmailAdr());
        rcvBillAcntInfo.setBillZipNo(request.getCstmrZipcd());
        rcvBillAcntInfo.setBillFndtCntplcSbst(request.getCstmrAdr());
        rcvBillAcntInfo.setBillMntCntplcSbst(request.getCstmrAdrDtl());
        rcvBillAcntInfo.setBlpymMthdCd(request.getReqPayTypeCd());

        if ("C".equals(request.getReqPayTypeCd())) {
            rcvBillAcntInfo.setDuedatDateIndCd("99");
            // crdtCardExprDate (reqCardYy + reqCardMm 결합 시)
            rcvBillAcntInfo.setCrdtCardExprDate(request.getReqCardYy() + request.getReqCardMm());
            rcvBillAcntInfo.setCrdtCardKindCd(request.getReqCardCompanyCd());
            rcvBillAcntInfo.setBlpymMthdIdntNo(request.getReqCardNo());
            rcvBillAcntInfo.setBlpymCustNm(request.getReqCardNm());
            rcvBillAcntInfo.setBlpymCustIdntNo(request.getReqCardRrn());
        } else {
            rcvBillAcntInfo.setDuedatDateIndCd("21");
            rcvBillAcntInfo.setBankCd(BankType.getByCode(request.getReqBankCd()).getTeleCode());
            rcvBillAcntInfo.setBlpymMthdIdntNo(request.getReqAccountNo());
            rcvBillAcntInfo.setBlpymCustNm(request.getReqAccountNm());
            rcvBillAcntInfo.setBlpymCustIdntNo(request.getReqAccountRrn());
            rcvBillAcntInfo.setAgreIndCd("01");
        }

        prdcList.setPrdcCd(request.getSoc());
        prdcList.setPrdcTypecd("P");

        mplatFormFMC0InfoRequest.setBaseInfo(baseInfo);
        mplatFormFMC0InfoRequest.setRcvCustInfo(rcvCustInfo);
        mplatFormFMC0InfoRequest.setRcvBillAcntInfo(rcvBillAcntInfo);
        mplatFormFMC0InfoRequest.setPrdcList(prdcList);

        return mplatFormFMC0InfoRequest;
    }

    public void callFS2(MplatFormFS2Request request) {
        MspPrxSoapResponse prxResponse = msfMcpOsstPrxService.callXmlOsstService(
            List.of(request),
            MplatformOsstServiceType.FRMPAP_ID_STATUS_CHANGE.getEventCd(),
            request.getMngmAgncId(),
            request.getMcnResNo()
        );

        MplatFormFS2Response fs2Response = XmlConvertUtils.xmlReturnParser(prxResponse.rawXml(), MplatFormFS2Response.class);

        if (!fs2Response.getCommHeader().isSuccess()) {
            log.info("mPlatform FS2 연동 결과 rslt : {}, rsltMsg : {}",
                fs2Response.getCommHeader().getResponseBasic(),
                fs2Response.getCommHeader().getResponseCode());

            if (!EnvironmentUtils.isLocal()) {
                throw new MspPrxClientException(fs2Response.getCommHeader().getResponseBasic(),
                    fs2Response.getCommHeader().getResponseCode(),
                    fs2Response.getCommHeader().getGlobalNo());

            }
            return;
        }

        if (!"Y".equals(fs2Response.getOutDto().getRsltCd())) {
            log.info("mPlatform FS2 연동 결과 rslt : {}, rsltMsg : {}", fs2Response.getOutDto().getRsltCd(), fs2Response.getOutDto().getRsltMsg());
            if (!EnvironmentUtils.isLocal()) {
                throw new MspPrxClientException(fs2Response.getCommHeader().getResponseBasic(),
                    fs2Response.getCommHeader().getResponseCode(),
                    fs2Response.getCommHeader().getGlobalNo());
            }
        }
    }

    public void applyLoginUserShopInfo(MsfRequestNameChgVo vo) {

        //AgencyCacheReader 에서 불러오기
        Optional<AgencyCache> agentInfo = agencyCacheReader.getAgency(vo.getAgentCd());
        if (agentInfo.isPresent()) {
            vo.setAgentCd(agentInfo.get().ktOrganizationId());
            vo.setAgentNm(agentInfo.get().organizationName());
            vo.setCntpntShopCd(agentInfo.get().organizationId());
            vo.setCntpntShopNm(agentInfo.get().organizationName());
            // vo.setManagerCd(agentInfo.get().respnPrsnId());
            // vo.setManagerNm(agentInfo.get().respnPrsnNm());
            vo.setManagerCd(AuthenticationUtils.getUser().getUserId());
            vo.setManagerNm(AuthenticationUtils.getUser().getUserName());
            //agentInfo.get().representativeTelephone(); //REP_TEL_NO
            //agentInfo.get().telephone(); //TELNUM
        }

        // 로그인한 판매점 정보로 값 세팅
        String shopCd = AuthenticationUtils.getShopCode();
        String shopNm = AuthenticationUtils.getShopName();
        vo.setShopCd(shopCd);
        vo.setShopNm(shopNm);
        vo.setCpntId(shopCd);
        vo.setCpntNm(shopNm);
        vo.setRealShopNm(shopNm);
    }

    /** 명의변경 신청서 데이터 조회 **/
    public OwnerChangeInitFormInfoResponse ownerChangeFormGet(OwnerChangeFormDetailRequest request) {
        OwnerChangeInitFormInfoResponse response = ownerChangeFieldMapper.toOwnerChangeInitFormInfoResponse(ownerChgMsfSvc.selectOwnerChgInfo(
            request.getRequestKey()));
        response.setup();
        if (StringUtils.hasText(response.getPlanInfo().getPlanName2())) {
            MspRateMstDto mspRateMstDto = ownerChgMcpSvc.selectRateInfo(response.getPlanInfo().getPlanName2());
            response.getPlanInfo().setJehuPartnerTypeNm(mspRateMstDto.getJehuProdNm());
            response.getPlanInfo().setJehuPartnerTypeCd(mspRateMstDto.getJehuProdType());
            response.getPlanInfo().setJehuProdTypeCd(mspRateMstDto.getJehuProdType());
        }
        return response;
    }

    public McpUserCntrMngDto ownerChangeValidate(MsfRequestNameChgVo request) {

        Map<String, String> params = new HashMap<String, String>();
        params.put("userNm", request.getTrnsNm());
        params.put("ctn", request.getTrnsMobileNo());
        params.put("userBirth", request.getUserBirth());
        params.put("cstmrType", request.getTrnsCstmrTypeCd());
        params.put("cstmrJuridicalRrn1", request.getTrCstmrJuridicalRrn1());
        params.put("cstmrJuridicalRrn2", request.getTrCstmrJuridicalRrn2());

        // 양도인 회선 존재 확인
        List<McpUserCntrMngDto> cntrList = mcpApiClient.post("/mypage/cntrListNmChg", params, List.class);
        if (cntrList == null || cntrList.isEmpty()) {
            throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        McpUserCntrMngDto cntrMngDto = cntrList.getFirst();

        // 정지회선일때
        if ("S".equals(cntrMngDto.getSubStatus())) {
            throw new CommonException(OwnerChangeType.STATUS_STOP.getMessage());
        }
        // 미납회원일때
        if ("D".equals(cntrMngDto.getColDelinqStatus())) {
            throw new CommonException(OwnerChangeType.NON_PAY.getMessage());
        }

        return cntrMngDto;

    }
}
