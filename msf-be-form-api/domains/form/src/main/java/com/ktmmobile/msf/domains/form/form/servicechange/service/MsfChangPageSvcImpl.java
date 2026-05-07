package com.ktmmobile.msf.domains.form.form.servicechange.service;

import java.net.SocketTimeoutException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ktmmobile.msf.commons.websecurity.web.util.RequestUtils;
import com.ktmmobile.msf.domains.form.common.code.ResSvcChgMessage;
import com.ktmmobile.msf.domains.form.common.dto.McpFarPriceDto;
import com.ktmmobile.msf.domains.form.common.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.response.FormResponse;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.SelfServiceException;
import com.ktmmobile.msf.domains.form.common.mplatform.MsfMplatFormService;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpMoscBilEmailInfoInVO;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpPerMyktfInfoVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.ChangInfoViewResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.FarPricePlanResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.repository.ChangPageRepositoryImpl;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressCondition;
import com.ktmmobile.msf.domains.shared.common.address.application.dto.SearchAddressResponse;
import com.ktmmobile.msf.domains.shared.common.address.application.port.in.AddressReader;

import jakarta.servlet.http.HttpServletRequest;

import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Service
public class MsfChangPageSvcImpl implements MsfChangPageSvc {

    private static final Logger logger = LoggerFactory.getLogger(MsfChangPageSvcImpl.class);

    @Autowired
    private ChangPageRepositoryImpl changPageRepositoryImpl;

    @Autowired
    private MsfMplatFormService msfMplatFormService;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfFarPricePlanService farPricePlanService;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private AddressReader addressReader;

    // [ASIS] interface 호출 소스 보관
    // @Value("${api.interface.server}")
    // private String apiInterfaceServer;

    @Override
    public MspJuoAddInfoDto selectMspAddInfo(String svcCntrNo) {
        logger.debug("[MsfChangPage][selectMspAddInfo] start: ncn={}, queryId={}",
            svcCntrNo, "ChangPageMapper.selectMspAddInfo");
        try {
            // 기존 interface 호출 소스 보관
            // String callUrl = apiInterfaceServer + "/mypage/mspAddInfo";
            // RestTemplate restTemplate = new RestTemplate();
            // MspJuoAddInfoDto response = restTemplate.postForObject(callUrl, svcCntrNo, MspJuoAddInfoDto.class);
            MspJuoAddInfoDto response = changPageRepositoryImpl.selectMspAddInfo(svcCntrNo);
            logger.debug("[MsfChangPage][selectMspAddInfo] response: ncn={}, hasBody={}, remainPay={}, remainMonth={}",
                svcCntrNo, response != null, response != null ? response.getRemainPay() : null, response != null ? response.getRemainMonth() : null);
            return response;
        } catch (Exception e) {
            logger.error("[MsfChangPage][selectMspAddInfo] error: ncn={}, queryId={}",
                svcCntrNo, "ChangPageMapper.selectMspAddInfo", e);
            throw e;
        }
    }

    @Override
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
        List<McpUserCntrMngDto> list = changPageRepositoryImpl.selectCntrList(params);

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

    @Override
    public McpUserCntrMngDto selectCntrListNoLogin(String contractNum) {
        if (contractNum == null || "".equals(contractNum)) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
        userCntrMngDto.setSvcCntrNo(contractNum);
        return selectCntrListNoLogin(userCntrMngDto);
    }

    @Override
    public McpUserCntrMngDto selectCntrListNoLogin(McpUserCntrMngDto userCntrMngDto) {
        if (userCntrMngDto.getSvcCntrNo() == null && userCntrMngDto.getCntrMobileNo() == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }
        // [ASIS] interface 호출 소스 보관
        // RestTemplate restTemplate = new RestTemplate();
        // return restTemplate.postForObject(apiInterfaceServer + "/mypage/cntrListNoLogin", userCntrMngDto, McpUserCntrMngDto.class);
        McpUserCntrMngDto cntrInfo = changPageRepositoryImpl.selectCntrListNoLogin(userCntrMngDto);
        applyRoadAddress(cntrInfo);
        return cntrInfo;
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

    @Override
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
        return FormResponse.ok(response);
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
}
