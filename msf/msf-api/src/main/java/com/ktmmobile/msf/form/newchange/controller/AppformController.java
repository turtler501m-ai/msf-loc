package com.ktmmobile.msf.form.newchange.controller;

import com.ktds.crypto.exception.CryptoException;
import com.ktmmobile.msf.form.newchange.dto.*;
import com.ktmmobile.msf.form.newchange.service.AppformSvc;
import com.ktmmobile.msf.form.newchange.service.EsimSvc;
import com.ktmmobile.msf.form.newchange.service.EventCodeSvc;
import com.ktmmobile.msf.form.newchange.service.ScanSvc;
import com.ktmmobile.msf.system.cert.dto.CertDto;
import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.constants.Constants;
import com.ktmmobile.msf.system.common.dto.*;
import com.ktmmobile.msf.system.common.dto.db.*;
import com.ktmmobile.msf.system.common.exception.*;
import com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscCrdtCardAthnInDto;
import com.ktmmobile.msf.system.common.mplatform.dto.MoscInqrUsimUsePsblOutDTO;
import com.ktmmobile.msf.system.common.mplatform.vo.*;
import com.ktmmobile.msf.system.common.mspservice.MspService;
import com.ktmmobile.msf.system.common.mspservice.dto.MspRateMstDto;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSalePlcyMstDto;
import com.ktmmobile.msf.system.common.mspservice.dto.MspSaleSubsdMstDto;
import com.ktmmobile.msf.system.common.service.*;
import com.ktmmobile.msf.system.common.util.*;
import com.ktmmobile.msf.system.common.legacy.etc.dto.GiftPromotionBas;
import com.ktmmobile.msf.system.common.legacy.etc.dto.GiftPromotionDtl;
import com.ktmmobile.msf.system.common.legacy.etc.dto.GiftPromotionDto;
import com.ktmmobile.msf.etc.service.GiftSvc;
import com.ktmmobile.msf.event.dto.NmcpEventLogDto;
import com.ktmmobile.msf.event.service.CoEventSvc;
import com.ktmmobile.msf.system.faceauth.dto.FathDto;
import com.ktmmobile.msf.system.faceauth.dto.FathFormInfo;
import com.ktmmobile.msf.system.faceauth.dto.FathSessionDto;
import com.ktmmobile.msf.system.faceauth.service.FathService;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.service.MyBenefitService;
import com.ktmmobile.msf.form.servicechange.service.MsfMyinfoService;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageUserService;
import com.ktmmobile.msf.form.common.constant.PhoneConstant;
import com.ktmmobile.msf.system.common.dto.OrderEnum;
import com.ktmmobile.msf.system.common.dto.PhoneMspDto;
import com.ktmmobile.msf.system.common.dto.PhoneProdBasDto;
import com.ktmmobile.msf.system.common.dto.PhoneSntyBasDto;
import com.ktmmobile.msf.system.common.service.PhoneService;
import com.ktmmobile.msf.system.common.legacy.point.dto.CustPointDto;
import com.ktmmobile.msf.rate.dto.RateGiftPrmtListDTO;
import com.ktmmobile.msf.usim.dto.UsimBasDto;
import com.ktmmobile.msf.usim.dto.UsimMspRateDto;
import com.ktmmobile.msf.usim.service.UsimService;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.net.SocketTimeoutException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.ktmmobile.msf.form.newchange.util.AppformUtil.isSuccessOP2;
import static com.ktmmobile.msf.system.common.constants.Constants.*;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.*;
import static com.ktmmobile.msf.form.common.constant.PhoneConstant.*;


/**
 * <pre>
 * 프로젝트 : kt M mobile
 * 파일명   : AppformController.java
 * 날짜     : 2016. 2. 18. 오후 3:50:18 55
 * 작성자   : papier TEST
 * 설명     :
 * </pre>
 */
@Controller
public class AppformController {

    private static final Logger logger = LoggerFactory.getLogger(AppformController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${sale.orgnId}")
    private String orgnId;

    /**
     * 자급제 조직 코드
     */
    @Value("${sale.sesplsOrgnId}")
    private String sesplsOrgnId;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    @Value("${SMARTRO_PC_SCRIPT_URL}")
    private String smartroPcScriptUrl;

    @Value("${SMARTRO_MOBILE_SCRIPT_URL}")
    private String smartroMobileScriptUrl;

    @Autowired
    PhoneService phoneService;

    @Autowired
    MspService mspService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    SmsSvc smsSvc;

    @Autowired
    private AppformSvc appformSvc;

    //@Autowired
    //private DaouPayService daouPayService;

    @Autowired
    private UsimService usimService;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private ScanSvc scanSvc;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private SmartroService smartroService;

    @Autowired
    private GiftSvc giftSvc;

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MyBenefitService myBenefitService;

    @Autowired
    private CoEventSvc coEventSvc;

    @Autowired
    private MsfMypageUserService mypageUserService;

    @Autowired
    private MsfMyinfoService myinfoService;

    @Autowired
    private NiceLogSvc nicelog;

    @Autowired
    private CertService certService;

    @Autowired
    private EsimSvc esimSvc;

    @Autowired
    private EventCodeSvc eventCodeSvc;
    @Autowired
    private FathService fathService;

    /**
     * <pre>
     * 설명     : 약관정보 패치
     * </pre>
     */
    @RequestMapping(value = "/getFormDescAjax.do")
    @ResponseBody
    public FormDtlDTO getFormDesc(FormDtlDTO formDtlDTO) {
        FormDtlDTO formDtlRtn = appformSvc.getFormDesc(formDtlDTO);

        if (formDtlRtn != null) {
            formDtlRtn.setDocContent(StringEscapeUtils.unescapeHtml(formDtlRtn.getDocContent()));
        }
        return formDtlRtn;
    }


    /**
     * 사은품 리스트 조회
     *
     *
     */
    @RequestMapping(value = {"/appForm/giftBaseListAjax.do", "/m/appForm/giftBaseListAjax.do"})
    @ResponseBody
    public List<GiftPromotionDto> giftBaseListAjax(GiftPromotionDto giftPromotionDto) {

        List<GiftPromotionDto> result = null;

        // 사은품 프로모션 아이디 조회
        String[] prmtIdArr = giftSvc.getPrmtId(giftPromotionDto);

        if (prmtIdArr != null && prmtIdArr.length > 0) {
            result = giftSvc.getGiftArrList(prmtIdArr);
        }

        return result;
    }

    /**
     * @param :
     * @return :
     * @Description : 프로모션 계층으로 조회 처리
     * @Author : power
     * @Create Date : 2022. 02. 10
     */
    @RequestMapping(value = "/appForm/giftBasListAjax.do")
    @ResponseBody
    public RateGiftPrmtListDTO giftBasList(GiftPromotionBas giftPromotionBas) {

        RateGiftPrmtListDTO rateGiftPrmtListDTO = new RateGiftPrmtListDTO();

        if(StringUtils.isBlank(giftPromotionBas.getRateCd())) {
            return rateGiftPrmtListDTO;
        }

        // m전산 사은품 프로모션 조회
        List<GiftPromotionBas> prmtList = giftSvc.giftBasList(giftPromotionBas);
        if(prmtList == null || prmtList.isEmpty()){
            return rateGiftPrmtListDTO;
        }

        // 사은품 프로모션에 해당하는 요금제 혜택요약 조회
        List<String> prmtIdList = prmtList.stream().map(GiftPromotionBas::getPrmtId).collect(Collectors.toList());
        rateGiftPrmtListDTO = giftSvc.selectRateGiftPrmtByIdList(prmtIdList);

        return rateGiftPrmtListDTO;
    }

    /**
     * @param :
     * @return :
     * @Description : 프로모션 계층으로 조회 처리
     * @Author : power
     * @Create Date : 2022. 02. 10
     */
    @RequestMapping(value = "/appForm/giftBasListDefaultAjax.do")
    @ResponseBody
    public List<GiftPromotionBas> giftBasListDefault(GiftPromotionBas giftPromotionBas, @RequestParam(value = "defaultOrgnId") String defaultOrgnId) {



        if (StringUtils.isBlank(giftPromotionBas.getRateCd())) {
            return null;
        }

        List<GiftPromotionBas> result = giftSvc.giftBasList(giftPromotionBas, defaultOrgnId);

        return result != null ? result : null ;
    }

    /**
     * 설명 : 유심 다이렉트 구매 화면
     *
     * @param request
     * @param model
     * @return
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping(value = {"/appForm/reqSelfDlvry.do", "/m/appForm/reqSelfDlvry.do"})
    public String reqSelfDlvry(HttpServletRequest request, Model model) {

        // 확장자 getExpnsnStrVal2 Y 인것만 표현
        /*
         * java.util.ConcurrentModificationException 위의 메서드를 실행할 때 예외가 발생한다는 건데, 위의 for
         * 문은 list에서 index를 비교하며 그에 맞는 값을 name에 넣어주고 돌리는 식이다. 그런데 돌리는 도중 remove를 호출하면 기존
         * index 값과 remove 호출로 인해 줄어든 size 값이 맞지 않기 때문에 예외가 발생한다.
         *
         *
         * 위의 방법은 반복자(Iterator)를 이용하는 방법이다. .remove() 메서드보다 무조건 .next() 메서드가 먼저 호출되어야만
         * 예외가 발생하지 않는다.
         */
        List<NmcpCdDtlDto> usimProdIdList = new ArrayList<NmcpCdDtlDto>(NmcpServiceUtils.getCodeList(Constants.USIM_PROD_ID_GROP_CODE));

        /*
        Iterator<NmcpCdDtlDto> iter = usimProdIdList.iterator();
        if (usimProdIdList != null) {
            while (iter.hasNext()) {
                if (!"Y".equals(iter.next().getExpnsnStrVal2())) {
                    iter.remove();
                }
            }
        }
         */

        String nowDate = DateTimeUtil.getFormatString("yyyyMMdd");
        NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(DLVRLY_ENEXCEPTION_DATE, nowDate);
        NmcpCdDtlDto viewDto = NmcpServiceUtils.getCodeNmDto(DLVRLY_VIEW_YN, "VIEW"); // 택배 강제 노출
        String isTimeCheck = "S";
        String isTimeMsg = "";
        String dView = "NOVIEW";
        String ableTime = "Y"; // 22~10 시까지

        if (nmcpCdDtlDto != null) {
            isTimeCheck = "F";
            isTimeMsg = nmcpCdDtlDto.getDtlCdDesc();
        }
        String dtlCdNm = "N";
        if (viewDto != null) {
            dtlCdNm = StringUtil.NVL(viewDto.getDtlCdNm(), "N");
        }
        if ("Y".equals(dtlCdNm)) {
            dView = "VIEW";
        }

        try {
            if (!DateTimeUtil.isMiddleTime("10:00", "22:00")) {
                ableTime = "N";
            }
        } catch(ParseException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

        String scriptUrl = smartroPcScriptUrl;
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            scriptUrl = smartroMobileScriptUrl;
        }

        String userName = "";
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession != null && !StringUtils.isEmpty(userSession.getUserId())) {
            userName = userSession.getName();
        }

        model.addAttribute("gdncPop", StringUtil.NVL(request.getParameter("gdncPop"), "Y"));
        model.addAttribute("usimProdIdList", usimProdIdList);  // 선택가능한 유심 리스트
        model.addAttribute("serverName", serverName);
        model.addAttribute("isTimeCheck", isTimeCheck);        // 바로배송 가능 날짜 (F: 불가능, S: 가능)
        model.addAttribute("isTimeMsg", isTimeMsg);            // 바로배송 불가 사유
        model.addAttribute("dView", dView);                    // 택배 노출 여부 (NOVIEW: 비노출, VIEW: 노출)
        model.addAttribute("ableTime", ableTime);              // 바로배송 가능 시간 (N: 불가능, Y: 가능)
        model.addAttribute("userName", userName);              // 로그인 사용자 이름

        model.addAttribute("smartroScriptUrl", scriptUrl);     // 결제 관련 스크립트 URL
        model.addAttribute("today", DateTimeUtil.getFormatString("yyyyMMddHHmmss"));  // CSS 버전 설정용

        SessionUtils.saveNiceRes(null);

        // ================= STEP START =================
        // STEP 초기화 및 메뉴명 세팅
        SessionUtils.removeCertSession();
        SessionUtils.setPageSession("usimBuy");

        // 인증세션 초기화
        SessionUtils.saveNiceRes(null);
        SessionUtils.saveNiceAgentRes(null);
        // ================= STEP END =================

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/appForm/reqSelfDlvry";
        } else {
            return "/portal/appForm/reqSelfDlvry";
        }
    }

    /**
     * <pre>
     * 설명     : 셀프개통 배송 요청 정보 등록
     * @param
     * @param  :
     * @return
     * @return: String
     * </pre>
     */
    @RequestMapping(value = {"/appForm/saveSelfDlvryAjax.do", "/m/appForm/saveSelfDlvryAjax.do"})
    @ResponseBody
    public Map<String, Object> saveSelfDlvry(McpRequestSelfDlvryDto reqSelfDlvry, HttpServletRequest request) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("0003", ExceptionMsgConstant.NICE_CERT_EXCEPTION);
        }

        // 기존 화면에서 넘어오던 정보, 서버세팅으로 변경
        reqSelfDlvry.setOnlineAuthInfo(sessNiceRes.getConnInfo());
        reqSelfDlvry.setOnlineAuthDi(sessNiceRes.getDupInfo());

        // 네이버, 패스, 카카오 인증은 CI값 세팅
        if("N".equals(sessNiceRes.getAuthType()) || "A".equals(sessNiceRes.getAuthType()) || "K".equals(sessNiceRes.getAuthType())){
            reqSelfDlvry.setOnlineAuthDi(sessNiceRes.getConnInfo());
        }

        reqSelfDlvry.setOnlineAuthInfo("ReqNo:" + sessNiceRes.getReqSeq() + ", ResNo:" + sessNiceRes.getResSeq());
        reqSelfDlvry.setOnlineAuthType(sessNiceRes.getAuthType());

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        String certId = "";
        if (userSessionDto != null) {
            certId = userSessionDto.getUserId();
        } else {
            certId = Constants.NON_MEMBER_ID;
        }
        reqSelfDlvry.setCretId(certId);


        List<NmcpCdDtlDto> usimProdDtlIdList = NmcpServiceUtils.getCodeList(Constants.USIM_PROD_DTL_ID_GROP_CODE);//유심코드상세 공통코드

        //유심상세 코드 설정
        //가장 정렬 우선순위 위에 있는것 설정
        String usimProdId = StringUtil.NVL(reqSelfDlvry.getUsimProdId(), "");
        if ( usimProdDtlIdList != null) { // 취약성 234
            for (NmcpCdDtlDto usimProdDtlIdValue : usimProdDtlIdList) {
                if (usimProdDtlIdValue.getExpnsnStrVal1().equals(usimProdId)) {
                    reqSelfDlvry.setUsimProdDtlId(usimProdDtlIdValue.getDtlCd());
                    break;
                }
            }
        }

        // 배로배송/택배 구분
        String dlvryKind = StringUtil.NVL(reqSelfDlvry.getDlvryKind(), ""); // 01: 바로배송 , 02: 택배
        String isTimeMsg = "";
        // 바로배송인 경우 영업시간 제약있음.
        if ("01".equals(dlvryKind)) {

            // 추석시간 막기
            String nowDate = DateTimeUtil.getFormatString("yyyyMMdd");
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(Constants.DLVRLY_ENEXCEPTION_DATE, nowDate);
            if (nmcpCdDtlDto != null) {
                isTimeMsg = nmcpCdDtlDto.getDtlCdDesc();
                rtnMap.put("isTimeMsg", isTimeMsg);
                rtnMap.put("RESULT_CODE", "TIMECHECK");
                return rtnMap;
            }

            try {
                if (!DateTimeUtil.isMiddleTime("09:00", "22:00")) {
                    rtnMap.put("RESULT_CODE", "TIME");
                    return rtnMap;
                }
                /*if (!"01".equals(usimProdId)) {
                    rtnMap.put("RESULT_CODE", "0001");
                    return rtnMap;
                }*/
            } catch (Exception e) {
                rtnMap.put("RESULT_CODE", "TIME");
                return rtnMap;
            }
        }

        // 창2개 띄어놓고 하면 결제 완료되도 완료 화면으로 넘어가지 않음.
        Long dlvryIdx = reqSelfDlvry.getSelfDlvryIdx();
        if (dlvryIdx >= 0) {
            List<SmartroDto> smartroDtoList = new ArrayList<SmartroDto>();
            String orderno = String.valueOf(dlvryIdx);
            smartroDtoList = smartroService.getSmartroDataList(orderno);
            if (smartroDtoList != null && !smartroDtoList.isEmpty()) {

                // completeUrl은 유심구매 페이지에서만 사용 (appform은 해당 값 사용하지 않음)
                String nowDlvryCompleteUrl = "/appForm/nowDlvryComplete.do";
                String selfDlvryCompleteUrl = "/appForm/selfDlvryComplete.do";

                if("Y".equals(NmcpServiceUtils.isMobile())){
                    nowDlvryCompleteUrl = "/m" + nowDlvryCompleteUrl;
                    selfDlvryCompleteUrl = "/m" + selfDlvryCompleteUrl;
                }

                rtnMap.put("completeUrl", "01".equals(dlvryKind) ? nowDlvryCompleteUrl : selfDlvryCompleteUrl);
                rtnMap.put("RESULT_CODE", "DONE");
                return rtnMap;
            }
        }
        // 창2개 띄어놓고 하면 결제 완료되도 완료 화면으로 넘어가지 않음끝.

        // ============ START 유심구매하기 페이지 추가 검증 및 데이터 세팅 ============
        String referer = StringUtil.NVL(request.getHeader("REFERER"), "");
        if(referer.indexOf("/reqSelfDlvry.do") > -1){

            int needStepCnt = 0;  // 필수 스텝 개수
            String[] certKey= null;
            String[] certValue= null;

            // 14세 미만 구매자인 경우 법정대리인 인증 정보 확인
            int age = NmcpServiceUtils.getBirthDateToAmericanAge(sessNiceRes.getBirthDate(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            boolean isUnderAge = age < USIM_BUY_CHILD_AGE;

            if(isUnderAge){

                // 법정대리인 인증 정보 체크
                NiceResDto sessAgentNiceRes = SessionUtils.getNiceAgentResCookieBean();
                if (sessAgentNiceRes == null) {
                    throw new McpCommonJsonException("0004", ExceptionMsgConstant.NICE_CERT_AGENT_EXCEPTION_INSR);
                }

                int minorAgentAge = NmcpServiceUtils.getBirthDateToAmericanAge(sessAgentNiceRes.getBirthDate(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
                if(minorAgentAge < USIM_BUY_MINOR_AGENT_AGE){
                    throw new McpCommonJsonException("0004", "법정대리인은 만 19세 이상 성인이어야 합니다.");
                }

                // 법정대리인 인증 약관 체크
                if(!"Y".equals(reqSelfDlvry.getMinorAgentAgrmYn())){
                    throw new McpCommonJsonException("0004", "법정대리인 필수 약관에 동의해야합니다.");
                }

                // 법정대리인 최종 스텝 데이터 체크
                certKey = new String[]{"urlType", "name", "ncType"};
                certValue = new String[]{"saveAgentDlvryForm", reqSelfDlvry.getMinorAgentName(), "1"};
                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                }

                reqSelfDlvry.setMinorOnlineAuthInfo("ReqNo:" + sessAgentNiceRes.getReqSeq() + ", ResNo:" + sessAgentNiceRes.getResSeq());
                needStepCnt = 5;

            }else{
                reqSelfDlvry.setMinorAgentName("");
                reqSelfDlvry.setMinorAgentAgrmYn("");
                reqSelfDlvry.setMinorOnlineAuthInfo("");
                needStepCnt = 3;
            }

            // 스텝 최종 개수 체크
            int dbStep = certService.getStepCnt();
            if(dbStep < needStepCnt){
                throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
            }

            // 구매자 스텝 죄종 데이터 체크
            // 구매자 데이터 체크
            certKey = new String[]{"urlType", "stepEndYn", "name", "ncType"};
            certValue = new String[]{"saveDlvryForm", "Y", reqSelfDlvry.getCstmrName(), "0"};
            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
            }
        }
        // ============ END 유심구매하기 페이지 추가 검증 및 데이터 세팅 ============

        Long selfDlvryIdx = 0L;

        String dlvryAddrDtl = StringEscapeUtils.unescapeXml(StringUtil.NVL(reqSelfDlvry.getDlvryAddrDtl(), ""));
        reqSelfDlvry.setDlvryAddrDtl(dlvryAddrDtl);
        String dlvryMemo = StringEscapeUtils.unescapeXml(StringUtil.NVL(reqSelfDlvry.getDlvryMemo(), ""));
        reqSelfDlvry.setDlvryMemo(dlvryMemo);
        String dlvryJibunAddr = StringEscapeUtils.unescapeXml(StringUtil.NVL(reqSelfDlvry.getDlvryJibunAddr(), ""));
        reqSelfDlvry.setDlvryJibunAddr(dlvryJibunAddr);
        String dlvryJibunAddrDtl = StringEscapeUtils.unescapeXml(StringUtil.NVL(reqSelfDlvry.getDlvryJibunAddrDtl(), ""));
        reqSelfDlvry.setDlvryJibunAddrDtl(dlvryJibunAddrDtl);
        String homePw = StringEscapeUtils.unescapeXml(StringUtil.NVL(reqSelfDlvry.getHomePw(), ""));
        reqSelfDlvry.setHomePw(homePw);

        if ("01".equals(dlvryKind)) {
            selfDlvryIdx = appformSvc.saveNowDlvryHist(reqSelfDlvry);
        } else {
            selfDlvryIdx = appformSvc.saveSelfDlvryHist(reqSelfDlvry);
        }

        String strSelfDlvryIdx = String.valueOf(selfDlvryIdx);
        if (selfDlvryIdx > 0) {
            rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
            rtnMap.put("selfDlvryIdx", selfDlvryIdx);
            rtnMap.put("certId", certId);
            HttpSession session = request.getSession(true);
            session.setAttribute("selfDlvryIdx", strSelfDlvryIdx);
            rtnMap.put("dlvryKind", dlvryKind);
        } else {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("certId", certId);
            rtnMap.put("dlvryKind", dlvryKind);
        }

        return rtnMap;
    }

    /**
     * 설명 : 유심 택배 구매 완료 화면
     *
     * @param request
     * @param reqSelfDlvry
     * @param model
     * @return
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping(value = {"/appForm/selfDlvryComplete.do", "/m/appForm/selfDlvryComplete.do"})
    public String selfDlvryComplete(HttpServletRequest request, @ModelAttribute McpRequestSelfDlvryDto reqSelfDlvry, Model model) {

        String strSelfDlvryIdx = StringUtil.NVL(request.getParameter("selfDlvryIdx"), "0");
        Long selfDlvryIdx = Long.parseLong(strSelfDlvryIdx);

        if (selfDlvryIdx == 0 && !"Y".equals(NmcpServiceUtils.isMobile())  ) {
            return "redirect:/appForm/reqSelfDlvry.do";
        }

        String resultCode = "00";
        McpRequestSelfDlvryDto mcpRequestSelfDlvryDto = null;
        String viewYn = "";
        if (selfDlvryIdx > 0) {
            mcpRequestSelfDlvryDto = appformSvc.getMcpSelfDlvryData(selfDlvryIdx);
            if (mcpRequestSelfDlvryDto != null) {
                viewYn = StringUtil.NVL(mcpRequestSelfDlvryDto.getViewYn(), "");
                if ("N".equals(viewYn)) {
                    // update Y 로해주기
                    appformSvc.updateSelfViewYn(selfDlvryIdx);
                    model.addAttribute("replaceYn", "Y");
                } else if ("Y".equals(viewYn)) {
                    mcpRequestSelfDlvryDto = null;
                }
            }

        }

        model.addAttribute("ReqSelfDlvry", mcpRequestSelfDlvryDto);
        model.addAttribute("resultCode", resultCode);
        SessionUtils.saveNiceRes(null);
        SessionUtils.saveNiceAgentRes(null);
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            return "/mobile/appForm/reqSelfDlvryComplete";
        } else {
            return "/portal/appForm/reqSelfDlvryComplete";
        }
    }

    /**
     * 설명 : 유심 바로배송 구매 완료 화면
     *
     * @param request
     * @param reqSelfDlvry
     * @param model
     * @return
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping(value = {"/appForm/nowDlvryComplete.do", "/m/appForm/nowDlvryComplete.do"})
    public String nowDlvryComplete(HttpServletRequest request, @ModelAttribute McpRequestSelfDlvryDto reqSelfDlvry, Model model) {

        String strSelfDlvryIdx = StringUtil.NVL(request.getParameter("selfDlvryIdx"), "0");
        Long selfDlvryIdx = Long.parseLong(strSelfDlvryIdx);

        if (selfDlvryIdx == 0 && !"Y".equals(NmcpServiceUtils.isMobile()) ) {
            return "redirect:/appForm/reqSelfDlvry.do";
        }

        McpRequestSelfDlvryDto mcpRequestNowDlvryDto = null;
        String resultCode = "00";
        String viewYn = "";
        try {
            if (selfDlvryIdx > 0) {
                mcpRequestNowDlvryDto = appformSvc.getMcpNowDlvryData(selfDlvryIdx);
                if (mcpRequestNowDlvryDto != null) { // 결제완료되었으면
                    viewYn = StringUtil.NVL(mcpRequestNowDlvryDto.getViewYn(), "");
                    if ("N".equals(viewYn)) {
                        // update Y 로해주기
                        appformSvc.updateNowViewYn(selfDlvryIdx);
                        model.addAttribute("replaceYn", "Y");
                    } else if ("Y".equals(viewYn)) {
                        mcpRequestNowDlvryDto = null;
                    }
                }
            }
        } catch(DataAccessException e) {
            throw new McpCommonException(DB_EXCEPTION);
        } catch (Exception e) {
            throw new McpCommonException(COMMON_EXCEPTION);
        }

        model.addAttribute("ReqSelfDlvry", mcpRequestNowDlvryDto);
        model.addAttribute("resultCode", resultCode);

        SessionUtils.saveNiceRes(null);
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            return "/mobile/appForm/reqSelfDlvryComplete";
        } else {
            return "/portal/appForm/reqSelfDlvryComplete";
        }
    }

    /**
     * <pre>
     * 설명     : 신규 , 번호이동 온라인 서식지 입력 폼
     * @param model
     * @param appformReqDto : 인자값
     * @return
     * @return: String
     * </pre>
     */
    @RequestMapping(value = {"/appForm/appForm.do", "/m/appForm/appForm.do"})
    public String appform(Model model
            , @ModelAttribute AppformReqDto appformReqDto) throws ParseException {

        model.addAttribute("appFormYn", "Y"); //이탈방지 처리
        SessionUtils.saveChangeAut(null);  // 기기변경 세션 제거

        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        //임시저장 및 인자값 확인
        AppformReqDto appformReqTemp = appformSvc.getAppFormTemp(appformReqDto.getRequestKey());

        if (appformReqTemp == null) {
            throw new McpCommonException(ExceptionMsgConstant.TEMP_FORM_INFO_NULL_EXCEPTION);
        }

        // 이벤트 코드 입력한 경우 추천인ID 표출여부 xml 조회
        if(!StringUtils.isEmpty(appformReqTemp.getEvntCdPrmt())){
            String recoUseYn = eventCodeSvc.getEventCodeRecoUseYnXML(appformReqTemp.getEvntCdPrmt());
            if(!StringUtils.isEmpty(recoUseYn)){
                appformReqTemp.setRecoUseYn(recoUseYn);
            }
        }


        // ============= START: 셀프개통 적용 플랫폼 확인 =============
        if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())){

            Map<String,String> selfPlatformMap= appformSvc.isSimpleApplyPlatform(appformReqTemp.getOperType());
            if(!"Y".equals(selfPlatformMap.get("applyYn"))){

                // 예외 IP 확인
                NmcpCdDtlDto exceptionIp= fCommonSvc.getDtlCodeWithNm(SIMPLE_OPEN_LIMIT_EXCEPTION_IP, ipstatisticService.getClientIp());

                if(exceptionIp == null){

                    // 셀프개통 이용불가 > 이전 페이지로 이동
                    String redirectPage= "/appForm/appFormDesignView.do?requestKey=" + appformReqDto.getRequestKey();
                    if(!"P".equals(selfPlatformMap.get("platformCd"))){
                        redirectPage= "/m/appForm/appFormDesignView.do?requestKey=" + appformReqDto.getRequestKey();
                    }

                    throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_PLATFORM_EXCEPTION, redirectPage);
                }

            }
        }
        // ============= EBD: 셀프개통 적용 플랫폼 확인 =============

        // 명의도용 IP 확인
        // 기존 appForm.js에서 Step 1->2 넘어갈 때 selectIdentityIp ajax로 호출하여 체크하던 로직을 접속 시 바로 체크하도록 수정
        if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())){

            String redirectPage = "/appForm/appFormDesignView.do";
            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                redirectPage = "/m/appForm/appFormDesignView.do";
            }

            int stolenIp = appformSvc.selectStolenIp(ipstatisticService.getClientIp());
            if(stolenIp > 0) {
                throw new McpCommonException(ExceptionMsgConstant.STOLEN_IP_EXCEPTION, redirectPage);
            }

            // 010 신규 유심 셀프개통인 경우, 동일아이피 차단 확인
            if(OPER_TYPE_NEW.equals(appformReqTemp.getOperType()) && !"09".equals(appformReqTemp.getUsimKindsCd())){
                int nacSelfIp = appformSvc.getNacSelfCount();
                if(nacSelfIp > 0){
                    throw new McpCommonException(ExceptionMsgConstant.NAC_SELF_IP_EXCEPTION, redirectPage);
                }
            }

        }

        // step검증용 페이지명 설정
        StringBuilder pageSb= new StringBuilder();
        if(OPER_TYPE_NEW.equals(appformReqTemp.getOperType())) pageSb.append("Nac");
        else if(OPER_TYPE_MOVE_NUM.equals(appformReqTemp.getOperType())) pageSb.append("Mnp");
        else if(OPER_TYPE_CHANGE.equals(appformReqTemp.getOperType())) pageSb.append("Hcn");
        else if(OPER_TYPE_EXCHANGE.equals(appformReqTemp.getOperType())) pageSb.append("Hdn");

        /*
        eSIM 검증
        09: eSIM
         */
        if ("09".equals(appformReqTemp.getUsimKindsCd()) ) {

            if (appformReqTemp.getUploadPhoneSrlNo() < 1) {
                throw new McpCommonException(PHONE_EID_NULL_EXCEPTION);
            }

            //셀프 개통일 때... 검증 추가
            if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) {
                McpUploadPhoneInfoDto uploadEPhone = appformSvc.getUploadPhoneInfo(appformReqTemp.getUploadPhoneSrlNo());
                if (uploadEPhone == null || StringUtils.isBlank(uploadEPhone.getEid()) || StringUtils.isBlank(uploadEPhone.getReqPhoneSn())) {
                    throw new McpCommonException(PHONE_EID_NULL_EXCEPTION);
                }
            }

            // ================= STEP START =================
            // step 초기화 및 상세 메뉴명 세팅
            if (!StringUtils.isBlank(appformReqTemp.getPrntsContractNo())){ // esimWatch > main step 4이상 삭제
                certService.delStepInfo(4);

                if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())) pageSb.append("EsimWatchSelfForm");
                else pageSb.append("EsimWatchForm");

            }else{  // esim > main step 3이상 삭제
                certService.delStepInfo(3);

                if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())) pageSb.append("EsimSelfForm");
                else pageSb.append("EsimForm");
            }

            SessionUtils.setPageSession(pageSb.toString());
            certService.updateCrtReferer();
            // ================= STEP END =================

        } else {
            // ================= STEP START =================
            // step 초기화 및 메뉴명 세팅
            SessionUtils.removeCertSession();

            if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())){  // 셀프개통
                pageSb.append("SelfForm");
            }else if(!REQ_BUY_TYPE_USIM.equals(appformReqTemp.getReqBuyType())){ // 핸드폰 요금제 가입
                pageSb.append("PhoneForm");
            }else if("Y".equals(appformReqTemp.getSesplsYn())){   // 자급제 요금제 가입
                pageSb.append("UsimForm");
            }else{ // 상담사 개통
                pageSb.append("CounselForm");
            }

            SessionUtils.setPageSession(pageSb.toString());
            // ================= STEP END =================
        }

        //logger.info("appformReqTemp==>"+ObjectUtils.convertObjectToString(appformReqTemp));

        String redirectUrl = "";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            redirectUrl = "/m/appForm/appFormDesignView.do?requestKey=" + appformReqDto.getRequestKey();
        } else {
            redirectUrl = "/appForm/appFormDesignView.do?requestKey=" + appformReqDto.getRequestKey();
        }


        //CntpntShopId
        if (StringUtils.isBlank(appformReqTemp.getSocCode())
                || StringUtils.isBlank(appformReqTemp.getOnOffType())
                || StringUtils.isBlank(appformReqTemp.getOperType())) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectUrl);
        }

        if ("Y".equals(appformReqTemp.getSesplsYn())) {
            //자급제.. 폰
            appformReqTemp.setCntpntShopId(Constants.CONTPNT_SELF_PHONE);
        }

        //임시저장값 기본값 설정
        //유심 보유 설정 값 설정
        if (StringUtils.isBlank(appformReqTemp.getClauseMpps35Flag())) {
            appformReqTemp.setClauseMpps35Flag("N");
        }

//        if (StringUtils.isBlank(appformReqTemp.getSelfCertType())) {
//            appformReqTemp.setSelfCertType("01");  //주민등록증 기본값
//        }

        if (StringUtils.isBlank(appformReqTemp.getMoveAuthType())) {
            appformReqTemp.setMoveAuthType("2");  //번호이동 인증유형 휴대폰 일련번호
        }

        if (StringUtils.isBlank(appformReqTemp.getCstmrBillSendCode())) {
            appformReqTemp.setCstmrBillSendCode("CB");  //모바일<br>명세서(MMS)
        }

        if (StringUtils.isBlank(appformReqTemp.getReqPayType())) {
            appformReqTemp.setReqPayType("D");  //자동이체
        }

        if (StringUtils.isBlank(appformReqTemp.getModelMonthly())) {
            appformReqTemp.setModelMonthly("0");  //할부 개월 기본값 설정
        }

        //로그인 정보 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        String userDivision = "-1";
        boolean isOwnVerify = false;

        if (userSession != null) {
            userDivision = userSession.getUserDivision();
            if (Constants.DIVISION_CODE_LEGALLY_MEMBER.equals(userDivision)) {
                List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
                model.addAttribute("cntrList", cntrList);
            }

            //이름, 휴대폰 번호, 주소
            if (StringUtils.isBlank(appformReqTemp.getCstmrName())) {
                appformReqTemp.setCstmrName(userSession.getName());
            }

            if (StringUtils.isBlank(appformReqTemp.getCstmrMobileFn()) && StringUtils.isBlank(appformReqTemp.getCstmrMobileMn())) {
                String mobileNo = userSession.getMobileNo();
                if (mobileNo != null && mobileNo.length() == 11) {
                    appformReqTemp.setCstmrMobileFn(mobileNo.substring(0, 3));
                    appformReqTemp.setCstmrMobileMn(mobileNo.substring(3, 7));
                    appformReqTemp.setCstmrMobileRn(mobileNo.substring(7, 11));
                } else if (mobileNo != null && mobileNo.length() == 10) {
                    appformReqTemp.setCstmrMobileFn(mobileNo.substring(0, 3));
                    appformReqTemp.setCstmrMobileMn(mobileNo.substring(3, 6));
                    appformReqTemp.setCstmrMobileRn(mobileNo.substring(6, 10));
                }
            }

            if (StringUtils.isBlank(appformReqTemp.getCstmrPost())) {
                appformReqTemp.setCstmrPost(userSession.getPost());
                appformReqTemp.setCstmrAddr(userSession.getAddress());
                appformReqTemp.setCstmrAddrDtl(userSession.getAddressDtl());
            }

            if (StringUtils.isBlank(appformReqTemp.getCstmrMail())) {
                appformReqTemp.setCstmrMail(userSession.getEmail());
            }

            if (appformReqTemp.getCretId().equals(userSession.getUserId())) {
                isOwnVerify = true;
            }

        } else {
            UserSessionDto guest = SessionUtils.getOrderSession();

            if (guest != null) {
                String gCstmrName = guest.getName();
                String gMobile = guest.getMobileNo();
                String tCstmrName = appformReqTemp.getCstmrName();
                String tCuMobile = appformReqTemp.getCstmrMobileFn()+ appformReqTemp.getCstmrMobileMn()+ appformReqTemp.getCstmrMobileRn();
                String tDlMobile = appformReqTemp.getDlvryMobileFn()+ appformReqTemp.getDlvryMobileMn()+ appformReqTemp.getDlvryMobileRn();

                if ( (gCstmrName.equals(tCstmrName) && gMobile.equals(tCuMobile) )
                        || (gCstmrName.equals(tCstmrName) && gMobile.equals(tDlMobile)) ) {
                    isOwnVerify = true;
                }
            }
        }

        //취약점 조치
        if (!isOwnVerify) {
            appformReqTemp.setCstmrName("");
            appformReqTemp.setCstmrNativeRrn("");
            appformReqTemp.setCstmrForeignerRrn("");
            appformReqTemp.setCstmrMobileFn("");
            appformReqTemp.setCstmrMobileMn("");
            appformReqTemp.setCstmrMobileRn("");
            appformReqTemp.setCstmrAddr("");
            appformReqTemp.setCstmrAddrDtl("");
        }
        model.addAttribute("userDivision", userDivision);


        /*
         * 5 온라인(셀프개통)
         * 0 온라인
         * 6 온라인(해피콜)

         * 7 모바일(셀프개통)
         * 3 모바일
         * 8 모바일(해피콜)
         * */
        if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) {
            //셀프개통
            /*
             * 셀프개통 가능한 접점 확인
             * cntpntShopId
             */
            NmcpCdDtlDto simpleSiteList = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_SIMPLE_REGI_SITE_LIST, appformReqTemp.getCntpntShopId());
            if (simpleSiteList == null) {
                throw new McpCommonException(ExceptionMsgConstant.SIMPLE_CNTPNT_SHOPID_EXCEPTION, redirectUrl);
            }

            Map<String, Object> mapIsApply = this.isSimpleApplyObj();
            if (Constants.OPER_TYPE_MOVE_NUM.equals(appformReqTemp.getOperType())) {
                if (!(Boolean) mapIsApply.get("IsMnpTime")) {
                    throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_TIME_EXCEPTION, redirectUrl);
                }
            } else if (Constants.OPER_TYPE_NEW.equals(appformReqTemp.getOperType())) {
                if (!(Boolean) mapIsApply.get("IsMacTime")) {
                    throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_TIME_EXCEPTION, redirectUrl);
                }
            } else {
                throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectUrl);
            }

            //session 초기화
            SessionUtils.saveAppformDto(null);
            SessionUtils.saveOsstDto(null);
            SessionUtils.saveCntSession(0);
        }
        SessionUtils.saveNiceRes(null);
        SessionUtils.saveNiceInsrRes(null);
        SessionUtils.saveNiceRwdRes(null);
        //안면인증 세션 초기화
        SessionUtils.initializeFathSession();

        NmcpCdDtlDto regiSiteOjb = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_REGI_SITE_LIST, appformReqTemp.getCntpntShopId());
        if (regiSiteOjb != null) {
            appformReqTemp.setSiteReferer(regiSiteOjb.getExpnsnStrVal1());
        }

        if (regiSiteOjb == null && StringUtils.isBlank(appformReqTemp.getModelSalePolicyCode())) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION, redirectUrl);
        }
        //&& StringUtils.isBlank(appformReqDto.getModelSalePolicyCode())

        //현재 대표 단말기 조회
        //현재 단품 ID 로 해당 대표모델ID를 조회한다.
        //TO_DO getModelId 확인 필요
        if (!StringUtils.isBlank(appformReqTemp.getModelId())) {

            // 현재 단품 ID 로 해당 대표모델ID를 조회한다.
            PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(appformReqTemp.getModelId());

            appformReqTemp.setRprsPrdtId(phoneMspDto.getRprsPrdtId());
            appformReqTemp.setPrdtId(appformReqTemp.getModelId());
        }

        String returnUrl = "/appForm/appForm";

         /*
        eSIM 검증
        09: eSIM
        부모 계약번호 존재...    eSIM watch
         */
        if ("09".equals(appformReqTemp.getUsimKindsCd()) && !StringUtils.isBlank(appformReqTemp.getPrntsContractNo())  ) {
            returnUrl = "/appForm/eSimWatchForm";
        }


        String scriptUrl = smartroPcScriptUrl;

        /*
         * 5 온라인(셀프개통)
         * 0 온라인
         * 6 온라인(해피콜)

         * 7  모바일(셀프개통)
         * 3  모바일
         * 8 모바일(해피콜)
         *
         * */
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            /*
             * if ( 0 > "|7|3|8|".indexOf(appformReqTemp.getOnOffType()) ) { throw new
             * McpCommonException(ExceptionMsgConstant.ON_OFF_TYPE_BIDING_EXCEPTION); }
             */
            if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) { //셀프개통
                appformReqTemp.setOnOffType("7");
            } else {
                appformReqTemp.setOnOffType("3");
//                if (regiSiteOjb != null  ) {
//                    appformReqTemp.setOnOffType("8");
//                } else {
//                    appformReqTemp.setOnOffType("3");
//                }

            }
            returnUrl = "/mobile".concat(returnUrl);
            scriptUrl = smartroMobileScriptUrl;
        } else {
            returnUrl = "/portal".concat(returnUrl);
            /*
             * if ( 0 > "|0|5|6|".indexOf(appformReqTemp.getOnOffType()) ) { throw new
             * McpCommonException(ExceptionMsgConstant.ON_OFF_TYPE_BIDING_EXCEPTION); }
             */
            if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) { //셀프개통
                appformReqTemp.setOnOffType("5");
            } else {
                appformReqTemp.setOnOffType("0");
//                if (regiSiteOjb != null  ) {
//                    appformReqTemp.setOnOffType("6");
//                } else {
//                    appformReqTemp.setOnOffType("0");
//                }
            }
        }

        //친추초대 ID 링크로 가입한 경우(세션에서 친추초대 ID를 가져온다) setCommendId
        String recommend = SessionUtils.getFriendInvitation();
        if(recommend != null && !"".equals(recommend)) {
            appformReqTemp.setCommendId(recommend);
        }
        //인가된 사용자 세션 이름 화면고정 2024-12-05 박민건
        if (userSession != null) {
            appformReqTemp.setCstmrName(userSession.getName());
        }
        model.addAttribute("today", DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
        model.addAttribute("smartroScriptUrl", scriptUrl);
        model.addAttribute("AppformReq", appformReqTemp);

        //위탁온라인 제휴계약점, 제휴요금제별 약관동의 프로세스 수정 2024-03-04 박민건
        NmcpCdDtlDto jehuProdInfo = fCommonSvc.getJehuProdInfo(appformReqTemp.getSocCode());
        NmcpCdDtlDto jehuPartnerInfo = fCommonSvc.getJehuPartnerInfo();
        SessionUtils.saveJehuPartnerType(jehuPartnerInfo.getExpnsnStrVal1());
        model.addAttribute("jehuProdType", jehuProdInfo.getDtlCd());
        model.addAttribute("jehuProdName", jehuProdInfo.getDtlCdNm());
        model.addAttribute("jehuPartnerType", jehuPartnerInfo.getDtlCd());
        model.addAttribute("jehuPartnerName", jehuPartnerInfo.getDtlCdNm());
        return returnUrl;

    }


    /**
     * <pre>
     * 설명     : 완료 페이지
     * /eSimForm/appFormComplete.do
     * google-analytics 구분 하기 위해
     * </pre>
     */
    @RequestMapping(value = {"/appForm/appFormComplete.do", "/m/appForm/appFormComplete.do", "/eSimForm/appFormComplete.do", "/m/eSimForm/appFormComplete.do"})
    public String appFormComplete(Model model, @ModelAttribute AppformReqDto appformReqDto) throws ParseException {
        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        //신청서 정보
        AppformReqDto appformReqTemp = appformSvc.getAppForm(appformReqDto);

        if (appformReqTemp == null) {
            throw new McpCommonException(ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
        }

        // ============= START: 완료페이지 진입 로직 강화 =============
        // 1. 셀프개통인 경우, OP2 응답 재확인
        if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())){
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(appformReqTemp.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_REQ_OPEN_RESULT);

            McpRequestOsstDto mcpRequestOsstDtoRtn= appformSvc.getRequestOsst(mcpRequestOsstDto);

            if(mcpRequestOsstDtoRtn == null || !isSuccessOP2(mcpRequestOsstDtoRtn.getRsltCd())){
                throw new McpCommonException("개통"+ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
            }
        }

        // 2. 신청정보와 본인인증 세션 정보 일치여부 확인 (본인인증한 신청서의 경우, ci값 존재)
        NiceResDto sessNiceRes = SessionUtils.getNiceReqTmpCookieBean();

        if(!StringUtil.isEmpty(appformReqTemp.getSelfCstmrCi())){
            if(sessNiceRes == null) throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);

            if(!appformReqTemp.getSelfCstmrCi().equals(sessNiceRes.getConnInfo())){
                throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
            }
        }else{
            // 3. CI값이 없는 신청정보인 경우, 본인인증 세션의 이름/생년월일과 신청정보의 이름/생년월일 비교
            if(sessNiceRes != null){
                String authNm= StringUtil.NVL(sessNiceRes.getName(), "").toUpperCase().replaceAll(" ", "");
                String authBirth= StringUtil.NVL(sessNiceRes.getBirthDate(), "");
                String requestNm= appformReqTemp.getCstmrName();
                String requestRrn= appformReqTemp.getCstmrNativeRrn();

                if(CSTMR_TYPE_NM.equals(appformReqTemp.getCstmrType())){
                    requestNm= appformReqTemp.getMinorAgentName();
                    requestRrn= appformReqTemp.getMinorAgentRrn();
                }

                try {
                    requestNm= StringUtil.NVL(requestNm, "").toUpperCase().replaceAll(" ", "");
                    requestRrn= EncryptUtil.ace256Dec(requestRrn);
                } catch (CryptoException e) {
                    requestNm= "";
                    requestRrn= "";
                }

                if(!requestNm.equals(authNm) || requestRrn.indexOf(authBirth) < 0){
                    throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
                }
            }
        }
        // ============= END: 완료페이지 진입 로직 강화 =============

        //현재 대표 단말기 조회
        //현재 단품 ID 로 해당 대표모델ID를 조회한다.
        //TO_DO getModelId 확인 필요
        if (!StringUtils.isBlank(appformReqTemp.getModelId())) {
            RestTemplate restTemplate = new RestTemplate();
            PhoneMspDto phoneMspDto = restTemplate.postForObject(apiInterfaceServer + "/msp/mspPhoneInfo", appformReqTemp.getModelId(), PhoneMspDto.class);
            appformReqTemp.setRprsPrdtId(phoneMspDto.getRprsPrdtId());
            appformReqTemp.setPrdtId(appformReqTemp.getModelId());
        }

        String returnUrl = "/appForm/appFormComplete";


        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) { //셀프개통
                appformReqTemp.setOnOffType("7");
            }
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) { //셀프개통
                appformReqTemp.setOnOffType("5");
            }
            returnUrl = "/portal".concat(returnUrl);
        }

        model.addAttribute("AppformReq", appformReqTemp);

        //이력 정보 저장 처리(친구초대 링크 개통까지 완료된 경우)
        String recommend = SessionUtils.getFriendInvitation();
        if(recommend != null && !"".equals(recommend)) {
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("INVITATION");
            mcpIpStatisticDto.setPrcsSbst("COMPLETED");
            mcpIpStatisticDto.setParameter(recommend);
            mcpIpStatisticDto.setTrtmRsltSmst("");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }

        //신규가입 + 셀프개통 케이스만 진행
        NiceResDto sessNiceMobile = SessionUtils.getNiceMobileSession();

     // 신규개통이면서 셀프개통으로 진행한 케이스에만 알림톡발송
        if (OPER_TYPE_NEW.equals(appformReqTemp.getOperType()) && ("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType()))) {
                MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
                mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_COMPLETE_TEMPLATE_ID);
                if (mspSmsTemplateMstDto != null) {

                    Date today = new Date();
                    String strToday = DateTimeUtil.changeFormat(today, "yyyy-MM-dd");
                    MspRateMstDto rateInfo = fCommonSvc.getMspRateMst(appformReqTemp.getSocCode());

                    mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{mobileNo}", "010-"+appformReqTemp.getReqWantNumber2()+"-"+appformReqTemp.getReqWantNumber3()));
                    mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{openingDate}", strToday));

                    if (rateInfo != null && rateInfo.getRateNm() != null) {
                        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{phonePlan}", rateInfo.getRateNm()));
                    }

                   if(sessNiceMobile !=null && sessNiceMobile.getsMobileNo() != null) {

                       smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), sessNiceMobile.getsMobileNo(), mspSmsTemplateMstDto.getText(),
                            mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                            Constants.KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_COMPLETE_TEMPLATE_ID));

                               SessionUtils.saveNiceMobileSession(null);
                }
             }
        }

        return returnUrl;

    }


    @RequestMapping(value = {"/appForm/appOutFormComplete.do", "/m/appForm/appOutFormComplete.do"})
    public String appOutFormComplete(Model model, @ModelAttribute AppformReqDto appformReqDto) throws ParseException {
        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        //신청서 정보
        AppformReqDto appformReqTemp = appformSvc.getAppForm(appformReqDto);

        if (appformReqTemp == null) {
            throw new McpCommonException(ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
        }

        // ============= START: 완료페이지 진입 로직 강화 =============
        // 신청정보와 본인인증 세션 정보 일치여부 확인 (본인인증한 신청서의 경우, ci값 존재)
        NiceResDto sessNiceRes = SessionUtils.getNiceReqTmpCookieBean();

        if(!StringUtil.isEmpty(appformReqTemp.getSelfCstmrCi())){
            if(sessNiceRes == null) throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);

            if(!appformReqTemp.getSelfCstmrCi().equals(sessNiceRes.getConnInfo())){
                throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
            }
        }else{
            // CI값이 없는 신청정보인 경우, 본인인증 세션의 이름/생년월일과 신청정보의 이름/생년월일 비교
            if(sessNiceRes != null){
                String authNm= StringUtil.NVL(sessNiceRes.getName(), "").toUpperCase().replaceAll(" ", "");
                String authBirth= StringUtil.NVL(sessNiceRes.getBirthDate(), "");
                String requestNm= appformReqTemp.getCstmrName();
                String requestRrn= appformReqTemp.getCstmrNativeRrn();

                if(CSTMR_TYPE_NM.equals(appformReqTemp.getCstmrType())){
                    requestNm= appformReqTemp.getMinorAgentName();
                    requestRrn= appformReqTemp.getMinorAgentRrn();
                }

                try {
                    requestNm= StringUtil.NVL(requestNm, "").toUpperCase().replaceAll(" ", "");
                    requestRrn= EncryptUtil.ace256Dec(requestRrn);
                } catch (CryptoException e) {
                    requestNm= "";
                    requestRrn= "";
                }

                if(!requestNm.equals(authNm) || requestRrn.indexOf(authBirth) < 0){
                    throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
                }
            }
        }
        // ============= END: 완료페이지 진입 로직 강화 =============


        //유심비 /  가입비 설정
        String cntpntShopId = appformReqTemp.getCntpntShopId();

        // 대리점 명 확인 필요...
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(cntpntShopId);

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            /*logger.info("orgnNM==>"+orgnNM);
            logger.info("roadnAdrZipcd==>"+roadnAdrZipcd);
            logger.info("roadnAdrBasSbst==>"+roadnAdrBasSbst);
            logger.info("roadnAdrDtlSbst==>"+roadnAdrDtlSbst);*/

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst  );

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }



        //현재 대표 단말기 조회
        //현재 단품 ID 로 해당 대표모델ID를 조회한다.
        //TO_DO getModelId 확인 필요
        if (!StringUtils.isBlank(appformReqTemp.getModelId())) {
            RestTemplate restTemplate = new RestTemplate();
            PhoneMspDto phoneMspDto = restTemplate.postForObject(apiInterfaceServer + "/msp/mspPhoneInfo", appformReqTemp.getModelId(), PhoneMspDto.class);
            appformReqTemp.setRprsPrdtId(phoneMspDto.getRprsPrdtId());
            appformReqTemp.setPrdtId(appformReqTemp.getModelId());
            appformReqTemp.setPrdtNm(phoneMspDto.getPrdtCode());
        }

        String returnUrl = "/appForm/appOutFormComplete";

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            returnUrl = "/portal".concat(returnUrl);
        }

        model.addAttribute("AppformReq", appformReqTemp);
        return returnUrl;

    }


    /**
     * 설명 : 셀프개통 가능 여부 체크
     *
     * @return
     * @throws ParseException
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping({"/m/appForm/isSimpleApplyAjax.do", "/appForm/isSimpleApplyAjax.do"})
    @ResponseBody
    public Map<String, Object> isSimpleApplyObj() throws ParseException {
        return appformSvc.isSimpleApplyObj();
    }

    /**
     * @param : subscriberNo 전화번호_이전CTN
     * @return :
     * @Description : 가입여부 확인
     * @Author : power
     * @Create Date : 2020. 05. 08
     */
    @RequestMapping(value = "/juoSubIngoCountAjax.do")
    @ResponseBody
    public int juoSubIngoCount(@RequestParam(required = false, defaultValue = "") String subscriberNo) {
        RestTemplate restTemplate = new RestTemplate();
        int rtnInt = 0;
        rtnInt = restTemplate.postForObject(apiInterfaceServer + "/msp/juoSubIngoCount", subscriberNo, Integer.class);
        return rtnInt;
    }

    /**
     * <pre>
     * 설명     : 부가서비스 목록 조회
     * @param mcpRequestAdditionDto
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/getMcpAdditionListAjax.do")
    @ResponseBody
    public List<McpRequestAdditionDto> getMcpAdditionList(McpRequestAdditionDto mcpRequestAdditionDto) {

        if (StringUtils.isBlank(mcpRequestAdditionDto.getChargeFlag())) {
            throw new McpCommonJsonException("0001", INVALID_PARAMATER_EXCEPTION);
        }
        List<McpRequestAdditionDto> rtnList = appformSvc.getMcpAdditionList(mcpRequestAdditionDto);
        return rtnList;
    }

    /**
     * <pre>
     * 설명     : 휴대폰 안심 서비스 정보 조회
     * @return
     * @return: List<IntmInsrRelDTO>
     * </pre>
     */
    @RequestMapping(value = "/appform/selectInsrProdListAjax.do")
    @ResponseBody
    public IntmInsrRelDTO[] selectInsrProdList(IntmInsrRelDTO intmInsrRelDTO) {
        if (StringUtils.isBlank(intmInsrRelDTO.getReqBuyType())) {
            throw new McpCommonJsonException("0001", INVALID_PARAMATER_EXCEPTION);
        }
        RestTemplate restTemplate = new RestTemplate();
        IntmInsrRelDTO[] insrProdList = restTemplate.postForObject(apiInterfaceServer + "/appform/selectInsrProdList", intmInsrRelDTO, IntmInsrRelDTO[].class);
        return insrProdList;
    }

    /**
     * <pre>
     * 설명     : 서식지 저장 호출
     * @param appformReqDto
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = {"/appForm/saveAppformAjax.do", "/m/appForm/saveAppformAjax.do"})
    @ResponseBody
    public Map<String, Object> saveAppformDb(AppformReqDto appformReqDto
            , @RequestParam(required = false, defaultValue = "") String resUniqId) {
        //String resUniqId      //계좌점유 키값 (PASS)
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //2026.03 - 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한
        //예외 요금제인지 확인
        NmcpCdDtlDto exceptionPrice = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_EXCEPTION_LIST_PRICE_CD, appformReqDto.getSocCode());
        if(exceptionPrice == null) {
            //직영, 신규개통, 상담사 신청서, 유심있음 일 때에만
            if ("1100011741".equals(appformReqDto.getCntpntShopId())
                    && "NAC3".equals(appformReqDto.getOperType())
                    && Arrays.asList("0", "3").contains(appformReqDto.getOnOffType())
                    && (!StringUtils.isEmpty(appformReqDto.getReqUsimSn()) || "09".equals(appformReqDto.getUsimKindsCd()))) {

                NiceResDto niceResCookieBean = SessionUtils.getNiceResCookieBean();
                if (niceResCookieBean == null) {
                    throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
                }

                AppformReqDto checkDto = new AppformReqDto();
                checkDto.setCstmrName(appformReqDto.getCstmrName());
                checkDto.setSelfCstmrCi(niceResCookieBean.getConnInfo());
                checkDto.setUsimKindsCd(appformReqDto.getUsimKindsCd());

                //신규개통 이력 체크
                AppformReqDto rtnAppformReq = appformSvc.getLimitForm(checkDto);
                if (rtnAppformReq != null) {
                    rtnMap.put("RESULT_CODE", "-1");
                    rtnMap.put("ERROR_NE_MSG", SELF_LIMIT_EXCEPTION);
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("SELF010_LIMIT_CHECK_4");
                    mcpIpStatisticDto.setPrcsSbst(rtnAppformReq.getContractNum());  //계약번호
                    mcpIpStatisticDto.setParameter("NCN[" + rtnAppformReq.getContractNum() + "]CI[" + rtnAppformReq.getSelfCstmrCi() + "]");
                    mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    return rtnMap;
                }
            }
        }

        // 2024-12-17 인가된 사용자 체크
        if (!"E0000003".equals(appformReqDto.getRprsPrdtId())) { // esimWatch는 패스
            Map<String, String> rtnChkAuthMap = new HashMap<>();

            String temSsn = "FN".equals(appformReqDto.getCstmrType()) ? appformReqDto.getCstmrForeignerRrnDesc() : appformReqDto.getDesCstmrNativeRrn();

            if (OPER_TYPE_CHANGE.equals(appformReqDto.getOperType()) || OPER_TYPE_EXCHANGE.equals(appformReqDto.getOperType())) {
                JuoSubInfoDto sesionJuoSub = SessionUtils.getChangeAutCookieBean();
                temSsn = sesionJuoSub.getCustomerSsn();
            }
            // 2025-02-03 외국인시 체크 로직 수정
            rtnChkAuthMap = msfMypageSvc.checkAuthUser(appformReqDto.getCstmrName(), temSsn);


            if (!"0000".equals(rtnChkAuthMap.get("returnCode"))) {
                rtnMap.put("RESULT_CODE", rtnChkAuthMap.get("returnCode"));
                rtnMap.put("RESULT_MSG", rtnChkAuthMap.get("returnMsg"));
                return rtnMap;
            }
        }

        //인자값 확인
        String reqBuyType = appformReqDto.getReqBuyType();
        if (!REQ_BUY_TYPE_PHONE.equals(reqBuyType) && !REQ_BUY_TYPE_USIM.equals(reqBuyType)) {
            throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
        }

        //eSIM 확인
        fnSetDataOfeSim(appformReqDto);

        // 이벤트코드 검증
        if(!StringUtil.isEmpty(appformReqDto.getEvntCdPrmt())){
            GiftPromotionBas giftPromotVal = new GiftPromotionBas();
            giftPromotVal = eventCodeSvc.getEventchk(appformReqDto.getEvntCdPrmt());

            if(giftPromotVal == null || giftPromotVal.getEcpSeq() ==null){
                throw new McpCommonJsonException("-1", "입력하신 이벤트 코드는 사용이 불가합니다.");
            }
        }

            //사은품 정보 검증
        String[] prmtIdList = appformReqDto.getPrmtIdList();
        String[] prdtIdList = appformReqDto.getPrdtIdList();
        String[] prmtTypeList = new String[prmtIdList.length];
        List<GiftPromotionBas> giftPromotionBasList = null;
        int prmtTypeListIndex = 0;

            //사은품정보는 유심코드에 조직코드에 우선 한다.
            String orgnId = "";
            if (!StringUtils.isBlank(appformReqDto.getReqUsimSn()) && !"Y".equals(appformReqDto.getJehuRefererYn())) { //제휴위탁온라인의 경우 대리점코드로 사은품 정보를 입력한다.
                orgnId = appformSvc.getUsimOrgnId(appformReqDto.getReqUsimSn());
                if (orgnId == null || orgnId.equals("")) {
                    orgnId = appformReqDto.getCntpntShopId();
                }
            } else {
                orgnId = appformReqDto.getCntpntShopId();
            }

            GiftPromotionBas giftPromotionBas = new GiftPromotionBas();
            giftPromotionBas.setOnOffType(appformReqDto.getOnOffType());
            giftPromotionBas.setOperType(appformReqDto.getOperType());
            giftPromotionBas.setReqBuyType(appformReqDto.getReqBuyType());
            giftPromotionBas.setAgrmTrm(appformReqDto.getEnggMnthCnt() + "");
            giftPromotionBas.setRateCd(appformReqDto.getSocCode());
            giftPromotionBas.setOrgnId(orgnId);
            giftPromotionBas.setModelId(appformReqDto.getModelId());
            giftPromotionBasList = giftSvc.giftBasList(giftPromotionBas, appformReqDto.getCntpntShopId());

            if (prmtIdList != null && prmtIdList.length > 0) {
                if (giftPromotionBasList == null || giftPromotionBasList.size() < 1) {
                    throw new McpCommonJsonException("8881", ExceptionMsgConstant.GIFT_EXSIST_EXCEPTION);
                } else {
                    //사은품 그룹 존재 여부 확인
                    for (int i = 0; i < prmtIdList.length; i++) {
                        GiftPromotionDtl giftPromotion = new GiftPromotionDtl();
                        giftPromotion.setPrmtId(prmtIdList[i]);
                        giftPromotion.setPrdtId(prdtIdList[i]);
                        boolean isHave = false;
                        for (GiftPromotionBas promotionBas : giftPromotionBasList) {
                            List<GiftPromotionDtl> giftPromotionDtlList = promotionBas.getGiftPromotionDtlList();
                            for (GiftPromotionDtl temp : giftPromotionDtlList) {
                                if (giftPromotion.getPrmtId().equals(temp.getPrmtId()) && giftPromotion.getPrdtId().equals(temp.getPrdtId())) {
                                    isHave = true;
                                    prmtTypeList[prmtTypeListIndex] = promotionBas.getPrmtType();
                                    prmtTypeListIndex++;
                                    break;
                                }
                            }
                            if (isHave) {
                                break;
                            }
                        }
                        if (!isHave) {
                            throw new McpCommonJsonException("8882", ExceptionMsgConstant.GIFT_EXSIST_EXCEPTION);
                        }
                    }

                    appformReqDto.setPrmtTypeList(prmtTypeList);

                    //사은품 갯수 확인
                    for (GiftPromotionBas promotionBas : giftPromotionBasList) {
                        int limitCnt = promotionBas.getLimitCnt();
                        int nowCnt = 0;//선택한 갯수
                        String basePrmtId = promotionBas.getPrmtId();
                        for (int i = 0; i < prmtIdList.length; i++) {
                            if (basePrmtId.equals(prmtIdList[i])) {
                                nowCnt++;
                            }
                        }
                        if (limitCnt < nowCnt) {
                            throw new McpCommonJsonException("8883", ExceptionMsgConstant.GIFT_LIMIT_EXCEPTION);
                        }
                    }
                }

            }
            //사은품 Check end

        //동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회
        int limitReqFormCnt = 100;

        String limitReqFormCntStr = NmcpServiceUtils.getCodeNm("Constant", "LimitReqFormCnt");
        if (limitReqFormCntStr != null && !limitReqFormCntStr.equals("")) {
            try {
                limitReqFormCnt = Integer.parseInt(limitReqFormCntStr);
            } catch (NumberFormatException e) {
                limitReqFormCnt = 100;
            }
        }

        /*
         * 100 이상이면 체크 하지 않음
         */
        int checkLimitOpenFormCount = -1;
        if (limitReqFormCnt < 100) {
            RestTemplate restTemplate = new RestTemplate();
            try {
                checkLimitOpenFormCount = restTemplate.postForObject(apiInterfaceServer + "/appform/checkLimitOpenFormCount", appformReqDto, Integer.class);
            } catch (Exception e) {
                throw new McpCommonJsonException("9996", "API연동 오류 /appform/checkLimitOpenFormCount");
            }

        }

        if (checkLimitOpenFormCount >= limitReqFormCnt) {
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FORM_OPEN_LIMIT");
            mcpIpStatisticDto.setPrcsSbst("fCnt[" + checkLimitOpenFormCount + "]lCnt[" + limitReqFormCnt + "]");
            mcpIpStatisticDto.setParameter("COUNT[" + checkLimitOpenFormCount + "]CUSTOMER_SSN[" + appformReqDto.getCstmrNativeRrn() + "]CSTMR_FOREIGNER_RRN[" + appformReqDto.getCstmrForeignerRrn() + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            throw new McpCommonJsonException("0006", OVER_LIMIT_OPEN_FORM_EXCEPTION);
        }

        //1. 인증 정보 확인
        String autCstmrNm = "";

        // 인자값 referer
        String orgOpenMarketReferer = appformReqDto.getOpenMarketReferer();

        //1-1. 청소년 나이 인증 확인
        if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
            //청소년
            autCstmrNm = appformReqDto.getMinorAgentName();
            //나이 확인
            int age = NmcpServiceUtils.getAge(appformReqDto.getDesCstmrNativeRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            if (19 <= age || 1 > age) {
                throw new McpCommonJsonException("0001", REGNO_TEEN_EXCEPTION);
            }
        } else if (CSTMR_TYPE_NA.equals(appformReqDto.getCstmrType())) {
            //내국인 성인 인증
            int age = NmcpServiceUtils.getAge(appformReqDto.getDesCstmrNativeRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
            if (19 > age) {
                throw new McpCommonJsonException("0001", REGNO_ADULT_EXCEPTION);
            }
            autCstmrNm = appformReqDto.getCstmrName();
        } else {
            autCstmrNm = appformReqDto.getCstmrName();
        }

        // 골드번호 입력 확인
        if (OPER_TYPE_NEW.equals(appformReqDto.getOperType())) {
            appformSvc.containsGoldNumbers(Arrays.asList(appformReqDto.getReqWantNumber(), appformReqDto.getReqWantNumber2(), appformReqDto.getReqWantNumber3()));
        }

        //유심비 /  가입비 설정
        String cntpntShopId = appformReqDto.getCntpntShopId();

        UsimBasDto usimBasDtoParm = new UsimBasDto();
        usimBasDtoParm.setOrgnId(cntpntShopId);
        usimBasDtoParm.setOperType(appformReqDto.getOperTypeSmall());
        usimBasDtoParm.setDataType(appformReqDto.getPrdtSctnCd());
        usimBasDtoParm.setRateCd(appformReqDto.getSocCode());
        usimBasDtoParm.setReqBuyType(appformReqDto.getReqBuyType());

        //NFC 유심 처리
        if ("08".equals(appformReqDto.getUsimKindsCd())) {
            usimBasDtoParm.setDataType(NFC_FOR_MSP);
        }

        //eSIM 처리
        if ("09".equals(appformReqDto.getUsimKindsCd())) {
            //eSIM
            usimBasDtoParm.setPrdtIndCd("10");
            /**
             * ㅇ 이슈사항 : M전산에서 스마트 워치 직접개통(OSST개통) 시 하기와 같은 alert 발생             *
             * ㅇ 발생 오류코드 : 3107  [DEFKTF] 상품은 [애플워치 단말기에서는 휴대폰결제 비밀번호서비스 부가서비스를 가입할 수 없습니다.]사유로 가입이 불가 합니다.
             * ㅇ 확인된 원인
             *  1) PC0(사전체크) 전문의 하기 두 값이 Y로 연동 될 경우 MP에서 '휴대폰결제 비밀번호서비스(MPAYPSSWD)를 자동으로 가입 시킴.
             *  2) 휴대폰결제 비밀번호서비스(MPAYPSSWD) 부가서비스와 스마트워치는 베타관계로, 부가서비스로 자동 가입으로 인한 개통 실패 발생
             * ※ 현재 포탈에서 신청서 작성 시 하기 값을 Null값으로 셋팅하고 있으나 M전산에서는 하기 값이 Null일 경우 "Y"로 신청서 생성 중
             * 셀프 개통 이동전화결제이용동의여부 무조건 N으로 설정
             */
            appformReqDto.setPhonePayment("N");

        } else {
            usimBasDtoParm.setPrdtIndCd(appformReqDto.getUsimKindsCd());
        }

        Map<String, String> simInfoMap = usimService.getSimInfo(usimBasDtoParm);

        int intJoinPrice = Integer.parseInt(simInfoMap.get("JOIN_PRICE"));
        int intUsimPrice = Integer.parseInt(simInfoMap.get("SIM_PRICE"));

        //1-2. 고객인증 인증 확인
        if (OPER_TYPE_CHANGE.equals(appformReqDto.getOperType()) || OPER_TYPE_EXCHANGE.equals(appformReqDto.getOperType())) {
            JuoSubInfoDto sesionJuoSub = SessionUtils.getChangeAutCookieBean();
            String ctn = appformReqDto.getCstmrMobileFn() + appformReqDto.getCstmrMobileMn() + appformReqDto.getCstmrMobileRn();
            String birthday = appformReqDto.getBirthDate(); //고객인증한 생년월일
            if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
                String strTemp = appformReqDto.getCstmrNativeRrnDesc();
                if (strTemp.length() > 5) {
                    birthday = strTemp.substring(0, 6);
                } else {
                    birthday = strTemp;
                }
            }

            if (sesionJuoSub == null
                    || sesionJuoSub.getCustomerSsn().indexOf(birthday) == -1
                    || !sesionJuoSub.getSubscriberNo().equals(ctn)) {
                String isNull = "N";
                String customerSsn = "";
                String subscriberNo = "";

                if (sesionJuoSub == null) {
                    isNull = "Y";
                } else {
                    customerSsn = sesionJuoSub.getCustomerSsn();
                    subscriberNo = sesionJuoSub.getSubscriberNo();
                }

                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("SAVE_APP_FORM_CHANG");
                mcpIpStatisticDto.setPrcsSbst("userName::[" + appformReqDto.getCstmrName() + "]");
                mcpIpStatisticDto.setParameter("JuoSubInfoDto[" + isNull + "::customerSsn[" + customerSsn + "]::subscriberNo[" + subscriberNo + "]::birthday[" + birthday + "]::ctn[" + ctn + "]");
                mcpIpStatisticDto.setTrtmRsltSmst("");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                throw new McpCommonJsonException("0002", CHANGE_AUT_EXCEPTION);
            }

            //계약 번호 설정
            appformReqDto.setContractNum(sesionJuoSub.getContractNum());
            //금액 정보 설정
            if (StringUtils.isBlank(appformReqDto.getModelSalePolicyCode())
                    || StringUtils.isBlank(appformReqDto.getRprsPrdtId())
                    || StringUtils.isBlank(appformReqDto.getCntpntShopId())
                    || StringUtils.isBlank(appformReqDto.getOperType())
                    || StringUtils.isBlank(appformReqDto.getSocCode())
                    || "null".equals(appformReqDto.getSocCode())) {
                throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
            }

            //가입비 납부방법(0 : 면제, 1:일시납, 2: 분납(3개월)   <=== TODO : 확인 필요
            //가입비 납부방법(1 면제 , 2 일시납, 3 3개월분납) <== MSP확인
            appformReqDto.setJoinPayMthdCd("1");
            appformReqDto.setJoinPriceType("P"); // 가입비납부유형(R:완납, I:분납 , P:면제) */
            // 현재 유심 유심번호 설정
            if ("iccId".equals(appformReqDto.getReqUsimSn())) {
                appformReqDto.setReqUsimSn(sesionJuoSub.getIccId());
                appformReqDto.setUsimPriceType("N");
            } else {
                appformReqDto.setUsimPrice(intUsimPrice);
            }

            //usimKind 06 미발송  _ 기존 유심 사용 ..
            if ("06".equals(appformReqDto.getUsimKindsCd())) {
                appformReqDto.setUsimPayMthdCd("1");
                appformReqDto.setUsimPrice(0);
            }


        } else {
            if (REQ_BUY_TYPE_PHONE.equals(reqBuyType)) {
                if (StringUtils.isBlank(appformReqDto.getModelSalePolicyCode())
                        || StringUtils.isBlank(appformReqDto.getRprsPrdtId())
                        || StringUtils.isBlank(appformReqDto.getCntpntShopId())
                        || StringUtils.isBlank(appformReqDto.getOperType())
                        || StringUtils.isBlank(appformReqDto.getSocCode())
                        || "null".equals(appformReqDto.getSocCode())) {
                    throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
                }
            } else {
                if (StringUtils.isBlank(appformReqDto.getCntpntShopId())
                        || StringUtils.isBlank(appformReqDto.getOperType())
                        || StringUtils.isBlank(appformReqDto.getSocCode())
                        || "null".equals(appformReqDto.getSocCode())) {
                    throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
                }
            }

            //가입비 유심비 설정
            /** 가입비 납부방법
             * 1 면제
             * 2 일시납
             * 3 3개월분납
             * 22년... 8월 23일 .. 세희 과장님 하고.. 통화 정리 함..
             * 고객에서 면제 라고 표현 하고 실제로.. M모바일에서 대납 처리 함....
             */
            if ("N".equals(simInfoMap.get("JOIN_IS_PAY"))) {
                appformReqDto.setJoinPayMthdCd("2");
                appformReqDto.setJoinPrice(0);
            } else {
                //가입비가 0원이면 면제 아니면 3개월 분납
                if (intJoinPrice > 0) {
                    appformReqDto.setJoinPayMthdCd("3");
                    appformReqDto.setJoinPrice(intJoinPrice);
                } else {
                    appformReqDto.setJoinPayMthdCd("2");
                    appformReqDto.setJoinPrice(0);
                }
            }

            //NFC확인 필요.....TO_DO
            if ("N".equals(simInfoMap.get("SIM_IS_PAY"))) {
                appformReqDto.setUsimPayMthdCd("1");
                appformReqDto.setUsimPrice(0);
            } else {
                //유심비가 0원이면 면제 아니면 3개월분납
                if (intUsimPrice > 0) {
                    appformReqDto.setUsimPayMthdCd("3");
                    appformReqDto.setUsimPrice(intUsimPrice);
                } else {
                    appformReqDto.setUsimPayMthdCd("1");
                    appformReqDto.setUsimPrice(0);
                }
            }

        }

        //1-3. 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

        //PASS + 계좌인증
        String dtoAuthType = StringUtil.NVL(appformReqDto.getOnlineAuthType(), "");
        if ("A".equals(dtoAuthType)) {
            sessNiceRes.setAuthType("A");  //검증 통과 하기 위해
        }

        if (!"Y".equals(appformReqDto.getTelAdvice())) {
            if (sessNiceRes == null
                    || !sessNiceRes.getAuthType().equals(dtoAuthType)
                    || !sessNiceRes.getName().equals(autCstmrNm)
                    || sessNiceRes.getBirthDate().indexOf(appformReqDto.getBirthDate()) < 0) {

                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("FORM_AUTH_ERROR");
                if (sessNiceRes == null) {
                    mcpIpStatisticDto.setPrcsSbst("sessNiceRes is NULL");
                } else {
                    mcpIpStatisticDto.setPrcsSbst("AuthType[" + sessNiceRes.getAuthType() + "] Name[" + sessNiceRes.getName() + "] BirthDate[" + sessNiceRes.getBirthDate() + "] ");
                }
                mcpIpStatisticDto.setParameter("AuthType[" + dtoAuthType + "] Name[" + autCstmrNm + "] BirthDate[" + appformReqDto.getBirthDate() + "] ");
                mcpIpStatisticDto.setTrtmRsltSmst("saveAppformDbAjax");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                throw new McpCommonJsonException("0004", NICE_CERT_EXCEPTION);
            }
            appformReqDto.setOnlineAuthInfo("ReqNo:" + sessNiceRes.getReqSeq() + ", ResNo:" + sessNiceRes.getResSeq());
            appformReqDto.setSelfCstmrCi(sessNiceRes.getConnInfo());
            appformReqDto.setSelfCstmrDi(sessNiceRes.getDupInfo());

        } else {
            appformReqDto.setOnlineAuthInfo("");
            appformReqDto.setSelfCstmrCi("");
            appformReqDto.setOnlineAuthType("G");
        }

        //휴대폰 안심 서비스 처리 인증 확인
        if (!StringUtils.isBlank(appformReqDto.getInsrProdCd())) {
            NiceResDto sessNiceInsrRes = SessionUtils.getNiceInsrResCookieBean();
            if (!"Y".equals(appformReqDto.getTelAdvice())) {
                if (sessNiceInsrRes == null
                        || !sessNiceRes.getConnInfo().equals(sessNiceInsrRes.getConnInfo())) {
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("FORM_AUTH_ERROR");
                    if (sessNiceInsrRes == null) {
                        mcpIpStatisticDto.setPrcsSbst("sessNiceInsrRes is NULL");
                    } else {
                        mcpIpStatisticDto.setPrcsSbst("ConnInfo[" + sessNiceInsrRes.getConnInfo() + "] Name[" + sessNiceInsrRes.getName() + "] ");
                    }
                    mcpIpStatisticDto.setParameter("ConnInfo[" + sessNiceRes.getConnInfo() + "]");
                    mcpIpStatisticDto.setTrtmRsltSmst("saveAppformDbAjax");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    throw new McpCommonJsonException("0005", NICE_CERT_EXCEPTION_INSR2);
                }
                appformReqDto.setClauseInsrProdFlag("Y");
                appformReqDto.setInsrAuthInfo("ReqNo:" + sessNiceInsrRes.getReqSeq() + ", ResNo:" + sessNiceInsrRes.getResSeq());
            } else {
                appformReqDto.setClauseInsrProdFlag("Y");
                appformReqDto.setInsrAuthInfo("");
            }
        }else{
            // 휴대폰 안심보험 인증 후 신청하지 않은 경우 step 제거
            if(0 < certService.getModuTypeStepCnt(INSR_PROD, "")){
                CertDto certDto = new CertDto();
                certDto.setModuType(INSR_PROD);
                certDto.setCompType("G");
                certService.getCertInfo(certDto);
            }
        }

        // 자급제 보상 서비스 처리 인증 확인
        if (!StringUtils.isBlank(appformReqDto.getRwdProdCd())) {
            NiceResDto sessNiceRwdRes = SessionUtils.getNiceRwdResCookieBean();
            if (!"Y".equals(appformReqDto.getTelAdvice())) {  // 해피콜이 아닌 경우만 신청 가능

                // 본인인증 정보와 동일하지 않은 경우
                if (sessNiceRwdRes == null || !sessNiceRes.getConnInfo().equals(sessNiceRwdRes.getConnInfo())) {
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("FORM_AUTH_ERROR");
                    if (sessNiceRwdRes == null) {
                        mcpIpStatisticDto.setPrcsSbst("sessNiceRwdRes is NULL");
                    } else {
                        mcpIpStatisticDto.setPrcsSbst("ConnInfo[" + sessNiceRwdRes.getConnInfo() + "] Name[" + sessNiceRwdRes.getName() + "] ");
                    }
                    mcpIpStatisticDto.setParameter("ConnInfo[" + sessNiceRes.getConnInfo() + "]");
                    mcpIpStatisticDto.setTrtmRsltSmst("saveAppformDbAjax");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                    throw new McpCommonJsonException("2001", NICE_CERT_EXCEPTION_RWD);
                }
                appformReqDto.setClauseRwdFlag("Y");
                appformReqDto.setRwdAuthInfo("ReqNo:" + sessNiceRwdRes.getReqSeq() + ", ResNo:" + sessNiceRwdRes.getResSeq());

            } else { // 해피콜인 경우 자급제 보상 서비스 신청 불가
                appformReqDto.setClauseRwdFlag("N");
                appformReqDto.setRwdAuthInfo("");
            }
        }


        //회원 아이디 설정
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            appformReqDto.setCretId(userSessionDto.getUserId());
        } else {
            appformReqDto.setCretId(NON_MEMBER_ID);
        }

        //할인정보 설정
        MspSaleSubsdMstDto saleSubsdMst = null;
        if (!StringUtils.isBlank(appformReqDto.getModelSalePolicyCode())) {
            MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
            mspSaleSubsdMstDto.setForFrontFastYn("Y");
            mspSaleSubsdMstDto.setSalePlcyCd(appformReqDto.getModelSalePolicyCode()); //정책코드
            mspSaleSubsdMstDto.setPrdtId(appformReqDto.getRprsPrdtId());  //대표상품 아이디
            mspSaleSubsdMstDto.setOldYn("N");
            mspSaleSubsdMstDto.setOrgnId(appformReqDto.getCntpntShopId());
            mspSaleSubsdMstDto.setOperType(appformReqDto.getOperType());//가입유형
            mspSaleSubsdMstDto.setNoArgmYn("Y");
            mspSaleSubsdMstDto.setAgrmTrm(String.valueOf(appformReqDto.getEnggMnthCnt()));//입력받은 할부기간을 약정기간
            mspSaleSubsdMstDto.setRateCd(appformReqDto.getSocCode());//요금제코드

            RestTemplate restTemplate = new RestTemplate();
            MspSaleSubsdMstDto[] chargeTemList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleSubsdMstList", mspSaleSubsdMstDto, MspSaleSubsdMstDto[].class);
            List<MspSaleSubsdMstDto> chargeList = Arrays.asList(chargeTemList);

            if (chargeList != null && chargeList.size() > 0) {
                saleSubsdMst = chargeList.get(0);

                //유심도 그냥.. 저장 해도 될것 같은데... ???
                if (REQ_BUY_TYPE_PHONE.equals(reqBuyType)) {
                    appformReqDto.setMaxDiscount3(saleSubsdMst.getAgncySubsdMax()); //대리점보조금MAX(VAT포함)
                    appformReqDto.setDcAmt(saleSubsdMst.getDcAmt());  //기본할인금액
                    appformReqDto.setAddDcAmt(saleSubsdMst.getAddDcAmt());  //추가할인금액
                    appformReqDto.setModelInstallment(saleSubsdMst.getInstAmt());     //할부원금(VAT포함)
                    appformReqDto.setModelDiscount2(saleSubsdMst.getSubsdAmt());     //공시지원금(VAT포함)
                    appformReqDto.setModelDiscount3(saleSubsdMst.getAgncySubsdAmt());     //대리점보조금(VAT포함)
                    appformReqDto.setRealMdlInstamt(saleSubsdMst.getHndstAmt());   //단말금액(VAT포함)
                    appformReqDto.setModelPrice(saleSubsdMst.getHndstVatExAmt());   //단말금액 VAT제외 리턴
                    appformReqDto.setModelPriceVat(saleSubsdMst.getHndstVatAmt());   //단말금액 VAT 리턴
                } else {
                    appformReqDto.setDcAmt(saleSubsdMst.getDcAmt());  //기본할인금액
                    appformReqDto.setAddDcAmt(saleSubsdMst.getAddDcAmt());  //추가할인금액
                }

            } else {
                throw new McpCommonJsonException("0005", ExceptionMsgConstant.NO_EXSIST_RATE);
            }
        }

        //상품정보 조회
        PhoneProdBasDto phoneProdBas = null;
        if (!StringUtils.isBlank(appformReqDto.getProdId())) {
            PhoneProdBasDto phoneProdBasDto = new PhoneProdBasDto();
            phoneProdBasDto.setProdId(appformReqDto.getProdId());
            /*
             * ## 1.상품및 단품정보 조회
             *  상품대표이미지,용량색상별 정보 등을 조회한다.*/
            phoneProdBas = phoneService.findPhoneProdBasDtoByProdId(phoneProdBasDto);

            if (phoneProdBas != null && !StringUtils.isBlank(phoneProdBas.getProdNm())) {
                appformReqDto.setProdNm(phoneProdBas.getProdNm());
            }

            //MODEL_MONTHLY 0 , 단말기 금액 0원 이상
            if ("0".equals(appformReqDto.getModelMonthly())  && saleSubsdMst.getInstAmt() > 0
            ) {
                if (saleSubsdMst == null) {
                    throw new McpCommonJsonException("0006", ExceptionMsgConstant.NO_EXSIST_RATE);
                }
                appformReqDto.setSettlWayCd("01");
                appformReqDto.setSettlAmt(saleSubsdMst.getInstAmt());  //단말기 할부원금을 가져온다.
                appformReqDto.setHndsetSalePrice(saleSubsdMst.getInstAmt() + ""); //단말기 금액설정?????
                appformReqDto.setPstate("10");  //10[고객취소] 초기 등록
            }
        }

        if (REQ_BUY_TYPE_USIM.equals(appformReqDto.getReqBuyType())) {
            //유심 이고..
            if (appformReqDto.getEnggMnthCnt() > 0) {
                //심플할인으로 설정
                appformReqDto.setSprtTp(SIMPLE_USIM_DISCOUNT_FOR_MSP);
            }

            //프리피아 여부 확인
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_REGI_SITE_LIST, appformReqDto.getCntpntShopId());
            if (nmcpCdDtlDto == null) {
                if (SERVICE_TYPE_PREPAID.equals(appformReqDto.getServiceType())) {
                    //선불
                    appformReqDto.setJoinPrice(0);
                    appformReqDto.setUsimPrice(0);
                    appformReqDto.setJoinPayMthdCd("1");
                    appformReqDto.setUsimPayMthdCd("1");
                } else {
                    //appformReqDto.setJoinPayMthdCd("3");  TO_DO 이것 뭐지 왜????
                    if (OPEN_MARKET_REFERER_M_SIMPLE.equals(appformReqDto.getOpenMarketReferer())  && "0000000000000000000".equals(appformReqDto.getReqUsimSn()) ) {
                        //M SHOP 셀프개통에서 해피콜 개통 호출
                        //온라인 구매
                        appformReqDto.setOpenMarketReferer(OPEN_MARKET_REFERER_M_SIMPLE + "_D");
                    }
                }


            } else {
                //프리피아 사이트
               /*
                * 1. 가입비/유심비 처리  UsimRegiSiteList
                   -유심비 
                                         유심오프라인의 모든 접점은 USIM_PAY_MTHD_CD  --USIM비 납부방법 1:면제 
                    -가입비 
                                    공통코드 UsimRegiSiteList 확장필드 2번에 따라 부과여부 결정 Y면 JOIN_PAY_MTHD_CD 값이 3, 면제면 1
              */
                //유심 오픈 라인
//               if("N".equals(nmcpCdDtlDto.getExpnsnStrVal2()) ){
//                   appformReqDto.setJoinPayMthdCd("1");
//                   appformReqDto.setJoinPrice(0);
//               } else {
//                   appformReqDto.setJoinPayMthdCd("3");
//               }

                NmcpCdDtlDto nmcpCdOnlineDto = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_OFF_ONLINE_LIST, appformReqDto.getCntpntShopId());
                if (nmcpCdOnlineDto != null && !StringUtils.isBlank(nmcpCdOnlineDto.getExpnsnStrVal1())) {
                    appformReqDto.setCntpntShopId(nmcpCdOnlineDto.getExpnsnStrVal1());
                    //온라인 구매
                    if ("0000000000000000000".equals(appformReqDto.getReqUsimSn())) {
                        if (OPEN_MARKET_REFERER_M_SIMPLE.equals(orgOpenMarketReferer)) {  //셀프개통에서 해피콜 개통 호출
                            appformReqDto.setOpenMarketReferer(nmcpCdOnlineDto.getExpnsnStrVal2() + "_D");
                        } else {
                            appformReqDto.setOpenMarketReferer(nmcpCdOnlineDto.getExpnsnStrVal2() + "_O");
                        }
                    } else {
                        appformReqDto.setOpenMarketReferer(nmcpCdOnlineDto.getExpnsnStrVal2());
                    }
                }

                /*
                 * 셀프개통 , 프리파아 해피콜에서 호출
                 * MSP_USIM_MGMT_MST@DL_MSP 에 유심 번호가 존재 하면 무료
                 * SRM19082377692 셀프개통 페이지 내 유심 미보유 고객 대상 유심배송 프로세스 추가 건
                 */
               /*if ( !StringUtils.isBlank(appformReqDto.getReqUsimSn()) && !"0000000000000000000".equals(appformReqDto.getReqUsimSn()) && "".equals(StringUtil.NVL(appformReqDto.getMarket(),"")) ) {
                   int usimCnt =  mspService.sellUsimMgmtCount(appformReqDto.getReqUsimSn());
                   if (usimCnt > 0) {
                       appformReqDto.setUsimPayMthdCd("1");
                       appformReqDto.setUsimPrice(0);
                   }
               }*/
            }

        } else if (REQ_BUY_TYPE_PHONE.equals(appformReqDto.getReqBuyType())  && appformReqDto.getEnggMnthCnt() == 0 ) {
            //SRM18010857388  풀할부 정책 운영을 위한 고객포탈 수정 요청
            appformReqDto.setSprtTp("");
        }

        /*
         * 프리피아 , 유심 , 휴대폰
         *  '면제'로 기입해도 직접개통 or KOS 개통 시 수정 가능하므로 '유심비 면제'로 연동
         */
        if (!StringUtils.isBlank(appformReqDto.getReqUsimSn())) {
            appformReqDto.setUsimKindsCd("06");  //유심 종류 기존 M전산에 존재하는 항목으로 ‘유심보유’로 선택해서 저장할 경우 유심종류에 ‘미발송’으로
            appformReqDto.setUsimPayMthdCd("1");
            appformReqDto.setUsimPrice(0);
        }

        /*MCP_REQUEST.PSTATE
        코드값	명칭
        00	정상
        10	고객취소
        20	관리자삭제
        30	예약번호삭제
        40	관리자삭제(유심번호미입력)*/

        /*
         * MCP_REQUEST.REQUEST_STATE_CODE
         *
         * MCP_REQUEST_STATE.REQUEST_STATE_CODE 코드값 명칭  
         * 00 접수대기   01 접수   02 해피콜   03 신청서배송   04 신청서배송완료   07 배송대기(퀵)   08 배송대기(소화물)
         *   09 배송대기(택배)   10 배송중   11 배송완료   13 배송완료(유심등록완료)   20 개통대기   21 개통완료  
         *  30 사전체크오류   31 개통처리오류  
         */
        appformReqDto.setManagerCode("M0001");
        appformReqDto.setRid("-");
        appformReqDto.setViewFlag("Y");
        appformReqDto.setRequestStateCode("00");


        if (phoneProdBas != null && "Y".equals(phoneProdBas.getSesplsYn())) {
            //자급제.. 폰
            appformReqDto.setSesplsYn("Y");
            appformReqDto.setHndsetSalePrice(phoneProdBas.getOutUnitPric() + ""); //단말기 금액설정
            appformReqDto.setCntpntShopId(Constants.CONTPNT_SELF_PHONE);
            appformReqDto.setProdType("04"); //일반 :01, 0원 상품 :02 , 중고폰 : 03, 자급제+유심 :04
            //appformReqDto.setSesplsProdId(phoneProdBas.getRprsPrdtId());


            List<PhoneSntyBasDto> phoneSntyBasDtoList = phoneProdBas.getPhoneSntyBasDtosList();

            Boolean isCheck = false;
            String rprsPrdtId = appformReqDto.getSesplsProdId();
            for (int i = 0; i < phoneSntyBasDtoList.size(); i++) {
                if (rprsPrdtId.equals(phoneSntyBasDtoList.get(i).getHndsetModelId())) {
                    isCheck = true;
                    break;
                }
            }

            if (!isCheck) {
                throw new McpCommonJsonException("0008", ExceptionMsgConstant.SELF_PHONE_CODE_NULL_EXCEPTION);
            }

            //할인 카드 검증
            //<c:set var="cardDcInfoList" value="${nmcp:getCodeList('BE0020')}" />
            //appformReqDto.getCardDcCd()
            if (!StringUtils.isBlank(appformReqDto.getCardDcCd()) && !"".equals(appformReqDto.getCardTotDcAmt())) {
                NmcpCdDtlDto cardDcInfo = NmcpServiceUtils.getCodeNmDto("BE0020", appformReqDto.getCardDcCd());
                int maxDcAmtInt = Integer.parseInt(cardDcInfo.getExpnsnStrVal3());
                int cardTotDcAmt = Integer.parseInt(appformReqDto.getCardTotDcAmt());

                //if (maxDcAmtInt < cardTotDcAmt) {
                if (cardTotDcAmt > maxDcAmtInt) {
                    throw new McpCommonJsonException("0009", ExceptionMsgConstant.MAX_DC_OVER_EXCEPTION);
                }
            }

            if (appformReqDto.getPaymentHndsetPrice() == 0) {
                //결제 처리 하지 않음
                appformReqDto.setPstate("00");
            } else {
                appformReqDto.setPstate("20");  //20[관리자삭제] 초기 등록
                appformReqDto.setRequestStateCode("30"); //사전체크오류
                appformReqDto.setViewFlag("N");
            }

        } else {
            if (!CNTPNT_SHOPID_PHONE_DIRECT.equals(appformReqDto.getCntpntShopId())) {
                appformReqDto.setProdType(PhoneConstant.PROD_TYPE_COMMON);
            } else {
                /** 1100034010 폰플러스(직구)  상품 분류 직구로 설정 */
                appformReqDto.setProdType(PhoneConstant.PROD_TYPE_DIRECT);
            }
        }


        if (!StringUtils.isBlank(appformReqDto.getUsePoint()) && !StringUtils.isBlank(appformReqDto.getUsePointSvcCntrNo()) && userSessionDto == null) {
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("FORM_SESSION_NULL");
            mcpIpStatisticDto.setPrcsSbst("USER_POINT[" + appformReqDto.getUsePoint() + "]SvcCnt[" + appformReqDto.getUsePointSvcCntrNo() + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            throw new McpCommonJsonException("1006", ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION);
        }


        CustPointDto custPoint = null;
        //포인트 정보 확인
        if (userSessionDto != null && Constants.DIVISION_CODE_LEGALLY_MEMBER.equals(userSessionDto.getUserDivision())
                && !StringUtils.isBlank(appformReqDto.getUsePoint()) && !StringUtils.isBlank(appformReqDto.getUsePointSvcCntrNo())) {

            int usePointInt = appformReqDto.getUsePointInt();

            if (usePointInt < 1) {
                appformReqDto.setUsePoint("0");
                appformReqDto.setUsePointSvcCntrNo("");
            } else {

                //서비스 번호 검증
                String usePointSvcCntrNo = appformReqDto.getUsePointSvcCntrNo();
                Boolean isOwn = false;
                List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSessionDto.getUserId());
                for (McpUserCntrMngDto userCntrMng : cntrList) {
                    if (userCntrMng.getSvcCntrNo().equals(usePointSvcCntrNo)) {
                        isOwn = true;
                        break;
                    }
                }

                if (!isOwn) {
                    throw new McpCommonJsonException("1001", ExceptionMsgConstant.POINT_OWN_EXCEPTION);
                }

                //포인트 정보 조회
                custPoint = myBenefitService.selectCustPoint(usePointSvcCntrNo);
                if (custPoint == null) {
                    throw new McpCommonJsonException("1002", ExceptionMsgConstant.POINT_NULL_EXCEPTION);
                }

                int remainPoint = custPoint.getTotRemainPoint();

                if (usePointInt > remainPoint) {
                    throw new McpCommonJsonException("1003", ExceptionMsgConstant.POINT_OVER_EXCEPTION);
                }

                if ("Y".equals(phoneProdBas.getSesplsYn())) {
                    //자급제.. 폰
                    int selPricInt = 0;

                    try {
                        selPricInt = Integer.parseInt(appformReqDto.getHndsetSalePrice());
                    } catch (NumberFormatException e) {
                        throw new McpCommonJsonException("1004", ExceptionMsgConstant.POINT_PHONE_PRICE_EXCEPTION);
                    }

                    if (usePointInt > selPricInt) {
                        throw new McpCommonJsonException("1004", ExceptionMsgConstant.POINT_PROD_OVER_EXCEPTION);
                    }
                } else {
                    //단말기
                    int modelInstallment = (int) appformReqDto.getModelInstallment();     //할부원금(VAT포함)
                    if (usePointInt > modelInstallment) {
                        throw new McpCommonJsonException("1005", ExceptionMsgConstant.POINT_PROD_OVER_EXCEPTION);
                    }
                }
            }
        } else {
            appformReqDto.setUsePoint("0");
            appformReqDto.setUsePointSvcCntrNo("");
        }

        appformReqDto.setRip(ipstatisticService.getClientIp());


        //2. 정보 설정
        //접점코드로 대리점 코드 조회
        String agentCode = "";

        try {
            agentCode = appformSvc.getAgentCode(appformReqDto.getCntpntShopId());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        appformReqDto.setAgentCode(agentCode);

        if (StringUtils.isBlank(appformReqDto.getAgentCode())) {
            appformReqDto.setAgentCode(AGENT_DEFALUT_CODE);
        }

        if (StringUtils.isBlank(appformReqDto.getShopCd())) {
            appformReqDto.setShopCd(appformReqDto.getCntpntShopId());
        }

        if ("02".equals(appformReqDto.getDlvryType())) {
            //바로 배송이면 면제
            appformReqDto.setUsimPayMthdCd("1");
            appformReqDto.setPstate("20");  //20[관리자삭제] 초기 등록
        }

        String cpntId = SessionUtils.getFathSession().getCpntId();
        if (StringUtils.isEmpty(cpntId)) {
            appformReqDto.setCpntId(appformReqDto.getCntpntShopId());
        } else {
            appformReqDto.setCpntId(cpntId);
        }
        AppformReqDto rtnAppformReqDto = null;

        try {

            /* 오프라인 평생할인 프로모션 ID 찾기*/
            String disPrmtId = appformSvc.getDisPrmtId(appformReqDto);
            if(!StringUtils.isBlank(disPrmtId)) appformReqDto.setPrmtId(disPrmtId);

            // ============ STEP START ============
            // 1. 번호이동인 경우, 번호이동 전화번호가 KT MMOBILE 회선인지 다시체크
            // ** esimWatch는 신규가입만 가능 > 이어하기 시 번호이동도 선택할 수 있으나 신청폼은 신규가입폼... 임시로 번호이동 검사에서 제외처리
            String esimWatchYn = ("09".equals(appformReqDto.getUsimKindsCd()) && !StringUtil.isBlank(appformReqDto.getPrntsContractNo())) ? "Y" : "N"; //esimWatch
            if(OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType()) && !"Y".equals(esimWatchYn)){
                if(StringUtils.isBlank(appformReqDto.getMoveMobileFn())
                        || StringUtils.isBlank(appformReqDto.getMoveMobileMn())
                        || StringUtils.isBlank(appformReqDto.getMoveMobileRn())){
                    throw new McpCommonJsonException("AUTH01", BIDING_EXCEPTION);
                }

                RestTemplate restTemplate = new RestTemplate();
                int rtnInt = restTemplate.postForObject(apiInterfaceServer + "/msp/juoSubIngoCount", appformReqDto.getMoveMobileFn() + appformReqDto.getMoveMobileMn() + appformReqDto.getMoveMobileRn(), Integer.class);
                if(rtnInt >= 1) throw new McpCommonJsonException("AUTH01", STEP_AUTH_MOVE_EXCEPTION);
            }

            // 2. crt 페이지세션 재설정 (대리점은 operType을 마지막까지 바꿀 수 있음)
            String crtPageNm= StringUtil.NVL(SessionUtils.getPageSession(), "");
            if(crtPageNm.indexOf("AgentForm") > -1){

                StringBuilder pageSb= new StringBuilder();

                if(OPER_TYPE_NEW.equals(appformReqDto.getOperType())) pageSb.append("Nac");
                else if(OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType())) pageSb.append("Mnp");
                else if(OPER_TYPE_CHANGE.equals(appformReqDto.getOperType())) pageSb.append("Hcn");
                else if(OPER_TYPE_EXCHANGE.equals(appformReqDto.getOperType())) pageSb.append("Hdn");

                pageSb.append("AgentForm");
                String newPageNm= pageSb.toString();

                if(!crtPageNm.equals(newPageNm)){
                    SessionUtils.setPageSession(newPageNm);
                    certService.updateCrtReferer();
                }
            }

            // 3. 계좌인증하지않고 신용카드 인증하는 경우
            if(!"D".equals(appformReqDto.getReqPayType())){
                // 계좌인증 관련 스텝 초기화
                if(0 < certService.getModuTypeStepCnt("account", "")){
                    String ncType= (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) ? "1" : "";

                    CertDto certDto = new CertDto();
                    certDto.setModuType("account");
                    certDto.setCompType("G");
                    certDto.setNcType(ncType);
                    certService.getCertInfo(certDto);
                }
            }

            // 신용카드 인증하지 않고 계좌인증 하는 경우
            if(!"C".equals(appformReqDto.getReqPayType())){
                // 신용카드 인증 관련 스텝 초기화
                if(0 < certService.getModuTypeStepCnt("card", "")){
                    String ncType= (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) ? "1" : "";

                    CertDto certDto = new CertDto();
                    certDto.setModuType("card");
                    certDto.setCompType("G");
                    certDto.setNcType(ncType);
                    certService.getCertInfo(certDto);
                }
            }

            // 4. 신청서 최종 정보 체크
            Map<String, String> crtResult= null;
            String pageNm= SessionUtils.getPageSession();

            if(!StringUtil.isEmpty(pageNm) && pageNm.contains("Self") && !"Y".equals(appformReqDto.getTelAdvice())){
                // 4-1. 셀프개통의 자동해피콜(본인인증이 필수인 온라인 서식지) : esimWatch 호환을 위해 onOffType으로 구분하지 않음
                crtResult= appformSvc.crtUpdateSimpleAppFormInfo(appformReqDto);
            }else{
                // 4-2. 이외
                crtResult= appformSvc.crtSaveAppFormInfo(appformReqDto);
            }

            if(!AJAX_SUCCESS.equals(crtResult.get("RESULT_CODE"))){
                throw new McpCommonJsonException("STEP01", crtResult.get("RESULT_DESC"));
            }
            // ============ STEP END ============

            rtnAppformReqDto = appformSvc.saveAppform(appformReqDto, custPoint, giftPromotionBasList);
        } catch(McpCommonJsonException e){
            // STEP 또는 AUTH 오류 시 리턴값 설정
            rtnMap.put("RESULT_CODE", e.getRtnCode());
            rtnMap.put("RESULT_MSG", e.getErrorMsg());
            return rtnMap;
        } catch(DataAccessException e) {
            logger.error(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }


        if (rtnAppformReqDto != null) {
            //SMS 전송 처리 ...
            List<NmcpCdDtlDto> cntpntShopIdList = NmcpServiceUtils.getCodeList(Constants.SEND_LMS_CNTPNT_GROP_CODE);//SMS발송 접점코드

            /*
             * 바로배송 제외 처리
             * 즉시결제 제외  appformReqDto.setSettlWayCd("01");
             */
            if (cntpntShopIdList != null && !"02".equals(appformReqDto.getDlvryType()) && !"01".equals(appformReqDto.getSettlWayCd())) { //
                for (NmcpCdDtlDto param : cntpntShopIdList) {
                    if (param.getDtlCd().equals(appformReqDto.getCntpntShopId())) {

                        try {
                            String rMobile = rtnAppformReqDto.getCstmrMobileFn() + rtnAppformReqDto.getCstmrMobileMn() + rtnAppformReqDto.getCstmrMobileRn();
                            MspSmsTemplateMstDto mspSmsTemplateMstDto = null;

                            if (!OPER_TYPE_CHANGE.equals(appformReqDto.getOperType()) && !OPER_TYPE_EXCHANGE.equals(appformReqDto.getOperType())) {
                                mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_TEMPLATE_ID_NEW);
                            } else {
                                mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_TEMPLATE_ID_CHAGNE);
                            }

                            if (mspSmsTemplateMstDto != null) {
                                mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{resNo}", rtnAppformReqDto.getResNo()));
                                //smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMobile, mspSmsTemplateMstDto.getText(), mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                                smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMobile, mspSmsTemplateMstDto.getText(),
                                        mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                        Constants.KAKAO_SENDER_KEY, String.valueOf(mspSmsTemplateMstDto.getTemplateId()));

                                //청소년 법정대리인에서도 SMS전송
                                if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
                                    String rMinorMobile = rtnAppformReqDto.getMinorAgentTelFn() + rtnAppformReqDto.getMinorAgentTelMn() + rtnAppformReqDto.getMinorAgentTelRn();
                                    if (StringUtil.checkMobile(rMinorMobile)) {
                                        //smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMinorMobile, mspSmsTemplateMstDto.getText(), mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(), Constants.KAKAO_SENDER_KEY);
                                        smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMinorMobile, mspSmsTemplateMstDto.getText(),
                                                mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                                Constants.KAKAO_SENDER_KEY, String.valueOf(mspSmsTemplateMstDto.getTemplateId()));
                                    }
                                }
                            }
                            break;
                        }  catch (RestClientException e) {
                            throw new McpCommonJsonException("API 연동 오류");
                        }   catch (Exception e) {        //예외 전환 처리
                            throw new McpCommonJsonException(COMMON_EXCEPTION);
                        }
                    }
                }
            }

            // 위탁 서식지로 들어온 경우 담당자문자발송
            List<NmcpCdDtlDto> nmcpCdDtlDtoList = NmcpServiceUtils.getCodeList(Constants.SEND_SMS_CNTPNT_AGENT_CODE);//SMS발송 대상 접점코드
            for (NmcpCdDtlDto dto : nmcpCdDtlDtoList) {
                String smsAgentCode = StringUtil.NVL(dto.getDtlCd(), "");
                String cntPntMobileNo = dto.getExpnsnStrVal2();
                if (smsAgentCode.equals(agentCode)) {

                    try {
                        MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
                        mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_CNTPNT_TEMPLATE_ID);
                        if (mspSmsTemplateMstDto != null) {

                            String operType = "";
                            if (OPER_TYPE_NEW.equals(appformReqDto.getOperType())) {
                                operType = "[신규]";
                            } else if (Constants.OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType())) {
                                operType = "[번호이동]";
                            }

                            Date today = new Date();
                            String strToday = DateTimeUtil.changeFormat(today, "yy년 MM월dd일 HH시mm분");
                            String name = MaskingUtil.getMaskedName(StringUtil.NVL(appformReqDto.getCstmrName(), ""));
                            String text = mspSmsTemplateMstDto.getText();
                            StringBuffer str = new StringBuffer();
                            str = str.append(operType).append(" ").append(strToday);
                            String smsMsg = String.format(text, str.toString(), name);
                            //smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), cntPntMobileNo, smsMsg, mspSmsTemplateMstDto.getCallback());
                            smsSvc.sendLms(mspSmsTemplateMstDto.getSubject(), cntPntMobileNo, smsMsg,
                                    mspSmsTemplateMstDto.getCallback(),String.valueOf(Constants.SMS_CNTPNT_TEMPLATE_ID),"SYSTEM");
                        }
                    } catch (Exception e) {
                        break;
                    }
                    break;
                }
            }
            // 위탁 서식지로 들어온 경우 담당자문자발송 끝


            SessionUtils.saveNiceRes(null);
            // 완료페이지 URL 진입 시 본인인증 ci값과 신청정보의 ci값 추가 비교를 위해, 임시 저장
            SessionUtils.saveNiceReqTmp(sessNiceRes);

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            //등록자 아이피 저장 ...
            //ipstatisticService.insertIpStat(request,rtnAppformReqDto.getResNo());
            /*
            SELECT * FROM MCP_ACCESS_TRACE
            WHERE  USER_ID  = '1306318';
            222.107.133.150     /appform/saveAppformDbAjax.do   1306318     20181222    2018/12/22 23:14:19
            */
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("SAVE_APP_FORM");
            mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + rtnAppformReqDto.getRequestKey() + "][" + rtnAppformReqDto.getResNo() + "]");
            mcpIpStatisticDto.setParameter(rtnAppformReqDto.getRequestKey() + "");
            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);


            //CPA이벤트 -S-
            if (StringUtils.isNotEmpty(appformReqDto.getOhvalue())) {
                NmcpEventLogDto nmcpEventLogDto = new NmcpEventLogDto();
                nmcpEventLogDto.setEventCd(Constants.CPA_EVENT_CD);
                nmcpEventLogDto.setEventVal(appformReqDto.getOhvalue());
                nmcpEventLogDto.setEventStatus(Constants.CPA_COMPLETE_EVENT_STATUS);
                nmcpEventLogDto.setEventResult(rtnAppformReqDto.getResNo());
                //coEventSvc.insertNmcpEventLog(nmcpEventLogDto);
            }
            //CPA이벤트 -E-

        } else {
            rtnMap.put("RESULT_CODE", "0001");
        }
        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : SCAN 서버 전송
     * @param appformReqDto
     * @return
     * @return: List<MspSaleSubsdMstDto>
     * </pre>
     */
    @RequestMapping(value = "/appform/sendScanAjax.do")
    @ResponseBody
    public Map<String, Object> sendScan(AppformReqDto appformReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        String smsMsg = "";

        //SCAN 서버에 서식지 데이터 전송여부 조회
        //2. 서직지 정보 패치
        McpRequestDto mcpRequest = appformSvc.getMcpRequest(appformReqDto.getRequestKey());
        if (mcpRequest == null || mcpRequest.getAppFormXmlYn() == null) {
            rtnMap.put("RESULT_MSG", "신청서 정보가 없습니다. ");
            rtnMap.put("RESULT_CODE", "FAIL");
            return rtnMap;
        }

        String appFormXmlYn = mcpRequest.getAppFormXmlYn();

        if (appFormXmlYn != null && "N".equals(appFormXmlYn) && !PRE_APPROVAL_PSTATE_CODE.equals(mcpRequest.getPstate())) {
            try {

                //3. SCAN 서버에 서식지 데이터 생성 및 전송
                scanSvc.prodSendScan(appformReqDto.getRequestKey(), appformReqDto.getCretId());

                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("SEND_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + appformReqDto.getRequestKey() + "]");
                mcpIpStatisticDto.setParameter(appformReqDto.getRequestKey() + "");
                mcpIpStatisticDto.setTrtmRsltSmst("SUCCESS");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } catch (Exception e) {        //예외 전환 처리


                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("SEND_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + appformReqDto.getRequestKey() + "]");
                mcpIpStatisticDto.setParameter(appformReqDto.getRequestKey() + "");
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                String referer = ipstatisticService.getReferer();
                //관리자가 클릭한것 제외 처리
                if (!"Y".equals(appformReqDto.getPrdtNm())
                        && "|양우철|양은휼|김일환|김세희|김지영|박성훈".indexOf(appformReqDto.getCstmrName()) == -1
                        && referer.indexOf("mcpstg") == -1) {
                    //관리자 에서 SMS 전송 처리 ...
                    List<NmcpCdDtlDto> smsList = NmcpServiceUtils.getCodeList(Constants.SEND_SMS_MNG_GROP_CODE);
                    if (smsList != null) {
                        for (NmcpCdDtlDto param : smsList) {
                            try {
                                smsMsg = "[" + appformReqDto.getRequestKey() + "][" + appformReqDto.getCstmrName() + "] SCAN 이미지 생성 실패 https://www.ktmmobile.com/appform/sendScanAjax.do?requestKey=" + appformReqDto.getRequestKey() + "&prdtNm=Y ";
                                //TO_DO 개발해야 함
                                //smsSvc.sendLms( "SCAN 서버 확인", param.getDtlCd(),smsMsg,"18995000") ;
                            } catch (Exception e2) {        //예외 전환 처리
                                logger.error(e2.getMessage());
                            }
                        }
                    }
                }
            }
        } else {
            if (appFormXmlYn != null && "Y".equals(appFormXmlYn)) {
                rtnMap.put("RESULT_MSG", "이미 생성된 서식지입니다.");
            } else if (PRE_APPROVAL_PSTATE_CODE.equals(mcpRequest.getPstate())) {
                rtnMap.put("RESULT_MSG", "미승인PCODE[99]  서식지입니다.");
            }
            rtnMap.put("RESULT_CODE", "FAIL22");
        }
        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 개통사전 체크 요청 - 셀프개통 휴대폰 인증 정보확인
     * @param appformReqDto
     * @return: Map<String, Object>
     * 날짜     : 2020. 12. 15.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/appform/reqPreOpenCheckAjax.do")
    @ResponseBody
    public Map<String, Object> reqPreOpenCheck(
            AppformReqDto appformReqDto
            , @RequestParam(required = false, defaultValue = "") String resUniqId      //계좌점유 키값 (PASS)
            , @RequestParam(required = false, defaultValue = "") String globalNoNp1    //NP1 GLOBAL NO
            , @RequestParam(required = false, defaultValue = "") String globalNoNp3    //NP3 GLOBAL NO
    ) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        AppformReqDto rtnAppformReqDto = null;
        NiceLogDto smsNiceLogDto= null;  // 010셀프개통대상 해피콜 처리


        //개통사전체크요청 확인
        AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession();
        if (sessAppformReqDto == null) {

            // 010 신규 유심 셀프개통인 경우, 동일아이피 차단 확인
            if(OPER_TYPE_NEW.equals(appformReqDto.getOperType()) && !"09".equals(appformReqDto.getUsimKindsCd())){
                int nacSelfIp = appformSvc.getNacSelfCount();
                if(nacSelfIp > 0){
                    throw new McpCommonJsonException("IP_FAIL", NAC_SELF_IP_EXCEPTION);
                }
            }

            //동일명의 회선 90일 이내에에 개통/개통취소 이력이 10회
            int limitReqFormCnt = 100;

            String limitReqFormCntStr = NmcpServiceUtils.getCodeNm("Constant", "LimitReqFormCnt");
            if (limitReqFormCntStr != null && !limitReqFormCntStr.equals("")) {
                try {
                    limitReqFormCnt = Integer.parseInt(limitReqFormCntStr);
                } catch (NumberFormatException e) {
                    limitReqFormCnt = 100;
                }
            }

            /*
             * 100 이상이면 체크 하지 않음
             */
            int checkLimitOpenFormCount = -1;
            if (limitReqFormCnt < 100) {
                RestTemplate restTemplate = new RestTemplate();

                try {
                    checkLimitOpenFormCount = restTemplate.postForObject(apiInterfaceServer + "/appform/checkLimitOpenFormCount", appformReqDto, Integer.class);
                } catch (RestClientException e) {
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                    mcpIpStatisticDto.setPrcsSbst("9996[RestClientException] ");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    throw new McpCommonJsonException("9996", "API연동 오류 /appform/checkLimitOpenFormCount");
                }  catch (Exception e) {

                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                    mcpIpStatisticDto.setPrcsSbst("9996[Exception] ");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    throw new McpCommonJsonException("9996", "API연동 오류 /appform/checkLimitOpenFormCount");
                }


            }

            if (checkLimitOpenFormCount >= limitReqFormCnt) {
                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("FORM_OPEN_LIMIT");
                mcpIpStatisticDto.setPrcsSbst("fCnt[" + checkLimitOpenFormCount + "]lCnt[" + limitReqFormCnt + "]");
                mcpIpStatisticDto.setParameter("COUNT[" + checkLimitOpenFormCount + "]CUSTOMER_SSN[" + appformReqDto.getCstmrNativeRrn() + "]CSTMR_FOREIGNER_RRN[" + appformReqDto.getCstmrForeignerRrn() + "]");
                mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                rtnMap.put("OSST_RESULT_CODE", "-1");
                rtnMap.put("ERROR_NE_MSG", OVER_LIMIT_OPEN_FORM_EXCEPTION);
                return rtnMap;
            }

            //eSIM 정보 확인 및 설정
            fnSetDataOfeSim(appformReqDto);
            // 2025-05-19, 부정사용주장 단말 확인, 개발팀 김동혁
            if (esimSvc.checkAbuseImeiList(Arrays.asList(appformReqDto.getImei1(), appformReqDto.getImei2()))) {
                throw new McpCommonJsonException("9901", ESIM_SELF_ABUSE_IMEI_EXCEPTION);
            }

            //1. 인증 정보 확인
            String autCstmrNm = "";

            if (CSTMR_TYPE_NA.equals(appformReqDto.getCstmrType())) {
                //내국인 성인 인증
                int age = NmcpServiceUtils.getAge(appformReqDto.getDesCstmrNativeRrn(), new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
                if (19 > age) {
                    rtnMap.put("RESULT_CODE", "-1");
                    rtnMap.put("ERROR_NE_MSG", REGNO_ADULT_EXCEPTION);
                    return rtnMap;
                }
                autCstmrNm = appformReqDto.getCstmrName();
            } else {
                autCstmrNm = appformReqDto.getCstmrName();
            }

            //1-3. 본인인증 확인
            NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

            //PASS + 계좌인증
            String dtoAuthType = StringUtil.NVL(appformReqDto.getOnlineAuthType(), "");
            if ("A".equals(dtoAuthType) && sessNiceRes != null) {
                //PASS + 계좌인증 검증
                sessNiceRes.setAuthType("A");  //검증 통과 하기 위해
            }

            //외국인 테스트 때문에 주석 처리
            if (sessNiceRes == null
                    || !sessNiceRes.getAuthType().equals(dtoAuthType)
                    || !sessNiceRes.getName().equals(autCstmrNm)
                    || sessNiceRes.getBirthDate().indexOf(appformReqDto.getBirthDate()) < 0){

                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                mcpIpStatisticDto.setPrcsSbst("0003[NICE_CERT_EXCEPTION] ");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
            }

            // usim 또는 esim 신규셀프개통 휴대폰 인증 정보확인
            if(StringUtil.isEmpty(appformReqDto.getPrntsContractNo())
                    && Constants.OPER_TYPE_NEW.equals(appformReqDto.getOperType())){

                // sms 인증 DB 조회
                if(StringUtil.isEmpty(appformReqDto.getReqSeq()) || StringUtil.isEmpty(appformReqDto.getResSeq())){
                    throw new McpCommonJsonException("0004", NICE_CERT_EXCEPTION);
                }

                NiceLogDto niceLogDto= new NiceLogDto();
                niceLogDto.setReqSeq(appformReqDto.getReqSeq());
                niceLogDto.setResSeq(appformReqDto.getResSeq());
                niceLogDto.setLimitMinute(90);  // 90분 이내의 이력 조회
                smsNiceLogDto = nicelog.getMcpNiceHistWithTime(niceLogDto);

                if(smsNiceLogDto == null){
                    throw new McpCommonJsonException("0004", NICE_CERT_EXCEPTION);
                }
            }

            appformReqDto.setOnlineAuthInfo("ReqNo:" + sessNiceRes.getReqSeq() + ", ResNo:" + sessNiceRes.getResSeq());
            appformReqDto.setSelfCstmrCi(sessNiceRes.getConnInfo());
            appformReqDto.setRip(ipstatisticService.getClientIp());

            //셀프개통 시 동일 명의의 경우 30일 이내 이력 확인 시 차단
            //기존 본인인증시 스크립트에서 체크하나 이후 사전체크 ,개통요청 시 한번 더 체크
            AppformReqDto rtnAppformReq = appformSvc.getLimitForm(appformReqDto);
            if(rtnAppformReq != null) {
                throw new McpCommonJsonException("9902", SELF_LIMIT_EXCEPTION);
            }

            //회원 아이디 설정
            UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
            if (userSessionDto != null) {
                appformReqDto.setCretId(userSessionDto.getUserId());
            } else {
                appformReqDto.setCretId(NON_MEMBER_ID);
            }

            //유심비 /  가입비 설정
            String cntpntShopId = appformReqDto.getCntpntShopId();

            UsimBasDto usimBasDtoParm = new UsimBasDto();
            usimBasDtoParm.setOrgnId(cntpntShopId);
            usimBasDtoParm.setOperType(appformReqDto.getOperTypeSmall());
            usimBasDtoParm.setDataType(appformReqDto.getPrdtSctnCd());
            usimBasDtoParm.setRateCd(appformReqDto.getSocCode());
            //eSIM 처리
            if ("09".equals(appformReqDto.getUsimKindsCd())) {
                //eSIM
                usimBasDtoParm.setPrdtIndCd("10");
            } else {
                usimBasDtoParm.setPrdtIndCd(appformReqDto.getUsimKindsCd());
            }

            Map<String, String> simInfoMap = usimService.getSimInfo(usimBasDtoParm);
            int intJoinPrice = Integer.parseInt(simInfoMap.get("JOIN_PRICE"));
            int intUsimPrice = Integer.parseInt(simInfoMap.get("SIM_PRICE"));

            //eSIM 처리
            if ("09".equals(appformReqDto.getUsimKindsCd()) || "11".equals(appformReqDto.getUsimKindsCd())) {
                //eSIM
                appformReqDto.setUsimPayMthdCd("3");
                appformReqDto.setUsimPrice(intUsimPrice);
            } else {
                appformReqDto.setUsimPayMthdCd("1");
                appformReqDto.setUsimPrice(0);
            }


            /** 가입비 납부방법
             * 1 면제
             * 2 일시납
             * 3 3개월분납
             * 22년... 8월 23일 .. 세희 과장님 하고.. 통화 정리 함..
             * 고객에서 면제 라고 표현 하고 실제로.. M모바일에서 대납 처리 함....
             * 셀프개통은... 1[면제로] 처리
             */
            if ("N".equals(simInfoMap.get("JOIN_IS_PAY"))) {
                appformReqDto.setJoinPayMthdCd("1");
                appformReqDto.setJoinPrice(0);
            } else {
                //가입비가 0원이면 면제 아니면 3개월 분납
                if (intJoinPrice > 0) {
                    appformReqDto.setJoinPayMthdCd("3");
                    appformReqDto.setJoinPrice(intJoinPrice);
                } else {
                    appformReqDto.setJoinPayMthdCd("1");
                    appformReqDto.setJoinPrice(0);
                }
            }

            if (REQ_BUY_TYPE_USIM.equals(appformReqDto.getReqBuyType()) && !orgnId.equals(cntpntShopId)  ) {
                NmcpCdDtlDto nmcpCdOnlineDto = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_OFF_ONLINE_LIST, appformReqDto.getCntpntShopId());
                if (nmcpCdOnlineDto != null && !StringUtils.isBlank(nmcpCdOnlineDto.getExpnsnStrVal1())) {
                    appformReqDto.setCntpntShopId(nmcpCdOnlineDto.getExpnsnStrVal1());
                    appformReqDto.setOpenMarketReferer(nmcpCdOnlineDto.getExpnsnStrVal2());
                }
            }


            //정책에서 할인요금 조회에서 등록
            if (!StringUtils.isBlank(appformReqDto.getModelSalePolicyCode())) {
                MspSaleSubsdMstDto mspSaleSubsdMstDto = new MspSaleSubsdMstDto();
                mspSaleSubsdMstDto.setForFrontFastYn("Y");
                mspSaleSubsdMstDto.setSalePlcyCd(appformReqDto.getModelSalePolicyCode()); //정책코드
                mspSaleSubsdMstDto.setPrdtId(appformReqDto.getModelId());  //대표상품 아이디
                mspSaleSubsdMstDto.setOldYn("N");
                mspSaleSubsdMstDto.setOrgnId(appformReqDto.getCntpntShopId());
                mspSaleSubsdMstDto.setOperType(appformReqDto.getOperType());//가입유형
                mspSaleSubsdMstDto.setNoArgmYn("Y");
                mspSaleSubsdMstDto.setAgrmTrm(String.valueOf(appformReqDto.getEnggMnthCnt()));//입력받은 할부기간을 약정기간
                mspSaleSubsdMstDto.setRateCd(appformReqDto.getSocCode());//요금제코드

                RestTemplate restTemplate2 = new RestTemplate();
                MspSaleSubsdMstDto[] chargeTemList = restTemplate2.postForObject(apiInterfaceServer + "/msp/mspSaleSubsdMstList", mspSaleSubsdMstDto, MspSaleSubsdMstDto[].class);
                List<MspSaleSubsdMstDto> chargeList = Arrays.asList(chargeTemList);

                if (chargeList != null && chargeList.size() > 0) {
                    MspSaleSubsdMstDto saleSubsdMst = chargeList.get(0);
                    appformReqDto.setDcAmt(saleSubsdMst.getDcAmt());  //기본할인금액
                    appformReqDto.setAddDcAmt(saleSubsdMst.getAddDcAmt());  //추가할인금액
                    appformReqDto.setSprtTp(saleSubsdMst.getSprtTp());  //지원금유형
                }
            }

            //2. 정보 설정
            //접점코드로 대리점 코드 조회
            String agentCode = "";
            agentCode = appformSvc.getAgentCode(appformReqDto.getCntpntShopId());

            if (StringUtils.isBlank(agentCode)) {
                rtnMap.put("RESULT_CODE", "-1");
                rtnMap.put("ERROR_NE_MSG", "대리점 코드가 존재 하지 않습니다.");
                return rtnMap;
            } else {
                appformReqDto.setAgentCode(agentCode);
            }
            appformReqDto.setProdType(PhoneConstant.PROD_TYPE_COMMON);
            appformReqDto.setManagerCode("M0001");
            appformReqDto.setRid("-");
            appformReqDto.setViewFlag("Y");
            appformReqDto.setRequestStateCode("00");

            if (StringUtils.isBlank(appformReqDto.getShopCd())) {
                appformReqDto.setShopCd(appformReqDto.getCntpntShopId());
            }

            if (Constants.OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType())) {
                //공통코드 expnsnStrVal3 값이 Y 일때 분납지속(LMS미청구) AD2로 저장 또는
                //기본값은 분납지속(LMS청구)(AD) 저장 처리
                NmcpCdDtlDto moveCompanyObj = NmcpServiceUtils.getCodeNmDto(Constants.WIRE_SERVICE_CODE, appformReqDto.getMoveCompany());
                if (moveCompanyObj != null) {
                    if ("Y".equals(moveCompanyObj.getExpnsnStrVal3())) {
                        appformReqDto.setMoveAllotmentStat(Constants.MOVE_ALLOTMENT_STAT_CODE2);
                    } else {
                        appformReqDto.setMoveAllotmentStat(Constants.MOVE_ALLOTMENT_STAT_CODE1);
                    }
                }
            }

            /**
             * ㅇ 이슈사항 : M전산에서 스마트 워치 직접개통(OSST개통) 시 하기와 같은 alert 발생             *
             * ㅇ 발생 오류코드 : 3107  [DEFKTF] 상품은 [애플워치 단말기에서는 휴대폰결제 비밀번호서비스 부가서비스를 가입할 수 없습니다.]사유로 가입이 불가 합니다.
             * ㅇ 확인된 원인
             *  1) PC0(사전체크) 전문의 하기 두 값이 Y로 연동 될 경우 MP에서 '휴대폰결제 비밀번호서비스(MPAYPSSWD)를 자동으로 가입 시킴.
             *  2) 휴대폰결제 비밀번호서비스(MPAYPSSWD) 부가서비스와 스마트워치는 베타관계로, 부가서비스로 자동 가입으로 인한 개통 실패 발생
             * ※ 현재 포탈에서 신청서 작성 시 하기 값을 Null값으로 셋팅하고 있으나 M전산에서는 하기 값이 Null일 경우 "Y"로 신청서 생성 중
             * 셀프 개통 이동전화결제이용동의여부 무조건 N으로 설정
             */
            appformReqDto.setPhonePayment("N");

            /* 직영 평생할인 프로모션 ID 가져오기 */
            String prmtId = appformSvc.getChrgPrmtId(appformReqDto);
            if(!StringUtils.isBlank(prmtId)) appformReqDto.setPrmtId(prmtId);

            String cpntId = SessionUtils.getFathSession().getCpntId();
            if (StringUtils.isEmpty(cpntId)) {
                appformReqDto.setCpntId(appformReqDto.getCntpntShopId());
            } else {
                appformReqDto.setCpntId(cpntId);
            }

            // (번이) 사전동의 예외 통신사인 경우 NP를 호출하지 않기 때문에 [번호이동전화번호] 값을 가진 본인인증 STEP이 누락됨
            //        → 사전체크 시도 시 해당 STEP 추가
            String moveMobileNo = appformReqDto.getMoveMobileFn() + appformReqDto.getMoveMobileMn() + appformReqDto.getMoveMobileRn();

            if(Constants.OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType())){
                NmcpCdDtlDto npNscException = NmcpServiceUtils.getCodeNmDto(NP_NSC_EXCEPTION, appformReqDto.getMpCode());
                if(npNscException != null){
                    String[] certKey= new String[]{"urlType", "mobileNo"};
                    String[] certValue= new String[]{"reqNpForm", moveMobileNo};
                    certService.vdlCertInfo("E", certKey, certValue);
                }
            }

            //if (!"LOCAL".equals(serverName)) {
                // ============ STEP START ============
                // 신청서 사전체크 정보 확인
                Map<String, String> crtResult= appformSvc.crtSaveSimpleAppFormInfo(appformReqDto);

                if(!AJAX_SUCCESS.equals(crtResult.get("RESULT_CODE"))){
                    rtnMap.put("RESULT_CODE", "STEP01");
                    rtnMap.put("ERROR_NE_MSG", crtResult.get("RESULT_DESC"));
                    return rtnMap;
                }
                // ============ STEP END ============
            //}
            //TO_DO확인

            // (번이) 동일 번호이동 휴대폰번호로 사전체크 시도 이력 존재시 → 실패처리
            if(Constants.OPER_TYPE_MOVE_NUM.equals(appformReqDto.getOperType())){
                Map<String, Object> chkMap = appformSvc.mnpPreCheckLimit(moveMobileNo);
                if(!AJAX_SUCCESS.equals(chkMap.get("RESULT_CODE"))){
                    return chkMap;
                }
            }

            rtnAppformReqDto = appformSvc.saveSimpleAppform(appformReqDto);
            //저장 정보 Session 저장 처리
            rtnAppformReqDto.setNiceLogDto(smsNiceLogDto);
            SessionUtils.saveAppformDto(rtnAppformReqDto);
        } else {
            //기존에 신청한 정보가 있다.
            rtnAppformReqDto = SessionUtils.getAppformSession();

            // (번이) 동일 번호이동 휴대폰번호로 사전체크 시도 이력 존재시 → 실패처리
            if(Constants.OPER_TYPE_MOVE_NUM.equals(rtnAppformReqDto.getOperType())){
                String moveMobileNo = rtnAppformReqDto.getMoveMobileFn() + rtnAppformReqDto.getMoveMobileMn() + rtnAppformReqDto.getMoveMobileRn();
                Map<String, Object> chkMap = appformSvc.mnpPreCheckLimit(moveMobileNo);
                if(!AJAX_SUCCESS.equals(chkMap.get("RESULT_CODE"))){
                    return chkMap;
                }
            }

            // 2025-05-19, 부정사용주장 단말 확인, 개발팀 김동혁
            if (esimSvc.checkAbuseImeiList(Arrays.asList(rtnAppformReqDto.getImei1(), rtnAppformReqDto.getImei2()))) {
                throw new McpCommonJsonException("9901", ESIM_SELF_ABUSE_IMEI_EXCEPTION);
            }
        }



        // 사전동의 예외 통신사인 경우 사전동의 방어로직 통과처리
        if(OPER_TYPE_MOVE_NUM.equals(rtnAppformReqDto.getOperType())){
            NmcpCdDtlDto npNscException = NmcpServiceUtils.getCodeNmDto(NP_NSC_EXCEPTION, appformReqDto.getMpCode());
            if(npNscException == null){

                /*
                 * 번호 이동
                 * NP1 실행 여부 확인
                 * NP1 MVNO_ORD_NO GlobalId insert 처리
                 */
                if ("".equals(globalNoNp1) ) {
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                    mcpIpStatisticDto.setPrcsSbst("1001[NP1_NULL_EXCEPTION] ");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    throw new McpCommonJsonException("1001", "번호이동 사전동의 요청(NP1) 정보가 없습니다. ");
                } else {
                    McpRequestOsstDto mcpRequestOsst = new McpRequestOsstDto();
                    mcpRequestOsst.setMvnoOrdNo(rtnAppformReqDto.getResNo());
                    mcpRequestOsst.setNstepGlobalId(globalNoNp1);
                    mcpRequestOsst.setPrgrStatCd(EVENT_CODE_NP_PRE_CHECK);
                    mcpRequestOsst.setRsltCd(OSST_SUCCESS);
                    //NP1 MVNO_ORD_NO GlobalId insert 처리
                    appformSvc.insertMcpRequestOsst(mcpRequestOsst);
                }

                /*
                 * 번호 이동
                 * NP3 실행 여부 확인
                 * NP4 MVNO_ORD_NO GlobalId insert 처리
                 */
                if ("".equals(globalNoNp3) ) {
                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
                    mcpIpStatisticDto.setPrcsSbst("1002[NP3_NULL_EXCEPTION] ");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    throw new McpCommonJsonException("1001", "번호이동 사전동의 요청(NP1) 정보가 없습니다. ");
                } else {
                    McpRequestOsstDto mcpRequestOsst = new McpRequestOsstDto();
                    mcpRequestOsst.setMvnoOrdNo(rtnAppformReqDto.getResNo());
                    mcpRequestOsst.setNstepGlobalId(globalNoNp3);
                    mcpRequestOsst.setPrgrStatCd(EVENT_CODE_NP_ARREE);
                    mcpRequestOsst.setRsltCd(OSST_SUCCESS);
                    //NP1 MVNO_ORD_NO GlobalId insert 처리
                    appformSvc.insertMcpRequestOsst(mcpRequestOsst);
                }
            }
        }

        MSimpleOsstXmlVO simpleOsstXmlVO = null;

        /*
         * 방어 로직 추가
         * PC2  0000
         * EVENT_CODE 사전체크 및 고객생성 결과 확인(PC2) 존재하며
         */
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_PC_RESULT);
        //mcpRequestOsstDto.setRsltCd("0000");
        int tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

        if (tryCount > 0) {
            //성공 처리
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

            // 010셀프개통대상 해피콜 처리
            NiceLogDto niceLogRtnDto= rtnAppformReqDto.getNiceLogDto();
            if(niceLogRtnDto != null){
                niceLogRtnDto.setRequestKey(rtnAppformReqDto.getRequestKey()+"");
                nicelog.insertSelfSmsAuth(niceLogRtnDto);
            }

            return rtnMap;
        }


        //PC0 ITL_SST_E0002
        /*
         * 접수받은 MVNO 접수번호 가 있습니다.
         * 성공 처리
         */
        tryCount = 0;
        mcpRequestOsstDto.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK);
        mcpRequestOsstDto.setRsltCd("ITL_SST_E0002");
        tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

        if (tryCount > 0) {
            //성공 처리
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

            // 010셀프개통대상 해피콜 처리
            NiceLogDto niceLogRtnDto= rtnAppformReqDto.getNiceLogDto();
            if(niceLogRtnDto != null){
                niceLogRtnDto.setRequestKey(rtnAppformReqDto.getRequestKey()+"");
                nicelog.insertSelfSmsAuth(niceLogRtnDto);
            }

            return rtnMap;
        }


        ////사전체크 및 고객생성 요청(PC0)
        try {
            Thread.sleep(3000);
            simpleOsstXmlVO = appformSvc.sendOsstService(rtnAppformReqDto.getResNo(), Constants.EVENT_CODE_PRE_CHECK);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

                // 010셀프개통대상 해피콜 처리
                NiceLogDto niceLogRtnDto= rtnAppformReqDto.getNiceLogDto();
                if(niceLogRtnDto != null){
                    niceLogRtnDto.setRequestKey(rtnAppformReqDto.getRequestKey()+"");
                    nicelog.insertSelfSmsAuth(niceLogRtnDto);
                }

            } else {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("ERROR_NE_MSG", simpleOsstXmlVO.getResultCode());
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("OSST_RESULT_CODE", "-1");//이력 정보 저장 처리
            if ("LOCAL".equals(serverName)) {
                //테스트 용...
                rtnMap.put("OSST_RESULT_CODE", "ITL_SST_E1020_03");
            }
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            return rtnMap;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("OSST_RESULT_CODE", "-2");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");

            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            return rtnMap;
        } catch (SelfServiceException e) {
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();
            if ("ITL_SST_E1020".equals(resultCode) && message.contains("BF1039") ) {
                resultCode = "ITL_SST_E1020_01";
            } else if ("ITL_SST_E1020".equals(resultCode) && message.contains("BF2001") ) {
                resultCode = "ITL_SST_E1020_02";
            } else if ("ITL_SST_E1020".equals(resultCode) && message.contains("BS0000") ) {
                resultCode = "ITL_SST_E1020_03";
            }

            rtnMap.put("OSST_RESULT_CODE", resultCode);
            rtnMap.put("ERROR_NE_MSG", message);

            if ("LOCAL".equals(serverName)) {
                int searchCnt = 0;
                searchCnt = SessionUtils.getCntSession();
                SessionUtils.saveCntSession(++searchCnt);

                rtnMap.put("OSST_RESULT_CODE", "ITL_SST_E1020_03");
                //강제 성공 처리
                /*if (searchCnt > 1) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                    rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
                }*/
            }
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "-2");
            rtnMap.put("ERROR_MSG", "Exception");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요..");



            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("PC0_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(rtnAppformReqDto.getResNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[Exception] ");
            mcpIpStatisticDto.setParameter("RES_NO[" + rtnAppformReqDto.getResNo() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);


            return rtnMap;
        }

        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 셀프 개통 신청서 초기화 처리
     * @param
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/reqInitAjax.do")
    @ResponseBody
    public Map<String, Object> reqInitAjax(OsstReqDto osstReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        SessionUtils.saveAppformDto(null);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("REQUEST_MSG", "session 초기화 처리");


        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 번호이동 사전동의 요청
     * @param
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/reqNpPreCheckAjax.do")
    @ResponseBody
    public Map<String, Object> reqNpPreCheck(OsstReqDto osstReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //1-3. 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

        if (sessNiceRes == null) {
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[sessNiceRes NUll] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        // ============ STEP START ============
        // 이름, 생년월일
        String[] certKey= {"urlType", "name", "birthDate"};
        String[] certValue= {"chkNpForm", osstReqDto.getCustNm(), EncryptUtil.ace256Enc(osstReqDto.getCustIdntNo())};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("ERROR_NE_MSG", vldReslt.get("RESULT_DESC"));
            return rtnMap;
        }
        // ============ STEP END ============

        // (번이) 동일 번호이동 휴대폰번호로 사전체크 시도 이력 존재시 → 실패처리
        Map<String, Object> chkMap = appformSvc.mnpPreCheckLimit(osstReqDto.getNpTlphNo());
        if(!AJAX_SUCCESS.equals(chkMap.get("RESULT_CODE"))){
            return chkMap;
        }

        MSimpleOsstXmlVO simpleOsstXmlVO = null;

        ////번호이동 사전동의 요청(NP1)
        try {
            simpleOsstXmlVO = appformSvc.sendOsstService(osstReqDto, EVENT_CODE_NP_PRE_CHECK);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("GLOBAL_NO", simpleOsstXmlVO.getGlobalNo());

                // ============ STEP START ============
                certKey= new String[]{"urlType", "mobileNo"};
                certValue= new String[]{"reqNpForm", osstReqDto.getNpTlphNo()};
                certService.vdlCertInfo("C", certKey, certValue);
                // ============ STEP END ============

            } else {

                //2.세션 CERT_SEQ 가져오기
                long crtSeq = SessionUtils.getCertSession();
                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
                //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
                mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
                mcpIpStatisticDto.setPrcsSbst("Exception[simpleOsstXmlVO.isNotSuccess] ");
                mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            }
        } catch (McpMplatFormException e) {
            //이력 정보 저장 처리
            //2.세션 CERT_SEQ 가져오기
            long crtSeq = SessionUtils.getCertSession();
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);


            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
        } catch (SocketTimeoutException e) {
            //이력 정보 저장 처리
            long crtSeq = SessionUtils.getCertSession();
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP1_ERROR");
            //mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setTrtmRsltSmst(crtSeq + "");
            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            return rtnMap;

        } catch (SelfServiceException e) {

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();

            rtnMap.put("GLOBAL_NO", e.getGlobalNo());

            if ("ITL_SST_E1014".equals(resultCode)) {
                //성공처리
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

                // ============ STEP START ============
                certKey= new String[]{"urlType", "mobileNo"};
                certValue= new String[]{"reqNpForm", osstReqDto.getNpTlphNo()};
                certService.vdlCertInfo("C", certKey, certValue);
                // ============ STEP END ============

                return rtnMap;
            } else {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
            }

            //session에 저장한 서식지 정보 초기화
            SessionUtils.saveAppformDto(null);
            if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("가입제한자") > -1) {
                resultCode = "ITL_SST_E1018_01";
            } else if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("미납고객") > -1) {
                resultCode = "ITL_SST_E1018_02";
            }

            rtnMap.put("OSST_RESULT_CODE", resultCode);
            rtnMap.put("ERROR_NE_MSG", message);
            return rtnMap;
        }
        return rtnMap;
    }



    /**
     * <pre>
     * 설명     : 사전동의 결과조회(NP3)
     * @param
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/reqNpAgreeAjax.do")
    @ResponseBody
    public Map<String, Object> reqNpAgree(OsstReqDto osstReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //1-3. 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

        if (sessNiceRes == null) {
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[sessNiceRes NUll] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        MSimpleOsstXmlVO simpleOsstXmlVO = null;

        ////사전동의 결과조회(NP3)
        try {
            simpleOsstXmlVO = appformSvc.sendOsstService(osstReqDto, EVENT_CODE_NP_ARREE);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RSLT_CD", simpleOsstXmlVO.getRsltCd());
                rtnMap.put("RSLT_MSG", simpleOsstXmlVO.getRsltMsg());
                rtnMap.put("GLOBAL_NO", simpleOsstXmlVO.getGlobalNo());


                if ("LOCAL".equals(serverName)) {
                    rtnMap.put("RSLT_CD", "S");
                    rtnMap.put("GLOBAL_NO", "12345678");
                    return rtnMap;
                }
            } else {

                //2.세션 CERT_SEQ 가져오기
                long crtSeq = SessionUtils.getCertSession();
                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
                mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
                mcpIpStatisticDto.setPrcsSbst("Exception[simpleOsstXmlVO.isNotSuccess] ");
                mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            }
        } catch (McpMplatFormException e) {
            //이력 정보 저장 처리
            //2.세션 CERT_SEQ 가져오기
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[McpMplatFormException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);


            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
        } catch (SocketTimeoutException e) {
            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("NP3_ERROR");
            mcpIpStatisticDto.setTrtmRsltSmst(osstReqDto.getNpTlphNo());
            mcpIpStatisticDto.setPrcsSbst("Exception[SocketTimeoutException] ");
            mcpIpStatisticDto.setParameter("NpTlphN[" + osstReqDto.getNpTlphNo() + "],moveCompany[" + osstReqDto.getBchngNpCommCmpnCd() + "],cstmrType[" + osstReqDto.getCustTypeCd() + "]");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("ERROR_NE_MSG", "일시적으로 서비스 이용이 불가합니다. 잠시 후 다시 시도 해 주세요.");
            return rtnMap;

        } catch (SelfServiceException e) {

            if ("LOCAL".equals(serverName)) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("RSLT_CD", "S");
                rtnMap.put("RSLT_MSG", "성공");
                rtnMap.put("GLOBAL_NO", "12345678");
                return rtnMap;
            }

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();
            rtnMap.put("OSST_RESULT_CODE", resultCode);
            rtnMap.put("ERROR_NE_MSG", message);
            return rtnMap;
        }
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 납부 주장 요청- 사용하지 않음.
     * @param appformReqDto
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/reqPayOpnAjax.do")
    @ResponseBody
    public Map<String, Object> reqPayOpn(McpRequestMoveDto requestMoveDto, AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if ("LOCAL".equals(serverName)) {
            try {
                Thread.sleep(15 * 1000);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            } catch (InterruptedException e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

            // *********************** STG 환경 강제로 성공 처리 시작
        } else if ("STG".equals(serverName)) {
            try {
                Thread.sleep(15 * 1000);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            } catch (InterruptedException e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }
            // *********************** STG 환경 강제로 성공 처리 끝

        }

        requestMoveDto.setRequestKey(appformReqDto.getRequestKey());

        if (appformSvc.updateMcpRequestMove(requestMoveDto)) {
            MSimpleOsstXmlVO simpleOsstXmlVO = null;
            ////번호이동 사전동의 요청(NP2)
            try {
                Thread.sleep(3000);
                simpleOsstXmlVO = appformSvc.sendOsstService(appformReqDto.getResNo(), EVENT_CODE_NP_REQ_PAY);
                if (simpleOsstXmlVO.isSuccess()) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                    rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                }
            } catch (McpMplatFormException e) {
                rtnMap.put("RESULT_CODE", "9997");
                rtnMap.put("ERROR_MSG", "response massage is null.");
            } catch (SocketTimeoutException e) {
                rtnMap.put("RESULT_CODE", "9999");
                rtnMap.put("ERROR_MSG", "SocketTimeout");
                return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
                return rtnMap;
            } catch (InterruptedException e) {
                logger.error("Exception e : {}", e.getMessage());
            }
        }

        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 개통요청
     * @param appformReqDto
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/reqSimpleOpenAjax.do")
    @ResponseBody
    public Map<String, Object> reqSimpleOpen(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //1-3. 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();

        //외국인 테스트 때문에 주석 처리
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        //개통사전체크요청 확인
        AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession();
        if (sessAppformReqDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }

        // 010 신규 유심 셀프개통인 경우, 동일아이피 차단 확인
        if(OPER_TYPE_NEW.equals(appformReqDto.getOperType()) && !"09".equals(appformReqDto.getUsimKindsCd())){
            int nacSelfIp = appformSvc.getNacSelfCount();
            if(nacSelfIp > 0){
                throw new McpCommonJsonException("IP_FAIL", NAC_SELF_IP_EXCEPTION);
            }
        }

        //휴대폰 안심 서비스 처리 인증 확인
        if (!StringUtils.isBlank(appformReqDto.getInsrProdCd())) {

            NiceResDto sessNiceInsrRes = SessionUtils.getNiceInsrResCookieBean();
            if (sessNiceInsrRes == null || !sessNiceRes.getConnInfo().equals(sessNiceInsrRes.getConnInfo())) {
                throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION_INSR2);
            } else {
                appformReqDto.setClauseInsrProdFlag("Y");
                appformReqDto.setInsrAuthInfo("ReqNo:" + sessNiceInsrRes.getReqSeq() + ", ResNo:" + sessNiceInsrRes.getResSeq());
            }
        }else{
            // 휴대폰 안심보험 인증 후 신청하지 않은 경우 step 제거
            if(0 < certService.getModuTypeStepCnt(INSR_PROD, "")){
                CertDto certDto = new CertDto();
                certDto.setModuType(INSR_PROD);
                certDto.setCompType("G");
                certService.getCertInfo(certDto);
            }
        }

        // 자급제 보상 서비스 처리 인증 확인
        if (!StringUtils.isBlank(appformReqDto.getRwdProdCd())) {

            NiceResDto sessNiceRwdRes = SessionUtils.getNiceRwdResCookieBean();
            if (sessNiceRwdRes == null || !sessNiceRes.getConnInfo().equals(sessNiceRwdRes.getConnInfo())) {

                //이력 정보 저장 처리
                McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("FORM_AUTH_ERROR");
                if (sessNiceRwdRes == null) {
                    mcpIpStatisticDto.setPrcsSbst("sessNiceRwdRes is NULL");
                } else {
                    mcpIpStatisticDto.setPrcsSbst("ConnInfo[" + sessNiceRwdRes.getConnInfo() + "] Name[" + sessNiceRwdRes.getName() + "] ");
                }
                mcpIpStatisticDto.setParameter("ConnInfo[" + sessNiceRes.getConnInfo() + "]");
                mcpIpStatisticDto.setTrtmRsltSmst("reqSimpleOpenAjax");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                throw new McpCommonJsonException("0004", NICE_CERT_EXCEPTION_RWD);
            } else {
                appformReqDto.setClauseRwdFlag("Y");
                appformReqDto.setRwdAuthInfo("ReqNo:" + sessNiceRwdRes.getReqSeq() + ", ResNo:" + sessNiceRwdRes.getResSeq());
            }
        }

        /*
         * 인자값 요금제 코드 , DB요금제 코드 비교
         * 인자값 변경 하며 할인 부가 서비스가 다른 등록 할 수 있음
         * 방어 로직  ...
         */
        if (!appformReqDto.getSocCode().equals(sessAppformReqDto.getSocCode())) {
            throw new McpCommonJsonException("8888", F_BIND_EXCEPTION);
        }

        /*
         * DB에서 개통 이후에서 계속 호출 하는 DATA확인
         * 고객이 오해 할 소지 있음
         * OP2 정상적으로 호출이 존재 할 경우 STOP
         * 방어 로직
         */
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setMvnoOrdNo(appformReqDto.getResNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_REQ_OPEN_RESULT);
        McpRequestOsstDto mcpRequestOsstDtoRtn = appformSvc.getRequestOsst(mcpRequestOsstDto);

        if (mcpRequestOsstDtoRtn != null && isSuccessOP2(mcpRequestOsstDtoRtn.getRsltCd())) {
            rtnMap.put("RESULT_CODE", "1003");
            rtnMap.put("ERROR_NE_MSG", "이전에 개통한 신청서 정보입니다.<br/>고객센터(114/무료)로 문의부탁드립니다.");
            return rtnMap;
        }

        //eSIM 확인
        fnSetDataOfeSim(appformReqDto);
        // 2025-05-19, 부정사용주장 단말 확인, 개발팀 김동혁
        if (esimSvc.checkAbuseImeiList(Arrays.asList(appformReqDto.getImei1(), appformReqDto.getImei2()))) {
            throw new McpCommonJsonException("9901", ESIM_SELF_ABUSE_IMEI_EXCEPTION);
        }

        // 이벤트코드 검증
        if(!StringUtil.isEmpty(appformReqDto.getEvntCdPrmt())){
            GiftPromotionBas giftPromotVal = new GiftPromotionBas();
            giftPromotVal = eventCodeSvc.getEventchk(appformReqDto.getEvntCdPrmt());

            if(giftPromotVal == null || giftPromotVal.getEcpSeq() ==null){
                throw new McpCommonJsonException("-1", "입력하신 이벤트 코드는 사용이 불가합니다.");
            }
        }

        //사은품 정보 검증
        String[] prmtIdList = appformReqDto.getPrmtIdList();
        String[] prdtIdList = appformReqDto.getPrdtIdList();
        String[] prmtTypeList = new String[prmtIdList.length];
        List<GiftPromotionBas> giftPromotionBasList = null;
        int prmtTypeListIndex = 0;

            //사은품정보는 유심코드에 조직코드에 우선 한다.
            String orgnId = "";
            if (!StringUtils.isBlank(appformReqDto.getReqUsimSn()) && !"Y".equals(appformReqDto.getJehuRefererYn())) { //제휴위탁온라인의 경우 대리점코드로 사은품 정보를 입력한다.
                orgnId = appformSvc.getUsimOrgnId(appformReqDto.getReqUsimSn());
                if (orgnId == null || orgnId.equals("")) {
                    orgnId = appformReqDto.getCntpntShopId();
                }
            } else {
                orgnId = appformReqDto.getCntpntShopId();
            }

            GiftPromotionBas giftPromotionBas = new GiftPromotionBas();
            giftPromotionBas.setOnOffType(appformReqDto.getOnOffType());
            giftPromotionBas.setOperType(appformReqDto.getOperType());
            giftPromotionBas.setReqBuyType(appformReqDto.getReqBuyType());
            giftPromotionBas.setAgrmTrm(appformReqDto.getEnggMnthCnt() + "");
            giftPromotionBas.setRateCd(appformReqDto.getSocCode());
            giftPromotionBas.setOrgnId(orgnId);
            giftPromotionBas.setModelId(appformReqDto.getModelId());
            giftPromotionBasList = giftSvc.giftBasList(giftPromotionBas, appformReqDto.getCntpntShopId());

            if (prmtIdList != null && prmtIdList.length > 0) {
                if (giftPromotionBasList == null || giftPromotionBasList.size() < 1) {
                    throw new McpCommonJsonException("8881", ExceptionMsgConstant.GIFT_EXSIST_EXCEPTION);
                } else {
                    //사은품 그룹 존재 여부 확인
                    for (int i = 0; i < prmtIdList.length; i++) {
                        GiftPromotionDtl giftPromotion = new GiftPromotionDtl();
                        giftPromotion.setPrmtId(prmtIdList[i]);
                        giftPromotion.setPrdtId(prdtIdList[i]);
                        boolean isHave = false;
                        for (GiftPromotionBas promotionBas : giftPromotionBasList) {
                            List<GiftPromotionDtl> giftPromotionDtlList = promotionBas.getGiftPromotionDtlList();
                            for (GiftPromotionDtl temp : giftPromotionDtlList) {
                                if (giftPromotion.getPrmtId().equals(temp.getPrmtId()) && giftPromotion.getPrdtId().equals(temp.getPrdtId())) {
                                    isHave = true;
                                    prmtTypeList[prmtTypeListIndex] = promotionBas.getPrmtType();
                                    prmtTypeListIndex++;
                                    break;
                                }
                            }
                            if (isHave) {
                                break;
                            }
                        }
                        if (!isHave) {
                            throw new McpCommonJsonException("8882", ExceptionMsgConstant.GIFT_EXSIST_EXCEPTION);
                        }
                    }

                    appformReqDto.setPrmtTypeList(prmtTypeList);

                    //사은품 갯수 확인
                    for (GiftPromotionBas promotionBas : giftPromotionBasList) {
                        int limitCnt = promotionBas.getLimitCnt();
                        int nowCnt = 0;//선택한 갯수
                        String basePrmtId = promotionBas.getPrmtId();
                        for (int i = 0; i < prmtIdList.length; i++) {
                            if (basePrmtId.equals(prmtIdList[i])) {
                                nowCnt++;
                            }
                        }
                        if (limitCnt < nowCnt) {
                            throw new McpCommonJsonException("8883", ExceptionMsgConstant.GIFT_LIMIT_EXCEPTION);
                        }
                    }
                }
            }
            //사은품 Check end



        //회원 아이디 설정
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            appformReqDto.setCretId(userSessionDto.getUserId());
        } else {
            appformReqDto.setCretId(NON_MEMBER_ID);
        }

        appformReqDto.setRequestKey(sessAppformReqDto.getRequestKey());
        appformReqDto.setResNo(sessAppformReqDto.getResNo());
        appformReqDto.setSelfCstmrCi(sessAppformReqDto.getSelfCstmrCi());

        //업데이트 방지
        appformReqDto.setUsimPayMthdCd(null);
        appformReqDto.setUsimPrice(-1);

        //샐프개통 신규(010) 1인 1회 개통 가능으로 가입 제한
        String operType = sessAppformReqDto.getOperType();
        if (OPER_TYPE_NEW.equals(operType) && appformSvc.checkLimitFormCount(appformReqDto) > 1 ) {
            rtnMap.put("RESULT_CODE", "0002");
            rtnMap.put("ERROR_NE_MSG", "셀프개통 신규가입은 명의당 1회선만 가능합니다.");

            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("SELF010_LIMIT_CHECK_2");
            mcpIpStatisticDto.setPrcsSbst(appformReqDto.getSelfCstmrCi());
            mcpIpStatisticDto.setParameter("REQUEST_KEY[" + appformReqDto.getRequestKey() + "]RES_NO[" + appformReqDto.getResNo() + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            return rtnMap;
        }

        //셀프개통 시 동일 명의의 경우 30일 이내 이력 확인 시 차단
        //기존 본인인증시 스크립트에서 체크하나 이후 사전체크 ,개통요청 시 한번 더 체크
        AppformReqDto rtnAppformReq = appformSvc.getLimitForm(appformReqDto);
        if(rtnAppformReq != null) {
            rtnMap.put("RESULT_CODE", "9902");
            rtnMap.put("ERROR_NE_MSG", SELF_LIMIT_EXCEPTION);

            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("SELF010_LIMIT_CHECK_3");
            mcpIpStatisticDto.setPrcsSbst(appformReqDto.getSelfCstmrCi());
            mcpIpStatisticDto.setParameter("REQUEST_KEY[" + appformReqDto.getRequestKey() + "]RES_NO[" + appformReqDto.getResNo() + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            return rtnMap;
        }

        //명의도용 IP 확인 후 제한
        if("5".equals(appformReqDto.getOnOffType()) || "7".equals(appformReqDto.getOnOffType())){
            int stolenIp = appformSvc.selectStolenIp(ipstatisticService.getClientIp());

            if(stolenIp > 0) {
                String redirectPage = "/appForm/appFormDesignView.do";

                if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                    redirectPage = "/m/appForm/appFormDesignView.do?";
                }

                throw new McpCommonException(ExceptionMsgConstant.STOLEN_IP_EXCEPTION, redirectPage);
            }
        }

        appformReqDto.setRip(ipstatisticService.getClientIp());

        // ============ STEP START ============
        // 계좌인증하지않고 신용카드 인증하는 경우
        if(!"D".equals(appformReqDto.getReqPayType())){
            // 계좌인증 관련 스텝 초기화
            if(0 < certService.getModuTypeStepCnt("account", "")){
                CertDto certDto = new CertDto();
                certDto.setModuType("account");
                certDto.setCompType("G");
                certService.getCertInfo(certDto);
            }
        }

        // 신용카드 인증하지 않고 계좌인증 하는 경우
        if(!"C".equals(appformReqDto.getReqPayType())){
            // 신용카드 인증 관련 스텝 초기화
            if(0 < certService.getModuTypeStepCnt("card", "")){
                CertDto certDto = new CertDto();
                certDto.setModuType("card");
                certDto.setCompType("G");
                certService.getCertInfo(certDto);
            }
        }

        // 신청서 최종 정보 체크
        Map<String, String> crtResult= appformSvc.crtUpdateSimpleAppFormInfo(appformReqDto);
        if(!AJAX_SUCCESS.equals(crtResult.get("RESULT_CODE"))){
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("ERROR_NE_MSG", crtResult.get("RESULT_DESC"));
            return rtnMap;
        }
        // ============ STEP END ============

        //테스트 계정 만 처리
        if (userSessionDto != null  && !StringUtils.isBlank(userSessionDto.getUserId() ) ) {
            String isExceptionId = NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSessionDto.getUserId());
            if (isExceptionId != null && "Y".equals(isExceptionId) ) {

                /**
                 * 부가파람 부가서비스가 포함된 OSST 개통 테스트 요청
                 *  - 상품코드 : PL249Q800  MVNO마스터결합전용 더미부가서비스(엠모바일)
                 * - 상품타입코드 : R (부가서비스)
                 * - 상품 파람 : 587838774
                 * ※ CTN : 01027435432
                 */
                List<String> additionKeyList = new ArrayList<>(Arrays.asList(appformReqDto.getAdditionKeyList()));
                additionKeyList.add("138");  //138 SELECT * FROM MCP_ADDITION;  품코드 : PL249Q800  MVNO마스터결합전용 더미부가서비스(엠모바일)
                String[] updatedArray = additionKeyList.toArray(new String[0]);
                appformReqDto.setAdditionKeyList(updatedArray);
            }
        }



        String svcMsg = appformSvc.saveSimpleAppformUpdate(appformReqDto, giftPromotionBasList);

        // ============ STEP START ============
        if("STEP_FAIL".equals(svcMsg)){
            // 최소 스텝 수 검사 실패
            rtnMap.put("RESULT_CODE", "STEP02");
            rtnMap.put("ERROR_NE_MSG", STEP_CNT_EXCEPTION);
            return rtnMap;
        }
        // ============ STEP END ============

        ////개통및수납(OP0)
        MSimpleOsstXmlVO simpleOsstXmlVO = new MSimpleOsstXmlVO();
        try {
            Thread.sleep(3000);
            // 타인납부를 위한 처리
            if (StringUtils.isBlank(appformReqDto.getPrntsBillNo())) {
                simpleOsstXmlVO = appformSvc.sendOsstService(sessAppformReqDto.getResNo(), EVENT_CODE_REQ_OPEN);
            } else {
                simpleOsstXmlVO = appformSvc.sendOsstAddBillService(sessAppformReqDto.getResNo(), Constants.EVENT_CODE_REQ_OPEN, appformReqDto.getPrntsBillNo());
            }

            if (simpleOsstXmlVO.isSuccess()) {

                // 완료페이지 URL 진입 시 본인인증 ci값과 신청정보의 ci값 추가 비교를 위해, 임시 저장
                SessionUtils.saveNiceReqTmp(sessNiceRes);

                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                /*
                 * 배치에서 삭제 하지 못 하도록
                 * 스캔 아이드 UPDATE 처리
                 */
                McpRequestDto mcpRequestDto = new McpRequestDto();
                mcpRequestDto.setRequestKey(sessAppformReqDto.getRequestKey());
                mcpRequestDto.setScanId("999999");
                appformSvc.updateMcpRequest(mcpRequestDto);

                /* 평생할인 프로모션 기적용 테이블 insert */
                appformSvc.insertDisPrmtApd(appformReqDto, "NAC");

            } else {
                svcMsg = simpleOsstXmlVO.getSvcMsg();
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("REQUEST_MSG", svcMsg);
                rtnMap.put("ERROR_NE_MSG", svcMsg);
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
            return rtnMap;
        } catch (SelfServiceException e) {
            svcMsg = simpleOsstXmlVO.getSvcMsg();
            rtnMap.put("REQUEST_MSG", svcMsg);
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());
            rtnMap.put("ERROR_NE_MSG", e.getMessageNe());
            return rtnMap;
        } catch (InterruptedException e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        rtnMap.put("REQUEST_KET", sessAppformReqDto.getRequestKey());
        rtnMap.put("RES_NO", sessAppformReqDto.getResNo());
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 개통사전 체크 확인 /  개통 및 수납 확인
     * @param appformReqDto
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/conPreCheckAjax.do")
    @ResponseBody
    public Map<String, Object> conPreCheck(AppformReqDto appformReqDto, @RequestParam(value = "prgrStatCd") String prgrStatCd
    ) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (StringUtils.isBlank(appformReqDto.getResNo())) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION);
        }


        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        McpRequestOsstDto mcpRequestOsstDtoRtn = null;
        mcpRequestOsstDto.setMvnoOrdNo(appformReqDto.getResNo());
        mcpRequestOsstDto.setPrgrStatCd(prgrStatCd);

        mcpRequestOsstDtoRtn = appformSvc.getRequestOsst(mcpRequestOsstDto);

        if (mcpRequestOsstDtoRtn != null) {
            String rsltMsg = mcpRequestOsstDtoRtn.getRsltMsg();
            String rsltCd = mcpRequestOsstDtoRtn.getRsltCd();
            rtnMap.put("RESULT_OBJ", mcpRequestOsstDtoRtn);
            rtnMap.put("RESULT_MSG", rsltMsg);

            if (EVENT_CODE_PC_RESULT.equals(prgrStatCd)) {
                if (OSST_SUCCESS.equals(rsltCd)) {
                    // ======= START : 사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회) =======
                    // ** issue : 사전체크 완료 소켓전송 시간과 사전체크 작업 완료 후 MP측 DB반영 시간 텀 존재
                    // ** to-be : ST1 연동으로 PC2 완료상태 조회 > PC2 완료 확인 후 Y39 연동

                    Map<String,String> prcSchMap= appformSvc.chkRealPc2Result(appformReqDto.getResNo(), appformReqDto.getContractNum());
                    if(!AJAX_SUCCESS.equals(prcSchMap.get("RESULT_CODE"))){
                        // 연동오류 또는 사전체크 완료 상태 확인 불가
                        rtnMap.put("RESULT_CODE", prcSchMap.get("RESULT_CODE"));
                        rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_SCH);
                        rtnMap.put("RESULT_MSG", StringUtil.NVL(prcSchMap.get("ERROR_MSG"), COMMON_EXCEPTION));
                        return rtnMap;
                    }
                    // ======= END : 사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회) =======

                    // ======= START : Y39 아이핀 Ci 조회(마이알뜰폰) =======
                    MpSvcContIpinVO mpSvcContIpinVO= new MpSvcContIpinVO();

                    try {
                        mpSvcContIpinVO= mPlatFormService.MoscSvcContService(mcpRequestOsstDtoRtn.getOsstOrdNo());
                    } catch(SocketTimeoutException e) {
                        logger.info("Y39 SocketTimeoutException ["+e.getMessage()+"]");
                    } catch(Exception e) {
                        logger.info("Y39 Exception ["+e.getMessage()+"]");
                    }

                    if(mpSvcContIpinVO == null || !mpSvcContIpinVO.isSuccess()) {
                        // PC2는 정상적으로 성공했으므로, APP_FORM_SESSION은 그대로 유지
                        throw new McpCommonJsonException("0002", COMMON_EXCEPTION);
                    }

                    //  =============== STEP START ===============
                    // CI
                    String[] certKey = {"urlType", "connInfo"};
                    String[] certValue = {"chkPreOpenCi", mpSvcContIpinVO.getIpinCi()};

                    // 운영환경 이외에서는 CI값이 제대로 리턴되지 않음. 따라서, LOCAL/DEV/STG에서는 본인인증 세션으로 대체
                    if("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName)){
                        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
                        if(sessNiceRes != null && !StringUtil.isEmpty(sessNiceRes.getConnInfo())){
                            certValue[1]= sessNiceRes.getConnInfo();
                        }
                    }

                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                        throw new McpCommonJsonException("0003", vldReslt.get("RESULT_DESC"));
                    }
                    //  =============== STEP START ===============

                    // ======= END : Y39 아이핀 Ci 조회(마이알뜰폰) =======

                    SessionUtils.saveOsstDto(mcpRequestOsstDtoRtn);  //사전 체크 정보 session 저장
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_MSG", "확인완료");
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    SessionUtils.saveAppformDto(null);
                    if ("2202".equals(rsltCd) && rsltMsg.contains("연동오류")) {
                        rsltCd = "2202_01";
                    } else if ("2412".equals(rsltCd) && rsltMsg.contains("BF1005")) {
                        rsltCd = "2412_01";
                    } else if ("2412".equals(rsltCd) && rsltMsg.contains("BF1004")) {
                        rsltCd = "2412_02";
                    } else if ("2412".equals(rsltCd) && rsltMsg.contains("BF2001")) {
                        rsltCd = "2412_03";
                    } else if ("2412".equals(rsltCd) && rsltMsg.contains("BF1010")) {
                        rsltCd = "2412_04";
                    } else if ("2412".equals(rsltCd) && rsltMsg.contains("BF1029")) {
                        rsltCd = "2412_04";
                    } else if ("7003".equals(rsltCd)) {
                        rsltCd = "7003";
                    }

                }
            } else if (EVENT_CODE_REQ_OPEN_RESULT.equals(prgrStatCd)) {
                if (isSuccessOP2(rsltCd)) {
                    SessionUtils.saveOsstDto(mcpRequestOsstDtoRtn);  //사전 체크 정보 session 저장
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_MSG", "확인완료");
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    SessionUtils.saveAppformDto(null);
                    if ("2028".equals(rsltCd) && rsltMsg.contains("유효기간")) {
                        rsltCd = "2028_01";
                    } else if ("2028".equals(rsltCd) && rsltMsg.contains("카드번호")) {
                        rsltCd = "2028_01";
                    } else if ("2028".equals(rsltCd) && rsltMsg.contains("입금계좌오류")) {
                        rsltCd = "2028_01";
                    } else if ("9000".equals(rsltCd) && rsltMsg.contains("ACTE0003")) {
                        rsltCd = "9000_01";
                    }
                }
            }
            rtnMap.put("OSST_RESULT_CODE", "RESULT_" + rsltCd);

        } else {
            //-- RSLT_CD  RSLT_MSG
            //로컬에서 강제로 성공 처리
            if ("LOCAL".equals(serverName)) {
                //if (EVENT_CODE_PC_RESULT.equals(prgrStatCd)) {

                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_MSG", "로컬에서 강제로 성공");

                    /*rtnMap.put("RESULT_CODE", "0001");
                    rtnMap.put("OSST_RESULT_CODE", "RESULT_2412_03");
                    rtnMap.put("RESULT_MSG", "아직 정보가 없습니다. 잠시후 다시 시도 하여 주시기 바랍니다.");*/

                    //SessionUtils.saveAppformDto(null);
                    //rtnMap.put("RESULT_CODE", "0001");
                    //rtnMap.put("OSST_RESULT_CODE", "RESULT_7003");
                    //rtnMap.put("RESULT_MSG", "고객정보 확인에 실패하였습니다.<br><br>확인버튼을 눌러 신분증 정보를 입력 후<br>다시 시도 해 주세요.");


                //} else if (EVENT_CODE_REQ_OPEN_RESULT.equals(prgrStatCd)) {
                    //rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    //rtnMap.put("RESULT_MSG", "로컬에서 강제로 성공");


                    //rtnMap.put("RESULT_CODE", "0001");
                    //rtnMap.put("OSST_RESULT_CODE", "RESULT_2028");

                    /*rtnMap.put("RESULT_CODE", "9999");
                    rtnMap.put("OSST_RESULT_CODE", "-1");
                    rtnMap.put("RESULT_MSG", "아직 정보가 없습니다. 잠시후 다시 시도 하여 주시기 바랍니다.");*/
                //}

                // *********************** STG 환경 강제로 성공 처리 시작
//            } else if("STG".equals(serverName)) {
//                if (EVENT_CODE_PC_RESULT.equals(prgrStatCd)) {
//
//                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//                    rtnMap.put("RESULT_MSG", "STG 강제로 성공");
//                } else if (EVENT_CODE_REQ_OPEN_RESULT.equals(prgrStatCd)) {
//                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//                    rtnMap.put("RESULT_MSG", "확인완료");
//                }
                // *********************** STG 환경 강제로 성공 처리 끝

//                rtnMap.put("RESULT_CODE", "9999");
//                rtnMap.put("OSST_RESULT_CODE", "-1");
//                rtnMap.put("RESULT_MSG", "아직 정보가 없습니다. 잠시후 다시 시도 하여 주시기 바랍니다.");

            } else {
                rtnMap.put("RESULT_CODE", "9999");
                rtnMap.put("OSST_RESULT_CODE", "-1");
                rtnMap.put("RESULT_MSG", "아직 정보가 없습니다. 잠시후 다시 시도 하여 주시기 바랍니다.");
            }
        }
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 전화번호 조회
     * @param
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/searchNumberAjax.do")
    @ResponseBody
    public Map<String, Object> searchNumber(McpRequestDto mcpRequestDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        AppformReqDto sessAppformReqDto = null;
        int searchCnt = 0;
        if ("LOCAL22".equals(serverName)) {
            searchCnt = SessionUtils.getCntSession();
            if (searchCnt > 19) {
                throw new McpCommonJsonException("0004", OVER_LIMIT_EXCEPTION);
            }

            sessAppformReqDto = new AppformReqDto();
            sessAppformReqDto.setResNo("3860");

            //방어 로직 추가
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(sessAppformReqDto.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_SEARCH_NUMBER);
            int tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

            if (tryCount > 19) {
                throw new McpCommonJsonException("0004", OVER_LIMIT_EXCEPTION);
            }

            // *********************** STG 환경 강제로 성공 처리 시작
//        } else if("STG".equals(serverName)) {
//            searchCnt = SessionUtils.getCntSession();
//            if (searchCnt > 19) {
//                throw new McpCommonJsonException("0004" ,OVER_LIMIT_EXCEPTION);
//            }
//
//            sessAppformReqDto = new AppformReqDto();
//            sessAppformReqDto.setResNo("3860");

            // *********************** STG 환경 강제로 성공 처리 끝

        } else {
            //개통사전체크요청 확인
            sessAppformReqDto = SessionUtils.getAppformSession();
            if (sessAppformReqDto == null) {
                throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
            }

            searchCnt = SessionUtils.getCntSession();
            if (searchCnt > 19) {
                throw new McpCommonJsonException("0004", OVER_LIMIT_EXCEPTION);
            }

            //방어 로직 추가
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(sessAppformReqDto.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_SEARCH_NUMBER);
            int tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

            if (tryCount > 24) {
                throw new McpCommonJsonException("0004", OVER_LIMIT_EXCEPTION);
            }

            //희망번호 저장
            mcpRequestDto.setRequestKey(sessAppformReqDto.getRequestKey());
            if (!appformSvc.updateMcpRequest(mcpRequestDto)) {
                throw new McpCommonJsonException("0004", DB_EXCEPTION);
            }
        }

        MPhoneNoListXmlVO mPhoneNoListXmlVO = null;
        try {
            mPhoneNoListXmlVO = appformSvc.getPhoneNoList(sessAppformReqDto.getResNo(), EVENT_CODE_SEARCH_NUMBER);
            if (mPhoneNoListXmlVO.isSuccess()) {
                if (mPhoneNoListXmlVO.getList() != null && !mPhoneNoListXmlVO.getList().isEmpty()) {
                    SessionUtils.saveCntSession(++searchCnt);
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_OBJ_LIST", mPhoneNoListXmlVO.getList());
                    rtnMap.put("SEARCH_CNT", searchCnt);
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                }
            } else {
                rtnMap.put("RESULT_CODE", "0002");
                rtnMap.put("RESULT_XML", mPhoneNoListXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", mPhoneNoListXmlVO.getResultCode());
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeoutException.");

        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());
        }
        return rtnMap;
    }


    public List<MPhoneNoVo> fnSearchNumber(McpRequestDto mcpRequestDto) {

        //CntpntShopId
        if (mcpRequestDto.getRequestKey() < 1
                || StringUtils.isBlank(mcpRequestDto.getResNo())
                || StringUtils.isBlank(mcpRequestDto.getReqWantNumber())) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION);
        }

        //희망번호 저장
        if (!appformSvc.updateMcpRequest(mcpRequestDto)) {
            throw new McpCommonException(DB_EXCEPTION);
        }

        List<MPhoneNoVo> rtnList = null;

        MPhoneNoListXmlVO mPhoneNoListXmlVO = null;
        try {
            Thread.sleep(3000);

            // 데이터쉐어링) osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam= new HashMap<>();
            osstParam.put("resNo", mcpRequestDto.getResNo());
            if(mcpRequestDto.getPrntsContractNo() != null) osstParam.put("prntsContractNo", mcpRequestDto.getPrntsContractNo());
            if(mcpRequestDto.getCustomerId() != null) osstParam.put("custNo", mcpRequestDto.getCustomerId());

            mPhoneNoListXmlVO = appformSvc.getPhoneNoList(osstParam, EVENT_CODE_SEARCH_NUMBER);

            if (mPhoneNoListXmlVO.isSuccess()) {
                if (mPhoneNoListXmlVO.getList() != null && !mPhoneNoListXmlVO.getList().isEmpty()) {
                    rtnList = mPhoneNoListXmlVO.getList();
                } else {
                    logger.debug("fnSearchNumber mPhoneNoListXmlVO.getList().isEmpty **********************************");
                }
            } else {
                logger.debug("fnSearchNumber 오류**********************************");
            }
        } catch (McpMplatFormException e) {
            logger.error("fnSearchNumber McpMplatFormException**********************************");
        } catch (SocketTimeoutException e) {
            logger.error("fnSearchNumber SocketTimeoutException**********************************");
        } catch (SelfServiceException e) {
            logger.error("fnSearchNumber SelfServiceException**********************************");
        } catch (Exception e) {
            logger.error("fnSearchNumber SelfServiceException**********************************");
        }
        return rtnList;
    }

    /**
     * <pre>
     * 설명     : 전화번호 예약
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/setNumberAjax.do")
    @ResponseBody
    public Map<String, Object> setNumber(McpRequestOsstDto mcpRequestOsstDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if ("LOCAL".equals(serverName)) {
            try {
                Thread.sleep(5000);
            } catch(IllegalArgumentException e) {
                logger.error("Exception e : {}", e.getMessage());
            } catch (Exception e) {
                logger.error("Exception e : {}", e.getMessage());
            }

            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;

            // *********************** STG 환경 강제로 성공 처리 시작
//        } else if("STG".equals(serverName)) {
//            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//            return rtnMap;
            // *********************** STG 환경 강제로 성공 처리 끝

        }

        //개통사전체크요청 확인
        AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession();
        if (sessAppformReqDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }

        McpRequestOsstDto sessRequestOsstDto = SessionUtils.getOsstDtoSession();
        if (sessRequestOsstDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }
        mcpRequestOsstDto.setMvnoOrdNo(sessAppformReqDto.getResNo());
        mcpRequestOsstDto.setOsstOrdNo(sessRequestOsstDto.getOsstOrdNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_NUMBER_REG);
        mcpRequestOsstDto.setAsgnAgncId(sessAppformReqDto.getAgentCode());
        mcpRequestOsstDto.setOpenSvcIndCd("03"); //03 고정 (3G)
        mcpRequestOsstDto.setIfType(WORK_CODE_RES);
        mcpRequestOsstDto.setRsltCd(OSST_SUCCESS);


        if (appformSvc.insertMcpRequestOsst(mcpRequestOsstDto)) {
            MSimpleOsstXmlVO simpleOsstXmlVO = null;
            ////번호예약(NU2)
            try {
                Thread.sleep(3000);
                simpleOsstXmlVO = appformSvc.sendOsstService(sessAppformReqDto.getResNo(), EVENT_CODE_NUMBER_REG, WORK_CODE_RES);
                if (simpleOsstXmlVO.isSuccess()) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                    rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                }
            } catch (McpMplatFormException e) {
                rtnMap.put("RESULT_CODE", "9997");
                rtnMap.put("ERROR_MSG", "response massage is null.");
            } catch (SocketTimeoutException e) {
                rtnMap.put("RESULT_CODE", "9999");
                return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG", e.getMessage());
                return rtnMap;
            } catch (InterruptedException e) {
                logger.error("Exception e : {}", e.getMessage());
            }

        } else {
            throw new McpCommonJsonException("9997", DB_EXCEPTION);
        }
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 전화번호 예약 취소
     * @param appformReqDto
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/cancelNumberAjax.do")
    @ResponseBody
    public Map<String, Object> cancelNumberAjax(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //개통사전체크요청 확인
        AppformReqDto sessAppformReqDto = SessionUtils.getAppformSession();
        if (sessAppformReqDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }

        MSimpleOsstXmlVO simpleOsstXmlVO = null;

        ////번호예약(NU2) 취소
        try {
            simpleOsstXmlVO = appformSvc.sendOsstService(sessAppformReqDto.getResNo(), EVENT_CODE_NUMBER_REG, WORK_CODE_RES_CANCEL);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } else {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG", "response massage is null.");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            return rtnMap;

        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("ERROR_MSG", e.getMessage());
            return rtnMap;
        }


        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 셀프개통신규(010) 다회선 제한 기능
     * @param appformReqDto
     * @return: Map<String, Object>
     * 날짜     : 2021. 03. 30.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/appform/checkLimitFormAjax.do")
    @ResponseBody
    public Map<String, Object> checkLimitForm(HttpServletRequest request, AppformReqDto appformReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//        if ("LOCAL".equals(serverName)) {
//            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
//            rtnMap.put("RESULT_MSG", "셀프개통  개통 가능 합니다.");
//            return rtnMap ;
//        }

        if (StringUtils.isBlank(appformReqDto.getSocCode()) || appformReqDto.getRequestKeyTemp() <= 0) {
            rtnMap.put("RESULT_CODE", "-2");
            rtnMap.put("RESULT_MSG", "필수 인자값[SocCode,requestKeyTemp]이 없습니다.");
            return rtnMap;
        }

        // =============== START: 본인인증 리턴처리 변경 ===============
        // 1. 인자값 확인
        if (StringUtils.isBlank(appformReqDto.getReqSeq())
                || StringUtils.isBlank(appformReqDto.getResSeq())) {
            throw new McpCommonJsonException("AUTH01", BIDING_EXCEPTION);
        }

        // 2. 인증정보 조회
        NiceLogDto niceLogDto= new NiceLogDto();
        niceLogDto.setReqSeq(appformReqDto.getReqSeq());
        niceLogDto.setResSeq(appformReqDto.getResSeq());
        NiceLogDto niceLogRtn = nicelog.getMcpNiceHistWithReqSeq(niceLogDto);
        if (niceLogRtn == null) {
            throw new McpCommonJsonException("AUTH02", NICE_CERT_EXCEPTION_INSR);
        }

        if(StringUtil.isEmpty(niceLogRtn.getConnInfo()) || StringUtil.isEmpty(niceLogRtn.getName())){
            throw new McpCommonJsonException("AUTH02", NICE_CERT_EXCEPTION_INSR);
        }

        // 3. 기존 화면에서 넘어오던 정보 서버세팅
        appformReqDto.setSelfCstmrCi(niceLogRtn.getConnInfo());
        appformReqDto.setCstmrName(niceLogRtn.getName());
        // =============== START: 본인인증 리턴처리 변경 ===============

        // ============ STEP START ============
        Map<String, String> resultMap = certService.isAuthStepApplyUrl(request);
        if("Y".equals(resultMap.get("isAuthStep"))) {

            // nicePin 인증 스텝 필수 존재
            if(0 >= certService.getModuTypeStepCnt("nicePin", "")){
                throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
            }

            // 이름, CI
            String[] certKey= {"urlType", "name", "connInfo"};
            String[] certValue= {"chkOpenLimit", appformReqDto.getCstmrName(), appformReqDto.getSelfCstmrCi()};

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
            }
        }
        // ============ STEP END ============

        //22026.03 - 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한
        //NMCP_REQUEST_TEMP에서 OPER_TYPE(NAC3), ON_OFF_TYPE(5,7) 인지 확인
        AppformReqDto reqTemp = appformSvc.getAppFormTemp(appformReqDto.getRequestKeyTemp());
        if(reqTemp != null) {
            if(!"NAC3".equals(reqTemp.getOperType()) || !Arrays.asList("5","7").contains(reqTemp.getOnOffType())) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            }
        }else {
            rtnMap.put("RESULT_CODE", "-2");
            rtnMap.put("RESULT_MSG", "NMCP_REQUEST_TEMP 조회 실패");
            return rtnMap;
        }

        /*
         * 셀프개통 요금제 기준 제외 처리
         * 공통코드에서 제외 요금제 확인
         */
        NmcpCdDtlDto exceptionPrice = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_EXCEPTION_LIST_PRICE_CD, appformReqDto.getSocCode());
        if (exceptionPrice != null) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "예외 요금제 입니다. 신규 개통 가능 합니다.");
            return rtnMap;
        }

        /*
         * [고객CI정보에 대한 개통 정보 확인 ]
         * 1. 셀프개통 여부
         * 2. 신규서식지
         * 3. 개통이 기준 +30일 이내
         */
        AppformReqDto rtnAppformReq = appformSvc.getLimitForm(appformReqDto);

        if (rtnAppformReq == null) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "개통 가능 합니다.");
        } else {

            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_MSG", "신규개통한 정보가 있습니다. \n개통이 불가능 합니다. ");

            //이력 정보 저장 처리
            McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
            mcpIpStatisticDto.setPrcsMdlInd("SELF010_LIMIT_CHECK");
            mcpIpStatisticDto.setPrcsSbst(rtnAppformReq.getContractNum());  //계약번호
            mcpIpStatisticDto.setParameter("NCN[" + rtnAppformReq.getContractNum() + "]CI[" + rtnAppformReq.getSelfCstmrCi() + "]");
            mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
            ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
        }

        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 요금제 정보 , 가격정보
     * @param  :
     * @return:
     * 날짜     : 2021. 03. 30.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/msp/listChargeInfoAjax.do")
    @ResponseBody
    public List<MspSaleSubsdMstDto> listChargeInfo(
            @ModelAttribute MspSaleSubsdMstDto saleSubsdMstDto
            , @RequestParam(defaultValue = "CHARGE_ROW", value = "orderEnum") OrderEnum orderEnum
            , @RequestParam(defaultValue = "") String onOffType) {


        if (!StringUtils.isBlank(saleSubsdMstDto.getSalePlcyCd())) {
            /*
             * getSalePlcyCd정책
             * getPrdtId 대표상품 아이디
             * getOrgnId 접점 코드
             * getOperType
             *
             * #{plcySctnCd}  AS PLCY_SCTN_CD  ,--정책구분코드(01:단말,02:유심)
                #{onOffType}   AS ON_OFF_TYPE ,


             */
            if (StringUtils.isBlank(saleSubsdMstDto.getPrdtId())
                    || StringUtils.isBlank(saleSubsdMstDto.getOrgnId())
                    || StringUtils.isBlank(saleSubsdMstDto.getOperType())) {
                throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
            }

            RestTemplate restTemplate = new RestTemplate();
            MspSaleSubsdMstDto[] chargeTemList = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSaleSubsdMstList", saleSubsdMstDto, MspSaleSubsdMstDto[].class);
            List<MspSaleSubsdMstDto> chargeList = Arrays.asList(chargeTemList);

            //최저가로 정렬처리하기위해 임시로 상품기본객체를 생성한다.
            PhoneProdBasDto temp = new PhoneProdBasDto();
            temp.setMspSaleSubsdMstListForLowPrice(chargeList);
            temp.setOrderEnum(orderEnum);
            if (chargeList != null && chargeList.size() > 0) {
                temp.doSort();
            }

            for (MspSaleSubsdMstDto bakset : chargeList) {    //요즘제 정보에 정책할부수수료와,기본수수료값을 세팅한다.
                if (saleSubsdMstDto.getModelMonthly() > 0) {
                    bakset.setModelMonthly(saleSubsdMstDto.getModelMonthly());    //입력받은 할부기간
                }
            }

            return chargeList;
        } else {
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_REGI_SITE_LIST, saleSubsdMstDto.getOrgnId());

            if (nmcpCdDtlDto == null) {
                throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
            }

            if (StringUtils.isBlank(saleSubsdMstDto.getOperType())) {
                throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
            }

            if (StringUtils.isBlank(nmcpCdDtlDto.getExpnsnStrVal3())) {
                throw new McpCommonJsonException("0007", BIDING_EXCEPTION);
            }

            // 요금제
            Map<String, String> inputMap = new HashMap<String, String>();
            inputMap.put("code", nmcpCdDtlDto.getExpnsnStrVal3());
            inputMap.put("orgnId", saleSubsdMstDto.getOrgnId());
            inputMap.put("onOffType", saleSubsdMstDto.getOnOffType());
            inputMap.put("operType", saleSubsdMstDto.getOperType());
            inputMap.put("rateCd", saleSubsdMstDto.getRateCd());
            inputMap.put("prdtSctnCd", LTE_FOR_MSP);  //--getPrdtSctnCd
            //inputMap.put("prdtSctnCd", appformReqDto.getPrdtSctnCd());

            RestTemplate restTemplate = new RestTemplate();
            MspSaleSubsdMstDto[] chargeTemList = restTemplate.postForObject(apiInterfaceServer + "/prepia/rateList", inputMap, MspSaleSubsdMstDto[].class);
            List<MspSaleSubsdMstDto> chargeList = Arrays.asList(chargeTemList);
            return chargeList;
        }
    }


    /**
     * <pre>
     * 설명     : 데이터 쉐어링 신규 개통요청
     * @param appformReqDto
     * contractNum :계약번호  NCN
     * reqUsimSn : 유심번호
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = {"/appform/saveDataSharingSimpleAjax.do", "/m/appform/saveDataSharingSimpleAjax.do"})
    @ResponseBody
    public Map<String, Object> saveDataSharingSimple(AppformReqDto appformReqDto
            , @RequestParam(value = "menuType", required = false) String menuType) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //TO_DO
        if (StringUtils.isBlank(appformReqDto.getContractNum()) || StringUtils.isBlank(appformReqDto.getReqUsimSn())) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        //로그인 정보 확인 및 정회원 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        McpUserCntrMngDto resultOut = new McpUserCntrMngDto();

        if (userSession != null) {
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        } else {
            McpUserCntrMngDto out = SessionUtils.getNonmemberSharingInfo();
            if (out == null) {
                throw new McpCommonJsonException("0003", ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION);
            }

            resultOut = msfMypageSvc.selectCntrListNoLogin(out);
        }

        //서비스 번호 ContractNum 검증
        Boolean isOwn = false;
        String cntrMobileNo = "";
        String certContractNum = null;
        String customerId = null;

        if (userSession != null) {
            for (McpUserCntrMngDto userCntrMng : cntrList) {
                if (userCntrMng.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                    isOwn = true;
                    cntrMobileNo = userCntrMng.getCntrMobileNo();
                    certContractNum = userCntrMng.getContractNum();
                    customerId = userCntrMng.getCustId();
                    break;
                }
            }
        } else {
            if (resultOut != null && resultOut.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                isOwn = true;
                cntrMobileNo = resultOut.getCntrMobileNo();
                certContractNum = resultOut.getContractNum();
                customerId= resultOut.getCustId();
            }
        }

        if (!isOwn) {
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.OWN_EXCEPTION);
        }

        // 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("0005", NICE_CERT_EXCEPTION);
        }


        //청구계정번호
        String billAcntNo = msfMypageSvc.selectBanSel(appformReqDto.getContractNum());


        //1. 신청서 테이블에 저장

        //1.1 기존에 저장한 정보 확인
        //두번 호출 하면 첫번째 요청에만 DB 저장한다.
        AppformReqDto rtnAppformReqDto = SessionUtils.getAppformSession();
        if (rtnAppformReqDto == null) {
            //1-2. 계약번호에 신청서 정보 설정
            AppformReqDto appformReqResult = appformSvc.getCopyMcpRequest(appformReqDto);
            if (appformReqResult == null) {
                throw new McpCommonJsonException("0006", ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
            }

            //1-2 인증정보 주민등록 번호 확인
            //임
            String cstmrNativeRrn = appformReqResult.getCstmrNativeRrnDesc();

            if (6 < cstmrNativeRrn.length()) {
                cstmrNativeRrn = cstmrNativeRrn.substring(0, 6);
            }


            if (sessNiceRes.getBirthDate().indexOf(cstmrNativeRrn) < 0) {
                throw new McpCommonJsonException("0007", NICE_CERT_EXCEPTION);
            }

            appformReqResult.setOperType(OPER_TYPE_NEW); //operType: NAC3  <- 고정
            appformReqResult.setOnlineAuthType(sessNiceRes.getAuthType());
            appformReqResult.setOnlineAuthInfo("ReqNo:" + sessNiceRes.getReqSeq() + ", ResNo:" + sessNiceRes.getResSeq());
            appformReqResult.setSelfCstmrCi(sessNiceRes.getConnInfo());
            appformReqResult.setServiceType("PO");
            appformReqResult.setClauseInsrProdFlag("N");
            appformReqResult.setClauseRwdFlag("N");
            if ("Y".equals(NmcpServiceUtils.isMobile())) {
                appformReqResult.setOnOffType("7");
            } else {
                appformReqResult.setOnOffType("5");
            }
            appformReqResult.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));
            appformReqResult.setCntpntShopId(orgnId);
            appformReqResult.setPstate("00");
            appformReqResult.setCstmrType(CSTMR_TYPE_NA);
            appformReqResult.setMaxDiscount3(0);
            appformReqResult.setDcAmt(0);
            appformReqResult.setAddDcAmt(0);
            appformReqResult.setEnggMnthCnt(0);
            appformReqResult.setModelInstallment(0);
            appformReqResult.setModelPriceVat(0);
            appformReqResult.setModelDiscount2(0);
            appformReqResult.setModelDiscount3(0);
            appformReqResult.setModelPrice(0);
            appformReqResult.setSocCode("KISOPMDSB");
            appformReqResult.setRealMdlInstamt(0);
            appformReqResult.setSettlAmt(0);
            appformReqResult.setPrdtSctnCd("LTE");
            appformReqResult.setJoinPrice(0);
            appformReqResult.setModelMonthly("0");  //MODEL_MONTHLY
            appformReqResult.setReqUsimSn(appformReqDto.getReqUsimSn());

            appformReqResult.setSelfCertType(appformReqDto.getSelfCertType());
            appformReqResult.setSelfIssuExprDt(appformReqDto.getSelfIssuExprDt());
            appformReqResult.setSelfInqryAgrmYn("Y");

            //안면인증값 설정
            appformReqResult.setFathTrgYn(appformReqDto.getFathTrgYn());
            appformReqResult.setClauseFathFlag(appformReqDto.getClauseFathFlag());
            appformReqResult.setFathTransacId(appformReqDto.getFathTransacId());
            appformReqResult.setFathCmpltNtfyDt(appformReqDto.getFathCmpltNtfyDt());
            if ("02".equals(appformReqDto.getSelfCertType()) || "04".equals(appformReqDto.getSelfCertType())) {
                appformReqResult.setSelfIssuNum(appformReqDto.getSelfIssuNum());
            }

            //유심비 설정
            appformReqResult.setUsimPayMthdCd("1");
            appformReqResult.setUsimPrice(0);

            //가입비 설정
            //직영 대리점
            /** 가입비 납부방법
             * 1 면제
             * 2 일시납
             * 3 3개월분납
             */
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(Constants.GROUP_CODE_USIM_PRICE_INFO, appformReqResult.getSocCode());

            if (nmcpCdDtlDto != null && "Y".equals(nmcpCdDtlDto.getExpnsnStrVal1())) {
                //유심비 /  가입비 설정
                UsimBasDto usimBasDtoParm = new UsimBasDto();
                usimBasDtoParm.setOperType(appformReqDto.getOperTypeSmall());
                usimBasDtoParm.setDataType(appformReqDto.getPrdtSctnCd());
                List<UsimMspRateDto> usimPriceList = usimService.selectJoinUsimPriceNew(usimBasDtoParm);
                int intJoinPrice = 0;

                if (usimPriceList != null && usimPriceList.size() > 0) {
                    intJoinPrice = Integer.parseInt(usimPriceList.get(0).getJoinPrice());
                }

                appformReqResult.setJoinPayMthdCd("3");
                appformReqResult.setJoinPrice(intJoinPrice);
            } else {
                appformReqResult.setJoinPayMthdCd("1");
                appformReqResult.setJoinPrice(0);
            }



            /*
             * 12 스팸차단서비스 29 발신번호표시 30 통합사서함 31 정보제공사업자번호차단
             */
            String[] additionKeyList = {"12", "29", "30", "31"};
            appformReqResult.setAdditionKeyList(additionKeyList);

            //접점코드로 대리점 코드 조회
            String agentCode = "";

            try {
                agentCode = appformSvc.getAgentCode(appformReqResult.getCntpntShopId());
            } catch (RestClientException e) {
                logger.error(e.getMessage());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            appformReqResult.setAgentCode(agentCode);
            appformReqResult.setManagerCode("M0001");
            appformReqResult.setRid("-");
            appformReqResult.setViewFlag("Y");
            appformReqResult.setRequestStateCode("00");
            appformReqResult.setRip(ipstatisticService.getClientIp());

            //다시 암호화 처리
            if (!StringUtils.isBlank(appformReqResult.getReqAccountNumber())) {
                appformReqResult.setReqAccountNumber(appformReqResult.getReqAccountNumber());
            }
            if (!StringUtils.isBlank(appformReqResult.getReqCardRrn())) {
                appformReqResult.setReqCardRrn(appformReqResult.getReqCardRrn());
            }
            if (!StringUtils.isBlank(appformReqResult.getReqAccountRrn())) {
                appformReqResult.setReqAccountRrn(appformReqResult.getReqAccountRrn());
            }
            if (!StringUtils.isBlank(appformReqResult.getReqCardNo())) {
                appformReqResult.setReqCardNo(appformReqResult.getReqCardNo());
            }

            /* 직영 평생할인 프로모션 ID 가져오기 */
            String prmtId = appformSvc.getChrgPrmtId(appformReqResult);
            if(!StringUtils.isBlank(prmtId))appformReqResult.setPrmtId(prmtId);

            String cpntId = SessionUtils.getFathSession().getCpntId();
            if (StringUtils.isEmpty(cpntId)) {
                appformReqResult.setCpntId(appformReqResult.getCntpntShopId());
            } else {
                appformReqResult.setCpntId(cpntId);
            }

            //1-3. 신청서 저장 호출
            try {

                // ============ STEP START ============
                // 1. nicePin 인증연동 확인
                if(0 >= certService.getModuTypeStepCnt("nicePin", "")){
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }

                // 2. 데이터쉐어링 최종 정보 확인
                // 계약번호, 유심번호, CI
                String[] certKey= {"urlType", "contractNum", "reqUsimSn", "connInfo"};
                String[] certValue= {"saveShareAppform", certContractNum, appformReqResult.getReqUsimSn(), sessNiceRes.getConnInfo()};

                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                }

                appformReqResult.setCertMenuType("sharing");
                // ============ STEP END ============

                rtnAppformReqDto = appformSvc.saveAppform(appformReqResult, null, null);

            } catch(McpCommonJsonException e){
                // STEP 오류 처리
                rtnMap.put("RESULT_CODE", e.getRtnCode());
                rtnMap.put("RESULT_MSG", e.getErrorMsg());
                return rtnMap;
            } catch(DataAccessException e) {
                throw new McpCommonException(DB_EXCEPTION);
            } catch (Exception e) {
                throw new McpCommonException(COMMON_EXCEPTION);
            }

        } else {
            //1-4. 신청서 SESSION
            rtnAppformReqDto = SessionUtils.getAppformSession();
        }

        //2. 사전체크 및 고객생성(PC0)
        MSimpleOsstXmlVO simpleOsstXmlVO = null;
        try {
            Thread.sleep(3000);

            // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam= new HashMap<>();
            osstParam.put("resNo", rtnAppformReqDto.getResNo());
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo", customerId);

            simpleOsstXmlVO = appformSvc.sendOsstService(osstParam, Constants.EVENT_CODE_PRE_CHECK);
            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            } else {
                rtnMap.put("RESULT_CODE", "1001");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                return rtnMap;
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "1002");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "response massage is null.");
            return rtnMap;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "1003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            return rtnMap;
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "1004");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", e.getMessage());
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "1005");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "Exception");
            return rtnMap;
        }

        if ("LOCAL".equals(serverName)) {
            try {
                Thread.sleep(15 * 1000);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
                return rtnMap;
            } catch (InterruptedException e) {
                throw new McpCommonJsonException("0007", COMMON_EXCEPTION);
            }
        }

        //3. 사전체크 확인 (PC2)
        //    - MCP_REQUEST_OSST CALL BACK 확인
        McpRequestOsstDto mcpRequestOsstDtoRtn = null;
        try {
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_PC_RESULT);

            //5초 24번 120초 ...
            for (int i = 0; i < 50; i++) {
                Thread.sleep(5000);
                mcpRequestOsstDtoRtn = appformSvc.getRequestOsst(mcpRequestOsstDto);
                if (mcpRequestOsstDtoRtn != null) {
                    String rsltMsg = mcpRequestOsstDtoRtn.getRsltMsg();
                    String rsltCd = mcpRequestOsstDtoRtn.getRsltCd();

                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
                    rtnMap.put("RESULT_OBJ", mcpRequestOsstDtoRtn);
                    rtnMap.put("RESULT_MSG", rsltMsg);
                    rtnMap.put("RESULT_CODE", rsltCd);

                    if (OSST_SUCCESS.equals(rsltCd)) {
                        //SessionUtils.saveOsstDto(mcpRequestOsstDtoRtn);  //사전 체크 정보 session 저장
                        break;
                    } else {
                        rtnMap.put("RESULT_CODE", "2001");
                        return rtnMap;
                    }
                }
            }

            if (mcpRequestOsstDtoRtn == null) {
                rtnMap.put("RESULT_CODE", "2002");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
                rtnMap.put("ERROR_MSG", "DB 결과값 없음");
                return rtnMap;
            }
        } catch(DataAccessException e) {
            rtnMap.put("RESULT_CODE", "2003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
            rtnMap.put("ERROR_MSG", "DataAccessException");
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "2003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PC_RESULT);
            rtnMap.put("ERROR_MSG", "Exception");
            return rtnMap;
        }

        // ======= START : 사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회) =======

        // ** issue : 사전체크 완료 소켓전송 시간과 사전체크 작업 완료 후 MP측 DB반영 시간 텀 존재
        // ** to-be : ST1 연동으로 PC2 완료상태 조회 > PC2 완료 확인 후 Y39 연동

        Map<String,String> prcSchMap= appformSvc.chkRealPc2Result(rtnAppformReqDto.getResNo(), appformReqDto.getContractNum());
        if(!AJAX_SUCCESS.equals(prcSchMap.get("RESULT_CODE"))){
            // 연동오류 또는 사전체크 완료 상태 확인 불가
            rtnMap.put("RESULT_CODE", prcSchMap.get("RESULT_CODE"));
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_SCH);
            rtnMap.put("ERROR_MSG", StringUtil.NVL(prcSchMap.get("ERROR_MSG"), COMMON_EXCEPTION));
            return rtnMap;
        }
        // ======= END : 사전체크 완료상태 조회 (사전체크 작업 완료 후 MP측 DB작업 반영 상태 조회) =======

        // ======= START : Y39 아이핀 Ci 조회(마이알뜰폰) =======
        MpSvcContIpinVO mpSvcContIpinVO= new MpSvcContIpinVO();

        try {
            mpSvcContIpinVO= mPlatFormService.MoscSvcContService(mcpRequestOsstDtoRtn.getOsstOrdNo());
        } catch(SocketTimeoutException e) {
            logger.info("Y39 SocketTimeoutException ["+e.getMessage()+"]");
        } catch(Exception e) {
            logger.info("Y39 Exception ["+e.getMessage()+"]");
        }

        if(mpSvcContIpinVO == null || !mpSvcContIpinVO.isSuccess()) {
            throw new McpCommonJsonException("7001", COMMON_EXCEPTION);
        }

        //  =============== STEP START ===============
        // step 종료여부, CI
        String[] certKey = {"urlType", "stepEndYn", "connInfo"};
        String[] certValue = {"chkPreOpenCi", "Y", mpSvcContIpinVO.getIpinCi()};

        // 운영환경 이외에서는 CI값이 제대로 리턴되지 않음. 따라서, LOCAL/DEV/STG에서는 본인인증 세션으로 대체
        if("LOCAL".equals(serverName) || "DEV".equals(serverName) || "STG".equals(serverName)){
            if(sessNiceRes != null && !StringUtil.isEmpty(sessNiceRes.getConnInfo())){
                certValue[2]= sessNiceRes.getConnInfo();
            }
        }

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP03", vldReslt.get("RESULT_DESC"));
        }
        //  =============== STEP START ===============

        // ======= END : Y39 아이핀 Ci 조회(마이알뜰폰) =======

        //4. 번호조회(NU1)
        /*
          tlphNo:$chkRadioObj.val()
         ,tlphNoStatCd:$chkRadioObj.attr("tlphNoStatCd")
         ,tlphNoOwnCmpnCd:$chkRadioObj.attr("tlphNoOwnCmncCmpnCd")
         ,encdTlphNo:$chkRadioObj.attr("encdTlphNo")
        */
        String tlphNo = "";
        String tlphNoStatCd = "";
        String tlphNoOwnCmpnCd = "";
        String encdTlphNo = "";

        McpRequestDto mcpRequestDto = new McpRequestDto();
        mcpRequestDto.setRequestKey(rtnAppformReqDto.getRequestKey());
        mcpRequestDto.setResNo(rtnAppformReqDto.getResNo());
        mcpRequestDto.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));  //끝번호

        // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
        mcpRequestDto.setPrntsContractNo(certContractNum);
        mcpRequestDto.setCustomerId(customerId);

        List<MPhoneNoVo> phoneNmeverList = fnSearchNumber(mcpRequestDto);

        if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
            tlphNo = phoneNmeverList.get(0).getTlphNo();
            tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
            tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
            encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
        } else {
            // 끝번호 조회 안되면
            // 중간 번호로 다시 시도
            mcpRequestDto.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 8, cntrMobileNo.length() - 4));  //중간번호
            phoneNmeverList = fnSearchNumber(mcpRequestDto);
            if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                tlphNo = phoneNmeverList.get(0).getTlphNo();
                tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
                tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
                encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
            } else {
                // 중간 번호 조회 안되면
                // Random

                Random random;
                String a = "";
                String b = "";
                String c = "";
                String d = "";
                try {
                    random = SecureRandom.getInstance("SHA1PRNG");
                    a = String.valueOf(random.nextInt(10));
                    b = String.valueOf(random.nextInt(10));
                    c = String.valueOf(random.nextInt(10));
                    d = String.valueOf(random.nextInt(10));
                } catch (NoSuchAlgorithmException e1) {
                    throw new McpErropPageException(COMMON_EXCEPTION);
                }

                mcpRequestDto.setReqWantNumber(a + b + c + d);  //Random
                phoneNmeverList = fnSearchNumber(mcpRequestDto);
                if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                    tlphNo = phoneNmeverList.get(0).getTlphNo();
                    tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
                    tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
                    encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
                } else {
                    rtnMap.put("RESULT_CODE", "3001");
                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_SEARCH_NUMBER);
                    rtnMap.put("ERROR_MSG", "조회 전화번호 없음");
                    return rtnMap;
                }
            }
        }


        //5. 번호예약/취소(NU2)
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setTlphNo(tlphNo);
        mcpRequestOsstDto.setTlphNoStatCd(tlphNoStatCd);
        mcpRequestOsstDto.setTlphNoOwnCmpnCd(tlphNoOwnCmpnCd);
        mcpRequestOsstDto.setEncdTlphNo(encdTlphNo);
        mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
        // mcpRequestOsstDto.setOsstOrdNo("22222");
        mcpRequestOsstDto.setOsstOrdNo(mcpRequestOsstDtoRtn.getOsstOrdNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_NUMBER_REG);
        mcpRequestOsstDto.setAsgnAgncId(rtnAppformReqDto.getAgentCode());
        mcpRequestOsstDto.setOpenSvcIndCd("03"); //03 고정 (3G)
        mcpRequestOsstDto.setIfType(WORK_CODE_RES);
        mcpRequestOsstDto.setRsltCd(OSST_SUCCESS);

        if (appformSvc.insertMcpRequestOsst(mcpRequestOsstDto)) {
            MSimpleOsstXmlVO simpleOsstXmlReg = null;
            ////번호예약(NU2)
            try {
                Thread.sleep(3000);

                // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
                Map<String, String> osstParam= new HashMap<>();
                osstParam.put("resNo", rtnAppformReqDto.getResNo());
                osstParam.put("gubun", WORK_CODE_RES);
                osstParam.put("prntsContractNo", certContractNum);
                osstParam.put("custNo", customerId);

                simpleOsstXmlReg = appformSvc.sendOsstService(osstParam, EVENT_CODE_NUMBER_REG);

                if (simpleOsstXmlVO.isSuccess()) {
                    logger.debug("sendOsstService 성공**********************************");
                } else {
                    rtnMap.put("RESULT_CODE", "4001");
                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                    rtnMap.put("RESULT_XML", simpleOsstXmlReg.getResponseXml());
                    rtnMap.put("ERROR_MSG", simpleOsstXmlReg.getResultCode());
                    return rtnMap;
                }
            } catch (McpMplatFormException e) {
                rtnMap.put("RESULT_CODE", "4002");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", "response massage is null.");
                return rtnMap;
            } catch (SocketTimeoutException e) {
                rtnMap.put("RESULT_CODE", "4003");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", "SocketTimeoutException");
                return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "4004");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", e.getMessage());
                return rtnMap;

            } catch (InterruptedException e) {
                logger.error("Exception e : {}", e.getMessage());
            }

        } else {
            rtnMap.put("RESULT_CODE", "4005");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
            rtnMap.put("ERROR_MSG", DB_EXCEPTION);
            return rtnMap;
        }


        //6. 개통및수납(OP0)
        MSimpleOsstXmlVO simpleOsstXmlVO3 = new MSimpleOsstXmlVO();
        String svcMsg = "";
        try {
            Thread.sleep(3000);
            //simpleOsstXmlVO = appformSvc.sendOsstService(rtnAppformReqDto.getResNo(), EVENT_CODE_REQ_OPEN);

            // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam= new HashMap<>();
            osstParam.put("resNo", rtnAppformReqDto.getResNo());
            osstParam.put("billAcntNo", billAcntNo);
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo", customerId);

            simpleOsstXmlVO = appformSvc.sendOsstService(osstParam, Constants.EVENT_CODE_REQ_OPEN);

            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

                /* 데이터 쉐어링 OP0 성공 후 평생할인 기적용 ISNERT */
                if (appformReqDto.getRequestKey() == 0) {
                    appformReqDto.setRequestKey(rtnAppformReqDto.getRequestKey());
                }
                appformReqDto.setResNo(rtnAppformReqDto.getResNo());
                appformReqDto.setSocCode(rtnAppformReqDto.getSocCode());
                appformReqDto.setOnOffType(rtnAppformReqDto.getOnOffType());
                appformReqDto.setEnggMnthCnt(rtnAppformReqDto.getEnggMnthCnt());

                appformSvc.insertDisPrmtApd(appformReqDto, "NAC");
            } else {
                svcMsg = simpleOsstXmlVO.getSvcMsg();
                rtnMap.put("RESULT_CODE", "5001");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
                rtnMap.put("RESULT_XML", simpleOsstXmlVO3.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO3.getResultCode());
                rtnMap.put("REQUEST_MSG", svcMsg);
                rtnMap.put("ERROR_NE_MSG", svcMsg);
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "5002");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "5003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
            return rtnMap;
        } catch (SelfServiceException e) {
            svcMsg = simpleOsstXmlVO.getSvcMsg();
            rtnMap.put("RESULT_CODE", "5004");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("REQUEST_MSG", svcMsg);
            rtnMap.put("ERROR_MSG", e.getMessage());
            rtnMap.put("ERROR_NE_MSG", e.getMessageNe());
            return rtnMap;
        } catch (InterruptedException e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        //7. 개통 확인(OP2)
        //- MCP_REQUEST_OSST CALL BACK 확인
        McpRequestOsstDto mcpRequestOsstRtn = null;
        try {
            mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_REQ_OPEN_RESULT);

            //5초 24번 120초 ...
            for (int i = 0; i < 50; i++) {
                Thread.sleep(5000);
                mcpRequestOsstRtn = appformSvc.getRequestOsst(mcpRequestOsstDto);
                if (mcpRequestOsstRtn != null) {
                    String rsltMsg = mcpRequestOsstRtn.getRsltMsg();
                    String rsltCd = mcpRequestOsstRtn.getRsltCd();

                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
                    rtnMap.put("RESULT_OBJ", mcpRequestOsstRtn);
                    rtnMap.put("RESULT_MSG", rsltMsg);
                    rtnMap.put("RESULT_CODE", rsltCd);

                    if (isSuccessOP2(rsltCd)) {
                        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                        break;
                    } else {
                        rtnMap.put("RESULT_CODE", "6001");
                        return rtnMap;
                    }
                }
            }

            if (mcpRequestOsstRtn == null) {
                rtnMap.put("RESULT_CODE", "6002");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
                rtnMap.put("ERROR_MSG", "DB 결과값 없음");
                return rtnMap;
            }
        } catch(IllegalArgumentException e) {
            rtnMap.put("RESULT_CODE", "6003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
            rtnMap.put("ERROR_MSG", "IllegalArgumentException");
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "6003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN_RESULT);
            rtnMap.put("ERROR_MSG", "Exception");
            return rtnMap;
        }

        rtnMap.put("tlphNo", tlphNo);
        rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
        rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());


        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 데이터 쉐어링 신규 개통요청
     *          신청서 테이블에 저장_데이터 쉐어링 DATA 생성
     * @param appformReqDto
     * contractNum :계약번호  NCN
     * reqUsimSn : 유심번호
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/saveDataSharingAjax.do")
    @ResponseBody
    public Map<String, Object> saveDataSharing(AppformReqDto appformReqDto , HttpSession session) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        if (StringUtils.isBlank(appformReqDto.getContractNum()) || StringUtils.isBlank(appformReqDto.getReqUsimSn())) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        logger.info("[WOO][WOO][WOO]appformReqDto.getOnOffType()===>" + appformReqDto.getOnOffType());

        //DATA설정
        Map<String, Object> dataInfoMap = fnSetDataOfdataSharing(appformReqDto);


        AppformReqDto rtnAppformReqDto = null;

        if (dataInfoMap != null) {
            Object obj = dataInfoMap.get("APP_FORM_REQ");

            if (obj instanceof AppformReqDto) {
                rtnAppformReqDto = (AppformReqDto) obj;
            } else {
                throw new McpCommonJsonException("0002", F_BIND_EXCEPTION);
            }
        } else {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }


        if ("0".equals(appformReqDto.getOnOffType()) || "3".equals(appformReqDto.getOnOffType())) {

            logger.info("[WOO][WOO][WOO] SEND SMS===>" );

            //문자 발송
            if (rtnAppformReqDto != null) {
                //SMS 전송 처리 ...
                List<NmcpCdDtlDto> cntpntShopIdList = NmcpServiceUtils.getCodeList(Constants.SEND_LMS_CNTPNT_GROP_CODE);//SMS발송 접점코드

                for (NmcpCdDtlDto param : cntpntShopIdList) {
                    if (param.getDtlCd().equals(appformReqDto.getCntpntShopId())) {

                        try {
                            String rMobile = rtnAppformReqDto.getCstmrMobileFn() + rtnAppformReqDto.getCstmrMobileMn() + rtnAppformReqDto.getCstmrMobileRn();
                            MspSmsTemplateMstDto mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_TEMPLATE_ID_NEW);

                            if (mspSmsTemplateMstDto != null) {
                                mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{resNo}", rtnAppformReqDto.getResNo()));
                                smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMobile, mspSmsTemplateMstDto.getText(),
                                        mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                        Constants.KAKAO_SENDER_KEY, String.valueOf(mspSmsTemplateMstDto.getTemplateId()));

                                //청소년 법정대리인에서도 SMS전송
                                if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
                                    String rMinorMobile = rtnAppformReqDto.getMinorAgentTelFn() + rtnAppformReqDto.getMinorAgentTelMn() + rtnAppformReqDto.getMinorAgentTelRn();
                                    if (StringUtil.checkMobile(rMinorMobile)) {
                                        smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), rMinorMobile, mspSmsTemplateMstDto.getText(),
                                                mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                                                Constants.KAKAO_SENDER_KEY, String.valueOf(mspSmsTemplateMstDto.getTemplateId()));
                                    }
                                }
                            }
                            break;
                        }  catch (RestClientException e) {
                            throw new McpCommonJsonException("API 연동 오류");
                        }   catch (Exception e) {        //예외 전환 처리
                            throw new McpCommonJsonException(COMMON_EXCEPTION);
                        }
                    }
                }
            }
        }


        session.setAttribute("opmdSvcNo", "11111111");  //이것이 뭘까???   실제로 개통한 전화 번호 같은데... 상담사 개통일 때 알수 없음...


        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
        rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());

        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 데이터 쉐어링 신규 개통요청
     *          신청서 테이블에 저장_데이터 쉐어링 DATA 생성
     *          사전체크 및 고객생성(PC0)
     * @param appformReqDto
     * contractNum :계약번호  NCN
     * reqUsimSn : 유심번호
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/saveDataSharingStep1Ajax.do")
    @ResponseBody
    public Map<String, Object> saveDataSharingStep1(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        // DATA 설정
        Map<String, Object> dataInfoMap = fnSetDataOfdataSharing(appformReqDto);

        if (dataInfoMap == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION); // 데이터 맵핑 실패
        }


        Object appFormReqObj = dataInfoMap.get("APP_FORM_REQ");
        if (!(appFormReqObj instanceof AppformReqDto)) {
            throw new McpCommonJsonException("0002", F_BIND_EXCEPTION); // 잘못된 형식
        }
        AppformReqDto rtnAppformReqDto = (AppformReqDto) appFormReqObj;

        // 모회선 계약번호와 고객아이디 추출
        String certContractNum = StringUtil.safeCastToString(dataInfoMap.get("CONTRANT_NUM"));
        String customerId = StringUtil.safeCastToString(dataInfoMap.get("CUSTOMER_ID"));

        logger.info("[WOO][WOO][WOO][saveDataSharingStep1Ajax]certContractNum===>" + certContractNum);
        logger.info("[WOO][WOO][WOO][saveDataSharingStep1Ajax]customerId===>" + customerId);

        //2. 사전체크 및 고객생성(PC0)
        MSimpleOsstXmlVO simpleOsstXmlVO = null;

        /*
         * 방어 로직 추가
         * PC2  0000
         * EVENT_CODE 사전체크 및 고객생성 결과 확인(PC2) 존재하며
         */
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_PC_RESULT);
        //mcpRequestOsstDto.setRsltCd("0000");
        int tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

        if (tryCount > 0) {
            //성공 처리
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            return rtnMap;
        }

        //PC0 ITL_SST_E0002
        /*
         * 접수받은 MVNO 접수번호 가 있습니다.
         * 성공 처리
         */
        tryCount = 0;
        mcpRequestOsstDto.setPrgrStatCd(Constants.EVENT_CODE_PRE_CHECK);
        mcpRequestOsstDto.setRsltCd("ITL_SST_E0002");
        tryCount = appformSvc.requestOsstCount(mcpRequestOsstDto);

        if (tryCount > 0) {
            //성공 처리
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
            rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            return rtnMap;
        }

        try {
            Thread.sleep(3000);

            // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam= new HashMap<>();
            osstParam.put("resNo", rtnAppformReqDto.getResNo());
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo", customerId);

            simpleOsstXmlVO = appformSvc.sendOsstService(osstParam, Constants.EVENT_CODE_PRE_CHECK);

            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("REQUEST_KET", rtnAppformReqDto.getRequestKey());
                rtnMap.put("RES_NO", rtnAppformReqDto.getResNo());
            } else {
                rtnMap.put("RESULT_CODE", "1001");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                return rtnMap;
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "1002");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "response massage is null.");
            return rtnMap;
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "1003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            return rtnMap;
        } catch (SelfServiceException e) {
            rtnMap.put("RESULT_CODE", "1004");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", e.getMessage());
            return rtnMap;
        } catch (Exception e) {
            rtnMap.put("RESULT_CODE", "1005");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_PRE_CHECK);
            rtnMap.put("ERROR_MSG", "Exception");
            return rtnMap;
        }

        return rtnMap;
    }



    /**
     * <pre>
     * 설명     : 데이터 쉐어링 신규 개통요청
     *           번호조회(NU1)
     *           번호예약/취소(NU2)
     * @param appformReqDto
     * contractNum :계약번호  NCN
     * reqUsimSn : 유심번호
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/saveDataSharingStep2Ajax.do")
    @ResponseBody
    public Map<String, Object> saveDataSharingStep2(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (StringUtils.isBlank(appformReqDto.getContractNum()) || StringUtils.isBlank(appformReqDto.getReqUsimSn())) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        AppformReqDto rtnAppformReqDto = SessionUtils.getAppformSession(); //신청 정보
        if (rtnAppformReqDto == null) {
            throw new McpCommonJsonException("0002", NO_SESSION_EXCEPTION);
        }


        McpRequestOsstDto sessRequestOsstDto = SessionUtils.getOsstDtoSession(); //개통 정보
        if (sessRequestOsstDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }


        //로그인 정보 확인 및 정회원 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        McpUserCntrMngDto resultOut = new McpUserCntrMngDto();

        if (userSession != null) {
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        } else {
            McpUserCntrMngDto out = SessionUtils.getNonmemberSharingInfo();
            if (out == null) {
                throw new McpCommonJsonException("0003", ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION);
            }
            resultOut = msfMypageSvc.selectCntrListNoLogin(out);
        }

        //서비스 번호 ContractNum 검증
        Boolean isOwn = false;
        String cntrMobileNo = "";
        String certContractNum = null;  // 모회선 계약번호
        String customerId = null;       // 고객아이디

        if (userSession != null) {
            for (McpUserCntrMngDto userCntrMng : cntrList) {
                if (userCntrMng.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                    isOwn = true;
                    cntrMobileNo = userCntrMng.getCntrMobileNo();
                    certContractNum = userCntrMng.getContractNum();
                    customerId = userCntrMng.getCustId();
                    break;
                }
            }
        } else {
            if (resultOut != null && resultOut.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                isOwn = true;
                cntrMobileNo = resultOut.getCntrMobileNo();
                certContractNum = resultOut.getContractNum();
                customerId = resultOut.getCustId();
            }
        }

        if (!isOwn) {
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.OWN_EXCEPTION);
        }

        //4. 번호조회(NU1)
        /*
          tlphNo:$chkRadioObj.val()
         ,tlphNoStatCd:$chkRadioObj.attr("tlphNoStatCd")
         ,tlphNoOwnCmpnCd:$chkRadioObj.attr("tlphNoOwnCmncCmpnCd")
         ,encdTlphNo:$chkRadioObj.attr("encdTlphNo")
        */
        String tlphNo = "";
        String tlphNoStatCd = "";
        String tlphNoOwnCmpnCd = "";
        String encdTlphNo = "";

        McpRequestDto mcpRequestDto = new McpRequestDto();
        mcpRequestDto.setRequestKey(rtnAppformReqDto.getRequestKey());
        mcpRequestDto.setResNo(rtnAppformReqDto.getResNo());
        mcpRequestDto.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));  //끝번호

        // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
        mcpRequestDto.setPrntsContractNo(certContractNum);
        mcpRequestDto.setCustomerId(customerId);

        List<MPhoneNoVo> phoneNmeverList = fnSearchNumber(mcpRequestDto);

        if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
            tlphNo = phoneNmeverList.get(0).getTlphNo();
            tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
            tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
            encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
        } else {
            // 끝번호 조회 안되면
            // 중간 번호로 다시 시도
            mcpRequestDto.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 8, cntrMobileNo.length() - 4));  //중간번호
            phoneNmeverList = fnSearchNumber(mcpRequestDto);
            if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                tlphNo = phoneNmeverList.get(0).getTlphNo();
                tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
                tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
                encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
            } else {
                // 중간 번호 조회 안되면
                // Random

                Random random;
                String a = "";
                String b = "";
                String c = "";
                String d = "";
                try {
                    random = SecureRandom.getInstance("SHA1PRNG");
                    a = String.valueOf(random.nextInt(10));
                    b = String.valueOf(random.nextInt(10));
                    c = String.valueOf(random.nextInt(10));
                    d = String.valueOf(random.nextInt(10));
                } catch (NoSuchAlgorithmException e1) {
                    throw new McpErropPageException(COMMON_EXCEPTION);
                }

                mcpRequestDto.setReqWantNumber(a + b + c + d);  //Random
                phoneNmeverList = fnSearchNumber(mcpRequestDto);
                if (phoneNmeverList != null && phoneNmeverList.size() > 0) {
                    tlphNo = phoneNmeverList.get(0).getTlphNo();
                    tlphNoStatCd = phoneNmeverList.get(0).getTlphNoStatCd();
                    tlphNoOwnCmpnCd = phoneNmeverList.get(0).getTlphNoOwnCmncCmpnCd();
                    encdTlphNo = phoneNmeverList.get(0).getEncdTlphNo();
                } else {
                    rtnMap.put("RESULT_CODE", "3001");
                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_SEARCH_NUMBER);
                    rtnMap.put("ERROR_MSG", "조회 전화번호 없음");
                    return rtnMap;
                }
            }
        }


        //5. 번호예약/취소(NU2)
        McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
        mcpRequestOsstDto.setTlphNo(tlphNo);
        mcpRequestOsstDto.setTlphNoStatCd(tlphNoStatCd);
        mcpRequestOsstDto.setTlphNoOwnCmpnCd(tlphNoOwnCmpnCd);
        mcpRequestOsstDto.setEncdTlphNo(encdTlphNo);
        mcpRequestOsstDto.setMvnoOrdNo(rtnAppformReqDto.getResNo());
        mcpRequestOsstDto.setOsstOrdNo(sessRequestOsstDto.getOsstOrdNo());
        mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_NUMBER_REG);
        mcpRequestOsstDto.setAsgnAgncId(rtnAppformReqDto.getAgentCode());
        mcpRequestOsstDto.setOpenSvcIndCd("03"); //03 고정 (3G)
        mcpRequestOsstDto.setIfType(WORK_CODE_RES);
        mcpRequestOsstDto.setRsltCd(OSST_SUCCESS);

        if (appformSvc.insertMcpRequestOsst(mcpRequestOsstDto)) {
            MSimpleOsstXmlVO simpleOsstXmlReg = null;
            ////번호예약(NU2)
            try {
                Thread.sleep(3000);

                // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
                Map<String, String> osstParam= new HashMap<>();
                osstParam.put("resNo", rtnAppformReqDto.getResNo());
                osstParam.put("gubun", WORK_CODE_RES);
                osstParam.put("prntsContractNo", certContractNum);
                osstParam.put("custNo", customerId);

                simpleOsstXmlReg = appformSvc.sendOsstService(osstParam, EVENT_CODE_NUMBER_REG);

                if (simpleOsstXmlReg.isSuccess()) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("TLPH_NO", tlphNo);
                } else {
                    rtnMap.put("RESULT_CODE", "4001");
                    rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                    rtnMap.put("RESULT_XML", simpleOsstXmlReg.getResponseXml());
                    rtnMap.put("ERROR_MSG", simpleOsstXmlReg.getResultCode());
                    return rtnMap;
                }
            } catch (McpMplatFormException e) {
                rtnMap.put("RESULT_CODE", "4002");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", "response massage is null.");
                return rtnMap;
            } catch (SocketTimeoutException e) {
                rtnMap.put("RESULT_CODE", "4003");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", "SocketTimeoutException");
                return rtnMap;

            } catch (SelfServiceException e) {
                rtnMap.put("RESULT_CODE", "4004");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
                rtnMap.put("ERROR_MSG", e.getMessage());
                return rtnMap;

            } catch (InterruptedException e) {
                logger.error("Exception e : {}", e.getMessage());
            }

        } else {
            rtnMap.put("RESULT_CODE", "4005");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_NUMBER_REG);
            rtnMap.put("ERROR_MSG", DB_EXCEPTION);
            return rtnMap;
        }

        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 데이터 쉐어링 신규 개통요청
     *           개통및수납(OP0)
     * @param appformReqDto
     * contractNum :계약번호  NCN
     * reqUsimSn : 유심번호
     * @return
     * @return: Map<String, Object>
     * </pre>
     */
    @RequestMapping(value = "/appform/saveDataSharingStep3Ajax.do")
    @ResponseBody
    public Map<String, Object> saveDataSharingStep3(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (StringUtils.isBlank(appformReqDto.getContractNum()) || StringUtils.isBlank(appformReqDto.getReqUsimSn())) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        AppformReqDto rtnAppformReqDto = SessionUtils.getAppformSession(); //신청 정보
        if (rtnAppformReqDto == null) {
            throw new McpCommonJsonException("0002", NO_SESSION_EXCEPTION);
        }


        McpRequestOsstDto sessRequestOsstDto = SessionUtils.getOsstDtoSession(); //개통 정보
        if (sessRequestOsstDto == null) {
            throw new McpCommonJsonException("0003", F_BIND_EXCEPTION);
        }


        //로그인 정보 확인 및 정회원 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        McpUserCntrMngDto resultOut = new McpUserCntrMngDto();

        if (userSession != null) {
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        } else {
            McpUserCntrMngDto out = SessionUtils.getNonmemberSharingInfo();
            if (out == null) {
                throw new McpCommonJsonException("0003", ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION);
            }
            resultOut = msfMypageSvc.selectCntrListNoLogin(out);
        }

        //서비스 번호 ContractNum 검증
        Boolean isOwn = false;
        String cntrMobileNo = "";
        String certContractNum = null;
        String customerId = null;

        if (userSession != null) {
            for (McpUserCntrMngDto userCntrMng : cntrList) {
                if (userCntrMng.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                    isOwn = true;
                    cntrMobileNo = userCntrMng.getCntrMobileNo();
                    certContractNum = userCntrMng.getContractNum();
                    customerId = userCntrMng.getCustId();
                    break;
                }
            }
        } else {
            if (resultOut != null && resultOut.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                isOwn = true;
                cntrMobileNo = resultOut.getCntrMobileNo();
                certContractNum = resultOut.getContractNum();
                customerId = resultOut.getCustId();
            }
        }

        if (!isOwn) {
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.OWN_EXCEPTION);
        }

        //청구계정번호
        String billAcntNo = msfMypageSvc.selectBanSel(appformReqDto.getContractNum());

        //6. 개통및수납(OP0)
        MSimpleOsstXmlVO simpleOsstXmlVO = new MSimpleOsstXmlVO();
        String svcMsg = "";
        try {
            Thread.sleep(3000);

            // osst 연동하는 동안 exception 발생 시, 고객아이디(customerId)와 모회선 계약번호도 이력에 남기기 위해 추가
            Map<String, String> osstParam= new HashMap<>();
            osstParam.put("resNo", rtnAppformReqDto.getResNo());
            osstParam.put("billAcntNo", billAcntNo);
            osstParam.put("prntsContractNo", certContractNum);
            osstParam.put("custNo", customerId);

            simpleOsstXmlVO = appformSvc.sendOsstService(osstParam, Constants.EVENT_CODE_REQ_OPEN);

            if (simpleOsstXmlVO.isSuccess()) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);

                /* 데이터 쉐어링 OP0 성공 후 평생할인 기적용 ISNERT */
                if (appformReqDto.getRequestKey() == 0) {
                    appformReqDto.setRequestKey(rtnAppformReqDto.getRequestKey());
                }
                appformReqDto.setResNo(rtnAppformReqDto.getResNo());
                appformReqDto.setSocCode(rtnAppformReqDto.getSocCode());
                appformReqDto.setOnOffType(rtnAppformReqDto.getOnOffType());
                appformReqDto.setEnggMnthCnt(rtnAppformReqDto.getEnggMnthCnt());

                appformSvc.insertDisPrmtApd(appformReqDto, "NAC");
            } else {
                svcMsg = simpleOsstXmlVO.getSvcMsg();
                rtnMap.put("RESULT_CODE", "5001");
                rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("REQUEST_MSG", svcMsg);
                rtnMap.put("ERROR_NE_MSG", svcMsg);
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "5002");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("ERROR_MSG", "response massage is null.");
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "5003");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("ERROR_NE_MSG", "시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다. ");
            return rtnMap;
        } catch (SelfServiceException e) {
            svcMsg = simpleOsstXmlVO.getSvcMsg();
            rtnMap.put("RESULT_CODE", "5004");
            rtnMap.put("EVENT_CODE", Constants.EVENT_CODE_REQ_OPEN);
            rtnMap.put("REQUEST_MSG", svcMsg);
            rtnMap.put("ERROR_MSG", e.getMessage());
            rtnMap.put("ERROR_NE_MSG", e.getMessageNe());
            return rtnMap;
        } catch (InterruptedException e) {
            logger.error("Exception e : {}", e.getMessage());
        }

        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 정책정보
     * @param  :
     * @return:
     * 날짜     : 2021. 03. 30.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/msp/getSalePlcyAjax.do")
    @ResponseBody
    public MspSalePlcyMstDto getSalePlcy(String salePlcyCd) {

        if (StringUtils.isBlank(salePlcyCd)) {
            throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
        }

        RestTemplate restTemplate = new RestTemplate();
        MspSalePlcyMstDto salePlcyMst = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSalePlcyMst", salePlcyCd, MspSalePlcyMstDto.class);

        return salePlcyMst;
    }


    /**
     * <pre>
     * 설명     : 정책정보 및 상품 정보
     * @param  :
     * @return :
     * SALE_PLCY_CD  D2021050305958
     * SALE_PLCY_NM  직영온라인_무약정유심_LTE_0501_수정
     * PRDT_ID K7006039
     * PRDT_IND_CD  CMN0045
     * 03	3G
     * 04	4G
     * 05	일반
     * 06	마이크로
     * 날짜     : 2022. 01. 20.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/msp/salePlcyMstListAjax.do")
    @ResponseBody
    public MspSalePlcyMstDto[] salePlcyMstList(MspSalePlcyMstDto salePlcyMst) {

        if (StringUtils.isBlank(salePlcyMst.getOrgnId())) {
            throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
        }

        RestTemplate restTemplate = new RestTemplate();
        MspSalePlcyMstDto[] salePlcyMstList = restTemplate.postForObject(apiInterfaceServer + "/msp/salePlcyMstList", salePlcyMst, MspSalePlcyMstDto[].class);

        return salePlcyMstList;
    }

    /**
     * <pre>
     * 설명     : 대표번호 조회
     * @param  :
     * @return :
     * </pre>
     */
    @RequestMapping(value = "/msp/mspPhoneInfoAjax.do")
    @ResponseBody
    public PhoneMspDto mspPhoneInfo(@RequestParam(required = true) String modelId) {
        RestTemplate restTemplate = new RestTemplate();
        PhoneMspDto phoneMspDto = restTemplate.postForObject(apiInterfaceServer + "/msp/mspPhoneInfo", modelId, PhoneMspDto.class);

        return phoneMspDto;
    }

    /**
     * <pre>
     * 설명     : USIM 유효성 체크(X85) 보안이슈 없나???
     * @param  :
     * @return:
     * 날짜     : 2021. 03. 30.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/msp/moscIntmMgmtAjax.do")
    @ResponseBody
    public Map<String, Object> moscIntmMgmt( HttpServletRequest request, @ModelAttribute JuoSubInfoDto juoSubInfo) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if (StringUtils.isBlank(juoSubInfo.getIccId())) {
            rtnMap.put("RESULT_CODE", "9998");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.F_BIND_EXCEPTION);
            return rtnMap;
        }

        /**
         * 불량유심 사용 제한
         */
        int failUsimCnt = this.usimService.selectFailUsims(juoSubInfo.getIccId());
        if (failUsimCnt > 0) {
            UserSessionDto sessionDto = SessionUtils.getUserCookieBean();

            if(!ObjectUtils.isEmpty(sessionDto) &&  StringUtils.isNotEmpty(sessionDto.getUserId())){
                juoSubInfo.setCustomerId(sessionDto.getUserId());
            }else{

                juoSubInfo.setCustomerId("");
                String reqSeq= request.getParameter("reqSeq");
                String resSeq= request.getParameter("resSeq");

                if(!StringUtil.isEmpty(reqSeq) && !StringUtil.isEmpty(resSeq)){
                    NiceLogDto niceLogDto= new NiceLogDto();
                    niceLogDto.setReqSeq(reqSeq);
                    niceLogDto.setResSeq(resSeq);
                    niceLogDto.setLimitMinute(90);  // 90분 이내의 이력 조회

                    NiceLogDto smsNiceLogDto = nicelog.getMcpNiceHistWithTime(niceLogDto);
                    if(smsNiceLogDto != null && !StringUtil.isEmpty(smsNiceLogDto.getsMobileNo())){
                        juoSubInfo.setCustomerId(smsNiceLogDto.getsMobileNo());
                    }
                }
            }

            this.usimService.updateFailUsims(juoSubInfo);
            rtnMap.put("RESULT_CODE", "0004");
            rtnMap.put("RESULT_MSG", "해당 유심은 사용이 불가합니다.<br>고객센터로 연락 바랍니다.");
            return rtnMap;
        }


        /**
         * 명의도용 추가피해 방지를 위한 유심재사용
         * 해당 쿼리 조회 후 데이터가 있으면 사용불가 유심입니다.
         * 개통이 불가능한 유심입니다.
         */
        int checkValidUsimCount = 0;
        RestTemplate restTemplate = new RestTemplate();
        checkValidUsimCount = restTemplate.postForObject(apiInterfaceServer + "/appform/checkValidUsimNo", juoSubInfo.getIccId(), Integer.class);

        if (checkValidUsimCount > 0) {
            rtnMap.put("RESULT_CODE", "0003");
            rtnMap.put("RESULT_MSG", "개통이 불가능한 유심입니다.");
            return rtnMap;
        }


        try {
            MoscInqrUsimUsePsblOutDTO moscInqrUsimUsePsblOutDTO = mPlatFormService.moscIntmMgmtSO(juoSubInfo);
            if (moscInqrUsimUsePsblOutDTO != null) {
                if ("Y".equals(moscInqrUsimUsePsblOutDTO.getPsblYn())) {
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                    rtnMap.put("RESULT_MSG", "사용가능");
                    //USIM 접점코드(ORGN_ID) 조회
                    String orgnId = appformSvc.getUsimOrgnId(juoSubInfo.getIccId());
                    rtnMap.put("USIM_ORGN_ID", orgnId);

                    // ============ STEP START ============
                    Map<String, String> resultMap = certService.isAuthStepApplyUrl(request);
                    if("Y".equals(resultMap.get("isAuthStep"))) {

                        String ncType= "";
                        if(request.getParameter("ncType") != null) ncType= request.getParameter("ncType");

                        // 유심번호
                        String[] certKey= {"urlType", "ncType", "reqUsimSn"};
                        String[] certValue= {"chkUsim", ncType, juoSubInfo.getIccId()};
                        certService.vdlCertInfo("C", certKey, certValue);
                    }
                    // ============ STEP END ============

                } else {
                    rtnMap.put("RESULT_CODE", "0001");
                    rtnMap.put("RESULT_MSG", moscInqrUsimUsePsblOutDTO.getRsltMsg()); //rsltMsg
                }
            } else {
                rtnMap.put("RESULT_CODE", "0002");
                rtnMap.put("RESULT_MSG", ExceptionMsgConstant.MPLATFORM_RESPONEXML_EMPTY_EXCEPTION); //rsltMsg
            }
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("RESULT_MSG", ExceptionMsgConstant.SOCKET_TIMEOUT_EXCEPTION);
        }

        return rtnMap;
    }

    @RequestMapping(value = "/appform/selRMemberAjax.do", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> selRMemberAjax(JuoSubInfoDto juoSubInfoDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String rrnShort = juoSubInfoDto.getCustomerSsn();
        if (StringUtils.isBlank(rrnShort)) {
            rtnMap.put("RESULT_CODE", "-1");
            rtnMap.put("RESULT_DESC", "비정상적인 접근입니다.");
            return rtnMap;
        }

        JuoSubInfoDto juoSubInfoRtn = appformSvc.selRMemberAjax(juoSubInfoDto);
        if(juoSubInfoRtn != null) {
            String customerSsn = juoSubInfoRtn.getCustomerSsn();
            String lglAgntSsn = juoSubInfoRtn.getLglAgntSsn();
            try {
                customerSsn = EncryptUtil.ace256Dec(customerSsn);
                lglAgntSsn = EncryptUtil.ace256Dec(lglAgntSsn);
            } catch (CryptoException e) {
                throw new McpCommonJsonException("0001", ACE_256_DECRYPT_EXCEPTION);
            }

            // 입력한 생년월일과 DB주민번호 값 비교
            if(!rrnShort.equals(customerSsn.substring(0,7))){
                rtnMap.put("RESULT_CODE", "0002");
                rtnMap.put("RESULT_DESC", "고객 정보가 일치하지 않습니다. <br> 확인 후 다시 입력 해 주세요 ");
                return rtnMap;
            }
            // 2024-12-17 인가된 사용자 체크
            Map<String, String> rtnChkAuthMap = msfMypageSvc.checkAuthUser(juoSubInfoDto.getCustomerLinkName(), customerSsn);
            if (!"0000".equals(rtnChkAuthMap.get("returnCode"))) {
                rtnMap.put("RESULT_CODE", rtnChkAuthMap.get("returnCode"));
                rtnMap.put("RESULT_MSG", rtnChkAuthMap.get("returnMsg"));
                return rtnMap;
            }
            juoSubInfoRtn.setCustomerSsn(customerSsn);
            juoSubInfoRtn.setLglAgntSsn(lglAgntSsn);
            if ("Y".equals(juoSubInfoRtn.getDvcChgYn())) {
                String ncType= ""; // 청소년 구분값

                rtnMap.put("CSTMR_TYPE", Constants.CSTMR_TYPE_NA);
                juoSubInfoRtn.setCustomerType(Constants.CSTMR_TYPE_NA);
                //외국인 청소년 구분
                if (customerSsn.length() > 6) {
                    String diviVal = customerSsn.substring(6, 7);
                    if ("|5|6|7|8".indexOf(diviVal) > -1) {
                        rtnMap.put("CSTMR_TYPE", Constants.CSTMR_TYPE_FN);
                        juoSubInfoRtn.setCustomerType(Constants.CSTMR_TYPE_FN);
                    } else {
                        //나이 확인
                        int age = NmcpServiceUtils.getAge(customerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));
                        if (19 > age) {
                            ncType= "0";
                            rtnMap.put("CSTMR_TYPE", Constants.CSTMR_TYPE_NM);
                            juoSubInfoRtn.setCustomerType(Constants.CSTMR_TYPE_NM);
                        }
                    }
                }

                // ============ STEP START ============
                String birthDate= (customerSsn.length() < 7) ? customerSsn : customerSsn.substring(0,6);

                // 대리인구분값, 이름, 생년월일, 기기변경 휴대폰번호, 계약번호
                String[] certKey= {"urlType", "ncType", "name", "birthDate"
                        , "mobileNo", "contractNum"};
                String[] certValue= {"changeAuth", ncType, juoSubInfoDto.getCustomerLinkName(), birthDate
                        , juoSubInfoDto.getSubscriberNo(), juoSubInfoRtn.getContractNum()};
                certService.vdlCertInfo("C", certKey, certValue);
                // ============ STEP END ============

                //session저장 처리
                SessionUtils.saveChangeAut(juoSubInfoRtn);

                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } else if ("E".equals(juoSubInfoRtn.getDvcChgYn())) {
                rtnMap.put("RESULT_CODE", "0004");
                rtnMap.put("RESULT_DESC", "약정기간이 6개월 이상 남아있어 온라인으로 기기변경 신청이 불가합니다.<br>기기변경을 원하실 경우 고객센터(1899-5000)로 신청을 부탁드립니다. <br>확인버튼을 누르실 경우 휴대폰 화면으로 이동합니다.");
            } else {
                rtnMap.put("RESULT_CODE", "0003");
                rtnMap.put("RESULT_DESC", "고객님께서는 기기변경 할 회선이 없습니다. <br>감사합니다.");
            }
        } else {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("RESULT_DESC", "입력하신 정보는 kt M모바일에 가입된 정보가 아니거나 사용중인 상태가 아닙니다 <br> 확인 후 다시 입력 해 주세요.");
        }
        return rtnMap;
    }

    /*
     *  신청서 임시 저장 처리
     */
    @RequestMapping(value = "/appForm/updateRequestTemp.do")
    @ResponseBody
    public Boolean updateRequestTemp(AppformReqDto appformReqDto) {

        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {

            // ============ STEP START ============
            if("01".equals(appformReqDto.getTmpStep()) && "Y".equals(appformReqDto.getTelAdvice())){
                // SMS인증 완료여부 확인
                if(0 >= certService.getModuTypeStepCnt("smsAuth", "")) {
                    // 전체스텝 초기화
                    certService.delStepInfo(1);
                }
            }
            // ============ STEP END ============

            //!"Y".contentEquals(appformReqDto.getTelAdvice()) &&
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        // ============ STEP START ============
        //해피콜인 경우 sessNiceRes == null
        if("01".equals(appformReqDto.getTmpStep())){

            String[] certKey= null;
            String[] certValue= null;

            String authRrn= null;   // 본인인증에 사용된 주민번호
            String authName= null;  // 본인인증에 사용된 이름
            String urlType= "chkRequestTemp";
            String ncType= "";

            if(CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())){ // 미성년자

                // 기기변경인 경우, 청소년 본인정보 추가검증
                String operType= appformReqDto.getOperType();
                if(OPER_TYPE_CHANGE.equals(operType) || OPER_TYPE_EXCHANGE.equals(operType)){

                    // 대리인구분값, 이름, 생년월일
                    certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
                    certValue= new String[]{urlType, "0", appformReqDto.getCstmrName(), appformReqDto.getCstmrNativeRrn()};

                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                        throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
                    }
                }

                urlType= "chkRequestAgentTemp";
                ncType= "1";
                authRrn = appformReqDto.getMinorAgentRrn(); // 대리인 주민번호
                authName= appformReqDto.getMinorAgentName(); // 대리인 이름

            }else if(CSTMR_TYPE_FN.equals((appformReqDto.getCstmrType()))){ // 외국인
                authRrn = appformReqDto.getCstmrForeignerRrn();
                authName= appformReqDto.getCstmrName();
            }else{
                authRrn= appformReqDto.getCstmrNativeRrn();
                authName= appformReqDto.getCstmrName();
            }

            // 대리인구분값, 이름, 생년월일
            certKey= new String[]{"urlType", "ncType", "name", "birthDate"};
            certValue= new String[]{urlType, ncType, authName, authRrn};

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
            }
        }
        // ============ STEP END ============

        if (sessNiceRes != null) {
            appformReqDto.setSelfCstmrCi(sessNiceRes.getConnInfo());
            appformReqDto.setRip(ipstatisticService.getClientIp());
        }

        appformSvc.updateRequestTempStep1(appformReqDto);
        return true;
    }

    /*
     *  신청서 임시 저장 처리
     */
    @RequestMapping(value = "/appForm/updateRequestTemp3.do")
    @ResponseBody
    public Boolean updateRequestTempStep3(AppformReqDto appformReqDto) {

        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        appformSvc.updateRequestTempStep3(appformReqDto);
        return true;
    }

    /*
     *  신청서 임시 저장 처리
     */
    @RequestMapping(value = "/appForm/updateRequestTemp4.do")
    @ResponseBody
    public Boolean updateRequestTempStep4(AppformReqDto appformReqDto) {

        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        appformSvc.updateRequestTempStep4(appformReqDto);
        return true;
    }

    /*
     *  신청서 임시 저장 처리
     */
    @RequestMapping(value = "/appForm/updateRequestTemp5.do")
    @ResponseBody
    public Boolean updateRequestTempStep5(AppformReqDto appformReqDto) {

        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonJsonException("0001", F_BIND_EXCEPTION);
        }

        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        appformSvc.updateRequestTempStep5(appformReqDto);
        return true;
    }

    //요금제 설계 임시저장한 부가서비스 목록
    @RequestMapping(value = "/appForm/getAdditionTempListAjax.do")
    @ResponseBody
    public List<String> getAdditionTempList(
            @ModelAttribute AppformReqDto appformReqDto) {
        if (!"Y".contentEquals(appformReqDto.getTelAdvice()) && 0 >= appformReqDto.getRequestKey()) {
            //throw new McpCommonJsonException("0001" ,F_BIND_EXCEPTION);
            return null;
        }

        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            //throw new McpCommonJsonException("0003" ,NICE_CERT_EXCEPTION);
            return null;
        }

        //List<String> list = new ArrayList<MPhoneNoVo>();
        List<String> rtnList = appformSvc.getAdditionTempList(appformReqDto);
        return rtnList != null ? rtnList : null ;
    }

    //유심상품 정보 조회
    @RequestMapping(value = "/appForm/getUsimBasInfoAjax.do")
    @ResponseBody
    public UsimBasDto getUsimBasInfo(
            @ModelAttribute UsimBasDto usimBasObj) {
        if (StringUtils.isBlank(usimBasObj.getPrdtId())) {
            return null;
        }
        UsimBasDto usimBasDto = appformSvc.getUsimBasInfo(usimBasObj);
        if (usimBasDto == null) {
            return null;
        } else {
            return usimBasDto;
        }

    }

    /**
     * 설명 : 바로 배송 가능 시간 체크
     *
     * @return
     * @throws ParseException
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping("/appform/isSelfDlvryAjax.do")
    @ResponseBody
    public Map<String, Object> isSelfDlvry(@RequestParam(required = false, defaultValue = "") String usimProdType) throws ParseException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        boolean isNowDlvry = true;
        boolean isDlvry = false ;
        String strMsg = "";
        String resultCode = AJAX_SUCCESS;

        /**
         * 바로 배송 여부 신청 여부
         */
        if (!DateTimeUtil.isMiddleTime("09:00", "22:00")) {  //if (!DateTimeUtil.isMiddleTime("09:00", "22:00")) {
            strMsg = "";
            resultCode = "002";   //기존 소스 호환을 위해.. 존재...  필요 없는데...
            isNowDlvry = false ;
        } else {
            String nowDate = DateTimeUtil.getFormatString("yyyyMMdd");
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(Constants.DLVRLY_ENEXCEPTION_DATE, nowDate);
            if (nmcpCdDtlDto != null) {
                strMsg = nmcpCdDtlDto.getDtlCdDesc();
                resultCode = "001";   //기존 소스 호환을 위해.. 존재...  필요 없는데...
                isNowDlvry = false ;
            }
        }


        /**
         * 유심 유형에 따른 바로배송 가능 여부 처리
         */
        if (!"".equals(usimProdType) && isNowDlvry) {
            // 확장자 getExpnsnStrVal2 Y 인것만 바로 배송 가능
            NmcpCdDtlDto dlvryCdDtl = NmcpServiceUtils.getCodeNmDto(Constants.USIM_PROD_ID_GROP_CODE, usimProdType);
            if (dlvryCdDtl != null){
                if (!"Y".equals(dlvryCdDtl.getExpnsnStrVal2())){
                    if (AJAX_SUCCESS.equals(resultCode) ) {
                        resultCode = "003";   //기존 소스 호환을 위해.. 존재...  필요 없는데...
                        isNowDlvry = false;
                        NmcpCdDtlDto dlvryCdErrorMsgDtl = NmcpServiceUtils.getCodeNmDto("usimProdInfoErrorDesc", usimProdType);
                        if (dlvryCdErrorMsgDtl != null){
                            strMsg = dlvryCdErrorMsgDtl.getDtlCdDesc();
                        }
                    }
                }
            } else {
                isNowDlvry = false;
            }
        }

        /**
         * 택배 표현 여부
         */
        if (!isNowDlvry) {
            //바로 배송이 신청 하지 못하면.. 배송 무조건 표현
            isDlvry = true ;
        } else {
            NmcpCdDtlDto viewDto = NmcpServiceUtils.getCodeNmDto(DLVRLY_VIEW_YN, "VIEW"); // 택배 강제 노출
            if (viewDto != null && "Y".equals(viewDto.getDtlCdNm())) {
                isDlvry = true ;
            }
        }

        rtnMap.put("IS_NOW_DLVRY", isNowDlvry);  // 바로 배송 여부
        rtnMap.put("IS_DLVRY", isDlvry);  //택배 여부     <=== 무슨 의미가 있을까?????? 필요 없을것 같은데...
        rtnMap.put("STR_MSG", strMsg);  // 바로 배송 여부
        rtnMap.put("isTimeMsg", strMsg);  //기존 소스 호환을 위해.. 존재...  필요 없는데...
        rtnMap.put("RESULT_CODE", resultCode);


        return rtnMap;
    }


    @RequestMapping(value = {"/appForm/appSimpleInfo.do", "/m/appForm/appSimpleInfo.do"})
    public String appSimpleInfo() {
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "redirect:/m/appForm/withUsim.do?tab=appSimpleinfo";
        } else {
            return "redirect:/appForm/withUsim.do?tab=appSimpleinfo";
        }
    }

    /**
     * 설명 : 가입신청(상담사 연결) 안내 화면
     *
     * @return
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping(value = {"/appForm/appCounselorInfo.do", "/m/appForm/appCounselorInfo.do"})
    public String appCounselorInfo() {
         if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "redirect:/m/appForm/withUsim.do?tab=appCounselorInfo";
        } else {
            return "redirect:/appForm/withUsim.do?tab=appCounselorInfo";
        }
    }

    /**
     * 설명 : 유심 /휴대폰 요금제 설계 화면
     *
     * @param prodCommendDto
     * @param phoneProdBasDto
     * @param operType
     * @param hndsetModelId
     * @param instNom
     * @param bannerCd
     * @param rateCd
     * @param sprtTp
     * @param model
     * @return
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping(value = {"/appForm/appFormDesignView.do", "/m/appForm/appFormDesignView.do"})
    public String appformDesignView(NmcpProdCommendDto prodCommendDto, BindingResult bind, PhoneProdBasDto phoneProdBasDto,
                                    @RequestParam(defaultValue = "") String operType,
                                    @RequestParam(defaultValue = "") String hndsetModelId,
                                    @RequestParam(defaultValue = "") String instNom,
                                    @RequestParam(defaultValue = "") String bannerCd,
                                    @RequestParam(defaultValue = "") String rateCd,
                                    @RequestParam(defaultValue = "") String sprtTp,
                                    @RequestParam(defaultValue = "") String prdtIndCd,
                                    @RequestParam(defaultValue = "") String uploadPhoneSrlNo,
                                    Model model) {

        if (bind.hasErrors()) {
            throw new McpCommonException(BIDING_EXCEPTION);
        }


        if (StringUtils.isBlank(prodCommendDto.getPrdtSctnCd())) {
            prodCommendDto.setPrdtSctnCd("LTE");
        }

        if (StringUtils.isNotBlank(prodCommendDto.getRateCd())) {
            MspRateMstDto rateInfo = fCommonSvc.getMspRateMst(prodCommendDto.getRateCd());
            if (rateInfo != null && rateInfo.getDataType() != null) {
                prodCommendDto.setPrdtSctnCd(rateInfo.getDataType());
            }
        }

        String prodCtgType = "";
        String temPrdtIndCd = prdtIndCd;
        String temUploadPhoneSrlNo = uploadPhoneSrlNo;

        String[] certKey= null;     // step검증 key값
        String[] certValue= null;   // step검증 value값

        AppformReqDto appformReqTemp = null;
        if (prodCommendDto.getRequestKey() != null && !"".equals(prodCommendDto.getRequestKey())) {
            //임시저장키로 데이터 셋팅
            appformReqTemp = appformSvc.getAppFormTemp(Long.parseLong(prodCommendDto.getRequestKey()));
        } else {
            if ("".equals(StringUtil.NVL(prodCommendDto.getOnOffType(), "")) && "".equals(StringUtil.NVL(prodCommendDto.getRateCd(), ""))
                    && "".equals(StringUtil.NVL(prodCommendDto.getOperType(), "")) && "".equals(StringUtil.NVL(prodCommendDto.getPrdtId(), ""))
                    && "".equals(StringUtil.NVL(prodCommendDto.getPrdtSctnCd(), ""))) {

                UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
                if (userSessionDto != null) {
                    appformReqTemp = appformSvc.getAppFormTempById(userSessionDto.getUserId());
                }
            }
        }

        if (appformReqTemp != null) {
            prodCommendDto.setOnOffType(appformReqTemp.getOnOffType());
            prodCommendDto.setOperType(appformReqTemp.getOperType());
            prodCommendDto.setModelId(appformReqTemp.getModelId());
            prodCommendDto.setPrdtSctnCd(appformReqTemp.getPrdtSctnCd());
            prodCommendDto.setModelMonthly(appformReqTemp.getModelMonthly());
            prodCommendDto.setRateCd(appformReqTemp.getSocCode());
            phoneProdBasDto.setProdId(appformReqTemp.getProdId());
            if ("Y".equals(appformReqTemp.getSesplsYn())) {
                prodCtgType = "03";
            }

            if ("09".equals(appformReqTemp.getUsimKindsCd()) || "11".equals(appformReqTemp.getUsimKindsCd())) {
                temUploadPhoneSrlNo = appformReqTemp.getUploadPhoneSrlNo() + "";

                if ("09".equals(appformReqTemp.getUsimKindsCd()) && StringUtils.isEmpty(appformReqTemp.getPrntsContractNo())) {
                    temPrdtIndCd = "10";
                    // ============ STEP START ============
                    // step 초기화 및 메뉴명 세팅
                    SessionUtils.removeCertSession();
                    // ** esimForm의 operType 설정은 step2 이후부터 진행. appform.do 진입시 상세 페이지명 재설정
                    SessionUtils.setPageSession("esimForm");

                    certKey= new String[]{"urlType", "uploadPhoneSrlNo"};
                    certValue= new String[]{"saveEsimSrlNo", temUploadPhoneSrlNo};
                    certService.vdlCertInfo("E", certKey, certValue);
                    // ============ STEP END ============
                } else {
                    temPrdtIndCd = "11";
                    // ============ STEP START ============
                    Map<String, String> resOjb= mypageUserService.selectContractObj("","",appformReqTemp.getPrntsContractNo()) ;
                    if (resOjb == null) throw new McpCommonException(F_BIND_EXCEPTION);

                    // step 초기화 및 메뉴명 세팅
                    SessionUtils.removeCertSession();
                    // ** esimWatchForm의 operType 설정은 step3 이후부터 진행. appform.do 진입시 상세 페이지명 재설정
                    SessionUtils.setPageSession("esimWatchForm");

                    // 청소년 확인
                    String ncType= certService.getNcTypeForCrt(resOjb.get("USER_SSN"), "");

                    // 대리인구분값, 이름, 생년월일, 계약번호
                    certKey= new String[]{"urlType", "ncType", "name", "birthDate", "contractNum"};
                    certValue= new String[]{"memberAuth", ncType, resOjb.get("SUB_LINK_NAME"), resOjb.get("USER_SSN"), appformReqTemp.getPrntsContractNo()};
                    certService.vdlCertInfo("C", certKey, certValue);

                    // 대리인구분값, esimSeq
                    certKey= new String[]{"urlType", "ncType", "uploadPhoneSrlNo"};
                    certValue= new String[]{"saveWatchSrlNo", ncType, temUploadPhoneSrlNo};
                    certService.vdlCertInfo("E", certKey, certValue);
                    // ============ STEP END ============
                }
            }
        }

        McpUploadPhoneInfoDto uploadEPhone = null;

        //eSIM 검증
        if ("11".equals(temPrdtIndCd)) {
            if (StringUtils.isBlank(temUploadPhoneSrlNo) || 1 > appformSvc.checkUploadPhoneInfoCount(temUploadPhoneSrlNo)) {
                throw new McpCommonException(PHONE_EID_NULL_EXCEPTION);
            }
            int uploadPhoneSrlNoCnt = Integer.parseInt(temUploadPhoneSrlNo);
            uploadEPhone = appformSvc.getUploadPhoneInfo(uploadPhoneSrlNoCnt);

            //logger.info("uploadEPhone==>"+ObjectUtils.convertObjectToString(uploadEPhone));

            // ============ STEP START ============
            if(uploadEPhone != null){
                if(StringUtils.isEmpty(uploadEPhone.getPrntsContractNo())){ // ESIM
                    // esimSeq
                    certKey= new String[]{"urlType", "uploadPhoneSrlNo"};
                    certValue= new String[]{"chkEsimSrlNo", temUploadPhoneSrlNo};

                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                        throw new McpCommonException(vldReslt.get("RESULT_DESC"));
                    }
                } else { // ESIM_WATCH

                    String ncType= certService.getNcTypeForCrt("", uploadEPhone.getPrntsContractNo());

                    // 대리인구분값, esimSeq, 계약번호
                    certKey= new String[]{"urlType", "ncType", "uploadPhoneSrlNo", "contractNum"};
                    certValue= new String[]{"chkWatchSrlNo", ncType, temUploadPhoneSrlNo, uploadEPhone.getPrntsContractNo()};

                    Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                    if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                        throw new McpCommonException(vldReslt.get("RESULT_DESC"));
                    }

                }
            }
            // ============ STEP END ============
        } else if ("10".equals(temPrdtIndCd)) {

            // ================= STEP START =================
            // step 초기화 및 메뉴명 세팅
            SessionUtils.removeCertSession();
            // ** esimForm의 operType 설정은 step2 이후부터 진행. appform.do 진입시 상세 페이지명 재설정
            SessionUtils.setPageSession("esimForm");
            // ================= STEP END =================
        }

        model.addAttribute("uploadEPhone", uploadEPhone);


        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            if ("5".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("7");
            } else if ("0".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("3");
            } else if ("6".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("8");
            }
        } else {
            if ("7".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("5");
            } else if ("3".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("0");
            } else if ("8".equals(prodCommendDto.getOnOffType())) {
                prodCommendDto.setOnOffType("6");
            }
        }

        model.addAttribute("serverName", serverName);
        model.addAttribute("ProdCommendDto", prodCommendDto);

        if (!"".equals(StringUtil.NVL(phoneProdBasDto.getProdId(), ""))) {

            //prodId=4677&hndsetModelId=K7034703&rateCd=PL21BN496&instNom=24
            //신규 페이지로 이동
            if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                return "forward:/m/product/phone/phoneView.do";
            } else {
                return "forward:/product/phone/phoneView.do";
            }
        }

        /*
         * 유심 유형 (10 eSIM 외 의미 없음 )
         * eSIM 구분 처리
         */
        model.addAttribute("prdtIndCd", temPrdtIndCd);
        model.addAttribute("uploadPhoneSrlNo", temUploadPhoneSrlNo);


        //menucode set
        String gnbMenuCode = "";
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
            String referer = StringUtil.NVL((String) request.getHeader("REFERER"), "");
            if (referer.indexOf("appSimpleInfo.do") > -1 || referer.indexOf("openMarketInfo.do") > -1 || referer.indexOf("storeInfo.do") > -1
                    || referer.indexOf("emart24.do") > -1 || referer.indexOf("cu.do") > -1 || referer.indexOf("cspace.do") > -1
                    || referer.indexOf("ministop.do") > -1 || referer.indexOf("7-11.do") > -1 || referer.indexOf("gs25.do") > -1
                    || referer.indexOf("storyway.do") > -1) {

                gnbMenuCode = "PCMENU0102"; //셀프개통 PC

                if ("0".equals(prodCommendDto.getOnOffType()) || "3".equals(prodCommendDto.getOnOffType())) {
                    gnbMenuCode = "PCMENU0103"; //셀프개통 PC
                }




                //if ("Y".equals(NmcpServiceUtils.isMobile())) {
                if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {    //셀프개통 M
                    gnbMenuCode = "MOMENU0102";
                    if ("0".equals(prodCommendDto.getOnOffType()) || "3".equals(prodCommendDto.getOnOffType())) {
                        gnbMenuCode = "MOMENU0103"; //셀프개통 PC
                    }
                }
            } else {
                gnbMenuCode = "PCMENU0103"; //온라인가입신청(상담사) PC
                //if ("Y".equals(NmcpServiceUtils.isMobile())) {
                if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                    gnbMenuCode = "MOMENU0103"; //온라인가입신청(상담사) M
                    if ("03".equals(StringUtil.NVL(phoneProdBasDto.getProdCtgType(), prodCtgType))) { //자급제
                        gnbMenuCode = "MOMENU020102"; //온라인가입신청(상담사) 자급제 M
                    } else if ("02".equals(StringUtil.NVL(phoneProdBasDto.getProdCtgType(), prodCtgType))) { //중고폰
                        gnbMenuCode = "MOMENU0202"; //온라인가입신청(상담사) 중고폰 M
                    } else if ("01".equals(StringUtil.NVL(phoneProdBasDto.getProdCtgType(), prodCtgType))) { //공시지원
                        gnbMenuCode = "MOMENU020101"; //온라인가입신청(상담사) 공시지원 M
                    }
                } else {
                    if ("03".equals(StringUtil.NVL(phoneProdBasDto.getProdCtgType(), prodCtgType))) { //자급제
                        gnbMenuCode = "PCMENU020102"; //온라인가입신청(상담사) 자급제 PC
                    } else if ("02".equals(StringUtil.NVL(phoneProdBasDto.getProdCtgType(), prodCtgType))) { //중고폰
                        gnbMenuCode = "PCMENU0202"; //온라인가입신청(상담사) 중고폰 PC
                    } else if ("01".equals(StringUtil.NVL(phoneProdBasDto.getProdCtgType(), prodCtgType))) { //공시지원
                        gnbMenuCode = "PCMENU020101"; //온라인가입신청(상담사) 공시지원 PC
                    }
                }
            }
        } catch (Exception e) {
            logger.error("Exception referrer : {}", e.getMessage());
        }


        SessionUtils.setGnbMenuCode(gnbMenuCode);

        // ex) pc 에서 공유하기 -> 폰으로 공유 링크 클릭시
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/appForm/appFormDesignView";
        } else {
            return "/portal/appForm/appFormDesignView";
        }
    }

    /**
     * 설명 : 요금제 설계 임시 저장
     *
     * @param appformReqDto
     * @return
     * @Author : jsh
     * @Date : 2021.12.30
     */
    @RequestMapping(value = {"/appForm/insertAppFormTempSave.do", "/m/appForm/insertAppFormTempSave.do"})
    @ResponseBody
    public Map<String, String> insertAppFormTempSave(@ModelAttribute AppformReqDto appformReqDto) {

        Map<String, String> result = new HashMap<String, String>();

        if (StringUtils.isBlank(appformReqDto.getCntpntShopId())) {
            appformReqDto.setCntpntShopId(Constants.CONTPNT_SHOP_ID_MSHOP);
        }


        long requestKey = appformSvc.getTempRequestKey();
        appformReqDto.setRequestKey(requestKey);

        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();
        if (userSessionDto != null) {
            appformReqDto.setCretId(userSessionDto.getUserId());
        } else {
            appformReqDto.setCretId(NON_MEMBER_ID);
        }

        // 이벤트코드 검증
        if(!StringUtil.isEmpty(appformReqDto.getEvntCdPrmt())){
            GiftPromotionBas giftPromotVal = new GiftPromotionBas();
            giftPromotVal = eventCodeSvc.getEventchk(appformReqDto.getEvntCdPrmt());

            if(giftPromotVal == null || giftPromotVal.getEcpSeq() ==null){
                throw new McpCommonJsonException("-1", "입력하신 이벤트 코드는 사용이 불가합니다.");
            }
        }

        int chk = appformSvc.insertAppFormTempSave(appformReqDto);
        if (chk > 0) {
            chk = appformSvc.insertSaleinfoTempSave(appformReqDto);
            result.put("resultCd", "0000");
            result.put("requestTempKey", String.valueOf(requestKey));
        } else {
            result.put("resultCd", "-1");
            result.put("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
        }

        if ("Y".equals(StringUtil.NVL(appformReqDto.getSesplsYn(), ""))) {
            int chk2 = appformSvc.insertAppFormApdTempSave(appformReqDto);
            if (chk2 > 0) {
                chk2 = appformSvc.insertSaleinfoApdTempSave(appformReqDto);
                result.put("resultCd", "0000");
                result.put("requestTempKey", String.valueOf(requestKey));
            } else {
                result.put("resultCd", "-1");
                result.put("msg", "처리중 오류가 발생하였습니다. 잠시후 다시 이용해 주세요.");
            }
        }
        return result;
    }

    //상품정보 조회  SESPLS_PROD_ID
    @RequestMapping(value = "/appForm/phoneProdInfoAjax.do")
    @ResponseBody
    public PhoneProdBasDto phoneProdInfo(
            @ModelAttribute PhoneProdBasDto phoneProdBas) {
        if (StringUtils.isBlank(phoneProdBas.getProdId())) {
            return null;
        }
        return phoneService.findPhoneProdBasDtoByProdId(phoneProdBas);
    }

    /*-------------------------------오픈마켓 시작----------------------------*/
    @RequestMapping(value = {"/request/marketRequest.do", "/m/request/marketRequest.do"})
    public String marketRequest(HttpServletRequest request
            , @RequestParam(value = "a", required = false, defaultValue = "") String pCntpntShopId          // 조직ID
            , @RequestParam(value = "o", required = false, defaultValue = "") String pOperType              // 가입유형
            , @RequestParam(value = "s", required = false, defaultValue = "") String pModelSalePolicyCode   // 판매정책id
            , @RequestParam(value = "p", required = false, defaultValue = "") String pReqModelName          // 제품ID
            , @RequestParam(value = "y", required = false, defaultValue = "0") String pEnggMnthCnt           // 약정기간
            , @RequestParam(value = "g", required = false, defaultValue = "") String pSprtTp                // 약정기간
            , @RequestParam(value = "n", required = false, defaultValue = "") String pModelMonthly          // 할부기간
            , @RequestParam(value = "u", required = false, defaultValue = "") String openMarketReferer      // 오픈마켓 입점지
            , @RequestParam(value = "q", required = false, defaultValue = "") String cstmrId          // 사용자 ID
            , @RequestParam(value = "r", required = false, defaultValue = "") String pSocCode          // 요금상품
            , Model model) {


        SessionUtils.saveChangeAut(null); // 기기변경 세션 제거

        //parameter 값 중 하나라도 없으면 에러
        if ("".equals(pCntpntShopId) || "".equals(pOperType) || "".equals(pModelSalePolicyCode) || "".equals(pReqModelName)) {
            throw new McpCommonException(INVALID_PARAMATER_EXCEPTION, "/main.do");
        }

        //쿠팡 접점일때.. cstmrId 필수 .. 처리
        if (Constants.CONTPNT_SHOP_ID_COUPANG.equals(pCntpntShopId) && "".equals(cstmrId)) {
            throw new McpCommonException(INVALID_PARAMATER_EXCEPTION, "/main.do");
        }

        String pOperTypeTem = pOperType;
        //pOperType이 3이 빠져있으면 3을 추가 20160926 kjw
        if (pOperType.length() == 3) {
            pOperTypeTem = pOperType + "3";
        } else {
            pOperTypeTem = pOperType;
        }

        String temp = "";
        boolean chechopenMarket = false;
        if (openMarketReferer != null && !openMarketReferer.equals("")) {
            //공백제거 시작
            temp = openMarketReferer.replaceAll("\\p{Z}", "");
            //공백제거 끝
            //파라미터 검증 시작
            chechopenMarket = !Pattern.matches("^[a-zA-Z0-9]*$", temp);
            if (temp.length() > 10) {
                chechopenMarket = true;
            }
            //파라미터 검증 끝
        }

        //오픈마켓 입점지 파라미터 검증
        if (chechopenMarket) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION, "/main.do");
        }

        //안면인증 세션 초기화
        SessionUtils.initializeFathSession();

        // ================= STEP START =================
        // step 초기화 및 메뉴명 세팅
        SessionUtils.removeCertSession();

        StringBuilder pageSb= new StringBuilder();

        if(OPER_TYPE_NEW.equals(pOperTypeTem)) pageSb.append("Nac");
        else if(OPER_TYPE_MOVE_NUM.equals(pOperTypeTem)) pageSb.append("Mnp");
        else if(OPER_TYPE_CHANGE.equals(pOperTypeTem)) pageSb.append("Hcn");
        else if(OPER_TYPE_EXCHANGE.equals(pOperTypeTem)) pageSb.append("Hdn");

        pageSb.append("AgentForm");

        // ** 대리점은 operType을 마지막까지 바꿀 수 있음 > 상세 페이지명 설정은 주문완료 단계에서 한번 더 진행
        SessionUtils.setPageSession(pageSb.toString());
        // ================= STEP END =================

        AppformReqDto appformReqDto = new AppformReqDto();
        appformReqDto.setCntpntShopId(pCntpntShopId);
        appformReqDto.setModelSalePolicyCode(pModelSalePolicyCode);
        appformReqDto.setOperType(pOperTypeTem);
        appformReqDto.setReqModelName(pReqModelName);
        appformReqDto.setSprtTp(pSprtTp);

        AppformReqDto rtnAppformReq = appformSvc.getMarketRequest(appformReqDto);
        if (rtnAppformReq == null) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION, "/main.do");
        }

        if ("01".contentEquals(rtnAppformReq.getPlcySctnCd())) {
            rtnAppformReq.setReqBuyType(Constants.REQ_BUY_TYPE_PHONE);
        } else if ("02".contentEquals(rtnAppformReq.getPlcySctnCd())) {
            rtnAppformReq.setReqBuyType(Constants.REQ_BUY_TYPE_USIM);
        }
        rtnAppformReq.setOpenMarketReferer(openMarketReferer);
        rtnAppformReq.setSiteReferer(Constants.SITE_REFERER_MARKET);
        rtnAppformReq.setCstmrJejuId(cstmrId);
        rtnAppformReq.setSocCode(pSocCode);
        rtnAppformReq.setPrdtId(rtnAppformReq.getRprsPrdtId()); //대표 상품코드를 상품코드로 설정
        rtnAppformReq.setOnOffType("0");

        if (StringUtils.isBlank(pSocCode)) {
            rtnAppformReq.setDataType("LTE");
        } else {
            MspRateMstDto rateInfo = fCommonSvc.getMspRateMst(pSocCode);
            if (rateInfo != null && rateInfo.getDataType() != null) {
                rtnAppformReq.setDataType(rateInfo.getDataType());
            }
        }

        int intEnggMnthCnt = 0;
        try {
            intEnggMnthCnt = Integer.parseInt(pEnggMnthCnt);
        } catch (NumberFormatException e) {
            intEnggMnthCnt = 0;
        }

        rtnAppformReq.setEnggMnthCnt(intEnggMnthCnt);
        rtnAppformReq.setModelMonthly(pModelMonthly);  // 할부기간

        //인가된 사용자 세션 이름 화면고정 2024-12-05 박민건
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null) {
            rtnAppformReq.setCstmrName(userSession.getName());
        }

        model.addAttribute("AppformReq", rtnAppformReq);

        //3.  할부개월
        /*
         * 01 :  단말 구매   할부 개월 리스트 표현
         * 02 :   USIM(유심)단독 구매:UU  24개월 고정
         */
        List<AppformReqDto> listModelMonthly = new ArrayList<AppformReqDto>();
        listModelMonthly = appformSvc.selectModelMonthlyList(rtnAppformReq);


        model.addAttribute("listModelMonthly", listModelMonthly);


        List<AppformReqDto> listEnggMnthCnt = appformSvc.selectMonthlyListMarket(rtnAppformReq);
        model.addAttribute("listEnggMnthCnt", listEnggMnthCnt);

        //5.  색상
        List<AppformReqDto> listColorInfo = appformSvc.selectPrdtColorList(rtnAppformReq);
        model.addAttribute("listColorInfo", listColorInfo);

        // 대리점 명 확인 필요...
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(pCntpntShopId);

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            /*logger.info("orgnNM==>"+orgnNM);
            logger.info("roadnAdrZipcd==>"+roadnAdrZipcd);
            logger.info("roadnAdrBasSbst==>"+roadnAdrBasSbst);
            logger.info("roadnAdrDtlSbst==>"+roadnAdrDtlSbst);*/

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst  );

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }

        String agentFormLink = request.getRequestURL().append("?").append(request.getQueryString()).toString();
        SessionUtils.saveAgentFormLink(agentFormLink);

        String returnUrl = "/appForm/appOutForm";


        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            //String operTpye = rtnAppformReq.getOperType();
            /*
             * if (OPER_TYPE_CHANGE.equals(operTpye) || OPER_TYPE_EXCHANGE.equals(operTpye))
             * { returnUrl = "/appForm/appOutChangeForm"; }
             */
            rtnAppformReq.setOnOffType("3");
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            rtnAppformReq.setOnOffType("0");
            returnUrl = "/portal".concat(returnUrl);
        }

        return returnUrl;

    }


    @RequestMapping(value = "/m/appform/setUsimNoDlvey.do")
    public String setUsimNoDlvey(@RequestParam(value = "b", required = false, defaultValue = "") String resNo, Model model) {

        //제휴 위탁온라인 유심미보유건
        if(StringUtils.isNotEmpty(resNo)){
            //대리점코드, 요금제코드 조회
            AppformReqDto appformReqDto = appformSvc.getJehuUsimlessByResNo(resNo);
            if(appformReqDto == null) {
                throw new McpCommonException(F_BIND_EXCEPTION, "/main.do");
            }
            String cntpntShopId = appformReqDto.getCntpntShopId();

            NmcpCdDtlDto jehuAgentDto = NmcpServiceUtils.getCodeNmDto(JEHU_AGENT_LIST, cntpntShopId);
            //공통코드 값 01 이 아닌경우 요금제코드 빈값으로
            if(!"01".equals(jehuAgentDto.getExpnsnStrVal1())){
                appformReqDto.setSocCode("");
            }

            model.addAttribute("appformReqDto", appformReqDto);
        }

        return "/mobile/appForm/setUsimNoDlvey.form";
    }


    /**
     * <pre>
     * 설명     : 바로배송 요청 신청서 정보 패치
     * @return
     * @return: List<AppformReqDto>
     * </pre>
     */
    @RequestMapping(value = {"/appform/getFormDlveyListAjax.do"})
    @ResponseBody
    public Map<String, Object> getFormDlveyList(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //인증 정보 검증
        // 1. 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null || StringUtil.isEmpty(sessNiceRes.getConnInfo())) {
            throw new McpCommonJsonException("0003", NICE_CERT_EXCEPTION);
        }

        appformReqDto.setSelfCstmrCi(sessNiceRes.getConnInfo());
        // ================ STEP START ================
        // 이름, 생년월일, ci
        String[] certKey= {"urlType", "name", "birthDate" , "connInfo"};
        String[] certValue= {"getReqDlveryList", appformReqDto.getCstmrName(), appformReqDto.getBirthDate(), appformReqDto.getSelfCstmrCi()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
        }
        // ================ STEP END ================

        List<AppformReqDto> usimNoDlveyList = appformSvc.getFormDlveyList(appformReqDto);

        //1.유심등록할 신청서가 존재하는 경우
        if (usimNoDlveyList != null && !usimNoDlveyList.isEmpty()) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_OBJ", usimNoDlveyList);
            return rtnMap;
        }
        //2.유심등록할 신청서가 존재하지 않는 경우
        String resNo = appformReqDto.getResNo();
        if (StringUtils.isEmpty(resNo)) { // 바로배송(파라미터에 예약번호 미존재)
            rtnMap.put("RESULT_CODE", "001");
            rtnMap.put("RESULT_OBJ", null);
            rtnMap.put("RESULT_MSG", "개통 신청 할 정보가 없습니다.");
            return rtnMap;
        }

        // 제휴위탁온라인(파라미터 예약번호 존재)
        AppformReqDto rtnAppformReqDto = appformSvc.getJehuUsimlessByResNo(resNo);
        if (rtnAppformReqDto != null && !StringUtils.isEmpty(rtnAppformReqDto.getReqUsimSn())) { //이미 유심등록한경우(유심번호존재)
            rtnMap.put("RESULT_CODE", "002");
            rtnMap.put("RESULT_OBJ", null);
            rtnMap.put("RESULT_MSG", "고객님은 유심번호 등록을 완료하여 신청서 접수 상태입니다.<br> 아래 개통실로 연락해주시기 바랍니다.");
        } else {
            rtnMap.put("RESULT_CODE", "003");
            rtnMap.put("RESULT_OBJ", null);
            rtnMap.put("RESULT_MSG", "고객님의 신청서 보관 기간이 경과되어 신청서를 다시 작성해주시기 바랍니다.");
        }

        return rtnMap;
    }


    /**
     * <pre>
     * 설명     : 바로배송 요청 신청서 USIM 번호 , PSTATE = '13' 업데이트
     * @return
     * @return:
     * </pre>
     */
    @RequestMapping(value = {"/appform/updateFormDlveyUsimAjax.do"})
    @ResponseBody
    public Map<String, Object> updateFormDlveyUsim(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 인증 정보 검증
        // 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null || StringUtil.isEmpty(sessNiceRes.getConnInfo())) {
            throw new McpCommonJsonException("AUTH01", NICE_CERT_EXCEPTION_INSR);
        }

        appformReqDto.setSelfCstmrCi(sessNiceRes.getConnInfo());
        // ================ STEP START ================
        // 1. 데이터 검증: 이름, 생년월일, 유심번호, ci
        String[] certKey= {"urlType", "name", "birthDate", "reqUsimSn", "connInfo"};
        String[] certValue= {"updateAppform", appformReqDto.getCstmrName(), appformReqDto.getBirthDate(), appformReqDto.getReqUsimSn(), appformReqDto.getSelfCstmrCi()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
        }

        // 2. (화면) request_key DB 검사
        List<AppformReqDto> usimNoDlveyList = appformSvc.getFormDlveyList(appformReqDto);
        if (usimNoDlveyList == null || usimNoDlveyList.size() == 0) {
            throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);
        }

        boolean reqKeyChk = false;
        for(int i=0; i<usimNoDlveyList.size(); i++) {
            if(usimNoDlveyList.get(i).getRequestKey() == appformReqDto.getRequestKey()) {
                reqKeyChk = true;
                break;
            }
        }

        if(!reqKeyChk) throw new McpCommonJsonException("AUTH02", F_BIND_EXCEPTION);

        // 3. 최소 스텝 수 확인
        if(certService.getStepCnt() < 4 ){
            throw new McpCommonJsonException("STEP02", STEP_CNT_EXCEPTION);
        }

        // 4. requestKey 저장
        // 4-1. requestKey 이력 존재여부 확인
        if(0 < certService.getModuTypeStepCnt("requestKey", "")){
            // requestKey 관련 스텝 초기화
            CertDto certDto = new CertDto();
            certDto.setModuType("requestKey");
            certDto.setCompType("G");
            certService.getCertInfo(certDto);
        }

        // 4-2. 인증종류, 스텝종료여부, requestKey
        certKey= new String[]{"urlType", "moduType", "stepEndYn", "requestKey"};
        certValue= new String[]{"updateRequestKey", "requestKey", "Y", appformReqDto.getRequestKey()+""};
        certService.vdlCertInfo("C", certKey, certValue);
        // ================ STEP END ================

        // 다이렉트몰 바로배송건 유심등록시 진행상태 REQUEST_STATE_CODE = '13' 으로 변경
        // 제휴대리점 위탁온라인 유심등록시 진행상태 REQUEST_STATE_CODE = '00' 으로 변경

        if (appformSvc.updateFormDlveyUsim(appformReqDto)) {
            // 제휴대리점 유심미보유 (배송타입 04) 유심등록
            if("04".equals(appformReqDto.getDlvryType())) {
                // Acen 대상 조건 확인 및 INSERT
                McpRequestDto mcpRequestDto = appformSvc.getMcpRequest(appformReqDto.getRequestKey());
                NmcpCdDtlDto acenLimit= NmcpServiceUtils.getCodeNmDto("AcenLimit", "useLimit");
                if(acenLimit != null && "Y".equals(acenLimit.getExpnsnStrVal1())){

                    AppformReqDto appformReqTemp = appformSvc.getAppForm(appformReqDto);
                    List<MspSalePlcyMstDto> mspSalePlcyMstDtoList = appformSvc.getSalePlcyInfo(appformReqTemp);

                    if(mspSalePlcyMstDtoList == null || mspSalePlcyMstDtoList.isEmpty()) {
                        throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION, "/main.do");
                    }
                    Map<String,String> acenEtcParam= new HashMap<>();
                    acenEtcParam.put("dataType", mspSalePlcyMstDtoList.get(0).getPrdtSctnCd());
                    acenEtcParam.put("socCode", appformReqTemp.getSocCode());

                    boolean isAcenTrg= appformSvc.chkAcenReqCondition(mcpRequestDto, acenEtcParam);
                    if(isAcenTrg){
                        appformSvc.insertAcenReqTrg(mcpRequestDto);
                    }
                }
            }
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "001");
            rtnMap.put("RESULT_MSG", "UPDATE 실패 하였습니다. ");
        }
        return rtnMap;
    }


    /**
     * @param : commendId 추천 아이디
     * @return :
     * @Description : 친구추천 아이디 확인
     * @Author : power
     * @Create Date : 2020. 07. 07
     */
    @RequestMapping(value = "/commendIdCountAjax.do")
    @ResponseBody
    public int commendIdCount(@RequestParam(required = false, defaultValue = "") String commendId) {
        if (StringUtils.isBlank(commendId)) {
            return -99;
        }
        return coEventSvc.duplicateChk(commendId);
    }

    @RequestMapping(value = {"/request/prepiaRequestForm.do", "/m/request/prepiaRequestForm.do"})
    public String prepiaRequest(Model model
            , @ModelAttribute AppformReqDto appformReq
            , @RequestParam(value = "simpleOpen", required = false, defaultValue = "") String simpleOpen
            , @RequestParam(value = "rateCd", required = false, defaultValue = "") String rateCd
            , RedirectAttributes redirectAttributes) {

        redirectAttributes.addAttribute("orgnId", appformReq.getCntpntShopId());
        redirectAttributes.addAttribute("prdtSctnCd", appformReq.getPrdtSctnCd());
        redirectAttributes.addAttribute("rateCd", rateCd);


        if ("Y".equals(simpleOpen)) {
            redirectAttributes.addAttribute("onOffType", "5");
        }
        return "redirect:/appForm/appFormDesignView.do";
    }

    /**
     * @param : AppformReqDto data설정 처리
     * @return :
     * @Description : eSIM 정보 확인 후 data설정
     * @Author : power
     * @Create Date : 2022. 01. 25
     */
    public void fnSetDataOfeSim(AppformReqDto appformReqDto) {

        if (!"09".equals(appformReqDto.getUsimKindsCd())) {
            return ;
        }

        if (appformReqDto.getUploadPhoneSrlNo() < 1) {
            throw new McpCommonJsonException("3001", PHONE_EID_NULL_EXCEPTION);
        }

        McpUploadPhoneInfoDto uploadEPhone = appformSvc.getUploadPhoneInfo(appformReqDto.getUploadPhoneSrlNo());

        if (uploadEPhone == null || StringUtils.isBlank(uploadEPhone.getEid())) {
            throw new McpCommonJsonException("3001", PHONE_EID_NULL_EXCEPTION);
        }

        if (StringUtils.isBlank(uploadEPhone.getPrntsContractNo())) {
            //일반 eSIM
            //eSIM 정보 설정
            appformReqDto.setEid(uploadEPhone.getEid());
            appformReqDto.setImei1(uploadEPhone.getImei1());
            appformReqDto.setImei2(uploadEPhone.getImei2());
            appformReqDto.setReqPhoneSn(uploadEPhone.getReqPhoneSn());
            appformReqDto.setEsimPhoneId(uploadEPhone.getModelId());
        } else {
            //eSIM watch
            //eSIM 정보 설정
            appformReqDto.setEid(uploadEPhone.getEid());
            appformReqDto.setImei1("");
            appformReqDto.setImei2(uploadEPhone.getImei1());
            appformReqDto.setReqPhoneSn(uploadEPhone.getReqPhoneSn());
            appformReqDto.setEsimPhoneId(uploadEPhone.getModelId());
            appformReqDto.setPrntsContractNo(uploadEPhone.getPrntsContractNo());

            //계약 정보 확인
            Map<String, String> resOjb = mypageUserService.selectContractObj("", "", uploadEPhone.getPrntsContractNo());

            if (resOjb == null) {
                throw new McpCommonJsonException("3003", "계약번호 정보를 확인 할 수 없습니다. ");
            }

            String customerSsn = resOjb.get("USER_SSN");
            String customerNm = resOjb.get("SUB_LINK_NAME");
            String birtDate = "";
            String cntrMobileNo = resOjb.get("SUBSCRIBER_NO");
            String custId = resOjb.get("CUSTOMER_ID");
            String svcCntrNo = resOjb.get("SVC_CNTR_NO");


            try {
                customerSsn = EncryptUtil.ace256Dec(customerSsn);
            } catch (CryptoException e) {
                throw new McpCommonJsonException("9998", ACE_256_DECRYPT_EXCEPTION);
            }
            birtDate = customerSsn.substring(0, 6);
            appformReqDto.setCstmrName(customerNm);

            if (CSTMR_TYPE_FN.equals(appformReqDto.getCstmrType())) {
                appformReqDto.setCstmrNativeRrn("");
                appformReqDto.setDesCstmrNativeRrn("");
                appformReqDto.setBirthDate(birtDate);
                appformReqDto.setCstmrForeignerRrn(customerSsn);
            } else if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
                appformReqDto.setCstmrNativeRrn(customerSsn);
                appformReqDto.setDesCstmrNativeRrn(customerSsn);
                appformReqDto.setCstmrForeignerRrn("");
            } else {
                appformReqDto.setCstmrNativeRrn(customerSsn);
                appformReqDto.setDesCstmrNativeRrn(customerSsn);
                appformReqDto.setBirthDate(birtDate);
                appformReqDto.setCstmrForeignerRrn("");
            }

            appformReqDto.setPrntsContractNo(resOjb.get("CONTRACT_NUM"));
            appformReqDto.setPrntsCtn(cntrMobileNo);
            appformReqDto.setPrntsBillNo(resOjb.get("BAN"));
            appformReqDto.setReqPayType("0");  //"통합청구" 코드 값으로 숫자 "0"으로 입력
            if (cntrMobileNo.length() > 10) {
                appformReqDto.setCstmrMobileFn(cntrMobileNo.substring(0, cntrMobileNo.length() - 8));
                appformReqDto.setCstmrMobileMn(cntrMobileNo.substring(cntrMobileNo.length() - 8, cntrMobileNo.length() - 4));
                appformReqDto.setCstmrMobileRn(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));

                appformReqDto.setCstmrTelFn(cntrMobileNo.substring(0, cntrMobileNo.length() - 8));
                appformReqDto.setCstmrTelMn(cntrMobileNo.substring(cntrMobileNo.length() - 8, cntrMobileNo.length() - 4));
                appformReqDto.setCstmrTelRn(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));
            }
            appformReqDto.setCstmrPost(resOjb.get("BAN_ADR_ZIP"));
            appformReqDto.setCstmrAddr(resOjb.get("BAN_ADR_PRIMARY_LN"));
            appformReqDto.setCstmrAddrDtl(resOjb.get("BAN_ADR_SECONDARY_LN"));
            //계약 정보 확인 끝~!


            //신청서 정보 확인
            //없으면... ??????
            //1-2. 계약번호에 신청서 정보 설정
            AppformReqDto paraAppform = new AppformReqDto();
            paraAppform.setContractNum(uploadEPhone.getPrntsContractNo());
            AppformReqDto appformReqResult = appformSvc.getCopyMcpRequest(paraAppform);

            if (appformReqResult == null) {
                throw new McpCommonJsonException("0006", ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
            }

            String cstmrMail = appformReqResult.getCstmrMail(); //신청서 이메일 정보

            /*2025-12-19 안면인증으로 eSim Watch의 경우에도 신분증 선택으로 인해 해당 소스 주석처리*/
//            String selfIssuNum = appformReqResult.getSelfIssuNum();
//            String minorSelfIssuNum = appformReqResult.getMinorSelfIssuNum();
//
//
//            if (selfIssuNum != null && !selfIssuNum.equals("")) {
//                try {
//                    selfIssuNum = EncryptUtil.ace256Dec(selfIssuNum);
//                } catch (CryptoException e) {
//                    throw new McpCommonJsonException("9998", ACE_256_DECRYPT_EXCEPTION);
//                }
//            }
//
//            if (minorSelfIssuNum != null && !minorSelfIssuNum.equals("")) {
//                try {
//                    minorSelfIssuNum = EncryptUtil.ace256Dec(minorSelfIssuNum);
//                } catch (CryptoException e) {
//                    throw new McpCommonJsonException("9998", ACE_256_DECRYPT_EXCEPTION);
//                }
//            }
//
//            if (CSTMR_TYPE_FN.equals(appformReqDto.getCstmrType())) {
//                appformReqDto.setCstmrForeignerNation(appformReqResult.getCstmrForeignerNation());
//                appformReqDto.setCstmrForeignerPn(appformReqResult.getCstmrForeignerPn());
//                appformReqDto.setCstmrForeignerSdate(appformReqResult.getCstmrForeignerSdate());
//                appformReqDto.setCstmrForeignerEdate(appformReqResult.getCstmrForeignerEdate());
//            }
//
//            if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
//                //청소년
//                appformReqDto.setMinorSelfInqryAgrmYn("Y"); //본인인증조회동의
//                appformReqDto.setMinorSelfCertType(appformReqResult.getMinorSelfCertType());
//                appformReqDto.setMinorSelfIssuExprDt(appformReqResult.getMinorSelfIssuExprDt());
//                appformReqDto.setMinorSelfIssuNum(minorSelfIssuNum);
//            } else {
//                appformReqDto.setSelfInqryAgrmYn("Y"); //본인인증조회동의
//                appformReqDto.setSelfCertType(appformReqResult.getSelfCertType());
//                appformReqDto.setSelfIssuExprDt(appformReqResult.getSelfIssuExprDt());
//                appformReqDto.setSelfIssuNumEnc(selfIssuNum);
//            }
            //계약번호에 신청서 정보 설정 끝

            /*
             * 청구서 발송 유형  조회
             * X49
             */

            if (StringUtils.isBlank(svcCntrNo)) {
                svcCntrNo = uploadEPhone.getPrntsContractNo();
            }
            MpMoscBilEmailInfoInVO moscBilEmailInfo = myinfoService.kosMoscBillInfo(svcCntrNo, cntrMobileNo, custId);
            if(moscBilEmailInfo !=null) {
                if (!StringUtils.isBlank(moscBilEmailInfo.getEmail()) ) {
                    cstmrMail = moscBilEmailInfo.getEmail();
                }
                appformReqDto.setCstmrBillSendCode(moscBilEmailInfo.getBillTypeCd());
            } else  {
                throw new McpCommonJsonException("0007", "청구서 정보를 확인할 수 없습니다. ");
            }

            if (!StringUtils.isBlank(cstmrMail) ) {
                appformReqDto.setCstmrMail(cstmrMail);
            }

            appformReqDto.setPstate("00"); //??? 아래에서 설정 하지 않은것 같아.. 그냥.. 설정 ...
        }

    }


    @RequestMapping("/appForm/selectIdentityIp.do")
    @ResponseBody
    public Map<String, Object> selectStolenIp() {

        HashMap<String, Object> returnMap = new HashMap<>();
        String customerIp = this.ipstatisticService.getClientIp();
//        customerIp = "10.220.71.226"; //test
        int stolenIp = this.appformSvc.selectStolenIp(customerIp);

        returnMap.put("RESULT_CODE", stolenIp > 0);
        returnMap.put("RESULT_MSG", customerIp);
        return returnMap;
    }



    /**
     * <pre>
     * 설명     : KtTriple 할인 금액 조회
     * @param  :
     * @return:  msp/getKtTripleDcAmt
     * 날짜     : 2023. 11. 02.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/msp/getKtTripleDcAmtAjax.do")
    @ResponseBody
    public int getKtTripleDcAmt(String rateCd) {

        if (StringUtils.isBlank(rateCd)) {
            throw new McpCommonJsonException("0006", BIDING_EXCEPTION);
        }

        int ktTripleDcAmt = -1;
        RestTemplate restTemplate = new RestTemplate();
        ktTripleDcAmt = restTemplate.postForObject(apiInterfaceServer + "/msp/getKtTripleDcAmt", rateCd, Integer.class);

        return ktTripleDcAmt;
    }


    /**
     * <pre>
     * 설명     : 신용카드 인증조회(X91)
     * @param  :
     * @return:  crdtCardAthnInfo
     * 날짜     : 2024. 03. 19.
     * 작성자   : papier
     * </pre>
     */
    @RequestMapping(value = "/crdtCardAthnInfoAjax.do")
    @ResponseBody
    public Map<String, Object> crdtCardAthnInfo(HttpServletRequest request, AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        //신용카드 인증조회(X91)
        String brthDate = appformReqDto.getReqCardRrn();
        String crdtCardNo = appformReqDto.getReqCardNo();
        try {
            brthDate = EncryptUtil.ace256Dec(brthDate);
            crdtCardNo = EncryptUtil.ace256Dec(crdtCardNo);
        } catch (CryptoException e) {
            brthDate= "";
        }
        brthDate =  NmcpServiceUtils.getSsnDate(brthDate); // YYYYMMDD 형태
        String crdtCardTermDay = appformReqDto.getReqCardYy() + appformReqDto.getReqCardMm();
        String custNm = appformReqDto.getReqCardName();

        /*logger.info("crdtCardNo==>"+crdtCardNo);
        logger.info("brthDate==>"+brthDate);
        logger.info("crdtCardTermDay==>"+crdtCardTermDay);
        logger.info("custNm==>"+custNm);*/

        MoscCrdtCardAthnInDto moscCrdtCardAthnIn = null;
        try {
            moscCrdtCardAthnIn = mPlatFormService.moscCrdtCardAthnInfo(crdtCardNo , crdtCardTermDay ,brthDate , custNm );
            if(moscCrdtCardAthnIn.isSuccess()) {
                if("Y".equals(moscCrdtCardAthnIn.getTrtResult()) ) {
                    String crdtCardKindCd = moscCrdtCardAthnIn.getCrdtCardKindCd();
                    String crdtCardKindNm = "";

                    rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                    rtnMap.put("TRT_MSG", moscCrdtCardAthnIn.getTrtMsg());
                    rtnMap.put("CRDT_CARD_KIND_CD", crdtCardKindCd);
                    rtnMap.put("CRDT_CARD_NM", moscCrdtCardAthnIn.getCrdtCardNm());

                    //공통 코드 존재 여부 확인 ... getXmlMessageOP0
                    List<NmcpCdDtlDto> crdtCardList = NmcpServiceUtils.getCodeList("CRD");//카드 정보

                    if ( crdtCardList != null) {
                        for (NmcpCdDtlDto crdtCardInfo : crdtCardList) {
                            if (crdtCardInfo.getExpnsnStrVal1().equals(crdtCardKindCd)) {
                                crdtCardKindNm = crdtCardInfo.getDtlCd();
                                break;
                            }
                        }
                    }

                    if (!"".equals(crdtCardKindNm)) {

                        // ============ STEP START ============
                        String ncType= "";
                        if(request.getParameter("ncType") != null) ncType= request.getParameter("ncType");

                        // 카드 인증 이력 존재여부 확인
                        if(0 < certService.getModuTypeStepCnt("card", ncType)){
                            // 카드 관련 스텝 초기화
                            CertDto certDto = new CertDto();
                            certDto.setModuType("card");
                            certDto.setCompType("G");
                            certDto.setNcType(ncType);
                            certService.getCertInfo(certDto);
                        }

                        // 인증종류, 대리인구분, 카드번호, 카드유효기간(년도+월), 카드회사코드, 주민번호, 이름
                        String[] certKey= {"urlType", "moduType", "ncType", "reqCardNo", "crdtCardTermDay", "reqCardCompany"
                                , "birthDate", "name" };
                        String[] certValue= {"chkCard", "card", ncType, appformReqDto.getReqCardNo(), crdtCardTermDay, crdtCardKindNm
                                , appformReqDto.getReqCardRrn(), appformReqDto.getReqCardName()};

                        certService.vdlCertInfo("C", certKey, certValue);

                        // 인증종류, 대리인구분, 주민번호, 이름
                        certKey= new String[]{"urlType", "moduType", "ncType", "birthDate", "name"};
                        certValue= new String[]{"compCard", "card", ncType, appformReqDto.getReqCardRrn(), appformReqDto.getReqCardName()};

                        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                            rtnMap.put("RESULT_CODE", "STEP01");
                            rtnMap.put("ALTER_MSG", vldReslt.get("RESULT_DESC"));
                            return rtnMap;
                        }
                        // ============ STEP END ============

                        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
                        rtnMap.put("CRDT_CARD_CODE_NM", crdtCardKindNm);
                        rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 성공하였습니다.");
                    } else {
                        rtnMap.put("RESULT_CODE", "00003");
                        rtnMap.put("TRT_MSG", "공통코드(CRD) 카드 정보가 없습니다. ");
                        rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 실패하였습니다.<br/>다른 카드로 변경하여 등록 해 주세요. ");
                    }
                } else {
                    String trtMsg = moscCrdtCardAthnIn.getTrtMsg();
                    if (trtMsg.contains("주민번호")) {
                        rtnMap.put("RESULT_CODE", "00004");
                        rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                        rtnMap.put("TRT_MSG", trtMsg);
                        rtnMap.put("ALTER_MSG", "최초 요금 납부등록은<br/> 가입자 본인 명의의 카드/계좌로만 가능합니다.<br/>재 확인 후 시도 바랍니다.");
                    } else {
                        rtnMap.put("RESULT_CODE", "00002");
                        rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                        rtnMap.put("TRT_MSG", trtMsg);
                        rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 실패하였습니다.<br/>신용카드 정보 확인 후 다시 입력 해 주세요. ");
                    }
                }
            } else {
                //??? 연동 오류 일때?????
                rtnMap.put("RESULT_CODE", "00001");
                rtnMap.put("GLOBAL_NO", moscCrdtCardAthnIn.getGlobalNo());
                //rtnMap.put("ALTER_MSG", "처리중인 업무가 있습니다. 잠시 후 다시 시도해 주시기 바랍니다..");
                rtnMap.put("ALTER_MSG", "신용카드 유효성 체크에 실패하였습니다.<br/>신용카드 정보 확인 후 다시 입력 해 주세요.");
            }
        } catch (SocketTimeoutException e){
            // ???
            rtnMap.put("RESULT_CODE", "99999");
            rtnMap.put("TRT_MSG", SOCKET_TIMEOUT_EXCEPTION);
            rtnMap.put("ALTER_MSG", "처리중인 업무가 있습니다. 잠시 후 다시 시도해 주시기 바랍니다.");
            //throw new McpCommonException(SOCKET_TIMEOUT_EXCEPTION);
        }
        return rtnMap;

        //테스트 계정 통과 처리
        /*

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null && !StringUtils.isBlank(userSession.getUserId() ) ) {
            String isExceptionId = NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSession.getUserId());
            if (isExceptionId != null && "Y".equals(isExceptionId) ) {

            }
        }

        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS); //무조건 성공 처리
        return rtnMap;
        */

    }


    /*
     * 스마트로 결제 테스트용 로직입니다.
     * jsp 는 따로 지우지 않았습니다.
     * history 내역 보면 테스트 가능합니다.
    @RequestMapping(value = "/appFormTestFirst.do")
    public String appFormTestFirst(Model model) {
        return "/mobile/appForm/appFormTestFirst";
    }

    @RequestMapping(value = "/appFormTest.do")
    public String appFormTest(Model model) {


        SimpleDateFormat yyyyMMddHHmmss = new SimpleDateFormat("yyyyMMddHHmmss");
        String ediDate = "";
        synchronized(new Date()) {
            ediDate = yyyyMMddHHmmss.format(new Date());
        }

        String mid = "t_mmob001m";
        String merchantKey = "0/4GFsSd7ERVRGX9WHOzJ96GyeMTwvIaKSWUCKmN3fDklNRGw3CualCFoMPZaS99YiFGOuwtzTkrLo4bR4V+Ow==";
        String encryptData	= new String(Base64.encodeBase64(DigestUtils.md5Hex(ediDate + mid + "100" + merchantKey).getBytes()));

        model.addAttribute("ediDate", ediDate);
        model.addAttribute("encryptData", encryptData);
        model.addAttribute("today", DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
        model.addAttribute("smartroScriptUrl", smartroMobileScriptUrl);
        if("LOCAL".equals(serverName)) {
            model.addAttribute("returnUrl", "https://local.portal.ktmmobile.com/m/smartro/returnPayTest.do");
            model.addAttribute("stopUrl", "https://local.portal.ktmmobile.com/appFormTest.do");
        }else if("DEV".equals(serverName)) {
            model.addAttribute("returnUrl", "https://dmcpdev.ktmmobile.com:8012/m/smartro/returnPayTest.do");
            model.addAttribute("stopUrl", "https://dmcpdev.ktmmobile.com:8012/appFormTest.do");

        }else if("STG".equals(serverName)) {
            model.addAttribute("returnUrl", "https://dmcpstg.ktmmobile.com/m/smartro/returnPayTest.do");
            model.addAttribute("stopUrl", "https://dmcpstg.ktmmobile.com/appFormTest.do");
        }

        return "/mobile/appForm/appFormTest";
    }

    @RequestMapping(value = "/appFormTestComplete.do")
    public String appFormTestComplete(HttpServletRequest request, Model model) {
        model.addAttribute("replaceYn", request.getParameter("replaceYn"));
        return "/mobile/appForm/appFormTestComplete";
    }

    @RequestMapping(value = "/appFormTestDummy.do")
    public String appFormTestDummy(HttpServletRequest request, Model model) {
        return "/mobile/appForm/appFormTestDummy";
    }
    */

    /*-------------------------------대리점 셀프개통 설계페이지 시작----------------------------*/
    @RequestMapping(value = {"/appForm/appAgentFormDesignView.do", "/m/appForm/appAgentFormDesignView.do"})
    public String appAgentFormDesignView(HttpServletRequest request
      , @RequestParam(value = "a", required = false, defaultValue = "")  String pCntpntShopId          // 조직ID
      , @RequestParam(value = "o", required = false, defaultValue = "")  String pOperType              // 가입유형
      , @RequestParam(value = "s", required = false, defaultValue = "")  String pModelSalePolicyCode   // 판매정책id
      , @RequestParam(value = "p", required = false, defaultValue = "")  String pReqModelName          // 제품ID
      , @RequestParam(value = "y", required = false, defaultValue = "0") String pEnggMnthCnt           // 약정기간
      , @RequestParam(value = "g", required = false, defaultValue = "")  String pSprtTp                // 약정기간
      , @RequestParam(value = "n", required = false, defaultValue = "")  String pModelMonthly          // 할부기간
      , @RequestParam(value = "u", required = false, defaultValue = "")  String openMarketReferer      // 오픈마켓 입점지
      , @RequestParam(value = "q", required = false, defaultValue = "")  String cstmrId                // 사용자 ID
      , @RequestParam(value = "r", required = false, defaultValue = "")  String pSocCode               // 요금상품
      , @RequestParam(value = "i", required = false, defaultValue = "")  String openMarketId           // 오픈마켓유입아이디
      , Model model) {

        // 1. parameter 값 중 하나라도 없으면 에러
        if ("".equals(pCntpntShopId) || "".equals(pOperType) || "".equals(pModelSalePolicyCode) || "".equals(pReqModelName)) {
            throw new McpCommonException(INVALID_PARAMATER_EXCEPTION, "/main.do");
        }

        // 2. 쿠팡 접점일때 cstmrId 필수
        if (Constants.CONTPNT_SHOP_ID_COUPANG.equals(pCntpntShopId) && "".equals(cstmrId)) {
            throw new McpCommonException(INVALID_PARAMATER_EXCEPTION, "/main.do");
        }

        // 3. pOperType 3이 빠져있으면 3 추가
        String pOperTypeTem = pOperType;
        if (pOperType.length() == 3) {
            pOperTypeTem = pOperType + "3";
        }

        // 4. 오픈마켓 입점지 파라미터 검증
        String temp = "";
        boolean checkopenMarket = false;
        if (openMarketReferer != null && !"".equals(openMarketReferer)) {
            //공백제거
            temp = openMarketReferer.replaceAll("\\p{Z}", "");
            //파라미터 검증
            checkopenMarket = !Pattern.matches("^[a-zA-Z0-9]*$", temp);
            if (temp.length() > 10) {
                checkopenMarket = true;
            }
        }

        if (checkopenMarket) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION, "/main.do");
        }

        // 5. 판매정책 조회
        AppformReqDto appformReqDto = new AppformReqDto();
        appformReqDto.setCntpntShopId(pCntpntShopId);
        appformReqDto.setModelSalePolicyCode(pModelSalePolicyCode);
        appformReqDto.setOperType(pOperTypeTem);
        appformReqDto.setReqModelName(pReqModelName);
        appformReqDto.setSprtTp(pSprtTp);

        AppformReqDto rtnAppformReq = appformSvc.getMarketRequest(appformReqDto);
        if (rtnAppformReq == null) {
            // 5-1. 판매정책 미존재
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION, "/main.do");
        } else if (!"02".contentEquals(rtnAppformReq.getPlcySctnCd()) ) {
            // 5-2. 정책구분이 USIM이 아닌경우 (단말인 경우 셀프개통 불가)
            throw new McpCommonException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION, "/main.do");
        } else if (!"Y".contentEquals(rtnAppformReq.getSelfOpenYn())){
            // 5-3. 대리점 셀프개통 정책이 아닌경우
            throw new McpCommonException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION, "/main.do");
        }

        // 6. 파라미터 추가 세팅
        rtnAppformReq.setReqBuyType(Constants.REQ_BUY_TYPE_USIM);
        rtnAppformReq.setOpenMarketReferer(openMarketReferer);
        rtnAppformReq.setSiteReferer(Constants.SITE_REFERER_MARKET);
        rtnAppformReq.setCstmrJejuId(cstmrId);
        rtnAppformReq.setSocCode(pSocCode);
        rtnAppformReq.setPrdtId(rtnAppformReq.getRprsPrdtId()); // 대표 상품코드를 상품코드로 설정
        rtnAppformReq.setOpenMarketId(openMarketId);

        if (StringUtils.isBlank(pSocCode)) {
            rtnAppformReq.setDataType("LTE");
        } else {
            MspRateMstDto rateInfo = fCommonSvc.getMspRateMst(pSocCode);
            if (rateInfo != null && rateInfo.getDataType() != null) {
                rtnAppformReq.setDataType(rateInfo.getDataType());
            }
        }

        int intEnggMnthCnt = 0;
        try {
            intEnggMnthCnt = Integer.parseInt(pEnggMnthCnt);
        } catch (NumberFormatException e) {
            intEnggMnthCnt = 0;
        }

        rtnAppformReq.setEnggMnthCnt(intEnggMnthCnt);
        rtnAppformReq.setModelMonthly(pModelMonthly);  // 할부기간

        String returnUrl = "/appForm/appAgentFormDesignView";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnAppformReq.setOnOffType("7");
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            rtnAppformReq.setOnOffType("5");
            returnUrl = "/portal".concat(returnUrl);
        }

        // 7. 세션 설정
        SessionUtils.saveOsstDto(null);
        SessionUtils.saveCntSession(0);
        SessionUtils.saveNiceRes(null);
        SessionUtils.saveNiceInsrRes(null);
        SessionUtils.saveNiceRwdRes(null);
        SessionUtils.saveAppformDto(rtnAppformReq);

        String agentFormLink = request.getRequestURL().append("?").append(request.getQueryString()).toString();
        SessionUtils.saveAgentFormLink(agentFormLink);

        model.addAttribute("AppformReq", rtnAppformReq);

        // 8. 대리점 셀프개통 부가설정 정보 확인
        String socLimitYn = "N";
        String addInfoTxt = "N";
        NmcpCdDtlDto formSettingDto = NmcpServiceUtils.getCodeNmDto(AGENT_FORM_SETTING, pCntpntShopId);

        if(formSettingDto != null){
            socLimitYn = "Y".equals(formSettingDto.getExpnsnStrVal1()) ? "Y" : "N";
            addInfoTxt = "Y".equals(formSettingDto.getExpnsnStrVal2()) ? "Y" : "N";
        }

        model.addAttribute("socLimitYn", socLimitYn);
        model.addAttribute("addInfoTxt", addInfoTxt);

        // 10. 판매정책 정보 상세조회
        RestTemplate restTemplate = new RestTemplate();
        MspSalePlcyMstDto mspSalePlcyMstDto = restTemplate.postForObject(apiInterfaceServer + "/msp/mspSalePlcyMst", pModelSalePolicyCode, MspSalePlcyMstDto.class);
        model.addAttribute("mspSalePlcyMstDto", mspSalePlcyMstDto);

        // 11. 대리점 정보 세팅
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(pCntpntShopId);

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst);

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }

        return returnUrl;
    }

    @RequestMapping(value = {"/appForm/appAgentForm.do", "/m/appForm/appAgentForm.do"})
    public String appAgentForm(Model model
            , @ModelAttribute AppformReqDto appformReqDto) throws ParseException {

        model.addAttribute("appFormYn", "Y"); //이탈방지 처리
        SessionUtils.saveChangeAut(null);     // 기기변경 세션 제거

        // redirectUrl
        String redirectPage = "/main.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            redirectPage = "/m/main.do";
        }

        String agentFormLink = SessionUtils.getAgentFormLink();  // 대리점 신청서 URL 세션
        if(agentFormLink != null && agentFormLink.indexOf("appAgentFormDesignView.do") > -1){
            redirectPage= agentFormLink;
        }

        // 1. 인자값 확인
        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION, redirectPage);
        }

        // 2. 임시저장값 확인
        AppformReqDto appformReqTemp = appformSvc.getAppFormTemp(appformReqDto.getRequestKey());
        if (appformReqTemp == null) {
            throw new McpCommonException(ExceptionMsgConstant.TEMP_FORM_INFO_NULL_EXCEPTION, redirectPage);
        }


        // 이벤트 코드 입력한 경우 추천인ID 표출여부 xml 조회
        if(!StringUtils.isEmpty(appformReqTemp.getEvntCdPrmt())){
            String recoUseYn = eventCodeSvc.getEventCodeRecoUseYnXML(appformReqTemp.getEvntCdPrmt());
            if(!StringUtils.isEmpty(recoUseYn)){
                appformReqTemp.setRecoUseYn(recoUseYn);
            }
        }

        AppformReqDto appformSession = SessionUtils.getAppformSession();
        if (appformSession == null) {
            throw new McpCommonException(ExceptionMsgConstant.TEMP_FORM_INFO_NULL_EXCEPTION, redirectPage);
        }
        String openMarketReferer = appformSession.getOpenMarketReferer();
        if(!StringUtils.isEmpty(openMarketReferer)) {
            appformReqTemp.setOpenMarketReferer(openMarketReferer);
        }

        // 2-1. 판매정책 및 조직ID 검증
        String sessionSalePolicyCode = appformSession.getModelSalePolicyCode();
        String sessionCntpntShopId = appformSession.getCntpntShopId();

        if (!sessionSalePolicyCode.equals(appformReqTemp.getModelSalePolicyCode())
          || !sessionCntpntShopId.equals(appformReqTemp.getCntpntShopId())) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION, redirectPage);
        }

        // 2-2. onOffType 검증
        if ("|5|7|".indexOf(appformReqTemp.getOnOffType()) <= 0) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION, redirectPage);
        }

        // 3. 셀프개통 가능 플랫폼 확인
        Map<String,String> selfPlatformMap= appformSvc.isSimpleApplyPlatform(appformReqTemp.getOperType());
            if(!"Y".equals(selfPlatformMap.get("applyYn"))){
                // 예외 IP 확인
                NmcpCdDtlDto exceptionIp= fCommonSvc.getDtlCodeWithNm(SIMPLE_OPEN_LIMIT_EXCEPTION_IP, ipstatisticService.getClientIp());
                if(exceptionIp == null){
                throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_PLATFORM_EXCEPTION, redirectPage);
            }
        }

        // 4. 명의도용 IP 확인
        int stolenIp = appformSvc.selectStolenIp(ipstatisticService.getClientIp());
        if(stolenIp > 0) {
            throw new McpCommonException(ExceptionMsgConstant.STOLEN_IP_EXCEPTION, redirectPage);
        }

        // 5. 010 신규 유심 셀프개통인 경우, 동일아이피 차단 확인
        if(OPER_TYPE_NEW.equals(appformReqTemp.getOperType()) && !"09".equals(appformReqTemp.getUsimKindsCd())){
            int nacSelfIp = appformSvc.getNacSelfCount();
            if(nacSelfIp > 0){
                throw new McpCommonException(ExceptionMsgConstant.NAC_AGENT_SELF_IP_EXCEPTION, redirectPage);
            }
        }

        // 6. STEP 초기화 및 메뉴명 세팅
        SessionUtils.removeCertSession();
        StringBuilder pageSb= new StringBuilder();
        if(OPER_TYPE_NEW.equals(appformReqTemp.getOperType())) pageSb.append("Nac");
        else if(OPER_TYPE_MOVE_NUM.equals(appformReqTemp.getOperType())) pageSb.append("Mnp");
        else if(OPER_TYPE_CHANGE.equals(appformReqTemp.getOperType())) pageSb.append("Hcn");
        else if(OPER_TYPE_EXCHANGE.equals(appformReqTemp.getOperType())) pageSb.append("Hdn");

        pageSb.append("AgentSelfForm");
        SessionUtils.setPageSession(pageSb.toString());

        // 7. 필수값 검사
        if (StringUtils.isBlank(appformReqTemp.getSocCode())
                || StringUtils.isBlank(appformReqTemp.getOnOffType())
                || StringUtils.isBlank(appformReqTemp.getOperType())) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectPage);
        }

        // 8. 기본값 설정
        if (StringUtils.isBlank(appformReqTemp.getClauseMpps35Flag())) {
            appformReqTemp.setClauseMpps35Flag("N");    // 선불 M PPS 요금제_동의
        }

//        if (StringUtils.isBlank(appformReqTemp.getSelfCertType())) {
//            appformReqTemp.setSelfCertType("01");       // 주민등록증 기본값
//        }

        if (StringUtils.isBlank(appformReqTemp.getMoveAuthType())) {
            appformReqTemp.setMoveAuthType("2");        // 번호이동 인증유형 - 휴대폰 일련번호
        }

        if (StringUtils.isBlank(appformReqTemp.getCstmrBillSendCode())) {
            appformReqTemp.setCstmrBillSendCode("CB");  // 이메일 명세서
        }

        if (StringUtils.isBlank(appformReqTemp.getReqPayType())) {
            appformReqTemp.setReqPayType("D");          // 자동이체
        }

        if (StringUtils.isBlank(appformReqTemp.getModelMonthly())) {
            appformReqTemp.setModelMonthly("0");        // 할부개월
        }

        // 9. 로그인 정보 세팅 (대리점 URL은 로그인정보 빈값으로 세팅)
        String userDivision = "-1";
        appformReqTemp.setCstmrName("");
        appformReqTemp.setCstmrNativeRrn("");
        appformReqTemp.setCstmrForeignerRrn("");
        appformReqTemp.setCstmrMobileFn("");
        appformReqTemp.setCstmrMobileMn("");
        appformReqTemp.setCstmrMobileRn("");
        appformReqTemp.setCstmrAddr("");
        appformReqTemp.setCstmrAddrDtl("");

        model.addAttribute("userDivision", userDivision);

        // 10. 셀프개통 가능 접점 확인
        NmcpCdDtlDto simpleSiteList = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_SIMPLE_REGI_SITE_LIST, appformReqTemp.getCntpntShopId());
        if (simpleSiteList == null) {
            throw new McpCommonException(ExceptionMsgConstant.SIMPLE_CNTPNT_SHOPID_EXCEPTION, redirectPage);
        }

        // 11. 셀프개통 가능 시간 확인
        Map<String, Object> mapIsApply = this.isSimpleApplyObj();
        if (Constants.OPER_TYPE_MOVE_NUM.equals(appformReqTemp.getOperType())) {
            if (!(Boolean) mapIsApply.get("IsMnpTime")) {
                throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_TIME_EXCEPTION, redirectPage);
            }
        } else if (Constants.OPER_TYPE_NEW.equals(appformReqTemp.getOperType())) {
            if (!(Boolean) mapIsApply.get("IsMacTime")) {
                throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_TIME_EXCEPTION, redirectPage);
            }
        } else {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectPage);
        }

        // 12. session 초기화
        SessionUtils.saveAppformDto(null);
        SessionUtils.saveOsstDto(null);
        SessionUtils.saveCntSession(0);
        SessionUtils.saveNiceRes(null);
        SessionUtils.saveNiceInsrRes(null);
        SessionUtils.saveNiceRwdRes(null);
        //안면인증 세션 초기화
        SessionUtils.initializeFathSession();

        // 13. 유심등록 사이트 리스트 분류코드
        NmcpCdDtlDto regiSiteOjb = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_REGI_SITE_LIST, appformReqTemp.getCntpntShopId());
        if (regiSiteOjb != null) {
            appformReqTemp.setSiteReferer(regiSiteOjb.getExpnsnStrVal1());
        }

        if (regiSiteOjb == null && StringUtils.isBlank(appformReqTemp.getModelSalePolicyCode())) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION, redirectPage);
        }

        // 14. 대표 상품정보 조회 → 현재 단품ID로 대표모델ID를 조회
        if (!StringUtils.isBlank(appformReqTemp.getModelId())) {

            PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(appformReqTemp.getModelId());

            if(phoneMspDto == null){
                throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectPage);
            }

            appformReqTemp.setRprsPrdtId(phoneMspDto.getRprsPrdtId());
            appformReqTemp.setPrdtId(appformReqTemp.getModelId());
            appformReqTemp.setPrdtNm(phoneMspDto.getPrdtNm());
        }

        // 15. 플랫폼에 따라 값 추가 세팅
        String returnUrl = "/appForm/appAgentForm";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            appformReqTemp.setOnOffType("7");
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            appformReqTemp.setOnOffType("5");
            returnUrl = "/portal".concat(returnUrl);
        }

        // 16. 대리점 정보 세팅
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(appformSession.getCntpntShopId());

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst);

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }

        // 17. 인가된 사용자 이름 화면 고정
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null) {
            appformReqTemp.setCstmrName(userSession.getName());
        }

        model.addAttribute("today", DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
        model.addAttribute("AppformReq", appformReqTemp);

        // 18. 제휴처 / 제휴사에 따른 정보 세팅
        NmcpCdDtlDto jehuProdInfo = fCommonSvc.getJehuProdInfo(appformReqTemp.getSocCode());
        NmcpCdDtlDto jehuPartnerInfo = fCommonSvc.getJehuPartnerInfo(appformReqTemp.getCntpntShopId());

        SessionUtils.saveJehuPartnerType(jehuPartnerInfo.getExpnsnStrVal1());
        model.addAttribute("jehuProdType", jehuProdInfo.getDtlCd());
        model.addAttribute("jehuProdName", jehuProdInfo.getDtlCdNm());
        model.addAttribute("jehuPartnerType", jehuPartnerInfo.getDtlCd());
        model.addAttribute("jehuPartnerName", jehuPartnerInfo.getDtlCdNm());

        // 19. 타이틀 변경
        NmcpCdDtlDto formSettingDto = NmcpServiceUtils.getCodeNmDto(AGENT_FORM_SETTING, appformSession.getCntpntShopId());
        String addInfoTxt = "N";

        if(formSettingDto != null){
            addInfoTxt = "Y".equals(formSettingDto.getExpnsnStrVal2()) ? "Y" : "N";
        }

        model.addAttribute("addInfoTxt", addInfoTxt);
        return returnUrl;
    }

    /**
     * <pre>
     * 설명     : 완료 페이지
     * google-analytics 구분 하기 위해
     * </pre>
     */
    @RequestMapping(value = {"/appForm/appAgentFormComplete.do", "/m/appForm/appAgentFormComplete.do"})
    public String appAgentFormComplete(Model model, @ModelAttribute AppformReqDto appformReqDto) throws ParseException {

        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        //신청서 정보
        AppformReqDto appformReqTemp = appformSvc.getAppForm(appformReqDto);

        if (appformReqTemp == null) {
            throw new McpCommonException(ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
        }

        // ============= START: 완료페이지 진입 로직 강화 =============
        // 1. 셀프개통인 경우, OP2 응답 재확인
        if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())){
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(appformReqTemp.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_REQ_OPEN_RESULT);

            McpRequestOsstDto mcpRequestOsstDtoRtn= appformSvc.getRequestOsst(mcpRequestOsstDto);

            if(mcpRequestOsstDtoRtn == null || !isSuccessOP2(mcpRequestOsstDtoRtn.getRsltCd())){
                throw new McpCommonException("개통"+ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
            }
        }

        // 2. 신청정보와 본인인증 세션 정보 일치여부 확인 (본인인증한 신청서의 경우, ci값 존재)
        NiceResDto sessNiceRes = SessionUtils.getNiceReqTmpCookieBean();

        if(!StringUtil.isEmpty(appformReqTemp.getSelfCstmrCi())){
            if(sessNiceRes == null) throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);

            if(!appformReqTemp.getSelfCstmrCi().equals(sessNiceRes.getConnInfo())){
                throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
            }
        }else{
            // 3. CI값이 없는 신청정보인 경우, 본인인증 세션의 이름/생년월일과 신청정보의 이름/생년월일 비교
            if(sessNiceRes != null){
                String authNm= StringUtil.NVL(sessNiceRes.getName(), "").toUpperCase().replaceAll(" ", "");
                String authBirth= StringUtil.NVL(sessNiceRes.getBirthDate(), "");
                String requestNm= appformReqTemp.getCstmrName();
                String requestRrn= appformReqTemp.getCstmrNativeRrn();

                if(CSTMR_TYPE_NM.equals(appformReqTemp.getCstmrType())){
                    requestNm= appformReqTemp.getMinorAgentName();
                    requestRrn= appformReqTemp.getMinorAgentRrn();
                }

                try {
                    requestNm= StringUtil.NVL(requestNm, "").toUpperCase().replaceAll(" ", "");
                    requestRrn= EncryptUtil.ace256Dec(requestRrn);
                } catch (CryptoException e) {
                    requestNm= "";
                    requestRrn= "";
                }

                if(!requestNm.equals(authNm) || requestRrn.indexOf(authBirth) < 0){
                    throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
                }
            }
        }
        // ============= END: 완료페이지 진입 로직 강화 =============

        String returnUrl = "/appForm/appAgentFormComplete";


        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) { //셀프개통
                appformReqTemp.setOnOffType("7");
            }
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) { //셀프개통
                appformReqTemp.setOnOffType("5");
            }
            returnUrl = "/portal".concat(returnUrl);
        }

        model.addAttribute("AppformReq", appformReqTemp);

        //유심비 /  가입비 설정
        String cntpntShopId = appformReqTemp.getCntpntShopId();

        // 대리점 명 확인 필요...
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(cntpntShopId);

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst  );

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }

        // 타이틀 변경
        NmcpCdDtlDto formSettingDto =NmcpServiceUtils.getCodeNmDto(AGENT_FORM_SETTING, cntpntShopId);

        String addInfoTxt = "N";
        if(formSettingDto != null){
            addInfoTxt = "Y".equals(formSettingDto.getExpnsnStrVal2()) ? "Y" : "N";
        }

        model.addAttribute("addInfoTxt", addInfoTxt);


        NiceResDto sessNiceMobile = SessionUtils.getNiceMobileSession();

     // 신규개통이면서 셀프개통으로 진행한 케이스에만 알림톡발송
        if (OPER_TYPE_NEW.equals(appformReqTemp.getOperType()) && ("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType()))) {
                MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
                mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_COMPLETE_TEMPLATE_ID);
                if (mspSmsTemplateMstDto != null) {

                    Date today = new Date();
                    String strToday = DateTimeUtil.changeFormat(today, "yyyy-MM-dd");
                    MspRateMstDto rateInfo = fCommonSvc.getMspRateMst(appformReqTemp.getSocCode());

                    mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{mobileNo}", "010-"+appformReqTemp.getReqWantNumber2()+"-"+appformReqTemp.getReqWantNumber3()));
                    mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{openingDate}", strToday));

                    if (rateInfo != null && rateInfo.getRateNm() != null) {
                        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{phonePlan}", rateInfo.getRateNm()));
                    }

                   if(sessNiceMobile !=null && sessNiceMobile.getsMobileNo() != null) {

                       smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), sessNiceMobile.getsMobileNo(), mspSmsTemplateMstDto.getText(),
                            mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                            Constants.KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_COMPLETE_TEMPLATE_ID));
                }
             }
        }

        return returnUrl;
    }

    @RequestMapping(value = "/appForm/getAgentFormLinkAjax.do")
    @ResponseBody
    public Map<String, String> getAgentFormLinkAjax(HttpServletRequest request) {

        Map<String, String> rtnMap = new HashMap<>();

        // 대리점 신청서 URL 세션 조회
        String agentFormLink = SessionUtils.getAgentFormLink();

        if (agentFormLink != null && !"".equals(agentFormLink)) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("agentFormLink", agentFormLink);
        } else {
            throw new McpCommonJsonException(NO_SESSION_EXCEPTION);
        }

        return rtnMap;
    }

    /** 신규 셀프개통 가입 제한 IP 확인 */
    @RequestMapping(value = "/appForm/selfIpChk.do")
    @ResponseBody
    public Map<String, String> selfIpChk() {

        Map<String, String> rtnMap = new HashMap<>();

        int nacSelfCnt = appformSvc.getNacSelfCount();
        String resultCode = nacSelfCnt > 0 ? "9999" : AJAX_SUCCESS;

        rtnMap.put("RESULT_CODE", resultCode);
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 중복 신청 체크
     * @param appformReqDto
     * @return: Map<String, Object>
     * 날짜     : 2025. 05. 27.
     * 작성자   :
     * </pre>
     */
    @RequestMapping(value = "/appform/checkDupReqAjax.do")
    @ResponseBody
    public Map<String, Object> checkDupReqAjax(HttpServletRequest request, AppformReqDto appformReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        Map<String, String> dupChkMap = appformSvc.checkDupReq(appformReqDto);
        if (!AJAX_SUCCESS.equals(dupChkMap.get("RESULT_CODE"))) {
            rtnMap.put("RESULT_CODE", dupChkMap.get("RESULT_CODE"));
            rtnMap.put("RESULT_MSG", dupChkMap.get("RESULT_MSG"));
        } else {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        }
        return rtnMap;
    }

    @RequestMapping(value = "/appform/containsGoldNumbersAjax.do")
    @ResponseBody
    public Map<String, Object> containsGoldNumbersAjax(@RequestParam(name = "reqWantNumber") String reqWantNumber
                                                , @RequestParam(name = "reqWantNumber2") String reqWantNumber2
                                                , @RequestParam(name = "reqWantNumber3") String reqWantNumber3 ) {
        Map<String, Object> rtnMap = new HashMap<>();

        List<String> reqWantNumbers = Arrays.asList(reqWantNumber, reqWantNumber2, reqWantNumber3);
        appformSvc.containsGoldNumbers(reqWantNumbers);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /*-------------------------------제휴 대리점 설계페이지 시작----------------------------*/
    @RequestMapping(value = {"/appForm/appJehuFormDesignView.do", "/m/appForm/appJehuFormDesignView.do"})
    public String appJehuFormDesignView(HttpServletRequest request
            , @RequestParam(value = "a", required = false, defaultValue = "") String pCntpntShopId          // 조직ID
            , @RequestParam(value = "r", required = false, defaultValue = "") String pSocCode               // 요금상품
            , @RequestParam(value = "u", required = false, defaultValue = "") String openMarketReferer      // 오픈마켓 입점지
            , Model model) {
        AppformReqDto appformReqDto = new AppformReqDto();

        // parameter 체크
        if ("".equals(pCntpntShopId)) {
            throw new McpCommonException(F_BIND_EXCEPTION, "/main.do");
        }
        // 제휴 대리점이 맞는지 확인
        NmcpCdDtlDto jehuAgentDto = NmcpServiceUtils.getCodeNmDto(JEHU_AGENT_LIST, pCntpntShopId);
        if(jehuAgentDto == null) throw new McpCommonException(F_BIND_EXCEPTION, "/main.do");

        // 오픈마켓 입점지 파라미터 검증
        String temp = "";
        boolean checkopenMarket = false;
        if (openMarketReferer != null && !"".equals(openMarketReferer)) {
            //공백제거
            temp = openMarketReferer.replaceAll("\\p{Z}", "");
            //파라미터 검증
            checkopenMarket = !Pattern.matches("^[a-zA-Z0-9]*$", temp);
            if (temp.length() > 10) {
                checkopenMarket = true;
            }
        }

        if (checkopenMarket) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_REFERER_EXCEPTION, "/main.do");
        }
        appformReqDto.setOpenMarketReferer(openMarketReferer);


        // 01이면 요금제 고정
        String socLimitYn = "N";
        if("01".equals(jehuAgentDto.getExpnsnStrVal1())){
            //추가 parameter 체크
            if ("".equals(pSocCode)) {
                throw new McpCommonException(F_BIND_EXCEPTION, "/main.do");
            }
            appformReqDto.setSocCode(pSocCode);
            socLimitYn = "Y";
        }
        model.addAttribute("socLimitYn", socLimitYn);


        appformReqDto.setCntpntShopId(pCntpntShopId);

        //해당 대리점의 모든 판매정책 조회 , 요금제가 있는 경우 해당 요금제의 판매정책 조회
        List<MspSalePlcyMstDto> mspSalePlcyMstDtoList = appformSvc.getSalePlcyInfo(appformReqDto);
        //판매정책 미존재
        if(mspSalePlcyMstDtoList == null || mspSalePlcyMstDtoList.isEmpty()) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION, "/main.do");
        }
        model.addAttribute("mspSalePlcyMstDtoList", mspSalePlcyMstDtoList);
        //조회된 판매정책 중 가장 최신에 등록한 판매정책
        appformReqDto.setModelSalePolicyCode(mspSalePlcyMstDtoList.get(0).getSalePlcyCd());
        appformReqDto.setEnggMnthCnt(Long.parseLong(mspSalePlcyMstDtoList.get(0).getAgrmTrm()));
        appformReqDto.setPrdtId(mspSalePlcyMstDtoList.get(0).getPrdtId());
        appformReqDto.setPrdtNm(mspSalePlcyMstDtoList.get(0).getPrdtNm());

        // 초기값은 "N" (조건 충족되면 Y로 변경)
        String selfOpenYn = "N";
        String nac3Yn = "N";
        String mnp3Yn = "N";
        for (MspSalePlcyMstDto rtn : mspSalePlcyMstDtoList) {
            // 조건 충족 시 Y로 세팅 (이미 Y면 건너뜀)
            if ("N".equals(selfOpenYn) && "Y".equals(rtn.getSelfOpenYn())) selfOpenYn = "Y";
            if ("N".equals(nac3Yn) && "NAC3".equals(rtn.getOperType())) nac3Yn = "Y";
            if ("N".equals(mnp3Yn) && "MNP3".equals(rtn.getOperType())) mnp3Yn = "Y";
        }
        model.addAttribute("selfOpenYn", selfOpenYn);
        model.addAttribute("nac3Yn", nac3Yn);
        model.addAttribute("mnp3Yn", mnp3Yn);

        //대리점 정보 세팅
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(pCntpntShopId);

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst);

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }

        model.addAttribute("AppformReq", appformReqDto);
        String returnUrl = "/appForm/appJehuFormDesignView";

        SessionUtils.saveAppformDto(appformReqDto);

        String jehuFormLink = request.getRequestURL().append("?").append(request.getQueryString()).toString();
        SessionUtils.saveAgentFormLink(jehuFormLink);

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            returnUrl = "/portal".concat(returnUrl);
        }

        return returnUrl;
    }

    @RequestMapping(value = {"/appForm/appJehuForm.do", "/m/appForm/appJehuForm.do"})
    public String appJehuForm(Model model
            , @ModelAttribute AppformReqDto appformReqDto) throws ParseException {

        model.addAttribute("appFormYn", "Y"); //이탈방지 처리
        SessionUtils.saveChangeAut(null);     // 기기변경 세션 제거

        // redirectUrl
        String redirectPage = "/main.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            redirectPage = "/m/main.do";
        }

        String agentFormLink = SessionUtils.getAgentFormLink();  // 대리점 신청서 URL 세션
        if(agentFormLink != null && agentFormLink.indexOf("appJehuFormDesignView.do") > -1){
            redirectPage= agentFormLink;
        }

        // 1. 인자값 확인
        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION, redirectPage);
        }

        // 2. 임시저장값 확인
        AppformReqDto appformReqTemp = appformSvc.getAppFormTemp(appformReqDto.getRequestKey());
        if (appformReqTemp == null) {
            throw new McpCommonException(ExceptionMsgConstant.TEMP_FORM_INFO_NULL_EXCEPTION, redirectPage);
        }

        AppformReqDto appformSession = SessionUtils.getAppformSession();
        if (appformSession == null) {
            throw new McpCommonException(ExceptionMsgConstant.TEMP_FORM_INFO_NULL_EXCEPTION, redirectPage);
        }


        String openMarketReferer = appformSession.getOpenMarketReferer();
        if(!StringUtils.isEmpty(openMarketReferer)) {
            appformReqTemp.setOpenMarketReferer(openMarketReferer);
        }


        // 2-1. 조직ID 검증
        String sessionCntpntShopId = appformSession.getCntpntShopId();

        if (!sessionCntpntShopId.equals(appformReqTemp.getCntpntShopId())) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION, redirectPage);
        }
        // 3. 셀프개통 가능 플랫폼 확인
        Map<String,String> selfPlatformMap= appformSvc.isSimpleApplyPlatform(appformReqTemp.getOperType());
        if(!"Y".equals(selfPlatformMap.get("applyYn"))){
            // 예외 IP 확인
            NmcpCdDtlDto exceptionIp= fCommonSvc.getDtlCodeWithNm(SIMPLE_OPEN_LIMIT_EXCEPTION_IP, ipstatisticService.getClientIp());
            if(exceptionIp == null){
                throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_PLATFORM_EXCEPTION, redirectPage);
            }
        }

        // 4. 명의도용 IP 확인
        int stolenIp = appformSvc.selectStolenIp(ipstatisticService.getClientIp());
        if(stolenIp > 0) {
            throw new McpCommonException(ExceptionMsgConstant.STOLEN_IP_EXCEPTION, redirectPage);
        }

        // 5. 010 신규 유심 셀프개통인 경우, 동일아이피 차단 확인
        if(OPER_TYPE_NEW.equals(appformReqTemp.getOperType()) && !"09".equals(appformReqTemp.getUsimKindsCd())){
            int nacSelfIp = appformSvc.getNacSelfCount();
            if(nacSelfIp > 0){
                throw new McpCommonException(ExceptionMsgConstant.NAC_AGENT_SELF_IP_EXCEPTION, redirectPage);
            }
        }

        // 6. STEP 초기화 및 메뉴명 세팅
        SessionUtils.removeCertSession();
        StringBuilder pageSb= new StringBuilder();
        if(OPER_TYPE_NEW.equals(appformReqTemp.getOperType())) pageSb.append("Nac");
        else if(OPER_TYPE_MOVE_NUM.equals(appformReqTemp.getOperType())) pageSb.append("Mnp");

        if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) pageSb.append("JehuSelfForm");
        else pageSb.append("JehuForm");

        SessionUtils.setPageSession(pageSb.toString());

        // 7. 필수값 검사
        if (StringUtils.isBlank(appformReqTemp.getSocCode())
                || StringUtils.isBlank(appformReqTemp.getOnOffType())
                || StringUtils.isBlank(appformReqTemp.getOperType())) {
            throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectPage);
        }

        // 8. 기본값 설정
        if (StringUtils.isBlank(appformReqTemp.getClauseMpps35Flag())) {
            appformReqTemp.setClauseMpps35Flag("N");    // 선불 M PPS 요금제_동의
        }

//        if (StringUtils.isBlank(appformReqTemp.getSelfCertType())) {
//            appformReqTemp.setSelfCertType("01");       // 주민등록증 기본값
//        }

        if (StringUtils.isBlank(appformReqTemp.getMoveAuthType())) {
            appformReqTemp.setMoveAuthType("2");        // 번호이동 인증유형 - 휴대폰 일련번호
        }

        if (StringUtils.isBlank(appformReqTemp.getCstmrBillSendCode())) {
            appformReqTemp.setCstmrBillSendCode("CB");  // 이메일 명세서
        }

        if (StringUtils.isBlank(appformReqTemp.getReqPayType())) {
            appformReqTemp.setReqPayType("D");          // 자동이체
        }

        if (StringUtils.isBlank(appformReqTemp.getModelMonthly())) {
            appformReqTemp.setModelMonthly("0");        // 할부개월
        }
        // 9. 로그인 정보 세팅 (대리점 URL은 로그인정보 빈값으로 세팅)
        String userDivision = "-1";
        appformReqTemp.setCstmrName("");
        appformReqTemp.setCstmrNativeRrn("");
        appformReqTemp.setCstmrForeignerRrn("");
        appformReqTemp.setCstmrMobileFn("");
        appformReqTemp.setCstmrMobileMn("");
        appformReqTemp.setCstmrMobileRn("");
        appformReqTemp.setCstmrAddr("");
        appformReqTemp.setCstmrAddrDtl("");

        model.addAttribute("userDivision", userDivision);


        if (0 < "|5|7|".indexOf(appformReqTemp.getOnOffType())) {
            // 10. 셀프개통 가능 접점 확인
            NmcpCdDtlDto simpleSiteList = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_SIMPLE_REGI_SITE_LIST, appformReqTemp.getCntpntShopId());
            if (simpleSiteList == null) {
                throw new McpCommonException(ExceptionMsgConstant.SIMPLE_CNTPNT_SHOPID_EXCEPTION, redirectPage);
            }

            // 11. 셀프개통 가능 시간 확인
            Map<String, Object> mapIsApply = this.isSimpleApplyObj();
            if (Constants.OPER_TYPE_MOVE_NUM.equals(appformReqTemp.getOperType())) {
                if (!(Boolean) mapIsApply.get("IsMnpTime")) {
                    throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_TIME_EXCEPTION, redirectPage);
                }
            } else if (Constants.OPER_TYPE_NEW.equals(appformReqTemp.getOperType())) {
                if (!(Boolean) mapIsApply.get("IsMacTime")) {
                    throw new McpCommonException(ExceptionMsgConstant.SIMPLE_OPEN_TIME_EXCEPTION, redirectPage);
                }
            } else {
                throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectPage);
            }
        }


        // 12. session 초기화
        SessionUtils.saveAppformDto(null);
        SessionUtils.saveOsstDto(null);
        SessionUtils.saveCntSession(0);
        SessionUtils.saveNiceRes(null);
        SessionUtils.saveNiceInsrRes(null);
        SessionUtils.saveNiceRwdRes(null);
        //안면인증 세션 초기화
        SessionUtils.initializeFathSession();

        // 13. 유심등록 사이트 리스트 분류코드
        NmcpCdDtlDto regiSiteOjb = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_USIM_REGI_SITE_LIST, appformReqTemp.getCntpntShopId());
        if (regiSiteOjb != null) {
            appformReqTemp.setSiteReferer(regiSiteOjb.getExpnsnStrVal1());
        }

        if (regiSiteOjb == null && StringUtils.isBlank(appformReqTemp.getModelSalePolicyCode())) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_SALE_PLCY_CD_EXCEPTION, redirectPage);
        }

        // 14. 대표 상품정보 조회 → 현재 단품ID로 대표모델ID를 조회
        if (!StringUtils.isBlank(appformReqTemp.getModelId())) {

            PhoneMspDto phoneMspDto = mspService.findMspPhoneInfo(appformReqTemp.getModelId());

            if(phoneMspDto == null){
                throw new McpCommonException(ExceptionMsgConstant.INVALID_PARAMATER_EXCEPTION, redirectPage);
            }

            appformReqTemp.setRprsPrdtId(phoneMspDto.getRprsPrdtId());
            appformReqTemp.setPrdtId(appformReqTemp.getModelId());
            appformReqTemp.setPrdtNm(phoneMspDto.getPrdtNm());
        }

        // 15. 플랫폼에 따라 값 추가 세팅
        String returnUrl = "/appForm/appJehuForm";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            returnUrl = "/portal".concat(returnUrl);
        }

        // 16. 대리점 정보 세팅
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(appformSession.getCntpntShopId());

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst);

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }

        // 17. 인가된 사용자 이름 화면 고정
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession != null) {
            appformReqTemp.setCstmrName(userSession.getName());
        }

        model.addAttribute("today", DateTimeUtil.getFormatString("yyyyMMddHHmmss"));
        model.addAttribute("jehuRefererYn", "Y");

        // 18. 제휴처 / 제휴사에 따른 정보 세팅
        NmcpCdDtlDto jehuProdInfo = fCommonSvc.getJehuProdInfo(appformReqTemp.getSocCode());
        NmcpCdDtlDto jehuPartnerInfo = fCommonSvc.getJehuPartnerInfo(appformReqTemp.getCntpntShopId());
        SessionUtils.saveJehuPartnerType(jehuPartnerInfo.getExpnsnStrVal1());
        model.addAttribute("jehuProdType", jehuProdInfo.getDtlCd());
        model.addAttribute("jehuProdName", jehuProdInfo.getDtlCdNm());
        model.addAttribute("jehuPartnerType", jehuPartnerInfo.getDtlCd());
        model.addAttribute("jehuPartnerName", jehuPartnerInfo.getDtlCdNm());

        // 19. 친구초대 추천인ID 영역 노출 여부
        String recommendFlagYn = "Y";

        // 이벤트 코드 입력한 경우 추천인ID 표출여부 xml 조회
        if(!StringUtils.isEmpty(appformReqTemp.getEvntCdPrmt())){
            String recoUseYn = eventCodeSvc.getEventCodeRecoUseYnXML(appformReqTemp.getEvntCdPrmt());
            if(!StringUtils.isEmpty(recoUseYn)){
                recommendFlagYn = recoUseYn;
            }
        }else{
            NmcpCdDtlDto jehuAgentDto = NmcpServiceUtils.getCodeNmDto(JEHU_AGENT_LIST, appformSession.getCntpntShopId());
            if(jehuAgentDto != null) {
                recommendFlagYn = "N".equals(jehuAgentDto.getExpnsnStrVal2()) ? "N" : "Y";
            }
        }

        model.addAttribute("AppformReq", appformReqTemp);
        model.addAttribute("recommendFlagYn", recommendFlagYn);

        return returnUrl;
    }

    /**
     * <pre>
     * 설명     : 완료 페이지
     * google-analytics 구분 하기 위해
     * </pre>
     */
    @RequestMapping(value = {"/appForm/appJehuFormComplete.do", "/m/appForm/appJehuFormComplete.do"})
    public String appJehuFormComplete(Model model, @ModelAttribute AppformReqDto appformReqDto) throws ParseException {

        if (0 >= appformReqDto.getRequestKey()) {
            throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        //신청서 정보
        AppformReqDto appformReqTemp = appformSvc.getAppForm(appformReqDto);

        if (appformReqTemp == null) {
            throw new McpCommonException(ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
        }

        // ============= START: 완료페이지 진입 로직 강화 =============
        // 1. 셀프개통인 경우, OP2 응답 재확인
        if("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType())){
            McpRequestOsstDto mcpRequestOsstDto = new McpRequestOsstDto();
            mcpRequestOsstDto.setMvnoOrdNo(appformReqTemp.getResNo());
            mcpRequestOsstDto.setPrgrStatCd(EVENT_CODE_REQ_OPEN_RESULT);

            McpRequestOsstDto mcpRequestOsstDtoRtn= appformSvc.getRequestOsst(mcpRequestOsstDto);

            if(mcpRequestOsstDtoRtn == null || !isSuccessOP2(mcpRequestOsstDtoRtn.getRsltCd())){
                throw new McpCommonException("개통"+ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
            }
        }

        // 2. 신청정보와 본인인증 세션 정보 일치여부 확인 (본인인증한 신청서의 경우, ci값 존재)
        NiceResDto sessNiceRes = SessionUtils.getNiceReqTmpCookieBean();

        if(!StringUtil.isEmpty(appformReqTemp.getSelfCstmrCi())){
            if(sessNiceRes == null) throw new McpCommonException(ExceptionMsgConstant.F_BIND_EXCEPTION);

            if(!appformReqTemp.getSelfCstmrCi().equals(sessNiceRes.getConnInfo())){
                throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
            }
        }else{
            // 3. CI값이 없는 신청정보인 경우, 본인인증 세션의 이름/생년월일과 신청정보의 이름/생년월일 비교
            if(sessNiceRes != null){
                String authNm= StringUtil.NVL(sessNiceRes.getName(), "").toUpperCase().replaceAll(" ", "");
                String authBirth= StringUtil.NVL(sessNiceRes.getBirthDate(), "");
                String requestNm= appformReqTemp.getCstmrName();
                String requestRrn= appformReqTemp.getCstmrNativeRrn();

                if(CSTMR_TYPE_NM.equals(appformReqTemp.getCstmrType())){
                    requestNm= appformReqTemp.getMinorAgentName();
                    requestRrn= appformReqTemp.getMinorAgentRrn();
                }

                try {
                    requestNm= StringUtil.NVL(requestNm, "").toUpperCase().replaceAll(" ", "");
                    requestRrn= EncryptUtil.ace256Dec(requestRrn);
                } catch (CryptoException e) {
                    requestNm= "";
                    requestRrn= "";
                }

                if(!requestNm.equals(authNm) || requestRrn.indexOf(authBirth) < 0){
                    throw new McpCommonException(ExceptionMsgConstant.NICE_CERT_EXCEPTION);
                }
            }
        }
        // ============= END: 완료페이지 진입 로직 강화 =============

        String returnUrl = "/appForm/appJehuFormComplete";
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile".concat(returnUrl);
        } else {
            returnUrl = "/portal".concat(returnUrl);
        }

        model.addAttribute("AppformReq", appformReqTemp);

        //유심비 /  가입비 설정
        String cntpntShopId = appformReqTemp.getCntpntShopId();

        // 대리점 명 확인 필요...
        Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(cntpntShopId);

        if (agentInfoOjb != null) {
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");
            String roadnAdrZipcd = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_ZIPCD"), "");
            String roadnAdrBasSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_BAS_SBST"), "");
            String roadnAdrDtlSbst = StringUtil.NVL(agentInfoOjb.get("ROADN_ADR_DTL_SBST"), "");

            model.addAttribute("agentNM", orgnNM);
            model.addAttribute("agentAddr", roadnAdrZipcd + " " + roadnAdrBasSbst + " " + roadnAdrDtlSbst  );

        } else {
            model.addAttribute("agentNM", "");
            model.addAttribute("agentAddr", "");
        }


        NiceResDto sessNiceMobile = SessionUtils.getNiceMobileSession();
     // 신규개통이면서 셀프개통으로 진행한 케이스에만 알림톡발송
        if (OPER_TYPE_NEW.equals(appformReqTemp.getOperType()) && ("5".equals(appformReqTemp.getOnOffType()) || "7".equals(appformReqTemp.getOnOffType()))) {
                MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
                mspSmsTemplateMstDto = fCommonSvc.getMspSmsTemplateMst(Constants.SMS_COMPLETE_TEMPLATE_ID);
                if (mspSmsTemplateMstDto != null) {

                    Date today = new Date();
                    String strToday = DateTimeUtil.changeFormat(today, "yyyy-MM-dd");
                    MspRateMstDto rateInfo = fCommonSvc.getMspRateMst(appformReqTemp.getSocCode());

                    mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{mobileNo}", "010-"+appformReqTemp.getReqWantNumber2()+"-"+appformReqTemp.getReqWantNumber3()));
                    mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{openingDate}", strToday));

                    if (rateInfo != null && rateInfo.getRateNm() != null) {
                        mspSmsTemplateMstDto.setText(mspSmsTemplateMstDto.getText().replace("#{phonePlan}", rateInfo.getRateNm()));
                    }

                   if(sessNiceMobile !=null && sessNiceMobile.getsMobileNo() != null) {

                       smsSvc.sendKakaoNoti(mspSmsTemplateMstDto.getSubject(), sessNiceMobile.getsMobileNo(), mspSmsTemplateMstDto.getText(),
                            mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                            Constants.KAKAO_SENDER_KEY, String.valueOf(Constants.SMS_COMPLETE_TEMPLATE_ID));
                }
             }
        }

        return returnUrl;
    }
    @RequestMapping("/appform/sendUsimRegUrl.do")
    @ResponseBody
    public Map<String, Object> sendUsimRegUrl(@ModelAttribute AppformReqDto appformReqDto) throws ParseException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String strMsg = "";
        String resultCode = AJAX_SUCCESS;

        //온라인 신청서 상태값 변경 처리
        appformSvc.updateAppForPstate(appformReqDto.getRequestKey());
        McpRequestDto mcpRequest = appformSvc.getMcpRequest(appformReqDto.getRequestKey());
        String orderRcvTlphNo = appformReqDto.getMobileNo();

        MspSmsTemplateMstDto mspSmsTemplateMstDto = null;
        mspSmsTemplateMstDto =  fCommonSvc.getMspSmsTemplateMst(Constants.SMS_JEHU_USIM_REG_TEMPLATE_ID);
        if (mspSmsTemplateMstDto != null) {

            String cntpntShopId = mcpRequest.getCntpntShopId();
            String resNo = mcpRequest.getResNo();
            //대리점명 확인
            Map<String,String> agentInfoOjb = appformSvc.getAgentInfoOjb(cntpntShopId);
            String orgnNM = StringUtil.NVL(agentInfoOjb.get("ORGN_NM"), "");

            String smsMsg = mspSmsTemplateMstDto.getText();
            smsMsg = smsMsg.replace("#{cntpntShopNm}", orgnNM).replace("#{resNo}", resNo);

            mspSmsTemplateMstDto.setText(smsMsg);
            smsSvc.sendKakaoNoti( mspSmsTemplateMstDto.getSubject(), orderRcvTlphNo, mspSmsTemplateMstDto.getText(),
                    mspSmsTemplateMstDto.getCallback(), mspSmsTemplateMstDto.getkTemplateCode(),
                    Constants.KAKAO_SENDER_KEY, String.valueOf(mspSmsTemplateMstDto.getTemplateId()));
        }

        rtnMap.put("RESULT_MSG", strMsg);
        rtnMap.put("RESULT_CODE", resultCode);


        return rtnMap;
    }

    @RequestMapping("/appform/getSalePlcyInfo.do")
    @ResponseBody
    public Map<String, Object> getSalePlcyInfo(@ModelAttribute AppformReqDto appformReqDto) throws ParseException {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String strMsg = "";
        String resultCode = AJAX_SUCCESS;

        if(StringUtils.isEmpty(appformReqDto.getCntpntShopId()) || StringUtils.isEmpty(appformReqDto.getPlcySctnCd())) {
            throw new McpCommonException(F_BIND_EXCEPTION, "/main.do");
        }

        List<MspSalePlcyMstDto> mspSalePlcyMstDtoList = appformSvc.getSalePlcyInfo(appformReqDto);
        if(mspSalePlcyMstDtoList == null || mspSalePlcyMstDtoList.isEmpty()) {
            throw new McpCommonException(ExceptionMsgConstant.NO_EXSIST_PROD_BAS_EXCEPTION, "/main.do");
        }
        rtnMap.put("salePlcyCd", mspSalePlcyMstDtoList.get(0).getSalePlcyCd());
        rtnMap.put("enggMnthCnt", mspSalePlcyMstDtoList.get(0).getAgrmTrm());
        rtnMap.put("prdtId", mspSalePlcyMstDtoList.get(0).getPrdtId());
        rtnMap.put("prdtNm", mspSalePlcyMstDtoList.get(0).getPrdtNm());


        rtnMap.put("RESULT_MSG", strMsg);
        rtnMap.put("RESULT_CODE", resultCode);


        return rtnMap;
    }

    /**
     * @param : AppformReqDto data설정 처리
     * @return :
     * @Description : dataSharing 정보 확인 후 data설정
     * @Author : power
     * @Create Date : 2025. 06. 2
     */
    public Map<String, Object> fnSetDataOfdataSharing(AppformReqDto appformReqDto) {

        Map<String, Object> rtnMap = new HashMap<String, Object>();


        //로그인 정보 확인 및 정회원 여부 확인
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = null;
        McpUserCntrMngDto resultOut = new McpUserCntrMngDto();

        if (userSession != null) {
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        } else {
            McpUserCntrMngDto out = SessionUtils.getNonmemberSharingInfo();
            if (out == null) {
                throw new McpCommonJsonException("0003", ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION);
            }
            resultOut = msfMypageSvc.selectCntrListNoLogin(out);
        }

        //서비스 번호 ContractNum 검증
        Boolean isOwn = false;
        String cntrMobileNo = "";
        String certContractNum = null;  // 모회선 계약번호
        String customerId = null;       // 고객아이디

        if (userSession != null) {
            for (McpUserCntrMngDto userCntrMng : cntrList) {
                if (userCntrMng.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                    isOwn = true;
                    cntrMobileNo = userCntrMng.getCntrMobileNo();
                    certContractNum = userCntrMng.getContractNum();
                    customerId = userCntrMng.getCustId();
                    break;
                }
            }
        } else {
            if (resultOut != null && resultOut.getSvcCntrNo().equals(appformReqDto.getContractNum())) {
                isOwn = true;
                cntrMobileNo = resultOut.getCntrMobileNo();
                certContractNum = resultOut.getContractNum();
                customerId = resultOut.getCustId();
            }
        }

        if (!isOwn) {
            throw new McpCommonJsonException("0004", ExceptionMsgConstant.OWN_EXCEPTION);
        }

        // 본인인증 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("0005", NICE_CERT_EXCEPTION);
        }

        //1. 신청서 테이블에 저장
        //1.1 기존에 저장한 정보 확인
        //두번 호출 하면 첫번째 요청에만 DB 저장한다.
        AppformReqDto rtnAppformReqDto = SessionUtils.getAppformSession();
        if (rtnAppformReqDto == null) {
            //1-2. 계약번호에 신청서 정보 설정
            AppformReqDto appformReqResult = appformSvc.getCopyMcpRequest(appformReqDto);
            if (appformReqResult == null) {
                throw new McpCommonJsonException("0006", ExceptionMsgConstant.NOTFOUND_REQUEST_DATA_EXCEPTION);
            }

            //1-2 인증정보 주민등록 번호 확인
            String cstmrNativeRrn = "";
            if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
                cstmrNativeRrn = appformReqResult.getMinorAgentRrnDesc();
                if (6 < cstmrNativeRrn.length()) {
                    cstmrNativeRrn = cstmrNativeRrn.substring(0, 6);
                }
            } else {
                //1-2 인증정보 주민등록 번호 확인
                cstmrNativeRrn = appformReqResult.getCstmrNativeRrnDesc();
                if (6 < cstmrNativeRrn.length()) {
                    cstmrNativeRrn = cstmrNativeRrn.substring(0, 6);
                }
            }

            if (sessNiceRes.getBirthDate().indexOf(cstmrNativeRrn) < 0) {
                throw new McpCommonJsonException("0007", NICE_CERT_EXCEPTION);
            }

            appformReqResult.setOperType(OPER_TYPE_NEW); //operType: NAC3  <- 고정
            appformReqResult.setOnlineAuthType(sessNiceRes.getAuthType());
            appformReqResult.setOnlineAuthInfo("ReqNo:" + sessNiceRes.getReqSeq() + ", ResNo:" + sessNiceRes.getResSeq());
            appformReqResult.setSelfCstmrCi(sessNiceRes.getConnInfo());
            appformReqResult.setServiceType("PO");
            appformReqResult.setClauseInsrProdFlag("N");
            appformReqResult.setClauseRwdFlag("N");

            /*
            if ("Y".equals(NmcpServiceUtils.isMobile())) {
                appformReqResult.setOnOffType("7");
            } else {
                appformReqResult.setOnOffType("5");
            }
             */
            appformReqResult.setOnOffType(appformReqDto.getOnOffType());

            appformReqResult.setReqWantNumber(cntrMobileNo.substring(cntrMobileNo.length() - 4, cntrMobileNo.length()));
            appformReqResult.setCntpntShopId(orgnId);
            appformReqResult.setPstate("00");
            appformReqResult.setCstmrType(appformReqDto.getCstmrType());
            appformReqResult.setMaxDiscount3(0);
            appformReqResult.setDcAmt(0);
            appformReqResult.setAddDcAmt(0);
            appformReqResult.setEnggMnthCnt(0);
            appformReqResult.setModelInstallment(0);
            appformReqResult.setModelPriceVat(0);
            appformReqResult.setModelDiscount2(0);
            appformReqResult.setModelDiscount3(0);
            appformReqResult.setModelPrice(0);
            appformReqResult.setSocCode("KISOPMDSB");
            appformReqResult.setRealMdlInstamt(0);
            appformReqResult.setSettlAmt(0);
            appformReqResult.setPrdtSctnCd("LTE");
            appformReqResult.setJoinPrice(0);
            appformReqResult.setModelMonthly("0");  //MODEL_MONTHLY
            appformReqResult.setReqUsimSn(appformReqDto.getReqUsimSn());

            if(appformReqDto.getCstmrMobileFn() != null && !appformReqDto.getCstmrMobileFn().isEmpty()) {
                appformReqResult.setCstmrMobileFn(appformReqDto.getCstmrMobileFn());
                appformReqResult.setCstmrMobileMn(appformReqDto.getCstmrMobileMn());
                appformReqResult.setCstmrMobileRn(appformReqDto.getCstmrMobileRn());

                appformReqResult.setCstmrTelFn(appformReqDto.getCstmrMobileFn());
                appformReqResult.setCstmrTelMn(appformReqDto.getCstmrMobileMn());
                appformReqResult.setCstmrTelRn(appformReqDto.getCstmrMobileRn());
            }

            if (CSTMR_TYPE_NM.equals(appformReqDto.getCstmrType())) {
                appformReqResult.setMinorAgentName(appformReqDto.getMinorAgentName());
                appformReqResult.setMinorAgentRrn(appformReqDto.getMinorAgentRrn());
                appformReqResult.setMinorAgentTelFn(appformReqDto.getMinorAgentTelFn());
                appformReqResult.setMinorAgentTelMn(appformReqDto.getMinorAgentTelMn());
                appformReqResult.setMinorAgentTelRn(appformReqDto.getMinorAgentTelRn());
                appformReqResult.setMinorAgentRelation(appformReqDto.getMinorAgentRelation());
                appformReqResult.setMinorAgentAgrmYn(appformReqDto.getMinorAgentAgrmYn());

                //청소년
                appformReqResult.setMinorSelfCertType(appformReqDto.getSelfCertType());
                appformReqResult.setMinorSelfIssuExprDt(appformReqDto.getSelfIssuExprDt());
                appformReqResult.setMinorSelfInqryAgrmYn("Y"); //본인인증조회동의

                if ("02".equals(appformReqDto.getSelfCertType()) || "04".equals(appformReqDto.getSelfCertType())) {
                    appformReqResult.setMinorSelfIssuNum(appformReqDto.getSelfIssuNum());
                }
            } else {
                appformReqResult.setSelfCertType(appformReqDto.getSelfCertType());
                appformReqResult.setSelfIssuExprDt(appformReqDto.getSelfIssuExprDt());
                appformReqResult.setSelfInqryAgrmYn("Y");
                appformReqResult.setCstmrForeignerNation(appformReqDto.getCstmrForeignerNation());

                if ("02".equals(appformReqDto.getSelfCertType()) || "04".equals(appformReqDto.getSelfCertType())) {
                    appformReqResult.setSelfIssuNum(appformReqDto.getSelfIssuNum());
                }
            }

            //안면인증값 설정
            appformReqResult.setFathTrgYn(appformReqDto.getFathTrgYn());
            appformReqResult.setClauseFathFlag(appformReqDto.getClauseFathFlag());
            appformReqResult.setFathTransacId(appformReqDto.getFathTransacId());
            appformReqResult.setFathCmpltNtfyDt(appformReqDto.getFathCmpltNtfyDt());

            //유심비 설정
            appformReqResult.setUsimPayMthdCd("1");
            appformReqResult.setUsimPrice(0);
            appformReqResult.setReqPayType("0"); //통합 청구
            appformReqResult.setPrntsContractNo(certContractNum);//모회선 계약번호
            appformReqResult.setPrntsCtn(cntrMobileNo); //모회선 전화번호
            //청구계정번호  PRNTS_BILL_NO
            String billAcntNo = msfMypageSvc.selectBanSel(certContractNum);
            appformReqResult.setPrntsBillNo(billAcntNo); //청구계정번호

            //약관동의
            appformReqResult.setClausePriOfferFlag(appformReqDto.getClausePriOfferFlag());
            appformReqResult.setClausePriCollectFlag(appformReqDto.getClausePriCollectFlag());
            appformReqResult.setClauseEssCollectFlag(appformReqDto.getClauseEssCollectFlag());
            appformReqResult.setPersonalInfoCollectAgree(appformReqDto.getPersonalInfoCollectAgree());
            appformReqResult.setClausePriAdFlag(appformReqDto.getClausePriAdFlag());
            appformReqResult.setOthersTrnsAgree(appformReqDto.getOthersTrnsAgree());
            appformReqResult.setOthersTrnsKtAgree(appformReqDto.getOthersTrnsKtAgree());
            appformReqResult.setOthersAdReceiveAgree(appformReqDto.getOthersAdReceiveAgree());
            appformReqResult.setIndvLocaPrvAgree(appformReqDto.getIndvLocaPrvAgree());
            appformReqResult.setNwBlckAgrmYn(appformReqDto.getNwBlckAgrmYn());
            appformReqResult.setAppBlckAgrmYn(appformReqDto.getAppBlckAgrmYn());

            //가입비 설정
            //직영 대리점
            /** 가입비 납부방법
             * 1 면제
             * 2 일시납
             * 3 3개월분납
             */
            NmcpCdDtlDto nmcpCdDtlDto = NmcpServiceUtils.getCodeNmDto(Constants.GROUP_CODE_USIM_PRICE_INFO, appformReqResult.getSocCode());

            if (nmcpCdDtlDto != null && "Y".equals(nmcpCdDtlDto.getExpnsnStrVal1())) {
                //유심비 /  가입비 설정
                UsimBasDto usimBasDtoParm = new UsimBasDto();
                usimBasDtoParm.setOperType(appformReqDto.getOperTypeSmall());
                usimBasDtoParm.setDataType(appformReqDto.getPrdtSctnCd());
                List<UsimMspRateDto> usimPriceList = usimService.selectJoinUsimPriceNew(usimBasDtoParm);
                int intJoinPrice = 0;

                if (usimPriceList != null && usimPriceList.size() > 0) {
                    intJoinPrice = Integer.parseInt(usimPriceList.get(0).getJoinPrice());
                }

                appformReqResult.setJoinPayMthdCd("3");
                appformReqResult.setJoinPrice(intJoinPrice);
            } else {
                appformReqResult.setJoinPayMthdCd("1");
                appformReqResult.setJoinPrice(0);
            }

            /*
             * 12 스팸차단서비스 29 발신번호표시 30 통합사서함 31 정보제공사업자번호차단
             */
            String[] additionKeyList = {"12", "29", "30", "31"};
            appformReqResult.setAdditionKeyList(additionKeyList);

            //접점코드로 대리점 코드 조회
            String agentCode = "";

            try {
                agentCode = appformSvc.getAgentCode(appformReqResult.getCntpntShopId());
            } catch (RestClientException e) {
                logger.error(e.getMessage());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            appformReqResult.setAgentCode(agentCode);
            appformReqResult.setManagerCode("M0001");
            appformReqResult.setRid("-");
            appformReqResult.setViewFlag("Y");
            appformReqResult.setRequestStateCode("00");
            appformReqResult.setRip(ipstatisticService.getClientIp());

            //다시 암호화 처리
            if (!StringUtils.isBlank(appformReqResult.getReqAccountNumber())) {
                appformReqResult.setReqAccountNumber(appformReqResult.getReqAccountNumber());
            }
            if (!StringUtils.isBlank(appformReqResult.getReqCardRrn())) {
                appformReqResult.setReqCardRrn(appformReqResult.getReqCardRrn());
            }
            if (!StringUtils.isBlank(appformReqResult.getReqAccountRrn())) {
                appformReqResult.setReqAccountRrn(appformReqResult.getReqAccountRrn());
            }
            if (!StringUtils.isBlank(appformReqResult.getReqCardNo())) {
                appformReqResult.setReqCardNo(appformReqResult.getReqCardNo());
            }

            /* 직영 평생할인 프로모션 ID 가져오기 */
            String prmtId = appformSvc.getChrgPrmtId(appformReqResult);
            if(!StringUtils.isBlank(prmtId))appformReqResult.setPrmtId(prmtId);

            String cpntId = SessionUtils.getFathSession().getCpntId();
            if (StringUtils.isEmpty(cpntId)) {
                appformReqResult.setCpntId(appformReqResult.getCntpntShopId());
            } else {
                appformReqResult.setCpntId(cpntId);
            }

            //1-3. 신청서 저장 호출
            try {
                // ============ STEP START ============
                // 1. nicePin 인증연동 확인
                if(0 >= certService.getModuTypeStepCnt("nicePin", "")){
                    throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
                }

                // 2. 데이터쉐어링 최종 정보 확인
                // 계약번호, 유심번호, CI
                String[] certKey= {"urlType", "contractNum", "reqUsimSn", "connInfo"};
                String[] certValue= {"saveShareAppform", certContractNum, appformReqResult.getReqUsimSn(), sessNiceRes.getConnInfo()};

                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
                }

                appformReqResult.setCertMenuType("sharing");
                // ============ STEP END ============
                rtnAppformReqDto = appformSvc.saveAppform(appformReqResult, null, null);
                SessionUtils.saveAppformDto(rtnAppformReqDto);

            } catch(McpCommonJsonException e){
                // STEP 오류 처리
                throw new McpCommonJsonException(e.getRtnCode(), e.getErrorMsg());
            } catch(DataAccessException e) {
                throw new McpCommonJsonException("999", DB_EXCEPTION);
            } catch (Exception e) {
                throw new McpCommonJsonException("999", COMMON_EXCEPTION);
            }
        } else {
            //1-4. 신청서 SESSION
            rtnAppformReqDto = SessionUtils.getAppformSession();
        }


        rtnMap.put("CNTR_MOBILE_NO", cntrMobileNo);
        rtnMap.put("CONTRANT_NUM", certContractNum);
        rtnMap.put("CUSTOMER_ID", customerId);
        rtnMap.put("APP_FORM_REQ", rtnAppformReqDto);
        return rtnMap;
    }

    /**
     * <pre>
     * 설명     : 고객 안면인증 대상 여부 조회 (FT0)
     * @param   osstFathReqDto
     * @return: Map<String, Object>
     * 날짜     : 2025. 12. 19.
     * 작성자   : 박민건
     * </pre>
     */
    @RequestMapping(value = "/appform/reqFathTgtYnAjax.do")
    @ResponseBody
    public Map<String, Object> reqFathTgtYnAjax(OsstFathReqDto osstFathReqDto) {

        if (StringUtils.isEmpty(osstFathReqDto.getOnlineOfflnDivCd()) ||
                 StringUtils.isEmpty(osstFathReqDto.getOrgId()) ||
                 StringUtils.isEmpty(osstFathReqDto.getSelfCertType())) {
            throw new McpCommonJsonException("1001", F_BIND_EXCEPTION);
        }

        HashMap<String, Object> rtnMap = new HashMap<>();
        // 안면인증 사용 공통코드 확인
        if(!"Y".equals(fathService.getFathUseYn())) {
            throw new McpCommonJsonException("00002", "안면인증 미사용");
        }
        // 안면인증 대상 신분증 여부확인
        String fathCertIdTypeYn = NmcpServiceUtils.getCodeNm("fathCertIdType", osstFathReqDto.getSelfCertType());
        if(!"Y".equals(fathCertIdTypeYn)) {
            throw new McpCommonJsonException("00002", "안면인증 미대상");
        }


        //이미 안면인증 완료를 하여 세션에 안면인증 완료일이 들어가있는경우
        if(!StringUtil.isEmpty(SessionUtils.getFathSession().getCmpltNtfyDt())) {
            throw new McpCommonJsonException("1002", "이미 안면인증을 완료하였습니다.");
        }

        String cpntId = fathService.findAndSaveSessionCpntId(osstFathReqDto.getOrgId());
        if (StringUtils.isEmpty(cpntId)) {
            String onOffType = osstFathReqDto.getOnOffType();
            if("5".equals(onOffType) || "7".equals(onOffType)) {
                throw new McpCommonJsonException("00001", "안면인증 대상이 아닙니다. <br> 현재 상담사 개통 신청만 가능합니다. <br>상담사 개통신청으로 진행합니다.");
            } else {
                throw new McpCommonJsonException("00002", "안면인증 없이 신분증정보 입력으로 진행됩니다. <br> 향후 추가 인증이 필요할 수 있습니다.");
            }
        }
        osstFathReqDto.setCpntId(cpntId);

        //안면인증 세션 초기화
        SessionUtils.initializeFathSession();
        String tempResNo = fathService.generateTempResNo();
        SessionUtils.saveFathTempResNo(tempResNo);

        osstFathReqDto.setResNo(tempResNo);

        osstFathReqDto.setRetvCdVal(FATH_RETV_CD_VAL.get(osstFathReqDto.getSelfCertType()));
        //대리점 코드 조회
        String agentCode = "";
        agentCode = appformSvc.getAgentCode(osstFathReqDto.getOrgId());
        if (StringUtils.isBlank(agentCode)) {
            throw new McpCommonJsonException("1004", "시스템 오류로 메세지가 반복되면 고객센터로 문의부탁드립니다.");
        } else {
            osstFathReqDto.setAsgnAgncId(agentCode);
        }

        return appformSvc.processOsstFt0Service(osstFathReqDto);
    }

    /**
     * <pre>
     * 설명     : 고객 안면인증 URL 요청 (FS8)
     * @param   osstFathReqDto
     * @return: Map<String, Object>
     * 날짜     : 2025. 12. 19.
     * 작성자   : 박민건
     * </pre>
     */
    @RequestMapping(value = "/appform/reqFathUrlRqtAjax.do")
    @ResponseBody
    public Map<String, Object> reqFathUrlRqtAjax(OsstFathReqDto osstFathReqDto) {

        FathSessionDto fathSessionDto = SessionUtils.getFathSession();
        //이미 안면인증 완료를 하여 세션에 안면인증 완료일이 들어가있는경우
        if(!StringUtil.isEmpty(fathSessionDto.getCmpltNtfyDt())) {
            throw new McpCommonJsonException("1005", "이미 안면인증을 완료하였습니다.");
        }

        // 연동 시도횟수
        if (SessionUtils.getFathSession().getTryCount() > 4) {
            throw new McpCommonJsonException("1006", FATH_LIMIT_EXCEPTION);
        }

        //resNo 존재 시 (셀프안면인증 개인화URL)
        if("P".equals(osstFathReqDto.getGubun())) {
            if(StringUtils.isEmpty(osstFathReqDto.getOnlineOfflnDivCd()) || StringUtils.isEmpty(osstFathReqDto.getOperType())){
                throw new McpCommonJsonException("1007", F_BIND_EXCEPTION);
            }

            FathFormInfo fathFormInfo = fathService.getFathFormInfo(osstFathReqDto.getResNo(), osstFathReqDto.getOperType());
            if(fathFormInfo == null) {
                throw new McpCommonJsonException("1008", F_BIND_EXCEPTION);
            }

            osstFathReqDto.setSelfCertType(fathFormInfo.getSelfCertType());
            osstFathReqDto.setOrgId(fathFormInfo.getCntpntShopId());
            osstFathReqDto.setCpntId(fathFormInfo.getCntpntShopId());
            osstFathReqDto.setSmsRcvTelNo(fathFormInfo.getFathTelNo());

        } else {
            //임시예약번호
            String tempResNo = SessionUtils.getFathSession().getTempResNo();
            if(StringUtils.isEmpty(tempResNo)) {
                throw new McpCommonJsonException("1009", FATH_CERT_EXPIR_EXCEPTION);
            }
            osstFathReqDto.setResNo(tempResNo);
        }

        if(!StringUtils.isEmpty(osstFathReqDto.getContractNum())) { //ESIM WATCH, 데이터쉐어링
            // 계약번호로 이전 신청서 조회
            Map<String, String> resOjb = mypageUserService.selectContractObj("", "", osstFathReqDto.getContractNum());
            if (resOjb == null) {
                throw new McpCommonJsonException("1010", ExceptionMsgConstant.F_BIND_EXCEPTION);
            }
            osstFathReqDto.setSmsRcvTelNo(resOjb.get("SUBSCRIBER_NO"));
        }

        if(StringUtils.isEmpty(osstFathReqDto.getOnlineOfflnDivCd()) ||
                StringUtils.isEmpty(osstFathReqDto.getOrgId()) ||
                StringUtils.isEmpty(osstFathReqDto.getCpntId()) ||
                StringUtils.isEmpty(osstFathReqDto.getSmsRcvTelNo()) ||
                StringUtils.isEmpty(osstFathReqDto.getOperType())) {
            throw new McpCommonJsonException("1011", ExceptionMsgConstant.F_BIND_EXCEPTION);
        }

        String cpntId = fathService.findAndSaveSessionCpntId(osstFathReqDto.getCpntId());
        if (StringUtils.isEmpty(cpntId)) {
            String onOffType = osstFathReqDto.getOnOffType();
            if("5".equals(onOffType) || "7".equals(onOffType)) {
                throw new McpCommonJsonException("00001", "안면인증 대상이 아닙니다. <br> 현재 상담사 개통 신청만 가능합니다. <br>상담사 개통신청으로 진행합니다.");
            } else {
                throw new McpCommonJsonException("00002", "안면인증 없이 신분증정보 입력으로 진행됩니다. <br> 향후 추가 인증이 필요할 수 있습니다.");
            }
        }
        osstFathReqDto.setCpntId(cpntId);
        osstFathReqDto.setRetvCdVal(FATH_RETV_CD_VAL.get(osstFathReqDto.getSelfCertType()));
        osstFathReqDto.setFathSbscDivCd(FATH_SBSC_DIV_CD.get(osstFathReqDto.getOperType()));

        //대리점 코드 조회
        String agentCode = appformSvc.getAgentCode(osstFathReqDto.getOrgId());
        if (StringUtils.isBlank(agentCode)) {
            throw new McpCommonJsonException("1013", "시스템 오류로 메세지가 반복되면 고객센터로 문의부탁드립니다.");
        } else {
            osstFathReqDto.setAsgnAgncId(agentCode);
        }

        return appformSvc.processOsstFs8Service(osstFathReqDto);
    }

    /**
     * <pre>
     * 설명     : -고객 안면인증 내역 조회  (PUSH 알림 결과를 조회하여 안면인증 결과에 따라 처리한다.
     *           -PUSH알림 안면인증 결과확인 버튼을 최초로 누른 이후 10분이후에도 PUSH 알림이 들어오지 않은 경우
     *           FS9(안면인증결과확인) 연동을 통해 안면인증 결과를 조회한다.
     * @param   osstFathReqDto
     * @return: Map<String, Object>
     * 날짜     : 2025. 12. 19.
     * 작성자   : 박민건
     * </pre>
     */
    @RequestMapping(value = "/appform/reqFathTxnRetvAjax.do")
    @ResponseBody
    public Map<String, Object> reqFathTxnRetvAjax(OsstFathReqDto osstFathReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // 세션의 트랜잭션 ID
        String fathTransacId = SessionUtils.getFathSession().getTransacId();
        if(StringUtils.isEmpty(fathTransacId)) {
            throw new McpCommonJsonException("9999", FATH_CERT_EXPIR_EXCEPTION);
        }
        // 안면인증 결과 PUSH 조회
        FathDto fathDto = fathService.selectFathResltPush(fathTransacId);

        if(fathDto == null) { //PUSH알림이 들어오지 않은경우
            //최초 안면인증확인 버튼 클릭시점 세션에 저장
            if(SessionUtils.getFathSession().getFathResltFirstReqAt() == null) {
                SessionUtils.saveFathResltFirstReqAt();
                throw new McpCommonJsonException("9998", FATH_CERT_RESLT1_EXCEPTION);
            }
            //FS9는 트래픽1회로 제한 (연동 이력이 존재하면 재연동하지않는다.)
            if("Y".equals(SessionUtils.getFathSession().getIsFs9())) {
                throw new McpCommonJsonException("9997", "이미 안면인증 결과확인을 하셨습니다.");
            }
            //공통코드 안면인증 PUSH 타임아웃 초과시간(2026-02-03 기준 10분)
            long fathResltTimeOut = Integer.parseInt(NmcpServiceUtils.getCodeNm("fathCertPolicy", "fathResltTimeOut")) * 60 * 1000;
            Date now = new Date();
            long passTimeMs  = now.getTime() - SessionUtils.getFathSession().getFathResltFirstReqAt().getTime();

            if(passTimeMs > fathResltTimeOut) { //타임아웃 시간 초과 시 FS9 연동
                return appformSvc.processOsstFs9Service(osstFathReqDto, "");
            } else {
                throw new McpCommonJsonException("9995", FATH_CERT_RESLT1_EXCEPTION);
            }
        }
        // 안면인증 PUSH 결과 성공인 경우
        if("0000".equals(fathDto.getFathResltCd())) {
            //복호화
            try {
                fathDto.setCustNm(EncryptUtil.ace256Dec(fathDto.getCustNm()));              //안면인증 이름
                fathDto.setCustIdfyNo(EncryptUtil.ace256Dec(fathDto.getCustIdfyNo()));      //안면인증 주민번호
                fathDto.setDriveLicnsNo(EncryptUtil.ace256Dec(fathDto.getDriveLicnsNo()));  //안면인증 운전면허번호
            } catch (CryptoException e) {
                throw new McpCommonJsonException("0001", ACE_256_DECRYPT_EXCEPTION);
            }
            rtnMap = fathService.fathSuccRtn(fathDto, osstFathReqDto);
        } else {
            rtnMap = fathService.fathFailRtn(osstFathReqDto);
        }
        return rtnMap;
    }

    /**
     * 유심 없다면? 페이지
     * eSIM / USIM 탭
     */
    @RequestMapping(value = {"/appForm/withoutUsim.do", "/m/appForm/withoutUsim.do"})
    public String withoutUsim(@RequestParam(value = "tab", required = false) String tab ,Model model) {

        String returnUrl;

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/appForm/withoutUsim";
        } else {
            returnUrl = "/portal/appForm/withoutUsim";
        }

        model.addAttribute("tab", tab);

        return returnUrl;
    }

    /**
     * 유심 있다면? 페이지
     * 셀프개통 / 상담사개통 탭
     */
    @RequestMapping(value = {"/appForm/withUsim.do", "/m/appForm/withUsim.do"})
    public String withUsim(@RequestParam(value = "tab", required = false) String tab ,Model model) {

        String returnUrl;

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/appForm/withUsim";
        } else {
            returnUrl = "/portal/appForm/withUsim";
        }

        model.addAttribute("tab", tab);

        return returnUrl;
    }

    @RequestMapping("/appform/requestCustFathTxnSkipAjax.do")
    @ResponseBody
    public Map<String, Object> requestCustFathTxnSkipAjax(OsstFathReqDto osstFathReqDto) {
        return appformSvc.processOsstFt1Service(osstFathReqDto);
    }

    @RequestMapping("/appform/isEnabledFT1.do")
    @ResponseBody
    public Map<String, Object> isEnabledFT1() {
        HashMap<String, Object> rtnMap = new HashMap<>();
        rtnMap.put("isEnabledFT1", appformSvc.isEnabledFT1());
        return rtnMap;
    }

    @RequestMapping(value = {"/appForm/checkLimitCounselAjax.do", "/m/appForm/checkLimitCounselAjax.do"})
    @ResponseBody
    public Map<String, Object> checkLimitCounselAjax(AppformReqDto appformReqDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        if(StringUtils.isBlank(appformReqDto.getSocCode()) || appformReqDto.getRequestKeyTemp() <= 0) {
            rtnMap.put("RESULT_CODE", "-2");
            rtnMap.put("RESULT_MSG", "필수 인자값[SocCode,requestKey]이 없습니다.");
            return rtnMap;
        }

        NmcpCdDtlDto exceptionPrice = NmcpServiceUtils.getCodeNmDto(GROUP_CODE_EXCEPTION_LIST_PRICE_CD, appformReqDto.getSocCode());
        if(exceptionPrice != null) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            rtnMap.put("RESULT_MSG", "예외 요금제 입니다. 신규 개통 가능 합니다.");
            return rtnMap;
        }

        //2026.03 - 신규(셀프/상담사)만 30일 이내 1회선으로 개통 제한
        //NMCP_REQUEST_TEMP에서 OPER_TYPE(NAC3), ON_OFF_TYPE(0,3) 인지 확인
        AppformReqDto reqTemp = appformSvc.getAppFormTemp(appformReqDto.getRequestKeyTemp());

        if(reqTemp != null) {
            //직영, 신규개통, 상담사 신청서 일 때에만
            if("1100011741".equals(reqTemp.getCntpntShopId()) && "NAC3".equals(reqTemp.getOperType()) && Arrays.asList("0", "3").contains(reqTemp.getOnOffType())) {

                //신규개통 이력 체크
                AppformReqDto rtnAppformReq = appformSvc.getLimitForm(reqTemp);

                if(rtnAppformReq != null) {
                    rtnMap.put("RESULT_CODE", "-1");
                    rtnMap.put("ERROR_NE_MSG", SELF_LIMIT_EXCEPTION);

                    //이력 정보 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("SELF010_LIMIT_CHECK_5");
                    mcpIpStatisticDto.setPrcsSbst(rtnAppformReq.getContractNum());  //계약번호
                    mcpIpStatisticDto.setParameter("NCN[" + rtnAppformReq.getContractNum() + "]CI[" + rtnAppformReq.getSelfCstmrCi() + "]");
                    mcpIpStatisticDto.setTrtmRsltSmst("CHECK");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);

                    return rtnMap;
                }
            }
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }
}