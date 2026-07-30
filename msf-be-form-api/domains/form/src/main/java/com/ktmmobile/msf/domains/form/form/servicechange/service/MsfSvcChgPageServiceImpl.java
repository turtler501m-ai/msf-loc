package com.ktmmobile.msf.domains.form.form.servicechange.service;


import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import com.ktmmobile.msf.commons.common.datasource.msp.MspDataSourceConfig;
import com.ktmmobile.msf.commons.websecurity.security.auth.util.AuthenticationUtils;
import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.cache.agency.application.port.in.AgencyCacheReader;
import com.ktmmobile.msf.domains.cache.agency.domain.dto.AgencyCache;
import com.ktmmobile.msf.domains.cache.commoncode.application.dto.CommonCodesRequest;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.in.CommonCodeReader;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeData;
import com.ktmmobile.msf.domains.cache.commoncode.domain.dto.CommonCodeGroups;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemFileUploadRequest;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.dto.ImageSystemPdfUploadResponse;
import com.ktmmobile.msf.domains.externalclient.imagesystem.application.port.in.ImageSystemUploader;
import com.ktmmobile.msf.domains.externalclient.mspprx.application.dto.MspPrxSoapResponse;
import com.ktmmobile.msf.domains.externalclient.mspprx.domain.code.MplatformServiceType;
import com.ktmmobile.msf.domains.externalclient.mspprx.support.util.XmlConvertUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.AppformReqDto;
import com.ktmmobile.msf.domains.form.common.dto.McpFarPriceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MplatFormXmlSelfcareRequest;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMcpOsstPrxService;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MplatformBase;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.OutDataSharingDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.repository.MspApiDirectRepository;
import com.ktmmobile.msf.domains.form.common.util.DateTimeUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.dto.PriceJoinUsimResponse;
import com.ktmmobile.msf.domains.form.form.common.repository.McpRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgDtlVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgVo;
import com.ktmmobile.msf.domains.form.form.newchange.service.ProductInfoService;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChangInfoViewResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CombineSelfRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.CombineSelfResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ImageSystemUploadReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.InsuranceProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyShareDataReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumberChgeProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.NumberChgeProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PossibleStateCheckRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PossibleStateCheckResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.PricePlanY02ResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ScanIdUpdateReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseProcessRequest;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UnpauseProcessResponse;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Request;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.UsimChangeUC0Response;
import com.ktmmobile.msf.domains.form.form.servicechange.field.ServiceChangeFieldMapper;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcChgPageRepositoryImpl;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.port.in.AddressReader;
import com.ktmmobile.msf.domains.shared.form.common.generate.application.port.out.GenerateKeyRepository;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.CONTPNT_SHOP_ID_MSHOP;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.OPER_TYPE_NEW;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Slf4j
@Service
@RequiredArgsConstructor
public class MsfSvcChgPageServiceImpl {

    private static final String REJOB_X70_MEMO_PREFIX = "REJOB_X70_";
    private static final int SVC_CHG_RES_MSG_MAX_LENGTH = 100;
    private final SvcChgPageRepositoryImpl svcChgPageRepositoryImpl;
    private final MsfMplatFormService msfMplatFormService;
    private final MsfRegSvcServiceImpl msfRegSvcServiceImpl;
    private final MsfSvcMyShareDataSvcImpl msfSvcMyShareDataSvcImpl;
    private final MsfSvcDataSharingSvcImpl msfSvcDataSharingSvcImpl;
    private final MsfMcpOsstPrxService msfMcpOsstPrxService;

    private final McpApiClient mcpApiClient;
    private final MspApiDirectRepository mspApiDirectRepository;
    private final AddressReader addressReader;
    private final MsfRequestRepositoryImpl msfRequestRepository;
    private final McpRequestRepositoryImpl mcpRequestRepository;
    private final TransactionTemplate transactionTemplate;
    @Qualifier(MspDataSourceConfig.MSP_TX_MANAGER)
    private final PlatformTransactionManager mspTransactionManager;
    private final ServiceChangeFieldMapper serviceChangeFieldMapper;
    private final AgencyCacheReader agencyCacheReader;
    private final MsfSvgChargePlanChangeService chargePlanChangeService;
    private final MsfPricePlanServiceImpl msfPricePlanService;
    private final MsfCombineSvcServiceImpl msfCombineSvcService;
    private final MsfInsuranceSvcServiceImpl msfInsuranceSvcService;
    private final MsfUsimChangeSvcServiceImpl msfUsimChangeSvcService;
    private final MsfSvgNumChgeService msfSvgNumChgeService;
    private final MsfSvgUnpauseService msfSvgUnpauseService;
    private final ImageSystemUploader imageSystemUploader;
    private final GenerateKeyRepository generateKeyRepository;
    private final CommonCodeReader commonCodeReader;  // 공통코드 조회 서비스 인터페이스 주입
    private final ProductInfoService productInfoService;

    public McpUserCntrMngDto selectCntrListNoLogin(String contractNum) {
        return selectCntrListNoLogin(contractNum, true);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(String contractNum, boolean roadAddrChk) {
        if (contractNum == null || "".equals(contractNum)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(contractNum);
        return selectCntrListNoLogin(userCntrMngDto, roadAddrChk);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        return selectCntrListNoLogin(userCntrMngDto, true);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto, boolean roadAddrChk) {
        if (userCntrMngDto.getSvcCntrNo() == null && userCntrMngDto.getCntrMobileNo() == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        // [ASIS] interface 호출 소스 보관
        // RestTemplate restTemplate = new RestTemplate();
        // return restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);
        McpUserCntrMngDto cntrInfo = svcChgPageRepositoryImpl.selectCntrListNoLogin(userCntrMngDto);
        // 서비스변경 주소 표출 제외에 따라 도로명 주소 변환 기능 미사용
        // if (roadAddrChk) {
        //     applyRoadAddress(cntrInfo);
        // }
        return cntrInfo;
    }

    public MspRateMstDto getMspRateMst(String rateCd) {
        //20260508 FCommonSvc 기존소스에서 변경함
        return mcpApiClient.post("/msp/mspRateMst", rateCd, MspRateMstDto.class);
    }

    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private void applyRoadAddress(McpUserCntrMngDto cntrInfo) {
        if (cntrInfo == null || StringUtils.isBlank(cntrInfo.getBanAdrPrimaryLn())) {
            return;
        }

        try {
            SearchAddressResponse.JusoResponse roadAddress = null;
            for (String keyword: buildRoadAddressSearchKeywords(cntrInfo)) {
                SearchAddressResponse response = addressReader.getListAddress(new SearchAddressCondition(1, 5, keyword));
                if (response == null || response.list() == null || response.list().isEmpty()) {
                    continue;
                }

                roadAddress = selectRoadAddress(cntrInfo, response.list());
                if (roadAddress != null) {
                    break;
                }
            }

            if (roadAddress == null || StringUtils.isBlank(roadAddress.roadAddress1())) {
                return;
            }

            cntrInfo.setBanAdrZip(StringUtil.NVL(roadAddress.zipNo(), cntrInfo.getBanAdrZip()));
            cntrInfo.setBanAdrPrimaryLn(roadAddress.roadAddress1());

            cntrInfo.setBanAdrSecondaryLn(buildRoadDetailAddress(roadAddress, cntrInfo.getBanAdrSecondaryLn()));
        } catch (Exception e) {
            log.info("[selectCntrListNoLogin] 도로명주소 보정 실패: {}", e.getMessage());
        }
    }

    private static List<String> buildRoadAddressSearchKeywords(McpUserCntrMngDto cntrInfo) {
        String primaryAddress = StringUtils.normalizeSpace(StringUtil.NVL(cntrInfo.getBanAdrPrimaryLn(), ""));
        String detailAddress = StringUtils.normalizeSpace(StringUtil.NVL(cntrInfo.getBanAdrSecondaryLn(), ""));
        String fullKeyword = StringUtils.normalizeSpace((primaryAddress + " " + detailAddress).trim());
        String shortKeyword = StringUtils.normalizeSpace((primaryAddress + " " + removeLastAddressToken(detailAddress)).trim());

        return List.of(fullKeyword, shortKeyword).stream()
            .filter(StringUtils::isNotBlank)
            .distinct()
            .toList();
    }

    private static String removeLastAddressToken(String address) {
        String normalizedAddress = StringUtils.normalizeSpace(StringUtil.NVL(address, ""));
        int lastSpaceIndex = normalizedAddress.lastIndexOf(' ');
        if (lastSpaceIndex < 0) {
            return "";
        }
        return normalizedAddress.substring(0, lastSpaceIndex);
    }

    private static SearchAddressResponse.JusoResponse selectRoadAddress(
        McpUserCntrMngDto cntrInfo,
        List<SearchAddressResponse.JusoResponse> roadAddresses
    ) {
        List<SearchAddressResponse.JusoResponse> usableAddresses = roadAddresses.stream()
            .filter(item -> StringUtils.isNotBlank(item.roadAddress1()))
            .toList();
        if (usableAddresses.size() == 1) {
            return usableAddresses.get(0);
        }

        String currentZip = StringUtil.NVL(cntrInfo.getBanAdrZip(), "");
        List<SearchAddressResponse.JusoResponse> zipMatches = usableAddresses.stream()
            .filter(item -> StringUtils.isNotBlank(currentZip))
            .filter(item -> StringUtils.equals(currentZip, item.zipNo()))
            .toList();
        if (zipMatches.size() == 1) {
            return zipMatches.get(0);
        }

        SearchAddressResponse.JusoResponse detailMatchedAddress = selectDetailMatchedRoadAddress(
            cntrInfo,
            zipMatches.isEmpty() ? usableAddresses : zipMatches
        );
        if (detailMatchedAddress != null) {
            return detailMatchedAddress;
        }

        boolean sameRoadAddress = usableAddresses.stream()
            .map(item -> StringUtils.normalizeSpace(item.roadAddress1()))
            .distinct()
            .count() == 1;
        return sameRoadAddress ? usableAddresses.get(0) : null;
    }

    private static SearchAddressResponse.JusoResponse selectDetailMatchedRoadAddress(
        McpUserCntrMngDto cntrInfo,
        List<SearchAddressResponse.JusoResponse> roadAddresses
    ) {
        List<SearchAddressResponse.JusoResponse> detailMatches = roadAddresses.stream()
            .filter(item -> containsAnyDetailToken(item, cntrInfo.getBanAdrSecondaryLn()))
            .toList();
        return detailMatches.size() == 1 ? detailMatches.get(0) : null;
    }

    private static boolean containsAnyDetailToken(SearchAddressResponse.JusoResponse roadAddress, String detailAddress) {
        String addressText = StringUtils.normalizeSpace(String.join(" ",
            StringUtil.NVL(roadAddress.roadAddress1(), ""),
            StringUtil.NVL(roadAddress.roadAddress2(), ""),
            StringUtil.NVL(roadAddress.roadAddress(), "")));
        for (String token: StringUtils.normalizeSpace(StringUtil.NVL(detailAddress, "")).split(" ")) {
            if (StringUtils.isNotBlank(token) && StringUtils.containsIgnoreCase(addressText, token)) {
                return true;
            }
        }
        return false;
    }

    private static String buildRoadDetailAddress(SearchAddressResponse.JusoResponse roadAddress, String detailAddress) {
        String roadReference = StringUtil.NVL(roadAddress.roadAddress2(), "");
        String remainingDetail = removeIncludedDetailTokens(roadAddress, detailAddress);
        return StringUtils.normalizeSpace((roadReference + " " + remainingDetail).trim());
    }

    private static String removeIncludedDetailTokens(SearchAddressResponse.JusoResponse roadAddress, String detailAddress) {
        String normalizedDetail = StringUtils.normalizeSpace(StringUtil.NVL(detailAddress, ""));
        String addressText = StringUtils.normalizeSpace(String.join(" ",
            StringUtil.NVL(roadAddress.roadAddress2(), ""),
            StringUtil.NVL(roadAddress.roadAddress(), ""),
            StringUtil.NVL(roadAddress.jibunAddress(), "")));

        List<String> remainingTokens = new ArrayList<>();
        for (String token: normalizedDetail.split(" ")) {
            if (isIncludedDetailToken(addressText, token)) {
                continue;
            }
            remainingTokens.add(token);
        }
        return StringUtils.normalizeSpace(String.join(" ", remainingTokens));
    }

    private static boolean isIncludedDetailToken(String addressText, String token) {
        if (StringUtils.isBlank(token)) {
            return false;
        }

        if (StringUtils.containsIgnoreCase(addressText, token)) {
            return true;
        }

        return token.endsWith("번지")
            && StringUtils.contains(addressText, StringUtils.removeEnd(token, "번지"));
    }

    public FormResponse<ChangInfoViewResDto> getChangInfoView(HttpServletRequest request, MyPageSearchDto searchVO) {
        if (searchVO == null) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        log.info("[getChangInfoView] 조회 시작 — ncn={}, ctn={}, custId={}", searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());

        List<McpUserCntrMngDto> cntrList = new ArrayList<>();

        if (StringUtils.isBlank(StringUtil.NVL(searchVO.getNcn(), searchVO.getContractNum()))) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        McpUserCntrMngDto cntrInfo;
        try {
            cntrInfo = resolveContractInfo(searchVO);
        } catch (McpCommonException e) {
            log.warn("[MsfChangPage][getChangInfoView] contract info not found: {}", e.getMessage());
            return FormResponse.of(ResSvcChgMessage.CHANGE_CONTRACT_NOT_FOUND);
        } catch (Exception e) {
            log.warn("[MsfChangPage][getChangInfoView] contract info lookup error", e);
            return FormResponse.of(ResSvcChgMessage.CHANGE_INFO_ERROR);
        }
        cntrList.add(cntrInfo);

        String userName = StringUtil.NVL(cntrInfo.getUserName(), StringUtil.NVL(searchVO.getUserName(), ""));
        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();
        String contractNum = searchVO.getContractNum();
        String modelName = StringUtil.NVL(searchVO.getModelName(), "-");
        String rprsPrdtId = StringUtil.NVL(searchVO.getRprsPrdtId(), "");
        if (StringUtils.isBlank(rprsPrdtId)) {
            // 서비스변경 ASIS는 계약조회 결과의 trgtModelId를 보험상품 조회 조건으로 사용한다.
            // TOBE에서는 계약정보의 modelId가 같은 역할을 하므로 요청값이 없을 때 보정한다.
            rprsPrdtId = StringUtil.NVL(cntrInfo.getModelId(), "");
        }

        Map<String, String> insrInfo = getStringMap(mcpApiClient.post("/mypage/getInsrInfo", ncn, Map.class));
        String reqBuyType = insrInfo != null ? (String) insrInfo.get("REQ_BUY_TYPE") : null; //구매유형코드(UU:유심, MM:단말)

        McpFarPriceDto mcpFarPriceDto = null;
        String prvRateGrpNm = "-";
        String rateAdsvcLteDesc = "- MB";
        String rateAdsvcCallDesc = "- 분";
        String rateAdsvcSmsDesc = "- 건";

        try {
            log.info("[getChangInfoView] 요금제 정보 조회 — contractNum={}", contractNum);
            mcpFarPriceDto = mspApiDirectRepository.query("/mypage/farPricePlan", contractNum, McpFarPriceDto.class);
            if (mcpFarPriceDto != null) {
                prvRateGrpNm = mcpFarPriceDto.getPrvRateGrpNm();
                log.info("[getChangInfoView] 요금제 정보 조회 완료 — prvRateGrpNm={}", prvRateGrpNm);

                //FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
                //rateAdsvcLteDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(), "- MB");
                //rateAdsvcCallDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(), "- 분");
                //rateAdsvcSmsDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(), "- 건");
            }
        } catch (SelfServiceException e) {
            log.info("[getChangInfoView] SelfServiceException: {}", e.getMessage());
        } catch (Exception e) {
            log.info("[getChangInfoView] 요금제 상세 조회 실패: {}", e.getMessage());
        }

        String addr = "-";
        String zipNo = StringUtil.NVL(cntrInfo.getBanAdrZip(), "");
        String address = StringUtil.NVL(cntrInfo.getBanAdrPrimaryLn(), "");
        String detailAddress = StringUtil.NVL(cntrInfo.getBanAdrSecondaryLn(), "");
        String initActivationDate = "-";
        String homeTel = "";
        String email = "";
        Map<String, Object> combinePayData = new HashMap<>();
        boolean skipMplatformLookup = Boolean.TRUE.equals(searchVO.getSkipPerMyktfInfo());

        if (skipMplatformLookup) {
            log.info("[getChangInfoView] mplatform lookup skip: ncn={}", ncn);
        } else {
            try {
                log.info("[getChangInfoView] perMyktfInfo(X01) 조회 — ncn={}, ctn={}, custId={}", ncn, ctn, custId);
                MpPerMyktfInfoVO perMyktfInfo = msfMplatFormService.perMyktfInfo(ncn, ctn, custId);
                if (perMyktfInfo != null) {
                    log.info("[getChangInfoView] perMyktfInfo(X01) 조회 결과 — addr={}, initActivationDate={}, homeTel={}, email={}",
                        perMyktfInfo.getAddr(), perMyktfInfo.getInitActivationDate(), perMyktfInfo.getHomeTel(), perMyktfInfo.getEmail());
                    addr = StringUtil.NVL(perMyktfInfo.getAddr(), "-");
                    initActivationDate = StringUtil.NVL(perMyktfInfo.getInitActivationDate(), "-");
                    homeTel = StringUtil.NVL(perMyktfInfo.getHomeTel(), "");
                    email = StringUtil.NVL(perMyktfInfo.getEmail(), "");
                } else {
                    log.info("[getChangInfoView] perMyktfInfo(X01) 조회 결과 — null");
                }
            } catch (SocketTimeoutException | SelfServiceException e) {
                log.error("[getChangInfoView] perMyktfInfo 조회 실패: {}", e.getMessage());
                return FormResponse.of(ResSvcChgMessage.CHANGE_INFO_ERROR, e.getMessage(), null);
            }

            try {
                log.info("[getChangInfoView] 납부방법/명세서 조회 시작 — ncn={}, ctn={}", ncn, ctn);
                MpFarChangewayInfoVO farChgWayInfo = msfMplatFormService.farChangewayInfo(ncn, ctn, custId);
                MpMoscBilEmailInfoInVO bilEmailInfo = null;
                if (farChgWayInfo != null) {
                    bilEmailInfo = msfMplatFormService.kosMoscBillInfo(ncn, ctn, custId);
                }
                combinePayData = combinePayData(farChgWayInfo, bilEmailInfo);
                log.info("[getChangInfoView] combinePayData 결과 — payData={}, billData={}",
                    combinePayData.get("payData") != null, combinePayData.get("billData") != null);
            } catch (SelfServiceException e) {
                log.warn("[getChangInfoView] 납부방법/명세서 조회 실패: {}", e.getMessage());
                combinePayData = combinePayData(null, null);
            } catch (Exception e) {
                log.warn("[getChangInfoView] 납부방법/명세서 조회 오류", e);
                combinePayData = combinePayData(null, null);
            }
        }

        // JVM 로컬 HttpSession의 마스킹 해제 상태에 의존하지 않고 기본 마스킹을 적용한다.
        searchVO.setUserName(StringMakerUtil.getName(userName));

        String remindBlckYn = "";
        if (!skipMplatformLookup) {
            try {
                McpUserCntrMngDto selectSocDesc = mspApiDirectRepository.query("/mypage/socDesc", contractNum, McpUserCntrMngDto.class);
                if (selectSocDesc != null
                    && "Y".equals(selectSocDesc.getRemindYn())
                    && !StringUtils.isEmpty(selectSocDesc.getRemindProdType())) {
                    remindBlckYn = "Y";
                }
                log.info("[getChangInfoView] selectSocDesc 결과 — remindYn={}, remindProdType={}, remindBlckYn={}",
                    selectSocDesc != null ? selectSocDesc.getRemindYn() : "null",
                    selectSocDesc != null ? selectSocDesc.getRemindProdType() : "null",
                    remindBlckYn);
            } catch (Exception e) {
                log.warn("[getChangInfoView] socDesc 조회 실패: {}", e.getMessage());
            }
        }

        ChangInfoViewResDto response = new ChangInfoViewResDto();
        response.setCntrList(cntrList);
        response.setSearchVO(searchVO);
        response.setNcn(ncn);
        response.setContractNum(contractNum);
        response.setCtn(ctn);
        response.setCustId(custId);
        response.setModelName(modelName);
        response.setRprsPrdtId(rprsPrdtId);
        response.setPrvRateGrpNm(prvRateGrpNm);
        response.setRateAdsvcLteDesc(rateAdsvcLteDesc);
        response.setRateAdsvcCallDesc(rateAdsvcCallDesc);
        response.setRateAdsvcSmsDesc(rateAdsvcSmsDesc);
        response.setInitActivationDate(initActivationDate);
        response.setZipNo(zipNo);
        response.setAddress(address);
        response.setDetailAddress(detailAddress);
        response.setAddr(addr);
        response.setHomeTel(homeTel);
        response.setEmail(email);
        response.setPayData(getStringMap(combinePayData, "payData"));
        response.setBillData(getStringMap(combinePayData, "billData"));
        response.setMaskingBtn("Y");
        response.setMaskingSession("");
        response.setRemindBlckYn(remindBlckYn);
        response.setSubStatus(StringUtil.NVL(cntrInfo.getSubStatus(), searchVO.getSubStatus()));
        response.setReqBuyType(reqBuyType);
        log.info(
            "[getChangInfoView] 화면 셋팅값 — prvRateGrpNm={}, initActivationDate={}, zipNo={}, address={}, detailAddress={}, addr={}, homeTel={}, email={}, remindBlckYn={}, payData={}, billData={}, maskingSession={}",
            prvRateGrpNm,
            initActivationDate,
            zipNo,
            address,
            detailAddress,
            addr,
            homeTel,
            email,
            remindBlckYn,
            combinePayData.get("payData") != null,
            combinePayData.get("billData") != null,
            "");
        log.info("[getChangInfoView] 조회 완료 — ncn={}, ctn={}, prvRateGrpNm={}, remindBlckYn={}", ncn, ctn, prvRateGrpNm, remindBlckYn);
        return FormResponse.of(ResSvcChgMessage.SUCCESS, response);
    }

    private McpUserCntrMngDto resolveContractInfo(MyPageSearchDto searchVO) {
        String lookupNcn = StringUtil.NVL(searchVO.getNcn(), searchVO.getContractNum());
        if (StringUtils.isBlank(lookupNcn)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        boolean roadAddrChk = searchVO.getRoadAddrChk() == null || searchVO.getRoadAddrChk();
        McpUserCntrMngDto cntrInfo = selectCntrListNoLogin(lookupNcn, roadAddrChk);
        if (cntrInfo == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        McpUserCntrMngDto prdtInfo = svcChgPageRepositoryImpl.selectRprsPrdtInfo(cntrInfo);

        String resolvedRprsPrdtId = prdtInfo != null ? prdtInfo.getRprsPrdtId() : "";
        if (StringUtils.isBlank(resolvedRprsPrdtId)) {
            // 대표단말 매핑 조회가 실패해도 ASIS trgtModelId 기준으로 보험 조회가 가능하도록 계약 modelId를 유지한다.
            resolvedRprsPrdtId = cntrInfo.getModelId();
        }
        searchVO.setRprsPrdtId(StringUtil.NVL(resolvedRprsPrdtId, ""));

        searchVO.setNcn(StringUtil.NVL(cntrInfo.getSvcCntrNo(), lookupNcn));
        searchVO.setContractNum(StringUtil.NVL(cntrInfo.getContractNum(), searchVO.getNcn()));
        searchVO.setCtn(StringUtil.NVL(cntrInfo.getCntrMobileNo(), searchVO.getCtn()));
        searchVO.setCustId(StringUtil.NVL(cntrInfo.getCustId(), searchVO.getCustId()));
        searchVO.setModelName(StringUtil.NVL(cntrInfo.getModelName(), searchVO.getModelName()));
        searchVO.setSubStatus(StringUtil.NVL(cntrInfo.getSubStatus(), searchVO.getSubStatus()));

        log.info("[resolveContractInfo] 계약정보 보강 완료 — ncn={}, contractNum={}, ctnPresent={}, custIdPresent={}",
            searchVO.getNcn(), searchVO.getContractNum(), !StringUtils.isEmpty(searchVO.getCtn()), !StringUtils.isEmpty(searchVO.getCustId()));
        return cntrInfo;
    }

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

    private Map<String, String> getStringMap(Map<String, Object> source, String key) {
        return getStringMap(source.get(key));
    }

    private Map<String, String> getStringMap(Object value) {
        if (value instanceof Map<?, ?>) {
            Map<String, String> stringMap = new HashMap<>();
            for (Map.Entry<?, ?> entry: ((Map<?, ?>) value).entrySet()) {
                if (entry.getKey() instanceof String && entry.getValue() instanceof String) {
                    stringMap.put((String) entry.getKey(), (String) entry.getValue());
                }
            }
            return stringMap;
        }
        return null;
    }


    public FormResponse<ServiceChangeCompleteResVO> complete(ServiceChangeCompleteReqDto req) throws IOException {
        long startedAt = System.currentTimeMillis();
        String ncn = req != null ? StringUtil.NVL(req.getNcn(), "") : "";
        List<String> serviceSelect = req != null && req.getServiceSelect() != null
            ? req.getServiceSelect() : new ArrayList<>();
        List<AdditionApplyReqDto> cancelList = req != null && req.getAdditionCancelList() != null
            ? req.getAdditionCancelList() : new ArrayList<>();
        List<AdditionApplyReqDto> addList = req != null && req.getAdditionList() != null
            ? req.getAdditionList() : new ArrayList<>();
        String requestMemo = normalizeLogMemo(req != null ? req.getMemo() : "");

        log.info("[serviceChangeComplete] request: ncn={}, agentCd={}, serviceSelect={}, addCount={}, cancelCount={}, memoPresent={}, memo={}",
            ncn, req == null ? "" : req.getAgentCd(), serviceSelect, addList.size(), cancelList.size(),
            StringUtils.isNotBlank(requestMemo), requestMemo);

        ServiceChangeCompleteResVO completeRes = ServiceChangeCompleteResVO.of("", addList.size(), cancelList.size());
        InsuranceProcessRequest completedInsuranceRequest = null;

        if (req == null || "".equals(StringUtil.NVL(req.getNcn(), "")) || "".equals(StringUtil.NVL(req.getCtn(), ""))) {
            log.warn("[serviceChangeComplete] invalid request: ncn={}, ctn={}",
                ncn, req != null ? req.getCtn() : "");
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }
        List<String> missingChangeServices = findMissingChangeServices(req, serviceSelect, addList, cancelList);
        if (!missingChangeServices.isEmpty()) {
            log.warn("[serviceChangeComplete] no change data: ncn={}, serviceSelect={}, missingServices={}",
                ncn, serviceSelect, missingChangeServices);
            return FormResponse.of(
                ResSvcChgMessage.CHANGE_REQUEST_INVALID,
                "선택한 서비스의 변경사항이 없습니다.",
                null);
        }

        if (req.getRequestKey() != null && msfRequestRepository.existsMsfRequestSvcChg(req.getRequestKey())) {
            log.warn("[serviceChangeComplete] duplicate request blocked: requestKey={}, ncn={}",
                req.getRequestKey(), ncn);
            return FormResponse.of(
                ResSvcChgMessage.CHANGE_REQUEST_INVALID,
                "이미 작성완료 처리된 신청입니다. 잠시 후 처리 결과를 확인해 주세요.", /* 20260722 작성완료 방어로직 메시지 수정 */
                null);
        }

        Long requestKey;
        try {
            requestKey = transactionTemplate.execute(status ->
                saveSvcChgRequest(ncn, req, cancelList, addList));
            completeRes.setRequestKey(requestKey == null ? "" : String.valueOf(requestKey));
            log.info("[serviceChangeComplete] DB pre-saved: requestKey={}, ncn={}, serviceSelect={}",
                requestKey, ncn, serviceSelect);
        } catch (ServiceChangeSaveFailureException e) {
            log.warn("[serviceChangeComplete] DB pre-save failed: ncn={}", ncn, e);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "서비스변경 작성완료 저장 중 오류가 발생했습니다.",
                null
            );
        } catch (Exception e) {
            log.error("[serviceChangeComplete] DB pre-save unexpected error: ncn={}", ncn, e);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "서비스변경 작성완료 저장 중 오류가 발생했습니다.",
                null
            );
        }

        // MCP 이관은 MSF 커밋 이후 별도 MSP 트랜잭션으로 처리한다.
        FormResponse<Void> transferResponse = transferToMcp(requestKey);
        if (!ResSvcChgMessage.SUCCESS.getCode().equals(transferResponse.resCode())) {
            return failWithSvcChgProcCd(
                requestKey,
                ResSvcChgMessage.APPLY_MCP_TRANSFER_ERROR,
                transferResponse.resMessage(),
                completeRes);
        }

        updateSvcChgProcCdQuietly(requestKey, "RQ", "", "", "");

        // R11(부가서비스) / R12(무선데이터차단): 해지 처리
        for (AdditionApplyReqDto cancelReq: cancelList) {
            fillCommonAdditionFields(cancelReq, req);
            FormResponse<AdditionApplyResVO> cancelRes = msfRegSvcServiceImpl.moscRegSvcCanChg(cancelReq);
            if (!ResSvcChgMessage.SUCCESS.getCode().equals(cancelRes.resCode())) {
                completeRes.addResult(toProcessResult("CANCEL", cancelReq, cancelRes));
                log.warn("[serviceChangeComplete] cancel failed: ncn={}, soc={}, agentCd={}, selfCareUnavailable={}, resCode={}, resMessage={}",
                    ncn, cancelReq.getSoc(), cancelReq.getAgentCd(), cancelReq.getSelfCareUnavailable(), cancelRes.resCode(), cancelRes.resMessage());
                continue;
            }
            completeRes.addResult(toProcessResult("CANCEL", cancelReq, cancelRes));
        }

        // R11(부가서비스) / R12(무선데이터차단): 신청 처리
        for (AdditionApplyReqDto addReq: addList) {
            fillCommonAdditionFields(addReq, req);
            FormResponse<AdditionApplyResVO> addRes = msfRegSvcServiceImpl.regSvcChg(addReq);
            if (!ResSvcChgMessage.SUCCESS.getCode().equals(addRes.resCode())) {
                completeRes.addResult(toProcessResult("ADD", addReq, addRes));
                log.warn("[serviceChangeComplete] reg failed: ncn={}, soc={}, agentCd={}, selfCareUnavailable={}, resCode={}, resMessage={}",
                    ncn, addReq.getSoc(), addReq.getAgentCd(), addReq.getSelfCareUnavailable(), addRes.resCode(), addRes.resMessage());
                continue;
            }
            completeRes.addResult(toProcessResult("ADD", addReq, addRes));
        }
        if (hasAdditionFailure(completeRes)) {
            log.info("[serviceChangeComplete] partial success: ncn={}, {}",
                ncn, buildAdditionProcessMessage("addition partial success", completeRes));
        }

        // P11(요금제변경)
        if (serviceSelect.contains("P11")) {

            String actCode = Optional.ofNullable(req).map(ServiceChangeCompleteReqDto::getPlanChange)
                .map(ServiceChangeCompleteReqDto.PlanChange::getActCode).orElse("");
            log.info("[serviceChangeComplete] P11 요금제변경 처리: ncn={}, actCode={}", ncn, actCode);

            if (actCode.equals("PCN")) {
                // 즉시 요금 변경
                PossibleStateCheckRequest chkReq = new PossibleStateCheckRequest();
                chkReq.setActCode(req.getPlanChange().getActCode());
                chkReq.setContractNum(StringUtil.NVL(req.getContractNum(), req.getNcn()));
                chkReq.setCustId(req.getCustId());
                chkReq.setNcn(req.getNcn());
                chkReq.setCtn(req.getCtn());
                chkReq.setCustomerSsn(req.getUserBirthDate());
                chkReq.setParentScanId(req.getParentScanId());
                chkReq.setOpeningDate(req.getPlanChange().getOpeningDate());
                chkReq.setPlanSoc(req.getPlanChange().getPlanSoc());
                chkReq.setBeforePlanSoc(req.getPlanChange().getBeforePlanSoc());
                chkReq.setBeforePlanAmt(req.getPlanChange().getBeforePlanAmt());

                PossibleStateCheckRequest.ProductInfo productInfo = new PossibleStateCheckRequest.ProductInfo();
                List<PossibleStateCheckRequest.ProductInfo> productInfoList = new ArrayList<>();
                productInfo.setPrdcCd(req.getPlanChange().getPlanSoc());
                productInfo.setFtrNewParam(req.getPlanChange().getPlanFtrNewParam());
                productInfoList.add(productInfo);
                chkReq.setPrdcList(productInfoList);
                FormResponse<PossibleStateCheckResponse> resDtoFormResponse = chargePlanChangeService.possibleStateChange(chkReq);

                boolean success = ResSvcChgMessage.SUCCESS.getCode().equals(resDtoFormResponse.resCode());
                addProcessResult(
                    completeRes,
                    "PLANCHG",
                    "",
                    "요금제 변경",
                    "",
                    success,
                    resDtoFormResponse.resCode(),
                    resolveMplatformResponseMessage(resDtoFormResponse));

                if (!ResSvcChgMessage.SUCCESS.getCode().equals(resDtoFormResponse.resCode())) {
                    log.warn("[serviceChangeComplete PCN] numberChge failed: ncn={}, resCode={}, resMessage={}",
                        ncn, resDtoFormResponse.resCode(), resDtoFormResponse.resMessage());
                }
            } else {
                // 예약 변경
                PossibleStateCheckRequest chkReq = new PossibleStateCheckRequest();
                chkReq.setActCode(req.getPlanChange().getActCode());
                chkReq.setContractNum(StringUtil.NVL(req.getContractNum(), req.getNcn()));
                chkReq.setCustId(req.getCustId());
                chkReq.setNcn(req.getNcn());
                chkReq.setCtn(req.getCtn());
                chkReq.setCustomerSsn(req.getUserBirthDate());
                chkReq.setParentScanId(req.getParentScanId());
                chkReq.setOpeningDate(req.getPlanChange().getOpeningDate());
                chkReq.setPlanSoc(req.getPlanChange().getPlanSoc());
                chkReq.setBeforePlanSoc(req.getPlanChange().getBeforePlanSoc());
                chkReq.setBeforePlanAmt(req.getPlanChange().getBeforePlanAmt());

                PossibleStateCheckRequest.ProductInfo productInfo = new PossibleStateCheckRequest.ProductInfo();
                List<PossibleStateCheckRequest.ProductInfo> productInfoList = new ArrayList<>();
                productInfo.setPrdcCd(req.getPlanChange().getPlanSoc());
                productInfo.setFtrNewParam(req.getPlanChange().getPlanFtrNewParam());
                productInfoList.add(productInfo);
                chkReq.setPrdcList(productInfoList);
                FormResponse<PossibleStateCheckResponse> resDtoFormResponse = chargePlanChangeService.reservedPriceChange(chkReq);

                boolean success = ResSvcChgMessage.SUCCESS.getCode().equals(resDtoFormResponse.resCode());
                addProcessResult(
                    completeRes,
                    "PLANRESERVECHG",
                    "",
                    "요금제 변경 예약",
                    "",
                    success,
                    resDtoFormResponse.resCode(),
                    resolveMplatformResponseMessage(resDtoFormResponse));

                if (!ResSvcChgMessage.SUCCESS.getCode().equals(resDtoFormResponse.resCode())) {
                    log.warn("[serviceChangeComplete RSV] numberChge failed: ncn={}, resCode={}, resMessage={}",
                        ncn, resDtoFormResponse.resCode(), resDtoFormResponse.resMessage());
                }
            }
        }

        // O11(번호변경)
        if (serviceSelect.contains("O11")) {
            log.info("[serviceChangeComplete] O11 번호변경 처리: ncn={}", ncn);
            //ServiceChangeCompleteReqDto.NumberChange numberChange = req.getNumberChange();
            NumberChgeProcessRequest numberChgeProcessReq = serviceChangeFieldMapper.toNumberChgeProcessRequest(req);
            FormResponse<NumberChgeProcessResponse> numberChgeProcessRes = msfSvgNumChgeService.numberChgeProcess(numberChgeProcessReq);

            boolean success = ResSvcChgMessage.SUCCESS.getCode().equals(numberChgeProcessRes.resCode());
            completeRes.addResult(
                ServiceChangeCompleteResVO.ProcessResult.of(
                    "NUMBERCHGE",
                    "",
                    "번호 변경",
                    "",
                    success,
                    StringUtil.NVL(numberChgeProcessRes.resCode(), ""),
                    resolveMplatformResponseMessage(numberChgeProcessRes)
                )
            );

            if (!ResSvcChgMessage.SUCCESS.getCode().equals(numberChgeProcessRes.resCode())) {
                log.warn("[serviceChangeComplete] numberChge failed: ncn={}, resCode={}, resMessage={}",
                    ncn, numberChgeProcessRes.resCode(), numberChgeProcessRes.resMessage());
            }

            AdditionApplyReqDto insrDto = new AdditionApplyReqDto();
            insrDto.setServiceName("번호 변경");
            //successfulCancelList.add(
            //
            //);
        }

        // O12(분실복구/일시정지해제)
        if (serviceSelect.contains("O12")) {
            log.info("[serviceChangeComplete] O12 분실복구/일시정지해제 처리: ncn={}", ncn);
            //ServiceChangeCompleteReqDto.Unpause unpause = req.getUnpause();
            UnpauseProcessRequest unpauseProcessReq = serviceChangeFieldMapper.toUnpauseProcessRequest(req);
            FormResponse<UnpauseProcessResponse> unpauseProcessRes = msfSvgUnpauseService.unpauseProcess(unpauseProcessReq);

            boolean success = ResSvcChgMessage.SUCCESS.getCode().equals(unpauseProcessRes.resCode());
            completeRes.addResult(
                ServiceChangeCompleteResVO.ProcessResult.of(
                    "UNPAUSE",
                    "",
                    "분실복구/일시정지해제",
                    "",
                    success,
                    StringUtil.NVL(unpauseProcessRes.resCode(), ""),
                    resolveMplatformResponseMessage(unpauseProcessRes)
                )
            );

            if (!ResSvcChgMessage.SUCCESS.getCode().equals(unpauseProcessRes.resCode())) {
                log.warn("[serviceChangeComplete] unpause failed: ncn={}, resCode={}, resMessage={}",
                    ncn, unpauseProcessRes.resCode(), unpauseProcessRes.resMessage());
            }

            AdditionApplyReqDto cancelDto = new AdditionApplyReqDto();
            cancelDto.setServiceName("분실복구/일시정지해제");
            //successfulCancelList.add(
            //
            //);
        }

        // R14(단말보험)
        if (serviceSelect.contains("R14")) {

            log.info("[serviceChangeComplete] R14 단말보험 처리: ncn={}", ncn);
            InsuranceProcessRequest insrReq = serviceChangeFieldMapper.toInsuranceProcessRequest(req);
            FormResponse<InsuranceProcessResponse> insrRes = msfInsuranceSvcService.insurProcessForServiceChange(insrReq);
            if ("IS".equals(insrReq.getReqType())) {
                completedInsuranceRequest = insrReq;
            }

            if (!ResSvcChgMessage.SUCCESS.getCode().equals(insrRes.resCode())) {

                log.warn("[serviceChangeComplete] insr failed: ncn={}, soc={}, resCode={}, resMessage={}",
                    ncn, insrReq.getInsrProdCd(), insrRes.resCode(), insrRes.resMessage());

                completeRes.addResult(
                    ServiceChangeCompleteResVO.ProcessResult.of(
                        "INSR",
                        "",
                        "단말보험",
                        "",
                        false,
                        StringUtil.NVL(insrRes.resCode(), ""),
                        StringUtil.NVL(insrRes.resMessage(), "")
                    )
                );
            } else {
                completeRes.addResult(
                    ServiceChangeCompleteResVO.ProcessResult.of(
                        "INSR",
                        "",
                        "단말보험",
                        "",
                        true,
                        StringUtil.NVL(insrRes.resCode(), ""),
                        StringUtil.NVL(insrRes.resMessage(), "")
                    )
                );
            }
            // completeRes.addResult(toProcessResult("INSR", cancelReq, cancelRes));
        }

        // O13(SIM정보) 유심변경 UC0
        if (serviceSelect.contains("O13")) {
            log.info("[serviceChangeComplete] O13 SIM정보 처리: ncn={}", ncn);
            UsimChangeUC0Request usimChangeUC0Request = serviceChangeFieldMapper.toUsimChangeUC0Request(req);
            usimChangeUC0Request.setRequestKey(req.getRequestKey());
            usimChangeUC0Request.setAgentCd(firstNonBlank(req.getKtOrgId(), req.getAgentCd()));
            FormResponse<UsimChangeUC0Response> uc0ResponseFormResponse = msfUsimChangeSvcService.usimChange(usimChangeUC0Request);
            boolean usimChangeSuccess = ResSvcChgMessage.SUCCESS.getCode().equals(uc0ResponseFormResponse.resCode());


            // 유심 변경 실패
            if (!usimChangeSuccess) {
                log.warn("[serviceChangeComplete] usimChange failed: ncn={}, ctn={}, resCode={}, resMessage={}",
                    ncn, usimChangeUC0Request.getCtn(), uc0ResponseFormResponse.resCode(), uc0ResponseFormResponse.resMessage());

                ServiceChangeCompleteResVO.ProcessResult result = ServiceChangeCompleteResVO.ProcessResult.of(
                    "USIM",
                    "",
                    "유심변경",
                    "",
                    false,
                    StringUtil.NVL(uc0ResponseFormResponse.resCode(), ""),
                    StringUtil.NVL(uc0ResponseFormResponse.resMessage(), "")
                );
                result.setResNo(resolveUsimOsstOrdNo(uc0ResponseFormResponse));
                completeRes.addResult(result);

            }

            if (usimChangeSuccess) {
                // 성공시
                ServiceChangeCompleteResVO.ProcessResult result = ServiceChangeCompleteResVO.ProcessResult.of(
                    "USIM",
                    "",
                    "유심변경",
                    "",
                    true,
                    StringUtil.NVL(uc0ResponseFormResponse.resCode(), ""),
                    StringUtil.NVL(uc0ResponseFormResponse.resMessage(), "")
                );
                result.setResNo(resolveUsimOsstOrdNo(uc0ResponseFormResponse));
                completeRes.addResult(result);
            }

        }

        // R15(데이터쉐어링)
        if (serviceSelect.contains("R15")) {
            log.info("[serviceChangeComplete] R15 데이터쉐어링 처리: ncn={}", ncn);
            boolean rejobX70 = isRejobX70Memo(req.getMemo());
            String rejobX70OpmdSvcNo = extractRejobX70OpmdSvcNo(req.getMemo());
            log.info("[serviceChangeComplete] R15 memo check: ncn={}, memoPresent={}, memo={}, rejobX70={}, extractedOpmdSvcNo={}",
                req.getNcn(), StringUtils.isNotBlank(requestMemo), requestMemo, rejobX70, rejobX70OpmdSvcNo);
            if (rejobX70) {
                if (StringUtils.isBlank(rejobX70OpmdSvcNo)) {
                    log.warn("[serviceChangeComplete] R15 REJOB_X70 invalid memo: ncn={}, memo={}",
                        req.getNcn(), requestMemo);
                    return failWithSvcChgProcCd(
                        requestKey,
                        ResSvcChgMessage.CHANGE_REQUEST_INVALID,
                        "Invalid REJOB_X70 memo.",
                        completeRes);
                }
                log.info("[serviceChangeComplete] R15 REJOB_X70 start: ncn={}, opmdSvcNo={}",
                    req.getNcn(), rejobX70OpmdSvcNo);
                FormResponse<Void> shareSaveRes = saveOpenedDataSharingRelation(req, rejobX70OpmdSvcNo);
                if (!ResSvcChgMessage.SUCCESS.getCode().equals(shareSaveRes.resCode())) {
                    addDataSharingProcessResult(completeRes, false, shareSaveRes.resCode(), shareSaveRes.resMessage());
                } else {
                    addDataSharingProcessResult(completeRes, true, shareSaveRes.resCode(), shareSaveRes.resMessage());
                }
            } else {
                ServiceChangeCompleteReqDto.DataSharing dataSharing = req.getDataSharing();
                if (dataSharing != null && "shareUseState1".equals(dataSharing.getShareUseState())) {
                    AppformReqDto dataSharingReqDto = new AppformReqDto();
                    dataSharingReqDto.setContractNum(req.getNcn());
                    dataSharingReqDto.setReqUsimSn(dataSharing.getShareUsimNum());
                    String mobileNo = StringUtil.NVL(req.getCtn(), "");
                    if (StringUtils.isBlank(mobileNo)) {
                        mobileNo = StringUtil.NVL(req.getMobileNo1(), "")
                            + StringUtil.NVL(req.getMobileNo2(), "")
                            + StringUtil.NVL(req.getMobileNo3(), "");
                    }
                    dataSharingReqDto.setMobileNo(mobileNo);
                    dataSharingReqDto.setCstmrType(req.getCstmrTypeCd());
                    dataSharingReqDto.setCstmrMobileFn(req.getMobileNo1());
                    dataSharingReqDto.setCstmrMobileMn(req.getMobileNo2());
                    dataSharingReqDto.setCstmrMobileRn(req.getMobileNo3());
                    // 데이터쉐어링 신청서는 AS-IS NICE 세션을 직접 조회하지 않고 서식지 요청의 인증 결과를 전달한다.
                    dataSharingReqDto.setOnlineAuthType(req.getOnlineAuthType());
                    dataSharingReqDto.setOnlineAuthInfo(req.getOnlineAuthInfo());
                    dataSharingReqDto.setSelfCstmrCi(req.getSelfCstmrCi());
                    // 데이터쉐어링 PC0는 AS-IS와 달리 MP 사전체크를 직접 호출하므로 신분증 인증값을 전달한다.
                    dataSharingReqDto.setSelfCertType(req.getSelfCertType());
                    dataSharingReqDto.setSelfIssuExprDt(req.getSelfIssuExprDt());
                    dataSharingReqDto.setSelfIssuNum(req.getSelfIssuNum());
                    dataSharingReqDto.setFathTransacId(req.getFathTransacId());
                    dataSharingReqDto.setOnOffType("Y".equals(NmcpServiceUtils.isMobile()) ? "7" : "5");
                    dataSharingReqDto.setCntpntShopId(StringUtil.NVL(req.getCntpntShopCd(), CONTPNT_SHOP_ID_MSHOP));
                    dataSharingReqDto.setAgentCode(req.getAgentCd());
                    dataSharingReqDto.setManagerCode(req.getManagerCd());
                    dataSharingReqDto.setCpntId(req.getCpntId());
                    dataSharingReqDto.setPrdtSctnCd("LTE");
                    dataSharingReqDto.setOperType(OPER_TYPE_NEW);
                    String reqWantNumber = StringUtil.NVL(dataSharing.getSharePhoneNum(), "");
                    if (StringUtils.isBlank(reqWantNumber)) {
                        reqWantNumber = mobileNo;
                    }
                    dataSharingReqDto.setReqWantNumber(reqWantNumber.length() >= 4
                        ? reqWantNumber.substring(reqWantNumber.length() - 4)
                        : reqWantNumber);
                    FormResponse<Map<String, Object>> dataSharingRes = msfSvcDataSharingSvcImpl.saveDataSharingSimple(dataSharingReqDto);
                    if (!ResSvcChgMessage.SUCCESS.getCode().equals(dataSharingRes.resCode())) {
                        addDataSharingProcessResult(completeRes, false, dataSharingRes.resCode(), dataSharingRes.resMessage());
                    } else {
                        String opmdSvcNo = extractDataSharingTlphNo(dataSharingRes.resData());
                        if (StringUtils.isBlank(opmdSvcNo)) {
                            log.warn("[serviceChangeComplete] R15 dataSharing join missing tlphNo: ncn={}", req.getNcn());
                            addDataSharingProcessResult(
                                completeRes,
                                false,
                                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR.getCode(),
                                dataSharingRes.resMessage());
                        } else {
                            FormResponse<Void> shareSaveRes = saveOpenedDataSharingRelation(req, opmdSvcNo);
                            if (!ResSvcChgMessage.SUCCESS.getCode().equals(shareSaveRes.resCode())) {
                                addDataSharingProcessResult(completeRes, false, shareSaveRes.resCode(), shareSaveRes.resMessage());
                            } else {
                                addDataSharingProcessResult(completeRes, true, shareSaveRes.resCode(), shareSaveRes.resMessage());
                            }
                        }
                    }
                } else {
                    FormResponse<Void> dataSharingRes = msfSvcMyShareDataSvcImpl.processDataSharing(req);
                    if (!ResSvcChgMessage.SUCCESS.getCode().equals(dataSharingRes.resCode())) {
                        addDataSharingProcessResult(completeRes, false, dataSharingRes.resCode(), dataSharingRes.resMessage());
                    } else {
                        addDataSharingProcessResult(completeRes, true, dataSharingRes.resCode(), dataSharingRes.resMessage());
                    }
                }
            }
        }

        // R16(결합Solo)
        if (serviceSelect.contains("R16")) {
            log.info("[serviceChangeComplete] R16 결합Solo 처리: ncn={}, ctn={}, custId={}", ncn, req.getCtn(), req.getCustId());
            CombineSelfRequest combineSelfRequest = serviceChangeFieldMapper.toCombineSelfRequest(req);
            FormResponse<CombineSelfResponse> combineSelfResponse = msfCombineSvcService.combineSelfProcess(combineSelfRequest);
            if (!ResSvcChgMessage.SUCCESS.getCode().equals(combineSelfResponse.resCode())) {
                log.warn("[serviceChangeComplete] combineSelf failed: ncn={}, ctn={}, resCode={}, resMessage={}",
                    ncn, combineSelfRequest.getCtn(), combineSelfResponse.resCode(), combineSelfResponse.resMessage());

                completeRes.addResult(
                    ServiceChangeCompleteResVO.ProcessResult.of(
                        "COMBINE",
                        "",
                        "아무나SOLO결합",
                        "",
                        false,
                        StringUtil.NVL(combineSelfResponse.resCode(), ""),
                        StringUtil.NVL(combineSelfResponse.resMessage(), "")
                    )
                );
            } else {
                completeRes.addResult(
                    ServiceChangeCompleteResVO.ProcessResult.of(
                        "COMBINE",
                        "",
                        "아무나SOLO결합",
                        "",
                        true,
                        StringUtil.NVL(combineSelfResponse.resCode(), ""),
                        StringUtil.NVL(combineSelfResponse.resMessage(), "")
                    )
                );
            }

        }

        long elapsed = System.currentTimeMillis() - startedAt;
        log.info("[serviceChangeComplete] mplatform completed: ncn={}, serviceSelect={}, addCount={}, cancelCount={}, elapsedMs={}",
            ncn, serviceSelect, addList.size(), cancelList.size(), elapsed);

        boolean hasProcessFailure = hasProcessFailure(completeRes);
        boolean hasProcessSuccess = hasProcessSuccess(completeRes);
        updateSvcChgDtlProcResultsQuietly(requestKey, completeRes);

        try {
            int inserted = mcpRequestRepository.insertMcpSvcChgCustRequestMst(requestKey, completedInsuranceRequest);
            if (inserted != 1) {
                throw new ServiceChangeSaveFailureException("service change MCP master source not found");
            }
        } catch (Exception e) {
            log.error("[serviceChangeComplete] MCP master save failed: requestKey={}, ncn={}", requestKey, ncn, e);
            return failWithSvcChgProcCd(
                requestKey,
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "서비스변경 MCP 신청정보 저장 중 오류가 발생했습니다.",
                completeRes);
        }

        if (hasProcessFailure && !hasProcessSuccess) {
            return failWithSvcChgProcCd(
                requestKey,
                resolveProcessFailureCode(completeRes),
                buildAdditionProcessMessage("service change failed", completeRes),
                completeRes);
        }

        String responseMessage = hasProcessFailure
            ? buildAdditionProcessMessage("partial success", completeRes)
            : ResSvcChgMessage.SUCCESS.getMessage();

        updateSvcChgProcCdQuietly(
            requestKey,
            "CP",
            ResSvcChgMessage.SUCCESS.getCode(),
            responseMessage,
            "");

        return FormResponse.of(ResSvcChgMessage.SUCCESS, responseMessage, completeRes);
    }

    public FormResponse<Void> transferToMcp(Long requestKey) {
        if (requestKey == null) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }
        try {
            new TransactionTemplate(mspTransactionManager).execute(status -> {
                transferToMcpInTransaction(requestKey);
                return null;
            });
            return FormResponse.of(ResSvcChgMessage.SUCCESS);
        } catch (Exception e) {
            log.error("[transferToMcp] service change MCP transfer failed: requestKey={}", requestKey, e);
            return FormResponse.of(ResSvcChgMessage.APPLY_MCP_TRANSFER_ERROR);
        }
    }

    private void transferToMcpInTransaction(Long requestKey) {
        requireInserted(
            mcpRequestRepository.insertMcpSvcChgRequestCstmr(requestKey),
            "transfer service change customer to MCP",
            requestKey);
        mcpRequestRepository.insertMcpSvcChgRequestAgent(requestKey);
    }

    private FormResponse<ServiceChangeCompleteResVO> failWithSvcChgProcCd(
        Long requestKey,
        ResSvcChgMessage message,
        String responseMessage,
        ServiceChangeCompleteResVO completeRes
    ) {
        updateSvcChgFailProcCd(requestKey, message.getCode(), responseMessage);
        return FormResponse.of(message, responseMessage, completeRes);
    }

    private void appendImageSystemFiles(
        List<ImageSystemFileUploadRequest.UploadFile> target,
        List<ServiceChangeCompleteReqDto.ImageSystemUploadFile> source,
        boolean reportFile
    ) {
        if (source == null || source.isEmpty()) {
            return;
        }

        for (ServiceChangeCompleteReqDto.ImageSystemUploadFile file: source) {
            String pathFileName = resolveImageSystemPathFileName(file);
            if (StringUtils.isBlank(pathFileName)) {
                continue;
            }
            target.add(new ImageSystemFileUploadRequest.UploadFile(
                pathFileName,
                reportFile ? "" : safe(file.getFileTypeCd()),
                file.getFilePageNo() == null ? 1 : file.getFilePageNo()
            ));
        }
    }

    private String resolveImageSystemPathFileName(ServiceChangeCompleteReqDto.ImageSystemUploadFile file) {
        if (file == null) {
            return "";
        }
        return firstNonBlank(file.getPathFileName(), file.getFilePathNm());
    }

    private FormResponse<ServiceChangeCompleteResVO> failWithSvcChgProcCd(
        Long requestKey,
        String responseCode,
        String responseMessage,
        ServiceChangeCompleteResVO completeRes
    ) {
        updateSvcChgFailProcCd(
            requestKey,
            responseCode,
            resolveProcessFailureResultMessage(completeRes, responseCode, responseMessage));
        return FormResponse.of(responseCode, responseMessage, completeRes);
    }

    private void updateSvcChgFailProcCd(Long requestKey, String responseCode, String responseMessage) {
        updateSvcChgProcCdQuietly(requestKey, "BK", responseCode, responseMessage, "");
    }

    private void updateSvcChgProcCdQuietly(
        Long requestKey,
        String procCd,
        String responseCode,
        String responseMessage,
        String responseNo
    ) {
        if (requestKey == null) {
            return;
        }
        try {
            MsfRequestSvcChgVo vo = new MsfRequestSvcChgVo();
            vo.setRequestKey(requestKey);
            vo.setProcCd(procCd);
            vo.setResCd(resolveDbResultCode(responseCode));
            vo.setResMsg(StringUtils.left(StringUtil.NVL(responseMessage, ""), SVC_CHG_RES_MSG_MAX_LENGTH));
            vo.setResNo(StringUtil.NVL(responseNo, ""));
            vo.setAmdIp(RequestUtils.getClientIp());
            vo.setAmdId(resolveLoginUserId(null));
            int updated = msfRequestRepository.updateMsfRequestSvcChgProcCd(vo);
            if (updated <= 0) {
                log.warn("[serviceChangeComplete] procCd update affected no rows: requestKey={}, procCd={}",
                    requestKey, procCd);
            }
        } catch (Exception e) {
            log.warn("[serviceChangeComplete] procCd update failed: requestKey={}, procCd={}",
                requestKey, procCd, e);
        }
    }

    private void updateSvcChgDtlProcResultsQuietly(Long requestKey, ServiceChangeCompleteResVO completeRes) {
        if (requestKey == null || completeRes == null || completeRes.getProcessResults() == null) {
            return;
        }

        for (ServiceChangeCompleteResVO.ProcessResult result: completeRes.getProcessResults()) {
            if (result == null) {
                continue;
            }
            String svcTgtCd = resolveProcessResultSvcTgtCd(result);
            if (StringUtils.isBlank(svcTgtCd)) {
                continue;
            }

            try {
                MsfRequestSvcChgDtlVo vo = new MsfRequestSvcChgDtlVo();
                vo.setRequestKey(requestKey);
                vo.setSvcTgtCd(svcTgtCd);
                vo.setProcTypeCd(StringUtil.NVL(result.getProcTypeCd(), ""));
                vo.setSocCd(StringUtil.NVL(result.getSoc(), ""));
                if (result.isSuccess()) {
                    vo.setProcCd("USIM".equals(result.getAction()) ? "RC" : "CP");
                } else {
                    vo.setProcCd("BK");
                }
                vo.setResCd(resolveDbResultCode(result.getResCode()));
                vo.setResMsg(resolveDtlResultMessage(result));
                vo.setResNo(StringUtil.NVL(result.getResNo(), ""));
                int updated = msfRequestRepository.updateMsfRequestSvcChgDtlProcResult(vo);
                if (updated == 0) {
                    log.warn("[serviceChangeComplete] dtl proc result update affected no rows: requestKey={}, svcTgtCd={}, soc={}, procTypeCd={}",
                        requestKey, svcTgtCd, vo.getSocCd(), vo.getProcTypeCd());
                }
            } catch (Exception e) {
                log.warn("[serviceChangeComplete] dtl proc result update failed: requestKey={}, svcTgtCd={}",
                    requestKey, svcTgtCd, e);
            }
        }
    }

    private String resolveDbResultCode(String resCode) {
        String safeResCode = StringUtil.NVL(resCode, "");
        // DB RES_CD 자리수를 초과하는 외부 IF 오류코드만 뒤 4자리로 저장한다.
        return safeResCode.length() > 10 ? StringUtils.right(safeResCode, 4) : safeResCode;
    }

    private String resolveProcessFailureResultMessage(
        ServiceChangeCompleteResVO result,
        String responseCode,
        String fallbackMessage
    ) {
        if (result != null && result.getProcessResults() != null) {
            Optional<ServiceChangeCompleteResVO.ProcessResult> failureResult = result.getProcessResults().stream()
                .filter(processResult -> processResult != null && !processResult.isSuccess())
                .findFirst();
            if (failureResult.isPresent()) {
                String resultCode = firstNonBlank(failureResult.get().getResCode(), responseCode);
                String resultMessage = StringUtil.NVL(failureResult.get().getResMessage(), "");
                return firstNonBlank(
                    (resultCode + " " + resultMessage).trim(),
                    resultCode,
                    resultMessage,
                    fallbackMessage);
            }
        }
        return firstNonBlank(
            (StringUtil.NVL(responseCode, "") + " " + StringUtil.NVL(fallbackMessage, "")).trim(),
            responseCode,
            fallbackMessage);
    }

    private String resolveDtlResultMessage(ServiceChangeCompleteResVO.ProcessResult result) {
        if (result == null) {
            return "";
        }
        String resCode = StringUtil.NVL(result.getResCode(), "");
        String resMessage = StringUtil.NVL(result.getResMessage(), "");
        String message = result.isSuccess() ? resMessage : firstNonBlank(resCode + " " + resMessage, resCode, resMessage);
        return StringUtils.left(message.trim(), SVC_CHG_RES_MSG_MAX_LENGTH);
    }

    private String resolveProcessResultSvcTgtCd(ServiceChangeCompleteResVO.ProcessResult result) {
        if (result == null) {
            return "";
        }
        if (StringUtils.isNotBlank(result.getSvcTgtCd())) {
            return result.getSvcTgtCd();
        }
        return switch (StringUtil.NVL(result.getAction(), "")) {
            case "PLANCHG", "PLANRESERVECHG" -> "P11";
            case "NUMBERCHGE" -> "O11";
            case "UNPAUSE" -> "O12";
            case "INSR" -> "R14";
            case "USIM" -> "O13";
            case "DATASHARING" -> "R15";
            case "COMBINE" -> "R16";
            default -> "";
        };
    }

    private String resolveUsimOsstOrdNo(FormResponse<UsimChangeUC0Response> response) {
        if (response == null || response.resData() == null || response.resData().getOutDto() == null) {
            return "";
        }
        return StringUtil.NVL(response.resData().getOutDto().getOsstOrdNo(), "");
    }

    private ServiceChangeCompleteResVO.ProcessResult toProcessResult(
        String action,
        AdditionApplyReqDto req,
        FormResponse<AdditionApplyResVO> response
    ) {
        boolean success = ResSvcChgMessage.SUCCESS.getCode().equals(response.resCode());
        String soc = req == null ? "" : StringUtil.NVL(req.getSoc(), "");
        String serviceName = resolveAdditionServiceName(req);
        String prodHstSeq = req == null ? "" : StringUtil.NVL(req.getProdHstSeq(), "");
        ServiceChangeCompleteResVO.ProcessResult result = ServiceChangeCompleteResVO.ProcessResult.of(
            action,
            soc,
            serviceName,
            prodHstSeq,
            success,
            StringUtil.NVL(response.resCode(), ""),
            StringUtil.NVL(response.resMessage(), "")
        );
        result.setSvcTgtCd(req == null ? "" : StringUtil.NVL(req.getSvcTgtCd(), ""));
        if ("CANCEL".equals(action)) {
            result.setProcTypeCd("C");
        } else if ("ADD".equals(action)) {
            result.setProcTypeCd(resolveSocAddProcTypeCd());
        }
        return result;
    }

    private void addDataSharingProcessResult(
        ServiceChangeCompleteResVO completeRes,
        boolean success,
        String resCode,
        String resMessage
    ) {
        addProcessResult(
            completeRes,
            "DATASHARING",
            "R15",
            "데이터쉐어링",
            "",
            success,
            resCode,
            resMessage);
    }

    private void addProcessResult(
        ServiceChangeCompleteResVO completeRes,
        String action,
        String soc,
        String serviceName,
        String prodHstSeq,
        boolean success,
        String resCode,
        String resMessage
    ) {
        if (completeRes == null) {
            return;
        }
        completeRes.addResult(
            ServiceChangeCompleteResVO.ProcessResult.of(
                StringUtil.NVL(action, ""),
                StringUtil.NVL(soc, ""),
                StringUtil.NVL(serviceName, ""),
                StringUtil.NVL(prodHstSeq, ""),
                success,
                StringUtil.NVL(resCode, ""),
                StringUtil.NVL(resMessage, "")
            )
        );
    }

    private String resolveMplatformResponseMessage(FormResponse<? extends MplatformBase> response) {
        if (response == null) {
            return "";
        }
        String responseBasic = Optional.ofNullable(response.resData())
            .map(MplatformBase::getCommHeader)
            .map(MplatformBase.CommHeader::getResponseBasic)
            .orElse("");
        if (!"".equals(responseBasic)) {
            return StringUtil.NVL(responseBasic, "");
        }
        return StringUtil.NVL(response.resMessage(), "");
    }

    private List<String> findMissingChangeServices(
        ServiceChangeCompleteReqDto req,
        List<String> serviceSelect,
        List<AdditionApplyReqDto> addList,
        List<AdditionApplyReqDto> cancelList
    ) {
        List<String> missing = new ArrayList<>();
        if (serviceSelect == null || serviceSelect.isEmpty()) {
            missing.add("serviceSelect");
            return missing;
        }
        if (serviceSelect.contains("R11") && !hasAdditionChange("R11", addList, cancelList)) {
            missing.add("R11");
        }
        if (serviceSelect.contains("R12") && !hasAdditionChange("R12", addList, cancelList)) {
            missing.add("R12");
        }
        if (serviceSelect.contains("P11") && !hasPlanChangeData(req == null ? null : req.getPlanChange())) {
            missing.add("P11");
        }
        if (serviceSelect.contains("O11") && !hasNumberChangeData(req == null ? null : req.getNumberChange())) {
            missing.add("O11");
        }
        if (serviceSelect.contains("O12") && !hasUnpauseData(req == null ? null : req.getUnpause())) {
            missing.add("O12");
        }
        if (serviceSelect.contains("R14") && !hasInsuranceData(req == null ? null : req.getInsurance())) {
            missing.add("R14");
        }
        if (serviceSelect.contains("O13") && !hasSimInfoData(req == null ? null : req.getSimInfo())) {
            missing.add("O13");
        }
        if (serviceSelect.contains("R15") && !hasDataSharingData(req == null ? null : req.getDataSharing())) {
            missing.add("R15");
        }
        if (serviceSelect.contains("R16") && !hasCombineSoloData(req == null ? null : req.getCombineSolo())) {
            missing.add("R16");
        }
        return missing;
    }

    private boolean hasAdditionChange(
        String serviceCode,
        List<AdditionApplyReqDto> addList,
        List<AdditionApplyReqDto> cancelList
    ) {
        return hasAdditionChange(serviceCode, addList) || hasAdditionChange(serviceCode, cancelList);
    }

    private boolean hasAdditionChange(String serviceCode, List<AdditionApplyReqDto> changeList) {
        if (changeList == null) {
            return false;
        }
        for (AdditionApplyReqDto change: changeList) {
            if (change == null || isBlank(change.getSoc())) {
                continue;
            }
            String svcTgtCd = safe(change.getSvcTgtCd());
            if (serviceCode.equals(svcTgtCd)) {
                return true;
            }
            if (isBlank(svcTgtCd)) {
                boolean wirelessDataBlock = isWirelessDataBlockSoc(change);
                if (("R12".equals(serviceCode) && wirelessDataBlock)
                    || ("R11".equals(serviceCode) && !wirelessDataBlock)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean hasPlanChangeData(ServiceChangeCompleteReqDto.PlanChange planChange) {
        return planChange != null
            && !isBlank(planChange.getActCode())
            && !isBlank(planChange.getPlanSoc());
    }

    private boolean hasNumberChangeData(ServiceChangeCompleteReqDto.NumberChange numberChange) {
        return numberChange != null
            && (!isBlank(numberChange.getWishNo())
            || !isBlank(joinParts(numberChange.getReqWantFnNo(), numberChange.getReqWantMnNo(), numberChange.getReqWantRnNo())));
    }

    private boolean hasUnpauseData(ServiceChangeCompleteReqDto.Unpause unpause) {
        return unpause != null && !isBlank(unpause.getUnLockPw());
    }

    private boolean hasInsuranceData(ServiceChangeCompleteReqDto.Insurance insurance) {
        return insurance != null && !isBlank(insurance.getInsrProdCd());
    }

    private boolean hasSimInfoData(ServiceChangeCompleteReqDto.SimInfo simInfo) {
        return simInfo != null
            && (!isBlank(simInfo.getReqUsimSn())
            || !isBlank(simInfo.getSimTypeCd())
            || !isBlank(simInfo.getEid())
            || !isBlank(simInfo.getImei1())
            || !isBlank(simInfo.getImei2()));
    }

    private boolean hasDataSharingData(ServiceChangeCompleteReqDto.DataSharing dataSharing) {
        return dataSharing != null
            && (!isBlank(dataSharing.getShareUseState())
            || !isBlank(dataSharing.getSharePhoneNum())
            || !isBlank(dataSharing.getShareUsimNum())
            || !isBlank(dataSharing.getDataSharingTargetNo()));
    }

    private boolean hasCombineSoloData(ServiceChangeCompleteReqDto.CombineSolo combineSolo) {
        return combineSolo != null && !isBlank(combineSolo.getSoloData());
    }

    private boolean hasAdditionFailure(ServiceChangeCompleteResVO result) {
        if (result == null) {
            return false;
        }
        return result.getCancelFailCount() + result.getAddFailCount() > 0;
    }

    private boolean hasProcessFailure(ServiceChangeCompleteResVO result) {
        if (result == null || result.getProcessResults() == null) {
            return false;
        }
        return result.getProcessResults().stream()
            .anyMatch(processResult -> processResult != null && !processResult.isSuccess());
    }

    private boolean hasProcessSuccess(ServiceChangeCompleteResVO result) {
        if (result == null || result.getProcessResults() == null) {
            return false;
        }
        return result.getProcessResults().stream()
            .anyMatch(processResult -> processResult != null && processResult.isSuccess());
    }

    private String resolveProcessFailureCode(ServiceChangeCompleteResVO result) {
        if (result == null || result.getProcessResults() == null) {
            return ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR.getCode();
        }
        return result.getProcessResults().stream()
            .filter(processResult -> processResult != null && !processResult.isSuccess())
            .map(ServiceChangeCompleteResVO.ProcessResult::getResCode)
            .filter(StringUtils::isNotBlank)
            .findFirst()
            .orElse(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR.getCode());
    }

    private String resolveAdditionServiceName(AdditionApplyReqDto req) {
        if (req == null) {
            return "";
        }
        String serviceName = StringUtil.NVL(req.getServiceName(), "");
        if (!"".equals(serviceName)) {
            return serviceName;
        }
        String soc = StringUtil.NVL(req.getSoc(), "");
        if ("".equals(soc)) {
            return "";
        }
        try {
            MspRateMstDto mspRateMstDto = getMspRateMst(soc);
            if (mspRateMstDto != null) {
                return StringUtil.NVL(mspRateMstDto.getRateNm(), "");
            }
        } catch (Exception e) {
            log.debug("[serviceChangeComplete] failed to resolve service name: soc={}, msg={}", soc, e.getMessage());
        }
        return "";
    }

    private String resolvePlanSocName(String planCd, String svcTgtCd) {
        String safePlanCd = StringUtil.NVL(planCd, "");
        if (!"".equals(safePlanCd)) {
            try {
                MspRateMstDto mspRateMstDto = getMspRateMst(safePlanCd);
                if (mspRateMstDto != null) {
                    String rateNm = StringUtil.NVL(mspRateMstDto.getRateNm(), "");
                    if (!"".equals(rateNm)) {
                        return rateNm;
                    }
                }
            } catch (Exception e) {
                log.debug("[serviceChangeComplete] failed to resolve plan SOC name: planCd={}, msg={}", safePlanCd, e.getMessage());
            }
        }
        return resolveServiceTargetName(svcTgtCd);
    }

    private String resolveServiceTargetName(String svcTgtCd) {
        String safeSvcTgtCd = StringUtil.NVL(svcTgtCd, "");
        if ("".equals(safeSvcTgtCd)) {
            return "";
        }
        try {
            CommonCodeGroups commonCodeGroups = commonCodeReader.getCommonCodes(CommonCodesRequest.of("SVC_TGT_CD"));
            return commonCodeGroups.getSingleGroup(safeSvcTgtCd)
                .map(CommonCodeData::title)
                .filter(StringUtils::isNotBlank)
                .orElse("");
        } catch (Exception e) {
            log.debug("[serviceChangeComplete] failed to resolve service target name: svcTgtCd={}, msg={}", safeSvcTgtCd, e.getMessage());
            return "";
        }
    }

    private String resolveDataSharingSocName(ServiceChangeCompleteReqDto.DataSharing dataSharing) {
        String actionName = dataSharing != null && "shareUseState2".equals(dataSharing.getShareUseState()) ? "해지" : "가입";
        String serviceName = resolveServiceTargetName(dataSharing == null ? "" : dataSharing.getSvcTgtCd());
        if (StringUtils.isNotBlank(serviceName)) {
            return serviceName.replace("가입/해지", actionName);
        }
        return "데이터쉐어링 " + actionName;
    }

    private String buildAdditionProcessMessage(String title, ServiceChangeCompleteResVO result) {
        if (result == null || result.getProcessResults() == null) {
            return "서비스변경 처리에 실패했습니다.";
        }
        List<String> failedMessages = new ArrayList<>();
        for (ServiceChangeCompleteResVO.ProcessResult processResult: result.getProcessResults()) {
            if (processResult.isSuccess()) {
                continue;
            }
            String message = StringUtil.NVL(processResult.getResMessage(), "");
            String serviceLabel = buildAdditionServiceLabel(processResult);
            String actionName = resolveProcessActionName(processResult.getAction());
            failedMessages.add(serviceLabel + ("".equals(actionName) ? "" : " " + actionName) + " 실패"
                + ("".equals(message) ? "" : ": " + message));
        }
        String titleMessage = title != null && title.contains("partial")
            ? "일부 서비스만 처리되었습니다."
            : "서비스변경 처리에 실패했습니다.";
        int totalCount = result.getProcessResults().size();
        int successCount = (int) result.getProcessResults().stream()
            .filter(processResult -> processResult != null && processResult.isSuccess())
            .count();
        int failCount = (int) result.getProcessResults().stream()
            .filter(processResult -> processResult != null && !processResult.isSuccess())
            .count();
        int processedCount = successCount + failCount;
        int unprocessedCount = Math.max(0, totalCount - processedCount);
        String countMessage = "총 " + totalCount + "건 중 처리 " + processedCount + "건"
            + "(성공 " + successCount + "건/실패 " + failCount + "건), 미처리 " + unprocessedCount + "건";
        if (!failedMessages.isEmpty()) {
            List<String> distinctMessages = failedMessages.stream()
                .distinct()
                .collect(Collectors.toList());
            return titleMessage + "\n"
                + countMessage + "\n"
                + distinctMessages.stream()
                .map(message -> "- " + message)
                .collect(Collectors.joining("\n"));
        }
        return titleMessage + "\n" + countMessage;
    }

    private String resolveProcessActionName(String action) {
        if ("CANCEL".equals(action)) {
            return "해지";
        }
        if ("ADD".equals(action) || "INSR".equals(action)) {
            return "가입";
        }
        if ("PLANRESERVECHG".equals(action)) {
            return "예약";
        }
        if ("PLANCHG".equals(action)
            || "NUMBERCHGE".equals(action)
            || "UNPAUSE".equals(action)
            || "USIM".equals(action)
            || "DATASHARING".equals(action)
            || "COMBINE".equals(action)) {
            return "처리";
        }
        return "";
    }

    private String buildAdditionServiceLabel(ServiceChangeCompleteResVO.ProcessResult processResult) {
        String soc = StringUtil.NVL(processResult.getSoc(), "");
        String serviceName = StringUtil.NVL(processResult.getServiceName(), "");
        if (!"".equals(serviceName) && !"".equals(soc)) {
            return serviceName + "(" + soc + ")";
        }
        if (!"".equals(serviceName)) {
            return serviceName;
        }
        if (!"".equals(soc)) {
            return soc;
        }
        return "부가서비스";
    }

    /**
     * 서비스변경 완료처리 후 MSF 테이블에 신청 기록 저장
     * M플랫폼 처리 완료 후 호출 — MSF 저장 구간은 smartform 트랜잭션으로 묶는다.
     *
     * [저장 테이블]
     * 1. MSF_REQUEST_SVC_CHG       — 서비스변경 신청 마스터 (SQ_REQUEST_KEY 채번)
     * 2. MSF_REQUEST_CSTMR         — 고객 정보 (requestKey 공유)
     * 3. MSF_REQUEST_SVC_CHG_DTL   — SOC별 처리 상세 (SQ_REQUEST_SVC_CHG_DTL_SEQ 채번)
     *    procTypeCd: "CANCEL"=해지, "REG"=신청
     */
    private void fillCommonAdditionFields(AdditionApplyReqDto target, ServiceChangeCompleteReqDto source) {
        if (target == null || source == null) {
            return;
        }
        if ("".equals(StringUtil.NVL(target.getNcn(), ""))) {
            target.setNcn(source.getNcn());
        }
        if ("".equals(StringUtil.NVL(target.getCtn(), ""))) {
            target.setCtn(source.getCtn());
        }
        if ("".equals(StringUtil.NVL(target.getCustId(), ""))) {
            target.setCustId(source.getCustId());
        }
        if ("".equals(StringUtil.NVL(target.getAgentCd(), ""))) {
            target.setAgentCd(source.getAgentCd());
        }
        target.setParentScanId(source.getParentScanId());
    }

    private String extractDataSharingTlphNo(Map<String, Object> dataSharingResult) {
        if (dataSharingResult == null || dataSharingResult.get("tlphNo") == null) {
            return "";
        }
        return String.valueOf(dataSharingResult.get("tlphNo")).trim();
    }

    private String extractRejobX70OpmdSvcNo(String memo) {
        String value = normalizeLogMemo(memo);
        if (!value.startsWith(REJOB_X70_MEMO_PREFIX)) {
            return "";
        }
        String opmdSvcNo = normalizePhone(value.substring(REJOB_X70_MEMO_PREFIX.length()));
        return opmdSvcNo.length() >= 10 ? opmdSvcNo : "";
    }

    private boolean isRejobX70Memo(String memo) {
        return normalizeLogMemo(memo).startsWith(REJOB_X70_MEMO_PREFIX);
    }

    private String normalizeLogMemo(String memo) {
        return StringUtils.abbreviate(StringUtil.NVL(memo, "").replaceAll("[\\r\\n\\t]", " ").trim(), 500);
    }

    private FormResponse<Void> saveOpenedDataSharingRelation(ServiceChangeCompleteReqDto req, String opmdSvcNo) {
        String ctn = StringUtil.NVL(req.getCtn(), "");
        if (StringUtils.isBlank(ctn)) {
            ctn = StringUtil.NVL(req.getMobileNo1(), "")
                + StringUtil.NVL(req.getMobileNo2(), "")
                + StringUtil.NVL(req.getMobileNo3(), "");
        }

        MyShareDataReqDto shareReq = new MyShareDataReqDto();
        shareReq.setCustId(req.getCustId());
        shareReq.setNcn(req.getNcn());
        shareReq.setCtn(ctn);
        shareReq.setCrprCtn("");
        shareReq.setOpmdSvcNo("");
        shareReq.setOpmdWorkDivCd("A");

        try {
            log.info("[serviceChangeComplete] R15 X69 precheck after open: ncn={}, crprCtnPresent={}, x70OpmdSvcNoPresent={}",
                req.getNcn(), false, StringUtils.isNotBlank(opmdSvcNo));
            MoscDataSharingResDto chkRes = msfSvcMyShareDataSvcImpl.moscDataSharingChk(shareReq);
            boolean available = hasAvailableDataSharingTarget(chkRes);
            if (!available) {
                log.warn(
                    "[serviceChangeComplete] R15 X69 precheck after open failed: ncn={}, opmdSvcNo={}, resultCode={}, itemCount={}, availableSvcNos={}",
                    req.getNcn(),
                    opmdSvcNo,
                    chkRes != null ? chkRes.getResultCode() : "",
                    chkRes != null && chkRes.getSharingList() != null ? chkRes.getSharingList().size() : 0,
                    extractAvailableDataSharingSvcNos(chkRes));
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "Data sharing target is not available.", null);
            }

            // X69 응답에서 rsltInd=Y인 첫 번째 svcNo를 opmdSvcNo로 사용 (NU2 배정 번호 아님)
            String x69SvcNo = chkRes.getSharingList().stream()
                .filter(item -> "Y".equals(item.getRsltInd()))
                .map(OutDataSharingDto::getSvcNo)
                .map(this::normalizePhone)
                .filter(StringUtils::isNotBlank)
                .findFirst()
                .orElse("");
            String finalOpmdSvcNo = StringUtils.isNotBlank(x69SvcNo) ? x69SvcNo : opmdSvcNo;
            shareReq.setOpmdSvcNo(finalOpmdSvcNo);  //일단 X69 결과번호 우선으로 셋팅
            //20260623  ASIS myShareDataReqDto.setOpmdSvcNo(cntrList.getUnSvcNo()); //실제 셋팅X NULL은허용X

            log.info("[serviceChangeComplete] R15 X70 save after open request: ncn={}, workDivCd=A, x69SvcNo={}, finalOpmdSvcNo={}",
                req.getNcn(), x69SvcNo, finalOpmdSvcNo);
            msfSvcMyShareDataSvcImpl.moscDataSharingSaveWithParentScanId(shareReq, req.getParentScanId());
            log.info("[serviceChangeComplete] R15 X70 save after open success: ncn={}, workDivCd=A", req.getNcn());
            return FormResponse.of(ResSvcChgMessage.SUCCESS, null);
        } catch (McpCommonException e) {
            log.warn("[serviceChangeComplete] R15 X70 save after open failed: ncn={}, opmdSvcNoPresent={}, message={}",
                req.getNcn(), StringUtils.isNotBlank(opmdSvcNo), e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            log.error("[serviceChangeComplete] R15 X70 save after open unexpected error: ncn={}, opmdSvcNoPresent={}",
                req.getNcn(), StringUtils.isNotBlank(opmdSvcNo), e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "Data sharing relation save failed.", null);
        }
    }

    private boolean hasAvailableDataSharingTarget(MoscDataSharingResDto chkRes) {
        return chkRes != null
            && chkRes.getSharingList() != null
            && chkRes.getSharingList().stream()
            .anyMatch(item -> "Y".equals(item.getRsltInd()));
    }

    private String extractAvailableDataSharingSvcNos(MoscDataSharingResDto chkRes) {
        if (chkRes == null || chkRes.getSharingList() == null) {
            return "";
        }
        return chkRes.getSharingList().stream()
            .filter(item -> "Y".equals(item.getRsltInd()))
            .map(OutDataSharingDto::getSvcNo)
            .map(this::normalizePhone)
            .filter(StringUtils::isNotBlank)
            .collect(Collectors.joining(","));
    }

    private String normalizePhone(String value) {
        return StringUtil.NVL(value, "").replaceAll("[^0-9]", "");
    }

    /**
     * 서비스변경 신청서키 사전 채번
     * 고객 정보 입력 완료(다음 버튼) 시점에 eform에 전달할 requestKey를 미리 발급한다.
     */
    public FormResponse<ServiceChangeCompleteResVO> generateRequestKey() {
        Long requestKey = generateKeyRepository.getGeneratedRequestKey();
        if (requestKey == null) {
            log.error("[generateRequestKey] request key generation failed");
            return FormResponse.of(ResSvcChgMessage.ERROR, "신청서키 생성에 실패했습니다.", null);
        }
        log.debug("[generateRequestKey] requestKey generated: {}", requestKey);
        ServiceChangeCompleteResVO resVO = new ServiceChangeCompleteResVO();
        resVO.setRequestKey(String.valueOf(requestKey));
        return FormResponse.of(ResSvcChgMessage.SUCCESS, resVO);
    }

    /** 이폼서버 업로드 완료 후 SCAN_ID 후처리 업데이트 (서비스변경 전용) */
    public FormResponse<Void> updateScanId(ScanIdUpdateReqDto req) {
        if (req == null || req.getRequestKey() == null) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID, "필수 파라미터가 누락되었습니다.", null);
        }
        Long requestKey = req.getRequestKey();
        List<ServiceChangeCompleteResVO.ProcessResult> successProcessResults = req.getSuccessProcessResults() != null
            ? req.getSuccessProcessResults()
            : new ArrayList<>();
        List<String> ids = req.getDocumentId() != null
            ? req.getDocumentId().stream().filter(StringUtils::isNotBlank).collect(Collectors.toList())
            : new ArrayList<>();

        String requestForm = !ids.isEmpty() ? ids.get(0) : "";
        boolean hasDataSharingJoinForm = Boolean.TRUE.equals(req.getDataSharing());
        String dataSharingForm = hasDataSharingJoinForm
            ? firstNonBlank(ids.size() > 1 ? ids.get(1) : "", requestForm)
            : requestForm;
        int insuranceIndex = hasDataSharingJoinForm ? 2 : 1;
        String insuranceForm = firstNonBlank(ids.size() > insuranceIndex ? ids.get(insuranceIndex) : "", requestForm);

        log.info("[updateScanId] requestKey={}, requestForm={}, dataSharingForm={}, insuranceForm={}",
            requestKey, requestForm, dataSharingForm, insuranceForm);

        MsfRequestSvcChgVo chgVo = new MsfRequestSvcChgVo();
        chgVo.setRequestKey(requestKey);
        chgVo.setScanId(requestForm);
        chgVo.setSignTgtSbst(StringUtil.NVL(req.getSignTgtSbst(), ""));
        List<ServiceChangeCompleteReqDto.ImageSystemUploadFile> reportFiles = req.getReportFiles() != null
            ? req.getReportFiles()
            : new ArrayList<>();
        ServiceChangeCompleteReqDto.ImageSystemUploadFile requestReportFile = findReportFile(
            reportFiles, "servicechange", requestForm);
        if (requestReportFile == null && !reportFiles.isEmpty()) {
            requestReportFile = reportFiles.get(0);
        }
        ServiceChangeCompleteReqDto.ImageSystemUploadFile dataSharingReportFile = findReportFile(
            reportFiles, "datasharing", dataSharingForm);
        ServiceChangeCompleteReqDto.ImageSystemUploadFile insuranceReportFile = findReportFile(
            reportFiles, "insurance", insuranceForm);
        String requestFileName = resolveImageSystemPathFileName(requestReportFile);
        String dataSharingFileName = firstNonBlank(
            resolveImageSystemPathFileName(dataSharingReportFile), requestFileName);
        String insuranceFileName = firstNonBlank(
            resolveImageSystemPathFileName(insuranceReportFile), requestFileName);
        chgVo.setFileNm(requestFileName);
        chgVo.setFileMaskNm(StringUtils.substringAfterLast(requestFileName, "/"));
        msfRequestRepository.updateMsfRequestSvcChgScanId(chgVo);

        if (!successProcessResults.isEmpty()) {
            successProcessResults.stream()
                .filter(result -> result != null && result.isSuccess())
                .forEach(result -> updateSvcChgDtlScanIdByProcessResult(
                    requestKey,
                    requestForm,
                    dataSharingForm,
                    insuranceForm,
                    requestFileName,
                    dataSharingFileName,
                    insuranceFileName,
                    result));
        }

        return FormResponse.of(ResSvcChgMessage.SUCCESS, null);
    }

    /** 서비스변경 신청서 및 구비서류 이미징 시스템 업로드 */
    public FormResponse<Void> uploadImageSystem(ImageSystemUploadReqDto req) {
        if (req == null || req.getRequestKey() == null) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID, "필수 파라미터가 누락되었습니다.", null);
        }

        try {
            uploadImageSystemFilesDeferred(req);
        } catch (Exception e) {
            log.warn("[uploadImageSystem] image-system upload failed: requestKey={}, message={}",
                req.getRequestKey(), e.getMessage(), e);
            return FormResponse.of(ResSvcChgMessage.CHANGE_PROCESS_ERROR, "이미징시스템 전송에 실패했습니다.", null);
        }

        return FormResponse.of(ResSvcChgMessage.SUCCESS, null);
    }

    private void updateSvcChgDtlScanIdByProcessResult(
        Long requestKey,
        String requestForm,
        String dataSharingForm,
        String insuranceForm,
        String requestFileName,
        String dataSharingFileName,
        String insuranceFileName,
        ServiceChangeCompleteResVO.ProcessResult result
    ) {
        String svcTgtCd = StringUtil.NVL(result.getSvcTgtCd(), "");
        if (StringUtils.isBlank(svcTgtCd)) {
            return;
        }
        String scanId = resolveServiceChangeDtlScanId(svcTgtCd, requestForm, dataSharingForm, insuranceForm);
        String fileName = resolveServiceChangeDtlFileName(
            svcTgtCd, requestFileName, dataSharingFileName, insuranceFileName);
        if (StringUtils.isBlank(scanId)) {
            return;
        }
        if (("ADD".equals(result.getAction()) || "CANCEL".equals(result.getAction()))
            && StringUtils.isNotBlank(result.getSoc())) {
            MsfRequestSvcChgDtlVo dtlVo = new MsfRequestSvcChgDtlVo();
            dtlVo.setRequestKey(requestKey);
            dtlVo.setSvcTgtCd(svcTgtCd);
            dtlVo.setProcTypeCd(result.getProcTypeCd());
            dtlVo.setSocCd(result.getSoc());
            dtlVo.setScanId(scanId);
            setSvcChgDtlFileName(dtlVo, fileName);
            msfRequestRepository.updateMsfRequestSvcChgDtlSocScanId(dtlVo);
            return;
        }
        updateSvcChgDtlScanIdBySvcTgtCd(requestKey, svcTgtCd, scanId, fileName);
    }

    private void updateSvcChgDtlScanIdBySvcTgtCd(
        Long requestKey,
        String svcTgtCd,
        String scanId,
        String fileName
    ) {
        if (StringUtils.isBlank(svcTgtCd) || StringUtils.isBlank(scanId)) {
            return;
        }
        MsfRequestSvcChgDtlVo dtlVo = new MsfRequestSvcChgDtlVo();
        dtlVo.setRequestKey(requestKey);
        dtlVo.setSvcTgtCd(svcTgtCd);
        dtlVo.setScanId(scanId);
        setSvcChgDtlFileName(dtlVo, fileName);
        msfRequestRepository.updateMsfRequestSvcChgDtlScanId(dtlVo);
    }

    private void setSvcChgDtlFileName(MsfRequestSvcChgDtlVo dtlVo, String fileName) {
        if (StringUtils.isBlank(fileName)) {
            return;
        }
        dtlVo.setFileNm(fileName);
        dtlVo.setFileMaskNm(StringUtils.substringAfterLast(fileName, "/"));
    }

    private String resolveServiceChangeDtlScanId(
        String svcTgtCd,
        String requestForm,
        String dataSharingForm,
        String insuranceForm
    ) {
        if ("R15".equals(svcTgtCd)) {
            return dataSharingForm;
        }
        if ("R14".equals(svcTgtCd)) {
            return insuranceForm;
        }
        return requestForm;
    }

    private String resolveServiceChangeDtlFileName(
        String svcTgtCd,
        String requestFileName,
        String dataSharingFileName,
        String insuranceFileName
    ) {
        if ("R15".equals(svcTgtCd)) {
            return dataSharingFileName;
        }
        if ("R14".equals(svcTgtCd)) {
            return insuranceFileName;
        }
        return requestFileName;
    }

    private ServiceChangeCompleteReqDto.ImageSystemUploadFile findReportFile(
        List<ServiceChangeCompleteReqDto.ImageSystemUploadFile> reportFiles,
        String documentType,
        String documentId
    ) {
        if (reportFiles == null || reportFiles.isEmpty()) {
            return null;
        }
        ServiceChangeCompleteReqDto.ImageSystemUploadFile matchedByType = reportFiles.stream()
            .filter(file -> file != null && documentType.equalsIgnoreCase(safe(file.getDocumentType())))
            .findFirst()
            .orElse(null);
        if (matchedByType != null || StringUtils.isBlank(documentId)) {
            return matchedByType;
        }
        return reportFiles.stream()
            .filter(file -> file != null && documentId.equals(file.getDocumentId()))
            .findFirst()
            .orElse(null);
    }

    private void uploadImageSystemFilesDeferred(ImageSystemUploadReqDto req) {
        List<ImageSystemFileUploadRequest.UploadFile> files = new ArrayList<>();
        appendImageSystemFiles(files, req.getReportFiles(), true);
        appendImageSystemFiles(files, req.getRequiredDocFiles(), false);

        if (files.isEmpty()) {
            log.info("[uploadImageSystem] image-system upload skipped: no files");
            return;
        }

        String orgId = firstNonBlank(req.getShopCd(), req.getCpntId(), req.getAgentCd());
        ImageSystemFileUploadRequest request = ImageSystemFileUploadRequest.builder()
            .files(files)
            .formTypeCd("servicechange")
            .operTypeCd("")
            .parentScanId(safe(req.getParentScanId()))
            .rgstPrsnId(firstNonBlank(req.getManagerCd(), resolveLoginUserId(null)))
            .orgId(orgId)
            .custNm(safe(req.getCstmrNm()))
            .memo(safe(req.getMemo()))
            .onlineYn("Y")
            .companyId(orgId)
            .build();

        List<ImageSystemPdfUploadResponse> responses = imageSystemUploader.uploadPdf(request);
        boolean hasFailure = responses == null || responses.stream().anyMatch(r -> r == null || !r.success());
        if (hasFailure) {
            throw new ServiceChangeImageSystemUploadException("image-system upload response failure");
        }

        log.info("[uploadImageSystem] image-system upload completed: fileCount={}", files.size());
    }

    private Long saveSvcChgRequest(
        String ncn,
        ServiceChangeCompleteReqDto req,
        List<AdditionApplyReqDto> cancelList,
        List<AdditionApplyReqDto> addList
    ) {
        // 사전 채번된 키가 있으면 재사용, 없으면 신규 발급 (서비스해지 패턴과 동일)
        Long requestKey = req.getRequestKey();
        if (requestKey == null) {
            requestKey = generateKeyRepository.getGeneratedRequestKey();
        }
        if (requestKey == null) {
            throw new ServiceChangeSaveFailureException("request key generation failed");
        }
        log.debug("[saveSvcChgRequest] requestKey={} (reused={})", requestKey, req.getRequestKey() != null);
        List<String> serviceSelect = req.getServiceSelect() != null ? req.getServiceSelect() : new ArrayList<>();

        applyWriterInfo(req);
        requireWriterInfo(req, requestKey);

        MsfRequestSvcChgVo svcChgVo = buildSvcChgVo(requestKey, req);
        requireInserted(msfRequestRepository.insertMsfRequestSvcChg(svcChgVo), "insert service change", requestKey);

        MsfRequestCstmrVo cstmrVo = buildSvcChgCstmrVo(requestKey, req);
        requireInserted(msfRequestRepository.insertMsfRequestCstmr(cstmrVo), "insert customer", requestKey);

        MsfRequestAgentVo agentVo = buildSvcChgAgentVo(requestKey, req);
        if (hasAgentData(agentVo)) {
            requireInserted(msfRequestRepository.insertMsfRequestAgent(agentVo), "insert agent", requestKey);
        }

        ServiceChangeDocumentIds documentIds = ServiceChangeDocumentIds.of(req);
        saveSocDtlList(requestKey, cancelList, addList, documentIds.requestForm(), req.getParentScanId());
        saveServiceTypeDtlList(requestKey, serviceSelect, req, documentIds);

        log.info("[serviceChangeComplete] DB saved: requestKey={}, ncn={}, serviceSelect={}",
            requestKey, ncn, serviceSelect);
        return requestKey;
    }

    private static class ServiceChangeDocumentIds {

        private final String requestForm;
        private final String dataSharingForm;
        private final String insuranceForm;

        private ServiceChangeDocumentIds(String requestForm, String dataSharingForm, String insuranceForm) {
            this.requestForm = requestForm;
            this.dataSharingForm = dataSharingForm;
            this.insuranceForm = insuranceForm;
        }

        private static ServiceChangeDocumentIds of(ServiceChangeCompleteReqDto req) {
            List<String> ids = req != null && req.getDocumentId() != null
                ? req.getDocumentId().stream()
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toList())
                : new ArrayList<>();

            String requestForm = getOrEmpty(ids, 0);
            boolean hasDataSharingJoinForm = hasDataSharingJoinForm(req);
            String dataSharingForm = hasDataSharingJoinForm ? firstNonBlank(getOrEmpty(ids, 1), requestForm) : requestForm;
            int insuranceIndex = hasDataSharingJoinForm ? 2 : 1;
            String insuranceForm = firstNonBlank(getOrEmpty(ids, insuranceIndex), requestForm);

            return new ServiceChangeDocumentIds(requestForm, dataSharingForm, insuranceForm);
        }

        private static boolean hasDataSharingJoinForm(ServiceChangeCompleteReqDto req) {
            return req != null
                && req.getDataSharing() != null
                && "shareUseState1".equals(req.getDataSharing().getShareUseState());
        }

        private static String getOrEmpty(List<String> ids, int index) {
            return ids != null && index >= 0 && index < ids.size() ? ids.get(index) : "";
        }

        private String requestForm() {
            return requestForm;
        }

        private String dataSharingForm() {
            return dataSharingForm;
        }

        private String insuranceForm() {
            return insuranceForm;
        }
    }

    /** R11/R12: SOC 해지·신청 DTL 일괄 저장 */
    private void saveSocDtlList(
        Long requestKey,
        List<AdditionApplyReqDto> cancelList,
        List<AdditionApplyReqDto> addList,
        String scanId,
        String parentScanId
    ) {
        for (AdditionApplyReqDto cancelReq: cancelList) {
            // PROC_TYPE_CD="C"(즉시처리) — DB 처리유형코드이며 가입/해지 구분 아님
            log.info("[serviceChangeComplete] SOC cancel dtl: requestKey={}, svcTgtCd={}, soc={}, prodHstSeq={}, procTypeCd=C(즉시처리)",
                requestKey, cancelReq.getSvcTgtCd(), cancelReq.getSoc(), StringUtil.NVL(cancelReq.getProdHstSeq(), "-"));
            Long dtlSeq = nextSvcChgDtlSeq(requestKey);
            requireInserted(
                msfRequestRepository.insertMsfRequestSvcChgDtl(
                    buildSocDtlVo(dtlSeq, requestKey, cancelReq.getSvcTgtCd(), cancelReq, "D", "C", scanId, parentScanId)),
                "insert cancel dtl", requestKey);
        }
        for (AdditionApplyReqDto addReq: addList) {
            String procTypeCd = resolveSocAddProcTypeCd();
            // PROC_TYPE_CD: C=즉시처리 — DB 처리유형코드이며 가입/해지 구분 아님
            // 실제 처리 IF는 regSvcChg에서 결정한다. 가입은 Y25 단건 우선 후 실패 시 X21, flag=Y 선해지는 같은 해지 흐름 후 가입.
            log.info(
                "[serviceChangeComplete] SOC add dtl: requestKey={}, svcTgtCd={}, soc={}, flag={}, selfCareUnavailable={}, hasFtrNewParam={}, procTypeCd={}({})",
                requestKey,
                addReq.getSvcTgtCd(),
                addReq.getSoc(),
                StringUtil.NVL(addReq.getFlag(), "N"),
                addReq.getSelfCareUnavailable(),
                StringUtil.NVL(addReq.getFtrNewParam(), "").isEmpty() ? "N" : "Y",
                procTypeCd,
                "C".equals(procTypeCd) ? "즉시처리" : "예약처리");
            Long dtlSeq = nextSvcChgDtlSeq(requestKey);
            requireInserted(
                msfRequestRepository.insertMsfRequestSvcChgDtl(
                    buildSocDtlVo(
                        dtlSeq,
                        requestKey,
                        addReq.getSvcTgtCd(),
                        addReq,
                        "Y".equalsIgnoreCase(StringUtil.NVL(addReq.getFlag(), "")) ? "C" : "I",
                        procTypeCd,
                        scanId,
                        parentScanId)),
                "insert add dtl", requestKey);
        }
    }

    /** 서비스 타입별 DTL 저장 (P11/O11/O12/R14/O13/R15/R16) */
    private void saveServiceTypeDtlList(
        Long requestKey,
        List<String> serviceSelect,
        ServiceChangeCompleteReqDto req,
        ServiceChangeDocumentIds documentIds
    ) {
        String parentScanId = req.getParentScanId();
        if (serviceSelect.contains("P11") && req.getPlanChange() != null) {
            savePlanChangeDtl(requestKey, req.getPlanChange(), documentIds.requestForm(), parentScanId);
        }
        if (serviceSelect.contains("O11") && req.getNumberChange() != null) {
            saveNumberChangeDtl(requestKey, req.getNumberChange(), documentIds.requestForm(), parentScanId);
        }
        if (serviceSelect.contains("O12") && req.getUnpause() != null) {
            saveUnpauseDtl(requestKey, req.getUnpause(), documentIds.requestForm(), parentScanId);
        }
        if (serviceSelect.contains("R14") && req.getInsurance() != null) {
            saveInsuranceDtl(requestKey, req.getInsurance(), documentIds.insuranceForm(), parentScanId);
        }
        if (serviceSelect.contains("O13") && req.getSimInfo() != null) {
            saveSimInfoDtl(requestKey, req, documentIds.requestForm(), parentScanId);
        }
        if (serviceSelect.contains("R15") && req.getDataSharing() != null) {
            saveDataSharingDtl(requestKey, req.getDataSharing(), documentIds.dataSharingForm(), parentScanId);
        }
        if (serviceSelect.contains("R16") && req.getCombineSolo() != null) {
            saveCombineSoloDtl(requestKey, req.getCombineSolo(), documentIds.requestForm(), parentScanId);
        }
    }

    private void savePlanChangeDtl(Long requestKey, ServiceChangeCompleteReqDto.PlanChange p, String scanId, String parentScanId) {
        try {
            Long dtlSeq = nextSvcChgDtlSeq(requestKey);
            String procTypeCd = "PCN".equals(p.getActCode()) ? "C" : "R";
            MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), procTypeCd, scanId, parentScanId);
            vo.setSvcChgTypeCd("C");
            vo.setSocCd(StringUtil.NVL(p.getPlanCd(), ""));
            vo.setSocNm(resolvePlanSocName(p.getPlanCd(), p.getSvcTgtCd()));
            //vo.setAddtionInfo(StringUtil.NVL(p.getChangeTypeCd(), ""));
            String today = DateTimeUtil.getShortDateString().replaceAll("-", "");
            String changeDate = "";

            if ("C".equals(procTypeCd)) {
                changeDate = today;
            } else {
                String chgapyDate = DateTimeUtil.addMonths(today, +1);
                String chgDate = chgapyDate.substring(0, 6);
                changeDate = chgDate + "01";
            }
            vo.setAddtionInfo(changeDate);
            insertSvcChgDtl(vo, "insert plan dtl");
        } catch (Exception e) {
            log.debug("요금변경 날짜 계산중 오류 발생");
        }
    }

    private void saveNumberChangeDtl(Long requestKey, ServiceChangeCompleteReqDto.NumberChange p, String scanId, String parentScanId) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "C", scanId, parentScanId);
        vo.setSvcChgTypeCd("C");
        vo.setSocCd(StringUtil.NVL(p.getSvcTgtCd(), ""));
        vo.setSocNm(resolveServiceTargetName(p.getSvcTgtCd()));
        vo.setAddtionInfo(StringUtil.NVL(p.getWishNo(), ""));
        insertSvcChgDtl(vo, "insert number dtl");
    }

    private void saveUnpauseDtl(Long requestKey, ServiceChangeCompleteReqDto.Unpause p, String scanId, String parentScanId) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "C", scanId, parentScanId);
        vo.setSvcChgTypeCd("C");
        vo.setSocCd(StringUtil.NVL(p.getSvcTgtCd(), ""));
        vo.setSocNm(resolveServiceTargetName(p.getSvcTgtCd()));
        vo.setAddtionInfo(StringUtil.NVL(p.getUnLockPw(), ""));
        insertSvcChgDtl(vo, "insert unpause dtl");
    }

    private void saveInsuranceDtl(Long requestKey, ServiceChangeCompleteReqDto.Insurance p, String scanId, String parentScanId) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "C", scanId, parentScanId);
        vo.setSvcChgTypeCd("I");
        vo.setClauseInsuranceYn(StringUtil.NVL(p.getClauseInsuranceYn(), "N"));
        // 보험코드와 성격 다름
        // vo.setInsrCd(StringUtil.NVL(p.getInsrProdCd(), ""));
        vo.setSocCd(firstNonBlank(p.getInsrProdCd(), p.getSvcTgtCd()));
        vo.setSocNm(resolveServiceTargetName(p.getSvcTgtCd()));
        vo.setAddtionInfo(StringUtil.NVL(p.getCatCd(), ""));
        insertSvcChgDtl(vo, "insert insurance dtl");
    }

    private void saveSimInfoDtl(Long requestKey, ServiceChangeCompleteReqDto req, String scanId, String parentScanId) {
        ServiceChangeCompleteReqDto.SimInfo p = req.getSimInfo();
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);

        String procTypeCd = "R"; // 소켓 통신으로 R 고정
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, "O13", procTypeCd, scanId, parentScanId);
        String usimModelNm = firstNonBlank(
            productInfoService.getUsimModelNm(p.getReqUsimSn()),
            p.getReqUsimNm(),
            resolveServiceTargetName("O13")
        );
        vo.setSocCd("O13");
        vo.setSocNm(usimModelNm);
        vo.setSvcChgTypeCd("C");
        vo.setUsimBuyTypeCd(org.springframework.util.StringUtils.hasText(p.getSimPurchaseMethod()) ? p.getSimPurchaseMethod() : "N");
        vo.setAddtionInfo(p.isHasSim() ? "1" : "B".equals(p.getSimPurchaseMethod()) ? "3" : "2");
        vo.setReqUsimNm(usimModelNm);
        vo.setReqUsimSn(StringUtil.NVL(p.getReqUsimSn(), ""));
        vo.setEid(StringUtil.NVL(p.getEid(), ""));
        vo.setImei1(StringUtil.NVL(p.getImei1(), ""));
        vo.setImei2(StringUtil.NVL(p.getImei2(), ""));

        if (!"ESIM".equals(p.getSimTypeCd())) {
            MplatFormXmlSelfcareRequest y02Request = MplatFormXmlSelfcareRequest.builder()
                .ncn(req.getNcn())
                .ctn(req.getCtn())
                .custId(req.getCustId())
                .build();
            MspPrxSoapResponse mspPrxSoapResponse = msfMcpOsstPrxService.callXmlSelfService(
                List.of(), MplatformServiceType.Y02, y02Request);
            PricePlanY02ResDto y02Response = XmlConvertUtils.xmlReturnParser(
                mspPrxSoapResponse.rawXml(), PricePlanY02ResDto.class);
            MsfRequestSvcChgDtlVo rateInfoVo = new MsfRequestSvcChgDtlVo();
            if (y02Response.getCommHeader().isSuccess()) {
                rateInfoVo.setSocCd(y02Response.getOutDto().getProdId());
                rateInfoVo.setSocNm(y02Response.getOutDto().getProdNm());
                String price = y02Response.getOutDto().getFamtTarifAmt();
                rateInfoVo.setProdAmt(price != null ? Long.parseLong(price) : null);
            }
            PriceJoinUsimResponse priceJoinUsimResponse = msfUsimChangeSvcService.selectRateInfo(rateInfoVo);
            // 가입비 및 유심비 조회

            Long prodAmt = org.springframework.util.StringUtils.hasText(priceJoinUsimResponse.getSimPrice())
                ? Long.parseLong(priceJoinUsimResponse.getSimPrice())
                : null;
            vo.setProdAmt(prodAmt);

            // 가입비 및 유심비 조회
            // MsfRequestSaleVo saleVo = new MsfRequestSaleVo();
            // saleVo.setRequestKey(requestKey);
            // saleVo.setUsimPrice(prodAmt);
            // saleVo.setSocCode(rateInfoVo.getSocCd());
            // saleVo.setSocNm(rateInfoVo.getSocNm());
            // saleVo.setSocBaseChrgAmt(rateInfoVo.getProdAmt());
            // saleVo.setUsimPayMthdCd(p.isHasSim() ? "1" : "B".equals(p.getSimPurchaseMethod()) ? "3" : "2");
            // saleVo.setUsimPriceTypeCd(p.isHasSim() ? "N" : p.getSimPurchaseMethod());
            // msfRequestRepository.insertMsfRequestSale(saleVo);

        } else {
            vo.setEid(StringUtil.NVL(p.getSimTypeCd(), ""));
        }

        insertSvcChgDtl(vo, "insert sim dtl");
    }

    private void saveDataSharingDtl(Long requestKey, ServiceChangeCompleteReqDto.DataSharing p, String scanId, String parentScanId) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        String procTypeCd = "C";
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), procTypeCd, scanId, parentScanId);
        vo.setSvcChgTypeCd("shareUseState2".equals(p.getShareUseState()) ? "D" : "I");
        vo.setSocCd(StringUtil.NVL(p.getSvcTgtCd(), ""));
        vo.setSocNm(resolveDataSharingSocName(p));
        String targetNo = "shareUseState2".equals(p.getShareUseState())
            ? StringUtil.NVL(p.getDataSharingTargetNo(), p.getSharePhoneNum())
            : p.getSharePhoneNum();
        vo.setAddtionInfo(normalizePhone(targetNo));
        vo.setReqUsimSn(StringUtil.NVL(p.getShareUsimNum(), ""));
        vo.setIccId(StringUtil.NVL(p.getShareUsimNum(), ""));
        insertSvcChgDtl(vo, "insert data sharing dtl");
    }

    private void saveCombineSoloDtl(Long requestKey, ServiceChangeCompleteReqDto.CombineSolo p, String scanId, String parentScanId) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "C", scanId, parentScanId);
        vo.setSvcChgTypeCd("I");
        vo.setSocCd(StringUtil.NVL(p.getSvcTgtCd(), ""));
        vo.setSocNm(resolveServiceTargetName(p.getSvcTgtCd()));
        vo.setCombineSoloYn("Y");
        vo.setAddtionInfo(StringUtil.NVL(p.getSoloData(), ""));
        insertSvcChgDtl(vo, "insert combine solo dtl");
    }

    private Long nextSvcChgDtlSeq(Long requestKey) {
        Long dtlSeq = generateKeyRepository.getGeneratedRequestKey();
        if (dtlSeq == null) {
            throw new ServiceChangeSaveFailureException("svc chg dtl sequence generation failed: requestKey=" + requestKey);
        }
        return dtlSeq;
    }

    private void insertSvcChgDtl(MsfRequestSvcChgDtlVo dtlVo, String stepName) {
        requireInserted(
            msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo),
            stepName,
            dtlVo.getRequestKey()
        );
    }

    private void requireInserted(int inserted, String stepName, Long requestKey) {
        if (inserted <= 0) {
            throw new ServiceChangeSaveFailureException(stepName + " failed: requestKey=" + requestKey + ", inserted=" + inserted);
        }
    }

    private void requireWriterInfo(ServiceChangeCompleteReqDto req, Long requestKey) {
        if (isBlank(req.getAgentCd())) {
            throw new ServiceChangeSaveFailureException("agentCd required: requestKey=" + requestKey);
        }
        if (isBlank(req.getManagerCd())) {
            throw new ServiceChangeSaveFailureException("managerCd required: requestKey=" + requestKey);
        }
    }

    private MsfRequestSvcChgVo buildSvcChgVo(Long requestKey, ServiceChangeCompleteReqDto req) {
        MsfRequestSvcChgVo vo = new MsfRequestSvcChgVo();
        String clientIp = RequestUtils.getClientIp();
        String loginUserId = resolveLoginUserId(req);
        vo.setRequestKey(requestKey);
        vo.setCretIp(clientIp);
        vo.setCretId(loginUserId);
        vo.setAmdIp(clientIp);
        vo.setAmdId(loginUserId);
        vo.setManagerCd(StringUtil.NVL(req.getManagerCd(), ""));
        vo.setManagerNm(StringUtil.NVL(req.getManagerNm(), ""));
        vo.setAgentCd(StringUtil.NVL(req.getAgentCd(), ""));
        vo.setAgentNm(StringUtil.NVL(req.getAgentNm(), ""));
        vo.setShopCd(safe(req.getShopCd()));
        vo.setShopNm(safe(req.getShopNm()));
        vo.setRealShopNm(safe(req.getRealShopNm()));
        vo.setCpntId(safe(req.getCpntId()));
        vo.setCpntNm(safe(req.getCpntNm()));
        vo.setCntpntShopCd(safe(req.getCntpntShopCd()));
        vo.setCntpntShopNm(safe(req.getCntpntShopNm()));
        vo.setCstmrTypeCd(StringUtil.NVL(req.getCstmrTypeCd(), "NA"));
        vo.setChgMobileNo(StringUtil.NVL(req.getCtn(), ""));
        vo.setChgContractNum(StringUtil.NVL(req.getNcn(), ""));
        vo.setMemo(safe(req.getMemo()));
        vo.setRegstId(loginUserId);
        vo.setProcCd("RC");
        vo.setRecYn("N");
        vo.setAppFormYn("N");
        vo.setAppFormXmlYn("N");
        ServiceChangeCompleteReqDto.ImageSystemUploadFile reportFile = req.getReportFiles() == null || req.getReportFiles().isEmpty()
            ? null
            : req.getReportFiles().get(0);
        String pathFileName = resolveImageSystemPathFileName(reportFile);
        vo.setFileNm(pathFileName);
        vo.setFileMaskNm(StringUtils.substringAfterLast(pathFileName, "/"));
        vo.setParentScanId(StringUtil.NVL(req.getParentScanId(), ""));
        vo.setSignTgtSbst(StringUtil.NVL(req.getSignTgtSbst(), ""));
        if (req.getDocumentId() != null && !req.getDocumentId().isEmpty()) {
            vo.setScanId(req.getDocumentId().get(0));
        }
        return vo;
    }

    private MsfRequestCstmrVo buildSvcChgCstmrVo(Long requestKey, ServiceChangeCompleteReqDto req) {
        MsfRequestCstmrVo vo = new MsfRequestCstmrVo();
        vo.setRequestKey(requestKey);
        vo.setCstmrNm(safe(req.getCstmrNm()));
        vo.setCstmrNativeRrn("");
        vo.setCstmrNativeBirth("");
        vo.setCstmrNativeGenderCd("");
        vo.setCstmrPrivateCname("");
        vo.setCstmrPrivateBizNo("");
        vo.setCstmrForeignerRrn("");
        vo.setCstmrForeignerBirth("");
        vo.setCstmrForeignerGenderCd("");
        vo.setCstmrForeignerPn("");
        vo.setCstmrForeignerCountryCd("");
        vo.setCstmrForeignerNation("");
        vo.setCstmrForeignerVisaNo("");
        vo.setCstmrForeignerVdateStartDate("");
        vo.setCstmrForeignerVdateEndDate("");
        vo.setCstmrJuridicalCname("");
        vo.setCstmrJuridicalRrn("");
        vo.setCstmrJuridicalBizNo("");
        vo.setCstmrJuridicalRepNm("");
        vo.setUpjnCd("");
        vo.setBcuSbst("");
        vo.setCstmrJuridicalUserNm("");
        vo.setCstmrJuridicalBirth("");
        vo.setCstmrVisitTypeCd(safe(req.getCstmrVisitTypeCd()));
        vo.setCstmrTelFnNo(safe(req.getTelNo1()));
        vo.setCstmrTelMnNo(safe(req.getTelNo2()));
        vo.setCstmrTelRnNo(safe(req.getTelNo3()));
        String cstmrTypeCd = safe(req.getCstmrTypeCd());
        String bizNo = joinParts(req.getCstmrJuridicalBizNo1(), req.getCstmrJuridicalBizNo2(), req.getCstmrJuridicalBizNo3());
        String foreignerNo = firstNonBlank(req.getCstmrForeignerRrn(), req.getCstmrPrivateBizNo());
        if (isJuridicalCustomerType(cstmrTypeCd)) {
            vo.setCstmrJuridicalCname(safe(req.getCstmrNm()));
            vo.setCstmrJuridicalRrn(joinParts(req.getCstmrJuridicalRrn1(), req.getCstmrJuridicalRrn2()));
            vo.setCstmrJuridicalBizNo(bizNo);
            vo.setCstmrJuridicalRepNm(safe(req.getCstmrJuridicalRepNm()));
            vo.setCstmrVisitTypeCd(safe(req.getCstmrVisitTypeCd()));
        } else if ("FN".equals(cstmrTypeCd) || "FM".equals(cstmrTypeCd)) {
            vo.setCstmrForeignerBirth(safe(req.getUserBirthDate()));
            vo.setCstmrForeignerGenderCd(safe(req.getUserGender()));
            if (!bizNo.isBlank()) {
                vo.setCstmrPrivateBizNo(bizNo);
                vo.setCstmrPrivateCname(safe(req.getCstmrNm()));
            } else {
                vo.setCstmrForeignerRrn(foreignerNo);
            }
        } else {
            vo.setCstmrNativeBirth(safe(req.getUserBirthDate()));
            vo.setCstmrNativeGenderCd(safe(req.getUserGender()));
            vo.setCstmrPrivateBizNo(bizNo);
            if (!bizNo.isBlank()) {
                vo.setCstmrPrivateCname(safe(req.getCstmrNm()));
            }
        }

        String mobileFnNo = safe(req.getMobileNo1());
        String mobileMnNo = safe(req.getMobileNo2());
        String mobileRnNo = safe(req.getMobileNo3());
        // Fallback to CTN when contact mobile parts are not populated.
        String ctn = StringUtil.NVL(req.getCtn(), "").replaceAll("\\D", "");
        if ((isBlank(mobileFnNo) || isBlank(mobileMnNo) || isBlank(mobileRnNo)) && ctn.length() >= 10) {
            mobileFnNo = ctn.substring(0, 3);
            mobileMnNo = ctn.substring(3, ctn.length() - 4);
            mobileRnNo = ctn.substring(ctn.length() - 4);
        }
        vo.setCstmrMobileFnNo(mobileFnNo);
        vo.setCstmrMobileMnNo(mobileMnNo);
        vo.setCstmrMobileRnNo(mobileRnNo);
        vo.setCstmrZipcd(safe(req.getZipNo()));
        vo.setCstmrAdr(safe(req.getAddress()));
        vo.setCstmrAdrDtl(safe(req.getDetailAddress()));
        vo.setCstmrAdrBjd("");
        vo.setCstmrEmailAdr(buildEmail(req.getEmailAddr1(), req.getEmailAddr2()));
        vo.setCstmrEmailReceiveYn("N");
        vo.setCstmrReceiveTelFnNo(mobileFnNo);
        vo.setCstmrReceiveTelNmNo(mobileMnNo);
        vo.setCstmrReceiveTelRnNo(mobileRnNo);
        return vo;
    }

    private MsfRequestAgentVo buildSvcChgAgentVo(Long requestKey, ServiceChangeCompleteReqDto req) {
        MsfRequestAgentVo agentVo = new MsfRequestAgentVo();
        agentVo.setRequestKey(requestKey);
        agentVo.setMinorAgentAgrmYn("N");
        agentVo.setMinorAgentSelfInqryAgrmYn("N");

        if (isMinorCustomerType(req.getCstmrTypeCd())) {
            agentVo.setMinorAgentNm(firstNonBlank(req.getRepName(), req.getMinorAgentNm()));
            agentVo.setMinorAgentRrn(firstNonBlank(
                joinParts(req.getRepRegistrationNo1(), req.getRepRegistrationNo2()),
                joinParts(req.getRepForeignerNo1(), req.getRepForeignerNo2())
            ));
            agentVo.setMinorAgentBirth(safe(req.getRepBirthDate()));
            agentVo.setMinorAgentGenderCd(safe(req.getRepGender()));
            agentVo.setMinorAgentRelTypeCd(safe(req.getMinorAgentRelTypeCd()));
            agentVo.setMinorAgentTelFnNo(safe(req.getMinorAgentTelFnNo()));
            agentVo.setMinorAgentTelMnNo(safe(req.getMinorAgentTelMnNo()));
            agentVo.setMinorAgentTelRnNo(safe(req.getMinorAgentTelRnNo()));
            agentVo.setMinorAgentAgrmYn(isChecked(req.getRepAgree()) ? "Y" : "N");
        }

        if (isJuridicalCustomerType(req.getCstmrTypeCd())
            && "VDP".equals(safe(req.getCstmrVisitTypeCd()))) {
            agentVo.setJrdclAgentNm(firstNonBlank(req.getMinorAgentNm(), req.getRepName()));
            agentVo.setJrdclAgentRrn(toAgentBirthGender(req.getAgentBirthDate(), req.getAgentGender()));
            agentVo.setJrdclAgentRelTypeCd(safe(req.getMinorAgentRelTypeCd()));
            agentVo.setJrdclAgentTelFnNo(safe(req.getMinorAgentTelFnNo()));
            agentVo.setJrdclAgentTelMnNo(safe(req.getMinorAgentTelMnNo()));
            agentVo.setJrdclAgentTelRnNo(safe(req.getMinorAgentTelRnNo()));
        }

        return agentVo;
    }

    private static boolean isMinorCustomerType(String cstmrTypeCd) {
        return "NM".equals(safe(cstmrTypeCd)) || "FM".equals(safe(cstmrTypeCd));
    }

    private static boolean isJuridicalCustomerType(String cstmrTypeCd) {
        return "JP".equals(safe(cstmrTypeCd)) || "GO".equals(safe(cstmrTypeCd));
    }

    private static String toAgentBirthGender(String birthDate, String gender) {
        String birth = safe(birthDate).replaceAll("\\D", "");
        if (birth.isEmpty()) {
            return "";
        }

        StringBuilder agentBirthGender = new StringBuilder(birth);
        String normalizedGender = safe(gender).trim();
        if (birth.length() == 8) {
            if ("M".equalsIgnoreCase(normalizedGender)) {
                agentBirthGender.append(birth.startsWith("19") ? "1" : "3");
            } else if ("F".equalsIgnoreCase(normalizedGender)) {
                agentBirthGender.append(birth.startsWith("19") ? "2" : "4");
            } else if (normalizedGender.matches("^[1-8]$")) {
                agentBirthGender.append(normalizedGender);
            }
        }

        // JRDCL_AGENT_RRN은 생년월일 또는 생년월일+성별 뒤를 0으로 채워 13자리로 등록한다.
        return StringUtils.rightPad(agentBirthGender.toString(), 13, '0');
    }

    private static boolean hasAgentData(MsfRequestAgentVo agentVo) {
        return agentVo != null
            && (
            !isBlank(agentVo.getMinorAgentNm())
                || !isBlank(agentVo.getMinorAgentRrn())
                || !isBlank(agentVo.getMinorAgentBirth())
                || !isBlank(agentVo.getMinorAgentTelFnNo())
                || !isBlank(agentVo.getJrdclAgentNm())
                || !isBlank(agentVo.getJrdclAgentTelFnNo())
        );
    }

    private static String firstNonBlank(String... values) {
        if (values == null) {
            return "";
        }
        for (String value: values) {
            if (!isBlank(value)) {
                return value;
            }
        }
        return "";
    }

    private static boolean isChecked(Object value) {
        if (value instanceof Boolean checked) {
            return checked;
        }
        String normalized = safe(value == null ? null : String.valueOf(value));
        return "Y".equalsIgnoreCase(normalized) || "true".equalsIgnoreCase(normalized) || "1".equals(normalized);
    }

    private static String safe(String value) {
        return StringUtil.NVL(value, "");
    }

    private static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    private static String joinParts(String... values) {
        StringBuilder builder = new StringBuilder();
        boolean hasValue = false;
        for (String value: values) {
            String safeValue = safe(value);
            if (!safeValue.isBlank()) {
                hasValue = true;
            }
            builder.append(safeValue);
        }
        return hasValue ? builder.toString() : "";
    }

    private static String buildEmail(String emailId, String emailDomain) {
        String id = safe(emailId);
        String domain = safe(emailDomain);
        if (id.isBlank()) {
            return "";
        }
        if (domain.isBlank()) {
            return id;
        }
        return id + "@" + domain;
    }

    private String resolveLoginUserId(ServiceChangeCompleteReqDto req) {
        try {
            if (AuthenticationUtils.getUser() != null && !isBlank(AuthenticationUtils.getUser().getUserId())) {
                return AuthenticationUtils.getUser().getUserId();
            }
        } catch (Exception e) {
            log.debug("[serviceChangeComplete] login user resolve failed: {}", e.getMessage());
        }
        return firstNonBlank(req == null ? "" : req.getManagerCd(), "MSF_FORM");
    }

    private void applyWriterInfo(ServiceChangeCompleteReqDto req) {
        if (req == null) {
            return;
        }

        String agentCd = req.getAgentCd();
        String managerCd = req.getManagerCd();
        String managerNm = req.getManagerNm();
        String agentNm = req.getAgentNm();
        String shopCd = req.getShopCd();
        String shopNm = req.getShopNm();
        String realShopNm = req.getRealShopNm();
        String cpntId = req.getCpntId();
        String cpntNm = req.getCpntNm();
        String cntpntShopCd = req.getCntpntShopCd();
        String cntpntShopNm = req.getCntpntShopNm();
        try {
            String loginAgentOrgnId = AuthenticationUtils.getAgentCode();
            String loginShopOrgnId = AuthenticationUtils.getShopCode();
            AgencyCache agentInfo = agencyCacheReader.getAgencyOrEmpty(loginAgentOrgnId);
            AgencyCache shopInfo = agencyCacheReader.getAgencyOrEmpty(loginShopOrgnId);

            log.debug(
                "[applyWriterInfo] organization source: requestAgentCd={}, requestKtOrgId={}, requestAgentNm={}, "
                    + "loginAgentOrgnId={}, agentKtOrganizationId={}, agentOrganizationId={}, agentOrganizationName={}, "
                    + "loginShopOrgnId={}, shopKtOrganizationId={}, shopOrganizationId={}, shopOrganizationName={}",
                agentCd, req.getKtOrgId(), agentNm,
                loginAgentOrgnId, agentInfo.ktOrganizationId(), agentInfo.organizationId(), agentInfo.organizationName(),
                loginShopOrgnId, shopInfo.ktOrganizationId(), shopInfo.organizationId(), shopInfo.organizationName());

            // 서비스변경 화면에서 선택한 대리점 값은 M플랫폼 처리에도 그대로 사용한다.
            // 신청서 표시용 판매점 정보는 화면에서 cpntId/cpntNm으로 별도 구성한다.
            // 20260716 DB AGENTCD (KT조직코드로 셋팅) M모바일 조직코드는 cntpntShopCd 여기를 참조
            agentCd = firstNonBlank(req.getKtOrgId(), agentCd, agentInfo.ktOrganizationId(), loginAgentOrgnId);
            agentNm = firstNonBlank(agentNm, AuthenticationUtils.getAgentName(), agentInfo.organizationName());
            shopCd = firstNonBlank(loginShopOrgnId, shopCd);
            shopNm = firstNonBlank(shopInfo.organizationName(), AuthenticationUtils.getShopName(), shopNm);
            realShopNm = firstNonBlank(shopNm, realShopNm);
            cpntId = firstNonBlank(shopCd, cpntId);
            cpntNm = firstNonBlank(shopNm, cpntNm);
            cntpntShopCd = firstNonBlank(cntpntShopCd, loginAgentOrgnId);
            cntpntShopNm = firstNonBlank(agentNm, cntpntShopNm);
            managerCd = AuthenticationUtils.getUser().getUserId();
            managerNm = AuthenticationUtils.getUser().getUserName();
        } catch (RuntimeException ignored) {
            agentCd = firstNonBlank(req.getKtOrgId(), agentCd, "TEST_AGENT");
            managerCd = firstNonBlank(managerCd, "MSF_FORM_TEST");
            managerNm = firstNonBlank(managerNm, "MSF Form Test");
            agentNm = firstNonBlank(agentNm, "Test Agency");
            shopCd = firstNonBlank(shopCd, agentCd);
            shopNm = firstNonBlank(shopNm, agentNm);
            realShopNm = firstNonBlank(realShopNm, shopNm);
            cpntId = firstNonBlank(cpntId, shopCd);
            cpntNm = firstNonBlank(cpntNm, shopNm);
            cntpntShopCd = firstNonBlank(cntpntShopCd, agentCd);
            cntpntShopNm = firstNonBlank(cntpntShopNm, agentNm);
        }

        log.debug(
            "[applyWriterInfo] final organization: agentCd={}, agentNm={}, shopCd={}, shopNm={}, realShopNm={}, "
                + "cpntId={}, cpntNm={}, cntpntShopCd={}, cntpntShopNm={}",
            agentCd, agentNm, shopCd, shopNm, realShopNm,
            cpntId, cpntNm, cntpntShopCd, cntpntShopNm);

        req.setManagerCd(managerCd);
        req.setManagerNm(managerNm);
        req.setAgentCd(agentCd);
        req.setAgentNm(agentNm);
        req.setShopCd(shopCd);
        req.setShopNm(shopNm);
        req.setRealShopNm(realShopNm);
        req.setCpntId(cpntId);
        req.setCpntNm(cpntNm);
        req.setCntpntShopCd(cntpntShopCd);
        req.setCntpntShopNm(cntpntShopNm);
    }

    private static String resolveSocAddProcTypeCd() {
        return "C";
    }

    // USIM_PYMN_MTHD_CD 기준: B(후청구/다음달요금합산)는 예약, 그 외 R(즉납)/N(비구매)은 즉시처리.
    @SuppressWarnings("PMD.UnusedPrivateMethod")
    private static String resolveSimInfoProcTypeCd(ServiceChangeCompleteReqDto.SimInfo simInfo) {
        String simPurchaseMethod = safe(simInfo == null ? null : simInfo.getSimPurchaseMethod()).trim();
        return "B".equalsIgnoreCase(simPurchaseMethod) ? "R" : "C";
    }

    private static boolean isWirelessDataBlockSoc(AdditionApplyReqDto req) {
        return req != null && "WIRELESSC".equalsIgnoreCase(safe(req.getSoc()));
    }

    /** 서비스 타입 기반 DTL 기본 구조 생성 */
    private MsfRequestSvcChgDtlVo buildBaseDtlVo(
        Long dtlSeq,
        Long requestKey,
        String svcType,
        String procTypeCd,
        String scanId,
        String parentScanId
    ) {
        MsfRequestSvcChgDtlVo vo = new MsfRequestSvcChgDtlVo();
        String clientIp = RequestUtils.getClientIp();
        vo.setRequestSvcChgDtlSeq(dtlSeq);
        vo.setRequestKey(requestKey);
        vo.setCretIp(clientIp);
        vo.setCretId("MSF_FORM");
        vo.setAmdIp(clientIp);
        vo.setAmdId("MSF_FORM");
        vo.setSvcTgtCd(StringUtil.NVL(svcType, ""));
        vo.setProcTypeCd(StringUtil.NVL(procTypeCd, ""));
        vo.setProcCd("RC");
        vo.setResCd("");
        vo.setResMsg("");
        vo.setResNo("");
        vo.setAppFormYn("N");
        vo.setAppFormXmlYn("N");
        vo.setScanId(StringUtil.NVL(scanId, ""));
        vo.setParentScanId(StringUtil.NVL(parentScanId, ""));
        return vo;
    }

    /** SOC 기반 DTL (R11 부가서비스 / R12 무선데이터차단) */
    private MsfRequestSvcChgDtlVo buildSocDtlVo(
        Long dtlSeq,
        Long requestKey,
        String svcTgtCd,
        AdditionApplyReqDto req,
        String svcChgTypeCd,
        String procTypeCd,
        String scanId,
        String parentScanId
    ) {
        String soc = req == null ? "" : req.getSoc();
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, StringUtil.NVL(svcTgtCd, ""), procTypeCd, scanId, parentScanId);
        vo.setSvcChgTypeCd(StringUtil.NVL(svcChgTypeCd, ""));
        vo.setSocCd(StringUtil.NVL(soc, ""));
        vo.setSocNm(resolveAdditionServiceName(req));
        vo.setProdAmt(resolveAdditionProdAmt(soc));
        vo.setAddtionInfo(req == null ? "" : StringUtil.NVL(req.getFtrNewParam(), ""));
        return vo;
    }

    private Long resolveAdditionProdAmt(String soc) {
        if (isBlank(soc)) {
            return null;
        }
        try {
            MspRateMstDto mspRateMstDto = getMspRateMst(soc);
            if (mspRateMstDto != null) {
                return (long) mspRateMstDto.getBaseAmt();
            }
        } catch (Exception e) {
            log.debug("[serviceChangeComplete] failed to resolve product amount: soc={}, msg={}", soc, e.getMessage());
        }
        return null;
    }

    private static class ServiceChangeSaveFailureException extends RuntimeException {

        private ServiceChangeSaveFailureException(String message) {
            super(message);
        }
    }

    private static class ServiceChangeImageSystemUploadException extends RuntimeException {

        private ServiceChangeImageSystemUploadException(String message) {
            super(message);
        }
    }


}
