package com.ktmmobile.mcp.point.controller;


import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonException;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.point.dto.MstoreContentItemDto;
import com.ktmmobile.mcp.point.service.MstoreEncryption;
import com.ktmmobile.mcp.point.service.MstoreService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.INVALID_REFERER_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_FRONT_SESSION_EXCEPTION;

@Controller
public class MstoreController {

    private static final Logger logger = LoggerFactory.getLogger(MstoreController.class);

    @Autowired
    private MstoreService mstoreService;

    @Value("${SERVER_NAME}")
    private String serverName;

    @Autowired
    private CertService certService;

    @Value("${mstore.cert.url}")
    private String mstoreCertUrl;

    /**
     * Mstore 소개 페이지
     * @Date : 2023.08.16
     * @param request
     * @param model
     * @return String
     */
    @RequestMapping(value = { "/point/mstoreView.do", "/m/point/mstoreView.do" })
    public String mstoreView(HttpServletRequest request, ModelMap model, @RequestParam(required = false, value="landingUrl") String landingUrl) {
        model.addAttribute("landingUrl", landingUrl);

        // 본인인증 세션 초기화 : Mstore 페이지 진입 시
        SessionUtils.saveNiceRes(null);

        // 1. 리턴페이지 설정
        String jspPageName = "/portal/point/mstoreView";
        model.addAttribute("isMobile", "N");
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            model.addAttribute("isMobile", "Y");
            jspPageName = "/mobile/point/mstoreView";
        }

        // 2. mstore 이용 가능 여부 체크
        Map<String, String> limitChkMap= mstoreService.chkMstoreUseLimit();

        String errorCause= limitChkMap.get("errorCause");
        String resultCd= limitChkMap.get("resultCd");

        model.addAttribute("bannerInfo", NmcpServiceUtils.getBannerList("C002")); // bannCtg

        // 2-1. 비로그인
        if("LOGIN".equals(errorCause)){
            model.addAttribute("loginYn", "N");
            return jspPageName;
        }

        // 2-2. resultCd와 errorCause에 따라 화면 표출
        // CASE 1) resultCd값이 0000인 경우: M스토어 이용 가능
        // CASE 2) resultCd값이 0003인 경우: 본인인증 폼 표출
        // CASE 3) 이외의 경우: 이용불가 문구 표출
        model.addAttribute("resultCd", resultCd);
        model.addAttribute("errorCause", errorCause);
        model.addAttribute("errorMsg", getMstoreErrorMsg(errorCause, resultCd));

        return jspPageName;
    }

    /**
     * Mstore 이용약관 동의 여부 확인
     * @Date : 2023.08.16
     * @param request
     * @return  Map<String,String>
     */
    @RequestMapping(value = "/point/mstoreTermsAgreeChk.do")
    @ResponseBody
    public Map<String,String> mstoreTermsAgreeChk(HttpServletRequest request) {

        Map<String,String> rtnMap = new HashMap<>();

        // 1. 로그인 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())){
            throw new McpCommonJsonException("0001" ,NO_FRONT_SESSION_EXCEPTION);
        }

        // 2. 이용약관 동의 여부 확인
        boolean agreeFlag= mstoreService.getMstoreTermsAgreeYn(userSession.getUserId());

        if(agreeFlag) rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);
        else rtnMap.put("RESULT_CODE", "0002");

        return rtnMap;
    }

    /**
     * Mstore 이용약관 이력 기록
     * @Date : 2023.08.16
     * @param agreeYn
     * @return  Map<String,String>
     */
    @RequestMapping(value = "/point/mstoreTermsAgreeAjax.do")
    @ResponseBody
    public Map<String,String> mstoreTermsAgreeAjax(HttpServletRequest request, @RequestParam(value="agreeYn") String agreeYn) {

        boolean limitChk = serverChk();
        if(!limitChk)  throw new McpCommonJsonException("운영에서만 이용 가능한 서비스입니다.");

        Map<String,String> rtnMap = new HashMap<>();

        // 이용약관 이력 기록 (취소 불가)
        if(!"Y".equalsIgnoreCase(agreeYn)){
            throw new McpCommonJsonException("M마켓 이용 안내에 동의하셔야 합니다.");
        }

        mstoreService.chgMstoreTermsAgreeHist(agreeYn);

        rtnMap.put("RESULT_CODE", Constants.AJAX_SUCCESS);

        return rtnMap;
    }


    /**
     * Mstore 쇼핑몰 이동 (SSO 연동)
     * @Date : 2023.07.13
     * @param request
     * @param model
     * @return
     */
    @RequestMapping(value = { "/point/mstoreAuth.do", "/m/point/mstoreAuth.do" })
    public String mstoreAuth(HttpServletRequest request, ModelMap model, @RequestParam(required = false, value="landingUrl") String landingUrl) {

        boolean limitChk = serverChk();
        if(!limitChk) throw new McpCommonException("운영에서만 이용 가능한 서비스입니다.");

        String jspPageName = "/portal/point/mstoreSsoView";

        // 1. 모바일 구분
        String isMobile = "N";
        if ("Y".equals(NmcpServiceUtils.isMobile())) isMobile = "Y";
        model.addAttribute("isMobile", isMobile);

        // 2. mstore 이용 가능 여부 체크: 이용가능[0000], 이외의 값은 이용 불가
        Map<String, String> limitChkMap= mstoreService.chkMstoreUseLimit();
        String errorCause= limitChkMap.get("errorCause");
        String resultCd= limitChkMap.get("resultCd");

        // 3. 본인인증 세션 확인
        if("PARAM".equals(errorCause) && "0003".equals(resultCd)){
            NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
            if(sessNiceRes != null
               && !StringUtils.isEmpty(sessNiceRes.getConnInfo())
               && !StringUtils.isEmpty(sessNiceRes.getName())
               && !StringUtils.isEmpty(sessNiceRes.getBirthDate())
            ){

                // ============ STEP START ============
                // 1. 최소 스텝 수 확인
                if(certService.getStepCnt() < 3 ){
                    model.addAttribute("resultCd", resultCd);  // 0003
                    model.addAttribute("errorCause", errorCause); // PARAM
                    return jspPageName;
                }

                // 2. 스텝종료여부, 이름, 생년월일, ci
                String[] certKey= {"urlType", "stepEndYn", "name", "birthDate", "connInfo"};
                String[] certValue= {"mstoreSsoAuth", "Y", sessNiceRes.getName(), sessNiceRes.getBirthDate(), sessNiceRes.getConnInfo()};

                Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
                if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                    model.addAttribute("resultCd", resultCd);  // 0003
                    model.addAttribute("errorCause", errorCause); // PARAM
                    return jspPageName;
                }
                // ============ STEP END ============

                resultCd= "0000"; // 일치 시 성공 처리
                limitChkMap.put("memId", sessNiceRes.getConnInfo());
            }
        }

        // 4. mstore 이용약관 동의 여부 확인 (resultCd가 0000: 이용 가능인 경우만)
        if("0000".equals(resultCd)){
            boolean agreeFlag= mstoreService.getMstoreTermsAgreeYn(limitChkMap.get("userId"));
            if(!agreeFlag){
                resultCd= "0001";
                errorCause= "TERMS";
            }
        }

        model.addAttribute("resultCd", resultCd);
        model.addAttribute("errorCause", errorCause);
        if(!"0000".equals(resultCd)) return jspPageName;

        // 4. SSO 연동 이력 MERGE (2023.12.05 신규 생성 테이블: NMCP_MSTORE_SSO_INFO)
        //    SSO 최초 연동 당시와 재진입 시 회원등급이 달라진 경우, 변경된 값으로 SSO 연동 진행
        mstoreService.mergeMstoreSSOInfo(limitChkMap);

        // 5. SSO 연동값 세팅
        model.addAttribute("mbrGrdCd", limitChkMap.get("mbrGrdCd"));
        model.addAttribute("cmpyNo", Constants.MSTORE_CMPYNO_CODE);
        model.addAttribute("lstComActvDate",limitChkMap.get("lstComActvDate")); // 개통일자
        model.addAttribute("mstoreCertUrl", mstoreCertUrl);
        model.addAttribute("landingUrl", landingUrl);

        String encMemId = MstoreEncryption.encode(limitChkMap.get("memId")); // CI값 암호화

        if("".equals(StringUtil.NVL(encMemId, ""))){ // 암복호화 오류
            model.addAttribute("resultCd", "0001");
            model.addAttribute("errorCause", "PARAM");
        }else{
            model.addAttribute("memId", encMemId);
        }

        return jspPageName;
    }


    /**
     * Mstore 쇼핑몰 이동 (SSO 연동) - 에러페이지
     * @param: resultCd
     * @param: errorCause
     * @return String
     */
    @RequestMapping(value = { "/point/mstoreErrorView.do", "/m/point/mstoreErrorView.do" })
    public String mstoreErrorView(HttpServletRequest request, ModelMap model
                                 ,@RequestParam(required = false, defaultValue = "") String resultCd
                                 ,@RequestParam(required = false, defaultValue = "") String errorCause) {

        String returnUrl = "/portal/point/mstoreErrorView";

        // 1. 모바일 여부 확인
        String isMobile = "N";
        if ("Y".equals(NmcpServiceUtils.isMobile())) isMobile = "Y";
        model.addAttribute("isMobile",isMobile);

        // 2. 에러 메세지 세팅
        model.addAttribute("errorMsg", getMstoreErrorMsg(errorCause,resultCd));
        return returnUrl;
    }

    @RequestMapping(value = "/point/checkMstoreUseAjax.do")
    @ResponseBody
    public Map<String, String> checkMstoreUse() {
        Map<String, String> limitChkMap= mstoreService.chkMstoreUseLimit();
        String errorCause= limitChkMap.get("errorCause");
        String resultCd= limitChkMap.get("resultCd");

        if("0000".equals(resultCd)){
            boolean agreeFlag= mstoreService.getMstoreTermsAgreeYn(limitChkMap.get("userId"));
            if(!agreeFlag){
                resultCd= "0001";
                errorCause= "TERMS";
            }
        }

        boolean isRedirect = false;
        List<NmcpCdDtlDto> legacyUrlRedirectList = NmcpServiceUtils.getCodeList("LegacyUrlRedirectList");
        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            isRedirect = legacyUrlRedirectList.stream().anyMatch(item -> "MARKET02".equals(item.getDtlCd()));
        } else {
            isRedirect = legacyUrlRedirectList.stream().anyMatch(item -> "MARKET01".equals(item.getDtlCd()));
        }

        Map<String, String> resultMap =  new HashMap<>();
        resultMap.put("resultCd", resultCd);
        resultMap.put("errorCause", errorCause);
        resultMap.put("resultMessage", getMstoreErrorMsg(errorCause, resultCd));
        resultMap.put("isRedirect", isRedirect ? "Y" : "N");
        return resultMap;
    }

    @RequestMapping(value = "/point/getMstoreContentsAjax.do")
    @ResponseBody
    public List<MstoreContentItemDto> getMstoreContents() {
        return mstoreService.getMstoreContents();
    }

    @RequestMapping(value = {"/point/mstoreAuthPop.do", "/m/point/mstoreAuthPop.do"})
    public String mstoreAuthPop() {
        String returnUrl = "/portal/point/mstoreAuthPop";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "/mobile/point/mstoreAuthPop";
        }

        return returnUrl;
    }

    /**
     * MSTORE 이용불가 코드 별 메세지
     * @param errorCause
     * @param errorCd
     * @return String
     */
    private String getMstoreErrorMsg(String errorCause, String errorCd){

         /*
            ======== errorCuase: LOGIN ========
            1) errorCd[0001] : 비로그인

            ======== errorCuase: GRADE ========
            1) errorCd[0001] : 이용불가 회원등급

            ======== errorCuase: TERMS ========
            1) errorCd[0001] : 약관 미동의

            ======== errorCuase: PARAM ========
            1) errorCd[0001] : 정회원 정보 보정 필요 / 암호화 오류
            2) errorCd[0002] : 고객유형 미충족
            3) errorCd[0003] : 본인인증 필요
            4) errorCd[0004] : 나이 조건 미충족
            5) errorCd[0000] : 이용가능
        */

        String rtnStr= INVALID_REFERER_EXCEPTION; // 잘못된 접근입니다.

        switch (errorCd){
            case "0000":
                rtnStr= "이용가능";
                break;
            case "0001":
                if("LOGIN".equals(errorCause)) rtnStr= "로그인 후 이용 바랍니다.";
                else if("GRADE".equals(errorCause)) rtnStr= "정회원만 이용 가능한 서비스입니다.<br/>회선을 사용중이실 경우, 마이페이지에서 인증 후 재 진행 바랍니다.";
                else if("TERMS".equals(errorCause)) rtnStr= "M마켓 이용 약관 동의 후 이용 바랍니다.";
                else rtnStr= "서비스 처리 중 오류가 발생하였습니다.<br/>고객센터(114/무료)로 문의부탁드립니다.";
                break;
            case "0002":
                rtnStr= "M마켓 이용은 개인 고객만 가능합니다.";
                break;
            case "0003":
                rtnStr= "본인인증 정보가 존재하지 않습니다.<br/>본인인증 후 재 진행 바랍니다.";
                break;
            case "0004":
                // 나이제한 코드 가져오기
                NmcpCdDtlDto resCodeVo= NmcpServiceUtils.getCodeNmDto("MSTOREAGE", "AGE");
                String limitAge= (resCodeVo == null) ? "14" : resCodeVo.getExpnsnStrVal1();
                rtnStr= limitAge+"세 이상 고객만 이용이 가능한 서비스입니다.";
                break;
            default :
                break; //pmd 처리
        }

        return rtnStr;
    }

    // M스토어 개발 서버 부재로 인한 local / dev / stg 진입 금지 > 공통코드로 처리
    private boolean serverChk (){

        // 공통코드로 서버 별 M스토어 이용가능여부 조회
        NmcpCdDtlDto resCodeVo = NmcpServiceUtils.getCodeNmDto("MSTORESERVER", serverName);
        if(resCodeVo == null || !"Y".equals(resCodeVo.getExpnsnStrVal1())) return false;

        return true;
    }

}
