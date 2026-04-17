package com.ktmmobile.msf.domains.form.form.servicechange.controller;

import static com.ktmmobile.msf.domains.form.common.constants.Constants.AJAX_SUCCESS;
import static com.ktmmobile.msf.domains.form.common.constants.Constants.SHARINGRATE;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.F_BIND_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.NOT_FULL_MEMBER_EXCEPTION;
import static com.ktmmobile.msf.domains.form.common.exception.msg.ExceptionMsgConstant.TIME_OVERLAP_EXCEPTION;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
// [ASIS] import org.springframework.ui.Model — TOBE: @RestController에서 Model 미사용
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import com.ktmmobile.msf.domains.form.form.newchange.service.AppformSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MaskingDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.McpUserCntrMngDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyPageSearchDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyShareDataReqDto;
import com.ktmmobile.msf.domains.form.form.servicechange.dto.MyShareDataResDto;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMaskingSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMyShareDataSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMypageSvc;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfMyinfoService;
import com.ktmmobile.msf.domains.form.form.servicechange.service.MsfNicePinService;
import com.ktmmobile.msf.domains.form.system.cert.service.CertService;
import com.ktmmobile.msf.domains.form.common.constants.Constants;
import com.ktmmobile.msf.domains.form.common.dto.AuthSmsDto;
import com.ktmmobile.msf.domains.form.common.dto.JsonReturnDto;
import com.ktmmobile.msf.domains.form.common.dto.NiceLogDto;
import com.ktmmobile.msf.domains.form.common.dto.ResponseSuccessDto;
import com.ktmmobile.msf.domains.form.common.dto.UserSessionDto;
import com.ktmmobile.msf.domains.form.common.dto.db.NmcpCdDtlDto;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonException;
import com.ktmmobile.msf.domains.form.common.exception.McpCommonJsonException;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.MoscDataSharingResDto;
import com.ktmmobile.msf.domains.form.common.mplatform.dto.OutDataSharingDto;
import com.ktmmobile.msf.domains.form.common.mplatform.vo.MpFarChangewayInfoVO;
import com.ktmmobile.msf.domains.form.common.service.IpStatisticService;
import com.ktmmobile.msf.domains.form.common.util.EncryptUtil;
import com.ktmmobile.msf.domains.form.common.util.NmcpServiceUtils;
import com.ktmmobile.msf.domains.form.common.util.ObjectUtils;
import com.ktmmobile.msf.domains.form.common.util.SessionUtils;
import com.ktmmobile.msf.domains.form.common.util.StringMakerUtil;
import com.ktmmobile.msf.domains.form.common.util.StringUtil;

@RestController
public class MsfMyShareDataController {

    private static final Logger logger = LoggerFactory.getLogger(MsfMyShareDataController.class);

    @Autowired
    MsfMyinfoService myinfoService;

    @Autowired
    MsfMypageSvc msfMypageSvc;

    @Autowired
    MsfMyShareDataSvc myShareDataSvc;

    @Autowired
    CertService certService;

    @Autowired
    MsfNicePinService nicePinService;

    @Autowired
    private MsfMaskingSvc maskingSvc;

//    @Autowired
//    private AppformSvc appformSvc;

    @Autowired
    private IpStatisticService ipstatisticService;

//    @Autowired
//    private FathService fathService;

    /**
     * 쉐어링 (인증전)
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @return
     */

    @RequestMapping(value = { "/mySharingCntrInfo.do", "/m/mySharingCntrInfo.do" })
    public Map<String, Object> doMySharingCntrInfo(HttpServletRequest request) {
        HashMap<String, Object> rtnMap = new HashMap<>();

        // 데이터쉐어링 - 비로그인 세션 비워주기
        SessionUtils.saveNonmemberSharingInfo(null);

        String userRtnUrl = "/content/mySharingView.do";
        if("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            userRtnUrl = "/m/content/mySharingView.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();

        if(userSession != null) {
            rtnMap.put("redirectUrl", userRtnUrl);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * 쉐어링 (인증후)
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param searchVO
     * @param menuType
     * @param phoneNum
     * @param session
     * @return
     */

    @RequestMapping(value = { "/content/mySharingView.do", "/m/content/mySharingView.do" })
    public Map<String, Object> doMySharingView(HttpServletRequest request
            ,	@ModelAttribute("searchVO") MyPageSearchDto searchVO
            ,@RequestParam(value = "menuType", required = false) String menuType
            ,@RequestParam(value = "phoneNum", required = false) String phoneNum
            , HttpSession session) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // ================= STEP START =================
        // step 초기화 및 메뉴명 세팅
        SessionUtils.removeCertSession();
        SessionUtils.setPageSession("dataSharing");
        // ================= STEP END =================

        // [ASIS] returnUrl "/portal/content/mySharingView" — TOBE 미사용
        String rtnUrl = "/mySharingCntrInfo.do";
        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnUrl = "/m/mySharingCntrInfo.do";
        }

        // [ASIS] overlapRequestCheck successRedirect — TOBE 미사용

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  authSmsDto  == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        String userType = ""; //외국인여부
        String[] certKey= null;
        String[] certValue= null;

        //비회원로그인
        if(authSmsDto != null) {

            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            if(phoneNum != null) {
                userCntrMngDto.setCntrMobileNo(phoneNum);
            } else {
                userCntrMngDto.setSvcCntrNo(searchVO.getNcn());
            }

            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            if(cntrList.getUnUserSSn() != null && cntrList.getUnUserSSn().length() > 12) {
                userType = cntrList.getUnUserSSn().substring(6,7);
                if("5".equals(userType) || "6".equals(userType)) {
                    userType = "Y";
                    rtnMap.put("userType", userType);
                }

            }

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            searchVO.setUserDivisionYn("02");

            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
            rtnMap.put("searchVO", searchVO);
            session.setAttribute("userDivisionYn", "02");

            // ============ STEP START ============
            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"memberAuth", cntrList.getUserName(), EncryptUtil.ace256Enc(cntrList.getUnUserSSn()), cntrList.getContractNum()};
            // ============ STEP END ============

        } else {

            String userId = userSessionDto.getUserId();
            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();

            // 본인이 가지고 있는 회선정보
            cntrList = msfMypageSvc.selectCntrList(userId);

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }

            if("Y".equals(searchVO.getUserType())) {
                rtnMap.put("userType", searchVO.getUserType());
            }

            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
            rtnMap.put("cntrList", cntrList);

        }

        // 현재 요금제 조회
        // 서비스계약번호
        McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(searchVO.getContractNum());
        MoscDataSharingResDto moscDataSharingResDto  = new MoscDataSharingResDto();

        //고객구분여부
        String customerType = msfMypageSvc.selectCustomerType(searchVO.getCustId());

        if("G".equals(customerType) || "B".equals(customerType)) {
            customerType = "Y";
            rtnMap.put("customerType", customerType); // 현재
        }

        String resultCode = "";
        String message ="";
        String socChkYn = "";
        List<NmcpCdDtlDto> socCodeList = NmcpServiceUtils.getCodeList(SHARINGRATE);
        if(socCodeList != null && mcpUserCntrMngDto != null) {
            for (NmcpCdDtlDto socCode : socCodeList) {
                if(mcpUserCntrMngDto.getSoc().equals(socCode.getDtlCd())) {
                    socChkYn = "Y";
                    break;
                }
            }
        }

        //x71
        if(!"Y".equals(customerType) && !"Y".equals(userType)) {
            moscDataSharingResDto = myShareDataSvc.mosharingList(myShareDataReqDto);
            if(moscDataSharingResDto.isSuccess()) {
                resultCode = "00";
                message = "";
            }
        }

        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        String subStatusYn = "";

        //일시정지
        if("S".equals(searchVO.getSubStatus())) {
            subStatusYn = "Y";
        }

        //청구계약조회
        MpFarChangewayInfoVO changeInfo = myinfoService.farChangewayInfo(myShareDataReqDto.getNcn(), myShareDataReqDto.getCtn(), myShareDataReqDto.getCustId());

        String changeYn = "N";
        if(changeInfo != null) {
            if("지로".equals(changeInfo.getPayMethod())) {
                if(!"".equals(changeInfo.getBlAddr())) {
                    changeYn = "Y";
                }
            }
        }

        // ============ STEP START ============
        // 비회원 쉐어링 가능한 경우만 STEP 시작
        if(certKey != null){
            if(!"Y".equals(changeYn) && !"Y".equals(customerType) && "Y".equals(socChkYn) && !"Y".equals(userType)
                && (moscDataSharingResDto.getSharingList() == null || moscDataSharingResDto.getSharingList().size() == 0)){
                certService.vdlCertInfo("C", certKey, certValue);
            }
        }
        // ============ STEP END ============

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            rtnMap.put("maskingSession", "Y");

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


        rtnMap.put("changeYn", changeYn); // 청구계약
        rtnMap.put("resultCode", resultCode); //
        rtnMap.put("message", message); //
        rtnMap.put("mcpUserCntrMngDto", mcpUserCntrMngDto); //
        rtnMap.put("subStatusYn", subStatusYn); //
        rtnMap.put("moscDataSharingResDto", moscDataSharingResDto); //
        rtnMap.put("menuType", menuType); //
        rtnMap.put("socChkYn", socChkYn); // 쉐어링 불가 요금제


        //테스트를 위한
        //login 확인
        /*
        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        if ("LOCAL".equals(serverName) || "youngtomo".equals(userSession.getUserId()) ) {

            rtnMap.put("changeYn", "N"); // 청구계약
            rtnMap.put("resultCode", "N"); //
            rtnMap.put("message", "N"); //
            rtnMap.put("subStatusYn", "N"); //
            rtnMap.put("socChkYn", "Y"); // 쉐어링 불가 요금제
        } else {
            rtnMap.put("changeYn", changeYn); // 청구계약
            rtnMap.put("resultCode", resultCode); //
            rtnMap.put("message", message); //
            rtnMap.put("subStatusYn", subStatusYn); //
            rtnMap.put("socChkYn", socChkYn); // 쉐어링 불가 요금제
        }
        */
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }



    /**
     * 쉐어링 _신규(인증전)
     * @author papier
     * @Date : 2024.05.13
     * @return
     */

    @RequestMapping(value = { "/content/dataSharingStep1.do", "/m/content/dataSharingStep1.do" })
    public Map<String, Object> dataSharingStep1() {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // 데이터쉐어링 - 비로그인 세션 비워주기
        SessionUtils.saveNonmemberSharingInfo(null);

        // [ASIS] returnUrl "/portal/content/dataSharingStep1" — TOBE 미사용
        String userRtnUrl = "/content/dataSharingStep2.do";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            userRtnUrl = "/m/content/dataSharingStep2.do";
        }

        UserSessionDto userSession = SessionUtils.getUserCookieBean();
        SessionUtils.saveDateSharingSession(null);

        if(userSession != null) {
            rtnMap.put("redirectUrl", userRtnUrl);
        }

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * 쉐어링 (인증후)
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param searchVO
     * @param menuType
     * @param phoneNum
     * @param session
     * @return
     */
    @RequestMapping(value = { "/content/dataSharingStep2.do", "/m/content/dataSharingStep2.do" })
    public Map<String, Object> dataSharingStep2(HttpServletRequest request
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO
            , @RequestParam(value = "menuType", required = false) String menuType
            , @RequestParam(value = "phoneNum", required = false) String phoneNum
            , HttpSession session) {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // ================= STEP START =================
        // step 초기화 및 메뉴명 세팅
        SessionUtils.removeCertSession();
        SessionUtils.setPageSession("dataSharing");
        // ================= STEP END =================

        // [ASIS] returnUrl "/portal/content/dataSharingStep2" — TOBE 미사용
        String rtnUrl = "/content/dataSharingStep1.do";
        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnUrl = "/m/content/dataSharingStep1.do";
        }

        // [ASIS] overlapRequestCheck successRedirect — TOBE 미사용

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession(menuType);
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  authSmsDto  == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        String userType = ""; //외국인여부
        String cstmrType = "NA"; //고객구분
        String[] certKey= null;
        String[] certValue= null;

        //비회원로그인
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            if(phoneNum != null) {
                userCntrMngDto.setCntrMobileNo(phoneNum);
            } else {
                userCntrMngDto.setSvcCntrNo(searchVO.getNcn());
            }

            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);
            if(cntrList == null) {
                throw new McpCommonException("[001]"+F_BIND_EXCEPTION);
            }


            if(cntrList.getUnUserSSn() != null && cntrList.getUnUserSSn().length() > 12) {
                String customerSsn = cntrList.getUnUserSSn();
                int age = NmcpServiceUtils.getAge(customerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));


                /** 고객구분
                 *  NA :내국인
                 *  NM : 내국인(미성년자)
                 *  FN:외국인
                 *  JP:법인
                *  PP:개인사업자
                private String cstmrType ;
                 * */
                userType = cntrList.getUnUserSSn().substring(6,7);
                if("5".equals(userType) || "6".equals(userType) || "7".equals(userType) || "8".equals(userType)) {
                    userType = "Y";
                    cstmrType =  "FN";
                    rtnMap.put("userType", userType);
                } else if (19 > age ) {
                    cstmrType =  "NM";
                }
            } else {
                throw new McpCommonException("[002]"+F_BIND_EXCEPTION);
            }

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            searchVO.setUserDivisionYn("02");

            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
            rtnMap.put("searchVO", searchVO);
            session.setAttribute("userDivisionYn", "02");

            // ============ STEP START ============
            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"memberAuth", cntrList.getUserName(), EncryptUtil.ace256Enc(cntrList.getUnUserSSn()), cntrList.getContractNum()};
            // ============ STEP END ============

        } else {

            String userId = userSessionDto.getUserId();
            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();

            // 본인이 가지고 있는 회선정보
            cntrList = msfMypageSvc.selectCntrList(userId);

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }

            if("Y".equals(searchVO.getUserType())) {
                rtnMap.put("userType", searchVO.getUserType());
            }


            McpUserCntrMngDto thisCntrMng = null;
            if (StringUtil.isEmpty(searchVO.getNcn())) {
                thisCntrMng = cntrList.get(0);
            } else {
                thisCntrMng = cntrList.stream()
                        .filter(dto -> StringUtil.equals(searchVO.getNcn(), String.valueOf(dto.getSvcCntrNo())))
                        .findFirst()
                        .orElse(null);
            }

            if (thisCntrMng == null) {
                throw new McpCommonException("[003]"+F_BIND_EXCEPTION);
            }

            String customerSsn = thisCntrMng.getUnUserSSn();
            if(customerSsn != null && customerSsn.length() > 12) {
                int age = NmcpServiceUtils.getAge(customerSsn, new SimpleDateFormat("yyyyMMdd", Locale.KOREA).format(new Date()));


                /** 고객구분
                 *  NA :내국인
                 *  NM : 내국인(미성년자)
                 *  FN:외국인
                 *  JP:법인
                 *  PP:개인사업자
                 private String cstmrType ;
                 * */
                userType = customerSsn.substring(6,7);
                if("5".equals(userType) || "6".equals(userType) || "7".equals(userType) || "8".equals(userType)) {
                    userType = "Y";
                    cstmrType =  "FN";
                    rtnMap.put("userType", userType);
                } else if (19 > age ) {
                    cstmrType =  "NM";
                }
            } else {
                throw new McpCommonException("[004]"+F_BIND_EXCEPTION);
            }
            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
            rtnMap.put("cntrList", cntrList);
        }

        // 현재 요금제 조회
        // 서비스계약번호
        McpUserCntrMngDto mcpUserCntrMngDto = msfMypageSvc.selectSocDesc(searchVO.getContractNum());
        MoscDataSharingResDto moscDataSharingResDto  = new MoscDataSharingResDto();

        //고객구분여부
        String customerType = msfMypageSvc.selectCustomerType(searchVO.getCustId());

        if("G".equals(customerType) || "B".equals(customerType)) {
            customerType = "Y";
            rtnMap.put("customerType", customerType); // 현재
        }



        String resultCode = "";
        String message ="";
        String socChkYn = "";
        List<NmcpCdDtlDto> socCodeList = NmcpServiceUtils.getCodeList(SHARINGRATE);
        if(socCodeList != null && mcpUserCntrMngDto != null) {
            for (NmcpCdDtlDto socCode : socCodeList) {
                if(mcpUserCntrMngDto.getSoc().equals(socCode.getDtlCd())) {
                    socChkYn = "Y";
                    break;
                }
            }
        }

        //x71
        if(!"Y".equals(customerType) && !"Y".equals(userType)) {
            moscDataSharingResDto = myShareDataSvc.mosharingList(myShareDataReqDto);
            if(moscDataSharingResDto.isSuccess()) {
                resultCode = "00";
                message = "";
            }
        }

        searchVO.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn()));
        String subStatusYn = "";

        //일시정지
        if("S".equals(searchVO.getSubStatus())) {
            subStatusYn = "Y";
        }

        //청구계약조회
        MpFarChangewayInfoVO changeInfo = myinfoService.farChangewayInfo(myShareDataReqDto.getNcn(), myShareDataReqDto.getCtn(), myShareDataReqDto.getCustId());

        String changeYn = "N";
        if(changeInfo != null) {
            if("지로".equals(changeInfo.getPayMethod())) {
                if(!"".equals(changeInfo.getBlAddr())) {
                    changeYn = "Y";
                }
            }
        }

        //테스트 계정 지로 청구 상관없이...   통과
        if (userSessionDto != null) {
        	String isExceptionId = NmcpServiceUtils.getCodeNm(Constants.ID_GROUP_EXCEPTION , userSessionDto.getUserId());
            if (isExceptionId != null && "Y".equals(isExceptionId) ) {
                changeYn = "N";
            }
        }



// [ASIS] Map<String, Object> mapIsApply= appformSvc.isSimpleApplyObj();
// [ASIS] boolean isMacTime = (boolean) Optional.ofNullable(mapIsApply.get("IsMacTime")).orElse(true);
        boolean isMacTime = true; // [ASIS] stub — appformSvc.isSimpleApplyObj() 미구현

        // ============ STEP START ============
        // 비회원 쉐어링 가능한 경우만 STEP 시작
        if(certKey != null){
            if(!"Y".equals(changeYn) && !"Y".equals(customerType) && "Y".equals(socChkYn) && isMacTime
                    && (moscDataSharingResDto.getSharingList() == null || moscDataSharingResDto.getSharingList().size() == 0)){
                certService.vdlCertInfo("C", certKey, certValue);
            }
        }
        // ============ STEP END ============

        // 마스킹해제
        if(SessionUtils.getMaskingSession() > 0 ) {
            rtnMap.put("maskingSession", "Y");
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



        rtnMap.put("isMacTime", isMacTime); //신규 셀프개통 가능 여부
        rtnMap.put("cstmrType", cstmrType);
        rtnMap.put("changeYn", changeYn); // 청구계약
        rtnMap.put("resultCode", resultCode); //
        rtnMap.put("message", message); //
        rtnMap.put("mcpUserCntrMngDto", mcpUserCntrMngDto); //
        rtnMap.put("subStatusYn", subStatusYn); //
        rtnMap.put("moscDataSharingResDto", moscDataSharingResDto); //
        rtnMap.put("menuType", menuType); //
        rtnMap.put("socChkYn", socChkYn); // 쉐어링 불가 요금제

        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }



    /**
     * 쉐어링 신청 VIEW
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param contractNum
     * @param searchVO
     * @param session
     * @return
     */
    @RequestMapping(value = { "/content/dataSharingStep3.do", "/m/content/dataSharingStep3.do" })
    public Map<String, Object> dataSharingStep3(HttpServletRequest request
            , @RequestParam(value = "contractNum", required = false) String contractNum
            , @RequestParam(value = "onOffType", required = false) String onOffType
            , @RequestParam(value = "cstmrType", required = true) String cstmrType
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO
            ,HttpSession session)  {

        HashMap<String, Object> rtnMap = new HashMap<>();

        String rtnUrl = "/content/dataSharingStep2.do";
        // [ASIS] returnUrl "/portal/content/dataSharingStep3" — TOBE 미사용
        String stepErrReturnUrl = "/main.do";
        String userDivisionYn = (String) session.getAttribute("userDivisionYn");

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnUrl = "/m/content/dataSharingStep2.do";
            stepErrReturnUrl = "/m/main.do";
        }

        if(contractNum == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();

        NiceLogDto niceLogDto= new NiceLogDto();

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("sharing");
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        String[] certKey= null;
        String[] certValue= null;
        Map<String,String> vldReslt= null;

        //비회원
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(contractNum);
            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList  == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            myShareDataReqDto.setBirthday(cntrList.getDobyyyymmdd());
            myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo())); // 전화번호
            myShareDataReqDto.setNcn(cntrList.getSvcCntrNo());
            myShareDataReqDto.setOpmdSvcNo(cntrList.getUnSvcNo());
            myShareDataReqDto.setName(cntrList.getUserName());
            myShareDataReqDto.setContractNum(cntrList.getContractNum());

            // ============ STEP START ============
            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"chkMemberAuth", cntrList.getUserName(), EncryptUtil.ace256Enc(cntrList.getUnUserSSn()), cntrList.getContractNum()};

            vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }
            // ============ STEP END ============

            niceLogDto.setName(cntrList.getUserName());
            niceLogDto.setBirthDate(cntrList.getUnUserSSn());

            // 고객인증 sms인증 세션은 데이터 쉐어링 개통요청 시점에 null > 비로그인 데이터쉐어링 고객 인증정보 별도 보관
            McpUserCntrMngDto mcpUserCntrMngDto= new McpUserCntrMngDto();
            mcpUserCntrMngDto.setSubLinkName(authSmsDto.getAuthNum());   // 이름
            mcpUserCntrMngDto.setCntrMobileNo(authSmsDto.getPhoneNum()); // 전화번호
            SessionUtils.saveNonmemberSharingInfo(mcpUserCntrMngDto);

        } else {

            searchVO.setNcn(contractNum);

            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSessionDto.getUserId());

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }

            String userSsn= null;
            for(McpUserCntrMngDto dto : cntrList) {
                if(contractNum.equals(dto.getSvcCntrNo())) {
                    myShareDataReqDto.setBirthday(userSessionDto.getBirthday());
                    myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn())); // 전화번호
                    myShareDataReqDto.setNcn(searchVO.getNcn());
                    myShareDataReqDto.setOpmdSvcNo(dto.getUnSvcNo());
                    myShareDataReqDto.setName(userSessionDto.getName());
                    myShareDataReqDto.setContractNum(dto.getContractNum());
                    userSsn= dto.getUnUserSSn();
                }
            }

            // ============ STEP START ============
            // 계약번호
            certKey= new String[]{"urlType", "contractNum"};
            certValue= new String[]{"chkMemberAuth", myShareDataReqDto.getContractNum()};

            vldReslt= certService.vdlCertInfo("F", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }

            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"memberAuth", myShareDataReqDto.getName(), EncryptUtil.ace256Enc(userSsn), myShareDataReqDto.getContractNum()};
            vldReslt= certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============

            niceLogDto.setName(myShareDataReqDto.getName());
            niceLogDto.setBirthDate(userSsn);
        }

        //session 초기화
        SessionUtils.saveAppformDto(null);
        SessionUtils.saveNiceRes(null);
        //안면인증 세션 초기화
//PNB_확인        SessionUtils.initializeFathSession();

        // ============ STEP START ============
        // nicePin연동
        Map<String, String> nicePinRtn= nicePinService.getNicePinCi(niceLogDto);
        if(!"0000".equals(nicePinRtn.get("returnCode"))) throw new McpCommonException(nicePinRtn.get("returnMsg"), stepErrReturnUrl);
        // ============ STEP END ============

        logger.info("[WOO][WOO][WOO]MyShareDataReqDto==>" + ObjectUtils.convertObjectToString(myShareDataReqDto) );
        SessionUtils.saveDateSharingSession(myShareDataReqDto);

        rtnMap.put("cstmrType", cstmrType);
        rtnMap.put("contractNum", contractNum);
        rtnMap.put("onOffType", onOffType);
        rtnMap.put("myShareDataReqDto", myShareDataReqDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }



    /**
     * 데이터 쉐어링 완료 view
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param myShareDataReqDto
     * @param session
     * @return
     */
    @RequestMapping(value = { "/content/dataSharingStep4.do", "/m/content/dataSharingStep4.do" })
    public Map<String, Object> dataSharingStep4(HttpServletRequest request
            ,@ModelAttribute("myShareDataReqDto") MyShareDataReqDto myShareDataReqDto
            ,HttpSession session)  {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // [ASIS] returnUrl "/portal/content/dataSharingStep4" — TOBE 미사용
        String rtnUrl = "/content/dataSharingStep2.do";

        if ("A".equals(NmcpServiceUtils.getPlatFormCd()) || "M".equals(NmcpServiceUtils.getPlatFormCd())) {
            rtnUrl = "/m/content/dataSharingStep2.do";
        }

        String rtnOpmdSvcNo = (String) session.getAttribute("opmdSvcNo");

        if(rtnOpmdSvcNo == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataResDto myShareDataResDto = new MyShareDataResDto();
        String svcNo = "";  //본인핸드폰번호
        String socNm = "";  //본인요금제
        String opmdSvcNoContractNum = ""; //개통이 완료된 서비스계약번호
        String opmdSvcSocNm = ""; //가입계약 요금제

        //AuthSmsDto authSmsDto = SessionUtils.getSmsSession("sharing");
        McpUserCntrMngDto nonMemberSess = SessionUtils.getNonmemberSharingInfo();
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  nonMemberSess  == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        if(nonMemberSess != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(myShareDataReqDto.getNcn());
            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            svcNo = StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo());
            myShareDataReqDto.setCustId(cntrList.getCustId());
            myShareDataReqDto.setNcn(cntrList.getContractNum());
            myShareDataReqDto.setCtn(cntrList.getCntrMobileNo());
            myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
        } else {
            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSessionDto.getUserId());
            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                    if (myShareDataReqDto.getNcn().equals(mcpUserCntrMngDto.getContractNum())) {
                        svcNo = StringMakerUtil.getPhoneNum(mcpUserCntrMngDto.getUnSvcNo());
                        myShareDataReqDto.setCustId(mcpUserCntrMngDto.getCustId());
                        myShareDataReqDto.setNcn(mcpUserCntrMngDto.getContractNum());
                        myShareDataReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
                        myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
                        break;
                    }
                }
            }
        }

        McpUserCntrMngDto result = msfMypageSvc.selectSocDesc(myShareDataReqDto.getNcn());

        if(result !=null) {
            socNm = result.getRateNm();
        }

        if(!StringUtil.isBlank(opmdSvcNoContractNum)) {
            McpUserCntrMngDto opmdSvcNoDto = msfMypageSvc.selectSocDesc(opmdSvcNoContractNum); //가입하는 데이터쉐어링 요금제
            if(opmdSvcNoDto !=null) {
                opmdSvcSocNm =opmdSvcNoDto.getRateNm();
            }
        }

        myShareDataResDto.setSvcNo(svcNo);
        myShareDataResDto.setSocNm(socNm);
        myShareDataResDto.setOpmdSvcSocNm(opmdSvcSocNm);
        myShareDataResDto.setOpmdSvcNo(StringMakerUtil.getPhoneNum(myShareDataReqDto.getOpmdSvcNo()));
        SessionUtils.saveNiceRes(null);
        session.removeAttribute("opmdSvcNo"); //저장완료후 삭제
        rtnMap.put("myShareDataResDto", myShareDataResDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * 데이터 쉐어링 사전체크 ajax
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param searchVO
     * @param contractNum
     * @param session
     * @return
     */
    // TOBESKIP: 사전 체크 Ajax는 사용하지 않아 URL 매핑만 막고 원본 로직은 보존한다.
    // @RequestMapping(value = { "/content/preOpenCheckAjax.do", "/m/content/preOpenCheckAjax.do" })
    @Deprecated
    public JsonReturnDto domyCntrListAjax(HttpServletRequest request
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO
            ,@RequestParam(value = "contractNum", required = false) String contractNum, HttpSession session) {

        String userDivisionYn = (String) session.getAttribute("userDivisionYn");

        JsonReturnDto jsonReturnDto = new JsonReturnDto();
        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();
        Map<String, Object> map = new HashMap<String, Object>();

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("sharing");
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        //비회원
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(contractNum);

            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);
            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            searchVO.setCustId(cntrList.getCustId());
            searchVO.setContractNum(cntrList.getContractNum());
            searchVO.setNcn(cntrList.getSvcCntrNo());
            searchVO.setCtn(cntrList.getCntrMobileNo());
            searchVO.setSubStatus(cntrList.getSubStatus());
            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());
            session.setAttribute("userDivisionYn", searchVO.getUserDivisionYn());

        } else {

            String userId = userSessionDto.getUserId();
            List<McpUserCntrMngDto> cntrList = new ArrayList<McpUserCntrMngDto>();

            // 본인이 가지고 있는 회선정보
            cntrList = msfMypageSvc.selectCntrList(userId);

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonJsonException("0098" ,NOT_FULL_MEMBER_EXCEPTION);
            }

            myShareDataReqDto.setCustId(searchVO.getCustId());
            myShareDataReqDto.setNcn(searchVO.getNcn());
            myShareDataReqDto.setCtn(searchVO.getCtn());
            myShareDataReqDto.setCrprCtn(searchVO.getCtn());

        }


        Object result = null;
        String resultCode = "";
        String message ="";
        MoscDataSharingResDto  moscDataSharingResDto = new MoscDataSharingResDto();
        //x71
        moscDataSharingResDto = myShareDataSvc.mosharingList(myShareDataReqDto);

        if(moscDataSharingResDto != null) {
            result = moscDataSharingResDto.getSharingList();
        }
        if(moscDataSharingResDto.isSuccess()) {
            resultCode = "00";
            message = "";
        }

        String subStatusYn = "";
        //일시정지
        if("S".equals(searchVO.getSubStatus())) {
            subStatusYn = "Y";
        }
        map.put("subStatusYn", subStatusYn);
        jsonReturnDto.setResultMap(map);
        jsonReturnDto.setMessage(message);
        jsonReturnDto.setReturnCode(resultCode);
        jsonReturnDto.setResult(result);
        return jsonReturnDto;

    }

    /**
     * 쉐어링 신청 VIEW
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param contractNum
     * @param searchVO
     * @param session
     * @return
     */
    @RequestMapping(value = { "/content/reqSharingView.do", "/m/content/reqSharingView.do" })
    public Map<String, Object> dorReqSharingView(HttpServletRequest request
            ,@RequestParam(value = "contractNum", required = false) String contractNum
            , @ModelAttribute("searchVO") MyPageSearchDto searchVO
            ,HttpSession session)  {

        HashMap<String, Object> rtnMap = new HashMap<>();

        String rtnUrl = "/content/mySharingView.do";
        // [ASIS] returnUrl "/portal/content/reqSharingView" — TOBE 미사용
        String stepErrReturnUrl = "/main.do";
        String userDivisionYn = (String) session.getAttribute("userDivisionYn");

        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnUrl = "/m/content/mySharingView.do";
            stepErrReturnUrl = "/m/main.do";
        }

        if(contractNum == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataReqDto myShareDataReqDto = new MyShareDataReqDto();

        NiceLogDto niceLogDto= new NiceLogDto();

        AuthSmsDto authSmsDto = SessionUtils.getSmsSession("sharing");
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  authSmsDto  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        String[] certKey= null;
        String[] certValue= null;
        Map<String,String> vldReslt= null;

        //안면인증 세션 초기화
//PNB_확인        SessionUtils.initializeFathSession();

        //비회원
        if(authSmsDto != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(contractNum);
            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList  == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            myShareDataReqDto.setBirthday(cntrList.getDobyyyymmdd());
            myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo())); // 전화번호
            myShareDataReqDto.setNcn(cntrList.getSvcCntrNo());
            myShareDataReqDto.setOpmdSvcNo(cntrList.getUnSvcNo());
            myShareDataReqDto.setName(cntrList.getUserName());
            myShareDataReqDto.setContractNum(cntrList.getContractNum());

            // ============ STEP START ============
            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"chkMemberAuth", cntrList.getUserName(), EncryptUtil.ace256Enc(cntrList.getUnUserSSn()), cntrList.getContractNum()};

            vldReslt= certService.vdlCertInfo("D", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }
            // ============ STEP END ============

            niceLogDto.setName(cntrList.getUserName());
            niceLogDto.setBirthDate(cntrList.getUnUserSSn());

            // 고객인증 sms인증 세션은 데이터 쉐어링 개통요청 시점에 null > 비로그인 데이터쉐어링 고객 인증정보 별도 보관
            McpUserCntrMngDto mcpUserCntrMngDto= new McpUserCntrMngDto();
            mcpUserCntrMngDto.setSubLinkName(authSmsDto.getAuthNum());   // 이름
            mcpUserCntrMngDto.setCntrMobileNo(authSmsDto.getPhoneNum()); // 전화번호
            SessionUtils.saveNonmemberSharingInfo(mcpUserCntrMngDto);

        }else {

            searchVO.setNcn(contractNum);

            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSessionDto.getUserId());

            if (!this.checkUserType(searchVO, cntrList, userSessionDto)) {
                throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION);
            }

            String userSsn= null;
            for(McpUserCntrMngDto dto : cntrList) {
                //if(contractNum.equals(dto.getContractNum())) {
                if(contractNum.equals(dto.getSvcCntrNo())) {
                    myShareDataReqDto.setCtn(StringMakerUtil.getPhoneNum(searchVO.getCtn())); // 전화번호
                    myShareDataReqDto.setNcn(searchVO.getNcn());
                    myShareDataReqDto.setOpmdSvcNo(dto.getUnSvcNo());
                    myShareDataReqDto.setBirthday(userSessionDto.getBirthday());
                    myShareDataReqDto.setName(userSessionDto.getName());
                    myShareDataReqDto.setContractNum(dto.getContractNum());
                    userSsn= dto.getUnUserSSn();
                }
            }

            // ============ STEP START ============
            // 계약번호
            certKey= new String[]{"urlType", "contractNum"};
            certValue= new String[]{"chkMemberAuth", myShareDataReqDto.getContractNum()};

            vldReslt= certService.vdlCertInfo("F", certKey, certValue);
            if(!AJAX_SUCCESS.equals(vldReslt.get("RESULT_CODE"))) {
                throw new McpCommonException(vldReslt.get("RESULT_DESC"), stepErrReturnUrl);
            }

            // 이름, 생년월일, 계약번호
            certKey= new String[]{"urlType", "name", "birthDate", "contractNum"};
            certValue= new String[]{"memberAuth", myShareDataReqDto.getName(), EncryptUtil.ace256Enc(userSsn), myShareDataReqDto.getContractNum()};
            vldReslt= certService.vdlCertInfo("C", certKey, certValue);
            // ============ STEP END ============

            niceLogDto.setName(myShareDataReqDto.getName());
            niceLogDto.setBirthDate(userSsn);
        }

        // ============ STEP START ============
        // nicePin연동
        Map<String, String> nicePinRtn= nicePinService.getNicePinCi(niceLogDto);
        if(!"0000".equals(nicePinRtn.get("returnCode"))) throw new McpCommonException(nicePinRtn.get("returnMsg"), stepErrReturnUrl);
        // ============ STEP END ============

        rtnMap.put("contractNum", contractNum);
        rtnMap.put("myShareDataReqDto", myShareDataReqDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    /**
     * 데이터 쉐어링
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param myShareDataReqDto
     * @param session
     * @return
     */
    @RequestMapping(value = { "/content/insertOpenRequestAjax.do", "/m/content/insertOpenRequestAjax.do" })
    public HashMap<String, Object> doinsertOpenRequestAjax(HttpServletRequest request,
                                                           @ModelAttribute("myShareDataReqDto") MyShareDataReqDto myShareDataReqDto
            , HttpSession session) {

        HashMap<String, Object> rtnMap = new HashMap<String, Object>();

        //AuthSmsDto authSmsDto = SessionUtils.getSmsSession("sharing");
        McpUserCntrMngDto nonMemberSess = SessionUtils.getNonmemberSharingInfo();
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  nonMemberSess  == null) {
            throw new McpCommonException(F_BIND_EXCEPTION);
        }

        if(nonMemberSess != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(myShareDataReqDto.getNcn());
            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }

            myShareDataReqDto.setCustId(cntrList.getCustId());
            myShareDataReqDto.setNcn(cntrList.getSvcCntrNo());
            myShareDataReqDto.setCtn(cntrList.getCntrMobileNo());
            myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());

        }else {

            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSessionDto.getUserId());
            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                    if (myShareDataReqDto.getNcn().equals(mcpUserCntrMngDto.getContractNum())) {
                        myShareDataReqDto.setCustId(mcpUserCntrMngDto.getCustId());
                        myShareDataReqDto.setNcn(mcpUserCntrMngDto.getSvcCntrNo());
                        myShareDataReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
                        myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
                        break;
                    }
                }
            }
        }

        MoscDataSharingResDto moscDataSharingChk  = new MoscDataSharingResDto();

        if(myShareDataReqDto.getSelfShareYn() != null && "Y".equals(myShareDataReqDto.getSelfShareYn())) { //셀프개통 사전체크
            myShareDataReqDto.setCrprCtn("");

            //x69 개통 사전체크
            moscDataSharingChk  = myShareDataSvc.moscDataSharingChk(myShareDataReqDto);
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        String shareDataYn = "";

        if(moscDataSharingChk.getSharingList() != null && moscDataSharingChk.getSharingList().size() > 0) {
            List<OutDataSharingDto> sharingList = moscDataSharingChk.getSharingList();

            for(OutDataSharingDto dto : sharingList) {
                if("Y".equals(dto.getRsltInd())) {
                    shareDataYn = "Y";
                    break;
                }
            }
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        if("Y".equals(shareDataYn)) {
            //x70 쉐어링 가입
            myShareDataSvc.moscDataSharingSave(myShareDataReqDto);
            rtnMap.put("RESULT_CODE", "S");
        } else {
            rtnMap.put("RESULT_CODE", "E");
        }

        session.setAttribute("opmdSvcNo", myShareDataReqDto.getOpmdSvcNo());
        return rtnMap;
    }

    /**
     * 데이터 쉐어링 완료 view
     * @author bsj
     * @Date : 2021.12.30
     * @param request
     * @param myShareDataReqDto
     * @param session
     * @return
     */

    @RequestMapping(value = { "/content/reqSharingCompleteView.do", "/m/content/reqSharingCompleteView.do" })
    public Map<String, Object> doReqSharingCompleteView(HttpServletRequest request
            ,@ModelAttribute("myShareDataReqDto") MyShareDataReqDto myShareDataReqDto
            ,HttpSession session)  {

        HashMap<String, Object> rtnMap = new HashMap<>();

        // [ASIS] returnUrl "/portal/content/reqSharingCompleteView" — TOBE 미사용
        String rtnUrl = "/content/mySharingView.do";
        if ("Y".equals(NmcpServiceUtils.isMobile())) {
            rtnUrl = "/m/content/mySharingView.do";
        }

        String rtnOpmdSvcNo = (String) session.getAttribute("opmdSvcNo");

        if(rtnOpmdSvcNo == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        MyShareDataResDto myShareDataResDto = new MyShareDataResDto();
        String svcNo = "";  //본인핸드폰번호
        String socNm = "";  //본인요금제
        String opmdSvcNoContractNum = ""; //개통이 완료된 서비스계약번호
        String opmdSvcSocNm = ""; //가입계약 요금제

        //AuthSmsDto authSmsDto = SessionUtils.getSmsSession("sharing");
        McpUserCntrMngDto nonMemberSess = SessionUtils.getNonmemberSharingInfo();
        UserSessionDto userSessionDto = SessionUtils.getUserCookieBean();

        if(userSessionDto == null &&  nonMemberSess  == null) {
            rtnMap.put("redirectUrl", rtnUrl);
            rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
            return rtnMap;
        }

        if(nonMemberSess != null) {
            McpUserCntrMngDto userCntrMngDto = new McpUserCntrMngDto();
            userCntrMngDto.setSvcCntrNo(myShareDataReqDto.getNcn());
            McpUserCntrMngDto cntrList =  msfMypageSvc.selectCntrListNoLogin(userCntrMngDto);

            if(cntrList == null) {
                throw new McpCommonException(F_BIND_EXCEPTION);
            }
            svcNo = StringMakerUtil.getPhoneNum(cntrList.getCntrMobileNo());
            myShareDataReqDto.setCustId(cntrList.getCustId());
            myShareDataReqDto.setNcn(cntrList.getContractNum());
            myShareDataReqDto.setCtn(cntrList.getCntrMobileNo());
            myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());


        }else {

            List<McpUserCntrMngDto> cntrList = msfMypageSvc.selectCntrList(userSessionDto.getUserId());

            if (cntrList != null && cntrList.size() > 0) {
                for (McpUserCntrMngDto mcpUserCntrMngDto : cntrList) {
                    if (myShareDataReqDto.getNcn().equals(mcpUserCntrMngDto.getContractNum())) {
                        svcNo = StringMakerUtil.getPhoneNum(mcpUserCntrMngDto.getUnSvcNo());
                        myShareDataReqDto.setCustId(mcpUserCntrMngDto.getCustId());
                        myShareDataReqDto.setNcn(mcpUserCntrMngDto.getContractNum());
                        myShareDataReqDto.setCtn(mcpUserCntrMngDto.getCntrMobileNo());
                        myShareDataReqDto.setOpmdSvcNo(myShareDataReqDto.getOpmdSvcNo());
                        break;
                    }
                }
            }
        }

        McpUserCntrMngDto result = msfMypageSvc.selectSocDesc(myShareDataReqDto.getNcn());

        if(result !=null) {
            socNm = result.getRateNm();
        }

        if(!StringUtil.isBlank(opmdSvcNoContractNum)) {
            McpUserCntrMngDto opmdSvcNoDto = msfMypageSvc.selectSocDesc(opmdSvcNoContractNum); //가입하는 데이터쉐어링 요금제
            if(opmdSvcNoDto !=null) {
                opmdSvcSocNm =opmdSvcNoDto.getRateNm();
            }
        }

        myShareDataResDto.setSvcNo(svcNo);
        myShareDataResDto.setSocNm(socNm);
        myShareDataResDto.setOpmdSvcSocNm(opmdSvcSocNm);
        myShareDataResDto.setOpmdSvcNo(StringMakerUtil.getPhoneNum(myShareDataReqDto.getOpmdSvcNo()));
        SessionUtils.saveNiceRes(null);
        session.removeAttribute("opmdSvcNo"); //저장완료후 삭제
        rtnMap.put("myShareDataResDto", myShareDataResDto);
        rtnMap.put("RESULT_CODE", AJAX_SUCCESS);
        return rtnMap;
    }

    private boolean checkUserType(MyPageSearchDto searchVO, List<McpUserCntrMngDto> cntrList,
                                  UserSessionDto userSession) {
        if (!StringUtil.equals(userSession.getUserDivision(), "01")) {
            return false;
        }

        if(cntrList == null) {
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
            if(userType != null && userType.length() == 13) {
                userType = userType.substring(6,7);
                if("5".equals(userType) || "6".equals(userType)) {
                    userType = "Y";
                    searchVO.setUserType(userType);
                }
            }
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

                if(userType != null && userType.length() == 13) {
                    userType = userType.substring(6,7);
                    if("5".equals(userType) || "6".equals(userType) || "7".equals(userType) || "8".equals(userType) ) {
                        userType = "Y";
                        searchVO.setUserType(userType);
                    }
                }
            }
        }

        return true;
    }

    // [ASIS] getMessageBox() — TOBE: throw new McpCommonException(NOT_FULL_MEMBER_EXCEPTION) 으로 대체
    // private ResponseSuccessDto getMessageBox() {
    //     ResponseSuccessDto mbox = new ResponseSuccessDto();
    //     String redirectUrl = "/mypage/updateForm.do";
    //     if ("Y".equals(NmcpServiceUtils.isMobile())) {
    //         redirectUrl = "/m/mypage/updateForm.do";
    //     }
    //     mbox.setRedirectUrl(redirectUrl);
    //     mbox.setSuccessMsg("정회원 인증 후 이용하실 수 있습니다.");
    //     return mbox;
    // }

}


