package com.ktmmobile.msf.domains.form.form.termination.controller;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfCustRequestScanService;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMyinfoService;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarRealtimePayInfoVO;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeReqDto;
import com.ktmmobile.msf.domains.form.form.termination.dto.CancelConsultDto.RemainChargeResVO;
import com.ktmmobile.msf.domains.form.form.termination.service.MsfCancelConsultSvc;
import org.springframework.web.bind.annotation.RequestBody;
import com.ktmmobile.msf.domains.form.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceResDto;
import com.ktmmobile.msf.domains.form.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@Controller
public class MsfCancelConsultController {
    private static Logger logger = LoggerFactory.getLogger(MsfCancelConsultController.class);

    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private MsfCancelConsultSvc msfCancelConsultSvc;

    @Autowired
    private MsfMyinfoService msfMyinfoService;

    @Autowired
    private MsfCustRequestScanService custRequestScanService;

//    @Autowired
//    private CertService certService;

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

        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());

        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        CancelConsultDto cancelConsultDto = new CancelConsultDto();
        cancelConsultDto.setRegstId(userSession.getUserId());
        List<CancelConsultDto> cancelConsultList = msfCancelConsultSvc.selectCancelConsultList(cancelConsultDto);

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
            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
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
            int isDuplicate  = msfCancelConsultSvc.countCancelConsult(cancelConsultDto);
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

//PNB_확인필요            
//            if(certService.getStepCnt() < certStep){
//                throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
//            }
//
//            Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);

//            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
//                throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
//            }

            cancelConsultDto.setOnlineAuthInfo("ReqNo:" + cancelConsultDto.getReqSeq() + ", ResNo:" + cancelConsultDto.getResSeq());
            cancelConsultDto.setCancelMobileNo(ctn);
            cancelConsultDto.setContractNum(ncn);
            cancelConsultDto.setCstmrName(userSession.getName());
            cancelConsultDto.setCstmrNativeRrn(EncryptUtil.ace256Enc(cstmrNativeRrn));
            cancelConsultDto.setRegstId(userSession.getUserId());
            cancelConsultDto.setRip(ipstatisticService.getClientIp());
            String result = msfCancelConsultSvc.cancelConsultRequest(cancelConsultDto);

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


    /**
     * X18 — 잔여요금·위약금 실시간 조회
     * POST /api/v1/cancel/remain-charge
     *
     * [TOBE] ncn만 프론트에서 수신, ctn·custId는 세션 계약 목록에서 조회 (getRealTimePriceAjax.do 방식 동일)
     */
    @RequestMapping(value = "/api/v1/cancel/remain-charge")
    @ResponseBody
    public RemainChargeResVO getRemainCharge(@RequestBody RemainChargeReqDto reqDto) {
        RemainChargeResVO errVO = new RemainChargeResVO();

        // 1. ncn 필수 검증
        if (StringUtils.isBlank(reqDto.getNcn())) {
            errVO.setSuccess(false);
            errVO.setMessage("계약번호(ncn)는 필수입니다.");
            return errVO;
        }

        // 2. 세션에서 userId 조회
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            errVO.setSuccess(false);
            errVO.setMessage("세션 정보가 없습니다.");
            return errVO;
        }

        // 3. 계약 목록에서 ncn에 해당하는 ctn·custId 조회 (getRealTimePriceAjax.do 방식)
        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        McpUserCntrMngDto cntrInfo = null;
        if (cntrList != null && !cntrList.isEmpty()) {
            cntrInfo = cntrList.stream()
                .filter(item -> reqDto.getNcn().equals(item.getSvcCntrNo()))
                .findFirst().orElse(null);
        }

        if (cntrInfo == null) {
            errVO.setSuccess(false);
            errVO.setMessage("계약 정보를 찾을 수 없습니다.");
            return errVO;
        }

        // 4. 세션에서 조회한 ctn·custId 세팅
        reqDto.setCtn(cntrInfo.getCntrMobileNo());
        reqDto.setCustId(cntrInfo.getCustId());

        logger.info("X18 잔여요금 조회 요청: ncn={}, ctn={}", reqDto.getNcn(), reqDto.getCtn());

        // 5. MsfMyinfoService.farRealtimePayInfo() 호출 (getRealTimePriceAjax.do 동일 경로)
        RemainChargeResVO resVO = new RemainChargeResVO();
        try {
            MpFarRealtimePayInfoVO mpVO = msfMyinfoService.farRealtimePayInfo(
                reqDto.getNcn(), reqDto.getCtn(), reqDto.getCustId());

            if (mpVO == null) {
                resVO.setSuccess(false);
                resVO.setMessage("X18 응답이 없습니다.");
                return resVO;
            }

            // MpFarRealtimePayInfoVO → RemainChargeResVO 매핑
            resVO.setSuccess(true);
            resVO.setSearchDay(mpVO.getSearchDay());
            resVO.setSearchTime(mpVO.getSearchTime());
            resVO.setSumAmt(mpVO.getSumAmt());

            if (mpVO.getList() != null) {
                java.util.List<RemainChargeResVO.FareItem> items = new java.util.ArrayList<>();
                for (MpFarRealtimePayInfoVO.RealFareVO src : mpVO.getList()) {
                    RemainChargeResVO.FareItem item = new RemainChargeResVO.FareItem();
                    item.setGubun(src.getGubun());
                    item.setPayment(src.getPayment());
                    items.add(item);
                }
                resVO.setItems(items);
            }

            logger.info("X18 잔여요금 조회 완료: sumAmt={}", resVO.getSumAmt());
        } catch (Exception e) {
            logger.error("X18 잔여요금 조회 오류: ncn={}, error={}", reqDto.getNcn(), e.getMessage(), e);
            resVO.setSuccess(false);
            resVO.setMessage("X18 조회 중 오류가 발생했습니다: " + e.getMessage());
        }
        return resVO;
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
