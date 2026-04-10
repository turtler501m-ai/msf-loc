package com.ktmmobile.mcp.mypage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ktmmobile.mcp.common.constants.Constants;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.mplatform.dto.MoscWireUseTimeInfoRes;
import com.ktmmobile.mcp.common.service.FCommonSvc;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.common.util.StringMakerUtil;
import com.ktmmobile.mcp.mypage.dto.MPayViewDto;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.FarPricePlanService;
import com.ktmmobile.mcp.mypage.service.MPayViewService;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;

import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.NO_SESSION_EXCEPTION;

@Controller
public class MPayViewController {

    private static final Logger logger = LoggerFactory.getLogger(MPayViewController.class);


    @Autowired
    private MypageService mypageService;

    @Autowired
    private FCommonSvc fCommonSvc;

    @Autowired
    FarPricePlanService farPricePlanService;

    @Autowired
    private MPayViewService mPayViewService;

    @Autowired
    private MaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;
    /**
     * 설명 : 소액결제 조회 및 한도변경 페이지
     */
    @RequestMapping(value = {"/mypage/mPayView.do", "/m/mypage/mPayView.do"}  )
    public String reSpnsrPlcyDc(ModelMap model , HttpServletRequest request
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO){

        String pageUrl = "";
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            pageUrl = "/mobile/mypage/mPayView";
            checkOverlapDto.setRedirectUrl("/m/mypage/mPayView.do");
        } else {
            pageUrl = "/portal/mypage/mPayView";
            checkOverlapDto.setRedirectUrl("/mypage/mPayView.do");
        }

        // 중복요청 체크
        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        // 로그인 여부 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) return "redirect:/loginForm.do";

        // 정회원 여부 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        searchVO.setUserName(StringMakerUtil.getName(userSession.getName()));
        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");
            searchVO.setUserName(userSession.getName());
            searchVO.setCtn(searchVO.getCtn());

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


        //개통일로부터 날짜 조회
        String openingDate= null;
        if(cntrList!=null) {
             openingDate= cntrList.get(0).getLstComActvDate();
             openingDate= openingDate.substring(0,6);
        }

        List<Map<String,String>> dateList= mPayViewService.getDateListFromOpening(openingDate);

        // 소액결제 상태 공통코드 조회
        NmcpCdDtlDto nmcpMainCdDtlDto = new NmcpCdDtlDto();
        nmcpMainCdDtlDto.setCdGroupId(Constants.MPAY_STATUS_CD);
        List<NmcpCdDtlDto> mPayStatusCd = fCommonSvc.getCodeList(nmcpMainCdDtlDto);

        model.addAttribute("dateList", dateList);
        model.addAttribute("mPayStatusCd", mPayStatusCd);
        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);

        return pageUrl;

    }

    /**
     * 설명 : 소액결제 내역 조회
     */
    @RequestMapping(value = {"/mypage/selectMPayListAjax.do"})
    @ResponseBody
    public Map<String, Object> selectMPayListAjax(@ModelAttribute("mPayViewDto") MPayViewDto mPayViewDto) {

        // 로그인 세션 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("9999", NO_SESSION_EXCEPTION);
        }

        // 회선리스트 조회
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());

        // 개통월일로 부터 날짜 조회
        String openingDate= null;
        for(int i=0;i<cntrList.size();i++) {
            if(cntrList.get(i).getSvcCntrNo().equals(mPayViewDto.getSvcCntrNo())) {
                openingDate= cntrList.get(i).getLstComActvDate();
                openingDate= openingDate.substring(0,6);
                break;
            }
        }

        List<Map<String,String>> dateList= mPayViewService.getDateListFromOpening(openingDate);


        String isEmpty= "N";
        Map<String, Object> rtnJsonMap = new HashMap<String, Object>();

        List<MPayViewDto> mPayList = mPayViewService.selectMPayList(mPayViewDto);

        if(mPayList == null || mPayList.size()==0) {
            isEmpty= "Y"; // 소액결제 내역이 존재하지 않으면 Y , 존재하면 N
        }

        // 이번달 소액결제 한도 조회
        String tmonLmtAmt= "0";
        tmonLmtAmt= mPayViewService.getTmonLmtAmt(mPayViewDto);

        rtnJsonMap.put("isEmpty", isEmpty);
        rtnJsonMap.put("mPayList", mPayList);
        rtnJsonMap.put("tmonLmtAmt", tmonLmtAmt);
        rtnJsonMap.put("dateList", dateList);
        return rtnJsonMap;
    }

    /**
     * 설명 : 소액결제 한도변경 가능여부 조회
    */
    @RequestMapping(value = {"/mypage/possibleChangeMpayLimitAjax.do"})
    @ResponseBody
    public Map<String, Object> possibleChangeMpayLimitAjax(@ModelAttribute MyPageSearchDto searchVO){

        // 로그인 세션 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            logger.error("=== possibleChangeMpayLimitAjax 로그인 세션이 없음");
            throw new McpCommonJsonException("9999", NO_SESSION_EXCEPTION);
        }

        // 정회원 체크
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            logger.error("=== possibleChangeMpayLimitAjax 정회원이 아님");
            throw new McpCommonJsonException("9998", NOT_FULL_MEMBER_EXCEPTION);
        }

        // 61일 이상 고객만 가능
        /*
        * x83.회선 사용기간 조회 realUseDayNum 실사용기간
        */

       Map<String, Object> rtnMap = new HashMap<String, Object>();
       MoscWireUseTimeInfoRes moscWireUseTimeInfoRes = farPricePlanService.moscWireUseTimeInfo(searchVO.getNcn(),
                                                           searchVO.getCtn(), searchVO.getCustId());

       if (moscWireUseTimeInfoRes.getRealUseDayNum() != null) {
           String realUseDay = moscWireUseTimeInfoRes.getRealUseDayNum();
           int realUseDayInt = Integer.parseInt(realUseDay, 10);

           if (realUseDayInt < 61) {
               rtnMap.put("resultCd", "F");
               rtnMap.put("resultMsg", "개통일로부터 실사용일 60일 이하 고객님께서는 고객센터(114/무료)를 통해 한도 변경을 신청 바랍니다.");
           }else {
               rtnMap.put("resultCd", "S");
               rtnMap.put("resultMsg", "한도 변경 가능합니다.");
           }
       }else {
           rtnMap.put("resultCd", "F");
           rtnMap.put("resultMsg", "시스템 장애로 인하여 통신이 원활하지 않습니다.");
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
