package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.appform.service.AppformSvc;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.db.McpRequestDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.McpMplatFormException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.MplatFormService;
import com.ktmmobile.mcp.common.mplatform.dto.MoscFarPriceChgDto;
import com.ktmmobile.mcp.common.mplatform.dto.MoscFarPriceResDto;
import com.ktmmobile.mcp.common.mplatform.vo.*;
import com.ktmmobile.mcp.common.mspservice.MspService;
import com.ktmmobile.mcp.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dto.*;
import com.ktmmobile.mcp.mypage.service.CallRegService;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.personal.service.PersonalService;
import com.ktmmobile.mcp.rate.dto.RateAdsvcBnfitGdncDtlDTO;
import com.ktmmobile.mcp.rate.dto.RateAdsvcBnfitGdncDtlXML;
import com.ktmmobile.mcp.rate.dto.RateAdsvcCtgBasDTO;
import com.ktmmobile.mcp.rate.service.RateAdsvcGdncService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static com.ktmmobile.mcp.common.constants.Constants.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;

@Controller
public class PersonalSocController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalSocController.class);

    private static final String PAGE_TYPE = "SOC";
    private static final String REDIRECT_URL = "/personal/auth.do";

    @Autowired
    private PersonalService personalService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private FarPricePlanService farPricePlanService;

    @Autowired
    private AppformSvc appformSvc;

    @Autowired
    private RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    private CallRegService callRegService;

    @Autowired
    private MspService mspService;


    /**
     * 개인화 url - 요금제 변경 페이지
     * @Date : 2025.05.26
     */
    @RequestMapping(value= {"/personal/farPricePlanView.do", "/m/personal/farPricePlanView.do"})
    public String personalFarPricePlanView(@ModelAttribute(value = "ncn") String ncn
                                                                                ,ModelMap model){

        String jspPageName = "/personal/portal/pFarPricePlanView";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/personal/mobile/pFarPricePlanView";
        }

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){

            String sessionUrl = SessionUtils.getPersonalUrl(PAGE_TYPE);
            if("".equals(sessionUrl)){
                throw new McpCommonException(F_BIND_EXCEPTION, REDIRECT_URL);
            }

            return "redirect:" + sessionUrl;
        }

        // 요금제 변경 가능 세션 제거
        SessionUtils.saveOtpInfo(null);

        String svcCntrNo = personalUser.getSvcCntrNo();
        String ctn = personalUser.getCntrMobileNo();
        String customerId = personalUser.getCustomerId();

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(svcCntrNo);
        searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
        searchVO.setCtn(StringMakerUtil.getPhoneNum(ctn));

        // 요금제 정보 조회
        McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(personalUser.getContractNum());
        if (mcpFarPriceDto == null) {
            throw new McpCommonException(NOT_FOUND_DATA_EXCEPTION, REDIRECT_URL);
        }

        try {
            /* [MP] 가입중인 부가서비스 조회 (X20) */
            MpAddSvcInfoDto getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(svcCntrNo, ctn, customerId);
            if(!getAddSvcInfo.isSuccess()){
                throw new McpCommonException(COMMON_EXCEPTION, REDIRECT_URL);
            }

            mcpFarPriceDto.setPromotionDcAmt(-getAddSvcInfo.getDiscountRate());
        }catch(SelfServiceException e) {
            throw new McpCommonException(COMMON_EXCEPTION, REDIRECT_URL);
        }catch(Exception e){
            throw new McpCommonException(COMMON_EXCEPTION, REDIRECT_URL);
        }

        model.addAttribute("isPriceChange", "TRUE");
        model.addAttribute("mcpFarPriceDto", mcpFarPriceDto);
        model.addAttribute("searchVO", searchVO);
        return jspPageName;
    }

    /**
     * 개인화 url - 이용중인 요금제 및 요금제 예약현황 조회
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/myRateResvViewAjax.do", "/m/personal/myRateResvViewAjax.do"})
    @ResponseBody
    public Map<String, Object> myAddSvcListAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        // 요금제 정보 조회
        McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(personalUser.getContractNum());
        if (mcpFarPriceDto == null) {
            throw new McpCommonJsonException("0002", NOT_FOUND_DATA_EXCEPTION);
        }

        // 요금제 상세정보 조회
        FarPricePlanResDto farPricePlanResDto = farPricePlanService.getFarPricePlanWrapper(mcpFarPriceDto);

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(personalUser.getSvcCntrNo());
        searchVO.setCtn(personalUser.getCntrMobileNo());
        searchVO.setCustId(personalUser.getCustomerId());

        try{
            /* [MP] 요금제 변경 예약 조회 (X89) */
            MoscFarPriceResDto outRes = farPricePlanService.doFarPricePlanRsrvSearch(searchVO);

            if(outRes.isSuccess()){
                mcpFarPriceDto.setRsrvPrdcCd(outRes.getPrdcCd());         // 예약상품요금제코드
                mcpFarPriceDto.setRsrvPrdcNm(outRes.getPrdcNm());         // 예약 상품요금제명
                mcpFarPriceDto.setRsrvBasicAmt(outRes.getBasicAmt());     // 예약 요금제변경 대상기본료
                mcpFarPriceDto.setRsrvAplyDate(outRes.getAplyDate());     // 예약요금제 변경 신청일자
                mcpFarPriceDto.setRsrvEfctStDate(outRes.getEfctStDate()); // 예약요금제 변경 적용일자
            }

        }catch(McpMplatFormException e){
            logger.info("myAddSvcListAjax.McpMplatFormException={}", e.getMessage());
        }catch(Exception e){
            logger.info("myAddSvcListAjax.Exception={}", e.getMessage());
        }

        // 변경가능한 요금제 개수 조회
        int total = mypageService.countFarPricePlanList(mcpFarPriceDto.getPrvRateCd());

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("total", total);
        rtnMap.put("prvRateGrpNm", mcpFarPriceDto.getPrvRateGrpNm());
        rtnMap.put("rateAdsvcLteDesc", StringUtil.NVL(farPricePlanResDto.getRateAdsvcLteDesc(), "-"));
        rtnMap.put("rateAdsvcCallDesc", StringUtil.NVL(farPricePlanResDto.getRateAdsvcCallDesc(), "-"));
        rtnMap.put("rateAdsvcSmsDesc", StringUtil.NVL(farPricePlanResDto.getRateAdsvcSmsDesc(), "-"));
        rtnMap.put("myPriceInfo", mcpFarPriceDto);
        rtnMap.put("prvRateGdncSeq", farPricePlanResDto.getRateAdsvcGdncSeq());
        return rtnMap;
    }

    /**
     * 개인화 url - 요금제 예약변경 취소
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/deleteMyRateResvAjax.do", "/m/personal/deleteMyRateResvAjax.do"})
    @ResponseBody
    public Map<String, Object> deleteMyRateResvAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();
        String returnCode = null;
        String message = null;

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(personalUser.getSvcCntrNo());
        searchVO.setCtn(personalUser.getCntrMobileNo());
        searchVO.setCustId(personalUser.getCustomerId());

        try{

            /* [MP] 요금제 변경 예약 취소 (X90) */
            MpCommonXmlVO res = farPricePlanService.doFarPricePlanRsrvCancel(searchVO);
            returnCode = res.isSuccess() ? AJAX_SUCCESS : "E";
            message = res.isSuccess() ? "처리성공" : COMMON_EXCEPTION;

        }catch(McpCommonException e){
            returnCode = "E";
            message = COMMON_EXCEPTION;
        }catch(Exception e){
            returnCode = "E";
            message = COMMON_EXCEPTION;
        }

        rtnMap.put("RESULT_CODE", returnCode);
        rtnMap.put("RESULT_MSG", message);
        return rtnMap;
    }

    /**
     * 개인화 url - 요금제 변경 가능여부
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/possibleStateCheckAjax.do", "/m/personal/possibleStateCheckAjax.do"})
    @ResponseBody
    public Map<String, Object> possibleStateCheckAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        try{
            // 1. 상품변경 불가 시간
            boolean timeChk = DateTimeUtil.isMiddleTime("23:29", "00:30");
            if(timeChk){
                rtnMap.put("RESULT_CODE", "006");
                rtnMap.put("RESULT_MSG", "해당 시간은 상품변경이 불가 합니다. (23:30분 ~ 익일 00:30분, 1시간)");
                return rtnMap;
            }

            // 2. 미성년자
            String birthday = NmcpServiceUtils.getSsnDate(personalUser.getUnUserSSn()); // yyyymmdd
            int age = NmcpServiceUtils.getBirthDateToAmericanAge(birthday, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            boolean underAge = (age < 19) ? true : false;

            if(underAge) {
                rtnMap.put("RESULT_CODE", "001");
                rtnMap.put("RESULT_MSG", "만 19세이상 성인고객만 가능합니다.");
                return rtnMap;
            }

            // 3. 약정 (약정고객 가입고객 변경 불가)
            MspJuoAddInfoDto enggInfo = mypageService.getEnggInfo(personalUser.getContractNum());

            if(enggInfo != null && "Y".equals(enggInfo.getEnggYn())){
                rtnMap.put("RESULT_CODE", "004");
                rtnMap.put("RESULT_MSG", "단말/유심 약정고객 요금제 변경이 불가능 합니다.");
                return rtnMap;
            }

            // 4. 요금제 변경 불가 부가서비스 가입
            /* [MP] 가입중인 부가서비스 조회 (X20) */
            String svcCntrNo = personalUser.getSvcCntrNo();
            String ctn = personalUser.getCntrMobileNo();
            String customerId = personalUser.getCustomerId();

            MpAddSvcInfoDto getAddSvcInfo = mPlatFormService.getAddSvcInfoDto(svcCntrNo, ctn, customerId);
            if(!getAddSvcInfo.isSuccess()){
                throw new McpCommonJsonException("-1", "[X20] 가입중인 부가서비스 리스트 조회 오류");
            }

            List<MpSocVO> addSvcList = getAddSvcInfo.getList();  // 가입되어 있는 부가서비스
            List<NmcpCdDtlDto> socCodeList = NmcpServiceUtils.getCodeList(GROUP_CODE_EXCEPTION_LIST_SOC_CODE); // 요금제 변경 불가 부가서비스 리스트

            if(socCodeList != null){
                Set<String> socCodeSet = socCodeList.stream().map(NmcpCdDtlDto::getDtlCd).collect(Collectors.toSet());
                boolean hasExceptionSoc = addSvcList.stream().map(MpSocVO::getSoc).anyMatch(socCodeSet::contains);

                if(hasExceptionSoc){
                    rtnMap.put("RESULT_CODE", "008");
                    rtnMap.put("RESULT_MSG", "요금제 변경 불가능한 부가서비스 존재");
                    return rtnMap;
                }
            }

            // 5. 유통영업팀 산하 대리점을 통해 모집된 고객
            McpRequestDto requestInfo = appformSvc.getMcpRequestByContractNum(personalUser.getContractNum());
            if(requestInfo != null && REQ_BUY_TYPE_USIM.equals(requestInfo.getReqBuyType())){

                MspJuoAddInfoDto channelInfo = mypageService.getChannelInfo(personalUser.getContractNum());

                if (channelInfo == null || (!"01".equals(channelInfo.getChannelCd()) && !"02".equals(channelInfo.getChannelCd()))) {
                    rtnMap.put("RESULT_CODE", "005");
                    rtnMap.put("RESULT_MSG", "유통영업팀 산하 대리점을 통해 모집된 고객일 경우 변경 불가");
                    return rtnMap;
                }
            }

            // 6. 개통당월 또는 당월 요금제 변경이력이 있는 경우 즉시변경 불가 (예약변경 가능)
            // 현재 요금제 조회
            McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(personalUser.getContractNum());

            // 요금제 변경일시 조회
            Map<String, String> map = new HashMap<String, String>();
            map.put("soc", mcpFarPriceDto.getPrvRateCd());
            map.put("cntrNo", personalUser.getContractNum());
            String getMonth = mypageService.selectFarPriceAddInfo(map);

            Date nowDay = new Date();
            String thisMonth = DateTimeUtil.changeFormat(nowDay, "yyyyMM");
            String openingDate = personalUser.getLstComActvDate().substring(0,6);

            String isActivatedThisMonth = "N"; // 당월 개통여부
            String isFarPriceThisMonth = "N";  // 당월 요금제 변경여부

            // 당월 개통
            if(thisMonth.equals(openingDate)){
                isActivatedThisMonth = "Y";
            }

            // 당월 요금제 변경 (개통 제외)
            if("N".equals(isActivatedThisMonth) && thisMonth.equals(getMonth)) {
                isFarPriceThisMonth = "Y";
            }

            rtnMap.put("isActivatedThisMonth", isActivatedThisMonth);
            rtnMap.put("isFarPriceThisMonth", isFarPriceThisMonth);
            SessionUtils.saveOtpInfo(svcCntrNo);

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "요금제 변경 가능");

        }catch (ParseException | SocketTimeoutException | SelfServiceException | McpCommonJsonException e){
            throw new McpCommonJsonException("9999", "요금제 변경 가능여부 확인 오류");
        }catch(Exception e){
            throw new McpCommonJsonException("9999", "요금제 변경 가능여부 확인 오류");
        }

        return rtnMap;
    }

    /**
     * 개인화 url - 요금제 변경 신청 팝업
     * @Date : 2025.05.26
     */
    @RequestMapping(value= {"/personal/regServicePop.do", "/m/personal/regServicePop.do"})
    public String regServicePop(@RequestParam(value = "ncn") String ncn
                             ,@RequestParam(value = "instAmt") String instAmt
                             ,@RequestParam(value = "prvRateGrpNm") String prvRateGrpNm
                             ,@RequestParam(value = "rateNm") String rateNm
                             ,@RequestParam(value = "baseAmt") String baseAmt
                             ,@RequestParam(value = "rateAdsvcCd") String rateAdsvcCd
                             ,@RequestParam(value = "nxtRateGdncSeq") int nxtRateGdncSeq
                             ,@RequestParam(value = "prvRateGdncSeq", required = false, defaultValue = "0") int prvRateGdncSeq
                             ,@RequestParam(value = "isActivatedThisMonth", required = false, defaultValue = "N") String isActivatedThisMonth
                             ,@RequestParam(value = "isFarPriceThisMonth", required = false, defaultValue = "N") String isFarPriceThisMonth
                             ,@RequestParam(value = "chkRadion", required = false, defaultValue = "S") String chkRadion
                             ,ModelMap model){

        String jspPageName = "/personal/portal/pRegServicePop";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/personal/mobile/pRegServicePop";
        }

        // 기존 요금제 정보
        RateAdsvcCtgBasDTO prvFarPriceDto = new RateAdsvcCtgBasDTO();
        prvFarPriceDto.setRateAdsvcGdncSeq(prvRateGdncSeq);

        List<RateAdsvcBnfitGdncDtlXML> prvBnfitList = null;
        if(prvFarPriceDto.getRateAdsvcGdncSeq() > 0){
            prvBnfitList= rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(prvFarPriceDto);
        }

        if(prvBnfitList != null && prvBnfitList.size() > 0) {
            for(RateAdsvcBnfitGdncDtlXML bnfitVo : prvBnfitList) {

                if("RATEBE01".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 기존 요금제 정보 > 데이터
                    prvFarPriceDto.setBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }else if("RATEBE02".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 기본 요금제 정보 > 음성
                    prvFarPriceDto.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }else if("RATEBE03".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 기본 요금제 정보 > 문자
                    prvFarPriceDto.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }else if("RATEBE04".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 기본 요금제 정보 > WiFi
                    prvFarPriceDto.setBnfitWifi(bnfitVo.getRateAdsvcItemDesc());
                }else if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 기존 요금제 정보 > 제휴혜택
                    RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());
                    prvFarPriceDto.getBnfitList().add(bnfitDto);
                }
            }
        }

        // 변경할 요금제 정보
        RateAdsvcCtgBasDTO nxtFarPriceDto = new RateAdsvcCtgBasDTO();
        nxtFarPriceDto.setRateAdsvcGdncSeq(nxtRateGdncSeq);

        List<RateAdsvcBnfitGdncDtlXML> nxtBnfitList = null;
        if(nxtFarPriceDto.getRateAdsvcGdncSeq() > 0){
            nxtBnfitList = rateAdsvcGdncService.getRateAdsvcBnfitGdncDtlXmlList(nxtFarPriceDto);
        }

        if(nxtBnfitList != null && nxtBnfitList.size() > 0) {
            for(RateAdsvcBnfitGdncDtlXML bnfitVo : nxtBnfitList) {

                if("RATEBE01".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 변경할 요금제 정보 > 데이터
                    nxtFarPriceDto.setBnfitData(bnfitVo.getRateAdsvcItemDesc());
                }else if("RATEBE02".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 변경할 요금제 정보 > 음성
                    nxtFarPriceDto.setBnfitVoice(bnfitVo.getRateAdsvcItemDesc());
                }else if("RATEBE03".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 변경할 요금제 정보 > 문자
                    nxtFarPriceDto.setBnfitSms(bnfitVo.getRateAdsvcItemDesc());
                }else if("RATEBE04".equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 변경할 요금제 정보 > WiFi
                    nxtFarPriceDto.setBnfitWifi(bnfitVo.getRateAdsvcItemDesc());
                }else if(("RATEBE11").equals(bnfitVo.getRateAdsvcBnfitItemCd())){
                    // 변경할 요금제 정보 > 제휴혜택
                    RateAdsvcBnfitGdncDtlDTO bnfitDto = new RateAdsvcBnfitGdncDtlDTO();
                    bnfitDto.setRateAdsvcBnfitItemCd(bnfitVo.getRateAdsvcBnfitItemCd());
                    bnfitDto.setRateAdsvcItemNm(bnfitVo.getRateAdsvcItemNm());
                    bnfitDto.setRateAdsvcItemDesc(bnfitVo.getRateAdsvcItemDesc());
                    nxtFarPriceDto.getBnfitList().add(bnfitDto);
                }
            }
        }

        // 변경할 요금제가 제휴요금제인지 확인
        NmcpCdDtlDto jehuProdInfo = fCommonSvc.getJehuProdInfo(rateAdsvcCd);
        String socAlliacYn = StringUtils.isEmpty(jehuProdInfo.getDtlCd()) ? "N" : "Y";

        model.addAttribute("ncn", ncn);
        model.addAttribute("instAmtDisp", instAmt);
        model.addAttribute("instAmt", instAmt.trim().replaceAll(",", "").replaceAll("원", ""));
        model.addAttribute("prvRateGrpNm", prvRateGrpNm);
        model.addAttribute("rateNm", rateNm);
        model.addAttribute("baseAmtDisp", baseAmt);
        model.addAttribute("baseAmt", baseAmt.trim().replaceAll(",", "").replaceAll("원", ""));
        model.addAttribute("rateAdsvcCd", rateAdsvcCd);
        model.addAttribute("prvFarPriceDto", prvFarPriceDto);
        model.addAttribute("nxtFarPriceDto", nxtFarPriceDto);
        model.addAttribute("socAlliacYn", socAlliacYn);
        model.addAttribute("jehuProdType", jehuProdInfo.getDtlCd());
        model.addAttribute("jehuProdName", jehuProdInfo.getDtlCdNm());
        model.addAttribute("isActivatedThisMonth", isActivatedThisMonth); // 이번달 개통여부
        model.addAttribute("isFarPriceThisMonth", isFarPriceThisMonth);   // 이번달 요금제 변경여부
        model.addAttribute("chkRadion",chkRadion); //P:예약, S:즉시변경
        return jspPageName;
    }

    /**
     * 개인화 url - 실시간 이용량 조회
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/selectRealTimeUseageAjax.do", "/m/personal/selectRealTimeUseageAjax.do"})
    @ResponseBody
    public Map<String, Object> selectRealTimeUseageAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        // 중복요청 체크
        String checkUrl = "/personal/selectRealTimeUseageAjax.do";
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(checkUrl);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            throw new McpCommonJsonException("0002", TIME_OVERLAP_EXCEPTION);
        }

        String svcCntrNo = personalUser.getSvcCntrNo();
        String ctn = personalUser.getCntrMobileNo();
        String customerId = personalUser.getCustomerId();

        /* [MP] 총 통화시간 조회 (X12) */
        MpTelTotalUseTimeDto vo = callRegService.telTotalUseTimeDto(svcCntrNo, ctn, customerId, null);

        rtnMap.put("useageData"      , vo.getItemTelVOListData());    // 실시간이용량 > 데이터
        rtnMap.put("useageVoice"     , vo.getItemTelVOListVoice());   // 실시간이용량 > 통화
        rtnMap.put("useageSms"       , vo.getItemTelVOListSms());     // 실시간이용량 > 문자
        rtnMap.put("useageEtc"       , vo.getItemTelVOListEtc());     // 실시간이용량 > 기타
        rtnMap.put("useTimeSvcLimit" , vo.isUseTimeSvcLimit());       // 조회가능한 횟수 초과여부

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "처리성공");
        return rtnMap;
    }

    @RequestMapping(value = "/personal/isOverUsageChargeAjax.do")
    @ResponseBody
    public Map<String, Object> isOverUsageChargeAjax(@RequestParam(value = "ncn") String ncn){

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            Map<String, Object> rtnMap = new HashMap<>();
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "개인화 URL 재발급/재인증 부탁드립니다.");
            return rtnMap;
        }

        // 중복요청 체크
        String checkUrl = "/personal/isOverUsageChargeAjax.do";
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl(checkUrl);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            Map<String, Object> rtnMap = new HashMap<>();
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG", TIME_OVERLAP_EXCEPTION);
            return rtnMap;
        }

        String svcCntrNo = personalUser.getSvcCntrNo();
        String ctn = personalUser.getCntrMobileNo();
        String customerId = personalUser.getCustomerId();
        String contractNum = personalUser.getContractNum();

        return callRegService.isOverUsageChargeAjax(svcCntrNo, contractNum, ctn, customerId);

    }

    /**
     * 개인화 url - 요금제 즉시 변경
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/farPricePlanChgAjax.do", "/m/personal/farPricePlanChgAjax.do"})
    @ResponseBody
    public Map<String, Object> farPricePlanChgAjax(@RequestParam(value = "toSocCode") String toSocCode,
                                                   @ModelAttribute McpServiceAlterTraceDto paraAlterTrace){

        Map<String, Object> rtnMap = new HashMap<>();

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, paraAlterTrace.getNcn());
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        // 요금제 변경가능여부 확인 (세션)
        String sessionOtp = SessionUtils.getOtpInfo();
        if(sessionOtp == null || !sessionOtp.equals(personalUser.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
        }

        try{

            // 현재 요금제 조회
            McpFarPriceDto mcpFarPriceDto = mypageService.selectFarPricePlan(personalUser.getContractNum());
            String nowPriceSocCode = mcpFarPriceDto.getPrvRateCd();

            // 요금제 변경일시 조회
            Map<String, String> map = new HashMap<String, String>();
            map.put("soc", mcpFarPriceDto.getPrvRateCd());
            map.put("cntrNo", personalUser.getContractNum());
            String getMonth = mypageService.selectFarPriceAddInfo(map);

            Date nowDay = new Date();
            String thisMonth = DateTimeUtil.changeFormat(nowDay, "yyyyMM");
            String openingDate = personalUser.getLstComActvDate().substring(0,6);

            // 당월 개통
            if(thisMonth.equals(openingDate)){
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("RESULT_MSG", "개통 당월에는 요금제 즉시 변경이 불가합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.");
                return rtnMap;
            }

            // 당월 요금제 변경 (개통 제외)
            if(thisMonth.equals(getMonth)) {
                rtnMap.put("RESULT_CODE", "0004");
                rtnMap.put("RESULT_MSG", "요금제는 월 1회만 변경 가능합니다.<br>예약변경을 이용하시거나 다음달에 요금제 변경을 진행해 주세요.");
                return rtnMap;
            }

            MyPageSearchDto searchVO = new MyPageSearchDto();
            searchVO.setNcn(personalUser.getSvcCntrNo());
            searchVO.setCtn(personalUser.getCntrMobileNo());
            searchVO.setCustId(personalUser.getCustomerId());
            searchVO.setContractNum(personalUser.getContractNum());

            // 부가서비스 해지 + 요금제 변경 + 부가서비스 가입 처리
            Map<String, Object> svcRsltMap = farPricePlanService.getAddSvcInfoDto(searchVO, toSocCode, nowPriceSocCode, paraAlterTrace);

            // 요금제 변경 성공
            if(AJAX_SUCCESS.equals(svcRsltMap.get("RESULT_CODE"))){

                MspRateMstDto rateInfo = mspService.getMspRateMst(toSocCode);
                if(!StringUtils.isEmpty(rateInfo.getJehuProdType())){
                    try {
                        /** 개인정보 활용동의 변경 (Y41) */
                        MpCustInfoAgreeVO agreeParam = MpCustInfoAgreeVO.builder().cnsgInfoAdvrRcvAgreYn("Y").build();
                        mPlatFormService.moscContCustInfoAgreeChg(searchVO.getNcn(), searchVO.getCtn(), searchVO.getCustId(), agreeParam);
                    }catch (Exception ignored) {
                        logger.info("farPricePlanChgAjax.Exception={}", ignored.getMessage());
                    }
                }
            }

            rtnMap.put("RESULT_CODE", svcRsltMap.get("RESULT_CODE"));
            rtnMap.put("RESULT_MSG", svcRsltMap.get("RESULT_MSG"));

        }catch(InterruptedException e){
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("RESULT_MSG", COMMON_EXCEPTION);
        }catch(Exception e){
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("RESULT_MSG", COMMON_EXCEPTION);
        }

        return rtnMap;
    }

    /**
     * 개인화 url - 요금제 예약 변경
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/farPricePlanResChgAjax.do", "/m/personal/farPricePlanResChgAjax.do"})
    @ResponseBody
    public Map<String, Object> farPricePlanResChgAjax(HttpServletRequest request
                                                     ,@RequestParam(value = "ncn") String ncn
                                                                                                     ,@RequestParam(value = "soc") String soc
                                                                                                     ,@RequestParam(value = "befChgRateCd", required=false, defaultValue="") String befChgRateCd
                                                                                                     ,@RequestParam(value = "befChgRateAmnt", required=false, defaultValue="0") String befChgRateAmnt){

        Map<String, Object> rtnMap = new HashMap<>();

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        // 요금제 변경가능여부 확인 (세션)
        String sessionOtp = SessionUtils.getOtpInfo();
        if(sessionOtp == null || !sessionOtp.equals(personalUser.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
        }

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(personalUser.getSvcCntrNo());
        searchVO.setCtn(personalUser.getCntrMobileNo());
        searchVO.setCustId(personalUser.getCustomerId());
        searchVO.setContractNum(personalUser.getContractNum());

        try{

            // 요금제 변경 예약 (X88)
            MoscFarPriceChgDto resOut = farPricePlanService.doFarPricePlanRsrvChg(searchVO, soc, "", request.getRemoteAddr(), "ACEN", befChgRateCd, befChgRateAmnt);

            if(resOut.isSuccess() &&  "Y".equals(resOut.getRsltYn())){
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RESULT_MSG", "처리성공");
            }else{
                List<MoscFarPriceChgDto.M_messageDto> list = resOut.getMsgList();
                MoscFarPriceChgDto.M_messageDto findDto = list.stream().filter(item -> "N".equals(item.getRsltCd())).findFirst().orElse(null);

                String errMsg = (findDto == null) ? COMMON_EXCEPTION  : findDto.getRuleMsgSbst();
                rtnMap.put("RESULT_CODE", "E");
                rtnMap.put("RESULT_MSG", errMsg);
            }

        }catch(McpCommonException e){
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("RESULT_MSG", COMMON_EXCEPTION);
        }catch(Exception e){
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("RESULT_MSG", COMMON_EXCEPTION);
        }

        return rtnMap;
    }

}
