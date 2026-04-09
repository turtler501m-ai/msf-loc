package com.ktmmobile.msf.form.servicechange.controller;


import static com.ktmmobile.msf.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.COMMON_EXCEPTION;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.DB_EXCEPTION;
import static com.ktmmobile.msf.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.ktmmobile.msf.form.ownerchange.dto.MyNameChgReqDto;
import com.ktmmobile.msf.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.form.servicechange.dto.MspJuoAddInfoDto;
import com.ktmmobile.msf.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.form.servicechange.service.MsfCustRequestScanService;
import com.ktmmobile.msf.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.common.dto.McpIpStatisticDto;
import com.ktmmobile.msf.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.common.dto.UserSessionDto;
import com.ktmmobile.msf.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.common.service.IpStatisticService;
import com.ktmmobile.msf.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.common.util.SessionUtils;

@Controller
public class MsfMypageController {

    private static final Logger logger = LoggerFactory.getLogger(MsfMypageController.class);


    @Autowired
    private MsfMypageSvc msfMypageSvc;

    @Autowired
    private MsfMaskingSvc maskingSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

    @Autowired
    private MsfCustRequestScanService custRequestScanService;

    /**
     * MCP 휴대폰 회선관리 리스트를 가지고 온다.
     * @param userId
     * @return List<McpUserCntrMngDto>
     */
    @RequestMapping(value = "/mypage/cntrList", method = {RequestMethod.POST,RequestMethod.GET})
    public List<McpUserCntrMngDto> cntrList(@RequestBody String userId) {

        List<McpUserCntrMngDto> cntrList = null;
        try {
            // Database 에서 조회함.
            cntrList = msfMypageSvc.selectCntrList(userId);
        } catch(DataAccessException e){
            throw new McpCommonJsonException(DB_EXCEPTION);
        }  catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
        return cntrList;
    }
//
//	/**
//	 * MCP 휴대폰 회선관리 리스트를 가지고 온다. ajax
//	 * @param userId
//	 * @return List<McpUserCntrMngDto>
//	 */
//
//	@RequestMapping(value = { "/mypage/cntrListAjax.do", "/m/mypage/cntrListAjax.do" })
//	@ResponseBody
//	public HashMap<String, Object> doCntrListAjax(HttpServletRequest request, Model model) {
//
//		UserSessionDto userSession = SessionUtils.getUserCookieBean();
//		HashMap<String, Object> rtnMap = new HashMap<String, Object>();
//
//		List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
//
//		rtnMap.put("cntrList", cntrList);
//		rtnMap.put("RESULT_CODE", "S");
//		return rtnMap;
//
//	}


    /**
     * 휴대폰 회선에 따른 요금제 정보를 가지고 온다.
     * @param svcCntrNo
     * @return McpUserCntrMngDto
     */
    @RequestMapping(value = "/mypage/socDesc", method = {RequestMethod.POST,RequestMethod.GET})
    public McpUserCntrMngDto socDesc(@RequestBody String svcCntrNo) {

        McpUserCntrMngDto socDesc = null;

        try {
            // Database 에서 조회함.
            socDesc = msfMypageSvc.selectSocDesc(svcCntrNo);
        } catch(DataAccessException e){
            throw new McpCommonJsonException(DB_EXCEPTION);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }

        return socDesc;
    }

    /**
     * 휴대폰 회선에 따른 요금제 정보를 가지고 온다.
     *
     * @param request
     * @param model
     * @param contractNum
     * @return
     */

    @RequestMapping(value = { "/mypage/socDescAjax.do", "/m/mypage/socDescAjax.do" })
    @ResponseBody
    public HashMap<String, Object> doSocDescAjax(HttpServletRequest request, Model model,
            @RequestParam(value = "contractNum", required = true) String contractNum) {

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        // 본인 서비스 계약번호 체크
        boolean checkFlag = true;
        List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        if (cntrList != null && cntrList.size() > 0) {
            for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                if (contractNum.equals(mcpUserCntrMngDto.getContractNum())) {
                    checkFlag = false;
                    break;
                }
            }
        }

        if (checkFlag) {
            rtnMap.put("RESULT_CODE", "E");
            rtnMap.put("message", "비정상적인 접근입니다.");
            return rtnMap;
        }

        // 서비스번호 요금제조회
        McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(contractNum);

        rtnMap.put("mcpUserCntrMngDto", mcpUserCntrMngDto);
        rtnMap.put("RESULT_CODE", "S");
        return rtnMap;

    }


    /**
     * 휴대폰 회선에 따른 단말기할인 정보를 가지고 온다.
     * @param svcCntrNo
     * @return MspJuoAddInfoDto
     */
    @RequestMapping(value = "/mypage/mspAddInfo", method = {RequestMethod.POST,RequestMethod.GET})
    public MspJuoAddInfoDto mspAddInfo(@RequestBody String svcCntrNo) {

        MspJuoAddInfoDto mspAddInfo = null;

        try {
            // Database 에서 조회함.
            mspAddInfo = msfMypageSvc.selectMspAddInfo(svcCntrNo);
            return mspAddInfo;
        } catch(DataAccessException e){
            throw new McpCommonJsonException(DB_EXCEPTION);
        } catch(Exception e) {
            throw new McpCommonJsonException(COMMON_EXCEPTION);
        }
    }

    /**
     * 결합 내역 조회
     * 아무나 결합과 아무나 가족결합+가 운영 중단 됨에따라 기존 가입자가 결합내역을 조회 할 수 있는곳이 없어 졌음
     * 마이페이지 내에 ‘결합 내역 조회’ 항목을 만들어 결합 조회가 가능하도록 메뉴 생성
     * 아무나 결합과 더불어 함께쓰기 결합내역도 조회가 가능하도록 개발
     * @author papier
     * @Date : 2024.04.02
     * @param model
     * @param searchVO
     * @return
     */
    @RequestMapping(value = "/mypage/myCombineList.do")
    public String myCombineList(Model model,@ModelAttribute("searchVO") MyPageSearchDto searchVO, HttpServletRequest request) {
        //중복요청 체크
        ResponseSuccessDto checkOverlapDto = new ResponseSuccessDto();
        checkOverlapDto.setRedirectUrl("/mypage/myCombineList.do");

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if(userSession==null || StringUtils.isEmpty(userSession.getUserId())) {
            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                return "redirect:/m/loginForm.do";
            } else {
                return "redirect:/loginForm.do";
            }
        }

        if (SessionUtils.overlapRequestCheck(checkOverlapDto)) {
            model.addAttribute("responseSuccessDto", checkOverlapDto);
            model.addAttribute("MyPageSearchDto", searchVO);
            return "/common/successRedirect";
        }

        List<McpUserCntrMngDto> cntrList = null;
        if (userSession != null) { // 취약성 289
            cntrList = msfMypageSvc.selectCntrList(userSession.getUserId());
        }

        boolean chk = msfMypageSvc.checkUserType(searchVO, cntrList, userSession);
        if(!chk){
            ResponseSuccessDto responseSuccessDto = new ResponseSuccessDto();
            String url = "/mypage/updateForm.do";
            if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
                url = "/m/mypage/updateForm.do";
            }
            responseSuccessDto.setRedirectUrl(url);
            responseSuccessDto.setSuccessMsg(NOT_FULL_MEMBER_EXCEPTION);

            model.addAttribute("responseSuccessDto", responseSuccessDto);
            return "/common/successRedirect";
        }

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            model.addAttribute("maskingSession", "Y");

            MaskingDto maskingDto = new MaskingDto();

            long maskingRelSeq = SessionUtils.getMaskingSession();
            maskingDto.setMaskingReleaseSeq(maskingRelSeq);
            maskingDto.setUnmaskingInfo("휴대폰번호,결합회선");
            maskingDto.setAccessIp(ipstatisticService.getClientIp());
            maskingDto.setAccessUrl(request.getRequestURI());
            maskingDto.setUserId(userSession.getUserId());
            maskingDto.setCretId(userSession.getUserId());
            maskingDto.setAmdId(userSession.getUserId());
            maskingSvc.insertMaskingReleaseHist(maskingDto);

        }

        //법인 회선 여부_아무나 SOLO 여부
        List<NmcpCdDtlDto> materCombineLineList = NmcpServiceUtils.getCodeList("MasterCombineLineInfo");
        if (null != materCombineLineList && materCombineLineList.size() <1) {
            model.addAttribute("isMaster", false);
        } else {
            model.addAttribute("isMaster", true);
        }

        model.addAttribute("searchVO", searchVO);
        model.addAttribute("cntrList", cntrList);

        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            return "/mobile/mypage/myCombineList";
        } else {
            return "/portal/mypage/myCombineList";
        }

    }

    /**
     * 인가된 사용자 체크
     * @author
     * @Date : 2024.12.17
     * @param name, paramR1, paramR2
     * @return MspJuoAddInfoDto
     */
    @RequestMapping(value = "/mypage/checkAuthUser.do")
    @ResponseBody
    public Map<String,String> checkAuthUser(@RequestParam(value= "name") String name,
                                            @RequestParam(value= "paramR1") String paramR1,
                                            @RequestParam(value= "paramR2") String paramR2) {
        return msfMypageSvc.checkAuthUser(name, paramR1 + paramR2);
    }

    /**
     * 설명 : SCAN 합본 수동 처리
     * @param myNameChgReqDto
     * /mypage/sendSyntheticScanAjax.do?custReqSeq=시퀀스&userid=유저아이디&reqType=요청타입(CL:통화내역열람 , NC:명의변경, IS:안심보험신청)
     * @return
     */
    @RequestMapping(value = "/mypage/sendSyntheticScanAjax.do")
    @ResponseBody
    public Map<String, Object> sendSyntheticScan(MyNameChgReqDto myNameChgReqDto) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

         //1. 서식지 여부 확인
        String  mcpRequest = msfMypageSvc.getChangeOfNameData(Long.parseLong(myNameChgReqDto.getCustReqSeq()),myNameChgReqDto.getReqType());

        if("FAIL".equals(mcpRequest)) {
            rtnMap.put("RESULT_MSG", "신청서 정보가 없습니다. ");
            rtnMap.put("RESULT_CODE", "FAIL");
            return rtnMap;
        }else {
                try {
                    //2. 서식지 재전송(CL:통화내역열람 , NC:명의변경, IS:안심보험신청)
                    custRequestScanService.prodSendScan(Long.parseLong(myNameChgReqDto.getCustReqSeq()), myNameChgReqDto.getUserid(),myNameChgReqDto.getReqType());

                    //로그 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("SEND_SYNTHETIC_SCAN");
                    mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + myNameChgReqDto.getCustReqSeq() + "]");
                    mcpIpStatisticDto.setParameter(myNameChgReqDto.getCustReqSeq() + "");
                    mcpIpStatisticDto.setTrtmRsltSmst("SUCCESS");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                    rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
                } catch (Exception e) {        //예외 전환 처리
                    rtnMap.put("RESULT_CODE", "FAIL");
                    //로그 저장 처리
                    McpIpStatisticDto mcpIpStatisticDto = new McpIpStatisticDto();
                    mcpIpStatisticDto.setPrcsMdlInd("SEND_SYNTHETIC_SCAN");
                    mcpIpStatisticDto.setPrcsSbst("REQUEST_KET[" + myNameChgReqDto.getCustReqSeq() + "]");
                    mcpIpStatisticDto.setParameter(myNameChgReqDto.getCustReqSeq() + "");
                    mcpIpStatisticDto.setTrtmRsltSmst("FAIL");
                    ipstatisticService.insertAdminAccessTrace(mcpIpStatisticDto);
                }
        }
        return rtnMap;
    }


}
