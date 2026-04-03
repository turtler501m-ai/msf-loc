package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;
import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ktmmobile.msf.form.newchange.service.FormDtlSvc;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpReqCombineDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyCombinationResDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MyCombinationSvc;
import com.ktmmobile.msf.form.servicechange.service.SfMypageSvc;
import com.ktmmobile.msf.system.cert.dto.CertDto;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.AuthSmsDto;
import com.ktmmobile.msf.system.common.dto.MoscCombChkReqDto;
import com.ktmmobile.msf.system.common.dto.MoscCombReqDto;
import com.ktmmobile.msf.system.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.dto.db.MspSmsTemplateMstDto;
import com.ktmmobile.msf.system.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.system.common.exception.McpCommonException;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.system.common.exception.McpErropPageException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscCombChkRes;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscCombChkRes.MoscCombPreChkListOut;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscCombDtlListOutDTO;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscCombDtlResDTO;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscSubMstCombChgRes;
import com.ktmmobile.msf.system.common.mplatform.vo.MpAddSvcInfoDto;
import com.ktmmobile.msf.system.common.mplatform.vo.MpSocVO;
import com.ktmmobile.msf.system.common.service.FCommonSvc;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.service.SmsSvc;
import com.ktmmobile.msf.system.common.util.DateTimeUtil;
import com.ktmmobile.msf.system.common.util.EncryptUtil;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.ObjectUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.system.common.util.StringUtil;

@Controller
public class CombineController {

    private static final Logger logger = LoggerFactory.getLogger(CombineController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    SfMypageSvc mypageService;

    @Autowired
    private FCommonSvc fCommonSvc;


    @Autowired
    MyCombinationSvc myCombinationSvc;

    @Autowired
    private MplatFormService mplatFormService;

    @Autowired
    private FormDtlSvc formDtlSvc;


    @Autowired
    SmsSvc smsSvc;

    @Autowired
    CertService certService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private MplatFormService mPlatFormService;

//    @Autowired
//    MstorySvc mstorySvc;

    /**
     * 추가혜택 > 무무선 결합 소개 <인증전>
     *
     * @param request
     * @param model
     * @return
     * @author papier
     * @Date : 2022.11.11
     */
    @RequestMapping(value = "/content/combineWireless_old.do")
    public String combineWireless(HttpServletRequest request, Model model
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        return "redirect:/content/combineSelf.do";
        /*
        model.addAttribute("popType", "combine");
        model.addAttribute("menuType", "combine");

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();



        if (userSessionDto != null) {
            String userId = userSessionDto.getUserId();
            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
            cntrList = mypageService.selectCntrList(userId);
            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
                String url = "/mypage/updateForm.do";
                if ("Y".equals(NmcpServiceUtils.isMobile())) {
                    url = "/m/mypage/updateForm.do";
                }
                responseSuccessDto.setRedirectUrl(url);
                responseSuccessDto.setSuccessMsg(NOT_FULL_MEMBER_EXCEPTION);
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";
            }
            model.addAttribute("cntrList", cntrList); // 현재 서비스계약번호
            model.addAttribute("searchVO", searchVO);

            // 마스킹해제
            if(SessionUtils.getMaskingSession() > 0 ) {

                model.addAttribute("maskingSession", "Y");
                UserSessionDto userSession = SessionUtils.getUserCookieBean();
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
        }
        //session 초기화
        AuthSmsDto authSmsDto = new AuthSmsDto();
        authSmsDto.setMenu("combine");
        SessionUtils.setAuthSmsSession(authSmsDto);

        AuthSmsDto childAutSms = new AuthSmsDto();
        childAutSms.setMenu("combineChild");

        //관리자 정보 session 저장 session 초기화
        SessionUtils.setAuthSmsSession(childAutSms);

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/content/combineWireless";
        } else {
            return "/portal/content/combineWireless";
        }
        *
         */
    }

    /**
     * 사은품 리스트 조회
     *
     *
     */
    @RequestMapping(value = {"/content/combineWirelessComplete.do", "/m/content/combineWirelessComplete.do", "/content/combineKtComplete.do", "/m/content/combineKtComplete.do"})
    public String combineWirelessComplete(Model model, @ModelAttribute McpReqCombineDto reqCombine) {
        if (StringUtils.isBlank(reqCombine.getReqSeq()) ) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION);
        }

        String redirectPage = "/main.do";
        if(!"P".equals(NmcpServiceUtils.getPlatFormCd())) redirectPage = "/m/main.do";

        // ============ STEP START ============
        // 1. 최소 스텝 수 체크
        int certStep= 1;
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) certStep= 4;

        if(certService.getStepCnt() < certStep){
            throw new McpCommonException(STEP_CNT_EXCEPTION, redirectPage);
        }

        // 2. 최종 데이터 체크: 스텝 종료 여부, combineSeq
        String[] certKey = {"urlType", "stepEndYn", "reqSeq"};
        String[] certValue = {"completeCombine", "Y", reqCombine.getReqSeq()};
        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonException(vldReslt.get("RESULT_DESC"), redirectPage);
        }
        // ============ STEP END ============

        AuthSmsDto pareSession = new AuthSmsDto();
        pareSession.setMenu("combine");
        pareSession = SessionUtils.getAuthSmsBean(pareSession);

        //모회선 확인
        /* 인증 정보가 없으면 확인 불가능 */
        if (pareSession == null || StringUtils.isBlank(pareSession.getSvcCntrNo())) {
            throw new McpCommonException(ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        reqCombine.setmSvcCntrNo(pareSession.getContractNum());
        List<McpReqCombineDto> reqCombineList = myCombinationSvc.selectMcpReqCombine(reqCombine);

        if (reqCombineList.size() < 1) {
            throw new McpCommonException(ExceptionMsgConstant.NOT_FOUND_DATA_EXCEPTION);
        }

        McpReqCombineDto rtnReqCombine = reqCombineList.get(0);
        String combTypeCd = rtnReqCombine.getCombTypeCd();
        //등록 시간 확인
        /*
        5분 제한 60 * 5 * 1000= 30000
        300000
         */
        Date nowDate = new Date();
        Date requestTime = rtnReqCombine.getRegDt();
        long diff = nowDate.getTime() - requestTime.getTime();
        //logger.info("[WOO][WOO][WOO][WOO][WOO]===>diff===>"+diff);
        //8274500
        if (diff > 300000) {
            throw new McpCommonException(ExceptionMsgConstant.NOT_FOUND_DATA_EXCEPTION);
        }
        model.addAttribute("reqCombine", rtnReqCombine);

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            if ("01".equals(combTypeCd)) {
                return "/mobile/content/combineWirelessComplete";
            } else {
                return "/mobile/content/combineKtComplete";
            }
        } else {
            if ("01".equals(combTypeCd)) {
                return "/portal/content/combineWirelessComplete";
            } else {
                return "/portal/content/combineKtComplete";
            }

        }
    }


//    /**
//     * 추가혜택 > 무무선 결합 소개 <인증전>
//     *
//     * @param request
//     * @param model
//     * @return
//     * @author papier
//     * @Date : 2022.11.11
//     */
//    @RequestMapping(value = {"/content/combineKt.do", "/m/content/combineKt.do"})
//    public String combineKt(HttpServletRequest request, Model model
//            , @ModelAttribute("searchVO") MyPageSearchDto searchVO) {
//        // 결합회선리스트 값 초기화
//        request.getSession().removeAttribute(SessionUtils.COMBINE_LIST);
//
//        /* return "redirect:/main.do"; */
//
////        model.addAttribute("popType", "combine");
//        model.addAttribute("menuType", "combine");
//
//        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
//
//        if (userSessionDto != null) {
//            String userId = userSessionDto.getUserId();
//            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
//            cntrList = mypageService.selectCntrList(userId);
//
//            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
//
//                ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
//                String url = "/mypage/updateForm.do";
//                if ("Y".equals(NmcpServiceUtils.isMobile())) {
//                    url = "/m/mypage/updateForm.do";
//                }
//
//                responseSuccessDto.setRedirectUrl(url);
//                responseSuccessDto.setSuccessMsg(NOT_FULL_MEMBER_EXCEPTION);
//                model.addAttribute("responseSuccessDto", responseSuccessDto);
//                return "/common/successRedirect";
//            }
//            model.addAttribute("cntrList", cntrList); // 현재 서비스계약번호
//            model.addAttribute("searchVO", searchVO);
//
//            // 마스킹해제
//            if(SessionUtils.getMaskingSession() > 0 ) {
//                model.addAttribute("maskingSession", "Y");
//                UserSessionDto userSession = SessionUtils.getUserCookieBean();
//                MaskingDto maskingDto = new MaskingDto();
//
//                long maskingRelSeq = SessionUtils.getMaskingSession();
//                maskingDto.setMaskingReleaseSeq(maskingRelSeq);
//                maskingDto.setUnmaskingInfo("휴대폰번호");
//                maskingDto.setAccessIp(ipstatisticService.getClientIp());
//                maskingDto.setAccessUrl(request.getRequestURI());
//                maskingDto.setUserId(userSession.getUserId());
//                maskingDto.setCretId(userSession.getUserId());
//                maskingDto.setAmdId(userSession.getUserId());
//                maskingSvc.insertMaskingReleaseHist(maskingDto);
//            }
//        }
//
//        //아무나 가족결합 동영상
//        MstoryDto mstoryDto = new MstoryDto();
//        mstoryDto.setSnsCtgCd("SNS003");
//        mstoryDto.setSnsCntpntCd("COMBPLUS");
//        List<MstoryDto> list = mstorySvc.getSnsList(mstoryDto);
//        MstoryDto combineVideo = null;
//        if (list.size() > 0) {
//            combineVideo = list.get(0);
//        }
//        model.addAttribute("combineVideo", combineVideo);
//
//        //session 초기화
//        AuthSmsDto authSmsDto = new AuthSmsDto();
//        authSmsDto.setMenu("combine");
//        SessionUtils.setAuthSmsSession(authSmsDto);
//
//        AuthSmsDto childAutSms = new AuthSmsDto();
//        childAutSms.setMenu("combineChild");
//
//        //관리자 정보 session 저장 session 초기화
//        SessionUtils.setAuthSmsSession(childAutSms);
//
//        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
//            return "/mobile/content/combineKt";
//        } else {
//            return "/portal/content/combineKt";
//        }
//    }

//
//    /**
//     * @param :
//     * @return :
//     * @Description : 시간연장처리
//     * @Author : power
//     * @Create Date : 2022. 11. 14
//     */
//    @RequestMapping(value = "/content/getRateInfoAjax.do")
//    @ResponseBody
//    public Map<String, Object> getRateInfo(MyPageSearchDto searchVO
//            , @RequestParam(required = false, defaultValue = "combine") String menuPara
//    ) {
//
//        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//        if (userSessionDto != null) {
//            // STEP검증 처음부터 시작
//            certService.delStepInfo(1);
//
//            String userId = userSessionDto.getUserId();
//            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
//            cntrList = mypageService.selectCntrList(userId);
//
//            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
//                throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_SESSION_EXCEPTION);
//            }
//
//            // NCN 변조 방지
//            if(StringUtils.isBlank(searchVO.getContractNum())){
//                throw new McpCommonJsonException("9999", ExceptionMsgConstant.INVALID_REFERER_EXCEPTION);
//            }
//
//            //로그인한 사용자 이용중인 요금제조회
//            McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(searchVO.getContractNum());
//
//            //로그인 사용자 , 비로그인 사용자 호환을 위해.. 설정
//            AuthSmsDto authSmsDto = new AuthSmsDto();
//            authSmsDto.setPhoneNum(searchVO.getCtn());
//            authSmsDto.setCtn(searchVO.getCtn());
//            authSmsDto.setMenu(menuPara);
//            authSmsDto.setRateCd(mcpUserCntrMngDto.getSoc());
//            authSmsDto.setRateNm(mcpUserCntrMngDto.getRateNm());
//            authSmsDto.setSvcCntrNo(searchVO.getNcn());
//            authSmsDto.setContractNum(searchVO.getContractNum());
//            authSmsDto.setCustId(searchVO.getCustId());
//            authSmsDto.setSubLinkName(mcpUserCntrMngDto.getSubLinkName());
//            authSmsDto.setUnSSn(searchVO.getUserType());  //주민 번호 설정
//            authSmsDto.setMessage(searchVO.getSubStatus());  //회선 현재상태 A 사용중 , S 정지 , C 해지
//            SessionUtils.setAuthSmsSession(authSmsDto);
//
//            logger.info("[WOO][WOO][WOO] strMenu==>"+ menuPara);
//
//            if ("combineSelf".equals(menuPara)) {
//                //마스터 결합.. 검증을 위한 ... 값 설정
//                // 결합대상 구분값, 인증종류, 이름, 핸드폰번호, 계약번호
//                String[] certKey = {"urlType", "name", "mobileNo", "contractNum"};
//                String[] certValue = {"combineSelf", mcpUserCntrMngDto.getSubLinkName() , searchVO.getCtn(), searchVO.getNcn()};
//
//                certService.vdlCertInfo("C", certKey, certValue);
//            }
//
//
//            //함께 쓰기 모회선 , 자회선 구분
//            //1. 공통코드 사용가능한 요금제 모회선 자회선 구분
//            String retvGubunCd = ""; // G(주기회선조회) / R(받기회선조회)
//
//            String shareChildNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(SHARE_RATE_CHILD_LIST,mcpUserCntrMngDto.getSoc()),"");
//            String shareParentNm = StringUtil.NVL(NmcpServiceUtils.getCodeNm(SHARE_RATE_PARENT_LIST,mcpUserCntrMngDto.getSoc()),"");
//
//            if( !"".equals(shareChildNm) &&  !"".equals(shareParentNm) ){
//                retvGubunCd = "";
//            } else if( !"".equals(shareChildNm) ){
//                retvGubunCd = "R";
//            } else if( !"".equals(shareParentNm) ){
//                retvGubunCd = "G";
//            }
//
//            rtnMap.put("RETV_GUBNU_CD", retvGubunCd); //함께 쓰기 모회선 , 자회선 구문...
//            // 마스킹해제
//            if(SessionUtils.getMaskingSession() > 0 ) {
//                String[] nums = StringUtil.getMobileNum(authSmsDto.getPhoneNum());
//                String telNo = nums[0]+"-"+nums[1]+"-"+nums[2];
//                rtnMap.put("RESULT_PHONE_NUM",telNo);
//                rtnMap.put("RESULT_NM", mcpUserCntrMngDto.getSubLinkName());
//            }else {
//                rtnMap.put("RESULT_PHONE_NUM", StringMakerUtil.getPhoneNum(authSmsDto.getPhoneNum()));
//                rtnMap.put("RESULT_NM", StringMakerUtil.getName(mcpUserCntrMngDto.getSubLinkName()));
//            }
//            rtnMap.put("RESULT_RATE_NM", authSmsDto.getRateNm());
//
//        } else {
//            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_SESSION_EXCEPTION);
//        }
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        return rtnMap;
//    }


//    /**
//     * @param :
//     * @return :
//     * @Description : 인증한 전화 번호 추출
//     * @Author : power
//     * @Create Date : 2022. 11. 11
//     */
//    @RequestMapping(value = "/content/getNoLoginRateInfoAjax.do")
//    @ResponseBody
//    public Map<String, Object> certPhoneInfo(AuthSmsDto authSmsDto) {
//
//        AuthSmsDto rtnDto = SessionUtils.getAuthSmsBean(authSmsDto);
//
//        if (rtnDto == null) {
//            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
//        }
//
//        // 현재 요금제 조회
//        // 서비스계약번호
//        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(rtnDto.getSvcCntrNo());
//
//        if (mcpUserCntrMngDto == null || StringUtils.isBlank(mcpUserCntrMngDto.getSoc())) {
//            throw new McpCommonJsonException("0002", "요금제 정보 조회 실패");
//        }
//
//        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        rtnDto.setRateCd(mcpUserCntrMngDto.getSoc());
//        rtnDto.setRateNm(mcpUserCntrMngDto.getRateNm());
//        rtnDto.setUnSSn(mcpUserCntrMngDto.getUnUserSSn());  //주민 번호 설정
//        //요금제 정보 저장후 다시 session 저정
//        rtnDto.setMenu(authSmsDto.getMenu());
//        //rtnDto.setCustId(mcpUserCntrMngDto.getCustId());
//
//        SessionUtils.setAuthSmsSession(rtnDto);
//
//        AuthSmsDto rtnDto2 = new AuthSmsDto();
//        rtnDto2.setSvcCntrNo(rtnDto.getSvcCntrNo());
//        rtnDto2.setRateCd(mcpUserCntrMngDto.getSoc());
//        rtnDto2.setRateNm(mcpUserCntrMngDto.getRateNm());
//        rtnDto2.setCtn(StringMakerUtil.getPhoneNum(rtnDto.getCtn()));
//        rtnDto2.setSubLinkName(StringMakerUtil.getName(rtnDto.getSubLinkName()));
//
//        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//        rtnMap.put("DATA_OBJ", rtnDto2);
//        return rtnMap;
//    }

    /**
     * @param :
     * @return :
     * @Description : 결합대상여부
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/checkCombineAjax.do")
    @ResponseBody
    public Map<String, Object> checkCombine(AuthSmsDto authSmsDto, MyPageSearchDto searchVO) {

        AuthSmsDto rtnDto = SessionUtils.getAuthSmsBean(authSmsDto);

        if (rtnDto == null || StringUtils.isBlank(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        if (!searchVO.getNcn().equals(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String customerSsn = rtnDto.getUnSSn();
        String subStatus = rtnDto.getMessage(); //회선 현재상태 A 사용중 , S 정지 , C 해지

        //비로그인 사용자 SMS인증은 .. PASS
        if (subStatus == null || subStatus.equals("")) {
            subStatus = "A" ;
        }

        //3. 청소년 외국인 확인
        //외국인 청소년 구분
        if (!StringUtils.isNumeric(customerSsn) || customerSsn.length() < 7) {
            rtnMap.put("RESULT_CODE", "10008");
            rtnMap.put("RESULT_MSG", "주민번호 형식이 일치하지 않습니다. ");
            return rtnMap;
        }

        if (!subStatus.equals("A")) {
            //Kt M모바일 고객만 결합이 가능합니다.
            rtnMap.put("RESULT_CODE", "00003");
            rtnMap.put("RESULT_MSG", "현재 회선을 사용 중인 고객만 결합이 가능합니다.");
            return rtnMap;
        }

        String tempRateCd = rtnDto.getRateCd();
        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(tempRateCd);

        if (myCombination == null) {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return rtnMap;
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_SERVICE_NM", myCombination.getrRateNm());
        rtnMap.put("RESULT_SERVICE_CD", myCombination.getrRateCd());

        return rtnMap;
    }




    /**
     * @param :
     * @return :
     * @Description : 결합 대상 회선 가능 여부
     * @Author : power
     * @Create Date : 2022. 11. 14
     * @RequestParam(required = false, defaultValue = "") String prcsMdlInd
     */
    @RequestMapping(value = "/content/childCertifyAjax.do")
    @ResponseBody
    public Map<String, Object> childCertify(AuthSmsDto authSmsDto, McpUserCntrMngDto userCntrMngDto
            , @RequestParam(required = false, defaultValue = "") String prcsMdlInd) {
        if (StringUtils.isBlank(userCntrMngDto.getCntrMobileNo())) {
            throw new McpCommonJsonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION);
        }
        AuthSmsDto rtnDto = SessionUtils.getAuthSmsBean(authSmsDto);

        if (rtnDto == null || StringUtils.isBlank(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //1 . Kt M모바일 고객만 결합이 가능합니다.
        ////청소년
        McpUserCntrMngDto cntrObj = mypageService.selectCntrListNoLogin(userCntrMngDto);
        if (cntrObj == null) {
            //Kt M모바일 고객만 결합이 가능합니다.
            rtnMap.put("RESULT_CODE", "00001");
            rtnMap.put("RESULT_MSG", "kt M모바일 고객만 결합이 가능합니다.");
            return rtnMap;
        }

        //3. 청소년 외국인 확인
        String customerSsn = cntrObj.getUnUserSSn();
        if (!StringUtils.isNumeric(customerSsn) || customerSsn.length() < 7) {
            rtnMap.put("RESULT_CODE", "10008");
            rtnMap.put("RESULT_MSG", "주민번호 형식이 일치하지 않습니다. ");
            return rtnMap;
        }

        if (rtnDto.getSvcCntrNo().equals(cntrObj.getSvcCntrNo())) {
            //Kt M모바일 고객만 결합이 가능합니다.
            rtnMap.put("RESULT_CODE", "00002");
            rtnMap.put("RESULT_MSG", "동일한 회선으로 요청 하였습니다.");
            return rtnMap;
        }

        if (!cntrObj.getSubStatus().equals("A")) {
            //Kt M모바일 고객만 결합이 가능합니다.
            rtnMap.put("RESULT_CODE", "00003");
            rtnMap.put("RESULT_MSG", "현재 회선을 사용 중인 고객만 결합이 가능합니다.");
            return rtnMap;
        }

        if (!cntrObj.getCustomerType().equals("I")) {
            //Kt M모바일 고객만 결합이 가능합니다.
            rtnMap.put("RESULT_CODE", "00004");
            rtnMap.put("RESULT_MSG", "개인고객만 가입이 가능합니다.");
            return rtnMap;
        }
        //M전산 해당 요금제에 대한 결합 가능 여부 확인

        // 결합대상 요금제 정보
        McpUserCntrMngDto mcpUserCntrMngDto = mypageService.selectSocDesc(cntrObj.getContractNum());

        //해당 상품은 결합이 불가합니다.
        String tempRateCd = "";
        tempRateCd = mcpUserCntrMngDto.getSoc();

        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(tempRateCd);
        if (myCombination == null) {
            rtnMap.put("RESULT_CODE", "0005");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return rtnMap;
        }
        rtnMap.put("RESULT_SERVICE_NM", myCombination.getrRateNm());

        //‘승인대기＇중인 회선에 결합을 신청 불가은 처리
        //모회선  결합 진행 현황 조회
        McpReqCombineDto reqCombine = new McpReqCombineDto();
        reqCombine.setmSvcCntrNo(rtnDto.getSvcCntrNo());
        reqCombine.setRsltCd("R");
        List<McpReqCombineDto> reqCombineList = myCombinationSvc.selectMcpReqCombine(reqCombine);
        if (reqCombineList != null && reqCombineList.size() > 0) {
            rtnMap.put("RESULT_CODE", "1001");
            rtnMap.put("RESULT_MSG", "결합 승인 대기 중인 회선입니다.<br> 현재 해당 회선에 결합 신청 하실 수 없습니다. ");
            return rtnMap;
        }

        //자회선 결합 진행 현황 조회
        McpReqCombineDto reqCombine2 = new McpReqCombineDto();
        reqCombine2.setmSvcCntrNo(cntrObj.getSvcCntrNo());
        reqCombine2.setRsltCd("R");
        List<McpReqCombineDto> reqCombineList2 = myCombinationSvc.selectMcpReqCombine(reqCombine2);

        if (reqCombineList2 != null && reqCombineList2.size() > 0) {
            rtnMap.put("RESULT_CODE", "1002");
            rtnMap.put("RESULT_MSG", "결합 승인 대기 중인 회선입니다.<br> 현재 해당 회선에 결합 신청 하실 수 없습니다. ");
            return rtnMap;
        }

        // MP연동 X77
        // 각각 결합여부 확인
        MoscCombReqDto moscCombReqDto = new MoscCombReqDto();
        moscCombReqDto.setCustId(rtnDto.getCustId());
        moscCombReqDto.setNcn(rtnDto.getSvcCntrNo());
        moscCombReqDto.setCtn(rtnDto.getPhoneNum());
        moscCombReqDto.setCombSvcNoCd("M");  //결합 모회선 사업자 구분 코드	1	O	M: MVNO회선, K:KT 회선
        moscCombReqDto.setCombSrchId(cntrObj.getCntrMobileNo()); // 결합 모회선 조회값	10	O	"MVNO회선은 전화번호        KT 회선은 이름"
        logger.info("X77 [CALL][CALL][CALL]moscCombReqDto==>" + ObjectUtils.convertObjectToString(moscCombReqDto));

        MoscCombInfoResDTO moscCombInfoResDTO = myCombinationSvc.selectCombiMgmtListLog(moscCombReqDto, prcsMdlInd,rtnDto.getContractNum());
        MoscMvnoComInfo parentObj = null;
        String childCobun = "N"; //아래 회선 결합 여부
        if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess()) {
            if (moscCombInfoResDTO.getMoscSrchCombInfoList() != null) {
                parentObj = moscCombInfoResDTO.getMoscMvnoComInfo();
                for (MoscMvnoComInfo item : moscCombInfoResDTO.getMoscSrchCombInfoList()) {
                    /*
                     * 모회선 , 자회선 결합 여부 확인
                     * 두개 모두 결합 인 경우 결합이 불가능 합니다.
                     */
                    if (cntrObj.getCntrMobileNo().equals(item.getSvcNo()) && "Y".equals(item.getCombYn())) {
                        childCobun = "Y";  //child 회선 결합 상태
                    }

                    //두개 모두 결합 한 회선이면.. 결합 불가능
                    if ("Y".equals(childCobun) && "Y".equals(parentObj.getCombYn())) {
                        rtnMap.put("RESULT_CODE", "0006");
                        rtnMap.put("RESULT_MSG", "이미 결합된 회선입니다. ");
                        return rtnMap;
                    }
                }
            }
        } else {
            if ("LOCAL222".equals(serverName)) {
                parentObj = new MoscMvnoComInfo();
                parentObj.setCombYn("N");
            } else {
                String strMsg = "개별 서비스 조회(X77) 실패 하였습니다. ";
                if (moscCombInfoResDTO != null) {
                    strMsg = moscCombInfoResDTO.getSvcMsg();
                }
                rtnMap.put("RESULT_CODE", "1001");
                rtnMap.put("RESULT_MSG", strMsg );
                return rtnMap;
            }
        }

        /*X78서비스를 통해 기존 결합에 회선 추가하실때
        ① 항목은 신규 결합할 회선,
        ② 항목은 기존 결합된 회선 이라고 생각하시면 될 것 같습니다.
        */
        AuthSmsDto paraMcpUser1 = new AuthSmsDto();
        AuthSmsDto paraMcpUser2 = new AuthSmsDto();

        //TO_DO MP연동 결합 대상 여부 확인  X78
        MoscCombChkReqDto moscCombChkReqDto = new MoscCombChkReqDto();
        String contractNum = "";

        if ("Y".equals(parentObj.getCombYn())) {
            //reverse
            paraMcpUser1.setCustId(cntrObj.getCustId());
            paraMcpUser1.setSvcCntrNo(cntrObj.getSvcCntrNo());
            paraMcpUser1.setCtn(cntrObj.getCntrMobileNo());
            paraMcpUser1.setPhoneNum(cntrObj.getCntrMobileNo());
            paraMcpUser1.setSubLinkName(cntrObj.getSubLinkName());
            contractNum = cntrObj.getContractNum();

            paraMcpUser2.setPhoneNum(rtnDto.getPhoneNum());
            paraMcpUser2.setCtn(rtnDto.getPhoneNum());
            paraMcpUser2.setCustId(rtnDto.getCustId());

            moscCombChkReqDto.setJobGubun("A");  //A:회선추가
        } else {
            paraMcpUser1.setCustId(rtnDto.getCustId());
            paraMcpUser1.setSvcCntrNo(rtnDto.getSvcCntrNo());
            paraMcpUser1.setCtn(rtnDto.getPhoneNum());
            paraMcpUser1.setPhoneNum(rtnDto.getPhoneNum());
            paraMcpUser1.setSubLinkName(rtnDto.getSubLinkName());
            contractNum = rtnDto.getContractNum();

            paraMcpUser2.setPhoneNum(cntrObj.getCntrMobileNo());
            paraMcpUser2.setCtn(cntrObj.getCntrMobileNo());
            paraMcpUser2.setCustId(cntrObj.getCustId());

            if ("Y".equals(childCobun)) {
                moscCombChkReqDto.setJobGubun("A");  //A:회선추가
            } else {
                moscCombChkReqDto.setJobGubun("N");  //N:신규결합신청
            }
        }

        moscCombChkReqDto.setCustId(paraMcpUser1.getCustId());
        moscCombChkReqDto.setNcn(paraMcpUser1.getSvcCntrNo());
        moscCombChkReqDto.setCtn(paraMcpUser1.getPhoneNum());
        moscCombChkReqDto.setSvcNoCd("M");
        moscCombChkReqDto.setSvcNoTypeCd("MB");
        moscCombChkReqDto.setCmbStndSvcNo(paraMcpUser2.getCtn());
        moscCombChkReqDto.setAplyMethCd("04"); // 창구방문 04
        moscCombChkReqDto.setAplyRelatnCd("01"); //  신청관계 01 본인        11 대리인
        moscCombChkReqDto.setAplyNm(paraMcpUser1.getSubLinkName());


        MoscCombChkRes moscCombChkRes = null;
        try {
            moscCombChkRes = myCombinationSvc.insertCombChkInfoLog(moscCombChkReqDto, prcsMdlInd , contractNum);
        } catch (SelfServiceException e) {
            if ("LOCAL222".equals(serverName)) {
                logger.info("LOCAL222  SelfServiceException==> PASS");
                moscCombChkRes = new MoscCombChkRes();
                moscCombChkRes.setSuccess(true);
                moscCombChkRes.setSbscYn("Y");
            } else {
                rtnMap.put("RESULT_CODE", "0007");
                if ("ITL_SFC_E110".equals(e.getResultCode())) {
                    rtnMap.put("RESULT_MSG", "결합중인 회선입니다. 결합신청이 불가합니다.");
                }else if("ITL_SYS_E00001".equals(e.getResultCode())) {
                    rtnMap.put("RESULT_MSG", "결합 중인 회선이 많아<br>추가 결합이 되지 않습니다.");
                } else {
                    rtnMap.put("RESULT_MSG", e.getMessageNe());
                }
                return rtnMap;
            }
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "0008");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            return rtnMap;
        }

        /*
        성공여부
        리스트에 N이 한개라도 있으면 결합이 안되는 것으로 판단 하시면 됩니다  .M-Platform운영 2022-12-16 (금) 17:39:22
         */
        boolean isRegYn = false;
        String resltMsg = "";

        if (moscCombChkRes != null && moscCombChkRes.isSuccess()) {
            List<MoscCombPreChkListOut> moscCombPreChkList = moscCombChkRes.getMoscCombChkList();
            if (moscCombPreChkList == null || moscCombPreChkList.size() < 1) {
                isRegYn = true;
            } else {
                isRegYn = true;
                for (MoscCombPreChkListOut item : moscCombPreChkList) {
                    if ("N".equals(item.getSbscYn())) {
                        resltMsg = item.getResltMsg();
                        isRegYn = false;
                        break;
                    }
                }
            }

            /*하위에 msg 없으며  상위 msg 담기*/
            if ("".equals(resltMsg)) {
                resltMsg = moscCombChkRes.getResltMsg();
            }

            if (!"Y".equals(moscCombChkRes.getSbscYn())) {
                isRegYn = false;
            }

            if (!isRegYn) {
                rtnMap.put("RESULT_CODE", "0009");
                rtnMap.put("RESULT_MSG", resltMsg);
                return rtnMap;
            }

        } else {
            rtnMap.put("RESULT_CODE", "0010");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            return rtnMap;
        }


        //SMS발송 처리
        MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.COMBINE_AUTS_TEMPLATE_ID);

        if (mspSmsTemplateMstDto != null) {
            Random random;
            String a = "";
            String b = "";
            String c = "";
            String d = "";
            String e = "";
            String f = "";
            try {
                random = SecureRandom.getInstance("SHA1PRNG");
                a = String.valueOf(random.nextInt(10));
                b = String.valueOf(random.nextInt(10));
                c = String.valueOf(random.nextInt(10));
                d = String.valueOf(random.nextInt(10));
                e = String.valueOf(random.nextInt(10));
                f = String.valueOf(random.nextInt(10));
            } catch (NoSuchAlgorithmException e1) {
                throw new McpErropPageException(COMMON_EXCEPTION);
            }

            String sendNo = a + b + c + d + e + f;
            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{phoneNum}", rtnDto.getPhoneNum()));
            mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{sendNo}", sendNo));
            //smsSvc.sendMsgQueue(mspSmsTemplateMstDto.getSubject(), cntrObj.getCntrMobileNo(), mspSmsTemplateMstDto.getText(), mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
            smsSvc.sendMsgQueue(mspSmsTemplateMstDto.getSubject(), cntrObj.getCntrMobileNo(), mspSmsTemplateMstDto.getText(),
                    mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY
                    ,String.valueOf(Constants.COMBINE_AUTS_TEMPLATE_ID),"SYSTEM");
            logger.info("mspSmsTemplateMstDto.getText()==>" + mspSmsTemplateMstDto.getText());

            String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
            AuthSmsDto childAutSms = new AuthSmsDto();
            childAutSms.setAuthNum(sendNo);
            childAutSms.setStartDate(today);

            childAutSms.setPhoneNum(cntrObj.getCntrMobileNo());
            childAutSms.setCtn(cntrObj.getCntrMobileNo());
            childAutSms.setSvcCntrNo(cntrObj.getSvcCntrNo());
            childAutSms.setContractNum(cntrObj.getContractNum());
            childAutSms.setRateCd(mcpUserCntrMngDto.getSoc());
            childAutSms.setRateNm(mcpUserCntrMngDto.getRateNm());
            childAutSms.setSubLinkName(mcpUserCntrMngDto.getSubLinkName());
            childAutSms.setCustId(cntrObj.getCustId());
            childAutSms.setUnSSn(mcpUserCntrMngDto.getUnUserSSn());
            childAutSms.setMenu("combineChild");

            //관리자 정보 session 저장
            SessionUtils.setAuthSmsSession(childAutSms);

            // ============ STEP START ============
            // 1. sms인증 호출 이력 확인
            if (0 < certService.getModuTypeStepCnt("smsChild", "")) {
                // sms인증 호출 관련 스텝 초기화
                CertDto certDto = new CertDto();
                certDto.setModuType("smsChild");
                certDto.setCompType("G");
                certService.getCertInfo(certDto);
            }

            // 결합대상 구분값, 인증종류, 핸드폰번호, 계약번호, 이름, 생년월일
            String[] certKey = {"urlType", "ncType", "moduType", "mobileNo", "contractNum", "name", "birthDate"};
            String[] certValue = {"reqChildSmsAuthNum", "1", "smsChild", childAutSms.getPhoneNum(), childAutSms.getContractNum(), childAutSms.getSubLinkName(), EncryptUtil.ace256Enc(childAutSms.getUnSSn())};
            certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============

            String message = "";
            if ("REAL".equals(serverName)) {
                message = "";
            } else {
                message = sendNo;//보안정책에 걸려서 막음
            }
            rtnMap.put("RESULT_MSG", message);
        }
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * @param : prcsMdlInd 로그 저장 구분자
     * @return :
     * @Description : 결합 대상 사전체크 후 결합 신청(본인명의)
     * @Author :
     * @Create Date : 2025. 04. 29
     */
    @RequestMapping(value = "/content/mineRegCombineKtAjax.do")
    @ResponseBody
    public Map<String, Object> childKtCertify(AuthSmsDto authSmsDto
            , @RequestParam(required = false, defaultValue = "") String prcsMdlInd
            , @RequestParam(required = false, defaultValue = "") String combSeq) {
        //모회선 정보
        AuthSmsDto rtnDto = SessionUtils.getAuthSmsBean(authSmsDto);

        if (rtnDto == null || StringUtils.isBlank(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //결합대상 회선 리스트
        List<MoscMvnoComInfo> combList = SessionUtils.getCombineList();
        if (!"".equals(combSeq) && combSeq != null) {
            MoscMvnoComInfo comInfo = new MoscMvnoComInfo();
            for (MoscMvnoComInfo item : combList) {
                if (combSeq.equals(item.getSeq())) {
                    comInfo = item;
                }
            }

            //childDto 에 본인정보 현행화
            authSmsDto.setCtn(comInfo.getSvcNo());
            authSmsDto.setSubLinkName(rtnDto.getSubLinkName());
            authSmsDto.setBirthday(rtnDto.getBirthdayOfYYYY());
            authSmsDto.setSexCd(rtnDto.getSexCdOfSSn());

            Map<String, Object> combChkMap = myCombinationSvc.getCombChkInfo(rtnDto, authSmsDto, prcsMdlInd, comInfo.getCombYn());

            // 사전체크 성공
            if (AJAX_SUCCESS.equals(combChkMap.get("RESULT_CODE"))) {
                if ("IT".equals(authSmsDto.getSvcNoTypeCd())) {
                    rtnMap.put("RESULT_KT_INFO", StringMakerUtil.getUserId(comInfo.getSvcNo()));
                } else if ("MB".equals(authSmsDto.getSvcNoTypeCd())) {
                    rtnMap.put("RESULT_KT_INFO", StringMakerUtil.getPhoneNum(comInfo.getSvcNo()));
                } else {
                    rtnMap.put("RESULT_KT_INFO", "");
                }
                authSmsDto.setMenu("combineChild");
                authSmsDto.setSvcCntrNo(comInfo.getSvcNo());  //호환을 위해....
                authSmsDto.setPhoneNum(comInfo.getSvcNo());
                if ("Y".equals(comInfo.getCombYn())) {
                    authSmsDto.setJobGubun("A");
                } else {
                    authSmsDto.setJobGubun("N");
                }
                authSmsDto.setCheck(true);
                //관리자 정보 session 저장
                SessionUtils.setAuthSmsSession(authSmsDto);

                // 결합신청
                Map<String, Object> regCombineMap = myCombinationSvc.regCombineKt(prcsMdlInd);

                return regCombineMap;
            } else {
                return combChkMap;
            }
        } else {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("RESULT_MSG", "결합 회선을 확인해주세요.");
            return rtnMap;
        }
    }

    /**
     * @param :
     * @return :
     * @Description : 결합 대상 회선 가능 여부
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/childCertifyKtAjax.do")
    @ResponseBody
    public Map<String, Object> childCertifyKt(AuthSmsDto authSmsDto
            , @RequestParam(required = false, defaultValue = "") String prcsMdlInd) {
        //모회선 정보
        AuthSmsDto prntDto = SessionUtils.getAuthSmsBean(authSmsDto);

        if (prntDto == null || StringUtils.isBlank(prntDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        Map<String, Object> combListMap = myCombinationSvc.selectCombMgmtList(prntDto, authSmsDto, prcsMdlInd);
        // 본인명의일시 결합대상 리스트 가져와서 리턴
        if ("Y".equals(authSmsDto.getSameCustYn())) {
            return combListMap;
        } else { // 가족명의일시 사전체크까지 진행
            HashMap<String, Object> rtnMap = new HashMap<String, Object>();
            if (AJAX_SUCCESS.equals(combListMap.get("RESULT_CODE"))) {
                String childCobun = "N"; //아래 회선 결합 여부
                childCobun = String.valueOf(combListMap.get("childCobun"));

                Map<String, Object> combChkMap = myCombinationSvc.getCombChkInfo(prntDto, authSmsDto, prcsMdlInd, childCobun);

                // 사전체크성공시
                if (AJAX_SUCCESS.equals(combChkMap.get("RESULT_CODE"))) {
                    //SMS발송 처리
                    MspSmsTemplateMstDto mspSmsTemplateMstDto = null;

                    if ("MB".equals(authSmsDto.getSvcNoTypeCd())) {
                        mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.COMBINE_AUTS_TEMPLATE_ID);
                    } else {

                        rtnMap.put("RESULT_KT_INFO", StringMakerUtil.getUserId(authSmsDto.getCtn()));
                        authSmsDto.setMenu("combineChild");
                        authSmsDto.setSvcCntrNo(authSmsDto.getCtn());  //호환을 위해....
                        authSmsDto.setPhoneNum(authSmsDto.getCtn());
                        if ("Y".equals(childCobun)) {
                            authSmsDto.setJobGubun("A");
                        } else {
                            authSmsDto.setJobGubun("N");
                        }
                        authSmsDto.setCheck(true);
                        //관리자 정보 session 저장
                        SessionUtils.setAuthSmsSession(authSmsDto);
                    }


                    if (mspSmsTemplateMstDto != null && "MB".equals(authSmsDto.getSvcNoTypeCd())) {
                        Random random;
                        String a = "";
                        String b = "";
                        String c = "";
                        String d = "";
                        String e = "";
                        String f = "";
                        try {
                            random = SecureRandom.getInstance("SHA1PRNG");
                            a = String.valueOf(random.nextInt(10));
                            b = String.valueOf(random.nextInt(10));
                            c = String.valueOf(random.nextInt(10));
                            d = String.valueOf(random.nextInt(10));
                            e = String.valueOf(random.nextInt(10));
                            f = String.valueOf(random.nextInt(10));
                        } catch (NoSuchAlgorithmException e1) {
                            throw new McpErropPageException(COMMON_EXCEPTION);
                        }

                        String sendNo = a + b + c + d + e + f;

                        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{phoneNum}", prntDto.getPhoneNum()));
                        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{sendNo}", sendNo));
                        //smsSvc.sendMsgQueue(mspSmsTemplateMstDto.getSubject(), authSmsDto.getCtn(), mspSmsTemplateMstDto.getText(), mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                        smsSvc.sendMsgQueue(mspSmsTemplateMstDto.getSubject(), authSmsDto.getCtn(), mspSmsTemplateMstDto.getText(),
                                mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY
                                ,String.valueOf(Constants.COMBINE_AUTS_TEMPLATE_ID),"SYSTEM");

                        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");

                        authSmsDto.setAuthNum(sendNo);
                        authSmsDto.setStartDate(today);
                        authSmsDto.setMenu("combineChild");
                        authSmsDto.setSvcCntrNo(authSmsDto.getCtn());
                        authSmsDto.setPhoneNum(authSmsDto.getCtn());
                        if ("Y".equals(childCobun)) {
                            authSmsDto.setJobGubun("A");
                        } else {
                            authSmsDto.setJobGubun("N");
                        }

                        //관리자 정보 session 저장
                        SessionUtils.setAuthSmsSession(authSmsDto);
                        String message = "";
                        if ("REAL".equals(serverName)) {
                            message = "";
                        } else {
                            message = sendNo;//보안정책에 걸려서 막음
                        }
                        rtnMap.put("RESULT_MSG", message);

                    }

                    // ============ STEP START ============
                    // 1. sms인증 호출 이력 확인
                    if (0 < certService.getModuTypeStepCnt("smsChild", "")) {
                        // sms인증 호출 관련 스텝 초기화
                        CertDto certDto = new CertDto();
                        certDto.setModuType("smsChild");
                        certDto.setCompType("G");
                        certService.getCertInfo(certDto);
                    }

                    // 결합대상 구분값, 인증종류, 핸드폰번호, 이름, 생년월일
                    String[] certKey = {"urlType", "ncType", "moduType", "mobileNo", "name", "birthDate"};
                    String[] certValue = {"reqChildSmsAuthNum", "1", "smsChild", authSmsDto.getCtn(), authSmsDto.getSubLinkName(), authSmsDto.getBirthday()};
                    certService.vdlCertInfo("C", certKey, certValue);

                    // 아무나 가족결합 - KT인터넷 결합인 경우 (SMS인증 완료 호환을 위해..)
                    if("IT".equals(authSmsDto.getSvcNoTypeCd())){
                        certValue[0] = "chkChildSmsAuthNum";

                        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                            rtnMap.put("RESULT_CODE", "STEP01");
                            rtnMap.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
                            return rtnMap;
                        }
                    }
                    // ============ STEP END ============

                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    return rtnMap;
                } else {
                    return combChkMap;
                }
            } else {
                return combListMap;
            }
        }
    }


    /**
     * @param :
     * @return :
     * @Description : 시간연장처리
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/childCertifyMoreAjax.do")
    @ResponseBody
    public Map<String, Object> childCertifyMore() {
        AuthSmsDto childAutSms = new AuthSmsDto();

        String today = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        childAutSms.setMenu("combineChild");
        childAutSms = SessionUtils.getAuthSmsBean(childAutSms);


        if (childAutSms == null || StringUtils.isBlank(childAutSms.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        childAutSms.setStartDate(today);
        //관리자 정보 session 저장
        SessionUtils.setAuthSmsSession(childAutSms);

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

        return rtnMap;
    }


    /**
     * @param :
     * @return :
     * @Description : 결합 대상 회선 가능 여부
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/childVerifyAjax.do")
    @ResponseBody
    public Map<String, Object> childVerifyAjax(AuthSmsDto authInput) {


        AuthSmsDto childAutSession = new AuthSmsDto();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        childAutSession.setMenu("combineChild");
        childAutSession = SessionUtils.getAuthSmsBean(childAutSession);

        if (childAutSession == null || StringUtils.isBlank(childAutSession.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        if (!childAutSession.getPhoneNum().equals(authInput.getPhoneNum())) {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_MSG", "SESSION 정보의 휴대폰 번호하고 상이합니다. ");
            return rtnMap;
        }

        if (!childAutSession.checkAuthTime(5)) {
            throw new McpCommonJsonException("0004", "유효시간이 지났습니다.인증번호를 재전송 후 다시 인증해주세요.");
        }

        if (!childAutSession.getAuthNum().equals(authInput.getAuthNum())) {
            rtnMap.put("RESULT_CODE", "0005");
            rtnMap.put("RESULT_MSG", "인증번호가 틀렸습니다. 다시 확인해 주세요.");
            return rtnMap;
        }

        // ============ STEP START ============
        // SMS세션을 이용하므로, STEP에는 포함될 필요 없지만.. 호환을 위해 포함
        String birthDay = StringUtil.NVL(childAutSession.getBirthday(), EncryptUtil.ace256Enc(childAutSession.getUnSSn()));

        // 인증종류, 결합대상 구분값, 이름, 생년월일, 핸드폰번호, 계약번호
        String[] certKey = {"urlType", "moduType", "ncType", "name", "birthDate", "mobileNo", "contractNum"};
        String[] certValue = {"chkChildSmsAuthNum", "smsChild", "1", childAutSession.getSubLinkName(), birthDay, childAutSession.getPhoneNum() , childAutSession.getContractNum()};

        // 계약번호가 없는 경우 contractNum 제외
        if(StringUtils.isBlank(childAutSession.getContractNum())){
            // 인증종류, 결합대상 구분값, 이름, 생년월일, 핸드폰번호
            certKey = Arrays.copyOfRange(certKey, 0, 6);
        }

        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
        }
        //============ STEP END ============

        childAutSession.setCheck(true);
        SessionUtils.setAuthSmsSession(childAutSession);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("RESULT_PHONE_NUM", StringMakerUtil.getPhoneNum(childAutSession.getPhoneNum()));
        rtnMap.put("RESULT_RATE_NM", childAutSession.getRateNm());

        //    svcNoTypeCd	회선구분코드	 인터넷:IT  모바일:MB    MVNO회선일 경우 MB만 가능
        if ("MB".equals(childAutSession.getSvcNoTypeCd())) {
            rtnMap.put("RESULT_KT_INFO", StringMakerUtil.getPhoneNum(childAutSession.getCtn()));
        } else if ("IT".equals(childAutSession.getSvcNoTypeCd())) {
            rtnMap.put("RESULT_KT_INFO", StringMakerUtil.getUserId(childAutSession.getCtn()));
        } else {
            rtnMap.put("RESULT_KT_INFO", "");
        }
        return rtnMap;
    }


    /**
     * @param :
     * @return :
     * @Description : 결합 처리
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/regCombineAjax.do")
    @ResponseBody
    public Map<String, Object> regCombine(@RequestParam(required = false, defaultValue = "") String prcsMdlInd) {

        AuthSmsDto pareSession = new AuthSmsDto();
        pareSession.setMenu("combine");
        pareSession = SessionUtils.getAuthSmsBean(pareSession);

        //모회선 확인
        if (pareSession == null || StringUtils.isBlank(pareSession.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        AuthSmsDto childAutSession = new AuthSmsDto();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        childAutSession.setMenu("combineChild");
        childAutSession = SessionUtils.getAuthSmsBean(childAutSession);
        logger.info("childAutSms==>" + ObjectUtils.convertObjectToString(childAutSession));

        //자회선 확인
        if (childAutSession == null || StringUtils.isBlank(childAutSession.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        if (!childAutSession.isCheck()) {
            throw new McpCommonJsonException("0003", "결합인증 정보를 확인 할 수 없습니다.");
        }

        // ============ 자회선 STEP START ============
        // 결합대상 구분값, 핸드폰번호, 계약번호, 이름, 생년월일
        String[] certKey = new String[]{"urlType", "ncType", "mobileNo", "contractNum"
                , "name", "birthDate"};
        String[] certValue = new String[]{"regChildCombine", "1", childAutSession.getPhoneNum(), childAutSession.getContractNum()
                , childAutSession.getSubLinkName(), EncryptUtil.ace256Enc(childAutSession.getUnSSn())};

        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
        }
        //============ 자회선 STEP END ============

        // ============ 모회선 STEP START ============
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            // 결합대상 구분값, 핸드폰번호, 계약번호, 이름
            certKey = new String[]{"urlType", "ncType", "mobileNo", "contractNum", "name"};
            certValue = new String[]{"regCombine", "0", pareSession.getPhoneNum(), pareSession.getContractNum(), pareSession.getSubLinkName()};
            vldReslt = certService.vdlCertInfo("D", certKey, certValue);
            if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
            }
        }
        // ============ 모회선 STEP END ==============

        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(pareSession.getRateCd());
        if (myCombination == null) {
            rtnMap.put("RESULT_CODE", "0009");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return rtnMap;
        }

        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination2 = myCombinationSvc.selectMspCombRateMapp(childAutSession.getRateCd());
        if (myCombination2 == null) {
            rtnMap.put("RESULT_CODE", "0010");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return rtnMap;
        }

        McpReqCombineDto reqCombine = new McpReqCombineDto();  //작업결과 DB저장 처리
        reqCombine.setRsltCd("S");            // '승인여부, R:승인대기, N:미제출, S:승인완료, B:승인반려, C:신청취소,, H:임의보류  ';  <=== 확인 필요


        // MP연동 X77 START
        // 각각 결합여부 확인
        MoscCombReqDto moscCombReqDto = new MoscCombReqDto();
        moscCombReqDto.setCustId(pareSession.getCustId());
        moscCombReqDto.setNcn(pareSession.getSvcCntrNo());
        moscCombReqDto.setCtn(pareSession.getPhoneNum());
        moscCombReqDto.setCombSvcNoCd("M");  //결합 모회선 사업자 구분 코드  : MVNO회선, K:KT 회선
        moscCombReqDto.setCombSrchId(childAutSession.getPhoneNum()); // 결합 모회선 조회값	10	O	"MVNO회선은 전화번호        KT 회선은 이름"

        MoscCombInfoResDTO moscCombInfoResDTO = myCombinationSvc.selectCombiMgmtListLog(moscCombReqDto, prcsMdlInd, pareSession.getContractNum());
        MoscMvnoComInfo parentObj = null;
        String childCobun = "N"; //아래 회선 결합 여부

        //성공여부
        boolean isRegYn = false;

        if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess()) {
            if (moscCombInfoResDTO.getMoscSrchCombInfoList() != null) {
                parentObj = moscCombInfoResDTO.getMoscMvnoComInfo();
                for (MoscMvnoComInfo item : moscCombInfoResDTO.getMoscSrchCombInfoList()) {
                    /*
                     * 모회선 , 자회선 결합 여부 확인
                     * 두개 모두 결합 인 경우 결합이 불가능 합니다.
                     */
                    if (childAutSession.getPhoneNum().equals(item.getSvcNo()) && "Y".equals(item.getCombYn())) {
                        childCobun = "Y";  //child 회선 결합 상태
                    }

                    //두개 모두 결합 한 회선이면.. 결합 불가능
                    if ("Y".equals(childCobun) && "Y".equals(parentObj.getCombYn())) {
                        rtnMap.put("RESULT_CODE", "0006");

                        //return rtnMap;

                        /**
                         * SocketTimeoutException 이후
                         * 다시 시도 할 때.. 결합 성공 여부 체크 로직 ..... .추가
                         * 결합 여부 확인
                         */
                        // 결합 서비스 계약 조회 X87
                        MoscCombDtlResDTO combSvcInfo = new MoscCombDtlResDTO();
                        try {
                            combSvcInfo = mplatFormService.moscCombSvcInfoList(pareSession.getCustId(), pareSession.getSvcCntrNo(), pareSession.getCtn());
                        } catch(SocketTimeoutException e) {
                            logger.info("X87 조회 오류[SocketTimeoutException]");
                        } catch (Exception e) {
                            logger.info("X87 조회 오류");
                        }

                        if (combSvcInfo != null && combSvcInfo.isSuccess()) {
                            List<MoscCombDtlListOutDTO> combDtlList = combSvcInfo.getCombList();  //결합 리스트
                            for (MoscCombDtlListOutDTO subItem : combDtlList) {
                                String svcContDivNm = subItem.getSvcContDivNm();
                                if (subItem.getSvcNo().equals(childAutSession.getPhoneNum())) { //결합 해야 할 회선 존재....
                                    //모회선 , 자회선 두개로 결합이 완성.......한 경우...
                                    //성공으로 ...
                                    isRegYn = true;
                                    break;
                                }
                            }
                        }
                        if (!isRegYn) {
                            rtnMap.put("RESULT_MSG", "다른 회선과 이미 결합된 회선입니다. <br> 결합이 불가능 합니다. ");
                            return rtnMap;
                        }
                    }
                }
            }
        } else {
            rtnMap.put("RESULT_CODE", "1001");
            rtnMap.put("RESULT_MSG", "개별 서비스 조회(X77) 실패 하였습니다.  ");
            return rtnMap;
        }
        // MP연동 X77 END

        if (!isRegYn) {
            //두개의 회선이 결합이 아닌경우 ...

            //결합 신청(X79)
            /*X78서비스를 통해 기존 결합에 회선 추가하실때
            ① 항목은 신규 결합할 회선,
            ② 항목은 기존 결합된 회선 이라고 생각하시면 될 것 같습니다.
            */
            AuthSmsDto paraMcpUser1 = new AuthSmsDto();
            AuthSmsDto paraMcpUser2 = new AuthSmsDto();

            //TO_DO MP연동 결합 대상 여부 확인  X79
            MoscCombChkReqDto moscCombChkReqDto = new MoscCombChkReqDto();
            String contractNum = "";

            if ("Y".equals(parentObj.getCombYn())) {
                //reverse
                paraMcpUser1.setCustId(childAutSession.getCustId());
                paraMcpUser1.setSvcCntrNo(childAutSession.getSvcCntrNo());
                paraMcpUser1.setCtn(childAutSession.getCtn());
                paraMcpUser1.setPhoneNum(childAutSession.getPhoneNum());
                paraMcpUser1.setSubLinkName(childAutSession.getSubLinkName());
                contractNum = childAutSession.getContractNum();

                paraMcpUser2.setPhoneNum(pareSession.getPhoneNum());
                paraMcpUser2.setCtn(pareSession.getCtn());
                paraMcpUser2.setCustId(pareSession.getCustId());

                moscCombChkReqDto.setJobGubun("A");  //A:회선추가
            } else {
                paraMcpUser1.setCustId(pareSession.getCustId());
                paraMcpUser1.setSvcCntrNo(pareSession.getSvcCntrNo());
                paraMcpUser1.setCtn(pareSession.getPhoneNum());
                paraMcpUser1.setPhoneNum(pareSession.getPhoneNum());
                paraMcpUser1.setSubLinkName(pareSession.getSubLinkName());
                contractNum = pareSession.getContractNum();

                paraMcpUser2.setPhoneNum(childAutSession.getPhoneNum());
                paraMcpUser2.setCtn(childAutSession.getCtn());
                paraMcpUser2.setCustId(childAutSession.getCustId());

                if ("Y".equals(childCobun)) {
                    moscCombChkReqDto.setJobGubun("A");  //A:회선추가
                } else {
                    moscCombChkReqDto.setJobGubun("N");  //N:신규결합신청
                }
            }

            moscCombChkReqDto.setCustId(paraMcpUser1.getCustId());
            moscCombChkReqDto.setNcn(paraMcpUser1.getSvcCntrNo());
            moscCombChkReqDto.setCtn(paraMcpUser1.getPhoneNum());
            moscCombChkReqDto.setSvcNoCd("M");   //M: MVNO회선, K:KT 회선
            moscCombChkReqDto.setSvcNoTypeCd("MB"); // "인터넷:IT  모바일:MB         MVNO회선일 경우 MB만 가능"
            moscCombChkReqDto.setCmbStndSvcNo(paraMcpUser2.getCtn());
            moscCombChkReqDto.setAplyMethCd("04"); // 창구방문 04
            moscCombChkReqDto.setAplyNm(paraMcpUser1.getSubLinkName());

            //확인 필요   reqCombine.setRsltCd("S");   <=== 타인 상관 없나?????
            if (pareSession.getCustId().equals(childAutSession.getCustId())) {
                moscCombChkReqDto.setAplyRelatnCd("01"); //????  신청관계 01 본인        11 대리인
                reqCombine.setCombTgtTypeCd("01");       // '결합대상 (01: 본인, 02: 가족, 03: 타인)';
            } else {
                moscCombChkReqDto.setAplyRelatnCd("01"); //????  신청관계 01 본인        11 대리인
                reqCombine.setCombTgtTypeCd("03");     // '결합대상 (01: 본인, 02: 가족, 03: 타인)'; <=== 확인 필요
            }

            MoscCombChkRes moscCombChkRes = null;
            try {
                moscCombChkRes = myCombinationSvc.moscCombSaveInfoLog(moscCombChkReqDto, prcsMdlInd, contractNum);
            } catch (SocketTimeoutException e) {
                logger.info("X79 [CALL][CALL][CALL]SocketTimeoutException==>");
                rtnMap.put("RESULT_CODE", "1999");
                rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
                return rtnMap;
            } catch (Exception e) {
                logger.info("X79 [CALL][CALL][CALL]Exception==>");
                rtnMap.put("RESULT_CODE", "0008");
                rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
                return rtnMap;
            }

            if (!moscCombChkRes.isSuccess()) {
                rtnMap.put("RESULT_CODE", "0009");
                rtnMap.put("RESULT_MSG", moscCombChkRes.getSvcMsg());
                return rtnMap;
            }


            String resltMsg = "";

            //리스트에 N이 한개라도 있으면 결합이 안되는 것으로 판단 하시면 됩니다  .M-Platform운영 2022-12-16 (금) 17:39:22
            List<MoscCombPreChkListOut> moscCombPreChkList = moscCombChkRes.getMoscCombChkList();
            if (moscCombPreChkList == null || moscCombPreChkList.size() < 1) {
                isRegYn = true;
            } else {
                isRegYn = true;
                for (MoscCombPreChkListOut item : moscCombPreChkList) {
                    if ("N".equals(item.getSbscYn())) {
                        resltMsg = item.getResltMsg();
                        isRegYn = false;
                        break;
                    }
                }
            }

            /*하위에 msg 없으며  상위 msg 담기*/
            if ("".equals(resltMsg)) {
                resltMsg = moscCombChkRes.getResltMsg();
            }

            if (!"Y".equals(moscCombChkRes.getSbscYn())) {
                isRegYn = false;
            }

            if (!isRegYn) {
                rtnMap.put("RESULT_CODE", "0009");
                rtnMap.put("RESULT_MSG", resltMsg);
                return rtnMap;
            }
        }


        //결과 DB 저장 처리
        reqCombine.setCombTypeCd("01");  //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03:* ktM+kt유선)
        reqCombine.setmCtn(pareSession.getPhoneNum()); //엠모바일 회선번호
        reqCombine.setmCustNm(pareSession.getSubLinkName()); //엠모바일 고객이름
        reqCombine.setmCustBirth(pareSession.getBirthdayOfYYYY()); //  '엠모바일 생년월일';
        reqCombine.setmSexCd("0" + pareSession.getSexCdOfSSn()); //     엠모바일 성별 (01: 남자, 02: 여자)';
        reqCombine.setmSvcCntrNo(pareSession.getContractNum()); //엠모바일 회선번호
        reqCombine.setmRateCd(pareSession.getRateCd()); //엠모바일 상품코드  M_RATE_CD
        reqCombine.setmRateNm(pareSession.getRateNm()); //엠모바일 상품명
        reqCombine.setmRateAdsvcCd(myCombination.getrRateCd()); //엠모바일 부가코드
        reqCombine.setmRateAdsvcNm(myCombination.getrRateNm());         //엠모바일 부가서비스명
        reqCombine.setCombSvcNo(childAutSession.getPhoneNum());              // '결합 회선번호 (모바일번호 or 인터넷서비스번호)';
        reqCombine.setCombSvcCntrNo(childAutSession.getContractNum());    // '무무 자회선 엠모바일 계약번호';
        reqCombine.setCombCustNm(childAutSession.getSubLinkName());     // '결합자 이름';
        reqCombine.setCombBirth(childAutSession.getBirthdayOfYYYY());   //'결합자 생년월일';
        reqCombine.setCombSexCd("0" + childAutSession.getSexCdOfSSn());   // '결합자 성별 (01: 남자, 02: 여자)';
        reqCombine.setCombSocCd(childAutSession.getRateCd());           // '결합회선 상품코드 무무결합인경우만 해당';
        reqCombine.setCombSocNm(childAutSession.getRateNm());           // '결합회선 상품명 무무결합인경우만 해당';
        reqCombine.setCombRateAdsvcCd(myCombination2.getrRateCd());     // '결합회선 부가코드 무무결합인경우만 해당';
        reqCombine.setCombRateAdsvcNm(myCombination2.getrRateNm());     // '결합회선 부가서비스명 무무결합인경우만 해당';

        if (myCombinationSvc.insertMcpReqCombine(reqCombine)) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQ_SEQ", reqCombine.getReqSeq());

            // ======================= STEP START =======================
            CertDto certDto = new CertDto();
            certDto.setUrlType("saveCombineSeq");
            certDto.setReqSeq(reqCombine.getReqSeq());
            certDto.setCompType("C");
            certService.getCertInfo(certDto);
            // ======================= STEP END =======================

        } else {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.DB_EXCEPTION);
        }


        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * @param : prcsMdlInd 로그 저장 구분자
     * @return :
     * @Description : 결합 신청(가족명의)
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/regCombineKtAjax.do")
    @ResponseBody
    public Map<String, Object> regCombineKt(@RequestParam(required = false, defaultValue = "") String prcsMdlInd) {
        Map<String, Object> regCombineMap = myCombinationSvc.regCombineKt(prcsMdlInd);
        return regCombineMap;
    }


    /**
     * @param :
     * @return :
     * @Description : x87 mvno 결합 서비스 계약조회
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/getCombiSvcListAjax.do")
    @ResponseBody
    public Map<String, Object> getCombiSvcList(MyPageSearchDto searchVO
            , @RequestParam(required = false, defaultValue = "combine") String menuPara
    ) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        AuthSmsDto pareSession = new AuthSmsDto();
        pareSession.setMenu(menuPara);
        pareSession = SessionUtils.getAuthSmsBean(pareSession);

        //모회선 확인
        if (pareSession == null || StringUtils.isBlank(pareSession.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        if (!searchVO.getNcn().equals(pareSession.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }
        logger.info("[getCombiSvcList]pareSession==>" + ObjectUtils.convertObjectToString(pareSession));


        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(pareSession.getRateCd());
        if (myCombination == null) {
            rtnMap.put("AdsvcNm", ""); //할인  부가서비스명
        } else {
            rtnMap.put("AdsvcNm", myCombination.getrRateNm()); //할인  부가서비스명
        }

        if ("myCombineSelf".equals(menuPara)) {
            //마스터 결합 SOLO 결합
            //아무나 Solo 결합 여부 확인
            //이용중인 부가서비스 조회
            MpAddSvcInfoDto addSvcInfoDto = null;
            boolean isService = false;
            try {
                addSvcInfoDto = mPlatFormService.getAddSvcInfoDto(pareSession.getSvcCntrNo(), pareSession.getCtn(), pareSession.getCustId());
            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "-1");
                rtnMap.put("RESULT_MSG", e.getMessageNe());
                return rtnMap;
            } catch (SocketTimeoutException e) {
                rtnMap.put("RESULT_CODE", "-1");
                rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
                return rtnMap;
            } catch (Exception e) {
                rtnMap.put("RESULT_CODE", "-1");
                rtnMap.put("RESULT_MSG", e.getMessage());
                return rtnMap;
            }

            if(addSvcInfoDto != null) {
                for (MpSocVO socVo: addSvcInfoDto.getList()) {
                    if ("PL249Q800".equals(socVo.getSoc()) ) {
                        isService = true ;

                        break;
                    }
                }
            }

            if (!isService) {
                rtnMap.put("AdsvcNm", ""); //할인  부가서비스명
            }

            rtnMap.put("IS_SOLO_SERVICE", isService);

        } else {

            //아무나 결합 , 아무나 결합 +
            // 결합 서비스 계약 조회 x87
            MoscCombDtlResDTO combSvcInfo = new MoscCombDtlResDTO();
            try {
                combSvcInfo = mplatFormService.moscCombSvcInfoList(pareSession.getCustId(), pareSession.getSvcCntrNo(), pareSession.getCtn());
            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("RESULT_MSG", e.getMessageNe());
                return rtnMap;
            } catch (SocketTimeoutException e) {
                rtnMap.put("RESULT_CODE", "0004");
                rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
                return rtnMap;
            }


            if (combSvcInfo != null && combSvcInfo.isSuccess()) {
                List<MoscCombDtlListOutDTO> combDtlList = combSvcInfo.getCombList();  //결합 리스트
                List<MoscCombDtlListOutDTO> rtnCombList = new ArrayList<MoscCombDtlListOutDTO>();

                if (combDtlList != null) {
                    for (MoscCombDtlListOutDTO item : combDtlList) {
                        String svcContDivNm = item.getSvcContDivNm();

                        if (!item.getSvcNo().equals(pareSession.getCtn())) { //본인 회선 제외
                            if (svcContDivNm.indexOf("Mobile") > -1) {
                                // 마스킹해제
                                if(SessionUtils.getMaskingSession() > 0 ) {
                                    String[] nums = StringUtil.getMobileNum(item.getSvcNo());
                                    String telNo = nums[0]+"-"+nums[1]+"-"+nums[2];
                                    item.setSvcNo(telNo);
                                }else {
                                    item.setSvcNo(StringMakerUtil.getPhoneNum(item.getSvcNo()));
                                }
                            } else {
                                // 마스킹해제
                                if(SessionUtils.getMaskingSession() > 0 ) {
                                    item.setSvcNo(item.getSvcNo());
                                }else {
                                    item.setSvcNo(StringMakerUtil.getUserId(item.getSvcNo()));
                                }
                            }
                            rtnCombList.add(item);
                        }
                    }
                }

                rtnMap.put("CombTypeNm", combSvcInfo.getCombTypeNm());
                rtnMap.put("CombProdNm", combSvcInfo.getCombProdNm());
                rtnMap.put("EngtPerdMonsNum", combSvcInfo.getEngtPerdMonsNum());
                rtnMap.put("CombDcTypeNm", combSvcInfo.getCombDcTypeNm());
                rtnMap.put("CombDcTypeDtl", combSvcInfo.getCombDcTypeDtl());
                rtnMap.put("RESULT_LIST", rtnCombList);
            } else {
                rtnMap.put("RESULT_LIST", null);
            }


            //결합 진행 현황 조회
            McpReqCombineDto reqCombine = new McpReqCombineDto();
            reqCombine.setmSvcCntrNo(pareSession.getSvcCntrNo());
            List<McpReqCombineDto> reqCombineList = myCombinationSvc.selectMcpReqCombine(reqCombine);

            if (reqCombineList != null) {
                for (McpReqCombineDto item : reqCombineList) {
                    String combTypeCd = item.getCombTypeCd();
                    if ("03".equals(combTypeCd)) {
                        // 마스킹해제
                        if(SessionUtils.getMaskingSession() > 0 ) {
                            item.setCombSvcNo(item.getCombSvcNo());   //combSvcNo
                        }else {
                            item.setCombSvcNo(StringMakerUtil.getUserId(item.getCombSvcNo()));   //combSvcNo
                        }
                    } else {
                        // 마스킹해제
                        if(SessionUtils.getMaskingSession() > 0 ) {
                            item.setCombSvcNo(item.getCombSvcNo());
                        }else {
                            item.setCombSvcNo(StringMakerUtil.getPhoneNum(item.getCombSvcNo()));
                        }
                    }
                    item.setReqSeq("");
                    item.setmCtn("");
                    item.setmCustNm("");
                    item.setmCustBirth("");
                    item.setmSexCd("");
                    item.setmSvcCntrNo("");
                    item.setmRateCd("");
                    item.setmRateNm("");
                    item.setmRateAdsvcCd("");
                    item.setmRateAdsvcNm("");
                    item.setCombSvcCntrNo("");
                    item.setCombCustNm("");
                    item.setCombBirth("");
                    item.setCombSexCd("");
                    item.setCombSocCd("");
                    item.setCombSocNm("");
                    item.setCombRateAdsvcCd("");
                    item.setCombRateAdsvcNm("");
                    item.setCombTgtTypeCd("");
                    item.setRsltMemo("");
                }
            }
            rtnMap.put("RESULT_LIST2", reqCombineList);
        }


        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }


    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList, UserSessionDto userSession) {

        if (!StringUtil.equals(userSession.getUserDivision(), "01")) {
            return false;
        }

        if (cntrList == null) {
            return false;
        }

        if (cntrList.size() <= 0) {
            return false;
        }

        String userType = "";
        if (StringUtil.isEmpty(searchVO.getNcn())) {
            searchVO.setNcn(cntrList.get(0).getSvcCntrNo());
            searchVO.setCtn(cntrList.get(0).getCntrMobileNo());
            searchVO.setCustId(cntrList.get(0).getCustId());
            searchVO.setModelName(cntrList.get(0).getModelName());
            searchVO.setContractNum(cntrList.get(0).getContractNum());
            searchVO.setSubStatus(cntrList.get(0).getSubStatus());
            userType = cntrList.get(0).getUnUserSSn();

            searchVO.setUserType(userType);
        }

        for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
            String ctn = mcpUserCntrMngDto.getCntrMobileNo();
            String ncn = mcpUserCntrMngDto.getSvcCntrNo();
            String custId = mcpUserCntrMngDto.getCustId();
            String modelName = mcpUserCntrMngDto.getModelName();
            String contractNum = mcpUserCntrMngDto.getContractNum();
            String subStatus = mcpUserCntrMngDto.getSubStatus();

            mcpUserCntrMngDto.setCntrMobileNo(StringMakerUtil.getPhoneNum(ctn));
            mcpUserCntrMngDto.setSvcCntrNo(ncn);
            mcpUserCntrMngDto.setCustId(custId);
            mcpUserCntrMngDto.setModelName(modelName);
            mcpUserCntrMngDto.setContractNum(contractNum);
            if (StringUtil.equals(searchVO.getNcn(), String.valueOf(mcpUserCntrMngDto.getSvcCntrNo()))) {
                searchVO.setNcn(ncn);
                searchVO.setCtn(ctn);
                searchVO.setCustId(custId);
                searchVO.setModelName(modelName);
                searchVO.setContractNum(contractNum);
                searchVO.setSubStatus(subStatus);
                userType = mcpUserCntrMngDto.getUnUserSSn();
                searchVO.setUserType(userType);
            }
        }

        return true;
    }



    /**
     * 설명 : KT인터넷 더블할인
     * @Author : 박성훈
     * @Date : 2023.10.12
     */
    @RequestMapping(value = {"/content/ktDcInfo.do", "/m/content/ktDcInfo.do"})
    public String eSimWatchInfo(Model model) {

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "redirect:/m/event/eventDetail.do?ntcartSeq=1490&sbstCtg=E&eventBranch=E";
        } else {
            return "redirect:/event/eventDetail.do?ntcartSeq=1490&sbstCtg=E&eventBranch=E";
        }

//         FormDtlDTO formDtlDTO = new FormDtlDTO();
//         formDtlDTO.setCdGroupId1("INFO");
//         formDtlDTO.setCdGroupId2("COMB00014");
//         FormDtlDTO formDtl = formDtlSvc.FormDtlView(formDtlDTO);
//
//         if (formDtl != null) {
//             formDtl.setDocContent(StringEscapeUtils.unescapeHtml(formDtl.getDocContent()));
//         }
//
//         model.addAttribute("formDtl", formDtl); // KT인터넷 더블할인 대상 요금제 html
//
//
//        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
//            return "/mobile/content/ktDcInfo";
//        } else {
//           return "/portal/content/ktDcInfo";
//        }
    }

    /**
     * 추가혜택 > 무무선 결합 소개 <인증전>
     *
     * @author papier
     * @Date : 2024.11.21
     */
    @RequestMapping(value = {"/content/combineSelf.do", "/m/content/combineSelf.do","/content/combineWireless.do"})
    public String combineSelf(HttpServletRequest request, Model model
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO) {


        // 전체스텝 초기화
        certService.delStepInfo(1);

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if (userSessionDto != null) {
            String userId = userSessionDto.getUserId();
            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();
            cntrList = mypageService.selectCntrList(userId);
            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
                String url = "/mypage/updateForm.do";
                if ("Y".equals(NmcpServiceUtils.isMobile())) {
                    url = "/m/mypage/updateForm.do";
                }
                responseSuccessDto.setRedirectUrl(url);
                responseSuccessDto.setSuccessMsg(NOT_FULL_MEMBER_EXCEPTION);
                model.addAttribute("responseSuccessDto", responseSuccessDto);
                return "/common/successRedirect";
            }
            searchVO.setMoCtn(cntrList.size());
            model.addAttribute("cntrList", cntrList); // 현재 서비스계약번호
            model.addAttribute("searchVO", searchVO);

            // 마스킹해제
            if(SessionUtils.getMaskingSession() > 0 ) {
                model.addAttribute("maskingSession", "Y");
                UserSessionDto userSession = SessionUtils.getUserCookieBean();
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
        }

        model.addAttribute("menuType", "combineSelf");

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/content/combineSelf";
        } else {
            return "/portal/content/combineSelf";
        }
    }


    /**
     * @param :
     * @return :
     * @Description : 마스터 결합 가능 여부 체크
     * @Author : power
     * @Create Date : 2022. 11. 14
     */
    @RequestMapping(value = "/content/checkCombineSelfAjax.do")
    @ResponseBody
    public Map<String, Object> checkCombineSelf(AuthSmsDto authSmsDto, MyPageSearchDto searchVO) {

        AuthSmsDto rtnDto = SessionUtils.getAuthSmsBean(authSmsDto);

        if (rtnDto == null || StringUtils.isBlank(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        if (!searchVO.getNcn().equals(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 결합대상 구분값, 인증종류, 이름, 핸드폰번호, 계약번호
        String[] certKey = {"urlType",  "name", "mobileNo", "contractNum"};
        String[] certValue = {"combineSelfAut", rtnDto.getSubLinkName() , rtnDto.getPhoneNum(), rtnDto.getSvcCntrNo()};  //rtnDto.getContractNum()

        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
            return rtnMap;
        }

        String customerSsn = rtnDto.getUnSSn();
        String subStatus = rtnDto.getMessage(); //회선 현재상태 A 사용중 , S 정지 , C 해지

        //비로그인 사용자 SMS인증은 .. PASS
        if (subStatus == null || subStatus.equals("")) {
            subStatus = "A" ;
        }

        //법인 회선 여부
        List<NmcpCdDtlDto> materCombineLineList = NmcpServiceUtils.getCodeList("MasterCombineLineInfo");
        if (null != materCombineLineList && materCombineLineList.size() < 1) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG", "현재 서비스 이용이 불가합니다.<br> 나중에 다시 이용 바랍니다.");
            return rtnMap;
        }

        //아무나 Solo 결합 여부 확인
        //이용중인 부가서비스 조회
        MpAddSvcInfoDto addSvcInfoDto = null;
        boolean isService = false;
        try {
            addSvcInfoDto = mPlatFormService.getAddSvcInfoDto(rtnDto.getSvcCntrNo(), rtnDto.getCtn(), rtnDto.getCustId());
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessageNe());
            return rtnMap;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessage());
            return rtnMap;
        }

        if(addSvcInfoDto != null) {
            for (MpSocVO socVo: addSvcInfoDto.getList()) {
                if ("PL249Q800".equals(socVo.getSoc()) ) {
                    isService = true ;
                    break;
                }
            }
        }

        if (isService) {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_MSG", "이미 신청 이력이 존재합니다. ");
            return rtnMap;
        }

        //3. 청소년 외국인 확인
        //외국인 청소년 구분
        if (!StringUtils.isNumeric(customerSsn) || customerSsn.length() < 7) {
            rtnMap.put("RESULT_CODE", "10008");
            rtnMap.put("RESULT_MSG", "주민번호 형식이 일치하지 않습니다. ");
            return rtnMap;
        }

        if (!subStatus.equals("A")) {
            //Kt M모바일 고객만 결합이 가능합니다.
            rtnMap.put("RESULT_CODE", "00003");
            rtnMap.put("RESULT_MSG", "현재 회선을 사용 중인 고객만 결합이 가능합니다.");
            return rtnMap;
        }

        //대상 요금제 체크
        String tempRateCd = rtnDto.getRateCd();
        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(tempRateCd);

        if (myCombination == null) {
            rtnMap.put("RESULT_CODE", "0004");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return rtnMap;
        } else if ("EMPTY".equals(myCombination.getrRateCd())) {
            rtnMap.put("RESULT_CODE", "0004");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다.(EMPTY) ");
            return rtnMap;
        }



        //아무나결합/가족결합 여부(X87)
        boolean isCombSvc = false;
        MoscCombDtlResDTO combSvcInfo = new MoscCombDtlResDTO();
        try {
            combSvcInfo = mplatFormService.moscCombSvcInfoList(rtnDto.getCustId(), rtnDto.getSvcCntrNo(), rtnDto.getCtn());
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessageNe());
            return rtnMap;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", e.getMessage());
            return rtnMap;
        }

        if (combSvcInfo != null && combSvcInfo.isSuccess()) {
            List<MoscCombDtlListOutDTO> combDtlList = combSvcInfo.getCombList();  //결합 리스트
            if (combDtlList != null && combDtlList.size() > 0) {
                isCombSvc = true;
                combDtlList.forEach(item->item.setSvcNo("")); //개인 정보 ???
                rtnMap.put("RESULT_COMBINE_LIST", combDtlList);
            } else {
                rtnMap.put("RESULT_COMBINE_LIST", null);
            }
        } else {
            rtnMap.put("RESULT_COMBINE_LIST", null);
        }


        //마스터 결합 여부 저정
        certValue = new String[]{"combineSelfCheck", rtnDto.getSubLinkName() , rtnDto.getPhoneNum(), rtnDto.getSvcCntrNo()};
        certService.vdlCertInfo("C", certKey, certValue);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("IS_COMBIN", isCombSvc);
        rtnMap.put("RESULT_SERVICE_NM", myCombination.getrRateNm());
        rtnMap.put("RESULT_SERVICE_CD", myCombination.getrRateCd());

        return rtnMap;
    }

    /**
     * @param :
     * @return :
     * @Description : 마스터 결합 처리
     * @Author : power
     * @Create Date : 2022. 11. 12
     */
    @RequestMapping(value = "/content/regCombineSelfAjax.do")
    @ResponseBody
    public Map<String, Object> regCombineSelf(AuthSmsDto authSmsDto, MyPageSearchDto searchVO) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        MoscSubMstCombChgRes moscSubMstCombChgRes = null;

        //법인 회선 여부
        List<NmcpCdDtlDto> materCombineLineList = NmcpServiceUtils.getCodeList("MasterCombineLineInfo");
        if (null != materCombineLineList && materCombineLineList.size() <1) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("RESULT_MSG", "현재 서비스 이용이 불가합니다.<br> 나중에 다시 이용 바랍니다.");
            return rtnMap;
        }

        NmcpCdDtlDto masterCombineInfo = materCombineLineList.get(0);
        String mstSvcContId = masterCombineInfo.getDtlCd();
        logger.info("Y44 mstSvcContId==>"+ mstSvcContId);

        AuthSmsDto rtnDto = SessionUtils.getAuthSmsBean(authSmsDto);

        if (rtnDto == null || StringUtils.isBlank(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0001", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        if (!searchVO.getNcn().equals(rtnDto.getSvcCntrNo())) {
            throw new McpCommonJsonException("0002", ExceptionMsgConstant.NO_FAIL_SESSION_EXCEPTION);
        }

        // 1. 필수 인증 내역 확인
        // 1-1. 마스터 결합 가능 여부 체크
        // 3. 최소 스텝 수 확인
        // 1. 최소 스텝 수 체크
        int certStep= 3;
        //로그인 정보 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null) {
            certStep= 4;
        }

        if(certService.getStepCnt() < certStep ){
            throw new McpCommonJsonException("STEP02", STEP_CNT_EXCEPTION);
        }

        // 결합대상 구분값, 인증종류, 이름, 핸드폰번호, 계약번호
        String[] certKey = {"urlType","name", "mobileNo", "contractNum"};
        String[] certValue = {"combineSelfSave", rtnDto.getSubLinkName() , rtnDto.getPhoneNum(), rtnDto.getSvcCntrNo()};  //rtnDto.getContractNum()

        Map<String, String> vldReslt = certService.vdlCertInfo("D", certKey, certValue);
        if (!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", vldReslt.get("RESULT_DESC"));
            return rtnMap;
        }


        try {
            moscSubMstCombChgRes = myCombinationSvc.moscSubMstCombChg(rtnDto.getSvcCntrNo(), rtnDto.getPhoneNum(),rtnDto.getCustId(),mstSvcContId);
            logger.info("Y44 [CALL][CALL][CALL]SocketTimeoutException==>"+ moscSubMstCombChgRes.getResultCd());
            logger.info("Y44 [CALL][CALL][CALL]SocketTimeoutException==>"+ moscSubMstCombChgRes.getResultMsg());
            if(moscSubMstCombChgRes.isSuccess() && "0000".equals(moscSubMstCombChgRes.getResultCd())) {

                //대상 요금제 체크
                String tempRateCd = rtnDto.getRateCd();
                //M전산 해당 요금제에 대한 결합 가능 여부 확인
                MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(tempRateCd);

                if (myCombination == null) {
                    rtnMap.put("RESULT_CODE", "0004");
                    rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
                    return rtnMap;
                }


                //결과 DB 저장 처리
                McpReqCombineDto reqCombine = new McpReqCombineDto();  //작업결과 DB저장 처리
                reqCombine.setRsltCd("S");            // '승인여부, R:승인대기, N:미제출, S:승인완료, B:승인반려, C:신청취소,, H:임의보류  ';  <=== 확인 필요
                reqCombine.setCombTypeCd("04");  //결합유형 (01: ktM+ktM, 02: ktM+kt무선, 03:* ktM+kt유선)
                reqCombine.setCombTgtTypeCd("03");       // '결합대상 (01: 본인, 02: 가족, 03: 타인)';
                reqCombine.setmCtn(rtnDto.getPhoneNum()); //엠모바일 회선번호
                reqCombine.setmCustNm(rtnDto.getSubLinkName()); //엠모바일 고객이름
                reqCombine.setmCustBirth(rtnDto.getBirthdayOfYYYY()); //  '엠모바일 생년월일';
                reqCombine.setmSexCd("0" + rtnDto.getSexCdOfSSn()); //     엠모바일 성별 (01: 남자, 02: 여자)';
                reqCombine.setmSvcCntrNo(rtnDto.getContractNum()); //엠모바일 회선번호
                reqCombine.setmRateCd(rtnDto.getRateCd()); //엠모바일 상품코드  M_RATE_CD
                reqCombine.setmRateNm(rtnDto.getRateNm()); //엠모바일 상품명
                reqCombine.setmRateAdsvcCd(myCombination.getrRateCd()); //엠모바일 부가코드
                reqCombine.setmRateAdsvcNm(myCombination.getrRateNm());         //엠모바일 부가서비스명
                reqCombine.setCombSvcNo(masterCombineInfo.getDtlCdNm());              // '결합 회선번호 (모바일번호 or 인터넷서비스번호)';
                reqCombine.setCombSvcCntrNo(mstSvcContId);    // '무무 자회선 엠모바일 계약번호';
                reqCombine.setCombCustNm("주식회사 케이티엠모바일");     // '결합자 이름';
                //reqCombine.setCombBirth(childAutSession.getBirthdayOfYYYY());   //'결합자 생년월일';
                reqCombine.setCombSexCd("03");   // '결합자 성별 (01: 남자, 02: 여자 , 03: 법인)';
                reqCombine.setCombSocCd("PL249Q804");           // '결합회선 상품코드 무무결합인경우만 해당';
                reqCombine.setCombSocNm("MVNO마스터결합전용 더미요금제 (엠모바일)");           // '결합회선 상품명 무무결합인경우만 해당';
                reqCombine.setCombRateAdsvcCd("EMPTY");     // '결합회선 부가코드 무무결합인경우만 해당';
                reqCombine.setCombRateAdsvcNm("데이터 제공 없음");     // '결합회선 부가서비스명 무무결합인경우만 해당';


                if (myCombinationSvc.insertMcpReqCombine(reqCombine)) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("REQ_SEQ", reqCombine.getReqSeq());
                } else {
                    rtnMap.put("RESULT_CODE", "9999");
                    rtnMap.put("RESULT_MSG", ExceptionMsgConstant.DB_EXCEPTION);
                }
            } else {
                if ("9999".equals(moscSubMstCombChgRes.getResultCd())) {
                    rtnMap.put("RESULT_CODE", "9999");
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                }
                rtnMap.put("RESULT_MSG", moscSubMstCombChgRes.getResultMsg());
            }
        } catch (SocketTimeoutException e) {
            logger.info("Y44 [CALL][CALL][CALL]SocketTimeoutException==>");
            rtnMap.put("RESULT_CODE", "1999");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "0008");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION);
            return rtnMap;
        }
        return rtnMap;
    }

    /**
     * @param :
     * @return :
     * @Description : 아무나 SOLO 결합 가능 여부 체크 (신청서 조회)
     * @Author :
     * @Create
     */
    @RequestMapping(value = "/content/getCombineSoloTypeAjax.do")
    @ResponseBody
    public Map<String, Object> combineSoloType(@RequestParam(value = "rateCd") String rateCd) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //M전산 해당 요금제에 대한 결합 가능 여부 확인
        MyCombinationResDto myCombination = myCombinationSvc.selectMspCombRateMapp(rateCd);

        if (myCombination == null) {
            rtnMap.put("RESULT_CODE", "00001");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다. ");
            return rtnMap;
        } else if ("EMPTY".equals(myCombination.getrRateCd())) {
            rtnMap.put("RESULT_CODE", "00002");
            rtnMap.put("RESULT_MSG", "해당 상품은 결합이 불가합니다.(EMPTY) ");
            return rtnMap;
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("R_RATE_NM",myCombination.getrRateNm());

        return rtnMap;
    }

}
