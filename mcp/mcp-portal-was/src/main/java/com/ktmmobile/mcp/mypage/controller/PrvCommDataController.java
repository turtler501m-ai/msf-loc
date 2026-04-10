package com.ktmmobile.mcp.mypage.controller;

import com.ktmmobile.mcp.cert.service.CertService;
import com.ktmmobile.mcp.common.dto.NiceResDto;
import com.ktmmobile.mcp.common.dto.ResponseSuccessDto;
import com.ktmmobile.mcp.common.dto.UserSessionDto;
import com.ktmmobile.mcp.common.dto.db.MspCommDatPrvTxnDto;
import com.ktmmobile.mcp.common.exception.McpCommonJsonException;
import com.ktmmobile.mcp.common.service.IpStatisticService;
import com.ktmmobile.mcp.common.util.DateTimeUtil;
import com.ktmmobile.mcp.common.util.NmcpServiceUtils;
import com.ktmmobile.mcp.common.util.SessionUtils;
import com.ktmmobile.mcp.mypage.dto.MaskingDto;
import com.ktmmobile.mcp.mypage.dto.McpUserCntrMngDto;
import com.ktmmobile.mcp.mypage.dto.MyPageSearchDto;
import com.ktmmobile.mcp.mypage.service.MaskingSvc;
import com.ktmmobile.mcp.mypage.service.MypageService;
import com.ktmmobile.mcp.mypage.service.PrvCommDataService;
import com.ktmmobile.mcp.usim.service.UsimService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ktmmobile.mcp.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.mcp.common.exception.msg.ExceptionMsgConstant.*;

@Controller
public class PrvCommDataController {

    private static Logger logger = LoggerFactory.getLogger(PrvCommDataController.class);

    @Value("${SERVER_NAME}")
    private String serverName;

    @Value("${api.interface.server}")
    private String apiInterfaceServer;

    /**조직 코드  */
    @Value("${sale.orgnId}")
    private String orgnId;

    @Autowired
    private PrvCommDataService prvCommDataService;

    @Autowired
    private MypageService mypageService;

    @Autowired
    UsimService usimService ;

    @Autowired
    IpStatisticService ipstatisticService;

    @Autowired
    CertService certService;

    @Autowired
    private MaskingSvc maskingSvc;

    /**
     * 통신자료 제공내역 조회요청
     */
    @RequestMapping(value = {"/mypage/prvCommData.do", "/m/mypage/prvCommData.do"}  )
    public String prvCommData(ModelMap model , HttpServletRequest request
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO){

        String jspPageName = "/portal/mypage/prvCommData";
        String thisPageName ="/mypage/prvCommData.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            jspPageName = "/mobile/mypage/prvCommData";
            thisPageName ="/m/mypage/prvCommData.do";
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

        String ncn = searchVO.getNcn();
        String custId = searchVO.getCustId();
        String ctn = searchVO.getCtn();

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

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);
        return jspPageName;
    }

    /**
     * 통신자료 제공내역 가져오기
     */
    @RequestMapping(value = {"/mypage/pvcCommDataListAjax.do"})
    @ResponseBody
    public Map<String, Object> pvcCommDataListAjax(@ModelAttribute("mspCommDatPrvTxnDto") MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 로그인 세션 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("9999", NO_SESSION_EXCEPTION);
        }

        // ============ STEP START ============
        // 계약번호
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"getPrvCommData", mspCommDatPrvTxnDto.getContractNum()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP01", vldReslt.get("RESULT_DESC"));
        }
        // ============ STEP END ============

        List<MspCommDatPrvTxnDto> dataList = prvCommDataService.pvcCommDataList(mspCommDatPrvTxnDto);

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        rtnMap.put("dataList", dataList);
        return rtnMap;
    }

    /**
     * 통신자료 제공내역 신청 insert
     */
    @RequestMapping(value = {"/mypage/insertPrvCommDataAjax.do"})
    @ResponseBody
    public Map<String, Object> insertPrvCommDataAjax(@ModelAttribute("mspCommDatPrvTxnDto") MspCommDatPrvTxnDto mspCommDatPrvTxnDto) {
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 로그인 세션 체크
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if (userSession == null || StringUtils.isEmpty(userSession.getUserId())) {
            throw new McpCommonJsonException("9999", NO_SESSION_EXCEPTION);
        }
        mspCommDatPrvTxnDto.setApyId(userSession.getUserId());

        // ============ STEP START ============
        // 1. 본인인증 세션 확인
        NiceResDto sessNiceRes = SessionUtils.getNiceResCookieBean();
        if (sessNiceRes == null) {
            throw new McpCommonJsonException("AUTH01", NICE_CERT_EXCEPTION_INSR);
        }

        // 2. 최소 스텝개수 확인
        if(certService.getStepCnt() < 5 ){
            throw new McpCommonJsonException("STEP01", STEP_CNT_EXCEPTION);
        }

        // 3. 최종 데이터 검증: step종료 여부, 계약번호, CI
        String[] certKey= {"urlType", "stepEndYn", "contractNum", "connInfo"};
        String[] certValue= {"saveReqPrvCommData", "Y", mspCommDatPrvTxnDto.getContractNum(), sessNiceRes.getConnInfo()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            throw new McpCommonJsonException("STEP02", vldReslt.get("RESULT_DESC"));
        }
        // ============ STEP END ============

        int result = prvCommDataService.insertPrvCommData(mspCommDatPrvTxnDto);

        if (result > 0) {
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        } else {
            rtnMap.put("RESULT_CODE", "0001");
            rtnMap.put("ERROR_NE_MSG","시스템에 문제가 발생하였습니다. 다음에 다시 진행 부탁드립니다.");
        }
        return rtnMap;
    }


    /**
     * 출력버튼
     */
    @RequestMapping("/mypage/prvCommDataPrintPop.do")
    public String prvCommDataPrintPop(HttpServletRequest request, Model model, @ModelAttribute("searchVO") MyPageSearchDto searchVO)  {

        UserSessionDto userSession = (UserSessionDto)request.getSession().getAttribute(SessionUtils.USER_SESSION);
        List<McpUserCntrMngDto> cntrList = mypageService.selectCntrList(userSession.getUserId());
        boolean chk = mypageService.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = getMessageBox();
            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        // ============ STEP START ============
        // 계약번호
        String[] certKey= {"urlType", "contractNum"};
        String[] certValue= {"reqPrvCommDataPrint", searchVO.getContractNum()};

        Map<String,String> vldReslt= certService.vdlCertInfo("D", certKey, certValue);
        if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
            model.addAttribute("ErrorTitle", "통신자료 제공내역 결과 통지서");
            model.addAttribute("ErrorMsg", vldReslt.get("RESULT_DESC"));
            return "/portal/errmsg/errorPop";
        }
        // ============ STEP END ============

        MspCommDatPrvTxnDto mspCommDatPrvTxnDto = new MspCommDatPrvTxnDto();
        mspCommDatPrvTxnDto.setContractNum(searchVO.getContractNum());
        mspCommDatPrvTxnDto.setApySeq(searchVO.getSeq());

        List<MspCommDatPrvTxnDto> dataList = prvCommDataService.pvcCommDataDtlList(mspCommDatPrvTxnDto);

        model.addAttribute("cstmrInfo", dataList.get(0));
        model.addAttribute("offerList", dataList);

        String today = DateTimeUtil.getFormatString("yyyy년 M월 d일");

        model.addAttribute("today", today);
        model.addAttribute("cntrList", cntrList);
        model.addAttribute("searchVO", searchVO);

        return "/portal/mypage/prvCommDataPrintPop";
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
