package com.ktmmobile.msf.form.servicechange.controller;

import static com.ktmmobile.msf.system.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.system.common.constants.Constants.EVENT_CODE_USIM_CHG_RST;
import static com.ktmmobile.msf.system.common.constants.Constants.EVENT_CODE_USIM_SELF_CHG;
import static com.ktmmobile.msf.system.common.exception.msg.ExceptionMsgConstant.*;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ktmmobile.msf.system.cert.service.CertService;
import com.ktmmobile.msf.system.common.dto.NiceResDto;
import com.ktmmobile.msf.system.common.exception.McpCommonJsonException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.msf.form.newchange.dto.OsstUc0ReqDto;
import com.ktmmobile.msf.form.newchange.service.AppformSvc;
import com.ktmmobile.msf.system.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.system.common.dto.UserSessionDto;
import com.ktmmobile.msf.system.common.exception.McpMplatFormException;
import com.ktmmobile.msf.system.common.exception.SelfServiceException;
import com.ktmmobile.msf.system.common.mplatform.MplatFormService;
import com.ktmmobile.msf.system.common.mplatform.vo.MSimpleOsstXmlUc0VO;
import com.ktmmobile.msf.system.common.service.IpStatisticService;
import com.ktmmobile.msf.system.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.system.common.util.SessionUtils;
import com.ktmmobile.msf.system.common.util.StringMakerUtil;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MypageService;
import org.springframework.web.client.RestClientException;

@Controller
public class UsimSelfChgController {

    private static final Logger logger = LoggerFactory.getLogger(UsimSelfChgController.class);


    @Autowired
    private MypageService mypageService;

    @Autowired
    private MplatFormService mPlatFormService;

    @Autowired
    private AppformSvc appformSvc;

    @Autowired
    private CertService certService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    /**
     * 설명 : 유심 셀프 변경 화면
     */
    @RequestMapping(value = {"/mypage/usimSelfChg.do", "/m/mypage/usimSelfChg.do"}  )
    public String reSpnsrPlcyDc(ModelMap model , HttpServletRequest request
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO){

        String jspPageName = "/portal/mypage/usimSelfChg";
        String thisPageName ="/mypage/usimSelfChg.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/mypage/usimSelfChg";
            thisPageName ="/m/mypage/usimSelfChg.do";
        }

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
        checkOverlapDto.setRedirectUrl(thisPageName);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
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

        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        model.addAttribute("phoneNum", searchVO.getCtn());
        model.addAttribute("custId", cntrList.get(0).getCustId());
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);
        return jspPageName;
    }


    /**
     * 설명     : 유심 변경
     */
    @RequestMapping(value = "/mypage/usimSelfChgAjax.do")
    @ResponseBody
    public Map<String, Object> usimSelfChgAjax(OsstUc0ReqDto osstUc0ReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // ============ STEP START ============
        // 1. 본인인증 세션 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            rtnMap.put("RESULT_CODE", "AUTH01");
            rtnMap.put("ERROR_NE_MSG", NICE_CERT_EXCEPTION_INSR);
            return rtnMap;
        }

        // 2. 최소 스텝 수 체크
        if(certService.getStepCnt() < 5){
            rtnMap.put("RESULT_CODE", "STEP01");
            rtnMap.put("ERROR_NE_MSG", STEP_CNT_EXCEPTION);
            return rtnMap;
        }

        McpUserCntrMngDto mcpUserCntrMngDto = new McpUserCntrMngDto();
        mcpUserCntrMngDto.setSvcCntrNo(osstUc0ReqDto.getSvcContId());
        mcpUserCntrMngDto = mypageService.selectCntrListNoLogin(mcpUserCntrMngDto);

        if(mcpUserCntrMngDto == null){
            rtnMap.put("RESULT_CODE", "AUTH02");
            rtnMap.put("ERROR_NE_MSG", F_BIND_EXCEPTION);
            return rtnMap;
        }

        osstUc0ReqDto.setCustNo(mcpUserCntrMngDto.getCustId());
        osstUc0ReqDto.setTlphNo(mcpUserCntrMngDto.getCntrMobileNo());

        // 3. 최종 데이터 체크: 스텝종료여부, 계약번호, 유심번호
        String[] certKey = {"urlType", "stepEndYn","contractNum", "reqUsimSn", "connInfo"};
        String[] certValue = {"chgUsimSelf", "Y", mcpUserCntrMngDto.getContractNum(), osstUc0ReqDto.getIccId(), sessNiceRes.getConnInfo()};
        Map<String, String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))){
            rtnMap.put("RESULT_CODE", "STEP02");
            rtnMap.put("ERROR_NE_MSG", vldReslt.get("RESULT_DESC"));
            return rtnMap;
        }
        // ============ STEP END ============

        MSimpleOsstXmlUc0VO  simpleOsstXmlVO = null;
        try {
            simpleOsstXmlVO = appformSvc.sendOsstUc0Service(osstUc0ReqDto, EVENT_CODE_USIM_SELF_CHG);
            if ("S".equals(simpleOsstXmlVO.getRsltCd())) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                rtnMap.put("MVNO_ORD_NO", simpleOsstXmlVO.getMvnoOrdNo());
            } else {
                rtnMap.put("RESULT_CODE", "0001");
                rtnMap.put("RESULT_XML", simpleOsstXmlVO.getResponseXml());
                rtnMap.put("ERROR_MSG", simpleOsstXmlVO.getResultCode());
                rtnMap.put("ERROR_NE_MSG","시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            }
        } catch (McpMplatFormException e) {
            rtnMap.put("RESULT_CODE", "9997");
            rtnMap.put("ERROR_MSG","response massage is null.");
            rtnMap.put("ERROR_NE_MSG","시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
        } catch (SocketTimeoutException e) {
            rtnMap.put("RESULT_CODE", "9999");
            rtnMap.put("ERROR_MSG", "SocketTimeout");
            rtnMap.put("ERROR_NE_MSG","시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
            return rtnMap;

        } catch (SelfServiceException e) {

            //메세지에 따른 resultCode 변경 처리
            String resultCode = e.getResultCode();
            String message = e.getMessageNe();

            if ("ITL_SST_E1014".equals(resultCode)) {
                //성공처리
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                return rtnMap;
            } else {
                rtnMap.put("RESULT_CODE", "9998");
                rtnMap.put("ERROR_MSG",e.getMessage());
            }

            //session에 저장한 서식지 정보 초기화
            SessionUtils.saveAppformDto(null);
            if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("가입제한자") > -1) {
                resultCode = "ITL_SST_E1018_01";
            } else if ("ITL_SST_E1018".equals(resultCode) && message.indexOf("미납고객") > -1) {
                resultCode = "ITL_SST_E1018_02";
            } else if("MCG_BIZ_E0422".equals(resultCode)){
                // 일별 호출 제한
                message = "현재 요청이 너무 많아 유심 셀프변경 신청이 제한되었습니다. 익일 다시 이용해주세요.";
            }else if("MCG_BIZ_E0421".equals(resultCode)) {
                // 분당 호출 제한
                message = "현재 요청이 너무 많아 잠시 제한되었습니다. 잠시 후 다시 시도해주세요.";
            }

            rtnMap.put("OSST_RESULT_CODE", resultCode);
            rtnMap.put("ERROR_NE_MSG",message);
            return rtnMap;
        }
        return rtnMap;
    }

    /**
     * 설명     : 유심변경 결과 확인
     */
    @RequestMapping(value = "/mypage/usimChgChkAjax.do")
    @ResponseBody
    public Map<String, Object> usimChgChkAjax(OsstUc0ReqDto osstUc0ReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        String result = appformSvc.usimChgChk(osstUc0ReqDto);
        rtnMap.put("RESULT_CODE", result);

        return rtnMap;
    }


    private ResponseSuccessDto getMessageBox(){
        ResponseSuccessDto mbox = new ResponseSuccessDto();
        mbox.setRedirectUrl("/mypage/updateForm.do");
        if("Y".equals(NmcpServiceUtils.isMobile())){
            mbox.setRedirectUrl("/m/mypage/updateForm.do");
        }
        mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
        return mbox;
    }


}
