package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;
import java.net.SocketTimeoutException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpRegServiceDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfRegSvcService;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.JsonReturnDto;
import com.ktmmobile.msf.system.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoParamDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringUtil;

@Controller
public class MsfRegSvcController {

    private static final Logger logger = LoggerFactory.getLogger(MsfRegSvcController.class);

    @Autowired
    private MsfMypageSvc mypageService;

    @Autowired
    private MsfRegSvcService regSvcService;

//    @Autowired
//    private MyBenefitService myBenefitService;
//
//    @Autowired
//    private PointService pointService;

//    @Autowired
//    RateAdsvcGdncService rateAdsvcGdncService;

    @Autowired
    CertService certService;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 부가서비스 신청 목록
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/mypage/regServiceView.do", "/m/mypage/regServiceView.do" })
    public String regServiceView(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String returnUrl = "/portal/mypage/regServiceView";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/mypage/regServiceView";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 380
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        //미성년자 체크
        int userAge= 0;
        boolean underAge = false;
        if(userSession.getBirthday() != null){
            userAge= NmcpServiceUtils.getBirthDateToAmericanAge(userSession.getBirthday(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            if(userAge < 19) {
                underAge = true;
            }
        }

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);
        }

        model.addAttribute("underAge", underAge);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        return returnUrl;

    }

    /**
     * 이용중인 부가서비스 목록 조회
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/mypage/myAddSvcListAjax.do", "/m/mypage/myAddSvcListAjax.do" })
    @ResponseBody
    public Map<String, Object> myAddSvcListAjax(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {
        String addDivCd = StringUtil.NVL(searchVO.getAddDivCd(), "");

        Map<String, Object> rtnMap = new HashMap<String, Object>();
        String returnUrl2 = "/mypage/updateForm.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl2 = "/m/mypage/updateForm.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSession != null) { // 취약성 378
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION, returnUrl2);
        }

        // x97
        // 이용중인 부가서비스
        MpAddSvcInfoParamDto res = regSvcService.selectmyAddSvcList(searchVO.getNcn(), searchVO.getCtn(),
                searchVO.getCustId(), cntrList.get(0).getLstComActvDate());

        List<MpSocVO> outList = res.getList();

        //아무나솔로 상품 가입시, 내부 관리를 위해  "MVNO마스터결합전용 더미부가서비스(엠모바일)" 부가상품(PL249Q800)이 자동으로 가입
        //목록에서 제거
        outList.removeIf(item -> item.getSoc().equals("PL249Q800"));

        //"G" 일반 부가서비스만 조회
        //"R" 로밍 부가서비스만 조회
        regSvcService.getMpSocListByDiv(outList, addDivCd);

        rtnMap.put("outList", outList);
        rtnMap.put("contractNum", searchVO.getContractNum());
        return rtnMap;
    }

    /**
     * 부가서비스 신청 목록 조회 AJAX
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @return
     */

    @RequestMapping(value = { "/mypage/addSvcListAjax.do", "/m/mypage/addSvcListAjax.do" })
    @ResponseBody
    public HashMap<String, Object> addSvcListAjax(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {
        String addDivCd = StringUtil.NVL(searchVO.getAddDivCd(), "");

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String url = "/mypage/updateForm.do";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { //취약성 372
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
             throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION,url);
        }

        // 이용중인 부가서비스 조회
        // 사용중인 부가서비스 SOC 리스트 x21
        List<String> useSocList = regSvcService.selectAddSvcInfoDto(searchVO.getNcn(), searchVO.getCtn(),
                searchVO.getCustId());

        try {

            // 부가서비스 조회
            List<McpRegServiceDto> tmpList = mypageService.selectRegService(searchVO.getContractNum());
            List<McpRegServiceDto> list = new ArrayList<McpRegServiceDto>(tmpList); //iterator remove 사용 위해 재선언

            List<McpRegServiceDto> listA = new ArrayList<McpRegServiceDto>();
            List<McpRegServiceDto> listC = new ArrayList<McpRegServiceDto>();

            regSvcService.getMcpRegServiceListByDiv(list, addDivCd);

            for (int i = 0; i < list.size(); i++) {

                if (useSocList.contains(list.get(i).getRateCd())) { // 사용중인 부가서비스일 경우
                    list.get(i).setUseYn("Y");
                } else {
                    list.get(i).setUseYn("N");
                }

                if ((list.get(i).getBaseAmt().equals("0") && list.get(i).getSvcRelTp().equals("C"))
                        || list.get(i).getSvcRelTp().equals("B")) {// 무료
                    listC.add(list.get(i));
                } else {// 유료
                    listA.add(list.get(i));
                }
            }

            rtnMap.put("listC", listC);
            rtnMap.put("listA", listA);
            rtnMap.put("list", list);

        } catch (SelfServiceException e) {
            throw new McpCommonException(e.getMessage());
        }

        rtnMap.put("ctn", searchVO.getCtn());
        rtnMap.put("contractNum", searchVO.getContractNum());

        return rtnMap;
    }

    /**
     * 부가서비스 선택시 부가 상세정보 AJAX
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param rateAdsvcCd
     * @return
     */

//    @RequestMapping(value = { "/mypage/getContRateAdditionAjax.do", "/m/mypage/getContRateAdditionAjax.do" })
//    @ResponseBody
//    public HashMap<String, Object> getContRateAdditionAjax(HttpServletRequest request, Model model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
//            @RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd) {
//
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
//        rateAdsvcCtgBasDTO.setRateAdsvcCd(rateAdsvcCd);
//        RateAdsvcGdncBasXML dto = regSvcService.selectAddSvcDtl(rateAdsvcCtgBasDTO);
//        rtnMap.put("rateDtl", dto);
//        return rtnMap;
//    }

    /**
     * 부가서비스 해지 신청
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param rateAdsvcCd
     * @return
     * @throws SocketTimeoutException
     */

    @RequestMapping(value = { "/mypage/moscRegSvcCanChgAjax.do", "/m/mypage/moscRegSvcCanChgAjax.do" })
    @ResponseBody
    public Map<String, Object> moscRegSvcCanChgAjax(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            @RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd) throws SocketTimeoutException {

        Map<String, Object> rtnMap = new HashMap<String, Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if ( userSession != null ) { // 취약성 384
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("message", F_BIND_EXCEPTION);
            return rtnMap;
        }

        // 부가서비스 해지 서비스 호출 X38
        rtnMap = regSvcService.moscRegSvcCanChg(searchVO, rateAdsvcCd);

        return rtnMap;
    }

    /**
     * 부가서비스 공통 신청 화면 POP VIEW
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param rateAdsvcCd
     * @param baseVatAmt
     * @param rateAdsvcNm
     * @param flag
     * @return
     * @throws ParseException
     */

    @RequestMapping(value = { "/mypage/addSvcViewPop.do", "/m/mypage/addSvcViewPop.do" })
    public String addSvcViewPop(HttpServletRequest request, Model model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO,
            @RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd,
            @RequestParam(value = "baseVatAmt", required = false) String baseVatAmt,
            @RequestParam(value = "rateAdsvcNm", required = false) String rateAdsvcNm,
            @RequestParam(value = "flag", required = false) String flag) throws ParseException {

        String returnUrl = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 386
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile/mypage/addSvcViewPop";
            if (Constants.REG_SVC_CD_1.equals(rateAdsvcCd)) { // 불법 TM수신차단
                returnUrl = "/mobile/mypage/addSvcTmViewPop";
            } else if (Constants.REG_SVC_CD_2.equals(rateAdsvcCd)) { // 번호도용차단서비스
                returnUrl = "/mobile/mypage/addSvcStealNumberPop";
            } else if (Constants.REG_SVC_CD_3.equals(rateAdsvcCd)) { // 로밍 하루종일 ON 부가서비스
                returnUrl = "/mobile/mypage/addSvcRoamingPop";
            } else if (Constants.REG_SVC_CD_4.equals(rateAdsvcCd)) { // 포인트할인
                returnUrl = "/mobile/mypage/pointAddSvcPop";
            }
        } else {
            returnUrl = "/portal/mypage/addSvcViewPop";
            if (Constants.REG_SVC_CD_1.equals(rateAdsvcCd)) { // 불법 TM수신차단
                returnUrl = "/portal/mypage/addSvcTmViewPop";
            } else if (Constants.REG_SVC_CD_2.equals(rateAdsvcCd)) { // 번호도용차단서비스
                returnUrl = "/portal/mypage/addSvcStealNumberPop";
            } else if (Constants.REG_SVC_CD_3.equals(rateAdsvcCd)) { // 로밍 하루종일 ON 부가서비스
                returnUrl = "/portal/mypage/addSvcRoamingPop";
            } else if (Constants.REG_SVC_CD_4.equals(rateAdsvcCd)) { // 포인트할인
                returnUrl = "/portal/mypage/pointAddSvcPop";
            }
        }

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String stDt = DateTimeUtil.addMonths(DateTimeUtil.getShortDateString(),2,"yyyyMMdd");
        String etDt = DateTimeUtil.addMonths(DateTimeUtil.getShortDateString(),6,"yyyyMMdd");
        String nowData = DateTimeUtil.getDateString().replaceAll("-", "");

//        CustPointDto custPoint = new CustPointDto();

//        // 포인트조회
//        if (Constants.REG_SVC_CD_4.equals(rateAdsvcCd)) {
//            custPoint = myBenefitService.selectCustPoint(searchVO.getNcn());
//        }

        model.addAttribute("stDt", stDt);
        model.addAttribute("etDt", etDt);
        model.addAttribute("nowData", nowData);
//        model.addAttribute("custPoint", custPoint);
        model.addAttribute("flag", flag);
        model.addAttribute("rateAdsvcCd", rateAdsvcCd);
        model.addAttribute("rateAdsvcNm", rateAdsvcNm);
        model.addAttribute("baseVatAmt", baseVatAmt);
        model.addAttribute("contractNum", searchVO.getContractNum());

        return returnUrl;
    }

    /**
     * 부가서비스 신청 AJAX
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param model
     * @param searchVO
     * @param soc
     * @param ftrNewParam
     * @param ctnVal
     * @param couoponPrice
     * @param flag
     * @return
     * @throws SocketTimeoutException
     */

    @RequestMapping(value = { "/mypage/regSvcChgAjax.do", "/m/mypage/regSvcChgAjax.do" })
    @ResponseBody
    public JsonReturnDto  regSvcChgAjax(HttpServletRequest request, ModelMap model,
            @ModelAttribute("searchVO") MyPageSearchDto searchVO, String soc, String ftrNewParam, String ctnVal
            ,String couoponPrice
            ,String flag) throws SocketTimeoutException {

        String resultCode = "99";
        String message = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }

        String ip = request.getRemoteAddr();

        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 374
            cntrList = mypageService.selectCntrList(userSession.getUserId());
        }
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
             throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION,url);
        }

        JsonReturnDto jsonReturnDto = new JsonReturnDto();
        Map<String, Object> rtnMap = new HashMap<String, Object>();

        // ============ STEP START ============
        String referer = StringUtil.NVL(request.getHeader("referer"), "");

        //당겨쓰기인 경우만 해당 step 에 포함
        if(referer.contains("/pullData01.do")){

            // 1. 최소 스텝 수 체크
            if(certService.getStepCnt() < 3 ){
                jsonReturnDto.setReturnCode("STEP01");
                jsonReturnDto.setMessage(STEP_CNT_EXCEPTION);
                return jsonReturnDto;
            }

            // 2. 최종 데이터 체크: step종료 여부, 계약번호, 핸드폰번호
            String[] certKey = {"urlType", "stepEndYn", "contractNum", "mobileNo"};
            String[] certValue = {"regSvcChg", "Y", searchVO.getContractNum(), searchVO.getCtn()};
            Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
                jsonReturnDto.setReturnCode("STEP02");
                jsonReturnDto.setMessage(vldReslt.get("RESULT_DESC"));
                return jsonReturnDto;
            }
        }
        // ============ STEP END ============

        try {

            //일단 변경은 로밍만 해당됨
            if("Y".equals(flag)) {
                //부가서비스 해지
                rtnMap = regSvcService.moscRegSvcCanChg(searchVO, soc);

                if("S".equals(rtnMap.get("RESULT_CODE"))) {
                    //부가서비스 신청
                    regSvcService.regSvcChg(searchVO.getNcn(),searchVO.getCtn(), searchVO.getCustId(),soc, ftrNewParam);
                    resultCode = "00";
                    message = "";
                } else {
                    resultCode = (String) rtnMap.get("RESULT_CODE");
                    message = (String) rtnMap.get("message");
                }
            } else {
                //부가서비스 신청
                MpRegSvcChgVO res = regSvcService.regSvcChg(searchVO.getNcn(),searchVO.getCtn(), searchVO.getCustId(),soc, ftrNewParam);
                resultCode = "00";
                message = "";

                if (Constants.REG_SVC_CD_4.equals(soc)) {
//                    if(couoponPrice != null && !"".equals(couoponPrice)) {
//                        CustPointDto custPoint = myBenefitService.selectCustPoint(searchVO.getNcn());
//                        if(custPoint != null) {
//                            CustPointTxnDto custPointTxnDto = new CustPointTxnDto();
//                            int strCouoponPrice = Integer.parseInt(couoponPrice);
//                            custPointTxnDto.setSvcCntrNo(searchVO.getNcn()); //cntrNo
//                            custPointTxnDto.setPoint(strCouoponPrice); // 사용포인트
//                            custPointTxnDto.setPointTrtCd(POINT_TRT_USE); // 사용사유코드
//                            custPointTxnDto.setPointTxnDtlRsnDesc("부가서비스 포인트 사용(요금할인)");		// 사용 설명
//                            custPointTxnDto.setPointTxnRsnCd(POINT_RSN_CD_U62); // 사용사유
//                            custPointTxnDto.setRemainPoint(custPoint.getTotRemainPoint() - strCouoponPrice); // 잔액
//                            custPointTxnDto.setPointDivCd("MP"); // 포인트종류
//                            custPointTxnDto.setCretId(userSession.getUserId());
//                            custPointTxnDto.setCretIp(ip);
//                            custPointTxnDto.setPointCustSeq(custPoint.getPointCustSeq());
//                            pointService.editPoint(custPointTxnDto);
//                            resultCode = "00";
//                            message = "";
//                        }
//                    }

                }

            }

        } catch (SelfServiceException e) {
             resultCode = getErrCd(e.getMessage());//N:성공, S:시스템에서, E:Biz 에러
             message = getErrMsg(e.getMessage());
        }  catch (SocketTimeoutException e){
            resultCode = getErrCd("S");//N:성공, S:시스템에서, E:Biz 에러
            message = getErrMsg(SOCKET_TIMEOUT_EXCEPTION);
        }

        jsonReturnDto.setReturnCode(resultCode);
        jsonReturnDto.setMessage(message);
        return jsonReturnDto;

    }

//PNB_확인
//    @RequestMapping(value = "/xmlInfo/getRateAdsvcDtlAjax.do")
//    @ResponseBody
//    public Map<String, Object> getRateAdsvcGdncDtlXml(@RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd)  {
//        Map<String, Object> rtnMap = new HashMap<String, Object>();
//        List<RateAdsvcGdncDtlXML> rtnXmlList = null;
//
//        try {
//            rtnXmlList = rateAdsvcGdncService.getRateAdsvcGdncDtlXml(rateAdsvcCd);
//        } catch(DataAccessException e) {
//            return null;
//        } catch(Exception e) {
//            return null;
//        }
//
//        rtnMap.put("DATA_OBJ", rtnXmlList);
//        return rtnMap;
//    }


    private String getErrCd(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        result = arg[0]; //N:성공, S:시스템에서, E:Biz 에러
        return result;
    }

    private String getErrMsg(String msg) {
        String result;
        String[] arg = msg.split(";;;");
        if(arg.length > 1) {
            result = arg[1];
        } else {
            result = arg[0];
        }
        return result;
    }

    private ResponseSuccessDto getMessageBox() {
        ResponseSuccessDto mbox = new ResponseSuccessDto();

        String url = "/mypage/updateForm.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            url = "/m/mypage/updateForm.do";
        }
        mbox.setRedirectUrl(url);
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }

//    /**
//     *  마이페이지 해외로밍 페이지
//     * @Date : 2023.06.15
//     * @param request
//     * @param model
//     * @param searchVO
//     * @return
//     */
//
//    @RequestMapping(value = { "/mypage/roamingView.do", "/m/mypage/roamingView.do" })
//    public String roamingView(HttpServletRequest request, ModelMap model,@ModelAttribute("searchVO") MyPageSearchDto searchVO) {
//
//        String returnUrl = "/portal/mypage/roamingView";
//
//        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
//            returnUrl = "/mobile/mypage/roamingView";
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        logger.error("userSession.getUserId()"+userSession.getUserId());
////		if(null == userSession || StringUtils.isEmpty(userSession.getUserId())) {
////			return "redirect:/loginForm.do";
////		}
//        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
//        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//        //미성년자 체크
//        int userAge= 0;
//        boolean underAge = false;
//        if(userSession.getBirthday() != null){
//            userAge= NmcpServiceUtils.getBirthDateToAmericanAge(userSession.getBirthday(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
//            if(userAge < 19) {
//                underAge = true;
//            }
//        }
//
//        // 마스킹해제
//        if(SessionUtils.getMaskingSession() > 0 ) {
//            model.addAttribute("maskingSession", "Y");
//            MaskingDto maskingDto = new MaskingDto();
//
//            long maskingRelSeq = SessionUtils.getMaskingSession();
//            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
//            maskingDto.setUnmaskingInfo("휴대폰번호");
//            maskingDto.setAccessIp(ipstatisticService.getClientIp());
//            maskingDto.setAccessUrl(request.getRequestURI());
//            maskingDto.setUserId(userSession.getUserId());
//            maskingDto.setCretId(userSession.getUserId());
//            maskingDto.setAmdId(userSession.getUserId());
//            maskingSvc.insertMaskingReleaseHist(maskingDto);
//        }
//
//        model.addAttribute("underAge", underAge);
//        model.addAttribute("cntrList", cntrList);
//        model.addAttribute("searchVO", searchVO);
//        return returnUrl;
//
//    }
//
//    /**
//     * 이용중인 로밍서비스 목록 조회
//     * @author 김동혁
//     * @Date : 2023.06.20
//     * @param request
//     * @param model
//     * @param searchVO
//     * @return
//     */
//
//    @RequestMapping(value = { "/mypage/myRoamJoinListAjax.do", "/m/mypage/myRoamJoinListAjax.do" })
//    @ResponseBody
//    public Map<String, Object> myRoamJoinListAjax(HttpServletRequest request, Model model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {
//        String addDivCd = StringUtil.NVL(searchVO.getAddDivCd(), "");
//
//        Map<String, Object> rtnMap = new HashMap<String, Object>();
//        String returnUrl2 = "/mypage/updateForm.do";
//
//        if ("Y".equals(NmcpServiceUtils.isMobile())) {
//            returnUrl2 = "/m/mypage/updateForm.do";
//        }
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
//        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION, returnUrl2);
//        }
//
//        // x97
//        // 이용중인 부가서비스
//        MpAddSvcInfoParamDto res = regSvcService.selectmyAddSvcList(searchVO.getNcn(), searchVO.getCtn(),
//                searchVO.getCustId(),cntrList.get(0).getLstComActvDate());
//
//        List<MpSocVO> outList = res.getList();
//        List<RateAdsvcCtgBasDTO> prodInfoList = new ArrayList<RateAdsvcCtgBasDTO>();
//
//        //"G" 일반 부가서비스만 조회
//        //"R" 로밍 부가서비스만 조회
//        if(outList != null && !outList.isEmpty()) {
//            regSvcService.getMpSocListByDiv(outList, addDivCd);
//
//            //outList에 있는 상품코드로 상품정보 가져오기
//            for(MpSocVO mpSoc : outList) {
//                RateAdsvcGdncProdRelXML rateAdsvcGdncProdRelXML = new RateAdsvcGdncProdRelXML();
//                RateAdsvcCtgBasDTO rateAdsvcCtgBasSeqDTO = new RateAdsvcCtgBasDTO();
//                RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO = new RateAdsvcCtgBasDTO();
//
//                rateAdsvcGdncProdRelXML = rateAdsvcGdncService.getRateAdsvcProdRelBySoc(mpSoc.getSoc());
//                rateAdsvcCtgBasSeqDTO.setRateAdsvcGdncSeq(rateAdsvcGdncProdRelXML.getRateAdsvcGdncSeq());
//                rateAdsvcCtgBasDTO = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasSeqDTO);
//                prodInfoList.add(rateAdsvcCtgBasDTO);
//
//                //해지가 가능하고 상품시작일자가 아직 도래하지 않은 로밍 상품은 신청내용 변경 가능
//                mpSoc.setUpdateFlag(regSvcService.getUpdateYn(mpSoc));
//
//                //dateType 1(시작일만 입력)이면 종료일을 (시작일+이용가능기간-1)일로 set
//                if("1".equals(rateAdsvcCtgBasDTO.getDateType())) {
//                    mpSoc.setEndDttm(regSvcService.getEndDttmUsePrd(mpSoc, rateAdsvcCtgBasDTO.getUsePrd()));
//                }
//
//                //대표상품 서브계약번호로 서브회선 가져오는 기능 추가
//                if("M".equals(rateAdsvcCtgBasDTO.getLineType())) {
//                    List<String> subNcnList = mpSoc.getShareSubContidList();
//                    List<String> subCtnList = null;
//
//                    if(subNcnList != null){
//                        subCtnList = subNcnList.stream().map(item -> {
//                            String subCtn = regSvcService.getCtnByNcn(item, true);
//                            return StringUtil.NVL(subCtn, "-");
//                        }).collect(Collectors.toList());
//                    }
//
//                    mpSoc.setShareSubCtnList(subCtnList);
//                }
//
//                //서브상품 대표계약번호로 대표회선 get
//                if("S".equals(rateAdsvcCtgBasDTO.getLineType())) {
//                    String mainCtn = regSvcService.getCtnByNcn(mpSoc.getShareMainContid(), true);
//                    mpSoc.setShareMainCtn(StringUtil.NVL(mainCtn, "-"));
//                }
//            }
//        }
//
//        rtnMap.put("prodInfoList", prodInfoList);
//        rtnMap.put("outList", outList);
//        rtnMap.put("contractNum", searchVO.getContractNum());
//        return rtnMap;
//    }
//
//    /**
//     * 로밍 부가서비스 신청 목록 조회 AJAX
//     * @author 김동혁
//     * @Date : 2023.06.20
//     * @param request
//     * @param model
//     * @param searchVO
//     * @return rtnMap
//     */
//
//    @RequestMapping(value = { "/mypage/regRoamListAjax.do", "/m/mypage/regRoamListAjax.do" })
//    @ResponseBody
//    public HashMap<String, Object> regRoamListAjax(HttpServletRequest request, Model model,
//            @ModelAttribute("searchVO") MyPageSearchDto searchVO) {
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//        String url = "/mypage/updateForm.do";
//
//        if ("Y".equals(NmcpServiceUtils.isMobile())) {
//            url = "/m/mypage/updateForm.do";
//        }
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//
//        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
//        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//             throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION,url);
//        }
//
//        try {
//            List<RateAdsvcCtgBasDTO> dupList = regSvcService.getRoamList();
//            List<RateAdsvcCtgBasDTO> list = rateAdsvcGdncService.deduplicateProdList(dupList); // 중복 상품 제거
//
//            rtnMap.put("list", list);
//        } catch (SelfServiceException e) {
//            throw new McpCommonException(e.getMessage());
//        }
//
//        rtnMap.put("ctn", searchVO.getCtn());
//        rtnMap.put("contractNum", searchVO.getContractNum());
//
//        return rtnMap;
//    }
//
//    /**
//     * 해외로밍 해지 신청
//     * @Date : 2023.06.15
//     * @param request
//     * @param model
//     * @param searchVO
//     * @param rateAdsvcCd
//     * @return
//     * @throws SocketTimeoutException
//     */
//
//    @RequestMapping(value = { "/mypage/roamingCanChgAjax.do", "/m/mypage/roamingCanChgAjax.do" })
//    @ResponseBody
//    public Map<String, Object> roamingCanChgAjax(HttpServletRequest request, Model model,
//                                                    @ModelAttribute("searchVO") MyPageSearchDto searchVO,
//                                                    @RequestParam(value = "rateAdsvcCd", required = true) String rateAdsvcCd,
//                                                    @RequestParam(value = "prodHstSeq", required = false) String prodHstSeq) throws SocketTimeoutException {
//
//        Map<String, Object> rtnMap = new HashMap<String, Object>();
//
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
//        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            rtnMap.put("RESULT_CODE", "E");
//            rtnMap.put("message", F_BIND_EXCEPTION);
//            return rtnMap;
//        }
//
//        // 부가서비스 해지 서비스 호출 X38
//        if("".equals(prodHstSeq) || prodHstSeq == null) {
//            rtnMap = regSvcService.moscRegSvcCanChg(searchVO, rateAdsvcCd);
//        } else {
//            rtnMap = regSvcService.moscRegSvcCanChgSeq(searchVO, rateAdsvcCd, prodHstSeq);
//        }
//        return rtnMap;
//    }
//
//
//
//    /**
//     * 마이페이지 해외로밍 신청 화면 POP VIEW
//     * @Date : 2023.06.21
//     * @param request
//     * @param model
//     * @param searchVO
//     * @param rateAdsvcCtgBasDTO
//     * @return
//     * @throws ParseException
//     */
//
//    @RequestMapping(value = { "/mypage/roamingViewPop.do", "/m/mypage/roamingViewPop.do" })
//    public String roamingViewPop(HttpServletRequest request, Model model,
//                                @ModelAttribute("searchVO") MyPageSearchDto searchVO, RateAdsvcCtgBasDTO rateAdsvcCtgBasDTO
//                                , String flag, String prodHstSeq) throws ParseException {
//
//        String returnUrl = "";
//        UserSessionDto userSession = SessionUtils.getUserCookieBean();
//        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
//
//        if ("Y".equals(NmcpServiceUtils.isMobile())) {
//            returnUrl = "/mobile/mypage/roamingViewPop";
//        } else {
//            returnUrl = "/portal/mypage/roamingViewPop";
//        }
//
//        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
//        if(!chk){
//            ResponseSuccessDto responseSuccessDto = getMessageBox();
//            model.addAttribute("responseSuccessDto", responseSuccessDto);
//            return "/common/successRedirect";
//        }
//
//
//        //팝업에 띄울 해외로밍 상품 정보 조회
//        RateAdsvcCtgBasDTO joinPop = rateAdsvcGdncService.getProdBySeq(rateAdsvcCtgBasDTO);
//        RateAdsvcGdncProdRelXML prodRel = rateAdsvcGdncService.getRateAdsvcGdncProdRel(rateAdsvcCtgBasDTO);
//
//        model.addAttribute("contractNum", searchVO.getContractNum());
//        model.addAttribute("joinPop", joinPop);
//        model.addAttribute("prodRel", prodRel);
//        model.addAttribute("flag", flag);
//        model.addAttribute("prodHstSeq", prodHstSeq);
//        return returnUrl;
//    }
//    
//    
}
