package com.ktmmobile.mcp.content.controller;

import java.net.SocketTimeoutException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.dto.MoscCombReqDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.exception.SelfServiceException;
import com.ktmmobile.mcp.common.mplatform.dto.MoscCombInfoResDTO;
import com.ktmmobile.mcp.common.mplatform.dto.MoscMvnoComInfo;
import com.ktmmobile.mcp.common.mplatform.dto.RegSvcChgRes;
import com.ktmmobile.mcp.common.mplatform.vo.MpRegSvcChgVO;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.common.util.StringUtil;
import com.ktmmobile.mcp.content.dto.KtDcDto;
import com.ktmmobile.mcp.content.dto.MyCombinationResDto;
import com.ktmmobile.mcp.content.service.KtDcSvc;
import com.ktmmobile.mcp.content.service.MyCombinationSvc;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpFarPriceDto;
import com.ktmmobile.mcp.mypage.dto.McpServiceAlterTraceDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.RegSvcService;

@Controller
public class KtDcController {

    private static final Logger logger = LoggerFactory.getLogger(KtDcController.class);

    @Autowired
    MypageService mypageService;

    @Autowired
    MyCombinationSvc myCombinationSvc;

    @Autowired
    KtDcSvc ktDcSvc;

    @Autowired
    RegSvcService regSvcService;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    FarPricePlanService farPricePlanService;

    @Autowired
    private MaskingSvc maskingSvc;


    /**
     * 트리플할인 첫화면
     */
    @RequestMapping(value = {"/content/ktDcView.do","/m/content/ktDcView.do"})
    public String ktDcView(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO) {


        String returnUrl = "portal/content/ktDcView";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            returnUrl = "mobile/content/ktDcView";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            return "redirect:/loginForm.do";
        }

        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);

        //정회원만 신청가능
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        String contractNum = searchVO.getContractNum();

        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("contractNum", contractNum);

        // 2.사용중인 요금제
        McpFarPriceDto mcpFarPriceDto = null;
        String prvRateGrpNm = ""; // 요금제명
        String prvRateCode = ""; //요금제 코드
        try {
            mcpFarPriceDto = mypageService.selectFarPricePlan(contractNum);
            if(mcpFarPriceDto !=null) {
                prvRateGrpNm = mcpFarPriceDto.getPrvRateGrpNm();
                prvRateCode = mcpFarPriceDto.getPrvRateCd();
            }

        } catch(SelfServiceException e) {
             logger.error("SelfServiceException e : {}", e.getMessage());
        } catch(Exception e) {
             logger.error("Exception e : {}", e.getMessage());
        }

        //트리플할인 신청 완료 후 처음으로 버튼 클릭
        String doneReturn = request.getParameter("doneReturn");
        if(StringUtils.isNotBlank(doneReturn)) {
            model.addAttribute("doneReturn", "Y");
       }else {
           model.addAttribute("doneReturn", "N");
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


        model.addAttribute("prvRateGrpNm", prvRateGrpNm);
        model.addAttribute("prvRateCode", prvRateCode);

        return returnUrl;
    }


    @RequestMapping(value = {"/content/ktDcViewAjax.do" , "/m/content/ktDcViewAjax.do"})
    @ResponseBody
    public Map<String, Object> ktDcViewAjax(HttpServletRequest request, @RequestParam(value = "ncn", required = true) String ncn) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String custId = "";
        String ctn = "";

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList !=null && !cntrList.isEmpty()) {
            for(McpUserCntrMngDto dto : cntrList) {
                String svcCntrNo = StringUtil.NVL(dto.getSvcCntrNo(),"");
                if(svcCntrNo.equals(ncn)) {
                    custId = dto.getCustId();
                    ctn = dto.getCntrMobileNo();
                    break;
                }
            }
        }
        if("".equals(custId) || "".equals(ctn)) {
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        return rtnMap;
    }


    @RequestMapping(value = {"/content/discountSearchAjax.do" , "/m/content/discountSearchAjax.do"})
    @ResponseBody
    public Map<String, Object> discountSearchAjax(HttpServletRequest request, @RequestParam(value = "ncn", required = true) String ncn,
            @ModelAttribute KtDcDto ktDcDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        String custId = "";
        String ctn = "";
        String contractNum = "";
        String prcsMdlInd = DateTimeUtil.getFormatString("yyyyMMddHHmmss");
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        if(cntrList !=null && !cntrList.isEmpty()) {
            for(McpUserCntrMngDto dto : cntrList) {
                String svcCntrNo = StringUtil.NVL(dto.getSvcCntrNo(),"");
                if(svcCntrNo.equals(ncn)) {
                    custId = dto.getCustId();
                    ctn = dto.getCntrMobileNo();
                    contractNum = dto.getContractNum();
                    break;
                }
            }
        }
        if("".equals(custId) || "".equals(ctn)) {
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("RESULT_MSG", "비정상적인 접근입니다.");
            return rtnMap;
        }

        // MP연동 X77 START
        // 각각 결합여부 확인
        MoscCombReqDto moscCombReqDto = new MoscCombReqDto();
        moscCombReqDto.setCustId(custId);
        moscCombReqDto.setNcn(ncn);
        moscCombReqDto.setCtn(ctn);
        moscCombReqDto.setCombSvcNoCd("M");  //결합 모회선 사업자 구분 코드  : MVNO회선, K:KT 회선
        moscCombReqDto.setSameCustKtRetvYn("Y"); // 동일명의 KT회선 조회여부 = 'Y'로 조회 시 미동의 회선의 정보 응답

        MoscCombInfoResDTO moscCombInfoResDTO = myCombinationSvc.selectCombiMgmtListLog(moscCombReqDto, prcsMdlInd, contractNum);
        MoscMvnoComInfo parentObj = null;

        if (moscCombInfoResDTO != null && moscCombInfoResDTO.isSuccess()) {

            String indvinfoAgree = moscCombInfoResDTO.getMoscMvnoComInfo().getIndvInfoAgreeMsgSbst();
            //MVNO 정보제공동의 체크
            if(!"".equals(indvinfoAgree)) {
                boolean containsInternet = indvinfoAgree.contains("인터넷");
                if(containsInternet) { //KT인터넷 미동의
                     rtnMap.put("RESULT_CODE", "1002");
                     rtnMap.put("RESULT_MSG", "KT고객센터로 전화하여<br>MVNO사업자에 정보 제공 동의 후<br>조회가 가능합니다.");
                     return rtnMap;
                }else { //KT인터넷 동의는 되어있으나 다른 항목 미동의
                    rtnMap.put("RESULT_CODE", "1003");
                    rtnMap.put("RESULT_MSG", "전월 내 엠모바일을 통해<br>인터넷을 가입한 이력이 존재하지 않습니다.");
                    return rtnMap;
                }
            }

            if (moscCombInfoResDTO.getMoscSrchCombInfoList() != null) {
                parentObj = moscCombInfoResDTO.getMoscMvnoComInfo();

                //인터넷 가입이력이 전월 또는 현재월에 있는지 체크
                int contOpnDtChk = 0;
                String corrNm = "";
                List<String> conrNmList = new ArrayList<String>();
                for (MoscMvnoComInfo item : moscCombInfoResDTO.getMoscSrchCombInfoList()) {

                    if("인터넷".equals(item.getSvcDivCd())) {
                        String contOpnDt = item.getSvcContOpnDt(); //가입일
                        String svcNo = item.getSvcNo(); //인터넷 등 유선 상품인 경우 인터넷ID
                        LocalDate today = LocalDate.now(); // 현재 날짜

                        // contOpnDt를 LocalDate 객체로 변환
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
                        LocalDate openDate = LocalDate.parse(contOpnDt, formatter);

                        // 오늘 날짜의 연도와 월
                        int currentYear = today.getYear();
                        int currentMonth = today.getMonthValue();

                        // 비교할 날짜의 연도와 월
                        int openYear = openDate.getYear();
                        int openMonth = openDate.getMonthValue();

                        // 전월 또는 이번달 인지 체크
                        boolean isCurrentOrPreviousMonth = (currentYear == openYear && currentMonth == openMonth) ||
                                                           (currentYear == openYear && currentMonth == openMonth + 1) ||
                                                           (currentYear == openYear + 1 && currentMonth == 1 && openMonth == 12);

                        if (isCurrentOrPreviousMonth) {
                            contOpnDtChk = contOpnDtChk += 1;
                            corrNm = item.getCorrNm();
                                if("MVNOKIS".equals(corrNm)) {
                                    conrNmList.add(StringMakerUtil.getUserId(svcNo) + "," + contOpnDt + "," + svcNo);
                                }
                         }

                    }

                }

                if(contOpnDtChk == 0) {
                     rtnMap.put("RESULT_CODE", "1003");
                     rtnMap.put("RESULT_MSG", "전월 내 엠모바일을 통해<br>인터넷을 가입한 이력이 존재하지 않습니다.");
                     return rtnMap;
                }


                List<KtDcDto> rateCode = ktDcSvc.selectRateNmList(ktDcDto);

                //고객 사용중 요금제가 트리플할인 정책 내 존재하지 않을 경우
                if(rateCode == null || rateCode.isEmpty()){
                    if("Y".equals(moscCombInfoResDTO.getMoscMvnoComInfo().getCombYn())){ //트리플할인 미대상 && 아무나가족결합 미대상
                        rtnMap.put("RESULT_CODE", "1006");
                        return rtnMap;
                    }else { //트리플할인 미대상 && 아무나가족결합 대상
                        rtnMap.put("RESULT_CODE", "1005");
                        return rtnMap;
                    }
                }else {
                    if("MVNOKIS".equals(corrNm)) {
                        rtnMap.put("resCode", rateCode);
                        rtnMap.put("conrNmList",conrNmList);
                        rtnMap.put("custId",custId);
                        rtnMap.put("ctn",ctn);
                        rtnMap.put("prcsMdlInd",prcsMdlInd);
                        rtnMap.put("combYn", moscCombInfoResDTO.getMoscMvnoComInfo().getCombYn());
                        rtnMap.put("RESULT_CODE", "1004");
                        return rtnMap;
                    }else {
                        rtnMap.put("RESULT_CODE", "1003");
                        rtnMap.put("RESULT_MSG", "전월 내 엠모바일을 통해<br>인터넷을 가입한 이력이 존재하지 않습니다.");
                        return rtnMap;
                    }
                }

            }else {
                rtnMap.put("RESULT_CODE", "1003");
                rtnMap.put("RESULT_MSG", "전월 내 엠모바일을 통해<br>인터넷을 가입한 이력이 존재하지 않습니다.");
                return rtnMap;
            }
        } else {
            rtnMap.put("RESULT_CODE", "1007");
            rtnMap.put("RESULT_MSG", "KT가입정보 조회에 실패하였습니다.<br>잠시 후 다시 시도 해 주세요.");
            return rtnMap;
        }
    }


    //KT 트리플할인 신청 접수 Ajax
    @RequestMapping(value = {"/content/insertKtDcAjax.do" , "/m/content/insertKtDcAjax.do"})
    @ResponseBody
    public Map<String, Object> insertKtDcAjax(HttpServletRequest request, @ModelAttribute KtDcDto ktDcDto )throws SocketTimeoutException {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();
        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        KtDcDto internetIdSucc = new KtDcDto();
        internetIdSucc.setKtInternetId(ktDcDto.getKtInternetId());

        //인터넷ID로 트리플할인 이력 존재 여부 체크
        KtDcDto selectKtDc = ktDcSvc.selectKtDc(internetIdSucc);

        if(selectKtDc != null) {
            rtnMap.put("RESULT_CODE", "00001");
            rtnMap.put("RESULT_MSG", "이미 신청이력이 존재합니다.<br>KT인터넷 트리플할인은 KT인터넷ID 당<br>1회만 수령 가능합니다.");
            return rtnMap;
        }else {

            KtDcDto contractNumSucc = new KtDcDto();
            contractNumSucc.setContractNum(ktDcDto.getContractNum());

            //인터넷ID는 다르지만 같은 계약번호로 신청한 이력 존재 여부 체크
            KtDcDto selectOverlapKtDc = ktDcSvc.selectKtDc(contractNumSucc);

            if(selectOverlapKtDc != null) {
                rtnMap.put("RESULT_CODE", "00002");
                rtnMap.put("RESULT_MSG", "해당 혜택은 중복으로 가입이 불가합니다");
                return rtnMap;
            }else {

                 //결과 로그 저장 처리
                McpServiceAlterTraceDto serviceAlterTraceSub = new McpServiceAlterTraceDto();
                serviceAlterTraceSub.setNcn(ktDcDto.getNcn());
                serviceAlterTraceSub.setContractNum(ktDcDto.getContractNum());
                serviceAlterTraceSub.setSubscriberNo(ktDcDto.getCtn());
                serviceAlterTraceSub.setPrcsMdlInd("MP"+ktDcDto.getPrcsMdlInd());
                serviceAlterTraceSub.setEventCode("X21");
                serviceAlterTraceSub.setTrtmRsltSmst("부가서비스신청");
                serviceAlterTraceSub.settSocCode(ktDcDto.getSoc());
                serviceAlterTraceSub.setaSocCode("");

                ktDcDto.setAdditionCd(ktDcDto.getAdditionCd());
                ktDcDto.setPrcsMdlInd("MP"+ktDcDto.getPrcsMdlInd());
                ktDcDto.setCretId(userSession.getUserId());
                ktDcDto.setAmdId(userSession.getUserId());
                ktDcDto.setRip(ipstatisticService.getClientIp());

                //X21 연동 부가서비스 가입
                RegSvcChgRes regSvcChgSelfVO = farPricePlanService.regSvcChgNe(ktDcDto.getNcn(),ktDcDto.getCtn(), ktDcDto.getCustId(),ktDcDto.getSoc(), "");

                String strParameter =  "["+ktDcDto.getRateNm()+"]["+ktDcDto.getBaseAmt()+"]";
                serviceAlterTraceSub.setParameter(strParameter);
                serviceAlterTraceSub.setGlobalNo(regSvcChgSelfVO.getGlobalNo());
                serviceAlterTraceSub.setRsltCd(regSvcChgSelfVO.getResultCode());
                serviceAlterTraceSub.setPrcsSbst(regSvcChgSelfVO.getSvcMsg());

                if (regSvcChgSelfVO.isSuccess()) {
                    if("Y".equals(ktDcDto.getCombYn())){ // Y인경우 추가 결합할인은 불가
                         rtnMap.put("RESULT_CODE", "0000");
                    }else {
                         rtnMap.put("RESULT_CODE", "00000");
                    }
                         serviceAlterTraceSub.setRsltCd("0000");
                         mypageService.insertServiceAlterTrace(serviceAlterTraceSub);
                         ktDcDto.setSuccYn("Y");
                         ktDcSvc.insertKtDc(ktDcDto);
                 }else {
                     rtnMap.put("RESULT_CODE", "30000");
                     serviceAlterTraceSub.setTrtmRsltSmst("부가서비스 신청 실패");
                     serviceAlterTraceSub.setPrcsSbst(regSvcChgSelfVO.getSvcMsg());
                     ktDcDto.setSuccYn("N");
                     ktDcDto.setReasonFail(regSvcChgSelfVO.getSvcMsg());
                     ktDcSvc.insertKtDc(ktDcDto);
                     mypageService.insertServiceAlterTrace(serviceAlterTraceSub);

                 }
            }
        }
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
