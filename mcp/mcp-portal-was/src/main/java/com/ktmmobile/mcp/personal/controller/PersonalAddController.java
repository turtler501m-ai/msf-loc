package com.ktmmobile.mcp.personal.controller;

import com.ktmmobile.mcp.common.dto.JsonReturnDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.mcp.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.mcp.common.mplatform.vo.MpSocVO;
import com.ktmmobile.mcp.common.util.*;
import com.ktmmobile.mcp.mypage.dto.McpRegServiceDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MyBenefitService;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.RegSvcService;
import com.ktmmobile.mcp.personal.dto.PersonalAddDto;
import com.ktmmobile.mcp.personal.dto.PersonalRoamingDto;
import com.ktmmobile.mcp.personal.service.PersonalService;
import com.ktmmobile.mcp.point.dto.CustPointDto;
import com.ktmmobile.mcp.point.dto.CustPointTxnDto;
import com.ktmmobile.mcp.point.service.PointService;
import com.ktmmobile.mcp.rate.dto.RateAdsvcCtgBasDTO;
import com.ktmmobile.mcp.rate.dto.RateAdsvcGdncBasXML;
import com.ktmmobile.mcp.rate.dto.RateAdsvcGdncProdRelXML;
import com.ktmmobile.mcp.rate.service.RateAdsvcGdncService;
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
import static com.ktmmobile.mcp.common.constants.Constants.REG_SVC_CD_4;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;

@Controller
public class PersonalAddController {

    private static final Logger logger = LoggerFactory.getLogger(PersonalAddController.class);

    private static final String PAGE_TYPE = "ADD";
    private static final String REDIRECT_URL = "/personal/auth.do";

    @Autowired
    private PersonalService personalService;

    @Autowired
    private RegSvcService regSvcService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MyBenefitService myBenefitService;

    @Autowired
    private PointService pointService;

    @Autowired
    private RateAdsvcGdncService rateAdsvcGdncService;


    /**
     * 개인화 url - 부가서비스 신청 및 해지 페이지
     * @Date : 2025.05.26
     */
    @RequestMapping(value= {"/personal/regServiceView.do", "/m/personal/regServiceView.do"})
    public String personalRegServiceView(@ModelAttribute(value = "ncn") String ncn
                                                                            ,@RequestParam(value = "tab", required = false, defaultValue = "1") String tab
                                                                            ,ModelMap model){

        String jspPageName = "/personal/portal/pRegServiceView";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/personal/mobile/pRegServiceView";
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

        // 미성년자 여부
        String birthday = NmcpServiceUtils.getSsnDate(personalUser.getUnUserSSn()); // yyyymmdd
        int age = NmcpServiceUtils.getBirthDateToAmericanAge(birthday, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
        boolean underAge = (age < 19) ? true : false;

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(personalUser.getSvcCntrNo());
        searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
        searchVO.setCtn(StringMakerUtil.getPhoneNum(personalUser.getCntrMobileNo()));

        model.addAttribute("underAge", underAge);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("tab", tab);
        return jspPageName;
    }

    /**
     * 개인화 url - 이용중인 부가서비스 조회
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/myAddSvcListAjax.do", "/m/personal/myAddSvcListAjax.do"})
    @ResponseBody
    public Map<String, Object> myAddSvcListAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();
        String returnCode = null;
        String message = null;

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        String svcCntrNo = personalUser.getSvcCntrNo();
        String ctn = personalUser.getCntrMobileNo();
        String customerId = personalUser.getCustomerId();
        String lstComActvDate = personalUser.getLstComActvDate();

        try{
            /* [MP] 가입중인 부가서비스 조회 (X97) */
            MpAddSvcInfoParamDto res = regSvcService.selectmyAddSvcList(svcCntrNo, ctn, customerId, lstComActvDate);
            List<MpSocVO> outList = res.getList();

            // 아무나솔로 상품 가입시, "MVNO마스터결합전용 더미부가서비스(엠모바일)" 부가상품(PL249Q800)이 자동으로 가입됨
            // → 목록에서 제거
            outList.removeIf(item -> "PL249Q800".equals(item.getSoc()));

            // addDivCd : 일반 부가서비스(G), 로밍 부가서비스(R)
            regSvcService.getMpSocListByDiv(outList, ADSVC_CODE);

            rtnMap.put("outList", outList);

            returnCode = AJAX_SUCCESS;
            message = "조회성공";

        }catch(McpCommonException e){
            returnCode = "0002";
            message = StringUtil.NVL(e.getErrorMsg(), COMMON_EXCEPTION);
        }catch(Exception e){
            returnCode = "0003";
            message = COMMON_EXCEPTION;
        }

        rtnMap.put("RESULT_CODE", returnCode);
        rtnMap.put("RESULT_MSG", message);
        return rtnMap;
    }

    /**
     * 개인화 url - 부가서비스 해지
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/moscRegSvcCanChgAjax.do", "/m/personal/moscRegSvcCanChgAjax.do"})
    @ResponseBody
    public Map<String, Object> moscRegSvcCanChgAjax(@RequestParam(value = "ncn") String ncn
                                                                                                 ,@RequestParam(value = "rateAdsvcCd") String rateAdsvcCd
                                                                                                 ,@RequestParam(value = "prodHstSeq", required = false) String prodHstSeq){

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

            /* [MP] 부가서비스해지 (X38) */
            Map<String, Object> map = null;
            if(StringUtil.isEmpty(prodHstSeq)){
                map = regSvcService.moscRegSvcCanChg(searchVO, rateAdsvcCd);
            }else{
                map = regSvcService.moscRegSvcCanChgSeq(searchVO, rateAdsvcCd, prodHstSeq);
            }

            returnCode = (String) map.get("RESULT_CODE");
            if(!"S".equals(returnCode)){
                message = StringUtil.NVL((String) map.get("message"), COMMON_EXCEPTION);
            }

        }catch(SocketTimeoutException e){
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
     * 개인화 url - 부가서비스 신청 가능 목록 조회
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/addSvcListAjax.do", "/m/personal/addSvcListAjax.do"})
    @ResponseBody
    public Map<String, Object> addSvcListAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();
        String returnCode = null;
        String message = null;

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        String svcCntrNo = personalUser.getSvcCntrNo();
        String ctn = personalUser.getCntrMobileNo();
        String customerId = personalUser.getCustomerId();

        try{

            /* [MP] 가입중인 부가서비스 조회 (X20) */
            List<String> useSocList = regSvcService.selectAddSvcInfoDto(svcCntrNo, ctn, customerId);

            // 부가서비스 조회
            List<McpRegServiceDto> tmpList = mypageService.selectRegService(personalUser.getContractNum());
            List<McpRegServiceDto> list = new ArrayList<>(tmpList);

            List<McpRegServiceDto> listA = new ArrayList<>();  // 유료 부가서비스
            List<McpRegServiceDto> listC = new ArrayList<>();  // 무료 부가서비스

            // 로밍 부가서비스 제외처리
            regSvcService.getMcpRegServiceListByDiv(list, ADSVC_CODE);

            // 가입여부 및 무료여부 세팅
            for(McpRegServiceDto regServiceDto : list){

                if(useSocList.contains(regServiceDto.getRateCd())){ // 사용중인 부가서비스
                    regServiceDto.setUseYn("Y");
                } else {
                    regServiceDto.setUseYn("N");
                }

                if("0".equals(regServiceDto.getBaseAmt()) && "C".equals(regServiceDto.getSvcRelTp())){
                    listC.add(regServiceDto);  // 무료
                }else if("B".equals(regServiceDto.getSvcRelTp())){
                    listC.add(regServiceDto);  // 무료
                }else{
                    listA.add(regServiceDto);  // 유료
                }
            }

            rtnMap.put("list", list);
            rtnMap.put("listA", listA);
            rtnMap.put("listC", listC);

            returnCode = AJAX_SUCCESS;
            message = "조회성공";

        }catch(McpCommonException e){
            returnCode = "0002";
            message = StringUtil.NVL(e.getErrorMsg(), COMMON_EXCEPTION);
        }catch(Exception e){
            returnCode = "0003";
            message = COMMON_EXCEPTION;
        }

        rtnMap.put("RESULT_CODE", returnCode);
        rtnMap.put("RESULT_MSG", message);
        return rtnMap;
    }

    /**
     * 개인화 url - 부가서비스 상세정보 조회
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/getContRateAdditionAjax.do", "/m/personal/getContRateAdditionAjax.do"})
    @ResponseBody
    public Map<String, Object> getContRateAdditionAjax(@RequestParam(value = "rateAdsvcCd") String rateAdsvcCd){

        HashMap<String, Object> rtnMap = new HashMap<>();
        RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
        rateAdsvcCtgBasDTO.setRateAdsvcCd(rateAdsvcCd);
        RateAdsvcGdncBasXML dto = regSvcService.selectAddSvcDtl(rateAdsvcCtgBasDTO);
        rtnMap.put("rateDtl", dto);
        return rtnMap;
    }

    /**
     * 개인화 url - 부가서비스 신청 팝업
     * @Date : 2025.05.26
     */
    @RequestMapping(value= {"/personal/addSvcViewPop.do", "/m/personal/addSvcViewPop.do"})
    public String addSvcViewPop(@RequestParam(value = "ncn") String ncn
                                                         ,@RequestParam(value = "rateAdsvcCd") String rateAdsvcCd
                                                         ,@RequestParam(value = "baseVatAmt", required = false) String baseVatAmt
                                                         ,@RequestParam(value = "rateAdsvcNm", required = false) String rateAdsvcNm
                                                         ,ModelMap model){

        String jspPageName = "/personal/portal/pAddSvcViewPop";
        String errPageName = "/portal/errmsg/errorPop";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/personal/mobile/pAddSvcViewPop";
            errPageName = "/mobile/errmsg/errorPop";

            if(REG_SVC_CD_1.equals(rateAdsvcCd)){ // 불법 TM수신차단
                jspPageName = "/personal/mobile/pAddSvcTmViewPop";
            }else if(REG_SVC_CD_2.equals(rateAdsvcCd)){ // 번호도용차단서비스
                jspPageName = "/personal/mobile/pAddSvcStealNumberPop";
            }else if(REG_SVC_CD_3.equals(rateAdsvcCd)){ // 로밍 하루종일 ON
                jspPageName = "/personal/mobile/pAddSvcRoamingPop";
            }else if(REG_SVC_CD_4.equals(rateAdsvcCd)){ // 요금할인
                jspPageName = "/personal/mobile/pPointAddSvcPop";
            }
        }else{
            if(REG_SVC_CD_1.equals(rateAdsvcCd)){ // 불법 TM수신차단
                jspPageName = "/personal/portal/pAddSvcTmViewPop";
            }else if(REG_SVC_CD_2.equals(rateAdsvcCd)){ // 번호도용차단서비스
                jspPageName = "/personal/portal/pAddSvcStealNumberPop";
            }else if(REG_SVC_CD_3.equals(rateAdsvcCd)){ // 로밍 하루종일 ON
                jspPageName = "/personal/portal/pAddSvcRoamingPop";
            }else if(REG_SVC_CD_4.equals(rateAdsvcCd)){ // 요금할인
                jspPageName = "/personal/portal/pPointAddSvcPop";
            }
        }

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            model.addAttribute("ErrorTitle", "부가서비스 신청");
            model.addAttribute("ErrorMsg", "개인화 URL 재발급/재인증 부탁드립니다.");
            return errPageName;
        }

        // 요금할인 부가서비스인 경우 포인트조회
        CustPointDto custPoint = new CustPointDto();
        if(REG_SVC_CD_4.equals(rateAdsvcCd)) {
            custPoint = myBenefitService.selectCustPoint(personalUser.getSvcCntrNo());
        }

        // 날짜정보
        String nowData = "";
        String stDt = "";
        String etDt = "";

        try{
            nowData = DateTimeUtil.getDateString().replaceAll("-", "");
            stDt = DateTimeUtil.addMonths(DateTimeUtil.getShortDateString(),2,"yyyyMMdd");
            etDt = DateTimeUtil.addMonths(DateTimeUtil.getShortDateString(),6,"yyyyMMdd");
        }catch(ParseException e){
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        model.addAttribute("nowData", nowData);
        model.addAttribute("stDt", stDt);
        model.addAttribute("etDt", etDt);
        model.addAttribute("custPoint", custPoint);
        model.addAttribute("rateAdsvcCd", rateAdsvcCd);
        model.addAttribute("rateAdsvcNm", rateAdsvcNm);
        model.addAttribute("baseVatAmt", baseVatAmt);
        model.addAttribute("ncn", personalUser.getSvcCntrNo());
        return jspPageName;
    }

    /**
     * 개인화 url - 부가서비스 신청
     * @Date : 2025.05.26
     */
    @RequestMapping(value = {"/personal/regSvcChgAjax.do", "/m/personal/regSvcChgAjax.do"})
    @ResponseBody
    public Map<String, Object> regSvcChgAjax(HttpServletRequest request
                                                                                    ,@ModelAttribute PersonalAddDto addDto){

        Map<String, Object> rtnMap = new HashMap<>();
        String returnCode = null;
        String message = null;

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, addDto.getNcn());
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        String svcCntrNo = personalUser.getSvcCntrNo();
        String ctn = personalUser.getCntrMobileNo();
        String customerId = personalUser.getCustomerId();

        try{

            /* [MP] 부가서비스신청 (X21) */
            MpRegSvcChgVO res = regSvcService.regSvcChg(svcCntrNo, ctn, customerId, addDto.getSoc(), addDto.getFtrNewParam());
            if(!res.isSuccess()){
                throw new McpCommonJsonException(COMMON_EXCEPTION);
            }

            // 포인트 사용 시 포인트 감소 처리
            if(REG_SVC_CD_4.equals(addDto.getSoc()) && !StringUtil.isEmpty(addDto.getCouponPrice())){
                CustPointDto custPoint = myBenefitService.selectCustPoint(svcCntrNo);

                if(custPoint != null) {

                    int couponPrice = Integer.parseInt(addDto.getCouponPrice());

                    CustPointTxnDto custPointTxnDto = new CustPointTxnDto();
                    custPointTxnDto.setSvcCntrNo(svcCntrNo);
                    custPointTxnDto.setPoint(couponPrice);
                    custPointTxnDto.setPointTrtCd(POINT_TRT_USE);
                    custPointTxnDto.setPointTxnDtlRsnDesc("부가서비스 포인트 사용(요금할인)");
                    custPointTxnDto.setPointTxnRsnCd(POINT_RSN_CD_U62);
                    custPointTxnDto.setRemainPoint(custPoint.getTotRemainPoint() - couponPrice);
                    custPointTxnDto.setPointDivCd("MP");
                    custPointTxnDto.setCretId("ACEN");
                    custPointTxnDto.setCretIp(request.getRemoteAddr());
                    custPointTxnDto.setPointCustSeq(custPoint.getPointCustSeq());
                    pointService.editPoint(custPointTxnDto);
                }
            }

            returnCode = AJAX_SUCCESS;
            message = "처리성공";

        }catch(McpCommonJsonException e){
            returnCode = "0002";
            message = StringUtil.NVL(e.getErrorMsg(), COMMON_EXCEPTION);
        }catch(SelfServiceException e){
            returnCode = "0003";
            message = StringUtil.NVL(e.getMessageNe(), COMMON_EXCEPTION);
        }catch(Exception e){
            returnCode = "0004";
            message = COMMON_EXCEPTION;
        }

        rtnMap.put("RESULT_CODE", returnCode);
        rtnMap.put("RESULT_MSG", message);
        return rtnMap;
    }

    /**
     * 개인화 url - 로밍 신청 및 해지 페이지
     * @Date : 2025.07.11
     */
    @RequestMapping(value= {"/personal/roamingView.do", "/m/personal/roamingView.do"})
    public String personalRoamingView(@ModelAttribute(value = "ncn") String ncn
                                                                   ,@RequestParam(value = "tab", required = false, defaultValue = "1") String tab
                                                                     ,ModelMap model){

        String jspPageName = "/personal/portal/pRoamingView";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/personal/mobile/pRoamingView";
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

        // 미성년자 여부
        String birthday = NmcpServiceUtils.getSsnDate(personalUser.getUnUserSSn()); // yyyymmdd
        int age = NmcpServiceUtils.getBirthDateToAmericanAge(birthday, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
        boolean underAge = (age < 19) ? true : false;

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(personalUser.getSvcCntrNo());
        searchVO.setUserName(StringMakerUtil.getName(personalUser.getUserName()));
        searchVO.setCtn(StringMakerUtil.getPhoneNum(personalUser.getCntrMobileNo()));

        model.addAttribute("underAge", underAge);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("tab", tab);
        return jspPageName;
    }

    /**
     * 개인화 url - 이용중인 로밍 조회
     * @Date : 2025.07.11
     */
    @RequestMapping(value = {"/personal/myRoamJoinListAjax.do", "/m/personal/myRoamJoinListAjax.do"})
    @ResponseBody
    public Map<String, Object> myRoamJoinListAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();
        String returnCode = null;
        String message = null;

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        String svcCntrNo = personalUser.getSvcCntrNo();
        String ctn = personalUser.getCntrMobileNo();
        String customerId = personalUser.getCustomerId();
        String lstComActvDate = personalUser.getLstComActvDate();

        try{
            /* [MP] 가입중인 부가서비스 조회 (X97) */
            MpAddSvcInfoParamDto res = regSvcService.selectmyAddSvcList(svcCntrNo, ctn, customerId, lstComActvDate);
            List<MpSocVO> outList = res.getList();                     // 상품정보(mp)
            List<RateAdsvcCtgBasDTO> prodInfoList = new ArrayList<>(); // 상품정보(xml)

            // addDivCd : 일반 부가서비스(G), 로밍 부가서비스(R)
            regSvcService.getMpSocListByDiv(outList, ROAMING_ADSVC_CODE);

            for(MpSocVO mpSoc : outList){

                // 로밍 변경 가능여부
                mpSoc.setUpdateFlag(regSvcService.getUpdateYn(mpSoc));

                // 상품정보(xml) 조회
                RateAdsvcGdncProdRelXML prodRelXML = rateAdsvcGdncService.getRateAdsvcProdRelBySoc(mpSoc.getSoc());

                RateAdsvcCtgBasDTO ctgSearchDTO = new RateAdsvcCtgBasDTO();
                ctgSearchDTO.setRateAdsvcGdncSeq(prodRelXML.getRateAdsvcGdncSeq());
                RateAdsvcCtgBasDTO ctgBasDTO = rateAdsvcGdncService.getProdBySeq(ctgSearchDTO);
                prodInfoList.add(ctgBasDTO);

                // 종료일 서버세팅 (가입기간 입력 유형: 시작일)
                if("1".equals(ctgBasDTO.getDateType())){
                    mpSoc.setEndDttm(regSvcService.getEndDttmUsePrd(mpSoc, ctgBasDTO.getUsePrd()));
                }

                // 회선 유형에 따라 대표/서브 전화번호 조회
                if("M".equals(ctgBasDTO.getLineType())){

                    // 대표상품 > 서브회선 전화번호 조회
                    List<String> subNcnList = mpSoc.getShareSubContidList();
                    List<String> subCtnList = null;

                    if(subNcnList != null){
                        subCtnList = subNcnList.stream().map(item -> {
                            String subCtn = regSvcService.getCtnByNcn(item, true);
                            return StringUtil.NVL(subCtn, "-");
                        }).collect(Collectors.toList());
                    }

                    mpSoc.setShareSubCtnList(subCtnList);

                }else if("S".equals(ctgBasDTO.getLineType())){

                    // 서브상품 > 대표회선 전화번호 조회
                    String mainCtn = regSvcService.getCtnByNcn(mpSoc.getShareMainContid(), true);
                    mpSoc.setShareMainCtn(StringUtil.NVL(mainCtn, "-"));
                }
            }

            rtnMap.put("prodInfoList", prodInfoList);
            rtnMap.put("outList", outList);

            returnCode = AJAX_SUCCESS;
            message = "조회성공";

        }catch(McpCommonException e){
            returnCode = "0002";
            message = StringUtil.NVL(e.getErrorMsg(), COMMON_EXCEPTION);
        }catch(Exception e){
            returnCode = "0003";
            message = COMMON_EXCEPTION;
        }

        rtnMap.put("RESULT_CODE", returnCode);
        rtnMap.put("RESULT_MSG", message);
        return rtnMap;
    }

    /**
     * 개인화 url - 로밍 신청 가능 목록 조회
     * @Date : 2025.07.11
     */
    @RequestMapping(value = {"/personal/regRoamListAjax.do", "/m/personal/regRoamListAjax.do"})
    @ResponseBody
    public Map<String, Object> regRoamListAjax(@RequestParam(value = "ncn") String ncn){

        Map<String, Object> rtnMap = new HashMap<>();

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        // 로밍 부가서비스 조회 (xml)
        List<RateAdsvcCtgBasDTO> dupList = regSvcService.getRoamList();
        List<RateAdsvcCtgBasDTO> list = rateAdsvcGdncService.deduplicateProdList(dupList);

        rtnMap.put("list", list);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_MSG", "조회성공");
        return rtnMap;
    }

    /**
     * 개인화 url - 로밍 신청 및 변경 팝업
     * @Date : 2025.07.11
     */
    @RequestMapping(value= {"/personal/roamingViewPop.do", "/m/personal/roamingViewPop.do"})
    public String roamingViewPop(@RequestParam(value = "ncn") String ncn
                                                            ,@RequestParam(value = "flag", required = false, defaultValue = "N") String flag
                                                            ,@RequestParam(value = "prodHstSeq", required = false) String prodHstSeq
                              ,@ModelAttribute RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO
                                                          ,ModelMap model){

        String jspPageName = "/personal/portal/pRoamingViewPop";
        String errPageName = "/portal/errmsg/errorPop";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/personal/mobile/pRoamingViewPop";
            errPageName = "/mobile/errmsg/errorPop";
        }

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, ncn);
        if(personalUser == null){
            model.addAttribute("ErrorTitle", "Y".equals(flag) ? "로밍 변경" : "로밍 신청");
            model.addAttribute("ErrorMsg", "개인화 URL 재발급/재인증 부탁드립니다.");
            return errPageName;
        }

        // 로밍 정보 조회 (xml)
        RateAdsvcCtgBasDTO prodInfo = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);
        RateAdsvcGdncProdRelXML prodRel = rateAdsvcGdncService.getRateAdsvcGdncProdRel(rateAdsvcCtgBasDTO);

        if(prodInfo.getRateAdsvcGdncSeq() <= 0 || prodRel.getRateAdsvcGdncSeq() <= 0){
            model.addAttribute("ErrorTitle", "Y".equals(flag) ? "로밍 변경" : "로밍 신청");
            model.addAttribute("ErrorMsg", "상품 정보가 존재하지 않습니다.");
            return errPageName;
        }

        // 필수 입력회선 개수 (서브상품은 대표회선 필수입력)
        String reqLineCnt = REG_SVC_CD_5.equals(prodRel.getRateAdsvcCd()) ? "1" : "0";
        if("S".equals(prodInfo.getLineType())) reqLineCnt = "1";

        model.addAttribute("ncn", personalUser.getSvcCntrNo());
        model.addAttribute("prodInfo", prodInfo);
        model.addAttribute("prodRel", prodRel);
        model.addAttribute("flag", flag);
        model.addAttribute("prodHstSeq", prodHstSeq);
        model.addAttribute("reqLineCnt", reqLineCnt);
        return jspPageName;
    }

    /**
     * 개인화 url - 로밍 신청
     * @Date : 2025.07.11
     */
    @RequestMapping(value = {"/personal/roamingJoinAjax.do", "/m/personal/roamingJoinAjax.do"})
    @ResponseBody
    public Map<String, Object> roamingJoinAjax(@ModelAttribute PersonalRoamingDto roamingDto
                                            ,@ModelAttribute RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO){

        Map<String, Object> rtnMap = new HashMap<>();
        String returnCode = null;
        String message = null;

        // 개인화 URL SMS인증 완료 사용자 계약정보 조회
        McpUserCntrMngDto personalUser = personalService.getPersonalUser(PAGE_TYPE, roamingDto.getNcn());
        if(personalUser == null){
            throw new McpCommonJsonException("0001", "개인화 URL 재발급/재인증 부탁드립니다.");
        }

        // 상품 정보 조회 (xml)
        RateAdsvcCtgBasDTO prod = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);

        // 로밍 가입 유효성 검사
        JsonReturnDto jsonReturnDto = rateAdsvcGdncService.regRoamValidationChk(prod, roamingDto.getSoc()
            , roamingDto.getStrtDt(), roamingDto.getStrtTm(), roamingDto.getEndDt(), roamingDto.getAddPhone());

        if(!"00".equals(jsonReturnDto.getReturnCode())) {
            throw new McpCommonJsonException(jsonReturnDto.getReturnCode(), jsonReturnDto.getMessage());
        }

        String svcCntrNo = personalUser.getSvcCntrNo();

        MyPageSearchDto searchVO = new MyPageSearchDto();
        searchVO.setNcn(svcCntrNo);
        searchVO.setCtn(personalUser.getCntrMobileNo());
        searchVO.setCustId(personalUser.getCustomerId());

        try{

            // 서브상품인 경우 대표상품 정보 조회
            String mtProdHstSeq = "";

            if("S".equals(prod.getLineType())){
                String mtCd = prod.getMtCd();
                mtProdHstSeq = rateAdsvcGdncService.getMtProdHstSeq(mtCd, roamingDto.getStrtDt(), roamingDto.getEndDt(), roamingDto.getAddPhone()[0], svcCntrNo);

                if(StringUtil.isEmpty(mtProdHstSeq)) {
                    throw new McpCommonJsonException("입력한 번호로 정보를 조회할 수 없습니다.<br><br>대표자의 가입 정보 확인 후 다시 시도하시기 바랍니다.");
                }
            }

            // 로밍 신청을 위한 부가정보 세팅
            String ftrNewParam = rateAdsvcGdncService.getRoamParam(prod, roamingDto.getSoc(), roamingDto.getStrtDt()
                , roamingDto.getStrtTm(), roamingDto.getEndDt(), roamingDto.getAddPhone(), mtProdHstSeq);

            // 부가서비스 변경인 경우: 기존 부가서비스 해지 후 재가입
            if("Y".equals(roamingDto.getFlag())){

                /* [MP] 부가서비스해지 (X38) */
                Map<String, Object> map = regSvcService.moscRegSvcCanChgSeq(searchVO, roamingDto.getSoc(), roamingDto.getProdHstSeq());
                if(!"S".equals(map.get("RESULT_CODE"))){
                    throw new McpCommonJsonException((String) map.get("message"));
                }
            }

            /* [MP] 부가서비스신청 (X21) */
            MpRegSvcChgVO res = regSvcService.regSvcChg(svcCntrNo, searchVO.getCtn(), searchVO.getCustId(), roamingDto.getSoc(), ftrNewParam);
            if(!res.isSuccess()){
                throw new McpCommonJsonException(COMMON_EXCEPTION);
            }

            returnCode = AJAX_SUCCESS;
            message = "처리성공";

        }catch(McpCommonJsonException e){
            returnCode = StringUtil.NVL(e.getRtnCode(), "0002");
            message = StringUtil.NVL(e.getErrorMsg(), COMMON_EXCEPTION);
        }catch(SelfServiceException e){
            returnCode = "0003";
            message = StringUtil.NVL(e.getMessageNe(), COMMON_EXCEPTION);
        }catch(SocketTimeoutException e){
            returnCode = "0004";
            message = COMMON_EXCEPTION;
        }catch(Exception e){
            returnCode = "0005";
            message = COMMON_EXCEPTION;
        }

        rtnMap.put("RESULT_CODE", returnCode);
        rtnMap.put("RESULT_MSG", message);
        return rtnMap;
    }

}
