package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import org.springframework.transaction.support.TransactionTemplate;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpFarPriceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.MspRateMstDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.OutDataSharingDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.domains.form.common.repository.McpApiClient;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.common.repository.MsfRequestRepositoryImpl;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestAgentVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestClauseVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestMstVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgDtlVo;
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestSvcChgVo;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.AdditionApplyResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChangInfoViewResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.FarPricePlanResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyShareDataReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ServiceChangeCompleteResVO;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.SvcChgPageRepositoryImpl;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.port.in.AddressReader;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Service
public class MsfSvcChgPageServiceImpl {

    private static final Logger logger = LoggerFactory.getLogger(MsfSvcChgPageServiceImpl.class);

    @Autowired
    private SvcChgPageRepositoryImpl svcChgPageRepositoryImpl;

    @Autowired
    private MsfMplatFormService msfMplatFormService;

    @Autowired
    private MsfRegSvcServiceImpl msfRegSvcServiceImpl;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfFarPricePlanService farPricePlanService;

    @Autowired
    private McpApiClient mcpApiClient;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private AddressReader addressReader;

    @Autowired
    private MsfRequestRepositoryImpl msfRequestRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        logger.debug("[MsfChangPage][selectMspAddInfo] start: ncn={}, queryId={}",
            svcCntrNo, "MspMyPageMapper.selectMspAddInfo");
        try {
            // 기존 interface 호출 소스 보관
            // String callUrl = apiInterfaceServer + "/mypage/mspAddInfo";
            // RestTemplate restTemplate = new RestTemplate();
            // MspJuoAddInfoDto response = restTemplate.postForObject(callUrl, svcCntrNo, MspJuoAddInfoDto.class);
            MspJuoAddInfoDto response = svcChgPageRepositoryImpl.selectMspAddInfo(svcCntrNo);
            logger.debug("[MsfChangPage][selectMspAddInfo] response: ncn={}, hasBody={}, remainPay={}, remainMonth={}",
                svcCntrNo, response != null, response != null ? response.getRemainPay() : null, response != null ? response.getRemainMonth() : null);
            return response;
        } catch (Exception e) {
            logger.error("[MsfChangPage][selectMspAddInfo] error: ncn={}, queryId={}",
                svcCntrNo, "MspMyPageMapper.selectMspAddInfo", e);
            throw e;
        }
    }

    public List<McpUserCntrMngDto> selectCntrList(String userId) {
        Map<String, String> params = new HashMap<>();
        params.put("userId", userId);
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            params.put("customerId", userSessionDto.getCustomerId());
        }

        // [ASIS] interface 호출 소스 보관
        // RestTemplate restTemplate = new RestTemplate();
        // McpUserCntrMngDto[] resultList = restTemplate.postForObject(apiInterfaceServer + "/changePage/cntrList", params, McpUserCntrMngDto[].class);
        // List<McpUserCntrMngDto> list = Optional.ofNullable(resultList).filter(r -> r.length != 0)
        //     .map(Arrays::asList).orElse(null);
        List<McpUserCntrMngDto> list = svcChgPageRepositoryImpl.selectCntrList(params);

        if (list != null) {
            for (McpUserCntrMngDto dto : list) {
                String strUnUserSSn = dto.getUnUserSSn();
                dto.setAge(Integer.toString(getAge(strUnUserSSn)));
                if (strUnUserSSn != null && strUnUserSSn.length() > 5) {
                    dto.setBirth(strUnUserSSn.substring(0, 6));
                } else if (strUnUserSSn != null) {
                    dto.setBirth(strUnUserSSn);
                }
            }
        }
        return list;
    }

    public McpUserCntrMngDto selectCntrListNoLogin(String contractNum) {
        if (contractNum == null || "".equals(contractNum)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(contractNum);
        return selectCntrListNoLogin(userCntrMngDto);
    }

    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        if (userCntrMngDto.getSvcCntrNo() == null && userCntrMngDto.getCntrMobileNo() == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        // [ASIS] interface 호출 소스 보관
        // RestTemplate restTemplate = new RestTemplate();
        // return restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);
        McpUserCntrMngDto cntrInfo = svcChgPageRepositoryImpl.selectCntrListNoLogin(userCntrMngDto);
        applyRoadAddress(cntrInfo);
        return cntrInfo;
    }

    public MspRateMstDto getMspRateMst(String rateCd) {
        //20260508 FCommonSvc 기존소스에서 변경함
        return mcpApiClient.post("/msp/mspRateMst", rateCd, MspRateMstDto.class);
    }

    private void applyRoadAddress(McpUserCntrMngDto cntrInfo) {
        if (cntrInfo == null || StringUtils.isBlank(cntrInfo.getBanAdrPrimaryLn())) {
            return;
        }

        try {
            SearchAddressResponse response = addressReader.getListAddress(
                    new SearchAddressCondition(1, 5, cntrInfo.getBanAdrPrimaryLn()));
            if (response == null || response.list() == null || response.list().isEmpty()) {
                return;
            }

            String currentZip = StringUtil.NVL(cntrInfo.getBanAdrZip(), "");
            SearchAddressResponse.JusoResponse roadAddress = response.list().stream()
                    .filter(item -> StringUtils.equals(currentZip, item.zipNo()))
                    .findFirst()
                    .orElse(response.list().get(0));
            if (StringUtils.isBlank(roadAddress.roadAddress1())) {
                return;
            }

            cntrInfo.setBanAdrZip(StringUtil.NVL(roadAddress.zipNo(), cntrInfo.getBanAdrZip()));
            cntrInfo.setBanAdrPrimaryLn(roadAddress.roadAddress1());

            String roadReference = StringUtil.NVL(roadAddress.roadAddress2(), "");
            String detailAddress = StringUtil.NVL(cntrInfo.getBanAdrSecondaryLn(), "");
            cntrInfo.setBanAdrSecondaryLn(StringUtils.normalizeSpace((roadReference + " " + detailAddress).trim()));
        } catch (Exception e) {
            logger.info("[서비스변경] selectCntrListNoLogin 도로명주소 보정 실패: {}", e.getMessage());
        }
    }

    public FormResponse<ChangInfoViewResDto> getChangInfoView(HttpServletRequest request, MyPageSearchDto searchVO) {
        if (searchVO == null) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        logger.info("[서비스변경] getChangInfoView 조회 시작 — ncn={}, ctn={}, custId={}", searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId());

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = new ArrayList<>();

        if (StringUtils.isBlank(StringUtil.NVL(searchVO.getNcn(), searchVO.getContractNum()))) {
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        McpUserCntrMngDto cntrInfo;
        try {
            cntrInfo = resolveContractInfo(searchVO);
        } catch (McpCommonException e) {
            logger.warn("[MsfChangPage][getChangInfoView] contract info not found: {}", e.getMessage());
            return FormResponse.of(ResSvcChgMessage.CHANGE_CONTRACT_NOT_FOUND);
        } catch (Exception e) {
            logger.warn("[MsfChangPage][getChangInfoView] contract info lookup error", e);
            return FormResponse.of(ResSvcChgMessage.CHANGE_INFO_ERROR);
        }
        cntrList.add(cntrInfo);

        String userName = StringUtil.NVL(cntrInfo.getUserName(), StringUtil.NVL(searchVO.getUserName(), ""));
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
            logger.info("[서비스변경] 요금제 정보 조회 — contractNum={}", contractNum);
            mcpFarPriceDto = msfMypageSvc.selectFarPricePlan(contractNum);
            if (mcpFarPriceDto != null) {
                prvRateGrpNm = mcpFarPriceDto.getPrvRateGrpNm();
                logger.info("[서비스변경] 요금제 정보 조회 완료 — prvRateGrpNm={}", prvRateGrpNm);

                FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);
                rateAdsvcLteDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(), "- MB");
                rateAdsvcCallDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(), "- 분");
                rateAdsvcSmsDesc = StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(), "- 건");
            }
        } catch (SelfServiceException e) {
            logger.info("[서비스변경][getChangInfoView] SelfServiceException: {}", e.getMessage());
        } catch (Exception e) {
            logger.info("[서비스변경][getChangInfoView] 요금제 상세 조회 실패: {}", e.getMessage());
        }

        String addr = "-";
        String zipNo = StringUtil.NVL(cntrInfo.getBanAdrZip(), "");
        String address = StringUtil.NVL(cntrInfo.getBanAdrPrimaryLn(), "");
        String detailAddress = StringUtil.NVL(cntrInfo.getBanAdrSecondaryLn(), "");
        String initActivationDate = "-";
        String homeTel = "";
        String email = "";
        Map<String, Object> combinePayData = new HashMap<>();

        try {
            logger.info("[서비스변경] perMyktfInfo(X01) 조회 — ncn={}, ctn={}, custId={}", ncn, ctn, custId);
            MpPerMyktfInfoVO perMyktfInfo = msfMplatFormService.perMyktfInfo(ncn, ctn, custId);
            if (perMyktfInfo != null) {
                logger.info("[서비스변경] perMyktfInfo(X01) 조회 결과 — addr={}, initActivationDate={}, homeTel={}, email={}",
                    perMyktfInfo.getAddr(), perMyktfInfo.getInitActivationDate(), perMyktfInfo.getHomeTel(), perMyktfInfo.getEmail());
                addr = StringUtil.NVL(perMyktfInfo.getAddr(), "-");
                initActivationDate = StringUtil.NVL(perMyktfInfo.getInitActivationDate(), "-");
                homeTel = StringUtil.NVL(perMyktfInfo.getHomeTel(), "");
                email = StringUtil.NVL(perMyktfInfo.getEmail(), "");
            } else {
                logger.info("[서비스변경] perMyktfInfo(X01) 조회 결과 — null");
            }
        } catch (SocketTimeoutException | SelfServiceException e) {
            logger.warn("[서비스변경][getChangInfoView] perMyktfInfo 조회 실패: {}", e.getMessage());
        }

        try {
            logger.info("[서비스변경] 납부방법/명세서 조회 시작 — ncn={}, ctn={}", ncn, ctn);
            MpFarChangewayInfoVO farChgWayInfo = msfMplatFormService.farChangewayInfo(ncn, ctn, custId);
            MpMoscBilEmailInfoInVO bilEmailInfo = null;
            if (farChgWayInfo != null) {
                bilEmailInfo = msfMplatFormService.kosMoscBillInfo(ncn, ctn, custId);
            }
            combinePayData = combinePayData(farChgWayInfo, bilEmailInfo);
            logger.info("[서비스변경] combinePayData 결과 — payData={}, billData={}",
                    combinePayData.get("payData") != null, combinePayData.get("billData") != null);
        } catch (SelfServiceException e) {
            logger.warn("[서비스변경][getChangInfoView] 납부방법/명세서 조회 실패: {}", e.getMessage());
            combinePayData = combinePayData(null, null);
        } catch (Exception e) {
            logger.warn("[서비스변경][getChangInfoView] 납부방법/명세서 조회 오류", e);
            combinePayData = combinePayData(null, null);
        }

        String maskingSession = "";
        if (userSession != null) {
            maskingSession = SessionUtils.getMaskingSession() > 0 ? "Y" : "";
        }
        if ("Y".equals(maskingSession)) {
            searchVO.setUserName(userSession.getName());

            String clientIp = RequestUtils.getClientIp();
            MaskingDto maskingDto = new MaskingDto();
            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호,납부정보");
            maskingDto.setAccessIp(clientIp);
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        } else {
            searchVO.setUserName(StringMakerUtil.getName(userName));
        }

        String remindBlckYn = "";
        try {
            McpUserCntrMngDto selectSocDesc = msfMypageSvc.selectSocDesc(contractNum);
            if (selectSocDesc != null
                    && "Y".equals(selectSocDesc.getRemindYn())
                    && !StringUtils.isEmpty(selectSocDesc.getRemindProdType())) {
                remindBlckYn = "Y";
            }
            logger.info("[서비스변경] selectSocDesc 결과 — remindYn={}, remindProdType={}, remindBlckYn={}",
                    selectSocDesc != null ? selectSocDesc.getRemindYn() : "null",
                    selectSocDesc != null ? selectSocDesc.getRemindProdType() : "null",
                    remindBlckYn);
        } catch (Exception e) {
            logger.warn("[서비스변경][getChangInfoView] socDesc 조회 실패: {}", e.getMessage());
        }

        ChangInfoViewResDto response = new ChangInfoViewResDto();
        response.setCntrList(cntrList);
        response.setSearchVO(searchVO);
        response.setNcn(ncn);
        response.setContractNum(contractNum);
        response.setCtn(ctn);
        response.setCustId(custId);
        response.setModelName(modelName);
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
        response.setMaskingSession(maskingSession);
        response.setRemindBlckYn(remindBlckYn);
        logger.info("[서비스변경] getChangInfoView 화면 셋팅값 — prvRateGrpNm={}, initActivationDate={}, zipNo={}, address={}, detailAddress={}, addr={}, homeTel={}, email={}, remindBlckYn={}, payData={}, billData={}, maskingSession={}",
                prvRateGrpNm, initActivationDate, zipNo, address, detailAddress, addr, homeTel, email, remindBlckYn,
                combinePayData.get("payData") != null, combinePayData.get("billData") != null, maskingSession);
        logger.info("[서비스변경] getChangInfoView 조회 완료 — ncn={}, ctn={}, prvRateGrpNm={}, remindBlckYn={}", ncn, ctn, prvRateGrpNm, remindBlckYn);
        return FormResponse.of(ResSvcChgMessage.SUCCESS, response);
    }

    private McpUserCntrMngDto resolveContractInfo(MyPageSearchDto searchVO) {
        String lookupNcn = StringUtil.NVL(searchVO.getNcn(), searchVO.getContractNum());
        if (StringUtils.isBlank(lookupNcn)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        McpUserCntrMngDto cntrInfo = selectCntrListNoLogin(lookupNcn);
        if (cntrInfo == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        searchVO.setNcn(StringUtil.NVL(cntrInfo.getSvcCntrNo(), lookupNcn));
        searchVO.setContractNum(StringUtil.NVL(cntrInfo.getContractNum(), searchVO.getNcn()));
        searchVO.setCtn(StringUtil.NVL(cntrInfo.getCntrMobileNo(), searchVO.getCtn()));
        searchVO.setCustId(StringUtil.NVL(cntrInfo.getCustId(), searchVO.getCustId()));
        searchVO.setModelName(StringUtil.NVL(cntrInfo.getModelName(), searchVO.getModelName()));
        searchVO.setSubStatus(StringUtil.NVL(cntrInfo.getSubStatus(), searchVO.getSubStatus()));

        logger.info("[서비스변경] 계약정보 보강 완료 — ncn={}, contractNum={}, ctnPresent={}, custIdPresent={}",
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

    @SuppressWarnings("unchecked")
    private Map<String, String> getStringMap(Map<String, Object> source, String key) {
        Object value = source.get(key);
        if (value instanceof Map<?, ?>) {
            return (Map<String, String>) value;
        }
        return null;
    }

    private static int getAge(String idNum) {
        if (idNum == null || idNum.trim().length() != 13) return 0;
        char g = idNum.charAt(6);
        if (g == '*') return -1;
        String century = (g == '1' || g == '2' || g == '5' || g == '6') ? "19" : "20";
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        String birthday = century + idNum.substring(0, 6);
        int age = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4));
        if (Integer.parseInt(today.substring(4)) < Integer.parseInt(birthday.substring(4))) age--;
        return age;
    }




    public FormResponse<ServiceChangeCompleteResVO> complete(String applicationKey, ServiceChangeCompleteReqDto req) {
        long startedAt = System.currentTimeMillis();
        String ncn = req != null ? StringUtil.NVL(req.getNcn(), "") : "";
        List<String> serviceSelect = req != null && req.getServiceSelect() != null
            ? req.getServiceSelect() : new ArrayList<>();
        List<AdditionApplyReqDto> cancelList = req != null && req.getAdditionCancelList() != null
            ? req.getAdditionCancelList() : new ArrayList<>();
        List<AdditionApplyReqDto> addList = req != null && req.getAdditionList() != null
            ? req.getAdditionList() : new ArrayList<>();

        logger.info("[serviceChangeComplete] request: applicationKey={}, ncn={}, serviceSelect={}, addCount={}, cancelCount={}",
            applicationKey, ncn, serviceSelect, addList.size(), cancelList.size());

        if (req == null || "".equals(StringUtil.NVL(req.getNcn(), "")) || "".equals(StringUtil.NVL(req.getCtn(), ""))) {
            logger.warn("[serviceChangeComplete] invalid request: applicationKey={}, ncn={}, ctn={}",
                applicationKey, ncn, req != null ? req.getCtn() : "");
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        // R11(부가서비스) / R12(무선데이터차단): 해지 처리
        for (AdditionApplyReqDto cancelReq : cancelList) {
            fillCommonAdditionFields(cancelReq, req);
            FormResponse<AdditionApplyResVO> cancelRes = msfRegSvcServiceImpl.moscRegSvcCanChg(cancelReq);
            if (!ResSvcChgMessage.SUCCESS.getCode().equals(cancelRes.resCode())) {
                logger.warn("[serviceChangeComplete] cancel failed: applicationKey={}, ncn={}, soc={}, resCode={}, resMessage={}",
                    applicationKey, ncn, cancelReq.getSoc(), cancelRes.resCode(), cancelRes.resMessage());
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, cancelRes.resMessage(), null);
            }
        }

        // R11(부가서비스) / R12(무선데이터차단): 신청 처리
        for (AdditionApplyReqDto addReq : addList) {
            fillCommonAdditionFields(addReq, req);
            FormResponse<AdditionApplyResVO> addRes = msfRegSvcServiceImpl.regSvcChg(addReq);
            if (!ResSvcChgMessage.SUCCESS.getCode().equals(addRes.resCode())) {
                logger.warn("[serviceChangeComplete] reg failed: applicationKey={}, ncn={}, soc={}, resCode={}, resMessage={}",
                    applicationKey, ncn, addReq.getSoc(), addRes.resCode(), addRes.resMessage());
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, addRes.resMessage(), null);
            }
        }

        // P11(요금제변경)
        if (serviceSelect.contains("P11")) {
            logger.info("[serviceChangeComplete] P11 요금제변경 처리: applicationKey={}, ncn={}", applicationKey, ncn);
            // TODO: M플랫폼 요금제변경 API 호출 구현 필요
        }

        // O11(번호변경)
        if (serviceSelect.contains("O11")) {
            logger.info("[serviceChangeComplete] O11 번호변경 처리: applicationKey={}, ncn={}", applicationKey, ncn);
            // TODO: M플랫폼 번호변경 API 호출 구현 필요
        }

        // O12(분실복구/일시정지해제)
        if (serviceSelect.contains("O12")) {
            logger.info("[serviceChangeComplete] O12 분실복구/일시정지해제 처리: applicationKey={}, ncn={}", applicationKey, ncn);
            // TODO: M플랫폼 분실복구 API 호출 구현 필요
        }

        // R14(단말보험)
        if (serviceSelect.contains("R14")) {
            logger.info("[serviceChangeComplete] R14 단말보험 처리: applicationKey={}, ncn={}", applicationKey, ncn);
            // TODO: 단말보험 가입/미가입 처리 구현 필요
        }

        // O13(SIM정보)
        if (serviceSelect.contains("O13")) {
            logger.info("[serviceChangeComplete] O13 SIM정보 처리: applicationKey={}, ncn={}", applicationKey, ncn);
            // TODO: SIM 정보 처리 구현 필요
        }

        // R15(데이터쉐어링)
        if (serviceSelect.contains("R15")) {
            logger.info("[serviceChangeComplete] R15 데이터쉐어링 처리: applicationKey={}, ncn={}", applicationKey, ncn);
            FormResponse<Void> dataSharingRes = processDataSharing(applicationKey, req);
            if (!ResSvcChgMessage.SUCCESS.getCode().equals(dataSharingRes.resCode())) {
                return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, dataSharingRes.resMessage(), null);
            }
        }

        // R16(결합Solo)
        if (serviceSelect.contains("R16")) {
            logger.info("[serviceChangeComplete] R16 결합Solo 처리: applicationKey={}, ncn={}", applicationKey, ncn);
            // TODO: 결합Solo 처리 구현 필요
        }

        long elapsed = System.currentTimeMillis() - startedAt;
        logger.info("[serviceChangeComplete] mplatform success: applicationKey={}, ncn={}, serviceSelect={}, addCount={}, cancelCount={}, elapsedMs={}",
            applicationKey, ncn, serviceSelect, addList.size(), cancelList.size(), elapsed);

        try {
            Long requestKey = transactionTemplate.execute(status ->
                saveSvcChgRequest(applicationKey, ncn, req, cancelList, addList));
            logger.info("[serviceChangeComplete] DB transaction committed: requestKey={}, applicationKey={}, ncn={}",
                requestKey, applicationKey, ncn);
        } catch (ServiceChangeSaveFailureException e) {
            logger.warn("[serviceChangeComplete] DB save failed after mplatform success: applicationKey={}, ncn={}",
                applicationKey, ncn, e);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "서비스변경 작성완료 저장 중 오류가 발생했습니다.",
                null
            );
        } catch (Exception e) {
            logger.error("[serviceChangeComplete] DB save unexpected error after mplatform success: applicationKey={}, ncn={}",
                applicationKey, ncn, e);
            return FormResponse.of(
                ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR,
                "서비스변경 작성완료 저장 중 오류가 발생했습니다.",
                null
            );
        }

        return FormResponse.of(
            ResSvcChgMessage.SUCCESS,
            ServiceChangeCompleteResVO.of(applicationKey, addList.size(), cancelList.size())
        );
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
    }

    private FormResponse<Void> processDataSharing(String applicationKey, ServiceChangeCompleteReqDto req) {
        ServiceChangeCompleteReqDto.DataSharing dataSharing = req.getDataSharing();
        if (dataSharing == null) {
            logger.warn("[serviceChangeComplete] R15 dataSharing is empty: applicationKey={}, ncn={}",
                applicationKey, req.getNcn());
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        String workDivCd = resolveDataSharingWorkDivCd(dataSharing.getShareUseState());
        String opmdSvcNo = StringUtil.NVL(dataSharing.getSharePhoneNum(), "").replaceAll("-", "");
        if ("".equals(workDivCd) || "".equals(opmdSvcNo)) {
            logger.warn("[serviceChangeComplete] R15 invalid dataSharing: applicationKey={}, ncn={}, shareUseState={}, opmdSvcNo={}",
                applicationKey, req.getNcn(), dataSharing.getShareUseState(), opmdSvcNo);
            return FormResponse.of(ResSvcChgMessage.CHANGE_REQUEST_INVALID);
        }

        MyShareDataReqDto shareReq = new MyShareDataReqDto();
        shareReq.setCustId(req.getCustId());
        shareReq.setNcn(req.getNcn());
        shareReq.setCtn(req.getCtn());
        shareReq.setCrprCtn(req.getCtn());
        shareReq.setOpmdSvcNo(opmdSvcNo);
        shareReq.setOpmdWorkDivCd(workDivCd);
        shareReq.setIccId(StringUtil.NVL(dataSharing.getShareUsimNum(), ""));

        try {
            if ("A".equals(workDivCd)) {
                MoscDataSharingResDto chkRes = msfMplatFormService.moscDataSharingChk(
                    shareReq.getCustId(), shareReq.getNcn(), shareReq.getCtn(), shareReq.getOpmdSvcNo());
                if (!hasAvailableSharingTarget(chkRes)) {
                    logger.warn("[serviceChangeComplete] R15 precheck failed: applicationKey={}, ncn={}, opmdSvcNo={}",
                        applicationKey, req.getNcn(), opmdSvcNo);
                    return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "데이터쉐어링 가입 가능한 대상이 아닙니다.", null);
                }
            }

            msfMplatFormService.moscDataSharingSave(
                shareReq.getCustId(), shareReq.getNcn(), shareReq.getCtn(), shareReq.getOpmdSvcNo(), shareReq.getOpmdWorkDivCd());
            return FormResponse.of(ResSvcChgMessage.SUCCESS, null);
        } catch (McpCommonException e) {
            logger.warn("[serviceChangeComplete] R15 failed: applicationKey={}, ncn={}, opmdSvcNo={}, workDivCd={}, message={}",
                applicationKey, req.getNcn(), opmdSvcNo, workDivCd, e.getMessage());
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, e.getMessage(), null);
        } catch (Exception e) {
            logger.error("[serviceChangeComplete] R15 unexpected error: applicationKey={}, ncn={}, opmdSvcNo={}, workDivCd={}",
                applicationKey, req.getNcn(), opmdSvcNo, workDivCd, e);
            return FormResponse.of(ResSvcChgMessage.ADDITION_SELF_SERVICE_ERROR, "데이터쉐어링 처리 중 오류가 발생했습니다.", null);
        }
    }

    private String resolveDataSharingWorkDivCd(String shareUseState) {
        if ("shareUseState1".equals(shareUseState)) {
            return "A";
        }
        if ("shareUseState2".equals(shareUseState)) {
            return "C";
        }
        return "";
    }

    private boolean hasAvailableSharingTarget(MoscDataSharingResDto chkRes) {
        if (chkRes == null || chkRes.getSharingList() == null) {
            return false;
        }
        for (OutDataSharingDto dto : chkRes.getSharingList()) {
            if (dto != null && "Y".equals(dto.getRsltInd())) {
                return true;
            }
        }
        return false;
    }

    private Long saveSvcChgRequest(
        String applicationKey, String ncn,
        ServiceChangeCompleteReqDto req,
        List<AdditionApplyReqDto> cancelList,
        List<AdditionApplyReqDto> addList) {
        Long requestKey = svcChgPageRepositoryImpl.nextRequestKey();
        if (requestKey == null) {
            throw new ServiceChangeSaveFailureException("request key generation failed");
        }
        List<String> serviceSelect = req.getServiceSelect() != null ? req.getServiceSelect() : new ArrayList<>();

        MsfRequestSvcChgVo svcChgVo = buildSvcChgVo(requestKey, req);
        requireInserted(msfRequestRepository.insertMsfRequestSvcChg(svcChgVo), "insert service change", requestKey);

        MsfRequestCstmrVo cstmrVo = buildSvcChgCstmrVo(requestKey, req);
        requireInserted(msfRequestRepository.insertMsfRequestCstmr(cstmrVo), "insert customer", requestKey);

        MsfRequestAgentVo agentVo = buildSvcChgAgentVo(requestKey, req);
        if (hasAgentData(agentVo)) {
            requireInserted(msfRequestRepository.insertMsfRequestAgent(agentVo), "insert agent", requestKey);
        }

        requireInserted(msfRequestRepository.insertMsfRequestMst(buildSvcChgMstVo(requestKey, req)), "insert request mst", requestKey);

        for (MsfRequestClauseVo clauseVo : buildSvcChgClauseVos(requestKey, req)) {
            requireInserted(
                msfRequestRepository.insertMsfRequestClause(clauseVo),
                "insert clause:" + safe(clauseVo.getCdGroupId()),
                requestKey
            );
        }

        saveSocDtlList(requestKey, cancelList, addList);
        saveServiceTypeDtlList(requestKey, serviceSelect, req);

        logger.info("[serviceChangeComplete] DB saved: requestKey={}, ncn={}, applicationKey={}, serviceSelect={}",
            requestKey, ncn, applicationKey, serviceSelect);
        return requestKey;
    }

    /** R11/R12: SOC 해지·신청 DTL 일괄 저장 */
    private void saveSocDtlList(Long requestKey, List<AdditionApplyReqDto> cancelList, List<AdditionApplyReqDto> addList) {
        for (AdditionApplyReqDto cancelReq : cancelList) {
            logger.info("[serviceChangeComplete] SOC cancel dtl: requestKey={}, svcTgtCd={}, soc={}, prodHstSeq={}",
                requestKey, cancelReq.getSvcTgtCd(), cancelReq.getSoc(), StringUtil.NVL(cancelReq.getProdHstSeq(), "-"));
            Long dtlSeq = nextSvcChgDtlSeq(requestKey);
            requireInserted(
                msfRequestRepository.insertMsfRequestSvcChgDtl(
                    buildSocDtlVo(dtlSeq, requestKey, cancelReq.getSvcTgtCd(), cancelReq.getSoc(), "C", cancelReq.getFtrNewParam())),
                "insert cancel dtl", requestKey);
        }
        for (AdditionApplyReqDto addReq : addList) {
            logger.info("[serviceChangeComplete] SOC add dtl: requestKey={}, svcTgtCd={}, soc={}, flag={}, hasFtrNewParam={}",
                requestKey, addReq.getSvcTgtCd(), addReq.getSoc(),
                StringUtil.NVL(addReq.getFlag(), "N"),
                StringUtil.NVL(addReq.getFtrNewParam(), "").isEmpty() ? "N" : "Y");
            Long dtlSeq = nextSvcChgDtlSeq(requestKey);
            requireInserted(
                msfRequestRepository.insertMsfRequestSvcChgDtl(
                    buildSocDtlVo(dtlSeq, requestKey, addReq.getSvcTgtCd(), addReq.getSoc(), "R", addReq.getFtrNewParam())),
                "insert add dtl", requestKey);
        }
    }

    /** 서비스 타입별 DTL 저장 (P11/O11/O12/R14/O13/R15/R16) */
    private void saveServiceTypeDtlList(Long requestKey, List<String> serviceSelect, ServiceChangeCompleteReqDto req) {
        if (serviceSelect.contains("P11") && req.getPlanChange() != null) {
            savePlanChangeDtl(requestKey, req.getPlanChange());
        }
        if (serviceSelect.contains("O11") && req.getNumberChange() != null) {
            saveNumberChangeDtl(requestKey, req.getNumberChange());
        }
        if (serviceSelect.contains("O12") && req.getUnpause() != null) {
            saveUnpauseDtl(requestKey, req.getUnpause());
        }
        if (serviceSelect.contains("R14") && req.getInsurance() != null) {
            saveInsuranceDtl(requestKey, req.getInsurance());
        }
        if (serviceSelect.contains("O13") && req.getSimInfo() != null) {
            saveSimInfoDtl(requestKey, req.getSimInfo());
        }
        if (serviceSelect.contains("R15") && req.getDataSharing() != null) {
            saveDataSharingDtl(requestKey, req.getDataSharing());
        }
        if (serviceSelect.contains("R16") && req.getCombineSolo() != null) {
            saveCombineSoloDtl(requestKey, req.getCombineSolo());
        }
    }

    private void savePlanChangeDtl(Long requestKey, ServiceChangeCompleteReqDto.PlanChange p) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "R");
        vo.setSocCd(StringUtil.NVL(p.getPlanCd(), ""));
        vo.setAddtionInfo(StringUtil.NVL(p.getChangeTypeCd(), ""));
        insertSvcChgDtl(vo, "insert plan dtl");
    }

    private void saveNumberChangeDtl(Long requestKey, ServiceChangeCompleteReqDto.NumberChange p) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "R");
        vo.setAddtionInfo(StringUtil.NVL(p.getWishNo(), ""));
        insertSvcChgDtl(vo, "insert number dtl");
    }

    private void saveUnpauseDtl(Long requestKey, ServiceChangeCompleteReqDto.Unpause p) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "R");
        vo.setAddtionInfo(StringUtil.NVL(p.getUnLockPw(), ""));
        insertSvcChgDtl(vo, "insert unpause dtl");
    }

    private void saveInsuranceDtl(Long requestKey, ServiceChangeCompleteReqDto.Insurance p) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "R");
        vo.setClauseInsuranceYn(StringUtil.NVL(p.getClauseInsuranceYn(), "N"));
        vo.setInsrCd(StringUtil.NVL(p.getInsrProdCd(), ""));
        vo.setAddtionInfo(StringUtil.NVL(p.getCatCd(), ""));
        insertSvcChgDtl(vo, "insert insurance dtl");
    }

    private void saveSimInfoDtl(Long requestKey, ServiceChangeCompleteReqDto.SimInfo p) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "R");
        vo.setUsimBuyTypeCd(StringUtil.NVL(p.getHasSim(), ""));
        vo.setReqUsimSn(StringUtil.NVL(p.getReqUsimSn(), ""));
        vo.setEid(StringUtil.NVL(p.getEid(), ""));
        vo.setImei1(StringUtil.NVL(p.getImei1(), ""));
        vo.setImei2(StringUtil.NVL(p.getImei2(), ""));
        insertSvcChgDtl(vo, "insert sim dtl");
    }

    private void saveDataSharingDtl(Long requestKey, ServiceChangeCompleteReqDto.DataSharing p) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        String procTypeCd = "shareUseState2".equals(p.getShareUseState()) ? "C" : "R";
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), procTypeCd);
        vo.setAddtionInfo(StringUtil.NVL(p.getSharePhoneNum(), ""));
        insertSvcChgDtl(vo, "insert data sharing dtl");
    }

    private void saveCombineSoloDtl(Long requestKey, ServiceChangeCompleteReqDto.CombineSolo p) {
        Long dtlSeq = nextSvcChgDtlSeq(requestKey);
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, p.getSvcTgtCd(), "R");
        vo.setCombineSoloYn("Y");
        vo.setAddtionInfo(StringUtil.NVL(p.getSoloData(), ""));
        insertSvcChgDtl(vo, "insert combine solo dtl");
    }

    private Long nextSvcChgDtlSeq(Long requestKey) {
        Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
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

    private MsfRequestSvcChgVo buildSvcChgVo(Long requestKey, ServiceChangeCompleteReqDto req) {
        MsfRequestSvcChgVo vo = new MsfRequestSvcChgVo();
        String clientIp = RequestUtils.getClientIp();
        vo.setRequestKey(requestKey);
        vo.setCretIp(clientIp);
        vo.setCretId("MSF_FORM");
        vo.setAmdIp(clientIp);
        vo.setAmdId("MSF_FORM");
        vo.setManagerCd(StringUtil.NVL(req.getManagerCd(), ""));
        vo.setManagerNm(StringUtil.NVL(req.getManagerNm(), ""));
        vo.setAgentCd(StringUtil.NVL(req.getAgentCd(), ""));
        vo.setAgentNm(StringUtil.NVL(req.getAgentNm(), ""));
        vo.setCpntId(StringUtil.NVL(req.getCpntId(), ""));
        vo.setCpntNm(StringUtil.NVL(req.getCpntNm(), ""));
        vo.setCntpntShopCd(StringUtil.NVL(req.getCntpntShopCd(), ""));
        vo.setCntpntShopNm(StringUtil.NVL(req.getCntpntShopNm(), ""));
        vo.setCstmrTypeCd(StringUtil.NVL(req.getCstmrTypeCd(), "NA"));
        vo.setChgMobileNo(StringUtil.NVL(req.getCtn(), ""));
        vo.setChgContractNum(StringUtil.NVL(req.getNcn(), ""));
        vo.setRegstId("MSF_FORM");
        vo.setProcCd("RQ");
        vo.setRecYn("N");
        vo.setAppFormYn("N");
        vo.setAppFormXmlYn("N");
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
        if ("JP".equals(cstmrTypeCd) || "GO".equals(cstmrTypeCd)) {
            vo.setCstmrJuridicalCname(safe(req.getCstmrNm()));
            vo.setCstmrJuridicalRrn(joinParts(req.getCstmrJuridicalRrn1(), req.getCstmrJuridicalRrn2()));
            vo.setCstmrJuridicalBizNo(bizNo);
            vo.setCstmrJuridicalRepNm(safe(req.getCstmrJuridicalRepNm()));
            vo.setCstmrVisitTypeCd(safe(req.getCstmrVisitTypeCd()));
        } else if ("FN".equals(cstmrTypeCd) || "FM".equals(cstmrTypeCd)) {
            vo.setCstmrForeignerBirth(safe(req.getUserBirthDate()));
            vo.setCstmrForeignerGenderCd(safe(req.getUserGender()));
            vo.setCstmrPrivateBizNo(bizNo);
            if (!bizNo.isBlank()) {
                vo.setCstmrPrivateCname(safe(req.getCstmrNm()));
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

        if ("V2".equals(safe(req.getCstmrVisitTypeCd()))) {
            agentVo.setJrdclAgentNm(firstNonBlank(req.getMinorAgentNm(), req.getRepName()));
            agentVo.setJrdclAgentRrn(joinParts(req.getRepRegistrationNo1(), req.getRepRegistrationNo2()));
            agentVo.setJrdclAgentRelTypeCd(safe(req.getMinorAgentRelTypeCd()));
            agentVo.setJrdclAgentTelFnNo(safe(req.getMinorAgentTelFnNo()));
            agentVo.setJrdclAgentTelMnNo(safe(req.getMinorAgentTelMnNo()));
            agentVo.setJrdclAgentTelRnNo(safe(req.getMinorAgentTelRnNo()));
        }

        return agentVo;
    }

    private MsfRequestMstVo buildSvcChgMstVo(Long requestKey, ServiceChangeCompleteReqDto req) {
        String mobileNo = firstNonBlank(
            joinParts(req.getMobileNo1(), req.getMobileNo2(), req.getMobileNo3()),
            safe(req.getCtn()).replaceAll("\\D", "")
        );

        MsfRequestMstVo mstVo = new MsfRequestMstVo();
        mstVo.setRequestKey(requestKey);
        mstVo.setCretIp(RequestUtils.getClientIp());
        mstVo.setCretId("MSF_FORM");
        mstVo.setReqTypeCd("SC");
        mstVo.setUserId(firstNonBlank(req.getManagerCd(), "MSF_FORM"));
        mstVo.setCstmrNm(safe(req.getCstmrNm()));
        mstVo.setMobileNo(mobileNo);
        mstVo.setCstmrNativeRrn("");
        mstVo.setContractNum(safe(req.getNcn()));
        mstVo.setCstmrTypeCd(firstNonBlank(req.getCstmrTypeCd(), "NA"));
        mstVo.setOnlineAuthTypeCd("");
        mstVo.setOnlineAuthInfo("MSF:" + requestKey);
        mstVo.setEtcMobileNo(mobileNo);
        return mstVo;
    }

    private List<MsfRequestClauseVo> buildSvcChgClauseVos(Long requestKey, ServiceChangeCompleteReqDto req) {
        List<MsfRequestClauseVo> clauseVos = new ArrayList<>();
        if (req.getClauses() != null) {
            for (ServiceChangeCompleteReqDto.Clause clause : req.getClauses()) {
                if (clause == null || isBlank(clause.getCode()) || !isChecked(clause.getChecked())) {
                    continue;
                }
                clauseVos.add(buildSvcChgClauseVo(requestKey, resolveClauseGroupId(clause), resolveClauseGroupId2(clause), clause.getVersion()));
            }
        }

        return clauseVos;
    }

    private MsfRequestClauseVo buildSvcChgClauseVo(Long requestKey, String cdGroupId, String cdGroupId2, String version) {
        MsfRequestClauseVo clauseVo = new MsfRequestClauseVo();
        clauseVo.setRequestKey(requestKey);
        clauseVo.setCdGroupId(cdGroupId);
        clauseVo.setCdGroupId2(cdGroupId2);
        clauseVo.setVersion(safe(version));
        return clauseVo;
    }

    private static String resolveClauseGroupId(ServiceChangeCompleteReqDto.Clause clause) {
        return firstNonBlank(clause.getTermsGroupCd(), clause.getCdGroupId(), "CLAUSE_FORM_01");
    }

    private static String resolveClauseGroupId2(ServiceChangeCompleteReqDto.Clause clause) {
        String termsItemCd = firstNonBlank(clause.getTermsItemCd(), clause.getCdGroupId2());
        if (!isBlank(termsItemCd)) {
            return termsItemCd;
        }
        return "CLAUSE_INFO_01".equals(safe(clause.getCode())) ? "01" : safe(clause.getCode());
    }

    private static boolean isMinorCustomerType(String cstmrTypeCd) {
        return "NM".equals(safe(cstmrTypeCd)) || "FM".equals(safe(cstmrTypeCd));
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
        for (String value : values) {
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
        for (String value : values) {
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

    /** 서비스 타입 기반 DTL 기본 구조 생성 */
    private MsfRequestSvcChgDtlVo buildBaseDtlVo(Long dtlSeq, Long requestKey, String svcType, String procTypeCd) {
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
        vo.setAppFormYn("N");
        vo.setAppFormXmlYn("N");
        return vo;
    }

    /** SOC 기반 DTL (R11 부가서비스 / R12 무선데이터차단) */
    private MsfRequestSvcChgDtlVo buildSocDtlVo(Long dtlSeq, Long requestKey, String svcTgtCd, String soc, String procTypeCd, String addtionInfo) {
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, StringUtil.NVL(svcTgtCd, ""), procTypeCd);
        vo.setSocCd(StringUtil.NVL(soc, ""));
        vo.setAddtionInfo(StringUtil.NVL(addtionInfo, ""));
        return vo;
    }

    private static class ServiceChangeSaveFailureException extends RuntimeException {
        private ServiceChangeSaveFailureException(String message) {
            super(message);
        }
    }




}
