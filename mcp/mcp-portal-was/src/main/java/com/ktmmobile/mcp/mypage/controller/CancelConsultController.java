package com.ktmmobile.mcp.mypage.controller;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.STEP_CNT_EXCEPTION;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.McpIpStatisticDto;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.EncryptUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.mypage.dto.CancelConsultDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.CancelConsultSvc;
import com.ktmmobile.mcp.mypage.service.CustRequestScanService;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;

@Controller
public class CancelConsultController {
    private static Logger logger = LoggerFactory.getLogger(CancelConsultController.class);

    @Autowired
    private MypageService mypageService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private CancelConsultSvc cancelConsultSvc;

    @Autowired
    private CustRequestScanService custRequestScanService;

    @Autowired
    private CertService certService;

    @Autowired
    private IpStatisticService ipstatisticService;

    /*
     * 해지 상담 신청 페이지
     */
    @RequestMapping(value = {"/mypage/cancelConsult.do", "/m/mypage/cancelConsult.do"})
    public String cancelConsult(Model model, HttpServletRequest request, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {

        String jspPageName = "/portal/mypage/cancelConsult";
        String thisPageName ="/mypage/cancelConsult.do";

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/mypage/cancelConsult";
            thisPageName ="/m/mypage/cancelConsult.do";
        }

        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();  //중복요청 체크
        checkOverlapDto.setRedirectUrl(thisPageName);

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            return "redirect:/loginForm.do";
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        CancelConsultDto cancelConsultDto = new CancelConsultDto();
        cancelConsultDto.setRegstId(userSession.getUserId());
        List<CancelConsultDto> cancelConsultList = cancelConsultSvc.selectCancelConsultList(cancelConsultDto);

        //주민번호 앞자리
        String birthday = userSession.getBirthday().substring(2,8);

        //주민번호 뒷자리 첫 번째 숫자
        String gender = cntrList.get(0).getUnUserSSn().substring(6,7);

        //현재 날짜
        String today = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());

        //만 나이 계산
        int age = NmcpServiceUtils.getBirthDateToAmericanAge(userSession.getBirthday().substring(0, 8), today);

        //미성년자 여부
        boolean underAge = age < 19;

        //마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");

            //마스킹 해제되어야 하는 데이터 세팅
            searchVO.setUserName(userSession.getName());

            //이력 insert
            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("이름,휴대폰번호");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }else{
            searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));

            if (cancelConsultList != null && !cancelConsultList.isEmpty()) {
                for (CancelConsultDto item : cancelConsultList) {
                    item.setCstmrName(StringMakerUtil.getName(item.getCstmrName()));
                    item.setCancelMobileNo(StringMakerUtil.getPhoneNum(item.getCancelMobileNo()));
                }
            }
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("birthday", birthday);
        model.addAttribute("gender", gender);
        model.addAttribute("underAge", underAge);
        model.addAttribute("cancelConsultList", cancelConsultList);

        return jspPageName;
    }

    /*
     * 해지 상담 신청
     */
    @RequestMapping(value = "/mypage/cancelConsultAjax.do")
    @ResponseBody
    public Map<String,Object> cancelConsultAjax(@ModelAttribute CancelConsultDto cancelConsultDto , HttpSession session)  {

        Map<String,Object> rtnMap = new HashMap<String,Object>();

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            rtnMap.put("RESULT_CODE", "00001");
            rtnMap.put("RESULT_MSG", "로그인 후 다시 시도 부탁드립니다.");
            return rtnMap;
        }

        try {
            //미성년자 확인
            String today = new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date());
            int age = NmcpServiceUtils.getBirthDateToAmericanAge(userSession.getBirthday().substring(0, 8), today);
            boolean underAge = age < 19;
            if(underAge) {
                rtnMap.put("RESULT_CODE", "00002");
                rtnMap.put("RESULT_MSG", "미성년자는 신청할 수 없습니다.");
                return rtnMap;
            }

            //계약번호 검즘
            List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
            boolean isCheck = false;

            if(cntrList.size() <= 0){
                rtnMap.put("RESULT_CODE","00003");
                rtnMap.put("RESULT_MSG","정회원 정보가 없습니다.");
                return rtnMap;
            }

            String ncn = StringUtil.NVL(cancelConsultDto.getContractNum(),"");
            String cancelMobileNo = StringUtil.NVL(cancelConsultDto.getCancelMobileNo(), "");
            //직접입력
            boolean isManualInput = "manual".equals(ncn);
            if(!isManualInput && "".equals(ncn) && "".equals(cancelMobileNo)){
                rtnMap.put("RESULT_CODE","00004");
                rtnMap.put("RESULT_MSG","해지 신청 번호가 없습니다.");
                return rtnMap;
            }

            String ctn = "";
            String cstmrNativeRrn ="";
            for(McpUserCntrMngDto mcpUserCntrMngDto : cntrList){
                //해지 신청 번호 select 박스 선택 시
                if(ncn.equals(mcpUserCntrMngDto.getSvcCntrNo())){
                    ctn = mcpUserCntrMngDto.getCntrMobileNo();
                    cstmrNativeRrn = mcpUserCntrMngDto.getUnUserSSn();
                    isCheck = true;
                    break;
                }

                //해지 신청 번호 직접 입력 시
                if(cancelMobileNo.equals(mcpUserCntrMngDto.getCntrMobileNo())){
                    ctn = cancelMobileNo;
                    ncn = mcpUserCntrMngDto.getSvcCntrNo();
                    cstmrNativeRrn = mcpUserCntrMngDto.getUnUserSSn();
                    isCheck = true;
                    break;
                }
            }

            if (!isCheck) {
                rtnMap.put("RESULT_CODE","00005");
                rtnMap.put("RESULT_MSG","해지 신청 번호 불일치");
                return rtnMap;
            }

            //동일 접수 여부 확인
            int isDuplicate  = cancelConsultSvc.countCancelConsult(cancelConsultDto);
            if(isDuplicate > 0) {
                rtnMap.put("RESULT_CODE", "00006");
                rtnMap.put("RESULT_MSG", "이미 동일한 해지 신청이 접수 중입니다.");
                return rtnMap;
            }

            //STEP 시작
            NiceResDto niceResDto = (NiceResDto) session.getAttribute(SessionUtils.NICE_AUT_COOKIE);

            //비정상 접근 확인
            if(niceResDto == null) {
                rtnMap.put("RESULT_CODE","00007");
                rtnMap.put("RESULT_MSG","본인인증 정보가 없습니다.");
                return rtnMap;
            }

            int certStep= 3;

            String[] certKey= new String[]{"urlType", "stepEndYn", "ncType", "authType"
                              , "reqSeq", "resSeq","dupInfo"};
            String[] certValue= new String[]{"saveCancelConsultForm", "Y", "0", cancelConsultDto.getOnlineAuthType()
                              , cancelConsultDto.getReqSeq(), cancelConsultDto.getResSeq(), niceResDto.getDupInfo()};

            if(certService.getStepCnt() < certStep){
                throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
            }

            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);

            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
            }

            cancelConsultDto.setOnlineAuthInfo("ReqNo:" + cancelConsultDto.getReqSeq() + ", ResNo:" + cancelConsultDto.getResSeq());
            cancelConsultDto.setCancelMobileNo(ctn);
            cancelConsultDto.setContractNum(ncn);
            cancelConsultDto.setCstmrName(userSession.getName());
            cancelConsultDto.setCstmrNativeRrn(EncryptUtil.ace256Enc(cstmrNativeRrn));
            cancelConsultDto.setRegstId(userSession.getUserId());
            cancelConsultDto.setRip(ipstatisticService.getClientIp());
            String result = cancelConsultSvc.cancelConsultRequest(cancelConsultDto);

            if("SUCCESS".equals(result)) {
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            }else {
                rtnMap.put("RESULT_CODE","00008");
                rtnMap.put("RESULT_MSG","시스템에 문제가 발생하였습니다. 다시 진행 부탁드립니다.");
                return rtnMap;
            }

            try {
                //SCAN 서버에 서식지 데이터 생성 및 전송
                custRequestScanService.prodSendScan(Long.parseLong(cancelConsultDto.getCustReqSeq()), userSession.getUserId(), "CC");

                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CANCEL_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + cancelConsultDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(cancelConsultDto.getCustReqSeq().toString());
                mcpIpStatisticDto.setTrtmRsltSmst("SUCCESS");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            } catch(McpCommonJsonException e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CANCEL_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + cancelConsultDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(cancelConsultDto.getCustReqSeq().toString());
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            }catch (Exception e) {
                rtnMap.put("RESULT_CODE", "FAIL");
                //로그 저장 처리
                McpIpStatisticDto mcpIpStatisticDto =  new McpIpStatisticDto();
                mcpIpStatisticDto.setPrcsMdlInd("CANCEL_REQUEST_SCAN");
                mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + cancelConsultDto.getCustReqSeq() +"]");
                mcpIpStatisticDto.setParameter(cancelConsultDto.getCustReqSeq().toString());
                mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
            }

        } catch(Exception e) {
            rtnMap.put("RESULT_CODE", "99999");
            rtnMap.put("RESULT_MSG", "해지 신청 처리 중 오류가 발생했습니다.");
        }

        return rtnMap ;
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
