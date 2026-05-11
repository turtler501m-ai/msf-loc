package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.cache.commoncode.application.port.out.CommonCodeRepository;
import com.ktmmobile.msf.domains.cache.commoncode.domain.entity.CommonCode;
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
import com.ktmmobile.msf.domains.form.form.common.vo.MsfRequestCstmrVo;
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
    private CommonCodeRepository commonCodeRepository;

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
        List<McpUserCntrMngDto> cntrList = new java.util.ArrayList<>();

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
        String today = "";
        String birthday = "";
        int myAge = 0;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd", Locale.KOREA);
        today = formatter.format(new Date());
        if (idNum != null && idNum.trim().length() == 13) {
            if (idNum.charAt(6) == '1' || idNum.charAt(6) == '2' || idNum.charAt(6) == '5' || idNum.charAt(6) == '6') {
                birthday = "19" + idNum.substring(0, 6);
            } else if (idNum.charAt(6) == '*') {
                return -1;
            } else {
                birthday = "20" + idNum.substring(0, 6);
            }
        } else {
            return 0;
        }
        myAge = Integer.parseInt(today.substring(0, 4)) - Integer.parseInt(birthday.substring(0, 4));
        if (Integer.parseInt(today.substring(4)) < Integer.parseInt(birthday.substring(4))) myAge = myAge - 1;
        return myAge;
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

        if (!saveSvcChgRequest(applicationKey, ncn, req, cancelList, addList)) {
            logger.warn("[serviceChangeComplete] DB save failed after mplatform success: applicationKey={}, ncn={}",
                applicationKey, ncn);
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
     * M플랫폼 처리 완료 후 호출 — DB 저장 실패 시 로그만 남기고 진행
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

    private boolean saveSvcChgRequest(
        String applicationKey, String ncn,
        ServiceChangeCompleteReqDto req,
        List<AdditionApplyReqDto> cancelList,
        List<AdditionApplyReqDto> addList) {
        try {
            Long requestKey = svcChgPageRepositoryImpl.nextRequestKey();
            List<String> serviceSelect = req.getServiceSelect() != null ? req.getServiceSelect() : new ArrayList<>();

            MsfRequestSvcChgVo svcChgVo = buildSvcChgVo(requestKey, req);
            msfRequestRepository.insertMsfRequestSvcChg(svcChgVo);

            MsfRequestCstmrVo cstmrVo = buildSvcChgCstmrVo(requestKey, req);
            msfRequestRepository.insertMsfRequestCstmr(cstmrVo);

            // R11/R12: SOC 해지 DTL 저장
            for (AdditionApplyReqDto cancelReq : cancelList) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                msfRequestRepository.insertMsfRequestSvcChgDtl(
                    buildSocDtlVo(dtlSeq, requestKey, cancelReq.getSvcTgtCd(), cancelReq.getSoc(), "C", cancelReq.getFtrNewParam()));
            }
            // R11/R12: SOC 신청 DTL 저장
            for (AdditionApplyReqDto addReq : addList) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                msfRequestRepository.insertMsfRequestSvcChgDtl(
                    buildSocDtlVo(dtlSeq, requestKey, addReq.getSvcTgtCd(), addReq.getSoc(), "R", addReq.getFtrNewParam()));
            }

            // P11(요금제변경) DTL 저장
            if (serviceSelect.contains("P11") && req.getPlanChange() != null) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                MsfRequestSvcChgDtlVo dtlVo = buildBaseDtlVo(dtlSeq, requestKey, "P11", "R");
                dtlVo.setSocCd(StringUtil.NVL(req.getPlanChange().getPlanCd(), ""));
                dtlVo.setAddtionInfo(StringUtil.NVL(req.getPlanChange().getChangeTypeCd(), ""));
                msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo);
            }

            // O11(번호변경) DTL 저장
            if (serviceSelect.contains("O11") && req.getNumberChange() != null) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                MsfRequestSvcChgDtlVo dtlVo = buildBaseDtlVo(dtlSeq, requestKey, "O11", "R");
                dtlVo.setAddtionInfo(StringUtil.NVL(req.getNumberChange().getWishNo(), ""));
                msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo);
            }

            // O12(분실복구/일시정지해제) DTL 저장
            if (serviceSelect.contains("O12") && req.getUnpause() != null) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                MsfRequestSvcChgDtlVo dtlVo = buildBaseDtlVo(dtlSeq, requestKey, "O12", "R");
                dtlVo.setAddtionInfo(StringUtil.NVL(req.getUnpause().getUnLockPw(), ""));
                msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo);
            }

            // R14(단말보험) DTL 저장
            if (serviceSelect.contains("R14") && req.getInsurance() != null) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                MsfRequestSvcChgDtlVo dtlVo = buildBaseDtlVo(dtlSeq, requestKey, "R14", "R");
                dtlVo.setClauseInsuranceYn(StringUtil.NVL(req.getInsurance().getClauseInsuranceYn(), "N"));
                dtlVo.setInsrCd(StringUtil.NVL(req.getInsurance().getInsrProdCd(), ""));
                dtlVo.setAddtionInfo(StringUtil.NVL(req.getInsurance().getCatCd(), ""));
                msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo);
            }

            // O13(SIM정보) DTL 저장
            if (serviceSelect.contains("O13") && req.getSimInfo() != null) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                MsfRequestSvcChgDtlVo dtlVo = buildBaseDtlVo(dtlSeq, requestKey, "O13", "R");
                dtlVo.setUsimBuyTypeCd(StringUtil.NVL(req.getSimInfo().getHasSim(), ""));
                dtlVo.setReqUsimSn(StringUtil.NVL(req.getSimInfo().getReqUsimSn(), ""));
                dtlVo.setEid(StringUtil.NVL(req.getSimInfo().getEid(), ""));
                dtlVo.setImei1(StringUtil.NVL(req.getSimInfo().getImei1(), ""));
                dtlVo.setImei2(StringUtil.NVL(req.getSimInfo().getImei2(), ""));
                msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo);
            }

            // R15(데이터쉐어링) DTL 저장
            if (serviceSelect.contains("R15") && req.getDataSharing() != null) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                String procTypeCd = "shareUseState2".equals(req.getDataSharing().getShareUseState()) ? "C" : "R";
                MsfRequestSvcChgDtlVo dtlVo = buildBaseDtlVo(dtlSeq, requestKey, "R15", procTypeCd);
                dtlVo.setAddtionInfo(StringUtil.NVL(req.getDataSharing().getSharePhoneNum(), ""));
                msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo);
            }

            // R16(결합Solo) DTL 저장
            if (serviceSelect.contains("R16") && req.getCombineSolo() != null) {
                Long dtlSeq = svcChgPageRepositoryImpl.nextSvcChgDtlSeq();
                MsfRequestSvcChgDtlVo dtlVo = buildBaseDtlVo(dtlSeq, requestKey, "R16", "R");
                dtlVo.setCombineSoloYn("Y");
                dtlVo.setAddtionInfo(StringUtil.NVL(req.getCombineSolo().getSoloData(), ""));
                msfRequestRepository.insertMsfRequestSvcChgDtl(dtlVo);
            }

            logger.info("[serviceChangeComplete] DB saved: requestKey={}, ncn={}, applicationKey={}, serviceSelect={}",
                requestKey, ncn, applicationKey, serviceSelect);
            return true;
        } catch (Exception e) {
            logger.error("[serviceChangeComplete] DB save failed (mplatform already processed): applicationKey={}, ncn={}", applicationKey, ncn, e);
            return false;
        }
    }

    private MsfRequestSvcChgVo buildSvcChgVo(Long requestKey, ServiceChangeCompleteReqDto req) {
        MsfRequestSvcChgVo vo = new MsfRequestSvcChgVo();
        vo.setRequestKey(requestKey);
        vo.setCretIp("127.0.0.1");
        vo.setCretId("MSF_FORM");
        vo.setAmdIp("127.0.0.1");
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
        vo.setCstmrNm("");
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
        vo.setCstmrVisitTypeCd("");
        vo.setCstmrTelFnNo("");
        vo.setCstmrTelMnNo("");
        vo.setCstmrTelRnNo("");
        // ctn → mobileFnNo 전체 저장 (분리 불필요)
        String ctn = StringUtil.NVL(req.getCtn(), "").replaceAll("\\D", "");
        String ctnFn = "";
        String ctnMn = "";
        String ctnRn = "";
        if (ctn.length() >= 10) {
            ctnFn = ctn.substring(0, 3);
            ctnMn = ctn.substring(3, ctn.length() - 4);
            ctnRn = ctn.substring(ctn.length() - 4);
        }
        vo.setCstmrMobileFnNo(ctnFn);
        vo.setCstmrMobileMnNo(ctnMn);
        vo.setCstmrMobileRnNo(ctnRn);
        vo.setCstmrZipcd("");
        vo.setCstmrAdr("");
        vo.setCstmrAdrDtl("");
        vo.setCstmrAdrBjd("");
        vo.setCstmrEmailAdr("");
        vo.setCstmrEmailReceiveYn("N");
        vo.setCstmrReceiveTelFnNo("");
        vo.setCstmrReceiveTelNmNo("");
        vo.setCstmrReceiveTelRnNo("");
        return vo;
    }

    /** 서비스 타입 기반 DTL 기본 구조 생성 */
    private MsfRequestSvcChgDtlVo buildBaseDtlVo(Long dtlSeq, Long requestKey, String svcType, String procTypeCd) {
        MsfRequestSvcChgDtlVo vo = new MsfRequestSvcChgDtlVo();
        vo.setRequestSvcChgDtlSeq(dtlSeq);
        vo.setRequestKey(requestKey);
        vo.setCretIp("127.0.0.1");
        vo.setCretId("MSF_FORM");
        vo.setAmdIp("127.0.0.1");
        vo.setAmdId("MSF_FORM");
        vo.setSvcTgtCd(resolveSvcTgtCd(svcType));
        vo.setProcTypeCd(StringUtil.NVL(procTypeCd, ""));
        vo.setAppFormYn("N");
        vo.setAppFormXmlYn("N");
        return vo;
    }

    /** SOC 기반 DTL (R11 부가서비스 / R12 무선데이터차단) */
    private MsfRequestSvcChgDtlVo buildSocDtlVo(Long dtlSeq, Long requestKey, String svcTgtCd, String soc, String procTypeCd, String addtionInfo) {
        MsfRequestSvcChgDtlVo vo = buildBaseDtlVo(dtlSeq, requestKey, StringUtil.NVL(svcTgtCd, "R11"), procTypeCd);
        vo.setSocCd(StringUtil.NVL(soc, ""));
        vo.setAddtionInfo(StringUtil.NVL(addtionInfo, ""));
        return vo;
    }

    /** SVC_TGT_CD 공통코드에서 svcType 코드의 sort_order를 조회하여 svcTgtCd로 반환 */
    private String resolveSvcTgtCd(String svcType) {
        String code = StringUtil.NVL(svcType, "R11");
        try {
            return commonCodeRepository.findAllCommonCodes().stream()
                .filter(c -> "SVC_TGT_CD".equals(c.getGroupId()))
                .filter(c -> code.equals(c.getCode()))
                .filter(CommonCode::isUsed)
                .map(CommonCode::getDetail)
                .filter(detail -> detail != null)
                .map(detail -> String.valueOf(detail.getSortOrder()))
                .findFirst()
                .orElse(code);
        } catch (Exception e) {
            logger.warn("[서비스변경][작성완료] SVC_TGT_CD sort_order 조회 실패, 코드 사용: svcType={}, {}", code, e.getMessage());
            return code;
        }
    }


}
